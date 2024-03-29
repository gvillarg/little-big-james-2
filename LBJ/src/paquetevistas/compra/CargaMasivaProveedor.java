/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package paquetevistas.compra;

import controlador.almacen.controladoralmacen;
import controlador.compra.controladorcompra;
import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import jxl.Sheet;
import jxl.Workbook;
import java.util.List;
import jxl.DateCell;
import java.util.Date;
import modelos.ProductoxproveedorId;
import static paquetevistas.configuracion.CargaMasivaMaquinas.mycontroladorconfiguracion;

/**
 *
 * @author Jacklin
 */
public class CargaMasivaProveedor extends javax.swing.JInternalFrame {
    
     public static controladoralmacen mycontroladoralmacen = new controladoralmacen();
     public static controladorcompra mycontroladorcompras = new controladorcompra();

    /**
     * Creates new form CargaMasivaProveedor
     */
    public CargaMasivaProveedor() {
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

        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLabel1 = new javax.swing.JLabel();
        txtNombreArchivo = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setTitle("Carga Masiva de Proveedores");

        jLayeredPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Seleccione Archivo"));

        jLabel1.setText("Archivo:");

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

        jLabel3.setText("Cargar");

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombreArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtNombreArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addContainerGap())
        );
        jLayeredPane1.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(txtNombreArchivo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jButton1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jButton2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        JFileChooser file = new JFileChooser();
        int opcion = file.showOpenDialog(this);
        if (opcion == JFileChooser.APPROVE_OPTION) {
            this.txtNombreArchivo.setText(file.getSelectedFile().getPath());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       if ("".equals(txtNombreArchivo.getText())) {
            JOptionPane.showMessageDialog(this, "Ruta de archivo no especificada.");
            return;
        }
        
        if (JOptionPane.showConfirmDialog(this,
                "¿Está seguro de agregar los Proveedores desde " + txtNombreArchivo.getText() + "?", "Carga Masiva de Proveedores", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION){
            //Empiezo a reccorre
            int fila = 0;
            int col = 0;
            try{
                Workbook archivoExcel = Workbook.getWorkbook(new File(txtNombreArchivo.getText()));
                
                Sheet hoja = archivoExcel.getSheet(0);
                int numColumnas = hoja.getColumns();
                int numFilas = hoja.getRows();
                
                List<modelos.Proveedor> proveedores = new ArrayList<modelos.Proveedor>();
                List<modelos.Proveedor> proveedoresexistentes = new ArrayList<modelos.Proveedor>();
                List<modelos.Productoxproveedor> productos =  new ArrayList<modelos.Productoxproveedor>();
                
                int existencia;
                int estadoid;
                
                 for (fila = 2; fila < numFilas; fila++) {
                    existencia=0;
                    col=0;
                    
                    modelos.Proveedor proveedor = new modelos.Proveedor();
                    String razonSocial, direccion, correo, nombrecontacto, estado, producto;
                    int telefono, dnicontacto, telefonocontacto;
                    Long ruc;

                    if(!((hoja.getCell(col, fila).getContents().toString()).isEmpty())){
                        razonSocial= hoja.getCell(col++, fila).getContents();
                        direccion = hoja.getCell(col++, fila).getContents();
                        telefono= Integer.parseInt(hoja.getCell(col++, fila).getContents());
                        correo = hoja.getCell(col++, fila).getContents();
                        dnicontacto = Integer.parseInt(hoja.getCell(col++, fila).getContents());
                        nombrecontacto = hoja.getCell(col++, fila).getContents();
                        telefonocontacto = Integer.parseInt(hoja.getCell(col++, fila).getContents());
                        ruc = Long.parseLong(hoja.getCell(col++, fila).getContents());
                        estado = hoja.getCell(col++, fila).getContents();

                        proveedor.setRazonsocial(razonSocial);
                        proveedor.setDireccion(direccion);
                        proveedor.setTelefono(telefono);
                        proveedor.setCorreo(correo);
                        proveedor.setDnicontacto(dnicontacto);
                        proveedor.setNombrecontacto(nombrecontacto);
                        proveedor.setTelefonocontacto(telefonocontacto);
                        proveedor.setRuc(ruc);
                        proveedor.setEstado(estado);

                        existencia = mycontroladorconfiguracion.verificaexistenciaproveedor(ruc);
                        if (existencia == 1) {
                            proveedoresexistentes.add(proveedor);
                        } else {
                            proveedores.add(proveedor);
                        }
                    }

                 }
                 
                   //AGREGO SEGUN CONDICIONES
                    if(proveedores.size() > 0 && proveedoresexistentes.size()==0){
                        mycontroladorconfiguracion.guardaListaproveedores(proveedores);
                        
                        fila = 0;
                        col = 0;

                        hoja = archivoExcel.getSheet(0);
                        numColumnas = hoja.getColumns();
                        numFilas = hoja.getRows();
                        modelos.Productoxproveedor prodxprov=new  modelos.Productoxproveedor();
                        String producto;
                        int cantidad,fila2=0;
                        float precio; 
                        Date strfechavigencia;
                        DateCell fechavigencia;
                        
                        for (fila = 2; fila < numFilas; fila++){
                             col = 0;
                             
                           if(!((hoja.getCell(col, fila).getContents().toString()).isEmpty())){
                                //SACO EL PRIMER PRODUCTO
                                String razonSocial= hoja.getCell(col, fila).getContents();
                                modelos.Proveedor prov = mycontroladorcompras.sacaproveedorbyrazon(razonSocial);
                                
                                
                                int col2=9;
                                producto = hoja.getCell(col2++, fila).getContents();
                                cantidad = Integer.parseInt(hoja.getCell(col2++, fila).getContents());
                                precio = Float.parseFloat(hoja.getCell(col2++, fila).getContents().replace(",", "."));
                                fechavigencia = (DateCell) hoja.getCell(col2++, fila);
                                strfechavigencia = fechavigencia.getDate();
//                                modelos.Producto pr = mycontroladoralmacen.sacaproductobynombre(producto);
//                                
//                                ProductoxproveedorId id = new ProductoxproveedorId();
//                                id.setIdproducto(pr.getIdproducto());
//                                id.setIdproveedor(prov.getIdproveedor());
//    
//                                prodxprov.setId(id);
//                                prodxprov.setProducto(pr);
//                                prodxprov.setProveedor(prov);
//                                prodxprov.setFecha(strfechavigencia);
//                                prodxprov.setPrecio(precio);
//                                prodxprov.setCantidadmaxima(cantidad);
                                
                                mycontroladorcompras.agregarproductosxprov(prov, producto, cantidad, precio, strfechavigencia);
                                //productos.add(prodxprov);
                                //SACO LOS DEMAS PRODUCTOS
                                if((hoja.getCell(col, fila+1).getContents().toString()).isEmpty()){
                                    fila2=fila+1;
                                    int col3;
                                    
                                    while((hoja.getCell(col, fila2).getContents().toString()).isEmpty() && fila2<numFilas){
                                        col3=9;
                                        producto = hoja.getCell(col3++, fila2).getContents();
                                        cantidad = Integer.parseInt(hoja.getCell(col3++, fila2).getContents());
                                        precio = Float.parseFloat(hoja.getCell(col3++, fila2).getContents().replace(",","."));
                                        fechavigencia = (DateCell) hoja.getCell(col3++, fila2);
                                        strfechavigencia = fechavigencia.getDate();
//                                        modelos.Producto pr2 = mycontroladoralmacen.sacaproductobynombre(producto);
//
//                                        ProductoxproveedorId id2 = new ProductoxproveedorId();
//                                        id2.setIdproducto(pr2.getIdproducto());
//                                        id2.setIdproveedor(prov.getIdproveedor());
//
//                                        prodxprov.setId(id2);
//                                        System.out.println(id2.getIdproducto());
//                                        System.out.println(id2.getIdproveedor());
//                                        prodxprov.setProducto(pr2);
//                                        prodxprov.setProveedor(prov);
//                                        prodxprov.setFecha(strfechavigencia);
//                                        prodxprov.setPrecio(precio);
//                                        prodxprov.setCantidadmaxima(cantidad);
//
//                                        productos.add(prodxprov);
                                        mycontroladorcompras.agregarproductosxprov(prov, producto, cantidad, precio, strfechavigencia);
                                        fila2++;
                                        if(fila2== numFilas || fila2> numFilas) break;
                                    }
                                }
           
                        } 
                        }
                        //mycontroladorconfiguracion.guardaListaProductosProveedor(productos);
                        JOptionPane.showMessageDialog(this, "Se agregaron los Proveedores y sus Productos correctamente.");
                        lbj.LBJ.Login.escritorio.proveedor.refreshSupplierTable();
                        this.dispose();
                    } else if (proveedores.size() == 0 && proveedoresexistentes.size() > 0) {
                        JOptionPane.showMessageDialog(this, "Los Proveedores que intenta guardar ya existen.");
                        this.dispose();
                    } else if(proveedores.size() > 0 && proveedoresexistentes.size()>0){
                        mycontroladorconfiguracion.guardaListaproveedores(proveedores);
                        JOptionPane.showMessageDialog(this, "Se agregaron los Proveedores nuevos exitosamente. Algunos ya existian");
                         lbj.LBJ.Login.escritorio.proveedor.refreshSupplierTable();
                        this.dispose();
                    } else if (proveedores.size() == 0 && proveedoresexistentes.size() > 0) {
                    JOptionPane.showMessageDialog(this, "El archivo que intenta cargar esta vacio.");
                    this.dispose();
                    }
                 
                 
                
            } catch(Exception ioe){
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
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JTextField txtNombreArchivo;
    // End of variables declaration//GEN-END:variables
}
