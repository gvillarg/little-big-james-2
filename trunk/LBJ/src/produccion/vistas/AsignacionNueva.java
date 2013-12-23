/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package produccion.vistas;

import produccion.algoritmos.Algoritmo;
import produccion.controlador.ControladorProduccion;
import controlador.almacen.controladoralmacen;
import controlador.configuracion.controladorconfiguracion;
import java.awt.Cursor;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import modelos.Actividad;
import modelos.Empleado;

/**
 *
 * @author Guti
 */
public class AsignacionNueva extends javax.swing.JInternalFrame {

    private final int numActividades = 4;
    private List<Empleado> listaEmpleados;
    private boolean[] listaSeleccion;
    private int[] numMaximoMaquinas;
    private boolean seleccionarTodo = false;
    private AbstractTableModel modeloTabla = new AbstractTableModel() {
        private String[] cabeceras = {"Código", "Nombres y Apellidos", "Seleccion"};

        @Override
        public int getRowCount() {
            return listaEmpleados.size();
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
            int codigo;
            String nombre;
            if (columnIndex == 0) {
                codigo = listaEmpleados.get(rowIndex).getIdempleado();
                return codigo;
            } else if (columnIndex == 1) {
                nombre = listaEmpleados.get(rowIndex).getNombre() + " ";
                nombre += listaEmpleados.get(rowIndex).getAppaterno() + " ";
                nombre += listaEmpleados.get(rowIndex).getApmaterno();
                return nombre;
            } else if (columnIndex == 2) {
                return listaSeleccion[rowIndex];
            }
            return null;
        }

        @Override
        public Class<?> getColumnClass(int columna) {
            if (columna == 2) {
                return Boolean.class;
            } else {
                return String.class;
            }
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            return (col == 2);
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if (columnIndex == 2) {
                listaSeleccion[rowIndex] = (boolean) aValue;
                fireTableCellUpdated(rowIndex, columnIndex);
                if (!seleccionarTodo) {
                    btnDeseleccionar.setText("Seleccionar Todo");
                    seleccionarTodo = true;
                }
            }
        }
    };

    public AsignacionNueva() {
        initComponents();

        ///////////////////
        dpFechaInicio.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                // the docs of JDateChooser says that when the date is modified, a "date" property change is fired
                if (evt.getPropertyName().equals("date")) {
                    dpFechaFin.setMinSelectableDate(dpFechaInicio.getDate());
                    modelos.Ordenproduccion orden=ControladorProduccion.buscarOrdenProduccionPorFecha(dpFechaInicio.getDate());
                    if(orden!=null){
                        dpFechaFin.setMaxSelectableDate(orden.getFechafin());
                        dpFechaFin.setEnabled(true);
                    }
                    else{
                        dpFechaFin.setDate(null);
                        dpFechaFin.setEnabled(false);
                        JOptionPane.showMessageDialog(null, "No se encuentra un plan de producción");                        
                    }
                }
            }
        });
        dpFechaFin.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                // the docs of JDateChooser says that when the date is modified, a "date" property change is fired
//                if (evt.getPropertyName().equals("date")) {
//                    dpFechaInicio.setMaxSelectableDate(dpFechaFin.getDate());
//                }
            }
        });
        ////////////////////

        controladorconfiguracion micontroladorconfiguracion = new controladorconfiguracion();
        ControladorProduccion micontroladorProduccion = new ControladorProduccion();
        listaEmpleados = micontroladorProduccion.seleccionarEmpleados();
        listaSeleccion = new boolean[listaEmpleados.size()];
        for (int i = 0; i < listaEmpleados.size(); i++) {
            listaSeleccion[i] = true;
        }
        tblTrabajadores.setModel(modeloTabla);
        controladoralmacen micontroladoralmacen = new controladoralmacen();
        List<Actividad> listaActividades = micontroladoralmacen.sacaactividades();
        numMaximoMaquinas = new int[numActividades];
        for (int i = 0; i < numActividades; i++) {
            numMaximoMaquinas[i] = ControladorProduccion.buscarMaquinasPorIdactividad(
                    listaActividades.get(i).getIdactividad()).size();
        }
        lblHorneado.setText("(max " + numMaximoMaquinas[0] + ")");
        txtHorneado.setText("" + numMaximoMaquinas[0]);
        lblRelleno.setText("(max " + numMaximoMaquinas[1] + ")");
        txtRelleno.setText("" + numMaximoMaquinas[1]);
        lblDecorado.setText("(max " + numMaximoMaquinas[2] + ")");
        txtDecorado.setText("" + numMaximoMaquinas[2]);
        lblEmpaque.setText("(max " + numMaximoMaquinas[3] + ")");
        txtEmpaque.setText("" + numMaximoMaquinas[3]);
        ////////////////////////
    }

    public void RefrescarTabla() {
        ((AbstractTableModel) tblTrabajadores.getModel()).fireTableDataChanged();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnSiguiente = new javax.swing.JButton();
        dpFechaInicio = new com.toedter.calendar.JDateChooser();
        dpFechaFin = new com.toedter.calendar.JDateChooser();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTrabajadores = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtHorneado = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtRelleno = new javax.swing.JTextField();
        txtDecorado = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtEmpaque = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        lblHorneado = new javax.swing.JLabel();
        lblRelleno = new javax.swing.JLabel();
        lblEmpaque = new javax.swing.JLabel();
        lblDecorado = new javax.swing.JLabel();
        btnDeseleccionar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Nueva Asignacion");

        jLabel7.setText("Fecha Inicio:");

        jLabel8.setText("Fecha Fin:");

        btnSiguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/next 2.png"))); // NOI18N
        btnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteActionPerformed(evt);
            }
        });

        dpFechaInicio.setDateFormatString("dd/MM/yyyy");
        dpFechaInicio.setMaxSelectableDate(dpFechaFin.getDate());
        dpFechaInicio.setMinSelectableDate(new java.util.Date());

        dpFechaFin.setDateFormatString("dd/MM/yyyy");
        dpFechaFin.setEnabled(false);
        dpFechaFin.setMaxSelectableDate(new java.util.Date(253370786477000L));
        dpFechaFin.setMinSelectableDate(dpFechaInicio.getDate());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Empleados"));

        tblTrabajadores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Codigo", "Nombres y Apellidos", "Seleccion"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTrabajadores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTrabajadoresMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblTrabajadores);

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 236, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Máquinas"));

        jLabel1.setText("Horneado");

        jLabel2.setText("Relleno");

        jLabel3.setText("Decorado");

        jLabel4.setText("Empaque");

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel1)
                    .add(jLabel2)
                    .add(jLabel3)
                    .add(jLabel4))
                .add(59, 59, 59)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(txtEmpaque, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(lblEmpaque, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 92, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(txtDecorado, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(lblDecorado, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 92, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(txtRelleno, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(lblRelleno, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 92, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(txtHorneado, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(lblHorneado, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 92, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(txtHorneado, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblHorneado, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(txtRelleno, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel2))
                    .add(lblRelleno, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(txtDecorado, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel3))
                    .add(lblDecorado, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(txtEmpaque, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel4))
                    .add(lblEmpaque, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(0, 0, Short.MAX_VALUE))
        );

        btnDeseleccionar.setText("Deseleccionar Todo");
        btnDeseleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeseleccionarActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(btnSiguiente, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(layout.createSequentialGroup()
                        .add(6, 6, 6)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(jLabel7)
                                .add(18, 18, 18)
                                .add(dpFechaInicio, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(27, 27, 27)
                                .add(jLabel8)
                                .add(18, 18, 18)
                                .add(dpFechaFin, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(0, 0, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(btnDeseleccionar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel7)
                    .add(dpFechaInicio, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(dpFechaFin, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel8))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnDeseleccionar)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 10, Short.MAX_VALUE)
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnSiguiente, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed

        try {
            // 1. Validar los datos (falta implementar)
            if (datosValidos()) {

                ArrayList<modelos.Asignacion> listaAsignaciones = ControladorProduccion.
                        buscarAsignacionesPorFechas(dpFechaInicio.getDate(), dpFechaFin.getDate());

                if (!listaAsignaciones.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Ya existen asignaciones registradas para las fechas seleccionadas");
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                    // 2. Obtener los datos del formulario
                    int[] numMaquinas = new int[numActividades];
                    numMaquinas[0] = Integer.parseInt(txtHorneado.getText());
                    numMaquinas[1] = Integer.parseInt(txtRelleno.getText());
                    numMaquinas[2] = Integer.parseInt(txtDecorado.getText());
                    numMaquinas[3] = Integer.parseInt(txtEmpaque.getText());

                    ArrayList<Empleado> empleados = new ArrayList<>();
                    for (int i = 0; i < listaEmpleados.size(); i++) {
                        if (listaSeleccion[i]) {
                            empleados.add(listaEmpleados.get(i));
                        }
                    }

                    // 3. Realizar la asignacion
                    Algoritmo.AsignacionAlgoritmo asignacionAlgoritmo = Algoritmo.ejecutar(dpFechaInicio.getDate(),
                            dpFechaFin.getDate(), empleados, numMaquinas);
                    if (asignacionAlgoritmo != null) {
                        // 4. Mostrar ventana con el detalle
                        for(int i=0;i<asignacionAlgoritmo.listaAsignacionxempleado.size();i++){
                            empleados.remove(asignacionAlgoritmo.listaAsignacionxempleado.get(i).getEmpleado());
                        }
                        AsignacionDetalle asignacionDetalle = new AsignacionDetalle(asignacionAlgoritmo.asignacion, asignacionAlgoritmo.listaAsignacionxempleado, asignacionAlgoritmo.listaAsignacionxproducto, true,empleados);
                        getDesktopPane().add(asignacionDetalle);
                        asignacionDetalle.setVisible(true);

                    } else {
                        // 4. Mostrar dialogo de error
                        JOptionPane.showMessageDialog(this, Algoritmo.getErrorMsg());
                    }
                }
            }
        } finally {
            this.setCursor(Cursor.getDefaultCursor());
        }

    }//GEN-LAST:event_btnSiguienteActionPerformed
    private void tblTrabajadoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTrabajadoresMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int row = tblTrabajadores.getSelectedRow();
            Empleado empleado = listaEmpleados.get(row);
            MuestraDesempeno muestraDesempeno = new MuestraDesempeno(empleado);
            getDesktopPane().add(muestraDesempeno);
            muestraDesempeno.setVisible(true);
        }
    }//GEN-LAST:event_tblTrabajadoresMouseClicked

    private void btnDeseleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeseleccionarActionPerformed
        if (seleccionarTodo) {
            for (int i = 0; i < listaSeleccion.length; i++) {
                listaSeleccion[i] = true;
            }
            btnDeseleccionar.setText("Deseleccionar Todo");
            seleccionarTodo = false;
        } else {
            for (int i = 0; i < listaSeleccion.length; i++) {
                listaSeleccion[i] = false;
            }
            btnDeseleccionar.setText("Seleccionar Todo");
            seleccionarTodo = true;
        }
        RefrescarTabla();
    }//GEN-LAST:event_btnDeseleccionarActionPerformed

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+");
    }

    private boolean datosValidos() {
        int cantidadTrabajadores = 0;
        for (int i = 0; i < listaSeleccion.length; i++) {
            if (listaSeleccion[i]) {
                cantidadTrabajadores++;
            }
        }
        if (dpFechaFin.getDate() != null && dpFechaInicio.getDate() != null && !txtHorneado.getText().equals("") && !txtDecorado.getText().equals("") && !txtEmpaque.getText().equals("")
                && !txtRelleno.getText().equals("") && isNumeric(txtHorneado.getText()) && isNumeric(txtDecorado.getText()) && isNumeric(txtEmpaque.getText()) && isNumeric(txtRelleno.getText()) && Integer.parseInt(txtHorneado.getText()) >= 0 && Integer.parseInt(txtDecorado.getText()) >= 0
                && Integer.parseInt(txtEmpaque.getText()) >= 0 && Integer.parseInt(txtRelleno.getText()) >= 0 && cantidadTrabajadores > 0
                && Integer.parseInt(txtHorneado.getText()) <= numMaximoMaquinas[0] && Integer.parseInt(txtRelleno.getText()) <= numMaximoMaquinas[1]
                && Integer.parseInt(txtDecorado.getText()) <= numMaximoMaquinas[2] && Integer.parseInt(txtEmpaque.getText()) <= numMaximoMaquinas[3]) {
            return true;
        } else {
            String error = "";
            if (dpFechaInicio.getDate() == null) {
                error += "en el campo FechaInicio, ";
            }
            if (dpFechaFin.getDate() == null) {
                error += "en el campo FechaFin, ";
            }
            if (txtHorneado.getText().equals("")) {
                error += "en el campo Horneado, ";
            } else {
                if (isNumeric(txtHorneado.getText())) {
                    if (Integer.parseInt(txtHorneado.getText()) < 0 || Integer.parseInt(txtHorneado.getText()) > numMaximoMaquinas[0]) {
                        error += "en el campo Horneado, ";
                    }
                } else {
                    error += "en el campo Horneado, ";
                }
            }
            if (txtRelleno.getText().equals("")) {
                error += "en el campo Relleno, ";
            } else {
                if (isNumeric(txtRelleno.getText())) {
                    if (Integer.parseInt(txtRelleno.getText()) < 0 || Integer.parseInt(txtRelleno.getText()) > numMaximoMaquinas[1]) {
                        error += "en el campo Relleno, ";
                    }
                } else {
                    error += "en el campo Relleno, ";
                }
            }
            if (txtDecorado.getText().equals("")) {
                error += "en el campo Decorado, ";
            } else {
                if (isNumeric(txtDecorado.getText())) {
                    if (Integer.parseInt(txtDecorado.getText()) < 0 || Integer.parseInt(txtDecorado.getText()) > numMaximoMaquinas[2]) {
                        error += "en el campo Decorado, ";
                    }
                } else {
                    error += "en el campo Decorado, ";
                }
            }
            if (txtEmpaque.getText().equals("")) {
                error += "en el campo Empaque, ";
            } else {
                if (isNumeric(txtEmpaque.getText())) {
                    if (Integer.parseInt(txtEmpaque.getText()) < 0 || Integer.parseInt(txtEmpaque.getText()) > numMaximoMaquinas[3]) {
                        error += "en el campo Empaque, ";
                    }
                } else {
                    error += "en el campo Empaque, ";
                }
            }
            if (cantidadTrabajadores == 0) {
                error += "debe seleccionar trabajadores. ";
            }
            error = error.substring(0, error.length() - 2);
            JOptionPane.showMessageDialog(null, "Corrija lo ingresado, " + error + ".");
            return false;
        }


        // falta implementar:
        //  -validacion de fechas
        //  -validacion de numero de maquinas (no pasarse del máximo)
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeseleccionar;
    private javax.swing.JButton btnSiguiente;
    private com.toedter.calendar.JDateChooser dpFechaFin;
    private com.toedter.calendar.JDateChooser dpFechaInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDecorado;
    private javax.swing.JLabel lblEmpaque;
    private javax.swing.JLabel lblHorneado;
    private javax.swing.JLabel lblRelleno;
    private javax.swing.JTable tblTrabajadores;
    private javax.swing.JTextField txtDecorado;
    private javax.swing.JTextField txtEmpaque;
    private javax.swing.JTextField txtHorneado;
    private javax.swing.JTextField txtRelleno;
    // End of variables declaration//GEN-END:variables
}
