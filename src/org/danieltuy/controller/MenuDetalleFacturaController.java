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

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<DetalleFactura> listaDetalleFactura;
    private ObservableList<Factura> listaFactura;
    private ObservableList<Productos> listaProductos;

    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtCodDetaFac;
    @FXML
    private TextField txtPreUniDetaFac;
    @FXML
    private TextField txtCanDetaFac;
    @FXML
    private ComboBox cmbNumFacDetaFac;
    @FXML
    private ComboBox cmbCodProDetaFac;
    @FXML
    private TableView tblDetalleFactura;
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
        cmbNumFacDetaFac.setItems(getFactura());
        cmbCodProDetaFac.setItems(getProducto());
    }

    public void cargarDatos() {
        tblDetalleFactura.setItems(getDetalleFactura());
        colCodDetaFac.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("codigoDetalleFactura"));
        colPreUniDetaFac.setCellValueFactory(new PropertyValueFactory<Factura, Double>("precioUnitario"));
        colCanDetaFac.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("cantidad"));
        colNumFacDetaFac.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("numeroFactura"));
        colCodProDetaFac.setCellValueFactory(new PropertyValueFactory<Factura, String>("codigoProducto"));

    }

    public void seleccionarElementos() {
        txtCodDetaFac.setText(String.valueOf(((DetalleFactura) tblDetalleFactura.getSelectionModel().getSelectedItem()).getCodigoDetalleFactura()));
        txtPreUniDetaFac.setText(String.valueOf(((DetalleFactura) tblDetalleFactura.getSelectionModel().getSelectedItem()).getPrecioUnitario()));
        txtCanDetaFac.setText(String.valueOf(((DetalleFactura) tblDetalleFactura.getSelectionModel().getSelectedItem()).getCantidad()));

    }

    public Productos buscarProductos(String codigoProducto) {
        Productos resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarProductos()}");
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

    public void agregar() {
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
                            "Eliminar la compra", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
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
                    JOptionPane.showConfirmDialog(null, "Debe de seleccionar una compra para eliminar");
                }

        }

    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblDetalleFactura.getSelectionModel().getSelectedItem() != null) {
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
        txtCodDetaFac.setEditable(false);
        txtPreUniDetaFac.setEditable(false);
        txtCanDetaFac.setEditable(false);
        cmbNumFacDetaFac.setDisable(true);
        cmbCodProDetaFac.setDisable(true);
    }

    public void activarControles() {
        txtCodDetaFac.setEditable(true);
        txtPreUniDetaFac.setEditable(true);
        txtCanDetaFac.setEditable(true);
        cmbNumFacDetaFac.setDisable(false);
        cmbCodProDetaFac.setDisable(false);
    }

    public void limpiarControles() {
        txtCodDetaFac.clear();
        txtPreUniDetaFac.clear();
        txtCanDetaFac.clear();
        tblDetalleFactura.getSelectionModel().getSelectedItem();
        cmbNumFacDetaFac.getSelectionModel().getSelectedItem();
        cmbCodProDetaFac.getSelectionModel().getSelectedItem();
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
