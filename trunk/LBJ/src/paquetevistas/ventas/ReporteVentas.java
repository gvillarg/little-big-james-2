/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paquetevistas.ventas;

import Seguridad.Controlador.controladorseguridad;
import java.util.Date;
import modelos.Cliente;
import modelos.Producto;
import modelos.Usuario;

/**
 *
 * @author James
 */
public class ReporteVentas extends javax.swing.JInternalFrame implements ClientReadyWindow {

    /**
     * Creates new form ReporteProduccion
     */
    public static Cliente cliente;
    public static Producto producto;
    
    //Para el registro de Log
    public static Usuario usuario = lbj.LBJ.Login.usuario;
    public static controladorseguridad mycontroladorseguridad = new controladorseguridad();
    
    public ReporteVentas() {
        initComponents();
        this.producto = null;
    }
    public void setCliente(Cliente c) {
        this.cliente = c;
    }
    public void setProducto(Producto p) {
        this.producto = p;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jDateChooser4 = new com.toedter.calendar.JDateChooser();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jTextField4 = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setTitle("Reporte de Ventas");
        setToolTipText("");

        jButton1.setText("Generar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jDateChooser3.setMaxSelectableDate(new java.util.Date());

        jDateChooser4.setMaxSelectableDate(new java.util.Date());

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione un reporte", "Ventas Totales", "Mayores Ventas" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel4.setText("Cliente:");

        jButton5.setText("Buscar Cliente");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jTextField4.setEditable(false);
        jTextField4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField4MouseClicked(evt);
            }
        });
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField4KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(126, 126, 126)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton5)))
                .addGap(46, 46, 46)
                .addComponent(jDateChooser4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jDateChooser3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jDateChooser4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jButton5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Date fechaInicio = jDateChooser3.getDate();
        Date fechaFin = jDateChooser4.getDate();
        Cliente cliente1 = this.cliente;
        Producto producto1 = null;
        ReporteVentasNuevo reporteVentas = new ReporteVentasNuevo();
        int index = this.jComboBox1.getSelectedIndex();
        System.out.println("reporte");
        if (index == 1 && cliente1 == null && producto1 == null) {
            index = 1;
            System.out.println("reporte11");
            reporteVentas.nuevoReporte(index,
                0,
                0,
                fechaInicio,fechaFin);
            System.out.println("reporte12");
            
            mycontroladorseguridad.insertarlog(usuario, new Date(), "Generar Reporte", "Ventas", "Generar Reporte");
        } else
        if (index == 1 && cliente1 == null && producto1 != null) {
            index = 3;
            System.out.println("reporte21");
            reporteVentas.nuevoReporte(index,
                0,
                producto1.getIdproducto(),
                fechaInicio,fechaFin);
            System.out.println("reporte22");
            
            mycontroladorseguridad.insertarlog(usuario, new Date(), "Generar Reporte", "Ventas", "Generar Reporte");
        } else
        if (index == 1 && cliente != null && producto1 == null) {
            
            System.out.println("reporte31");
            index = 2;
            reporteVentas.nuevoReporte(index,
                cliente1.getIdcliente(),
                0,
                fechaInicio,fechaFin);
            System.out.println("reporte32");
            
            mycontroladorseguridad.insertarlog(usuario, new Date(), "Generar Reporte", "Ventas", "Generar Reporte");
        } else
        if (index == 2 && cliente1 == null && producto1 == null) {
            index = 4;
            System.out.println("reporte41");
            reporteVentas.nuevoReporte(index,
                0,
                0,
                fechaInicio,fechaFin);
            System.out.println("reporte42");
            
            mycontroladorseguridad.insertarlog(usuario, new Date(), "Generar Reporte", "Ventas", "Generar Reporte");
        } else
        if (index == 2 && cliente1 == null && producto1 != null) {
            index = 6;
            System.out.println("reporte51");
            reporteVentas.nuevoReporte(index,
                0,
                producto1.getIdproducto(),
                fechaInicio,fechaFin);
            System.out.println("reporte52");
            
            mycontroladorseguridad.insertarlog(usuario, new Date(), "Generar Reporte", "Ventas", "Generar Reporte");
        } else
        if (index == 2 && cliente != null && producto1 == null) {
            index = 5;
            System.out.println("reporte61");
            reporteVentas.nuevoReporte(index,
                cliente1.getIdcliente(),
                0,
                fechaInicio,fechaFin);
            System.out.println("reporte62");
            
            mycontroladorseguridad.insertarlog(usuario, new Date(), "Generar Reporte", "Ventas", "Generar Reporte");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        BuscarCliente buscarCliente = null;
        if (buscarCliente == null || buscarCliente.isClosed()) {
            buscarCliente = new BuscarCliente(this,jTextField4);
            getDesktopPane().add(buscarCliente);
            buscarCliente.setVisible(true);
        } else {
            try {
                buscarCliente.setSelected(true);
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTextField4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField4MouseClicked
        this.jTextField4.setText("");
        this.cliente = null;
    }//GEN-LAST:event_jTextField4MouseClicked

    private void jTextField4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4KeyPressed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox jComboBox1;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private com.toedter.calendar.JDateChooser jDateChooser4;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}