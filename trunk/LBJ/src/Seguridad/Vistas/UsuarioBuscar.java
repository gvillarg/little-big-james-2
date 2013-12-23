/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Seguridad.Vistas;

import Seguridad.Controlador.controladorseguridad;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelos.Usuario;
import modelos.Empleado;
import modelos.Rol;
import paquetevistas.Escritorio;


import javax.swing.JOptionPane;
import paquetevistas.Escritorio;
import static Seguridad.Vistas.RolBuscar.mycontroladorseguridad;


/**
 *
 * @author Saori
 */
public class UsuarioBuscar extends javax.swing.JInternalFrame {

    /**
     * Creates new form UsuarioBuscar
     */
    public static Usuario usuario = lbj.LBJ.Login.usuario;
    public static UsuarioNuevo usuarionuevo;
    public static UsuarioEditar usuarioeditar;
    UserTableModel myUserTableModel=new UserTableModel();
    public static controladorseguridad mycontroladorseguridad = new controladorseguridad();
    
    class UserTableModel extends AbstractTableModel {

        List<Usuario> usuarios = new ArrayList<Usuario>();

        public UserTableModel() {
//            usuarios = mycontroladorseguridad.sacausuarios();
            usuarios = mycontroladorseguridad.buscarusuarios("","","");
        }
        String[] titles = {"Codigo", "Usuario", "Rol", "Estado"};

        public int getRowCount() {
            return usuarios.size();
        }

        public int getColumnCount() {
            return 4;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            String res = null;
            int idrol=0;
            String nombre = null;
            Usuario u = usuarios.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    res = "" + u.getIdempleado();
                    break;
                case 1:
                    res = "" + u.getNombreusuario();
                    break;
                case 2:
//                    res = "" + u.getRol().getNombre();
                    idrol = u.getRol().getIdrol();
                    nombre = mycontroladorseguridad.sacanombrerol(idrol);
                    res = nombre;
                    break;
                case 3:
                    if(u.getEstado()==1) res="Activo";
                    else res="Inactivo";
                    
                    break;
            }
            return res;
        }

        public String getColumnName(int col) {
            return titles[col];
        }
    }
    
    
    public UsuarioBuscar() {
        initComponents();
        llenarCombo();
        tblResultadoUsuario.setModel(myUserTableModel);
        refreshUserTable();
        mycontroladorseguridad.insertarlog(usuario, new Date(), "Consultar", "Usuario", "Consultar Usuarios");
        
    }
    
    public void refreshUserTable() {
//        myUserTableModel.usuarios = mycontroladorseguridad.sacausuarios();
        myUserTableModel.usuarios = mycontroladorseguridad.buscarusuarios("","","");
        myUserTableModel.fireTableChanged(null);
    }
    
    public void llenarCombo() {
        List<Rol> roles = new ArrayList<Rol>();
//        roles = mycontroladorseguridad.sacaroles();
        roles = mycontroladorseguridad.buscarroles("","Activo");
        for(Rol r : roles){
            cmbRolUsuario.addItem(r.getNombre());
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
        btnAgregarUsuario = new javax.swing.JButton();
        btnBuscarUsuario = new javax.swing.JButton();
        btnEliminarUsuario = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblResultadoUsuario = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtNombreUsuario = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cmbRolUsuario = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        cmbEstadoUsuario = new javax.swing.JComboBox();

        setClosable(true);
        setIconifiable(true);
        setTitle("Mantenimiento de Usuarios");

        btnAgregarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/file plus.png"))); // NOI18N
        btnAgregarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarUsuarioActionPerformed(evt);
            }
        });

        btnBuscarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/search.png"))); // NOI18N
        btnBuscarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarUsuarioActionPerformed(evt);
            }
        });

        btnEliminarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/trash.png"))); // NOI18N
        btnEliminarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarUsuarioActionPerformed(evt);
            }
        });

        jLabel1.setText("Agregar");

        jLabel2.setText("Buscar");

        jLabel3.setText("Eliminar");

        tblResultadoUsuario.setModel(new javax.swing.table.DefaultTableModel(
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
        tblResultadoUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblResultadoUsuarioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblResultadoUsuario);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Criterios de Búsqueda"));

        jLabel4.setText("Nombre:");

        txtNombreUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreUsuarioActionPerformed(evt);
            }
        });

        jLabel7.setText("Rol:");

        cmbRolUsuario.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar" }));
        cmbRolUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbRolUsuarioActionPerformed(evt);
            }
        });

        jLabel9.setText("Estado:");

        cmbEstadoUsuario.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar", "Activo", "Inactivo" }));
        cmbEstadoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEstadoUsuarioActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel4)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(txtNombreUsuario, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(38, 38, 38)
                .add(jLabel7)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cmbRolUsuario, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(34, 34, 34)
                .add(jLabel9)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cmbEstadoUsuario, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4)
                    .add(txtNombreUsuario, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(cmbRolUsuario, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel7)
                    .add(jLabel9)
                    .add(cmbEstadoUsuario, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel1Layout.createSequentialGroup()
                        .add(24, 24, 24)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel1)
                            .add(btnAgregarUsuario, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(21, 21, 21)
                                .add(jLabel2)
                                .add(29, 29, 29))
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(btnBuscarUsuario, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(18, 18, 18)))
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(4, 4, 4)
                                .add(jLabel3))
                            .add(btnEliminarUsuario, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jScrollPane1)))
                .add(21, 21, 21))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(25, 25, 25)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(btnBuscarUsuario, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(btnAgregarUsuario, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(btnEliminarUsuario, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(jLabel2)
                    .add(jLabel3))
                .add(30, 30, 30)
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(33, 33, 33)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 89, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(43, 43, 43))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(133, 133, 133))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarUsuarioActionPerformed
        // TODO add your handling code here:
        if(usuarionuevo == null || usuarionuevo.isClosed()){
            usuarionuevo = new UsuarioNuevo();
            Escritorio.usuario.getDesktopPane().add(usuarionuevo);
            usuarionuevo.setVisible(true);
        }else
            try{
                usuarionuevo.setSelected(true);
            }catch(Exception e){} 
    }//GEN-LAST:event_btnAgregarUsuarioActionPerformed

    private void cmbRolUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbRolUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbRolUsuarioActionPerformed

    private void btnEliminarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarUsuarioActionPerformed

        // TODO add your handling code here:
        if(tblResultadoUsuario.getSelectedRow()!=-1){
            int resp = JOptionPane.showConfirmDialog(null, "Está seguro de eliminar el usuario?","Mensaje",JOptionPane.INFORMATION_MESSAGE);
            if(resp==0){
                int row=tblResultadoUsuario.getSelectedRow();
                int column=tblResultadoUsuario.getSelectedColumn();
                mycontroladorseguridad.eliminarUsuario(row,column);
                mycontroladorseguridad.insertarlog(usuario, new Date(), "Eliminar", "Usuario", "Eliminar Usuario");
                //volver a cargar la tabla
                myUserTableModel.usuarios = mycontroladorseguridad.buscarusuarios("","","");
                myUserTableModel.fireTableChanged(null);
            }
        }
        else
            JOptionPane.showMessageDialog(null, "No ha seleccionado ningún elemento a eliminar.","Mensaje",JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnEliminarUsuarioActionPerformed

    private void btnBuscarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarUsuarioActionPerformed
        // TODO add your handling code here:
        myUserTableModel.usuarios = mycontroladorseguridad.buscarusuarios(txtNombreUsuario.getText(),cmbRolUsuario.getSelectedItem().toString(),cmbEstadoUsuario.getSelectedItem().toString());
        myUserTableModel.fireTableChanged(null);
        mycontroladorseguridad.insertarlog(usuario, new Date(), "Consultar", "Usuario", "Consultar Usuarios");
    }//GEN-LAST:event_btnBuscarUsuarioActionPerformed

    private void txtNombreUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreUsuarioActionPerformed

    private void tblResultadoUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblResultadoUsuarioMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount()==2){
            int row=tblResultadoUsuario.getSelectedRow();
            int column=tblResultadoUsuario.getSelectedColumn();
            int codigo=Integer.parseInt(tblResultadoUsuario.getValueAt(row, 0).toString());
            Usuario usuario=mycontroladorseguridad.getUsuarioById(codigo);
            Empleado empleado=mycontroladorseguridad.getEmpleadoById(codigo);
            usuarioeditar=new UsuarioEditar(usuario,empleado);
            Escritorio.usuario.getDesktopPane().add(usuarioeditar);
            usuarioeditar.setVisible(true);
        }
    }//GEN-LAST:event_tblResultadoUsuarioMouseClicked

    private void cmbEstadoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEstadoUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbEstadoUsuarioActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarUsuario;
    private javax.swing.JButton btnBuscarUsuario;
    private javax.swing.JButton btnEliminarUsuario;
    private javax.swing.JComboBox cmbEstadoUsuario;
    private javax.swing.JComboBox cmbRolUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblResultadoUsuario;
    private javax.swing.JTextField txtNombreUsuario;
    // End of variables declaration//GEN-END:variables
}