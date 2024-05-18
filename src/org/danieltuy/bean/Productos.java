package org.danieltuy.bean;

/**
 *
 * @author Tuchez
 */
public class Productos {

    /* 
     *Creamos las variables de la base de datos que tienen que ser los mismos
     *y colocarlos en la clase Productos.
     */
    private String codigoProducto;
    private String descripcionProducto;
    private double precioUnitario;
    private double precioDocena;
    private double precioMayor;
    private int existencia;
    private int codigoTipoProducto;
    private int codigoProveedor;

    // constructor vacio de la clase Productos
    public Productos() {
    }

    // constructor lleno de la clase Productos con las variables.
    public Productos(String codigoProducto, String descripcionProducto, double precioUnitario, double precioDocena, double precioMayor, int existencia, int codigoTipoProducto, int codigoProveedor) {
        this.codigoProducto = codigoProducto;
        this.descripcionProducto = descripcionProducto;
        this.precioUnitario = precioUnitario;
        this.precioDocena = precioDocena;
        this.precioMayor = precioMayor;
        this.existencia = existencia;
        this.codigoTipoProducto = codigoTipoProducto;
        this.codigoProveedor = codigoProveedor;
    }

    // Hice get y set de cada una de las variables, ya que get es para llamar y el set para enviar.
    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getPrecioDocena() {
        return precioDocena;
    }

    public void setPrecioDocena(double precioDocena) {
        this.precioDocena = precioDocena;
    }

    public double getPrecioMayor() {
        return precioMayor;
    }

    public void setPrecioMayor(double precioMayor) {
        this.precioMayor = precioMayor;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public int getCodigoTipoProducto() {
        return codigoTipoProducto;
    }

    public void setCodigoTipoProducto(int codigoTipoProducto) {
        this.codigoTipoProducto = codigoTipoProducto;
    }

    public int getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(int codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    // Se hace un to String para que nos muestre los datos al momento de listar el comboBox de la clase productos
    @Override
    public String toString() {
        return getCodigoProducto() + " " + getDescripcionProducto() + " " + getPrecioUnitario()
                + getPrecioDocena() + " " + getPrecioMayor() + " " + getExistencia()
                + getCodigoTipoProducto() + " " + getCodigoProveedor();
    }

}
