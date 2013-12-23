/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paquetevistas.compra;

import Seguridad.Controlador.Validacion;
import controlador.almacen.controladoralmacen;
import controlador.compra.controladorcompra;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import modelos.NewHibernateUtil;
import modelos.Producto;
import modelos.Productoxproveedor;
import modelos.Proveedor;
import modelos.Requerimientocompraxproducto;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Jacklin
 */
public class AsignarOrdenPedioaCompra extends javax.swing.JInternalFrame {

    public static controladoralmacen mycontroladoralmacen = new controladoralmacen();
    public static controladorcompra mycontroladorcompra = new controladorcompra();
    public static List<Integer> listidprods = new ArrayList<Integer>();
    public static List<Integer> listidreq = new ArrayList<Integer>();
    RequerimentxProdTableModel myRequerimentxProdTableModel = new RequerimentxProdTableModel();
    private JTextField jTextField;
    public static Validacion validacion = new Validacion();

    /**
     * Creates new form AsignarOrdenPedioaCompra
     */
    public AsignarOrdenPedioaCompra(JTextField j) {
        initComponents();
        loadComponents();
        this.jTextField = j;
        jTable1.setModel(myRequerimentxProdTableModel);
    }

    void loadComponents() {
        Proveedor prov = lbj.LBJ.Login.escritorio.compra.compra.proveedorcompra;
        jproveedor.setText(prov.getRazonsocial().toString());
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        List<Requerimientocompraxproducto> reqcompxprod = new ArrayList<Requerimientocompraxproducto>();
        reqcompxprod = mycontroladorcompra.sacareqcompxprod();
        Productoxproveedor prodxprov = new Productoxproveedor();
        int idprov = prov.getIdproveedor();
        for (int i = 0; i < reqcompxprod.size(); i++) {
            int idprod = reqcompxprod.get(i).getId().getIdproducto();
            tx = session.beginTransaction();
            String sqlquery = "FROM Productoxproveedor WHERE idproducto = :idprod AND idproveedor = :idprov";
            query = session.createQuery(sqlquery);
            query.setParameter("idprod", idprod);
            query.setParameter("idprov", idprov);
            prodxprov = (Productoxproveedor) query.uniqueResult();

            if (prodxprov != null) {
                int idreq = reqcompxprod.get(i).getId().getIdreqerimientocompra();
                String nombprod = mycontroladoralmacen.sacaproductobyid(idprod).getNombre();
                listidprods.add(idprod);
                listidreq.add(idreq);
                jComboOrden.addItem("Requerimiento " + idreq + "-" + nombprod);
            }
        }
        jComboOrden.setSelectedIndex(0);
    }

    class RequerimentxProdTableModel extends AbstractTableModel {

        List<Requerimientocompraxproducto> reqscompxprod = new ArrayList<Requerimientocompraxproducto>();
        List<Float> cantidades = new ArrayList<Float>();
        Float preciototal = 0.0f;

        public RequerimentxProdTableModel() {
//            reqscompxprod = mycontroladorcompra.sacareqcompxprod();
        }
        String[] titles = {"Orden de pedido", "Producto", "Cantidad", "Precio del producto (S/.)"};

        @Override
        public int getRowCount() {
            return reqscompxprod.size();
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            String res = null;
            Requerimientocompraxproducto reqcompxprod = reqscompxprod.get(rowIndex);
            int idprod = reqcompxprod.getId().getIdproducto();
            int idreq = reqcompxprod.getRequirimientocompra().getIdreqerimientocompra();
            Producto prod = mycontroladoralmacen.sacaproductobyid(idprod);
            String razon = jproveedor.getText();
            Proveedor prov = mycontroladorcompra.sacaproveedorbyrazon(razon);
            int idprov = prov.getIdproveedor();
            Productoxproveedor prodxprov = mycontroladorcompra.sacaproductoxproveedor(idprov, idprod);

            switch (columnIndex) {
                case 0:
                    res = "" + idreq;
                    break;
                case 1:

                    res = "" + prod.getNombre();
                    break;
                case 2:
                    res = "" + this.cantidades.get(rowIndex);
                    break;
                case 3:
                    res = "" + prodxprov.getPrecio();
                    break;
            }
            return res;
        }

        public String getColumnName(int col) {
            return titles[col];
        }
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
        jproveedor = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboOrden = new javax.swing.JComboBox();
        jcantReq = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jcantMaxProv = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jcantidadComprar = new javax.swing.JTextField();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        jButton2 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setTitle("Asignar Orden de Pedido a Compra");

        jproveedor.setEnabled(false);
        jproveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jproveedorActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jproveedor);
        jproveedor.setBounds(230, 20, 190, 20);

        jLabel2.setText("Requerimiento de compra:");
        jLayeredPane1.add(jLabel2);
        jLabel2.setBounds(40, 60, 170, 20);

        jLabel3.setText("Proveedor:");
        jLayeredPane1.add(jLabel3);
        jLabel3.setBounds(40, 20, 100, 14);

        jComboOrden.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione un requerimiento" }));
        jComboOrden.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jComboOrdenMousePressed(evt);
            }
        });
        jComboOrden.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboOrdenItemStateChanged(evt);
            }
        });
        jComboOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboOrdenActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jComboOrden);
        jComboOrden.setBounds(230, 60, 190, 20);

        jcantReq.setEditable(false);
        jLayeredPane1.add(jcantReq);
        jcantReq.setBounds(230, 100, 190, 20);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Orden de pedido", "Producto", "Cantidad", "Precio del producto (S/.)"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        jLayeredPane1.add(jScrollPane2);
        jScrollPane2.setBounds(10, 220, 530, 90);

        jLabel5.setText("Cantidad a cubrir la compra:");
        jLayeredPane1.add(jLabel5);
        jLabel5.setBounds(40, 180, 180, 14);

        jLabel6.setText("Cantidad Requerida:");
        jLayeredPane1.add(jLabel6);
        jLabel6.setBounds(40, 100, 120, 14);

        jcantMaxProv.setEditable(false);
        jcantMaxProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcantMaxProvActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jcantMaxProv);
        jcantMaxProv.setBounds(230, 140, 190, 20);

        jLabel7.setText("Cantidad máx del proveedor:");
        jLayeredPane1.add(jLabel7);
        jLabel7.setBounds(40, 140, 190, 14);

        jcantidadComprar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcantidadComprarActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jcantidadComprar);
        jcantidadComprar.setBounds(230, 180, 190, 20);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/limpiar.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jLayeredPane2.add(jButton2);
        jButton2.setBounds(350, 10, 59, 40);

        jLabel11.setText("Limpiar");
        jLayeredPane2.add(jLabel11);
        jLabel11.setBounds(360, 60, 70, 14);

        jLabel9.setText("Guardar");
        jLayeredPane2.add(jLabel9);
        jLabel9.setBounds(260, 60, 60, 14);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/save.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jLayeredPane2.add(jButton1);
        jButton1.setBounds(250, 10, 65, 41);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/plus.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jLayeredPane2.add(jButton3);
        jButton3.setBounds(50, 10, 63, 39);

        jLabel10.setText("Agregar");
        jLayeredPane2.add(jLabel10);
        jLabel10.setBounds(60, 60, 70, 14);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/minus.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jLayeredPane2.add(jButton4);
        jButton4.setBounds(150, 10, 63, 40);

        jLabel8.setText("Quitar");
        jLayeredPane2.add(jLabel8);
        jLabel8.setBounds(170, 60, 40, 14);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
                    .addComponent(jLayeredPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jproveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jproveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jproveedorActionPerformed

    private void jComboOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboOrdenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboOrdenActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jcantMaxProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcantMaxProvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcantMaxProvActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        int row = jTable1.getSelectedRow();
        myRequerimentxProdTableModel.reqscompxprod.remove(row);
        myRequerimentxProdTableModel.cantidades.remove(row);
        myRequerimentxProdTableModel.fireTableChanged(null);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jComboOrdenMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboOrdenMousePressed
        // TODO add your handling code here:     
    }//GEN-LAST:event_jComboOrdenMousePressed

    private void jComboOrdenItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboOrdenItemStateChanged
        // TODO add your handling code here:
        Integer row = jComboOrden.getSelectedIndex();
        if (row.compareTo(0) != 0) {
            int idreq = listidreq.get(row - 1);
            int idprod = listidprods.get(row - 1);
            String nombre = mycontroladoralmacen.sacaproductobyid(idprod).getNombre();
            Requerimientocompraxproducto reqcompxprod = mycontroladorcompra.sacareqcompxprodbyid(idreq, idprod);
            String razon = jproveedor.getText();
            Proveedor prov = mycontroladorcompra.sacaproveedorbyrazon(razon);
            int idprov = prov.getIdproveedor();
            Productoxproveedor prodxprov = mycontroladorcompra.sacaproductoxproveedor(idprov, idprod);
            jcantReq.setText(""+reqcompxprod.getCantidad());
            jcantMaxProv.setText(prodxprov.getCantidadmaxima().toString());
        }
    }//GEN-LAST:event_jComboOrdenItemStateChanged

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        Integer row = jComboOrden.getSelectedIndex();


        if (validacion.es_vacio(jcantidadComprar.getText())) {
            JOptionPane.showMessageDialog(this, validacion.msj + "cantidad a cubrir la compra");
        } else if (!validacion.es_numero_real(jcantidadComprar.getText())) {
            JOptionPane.showMessageDialog(this, validacion.msj);
        } else {
            Integer numcantidad = Integer.parseInt(jcantidadComprar.getText());
            Integer cantReq = Integer.parseInt(jcantReq.getText());
            Integer cantMaxProv = Integer.parseInt(jcantMaxProv.getText());
            if (jcantidadComprar.getText().equalsIgnoreCase("0") || (numcantidad < 0)) {
                JOptionPane.showMessageDialog(this, "Debe colocar una cantidad mayor a 0 del producto a comprar");
            }else if((numcantidad>cantMaxProv)||(numcantidad>cantReq)){
                JOptionPane.showMessageDialog(this, "Debe colocar una cantidad menor o igual a la requerida y a la que se puede proveer");
            }else
            if ((!jcantidadComprar.getText().isEmpty() || !jcantidadComprar.getText().equalsIgnoreCase("0") || numcantidad > 0) && row.compareTo(0) != 0) {
                Float cantidad = Float.parseFloat(jcantidadComprar.getText());
                int idreq = listidreq.get(row - 1);
                int idprod = listidprods.get(row - 1);
                int encontro = 0;
                for (int i = 0; i < myRequerimentxProdTableModel.reqscompxprod.size(); i++) {
                    if ((idreq == myRequerimentxProdTableModel.reqscompxprod.get(i).getId().getIdreqerimientocompra()) && (idprod == myRequerimentxProdTableModel.reqscompxprod.get(i).getId().getIdproducto())) {
                        encontro = 1;
                        jcantReq.setText("");
                        jcantMaxProv.setText("");
                        jcantidadComprar.setText("");
                        jComboOrden.setSelectedIndex(0);
                        JOptionPane.showMessageDialog(this, "Usted ya agregó este requerimiento");
                        break;
                    }
                }

                if (encontro == 0) {
                    myRequerimentxProdTableModel.reqscompxprod.add(mycontroladorcompra.sacareqcompxprodbyid(idreq, idprod));
                    myRequerimentxProdTableModel.cantidades.add(cantidad);
                    myRequerimentxProdTableModel.fireTableChanged(null);
                    String razon = jproveedor.getText();
                    Proveedor proveedor = mycontroladorcompra.sacaproveedorbyrazon(razon);
                    int idprov = proveedor.getIdproveedor();
                    Productoxproveedor prodxprov = mycontroladorcompra.sacaproductoxproveedor(idprov, idprod);
                    float precio = prodxprov.getPrecio();
                    myRequerimentxProdTableModel.preciototal += (Float) cantidad * precio;
                    jcantReq.setText("");
                    jcantMaxProv.setText("");
                    jcantidadComprar.setText("");
                    jComboOrden.setSelectedIndex(0);
                }
            }
        }

        if (row.compareTo(0) == 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una orden de pedido");
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jcantidadComprarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcantidadComprarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcantidadComprarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        lbj.LBJ.Login.escritorio.compra.compra.reqscompxprod = myRequerimentxProdTableModel.reqscompxprod;
        lbj.LBJ.Login.escritorio.compra.compra.cantidades = myRequerimentxProdTableModel.cantidades;
        this.jTextField.setText(myRequerimentxProdTableModel.preciototal.toString());
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox jComboOrden;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jcantMaxProv;
    private javax.swing.JTextField jcantReq;
    private javax.swing.JTextField jcantidadComprar;
    private javax.swing.JTextField jproveedor;
    // End of variables declaration//GEN-END:variables
}
