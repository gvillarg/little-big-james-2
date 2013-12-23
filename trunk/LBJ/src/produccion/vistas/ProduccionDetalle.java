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
import modelos.Produccionxproducto;
import modelos.Productoxreceta;
import paquetevistas.Escritorio;

/**
 *
 * @author James
 */
public class ProduccionDetalle extends javax.swing.JInternalFrame {

    public static final int NUEVO = 0;
    public static final int EDITAR = 1;
    public static Escritorio escritorio;
    public static ProduccionDetalle desempenoTrabajador;
    private ArrayList<modelos.Actividad> actividades = ControladorProduccion.seleccionarActividades();
    private ArrayList<modelos.Turno> turnos = ControladorProduccion.seleccionarTurnos();
    private int actSeleccionada;
    private int turnoSeleccionado;
    private ArrayList<modelos.Produccionxproducto> listaProduccionxproducto;
    private modelos.Produccion prod;
    private ArrayList<modelos.Produccionxempleado> listaProduccionxempleado;
    private ArrayList<modelos.Produccionxempleado> listaProduccionxempleadoBuscados;
    private ArrayList<ArrayList<modelos.Productoxreceta>> listaRecetas;
    private int modo;
    private int tamListaProduccionxproducto;

    public ProduccionDetalle(modelos.Produccion produccion, ArrayList<modelos.Produccionxempleado> listaProduccionxempleado,
            ArrayList<modelos.Produccionxproducto> listaProduccionxproducto, int modo) {
        initComponents();

        this.listaProduccionxproducto = listaProduccionxproducto;
        tamListaProduccionxproducto = listaProduccionxproducto.size();
        this.prod = produccion;
        this.modo = modo;
        if (modo == NUEVO) {
            this.setTitle("Nueva Produccion");
        } else if (modo == EDITAR) {
            this.setTitle("Editar Produccion");
            btnGuardar.setEnabled(false);
        }

        this.listaProduccionxempleado = listaProduccionxempleado;
        this.listaProduccionxempleadoBuscados = new ArrayList<>();
        this.listaProduccionxempleadoBuscados.addAll(listaProduccionxempleado);

        boolean encontrado;
        listaRecetas = new ArrayList<>();
        ArrayList<modelos.Productoxreceta> listaProductoxreceta;
        ArrayList<modelos.Productoxreceta> listaOtrosProductos = new ArrayList<>();
        for (int i = 0; i < listaProduccionxproducto.size(); i++) {
            listaProductoxreceta = ControladorProduccion.buscarProductoxRecetaPorIdproducto(listaProduccionxproducto.get(i).
                    getAsignacionxproducto().getProducto().getIdproducto());
            for (int j = 0; j < listaProductoxreceta.size(); j++) {
                //buscar en primera lista
                encontrado = false;
                modelos.Productoxreceta productoxreceta = listaProductoxreceta.get(j);
                for (int k = 0; k < listaProduccionxproducto.size() && !encontrado; k++) {
                    if (listaProduccionxproducto.get(k).getAsignacionxproducto().getProducto().getIdproducto()
                            == productoxreceta.getProducto().getIdproducto()) {
                        encontrado = true;
                    }
                }
                //si no se encuentra buscar en segunda lista
                for (int k = 0; k < listaOtrosProductos.size() & !encontrado; k++) {
                    if (listaOtrosProductos.get(k).getProducto().getIdproducto()
                            == productoxreceta.getProducto().getIdproducto()) {
                        encontrado = true;
                    }
                }
                // Si tampoco se encuentra agregar a la lista de Otros Productos
                if (!encontrado) {
                    listaOtrosProductos.add(productoxreceta);
                }

            }
            listaRecetas.add(listaProductoxreceta);
        }

        modelos.Productoxreceta productoxreceta;
        modelos.Produccionxproducto produccionxproducto;
        for (int i = 0; i < listaOtrosProductos.size(); i++) {

            productoxreceta = listaOtrosProductos.get(i);
            produccionxproducto = new modelos.Produccionxproducto();
            produccionxproducto.setId(new modelos.ProduccionxproductoId());
            produccionxproducto.getId().setIdproducto(productoxreceta.getProducto().getIdproducto());
            produccionxproducto.setAsignacionxproducto(new modelos.Asignacionxproducto());
            produccionxproducto.getAsignacionxproducto().setProducto(productoxreceta.getProducto());

            listaProduccionxproducto.add(produccionxproducto);
        }

        tblProducciones.setModel(modeloTabla);
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
        if (produccion.getFecha() != null) {
            sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            String fechaI = sdf.format(produccion.getFecha());
            lblFecha.setText(fechaI);
        }
    }
    private AbstractTableModel modeloTabla = new AbstractTableModel() {
        private String[] cabeceras = {"Maquina", "Trabajador", "Actividad", "Turno", "Producto", "Produccion Estimada", "Merma Estimada", "Produccion Real", "Merma Real"};

        @Override
        public int getRowCount() {
            return listaProduccionxempleadoBuscados.size();
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
                return listaProduccionxempleadoBuscados.get(rowIndex).getAsignacionxempleado().getMaquina().getNombre();
            } else if (columnIndex == 1) {
                String nombreYApellidos = listaProduccionxempleadoBuscados.get(rowIndex).getAsignacionxempleado().getEmpleado().getNombre()
                        + " " + listaProduccionxempleadoBuscados.get(rowIndex).getAsignacionxempleado().getEmpleado().getAppaterno() + " "
                        + listaProduccionxempleadoBuscados.get(rowIndex).getAsignacionxempleado().getEmpleado().getApmaterno();
                return nombreYApellidos;
            } else if (columnIndex == 2) {
                return listaProduccionxempleadoBuscados.get(rowIndex).getAsignacionxempleado().getMaquina().getActividad().getNombre();
            } else if (columnIndex == 3) {
                return listaProduccionxempleadoBuscados.get(rowIndex).getAsignacionxempleado().getTurno().getNombre();
            } else if (columnIndex == 4) {
                return listaProduccionxempleadoBuscados.get(rowIndex).getAsignacionxempleado().getProducto().getNombre();
            } else if (columnIndex == 5) {
                return listaProduccionxempleadoBuscados.get(rowIndex).getAsignacionxempleado().getProduccionestimada();
            } else if (columnIndex == 6) {
                return listaProduccionxempleadoBuscados.get(rowIndex).getAsignacionxempleado().getMermaestimada();
            } else if (columnIndex == 7) {
                return listaProduccionxempleadoBuscados.get(rowIndex).getProduccion_1();
            } else if (columnIndex == 8) {
                return listaProduccionxempleadoBuscados.get(rowIndex).getMerma();
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

        @Override
        public boolean isCellEditable(int row, int col) {
            return (col == 7 || col == 8);
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if (columnIndex == 7) {
                modelos.Produccionxempleado produccionxempleado = listaProduccionxempleadoBuscados.get(rowIndex);
                int indiceProducto = 0;
                while (indiceProducto < listaProduccionxproducto.size() && listaProduccionxproducto.get(indiceProducto).getId().getIdproducto()
                        != produccionxempleado.getAsignacionxempleado().getProducto().getIdproducto()) {
                    indiceProducto++;
                }
                if (indiceProducto < listaProduccionxproducto.size()) {
                    modelos.Produccionxproducto produccionxproducto = listaProduccionxproducto.get(indiceProducto);
                    float diferencia = (float) aValue - produccionxempleado.getProduccion_1();
                    float stock = produccionxproducto.getAsignacionxproducto().getProducto().getStock() + diferencia;
                    produccionxproducto.getAsignacionxproducto().getProducto().setStock(stock);
                    float produc = produccionxproducto.getProduccion_1() + diferencia;
                    produccionxproducto.setProduccion_1(produc);

                    ArrayList<modelos.Productoxreceta> listaProductoxreceta = buscarReceta(produccionxproducto);
                    modelos.Productoxreceta productoxreceta;
                    for (int i = 0; i < listaProductoxreceta.size(); i++) {
                        productoxreceta = listaProductoxreceta.get(i);

                        //bucar producto
                        indiceProducto = 0;
                        while (indiceProducto < listaProduccionxproducto.size()
                                && listaProduccionxproducto.get(indiceProducto).getId().getIdproducto() != productoxreceta.getId().getIdproducto()) {
                            indiceProducto++;
                        }
                        if (indiceProducto < listaProduccionxproducto.size()) {
                            //reducir stock (stock=stock-cantidad*diferencia)
                            produccionxproducto = listaProduccionxproducto.get(indiceProducto);
                            stock = produccionxproducto.getAsignacionxproducto().getProducto().getStock() - diferencia * productoxreceta.getCantidad();
                            produccionxproducto.getAsignacionxproducto().getProducto().setStock(stock);
                        }
                    }
                }
                listaProduccionxempleadoBuscados.get(rowIndex).setProduccion_1((float) aValue);
                RefrescarTablaProductos();
            } else if (columnIndex == 8) {

                modelos.Produccionxempleado produccionxempleado = listaProduccionxempleadoBuscados.get(rowIndex);
                int indiceProducto = 0;
                while (indiceProducto < listaProduccionxproducto.size() && listaProduccionxproducto.get(indiceProducto).getId().getIdproducto()
                        != produccionxempleado.getAsignacionxempleado().getProducto().getIdproducto()) {
                    indiceProducto++;
                }
                if (indiceProducto < listaProduccionxproducto.size()) {
                    modelos.Produccionxproducto produccionxproducto = listaProduccionxproducto.get(indiceProducto);
                    float diferencia = (float) aValue - produccionxempleado.getMerma();
                    //float stock = produccionxproducto.getAsignacionxproducto().getProducto().getStock() + diferencia;
                    //produccionxproducto.getAsignacionxproducto().getProducto().setStock(stock);
                    float merma = produccionxproducto.getMerma() + diferencia;
                    produccionxproducto.setMerma(merma);

                    ArrayList<modelos.Productoxreceta> listaProductoxreceta = buscarReceta(produccionxproducto);
                    modelos.Productoxreceta productoxreceta;
                    for (int i = 0; i < listaProductoxreceta.size(); i++) {
                        productoxreceta = listaProductoxreceta.get(i);

                        //bucar producto
                        indiceProducto = 0;
                        while (indiceProducto < listaProduccionxproducto.size()
                                && listaProduccionxproducto.get(indiceProducto).getId().getIdproducto() != productoxreceta.getId().getIdproducto()) {
                            indiceProducto++;
                        }
                        if (indiceProducto < listaProduccionxproducto.size()) {
                            //reducir stock (stock=stock-cantidad*diferencia)
                            produccionxproducto = listaProduccionxproducto.get(indiceProducto);
                            float stock = produccionxproducto.getAsignacionxproducto().getProducto().getStock() - diferencia * productoxreceta.getCantidad();
                            produccionxproducto.getAsignacionxproducto().getProducto().setStock(stock);
                        }
                    }
                }

                listaProduccionxempleadoBuscados.get(rowIndex).setMerma((float) aValue);
                RefrescarTablaProductos();
            }
        }

        private ArrayList<Productoxreceta> buscarReceta(Produccionxproducto produccionxproducto) {
            int indice = listaProduccionxproducto.indexOf(produccionxproducto);
            if (indice != -1) {
                return listaRecetas.get(indice);
            } else {
                return null;
            }
        }
    };
    private AbstractTableModel modeloTabla2 = new AbstractTableModel() {
        private String[] cabeceras = {"Producto", "Cantidad Requerida", "Produccion Estimada", "Merma Estimada", "Produccion Real", "Merma Real", "Stock"};

        @Override
        public int getRowCount() {
            return listaProduccionxproducto.size();
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
                String nombre = listaProduccionxproducto.get(rowIndex).getAsignacionxproducto().getProducto().getNombre();
                return nombre;
            } else if (columnIndex == 1) {
                float cantidad = listaProduccionxproducto.get(rowIndex).getAsignacionxproducto().getCantidadrequerida();
                return cantidad;
            } else if (columnIndex == 2) {
                float cantidad = listaProduccionxproducto.get(rowIndex).getAsignacionxproducto().getProduccionestimada();
                return cantidad;
            } else if (columnIndex == 3) {
                float cantidad = listaProduccionxproducto.get(rowIndex).getAsignacionxproducto().getMermaestimada();
                return cantidad;
            } else if (columnIndex == 4) {
                return listaProduccionxproducto.get(rowIndex).getProduccion_1();
            } else if (columnIndex == 5) {
                return listaProduccionxproducto.get(rowIndex).getMerma();
            } else if (columnIndex == 6) {
                return listaProduccionxproducto.get(rowIndex).getAsignacionxproducto().getProducto().getStock();
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

        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        lblFecha = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cbTurno = new javax.swing.JComboBox();
        cbActividad = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        cards = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblProducciones = new javax.swing.JTable();
        btnCancelar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setTitle("Desempeno Real");

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
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 808, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        lblFecha.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lblFecha.setText("12/11/2013");

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/save.png"))); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar"));

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

        cards.setBorder(javax.swing.BorderFactory.createTitledBorder("Empleados"));
        cards.setLayout(new java.awt.CardLayout());

        tblProducciones.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tblProducciones);

        cards.add(jScrollPane3, "card2");

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error 1.png"))); // NOI18N
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1103, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(cards, javax.swing.GroupLayout.DEFAULT_SIZE, 1083, Short.MAX_VALUE)
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
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 599, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                    .addGap(24, 24, 24)
                    .addComponent(cards, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(21, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

        if (datosValidos()) {
            String mensajeError = null;
            ArrayList<modelos.Produccionxproducto> listaTemporal = new ArrayList<>();
            listaTemporal.addAll(listaProduccionxproducto.subList(0, tamListaProduccionxproducto));
            if (modo == NUEVO) {
                prod.setEstado(produccion.vistas.BuscarProduccion.ACTIVA);
                mensajeError = ControladorProduccion.guardarProduccion(prod, listaProduccionxempleado, listaTemporal);
            } else if (modo == EDITAR) {
                mensajeError = ControladorProduccion.actualizarProduccion(prod, listaProduccionxempleado, listaTemporal);
            }
            if (mensajeError == null) {
                if (Escritorio.buscarProduccion != null && Escritorio.buscarProduccion.isClosed() == false) {
                    Escritorio.buscarProduccion.ActualizarDatos();
                }
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, mensajeError);
            }

        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        listaProduccionxempleadoBuscados = new ArrayList<>();
        modelos.Produccionxempleado produccionxempleado;
        String nombreEmpleado = jTextField1.getText();
        for (int i = 0; i < listaProduccionxempleado.size(); i++) {
            produccionxempleado = listaProduccionxempleado.get(i);
            if (nombreEmpleado != null) {
                String nombreCompleto = produccionxempleado.getAsignacionxempleado().getEmpleado().getNombre()
                        + " " + produccionxempleado.getAsignacionxempleado().getEmpleado().getAppaterno() + " "
                        + produccionxempleado.getAsignacionxempleado().getEmpleado().getApmaterno();
                boolean esContenido = nombreCompleto.toLowerCase().contains(nombreEmpleado.toLowerCase());
                if (actSeleccionada != -1 && turnoSeleccionado != -1) {
                    if (esContenido && produccionxempleado.getAsignacionxempleado().getTurno().getIdturno() == turnos.get(turnoSeleccionado).getIdturno()
                            && produccionxempleado.getAsignacionxempleado().getMaquina().getActividad().getIdactividad() == actividades.get(actSeleccionada).getIdactividad()) {
                        listaProduccionxempleadoBuscados.add(produccionxempleado);
                    }
                } else if (actSeleccionada != -1 && turnoSeleccionado == -1) {
                    if (esContenido
                            && produccionxempleado.getAsignacionxempleado().getMaquina().getActividad().getIdactividad() == actividades.get(actSeleccionada).getIdactividad()) {
                        listaProduccionxempleadoBuscados.add(produccionxempleado);
                    }
                } else if (actSeleccionada == -1 && turnoSeleccionado != -1) {
                    if (esContenido && produccionxempleado.getAsignacionxempleado().getTurno().getIdturno() == turnos.get(turnoSeleccionado).getIdturno()) {
                        listaProduccionxempleadoBuscados.add(produccionxempleado);
                    }
                } else if (actSeleccionada == -1 && turnoSeleccionado == -1) {
                    if (esContenido) {
                        listaProduccionxempleadoBuscados.add(produccionxempleado);
                    }
                }
            } else {
                if (actSeleccionada != -1 && turnoSeleccionado != -1) {
                    if (produccionxempleado.getAsignacionxempleado().getTurno().getIdturno() == turnos.get(turnoSeleccionado).getIdturno()
                            && produccionxempleado.getAsignacionxempleado().getMaquina().getActividad().getIdactividad() == actividades.get(actSeleccionada).getIdactividad()) {
                        listaProduccionxempleadoBuscados.add(produccionxempleado);
                    }
                } else if (actSeleccionada == -1 && turnoSeleccionado != -1) {
                    if (produccionxempleado.getAsignacionxempleado().getTurno().getIdturno() == turnos.get(turnoSeleccionado).getIdturno()) {
                        listaProduccionxempleadoBuscados.add(produccionxempleado);
                    }
                } else if (actSeleccionada != -1 && turnoSeleccionado == -1) {
                    if (produccionxempleado.getAsignacionxempleado().getMaquina().getActividad().getIdactividad() == actividades.get(actSeleccionada).getIdactividad()) {
                        listaProduccionxempleadoBuscados.add(produccionxempleado);
                    }
                } else if (actSeleccionada == -1 && turnoSeleccionado == -1) {
                    listaProduccionxempleadoBuscados.add(produccionxempleado);
                }
            }
        }
        RefrescarTablaProducciones();
    }//GEN-LAST:event_btnBuscarActionPerformed

    public void RefrescarTablaProducciones() {
        ((AbstractTableModel) tblProducciones.getModel()).fireTableDataChanged();
    }

    public void RefrescarTablaProductos() {
        ((AbstractTableModel) tblProductos.getModel()).fireTableDataChanged();
    }

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
       if(JOptionPane.showConfirmDialog(null, "¿Está seguro de cancelar la actualización de datos",
            "Mensaje",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
        this.dispose();
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void cbActividadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbActividadActionPerformed
        actSeleccionada = cbActividad.getSelectedIndex() - 1;
    }//GEN-LAST:event_cbActividadActionPerformed

    private void cbTurnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTurnoActionPerformed
        turnoSeleccionado = cbTurno.getSelectedIndex() - 1;
    }//GEN-LAST:event_cbTurnoActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JPanel cards;
    private javax.swing.JComboBox cbActividad;
    private javax.swing.JComboBox cbTurno;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JTable tblProducciones;
    private javax.swing.JTable tblProductos;
    // End of variables declaration//GEN-END:variables

    private boolean datosValidos() {
        boolean valoresPositivos = true;
        boolean stockPositivo = true;

        int indice = 0;
        while (valoresPositivos && indice < listaProduccionxempleado.size()) {
            if (listaProduccionxempleado.get(indice).getMerma() < 0
                    || listaProduccionxempleado.get(indice).getProduccion_1() < 0) {
                valoresPositivos = false;
            }
            indice++;
        }

        indice = 0;
        while (stockPositivo && indice < listaProduccionxproducto.size()) {
            if (listaProduccionxproducto.get(indice).getAsignacionxproducto().getProducto().getStock() < 0) {
                stockPositivo = false;
            }
            indice++;
        }

        String mensajeError = null;
        if (!valoresPositivos) {
            mensajeError = "Debe ingresar datos positivos";
        }
        if (!stockPositivo) {
            if (mensajeError == null) {
                mensajeError = "Debe asegurarse que el stock sea positivo";

            } else {
                mensajeError += " y asegurarse que el stock sea positivo";
            }
        }

        if (mensajeError != null) {
            JOptionPane.showMessageDialog(this, mensajeError);
        }
        return valoresPositivos && stockPositivo;
    }
}
