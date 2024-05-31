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

    // Se hace un to String para que nos muestre los datos al momento de listar el comboBox de la clase cargoEmpleado
    @Override
    public String toString() {
        return getCodigoCargoEmpleado() + "  " + getNombreCargo() + "  " + getDescripcionCargo();
    }

}
