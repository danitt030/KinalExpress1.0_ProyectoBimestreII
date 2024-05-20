package org.danieltuy.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.danieltuy.controller.MenuCargoEmpleadoController;
import org.danieltuy.controller.MenuClientesController;
import org.danieltuy.controller.MenuComprasController;
import org.danieltuy.controller.MenuDetalleCompraController;
import org.danieltuy.controller.MenuDetalleFacturaController;
import org.danieltuy.controller.MenuEmailProveedorController;
import org.danieltuy.controller.MenuEmpleadosController;
import org.danieltuy.controller.MenuFacturaController;
import org.danieltuy.controller.MenuPrincipalController;
import org.danieltuy.controller.MenuProductosController;
import org.danieltuy.controller.MenuProgramadorController;
import org.danieltuy.controller.MenuProveedoresController;
import org.danieltuy.controller.MenuTelefonoProveedorController;
import org.danieltuy.controller.MenuTipoProductoController;

/**
 * Documentacion 
 * Nombre: Daniel Andrés Tuy Tuchez 
 * Carné: 2023313 
 * Codigo tecnico:IN5BM 
 * Fecha de Creacion: 10/04/2024 
 * Fecha de modificaciones: 26/04/2024 10/05/2024 16/05/2024
 * 18/05/2024 19/05/2024 20/05/2024
 */
public class Main extends Application {

    /* 
     *Creamos una variable privada y esto presenta la ventana principal que nos
     *y esto nos mostrar escenas diferentes de nuestro programa.
     */
    private Stage escenarioPrincipal;
    // Creamos esta variable que representa la escena de nuestro prograna.
    private Scene escena;

    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Kinal Express");
        escenarioPrincipal.getIcons().add(new Image("/org/danieltuy/images/LOGOEMPRESA.png"));
        MenuPrincipalView();
        // Parent root = FXMLLoader.load(getClass().getResource("/org/danieltuy/view/MenuPrincipalView.fxml"));
        // Scene escena = new Scene(root);
        // escenarioPrincipal.setScene(escena);
        escenarioPrincipal.show();
    }

    // loader es un metodo para cambiar de escena
    public Initializable cambiarEscena(String fxmlname, int width, int height) throws Exception {
        Initializable resultado;
        FXMLLoader loader = new FXMLLoader();
        InputStream file = Main.class.getResourceAsStream("/org/danieltuy/view/" + fxmlname);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource("/org/danieltuy/view/" + fxmlname));

        escena = new Scene((AnchorPane) loader.load(file), width, height);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();

        resultado = (Initializable) loader.getController();

        return resultado;
    }

    // Esto es un metodo que nos permite ver el menuPrincipal de nuestro programa
    public void MenuPrincipalView() {
        try {
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml", 710, 620);
            menuPrincipalView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            // System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    // Esto es un metodo que nos permite cambiar del menuPrincipal al menuClientesView
    public void menuClientesView() {
        try {
            MenuClientesController menuClientesView = (MenuClientesController) cambiarEscena("MenuClientesView.fxml", 1100, 621);
            menuClientesView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            // System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    // Esto es un metodo que nos permite cambiar del menuPrincipal al menuTipoProductoView
    public void menuTipoProductoView() {
        try {
            MenuTipoProductoController menuTipoProductoView = (MenuTipoProductoController) cambiarEscena("MenuTipoProductoView.fxml", 1100, 621);
            menuTipoProductoView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Esto es un metodo que nos permite cambiar del menuPrincipal al menuProveedoresView
    public void menuProveedoresView() {
        try {
            MenuProveedoresController menuProveedoresView = (MenuProveedoresController) cambiarEscena("MenuProveedoresView.fxml", 1100, 621);
            menuProveedoresView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Esto es un metodo que nos permite cambiar del menuPrincipal al menuCargoEmpleadoView
    public void menuCargoEmpleadoView() {
        try {
            MenuCargoEmpleadoController menuCargoEmpleadoView = (MenuCargoEmpleadoController) cambiarEscena("MenuCargoEmpleadoView.fxml", 1100, 621);
            menuCargoEmpleadoView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Esto es un metodo que nos permite cambiar del menuPrincipal al menuComprasView
    public void menuComprasView() {
        try {
            MenuComprasController menuComprasView = (MenuComprasController) cambiarEscena("MenuComprasView.fxml", 1100, 621);
            menuComprasView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Esto es un metodo que nos permite cambiar del menuPrincipal al menuProductosView
    public void menuProductosView() {
        try {
            MenuProductosController menuProductosView = (MenuProductosController) cambiarEscena("MenuProductosView.fxml", 1100, 621);
            menuProductosView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Esto es un metodo que nos permite cambiar del menuPrincipal al menuTelefonoProveedorView
    public void menuTelefonoProveedorView() {
        try {
            MenuTelefonoProveedorController menuTelefonoProveedorView = (MenuTelefonoProveedorController) cambiarEscena("MenuTelefonoProveedorView.fxml", 1100, 621);
            menuTelefonoProveedorView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Esto es un metodo que nos permite cambiar del menuPrincipal al menuEmailProveedorView
    public void menuEmailProveedorView() {
        try {
            MenuEmailProveedorController menuEmailProveedorView = (MenuEmailProveedorController) cambiarEscena("MenuEmailProveedorView.fxml", 1100, 621);
            menuEmailProveedorView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Esto es un metodo que nos permite cambiar del menuPrincipal al menuEmpleadosView
    public void menuEmpleadosView() {
        try {
            MenuEmpleadosController menuEmpleadosView = (MenuEmpleadosController) cambiarEscena("MenuEmpleadosView.fxml", 1100, 621);
            menuEmpleadosView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Esto es un metodo que nos permite cambiar del menuPrincipal al menuFacturaView
    public void menuFacturaView() {
        try {
            MenuFacturaController menuFacturaView = (MenuFacturaController) cambiarEscena("MenuFacturaView.fxml", 1100, 621);
            menuFacturaView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Esto es un metodo que nos permite cambiar del menuPrincipal al menuDetalleCompraView
    public void menuDetalleCompraView() {
        try {
            MenuDetalleCompraController menuDetalleCompraView = (MenuDetalleCompraController) cambiarEscena("MenuDetalleCompraView.fxml", 1100, 621);
            menuDetalleCompraView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Esto es un metodo que nos permite cambiar del menuPrincipal al menuDetalleFacturaView
    public void menuDetalleFacturaView() {
        try {
            MenuDetalleFacturaController menuDetalleFacturaView = (MenuDetalleFacturaController) cambiarEscena("MenuDetalleFacturaView.fxml", 1100, 621);
            menuDetalleFacturaView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Esto es un metodo que nos permite cambiar del menuPrincipal al menuProgramador
    public void Programador() {
        try {
            MenuProgramadorController programador = (MenuProgramadorController) cambiarEscena("Programador.fxml", 993, 556);
            programador.setEscenarioPrincipal(this);
        } catch (Exception e) {
            //System.out.println(e.getMessage());
            e.printStackTrace();

        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
