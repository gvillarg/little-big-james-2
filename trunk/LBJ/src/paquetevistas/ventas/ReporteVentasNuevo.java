
package paquetevistas.ventas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class ReporteVentasNuevo {
    public static final String DRIVER = "org.postgresql.Driver";
    public static final String RUTA = "jdbc:postgresql://quilla.lab.inf.pucp.edu.pe:1042/postgres";
    public static final String USER = "postgres";
    public static final String PASSWORD = "empaques";
    public static Connection CONEXION;
    
    public ReporteVentasNuevo() {
        
    }
    public void nuevoReporte(int index, Integer cliente, Integer producto, Date fechaInicio, Date fechaFin) {
        try {
                Class.forName(DRIVER);
                CONEXION = DriverManager.getConnection(RUTA, USER, PASSWORD);
                
                Map param = new HashMap();
                param.put("inicio", fechaInicio);
                param.put("fin",fechaFin);
                String titulo = "";
                String template = ""; //ruta de la plantilla generada por el iReport
                switch (index) {
                    case 1: template = "src/Reporte/Ventas/VentasTotales.jasper";
                            titulo = "Ventas Totales";
                        break;
                    case 2: template = "src/Reporte/Ventas/TotalesCliente.jasper";
                            param.put("idCliente",cliente);
                            titulo = "Ventas Totales Por Cliente";
                        break;
                    case 3: template = "src/Reporte/Ventas/TotalesProducto.jasper";
                            param.put("producto",producto);
                            titulo = "Ventas Totales Por Producto";
                        break;
                    case 4: template = "src/Reporte/Ventas/MejoresTodo.jasper";
                            titulo = "Mejores Ventas";
                        break;
                    case 5: template = "src/Reporte/Ventas/MejoresCliente.jasper";
                            param.put("idCliente",cliente);
                            titulo = "Mejores Ventas Por Cliente";
                        break;
                    case 6: template = "src/Reporte/Ventas/MejoresProducto.jasper";
                            param.put("producto",producto);
                            titulo = "Mejores Ventas Por Producto";
                        break;
                    case 7: template = "src/Reporte/Ventas/TotalesTodo.jasper";
                            param.put("cliente",cliente);
                            param.put("producto",producto);
                            titulo = "Ventas Totales por Cliente y Producto";
                        break;
                    case 8: template = "src/Reporte/Ventas/MejoresTodo";
                            param.put("cliente",cliente);
                            param.put("producto",producto);
                            titulo = "Mejores Ventas por Cliente y Producto.jasper";
                        break;
                }
                
                JasperReport reporte = null;
                try{
                    reporte = (JasperReport) JRLoader.loadObject(template);
                }catch (JRException e){
                    //new dlgMensaje((JDialog)null,true,"No se pudo generar el reporte. "+e.getMessage(),"Error");
                    //System.out.println(e.getMessage());
                    e.printStackTrace();
                    return;
                }

                JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param, CONEXION);
                JasperViewer visor = new JasperViewer(jasperprint, false);
                visor.setTitle("LBJ - " + titulo);
                visor.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                javax.swing.JOptionPane.showMessageDialog(null, e);
            }
        }
    }
