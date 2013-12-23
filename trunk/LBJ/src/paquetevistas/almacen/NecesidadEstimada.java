/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paquetevistas.almacen;

import Seguridad.Controlador.controladorseguridad;
import controlador.almacen.controladoralmacen;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelos.Detalleordenproduccion;
import modelos.Productoxreceta;
import modelos.Usuario;
import paquetevistas.Escritorio;
import static paquetevistas.almacen.CalcularStockMP.necesidadestimada;

/**
 *
 * @author Karina
 */
public class NecesidadEstimada extends javax.swing.JInternalFrame {

    /**
     * Creates new form NecesidadEstimada
     */
    List<Detalleordenproduccion> detalleOrdenxgalleta = lbj.LBJ.Login.escritorio.stock.plan;
    //int idordeninicial= lbj.LBJ.Login.escritorio.stock.idorden;
    
    public static controladoralmacen mycontroladoralmacen = new controladoralmacen();
    //int idgalleta1 = mycontroladoralmacen.sacaproductobynombre("Galleta Sbelt").getIdproducto();
    //int idgalleta2 = mycontroladoralmacen.sacaproductobynombre("Galleta Super Rellenita").getIdproducto();
    //int idgalleta3 = mycontroladoralmacen.sacaproductobynombre("Galleta Smile").getIdproducto();
    GalletaTableModel Galleta1 = new GalletaTableModel(); 

   
    public static List<modelos.Productoxreceta> orden ;
    public static NecesidadReal stockreal;
    public static List<Double> cantidadesplan = new ArrayList<Double>();
    
    //Para el registro de Log
    public static Usuario usuario = lbj.LBJ.Login.usuario;
    public static controladorseguridad mycontroladorseguridad = new controladorseguridad();
    

    class GalletaTableModel extends AbstractTableModel {

        List<modelos.Productoxreceta> recetatotal = new ArrayList<modelos.Productoxreceta>();
        List<modelos.Productoxreceta> receta = new ArrayList<modelos.Productoxreceta>();
        List<Integer> cantidadesgalletas = new ArrayList<Integer>();
        float cantidad;
        float cantidadamult=0;
        int idprod;
        int indicecantidades=0;
        
        public GalletaTableModel() {
            orden = new ArrayList<>();
            for(int k=0; k<detalleOrdenxgalleta.size();k++ ){
                idprod=detalleOrdenxgalleta.get(k).getProducto().getIdproducto();
                Productoxreceta pr = mycontroladoralmacen.sacaningunoPT(idprod, detalleOrdenxgalleta.get(k).getCantidad());
                recetatotal.add(pr);
                //cantidadesgalletas.add(detalleOrdenxgalleta.get(k).getCantidadaproducir());
                
                //Aqui empiezo a agregar las recetas de cada producto
                
                receta = mycontroladoralmacen.sacaRecetaporGalleta(idprod);
                if(receta != null){
                    for(int i=0; i<receta.size(); i++)
                    receta.get(i).setCantidad(receta.get(i).getCantidad()*pr.getCantidad());
                    int indice=0;
                    for (int i = 0; i < receta.size(); i++) {
                        List<modelos.Productoxreceta> recetaintermedios;
                        modelos.Producto p = mycontroladoralmacen.sacaproductobyid(receta.get(i).getId().getIdproducto());  
                        if (p.getTipoproducto().getIdtipoproducto() == 3) {
                            recetaintermedios = mycontroladoralmacen.sacaRecetaporGalleta(p.getIdproducto());
                            modelos.Productoxreceta pr2 = receta.get(i);
                            receta.remove(i);
                            i--;
                            for (int j = 0; j < recetaintermedios.size(); j++) {
                                //PRIMERO SE BUSCA
                                indice = 0;
                                while (indice< receta.size() && recetaintermedios.get(j).getProducto().getIdproducto() != receta.get(indice).getProducto().getIdproducto())
                                       {
                                    indice++;
                                }
                                //SI ENCUENTRA AGREGO CANTIDAD, SINO SE AGREGA AL FINAL
                                if (indice < receta.size()) {
                                    receta.get(indice).setCantidad(receta.get(indice).getCantidad() + recetaintermedios.get(j).getCantidad() * pr2.getCantidad());
                                } else {
                                    recetaintermedios.get(j).setCantidad(recetaintermedios.get(j).getCantidad() * pr2.getCantidad());
                                    receta.add(recetaintermedios.get(j));
                                }
                            }
                            //receta.addAll(recetaintermedios);
                        }
                    }
                    recetatotal.addAll(receta);
                    orden.addAll(receta);
                };
                
 
            }
            
            
        }
        String[] titles = {"Nombre", "Cantidad", "Unidad"};

        @Override
        public int getRowCount() {
            return recetatotal.size();
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            String res = null;
            String cantaux = null; 
            modelos.Productoxreceta p = recetatotal.get(rowIndex);
            //cantidad = recetatotal.get(rowIndex).getCantidad();
            switch (columnIndex) {
                case 0:
                    if(p.getProducto().getTipoproducto().getIdtipoproducto()!= 4)
                        res = "         " + p.getProducto().getNombre();
                    else  res = "" + p.getProducto().getNombre();
                    break;
                case 1:
                        res = ""+p.getCantidad();
                        float aux= (p.getCantidad());
                        cantaux = Float.toString(aux);
                        int ubi = cantaux.indexOf(".");
                        cantaux = cantaux.substring(0, ubi+2);
                        p.setCantidad(Float.parseFloat(cantaux));
                        res = "" + (p.getCantidad());
                    
                    break;
                case 2:
                    int unidad = p.getProducto().getUnidadmedida().getIdunidadmedida();
                    String unidadnombre = mycontroladoralmacen.sacanombreunidad(unidad);
                    res = unidadnombre;    
                    break;
            }
            return res;
        }

        public String getColumnName(int col) {
            return titles[col];
        }
    }

    public NecesidadEstimada() {
        initComponents();
        jTable2.setModel(Galleta1);
        
        mycontroladorseguridad.insertarlog(usuario, new Date(), "Consultar", "Productoxreceta", "Consultar Productoxreceta");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        jLabel2.setText("Galletas Soda Sbelt");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Necesidad Estimada de Materias Primas");

        jLabel1.setText("Recetas por Productos");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nombre", "Tipo", "Cantidad", "Unidad"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/next 2.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/prev 2.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel5.setText("Calcular Necesidad Real");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 332, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(37, 37, 37))))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addGap(274, 274, 274)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addContainerGap(15, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(53, 53, 53)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(93, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        stockreal = new NecesidadReal();
        Escritorio.stock.getDesktopPane().add(stockreal);
        stockreal.setVisible(true);
        this.setVisible(false);

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Escritorio.stock.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
