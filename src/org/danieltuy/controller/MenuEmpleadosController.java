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
import org.danieltuy.bean.CargoEmpleado;
import org.danieltuy.bean.Empleados;
import org.danieltuy.db.Conexion;
import org.danieltuy.system.Main;

/**
 *
 * @author Tuchez
 */
public class MenuEmpleadosController implements Initializable {

    // Se importa la clase Main para que podamos realizar las acciones.
    private Main escenarioPrincipal;

    // Se utilizan enumeradores ya que se puede utilizar como metodos.
    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    // Se utiliza un ObservableList para llamar a la clase Empleados.
    private ObservableList<Empleados> listaEmpleados;
    // Se utiliza un ObservableList para llamar a la clase CargoEmpleado.
    private ObservableList<CargoEmpleado> listaCargoEmpleado;

    // Colocamos la variable btnRegresar para que podamos regresar al menu.
    @FXML
    private Button btnRegresar;
    // Utilizamos un textField para que el usuario ingrese los datos.
    @FXML
    private TextField txtCodigoEmple;
    @FXML
    private TextField txtNomEmple;
    @FXML
    private TextField txtApeEmple;
    @FXML
    private TextField txtSuelEmple;
    @FXML
    private TextField txtDireEmple;
    @FXML
    private TextField txtTurEmple;
    // Utilizamos un comboBox para poder listar y agregar los datos de las clases
    @FXML
    private ComboBox cmbCodCargEmple;
    // Un table view para que se muestren los datos de la tabla Empleados.    
    @FXML
    private TableView tblEmpleados;
    /* 
     * Un table column para que muestre los datos, siempre hay que colocarlos ordenados
     * para que no se nos dificulte colocarlos en cada metodo.
     */
    @FXML
    private TableColumn colCodigoEmple;
    @FXML
    private TableColumn colNomEmple;
    @FXML
    private TableColumn colApeEmple;
    @FXML
    private TableColumn colSuelEmple;
    @FXML
    private TableColumn colDireEmple;
    @FXML
    private TableColumn colTurEmple;
    @FXML
    private TableColumn colCodCargEmple;
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
    * Carga los datos en la vista al inicializar el controlador Empleados.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodCargEmple.setItems(getCargoEmpleado());
    }

    // Este metodo nos permite cargar los datos a la vista hay que colocarlos de forma ordenada.
    public void cargarDatos() {
        tblEmpleados.setItems(getEmpleados());
        colCodigoEmple.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("codigoEmpleado"));
        colNomEmple.setCellValueFactory(new PropertyValueFactory<Empleados, String>("nombresEmpleados"));
        colApeEmple.setCellValueFactory(new PropertyValueFactory<Empleados, String>("apellidosEmpleados"));
        colSuelEmple.setCellValueFactory(new PropertyValueFactory<Empleados, Double>("sueldo"));
        colDireEmple.setCellValueFactory(new PropertyValueFactory<Empleados, String>("direccion"));
        colTurEmple.setCellValueFactory(new PropertyValueFactory<Empleados, String>("turno"));
        colCodCargEmple.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("codigoCargoEmpleado"));

    }

    // Este metodo nos permite seleccionar los datos de la tabla Empleados.
    public void seleccionarElementos() {
        txtCodigoEmple.setText(String.valueOf(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
        txtNomEmple.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getNombresEmpleados());
        txtApeEmple.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getApellidosEmpleados());
        txtSuelEmple.setText(String.valueOf(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getSueldo()));
        txtDireEmple.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getDireccion());
        txtTurEmple.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getTurno());

    }

    /*
    * Nos permite buscar un CargoEmpleado por el codigoCargoEmpleado del CargoEmpleado y va
    * retornar el CargoEmpleado que se encontro y si no se encontro sera nulo.
     */
    public CargoEmpleado buscarCargoEmpleado(int codigoCargoEmpleado) {
        CargoEmpleado resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarCargoEmpleado()}");
            procedimiento.setInt(1, codigoCargoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new CargoEmpleado(registro.getInt("codigoCargoEmpleado"),
                        registro.getString("nombreCargo"),
                        registro.getString("descripcionCargo"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    /* 
    * Se utiliza un observableList para que liste los datos de la tabla Empleados
    * y utilizamos un arrayList porque no sabemos cuanto son los atributos que listaran
    * utilizamos un get para que recibir los datos de Empleados y utilizamos una excepcion
    * para que no crashee el programa.
     */
    public ObservableList<Empleados> getEmpleados() {
        ArrayList<Empleados> lista = new ArrayList<Empleados>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarEmpleados()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Empleados(resultado.getInt("codigoEmpleado"),
                        resultado.getString("nombresEmpleados"),
                        resultado.getString("apellidosEmpleados"),
                        resultado.getDouble("sueldo"),
                        resultado.getString("direccion"),
                        resultado.getString("turno"),
                        resultado.getInt("codigoCargoEmpleado")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaEmpleados = FXCollections.observableArrayList(lista);

    }

    /* 
    * Se utiliza un observableList para que liste los datos de la tabla CargoEmpleado
    * y utilizamos un arrayList porque no sabemos cuanto son los atributos que listaran
    * utilizamos un get para que recibir los datos de CargoEmpleado y utilizamos una excepcion
    * para que no crashee el programa.
     */
    public ObservableList<CargoEmpleado> getCargoEmpleado() {
        ArrayList<CargoEmpleado> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarCargoEmpleado()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new CargoEmpleado(resultado.getInt("codigoCargoEmpleado"),
                        resultado.getString("nombreCargo"),
                        resultado.getString("descripcionCargo")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaCargoEmpleado = FXCollections.observableList(lista);
    }

    // Este metodo nos permite que el boton puede realizar la accion de agregar el empleado
    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                /*
                 * El usuario presiona el boton para agregar el empleado y le cambiara 
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
        Empleados registro = new Empleados();
        registro.setCodigoEmpleado(Integer.parseInt(txtCodigoEmple.getText()));
        registro.setNombresEmpleados(txtNomEmple.getText());
        registro.setApellidosEmpleados(txtApeEmple.getText());
        registro.setSueldo((Double.parseDouble(txtSuelEmple.getText())));
        registro.setDireccion(txtDireEmple.getText());
        registro.setTurno(txtTurEmple.getText());
        registro.setCodigoCargoEmpleado(((CargoEmpleado) cmbCodCargEmple.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarEmpleados(?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoEmpleado());
            procedimiento.setString(2, registro.getNombresEmpleados());
            procedimiento.setString(3, registro.getApellidosEmpleados());
            procedimiento.setDouble(4, registro.getSueldo());
            procedimiento.setString(5, registro.getDireccion());
            procedimiento.setString(6, registro.getTurno());
            procedimiento.setInt(7, registro.getCodigoCargoEmpleado());
            procedimiento.execute();

            listaEmpleados.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
    * Este metodo nos permite los datos de la tabla Empleados y si lo elimina se muestra un mensaje
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
                if (tblEmpleados.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro",
                            "Eliminar Empleados", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarEmpleados(?)}");
                            procedimiento.setInt(1, ((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
                            procedimiento.execute();
                            listaEmpleados.remove(tblEmpleados.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
                } else {
                    JOptionPane.showConfirmDialog(null, "Debe de seleccionar un Empleado para eliminar");
                }
        }
    }

    // editar lleva el mismo concepto que agregar y eliminar.
    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblEmpleados.getSelectionModel().getSelectedItem() != null) {
                    // Realiza la accion para actualizar los datos o cancelarlos.
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/danieltuy/images/Actualizar.png"));
                    imgReporte.setImage(new Image("/org/danieltuy/images/ImagenCancelar.png"));
                    activarControles();
                    txtCodigoEmple.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;

                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar el Empleado para editar");
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

    // Actualiza los datos de la tabla Empleados, CargoEmpleado y se utiliza un procedimiento almacenado.     
    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarEmpleados(?, ?, ?, ?, ?, ?, ?)}");
            Empleados registro = (Empleados) tblEmpleados.getSelectionModel().getSelectedItem();
            registro.setCodigoEmpleado(Integer.parseInt(txtCodigoEmple.getText()));
            registro.setNombresEmpleados(txtNomEmple.getText());
            registro.setApellidosEmpleados(txtApeEmple.getText());
            registro.setSueldo(Double.parseDouble(txtSuelEmple.getText()));
            registro.setDireccion(txtDireEmple.getText());
            registro.setTurno(txtTurEmple.getText());
            registro.setCodigoCargoEmpleado(((CargoEmpleado) cmbCodCargEmple.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());
            procedimiento.setInt(1, registro.getCodigoEmpleado());
            procedimiento.setString(2, registro.getNombresEmpleados());
            procedimiento.setString(3, registro.getApellidosEmpleados());
            procedimiento.setDouble(4, registro.getSueldo());
            procedimiento.setString(5, registro.getDireccion());
            procedimiento.setString(6, registro.getTurno());
            procedimiento.setInt(7, registro.getCodigoCargoEmpleado());
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
        txtCodigoEmple.setEditable(false);
        txtNomEmple.setEditable(false);
        txtApeEmple.setEditable(false);
        txtSuelEmple.setEditable(false);
        txtDireEmple.setEditable(false);
        txtTurEmple.setEditable(false);
        cmbCodCargEmple.setDisable(true);

    }

    // Este metodo lo que hace es habilitar los txt y los combobox donde ingresan los datos.
    public void activarControles() {
        txtCodigoEmple.setEditable(true);
        txtNomEmple.setEditable(true);
        txtApeEmple.setEditable(true);
        txtSuelEmple.setEditable(true);
        txtDireEmple.setEditable(true);
        txtTurEmple.setEditable(true);
        cmbCodCargEmple.setDisable(false);
    }

    /*
     * Este metodo nos permite limpiar los datos que ingresamos, la seleccion de
     * la tabla y los comboBox.
     */
    public void limpiarControles() {
        txtCodigoEmple.clear();
        txtNomEmple.clear();
        txtApeEmple.clear();
        txtSuelEmple.clear();
        txtDireEmple.clear();
        txtTurEmple.clear();
        tblEmpleados.getSelectionModel().getSelectedItem();
        cmbCodCargEmple.getSelectionModel().getSelectedItem();
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
