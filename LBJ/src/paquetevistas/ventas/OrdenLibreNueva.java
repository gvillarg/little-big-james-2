/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paquetevistas.ventas;

import controlador.ventas.ControllerClientes;
import controlador.ventas.ControllerOrdenes;
import controlador.ventas.ControllerVentas;
import controlador.ventas.Validacion;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import modelos.Cliente;
import modelos.Producto;
import static paquetevistas.ventas.Clientes.controllerClientes;

/**
 *
 * @author alulab11
 */
public class OrdenLibreNueva extends javax.swing.JInternalFrame implements ClientReadyWindow {

    public static Ventas ventas;
    public static OrdenesDeProduccion ordenes;
    public static BuscarCliente buscarCliente;
    public static Cliente cliente;
    public static BuscarProductoParaOrdenDeProduccion buscarProducto;
    public static Producto producto;
    public static ControllerVentas controllerVentas = new ControllerVentas();
    public static ControllerOrdenes controllerOrdenes = new ControllerOrdenes();
    TablaProductos tablaProductosC = new TablaProductos();
    public static Validacion validacion = new Validacion();
    public static String cotizar = null;
    
    class TablaProductos extends AbstractTableModel {

        List<ProductoC> productos = new ArrayList<ProductoC>();
        List<ProductoC> productos2 = new ArrayList<ProductoC>();
        List<ProductoC> productos3 = new ArrayList<ProductoC>();
        String[] titles = {"Codigo", "Producto", "Cantidad Requerida", "Cantidad Sugerida", "Cantidad"};

        public TablaProductos() {
            controllerVentas.getProductosAProducir(productos,productos2,productos3);
        }
        public void refresh() {
            this.fireTableChanged(null);
        }
        
        public int getRowCount() {
            return productos.size();
        }

        public int getColumnCount() {
            return 5;
        }
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            if (columnIndex == 4) {
                return true;
            }
            return false;
        }
        @Override
        public Class<?> getColumnClass(int column) {
            if (column < 2) {
                return String.class;
            }
            return Float.class;
        }
        public void setValueAt(Object value, int rowIndex, int columnIndex) {
            ProductoC productoC = productos3.get(rowIndex);
            productoC.setCantidad((float)value);
        }
        public Object getValueAt(int rowIndex, int columnIndex) {
            ProductoC productoC = productos.get(rowIndex);
            ProductoC productoC2 = productos2.get(rowIndex);
            ProductoC productoC3 = productos3.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return productoC.getIdProducto();
                case 1:
                    return productoC.getNombre();
                case 2:
                    return productoC.getCantidad();
                case 3:
                    return productoC2.getCantidad();
                case 4:
                    return productoC3.getCantidad();
            }
            return null;
        }

        public String getColumnName(int col) {
            return titles[col];
        }
        public void modify(Producto p, float cantidad, float precio) {
            ProductoC productoC = find3(p);
            if (productoC != null) {
                productoC.setCantidad(cantidad);
                productoC.setPrecioUnd(precio);
            } else {
                productoC = new ProductoC(p,0,precio);
                productos.add(productoC);
                productoC = new ProductoC(p,controllerVentas.calcularStockEstimado(p),0);
                productos2.add(productoC);
                productoC = new ProductoC(p,cantidad,0);
                productos3.add(productoC);
            }
            refresh();
        }
        public ProductoC find(Producto p) {
            int j = productos.size();
            for (int i=0; i<j; i++) {
                ProductoC productoC = productos.get(i);
                if (productoC.getIdProducto() == p.getIdproducto()) {
                    return productoC;
                }
            }
            return null;
        }
        public ProductoC find3(Producto p) {
            int j = productos3.size();
            for (int i=0; i<j; i++) {
                ProductoC productoC = productos3.get(i);
                if (productoC.getIdProducto() == p.getIdproducto()) {
                    return productoC;
                }
            }
            return null;
        }
        public float getMontoTotal() {
            float total = 0;
            int j = productos.size();
            for (int i=0; i<j; i++) {
                ProductoC productoC = productos.get(i);
                total = total + productoC.getCantidad() * productoC.getPrecioUnd();
            }
            return total;
        }
//        public void cotizar() {
//            int j = productos.size();
//            for (int i=0; i<j; i++) {
//                ProductoC productoC = productos.get(i);
//                productoC.cotizar();
//            }
//            refresh();
//        }
        public List<ProductoC> asList() {
            return productos3;
        }
        public ProductoC getInfoProducto(Producto p) {
            return find3(p);
        }
        
    }
    public OrdenLibreNueva(OrdenesDeProduccion o) {
        this.ordenes = o;
        initComponents();
        jTable1.setModel(tablaProductosC);
        tablaProductosC.refresh();
    }
    public void refresh() {
        ((TablaProductos)this.jTable1.getModel()).refresh();
        this.tablaProductosC.fireTableChanged(null);
    }
    public void setCliente(Cliente c) {
        this.cliente = c;
    }
    public void setProducto(Producto p) {
        this.producto = p;
        findCantidades(p);
    }
    public void findCantidades(Producto producto) {
        ProductoC p = ((TablaProductos)this.jTable1.getModel()).getInfoProducto(producto);
        if (p != null) {
            this.jTextField2.setText(String.valueOf(p.getCantidad()));
        } else {
            this.jTextField2.setText("");
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

        jSpinner1 = new javax.swing.JSpinner();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setTitle("Orden Libre Nueva");
        setPreferredSize(new java.awt.Dimension(500, 430));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Simple", null, null, "14/09/2013", null},
                {"Rellena", null, null, "15/09/2013", null},
                {"Diseño", null, null, "16/09/2013", null}
            },
            new String [] {
                "Galleta", "Cantidad", "Precio Und", "Fecha", "Fecha Cotizada"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class, java.lang.Float.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(50);
        }

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/save.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel7.setText("Guardar");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Agregar Productos"));

        jLabel1.setText("Producto:");

        jTextField1.setEditable(false);
        jTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField1MouseClicked(evt);
            }
        });
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton4.setText("Buscar Producto");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel2.setText("Cantidad:");

        jButton5.setText("Agregar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jButton5)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error 1.png"))); // NOI18N
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Cancelar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(165, 165, 165)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel7))
                            .addComponent(jButton2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton9))))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel11))
                .addContainerGap(109, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (this.tablaProductosC.asList().size() == 0) {
            JOptionPane.showMessageDialog(null, "Debe elegir productos antes de guardar la orden de produccion.");
        } else {
            this.controllerOrdenes.createOrdenProductos(this.tablaProductosC.asList());
            this.ordenes.refresh();
            JOptionPane.showMessageDialog(null, "La Orden de Producción se registró existosamente.");
            this.dispose();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (validacion.es_vacio(this.jTextField2.getText())) {
            JOptionPane.showMessageDialog(null, validacion.msj + "Cantidad");
        } else if (!validacion.es_numero_entero(this.jTextField2.getText())) {
            JOptionPane.showMessageDialog(null, validacion.msj);
        } else if (this.producto == null) {
            validacion.es_vacio("");
            JOptionPane.showMessageDialog(null, validacion.msj + "Producto");
        } else if (this.producto != null) {
            tablaProductosC.modify(producto,Float.parseFloat(this.jTextField2.getText()),
                    0);
            refresh();
        }
    }//GEN-LAST:event_jButton5ActionPerformed
    public String toNum(String str) {
        int pos = str.indexOf(".");
        if (pos != -1) {
            int extra = str.length() - pos;
            if (extra > 2) {
                return str.substring(0, pos+3);
            }
        }
        return str;
    }
    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (buscarProducto == null || buscarProducto.isClosed()) {
            buscarProducto = new BuscarProductoParaOrdenDeProduccion(this,jTextField1);
            getDesktopPane().add(buscarProducto);
            buscarProducto.setVisible(true);
        } else {
            try {
                buscarProducto.setSelected(true);
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MouseClicked
        this.jTextField1.setText("");
        this.jTextField2.setText("");
        this.producto = null;
    }//GEN-LAST:event_jTextField1MouseClicked

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        if(JOptionPane.showConfirmDialog(null, "¿Está seguro de cancelar el registro?",
                "Mensaje",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            this.dispose();
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
