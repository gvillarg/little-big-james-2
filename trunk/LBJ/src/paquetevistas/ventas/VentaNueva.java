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
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import modelos.Cliente;
import modelos.Producto;
import modelos.Venta;
import static paquetevistas.ventas.Clientes.controllerClientes;

/**
 *
 * @author alulab11
 */
public class VentaNueva extends javax.swing.JInternalFrame implements ClientReadyWindow {

    public static Ventas ventas;
    public static BuscarCliente buscarCliente;
    public static Cliente cliente;
    public static BuscarProducto buscarProducto;
    public static Producto producto;
    public static ControllerVentas controllerVentas = new ControllerVentas();
    TablaProductos tablaProductosC = new TablaProductos();
    public static Validacion validacion = new Validacion();
    public static String cotizar = null;
    
    class TablaProductos extends AbstractTableModel {

        List<ProductoC> productos = new ArrayList<ProductoC>();
        List<ProductoC> productos2 = new ArrayList<ProductoC>();
        String[] titles = {"Codigo", "Producto", "Cantidad", "Stock", "Precio Und", "Fecha Cotiz"};

        public TablaProductos() {
            
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

        public Object getValueAt(int rowIndex, int columnIndex) {
            ProductoC productoC = productos.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return productoC.getIdProducto();
                case 1:
                    return productoC.getNombre();
                case 2:
                    return productoC.getCantidad();
                case 3:
                    return productos2.get(rowIndex).getCantidad();
                case 4:
                    return productoC.getPrecioUnd();
            }
            return null;
        }

        public String getColumnName(int col) {
            return titles[col];
        }
        public void modify(Producto p, float cantidad, float precio) {
            ProductoC productoC = find(p);
            ProductoC productoC2 = find2(p);
            if (productoC != null) {
                productoC.setCantidad(cantidad);
                productoC.setPrecioUnd(precio);
            } else {
                productoC = new ProductoC(p,cantidad,precio);
                productos.add(productoC);
            }
            if (productoC2 != null) {
            } else {
                float stock = p.getStock();
                float stockComprometido = p.getStockcomprometido();
                float stockProducir = p.getStocksolicitado(); // Stock a producir?
                float cantidad2 = stock - stockComprometido;
                productoC2 = new ProductoC(p,cantidad2,0);
                productos2.add(productoC2);
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
        public ProductoC find2(Producto p) {
            int j = productos2.size();
            for (int i=0; i<j; i++) {
                ProductoC productoC = productos2.get(i);
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
        public String cotizar(int type) {
            int j = productos.size();
            String fechaCotizada = "";
            long fecha = System.currentTimeMillis();
            
            Date cotizada = new Date();
            cotizada.setTime(System.currentTimeMillis());
            if (type == 1) {
                cotizada.setTime(System.currentTimeMillis() + 1000*60*60*24);
            } else {
                ControllerOrdenes controllerOrdenes = new ControllerOrdenes();
                cotizada.setTime(controllerOrdenes.getLastOrdenFecha().getTime() + 1000*60*60*24);
            }
            for (int i=0; i<j; i++) {
                ProductoC productoC = productos.get(i);
                productoC.setFechaCotizada(cotizada);
            }
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/YYYY");
            fechaCotizada = sdf.format(cotizada).toString();
            refresh();
            return fechaCotizada.toString();
        }
        public List<ProductoC> asList() {
            return productos;
        }
        public void changeVentas() {
            int j = productos.size();
            for (int i=0; i<j; i++) {
                float cant1 = productos.get(i).getCantidad();
                float cant2 = productos2.get(i).getCantidad();
                if (cant2 < cant1) {
                    cant1 = cant2;
                }
                productos.get(i).setCantidad(cant1);
            }
        }
        public boolean checkVentas() {
            int j = productos.size();
            for (int i=0; i<j; i++) {
                if (productos.get(i).getCantidad() > productos2.get(i).getCantidad()) {
                    return false;
                }
            }
            return true;
        }
        public ProductoC getInfoProducto(Producto p) {
            return find(p);
        }

        private void removeProducto(int index) {
            productos.remove(index);
            productos2.remove(index);
            refresh();
        }
        
    }
    public VentaNueva(Ventas v) {
        this.ventas = v;
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
            this.jTextField5.setText(String.valueOf(p.getPrecioUnd()));
        } else {
            this.jTextField2.setText("");
            this.jTextField5.setText("");
        }
    }
    public void changeVentas() {
        ((TablaProductos)this.jTable1.getModel()).changeVentas();
        this.tablaProductosC.fireTableChanged(null);
    }
    public boolean checkVentas() {
        return ((TablaProductos)this.jTable1.getModel()).checkVentas();
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
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jTextField3 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setTitle("Venta Nueva");
        setPreferredSize(new java.awt.Dimension(530, 530));

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

        jButton1.setText("Buscar Cliente");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Cliente:");

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/save.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel7.setText("Guardar");

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Cotizar");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/search.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextField3.setEditable(false);

        jLabel6.setText("Monto (S/.):");

        jTextField4.setEditable(false);
        jTextField4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField4MouseClicked(evt);
            }
        });
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField4KeyPressed(evt);
            }
        });

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

        jButton5.setText("Aceptar");
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

        jLabel4.setText("Precio Und:");

        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton5)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4)))
                .addContainerGap())
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
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel9.setText("Igv (S/.):");

        jTextField6.setEditable(false);

        jLabel10.setText("Total (S/.):");

        jTextField7.setEditable(false);

        jLabel5.setText("Fecha Cotizada:");

        jTextField8.setEditable(false);

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error 1.png"))); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Cancelar");

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/minus.png"))); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Quitar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jButton1))
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(111, 111, 111)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton8))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(10, 10, 10)
                                            .addComponent(jLabel7)
                                            .addGap(16, 16, 16)))
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton7)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addGap(47, 47, 47)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextField3)
                                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(93, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton7)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(13, 13, 13)
                        .addComponent(jLabel7))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton3)
                            .addComponent(jButton8))
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel12))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (buscarCliente == null || buscarCliente.isClosed()) {
            buscarCliente = new BuscarCliente(this,jTextField4);
            getDesktopPane().add(buscarCliente);
            buscarCliente.setVisible(true);
        } else {
            try {
                buscarCliente.setSelected(true);
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String fechaCotizada = "";
        if (!checkVentas()) {
                int option = JOptionPane.showConfirmDialog(rootPane,"Desea reducir la venta a los productos disponibles y "
                        + " despacharlos antes?",
                "Mensaje",JOptionPane.YES_NO_CANCEL_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    changeVentas();
                    fechaCotizada = this.tablaProductosC.cotizar(1); // Cotiza para mañana
                } else if (option == JOptionPane.NO_OPTION) {
                    fechaCotizada = this.tablaProductosC.cotizar(2); // Cotiza para mañana
                } else if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                }
        } else {
            fechaCotizada = this.tablaProductosC.cotizar(1); // Cotiza para el siguiente mes
        }
        this.cotizar = String.valueOf(fechaCotizada);
        this.jTextField8.setText(String.valueOf(fechaCotizada));
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (this.cotizar == null) {
            JOptionPane.showMessageDialog(null, "Debe cotizar antes de guardar la venta.");
        } else if (this.cliente == null) {
            JOptionPane.showMessageDialog(null, "Debe elegir un cliente antes de guardar la venta.");
        } else if (validacion.es_vacio(this.jTextField3.getText())) {
            JOptionPane.showMessageDialog(null, "Debe elegir productos antes de guardar la venta.");
        } else {
            Venta v = this.controllerVentas.getCreateVenta(this.cliente,this.tablaProductosC.asList(),
            this.tablaProductosC.getMontoTotal());
            this.ventas.refresh();
            JOptionPane.showMessageDialog(null, "La venta se registró existosamente.");
            Factura factura = new Factura(v);
            getDesktopPane().add(factura);
            factura.setVisible(true);
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
        } else if (validacion.es_vacio(this.jTextField5.getText())) {
            JOptionPane.showMessageDialog(null, validacion.msj + "Precio");
        } else if (!validacion.es_numero_real(this.jTextField5.getText())) {
            JOptionPane.showMessageDialog(null, validacion.msj);
        } else if (this.producto == null) {
            validacion.es_vacio("");
            JOptionPane.showMessageDialog(null, validacion.msj + "Producto");
        } else if (this.producto != null) {
            this.jTextField2.setText(toNum(this.jTextField2.getText()));
            this.jTextField5.setText(toNum(this.jTextField5.getText()));
            tablaProductosC.modify(producto,Float.parseFloat(this.jTextField2.getText()),
                    Float.parseFloat(this.jTextField5.getText()));
            refresh();
            this.cotizar = null;
            this.jTextField8.setText("");
            this.jTextField3.setText(toNum(String.valueOf(tablaProductosC.getMontoTotal())));
            this.jTextField6.setText(toNum(String.valueOf(tablaProductosC.getMontoTotal()*(controllerVentas.getIgv()/100))));
            this.jTextField7.setText(toNum(String.valueOf(tablaProductosC.getMontoTotal()*(1+controllerVentas.getIgv()/100))));
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
            buscarProducto = new BuscarProducto(this,jTextField1);
            getDesktopPane().add(buscarProducto);
            buscarProducto.setVisible(true);
        } else {
            try {
                buscarProducto.setSelected(true);
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jTextField1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MouseClicked
        this.jTextField1.setText("");
        this.jTextField2.setText("");
        this.jTextField5.setText("");
        this.producto = null;
    }//GEN-LAST:event_jTextField1MouseClicked

    private void jTextField4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4KeyPressed

    private void jTextField4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField4MouseClicked
        this.jTextField4.setText("");
        this.cliente = null;
    }//GEN-LAST:event_jTextField4MouseClicked

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if(JOptionPane.showConfirmDialog(null, "¿Está seguro de cancelar el registro?",
                "Mensaje",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            this.dispose();
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        int index = this.jTable1.getSelectedRow();
        if (index != -1) {
            ((TablaProductos)this.jTable1.getModel()).removeProducto(index);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    // End of variables declaration//GEN-END:variables
}
