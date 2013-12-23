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
import modelos.Producto;
import modelos.Tipoproducto;
import modelos.Unidadmedida;
import modelos.Usuario;

/**
 *
 * @author mariofcandia
 */
public class ProductoEditar extends javax.swing.JInternalFrame {

    /**
     * Creates new form ProductoEditar
     */
    public static controladoralmacen mycontroladoralmacen = new controladoralmacen();

    //Para el registro de Log
    public static Usuario usuario = lbj.LBJ.Login.usuario;
    public static controladorseguridad mycontroladorseguridad = new controladorseguridad();
    
    public ProductoEditar() {
        initComponents();
        loadComponents();
    }

    void loadComponents() {

        lbj.LBJ.Login.escritorio.producto.refreshProductTable();
        Producto product = lbj.LBJ.Login.escritorio.producto.productoaeditar;

        jTextField1.setText(product.getNombre());
        jTextArea1.setText(product.getDescripcion());
        jTextField2.setText("" + product.getStock());
        jTextField4.setText("" + product.getStockminimo());
        jTextField5.setText("" + product.getStockmaximo());
        if (product.getPeso() != null) {
            jTextField3.setText("" + product.getPeso());
        } else {
            jTextField3.setText("");
        }
        if (product.getCosto() != null) {
            jTextField6.setText("" + product.getCosto());
        } else {
            jTextField6.setText("");
        }

        if (product.getEstado() == 1) {
            jRadioButton1.setSelected(true);
            jRadioButton2.setSelected(false);
        } else {
            jRadioButton1.setSelected(false);
            jRadioButton2.setSelected(true);
        }

        //jComboBox1.removeAllItems();

        jComboBox2.removeAllItems();
        List<modelos.Almacen> almacenes = new ArrayList<modelos.Almacen>();
        almacenes = mycontroladoralmacen.sacaalmacen();
        for (int i = 0; i < almacenes.size(); i++) {
            jComboBox2.addItem(almacenes.get(i).getNombre());
        }
        jComboBox2.setSelectedIndex(product.getAlmacen().getIdalmacen() - 1);

        List<Tipoproducto> tiposproductos = new ArrayList<Tipoproducto>();
        tiposproductos = mycontroladoralmacen.sacatipoproducto();
        if (product.getAlmacen().getIdalmacen() == 1) {
            for (int i = 0; i < tiposproductos.size() - 2; i++) {
                jComboBox1.addItem(tiposproductos.get(i).getNombre());
            }
            jComboBox1.setSelectedIndex(product.getTipoproducto().getIdtipoproducto() - 1);
        } else {
            for (int i = 2; i < tiposproductos.size(); i++) {
                jComboBox1.addItem(tiposproductos.get(i).getNombre());
            }
            jComboBox1.setSelectedIndex(product.getTipoproducto().getIdtipoproducto() - 3);
        }

        jComboBox3.removeAllItems();
        List<Unidadmedida> unidadesmedidas = new ArrayList<Unidadmedida>();
        unidadesmedidas = mycontroladoralmacen.sacaunidadmedida();
        for (int i = 0; i < unidadesmedidas.size(); i++) {
            jComboBox3.addItem(unidadesmedidas.get(i).getNombre());
        }
        jComboBox3.setSelectedIndex(product.getUnidadmedida().getIdunidadmedida() - 1);


        jComboBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //jComboBox1.removeAllItems();
                List<Tipoproducto> tp = new ArrayList<Tipoproducto>();
                tp = mycontroladoralmacen.sacatipoproducto();
                String seleccionalmacen = jComboBox2.getSelectedItem().toString();

                if (seleccionalmacen.equalsIgnoreCase("Principal")) {
                    System.out.println("Entro aqui Principal");
                    int i = 0;
                    for (i = 0; i < tp.size(); i++) {
                        if (tp.get(i).getIdtipoproducto() == 1
                                || tp.get(i).getIdtipoproducto() == 2) {
                            jComboBox1.addItem(tp.get(i).getNombre());
                        }
                    }
                    int cantidad = jComboBox1.getItemCount();
                    System.out.println(cantidad);
                    //for(int j=2; j<cantidad; j++){
                    jComboBox1.removeItemAt(1);
                    jComboBox1.removeItemAt(0);
                    //}   


                    jComboBox1.setSelectedIndex(0);
                    jTextField4.setText("");
                    jTextField3.setText("");
                    //jTextField4.enable(false);
                    //jTextField3.enable(false);
                    System.out.println("salio aqui Principal");
                } else {
                    System.out.println("Entro aqui Produccion");
                    int i = 0;
                    for (i = 0; i < tp.size(); i++) {
                        if (tp.get(i).getIdtipoproducto() == 3
                                || tp.get(i).getIdtipoproducto() == 4) {
                            jComboBox1.addItem(tp.get(i).getNombre());
                        }
                    }
                    int cantidad = jComboBox1.getItemCount();
                    System.out.println(cantidad);
                    //for(int j=2; j<cantidad; j++){
                    jComboBox1.removeItemAt(1);
                    jComboBox1.removeItemAt(0);
                    //}

                    jComboBox1.setSelectedIndex(0);
                    //jTextField4.enable(true);
                    //jTextField3.enable(true);
                    System.out.println("salio aqui Produccion");
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jRadioButton2 = new javax.swing.JRadioButton();
        jComboBox3 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTextField6 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jRadioButton1 = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setClosable(true);
        setTitle("Editar Producto");
        setPreferredSize(new java.awt.Dimension(507, 417));
        setSize(new java.awt.Dimension(0, 0));

        jLabel10.setText("Editar");

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jRadioButton2.setText("Inactivo");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("Nombre:");

        jLabel11.setText("Cancelar");

        jLabel8.setText("Almacen:");

        jLabel4.setText("Stock Minimo:");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/save.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel5.setText("Stock Maximo:");

        jLabel3.setText("Stock:");

        jLabel7.setText("Tipo:");

        jLabel13.setText("Costo:");

        jLabel2.setText("Descripcion:");

        jLabel9.setText("Unidad:");

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error 1.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel6.setText("Estado:");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField2.setEditable(false);

        jLabel12.setText("Peso:");

        jRadioButton1.setText("Activo");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(43, 43, 43)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jLabel1)
                        .add(45, 45, 45)
                        .add(jTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 222, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel2)
                            .add(jLabel3))
                        .add(18, 18, 18)
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel2Layout.createSequentialGroup()
                                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 224, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(0, 0, Short.MAX_VALUE))
                            .add(jPanel2Layout.createSequentialGroup()
                                .add(jTextField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 112, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 33, Short.MAX_VALUE)
                                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                    .add(jLabel8)
                                    .add(jLabel9)
                                    .add(jLabel7))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                    .add(jComboBox1, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .add(jComboBox2, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .add(jComboBox3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 123, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))))
                    .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(jPanel2Layout.createSequentialGroup()
                            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(jButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabel10))
                            .add(31, 31, 31)
                            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(jButton2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabel11)))
                        .add(jPanel2Layout.createSequentialGroup()
                            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabel4)
                                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel5))
                                .add(jLabel6)
                                .add(jLabel12)
                                .add(jLabel13))
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(jPanel2Layout.createSequentialGroup()
                                    .add(jRadioButton1)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(jRadioButton2))
                                .add(jTextField4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 112, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                    .add(org.jdesktop.layout.GroupLayout.LEADING, jTextField6)
                                    .add(org.jdesktop.layout.GroupLayout.LEADING, jTextField3)
                                    .add(org.jdesktop.layout.GroupLayout.LEADING, jTextField5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE))))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(23, 23, 23)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(jTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel2)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 48, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel2Layout.createSequentialGroup()
                                .add(19, 19, 19)
                                .add(jLabel3))
                            .add(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(jTextField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel4)
                            .add(jTextField4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel5)
                            .add(jTextField5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jTextField3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel12))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel13)
                            .add(jTextField6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(12, 12, 12)
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel8)
                            .add(jComboBox2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel9)
                            .add(jComboBox3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel7)
                            .add(jComboBox1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel6)
                    .add(jRadioButton1)
                    .add(jRadioButton2))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jButton2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel10)
                    .add(jLabel11))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 5, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (JOptionPane.showConfirmDialog(rootPane, "¿Desea realmente editar este producto?",
                "Mensaje", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {


            Producto product = lbj.LBJ.Login.escritorio.producto.productoaeditar;
            String nombre = jTextField1.getText();
            String descripcion = jTextArea1.getText();
            Float stock = Float.parseFloat(jTextField2.getText());
            String stockminimo = jTextField4.getText();
            String stockmaximo = jTextField5.getText();
            String peso = jTextField3.getText();
            String costo = jTextField6.getText();
            int estado;
            if (jRadioButton1.isSelected()) {
                estado = 1;
            } else {
                estado = 2;
            }
            String tipo = jComboBox1.getSelectedItem().toString();
            Tipoproducto tipoproducto = mycontroladoralmacen.sadaidtipo(tipo);
            String almacen = jComboBox2.getSelectedItem().toString();
            modelos.Almacen idalmacen = mycontroladoralmacen.sacaidalmacen(almacen);
            String unidad = jComboBox3.getSelectedItem().toString();
            Unidadmedida idunidad = mycontroladoralmacen.sacaidunidad(unidad);


            int nombrecorrecto = mycontroladoralmacen.validanombre(nombre);

            if (nombrecorrecto == 1) { // cuando el nombre esta vacio
                JOptionPane.showMessageDialog(this, "Ingrese el nombre del producto");
            } else if (nombrecorrecto == 2) {//cuando se ingresan caracteres no permitidos
                JOptionPane.showMessageDialog(this, "Ingrese caracteres correctos en el nombre");
            } else {
                int stockminimocorrecto = mycontroladoralmacen.validastockminimo(stockminimo);
                if (stockminimocorrecto == 1) {
                    JOptionPane.showMessageDialog(this, "Ingrese el stock mínimo");
                } else if (stockminimocorrecto == 2) {
                    JOptionPane.showMessageDialog(this, "Ingrese numeros positivos en el stock mínimo");
                } else {
                    float smin = Float.parseFloat(stockminimo);
                    int stockmaximocorrecto = mycontroladoralmacen.validastockmaximo2(stockmaximo,smin);
                    if (stockmaximocorrecto == 1) {
                        JOptionPane.showMessageDialog(this, "Ingrese el stock máximo");
                    } else if (stockmaximocorrecto == 2) {
                        JOptionPane.showMessageDialog(this, "Ingrese numeros positivos en el stock máximo");
                    } else if (stockmaximocorrecto == 3) {
                        JOptionPane.showMessageDialog(this, "El stock máximo no puede ser 0");
                    } else if (stockmaximocorrecto ==  4){
                        JOptionPane.showMessageDialog(this, "El stock minimo no puede ser mayor que el maximo");
                    }else {
                        if (tipoproducto.getIdtipoproducto() == 3 || tipoproducto.getIdtipoproducto() == 4) {
                            //3 necesita costo y 4 necesita coso y peso
                            int costocorrecto = mycontroladoralmacen.validastockmaximo(costo);
                            if (costocorrecto == 1) {
                                JOptionPane.showMessageDialog(this, "Ingrese el costo");
                            } else if (costocorrecto == 2) {
                                JOptionPane.showMessageDialog(this, "Ingrese numeros positivos en el costo");
                            } else if (costocorrecto == 3) {
                                JOptionPane.showMessageDialog(this, "El costo no puede ser 0");
                            } else {
                                if (tipoproducto.getIdtipoproducto() == 4) {
                                    int pesocorrecto = mycontroladoralmacen.validastockmaximo(peso);
                                    if (pesocorrecto == 1) {
                                        JOptionPane.showMessageDialog(this, "Ingrese el peso");
                                    } else if (pesocorrecto == 2) {
                                        JOptionPane.showMessageDialog(this, "Ingrese numeros positivos en el peso");
                                    } else if (pesocorrecto == 3) {
                                        JOptionPane.showMessageDialog(this, "El peso no puede ser 0");
                                    } else {
                                        Float smax = Float.parseFloat(stockmaximo);
                                        mycontroladoralmacen.editarproducto(product, nombre, descripcion, stock, smin, smax, estado,
                                                tipoproducto, idalmacen, idunidad, peso, costo);
                                        lbj.LBJ.Login.escritorio.producto.refreshProductTable();
                                        JOptionPane.showMessageDialog(null, "El producto se actualizó existosamente.");
                                        this.dispose();
                                        
                                        mycontroladorseguridad.insertarlog(usuario, new Date(), "Editar", "Producto", "Editar Producto");
                                    }
                                } else {
                                    Float smax = Float.parseFloat(stockmaximo);
                                    mycontroladoralmacen.editarproducto(product, nombre, descripcion, stock, smin, smax, estado,
                                            tipoproducto, idalmacen, idunidad, peso, costo);
                                    lbj.LBJ.Login.escritorio.producto.refreshProductTable();
                                    JOptionPane.showMessageDialog(null, "El producto se actualizó existosamente.");
                                    this.dispose();
                                    
                                    mycontroladorseguridad.insertarlog(usuario, new Date(), "Editar", "Producto", "Editar Producto");
                                }
                            }
                        } else {
                            Float smax = Float.parseFloat(stockmaximo);
                            mycontroladoralmacen.editarproducto(product, nombre, descripcion, stock, smin, smax, estado,
                                    tipoproducto, idalmacen, idunidad, peso, costo);
                            lbj.LBJ.Login.escritorio.producto.refreshProductTable();
                            JOptionPane.showMessageDialog(null, "El producto se actualizó existosamente.");
                            this.dispose();
                            
                            mycontroladorseguridad.insertarlog(usuario, new Date(), "Editar", "Producto", "Editar Producto");
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
        if(!(jRadioButton1.isSelected())){
            jRadioButton2.setSelected(true);
        }
        jRadioButton1.setSelected(false);
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
        if(!(jRadioButton2.isSelected())){
            jRadioButton1.setSelected(true);
        }
        jRadioButton2.setSelected(false);
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        jComboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipoproducto = jComboBox1.getSelectedItem().toString();
                if (tipoproducto.equalsIgnoreCase("Producto Intermedio")) {
                    jTextField6.enable(true);
                    jTextField3.setText("");
                    jTextField3.enable(false);
                } else if (tipoproducto.equalsIgnoreCase("Producto Terminado")) {
                    jTextField6.enable(true);
                    jTextField3.enable(true);
                } else {
                    jTextField6.setText("");
                    jTextField6.enable(false);
                    jTextField3.setText("");
                    jTextField3.enable(false);
                }
            }
        });
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables
}
