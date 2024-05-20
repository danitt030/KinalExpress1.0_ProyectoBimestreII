package org.danieltuy.bean;

/**
 *
 * @author Tuchez
 */
public class Compras {

    /* 
     *Creamos las variables de la base de datos que tienen que ser los mismos
     *y colocarlos en la clase Compras.
     */
    private int numeroDocumento;
    private String fechaDocumento;
    private String descripcion;
    private double totalDocumento;

    // constructor vacio de la clase Compras
    public Compras() {
    }

    // constructor lleno de la clase Compras con las variables.
    public Compras(int numeroDocumento, String fechaDocumento, String descripcion, double totalDocumento) {
        this.numeroDocumento = numeroDocumento;
        this.fechaDocumento = fechaDocumento;
        this.descripcion = descripcion;
        this.totalDocumento = totalDocumento;
    }

    // Hice get y set de cada una de las variables, ya que get es para llamar y el set para enviar.
    public int getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(int numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(String fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getTotalDocumento() {
        return totalDocumento;
    }

    public void setTotalDocumento(double totalDocumento) {
        this.totalDocumento = totalDocumento;
    }

    // Se hace un to String para que nos muestre los datos al momento de listar el comboBox de Compras
    @Override
    public String toString() {
        return getNumeroDocumento() + "  ";
    }

}
