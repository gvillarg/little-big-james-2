/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package produccion.vistas;

import produccion.controlador.ControladorProduccion;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import utils.Utils;

/**
 *
 * @author James
 */
public class BuscarProduccion extends javax.swing.JInternalFrame {

    public static final int ELIMINADA = 0;
    public static final int ACTIVA = 1;
    private ArrayList<modelos.Produccion> listaProducciones; // Aca se setea la lista de asignaciones :P
    private ArrayList<modelos.Produccion> listaProduccionesBuscadas;

    public BuscarProduccion() {
        initComponents();
        listaProducciones = ControladorProduccion.seleccionarProducciones();
        listaProduccionesBuscadas = new ArrayList<>();
        for (int i = 0; i < listaProducciones.size(); i++) {
            listaProduccionesBuscadas.add(listaProducciones.get(i));
        }
        dpFechaInicio.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("date")) {
                    dpFechaFin.setMinSelectableDate(dpFechaInicio.getDate());
                }
            }
        });
        dpFechaFin.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("date")) {
                    dpFechaInicio.setMaxSelectableDate(dpFechaFin.getDate());
                }
            }
        });
        tblAsignaciones.setModel(modeloTabla);
    }
    private AbstractTableModel modeloTabla = new AbstractTableModel() {
        private String[] cabeceras = {"Fecha"};

        @Override
        public int getRowCount() {
            return listaProduccionesBuscadas.size();
        }

        @Override
        public int getColumnCount() {
            return cabeceras.length;
        }

        @Override
        public String getColumnName(int columna) {
            return cabeceras[columna];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {

            switch (columnIndex) {
                case 0:
                    return listaProduccionesBuscadas.get(rowIndex).getFecha();
            }
            return null;
        }

        @Override
        public Class<?> getColumnClass(int columna) {
            if (columna == 0) {
                return Date.class;
            } else {
                return String.class;
            }
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        }
    };

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblAsignaciones = new javax.swing.JTable();
        btnEliminar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        dpFechaInicio = new com.toedter.calendar.JDateChooser();
        dpFechaFin = new com.toedter.calendar.JDateChooser();

        setClosable(true);
        setIconifiable(true);
        setTitle("Mantenimiento de Produccion");

        tblAsignaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"17/09/2013", "Pendiente"},
                {"16/09/2013", "Activa"},
                {"15/09/2013", "Completada"},
                {"14/09/2013", "Completada"}
            },
            new String [] {
                "Fecha", "Estado"
            }
        ));
        tblAsignaciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAsignacionesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblAsignaciones);

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/trash.png"))); // NOI18N
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/file plus.png"))); // NOI18N
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/search.png"))); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jLabel5.setText("Fecha Inicio:");

        jLabel6.setText("Fecha Fin:");

        dpFechaInicio.setDateFormatString("dd/MM/yyyy");

        dpFechaFin.setDateFormatString("dd/MM/yyyy");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dpFechaInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dpFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnNuevo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBuscar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEliminar))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(dpFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dpFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 17, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(24, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        ProduccionNueva produccionNueva = new ProduccionNueva();
        getDesktopPane().add(produccionNueva);
        produccionNueva.setVisible(true);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        //SI QUERIAS HACERLO CON BASE DE DATOS MODIFICALO XD SOLO CONTINUARIAN LOS MISMO IF XD 
        buscarProducciones();
        RefrescarTabla();
    }//GEN-LAST:event_btnBuscarActionPerformed

    public void buscarProducciones() {
        modelos.Produccion produccion;

        listaProduccionesBuscadas = new ArrayList<>();
        for (int i = 0; i < listaProducciones.size(); i++) {
            produccion = listaProducciones.get(i);
            if ((dpFechaFin.getDate() == null || Utils.compararFechas(dpFechaFin.getDate(), produccion.getFecha()) >= 0)
                    && (dpFechaInicio.getDate() == null || Utils.compararFechas(dpFechaInicio.getDate(), produccion.getFecha()) <= 0)) {
                listaProduccionesBuscadas.add(produccion);
            }
        }
    }

    private void tblAsignacionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAsignacionesMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int row = tblAsignaciones.getSelectedRow();
            modelos.Produccion produccion = listaProduccionesBuscadas.get(row);
            ArrayList<modelos.Produccionxempleado> listaProduccionxempleado = ControladorProduccion.
                    buscarProduccionxempleado(produccion.getIdproduccion());
            ArrayList<modelos.Produccionxproducto> listaProduccionxproducto =
                    ControladorProduccion.buscarProduccionxproducto(produccion.getIdproduccion());


            //FALTA PASAR LA LISTA DE PRODUCTOS, FECHA DE INICIO Y FECHA DE FIN
            ProduccionDetalle ventanaProduccion = new ProduccionDetalle(produccion,
                    listaProduccionxempleado, listaProduccionxproducto, ProduccionDetalle.EDITAR);
            getDesktopPane().add(ventanaProduccion);
            ventanaProduccion.setVisible(true);

        }
    }//GEN-LAST:event_tblAsignacionesMouseClicked

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int row = tblAsignaciones.getSelectedRow();
        if (row != -1) {
//            if (listaProduccionesBuscadas.get(row).getEstado() == COMPLETADA) {
//                JOptionPane.showMessageDialog(null, "No se puede eliminar una produccion en estado COMPLETA", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
//            } else 
            if (JOptionPane.showConfirmDialog(rootPane, "¿Desea realmente eliminar esta asignacion?",
                    "Mensaje", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                modelos.Produccion produccion = listaProduccionesBuscadas.get(row);
                String res = ControladorProduccion.eliminarProduccion(produccion);
                if (res == null) {
                    ActualizarDatos();
                } else {
                    JOptionPane.showMessageDialog(null, res, "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un producto para eliminarlo.", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    public void RefrescarTabla() {
        ((AbstractTableModel) tblAsignaciones.getModel()).fireTableDataChanged();
    }

    public void ActualizarDatos() {
        listaProducciones = ControladorProduccion.seleccionarProducciones();
        buscarProducciones();
        RefrescarTabla();
    }

    public boolean compararFechas(Date fecha) {
        if (fecha.compareTo(dpFechaInicio.getDate()) >= 0 && fecha.compareTo(dpFechaFin.getDate()) <= 0) {
            return true;
        } else {
            return false;
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnNuevo;
    private com.toedter.calendar.JDateChooser dpFechaFin;
    private com.toedter.calendar.JDateChooser dpFechaInicio;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblAsignaciones;
    // End of variables declaration//GEN-END:variables
}
