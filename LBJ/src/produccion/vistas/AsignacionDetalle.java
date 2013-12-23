/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package produccion.vistas;

import produccion.controlador.ControladorProduccion;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import modelos.Empleado;
import paquetevistas.Escritorio;

/**
 *
 * @author James
 */
public class AsignacionDetalle extends javax.swing.JInternalFrame {

    public static Escritorio escritorio;
    public static AsignacionDetalle desempenoTrabajador;
    private ArrayList<modelos.Actividad> actividades = ControladorProduccion.seleccionarActividades();
    private ArrayList<modelos.Turno> turnos = ControladorProduccion.seleccionarTurnos();
    private int actSeleccionada;
    private int turnoSeleccionado;
    private ArrayList<modelos.Asignacionxproducto> listaAsignacionxproducto;
    private modelos.Asignacion asignacion;
    private ArrayList<modelos.Asignacionxempleado> listaAsignacionxempleado;
    private ArrayList<modelos.Asignacionxempleado> listaAsignacionxempleadoBuscados;
    private ArrayList<Empleado> empNoAsignados;

    public AsignacionDetalle(modelos.Asignacion asignacion, ArrayList<modelos.Asignacionxempleado> listaAsignacionxempleado,
            ArrayList<modelos.Asignacionxproducto> listaAsignacionxproducto, boolean esNueva, ArrayList<Empleado> empleadosNoAsignados) {
        initComponents();
        empNoAsignados = empleadosNoAsignados;
        if(empNoAsignados == null){
            btnEmpleadosNoAsignados.setVisible(false);
        }
        if (!esNueva) {
            btnGuardar.setEnabled(false);
        }

        this.listaAsignacionxproducto = listaAsignacionxproducto;
        this.asignacion = asignacion;

        this.listaAsignacionxempleado = listaAsignacionxempleado;
        this.listaAsignacionxempleadoBuscados = new ArrayList<>();
        this.listaAsignacionxempleadoBuscados.addAll(listaAsignacionxempleado);

        tblAsignaciones.setModel(modeloTabla);
        tblProductos.setModel(modeloTabla2);
        //Llenado de Comboboxs
        cbActividad.removeAllItems();
        cbActividad.addItem("Seleccionar Todos");
        for (int i = 0; i < actividades.size(); i++) {
            cbActividad.addItem(actividades.get(i).getNombre());
        }
        cbTurno.removeAllItems();
        cbTurno.addItem("Seleccionar Todos");
        for (int i = 0; i < turnos.size(); i++) {
            cbTurno.addItem(turnos.get(i).getNombre());
        }

        actSeleccionada = -1;
        turnoSeleccionado = -1;
        SimpleDateFormat sdf;
        if (asignacion.getFechainicial() != null && asignacion.getFechafinal() != null) {
            sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            String fechaI = sdf.format(asignacion.getFechainicial());
            String fechaF = sdf.format(asignacion.getFechafinal());
            lblFecha.setText(fechaI + " - " + fechaF);
        }
    }
    private AbstractTableModel modeloTabla = new AbstractTableModel() {
        private String[] cabeceras = {"Maquina", "Trabajador", "Actividad", "Turno", "Producto", "Produccion Estimada", "Merma Estimada"};

        @Override
        public int getRowCount() {
            return listaAsignacionxempleadoBuscados.size();
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
            if (columnIndex == 0) {
                return listaAsignacionxempleadoBuscados.get(rowIndex).getMaquina().getNombre();
            } else if (columnIndex == 1) {
                String nombreYApellidos = listaAsignacionxempleadoBuscados.get(rowIndex).getEmpleado().getNombre()
                        + " " + listaAsignacionxempleadoBuscados.get(rowIndex).getEmpleado().getAppaterno() + " "
                        + listaAsignacionxempleadoBuscados.get(rowIndex).getEmpleado().getApmaterno();
                return nombreYApellidos;
            } else if (columnIndex == 2) {
                return listaAsignacionxempleadoBuscados.get(rowIndex).getMaquina().getActividad().getNombre();
            } else if (columnIndex == 3) {
                return listaAsignacionxempleadoBuscados.get(rowIndex).getTurno().getNombre();
            } else if (columnIndex == 4) {
                return listaAsignacionxempleadoBuscados.get(rowIndex).getProducto().getNombre();
            } else if (columnIndex == 5) {
                return listaAsignacionxempleadoBuscados.get(rowIndex).getProduccionestimada();
            } else if (columnIndex == 6) {
                return listaAsignacionxempleadoBuscados.get(rowIndex).getMermaestimada();
            }
            return null;
        }

        @Override
        public Class<?> getColumnClass(int columna) {
            if (columna < 5) {
                return String.class;
            } else {
                return Float.class;
            }
        }
    };
    private AbstractTableModel modeloTabla2 = new AbstractTableModel() {
        private String[] cabeceras = {"Producto", "Cantidad Requerida", "Produccion Estimada", "Merma Estimada"};

        @Override
        public int getRowCount() {
            return listaAsignacionxproducto.size();
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
            if (columnIndex == 0) {
                String nombre = listaAsignacionxproducto.get(rowIndex).getProducto().getNombre();
                return nombre;
            } else if (columnIndex == 1) {
                float cantidad = listaAsignacionxproducto.get(rowIndex).getCantidadrequerida();
                return cantidad;
            } else if (columnIndex == 2) {
                float cantidad = listaAsignacionxproducto.get(rowIndex).getProduccionestimada();
                return cantidad;
            } else if (columnIndex == 3) {
                float cantidad = listaAsignacionxproducto.get(rowIndex).getMermaestimada();
                return cantidad;
            } else if (columnIndex == 4) {
                float cantidad = listaAsignacionxproducto.get(rowIndex).getProduccionestimada() - listaAsignacionxproducto.get(rowIndex).getMermaestimada();
                return cantidad;
            }
            return null;
        }

        @Override
        public Class<?> getColumnClass(int columna) {
            if (columna < 1) {
                return String.class;
            } else {
                return Float.class;
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
        jTable1 = new javax.swing.JTable();
        btnCancelar = new javax.swing.JButton();
        lblFecha = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        cards = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblAsignaciones = new javax.swing.JTable();
        lblPagina = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cbTurno = new javax.swing.JComboBox();
        cbActividad = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        btnEmpleadosNoAsignados = new javax.swing.JButton();

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
        setTitle("Asignación de Trabajadores y Máquinas");

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error 1.png"))); // NOI18N
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        lblFecha.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lblFecha.setText("12/11/2013");

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/save.png"))); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        cards.setBorder(javax.swing.BorderFactory.createTitledBorder("Asignaciones"));
        cards.setLayout(new java.awt.CardLayout());

        tblAsignaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Maquina", "Trabajador", "Produccion Estimada", "Merma Estimada", "Produccion Real", "Merma Real"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblAsignaciones);

        cards.add(jScrollPane3, "card2");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar Asignacion"));

        jLabel2.setText("Turno:");

        cbTurno.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbTurno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTurnoActionPerformed(evt);
            }
        });

        cbActividad.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbActividad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbActividadActionPerformed(evt);
            }
        });

        jLabel1.setText("Actividad:");

        jLabel4.setText("Empleado:");

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/search.png"))); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(42, 42, 42)
                                .addComponent(cbTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbActividad, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(185, 185, 185)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbActividad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbTurno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Productos"));

        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblProductos);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnEmpleadosNoAsignados.setText("Ver Trabajadores No Asignados");
        btnEmpleadosNoAsignados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmpleadosNoAsignadosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblPagina)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cards, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFecha)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnEmpleadosNoAsignados)
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFecha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 26, Short.MAX_VALUE)
                        .addComponent(lblPagina)
                        .addGap(306, 306, 306))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cards, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEmpleadosNoAsignados)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

        ArrayList<modelos.Asignacion> listaAsignaciones = ControladorProduccion.
                buscarAsignacionesPorFechas(asignacion.getFechainicial(), asignacion.getFechafinal());

        if (!listaAsignaciones.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ya existen asignaciones registradas para las fechas seleccionadas");
        } else {
            ControladorProduccion.guardarAsignacion(asignacion, listaAsignacionxempleado, listaAsignacionxproducto);
            if (Escritorio.buscarAsignacion != null && Escritorio.buscarAsignacion.isClosed() == false) {
                Escritorio.buscarAsignacion.ActualizarDatos();
            }
            this.dispose();
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        if(JOptionPane.showConfirmDialog(null, "¿Está seguro de cancelar la asignacion?",
            "Mensaje",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
        this.dispose();
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        listaAsignacionxempleadoBuscados = new ArrayList<>();
        modelos.Asignacionxempleado asignacionxempleado;
        String nombreEmpleado = jTextField1.getText();
        for (int i = 0; i < listaAsignacionxempleado.size(); i++) {
            asignacionxempleado = listaAsignacionxempleado.get(i);
            if (nombreEmpleado != null) {
                String nombreCompleto = asignacionxempleado.getEmpleado().getNombre()
                        + " " + asignacionxempleado.getEmpleado().getAppaterno() + " "
                        + asignacionxempleado.getEmpleado().getApmaterno();
                boolean esContenido = nombreCompleto.toLowerCase().contains(nombreEmpleado.toLowerCase());
                if (actSeleccionada != -1 && turnoSeleccionado != -1) {
                    if (esContenido && asignacionxempleado.getTurno().getIdturno() == turnos.get(turnoSeleccionado).getIdturno()
                            && asignacionxempleado.getMaquina().getActividad().getIdactividad() == actividades.get(actSeleccionada).getIdactividad()) {
                        listaAsignacionxempleadoBuscados.add(asignacionxempleado);
                    }
                } else if (actSeleccionada != -1 && turnoSeleccionado == -1) {
                    if (esContenido
                            && asignacionxempleado.getMaquina().getActividad().getIdactividad() == actividades.get(actSeleccionada).getIdactividad()) {
                        listaAsignacionxempleadoBuscados.add(asignacionxempleado);
                    }
                } else if (actSeleccionada == -1 && turnoSeleccionado != -1) {
                    if (esContenido && asignacionxempleado.getTurno().getIdturno() == turnos.get(turnoSeleccionado).getIdturno()) {
                        listaAsignacionxempleadoBuscados.add(asignacionxempleado);
                    }
                } else if (actSeleccionada == -1 && turnoSeleccionado == -1) {
                    if (esContenido) {
                        listaAsignacionxempleadoBuscados.add(asignacionxempleado);
                    }
                }
            } else {
                if (actSeleccionada != -1 && turnoSeleccionado != -1) {
                    if (asignacionxempleado.getTurno().getIdturno() == turnos.get(turnoSeleccionado).getIdturno()
                            && asignacionxempleado.getMaquina().getActividad().getIdactividad() == actividades.get(actSeleccionada).getIdactividad()) {
                        listaAsignacionxempleadoBuscados.add(asignacionxempleado);
                    }
                } else if (actSeleccionada == -1 && turnoSeleccionado != -1) {
                    if (asignacionxempleado.getTurno().getIdturno() == turnos.get(turnoSeleccionado).getIdturno()) {
                        listaAsignacionxempleadoBuscados.add(asignacionxempleado);
                    }
                } else if (actSeleccionada != -1 && turnoSeleccionado == -1) {
                    if (asignacionxempleado.getMaquina().getActividad().getIdactividad() == actividades.get(actSeleccionada).getIdactividad()) {
                        listaAsignacionxempleadoBuscados.add(asignacionxempleado);
                    }
                } else if (actSeleccionada == -1 && turnoSeleccionado == -1) {
                    listaAsignacionxempleadoBuscados.add(asignacionxempleado);
                }
            }
        }
        RefrescarTabla();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void cbActividadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbActividadActionPerformed
        actSeleccionada = cbActividad.getSelectedIndex() - 1;
    }//GEN-LAST:event_cbActividadActionPerformed

    private void cbTurnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTurnoActionPerformed
        turnoSeleccionado = cbTurno.getSelectedIndex() - 1;
    }//GEN-LAST:event_cbTurnoActionPerformed

    private void btnEmpleadosNoAsignadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmpleadosNoAsignadosActionPerformed
        // TODO add your handling code here:
        EmpleadoNoAsignados asignacionDetalle = new EmpleadoNoAsignados(empNoAsignados);
        getDesktopPane().add(asignacionDetalle);
        asignacionDetalle.setVisible(true);
    }//GEN-LAST:event_btnEmpleadosNoAsignadosActionPerformed

    public void RefrescarTabla() {
        ((AbstractTableModel) tblAsignaciones.getModel()).fireTableDataChanged();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEmpleadosNoAsignados;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JPanel cards;
    private javax.swing.JComboBox cbActividad;
    private javax.swing.JComboBox cbTurno;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblPagina;
    private javax.swing.JTable tblAsignaciones;
    private javax.swing.JTable tblProductos;
    // End of variables declaration//GEN-END:variables
}
