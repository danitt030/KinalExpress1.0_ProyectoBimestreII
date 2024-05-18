package org.danieltuy.bean;

/**
 *
 * @author Tuchez
 */
public class DetalleCompra {

    /* 
     *Creamos las variables de la base de datos que tienen que ser los mismos
     *y colocarlos en la clase DetalleCompra.
     */
    private int codigoDetalleCompra;
    private double costoUnitario;
    private int cantidad;
    private String codigoProducto;
    private int numeroDocumento;

    // constructor vacio de la clase DetalleCompra
    public DetalleCompra() {
    }

    // constructor lleno de la clase DetalleCompra con las variables.
    public DetalleCompra(int codigoDetalleCompra, double costoUnitario, int cantidad, String codigoProducto, int numeroDocumento) {
        this.codigoDetalleCompra = codigoDetalleCompra;
        this.costoUnitario = costoUnitario;
        this.cantidad = cantidad;
        this.codigoProducto = codigoProducto;
        this.numeroDocumento = numeroDocumento;
    }

    // Hice get y set de cada una de las variables, ya que get es para llamar y el set para enviar.
    public int getCodigoDetalleCompra() {
        return codigoDetalleCompra;
    }

    public void setCodigoDetalleCompra(int codigoDetalleCompra) {
        this.codigoDetalleCompra = codigoDetalleCompra;
    }

    public double getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(double costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public int getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(int numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

}
