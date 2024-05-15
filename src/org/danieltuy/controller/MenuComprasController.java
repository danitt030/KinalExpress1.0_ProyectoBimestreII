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
import org.danieltuy.bean.Compras;
import org.danieltuy.db.Conexion;
import org.danieltuy.system.Main;

/**
 *
 * @author Tuchez
 */
public class MenuComprasController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    @FXML
    ObservableList<Compras> listaCompras;
    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtNumeroDoC;
    @FXML
    private TextField txtFecDoC;
    @FXML
    private TextField txtDesC;
    @FXML
    private TextField txtToDoC;
    @FXML
    private TableView tblCompras;
    @FXML
    private TableColumn colNumeroDoC;
    @FXML
    private TableColumn colFecDoC;
    @FXML
    private TableColumn colDesC;
    @FXML
    private TableColumn colToDoC;
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
        tblCompras.setItems(getCompras());
        colNumeroDoC.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("numeroDocumento"));
        colFecDoC.setCellValueFactory(new PropertyValueFactory<Compras, String>("fechaDocumento"));
        colDesC.setCellValueFactory(new PropertyValueFactory<Compras, String>("descripcion"));
        colToDoC.setCellValueFactory(new PropertyValueFactory<Compras, Double>("totalDocumento"));

    }

    public void seleccionarElemento() {
        txtNumeroDoC.setText(String.valueOf(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento()));
        txtFecDoC.setText(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getFechaDocumento());
        txtDesC.setText(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getDescripcion());
        txtToDoC.setText(String.valueOf(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getTotalDocumento()));

    }

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
        Compras registro = new Compras();
        registro.setNumeroDocumento(Integer.parseInt(txtNumeroDoC.getText()));
        registro.setFechaDocumento(txtFecDoC.getText());
        registro.setDescripcion(txtDesC.getText());
        registro.setTotalDocumento(Double.parseDouble(txtToDoC.getText()));
        try {
            // Se utiliza el mismo metodo porque la base de datos es local
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarCompras(?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getNumeroDocumento());
            procedimiento.setString(2, registro.getFechaDocumento());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setDouble(4, registro.getTotalDocumento());
            procedimiento.execute();
            listaCompras.add(registro);

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
                if (tblCompras.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro",
                            "Eliminar la compra", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarCompras(?)}");
                            procedimiento.setInt(1, ((Compras) tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento());
                            procedimiento.execute();
                            listaCompras.remove(tblCompras.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showConfirmDialog(null, "Debe de seleccionar una compra para eliminar");
                }

        }

    }

    // editar lleva el mismo concepto que agregar y eliminar
    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblCompras.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/danieltuy/images/Actualizar.png"));
                    imgReporte.setImage(new Image("/org/danieltuy/images/ImagenCancelar.png"));
                    activarControles();
                    txtNumeroDoC.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar una compra para editar");
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarCompras(?, ?, ?, ?)}");
            Compras registro = (Compras) tblCompras.getSelectionModel().getSelectedItem();
            registro.setFechaDocumento(txtFecDoC.getText());
            registro.setDescripcion(txtDesC.getText());
            registro.setTotalDocumento(Double.parseDouble(txtToDoC.getText()));
            procedimiento.setInt(1, registro.getNumeroDocumento());
            procedimiento.setString(2, registro.getFechaDocumento());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setDouble(4, registro.getTotalDocumento());
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
        txtNumeroDoC.setEditable(false);
        txtFecDoC.setEditable(false);
        txtDesC.setEditable(false);
        txtToDoC.setEditable(false);
    }

    public void activarControles() {
        txtNumeroDoC.setEditable(true);
        txtFecDoC.setEditable(true);
        txtDesC.setEditable(true);
        txtToDoC.setEditable(true);

    }

    public void limpiarControles() {
        txtNumeroDoC.clear();
        txtFecDoC.clear();
        txtDesC.clear();
        txtToDoC.clear();

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
