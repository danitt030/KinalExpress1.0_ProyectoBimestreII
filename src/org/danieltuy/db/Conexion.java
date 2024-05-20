package org.danieltuy.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Tuchez
 */
public class Conexion {

    // Nos sirve para hacer la conexion 
    private Connection conexion;
    // Instancea a la conexion una sola vez
    private static Conexion instancia;

    // Esto es un constructor que es donde se hace la conexión a la base de datos
    public Conexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/DBKinalExpress_2023313?useSSL=false", "Trabajos Daniel Tuy_IN5BM**", "abc123**");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* 
     *Se creo un metodo estatico que es la instancia unica de nuestra clase conexion
     *Esto hace que cuando la instancia no este creada, esto va hacer que cree una nueva instancia
     * y retorna a la clase conexion
     */
    public static Conexion getInstance() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    // este metodo get nos permite conectarnos a la base de datos.
    public Connection getConexion() {
        return conexion;
    }

    // Este metodo nos ayuda que la conexión se vuelva estable a la base de datos.
    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

}
