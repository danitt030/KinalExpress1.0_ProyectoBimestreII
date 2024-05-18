package org.danieltuy.bean;

/**
 *
 * @author Tuchez
 */
public class DetalleFactura {

    /* 
     *Creamos las variables de la base de datos que tienen que ser los mismos
     *y colocarlos en la clase DetalleFactura.
     */
    private int codigoDetalleFactura;
    private double precioUnitario;
    private int cantidad;
    private int numeroFactura;
    private String codigoProducto;

    // constructor vacio de la clase DetalleFactura
    public DetalleFactura() {
    }

    // constructor lleno de la clase DetalleFactura con las variables.
    public DetalleFactura(int codigoDetalleFactura, double precioUnitario, int cantidad, int numeroFactura, String codigoProducto) {
        this.codigoDetalleFactura = codigoDetalleFactura;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.numeroFactura = numeroFactura;
        this.codigoProducto = codigoProducto;
    }

    // Hice get y set de cada una de las variables, ya que get es para llamar y el set para enviar.
    public int getCodigoDetalleFactura() {
        return codigoDetalleFactura;
    }

    public void setCodigoDetalleFactura(int codigoDetalleFactura) {
        this.codigoDetalleFactura = codigoDetalleFactura;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(int numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

}
