/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paquetevistas.ventas;

import controlador.almacen.controladoralmacen;
import controlador.compra.controladorcompra;
import controlador.configuracion.controladorconfiguracion;
import controlador.ventas.ControllerClientes;
import controlador.ventas.ControllerVentas;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import jxl.Sheet;
import jxl.Workbook;
import java.util.List;
import jxl.DateCell;

/**
 *
 * @author Karina
 */
public class CargaMasivaVentas extends javax.swing.JInternalFrame {

    /**
     * Creates new form CargaMasivaVentas
     * 
     */
    public static ControllerClientes mycontroladorclientes = new ControllerClientes();
    public static ControllerVentas mycontroladorventas= new ControllerVentas();
    public static controladoralmacen mycontroladoralmacen = new controladoralmacen();
     public static controladorconfiguracion mycontroladorconfiguracion = new controladorconfiguracion();
    
    public CargaMasivaVentas() {
        initComponents();
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
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setTitle("Carga Masiva Ventas");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Selecciona el Archivo"));

        jLabel1.setText("Archivo");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/search.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/save.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setText("Buscar");

        jLabel3.setText("Guardar");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel2)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel3))
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Busca el Archivo
        JFileChooser file = new JFileChooser();
        int opcion = file.showOpenDialog(this);
        if (opcion == JFileChooser.APPROVE_OPTION) {
            this.jTextField1.setText(file.getSelectedFile().getPath());
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //
        if ("".equals(jTextField1.getText())) {
            JOptionPane.showMessageDialog(this, "Ruta de archivo no especificada.");
            return;
        }
        
        if (JOptionPane.showConfirmDialog(this,
                "¿Está seguro de agregar el Historial de Ventas desde " + jTextField1.getText() + "?", "Carga Masiva de Ventas", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION){
            int fila = 0;
            int col = 0;
            try{
                //Primero guardo la venta
                Workbook archivoExcel = Workbook.getWorkbook(new File(jTextField1.getText()));
                
                Sheet hoja = archivoExcel.getSheet(0);
                int numColumnas = hoja.getColumns();
                int numFilas = hoja.getRows();
                
                List<modelos.Venta> listaventas = new ArrayList<modelos.Venta>();
                
                int existencia;
                int estadoid;
                modelos.Venta ultimaventa = new modelos.Venta();
                
                for (fila = 2; fila < numFilas; fila++) {
                    existencia=0;
                    col=0;
                    
                    modelos.Venta venta = new modelos.Venta();

                    float precio, igv;
                    Date fecharegistro, fechaentregaacordado, fechaentregareal;
                    DateCell fecharegistrocell, fechaentregaacordadocell, fechaentregarealcell;
                    String cliente, estado, producto;
                    float cantidad, preciounitario;
                    Date fecha;
                    DateCell fechacell;
                    int ultimoid;
                    
                    
                    if(!((hoja.getCell(col, fila).getContents().toString()).isEmpty())){
                        precio = Float.parseFloat(hoja.getCell(col++, fila).getContents().replace(",", "."));
                        igv=Float.parseFloat(hoja.getCell(col++, fila).getContents().replace(",", "."));
                        fecharegistrocell = (DateCell) hoja.getCell(col++, fila);
                        fecharegistro = fecharegistrocell.getDate();
                        fechaentregaacordadocell = (DateCell) hoja.getCell(col++, fila);
                        fechaentregaacordado = fechaentregaacordadocell.getDate();
                        fechaentregarealcell = (DateCell) hoja.getCell(col++, fila);
                        fechaentregareal = fechaentregarealcell.getDate();
                        cliente = hoja.getCell(col++, fila).getContents();
                        estado = hoja.getCell(col++, fila).getContents();
                        
                        modelos.Cliente cli = mycontroladorclientes.sacaClientexNombre(cliente);
                        venta.setCliente(cli);
                        venta.setFechaentregaacordada(fechaentregaacordado);
                        venta.setFechaentregareal(fechaentregareal);
                        venta.setFecharegistro(fecharegistro);
                        venta.setPrecioacordado(precio);
                        venta.setIgv(igv);
                        if (estado.equalsIgnoreCase("Cancelada"))
                            venta.setEstado("2");
                        else if(estado.equalsIgnoreCase("Despachado"))
                            venta.setEstado("1");
                        else venta.setEstado("0");
                        
                       ultimaventa = mycontroladorventas.guardaventa(venta);
                       ultimoid=ultimaventa.getIdventa();
                       
                       //AGREGO PRIMER DETALLE
                       producto= hoja.getCell(col++, fila).getContents();
                       modelos.Producto p = mycontroladoralmacen.sacaproductobynombre(producto);
                       cantidad = Float.parseFloat(hoja.getCell(col++, fila).getContents().replace(",", "."));
                       preciounitario = Float.parseFloat(hoja.getCell(col++, fila).getContents().replace(",", "."));
                       fechacell = (DateCell) hoja.getCell(col++, fila);
                       fecha = fechacell.getDate();
                       
                       mycontroladorventas.agregardetalleventa(ultimaventa, p, cantidad, preciounitario, fecha);
                       
                    }else{
                        //HAY MAS PRODUCTOS
                        int col2=7;
                        producto= hoja.getCell(col2++, fila).getContents();
                        modelos.Producto p = mycontroladoralmacen.sacaproductobynombre(producto);
                        cantidad = Float.parseFloat(hoja.getCell(col2++, fila).getContents().replace(",", "."));
                        preciounitario = Float.parseFloat(hoja.getCell(col2++, fila).getContents().replace(",", "."));
                        fechacell = (DateCell) hoja.getCell(col2++, fila);
                        fecha = fechacell.getDate();
                        
                        mycontroladorventas.agregardetalleventa(ultimaventa, p, cantidad, preciounitario, fecha);
                    }     
                }
                JOptionPane.showMessageDialog(this, "Se agrego el historial de Ventas correctamente.");
                lbj.LBJ.Login.escritorio.ventas.refresh();
                this.dispose();
                
            }catch(Exception ioe){
                ioe.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: En el Archivo, en la Fila " + (fila + 1) + " y Columna " + col);
            }   
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
