/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paquetevistas.compra;

import Seguridad.Controlador.controladorseguridad;
import controlador.compra.controladorcompra;
import controlador.ventas.ControllerVentas;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import modelos.Proveedor;
import modelos.Requerimientocompraxproducto;
import modelos.Requirimientocompra;
import modelos.Usuario;
import paquetevistas.almacen.OrdenRequerimiento1;

/**
 *
 * @author Jacklin
 */
public class NuevaCompra2 extends javax.swing.JInternalFrame {

    /**
     * Creates new form NuevaCompra2
     */
    public static ControllerVentas controladorVentas = new ControllerVentas();
    public static controladorcompra controladorCompra = new controladorcompra();
    public List<Integer> idsRequerimientos;
    TablaDetallesRequerimiento tablaDetallesRequerimiento;
    public static AsignarProveedorOrden2 asignarProveedorOrden2;
    public static List<LineaProveedoresPorProducto> lineas;
    public int rowSelected;
    
    //Para el registro de Log
    public static Usuario usuario = lbj.LBJ.Login.usuario;
    public static controladorseguridad mycontroladorseguridad = new controladorseguridad();

    class TablaDetallesRequerimiento extends AbstractTableModel {

        List<Requerimientocompraxproducto> detalles = new ArrayList<Requerimientocompraxproducto>();
        List<Float> cantidadesCubiertas;
        int requerimiento;
        String[] titles = {"N° de pedido por producto", "Producto", "Cantidad requerida", "CantidadCubierta"};
        String[] estados = {"Registrada", "Procesandose", "Lista", "Terminada", "Cancelada"};

        public TablaDetallesRequerimiento(int requerimiento) {
            this.requerimiento = requerimiento;
            detalles = controladorCompra.sacareqcompxprodbyid(requerimiento);
            cantidadesCubiertas = new ArrayList<Float>();
            int j = detalles.size();
            for (int i = 0; i < j; i++) {
                cantidadesCubiertas.add(new Float(0));
            }
        }

        public void refresh() {
            detalles = controladorCompra.sacareqcompxprodbyid(requerimiento);
            this.fireTableChanged(null);
        }

        @Override
        public int getRowCount() {
            return detalles.size();
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Requerimientocompraxproducto detalle = detalles.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return "Req " + detalle.getId().getIdreqerimientocompra() + " - Prod "
                            + detalle.getId().getIdproducto();
                case 1:
                    int idProd = detalle.getId().getIdproducto();
                    return controladorVentas.getProducto(idProd).getNombre();
                case 2:
                    return detalle.getCantidad();
                case 3:
                    return cantidadesCubiertas.get(rowIndex);
            }
            return null;
        }

        public String getColumnName(int col) {
            return titles[col];
        }

        public Requerimientocompraxproducto getDetalle(int row) {
            return detalles.get(row);
        }

        public float getCantidadCubierta(int row) {
            return cantidadesCubiertas.get(row);
        }

        public void borraDatos() {
            this.detalles.clear();
            this.fireTableDataChanged();
        }

        public void createLineas(List<LineaProveedoresPorProducto> lineas) {
            int j = detalles.size();
            for (int i = 0; i < j; i++) {
                lineas.add(new LineaProveedoresPorProducto());
            }
        }

        private boolean isFull() {
            int j = detalles.size();
            for (int i = 0; i < j; i++) {
                if (detalles.get(i).getCantidad() > cantidadesCubiertas.get(i)) {
                    return false;
                }
            }
            return true;
        }

        private void fillCantidadesCubiertas(int row, float cantidad) {
            cantidadesCubiertas.set(row, new Float(cantidad));
            refresh();
        }

        public List<Requerimientocompraxproducto> getDetalles() {
            return this.detalles;
        }

    }

    public NuevaCompra2() {
        initComponents();
        Date hoy = new Date();
        jFechaAcordada.setMinSelectableDate(hoy);
        loadComponents();
    }

    public void pasarDatos(List<Proveedor> proveedores, List<Float> cantidades, List<Float> precios) {
        this.lineas.get(rowSelected).fill(proveedores, cantidades, precios);
        this.tablaDetallesRequerimiento.fillCantidadesCubiertas(rowSelected,
                this.lineas.get(rowSelected).getCantidadCubierta());
    }

    public void loadComponents() {
        idsRequerimientos = new ArrayList<Integer>();
        lineas = new ArrayList<LineaProveedoresPorProducto>();
        fillComboOrdenes(this.jComboRequerimientos);
    }

    public void refreshTablaDetalles() {
        this.tablaDetallesRequerimiento.refresh();
    }

    public void fillComboOrdenes(JComboBox jComboRequerimientos) {
        int estado = 0;
        List<Requirimientocompra> requerimientos = controladorCompra.sacaRequerimientos(estado);
        int j = requerimientos.size();
        for (int i = 0; i < j; i++) {
            jComboRequerimientos.addItem("Requerimiento - " + requerimientos.get(i).getIdreqerimientocompra());
            idsRequerimientos.add(requerimientos.get(i).getIdreqerimientocompra());
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
        jLabel1 = new javax.swing.JLabel();
        jComboRequerimientos = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jFechaAcordada = new com.toedter.calendar.JDateChooser();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablaDetallesRequerimiento = new javax.swing.JTable();
        jLayeredPane3 = new javax.swing.JLayeredPane();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setTitle("Atender Orden de Requerimiento de Compra");
        setName(""); // NOI18N

        jLabel1.setText("Orden de Compra:");
        jLayeredPane1.add(jLabel1);
        jLabel1.setBounds(120, 20, 110, 30);

        jComboRequerimientos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Eliga una orden de compra" }));
        jComboRequerimientos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jComboRequerimientosMousePressed(evt);
            }
        });
        jComboRequerimientos.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboRequerimientosItemStateChanged(evt);
            }
        });
        jComboRequerimientos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboRequerimientosActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jComboRequerimientos);
        jComboRequerimientos.setBounds(290, 20, 180, 30);

        jLabel3.setText("Fecha Acordada:");
        jLayeredPane1.add(jLabel3);
        jLabel3.setBounds(120, 70, 140, 20);
        jLayeredPane1.add(jFechaAcordada);
        jFechaAcordada.setBounds(290, 70, 180, 20);

        jLayeredPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("Productos Asociados"));

        jTablaDetallesRequerimiento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "N° de pedido por producto", "Producto", "Cantidad requerida", "Cantidad cubierta"
            }
        ));
        jTablaDetallesRequerimiento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaDetallesRequerimientoMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTablaDetallesRequerimientoMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTablaDetallesRequerimiento);

        jLayeredPane2.add(jScrollPane1);
        jScrollPane1.setBounds(10, 20, 610, 120);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/save.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jLayeredPane3.add(jButton1);
        jButton1.setBounds(460, 0, 70, 40);

        jLabel2.setText("Atender Pedido");
        jLayeredPane3.add(jLabel2);
        jLabel2.setBounds(460, 50, 90, 14);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error 1.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jLayeredPane3.add(jButton5);
        jButton5.setBounds(560, 0, 60, 40);

        jLabel12.setText("Cancelar");
        jLayeredPane3.add(jLabel12);
        jLabel12.setBounds(570, 50, 60, 14);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 655, Short.MAX_VALUE)
                    .addComponent(jLayeredPane2)
                    .addComponent(jLayeredPane3)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboRequerimientosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboRequerimientosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboRequerimientosActionPerformed

    private void jTablaDetallesRequerimientoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaDetallesRequerimientoMousePressed

    }//GEN-LAST:event_jTablaDetallesRequerimientoMousePressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int row = jComboRequerimientos.getSelectedIndex();
        Date fechaAcordada = jFechaAcordada.getDate();

        if (row == 0) {
            JOptionPane.showMessageDialog(null, "Seleccione una orden de compra a cubrir");
        } else if (fechaAcordada == null) {
            JOptionPane.showMessageDialog(null, "Seleccione una fecha");
        } else {
            if (row > 0 && tablaDetallesRequerimiento.isFull()) {
                controladorCompra.createCompra(this.idsRequerimientos.get(row - 1), this.lineas,
                        this.tablaDetallesRequerimiento.getDetalles(), jFechaAcordada.getDate());
                lbj.LBJ.Login.escritorio.compra.refreshPurchaseTable();
                JOptionPane.showMessageDialog(this, "Se atendió el pedido y se ingresaron las compras correctamente");
                
                mycontroladorseguridad.insertarlog(usuario, new Date(), "Insertar", "Compra", "Registrar Compra");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "La orden no ha sido atendida completamente");
            }
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboRequerimientosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboRequerimientosMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboRequerimientosMousePressed

    private void jTablaDetallesRequerimientoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaDetallesRequerimientoMouseClicked
        Requerimientocompraxproducto requerimiento;
        float cubierta;
        if (evt.getClickCount() == 2) {
            int row = jTablaDetallesRequerimiento.getSelectedRow();
            requerimiento = tablaDetallesRequerimiento.getDetalle(row);
            cubierta = tablaDetallesRequerimiento.getCantidadCubierta(row);
            this.rowSelected = row;

            asignarProveedorOrden2 = new AsignarProveedorOrden2(this, requerimiento, cubierta, lineas.get(row));
            getDesktopPane().add(asignarProveedorOrden2);
            asignarProveedorOrden2.setVisible(true);
        }
        try {
            asignarProveedorOrden2.setSelected(true);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jTablaDetallesRequerimientoMouseClicked

    private void jComboRequerimientosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboRequerimientosItemStateChanged
        int row = jComboRequerimientos.getSelectedIndex();
        this.lineas.clear();
        if (row > 0) {
            row = row - 1;
            int id = idsRequerimientos.get(row);
            this.tablaDetallesRequerimiento = new TablaDetallesRequerimiento(id);
            this.tablaDetallesRequerimiento.createLineas(lineas);
            this.jTablaDetallesRequerimiento.setModel(this.tablaDetallesRequerimiento);
        } else {
            this.tablaDetallesRequerimiento.borraDatos();
        }
    }//GEN-LAST:event_jComboRequerimientosItemStateChanged

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        if(JOptionPane.showConfirmDialog(null, "¿Está seguro de cancelar el registro?",
            "Mensaje",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
        lbj.LBJ.Login.escritorio.compra.refreshPurchaseTable();
        this.dispose();
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox jComboRequerimientos;
    private com.toedter.calendar.JDateChooser jFechaAcordada;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTablaDetallesRequerimiento;
    // End of variables declaration//GEN-END:variables
}
