/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Reporte;

import java.util.Date;
import java.sql.Connection;
//import java.sql.Date;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Saori
 */
public class Reporte {

    public static final String DRIVER = "org.postgresql.Driver";
    public static final String RUTA = "jdbc:postgresql://quilla.lab.inf.pucp.edu.pe:1042/postgres";
    public static final String USER = "postgres";
    public static final String PASSWORD = "empaques";
    public static Connection CONEXION;

    public static void reportear(String titulo, String archIN, String archOUT) {
        try {
            Class.forName(DRIVER);
            CONEXION = DriverManager.getConnection(RUTA, USER, PASSWORD);

            String template = archIN; //ruta de la plantilla generada por el iReport
            //System.out.println(template);
            JasperReport reporte = null;
            try{
                reporte = (JasperReport) JRLoader.loadObject(template);
            }catch (JRException e){
                //new dlgMensaje((JDialog)null,true,"No se pudo generar el reporte. "+e.getMessage(),"Error");
                //System.out.println(e.getMessage());
                return;
            }
            Map param = new HashMap();

            JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param, CONEXION);
            JasperViewer visor = new JasperViewer(jasperprint, false);
            visor.setTitle("LBJ - " + titulo);
            visor.setVisible(true);

        } catch (Exception e) {
            //System.out.println(e);
            javax.swing.JOptionPane.showMessageDialog(null, e);
        }
    }

    //Reporte de Logs
    public static void reportearConParametros(String titulo, String archIN, String archOUT, Date fechaInicio, Date fechaFin, String nombre) {
        try {
            Class.forName(DRIVER);
            CONEXION = DriverManager.getConnection(RUTA, USER, PASSWORD);

            String template = archIN; //ruta de la plantilla generada por el iReport
            //System.out.println(template);
            JasperReport reporte = null;
            try{
                reporte = (JasperReport) JRLoader.loadObject(template);
            }catch (JRException e){
                //new dlgMensaje((JDialog)null,true,"No se pudo generar el reporte. "+e.getMessage(),"Error");
                //System.out.println(e.getMessage());
                return;
            }
            Map param = new HashMap();
            param.put("fechaInicio",fechaInicio);
            param.put("fechaFin",fechaFin);
            param.put("nombre", nombre);

            JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param, CONEXION);
            JasperViewer visor = new JasperViewer(jasperprint, false);
            visor.setTitle("LBJ - " + titulo);
            visor.setVisible(true);

        } catch (Exception e) {
            //System.out.println(e);
            javax.swing.JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //Reporte de Kardex: Solo lleva de parámetro el nombre del Almacén
    public static void reportearPorNombre(String titulo, String archIN, String archOUT, String nombre) {
        try {
            Class.forName(DRIVER);
            CONEXION = DriverManager.getConnection(RUTA, USER, PASSWORD);

            String template = archIN; //ruta de la plantilla generada por el iReport
            //System.out.println(template);
            JasperReport reporte = null;
            try{
                reporte = (JasperReport) JRLoader.loadObject(template);
            }catch (JRException e){
                //new dlgMensaje((JDialog)null,true,"No se pudo generar el reporte. "+e.getMessage(),"Error");
                //System.out.println(e.getMessage());
                return;
            }
            Map param = new HashMap();
            param.put("nombre", nombre);

            JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param, CONEXION);
            JasperViewer visor = new JasperViewer(jasperprint, false);
            visor.setTitle("LBJ - " + titulo);
            visor.setVisible(true);

        } catch (Exception e) {
            //System.out.println(e);
            javax.swing.JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //Reporte de Kardex: Solo lleva de parámetro fecha inicio y fecha fin
    public static void reportearPorFechas(String titulo, String archIN, String archOUT, Date fechaInicio, Date fechaFin) {
        try {
            Class.forName(DRIVER);
            CONEXION = DriverManager.getConnection(RUTA, USER, PASSWORD);

            String template = archIN; //ruta de la plantilla generada por el iReport
            //System.out.println(template);
            JasperReport reporte = null;
            try{
                reporte = (JasperReport) JRLoader.loadObject(template);
            }catch (JRException e){
                //new dlgMensaje((JDialog)null,true,"No se pudo generar el reporte. "+e.getMessage(),"Error");
                //System.out.println(e.getMessage());
                return;
            }
            Map param = new HashMap();
            param.put("fechaInicio",fechaInicio);
            param.put("fechaFin",fechaFin);

            JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param, CONEXION);
            JasperViewer visor = new JasperViewer(jasperprint, false);
            visor.setTitle("LBJ - " + titulo);
            visor.setVisible(true);

        } catch (Exception e) {
            //System.out.println(e);
            javax.swing.JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //*******************************REPORTE DE STOCK MINIMO******************************
    
    public static void reporteStockxTipo(String titulo, String archIN, String archOUT, String nombretipo) {
        try {
            Class.forName(DRIVER);
            CONEXION = DriverManager.getConnection(RUTA, USER, PASSWORD);

            String template = archIN; //ruta de la plantilla generada por el iReport
            //System.out.println(template);
            JasperReport reporte = null;
            try{
                reporte = (JasperReport) JRLoader.loadObject(template);
            }catch (JRException e){
                //new dlgMensaje((JDialog)null,true,"No se pudo generar el reporte. "+e.getMessage(),"Error");
                //System.out.println(e.getMessage());
                return;
            }
            Map param = new HashMap();
            param.put("TipoProducto", nombretipo);

            JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param, CONEXION);
            JasperViewer visor = new JasperViewer(jasperprint, false);
            visor.setTitle("LBJ - " + titulo);
            visor.setVisible(true);

        } catch (Exception e) {
            //System.out.println(e);
            javax.swing.JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //***************************REPORTE DE PRODUCTOS CON MAYOR MOVIMIENTOS******************
    
    public static void reporteProdMov(String titulo, String archIN, String archOUT, int tipo, Date fechaini, Date fechafin) {
        try {
            Class.forName(DRIVER);
            CONEXION = DriverManager.getConnection(RUTA, USER, PASSWORD);

            String template = archIN; //ruta de la plantilla generada por el iReport
            //System.out.println(template);
            JasperReport reporte = null;
            try{
                reporte = (JasperReport) JRLoader.loadObject(template);
            }catch (JRException e){
                //new dlgMensaje((JDialog)null,true,"No se pudo generar el reporte. "+e.getMessage(),"Error");
                //System.out.println(e.getMessage());
                return;
            }
            Map param = new HashMap();
            param.put("Estado", tipo);
            param.put("FechaIni", fechaini);
            param.put("FechaFin", fechafin);

            JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param, CONEXION);
            JasperViewer visor = new JasperViewer(jasperprint, false);
            visor.setTitle("LBJ - " + titulo);
            visor.setVisible(true);

        } catch (Exception e) {
            //System.out.println(e);
            javax.swing.JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public static void reporteProduccionConParametros(String titulo, String archIN, String archOUT, Date fechaInicio, Date fechaFin, Integer idGalleta) {
        try {
            Class.forName(DRIVER);
            CONEXION = DriverManager.getConnection(RUTA, USER, PASSWORD);

            String template = archIN; //ruta de la plantilla generada por el iReport
            //System.out.println(template);
            JasperReport reporte = null;
            try{
                reporte = (JasperReport) JRLoader.loadObject(template);
            }catch (JRException e){
                //new dlgMensaje((JDialog)null,true,"No se pudo generar el reporte. "+e.getMessage(),"Error");
                //System.out.println(e.getMessage());
                return;
            }
            Map param = new HashMap();
            param.put("fechaInicio",fechaInicio);
            param.put("fechaFin",fechaFin);
            param.put("idGalleta", idGalleta);

            JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param, CONEXION);
            JasperViewer visor = new JasperViewer(jasperprint, false);
            visor.setTitle("LBJ - " + titulo);
            visor.setVisible(true);

        } catch (Exception e) {
            //System.out.println(e);
            javax.swing.JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public static void reporteProduccionPorObreroConParametros(String titulo, String archIN, String archOUT, Date fechaInicio, Date fechaFin, int idActividad, int idTurno) {
        try {
            Class.forName(DRIVER);
            CONEXION = DriverManager.getConnection(RUTA, USER, PASSWORD);

            String template = archIN; //ruta de la plantilla generada por el iReport
            //System.out.println(template);
            JasperReport reporte = null;
            try{
                reporte = (JasperReport) JRLoader.loadObject(template);
            }catch (JRException e){
                //new dlgMensaje((JDialog)null,true,"No se pudo generar el reporte. "+e.getMessage(),"Error");
                //System.out.println(e.getMessage());
                return;
            }
            Map param = new HashMap();
            param.put("fechaInicio",fechaInicio);
            param.put("fechaFin",fechaFin);
            param.put("idActividad",idActividad);
            param.put("idTurno",idTurno);
            

            JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param, CONEXION);
            JasperViewer visor = new JasperViewer(jasperprint, false);
            visor.setTitle("LBJ - " + titulo);
            visor.setVisible(true);

        } catch (Exception e) {
            //System.out.println(e);
            javax.swing.JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //////*********************REPORTE PARA OBTENER LA RECETA DE UN PRODUCTO
    
    public static void reporteReceta(String titulo, String archIN, String archOUT, int id) {
        try {
            Class.forName(DRIVER);
            CONEXION = DriverManager.getConnection(RUTA, USER, PASSWORD);

            String template = archIN; //ruta de la plantilla generada por el iReport
            //System.out.println(template);
            JasperReport reporte = null;
            try{
                reporte = (JasperReport) JRLoader.loadObject(template);
            }catch (JRException e){
                //new dlgMensaje((JDialog)null,true,"No se pudo generar el reporte. "+e.getMessage(),"Error");
                //System.out.println(e.getMessage());
                return;
            }
            Map param = new HashMap();
            param.put("idProducto", id);

            JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param, CONEXION);
            JasperViewer visor = new JasperViewer(jasperprint, false);
            visor.setTitle("LBJ - " + titulo);
            visor.setVisible(true);

        } catch (Exception e) {
            //System.out.println(e);
            javax.swing.JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public static void ReporteProduccionPorActividad(String titulo, String archIN, String archOUT, Date fechaInicio, Date fechaFin) {
        try {
            Class.forName(DRIVER);
            CONEXION = DriverManager.getConnection(RUTA, USER, PASSWORD);

            String template = archIN; //ruta de la plantilla generada por el iReport
            //System.out.println(template);
            JasperReport reporte = null;
            try{
                reporte = (JasperReport) JRLoader.loadObject(template);
            }catch (JRException e){
                //new dlgMensaje((JDialog)null,true,"No se pudo generar el reporte. "+e.getMessage(),"Error");
                //System.out.println(e.getMessage());
                return;
            }
            Map param = new HashMap();
            param.put("fechaInicio",fechaInicio);
            param.put("fechaFin",fechaFin);
            
            

            JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param, CONEXION);
            JasperViewer visor = new JasperViewer(jasperprint, false);
            visor.setTitle("LBJ - " + titulo);
            visor.setVisible(true);

        } catch (Exception e) {
            //System.out.println(e);
            javax.swing.JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public static void ReporteDeAsignacion(String titulo, String archIN, String archOUT, Date fecha) {
        try {
            Class.forName(DRIVER);
            CONEXION = DriverManager.getConnection(RUTA, USER, PASSWORD);

            String template = archIN; //ruta de la plantilla generada por el iReport
            //System.out.println(template);
            JasperReport reporte = null;
            try{
                reporte = (JasperReport) JRLoader.loadObject(template);
            }catch (JRException e){
                //new dlgMensaje((JDialog)null,true,"No se pudo generar el reporte. "+e.getMessage(),"Error");
                //System.out.println(e.getMessage());
                return;
            }
            Map param = new HashMap();
            param.put("fecha",fecha);
            
            JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param, CONEXION);
            JasperViewer visor = new JasperViewer(jasperprint, false);
            visor.setTitle("LBJ - " + titulo);
            visor.setVisible(true);

        } catch (Exception e) {
            //System.out.println(e);
            javax.swing.JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //Reporte Compras
    public static void reportearComprasPorId(String titulo, String archIN, String archOUT, int num) {
        try {
            Class.forName(DRIVER);
            CONEXION = DriverManager.getConnection(RUTA, USER, PASSWORD);

            String template = archIN; //ruta de la plantilla generada por el iReport
            //System.out.println(template);
            JasperReport reporte = null;
            try{
                reporte = (JasperReport) JRLoader.loadObject(template);
            }catch (JRException e){
                //new dlgMensaje((JDialog)null,true,"No se pudo generar el reporte. "+e.getMessage(),"Error");
                //System.out.println(e.getMessage());
                return;
            }
            Map param = new HashMap();
            param.put("idOrden",num);
            
            JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param, CONEXION);
            JasperViewer visor = new JasperViewer(jasperprint, false);
            visor.setTitle("LBJ - " + titulo);
            visor.setVisible(true);

        } catch (Exception e) {
            //System.out.println(e);
            javax.swing.JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public static void reportearComprasParametros(String titulo, String archIN, String archOUT, Date fechaInicio, Date fechaFin,  int num) {
        try {
            Class.forName(DRIVER);
            CONEXION = DriverManager.getConnection(RUTA, USER, PASSWORD);

            String template = archIN; //ruta de la plantilla generada por el iReport
            //System.out.println(template);
            JasperReport reporte = null;
            try{
                reporte = (JasperReport) JRLoader.loadObject(template);
            }catch (JRException e){
                //new dlgMensaje((JDialog)null,true,"No se pudo generar el reporte. "+e.getMessage(),"Error");
                //System.out.println(e.getMessage());
                return;
            }
            Map param = new HashMap();
            param.put("idOrden",num);
            param.put("fechaInicio",fechaInicio);
            param.put("fechaFin",fechaFin);
            
            JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param, CONEXION);
            JasperViewer visor = new JasperViewer(jasperprint, false);
            visor.setTitle("LBJ - " + titulo);
            visor.setVisible(true);

        } catch (Exception e) {
            //System.out.println(e);
            javax.swing.JOptionPane.showMessageDialog(null, e);
        }
    }
}
