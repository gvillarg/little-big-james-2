/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paquetevistas.almacen;

import Seguridad.Controlador.controladorseguridad;
import controlador.almacen.controladoralmacen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import modelos.Motivo;
import modelos.Movimiento;
import modelos.Movsalidadetalle;
import modelos.Producto;
import modelos.Productoxmovimiento;
import modelos.Usuario;
import paquetevistas.Escritorio;

/**
 *
 * @author mariofcandia
 */
public class Movimientos extends javax.swing.JInternalFrame {

    /**
     * Creates new form Movimientos
     */
    public static MovimientoNuevo movimientonuevo;
    public static controladoralmacen mycontroladoralmacen = new controladoralmacen();
    ProductTableModel myProductTableModel = new ProductTableModel();
    public static Movimiento movimientoaeditar;
    //Para el registro de Log
    public static Usuario usuario = lbj.LBJ.Login.usuario;
    public static controladorseguridad mycontroladorseguridad = new controladorseguridad();

    public Movimientos() {
        initComponents();
        loadComponents();
        jTable1.setModel(myProductTableModel);

        mycontroladorseguridad.insertarlog(usuario, new Date(), "Consultar", "Movimiento", "Consultar Movimientos");
    }

    class ProductTableModel extends AbstractTableModel {

        //List<Producto> pro = new ArrayList<Producto>();
        List<Movimiento> mov = new ArrayList<Movimiento>();

        public ProductTableModel() {
            //pro = mycontroladoralmacen.sacaproductos();
            mov = mycontroladoralmacen.sacamovimientos();
        }
        String[] titles = {"id", "Fecha", "Tipo", "Motivo", "Estado"};

        public int getRowCount() {
            return mov.size();
        }

        public int getColumnCount() {
            return 5;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            String res = null;
            int tipo, idmotivo, estado;
            Motivo motivo = new Motivo();
            Movimiento m = mov.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    res = "" + m.getIdmovimiento();
                    break;
                case 1:
                    res = "" + m.getFecha();
                    break;
                case 2:
                    tipo = m.getTipo();
                    if (tipo == 1) {
                        res = "Entrada";
                        break;
                    } else {
                        res = "Salida";
                        break;
                    }
                case 3:
                    idmotivo = m.getMotivo().getIdmotivo();
                    motivo = mycontroladoralmacen.sacamotivobyid(idmotivo);
                    res = motivo.getNombre();
                    break;
                case 4:
                    if (m.getTipo() == 1) {
                        estado = m.getEstado();
                        if (estado == 1) {
                            res = "Restante";
                            break;
                        } else if (estado == 2) {
                            res = "Consumida";
                            break;
                        } else {
                            res = "Cancelada";
                            break;
                        }
                    } else {
                        estado = m.getEstado();
                        if (estado == 1) {
                            res = "Confirmada";
                            break;
                        } else {
                            res = "Cancelada";
                            break;
                        }

                    }
            }
            return res;
        }

        public String getColumnName(int col) {
            return titles[col];
        }
    }

    public void loadComponents() {

        cmbTipo.removeAllItems();
        cmbTipo.addItem("Todos");
        cmbTipo.addItem("Entrada");
        cmbTipo.addItem("Salida");
        cmbTipo.setSelectedIndex(0);

        cmbMotivo.removeAllItems();
        List<Motivo> motivos = new ArrayList<Motivo>();
        motivos = mycontroladoralmacen.sacamotivos();
        cmbMotivo.addItem("Todos");
        for (int i = 0; i < motivos.size(); i++) {
            if (motivos.get(i).getIdmotivo() == 1
                    || motivos.get(i).getIdmotivo() == 2
                    || motivos.get(i).getIdmotivo() == 3
                    || motivos.get(i).getIdmotivo() == 5
                    || motivos.get(i).getIdmotivo() == 6) {
                cmbMotivo.addItem(motivos.get(i).getNombre());
            }
        }
        cmbMotivo.setSelectedIndex(0);

        jRadioButton1.setSelected(true);
        jRadioButton2.setSelected(false);

        cmbTipo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipo = cmbTipo.getSelectedItem().toString();

                if (tipo.equalsIgnoreCase("Entrada")) {
                    cmbMotivo.removeAllItems();
                    List<Motivo> motivos = new ArrayList<Motivo>();
                    motivos = mycontroladoralmacen.sacamotivos();
                    cmbMotivo.addItem("Todos");
                    for (int i = 0; i < motivos.size(); i++) {
                        if (motivos.get(i).getIdmotivo() == 1
                                || motivos.get(i).getIdmotivo() == 2
                                || motivos.get(i).getIdmotivo() == 3
                                || motivos.get(i).getIdmotivo() == 5
                                || motivos.get(i).getIdmotivo() == 6) {
                            cmbMotivo.addItem(motivos.get(i).getNombre());
                        }
                    }
                    cmbMotivo.setSelectedIndex(0);
                } else if (tipo.equalsIgnoreCase("Salida")) {
                    cmbMotivo.removeAllItems();
                    List<Motivo> motivos = new ArrayList<Motivo>();
                    motivos = mycontroladoralmacen.sacamotivos();
                    cmbMotivo.addItem("Todos");
                    for (int i = 0; i < motivos.size(); i++) {
                        if (motivos.get(i).getIdmotivo() == 2
                                || motivos.get(i).getIdmotivo() == 3
                                || motivos.get(i).getIdmotivo() == 4
                                || motivos.get(i).getIdmotivo() == 5
                                || motivos.get(i).getIdmotivo() == 6) {
                            cmbMotivo.addItem(motivos.get(i).getNombre());
                        }
                    }
                    cmbMotivo.setSelectedIndex(0);
                } else {
                    cmbMotivo.removeAllItems();
                    List<Motivo> motivos = new ArrayList<Motivo>();
                    motivos = mycontroladoralmacen.sacamotivos();
                    cmbMotivo.addItem("Todos");
                    for (int i = 0; i < motivos.size(); i++) {
                        cmbMotivo.addItem(motivos.get(i).getNombre());
                    }
                    cmbMotivo.setSelectedIndex(0);
                }

//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }

    public void refreshProductTable() {
        myProductTableModel.mov = mycontroladoralmacen.sacamovimientos();
        myProductTableModel.fireTableChanged(null);
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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        cmbMotivo = new javax.swing.JComboBox();
        cmbTipo = new javax.swing.JComboBox();
        jRadioButton3 = new javax.swing.JRadioButton();
        jLabel8 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();

        setClosable(true);
        setIconifiable(true);
        setTitle("Mantenimiento de Movimientos");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/file plus.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/search.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/trash.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel1.setText("Agregar");

        jLabel2.setText("Buscar");

        jLabel3.setText("Eliminar");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"22/09/2013", "Entrada", "Consumida"},
                {"19/09/2013", "Entrada", "Restante"},
                {"20/09/2013", "Salida", null},
                {null, null, null}
            },
            new String [] {
                "Fecha", "Tipo", "Estado"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Criterios de Busqueda"));

        jLabel4.setText("Tipo:");

        jLabel6.setText("Motivo:");

        jLabel5.setText("Desde:");

        jLabel7.setText("Estado:");

        jRadioButton1.setText("Restante / Confirmada");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        jRadioButton2.setText("Consumido");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        cmbMotivo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fecha vencimiento" }));
        cmbMotivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMotivoActionPerformed(evt);
            }
        });

        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Entrada", "Salida" }));

        jRadioButton3.setText("Cancelado");
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        jLabel8.setText("Hasta:");

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel4)
                            .add(jLabel6)
                            .add(jLabel5))
                        .add(38, 38, 38)
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                .add(cmbTipo, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(cmbMotivo, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .add(jPanel3Layout.createSequentialGroup()
                                .add(jDateChooser1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 145, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(18, 18, 18)
                                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jPanel3Layout.createSequentialGroup()
                                        .add(jRadioButton2)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(jRadioButton3))
                                    .add(jPanel3Layout.createSequentialGroup()
                                        .add(jLabel8)
                                        .add(18, 18, 18)
                                        .add(jDateChooser2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))))
                    .add(jLabel7)
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(70, 70, 70)
                        .add(jRadioButton1))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4)
                    .add(cmbTipo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel6)
                    .add(cmbMotivo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(jLabel5)
                    .add(jDateChooser1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel8)
                    .add(jDateChooser2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(jLabel7)
                    .add(jRadioButton1)
                    .add(jRadioButton2)
                    .add(jRadioButton3))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(14, 14, 14)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(jLabel1)
                                .add(18, 18, 18)
                                .add(jLabel2))
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(jButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(18, 18, 18)
                                .add(jButton2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .add(18, 18, 18)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jButton3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel3)))
                    .add(jPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(15, 15, 15)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jButton2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jButton3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(jLabel2)
                    .add(jLabel3))
                .add(18, 18, 18)
                .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (movimientonuevo == null || movimientonuevo.isClosed()) {
            movimientoaeditar = new Movimiento();
            movimientoaeditar.setIdmovimiento(0);
            movimientonuevo = new MovimientoNuevo();
            Escritorio.movimientos.getDesktopPane().add(movimientonuevo);
            movimientonuevo.setTitle("Movimiento Nuevo");
            movimientonuevo.setVisible(true);
        } else {
            try {
                movimientonuevo.setSelected(true);
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:        
        if (evt.getClickCount() == 2) {
            int row = jTable1.getSelectedRow();
            String ids = jTable1.getValueAt(row, 0).toString();
            int id = Integer.parseInt(ids);
            movimientoaeditar = mycontroladoralmacen.sacamovimientobyid(id);

            movimientonuevo = new MovimientoNuevo();
            Escritorio.movimientos.getDesktopPane().add(movimientonuevo);
            movimientonuevo.setTitle("Editar Movimiento");
            movimientonuevo.setVisible(true);
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
        if ((!jRadioButton2.isSelected()) && (!jRadioButton3.isSelected())) {
            jRadioButton1.setSelected(true);
        }
        jRadioButton2.setSelected(false);
        jRadioButton3.setSelected(false);
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
        if ((!jRadioButton1.isSelected()) && (!jRadioButton3.isSelected())) {
            jRadioButton2.setSelected(true);
        }
        jRadioButton1.setSelected(false);
        jRadioButton3.setSelected(false);
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void cmbMotivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMotivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbMotivoActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        String tipo = cmbTipo.getSelectedItem().toString();
        int estado;
        String tipoint, smotivo;
        if (tipo.equalsIgnoreCase("Entrada")) {
            tipoint = "1";
        } else if (tipo.equalsIgnoreCase("Salida")) {
            tipoint = "2";
        } else {
            tipoint = "Todos";
        }
        String nombremotivo = cmbMotivo.getSelectedItem().toString();
        if (nombremotivo.equalsIgnoreCase("Todos")) {
            smotivo = "Todos";
        } else {
            Motivo motivo = mycontroladoralmacen.sacamotivobyname(nombremotivo);
            smotivo = "" + motivo.getIdmotivo();
        }
        Date fecha = jDateChooser1.getDate();
        Date fecha2 = jDateChooser2.getDate();
        if (jRadioButton1.isSelected()) {
            estado = 1;
        } else if (jRadioButton2.isSelected()) {
            estado = 2;
        } else {
            estado = 3;
        }
        myProductTableModel.mov = mycontroladoralmacen.buscamovimientos(tipoint, smotivo, fecha, fecha2, estado);
        myProductTableModel.fireTableChanged(null);

        mycontroladorseguridad.insertarlog(usuario, new Date(), "Consultar", "Movimiento", "Consultar Movimientos");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if (jTable1.getSelectedRow() != -1) {
            int row = jTable1.getSelectedRow();
            int id = Integer.parseInt(jTable1.getValueAt(row, 0).toString());
            Movimiento mov = mycontroladoralmacen.sacamovimientobyid(id);
            if (mov.getEstado() == 2) {
                JOptionPane.showMessageDialog(null, "No puede cancelar el movimiento, ya se consumieron sus productos.", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            } else if (mov.getEstado() == 1) {
                if (mov.getTipo() == 1) {
                    int tienestockactual = 1;
                    tienestockactual = mycontroladoralmacen.verificastockactual(mov);
                    if (tienestockactual == 2) {
                        JOptionPane.showMessageDialog(null, "El movimiento esta siendo usado, no puede eliminarlo", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                    } else {

                        if (JOptionPane.showConfirmDialog(rootPane, "¿Desea realmente eliminar el movimiento " + mov.getIdmovimiento() + " ?",
                                "Mensaje", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            //elimina el movimiento
                            mycontroladoralmacen.eliminamovimiento(mov);
                            //actualiza los stocks
                            List<Productoxmovimiento> pxmov = mycontroladoralmacen.sacaproductosxmovimiento(mov.getIdmovimiento());
                            for (int i = 0; i < pxmov.size(); i++) {
                                Producto p = mycontroladoralmacen.sacaproductobyid(pxmov.get(i).getId().getIdproducto());
                                System.out.println(p.getStock() - pxmov.get(i).getStock());
                                mycontroladoralmacen.actualizarstock(p, p.getStock() - pxmov.get(i).getStock());
                            }
                            refreshProductTable();

                            mycontroladorseguridad.insertarlog(usuario, new Date(), "Eliminar", "Movimiento", "Eliminar Movimiento");
                        }
                    }
                } else {
                    //JOptionPane.showMessageDialog(null, "En breve estara implementado la cancelacion de los movimientos de salida", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                    //ACA ELIMINO LA SALIDA

                    if (JOptionPane.showConfirmDialog(rootPane, "¿Desea realmente eliminar el movimiento " + mov.getIdmovimiento() + " ?",
                            "Mensaje", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        //elimina el movimiento
                        mycontroladoralmacen.eliminamovimiento(mov);
                        //actualiza los stocks
                        List<Productoxmovimiento> pxmov = mycontroladoralmacen.sacaproductosxmovimiento(mov.getIdmovimiento());
                        for (int i = 0; i < pxmov.size(); i++) {
                            Producto p = mycontroladoralmacen.sacaproductobyid(pxmov.get(i).getId().getIdproducto());
                            System.out.println(p.getStock() - pxmov.get(i).getStock());
                            mycontroladoralmacen.actualizarstock(p, p.getStock() + pxmov.get(i).getStock());
                            //ACA RECORE LAS ENTRADAS DE DONDE SACO ESE PRODUCTO, LES AUMENTA EL STOCK ACTUAL Y CAMBIA EL ESTADO AL MOVIMIENTO DE ENTRADA
                            List<Movsalidadetalle> movsaldtll = mycontroladoralmacen.sacamovimientosalidadetalle(pxmov.get(i).getId().getIdmovimiento(),
                                    pxmov.get(i).getId().getIdproducto());
                            for (int j = 0; j < movsaldtll.size(); j++) {
                                mycontroladoralmacen.actualizamovimiento(movsaldtll.get(j).getMovimiento().getIdmovimiento(),
                                        movsaldtll.get(j).getCantidad(), movsaldtll.get(j).getProductoxmovimiento().getId().getIdproducto());
                            }
                            //ACA RECORE LAS ENTRADAS DE DONDE SACO ESE PRODUCTO, LES AUMENTA EL STOCK ACTUAL Y CAMBIA EL ESTADO AL MOVIMIENTO DE ENTRADA
                        }
                        refreshProductTable();

                        mycontroladorseguridad.insertarlog(usuario, new Date(), "Eliminar", "Movimiento", "Eliminar Movimiento");
                    }

                }
            } else if (mov.getEstado() == 3) {
                JOptionPane.showMessageDialog(null, "El movimiento ya esta cancelado.", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un movimiento para cancelarlo.", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
        // TODO add your handling code here:
        if ((!jRadioButton1.isSelected()) && (!jRadioButton2.isSelected())) {
            jRadioButton3.setSelected(true);
        }
        jRadioButton1.setSelected(false);
        jRadioButton2.setSelected(false);
    }//GEN-LAST:event_jRadioButton3ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cmbMotivo;
    private javax.swing.JComboBox cmbTipo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
