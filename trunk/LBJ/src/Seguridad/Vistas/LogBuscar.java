/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Seguridad.Vistas;

import Reporte.Reporte;
import Seguridad.Controlador.controladorseguridad;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelos.Usuario;
import modelos.Log;
import paquetevistas.Escritorio;

import javax.swing.JOptionPane;

/**
 *
 * @author Saori
 */
public class LogBuscar extends javax.swing.JInternalFrame {

    /**
     * Creates new form Usuario
     */

    public static Usuario usuario = lbj.LBJ.Login.usuario;
    LogTableModel myLogTableModel=new LogTableModel();
    public static controladorseguridad mycontroladorseguridad = new controladorseguridad();
    
    class LogTableModel extends AbstractTableModel {

        List<Log> logs = new ArrayList<Log>();

        public LogTableModel() {
//            logs = mycontroladorseguridad.sacalogs();
//            logs = mycontroladorseguridad.buscarlogs("",null,null);//Lo comento porque sino se demora en cargar la opción de Log
        }
        String[] titles = {"Codigo", "Usuario", "Accion", "Tabla", "Fecha"};

        public int getRowCount() {
            return logs.size();
        }

        public int getColumnCount() {
            return 5;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            String res = null;
            int codigo = 0;
            String nombre = null;
            Log l = logs.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    res = "" + l.getIdlog();
                    break;
                case 1:
                    codigo = l.getUsuario().getIdempleado();
                    nombre = mycontroladorseguridad.sacanombreusuario(codigo);;
                    res = nombre;
                    break;
                case 2:
                    res = l.getAccion();
                    break;
                case 3:
                    res = l.getTabla();
                    break;
                case 4:
                    res = "" + l.getFecha().toString();
                    break;
            }
            return res;
        }

        public String getColumnName(int col) {
            return titles[col];
        }
    }
    
    public LogBuscar() {
        initComponents();
        
        txtFechaInicio.addPropertyChangeListener( new PropertyChangeListener(){
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            // the docs of JDateChooser says that when the date is modified, a "date" property change is fired
            if ( evt.getPropertyName().equals( "date" ) ) {
                txtFechaFin.setMinSelectableDate(txtFechaInicio.getDate() );
            }
        }
        });
        
        txtFechaFin.addPropertyChangeListener( new PropertyChangeListener(){
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            // the docs of JDateChooser says that when the date is modified, a "date" property change is fired
            if ( evt.getPropertyName().equals( "date" ) ) {
                txtFechaInicio.setMaxSelectableDate(txtFechaFin.getDate() );
            }
        }
        });
        
        tblResultado.setModel(myLogTableModel);
        refreshLogTable();
//        mycontroladorseguridad.insertarlog(usuario, new Date(), "Consultar", "Log", "Consultar Logs");
    }
    
    public void refreshLogTable() {
//        myLogTableModel.logs = mycontroladorseguridad.sacalogs();
//        myLogTableModel.logs = mycontroladorseguridad.buscarlogs("",null,null);//Lo comento porque sino se demora en cargar la opción de Log
        myLogTableModel.fireTableChanged(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnBuscar = new javax.swing.JButton();
        btnReporte = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblResultado = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtNombreUsuario = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtFechaInicio = new com.toedter.calendar.JDateChooser();
        lblFechaFin = new javax.swing.JLabel();
        txtFechaFin = new com.toedter.calendar.JDateChooser();

        setClosable(true);
        setIconifiable(true);
        setTitle("Consulta de Log");

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/search.png"))); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/file plus.png"))); // NOI18N
        btnReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReporteActionPerformed(evt);
            }
        });

        jLabel2.setText("Buscar");

        jLabel3.setText("Generar Reporte");

        tblResultado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblResultado.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tblResultado.setFocusable(false);
        jScrollPane1.setViewportView(tblResultado);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Criterios de Búsqueda"));

        jLabel4.setText("Usuario:");

        jLabel5.setText("Fecha Inicio:");

        lblFechaFin.setText("Fecha Fin:");

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel4)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(txtNombreUsuario, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 122, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(7, 7, 7)
                .add(jLabel5)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(txtFechaInicio, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 122, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(lblFechaFin)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(txtFechaFin, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 122, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(14, 14, 14)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(txtFechaFin, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel4)
                        .add(txtNombreUsuario, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel5))
                    .add(txtFechaInicio, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblFechaFin))
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(28, 28, 28)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(btnBuscar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(10, 10, 10)
                                .add(jLabel2)))
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(57, 57, 57)
                                .add(btnReporte, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(41, 41, 41)
                                .add(jLabel3))))
                    .add(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 486, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(29, 29, 29)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(btnBuscar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(btnReporte, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel3)
                    .add(jLabel2))
                .add(29, 29, 29)
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(28, 28, 28)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                .add(19, 19, 19))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 12, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteActionPerformed
        // TODO add your handling code here:
        Date fechaInicio, fechaFin;
        fechaInicio = new Date(0000,01,01);
        fechaFin = new Date(9999,12,25);
        
        if(txtFechaInicio.getDate()==null && txtFechaFin.getDate()==null)
            Reporte.reportearConParametros("Reporte de Logs", "src/Reporte/Seguridad/Log.jasper", "src/Reporte/LBJ-Log.pdf",fechaInicio,fechaFin,txtNombreUsuario.getText());//Las fechas no interesan, se coloca cualquier cosa por el método, solo importa el usuario
//            Reporte.reportearConParametros("Reporte de Logs", "src/Reporte/Seguridad/Log.jasper", "src/Reporte/LBJ-Log.pdf",new Date(),txtFechaFin.getDate(),txtNombreUsuario.getText());//Las fechas no interesan, se coloca cualquier cosa por el método, solo importa el usuario
        else if (txtFechaInicio.getDate()==null)
            Reporte.reportearConParametros("Reporte de Logs", "src/Reporte/Seguridad/LogParametro.jasper", "src/Reporte/LBJ-Log.pdf",fechaInicio,txtFechaFin.getDate(),txtNombreUsuario.getText());
//            Reporte.reportearConParametros("Reporte de Logs", "src/Reporte/Seguridad/LogParametro.jasper", "src/Reporte/LBJ-Log.pdf",new Date(),txtFechaFin.getDate(),txtNombreUsuario.getText());
        else if(txtFechaFin.getDate()==null)
            Reporte.reportearConParametros("Reporte de Logs", "src/Reporte/Seguridad/LogParametro.jasper", "src/Reporte/LBJ-Log.pdf",txtFechaInicio.getDate(),fechaFin,txtNombreUsuario.getText());
//            Reporte.reportearConParametros("Reporte de Logs", "src/Reporte/Seguridad/LogParametro.jasper", "src/Reporte/LBJ-Log.pdf",txtFechaInicio.getDate(),new Date(),txtNombreUsuario.getText());
        else 
            Reporte.reportear("Reporte de Logs", "src/Reporte/Seguridad/Log.jasper", "src/Reporte/LBJ-Log.pdf");
        
        mycontroladorseguridad.insertarlog(usuario, new Date(), "Generar Reporte", "Log", "Generar Reporte Log");
    }//GEN-LAST:event_btnReporteActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        myLogTableModel.logs = mycontroladorseguridad.buscarlogs(txtNombreUsuario.getText(),txtFechaInicio.getDate(),txtFechaFin.getDate());
        myLogTableModel.fireTableChanged(null);
        mycontroladorseguridad.insertarlog(usuario, new Date(), "Consultar", "Log", "Consultar Logs");
    }//GEN-LAST:event_btnBuscarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnReporte;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFechaFin;
    private javax.swing.JTable tblResultado;
    private com.toedter.calendar.JDateChooser txtFechaFin;
    private com.toedter.calendar.JDateChooser txtFechaInicio;
    private javax.swing.JTextField txtNombreUsuario;
    // End of variables declaration//GEN-END:variables
}