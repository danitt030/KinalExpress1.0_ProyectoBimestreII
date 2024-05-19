package org.danieltuy.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.danieltuy.system.Main;

/**
 *
 * @author Tuchez
 */
public class MenuPrincipalController implements Initializable {

    // Referencia a la clase principal Main
    private Main escenarioPrincipal;

    /*
     *Elementos del MenuPrincipal con sus las variables de cada boton que son
     *En el archivo FXML
    */ 
    @FXML
    MenuItem btnMenuClientes, btnMenuProgramador, btnMenuTipoProducto, btnMenuProveedores,
            btnMenuCargoEmpleado, btnMenuCompras, btnMenuProductos, btnMenuTelefonoProveedor,
            btnMenuEmpleados, btnMenuEmailProveedor, btnMenuFactura, btnDetalleFactura, btnMenuDetalleCompra;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    // Obtiene de la clase Main donde llama al escenario principal.
    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    // Referencia a la clase Main donde establece al escenario principal.
    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    /* 
     *Aqui utilizamos el metodo handleButtonAction que nos sirve para
     *que cada boton se puede realizar la accion de acceder a los menus 
     *del programa.
     */
    @FXML
    public void handleButtonAction(ActionEvent event) {
        /* 
         *Aqui utilice un if else para el boton para acceder a los menus, para que el usuario puede
         *ingresar a diferentes menus.
         */
        if (event.getSource() == btnMenuClientes) {
            escenarioPrincipal.menuClientesView();
        } else if (event.getSource() == btnMenuProgramador) {
            escenarioPrincipal.Programador();
        } else if (event.getSource() == btnMenuTipoProducto) {
            escenarioPrincipal.menuTipoProductoView();
        } else if (event.getSource() == btnMenuProveedores) {
            escenarioPrincipal.menuProveedoresView();
        } else if (event.getSource() == btnMenuCargoEmpleado) {
            escenarioPrincipal.menuCargoEmpleadoView();
        } else if (event.getSource() == btnMenuCompras) {
            escenarioPrincipal.menuComprasView();
        } else if (event.getSource() == btnMenuProductos) {
            escenarioPrincipal.menuProductosView();
        } else if (event.getSource() == btnMenuTelefonoProveedor) {
            escenarioPrincipal.menuTelefonoProveedorView();
        } else if (event.getSource() == btnMenuEmpleados) {
            escenarioPrincipal.menuEmpleadosView();
        } else if (event.getSource() == btnMenuEmailProveedor) {
            escenarioPrincipal.menuEmailProveedorView();
        } else if (event.getSource() == btnMenuFactura) {
            escenarioPrincipal.menuFacturaView();
        } else if (event.getSource() == btnDetalleFactura) {
            escenarioPrincipal.menuDetalleFacturaView();
        } else if (event.getSource() == btnMenuDetalleCompra) {
            escenarioPrincipal.menuDetalleCompraView();
        }
    }
}
