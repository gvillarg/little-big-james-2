/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paquetevistas.almacen;

import Seguridad.Controlador.controladorseguridad;
import controlador.almacen.controladoralmacen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import modelos.Compra;
import modelos.Detallecompra;
import modelos.Detalleventa;
import modelos.Motivo;
import modelos.Movimiento;
import modelos.Movsalidadetalle;
import modelos.MovsalidadetalleId;
import modelos.Producto;
import modelos.Productoxmovimiento;
import modelos.ProductoxmovimientoId;
import modelos.Requerimientocompraxproducto;
import modelos.Tipoproducto;
import modelos.Usuario;
import modelos.Venta;
import paquetevistas.Login;

/**
 *
 * @author mariofcandia
 */
public class MovimientoNuevo extends javax.swing.JInternalFrame {

    /**
     * Creates new form MovimientoNuevo
     */
    public static controladoralmacen mycontroladoralmacen = new controladoralmacen();
    ProductTableModel myProductTableModel = new ProductTableModel();
    ProductTableModel2 myProductTableModel2 = new ProductTableModel2();
    public static int usacompra = 0;
    public static int usaventa = 0;
    //Para el registro de Log
    public static Usuario usuario = lbj.LBJ.Login.usuario;
    public static controladorseguridad mycontroladorseguridad = new controladorseguridad();

//    ProductTableModel3 myProductTableModel3 = new ProductTableModel3();
    //ArrayList<String> fecha2 = new ArrayList<String>();
    //ArrayList<Date> fecha2 = new ArrayList<Date>();
    //ArrayList<String> array3 = new ArrayList<String>();
    //List<Producto> array2 = new ArrayList<Producto>();
    class ProductTableModel extends AbstractTableModel {

        List<Producto> pro = new ArrayList<Producto>();

        public ProductTableModel() {
            pro = mycontroladoralmacen.sacaproductosmp();
        }
        String[] titles = {"Nombre", "Tipo", "Stock", "Unidad"};

        public int getRowCount() {
            return pro.size();
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
                    res = "" + p.getStock();
                    break;
                case 3:
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

        List<Producto> lista2 = new ArrayList<Producto>();
        ArrayList<String> lista3 = new ArrayList<String>();
        ArrayList<String> lista4 = new ArrayList<String>();
        //ArrayList<String> array3 = new ArrayList<String>();
        //ArrayList<Date> fecha1 = new ArrayList<Date>();
        ArrayList<String> fecha1 = new ArrayList<String>();
        //ArrayList<Date> fecha2 = new ArrayList<Date>();

        public ProductTableModel2() {
            //array2 = mycontroladoralmacen.sacaninguno();
            Movimiento movi = lbj.LBJ.Login.escritorio.movimientos.movimientoaeditar;
            List<Productoxmovimiento> prodxmovimientos = mycontroladoralmacen.sacaproductosxmovimiento(movi.getIdmovimiento());
            for (int i = 0; i < prodxmovimientos.size(); i++) {
                Producto p = mycontroladoralmacen.sacaproductobyid(prodxmovimientos.get(i).getId().getIdproducto());
                lista2.add(p);
                lista3.add("" + prodxmovimientos.get(i).getStock());
                lista4.add("" + prodxmovimientos.get(i).getStockactual());
                //fecha1.add(prodxmovimientos.get(i).getFvencimiento());
                SimpleDateFormat sdf;
                String fecha;
                sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
                fecha = sdf.format(prodxmovimientos.get(i).getFvencimiento());
                fecha1.add(fecha);
            }
        }
        String[] titles = {"Nombre", "Tipo", "Stock", "StockActual", "Unidad", "Cantidad", "F.Vencimiento"};

        public int getRowCount() {
            return lista2.size();
        }

        public int getColumnCount() {
            return 7;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            String res = null;
            int tipo;
            int unidad;
            String unidadnombre = null;
            String nombre = null;
            Producto p = lista2.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    res = "" + p.getNombre();
                    break;
                case 1:
                    if (p.getNombre().equalsIgnoreCase("Seleccione")) {
                        res = "un producto";
                        break;
                    } else {
                        tipo = p.getTipoproducto().getIdtipoproducto();
                        nombre = mycontroladoralmacen.sacanombretipo(tipo);
                        res = nombre;
                        break;
                    }
                case 2:
                    res = "" + p.getStock();
                    break;
                case 3:
                    res = lista4.get(rowIndex);
                    break;
                case 4:
                    unidad = p.getUnidadmedida().getIdunidadmedida();
                    unidadnombre = mycontroladoralmacen.sacanombreunidad(unidad);
                    res = unidadnombre;
                    break;
                case 5:
                    res = lista3.get(rowIndex);
                    break;
                case 6:
                    //if (fecha1.get(rowIndex) == null) {
                    //    res = "";
                    //} else {
                    res = fecha1.get(rowIndex);
                    //}
                    break;
            }
            return res;
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            if (usacompra == 0 && usaventa == 0) {
                if (columnIndex == 5 || columnIndex == 6) {
                    return true;
                }
            } else {
                if (columnIndex == 6) {
                    return true;
                }
            }
            return false;
        }

        public void setValueAt(Object value, int row, int col) {
            if (col == 5) {
                String cadena = value.toString();
                lista3.set(row, cadena);
            } else if (col == 6) {
                String cadena2 = value.toString();
                fecha1.set(row, cadena2);
            }
        }

        public String getColumnName(int col) {
            return titles[col];
        }
    }

    public MovimientoNuevo() {
        initComponents();
        loadComponents();
        jTable1.setModel(myProductTableModel);
        jTable2.setModel(myProductTableModel2);
    }

    public void loadComponents() {
        usacompra = 0;
        usaventa = 0;
        Movimiento mov = lbj.LBJ.Login.escritorio.movimientos.movimientoaeditar;
        List<Motivo> motivos = new ArrayList<Motivo>();
        motivos = mycontroladoralmacen.sacamotivos();
        Date fechahoy = new Date();
        Date fechavencimiento = new Date();
        fechavencimiento.setMonth(fechavencimiento.getMonth() + 1);
        jDateChooser2.setDate(fechavencimiento);
        cmbTipo.removeAllItems();
        cmbTipo.addItem("Entrada");
        cmbTipo.addItem("Salida");

        if (mov.getIdmovimiento() == 0) {
            jComboBox2.removeAllItems();
            List<modelos.Almacen> almacenes = new ArrayList<modelos.Almacen>();
            almacenes = mycontroladoralmacen.sacaalmacen();
            for (int i = 0; i < almacenes.size(); i++) {
                jComboBox2.addItem(almacenes.get(i).getNombre());
            }

            jComboBox4.removeAllItems();
            List<Tipoproducto> tp = new ArrayList<Tipoproducto>();
            tp = mycontroladoralmacen.sacatipoproducto();
            for (int i = 0; i < tp.size(); i++) {
                if (tp.get(i).getIdtipoproducto() == 1
                        || tp.get(i).getIdtipoproducto() == 2) {
                    jComboBox4.addItem(tp.get(i).getNombre());
                }
            }
            jComboBox4.setSelectedIndex(0);

            cmbMotivo.removeAllItems();
            //jComboBox5.disable();
            jComboBox5.enable(false);
            jComboBox6.disable();
            jButton4.setEnabled(false);
            jButton5.setEnabled(false);

            jDateChooser1.setDate(fechahoy);
            cmbTipo.setSelectedIndex(0);
            jComboBox2.setSelectedIndex(0);
            for (int i = 0; i < motivos.size(); i++) {
                if (motivos.get(i).getIdmotivo() == 1
                        || motivos.get(i).getIdmotivo() == 2
                        || motivos.get(i).getIdmotivo() == 3
                        || motivos.get(i).getIdmotivo() == 5) {
                    cmbMotivo.addItem(motivos.get(i).getNombre());
                }
            }
            cmbMotivo.setSelectedIndex(0);

            cmbTipo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String tipo = cmbTipo.getSelectedItem().toString();

                    if (tipo.equalsIgnoreCase("Entrada")) {
                        cmbMotivo.removeAllItems();
                        List<Motivo> motivos = new ArrayList<Motivo>();
                        motivos = mycontroladoralmacen.sacamotivos();
                        for (int i = 0; i < motivos.size(); i++) {
                            if (motivos.get(i).getIdmotivo() == 1
                                    || motivos.get(i).getIdmotivo() == 2
                                    || motivos.get(i).getIdmotivo() == 3
                                    || motivos.get(i).getIdmotivo() == 5) {
                                cmbMotivo.addItem(motivos.get(i).getNombre());
                            }
                        }
                        cmbMotivo.setSelectedIndex(0);
                        jComboBox5.enable();
                        jComboBox5.removeAllItems();
                        List<Compra> compras = mycontroladoralmacen.sacacompras();
                        for (int j = 0; j < compras.size(); j++) {
                            jComboBox5.addItem(compras.get(j).getIdcompra());
                        }
                        //jComboBox6.disable();
                        jComboBox6.enable(false);
                        jButton4.setEnabled(true);
                        jButton5.setEnabled(false);
                    } else {
                        cmbMotivo.removeAllItems();
                        List<Motivo> motivos = new ArrayList<Motivo>();
                        motivos = mycontroladoralmacen.sacamotivos();
                        for (int i = 0; i < motivos.size(); i++) {
                            if (motivos.get(i).getIdmotivo() == 4
                                    || motivos.get(i).getIdmotivo() == 5) {
                                cmbMotivo.addItem(motivos.get(i).getNombre());
                            }
                        }
                        cmbMotivo.setSelectedIndex(0);
                        //jComboBox5.disable();
                        jComboBox5.enable(false);
                        jComboBox6.enable();
                        jComboBox6.removeAllItems();
                        List<Venta> ventas = mycontroladoralmacen.sacaventas();
                        for (int j = 0; j < ventas.size(); j++) {
                            jComboBox6.addItem(ventas.get(j).getIdventa());
                        }
                        jButton4.setEnabled(false);
                        jButton5.setEnabled(true);
                    }

//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });

            jComboBox2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    /*ACA ESTOY PONIENDO MI CODIGO*/
                    String seleccionalmacen = jComboBox2.getSelectedItem().toString();
                    if (seleccionalmacen.equalsIgnoreCase("Principal")) {
                        jComboBox4.removeAllItems();
                        List<Tipoproducto> tp = new ArrayList<Tipoproducto>();
                        tp = mycontroladoralmacen.sacatipoproducto();
                        for (int i = 0; i < tp.size(); i++) {
                            if (tp.get(i).getIdtipoproducto() == 1
                                    || tp.get(i).getIdtipoproducto() == 2) {
                                jComboBox4.addItem(tp.get(i).getNombre());
                            }
                        }
                        jComboBox4.setSelectedIndex(0);
                    } else {
                        jComboBox4.removeAllItems();
                        List<Tipoproducto> tp = new ArrayList<Tipoproducto>();
                        tp = mycontroladoralmacen.sacatipoproducto();
                        for (int i = 0; i < tp.size(); i++) {
                            if (tp.get(i).getIdtipoproducto() == 3
                                    || tp.get(i).getIdtipoproducto() == 4) {
                                jComboBox4.addItem(tp.get(i).getNombre());
                            }
                        }
                        jComboBox4.setSelectedIndex(0);
                    }
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
        } else {

            if (mov.getProduccion() != null) {
                jTextField1.setText("" + mov.getProduccion().getIdproduccion());
            }

            jDateChooser1.setDate(mov.getFecha());
            if (mov.getTipo() == 1) {
                cmbTipo.setSelectedIndex(0);
            } else {
                cmbTipo.setSelectedIndex(1);
            }
            cmbTipo.enable(false);

            cmbMotivo.removeAllItems();
            Motivo m = mycontroladoralmacen.sacamotivobyid(mov.getMotivo().getIdmotivo());
            cmbMotivo.addItem(m.getNombre());
            cmbMotivo.setSelectedIndex(0);
            cmbMotivo.enable(false);

            jComboBox2.removeAllItems();
            modelos.Almacen al = mycontroladoralmacen.sacaalmacenbyid(mov.getAlmacen().getIdalmacen());
            jComboBox2.addItem(al.getNombre());
            jComboBox2.setSelectedIndex(0);
            jComboBox2.enable(false);

            if (mov.getCompra() != null) {
                jComboBox5.addItem(mov.getCompra().getIdcompra());
                jComboBox5.setSelectedIndex(0);
            }
            if (mov.getVenta() != null) {
                jComboBox6.addItem(mov.getVenta().getIdventa());
                jComboBox6.setSelectedIndex(0);
            }
            jComboBox5.enable(false);
            jComboBox6.enable(false);
            jButton4.setEnabled(false);
            jButton5.setEnabled(false);
        }
    }

    public void refreshProductTable() {
        myProductTableModel.pro = mycontroladoralmacen.sacaproductos();
        myProductTableModel.fireTableChanged(null);
    }

    public void refreshProductTable2() {
        //myProductTableModel2.lista2 = array2;
        myProductTableModel2.fireTableChanged(null);
//        myProductTableModel2.fecha1 = fecha2;
//        myProductTableModel2.fireTableChanged(null);
//        myProductTableModel2.lista3 = array3;
//        myProductTableModel2.fireTableChanged(null);
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
        jLabel1 = new javax.swing.JLabel();
        cmbTipo = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cmbMotivo = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox();
        jButton3 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setTitle("Movimiento Nuevo");

        jLabel1.setText("Tipo:");

        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Entrada", "Salida" }));
        cmbTipo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cmbTipoMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                cmbTipoMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbTipoMouseClicked(evt);
            }
        });
        cmbTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoActionPerformed(evt);
            }
        });

        jLabel2.setText("Almacen:");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Principal", "Produccion" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel3.setText("Fecha:");

        jLabel4.setText("Motivo:");

        cmbMotivo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pedido de ventas", "stock minimo", "fecha vencimiento", "salida horneado", "salida relleno", "salida decorado", "salida empaquetado" }));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Detalle Movimiento"));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 129, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/save.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel9.setText("Guardar");

        jLabel11.setText("Orden Compra:");

        jLabel12.setText("Orden Salida:");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/minus.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel13.setText("Eliminar");

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Busqueda Producto"));

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
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))
        );

        jLabel6.setText("Nombre:");

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel7.setText("Tipo:");

        jLabel14.setText("Fecha Vencimiento");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Materia Prima", "Insumo", "Producto Intermedio", "Producto Terminado" }));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/search.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel10.setText("Buscar");

        org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel5Layout.createSequentialGroup()
                        .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel7)
                            .add(jPanel5Layout.createSequentialGroup()
                                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jLabel10))))
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel5Layout.createSequentialGroup()
                        .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jPanel5Layout.createSequentialGroup()
                                .add(jLabel14)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(jDateChooser2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel5Layout.createSequentialGroup()
                                .add(jLabel6)
                                .add(18, 18, 18)
                                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jComboBox4, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .add(jTextField2))))
                        .add(272, 272, 272))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel6)
                    .add(jTextField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(11, 11, 11)
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jPanel5Layout.createSequentialGroup()
                        .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel7)
                            .add(jComboBox4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(15, 15, 15)
                        .add(jLabel14))
                    .add(jDateChooser2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel5Layout.createSequentialGroup()
                        .add(18, 18, 18)
                        .add(jButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel10)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton4.setText("Carga");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Carga");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel5.setText("Produccion:");

        jTextField1.setEditable(false);

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jButton2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel9)
                    .add(jLabel13)
                    .add(jButton3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .add(jPanel1Layout.createSequentialGroup()
                .add(54, 54, 54)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel2)
                            .add(jLabel1)
                            .add(jLabel11)
                            .add(jLabel5))
                        .add(41, 41, 41)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jButton4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(jComboBox5, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(jComboBox2, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(cmbTipo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 97, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .add(0, 0, Short.MAX_VALUE)))
                        .add(62, 62, 62)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel4)
                            .add(jLabel3)
                            .add(jLabel12))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jButton5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(jComboBox6, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(cmbMotivo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 156, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jDateChooser1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .add(38, 38, 38))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel3)
                            .add(jDateChooser1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel4)
                            .add(cmbMotivo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(10, 10, 10)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel12)
                            .add(jComboBox6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(cmbTipo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel1))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jComboBox2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel2))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jComboBox5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel11))
                        .add(3, 3, 3)))
                .add(3, 3, 3)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButton4)
                    .add(jButton5))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel5)
                    .add(jTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                        .add(jButton3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel13)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jButton2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel9)))
                .add(14, 14, 14))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        Producto p = new Producto();
        float stock;
        String tipo = cmbTipo.getSelectedItem().toString();
        int tipomovimiento, correcto;
        Date fecha = jDateChooser1.getDate();
        int fechacabecera = 1;
        fechacabecera = mycontroladoralmacen.validafechacabecera(fecha);
        if (fechacabecera == 2) {
            JOptionPane.showMessageDialog(null, "Ingrese el formato de fecha correcto en la cabecera");
        } else {
            if (myProductTableModel2.lista2.size() == 0) {
                JOptionPane.showMessageDialog(null, "Ingrese un producto, el movimiento está vacío");
            } else {
                correcto = mycontroladoralmacen.verificanumeros1(myProductTableModel2.lista3);
                if (correcto == 1) {
                    JOptionPane.showMessageDialog(null, "No puede dejar una cantidad vacia");
                } else if (correcto == 2) {
                    JOptionPane.showMessageDialog(null, "Ingrese valores numericos positivos");
                } else if (correcto == 3) {
                    JOptionPane.showMessageDialog(null, "No puede dejar un movimiento en 0");
                } else {
                    int fechacorrecta = mycontroladoralmacen.verificafechas(myProductTableModel2.fecha1);
                    if (fechacorrecta == 3) {
                        JOptionPane.showMessageDialog(null, "El producto ya vencio");
                    } else if (fechacorrecta == 2) {
                        JOptionPane.showMessageDialog(null, "Ingrese el formato de fecha correcto en el detalle del movimiento");
                    } else {
                        if (tipo.equalsIgnoreCase("Entrada")) {
                            tipomovimiento = 1;
                        } else {
                            tipomovimiento = 2;
                        }
                        String almacenn = jComboBox2.getSelectedItem().toString();
                        modelos.Almacen alma = mycontroladoralmacen.sacaidalmacen(almacenn);
                        String motivon = cmbMotivo.getSelectedItem().toString();
                        Motivo motivo = mycontroladoralmacen.sacamotivobyname(motivon);
                        Venta venta = new Venta();
                        Compra compra = new Compra();
                        int estado = 1;
                        if (tipomovimiento == 2) {//evalua que haya stock antes que se realice la salida
                            int alcanzastock = mycontroladoralmacen.comparacantidades(myProductTableModel2.lista2, myProductTableModel2.lista3);
                            if (alcanzastock != 0) {
                                JOptionPane.showMessageDialog(null, "El producto " + myProductTableModel2.lista2.get(alcanzastock - 1).getNombre() + " no tiene suficiente stock");
                            } else {
                                mycontroladoralmacen.guardamovimiento(tipomovimiento, alma, motivo, fecha, estado, usacompra, usaventa);
                                //AHORA VA A GUARDAR EN LA TABLA DE MUCHOS A MUCHOS
                                //tengo que mejorar esto de sacar al ultimo
                                Movimiento movimiento = mycontroladoralmacen.sacaultimomovimiento();
                                //System.out.println(movimiento.getIdmovimiento());
                                for (int i = 0; i < myProductTableModel2.lista2.size(); i++) {
                                    ProductoxmovimientoId pxm = new ProductoxmovimientoId();
                                    pxm.setIdmovimiento(movimiento.getIdmovimiento());
                                    pxm.setIdproducto(myProductTableModel2.lista2.get(i).getIdproducto());
                                    float cantidad = Float.parseFloat(myProductTableModel2.lista3.get(i));
                                    float cantidadauxiliar = cantidad;
                                    String fechastring = myProductTableModel2.fecha1.get(i);
                                    //Date fechav = myProductTableModel2.fecha1.get(i);
                                    Date fechav = new Date();
                                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                    try {
                                        fechav = formatter.parse(fechastring);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }


                                    Productoxmovimiento productoxmovimientoparalasalida = mycontroladoralmacen.guardaproductoxmovimiento2(pxm, movimiento, myProductTableModel2.lista2.get(i), cantidad, fechav);
                                    //AQUI APLICO FIFO
                                    //busco una entrada que tenga ese producto con la fecha de vencimiento mas cercana,
                                    //donde haya producto y que este en estado restante
                                    List<Movimiento> entradas = mycontroladoralmacen.sacamovimientosentradas();//aqui estan las entradas restantes
                                    for (int k = 0; k < entradas.size(); k++) {
                                        System.out.println(entradas.get(k).getIdmovimiento());
                                    }
                                    List<Productoxmovimiento> entradasconeseproducto = new ArrayList<Productoxmovimiento>();
                                    entradasconeseproducto = mycontroladoralmacen.sacaproductosxmovimientoordenadosporfecha(myProductTableModel2.lista2.get(i), entradas);
                                    for (int l = 0; l < entradasconeseproducto.size(); l++) {
                                        System.out.println(entradasconeseproducto.get(l).getId().getIdmovimiento());
                                        System.out.println(entradasconeseproducto.get(l).getId().getIdproducto());
                                    }
                                    int cont = 0;
                                    float cantidadactualdelmovimiento;
                                    float valor = 0;
                                    float valorcancelacion = 0;
                                    while (cantidadauxiliar > 0) {
                                        cantidadactualdelmovimiento = entradasconeseproducto.get(cont).getStockactual();
                                        if (cantidadauxiliar > cantidadactualdelmovimiento) {
                                            cantidadauxiliar = cantidadauxiliar - cantidadactualdelmovimiento;
                                            valor = 0;
                                            valorcancelacion = cantidadactualdelmovimiento;
                                        } else if (cantidadauxiliar < cantidadactualdelmovimiento) {
                                            valor = cantidadactualdelmovimiento - cantidadauxiliar;
                                            valorcancelacion = cantidadauxiliar;
                                            cantidadauxiliar = 0;
                                        } else if (cantidadauxiliar == cantidadactualdelmovimiento) {
                                            valorcancelacion = cantidadauxiliar;
                                            cantidadauxiliar = 0;
                                            valor = 0;
                                        }
                                        //actualizaelstockactual del productoxmovimiento y si ya estan todos en 0, lo pone en estado 2
                                        Productoxmovimiento prodxmov = mycontroladoralmacen.sacaproductoxmovimiento(entradasconeseproducto.get(cont).getId().getIdmovimiento(),
                                                entradasconeseproducto.get(cont).getId().getIdproducto());
                                        System.out.println("El stock actual es " + prodxmov.getStockactual());
                                        mycontroladoralmacen.actualizastockactual(prodxmov, valor);
                                        //ACA AGREGO LA TABLA EXTRA PARA SABER DE DONDE VINIERON ESAS ENTRADAS
                                        MovsalidadetalleId mvsdid = new MovsalidadetalleId();
                                        mvsdid.setIdproducto(myProductTableModel2.lista2.get(i).getIdproducto());
                                        mvsdid.setIdmovimientos(movimiento.getIdmovimiento());
                                        mvsdid.setIdmovimientoe(prodxmov.getMovimiento().getIdmovimiento());

                                        Movsalidadetalle movsalidadetalle = new Movsalidadetalle();
                                        mycontroladoralmacen.guardamovsalidadetalle(mvsdid, prodxmov.getMovimiento(), productoxmovimientoparalasalida, valorcancelacion);
                                        //ACA AGREGO LA TABLA EXTRA PARA SABER DE DONDE VINIERON ESAS ENTRADAS
                                        cont = cont + 1;
                                    }
                                    //si esa entrada cumplio lo que se debe entonces paro
                                    //veo si ese moviento ya acabo para cambiarlo de estado
                                    //AQUI APLICO FIFO

                                    //aqui cambia el stock del producto
                                    p = mycontroladoralmacen.sacaproductobyid(myProductTableModel2.lista2.get(i).getIdproducto());
                                    stock = p.getStock() - cantidad;
                                    mycontroladoralmacen.actualizarstock(p, stock);
                                }
                                Login.escritorio.movimientos.refreshProductTable();
                                this.dispose();

                                mycontroladorseguridad.insertarlog(usuario, new Date(), "Insertar", "Movimiento", "Insertar Movimiento");
                                mycontroladorseguridad.insertarlog(usuario, new Date(), "Insertar", "Productoxmovimiento", "Insertar Productoxmovimiento");
                            }
                        } else {

                            Movimiento m = lbj.LBJ.Login.escritorio.movimientos.movimientoaeditar;
                            if (m.getIdmovimiento() == 0) {
                                mycontroladoralmacen.guardamovimiento(tipomovimiento, alma, motivo, fecha, estado, usacompra, usaventa);
                                //AHORA VA A GUARDAR EN LA TABLA DE MUCHOS A MUCHOS
                                //tengo que mejorar esto de sacar al ultimo
                                Movimiento movimiento = mycontroladoralmacen.sacaultimomovimiento();
                                //System.out.println(movimiento.getIdmovimiento());
                                for (int i = 0; i < myProductTableModel2.lista2.size(); i++) {
                                    ProductoxmovimientoId pxm = new ProductoxmovimientoId();
                                    pxm.setIdmovimiento(movimiento.getIdmovimiento());
                                    pxm.setIdproducto(myProductTableModel2.lista2.get(i).getIdproducto());
                                    float cantidad = Float.parseFloat(myProductTableModel2.lista3.get(i));
                                    //Date fechav = myProductTableModel2.fecha1.get(i);

                                    String fechastring = myProductTableModel2.fecha1.get(i);
                                    //Date fechav = myProductTableModel2.fecha1.get(i);
                                    Date fechav = new Date();
                                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                    try {
                                        fechav = formatter.parse(fechastring);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    mycontroladoralmacen.guardaproductoxmovimiento(pxm, movimiento, myProductTableModel2.lista2.get(i), cantidad, fechav);
                                    //aqui cambia el stock del producto
                                    p = mycontroladoralmacen.sacaproductobyid(myProductTableModel2.lista2.get(i).getIdproducto());
                                    stock = cantidad + p.getStock();
                                    mycontroladoralmacen.actualizarstock(p, stock);
                                }
                                Login.escritorio.movimientos.refreshProductTable();
                                this.dispose();
                            } else {
                                int i = 0, yaseconsumio = 0;
                                ArrayList<Integer> codigosproductos = new ArrayList<Integer>();
                                ArrayList<Integer> cantidadposicion = new ArrayList<Integer>();
                                for (i = 0; i < myProductTableModel2.lista2.size(); i++) {
                                    Productoxmovimiento pxmanterior = mycontroladoralmacen.sacaproductoxmovimiento(m.getIdmovimiento(), myProductTableModel2.lista2.get(i).getIdproducto());
                                    if (Float.parseFloat(myProductTableModel2.lista4.get(i)) == Float.parseFloat("0") && (Float.parseFloat(myProductTableModel2.lista3.get(i)) < pxmanterior.getStock())) {
                                        yaseconsumio = 1;
                                        break;
                                    } else if (Float.parseFloat(myProductTableModel2.lista4.get(i)) == Float.parseFloat("0") && (Float.parseFloat(myProductTableModel2.lista3.get(i)) > pxmanterior.getStock())) {
                                        codigosproductos.add(myProductTableModel2.lista2.get(i).getIdproducto());
                                        cantidadposicion.add(i);
                                    } else if (Float.parseFloat(myProductTableModel2.lista4.get(i)) != Float.parseFloat("0") && (Float.parseFloat(myProductTableModel2.lista3.get(i)) < pxmanterior.getStock())) {
                                        if (Float.parseFloat(myProductTableModel2.lista3.get(i)) < (pxmanterior.getStock() - pxmanterior.getStockactual())) {
                                            yaseconsumio = 2;
                                            break;
                                        }
                                    }
                                }
                                if (yaseconsumio == 1) {
                                    JOptionPane.showMessageDialog(null, "No puede reducir la cantidad de " + myProductTableModel2.lista2.get(i).getNombre() + " el producto ya se consumio");
                                } else if (yaseconsumio == 2) {
                                    JOptionPane.showMessageDialog(null, "No puede reducir la cantidad de " + myProductTableModel2.lista2.get(i).getNombre() + " mas de lo que ya se consumio");
                                } else {
                                    if (codigosproductos.size() > 0) {
                                        for (int j = 0; j < codigosproductos.size(); j++) {
                                            mycontroladoralmacen.actualizaproductoxmovimiento1(m.getIdmovimiento(), Float.parseFloat(myProductTableModel2.lista3.get(cantidadposicion.get(j))), codigosproductos.get(j));
                                        }
                                        mycontroladoralmacen.actualizamovimiento1(m.getIdmovimiento());
                                        Login.escritorio.movimientos.refreshProductTable();
                                    }
                                    JOptionPane.showMessageDialog(null, "El movimiento se actualizó existosamente.");
                                    this.dispose();
                                }

                            }

                            mycontroladorseguridad.insertarlog(usuario, new Date(), "Insertar", "Movimiento", "Insertar Movimiento");
                            mycontroladorseguridad.insertarlog(usuario, new Date(), "Insertar", "Productoxmovimiento", "Insertar Productoxmovimiento");
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void cmbTipoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbTipoMouseClicked
    }//GEN-LAST:event_cmbTipoMouseClicked

    private void cmbTipoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbTipoMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbTipoMouseReleased

    private void cmbTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbTipoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String nombre = jTextField2.getText();
        String tipo = jComboBox4.getSelectedItem().toString();
        Tipoproducto tp = mycontroladoralmacen.sacatipoproductobynombre(tipo);
        int tipoint = tp.getIdtipoproducto();
        //myProductTableModel.pro = mycontroladoralmacen.buscaproductos(nombre,1,tipo,almacen);
        myProductTableModel.pro = mycontroladoralmacen.buscaproductosmovimientos(nombre, tipoint);
        myProductTableModel.fireTableChanged(null);

        mycontroladorseguridad.insertarlog(usuario, new Date(), "Consultar", "Producto", "Consultar Productos");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            if (usacompra != 0 || usaventa != 0) {
                JOptionPane.showMessageDialog(null, "Usted no puede agregar productos a este movimiento.", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            } else {
                Date fecha21 = jDateChooser2.getDate();
                if (fecha21 != null) {
                    int row = jTable1.getSelectedRow();
                    int encontro = 0;
                    String nombrep = jTable1.getValueAt(row, 0).toString();
                    Producto p = mycontroladoralmacen.sacaproductobynombre(nombrep);
                    /*aca evalua si el producto ya se encuentra agregado o no*/
                    for (int i = 0; i < myProductTableModel2.lista2.size(); i++) {
                        System.out.println(myProductTableModel2.lista2.get(i).getNombre());
                        if (p.getIdproducto() == myProductTableModel2.lista2.get(i).getIdproducto()) {
                            encontro = 1;
                            JOptionPane.showMessageDialog(this, "Ya agrego este producto");
                            break;
                        }
                    }
                    //aca evalua si el producto ya se encuentra agregado o no
                    if (encontro == 0) {
                        myProductTableModel2.lista2.add(p);
                        myProductTableModel2.lista3.add("0");
                        myProductTableModel2.lista4.add("0");
                        SimpleDateFormat sdf;
                        String fecha;
                        sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
                        fecha = sdf.format(fecha21);
                        myProductTableModel2.fecha1.add(fecha);
                        refreshProductTable2();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Ingrese el formato de fecha correcto en el detalle");
                }
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if (jTable2.getSelectedRow() != -1) {
            if (usacompra != 0 || usaventa != 0) {
                JOptionPane.showMessageDialog(null, "Usted no puede eliminar productos de este movimiento.", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            } else {
                int row = jTable2.getSelectedRow();
                String nombrep = jTable2.getValueAt(row, 0).toString();
                Producto p = mycontroladoralmacen.sacaproductobynombre(nombrep);
                for (int i = 0; i < myProductTableModel2.lista2.size(); i++) {
                    if (p.getIdproducto() == myProductTableModel2.lista2.get(i).getIdproducto()) {
                        myProductTableModel2.lista2.remove(i);
                        myProductTableModel2.lista3.remove(i);
                        myProductTableModel2.lista4.remove(i);
                        myProductTableModel2.fecha1.remove(i);
                        refreshProductTable2();
                        break;
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un producto para sacarlo del movimiento.", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void cmbTipoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbTipoMousePressed
        // TODO add your handling code here:
        String tipo = cmbTipo.getSelectedItem().toString();
        if (tipo.equalsIgnoreCase("Entrada")) {
            cmbMotivo.removeAllItems();
            List<Motivo> motivos = new ArrayList<Motivo>();
            motivos = mycontroladoralmacen.sacamotivos();
            for (int i = 0; i < motivos.size(); i++) {
                if (motivos.get(i).getIdmotivo() == 1
                        || motivos.get(i).getIdmotivo() == 2
                        || motivos.get(i).getIdmotivo() == 3) {
                    cmbMotivo.addItem(motivos.get(i).getNombre());
                }
            }
            cmbMotivo.setSelectedIndex(0);
            jComboBox6.disable();
        } else {
            cmbMotivo.removeAllItems();
            List<Motivo> motivos = new ArrayList<Motivo>();
            motivos = mycontroladoralmacen.sacamotivos();
            for (int i = 0; i < motivos.size(); i++) {
                if (motivos.get(i).getIdmotivo() == 4) {
                    cmbMotivo.addItem(motivos.get(i).getNombre());
                    break;
                }
            }
            cmbMotivo.setSelectedIndex(0);
            jComboBox5.disable();
        }
    }//GEN-LAST:event_cmbTipoMousePressed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        int cantidad = jComboBox5.getItemCount();
        if (cantidad > 0) {
            if (JOptionPane.showConfirmDialog(rootPane, "¿Seguro que desa cargar el detalle de esta compra?",
                    "Mensaje", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (myProductTableModel2.lista2.size() > 0) {
                    int cont = myProductTableModel2.lista2.size() - 1;
                    for (int i = 0; i < myProductTableModel2.lista2.size(); i++) {
                        myProductTableModel2.lista2.remove(cont);
                        myProductTableModel2.lista3.remove(cont);
                        myProductTableModel2.lista4.remove(cont);
                        cont = cont - 1;
                    }
                    if (myProductTableModel2.lista2.size() > 0) {
                        myProductTableModel2.lista2.remove(0);
                        myProductTableModel2.lista3.remove(0);
                        myProductTableModel2.lista4.remove(0);
                    }
                }
                String idcompra = jComboBox5.getSelectedItem().toString();
                int idcompraint = Integer.parseInt(idcompra);
                List<Detallecompra> detalledelacompra = mycontroladoralmacen.sacadetallecompra(idcompraint);
                for (int i = 0; i < detalledelacompra.size(); i++) {
                    Requerimientocompraxproducto rcxp = new Requerimientocompraxproducto();
                    rcxp = detalledelacompra.get(i).getRequerimientocompraxproducto();
                    int idproducto = rcxp.getId().getIdproducto();
                    Producto p = mycontroladoralmacen.sacaproductobyid(idproducto);
                    myProductTableModel2.lista2.add(p);
                    myProductTableModel2.lista3.add("" + detalledelacompra.get(i).getCantidad());
                    myProductTableModel2.lista4.add("" + detalledelacompra.get(i).getCantidad());
                    Date fechavencimiento = new Date();
                    fechavencimiento.setMonth(fechavencimiento.getMonth() + 1);
                    //myProductTableModel2.fecha1.add(fechavencimiento);
                    //myProductTableModel2.fecha1.add("" + fechavencimiento);
                    SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");;
                    String fecha;
                    fecha = sdf.format(fechavencimiento);
                    myProductTableModel2.fecha1.add(fecha);

                }
                usacompra = idcompraint;
                usaventa = 0;
                myProductTableModel2.fireTableChanged(null);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No hay ninguna orden de compra por el momento");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        int cantidad = jComboBox6.getItemCount();
        if (cantidad > 0) {
            if (JOptionPane.showConfirmDialog(rootPane, "¿Seguro que desa cargar el detalle de esta venta?",
                    "Mensaje", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (myProductTableModel2.lista2.size() > 0) {
                    int cont = myProductTableModel2.lista2.size() - 1;
                    for (int i = 0; i < myProductTableModel2.lista2.size(); i++) {
                        myProductTableModel2.lista2.remove(cont);
                        myProductTableModel2.lista3.remove(cont);
                        myProductTableModel2.lista4.remove(cont);
                        cont = cont - 1;
                    }
                    if (myProductTableModel2.lista2.size() > 0) {
                        myProductTableModel2.lista2.remove(0);
                        myProductTableModel2.lista3.remove(0);
                        myProductTableModel2.lista4.remove(0);
                    }
                }
                String idventa = jComboBox6.getSelectedItem().toString();
                int idventaint = Integer.parseInt(idventa);
                List<Detalleventa> detalledelaventa = mycontroladoralmacen.sacadetalleventa(idventaint);
                for (int i = 0; i < detalledelaventa.size(); i++) {
                    int idproducto = detalledelaventa.get(i).getProducto().getIdproducto();
                    Producto p = mycontroladoralmacen.sacaproductobyid(idproducto);
                    myProductTableModel2.lista2.add(p);
                    myProductTableModel2.lista3.add("" + detalledelaventa.get(i).getCantidad());
                    myProductTableModel2.lista4.add("" + detalledelaventa.get(i).getCantidad());
                    Date fechavencimiento = new Date();
                    fechavencimiento.setMonth(fechavencimiento.getMonth() + 1);
                    //myProductTableModel2.fecha1.add(fechavencimiento);
                    //myProductTableModel2.fecha1.add("" + fechavencimiento);
                    SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");;
                    String fecha;
                    fecha = sdf.format(fechavencimiento);
                    myProductTableModel2.fecha1.add(fecha);
                }
                usacompra = 0;
                usaventa = idventaint;
                myProductTableModel2.fireTableChanged(null);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No hay ninguna venta por el momento");
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cmbMotivo;
    private javax.swing.JComboBox cmbTipo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JComboBox jComboBox5;
    private javax.swing.JComboBox jComboBox6;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
