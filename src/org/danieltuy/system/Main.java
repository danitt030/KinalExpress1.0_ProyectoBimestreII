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
import org.danieltuy.controller.MenuPrincipalController;
import org.danieltuy.controller.MenuProgramadorController;
import org.danieltuy.controller.MenuProveedoresController;
import org.danieltuy.controller.MenuTipoProductoController;

/**
 * Documentacion 
 * Nombre: Daniel Andrés Tuy Tuchez 
 * Carné: 2023313 
 * Codigo tecnico:IN5BM 
 * Fecha de Creacion: 10/04/2024 
 * Fecha de modificaciones: 26/04/2024 10/05/2024
 */
public class Main extends Application {

    private Stage escenarioPrincipal;
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

    public void MenuPrincipalView() {
        try {
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml", 668, 588);
            menuPrincipalView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            // System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void menuClientesView() {
        try {
            MenuClientesController menuClientesView = (MenuClientesController) cambiarEscena("MenuClientesView.fxml", 1100, 621);
            menuClientesView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            // System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    public void menuTipoProductoView() {
        try {
            MenuTipoProductoController menuTipoProductoView = (MenuTipoProductoController) cambiarEscena("MenuTipoProductoView.fxml", 1100, 621);
            menuTipoProductoView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuProveedoresView() {
        try {
            MenuProveedoresController menuProveedoresView = (MenuProveedoresController) cambiarEscena("MenuProveedoresView.fxml", 1100, 621);
            menuProveedoresView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuCargoEmpleadoView() {
        try {
            MenuCargoEmpleadoController menuCargoEmpleadoView = (MenuCargoEmpleadoController) cambiarEscena("MenuCargoEmpleadoView.fxml", 1100, 621);
            menuCargoEmpleadoView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuComprasView() {
        try {
            MenuComprasController menuComprasView = (MenuComprasController) cambiarEscena("MenuComprasView.fxml", 1100, 621);
            menuComprasView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
