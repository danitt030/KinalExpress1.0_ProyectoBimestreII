package org.danieltuy.bean;

/**
 *
 * @author Tuchez
 */
public class CargoEmpleado {
    // Creamos las variables de cargoEmpleado.
    private int codigoCargoEmpleado;
    private String nombreCargo;
    private String descripcionCargo;
    
    // constructor vacio de la clase cargoEmpleado.
    public CargoEmpleado() {
    }
    
    // constructor lleno de la clase cargoEmpleado con las variables.
    public CargoEmpleado(int codigoCargoEmpleado, String nombreCargo, String descripcionCargo) {
        this.codigoCargoEmpleado = codigoCargoEmpleado;
        this.nombreCargo = nombreCargo;
        this.descripcionCargo = descripcionCargo;
    }
    
    // Hice get y set de cada una de las variables, ya que get es para llamar y el set para enviar.

    public int getCodigoCargoEmpleado() {
        return codigoCargoEmpleado;
    }

    public void setCodigoCargoEmpleado(int codigoCargoEmpleado) {
        this.codigoCargoEmpleado = codigoCargoEmpleado;
    }

    public String getNombreCargo() {
        return nombreCargo;
    }

    public void setNombreCargo(String nombreCargo) {
        this.nombreCargo = nombreCargo;
    }

    public String getDescripcionCargo() {
        return descripcionCargo;
    }

    public void setDescripcionCargo(String descripcionCargo) {
        this.descripcionCargo = descripcionCargo;
    }

}
