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
import org.danieltuy.bean.EmailProveedor;
import org.danieltuy.bean.Proveedores;
import org.danieltuy.db.Conexion;
import org.danieltuy.system.Main;

/**
 *
 * @author Tuchez
 */
public class MenuEmailProveedorController implements Initializable {

    // Se importa la clase Main para que podamos realizar las acciones.
    private Main escenarioPrincipal;

    // Se utilizan enumeradores ya que se puede utilizar como metodos.
    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    // Se utiliza un ObservableList para llamar a la clase EmailProveedor.
    private ObservableList<EmailProveedor> listaEmailProveedor;
    // Se utiliza un ObservableList para llamar a la clase Proveedores.
    private ObservableList<Proveedores> listaProveedores;

    // Colocamos la variable btnRegresar para que podamos regresar al menu
    @FXML
    private Button btnRegresar;
    // Utilizamos un textField para que el usuario ingrese los datos.
    @FXML
    private TextField txtCodigoEmaPro;
    @FXML
    private TextField txtEmaPro;
    @FXML
    private TextField txtDescriEmaPro;
    // Utilizamos un comboBox para poder listar y agregar los datos de las clases
    @FXML
    private ComboBox cmbCodProEmaPro;
    // Un table view para que se muestren los datos de la tabla EmailProveedor.
    @FXML
    private TableView tblEmailProveedor;
    /* 
     * Un table column para que muestre los datos, siempre hay que colocarlos ordenados
     * para que no se nos dificulte colocarlos en cada metodo.
     */
    @FXML
    private TableColumn colCodigoEmaPro;
    @FXML
    private TableColumn colEmaPro;
    @FXML
    private TableColumn colDescriEmaPro;
    @FXML
    private TableColumn colCodProEmaPro;
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
    * Carga los datos en la vista al inicializar el controlador EmailProveedor.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodProEmaPro.setItems(getProveedores());
    }

    // Este metodo nos permite cargar los datos a la vista hay que colocarlos de forma ordenada.
    public void cargarDatos() {
        tblEmailProveedor.setItems(getEmailProveedor());
        colCodigoEmaPro.setCellValueFactory(new PropertyValueFactory<EmailProveedor, Integer>("codigoEmailProveedor"));
        colEmaPro.setCellValueFactory(new PropertyValueFactory<EmailProveedor, String>("emailProveedor"));
        colDescriEmaPro.setCellValueFactory(new PropertyValueFactory<EmailProveedor, String>("descripcion"));
        colCodProEmaPro.setCellValueFactory(new PropertyValueFactory<EmailProveedor, Integer>("codigoProveedor"));

    }

    // Este metodo nos permite seleccionar los datos de la tabla EmailProveedor.
    public void seleccionarElementos() {
        txtCodigoEmaPro.setText(String.valueOf(((EmailProveedor) tblEmailProveedor.getSelectionModel().getSelectedItem()).getCodigoEmailProveedor()));
        txtEmaPro.setText(((EmailProveedor) tblEmailProveedor.getSelectionModel().getSelectedItem()).getEmailProveedor());
        txtDescriEmaPro.setText(((EmailProveedor) tblEmailProveedor.getSelectionModel().getSelectedItem()).getDescripcion());

    }

    /*
    * Nos permite buscar un Proveedor por el codigo Proveedor del Proveedor y va
    * retorna el proveedor que se encontro y si no se encontro sera nulo.
     */
    public Proveedores buscarProveedores(int codigoProveedor) {
        Proveedores resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarProveedores()}");
            procedimiento.setInt(1, codigoProveedor);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Proveedores(registro.getInt("codigoProveedor"),
                        registro.getString("NITProveedor"),
                        registro.getString("nombresProveedor"),
                        registro.getString("apellidosProveedor"),
                        registro.getString("direccionProveedor"),
                        registro.getString("razonSocial"),
                        registro.getString("contactoPrincipal"),
                        registro.getString("paginaWeb"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    /* 
    * Se utiliza un observableList para que liste los datos de la tabla EmailProveedor
    * y utilizamos un arrayList porque no sabemos cuanto son los atributos que listaran
    * utilizamos un get para que recibir los datos del EmailProveedor y utilizamos una excepcion
    * para que no crashee el programa.
     */
    public ObservableList<EmailProveedor> getEmailProveedor() {
        ArrayList<EmailProveedor> lista = new ArrayList<EmailProveedor>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarEmailProveedor()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new EmailProveedor(resultado.getInt("codigoEmailProveedor"),
                        resultado.getString("emailProveedor"),
                        resultado.getString("descripcion"),
                        resultado.getInt("codigoProveedor")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaEmailProveedor = FXCollections.observableArrayList(lista);

    }

    /* 
    * Se utiliza un observableList para que liste los datos de la tabla Proveedores
    * y utilizamos un arrayList porque no sabemos cuanto son los atributos que listaran
    * utilizamos un get para que recibir los datos de Proveedores y utilizamos una excepcion
    * para que no crashee el programa.
     */
    public ObservableList<Proveedores> getProveedores() {
        ArrayList<Proveedores> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarProveedores()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Proveedores(resultado.getInt("codigoProveedor"),
                        resultado.getString("NITProveedor"),
                        resultado.getString("nombresProveedor"),
                        resultado.getString("apellidosProveedor"),
                        resultado.getString("direccionProveedor"),
                        resultado.getString("razonSocial"),
                        resultado.getString("contactoPrincipal"),
                        resultado.getString("paginaWeb")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProveedores = FXCollections.observableList(lista);
    }

    // Este metodo nos permite que el boton puede realizar la accion de agregar el EmailProveedor.    
    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                /*
                 * El usuario presiona el boton para agregar el EmailProveedor y le cambiara 
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
        EmailProveedor registro = new EmailProveedor();
        registro.setCodigoEmailProveedor(Integer.parseInt(txtCodigoEmaPro.getText()));
        registro.setEmailProveedor(txtEmaPro.getText());
        registro.setDescripcion(txtDescriEmaPro.getText());
        registro.setCodigoProveedor(((Proveedores) cmbCodProEmaPro.getSelectionModel().getSelectedItem()).getCodigoProveedor());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarEmailProveedor(?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoEmailProveedor());
            procedimiento.setString(2, registro.getEmailProveedor());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setInt(4, registro.getCodigoProveedor());
            procedimiento.execute();

            listaEmailProveedor.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
    * Este metodo nos permite los datos de la tabla EmailProveedor y si lo elimina se muestra un mensaje
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
                if (tblEmailProveedor.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro",
                            "Eliminar email del proveedor", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarEmailProveedor(?)}");
                            procedimiento.setInt(1, ((EmailProveedor) tblEmailProveedor.getSelectionModel().getSelectedItem()).getCodigoEmailProveedor());
                            procedimiento.execute();
                            listaEmailProveedor.remove(tblEmailProveedor.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
                } else {
                    JOptionPane.showConfirmDialog(null, "Debe de seleccionar un email del proveedor para eliminar");
                }
        }
    }

    // editar lleva el mismo concepto que agregar y eliminar.    
    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblEmailProveedor.getSelectionModel().getSelectedItem() != null) {
                    // Realiza la accion para actualizar los datos o cancelarlos.
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/danieltuy/images/Actualizar.png"));
                    imgReporte.setImage(new Image("/org/danieltuy/images/ImagenCancelar.png"));
                    activarControles();
                    txtCodigoEmaPro.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;

                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar email del proveedor para editar");
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

    // Actualiza los datos de la tabla EmailProveedor, Proveedores y se utiliza un procedimiento almacenado.     
    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarEmailProveedor(?, ?, ?, ?)}");
            EmailProveedor registro = (EmailProveedor) tblEmailProveedor.getSelectionModel().getSelectedItem();
            registro.setCodigoEmailProveedor(Integer.parseInt(txtCodigoEmaPro.getText()));
            registro.setEmailProveedor(txtEmaPro.getText());
            registro.setDescripcion(txtDescriEmaPro.getText());
            registro.setCodigoProveedor(((Proveedores) cmbCodProEmaPro.getSelectionModel().getSelectedItem()).getCodigoProveedor());
            procedimiento.setInt(1, registro.getCodigoEmailProveedor());
            procedimiento.setString(2, registro.getEmailProveedor());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setInt(4, registro.getCodigoProveedor());
            procedimiento.execute();
        } catch (Exception e) {
            e.getMessage();
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
        txtCodigoEmaPro.setEditable(false);
        txtEmaPro.setEditable(false);
        txtDescriEmaPro.setEditable(false);
        cmbCodProEmaPro.setDisable(true);
    }

    // Este metodo lo que hace es habilitar los txt y los combobox donde ingresan los datos.
    public void activarControles() {
        txtCodigoEmaPro.setEditable(true);
        txtEmaPro.setEditable(true);
        txtDescriEmaPro.setEditable(true);
        cmbCodProEmaPro.setDisable(false);
    }

    /*
     *Este metodo nos permite limpiar los datos que ingresamos, la seleccion de
     * la tabla y los comboBox.
     */
    public void limpiarControles() {
        txtCodigoEmaPro.clear();
        txtEmaPro.clear();
        txtDescriEmaPro.clear();
        tblEmailProveedor.getSelectionModel().getSelectedItem();
        cmbCodProEmaPro.getSelectionModel().getSelectedItem();
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
