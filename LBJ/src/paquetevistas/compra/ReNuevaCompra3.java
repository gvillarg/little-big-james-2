/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paquetevistas.compra;

import controlador.compra.controladorcompra;
import controlador.ventas.ControllerVentas;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import modelos.Compra;
import modelos.Detallecompra;
import modelos.NewHibernateUtil;
import modelos.Producto;
import modelos.Proveedor;
import modelos.Requerimientocompraxproducto;
import modelos.RequerimientocompraxproductoId;
import modelos.Requirimientocompra;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import paquetevistas.almacen.OrdenRequerimiento1;

/**
 *
 * @author Jacklin
 */
public class ReNuevaCompra3 extends javax.swing.JInternalFrame {

    /**
     * Creates new form NuevaCompra2
     */
    public static Compra compra;
    public static ModificarCompra ventana;
    public static List<Integer> idsProductos;
    public static List<Float> cantidadesRequeridas;
    public static ControllerVentas controladorVentas = new ControllerVentas();
    public static controladorcompra controladorCompra = new controladorcompra();
    public List<Integer> idsRequerimientos;
    TablaDetallesRequerimiento tablaDetallesRequerimiento;
    public static AsignarProveedorOrden3 asignarProveedorOrden2;
    public static List<LineaProveedoresPorProducto> lineas;
    public int rowSelected;
    public static controladorcompra mycontroladorcompra = new controladorcompra();

    class TablaDetallesRequerimiento extends AbstractTableModel {

        List<Requerimientocompraxproducto> detalles = new ArrayList<Requerimientocompraxproducto>();
        List<Float> cantidadesCubiertas;
        List<Float> cantidadesRequeridas;
        String[] titles = {"N° de pedido por producto", "Producto", "Cantidad requerida", "CantidadCubierta"};
        String[] estados = {"Registrada", "Procesandose", "Lista", "Terminada", "Cancelada"};

        public TablaDetallesRequerimiento(List<Integer> requerimientos, List<Integer> productos,
                List<Float> cantidadesRequeridas) {
            detalles = sacaDetalle(requerimientos, productos);
            this.cantidadesRequeridas = cantidadesRequeridas;
            this.cantidadesCubiertas = new ArrayList<Float>();
            int j = detalles.size();
            for (int i = 0; i < j; i++) {
                cantidadesCubiertas.add(new Float(0));
            }
        }

        public void refresh() {
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
                    return cantidadesRequeridas.get(rowIndex);
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
                if (cantidadesRequeridas.get(i) > cantidadesCubiertas.get(i)) {
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

    public ReNuevaCompra3(ModificarCompra ventana, Compra compra) {
        this.ventana = ventana;
        this.compra = compra;
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
        idsProductos = new ArrayList<Integer>();
        cantidadesRequeridas = new ArrayList<Float>();
        fill(idsRequerimientos, idsProductos, cantidadesRequeridas);
        this.tablaDetallesRequerimiento = new TablaDetallesRequerimiento(idsRequerimientos, idsProductos, cantidadesRequeridas);
        lineas = new ArrayList<LineaProveedoresPorProducto>();
        this.tablaDetallesRequerimiento.createLineas(lineas);
        this.jTablaDetallesRequerimiento.setModel(this.tablaDetallesRequerimiento);
        jorden.setText("Requerimiento-" + idsRequerimientos.get(0).toString());
        jFechaAcordada.setDate(lbj.LBJ.Login.escritorio.compra.compraaeditar.getFechareal());
    }

    public void fill(List<Integer> idsRequerimientos, List<Integer> idsProductos, List<Float> cantidadesRequeridas) {
        controladorcompra controladorCompra = new controladorcompra();
        List<Detallecompra> detalles = controladorCompra.sacadetallesxcompbycompid(compra.getIdcompra());
        int j = detalles.size();
        for (int i = 0; i < j; i++) {
            Detallecompra detalle = detalles.get(i);
            Requerimientocompraxproducto r = detalle.getRequerimientocompraxproducto();
            RequerimientocompraxproductoId id = r.getId();
            idsRequerimientos.add(id.getIdreqerimientocompra());
            idsProductos.add(id.getIdproducto());
            cantidadesRequeridas.add(detalle.getCantidad());
        }
    }

    public void refreshTablaDetalles() {
        this.tablaDetallesRequerimiento.refresh();
    }

    public List<Requerimientocompraxproducto> sacaDetalle(List<Integer> requerimientos, List<Integer> productos) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        List<Requerimientocompraxproducto> detalles = new ArrayList<Requerimientocompraxproducto>();
        try {
            tx = session.beginTransaction();
            int j = requerimientos.size();
            for (int i = 0; i < j; i++) {
                String sqlquery = "FROM Requerimientocompraxproducto WHERE idreqerimientocompra = :id1 AND idproducto = :id2";
                query = session.createQuery(sqlquery);
                query.setParameter("id1", requerimientos.get(i).intValue());
                query.setParameter("id2", productos.get(i).intValue());
                detalles.add((Requerimientocompraxproducto) query.uniqueResult());
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return detalles;
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
        jLabel3 = new javax.swing.JLabel();
        jFechaAcordada = new com.toedter.calendar.JDateChooser();
        jorden = new javax.swing.JTextField();
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
        setTitle("Atender Orden de Requerimiento de Compra");
        setName(""); // NOI18N

        jLabel1.setText("Orden de Compra:");
        jLayeredPane1.add(jLabel1);
        jLabel1.setBounds(120, 20, 110, 30);

        jLabel3.setText("Fecha Acordada:");
        jLayeredPane1.add(jLabel3);
        jLabel3.setBounds(120, 70, 140, 20);
        jLayeredPane1.add(jFechaAcordada);
        jFechaAcordada.setBounds(290, 70, 180, 20);

        jorden.setEditable(false);
        jorden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jordenActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jorden);
        jorden.setBounds(290, 20, 170, 20);

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
        jButton1.setBounds(480, 0, 70, 40);

        jLabel2.setText("Atender Pedido");
        jLayeredPane3.add(jLabel2);
        jLabel2.setBounds(480, 50, 90, 14);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error 1.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jLayeredPane3.add(jButton5);
        jButton5.setBounds(580, 0, 60, 40);

        jLabel12.setText("Cancelar");
        jLayeredPane3.add(jLabel12);
        jLabel12.setBounds(590, 50, 60, 14);

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

    private void jTablaDetallesRequerimientoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaDetallesRequerimientoMousePressed

    }//GEN-LAST:event_jTablaDetallesRequerimientoMousePressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Date fechaAcordada = jFechaAcordada.getDate();

        if (fechaAcordada == null) {
            JOptionPane.showMessageDialog(null, "Seleccione una fecha");
        } else {
            if (tablaDetallesRequerimiento.isFull()) {
                controladorCompra.createCompra(this.idsRequerimientos.get(0), this.lineas,
                        this.tablaDetallesRequerimiento.getDetalles(), jFechaAcordada.getDate());
//            JOptionPane.showMessageDialog(null,"sdfsafaf");
                Compra compra = lbj.LBJ.Login.escritorio.compra.compraaeditar;
                mycontroladorcompra.modificarcompraestado(compra);
                lbj.LBJ.Login.escritorio.compra.refreshPurchaseTable();
                JOptionPane.showMessageDialog(this, "Se atendió el pedido y se ingresaron las compras correctamente");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "La orden no ha sido atendida completamente");
            }
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTablaDetallesRequerimientoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaDetallesRequerimientoMouseClicked
        Requerimientocompraxproducto requerimiento;
        float cubierta;
        if (evt.getClickCount() == 2) {
            int row = jTablaDetallesRequerimiento.getSelectedRow();
            requerimiento = tablaDetallesRequerimiento.getDetalle(row);
            cubierta = tablaDetallesRequerimiento.getCantidadCubierta(row);
            this.rowSelected = row;
            float requerido = (Float) jTablaDetallesRequerimiento.getModel().getValueAt(row, 2);
            asignarProveedorOrden2 = new AsignarProveedorOrden3(this, requerimiento, cubierta, lineas.get(row), requerido);
            getDesktopPane().add(asignarProveedorOrden2);
            asignarProveedorOrden2.setVisible(true);
        }
        try {
            asignarProveedorOrden2.setSelected(true);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jTablaDetallesRequerimientoMouseClicked

    private void jordenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jordenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jordenActionPerformed

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
    private javax.swing.JTextField jorden;
    // End of variables declaration//GEN-END:variables
}
