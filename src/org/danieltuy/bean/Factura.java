package org.danieltuy.bean;

/**
 *
 * @author Tuchez
 */
public class Factura {

    /* 
     *Creamos las variables de la base de datos que tienen que ser los mismos
     *y colocarlos en la clase Factura.
     */
    private int numeroFactura;
    private String estado;
    private double totalFactura;
    private String fechaFactura;
    private int codigoCliente;
    private int codigoEmpleado;

    // constructor vacio de la clase Factura
    public Factura() {
    }

    // constructor lleno de la clase Factura con las variables.
    public Factura(int numeroFactura, String estado, double totalFactura, String fechaFactura, int codigoCliente, int codigoEmpleado) {
        this.numeroFactura = numeroFactura;
        this.estado = estado;
        this.totalFactura = totalFactura;
        this.fechaFactura = fechaFactura;
        this.codigoCliente = codigoCliente;
        this.codigoEmpleado = codigoEmpleado;
    }

    // Hice get y set de cada una de las variables, ya que get es para llamar y el set para enviar.
    public int getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(int numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(double totalFactura) {
        this.totalFactura = totalFactura;
    }

    public String getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(String fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public int getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(int codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

}
