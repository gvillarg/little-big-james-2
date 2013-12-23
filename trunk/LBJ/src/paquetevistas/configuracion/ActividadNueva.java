/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paquetevistas.configuracion;

import controlador.almacen.controladoralmacen;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import modelos.Actividad;
//import modelos.Actividadxproducto;
//import modelos.ActividadxproductoId;
import modelos.Producto;
import modelos.Tipoproducto;
import paquetevistas.Escritorio;
import paquetevistas.Login;

/**
 *
 * @author mariofcandia
 */
public class ActividadNueva extends javax.swing.JInternalFrame {

    /**
     * Creates new form ActividadNueva
     */
    public static controladoralmacen mycontroladoralmacen = new controladoralmacen();
    ProductTableModel myProductTableModel = new ProductTableModel();
    ProductTableModel2 myProductTableModel2 = new ProductTableModel2();
    public static Producto productoareceta;
    public static RecetaNueva recetanueva;

    class ProductTableModel extends AbstractTableModel {

        List<Producto> pro = new ArrayList<Producto>();

        public ProductTableModel() {
            Actividad act = lbj.LBJ.Login.escritorio.actividades.actividadeditar;
            pro = mycontroladoralmacen.sacaproductosdependeactividad(act.getIdactividad());
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
        List<Producto> entradas = new ArrayList<Producto>();
        ArrayList<String> entradasalida = new ArrayList<String>();
        List<Producto> entradaauxiliar = new ArrayList<Producto>();

        public ProductTableModel2() {
            Actividad act = lbj.LBJ.Login.escritorio.actividades.actividadeditar;
            System.out.println(act.getNombre());
            pro2 = mycontroladoralmacen.sacaproductosalidaactividad(act.getIdactividad());
            for (int i = 0; i < pro2.size(); i++) {
                System.out.println(pro2.get(i).getNombre());
                entradasalida.add("Salida");
            }
            List<modelos.Receta> rece = mycontroladoralmacen.sacarecetabyactividad(act.getIdactividad());
            for (int j = 0; j < rece.size(); j++) {
                entradaauxiliar = mycontroladoralmacen.sacaproductosdereceta(rece.get(j).getIdreceta());
                for (int m = 0; m < entradaauxiliar.size(); m++) {
                    int encontro = 0;
                    if (entradas.size() > 0) {
                        for (int e = 0; e < entradas.size(); e++) {
                            if (entradas.get(e).getIdproducto() == entradaauxiliar.get(m).getIdproducto()) {
                                encontro = 1;
                                break;
                            }
                        }
                        if (encontro == 0) {
                            entradas.add(entradaauxiliar.get(m));
                        }
                    } else {
                        entradas.add(entradaauxiliar.get(m));
                    }
                }
            }
            for (int j = 0; j < entradas.size(); j++) {
                pro2.add(entradas.get(j));
                entradasalida.add("Entrada");
            }

        }
        String[] titles = {"Codigo", "Nombre", "Tipo", "Unidad", "T.Movimiento"};

        public int getRowCount() {
            return pro2.size();
        }

        public int getColumnCount() {
            return 5;
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
                    res = "" + p.getIdproducto();
                    break;
                case 1:
                    res = p.getNombre();
                    break;
                case 2:
                    tipo = p.getTipoproducto().getIdtipoproducto();
                    nombre = mycontroladoralmacen.sacanombretipo(tipo);
                    res = nombre;
                    break;
                case 3:
                    unidad = p.getUnidadmedida().getIdunidadmedida();
                    unidadnombre = mycontroladoralmacen.sacanombreunidad(unidad);
                    res = unidadnombre;
                    break;
                case 4:
                    res = entradasalida.get(rowIndex);
                    break;
            }
            return res;
        }
//        public boolean isCellEditable(int rowIndex, int columnIndex) {
//            if (columnIndex == 2) {
//                return true;
//            }
//            return false;
//        }
//
//        public void setValueAt(Object value, int row, int col) {
//            String cadena = value.toString();
//            lista3.set(row, cadena);
//        }

        public String getColumnName(int col) {
            return titles[col];
        }
    }

    public ActividadNueva() {
        initComponents();
        loadComponents();
//        jTable1.setModel(myProductTableModel);
        jTable2.setModel(myProductTableModel2);
    }

    void loadComponents() {

        Actividad a = Login.escritorio.actividades.actividadeditar;

        jTextField1.setText(a.getNombre());
        jTextArea1.setText(a.getDescripcion());

//        jComboBox1.removeAllItems();
//        List<Tipoproducto> tiposproductos = new ArrayList<Tipoproducto>();
//        tiposproductos = mycontroladoralmacen.sacatipoproducto();
//        if (a.getIdactividad() == 4) {
//            for (int i = 0; i < tiposproductos.size(); i++) {
//                if (tiposproductos.get(i).getIdtipoproducto() == 3) {
//                    jComboBox1.addItem(tiposproductos.get(i).getNombre());
//                    break;
//                }
//            }
//        } else {
//            for (int i = 0; i < tiposproductos.size(); i++) {
//                if (tiposproductos.get(i).getIdtipoproducto() == 2) {
//                    jComboBox1.addItem(tiposproductos.get(i).getNombre());
//                    break;
//                }
//            }
//        }
//        jComboBox1.setSelectedIndex(0);
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
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setClosable(true);
        setTitle("Actividad Nueva");

        jLabel1.setText("Nombre:");

        jLabel2.setText("Descripcion:");

        jTextField1.setEditable(false);
        jTextField1.setText("Horneado");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Detalle Actividad"));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Galleta", "PI", "Salida", "100", "Unidades"},
                {"Arina", "MP", "Entrada", "200", "Kg"},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Producto", "Tipo", "T.Actividad", "Cantidad", "Unidad"
            }
        ));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable2);

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .add(jScrollPane3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 131, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(13, 13, 13))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel5Layout.createSequentialGroup()
                .add(0, 11, Short.MAX_VALUE)
                .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(11, 11, 11))
        );

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(23, 23, 23)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jLabel1)
                        .add(34, 34, 34)
                        .add(jTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 183, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(jTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel2)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 48, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(jPanel5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
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

    public void refreshProductTable2() {
        Actividad act = lbj.LBJ.Login.escritorio.actividades.actividadeditar;
            System.out.println(act.getNombre());
            myProductTableModel2.pro2 = mycontroladoralmacen.sacaproductosalidaactividad(act.getIdactividad());
            for (int i = 0; i < myProductTableModel2.pro2.size(); i++) {
                System.out.println(myProductTableModel2.pro2.get(i).getNombre());
                myProductTableModel2.entradasalida.add("Salida");
            }
            List<modelos.Receta> rece = mycontroladoralmacen.sacarecetabyactividad(act.getIdactividad());
            for (int j = 0; j < rece.size(); j++) {
                myProductTableModel2.entradaauxiliar = mycontroladoralmacen.sacaproductosdereceta(rece.get(j).getIdreceta());
                for (int m = 0; m < myProductTableModel2.entradaauxiliar.size(); m++) {
                    int encontro = 0;
                    if (myProductTableModel2.entradas.size() > 0) {
                        for (int e = 0; e < myProductTableModel2.entradas.size(); e++) {
                            if (myProductTableModel2.entradas.get(e).getIdproducto() == myProductTableModel2.entradaauxiliar.get(m).getIdproducto()) {
                                encontro = 1;
                                break;
                            }
                        }
                        if (encontro == 0) {
                            myProductTableModel2.entradas.add(myProductTableModel2.entradaauxiliar.get(m));
                        }
                    } else {
                        myProductTableModel2.entradas.add(myProductTableModel2.entradaauxiliar.get(m));
                    }
                }
            }
            for (int j = 0; j < myProductTableModel2.entradas.size(); j++) {
                myProductTableModel2.pro2.add(myProductTableModel2.entradas.get(j));
                myProductTableModel2.entradasalida.add("Entrada");
            }
        myProductTableModel2.fireTableChanged(null);
    }

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int row = jTable2.getSelectedRow();
            String ids = jTable2.getValueAt(row, 0).toString();
            int id = Integer.parseInt(ids);
            productoareceta = mycontroladoralmacen.sacaproductobyid(id);
            String tipo = jTable2.getValueAt(row, 4).toString();
            if (tipo.equalsIgnoreCase("Salida")) {
                if (recetanueva == null || recetanueva.isClosed()) {
                    recetanueva = new RecetaNueva();
                    Escritorio.actividades.actividadnueva.getDesktopPane().add(recetanueva);
                    recetanueva.setVisible(true);
                } else {
                    try {
                        recetanueva.setSelected(true);
                    } catch (Exception e) {
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Solo puede editar a las salidas del proceso");
            }
        }
    }//GEN-LAST:event_jTable2MouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
