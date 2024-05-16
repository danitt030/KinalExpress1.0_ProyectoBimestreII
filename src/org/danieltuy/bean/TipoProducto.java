package org.danieltuy.bean;

/**
 *
 * @author Tuchez
 */
public class TipoProducto {
    
    /* 
     *Creamos las variables de la base de datos que tienen que ser los mismos
     *y colocarlos en la clase TipoProducto.
    */ 
    private int codigoTipoProducto;
    private String descripcion;
    
    // constructor vacio de la clase TipoProducto
    public TipoProducto() {
    }
    
    // constructor lleno de la clase TipoProducto con las variables.
    public TipoProducto(int codigoTipoProducto, String descripcion) {
        this.codigoTipoProducto = codigoTipoProducto;
        this.descripcion = descripcion;
    }
    
    // Hice get y set de cada una de las variables, ya que get es para llamar y el set para enviar.
    public int getCodigoTipoProducto() {
        return codigoTipoProducto;
    }

    public void setCodigoTipoProducto(int codigoTipoProducto) {
        this.codigoTipoProducto = codigoTipoProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
