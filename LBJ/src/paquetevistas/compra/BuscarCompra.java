/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paquetevistas.compra;

import Seguridad.Controlador.Validacion;
import Seguridad.Controlador.controladorseguridad;
import controlador.almacen.controladoralmacen;
import controlador.compra.controladorcompra;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import modelos.Compra;
import modelos.Detallecompra;
import modelos.Producto;
import modelos.Usuario;
import paquetevistas.Escritorio;

/**
 *
 * @author Jacklin
 */
public class BuscarCompra extends javax.swing.JInternalFrame {

    /**
     * Creates new form BuscarCompra
     */
    public static NuevaCompra2 compra2;
    public static NuevaCompraLibre compralibre;
    public static ReporteCompras reporteCompras;
    public static NuevaCompra compra;
    public static ModificarCompra compraModificar;
    public static Compra compraaeditar;
    public static ModificarCompra compraEditar;
    PurchaseTableModel myPurchaseTableModel = new PurchaseTableModel();
    public static controladorcompra mycontroladorcompra = new controladorcompra();
    public static controladoralmacen mycontroladoralmacen = new controladoralmacen();
    public static List <Detallecompra> detallexcomp;
    public static Compra compradetalle;
    public static ProductosCompra productosxComp;
    public static Validacion validacion = new Validacion();

    //Para el registro de Log
    public static Usuario usuario = lbj.LBJ.Login.usuario;
    public static controladorseguridad mycontroladorseguridad = new controladorseguridad();
    
    private void loadComponents() {
//        jComboBox2.removeAllItems();
        List<Producto> productos=  new ArrayList<Producto>();
        productos=mycontroladoralmacen.sacaproductos();
        for (int i=0; i < productos.size(); i++){
            jComboProducto.addItem(productos.get(i).getNombre());
        }
        jComboProducto.setSelectedIndex(0);
    }
    
    class PurchaseTableModel extends AbstractTableModel {
        List<Compra> comp = new ArrayList<Compra>();
        
        public PurchaseTableModel() {
            comp = mycontroladorcompra.sacacompras();
        }
        String[] titles = {"Código", "Proveedor", "Precio Acordado", "Productos","Estado"};
        public int getRowCount() {
            return comp.size();
        }

       
        public int getColumnCount() {
            return 5;
        }

        
        public Object getValueAt(int rowIndex, int columnIndex) {
            String res= null;
            Compra p=comp.get(rowIndex);
            String productos = null;
            int idcomp,idprov;
            String razonSocial=null;
            switch(columnIndex){
                case 0:
                    res=""+p.getIdcompra();
                    break;
                case 1:
                    idprov=p.getProveedor().getIdproveedor();
                    razonSocial = mycontroladorcompra.sacaRazonsocial(idprov);
                    res = razonSocial;
                    break;
                case 2:
                    res=""+p.getPrecioacordado();
                    break;
                case 3:
                    idcomp=p.getIdcompra();
                    productos=mycontroladorcompra.getProductos(idcomp);
                    res=productos;
                    break;
                case 4:
                    res=""+p.getEstado();
                    break;
            }
            return res;
            
        }
        
        public String getColumnName(int col) {
            return titles[col];
        }
        
    }
        
    public BuscarCompra() {
        initComponents();
        
        jDate1.addPropertyChangeListener( new PropertyChangeListener(){
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            // the docs of JDateChooser says that when the date is modified, a "date" property change is fired
            if ( evt.getPropertyName().equals( "date" ) ) {
                jDate2.setMinSelectableDate(jDate1.getDate() );
            }
        }
        });
        
        jDate2.addPropertyChangeListener( new PropertyChangeListener(){
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            // the docs of JDateChooser says that when the date is modified, a "date" property change is fired
            if ( evt.getPropertyName().equals( "date" ) ) {
                jDate1.setMaxSelectableDate(jDate2.getDate() );
            }
        }
        });
        
        loadComponents();
        jTableCompra.setModel(myPurchaseTableModel);
        refreshPurchaseTable();
        
        mycontroladorseguridad.insertarlog(usuario, new Date(), "Consultar", "Compra", "Consultar Compra");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLayeredPane3 = new javax.swing.JLayeredPane();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jComboEstado = new javax.swing.JComboBox();
        jruc = new javax.swing.JTextField();
        jComboProducto = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jDate1 = new com.toedter.calendar.JDateChooser();
        jDate2 = new com.toedter.calendar.JDateChooser();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableCompra = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        setClosable(true);
        setIconifiable(true);
        setTitle("Mantenimiento de Compras");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/file plus.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jLayeredPane2.add(jButton1);
        jButton1.setBounds(50, 10, 63, 39);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/search.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jLayeredPane2.add(jButton2);
        jButton2.setBounds(280, 10, 70, 40);

        jLabel1.setText("Atender Orden de compra");
        jLayeredPane2.add(jLabel1);
        jLabel1.setBounds(10, 50, 150, 14);

        jLabel2.setText("Buscar");
        jLayeredPane2.add(jLabel2);
        jLabel2.setBounds(290, 50, 60, 14);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/file plus.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jLayeredPane2.add(jButton5);
        jButton5.setBounds(173, 10, 70, 40);

        jLabel8.setText("Compra libre");
        jLayeredPane2.add(jLabel8);
        jLabel8.setBounds(170, 50, 90, 14);

        jLayeredPane3.setBorder(javax.swing.BorderFactory.createTitledBorder("Criterios de Búsqueda"));

        jLabel4.setText("Ruc del Proveedor:");
        jLayeredPane3.add(jLabel4);
        jLabel4.setBounds(60, 60, 150, 14);

        jLabel5.setText("Producto:");
        jLayeredPane3.add(jLabel5);
        jLabel5.setBounds(60, 90, 130, 20);

        jLabel6.setText("Estado:");
        jLayeredPane3.add(jLabel6);
        jLabel6.setBounds(60, 30, 140, 14);

        jLabel9.setText("Fecha Pactada Entrega de:");
        jLayeredPane3.add(jLabel9);
        jLabel9.setBounds(60, 110, 170, 20);

        jComboEstado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Eliga un estado", "En Proceso", "Anulada", "Procesada" }));
        jComboEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboEstadoActionPerformed(evt);
            }
        });
        jLayeredPane3.add(jComboEstado);
        jComboEstado.setBounds(260, 20, 150, 20);

        jruc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrucActionPerformed(evt);
            }
        });
        jLayeredPane3.add(jruc);
        jruc.setBounds(260, 50, 150, 20);

        jComboProducto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Eliga un producto" }));
        jComboProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboProductoActionPerformed(evt);
            }
        });
        jLayeredPane3.add(jComboProducto);
        jComboProducto.setBounds(260, 80, 150, 20);

        jLabel10.setText("Fecha Pactada Entrega a:");
        jLayeredPane3.add(jLabel10);
        jLabel10.setBounds(60, 140, 170, 20);
        jLayeredPane3.add(jDate1);
        jDate1.setBounds(260, 110, 150, 20);
        jLayeredPane3.add(jDate2);
        jDate2.setBounds(260, 140, 150, 20);

        jTableCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Código", "Proveedor", "Precio Acordado (S/.)", "Productos", "Estado"
            }
        ));
        jTableCompra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableCompraMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTableCompra);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/search.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel7.setText(" Ver Detalle");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
            .addComponent(jLayeredPane2)
            .addGroup(layout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(117, 117, 117))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(27, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addGap(64, 64, 64))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
            if(compra2 == null || compra2.isClosed()){
            compra2 = new NuevaCompra2();
            Escritorio.compra.getDesktopPane().add(compra2);
            compra2.setVisible(true);
        }else
            try{
                compra2.setSelected(true);
            }catch(Exception e){}   
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Long ruc;
        String estado=jComboEstado.getSelectedItem().toString();
        if(estado.equalsIgnoreCase("Eliga un estado")){
            estado="";
        }
        if((jruc.getText().isEmpty())){
            ruc=0L;
        }else if(!validacion.es_numero_entero(jruc.getText())){
                ruc=-1L;
            }else{
          ruc= Long.parseLong(jruc.getText());  
          Long limitinf=9999999999L;
          Long limitsup=100000000000L;
          if((ruc.compareTo(limitinf)<0)||(ruc.compareTo(limitsup)>0)){
              ruc=-1L;
          }
        }
        String producto=jComboProducto.getSelectedItem().toString();
        if(producto.equalsIgnoreCase("Eliga un producto")){
            producto="";
        }
        Date fechaent1, fechaent2;
        fechaent1=jDate1.getDate();
        fechaent2=jDate2.getDate();
        
        myPurchaseTableModel.comp = mycontroladorcompra.buscacompras(estado,ruc,producto,fechaent1,fechaent2);
        myPurchaseTableModel.fireTableChanged(null);
        
        mycontroladorseguridad.insertarlog(usuario, new Date(), "Consultar", "Compra", "Consultar Compra");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboEstadoActionPerformed

    private void jrucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrucActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jrucActionPerformed

    private void jTableCompraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableCompraMouseClicked
        // TODO add your handling code here:                          
//         if (compraModificar == null || compraModificar.isClosed()) {
//            compraModificar = new ModificarCompra();
//            getDesktopPane().add(compraModificar);
//            compraModificar.setVisible(true);
//        } else 
//            try {
//                compraModificar.setSelected(true);
//            } catch (Exception e) {} 
        
        if (evt.getClickCount() == 2) {
            int row = jTableCompra.getSelectedRow();
            String ids = jTableCompra.getValueAt(row, 0).toString();
            int id = Integer.parseInt(ids);
            compraaeditar = mycontroladorcompra.sacacomprabyid(id);
            if(compraaeditar.getEstado().equalsIgnoreCase("En Proceso")){
                compraEditar = new ModificarCompra();
            Escritorio.compra.getDesktopPane().add(compraEditar);
            compraEditar.setVisible(true);
            }else{
               JOptionPane.showMessageDialog(null, "Solo se pueden modificar compras 'En proceso'"); 
            }
            
        }
    }//GEN-LAST:event_jTableCompraMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        if(jTableCompra.getSelectedRow()==-1){
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila de la tabla");
            
        }else{
           int row = jTableCompra.getSelectedRow();
        String ids = jTableCompra.getValueAt(row, 0).toString();
        Integer id = Integer.parseInt(ids);
        detallexcomp = mycontroladorcompra.sacadetallesxcompbycompid(id);//investigar
        compradetalle = mycontroladorcompra.sacacomprabyid(id);
        productosxComp = new ProductosCompra();
        Escritorio.compra.getDesktopPane().add(productosxComp);
        productosxComp.setVisible(true); 
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
         if(compralibre == null || compralibre.isClosed()){
            compralibre = new NuevaCompraLibre();
            Escritorio.compra.getDesktopPane().add(compralibre);
            compralibre.setVisible(true);
        }else
            try{
                compralibre.setSelected(true);
            }catch(Exception e){}   
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jComboProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboProductoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox jComboEstado;
    private javax.swing.JComboBox jComboProducto;
    private com.toedter.calendar.JDateChooser jDate1;
    private com.toedter.calendar.JDateChooser jDate2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTableCompra;
    private javax.swing.JTextField jruc;
    // End of variables declaration//GEN-END:variables

    void refreshPurchaseTable() {
        myPurchaseTableModel.comp = mycontroladorcompra.sacacompras();
        myPurchaseTableModel.fireTableChanged(null);
    }
}
