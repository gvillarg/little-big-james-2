/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paquetevistas.configuracion;

import Seguridad.Controlador.controladorseguridad;
import controlador.almacen.controladoralmacen;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import modelos.Actividad;
import modelos.Producto;
import modelos.Productoxreceta;
import modelos.ProductoxrecetaId;
import modelos.Tipoproducto;
import modelos.Usuario;
import paquetevistas.Escritorio;
import paquetevistas.almacen.Productos;

/**
 *
 * @author mariofcandia
 */
public class RecetaNueva extends javax.swing.JInternalFrame {

    /**
     * Creates new form RecetaNueva
     */
    public static Receta receta;
    public static Productos producto;
    public static controladoralmacen mycontroladoralmacen = new controladoralmacen();
    ProductTableModel myProductTableModel = new ProductTableModel();
    ProductTableModel2 myProductTableModel2 = new ProductTableModel2();
    
    //Para el registro de Log
    public static Usuario usuario = lbj.LBJ.Login.usuario;
    public static controladorseguridad mycontroladorseguridad = new controladorseguridad();
    
    //List<Producto> pro2 = new ArrayList<Producto>();
    //ArrayList<String> array3 = new ArrayList<String>();
    //ArrayList<String> lista3 = new ArrayList<String>();
    //List<Producto> array2 = new ArrayList<Producto>();

    class ProductTableModel extends AbstractTableModel {

        List<Producto> pro = new ArrayList<Producto>();

        public ProductTableModel() {
            pro = mycontroladoralmacen.sacaproductosmp();
        }
        String[] titles = {"Nombre", "Tipo", "Unidad"};

        public int getRowCount() {
            return pro.size();
        }

        public int getColumnCount() {
            return 3;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            String res = null;
            int tipo;
            int unidad;
            String unidadnombre = null;
            String nombre = null;
            Producto p = pro.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    res = "" + p.getNombre();
                    break;
                case 1:
                    tipo = p.getTipoproducto().getIdtipoproducto();
                    nombre = mycontroladoralmacen.sacanombretipo(tipo);
                    res = nombre;
                    break;
                case 2:
                    unidad = p.getUnidadmedida().getIdunidadmedida();
                    unidadnombre = mycontroladoralmacen.sacanombreunidad(unidad);
                    res = unidadnombre;
                    break;
            }
            return res;
        }

        public String getColumnName(int col) {
            return titles[col];
        }
    }

    class ProductTableModel2 extends AbstractTableModel {

        List<Producto> pro2 = new ArrayList<Producto>();
        ArrayList<String> lista3 = new ArrayList<String>();
        //ArrayList<String> array3 = new ArrayList<String>();

        public ProductTableModel2() {
            Producto prod = lbj.LBJ.Login.escritorio.producto.productoareceta;
            if (prod == null) {
                prod = lbj.LBJ.Login.escritorio.actividades.actividadnueva.productoareceta;
            }
            String nombre = prod.getNombre();

            int verificaexistencia = mycontroladoralmacen.verificaexistenciareceta(nombre);
            if (verificaexistencia == 0) {
                pro2 = mycontroladoralmacen.sacaproductosdereceta(200);
                lista3 = mycontroladoralmacen.sacacantidadreceta(200);
            } else {
                modelos.Receta receta = mycontroladoralmacen.sacaidreceta(nombre);
                pro2 = mycontroladoralmacen.sacaproductosdereceta(receta.getIdreceta());
                lista3 = mycontroladoralmacen.sacacantidadreceta(receta.getIdreceta());
            }
        }
        String[] titles = {"Nombre", "Tipo", "Cantidad", "Unidad"};

        public int getRowCount() {
            return pro2.size();
        }

        public int getColumnCount() {
            return 4;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            String res = null;
            int tipo;
            int unidad;
            String unidadnombre = null;
            String nombre = null;
            Producto p = pro2.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    res = "" + p.getNombre();
                    break;
                case 1:
                    tipo = p.getTipoproducto().getIdtipoproducto();
                    nombre = mycontroladoralmacen.sacanombretipo(tipo);
                    res = nombre;
                    break;
                case 2:
                    res = lista3.get(rowIndex);
                    ;
                    break;
                case 3:
                    unidad = p.getUnidadmedida().getIdunidadmedida();
                    unidadnombre = mycontroladoralmacen.sacanombreunidad(unidad);
                    res = unidadnombre;
                    break;
            }
            return res;
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            if (columnIndex == 2) {
                return true;
            }
            return false;
        }

        public void setValueAt(Object value, int row, int col) {
            String cadena = value.toString();
            lista3.set(row, cadena);
        }

        public String getColumnName(int col) {
            return titles[col];
        }
    }

    public RecetaNueva() {
        initComponents();
        loadComponents();
        jTable2.setModel(myProductTableModel);
        jTable1.setModel(myProductTableModel2);
    }

    public void loadComponents() {
        Producto product = lbj.LBJ.Login.escritorio.producto.productoareceta;
        if (product == null) {
            product = lbj.LBJ.Login.escritorio.actividades.actividadnueva.productoareceta;
        }
        jTextField1.setText(product.getNombre());
        jComboBox1.removeAllItems();
        List<Tipoproducto> tiposproductos = new ArrayList<Tipoproducto>();
        tiposproductos = mycontroladoralmacen.sacatipoproducto();
        if (product.getTipoproducto().getIdtipoproducto() == 3) {
            for (int i = 0; i < tiposproductos.size() - 1; i++) {
                jComboBox1.addItem(tiposproductos.get(i).getNombre());
            }
            jComboBox1.setSelectedIndex(0);
        } else {
            for (int i = 0; i < tiposproductos.size() - 1; i++) {
                jComboBox1.addItem(tiposproductos.get(i).getNombre());
            }
            jComboBox1.setSelectedIndex(0);
        }
        jComboBox2.removeAllItems();
        List<Actividad> actividades = new ArrayList<Actividad>();
        actividades = mycontroladoralmacen.sacaactividades();
        for (int j = 0; j < actividades.size(); j++) {
            jComboBox2.addItem(actividades.get(j).getNombre());
        }
        modelos.Receta receta = mycontroladoralmacen.sacarecetabyproducto(product.getIdproducto());
        if (receta != null) {
            for (int j = 0; j < actividades.size(); j++) {
                if (actividades.get(j).getIdactividad() == receta.getActividad().getIdactividad()) {
                    jComboBox2.setSelectedIndex(j);
                    break;
                }
            }
        } else {
            jComboBox2.setSelectedIndex(0);
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

        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jButton3 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Editar Receta");

        jTextField1.setEditable(false);
        jTextField1.setText("Soda Sbelt");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Busqueda de Producto"));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
            .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 98, Short.MAX_VALUE)
            .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jLabel4.setText("Nombre:");

        jLabel5.setText("Tipo:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Materia Prima", "Insumo" }));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/search.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel6.setText("Buscar");

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel4Layout.createSequentialGroup()
                .add(0, 0, 0)
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel4Layout.createSequentialGroup()
                        .add(jLabel4)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTextField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 107, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel5)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jComboBox1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 136, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(229, 229, 229))
                    .add(jPanel4Layout.createSequentialGroup()
                        .add(12, 12, 12)
                        .add(jPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jButton3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel6))
                        .add(217, 217, 217))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4)
                    .add(jTextField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel5)
                    .add(jComboBox1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel4Layout.createSequentialGroup()
                        .add(jButton3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel6))
                    .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Detalle Receta"));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/save.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Grabar");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Azucar", "Materia Prima", "10", "Kg"},
                {"Arina", "Materia Prima", "8", "Kg"},
                {"Relleno", "Insumo", "7", "Kg"},
                {"Caja", "Insumo", "1", "Unidad"}
            },
            new String [] {
                "Nombre", "Tipo", "Cantidad", "Unidad"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/minus.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setText("Eliminar");

        org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 300, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel1)
                    .add(jButton2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel2)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .add(jPanel5Layout.createSequentialGroup()
                        .add(jButton2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 23, Short.MAX_VALUE)
                        .add(jButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel1)))
                .add(19, 19, 19))
        );

        jLabel3.setText("Actividad");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(31, 31, 31)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jPanel5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 380, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(61, 61, 61)
                        .add(jLabel3)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jComboBox2, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(20, 20, 20)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel3)
                    .add(jComboBox2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(jPanel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        Producto product = lbj.LBJ.Login.escritorio.producto.productoareceta;
        if (product == null) {
            product = lbj.LBJ.Login.escritorio.actividades.actividadnueva.productoareceta;
        }

        String nombre = jTextField2.getText();
        String tipo = jComboBox1.getSelectedItem().toString();
        Tipoproducto tp = mycontroladoralmacen.sacatipoproductobynombre(tipo);
        int tipoint = tp.getIdtipoproducto();
        myProductTableModel.pro = mycontroladoralmacen.buscaproductosreceta(nombre, tipoint, product);
        myProductTableModel.fireTableChanged(null);
        
        mycontroladorseguridad.insertarlog(usuario, new Date(), "Consultar", "Producto", "Consultar Productos");
    }//GEN-LAST:event_jButton3ActionPerformed

    public void refreshProductTable2() {
        //myProductTableModel2.pro2 = array2;
        myProductTableModel2.fireTableChanged(null);
        //myProductTableModel2.lista3 = array3;
        //myProductTableModel2.fireTableChanged(null);
    }

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int row = jTable2.getSelectedRow();
            int encontro = 0;
            String nombrep = jTable2.getValueAt(row, 0).toString();
            Producto p = mycontroladoralmacen.sacaproductobynombre(nombrep);
            /*aca evalua si el producto ya se encuentra agregado o no*/
            for (int i = 0; i < myProductTableModel2.pro2.size(); i++) {
                if (p.getIdproducto() == myProductTableModel2.pro2.get(i).getIdproducto()) {
                    encontro = 1;
                    JOptionPane.showMessageDialog(this, "Ya agrego este producto");
                    break;
                }
            }
            //aca evalua si el producto ya se encuentra agregado o no
            if (encontro == 0) {
                myProductTableModel2.pro2.add(p);
                myProductTableModel2.lista3.add("0");
//                array2.add(p);
//                array3.add("0");
                refreshProductTable2();
            }
        }
    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String nombre = jTextField1.getText();
        int verificaexistencia = mycontroladoralmacen.verificaexistenciareceta(nombre);
        //int idreceta = 0, i;
        int i, k, m, esta;
        modelos.Receta receta = new modelos.Receta();

        if (myProductTableModel2.pro2.size() == 0) {
            JOptionPane.showMessageDialog(null, "Ingrese un producto, la receta está vacía");
        } else {
            int correcto = mycontroladoralmacen.verificanumeros1(myProductTableModel2.lista3);
            if (correcto == 1) {
                JOptionPane.showMessageDialog(null, "No puede dejar una cantidad vacia en la receta");
            } else if (correcto == 2) {
                JOptionPane.showMessageDialog(null, "Ingrese valores numericos positivos");
            } else if (correcto == 3) {
                JOptionPane.showMessageDialog(null, "No puede dejar una receta en 0");
            } else {
                //HORNEADO NO PUEDE TENER UN PRODUCTO INTERMEDIO
                String nombreactividad2 = jComboBox2.getSelectedItem().toString();
                Actividad actv2 = mycontroladoralmacen.sacaactividadbyname(nombreactividad2);
                int primeraactividad = 1;
                if (actv2.getIdactividad() == 1) {
                    for (int t = 0; t < myProductTableModel2.pro2.size(); t++) {
                        if (myProductTableModel2.pro2.get(t).getTipoproducto().getIdtipoproducto() == 3) {
                            primeraactividad = 2;
                            break;
                        }
                        if (myProductTableModel2.pro2.get(t).getTipoproducto().getIdtipoproducto() == 4) {
                            primeraactividad = 3;
                            break;
                        }
                    }
                }
                //HORNEADO NO PUEDE TENER UN PRODUCTO INTERMEDIO
                if (primeraactividad == 2) {
                    JOptionPane.showMessageDialog(null, "Usted esta tratando de ingresar un producto intermedio en la actividad de Horneado.");
                } else if (primeraactividad == 3) {
                    JOptionPane.showMessageDialog(null, "Usted esta tratando de ingresar un producto terminado en la actividad de Horneado.");
                } else {
                    int consistencia = 1;
                    //2 si es decorado e ingreso relleno
                    //3 si es relleno e ingreso decorado
                    consistencia = mycontroladoralmacen.verificaconsistencia(actv2, myProductTableModel2.pro2);
                    //ACA TENGO QUE VALIDAR A DECORADO, RELLENO Y EMPAQUETADO
                    if (consistencia == 2) {
                        JOptionPane.showMessageDialog(null, "Usted esta tratando de ingresar un producto de relleno en la actividad de Decorado.");
                    } else if (consistencia == 3) {
                        JOptionPane.showMessageDialog(null, "Usted esta tratando de ingresar un producto de decorado en la actividad de Relleno.");
                    } else {
                        //ESTO AGREGUE
                        if (verificaexistencia == 1) {
                            //YA EXISTE LA RECETA guarda solo a la lista de productos
                            receta = mycontroladoralmacen.sacaidreceta(nombre);
                            String nombreactividad = jComboBox2.getSelectedItem().toString();
                            Actividad actv = mycontroladoralmacen.sacaactividadbyname(nombreactividad);
                            mycontroladoralmacen.editareceta(receta, nombre, actv);
                            List<Producto> actuales = mycontroladoralmacen.sacaproductosdereceta(receta.getIdreceta());
                            //1) CREA EL IDPRODUCTOXRECETA
                            //2) POR CADA PRODUCTO 
                            //2.1)CREA EL IDPRODUXTOXRECETA, 
                            //2.2)GUARDA PRODUCTO, RECETA Y CANTIDAD
                            for (int j = 0; j < myProductTableModel2.pro2.size(); j++) {
                                ProductoxrecetaId pxr = new ProductoxrecetaId();
                                pxr.setIdproducto(myProductTableModel2.pro2.get(j).getIdproducto());
                                pxr.setIdreceta(receta.getIdreceta());
                                float cantidad = Float.parseFloat(myProductTableModel2.lista3.get(j));
                                mycontroladoralmacen.guardaproductoxreceta(pxr, receta, myProductTableModel2.pro2.get(j), cantidad);
                            }

                            //ELIMINA LOS PRODUCTOS QUE NO ESTAN EN PRO2

                            Productoxreceta pxr = new Productoxreceta();
                            for (k = 0; k < actuales.size(); k++) {
                                System.out.println(actuales.get(k).getNombre());
                                esta = 0;
                                for (m = 0; m < myProductTableModel2.pro2.size(); m++) {
                                    if (myProductTableModel2.pro2.get(m).getIdproducto() == actuales.get(k).getIdproducto()) {
                                        esta = 1;
                                        break;
                                    }
                                }
                                if (esta == 0) {
                                    System.out.println(receta.getIdreceta());
                                    System.out.println(actuales.get(k).getIdproducto());
                                    pxr = mycontroladoralmacen.sacapxr(receta.getIdreceta(), actuales.get(k).getIdproducto());
                                    //ACA ESTA lo pienso en mi jato
                                    ProductoxrecetaId pxrcid = new ProductoxrecetaId();
                                    pxrcid.setIdproducto(actuales.get(k).getIdproducto());
                                    pxrcid.setIdreceta(receta.getIdreceta());
                                    pxr.setId(pxrcid);
                                    mycontroladoralmacen.eliminaproductoreceta(pxr);
                                }
                            }
                        } else {
                            //NO EXISTE LA RECETA, LA GUARDA
                            //1) GUARDA LA RECETA
                            String nombreactividad = jComboBox2.getSelectedItem().toString();
                            Actividad actv = mycontroladoralmacen.sacaactividadbyname(nombreactividad);
                            mycontroladoralmacen.guardareceta(nombre, actv);
                            receta = mycontroladoralmacen.sacaidreceta(nombre);
                            for (i = 0; i < myProductTableModel2.pro2.size(); i++) {
                                //2) POR CADA PRODUCTO 
                                //2.1)CREA EL IDPRODUXTOXRECETA, 
                                //2.2)GUARDA PRODUCTO, RECETA Y CANTIDAD
                                ProductoxrecetaId pxr = new ProductoxrecetaId();
                                pxr.setIdproducto(myProductTableModel2.pro2.get(i).getIdproducto());
                                pxr.setIdreceta(receta.getIdreceta());
                                float cantidad = Float.parseFloat(myProductTableModel2.lista3.get(i));
                                mycontroladoralmacen.guardaproductoxreceta(pxr, receta, myProductTableModel2.pro2.get(i), cantidad);
                                //System.out.print(array2.get(i).getNombre());
                                //System.out.print(array3.get(i));
                                
                                mycontroladorseguridad.insertarlog(usuario, new Date(), "Insertar", "Productoxreceta", "Insertar Productoxreceta");
                            }
                        }
                        Producto product = lbj.LBJ.Login.escritorio.producto.productoareceta;
                        if (product == null) {
                            lbj.LBJ.Login.escritorio.actividades.actividadnueva.refreshProductTable2();
                        } else {
                            lbj.LBJ.Login.escritorio.producto.refreshProductTable();
                        }
                        this.dispose();
                    }

                }
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (jTable1.getSelectedRow() != -1) {
            int row = jTable1.getSelectedRow();
            int encontro = 0;
            String nombrep = jTable1.getValueAt(row, 0).toString();
            Producto p = mycontroladoralmacen.sacaproductobynombre(nombrep);
            for (int i = 0; i < myProductTableModel2.pro2.size(); i++) {
                if (myProductTableModel2.pro2.get(i).getIdproducto() == p.getIdproducto()) {
                    myProductTableModel2.pro2.remove(i);
                    myProductTableModel2.lista3.remove(i);
                    break;
                }
            }
            refreshProductTable2();
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un producto para sacarlo de la receta.", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton2ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
