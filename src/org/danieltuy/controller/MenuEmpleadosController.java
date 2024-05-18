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

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Empleados> listaEmpleados;
    private ObservableList<CargoEmpleado> listaCargoEmpleado;

    @FXML
    private Button btnRegresar;
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
    @FXML
    private ComboBox cmbCodCargEmple;
    @FXML
    private TableView tblEmpleados;
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
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnReporte;
    @FXML
    private ImageView imgEditar;
    @FXML
    private ImageView imgAgregar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private ImageView imgReporte;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodCargEmple.setItems(getCargoEmpleado());
    }

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

    public void seleccionarElementos() {
        txtCodigoEmple.setText(String.valueOf(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
        txtNomEmple.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getNombresEmpleados());
        txtApeEmple.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getApellidosEmpleados());
        txtSuelEmple.setText(String.valueOf(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getSueldo()));
        txtDireEmple.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getDireccion());
        txtTurEmple.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getTurno());
       
    }

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

    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgAgregar.setImage(new Image("/org/danieltuy/images/ImagenGuardar.png"));
                imgEliminar.setImage(new Image("/org/danieltuy/images/ImagenCancelar.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/danieltuy/images/Agregar.png"));
                imgEliminar.setImage(new Image("/org/danieltuy/images/Eliminar.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
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
                    JOptionPane.showConfirmDialog(null, "Debe de seleccionar un producto para eliminar");
                }
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblEmpleados.getSelectionModel().getSelectedItem() != null) {
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
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar el proveedor para editar");
                }
                break;
            case ACTUALIZAR:
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

    public void desactivarControles() {
        txtCodigoEmple.setEditable(false);
        txtNomEmple.setEditable(false);
        txtApeEmple.setEditable(false);
        txtSuelEmple.setEditable(false);
        txtDireEmple.setEditable(false);
        txtTurEmple.setEditable(false);
        cmbCodCargEmple.setDisable(true);

    }

    public void activarControles() {
        txtCodigoEmple.setEditable(true);
        txtNomEmple.setEditable(true);
        txtApeEmple.setEditable(true);
        txtSuelEmple.setEditable(true);
        txtDireEmple.setEditable(true);
        txtTurEmple.setEditable(true);
        cmbCodCargEmple.setDisable(false);
    }

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

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public Button getBtnRegresar() {
        return btnRegresar;
    }

    public void setBtnRegresar(Button btnRegresar) {
        this.btnRegresar = btnRegresar;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.MenuPrincipalView();
        }

    }

}
