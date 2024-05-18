package org.danieltuy.bean;

/**
 *
 * @author Tuchez
 */
public class EmailProveedor {

    /* 
     *Creamos las variables de la base de datos que tienen que ser los mismos
     *y colocarlos en la clase EmailProveedor.
     */
    private int codigoEmailProveedor;
    private String emailProveedor;
    private String descripcion;
    private int codigoProveedor;

    // constructor vacio de la clase EmailProveedor
    public EmailProveedor() {
    }

    // constructor lleno de la clase EmailProveedor con las variables.
    public EmailProveedor(int codigoEmailProveedor, String emailProveedor, String descripcion, int codigoProveedor) {
        this.codigoEmailProveedor = codigoEmailProveedor;
        this.emailProveedor = emailProveedor;
        this.descripcion = descripcion;
        this.codigoProveedor = codigoProveedor;
    }

    // Hice get y set de cada una de las variables, ya que get es para llamar y el set para enviar.
    public int getCodigoEmailProveedor() {
        return codigoEmailProveedor;
    }

    public void setCodigoEmailProveedor(int codigoEmailProveedor) {
        this.codigoEmailProveedor = codigoEmailProveedor;
    }

    public String getEmailProveedor() {
        return emailProveedor;
    }

    public void setEmailProveedor(String emailProveedor) {
        this.emailProveedor = emailProveedor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(int codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

}
