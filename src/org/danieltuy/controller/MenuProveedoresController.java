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
import org.danieltuy.bean.Proveedores;
import org.danieltuy.db.Conexion;
import org.danieltuy.system.Main;

/**
 *
 * @author Tuchez
 */
public class MenuProveedoresController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    @FXML
    ObservableList<Proveedores> listaProveedores;
    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtCodProPr;
    @FXML
    private TextField txtNitProPr;
    @FXML
    private TextField txtNomProPr;
    @FXML
    private TextField txtApeProPr;
    @FXML
    private TextField txtDireProPr;
    @FXML
    private TextField txtRaSoProPr;
    @FXML
    private TextField txtConPrinPr;
    @FXML
    private TextField txtPagWebPr;
    @FXML
    private TableView tblProveedores;
    @FXML
    private TableColumn colCodProPr;
    @FXML
    private TableColumn colNitProPr;
    @FXML
    private TableColumn colNomProPr;
    @FXML
    private TableColumn colApeProPr;
    @FXML
    private TableColumn colDireProPr;
    @FXML
    private TableColumn colRaSoProPr;
    @FXML
    private TableColumn colConPrinPr;
    @FXML
    private TableColumn colPagWebPr;
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
        tblProveedores.setItems(getProveedores());
        colCodProPr.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("codigoProveedor"));
        colNitProPr.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("NITProveedor"));
        colNomProPr.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("nombresProveedor"));
        colApeProPr.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("apellidosProveedor"));
        colDireProPr.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("direccionProveedor"));
        colRaSoProPr.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("razonSocial"));
        colConPrinPr.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("contactoPrincipal"));
        colPagWebPr.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("paginaWeb"));
    }

    public void seleccionarElemento() {
        txtCodProPr.setText(String.valueOf(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
        txtNitProPr.setText(String.valueOf(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getNITProveedor()));
        txtNomProPr.setText(String.valueOf(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getNombresProveedor()));
        txtApeProPr.setText(String.valueOf(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getApellidosProveedor()));
        txtDireProPr.setText(String.valueOf(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getDireccionProveedor()));
        txtRaSoProPr.setText(String.valueOf(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getRazonSocial()));
        txtConPrinPr.setText(String.valueOf(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getContactoPrincipal()));
        txtPagWebPr.setText(String.valueOf(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getPaginaWeb()));
    }

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
        Proveedores registro = new Proveedores();
        registro.setCodigoProveedor(Integer.parseInt(txtCodProPr.getText()));
        registro.setNITProveedor(txtNitProPr.getText());
        registro.setNombresProveedor(txtNomProPr.getText());
        registro.setApellidosProveedor(txtApeProPr.getText());
        registro.setDireccionProveedor(txtDireProPr.getText());
        registro.setRazonSocial(txtRaSoProPr.getText());
        registro.setContactoPrincipal(txtConPrinPr.getText());
        registro.setPaginaWeb(txtPagWebPr.getText());
        try {

            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarProveedores(?, ?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getNITProveedor());
            procedimiento.setString(3, registro.getNombresProveedor());
            procedimiento.setString(4, registro.getApellidosProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor());
            procedimiento.setString(6, registro.getRazonSocial());
            procedimiento.setString(7, registro.getContactoPrincipal());
            procedimiento.setString(8, registro.getPaginaWeb());
            procedimiento.execute();
            listaProveedores.add(registro);
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
                if (tblProveedores.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro",
                            "Eliminar Proveedores", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarProveedores(?)}");
                            procedimiento.setInt(1, ((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor());
                            procedimiento.execute();
                            listaProveedores.remove(tblProveedores.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
                } else {
                    JOptionPane.showConfirmDialog(null, "Debe de seleccionar proveedor para eliminar");
                }
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblProveedores.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/danieltuy/images/Actualizar.png"));
                    imgReporte.setImage(new Image("/org/danieltuy/images/ImagenCancelar.png"));
                    activarControles();
                    txtCodProPr.setEditable(false);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarProveedores(?, ?, ?, ?, ?, ?, ?, ?)}");
            Proveedores registro = (Proveedores) tblProveedores.getSelectionModel().getSelectedItem();
            registro.setNITProveedor(txtNitProPr.getText());
            registro.setNombresProveedor(txtNomProPr.getText());
            registro.setApellidosProveedor(txtApeProPr.getText());
            registro.setDireccionProveedor(txtDireProPr.getText());
            registro.setRazonSocial(txtRaSoProPr.getText());
            registro.setContactoPrincipal(txtConPrinPr.getText());
            registro.setPaginaWeb(txtPagWebPr.getText());
            procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getNITProveedor());
            procedimiento.setString(3, registro.getNombresProveedor());
            procedimiento.setString(4, registro.getApellidosProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor());
            procedimiento.setString(6, registro.getRazonSocial());
            procedimiento.setString(7, registro.getContactoPrincipal());
            procedimiento.setString(8, registro.getPaginaWeb());
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
        txtCodProPr.setEditable(false);
        txtNitProPr.setEditable(false);
        txtNomProPr.setEditable(false);
        txtApeProPr.setEditable(false);
        txtDireProPr.setEditable(false);
        txtRaSoProPr.setEditable(false);
        txtConPrinPr.setEditable(false);
        txtPagWebPr.setEditable(false);

    }

    public void activarControles() {
        txtCodProPr.setEditable(true);
        txtNitProPr.setEditable(true);
        txtNomProPr.setEditable(true);
        txtApeProPr.setEditable(true);
        txtDireProPr.setEditable(true);
        txtRaSoProPr.setEditable(true);
        txtConPrinPr.setEditable(true);
        txtPagWebPr.setEditable(true);
    }

    public void limpiarControles() {
        txtCodProPr.clear();
        txtNitProPr.clear();
        txtNomProPr.clear();
        txtApeProPr.clear();
        txtDireProPr.clear();
        txtRaSoProPr.clear();
        txtConPrinPr.clear();
        txtPagWebPr.clear();
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
