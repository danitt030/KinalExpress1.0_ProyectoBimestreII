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

    private Main escenarioPrincipal;
    @FXML
    MenuItem btnMenuClientes, btnMenuProgramador, btnMenuTipoProducto, btnMenuProveedores,
            btnMenuCargoEmpleado, btnMenuCompras, btnMenuProductos, btnMenuTelefonoProveedor,
            btnMenuEmpleados, btnMenuEmailProveedor, btnMenuFactura, btnDetalleFactura, btnMenuDetalleCompra;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
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
