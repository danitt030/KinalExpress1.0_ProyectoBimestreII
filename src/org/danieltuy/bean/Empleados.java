package org.danieltuy.bean;

/**
 *
 * @author Tuchez
 */
public class Empleados {

    /* 
     *Creamos las variables de la base de datos que tienen que ser los mismos
     *y colocarlos en la clase Empleados.
     */
    private int codigoEmpleado;
    private String nombresEmpleados;
    private String apellidosEmpleados;
    private double sueldo;
    private String direccion;
    private String turno;
    private int codigoCargoEmpleado;

    // constructor vacio de la clase Empleados
    public Empleados() {
    }

    // constructor lleno de la clase Empleados con las variables.
    public Empleados(int codigoEmpleado, String nombresEmpleados, String apellidosEmpleados, double sueldo, String direccion, String turno, int codigoCargoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
        this.nombresEmpleados = nombresEmpleados;
        this.apellidosEmpleados = apellidosEmpleados;
        this.sueldo = sueldo;
        this.direccion = direccion;
        this.turno = turno;
        this.codigoCargoEmpleado = codigoCargoEmpleado;
    }

    // Hice get y set de cada una de las variables, ya que get es para llamar y el set para enviar.
    public int getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(int codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public String getNombresEmpleados() {
        return nombresEmpleados;
    }

    public void setNombresEmpleados(String nombresEmpleados) {
        this.nombresEmpleados = nombresEmpleados;
    }

    public String getApellidosEmpleados() {
        return apellidosEmpleados;
    }

    public void setApellidosEmpleados(String apellidosEmpleados) {
        this.apellidosEmpleados = apellidosEmpleados;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public int getCodigoCargoEmpleado() {
        return codigoCargoEmpleado;
    }

    public void setCodigoCargoEmpleado(int codigoCargoEmpleado) {
        this.codigoCargoEmpleado = codigoCargoEmpleado;
    }

    // Se hace un to String para que nos muestre los datos al momento de listar el comboBox de Empleados
    @Override
    public String toString() {
        return getCodigoEmpleado() + "  ";
    }

}
