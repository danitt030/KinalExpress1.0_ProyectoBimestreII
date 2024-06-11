/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.danieltuy.report;

import java.io.InputStream;
import java.util.Map;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.danieltuy.db.Conexion;

/**
 *
 * @author Tuchez
 */
public class GenerarReportes {
    
    public static void mostrarReportes(String nombreReporte, String titulo, Map parametros){
        InputStream reporte = GenerarReportes.class.getResourceAsStream(nombreReporte);
        try{
            JasperReport reportes = (JasperReport)JRLoader.loadObject(reporte);
            JasperPrint reporteImpreso = JasperFillManager.fillReport(reportes, parametros, Conexion.getInstance().getConexion());
            JasperViewer visor = new JasperViewer(reporteImpreso, false);
            visor.setTitle(titulo);
            visor.setVisible(true);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
}

/*
Interfaz MAP
    hashmap es uno de los objetos que implementa un conjunto  de key-value.
    Tiene un constructor sin parametros new HashMap() y su finalidad suele
    referirse para agrupar informacion en un unico objeto.
    
    Tiene cierta similitud con la coleccion objetos (ArrayList) pero con la 
    diferencia  que estos no tienen orden 

    Hash hace referencia a una tecnica de organizacion de archivos, hashing (abierto-cerrado)
    en la que se almacena registro en una direccion que es generada por una funcion se aplica a llave del

    En java se el posee una espacion de memoria y cuando se guarda un objeto en dicho espacio
    se determina su direccion aplicando una funcion a la llave que le indiquemos. Cada objeto se
    identifica mediante algun indentificador apropiado
*/