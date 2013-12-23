/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paquetevistas.compra;

import Seguridad.Controlador.Validacion;
import controlador.almacen.controladoralmacen;
import controlador.compra.controladorcompra;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import modelos.Producto;
import modelos.Productoxproveedor;
import modelos.Proveedor;
import paquetevistas.Escritorio;
import static paquetevistas.compra.BuscarProveedor.productoxproveedor;

/**
 *
 * @author Jacklin
 */
public class ModificarProductoaProveedor extends javax.swing.JInternalFrame {

    public static Productoxproveedor productosxprovaeditar;
    public static controladorcompra mycontroladorcompra = new controladorcompra();
    public static BuscarProveedor buscarproveedor = new BuscarProveedor();
    public static controladoralmacen mycontroladoralmacen = new controladoralmacen();
    public static Validacion validacion = new Validacion();

    /**
     * Creates new form ModificarProductoaProveedor
     */
    public ModificarProductoaProveedor() {
        initComponents();
        loadComponents();//falta
    }

    void loadComponents() {
        buscarproveedor.productoxproveedor.refreshProductsxSupplierTable();
        Productoxproveedor prodxprov = buscarproveedor.productoxproveedor.productosxprovamodificar;

        Integer idprov = prodxprov.getProveedor().getIdproveedor();
        Integer idprod = prodxprov.getProducto().getIdproducto();

        Producto prod = mycontroladoralmacen.sacaproductobyid(idprod);
        String razon = mycontroladorcompra.sacaRazonsocial(idprov);
        jprov.setText(razon);
        jprod.setText(prod.getNombre());
        jcantidad.setText(prodxprov.getCantidadmaxima().toString());
        jprecio.setText(prodxprov.getPrecio().toString());
        jfechaVigencia.setDate(prodxprov.getFecha());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jprecio = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jcantidad = new javax.swing.JTextField();
        jprov = new javax.swing.JTextField();
        jprod = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jfechaVigencia = new com.toedter.calendar.JDateChooser();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        jButton1 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setTitle("Editar Productos del Proveedor");

        jLabel1.setText("Proveedor:");
        jLayeredPane1.add(jLabel1);
        jLabel1.setBounds(30, 20, 90, 14);

        jLabel2.setText("Producto:");
        jLayeredPane1.add(jLabel2);
        jLabel2.setBounds(30, 60, 80, 14);

        jLabel3.setText("Precio (S/.):");
        jLayeredPane1.add(jLabel3);
        jLabel3.setBounds(30, 140, 80, 10);

        jprecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jprecioActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jprecio);
        jprecio.setBounds(140, 130, 210, 20);

        jLabel4.setText("Cantidad Máx:");
        jLayeredPane1.add(jLabel4);
        jLabel4.setBounds(30, 104, 100, 10);

        jcantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcantidadActionPerformed(evt);
            }
        });
        jcantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jcantidadKeyTyped(evt);
            }
        });
        jLayeredPane1.add(jcantidad);
        jcantidad.setBounds(140, 100, 210, 20);

        jprov.setEnabled(false);
        jprov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jprovActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jprov);
        jprov.setBounds(140, 20, 210, 20);

        jprod.setEnabled(false);
        jLayeredPane1.add(jprod);
        jprod.setBounds(140, 60, 210, 20);

        jLabel5.setText("Fecha de vigencia: ");
        jLayeredPane1.add(jLabel5);
        jLabel5.setBounds(30, 170, 120, 14);
        jLayeredPane1.add(jfechaVigencia);
        jfechaVigencia.setBounds(140, 170, 210, 20);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/save.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jLayeredPane2.add(jButton1);
        jButton1.setBounds(60, 20, 65, 41);

        jLabel9.setText("Guardar");
        jLayeredPane2.add(jLabel9);
        jLabel9.setBounds(70, 70, 70, 14);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/limpiar.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jLayeredPane2.add(jButton2);
        jButton2.setBounds(160, 20, 59, 40);

        jLabel11.setText("Limpiar");
        jLayeredPane2.add(jLabel11);
        jLabel11.setBounds(170, 70, 70, 14);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error 1.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jLayeredPane2.add(jButton5);
        jButton5.setBounds(250, 20, 60, 40);

        jLabel12.setText("Cancelar");
        jLayeredPane2.add(jLabel12);
        jLabel12.setBounds(260, 70, 60, 14);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLayeredPane2)
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE))
                .addGap(56, 56, 56))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jprecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jprecioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jprecioActionPerformed

    private void jcantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcantidadActionPerformed

    private void jprovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jprovActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jprovActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        jcantidad.setText("");
        jprecio.setText("");
        jfechaVigencia.setDate(null);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (validacion.es_vacio(jcantidad.getText())) {
            JOptionPane.showMessageDialog(null, validacion.msj + "Cantidad máxima");
        } else if (!validacion.es_numero_real(jcantidad.getText())) {
            JOptionPane.showMessageDialog(null, validacion.msj);
        } else if (Float.parseFloat(jcantidad.getText()) <= 0) {
            JOptionPane.showMessageDialog(null, "Debe colocar un número mayor a 0");
        } else if (validacion.es_vacio(jprecio.getText())) {
            JOptionPane.showMessageDialog(null, validacion.msj + "Precio");
        } else if (!validacion.es_numero_real(jprecio.getText())) {
            JOptionPane.showMessageDialog(null, validacion.msj);
        } else if (Float.parseFloat(jprecio.getText()) <= 0) {
            JOptionPane.showMessageDialog(null, "Debe colocar un número mayor a 0");
        } else if (jfechaVigencia.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fecha de vigencia");
        } else {
            if (JOptionPane.showConfirmDialog(rootPane, "¿Desea realmente editar este proveedor?",
                    "Mensaje", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                Productoxproveedor prodxprov = buscarproveedor.productoxproveedor.productosxprovamodificar;
                Integer cantidad = Integer.parseInt(jcantidad.getText());
                Float precio = Float.parseFloat(jprecio.getText());
                Date fecha = jfechaVigencia.getDate();
                mycontroladorcompra.modificarproductoxproveedor(prodxprov, cantidad, precio, fecha);
                int idprov = prodxprov.getProveedor().getIdproveedor();
                List<Productoxproveedor> prodsxprov = mycontroladorcompra.sacaprodxprovbyid(idprov);
                buscarproveedor.productoxproveedor.refreshProductsxSupplierTable(prodsxprov);
                //this.setVisible(false);
                JOptionPane.showMessageDialog(this, "El producto del proveedor se editó correctamente");
                this.dispose();
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jcantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jcantidadKeyTyped
        // TODO add your handling code here:
        char car = evt.getKeyChar();
        if((car<'0' || car>'9') && (car!=(char) KeyEvent.VK_BACK_SPACE)) {
            evt.consume();
            JOptionPane.showMessageDialog(this, "Debe insertar solo números enteros");
       
        }
    }//GEN-LAST:event_jcantidadKeyTyped

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        if(JOptionPane.showConfirmDialog(null, "¿Está seguro de cancelar la edición?",
            "Mensaje",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
        lbj.LBJ.Login.escritorio.proveedor.refreshSupplierTable();
        this.dispose();
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JTextField jcantidad;
    private com.toedter.calendar.JDateChooser jfechaVigencia;
    private javax.swing.JTextField jprecio;
    private javax.swing.JTextField jprod;
    private javax.swing.JTextField jprov;
    // End of variables declaration//GEN-END:variables
}