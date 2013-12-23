/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paquetevistas.compra;

import Seguridad.Controlador.Validacion;
import Seguridad.Controlador.controladorseguridad;
import controlador.almacen.controladoralmacen;
import controlador.compra.controladorcompra;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import modelos.Producto;
import modelos.Productoxproveedor;
import modelos.Proveedor;
import modelos.Requirimientocompra;
import modelos.Usuario;

/**
 *
 * @author Jacklin
 */
public class NuevaCompraLibre extends javax.swing.JInternalFrame {

    public static controladorcompra mycontroladorcompra = new controladorcompra();
    public static controladoralmacen mycontroladoralmacen = new controladoralmacen();
    public static Validacion validacion = new Validacion();
    public static controladorcompra controladorCompra = new controladorcompra();
    RequerimentxProdTableModel tablareqxprov = new RequerimentxProdTableModel();

    //Para el registro de Log
    public static Usuario usuario = lbj.LBJ.Login.usuario;
    public static controladorseguridad mycontroladorseguridad = new controladorseguridad();

    /**
     * Creates new form NuevaCompraLibre
     */
    public NuevaCompraLibre() {
        initComponents();
        Date hoy = new Date();
        jFechaAcordada.setMinSelectableDate(hoy);
        loadComponents();
        jTablaProveedores.setModel(tablareqxprov);
    }

    class RequerimentxProdTableModel extends AbstractTableModel {

        List<Proveedor> proveedores = new ArrayList<Proveedor>();
        List<Float> cantidades = new ArrayList<Float>();
        List<Float> precios = new ArrayList<Float>();
        List<Producto> productos = new ArrayList<Producto>();

        String[] titles = {"Proveedor", "Producto", "Cantidad a cubrir", "Precio (S/.)"};

        public RequerimentxProdTableModel() {

        }

        public void refresh() {
            this.fireTableDataChanged();
        }

        public void agregar(Proveedor proveedor, float cantidad, float precio, Producto producto, RequerimentxProdTableModel auxtable, float cantmax) {
            int row = getRowCount();
            int esta = 0;
            for (int i = 0; i < row; i++) {
                if (proveedor.getRazonsocial().equalsIgnoreCase(getValueAt(i, 0).toString()) && producto.getNombre().equalsIgnoreCase(getValueAt(i, 1).toString())) {
                    esta = 1;
                    float cantactual = Float.parseFloat(getValueAt(i, 2).toString());
                    Float cantidadaux = cantactual + cantidad;
                    if (cantidadaux > cantmax) {
                        float aux = cantmax - cantactual;
                        JOptionPane.showMessageDialog(null, "Se excedió la cantidad que provee el proveedor, solo puede proveer " + aux);
                    } else {
                        this.cantidades.set(i, cantidadaux);
                    }
                    break;
                }
            }
            if (esta == 0) {
                this.proveedores.add(proveedor);
                this.cantidades.add(cantidad);
                this.precios.add(precio);
                this.productos.add(producto);
            }
            refresh();
        }

        @Override
        public int getRowCount() {
            return proveedores.size();
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            String res = null;
            Proveedor proveedor = proveedores.get(rowIndex);
            float cantidad = cantidades.get(rowIndex);
            float precio = precios.get(rowIndex);
            Producto producto = productos.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    res = proveedor.getRazonsocial();
                    break;
                case 1:
                    res = producto.getNombre();
                    break;
                case 2:
                    res = "" + cantidad;
                    break;
                case 3:
                    res = "" + precio;
                    break;
            }
            return res;
        }

        public String getColumnName(int col) {
            return titles[col];
        }

        private void restore(int row) {
            this.proveedores.remove(row);
            this.cantidades.remove(row);
            this.precios.remove(row);
            this.productos.remove(row);
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
        jComboProducto = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jComboProveedor = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jCantidadMaximaProveedor = new javax.swing.JTextField();
        jCantidadACubrir = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jFechaAcordada = new com.toedter.calendar.JDateChooser();
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
        setTitle("Compra Libre");

        jLabel1.setText("Producto:");

        jComboProducto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione un producto" }));
        jComboProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboProductoMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jComboProductoMousePressed(evt);
            }
        });
        jComboProducto.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboProductoItemStateChanged(evt);
            }
        });

        jLabel2.setText("Proveedor:");

        jComboProveedor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione un proveedor" }));
        jComboProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboProveedorMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jComboProveedorMousePressed(evt);
            }
        });
        jComboProveedor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboProveedorItemStateChanged(evt);
            }
        });

        jLabel3.setText("Cantidad máx del proveedor:");

        jCantidadMaximaProveedor.setEditable(false);
        jCantidadMaximaProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCantidadMaximaProveedorActionPerformed(evt);
            }
        });

        jLabel4.setText("Cantidad a cubrir:");

        jLabel5.setText("Fecha Acordada:");

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jCantidadACubrir)
                            .addComponent(jComboProducto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboProveedor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCantidadMaximaProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(jFechaAcordada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(61, 61, 61))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboProveedor)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jCantidadMaximaProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCantidadACubrir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFechaAcordada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jLayeredPane1.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jComboProducto, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jComboProveedor, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jCantidadMaximaProveedor, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jCantidadACubrir, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jFechaAcordada, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLayeredPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("Proveedores Seleccionados"));

        jTablaProveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Proveedor", "Producto", "Cantidad a cubrir", "Precio (S/.)"
            }
        ));
        jScrollPane1.setViewportView(jTablaProveedores);

        javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jLayeredPane2Layout.setVerticalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        jLayeredPane2.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/limpiar.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jLayeredPane3.add(jButton2);
        jButton2.setBounds(320, 10, 59, 40);

        jLabel11.setText("Limpiar");
        jLayeredPane3.add(jLabel11);
        jLabel11.setBounds(330, 60, 70, 14);

        jLabel9.setText("Guardar");
        jLayeredPane3.add(jLabel9);
        jLabel9.setBounds(230, 60, 60, 14);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/save.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jLayeredPane3.add(jButton1);
        jButton1.setBounds(220, 10, 65, 41);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/plus.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jLayeredPane3.add(jButton3);
        jButton3.setBounds(20, 10, 63, 40);

        jLabel10.setText("Agregar");
        jLayeredPane3.add(jLabel10);
        jLabel10.setBounds(30, 60, 70, 14);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/minus.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jLayeredPane3.add(jButton4);
        jButton4.setBounds(120, 10, 63, 40);

        jLabel8.setText("Quitar");
        jLayeredPane3.add(jLabel8);
        jLabel8.setBounds(140, 60, 40, 14);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error 1.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jLayeredPane3.add(jButton5);
        jButton5.setBounds(410, 10, 60, 40);

        jLabel12.setText("Cancelar");
        jLayeredPane3.add(jLabel12);
        jLabel12.setBounds(420, 60, 60, 14);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1)
            .addComponent(jLayeredPane2)
            .addComponent(jLayeredPane3)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLayeredPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCantidadMaximaProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCantidadMaximaProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCantidadMaximaProveedorActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jComboProducto.setSelectedIndex(0);
        jComboProveedor.setSelectedIndex(0);
        jCantidadMaximaProveedor.setText("");
        jCantidadACubrir.setText("");
        jFechaAcordada.setDate(null);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (jFechaAcordada.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Debe colocar una fecha de entrega");
        } else {

            if (tablareqxprov.getRowCount() <= 0) {
                JOptionPane.showMessageDialog(null, "No ha seleccionado ningún proveedor a cubrir la compra");
            } else {
                mycontroladoralmacen.guardaOrdenCompra();
                int guardo = mycontroladorcompra.guardaDetalleOrdenCompraLibre(tablareqxprov.productos, tablareqxprov.cantidades);
                if (guardo == 1) {
                    Requirimientocompra req = mycontroladoralmacen.buscaUltimaOrden();
                    mycontroladorcompra.guardaCompraLibre(tablareqxprov.productos, tablareqxprov.cantidades, tablareqxprov.proveedores, req, jFechaAcordada.getDate());
                    lbj.LBJ.Login.escritorio.compra.refreshPurchaseTable();
                    JOptionPane.showMessageDialog(this, "La compra se ingresó correctamente");

                    mycontroladorseguridad.insertarlog(usuario, new Date(), "Insertar", "Compra", "Registrar Compra");
                    this.dispose();
                }

            }

        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        int row = jComboProveedor.getSelectedIndex();
        if (row > 0) {
            if (validacion.es_vacio(jCantidadACubrir.getText())) {
                JOptionPane.showMessageDialog(this, validacion.msj + "cantidad a cubrir la compra");
            } else if (!validacion.es_numero_real(jCantidadACubrir.getText())) {
                JOptionPane.showMessageDialog(this, validacion.msj);
            } else {
                Float numcantidad = Float.parseFloat(jCantidadACubrir.getText());
                Float cantMaxProv = Float.parseFloat(jCantidadMaximaProveedor.getText());
                if (jCantidadACubrir.getText().equalsIgnoreCase("0") || (numcantidad < 0)) {
                    JOptionPane.showMessageDialog(this, "Debe colocar una cantidad mayor a 0 del producto a comprar");
                } else if ((numcantidad > cantMaxProv)) {
                    JOptionPane.showMessageDialog(this, "Debe colocar una cantidad menor o igual a la requerida y a la que se puede proveer");
                } else {
                    Proveedor proveedor = controladorCompra.sacaproveedorbyrazon(jComboProveedor.getSelectedItem().toString());
                    Producto producto = mycontroladoralmacen.sacaproductobynombre(jComboProducto.getSelectedItem().toString());
                    Productoxproveedor prodxprov = controladorCompra.sacaproductoxproveedor(proveedor.getIdproveedor(), producto.getIdproducto());
                    tablareqxprov.agregar(proveedor, numcantidad, prodxprov.getPrecio(), producto, tablareqxprov, cantMaxProv);
                    jComboProducto.setSelectedIndex(0);
                    jComboProveedor.setSelectedIndex(0);
                    jCantidadMaximaProveedor.setText("");
                    jCantidadACubrir.setText("");
                }
            }

        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un proveedor");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int row = jTablaProveedores.getSelectedRow();
        if (row >= 0) {
            this.tablareqxprov.restore(row);
            this.tablareqxprov.refresh();
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila de la tabla");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jComboProductoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboProductoItemStateChanged
        // TODO add your handling code here:

        int row = jComboProducto.getSelectedIndex();
        if (row > 0) {
            jComboProveedor.removeAllItems();
            String producto = jComboProducto.getSelectedItem().toString();
            int idprod = mycontroladoralmacen.sacaproductobynombre(producto).getIdproducto();
            List<Proveedor> proveedores = mycontroladorcompra.sacaproveedoresbyproducto(idprod);
            jComboProveedor.addItem("Seleccione un proveedor");
            for (int i = 0; i < proveedores.size(); i++) {
                jComboProveedor.addItem(proveedores.get(i).getRazonsocial());
            }
            jCantidadMaximaProveedor.setText("");
            jComboProveedor.setSelectedIndex(0);
            jCantidadACubrir.setText("");
            jComboProveedor.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    int row = jComboProveedor.getSelectedIndex();
                    if (row > 0) {
                        String nomprob = jComboProveedor.getSelectedItem().toString();
                        int idprov = mycontroladorcompra.sacaproveedorbyrazon(nomprob).getIdproveedor();
                        String producto = jComboProducto.getSelectedItem().toString();
                        int idprod = mycontroladoralmacen.sacaproductobynombre(producto).getIdproducto();
                        Productoxproveedor prodxprov = mycontroladorcompra.sacaproductoxproveedor(idprov, idprod);
                        jCantidadMaximaProveedor.setText(prodxprov.getCantidadmaxima().toString());
                        jCantidadACubrir.setText(jCantidadMaximaProveedor.getText());
                    } else {
                        jCantidadMaximaProveedor.setText("");
                        jCantidadACubrir.setText("");
                    }
                }

            });
        } else {
            jComboProveedor.removeAllItems();
            jComboProveedor.addItem("Seleccione un proveedor");
            jComboProveedor.setSelectedIndex(0);
            jCantidadMaximaProveedor.setText("");
            jCantidadACubrir.setText("");
        }

    }//GEN-LAST:event_jComboProductoItemStateChanged

    private void jComboProductoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboProductoMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboProductoMousePressed

    private void jComboProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboProductoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboProductoMouseClicked

    private void jComboProveedorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboProveedorItemStateChanged
    }//GEN-LAST:event_jComboProveedorItemStateChanged

    private void jComboProveedorMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboProveedorMousePressed
    }//GEN-LAST:event_jComboProveedorMousePressed

    private void jComboProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboProveedorMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboProveedorMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        if (JOptionPane.showConfirmDialog(null, "¿Está seguro de cancelar el registro?",
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
    private javax.swing.JTextField jCantidadMaximaProveedor;
    private javax.swing.JComboBox jComboProducto;
    private javax.swing.JComboBox jComboProveedor;
    private com.toedter.calendar.JDateChooser jFechaAcordada;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTablaProveedores;
    // End of variables declaration//GEN-END:variables

    private void loadComponents() {
        List<Producto> productos = new ArrayList<Producto>();
        productos = mycontroladoralmacen.sacaproductos();
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getEstado() == 1 && (productos.get(i).getTipoproducto().getIdtipoproducto() == 1 || productos.get(i).getTipoproducto().getIdtipoproducto() == 2)) {
                jComboProducto.addItem(productos.get(i).getNombre());
            }
        }
        jComboProducto.setSelectedIndex(0);
    }
}
