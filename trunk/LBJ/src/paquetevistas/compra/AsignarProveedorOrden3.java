/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paquetevistas.compra;

import Seguridad.Controlador.Validacion;
import controlador.compra.controladorcompra;
import controlador.ventas.ControllerVentas;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import modelos.Producto;
import modelos.Proveedor;
import modelos.Requerimientocompraxproducto;

/**
 *
 * @author Jacklin
 */
public class AsignarProveedorOrden3 extends javax.swing.JInternalFrame {

    /**
     * Creates new form AsignarProveedorOrden2
     */
    public static ControllerVentas controladorVentas = new ControllerVentas();
    public static controladorcompra controladorCompra = new controladorcompra();
    public static Requerimientocompraxproducto requerimiento;
    public static List<Integer> idsProveedores = new ArrayList<Integer>();
    public static List<Float> cantidadesProveedores = new ArrayList<Float>();
    public static List<Float> preciosProveedores = new ArrayList<Float>();
    public static ReNuevaCompra3 ventana;
    public static float cubierta;
    TablaProveedores tablaProveedores;
    public static Validacion validacion = new Validacion();

    class TablaProveedores extends AbstractTableModel {

        List<Proveedor> proveedores = new ArrayList<Proveedor>();
        List<Float> cantidades = new ArrayList<Float>();
        List<Float> precios = new ArrayList<Float>();
        List<Float> cantidadesMaximas = new ArrayList<Float>();
        String[] titles = {"Proveedor", "Cantidad a Cubrir", "Precio (S/.)"};
        String[] estados = {"Registrada", "Procesandose", "Lista", "Terminada", "Cancelada"};

        public TablaProveedores() {

        }

        public void refresh() {
            this.fireTableDataChanged();
        }

        public void agregar(Proveedor proveedor, float cantidad, float precio, float cantidadMaxima) {
            this.proveedores.add(proveedor);
            this.cantidades.add(cantidad);
            this.precios.add(precio);
            this.cantidadesMaximas.add(cantidadMaxima);
            refresh();
        }

        @Override
        public int getRowCount() {
            return proveedores.size();
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Proveedor proveedor = proveedores.get(rowIndex);
            float cantidad = cantidades.get(rowIndex);
            float precio = precios.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return proveedor.getRazonsocial();
                case 1:
                    return cantidad;
                case 2:
                    return precio;
            }
            return null;
        }

        public String getColumnName(int col) {
            return titles[col];
        }

        private void restore(int row, List<Integer> idsProveedores, List<Float> cantidadesProveedores,
                List<Float> preciosProveedores, JComboBox jComboProveedores) {
            idsProveedores.add(this.proveedores.get(row).getIdproveedor());
            cantidadesProveedores.add(this.cantidadesMaximas.get(row));
            preciosProveedores.add(this.precios.get(row));
            jComboProveedores.addItem(this.proveedores.get(row).getRazonsocial());

            this.proveedores.remove(row);
            this.cantidades.remove(row);
            this.cantidadesMaximas.remove(row);
            this.precios.remove(row);
        }

        private float getCantidadCubierta() {
            float cantidad = 0;
            int j = this.cantidades.size();
            for (int i = 0; i < j; i++) {
                cantidad = cantidad + cantidades.get(i);
            }
            return cantidad;
        }

        private void retrieveData(List<Proveedor> proveedores, List<Float> cantidades, List<Float> precios) {
            int j = this.proveedores.size();
            for (int i = 0; i < j; i++) {
                proveedores.add(this.proveedores.get(i));
                cantidades.add(this.cantidades.get(i));
                precios.add(this.precios.get(i));
            }
        }

    }

    public AsignarProveedorOrden3(ReNuevaCompra3 ventana, Requerimientocompraxproducto requerimiento, float cubierta,
            LineaProveedoresPorProducto linea, float requerido) {
        this.ventana = ventana;
        this.requerimiento = requerimiento;
        initComponents();
        loadComponents(requerido);
        fillTabla(linea);
    }

    public void loadComponents(float requerido) {
        this.jNumeroOrden.setText(String.valueOf(this.requerimiento.getRequirimientocompra().getIdreqerimientocompra()));
        Producto producto = controladorVentas.getProducto(this.requerimiento.getProducto().getIdproducto());
        this.jNombreProducto.setText(producto.getNombre());
//        this.jCantidadReq.setText(String.valueOf(this.requerimiento.getCantidad()));
        this.jCantidadReq.setText("" + requerido);
        this.jCantidadCubierta.setText(String.valueOf(this.cubierta));
        loadProveedores();
        this.tablaProveedores = new TablaProveedores();
        this.jTablaProveedores.setModel(tablaProveedores);
    }

    public void loadProveedores() {
        List<Proveedor> proveedores = new ArrayList<Proveedor>();
        List<Integer> cantidades = new ArrayList<Integer>();
        List<Float> precios = new ArrayList<Float>();
        controladorCompra.sacaprovxprodbyid(this.requerimiento.getProducto().getIdproducto(),
                proveedores, cantidades, precios);
        int j = proveedores.size();
        for (int i = 0; i < j; i++) {
            Proveedor proveedor = proveedores.get(i);
            jComboProveedores.addItem(proveedor.getRazonsocial());
            idsProveedores.add(proveedor.getIdproveedor());
            cantidadesProveedores.add(cantidades.get(i).floatValue());
            preciosProveedores.add(precios.get(i).floatValue());
        }
    }

    public void fillTabla(LineaProveedoresPorProducto linea) {
        int j = linea.size();
        for (int i = 0; i < j; i++) {
            Proveedor p = linea.getProveedor(i);
            float cantidad = linea.getCantidad(i);
            float precio = linea.getPrecio(i);
            tablaProveedores.agregar(p, cantidad, precio, cantidad);
            int k = getSelectedIndex(p.getIdproveedor());
            idsProveedores.remove(k);
            cantidadesProveedores.remove(k);
            preciosProveedores.remove(k);
            jComboProveedores.removeItemAt(k + 1);
            jCantidadCubierta.setText(String.valueOf(tablaProveedores.getCantidadCubierta()));
        }
    }

    public int getSelectedIndex(int idProveedor) {
        int j = idsProveedores.size();
        for (int i = 0; i < j; i++) {
            if (idsProveedores.get(i) == idProveedor) {
                return i;
            }
        }
        return -1;
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
        jNumeroOrden = new javax.swing.JTextField();
        jNombreProducto = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jComboProveedores = new javax.swing.JComboBox();
        jCantidadReq = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jCantidadCubierta = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jCantidadMaximaProveedor = new javax.swing.JTextField();
        jCantidadACubrir = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablaProveedores = new javax.swing.JTable();
        jLayeredPane3 = new javax.swing.JLayeredPane();
        jButton2 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setTitle("Asignar Proveedores a Orden de Compra por Producto");

        jLabel1.setText("Producto: ");
        jLayeredPane1.add(jLabel1);
        jLabel1.setBounds(80, 60, 150, 20);

        jLabel2.setText("N° de orden de pedido:");
        jLayeredPane1.add(jLabel2);
        jLabel2.setBounds(80, 20, 150, 14);

        jNumeroOrden.setEditable(false);
        jNumeroOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jNumeroOrdenActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jNumeroOrden);
        jNumeroOrden.setBounds(280, 20, 180, 20);

        jNombreProducto.setEditable(false);
        jLayeredPane1.add(jNombreProducto);
        jNombreProducto.setBounds(280, 60, 180, 20);

        jLabel3.setText("Proveedores:");
        jLayeredPane1.add(jLabel3);
        jLabel3.setBounds(80, 180, 120, 14);

        jComboProveedores.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione un proveedor" }));
        jComboProveedores.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboProveedoresItemStateChanged(evt);
            }
        });
        jLayeredPane1.add(jComboProveedores);
        jComboProveedores.setBounds(280, 180, 180, 20);

        jCantidadReq.setEditable(false);
        jLayeredPane1.add(jCantidadReq);
        jCantidadReq.setBounds(280, 100, 180, 20);

        jLabel4.setText("Cantidad requerida:");
        jLayeredPane1.add(jLabel4);
        jLabel4.setBounds(80, 100, 170, 14);

        jLabel5.setText("Cantidad cubierta:");
        jLayeredPane1.add(jLabel5);
        jLabel5.setBounds(80, 140, 100, 14);

        jCantidadCubierta.setEditable(false);
        jCantidadCubierta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCantidadCubiertaActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jCantidadCubierta);
        jCantidadCubierta.setBounds(280, 140, 180, 20);

        jLabel6.setText("Cantidad a cubrir :");
        jLayeredPane1.add(jLabel6);
        jLabel6.setBounds(80, 260, 150, 14);

        jCantidadMaximaProveedor.setEditable(false);
        jLayeredPane1.add(jCantidadMaximaProveedor);
        jCantidadMaximaProveedor.setBounds(280, 220, 180, 20);
        jLayeredPane1.add(jCantidadACubrir);
        jCantidadACubrir.setBounds(280, 260, 180, 20);

        jLabel7.setText("Cantidad máx del proveedor:");
        jLayeredPane1.add(jLabel7);
        jLabel7.setBounds(80, 220, 170, 14);

        jLayeredPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("Proveedores Seleccionados"));

        jTablaProveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Proveedor", "Cantidad a cubrir", "Precio (S/.)"
            }
        ));
        jScrollPane1.setViewportView(jTablaProveedores);
        if (jTablaProveedores.getColumnModel().getColumnCount() > 0) {
            jTablaProveedores.getColumnModel().getColumn(2).setResizable(false);
        }

        jLayeredPane2.add(jScrollPane1);
        jScrollPane1.setBounds(30, 30, 452, 140);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/limpiar.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jLayeredPane3.add(jButton2);
        jButton2.setBounds(340, 10, 59, 40);

        jLabel11.setText("Limpiar");
        jLayeredPane3.add(jLabel11);
        jLabel11.setBounds(350, 60, 70, 14);

        jLabel9.setText("Guardar");
        jLayeredPane3.add(jLabel9);
        jLabel9.setBounds(250, 60, 60, 14);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/save.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jLayeredPane3.add(jButton1);
        jButton1.setBounds(240, 10, 65, 41);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/plus.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jLayeredPane3.add(jButton3);
        jButton3.setBounds(40, 10, 63, 39);

        jLabel10.setText("Agregar");
        jLayeredPane3.add(jLabel10);
        jLabel10.setBounds(50, 60, 70, 14);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/minus.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jLayeredPane3.add(jButton4);
        jButton4.setBounds(140, 10, 63, 40);

        jLabel8.setText("Quitar");
        jLayeredPane3.add(jLabel8);
        jLabel8.setBounds(160, 60, 40, 14);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error 1.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jLayeredPane3.add(jButton5);
        jButton5.setBounds(430, 10, 60, 40);

        jLabel12.setText("Cancelar");
        jLayeredPane3.add(jLabel12);
        jLabel12.setBounds(440, 60, 60, 14);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jLayeredPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
            .addComponent(jLayeredPane3)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jNumeroOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jNumeroOrdenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jNumeroOrdenActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        jComboProveedores.setSelectedIndex(0);
        jCantidadMaximaProveedor.setText("");
        jCantidadACubrir.setText("");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (tablaProveedores.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(null, "No ha seleccionado ningún proveedor a cubrir el pedido");
        } else {
            List<Proveedor> proveedores = new ArrayList<Proveedor>();
            List<Float> cantidades = new ArrayList<Float>();
            List<Float> precios = new ArrayList<Float>();
            tablaProveedores.retrieveData(proveedores, cantidades, precios);
            this.ventana.pasarDatos(proveedores, cantidades, precios);
            this.dispose();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        int row = jComboProveedores.getSelectedIndex();
        if (row > 0) {
            if (validacion.es_vacio(jCantidadACubrir.getText())) {
                JOptionPane.showMessageDialog(this, validacion.msj + "cantidad a cubrir la compra");
            } else if (!validacion.es_numero_real(jCantidadACubrir.getText())) {
                JOptionPane.showMessageDialog(this, validacion.msj);
            } else {
                Float numcantidad = Float.parseFloat(jCantidadACubrir.getText());
                Float cantReq = Float.parseFloat(jCantidadReq.getText());
                Float cantCubierta = Float.parseFloat(jCantidadCubierta.getText());
                Float cantMaxProv = Float.parseFloat(jCantidadMaximaProveedor.getText());
                if (jCantidadACubrir.getText().equalsIgnoreCase("0") || (numcantidad < 0)) {
                    JOptionPane.showMessageDialog(this, "Debe colocar una cantidad mayor a 0 del producto a comprar");
                } else if (cantCubierta + numcantidad > cantReq) {
                    float falta = cantReq - cantCubierta;
                    JOptionPane.showMessageDialog(this, "Está excediendo la cantidad requerida, solo falta: " + falta);
                } else if ((numcantidad > cantMaxProv) || (numcantidad > cantReq)) {
                    JOptionPane.showMessageDialog(this, "Debe colocar una cantidad menor o igual a la requerida y a la que se puede proveer");
                } else {
                    row = row - 1;
                    float cantidad = Float.parseFloat(jCantidadACubrir.getText());
                    Proveedor proveedor = controladorCompra.sacaproveedorbyid(idsProveedores.get(row));
                    tablaProveedores.agregar(proveedor, cantidad, preciosProveedores.get(row),
                            cantidadesProveedores.get(row));
                    jCantidadACubrir.setText("");
                    idsProveedores.remove(row);
                    cantidadesProveedores.remove(row);
                    preciosProveedores.remove(row);
                    jComboProveedores.removeItemAt(row + 1);
                    jCantidadCubierta.setText(String.valueOf(tablaProveedores.getCantidadCubierta()));
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un proveedor");
        }
    }//GEN-LAST:event_jButton3ActionPerformed
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int row = jTablaProveedores.getSelectedRow();
        if (row >= 0) {
            this.tablaProveedores.restore(row, idsProveedores, cantidadesProveedores, preciosProveedores, jComboProveedores);
            this.tablaProveedores.refresh();
            jCantidadCubierta.setText(String.valueOf(tablaProveedores.getCantidadCubierta()));
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila de la tabla");
        }

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jCantidadCubiertaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCantidadCubiertaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCantidadCubiertaActionPerformed

    private void jComboProveedoresItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboProveedoresItemStateChanged
        float req = Float.parseFloat(jCantidadReq.getText().toString());
        int row = jComboProveedores.getSelectedIndex();
        if (row == 0) {
            this.jCantidadMaximaProveedor.setText("");
            jCantidadACubrir.setText("");
        } else {
            row = row - 1;
            this.jCantidadMaximaProveedor.setText(String.valueOf(cantidadesProveedores.get(row)));
            float cantmax = Float.parseFloat(jCantidadMaximaProveedor.getText().toString());
            if (req < cantmax) {
                jCantidadACubrir.setText("" + req);
            } else {
                jCantidadACubrir.setText("" + cantmax);
            }
        }
    }//GEN-LAST:event_jComboProveedoresItemStateChanged

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        if (JOptionPane.showConfirmDialog(null, "¿Está seguro de cancelar la transacción?",
                "Mensaje", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            lbj.LBJ.Login.escritorio.compra.refreshPurchaseTable();
            this.dispose();
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JTextField jCantidadACubrir;
    private javax.swing.JTextField jCantidadCubierta;
    private javax.swing.JTextField jCantidadMaximaProveedor;
    private javax.swing.JTextField jCantidadReq;
    private javax.swing.JComboBox jComboProveedores;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JTextField jNombreProducto;
    private javax.swing.JTextField jNumeroOrden;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTablaProveedores;
    // End of variables declaration//GEN-END:variables
}
