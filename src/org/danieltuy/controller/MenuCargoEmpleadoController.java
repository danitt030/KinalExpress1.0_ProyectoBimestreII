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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.danieltuy.bean.CargoEmpleado;
import org.danieltuy.db.Conexion;
import org.danieltuy.system.Main;

/**
 *
 * @author Tuchez
 */
public class MenuCargoEmpleadoController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    @FXML
    ObservableList<CargoEmpleado> listaCargoEmpleado;
    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtCodigoCargE;
    @FXML
    private TextField txtNomCargE;
    @FXML
    private TextField txtDescripCargE;
    @FXML
    private TableView tblCargoEmpleado;
    @FXML
    private TableColumn colCodigoCargE;
    @FXML
    private TableColumn colNomCargE;
    @FXML
    private TableColumn colDescripCargE;
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
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }

    public void cargarDatos() {
        tblCargoEmpleado.setItems(getCargoEmpleado());
        colCodigoCargE.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, Integer>("codigoCargoEmpleado"));
        colNomCargE.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, String>("nombreCargo"));
        colDescripCargE.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, String>("descripcionCargo"));

    }

    public void seleccionarElemento() {
        txtCodigoCargE.setText(String.valueOf(((CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado()));
        txtNomCargE.setText(((CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem()).getNombreCargo());
        txtDescripCargE.setText(((CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem()).getDescripcionCargo());

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

    public void Agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
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

    public void guardar() {
        CargoEmpleado registro = new CargoEmpleado();
        registro.setCodigoCargoEmpleado(Integer.parseInt(txtCodigoCargE.getText()));
        registro.setNombreCargo(txtNomCargE.getText());
        registro.setDescripcionCargo(txtDescripCargE.getText());
        try {
            // Se utiliza el mismo metodo porque la base de datos es local
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarCargoEmpleado(?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoCargoEmpleado());
            procedimiento.setString(2, registro.getNombreCargo());
            procedimiento.setString(3, registro.getDescripcionCargo());
            procedimiento.execute();
            listaCargoEmpleado.add(registro);

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
                if (tblCargoEmpleado.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro",
                            "Eliminar el cargo de empleado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarCargoEmpleado(?)}");
                            procedimiento.setInt(1, ((CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());
                            procedimiento.execute();
                            listaCargoEmpleado.remove(tblCargoEmpleado.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showConfirmDialog(null, "Debe de seleccionar un cargo de empleado para eliminar");
                }

        }

    }

    // editar lleva el mismo concepto que agregar y eliminar
    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblCargoEmpleado.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/danieltuy/images/Actualizar.png"));
                    imgReporte.setImage(new Image("/org/danieltuy/images/ImagenCancelar.png"));
                    activarControles();
                    txtCodigoCargE.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un cargo de empleado para editar");
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarCargoEmpleado(?, ?, ?)}");
            CargoEmpleado registro = (CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem();
            registro.setNombreCargo(txtNomCargE.getText());
            registro.setDescripcionCargo(txtDescripCargE.getText());
            procedimiento.setInt(1, registro.getCodigoCargoEmpleado());
            procedimiento.setString(2, registro.getNombreCargo());
            procedimiento.setString(3, registro.getDescripcionCargo());
            procedimiento.execute();
        } catch (Exception e) {
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
        txtCodigoCargE.setEditable(false);
        txtNomCargE.setEditable(false);
        txtDescripCargE.setEditable(false);

    }

    public void activarControles() {
        txtCodigoCargE.setEditable(true);
        txtNomCargE.setEditable(true);
        txtDescripCargE.setEditable(true);

    }

    public void limpiarControles() {
        txtCodigoCargE.clear();
        txtNomCargE.clear();
        txtDescripCargE.clear();

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
