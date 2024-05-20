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
import org.danieltuy.bean.TipoProducto;
import org.danieltuy.db.Conexion;
import org.danieltuy.system.Main;

/**
 *
 * @author Tuchez
 */
public class MenuTipoProductoController implements Initializable {

    // Se importa la clase Main para que podamos realizar las acciones.
    private Main escenarioPrincipal;

    // Se utilizan enumeradores ya que se puede utilizar como metodos.
    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    // Se utiliza un ObservableList para llamar a la clase TipoProducto.
    @FXML
    ObservableList<TipoProducto> listaTipoProducto;

    // Colocamos la variable btnRegresar para que podamos regresar al menu
    @FXML
    private Button btnRegresar;
    // Utilizamos un textField para que el usuario ingrese los datos.
    @FXML
    private TextField txtCodigoTipoProductoP;
    @FXML
    private TextField txtdescripcionP;
    // Un table view para que se muestren los datos de la tabla tipoProducto
    @FXML
    private TableView tblTipoProducto;
    /* 
     * Un table column para que muestre los datos, siempre hay que colocarlos ordenados
     * para que no se nos dificulte colocarlos en cada metodo.
     */
    @FXML
    private TableColumn colCodigoTipoProductoP;
    @FXML
    private TableColumn coldescripcionP;
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
    * Carga los datos en la vista al inicializar el controlador tipoProducto.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }

    // Este metodo nos permite cargar los datos a la vista hay que colocarlos de forma ordenada.
    public void cargarDatos() {
        tblTipoProducto.setItems(getTipoProducto());
        colCodigoTipoProductoP.setCellValueFactory(new PropertyValueFactory<TipoProducto, Integer>("codigoTipoProducto"));
        coldescripcionP.setCellValueFactory(new PropertyValueFactory<TipoProducto, String>("descripcion"));
    }

    // Este metodo nos permite seleccionar los datos de la tabla TipoProducto.
    public void seleccionarElemento() {
        txtCodigoTipoProductoP.setText(String.valueOf(((TipoProducto) tblTipoProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
        txtdescripcionP.setText(String.valueOf(((TipoProducto) tblTipoProducto.getSelectionModel().getSelectedItem()).getDescripcion()));
    }

    /* 
    * Se utiliza un observableList para que liste los datos de la tabla TipoProducto
    * y utilizamos un arrayList porque no sabemos cuanto son los atributos que listaran
    * utilizamos un get para que recibir los datos del tipo del producto y utilizamos una excepcion
    * para que no crashee el programa.
     */
    public ObservableList<TipoProducto> getTipoProducto() {
        ArrayList<TipoProducto> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarTipoProducto()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new TipoProducto(resultado.getInt("codigoTipoProducto"),
                        resultado.getString("descripcion")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaTipoProducto = FXCollections.observableList(lista);
    }

    // Este metodo nos permite que el boton puede realizar la accion de agregar un tipoProducto.
    public void Agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                /*
                 * El usuario presiona el boton para agregar un tipoProducto y le cambiara 
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

    // Este metodo nos permite guardar los datos al momento de agregar un TipoProducto y que se muestre en la tabla.
    public void guardar() {
        TipoProducto registro = new TipoProducto();
        registro.setCodigoTipoProducto(Integer.parseInt(txtCodigoTipoProductoP.getText()));
        registro.setDescripcion(txtdescripcionP.getText());
        try {
            // Se utiliza el mismo metodo porque la base de datos es local
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarTipoProducto(?,?)}");
            procedimiento.setInt(1, registro.getCodigoTipoProducto());
            procedimiento.setString(2, registro.getDescripcion());
            procedimiento.execute();
            listaTipoProducto.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * Este metodo nos permite los datos de la tabla TipoProducto y si lo elimina se muestra un mensaje
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
                if (tblTipoProducto.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro",
                            "Eliminar TipoProducto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarTipoProducto(?)}");
                            procedimiento.setInt(1, ((TipoProducto) tblTipoProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto());
                            procedimiento.execute();
                            listaTipoProducto.remove(tblTipoProducto.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }

                } else {
                    JOptionPane.showConfirmDialog(null, "Debe de seleccionar un TipoProducto para eliminar");

                }
        }
    }

    // editar lleva el mismo concepto que agregar y eliminar.
    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblTipoProducto.getSelectionModel().getSelectedItem() != null) {
                    // Realiza la accion para actualizar los datos o cancelarlos.                    
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/danieltuy/images/Actualizar.png"));
                    imgReporte.setImage(new Image("/org/danieltuy/images/ImagenCancelar.png"));
                    activarControles();
                    txtCodigoTipoProductoP.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;

                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar el tipo de producto para editar");
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

    // Actualiza los datos de la tabla TipoProducto y se utiliza un procedimiento almacenado.
    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarTipoProducto(?, ?)}");
            TipoProducto registro = (TipoProducto) tblTipoProducto.getSelectionModel().getSelectedItem();
            registro.setDescripcion(txtdescripcionP.getText());
            procedimiento.setInt(1, registro.getCodigoTipoProducto());
            procedimiento.setString(2, registro.getDescripcion());
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

    // Este metodo lo que hace es desabilitar los txt donde ingresan los datos.
    public void desactivarControles() {
        txtCodigoTipoProductoP.setEditable(false);
        txtdescripcionP.setEditable(false);
    }

    // Este metodo nos permite habilitar los datos para ingresarlos a la tabla.
    public void activarControles() {
        txtCodigoTipoProductoP.setEditable(true);
        txtdescripcionP.setEditable(true);
    }

    // Este metodo nos permite limpiar los datos que ingresamos. 
    public void limpiarControles() {
        txtCodigoTipoProductoP.clear();
        txtdescripcionP.clear();
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
