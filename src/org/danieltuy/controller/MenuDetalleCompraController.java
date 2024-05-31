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
import org.danieltuy.bean.Compras;
import org.danieltuy.bean.DetalleCompra;
import org.danieltuy.bean.Productos;
import org.danieltuy.db.Conexion;
import org.danieltuy.system.Main;

/**
 *
 * @author Tuchez
 */
public class MenuDetalleCompraController implements Initializable {

    // Se importa la clase Main para que podamos realizar las acciones.
    private Main escenarioPrincipal;

    // Se utilizan enumeradores ya que se puede utilizar como metodos.
    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    // Se utiliza un ObservableList para llamar a la clase DetalleCompra.
    private ObservableList<DetalleCompra> listaDetalleCompra;
    // Se utiliza un ObservableList para llamar a la clase Productos.
    private ObservableList<Productos> listaProductos;
    // Se utiliza un ObservableList para llamar a la clase Compras.
    private ObservableList<Compras> listaCompras;
    
    // Colocamos la variable btnRegresar para que podamos regresar al menu.
    @FXML
    private Button btnRegresar;
    // Utilizamos un textField para que el usuario ingrese los datos.
    @FXML
    private TextField txtCodDetaComp;
    @FXML
    private TextField txtCosUniDetaComp;
    @FXML
    private TextField txtCanDetaComp;
    // Utilizamos un comboBox para poder listar y agregar los datos de las clases
    @FXML
    private ComboBox cmbCodProDetaComp;
    @FXML
    private ComboBox cmbNumDocDetaComp;
    // Un table view para que se muestren los datos de la tabla DetalleCompra.    
    @FXML
    private TableView tblDetalleCompra;
    /* 
     * Un table column para que muestre los datos, siempre hay que colocarlos ordenados
     * para que no se nos dificulte colocarlos en cada metodo.
     */
    @FXML
    private TableColumn colCodDetaComp;
    @FXML
    private TableColumn colCosUniDetaComp;
    @FXML
    private TableColumn colCanDetaComp;
    @FXML
    private TableColumn colCodProDetaComp;
    @FXML
    private TableColumn colNumDocDetaComp;
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
    * Carga los datos en la vista al inicializar el controlador DetalleCompra.
    */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodProDetaComp.setItems(getProducto());
        cmbNumDocDetaComp.setItems(getCompras());
    }

    // Este metodo nos permite cargar los datos a la vista hay que colocarlos de forma ordenada.
    public void cargarDatos() {
        tblDetalleCompra.setItems(getDetalleCompra());
        colCodDetaComp.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("codigoDetalleCompra"));
        colCosUniDetaComp.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Double>("costoUnitario"));
        colCanDetaComp.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("cantidad"));
        colCodProDetaComp.setCellValueFactory(new PropertyValueFactory<DetalleCompra, String>("codigoProducto"));
        colNumDocDetaComp.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("numeroDocumento"));
       
    }

    // Este metodo nos permite seleccionar los datos de la tabla DetalleCompra.
    public void seleccionarElementos() {
        txtCodDetaComp.setText(String.valueOf(((DetalleCompra) tblDetalleCompra.getSelectionModel().getSelectedItem()).getCodigoDetalleCompra()));
        txtCosUniDetaComp.setText(String.valueOf(((DetalleCompra) tblDetalleCompra.getSelectionModel().getSelectedItem()).getCostoUnitario()));
        txtCanDetaComp.setText(String.valueOf(((DetalleCompra) tblDetalleCompra.getSelectionModel().getSelectedItem()).getCantidad()));
        cmbCodProDetaComp.getSelectionModel().select(buscarProductos(((DetalleCompra) tblDetalleCompra.getSelectionModel().getSelectedItem()).getCodigoProducto()));
        cmbNumDocDetaComp.getSelectionModel().select(buscarCompras(((DetalleCompra) tblDetalleCompra.getSelectionModel().getSelectedItem()).getNumeroDocumento()));
    }

    /*
    * Nos permite buscar el producto por el codigo de producto de producto y va
    * retorna el producto que se encontro y si no se encontro sera nulo.
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
    * Nos permite buscar una compra por el numero de documento de la compra y va
    * retorna la compra que se encontro y si no se encontro sera nulo.
    */
    public Compras buscarCompras(int numeroDocumento) {
        Compras resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarCompras(?)}");
            procedimiento.setInt(1, numeroDocumento);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Compras(registro.getInt("numeroDocumento"),
                        registro.getString("fechaDocumento"),
                        registro.getString("descripcion"),
                        registro.getDouble("totalDocumento"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

   /* 
    * Se utiliza un observableList para que liste los datos de la tabla DetalleCompra
    * y utilizamos un arrayList porque no sabemos cuanto son los atributos que listaran
    * utilizamos un get para que recibir los datos del DetalleCompra y utilizamos una excepcion
    * para que no crashee el programa.
    */
    public ObservableList<DetalleCompra> getDetalleCompra() {
        ArrayList<DetalleCompra> lista = new ArrayList<DetalleCompra>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarDetalleCompra()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new DetalleCompra(resultado.getInt("codigoDetalleCompra"),
                        resultado.getDouble("costoUnitario"),
                        resultado.getInt("cantidad"),
                        resultado.getString("codigoProducto"),
                        resultado.getInt("numeroDocumento")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaDetalleCompra = FXCollections.observableArrayList(lista);

    }

   /* 
    * Se utiliza un observableList para que liste los datos de la tabla Productos
    * y utilizamos un arrayList porque no sabemos cuanto son los atributos que listaran
    * utilizamos un get para que recibir los datos de Productos y utilizamos una excepcion
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

   /* 
    * Se utiliza un observableList para que liste los datos de la tabla Compras
    * y utilizamos un arrayList porque no sabemos cuanto son los atributos que listaran
    * utilizamos un get para que recibir los datos de Compras y utilizamos una excepcion
    * para que no crashee el programa.
    */    
    public ObservableList<Compras> getCompras() {
        ArrayList<Compras> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarCompras()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Compras(resultado.getInt("numeroDocumento"),
                        resultado.getString("fechaDocumento"),
                        resultado.getString("descripcion"),
                        resultado.getDouble("totalDocumento")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaCompras = FXCollections.observableList(lista);
    }

    // Este metodo nos permite que el boton puede realizar la accion de agregar el Detalle de la compra.
    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                /*
                 * El usuario presiona el boton para agregar el detalle de la compra y le cambiara 
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
        DetalleCompra registro = new DetalleCompra();
        registro.setCodigoDetalleCompra(Integer.parseInt(txtCodDetaComp.getText()));
        registro.setCostoUnitario(Double.parseDouble(txtCosUniDetaComp.getText()));
        registro.setCantidad(Integer.parseInt(txtCanDetaComp.getText()));
        registro.setCodigoProducto(((Productos) cmbCodProDetaComp.getSelectionModel().getSelectedItem()).getCodigoProducto());
        registro.setNumeroDocumento(((Compras) cmbNumDocDetaComp.getSelectionModel().getSelectedItem()).getNumeroDocumento());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarDetalleCompra(?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoDetalleCompra());
            procedimiento.setDouble(2, registro.getCostoUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setString(4, registro.getCodigoProducto());
            procedimiento.setInt(5, registro.getNumeroDocumento());
            procedimiento.execute();

            listaDetalleCompra.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

   /*
    * Este metodo nos permite los datos de la tabla DetalleCompra y si lo elimina se muestra un mensaje
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
                if (tblDetalleCompra.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro",
                            "Eliminar el detalle de la compra", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarDetalleCompra(?)}");
                            procedimiento.setInt(1, ((DetalleCompra) tblDetalleCompra.getSelectionModel().getSelectedItem()).getCodigoDetalleCompra());
                            procedimiento.execute();
                            listaDetalleCompra.remove(tblDetalleCompra.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showConfirmDialog(null, "Debe de seleccionar el detalle de la compra para eliminar");
                }

        }

    }

    // editar lleva el mismo concepto que agregar y eliminar.
    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblDetalleCompra.getSelectionModel().getSelectedItem() != null) {
                    // Realiza la accion para actualizar los datos o cancelarlos.
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/danieltuy/images/Actualizar.png"));
                    imgReporte.setImage(new Image("/org/danieltuy/images/ImagenCancelar.png"));
                    activarControles();
                    txtCodDetaComp.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;

                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar el detalle de la compra para editar");
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

    // Actualiza los datos de la tabla DetalleCompra, Productos y Compras y se utiliza un procedimiento almacenado. 
    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarDetalleCompra(?, ?, ?, ?, ?)}");
            DetalleCompra registro = (DetalleCompra) tblDetalleCompra.getSelectionModel().getSelectedItem();
            registro.setCodigoDetalleCompra(Integer.parseInt(txtCodDetaComp.getText()));
            registro.setCostoUnitario(Double.parseDouble(txtCosUniDetaComp.getText()));
            registro.setCantidad(Integer.parseInt(txtCanDetaComp.getText()));
            registro.setCodigoProducto(((Productos) cmbCodProDetaComp.getSelectionModel().getSelectedItem()).getCodigoProducto());
            registro.setNumeroDocumento(((Compras) cmbNumDocDetaComp.getSelectionModel().getSelectedItem()).getNumeroDocumento());
            procedimiento.setInt(1, registro.getCodigoDetalleCompra());
            procedimiento.setDouble(2, registro.getCostoUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setString(4, registro.getCodigoProducto());
            procedimiento.setInt(5, registro.getNumeroDocumento());
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
        txtCodDetaComp.setEditable(false);
        txtCosUniDetaComp.setEditable(false);
        txtCanDetaComp.setEditable(false);
        cmbCodProDetaComp.setDisable(true);
        cmbNumDocDetaComp.setDisable(true);
    }

    // Este metodo lo que hace es habilitar los txt y los combobox donde ingresan los datos.       
    public void activarControles() {
        txtCodDetaComp.setEditable(true);
        txtCosUniDetaComp.setEditable(true);
        txtCanDetaComp.setEditable(true);
        cmbCodProDetaComp.setDisable(false);
        cmbNumDocDetaComp.setDisable(false);
    }

    /*
     *Este metodo nos permite limpiar los datos que ingresamos, la seleccion de
     * la tabla y los comboBox.
     */
    public void limpiarControles() {
        txtCodDetaComp.clear();
        txtCosUniDetaComp.clear();
        txtCanDetaComp.clear();
        tblDetalleCompra.getSelectionModel().getSelectedItem();
        cmbCodProDetaComp.setValue(null);
        cmbNumDocDetaComp.setValue(null);
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
