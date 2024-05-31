package org.danieltuy.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.danieltuy.bean.DetalleFactura;
import org.danieltuy.bean.Factura;
import org.danieltuy.bean.Productos;
import org.danieltuy.db.Conexion;
import org.danieltuy.system.Main;

/**
 *
 * @author Tuchez
 */
public class MenuDetalleFacturaController implements Initializable {

    // Se importa la clase Main para que podamos realizar las acciones.
    private Main escenarioPrincipal;

    // Se utilizan enumeradores ya que se puede utilizar como metodos.
    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    // Se utiliza un ObservableList para llamar a la clase DetalleFactura.
    private ObservableList<DetalleFactura> listaDetalleFactura;
    // Se utiliza un ObservableList para llamar a la clase Factura.
    private ObservableList<Factura> listaFactura;
    // Se utiliza un ObservableList para llamar a la clase Productos.
    private ObservableList<Productos> listaProductos;

    // Colocamos la variable btnRegresar para que podamos regresar al menu.
    @FXML
    private Button btnRegresar;
    // Utilizamos un textField para que el usuario ingrese los datos.
    @FXML
    private TextField txtCodDetaFac;
    @FXML
    private TextField txtPreUniDetaFac;
    @FXML
    private TextField txtCanDetaFac;
    // Utilizamos un comboBox para poder listar y agregar los datos de las clases.
    @FXML
    private ComboBox cmbNumFacDetaFac;
    @FXML
    private ComboBox cmbCodProDetaFac;
    // Un table view para que se muestren los datos de la tabla DetalleFactura.    
    @FXML
    private TableView tblDetalleFactura;
    /* 
     * Un table column para que muestre los datos, siempre hay que colocarlos ordenados
     * para que no se nos dificulte colocarlos en cada metodo.
     */
    @FXML
    private TableColumn colCodDetaFac;
    @FXML
    private TableColumn colPreUniDetaFac;
    @FXML
    private TableColumn colCanDetaFac;
    @FXML
    private TableColumn colNumFacDetaFac;
    @FXML
    private TableColumn colCodProDetaFac;
    // Utilizamos botones para que el usuario pueda realiza accion con cada uno.
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnReporte;
    // Se utiliza un ImageView para que muestre las imagenes colocadas en la vista
    @FXML
    private ImageView imgEditar;
    @FXML
    private ImageView imgAgregar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private ImageView imgReporte;

    /*
    * Carga los datos en la vista al inicializar el controlador DetalleFactura.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbNumFacDetaFac.setItems(getFactura());
        cmbCodProDetaFac.setItems(getProducto());
    }

    // Este metodo nos permite cargar los datos a la vista hay que colocarlos de forma ordenada.
    public void cargarDatos() {
        tblDetalleFactura.setItems(getDetalleFactura());
        colCodDetaFac.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("codigoDetalleFactura"));
        colPreUniDetaFac.setCellValueFactory(new PropertyValueFactory<Factura, Double>("precioUnitario"));
        colCanDetaFac.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("cantidad"));
        colNumFacDetaFac.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("numeroFactura"));
        colCodProDetaFac.setCellValueFactory(new PropertyValueFactory<Factura, String>("codigoProducto"));

    }

    // Este metodo nos permite seleccionar los datos de la tabla DetalleFactura.
    public void seleccionarElementos() {
        txtCodDetaFac.setText(String.valueOf(((DetalleFactura) tblDetalleFactura.getSelectionModel().getSelectedItem()).getCodigoDetalleFactura()));
        txtPreUniDetaFac.setText(String.valueOf(((DetalleFactura) tblDetalleFactura.getSelectionModel().getSelectedItem()).getPrecioUnitario()));
        txtCanDetaFac.setText(String.valueOf(((DetalleFactura) tblDetalleFactura.getSelectionModel().getSelectedItem()).getCantidad()));
        cmbNumFacDetaFac.getSelectionModel().select(buscarFactura(((DetalleFactura) tblDetalleFactura.getSelectionModel().getSelectedItem()).getNumeroFactura()));
        cmbCodProDetaFac.getSelectionModel().select(buscarProductos(((DetalleFactura) tblDetalleFactura.getSelectionModel().getSelectedItem()).getCodigoProducto()));
    }

    /*
    * Nos permite buscar la Factura por el numeroFactura de Factura y va
    * retornar Factura que se encontro y si no se encontro sera nulo.
     */
    public Factura buscarFactura(int numeroFactura) {
        Factura resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarFactura(?)}");
            procedimiento.setInt(1, numeroFactura);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Factura(registro.getInt("numeroFactura"),
                        registro.getString("estado"),
                        registro.getDouble("totalFactura"),
                        registro.getString("fechaFactura"),
                        registro.getInt("codigoCliente"),
                        registro.getInt("codigoEmpleado"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    /*
    * Nos permite buscar un producto por el codigoProducto del producto y va
    * retornar Productos que se encontro y si no se encontro sera nulo.
     */
    public Productos buscarProductos(String codigoProducto) {
        Productos resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarProductos(?)}");
            procedimiento.setString(1, codigoProducto);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Productos(registro.getString("codigoProducto"),
                        registro.getString("descripcionProducto"),
                        registro.getDouble("precioUnitario"),
                        registro.getDouble("precioDocena"),
                        registro.getDouble("precioMayor"),
                        registro.getInt("existencia"),
                        registro.getInt("codigoTipoProducto"),
                        registro.getInt("codigoProveedor"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    /* 
    * Se utiliza un observableList para que liste los datos de la tabla DetalleFactura
    * y utilizamos un arrayList porque no sabemos cuanto son los atributos que listaran
    * utilizamos un get para que recibir los datos del DetalleFactura y utilizamos una excepcion
    * para que no crashee el programa.
     */
    public ObservableList<DetalleFactura> getDetalleFactura() {
        ArrayList<DetalleFactura> lista = new ArrayList<DetalleFactura>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarDetalleFactura()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new DetalleFactura(resultado.getInt("codigoDetalleFactura"),
                        resultado.getDouble("precioUnitario"),
                        resultado.getInt("cantidad"),
                        resultado.getInt("numeroFactura"),
                        resultado.getString("codigoProducto")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaDetalleFactura = FXCollections.observableArrayList(lista);

    }

    /* 
    * Se utiliza un observableList para que liste los datos de la tabla Factura
    * y utilizamos un arrayList porque no sabemos cuanto son los atributos que listaran
    * utilizamos un get para que recibir los datos de Factura y utilizamos una excepcion
    * para que no crashee el programa.
     */
    public ObservableList<Factura> getFactura() {
        ArrayList<Factura> lista = new ArrayList<Factura>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarFactura()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Factura(resultado.getInt("numeroFactura"),
                        resultado.getString("estado"),
                        resultado.getDouble("totalFactura"),
                        resultado.getString("fechaFactura"),
                        resultado.getInt("codigoCliente"),
                        resultado.getInt("codigoEmpleado")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaFactura = FXCollections.observableArrayList(lista);

    }

    /* 
    * Se utiliza un observableList para que liste los datos de la tabla Productos
    * y utilizamos un arrayList porque no sabemos cuanto son los atributos que listaran
    * utilizamos un get para que recibir los datos de los Productos y utilizamos una excepcion
    * para que no crashee el programa.
     */
    public ObservableList<Productos> getProducto() {
        ArrayList<Productos> lista = new ArrayList<Productos>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarProductos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Productos(resultado.getString("codigoProducto"),
                        resultado.getString("descripcionProducto"),
                        resultado.getDouble("precioUnitario"),
                        resultado.getDouble("precioDocena"),
                        resultado.getDouble("precioMayor"),
                        resultado.getInt("existencia"),
                        resultado.getInt("codigoTipoProducto"),
                        resultado.getInt("codigoProveedor")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProductos = FXCollections.observableArrayList(lista);

    }

    // Este metodo nos permite que el boton puede realizar la accion de agregar el Detalle de la Factura.
    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                /*
                 * El usuario presiona el boton para agregar el detalle de la factura y le cambiara 
                 * la animacion del boton y de las imagenes.
                 */
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgAgregar.setImage(new Image("/org/danieltuy/images/ImagenGuardar.png"));
                imgEliminar.setImage(new Image("/org/danieltuy/images/ImagenCancelar.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                cargarDatos();
                limpiarControles();
                // Aqui los botones regresan a su estado original.
                btnAgregar.setText("Agregar");
                btnEliminar.setText("eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/danieltuy/images/Agregar.png"));
                imgEliminar.setImage(new Image("/org/danieltuy/images/Eliminar.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    // COLOCARLO EN ORDEN COMO ESTA LA BASE DE DATOS PARA QUE NO CRASHEE EL PROGRAMA
    public void guardar() {
        DetalleFactura registro = new DetalleFactura();
        registro.setCodigoDetalleFactura(Integer.parseInt(txtCodDetaFac.getText()));
        registro.setPrecioUnitario(Double.parseDouble(txtPreUniDetaFac.getText()));
        registro.setCantidad(Integer.parseInt(txtCanDetaFac.getText()));
        registro.setNumeroFactura(((Factura) cmbNumFacDetaFac.getSelectionModel().getSelectedItem()).getNumeroFactura());
        registro.setCodigoProducto(((Productos) cmbCodProDetaFac.getSelectionModel().getSelectedItem()).getCodigoProducto());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarDetalleFactura(?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoDetalleFactura());
            procedimiento.setDouble(2, registro.getPrecioUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setInt(4, registro.getNumeroFactura());
            procedimiento.setString(5, registro.getCodigoProducto());
            procedimiento.execute();

            listaDetalleFactura.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
    * Este metodo nos permite los datos de la tabla DetalleFactura y si lo elimina se muestra un mensaje
    * y se limpia la tabla al momento de eliminar esos datos.
     */
    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/danieltuy/images/Agregar.png"));
                imgEliminar.setImage(new Image("/org/danieltuy/images/Eliminar.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblDetalleFactura.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro",
                            "Eliminar el detalle de la factura", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarDetalleFactura(?)}");
                            procedimiento.setInt(1, ((DetalleFactura) tblDetalleFactura.getSelectionModel().getSelectedItem()).getCodigoDetalleFactura());
                            procedimiento.execute();
                            listaDetalleFactura.remove(tblDetalleFactura.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showConfirmDialog(null, "Debe de seleccionar detalleFactura para eliminar");
                }

        }

    }

    // editar lleva el mismo concepto que agregar y eliminar.
    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblDetalleFactura.getSelectionModel().getSelectedItem() != null) {
                    // Realiza la accion para actualizar los datos o cancelarlos.
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/danieltuy/images/Actualizar.png"));
                    imgReporte.setImage(new Image("/org/danieltuy/images/ImagenCancelar.png"));
                    activarControles();
                    txtCodDetaFac.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;

                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar el detalle de la factura para editar");
                }
                break;
            case ACTUALIZAR:
                // Si se realiza la accion los actualiza o no los botones regresaran a su estado original.
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/danieltuy/images/Editar.png"));
                imgReporte.setImage(new Image("/org/danieltuy/images/Reporte.png"));
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }

    // Actualiza los datos de la tabla DetalleFactura, Factura y Productos y se utiliza un procedimiento almacenado. 
    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarDetalleFactura(?, ?, ?, ?, ?)}");
            DetalleFactura registro = (DetalleFactura) tblDetalleFactura.getSelectionModel().getSelectedItem();
            registro.setCodigoDetalleFactura(Integer.parseInt(txtCodDetaFac.getText()));
            registro.setPrecioUnitario(Double.parseDouble(txtPreUniDetaFac.getText()));
            registro.setCantidad(Integer.parseInt(txtCanDetaFac.getText()));
            registro.setNumeroFactura(((Factura) cmbNumFacDetaFac.getSelectionModel().getSelectedItem()).getNumeroFactura());
            registro.setCodigoProducto(((Productos) cmbCodProDetaFac.getSelectionModel().getSelectedItem()).getCodigoProducto());
            procedimiento.setInt(1, registro.getCodigoDetalleFactura());
            procedimiento.setDouble(2, registro.getPrecioUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setInt(4, registro.getNumeroFactura());
            procedimiento.setString(5, registro.getCodigoProducto());
            procedimiento.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Este metodo nos permite realizar la accion en el boton reporte y haciendo la animacion de las imagenes.
    public void reportes() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/danieltuy/images/Editar.png"));
                imgReporte.setImage(new Image("/org/danieltuy/images/Reporte.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    /*
    *Este metodo lo que hace es desabilitar los txt y los combobox donde ingresan los datos.
     */
    public void desactivarControles() {
        txtCodDetaFac.setEditable(false);
        txtPreUniDetaFac.setEditable(false);
        txtCanDetaFac.setEditable(false);
        cmbNumFacDetaFac.setDisable(true);
        cmbCodProDetaFac.setDisable(true);
    }

    // Este metodo lo que hace es habilitar los txt y los combobox donde ingresan los datos. 
    public void activarControles() {
        txtCodDetaFac.setEditable(true);
        txtPreUniDetaFac.setEditable(true);
        txtCanDetaFac.setEditable(true);
        cmbNumFacDetaFac.setDisable(false);
        cmbCodProDetaFac.setDisable(false);
    }

    /*
     *Este metodo nos permite limpiar los datos que ingresamos, la seleccion de
     * la tabla y los comboBox.
     */
    public void limpiarControles() {
        txtCodDetaFac.clear();
        txtPreUniDetaFac.clear();
        txtCanDetaFac.clear();
        tblDetalleFactura.getSelectionModel().getSelectedItem();
        cmbNumFacDetaFac.setValue(null);
        cmbCodProDetaFac.setValue(null);
    }

    // Referencia a la clase Main donde establece al escenario principal.
    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    // Este metodo nos permite realizar la accion en el boton regresar del controlador y lo retorna.    
    public Button getBtnRegresar() {
        return btnRegresar;
    }

    /*
     * Es un metodo que coloca un nuevo boton que es BTNREGRESAR y es un objeto
     * que se le asignara al atributo regresar.
     */
    public void setBtnRegresar(Button btnRegresar) {
        this.btnRegresar = btnRegresar;
    }

    /*
     * Este metodo maneja la interaccion del boton regresar y si inyecta en la vista
     * y verifica si fue hecha la accion que fue generado por el botón
     * regresar y que vuelva al menu principal.
     */
    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.MenuPrincipalView();
        }

    }
}
