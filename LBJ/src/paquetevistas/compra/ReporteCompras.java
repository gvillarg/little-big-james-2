/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paquetevistas.compra;

import Reporte.Reporte;
import Seguridad.Controlador.controladorseguridad;
import java.util.Date;
import modelos.Usuario;
import controlador.compra.controladorcompra;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import modelos.Compra;
import modelos.Requirimientocompra;

/**
 *
 * @author Saori
 */
public class ReporteCompras extends javax.swing.JInternalFrame {

    /**
     * Creates new form ReporteCompras
     */
    
    public static Usuario usuario = lbj.LBJ.Login.usuario;
    public static controladorseguridad mycontroladorseguridad = new controladorseguridad();
    public static controladorcompra mycontroladorcompra = new controladorcompra();
    
    public ReporteCompras() {
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
        
        loadComponents();
    }
    
    private void loadComponents() {
        List<Requirimientocompra>reqcompras =new ArrayList<Requirimientocompra>();
        int estado=-1;
        reqcompras=mycontroladorcompra.sacaRequerimientos(estado);
        String orden="";
        for (int i=0; i < reqcompras.size(); i++){
            orden="Orden de compra-"+reqcompras.get(i).getIdreqerimientocompra();
            cmbOrden.addItem(orden);
        }
        cmbOrden.setSelectedIndex(0);
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
        btnGenerar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cmbOrden = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        txtFechaInicio = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        txtFechaFin = new com.toedter.calendar.JDateChooser();

        setClosable(true);
        setIconifiable(true);
        setTitle("Reporte Compras");

        btnGenerar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/tick 1.png"))); // NOI18N
        btnGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarActionPerformed(evt);
            }
        });

        jLabel5.setText("Generar Reporte");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Seleccione los parametros"));

        jLabel2.setText("Orden de Compra:");

        cmbOrden.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Todos" }));

        jLabel3.setText("Fecha inicio:");

        jLabel4.setText("Fecha Fin:");

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel3)
                    .add(jLabel4)
                    .add(jLabel2))
                .add(18, 18, 18)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(cmbOrden, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(txtFechaInicio, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                    .add(txtFechaFin, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .add(0, 36, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(cmbOrden, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(txtFechaInicio, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel3))
                .add(17, 17, 17)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel4)
                    .add(txtFechaFin, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(106, 106, 106)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel5)
                            .add(btnGenerar))))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(15, 15, 15)
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(btnGenerar)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel5)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarActionPerformed
        // TODO add your handling code here:
        Date fechaInicio, fechaFin;
        fechaInicio = new Date(0000,01,01);
        fechaFin = new Date(9999,12,25);
        int position = 0, numorden = 0;
        String strorden;
        
        String orden=cmbOrden.getSelectedItem().toString();
        if(!orden.equalsIgnoreCase("Todos")){
            position=orden.indexOf("-")+1;
            strorden=""+orden.charAt(position);
            numorden = Integer.parseInt(strorden);
        }
        
        if(txtFechaInicio.getDate()==null && txtFechaFin.getDate()==null && cmbOrden.getSelectedItem().toString().equalsIgnoreCase("Todos"))
            Reporte.reportear("Compras", "src/Reporte/Compras/Compras.jasper", "src/Reporte/LBJ-Compra.pdf");
        else if (txtFechaInicio.getDate()==null && txtFechaFin.getDate()!=null && !cmbOrden.getSelectedItem().toString().equalsIgnoreCase("Todos"))
            Reporte.reportearComprasParametros("Compras", "src/Reporte/Compras/ComprasParametro.jasper", "src/Reporte/LBJ-Compra.pdf",fechaInicio,txtFechaInicio.getDate(),numorden);
        else if (txtFechaInicio.getDate()==null && txtFechaFin.getDate()!=null && cmbOrden.getSelectedItem().toString().equalsIgnoreCase("Todos"))
            Reporte.reportearPorFechas("Compras", "src/Reporte/Compras/ComprasSoloFechas.jasper", "src/Reporte/LBJ-Compra.pdf",fechaInicio,txtFechaInicio.getDate());
        else if (txtFechaInicio.getDate()!=null && txtFechaFin.getDate()==null && !cmbOrden.getSelectedItem().toString().equalsIgnoreCase("Todos"))
            Reporte.reportearComprasParametros("Compras", "src/Reporte/Compras/ComprasParametro.jasper", "src/Reporte/LBJ-Compra.pdf",txtFechaInicio.getDate(),fechaFin,numorden);
        else if (txtFechaInicio.getDate()!=null && txtFechaFin.getDate()==null && cmbOrden.getSelectedItem().toString().equalsIgnoreCase("Todos"))
            Reporte.reportearPorFechas("Compras", "src/Reporte/Compras/ComprasSoloFechas.jasper", "src/Reporte/LBJ-Compra.pdf",txtFechaInicio.getDate(),fechaFin);
        else if (txtFechaInicio.getDate()==null && txtFechaFin.getDate()==null && !cmbOrden.getSelectedItem().toString().equalsIgnoreCase("Todos"))
            Reporte.reportearComprasPorId("Compras", "src/Reporte/Compras/ComprasSoloId.jasper", "src/Reporte/LBJ-Compra.pdf",numorden);
        else if (txtFechaInicio.getDate()!=null && txtFechaFin.getDate()!=null && cmbOrden.getSelectedItem().toString().equalsIgnoreCase("Todos"))
            Reporte.reportearPorFechas("Compras", "src/Reporte/Compras/ComprasSoloFechas.jasper", "src/Reporte/LBJ-Compra.pdf",txtFechaInicio.getDate(),txtFechaFin.getDate());
        else if (txtFechaInicio.getDate()!=null && txtFechaFin.getDate()!=null && !cmbOrden.getSelectedItem().toString().equalsIgnoreCase("Todos"))
            Reporte.reportearComprasParametros("Compras", "src/Reporte/Compras/ComprasParametro.jasper", "src/Reporte/LBJ-Compra.pdf",txtFechaInicio.getDate(),txtFechaFin.getDate(),numorden);
        else
           Reporte.reportear("Compras", "src/Reporte/Compras/Compras.jasper", "src/Reporte/LBJ-Compra.pdf"); 
        
        mycontroladorseguridad.insertarlog(usuario, new Date(), "Generar Reporte Compras", "Compra", "Generar Reporte Compras");
    }//GEN-LAST:event_btnGenerarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGenerar;
    private javax.swing.JComboBox cmbOrden;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private com.toedter.calendar.JDateChooser txtFechaFin;
    private com.toedter.calendar.JDateChooser txtFechaInicio;
    // End of variables declaration//GEN-END:variables
}
