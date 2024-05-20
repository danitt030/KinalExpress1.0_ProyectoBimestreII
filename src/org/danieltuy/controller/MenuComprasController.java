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

    // Se importa la clase Main para que podamos realizar las acciones.
    private Main escenarioPrincipal;

    // Se utilizan enumeradores ya que se puede utilizar como metodos.
    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    // Se utiliza un ObservableList para llamar a la clase Compras.
    @FXML
    ObservableList<Compras> listaCompras;

    // Colocamos la variable btnRegresar para que podamos regresar al menu
    @FXML
    private Button btnRegresar;
    // Utilizamos un textField para que el usuario ingrese los datos.
    @FXML
    private TextField txtNumeroDoC;
    @FXML
    private TextField txtFecDoC;
    @FXML
    private TextField txtDesC;
    @FXML
    private TextField txtToDoC;
    // Un table view para que se muestren los datos de la tabla Compras.
    @FXML
    private TableView tblCompras;
    /* 
     * Un table column para que muestre los datos, siempre hay que colocarlos ordenados
     * para que no se nos dificulte colocarlos en cada metodo.
     */
    @FXML
    private TableColumn colNumeroDoC;
    @FXML
    private TableColumn colFecDoC;
    @FXML
    private TableColumn colDesC;
    @FXML
    private TableColumn colToDoC;
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
    * Carga los datos en la vista al inicializar el controlador Compras.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }

    // Este metodo nos permite cargar los datos a la vista hay que colocarlos de forma ordenada.
    public void cargarDatos() {
        tblCompras.setItems(getCompras());
        colNumeroDoC.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("numeroDocumento"));
        colFecDoC.setCellValueFactory(new PropertyValueFactory<Compras, String>("fechaDocumento"));
        colDesC.setCellValueFactory(new PropertyValueFactory<Compras, String>("descripcion"));
        colToDoC.setCellValueFactory(new PropertyValueFactory<Compras, Double>("totalDocumento"));

    }

    // Este metodo nos permite seleccionar los datos de la tabla Compras.
    public void seleccionarElemento() {
        txtNumeroDoC.setText(String.valueOf(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento()));
        txtFecDoC.setText(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getFechaDocumento());
        txtDesC.setText(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getDescripcion());
        txtToDoC.setText(String.valueOf(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getTotalDocumento()));

    }

    /* 
    * Se utiliza un observableList para que liste los datos de la tabla compras
    * y utilizamos un arrayList porque no sabemos cuanto son los atributos que listaran
    * utilizamos un get para que recibir los datos de las compras y utilizamos una excepcion
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

    // Este metodo nos permite que el boton puede realizar la accion de agregar una compra.
    public void Agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                /*
                 * El usuario presiona el boton para agregar una compra y le cambiara 
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

    // Este metodo nos permite guardar los datos al momento de agregar una compra y que se muestre en la tabla.
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

    /*
    * Este metodo nos permite los datos de la tabla Compras y si lo elimina se muestra un mensaje
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

    // editar lleva el mismo concepto que agregar y eliminar.
    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblCompras.getSelectionModel().getSelectedItem() != null) {
                    // Realiza la accion para actualizar los datos o cancelarlos.
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

    // Actualiza los datos de la tabla Compras y se utiliza un procedimiento almacenado.
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
        txtNumeroDoC.setEditable(false);
        txtFecDoC.setEditable(false);
        txtDesC.setEditable(false);
        txtToDoC.setEditable(false);
    }

    // Este metodo nos permite habilitar los datos para ingresarlos a la tabla.
    public void activarControles() {
        txtNumeroDoC.setEditable(true);
        txtFecDoC.setEditable(true);
        txtDesC.setEditable(true);
        txtToDoC.setEditable(true);

    }

    // Este metodo nos permite limpiar los datos que ingresamos. 
    public void limpiarControles() {
        txtNumeroDoC.clear();
        txtFecDoC.clear();
        txtDesC.clear();
        txtToDoC.clear();

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
