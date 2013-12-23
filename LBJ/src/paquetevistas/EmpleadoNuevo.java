/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paquetevistas;
import Seguridad.Controlador.controladorseguridad;
import controlador.almacen.controladoralmacen;
import controlador.configuracion.controladorconfiguracion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import modelos.Actividad;
import modelos.DesempenoId;
import modelos.Tipoempleado;
import modelos.Tipoproducto;
import modelos.Usuario;
import static paquetevistas.almacen.ProductoNuevo.mycontroladoralmacen;

/**
 *
 * @author Karina
 */
public class EmpleadoNuevo extends javax.swing.JInternalFrame {

    /**
     * Creates new form EmpleadoNuevo
     */
    public static controladorconfiguracion mycontroladorconfiguracion = new controladorconfiguracion();
    public static controladoralmacen mycontroladoralmacen = new controladoralmacen();
    
    //Para el registro de Log
    public static Usuario usuario = lbj.LBJ.Login.usuario;
    public static controladorseguridad mycontroladorseguridad = new controladorseguridad();
    
    public EmpleadoNuevo() {
        initComponents();
        loadComponents();
    }
    
    void loadComponents(){
        jComboBox1.removeAllItems();
        List<Tipoempleado> tiposemp = new ArrayList<Tipoempleado>();
        tiposemp = mycontroladorconfiguracion.sacatipoempleado();
        for (int i=0; i < tiposemp.size(); i++){
            jComboBox1.addItem(tiposemp.get(i).getNombre());
        }
        jComboBox1.setSelectedIndex(0);
        jTextField6.setEditable(false);
        jRadioButton1.setSelected(true);
        jRadioButton2.setSelected(false);
        jRadioButton3.setSelected(true);
        jRadioButton4.setSelected(false);
        jTextField8.setEditable(false);
         jTextField9.setEditable(false);
         jTextField5.setEditable(false);
         jTextField7.setEditable(false);
         jTextField14.setEditable(false);
         jTextField15.setEditable(false);
         jTextField20.setEditable(false);
         jTextField21.setEditable(false);
         jTextField17.setEditable(false);
         jTextField16.setEditable(false);
         jTextField12.setEditable(false);
         jTextField13.setEditable(false);
         jTextField19.setEditable(false);
         jTextField18.setEditable(false);
         jTextField13.setEditable(false);
         jTextField12.setEditable(false);
         jTextField10.setEditable(false);
         jTextField11.setEditable(false);
        
         //Validacion de Fechas
         jDateChooser1.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                // the docs of JDateChooser says that when the date is modified, a "date" property change is fired
                if (evt.getPropertyName().equals("date")) {
                    jDateChooser2.setMinSelectableDate(jDateChooser1.getDate());
                }
            }
        });
         
         jDateChooser2.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                // the docs of JDateChooser says that when the date is modified, a "date" property change is fired
                if (evt.getPropertyName().equals("date")) {
                    jDateChooser1.setMaxSelectableDate(jDateChooser2.getDate());
                }
            }
        });
         
         ///////
         
        jComboBox1.addActionListener(
                new ActionListener(){

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String tipo = jComboBox1.getSelectedItem().toString();
                        Tipoempleado tipoempleado = mycontroladorconfiguracion.sacaidtipo(tipo);
                        jTextField6.setText("" + tipoempleado.getSueldo());
                         
                         if(tipoempleado.getIdtipoempleado()==5){
                             jTextField8.setEditable(true);
                             jTextField9.setEditable(true);
                             jTextField5.setEditable(true);
                             jTextField7.setEditable(true);
                             jTextField14.setEditable(true);
                             jTextField15.setEditable(true);
                             jTextField20.setEditable(true);
                             jTextField21.setEditable(true);
                             jTextField17.setEditable(true);
                             jTextField16.setEditable(true);
                             jTextField12.setEditable(true);
                             jTextField13.setEditable(true);
                             jTextField19.setEditable(true);
                             jTextField18.setEditable(true);
                             jTextField10.setEditable(true);
                             jTextField11.setEditable(true);
                         }else{
                             jTextField8.setEditable(false);
                             jTextField9.setEditable(false);
                             jTextField5.setEditable(false);
                             jTextField7.setEditable(false);
                             jTextField14.setEditable(false);
                             jTextField15.setEditable(false);
                             jTextField20.setEditable(false);
                             jTextField21.setEditable(false);
                             jTextField17.setEditable(false);
                             jTextField16.setEditable(false);
                             jTextField12.setEditable(false);
                             jTextField13.setEditable(false);
                             jTextField19.setEditable(false);
                             jTextField18.setEditable(false);
                             jTextField10.setEditable(false);
                             jTextField11.setEditable(false);
                         }
                    }
                
                }
                
                );
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jTextField6 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jTextField20 = new javax.swing.JTextField();
        jTextField21 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jTextField16 = new javax.swing.JTextField();
        jTextField17 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jTextField13 = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jTextField18 = new javax.swing.JTextField();
        jTextField19 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setTitle("Empleado Nuevo");

        jLabel1.setText("Nombre:");

        jLabel2.setText("Apeliido Paterno:");

        jLabel3.setText("Apellido Materno:");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField2KeyTyped(evt);
            }
        });

        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField3KeyTyped(evt);
            }
        });

        jLabel4.setText("Genero:");

        jRadioButton1.setText("Femenino");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        jRadioButton2.setText("Masculino");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        jLabel5.setText("DNI:");

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField4KeyTyped(evt);
            }
        });

        jLabel6.setText("Fecha de Nacimiento");

        jLabel7.setText("Tipo:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Asistente de Almacen", "Supervisor de Almacen", "Asistente de Ventas", "Supervisor de Ventas", "Aistente de Producción", "Supervisor de Producción", "Operario" }));

        jLabel8.setText("Sueldo:");

        jLabel9.setText("Estado:");

        jRadioButton3.setText("Activo");
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        jRadioButton4.setText("Cesado");
        jRadioButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton4ActionPerformed(evt);
            }
        });

        jTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField6KeyTyped(evt);
            }
        });

        jLabel10.setText("Fecha de Contrato:");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/save.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel13.setText("Guardar");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error 1.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel11.setText("Cancelar");

        jLabel17.setText("Desempeño");

        jLabel18.setText("Galletas Producidas Promedio");

        jLabel19.setText("Galletas Rotas Promedio");

        jTextField8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField8KeyTyped(evt);
            }
        });

        jTextField9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField9KeyTyped(evt);
            }
        });

        jLabel36.setText("Turno 1");

        jLabel37.setText("Turno 2");

        jTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField5KeyTyped(evt);
            }
        });

        jTextField7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField7KeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19))
                .addGap(67, 67, 67)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField8)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField7, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(jTextField5))
                .addGap(0, 31, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel36)
                .addGap(83, 83, 83)
                .addComponent(jLabel37)
                .addGap(40, 40, 40))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(jLabel37))
                .addGap(8, 8, 8)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Horneado", jPanel3);

        jLabel23.setText("Galletas Producidas Promedio");

        jLabel24.setText("Galletas Rotas Promedio");

        jTextField14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField14KeyTyped(evt);
            }
        });

        jTextField15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField15KeyTyped(evt);
            }
        });

        jLabel14.setText("Turno 1");

        jLabel15.setText("Turno 2");

        jTextField20.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField20KeyTyped(evt);
            }
        });

        jTextField21.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField21KeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24))
                .addGap(67, 67, 67)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField14)
                    .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField20, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                    .addComponent(jTextField21))
                .addGap(0, 32, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addGap(75, 75, 75)
                .addComponent(jLabel15)
                .addGap(50, 50, 50))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        jTabbedPane1.addTab("Decorado", jPanel4);

        jLabel25.setText("Galletas Producidas Promedio");

        jTextField16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField16KeyTyped(evt);
            }
        });

        jTextField17.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField17KeyTyped(evt);
            }
        });

        jLabel26.setText("Galletas Rotas Promedio");

        jLabel31.setText("Turno 1");

        jLabel33.setText("Turno 2");

        jTextField12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField12KeyTyped(evt);
            }
        });

        jTextField13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField13KeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel31)
                .addGap(74, 74, 74)
                .addComponent(jLabel33)
                .addGap(51, 51, 51))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25)
                    .addComponent(jLabel26))
                .addGap(67, 67, 67)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField17)
                    .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField12, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                    .addComponent(jTextField13))
                .addGap(0, 39, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Relleno", jPanel5);

        jLabel27.setText("Galletas Producidas Promedio");

        jTextField18.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField18KeyTyped(evt);
            }
        });

        jTextField19.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField19KeyTyped(evt);
            }
        });

        jLabel28.setText("Galletas Rotas Promedio");

        jLabel34.setText("Turno 1");

        jLabel35.setText("Turno 2");

        jTextField10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField10KeyTyped(evt);
            }
        });

        jTextField11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField11KeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28))
                .addGap(67, 67, 67)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField19)
                    .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField10, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                    .addComponent(jTextField11))
                .addGap(30, 30, 30))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel34)
                .addGap(79, 79, 79)
                .addComponent(jLabel35)
                .addGap(45, 45, 45))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(jLabel35))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
        );

        jTabbedPane1.addTab("Empaque", jPanel6);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(73, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(22, 22, 22)
                                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(22, 22, 22)
                                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jDateChooser2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jRadioButton1)
                                        .addGap(10, 10, 10)
                                        .addComponent(jRadioButton2))
                                    .addComponent(jComboBox1, 0, 192, Short.MAX_VALUE)
                                    .addComponent(jTextField4)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField6))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButton3)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButton4)))
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGap(203, 203, 203)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jRadioButton1)
                            .addComponent(jRadioButton2))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jRadioButton3)
                            .addComponent(jRadioButton4))))
                .addGap(25, 25, 25)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        // VALIDACION PARA CAMPO NOMBRE
        char car = evt.getKeyChar();
        if((car<'a' || car>'z') && (car<'A' || car>'Z') && (car!=(char) KeyEvent.VK_BACK_SPACE) && (car!=(char) KeyEvent.VK_SPACE)) {
            evt.consume();
            JOptionPane.showMessageDialog(this, "Debe insertar solo letras en el campo Nombre");

        }
        if(jTextField1.getText().length()>=50) {
            evt.consume();
            JOptionPane.showMessageDialog(this, "Solo puede insertar 50 caracteres en el campo Nombre");
        }

    }//GEN-LAST:event_jTextField1KeyTyped

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyTyped
        // VALIDACION PARA CAMPO Apellido P
        char car = evt.getKeyChar();
        if((car<'a' || car>'z') && (car<'A' || car>'Z') && (car!=(char) KeyEvent.VK_BACK_SPACE) && (car!=(char) KeyEvent.VK_SPACE)) {
            evt.consume();
            JOptionPane.showMessageDialog(this, "Debe insertar solo letras en el campo Apellido Paterno");

        }
        if(jTextField2.getText().length()>=50) {
            evt.consume();
            JOptionPane.showMessageDialog(this, "Solo puede insertar 50 caracteres en el campo Nombre");
        }
    }//GEN-LAST:event_jTextField2KeyTyped

    private void jTextField3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyTyped
        // VALIDACION PARA CAMPO Apellido M
        char car = evt.getKeyChar();
        if((car<'a' || car>'z') && (car<'A' || car>'Z') && (car!=(char) KeyEvent.VK_BACK_SPACE) && (car!=(char) KeyEvent.VK_SPACE)) {
            evt.consume();
            JOptionPane.showMessageDialog(this, "Debe insertar solo letras en el campo Apellido Materno");

        }
        if(jTextField3.getText().length()>=50) {
            evt.consume();
            JOptionPane.showMessageDialog(this, "Solo puede insertar 50 caracteres en el campo Apellido Materno");
        }
    }//GEN-LAST:event_jTextField3KeyTyped

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        jRadioButton2.setSelected(false);
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
        jRadioButton1.setSelected(false);
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyTyped
        // VALIDACION PARA CAMPO DNI
        char car = evt.getKeyChar();
        if((car<'0' || car>'9') && (car!=(char) KeyEvent.VK_BACK_SPACE)) {
            evt.consume();
            JOptionPane.showMessageDialog(this, "Debe insertar solo numeros en el campo DNI");

        }
        if(jTextField4.getText().length()>=8) {
            evt.consume();
            JOptionPane.showMessageDialog(this, "Solo puede insertar 8 caracteres");
        }
    }//GEN-LAST:event_jTextField4KeyTyped

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
        // TODO add your handling code here:
        jRadioButton4.setSelected(false);
    }//GEN-LAST:event_jRadioButton3ActionPerformed

    private void jRadioButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton4ActionPerformed
        // TODO add your handling code here:
        jRadioButton3.setSelected(false);
    }//GEN-LAST:event_jRadioButton4ActionPerformed

    private void jTextField6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField6KeyTyped
        // vALIDACION PARA SUELDOS - FLOAT
        char car = evt.getKeyChar();
        if((car<'0' || car>'9') && jTextField6.getText().contains(".") && (car!=(char) KeyEvent.VK_BACK_SPACE)){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Ingrese valor correctamente");
        }else if((car<'0' || car>'9') && (car!='.') && (car!=(char) KeyEvent.VK_BACK_SPACE)){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Solo puede insertar valores decimales");
        }
    }//GEN-LAST:event_jTextField6KeyTyped

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        //BOTON PARA GUARDAR
        String nombre = jTextField1.getText();
        String apellidop = jTextField2.getText();
        String apellidom = jTextField3.getText();
        Date fechanac = jDateChooser1.getDate();
        Date fechacontrato = jDateChooser2.getDate();
        int genero;
        if (jRadioButton1.isSelected()) {
            genero = 1;
        } else {
            genero = 0;
        }
        int DNI=0;
        if(jTextField4.getText().isEmpty()){
            DNI=0;}else{
            DNI = Integer.parseInt(jTextField4.getText());
        }

        String tipo = jComboBox1.getSelectedItem().toString();
        Tipoempleado tipoempleado = mycontroladorconfiguracion.sacaidtipo(tipo);

        Float sueldo;
        if(jTextField6.getText().isEmpty()) sueldo=null; else {sueldo = Float.parseFloat(jTextField6.getText());}

        int estado;
        if (jRadioButton3.isSelected()) {
            estado = 1;
        } else {
            estado = 0;
        }

        float prodhorneado1=0;
        if(jTextField8.getText().isEmpty())prodhorneado1=0; else{ prodhorneado1 = Float.parseFloat(jTextField8.getText());}

        float rotashorneado1 = 0;
        if(jTextField9.getText().isEmpty())rotashorneado1=0; else{ rotashorneado1 = Float.parseFloat(jTextField9.getText());}

        float prodhorneado2 = 0;
        if(jTextField5.getText().isEmpty())prodhorneado2=0; else{ prodhorneado2 = Float.parseFloat(jTextField5.getText());}

        float rotashorneado2 = 0;
        if(jTextField7.getText().isEmpty())rotashorneado2=0; else{ rotashorneado2 = Float.parseFloat(jTextField7.getText());}

        float prodrelleno1 = 0;
        if(jTextField14.getText().isEmpty())prodrelleno1=0; else{ prodrelleno1 = Float.parseFloat(jTextField14.getText());}

        float rotasrelleno1 = 0;
        if(jTextField15.getText().isEmpty())rotasrelleno1=0; else{ rotasrelleno1 = Float.parseFloat(jTextField15.getText());}

        float prodrelleno2 = 0;
        if(jTextField20.getText().isEmpty())prodrelleno2=0; else{ prodrelleno2 = Float.parseFloat(jTextField20.getText());}

        float rotasrelleno2 = 0;
        if(jTextField21.getText().isEmpty())rotasrelleno2=0; else{ rotasrelleno2 = Float.parseFloat(jTextField21.getText());}

        float proddecorado1 = 0;
        if(jTextField17.getText().isEmpty())proddecorado1=0; else{ proddecorado1 = Float.parseFloat(jTextField17.getText());}

        float rotasdecorado1 = 0;
        if(jTextField16.getText().isEmpty())rotasdecorado1=0; else{ rotasdecorado1 = Float.parseFloat(jTextField16.getText());}

        float proddecorado2 = 0;
        if(jTextField12.getText().isEmpty())proddecorado2=0; else{ proddecorado2 = Float.parseFloat(jTextField12.getText());}

        float rotasdecorado2 = 0;
        if(jTextField13.getText().isEmpty())rotasdecorado2=0; else{ rotasdecorado2 = Float.parseFloat(jTextField13.getText());}

        float prodempaque1 = 0;
        if(jTextField19.getText().isEmpty())prodempaque1=0; else{ prodempaque1 = Float.parseFloat(jTextField19.getText());}

        float rotasempaque1 = 0;
        if(jTextField18.getText().isEmpty())rotasempaque1=0; else{ rotasempaque1 = Float.parseFloat(jTextField18.getText());}

        float prodempaque2 = 0;
        if(jTextField10.getText().isEmpty())prodempaque2=0; else{ prodempaque2 = Float.parseFloat(jTextField10.getText());}

        float rotasempaque2 = 0;
        if(jTextField11.getText().isEmpty())rotasempaque2=0; else{ rotasempaque2 = Float.parseFloat(jTextField11.getText());}

        //AQUI EMPIEZA LA VALIDACION

        if(tipoempleado.getIdtipoempleado()!=5){
            int i = mycontroladoralmacen.validaempleado1(jTextField1, jTextField2, jTextField3, jTextField4, jTextField6);
            if(i==0) {
                JOptionPane.showMessageDialog(this, "Ingrese todos los valores");
            }else{
                int k= mycontroladorconfiguracion.validaempleadoxDNI(DNI,-1);
                if(k==1){
                    JOptionPane.showMessageDialog(this, "El DNI ya está registrado. Por favor ingrese otro empleado");
                }
                else{
                    int w=mycontroladorconfiguracion.validaDni8(DNI);
                    if(w==0){
                        JOptionPane.showMessageDialog(this, "El DNI debe contener 8 caracteres");
                    }else{
                        int x = mycontroladorconfiguracion.validaedad(fechanac, fechacontrato);
                        if(x==0){
                            JOptionPane.showMessageDialog(this, "El empleado deberá ser mayor de edad para ser registrado");
                        }else{
                            mycontroladorconfiguracion.guardaempleado(nombre, apellidop, apellidom, fechanac, fechacontrato, genero, DNI,sueldo, tipoempleado,estado);
                            lbj.LBJ.Login.escritorio.empleado.refreshEmpleadoTable();
                            JOptionPane.showMessageDialog(this, "El empleado se ingresó correctamente");
                            this.dispose();
                        }
                    }
                }
            }
        } else {
            int i= mycontroladoralmacen.validaempleado(jTextField1, jTextField2, jTextField3, jTextField4, jTextField6,
                jTextField8, jTextField9, jTextField5, jTextField7, jTextField14, jTextField15, jTextField20, jTextField21,
                jTextField17, jTextField16, jTextField12, jTextField13, jTextField19, jTextField18, jTextField10,jTextField11);

            if(i==0) {
                JOptionPane.showMessageDialog(this, "Ingrese todos los valores");
            }else{
                int k= mycontroladorconfiguracion.validaempleadoxDNI(DNI,-1);
                if(k==1){
                    JOptionPane.showMessageDialog(this, "El DNI ya está registrado. Por favor ingrese otro empleado");
                }
                else{
                    int x = mycontroladorconfiguracion.validaedad(fechanac, fechacontrato);
                    if(x==0){
                        JOptionPane.showMessageDialog(this, "El empleado deberá ser mayor de edad para ser registrado");
                    }else{
                        int w=mycontroladorconfiguracion.validaDni8(DNI);
                        if(w==0){
                            JOptionPane.showMessageDialog(this, "El DNI debe contener 8 caracteres");
                        }else{
                            mycontroladorconfiguracion.guardaempleado(nombre, apellidop, apellidom, fechanac, fechacontrato, genero, DNI,sueldo, tipoempleado,estado);
                            modelos.Empleado emp = mycontroladorconfiguracion.sacaempleadoxDNI(DNI);
                            modelos.Turno tur1 = mycontroladorconfiguracion.sacaturnoxid(1);
                            modelos.Turno tur2 = mycontroladorconfiguracion.sacaturnoxid(2);
                            int act;
                            for(act=1; act <5;act++){
                                Actividad actividad= mycontroladoralmacen.sacaactividadbyid(act);
                                DesempenoId desid1 = new DesempenoId();
                                desid1.setIdactividad(act);
                                desid1.setIdempleado(emp.getIdempleado());
                                desid1.setIdturno(tur1.getIdturno());
                                DesempenoId desid2 = new DesempenoId();
                                desid2.setIdactividad(act);
                                desid2.setIdempleado(emp.getIdempleado());
                                desid2.setIdturno(tur2.getIdturno());

                                switch (act){
                                    case 1: mycontroladorconfiguracion.guardadesempeñoxempleado(desid1, emp, actividad, tur1, rotashorneado1,  prodhorneado1);
                                    mycontroladorconfiguracion.guardadesempeñoxempleado(desid2, emp, actividad, tur2, rotashorneado2,  prodhorneado2);
                                    break;
                                    case 2: mycontroladorconfiguracion.guardadesempeñoxempleado(desid1, emp, actividad, tur1, rotasrelleno1, prodrelleno1);
                                    mycontroladorconfiguracion.guardadesempeñoxempleado(desid2, emp, actividad, tur2, rotasrelleno2,  prodrelleno2);
                                    break;
                                    case 3: mycontroladorconfiguracion.guardadesempeñoxempleado(desid1, emp, actividad, tur1, rotasdecorado1, proddecorado1);
                                    mycontroladorconfiguracion.guardadesempeñoxempleado(desid2, emp, actividad, tur2, rotasdecorado2, proddecorado2);
                                    break;
                                    case 4: mycontroladorconfiguracion.guardadesempeñoxempleado(desid1, emp, actividad, tur1,  rotasempaque1, prodempaque1);
                                    mycontroladorconfiguracion.guardadesempeñoxempleado(desid2, emp, actividad, tur2,  rotasempaque2, prodempaque2);
                                    break;
                                }
                            }

                            lbj.LBJ.Login.escritorio.empleado.refreshEmpleadoTable();
                            JOptionPane.showMessageDialog(this, "El empleado y su desempeño se ingresaron correctamente");
                            this.dispose();

                            mycontroladorseguridad.insertarlog(usuario, new Date(), "Insertar", "Empleado", "Insertar Empleado");
                            mycontroladorseguridad.insertarlog(usuario, new Date(), "Insertar", "Desempeño", "Insertar Desempeño");
                        }
                    }
                }
            }
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Cancelar
        if(JOptionPane.showConfirmDialog(null, "¿Está seguro de cancelar el registro?",
            "Mensaje",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
        lbj.LBJ.Login.escritorio.empleado.refreshEmpleadoTable();
        this.dispose();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField8KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField8KeyTyped
        char car = evt.getKeyChar();
        if((car<'0' || car>'9') && jTextField8.getText().contains(".") && (car!=(char) KeyEvent.VK_BACK_SPACE)){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Ingrese valor correctamente");
        }else if((car<'0' || car>'9') && (car!='.') && (car!=(char) KeyEvent.VK_BACK_SPACE)){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Solo puede insertar valores decimales");
        }
    }//GEN-LAST:event_jTextField8KeyTyped

    private void jTextField9KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField9KeyTyped
        char car = evt.getKeyChar();
        if((car<'0' || car>'9') && jTextField9.getText().contains(".") && (car!=(char) KeyEvent.VK_BACK_SPACE)){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Ingrese valor correctamente");
        }else if((car<'0' || car>'9') && (car!='.') && (car!=(char) KeyEvent.VK_BACK_SPACE)){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Solo puede insertar valores decimales");
        }
    }//GEN-LAST:event_jTextField9KeyTyped

    private void jTextField5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyTyped
        char car = evt.getKeyChar();
        if((car<'0' || car>'9') && jTextField5.getText().contains(".") && (car!=(char) KeyEvent.VK_BACK_SPACE)){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Ingrese valor correctamente");
        }else if((car<'0' || car>'9') && (car!='.') && (car!=(char) KeyEvent.VK_BACK_SPACE)){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Solo puede insertar valores decimales");
        }
    }//GEN-LAST:event_jTextField5KeyTyped

    private void jTextField7KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField7KeyTyped
        char car = evt.getKeyChar();
        if((car<'0' || car>'9') && jTextField7.getText().contains(".") && (car!=(char) KeyEvent.VK_BACK_SPACE)){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Ingrese valor correctamente");
        }else if((car<'0' || car>'9') && (car!='.') && (car!=(char) KeyEvent.VK_BACK_SPACE)){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Solo puede insertar valores decimales");
        }
    }//GEN-LAST:event_jTextField7KeyTyped

    private void jTextField14KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField14KeyTyped
        char car = evt.getKeyChar();
        if((car<'0' || car>'9') && jTextField14.getText().contains(".") && (car!=(char) KeyEvent.VK_BACK_SPACE)){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Ingrese valor correctamente");
        }else if((car<'0' || car>'9') && (car!='.') && (car!=(char) KeyEvent.VK_BACK_SPACE)){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Solo puede insertar valores decimales");
        }

    }//GEN-LAST:event_jTextField14KeyTyped

    private void jTextField15KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField15KeyTyped
        char car = evt.getKeyChar();
        if((car<'0' || car>'9') && jTextField15.getText().contains(".") && (car!=(char) KeyEvent.VK_BACK_SPACE)){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Ingrese valor correctamente");
        }else if((car<'0' || car>'9') && (car!='.') && (car!=(char) KeyEvent.VK_BACK_SPACE)){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Solo puede insertar valores decimales");
        }

    }//GEN-LAST:event_jTextField15KeyTyped

    private void jTextField20KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField20KeyTyped
        char car = evt.getKeyChar();
        if((car<'0' || car>'9') && jTextField20.getText().contains(".") && (car!=(char) KeyEvent.VK_BACK_SPACE)){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Ingrese valor correctamente");
        }else if((car<'0' || car>'9') && (car!='.') && (car!=(char) KeyEvent.VK_BACK_SPACE)){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Solo puede insertar valores decimales");
        }

    }//GEN-LAST:event_jTextField20KeyTyped

    private void jTextField21KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField21KeyTyped
        char car = evt.getKeyChar();
        if((car<'0' || car>'9') && jTextField21.getText().contains(".") && (car!=(char) KeyEvent.VK_BACK_SPACE)){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Ingrese valor correctamente");
        }else if((car<'0' || car>'9') && (car!='.') && (car!=(char) KeyEvent.VK_BACK_SPACE)){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Solo puede insertar valores decimales");
        }

    }//GEN-LAST:event_jTextField21KeyTyped

    private void jTextField16KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField16KeyTyped
        char car = evt.getKeyChar();
        if((car<'0' || car>'9') && jTextField16.getText().contains(".") && (car!=(char) KeyEvent.VK_BACK_SPACE)){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Ingrese valor correctamente");
        }else if((car<'0' || car>'9') && (car!='.') && (car!=(char) KeyEvent.VK_BACK_SPACE)){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Solo puede insertar valores decimales");
        }

    }//GEN-LAST:event_jTextField16KeyTyped

    private void jTextField17KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField17KeyTyped
        char car = evt.getKeyChar();
        if((car<'0' || car>'9') && jTextField17.getText().contains(".") && (car!=(char) KeyEvent.VK_BACK_SPACE)){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Ingrese valor correctamente");
        }else if((car<'0' || car>'9') && (car!='.') && (car!=(char) KeyEvent.VK_BACK_SPACE)){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Solo puede insertar valores decimales");
        }
    }//GEN-LAST:event_jTextField17KeyTyped

    private void jTextField12KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField12KeyTyped
        char car = evt.getKeyChar();
        if((car<'0' || car>'9') && jTextField12.getText().contains(".") && (car!=(char) KeyEvent.VK_BACK_SPACE)){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Ingrese valor correctamente");
        }else if((car<'0' || car>'9') && (car!='.') && (car!=(char) KeyEvent.VK_BACK_SPACE)){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Solo puede insertar valores decimales");
        }

    }//GEN-LAST:event_jTextField12KeyTyped

    private void jTextField13KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField13KeyTyped
        char car = evt.getKeyChar();
        if((car<'0' || car>'9') && jTextField13.getText().contains(".") && (car!=(char) KeyEvent.VK_BACK_SPACE)){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Ingrese valor correctamente");
        }else if((car<'0' || car>'9') && (car!='.') && (car!=(char) KeyEvent.VK_BACK_SPACE)){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Solo puede insertar valores decimales");
        }

    }//GEN-LAST:event_jTextField13KeyTyped

    private void jTextField18KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField18KeyTyped
        char car = evt.getKeyChar();
        if((car<'0' || car>'9') && jTextField18.getText().contains(".") && (car!=(char) KeyEvent.VK_BACK_SPACE)){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Ingrese valor correctamente");
        }else if((car<'0' || car>'9') && (car!='.') && (car!=(char) KeyEvent.VK_BACK_SPACE)){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Solo puede insertar valores decimales");
        }

    }//GEN-LAST:event_jTextField18KeyTyped

    private void jTextField19KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField19KeyTyped
        char car = evt.getKeyChar();
        if((car<'0' || car>'9') && jTextField19.getText().contains(".") && (car!=(char) KeyEvent.VK_BACK_SPACE)){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Ingrese valor correctamente");
        }else if((car<'0' || car>'9') && (car!='.') && (car!=(char) KeyEvent.VK_BACK_SPACE)){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Solo puede insertar valores decimales");
        }
    }//GEN-LAST:event_jTextField19KeyTyped

    private void jTextField10KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField10KeyTyped
        char car = evt.getKeyChar();
        if((car<'0' || car>'9') && jTextField10.getText().contains(".") && (car!=(char) KeyEvent.VK_BACK_SPACE)){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Ingrese valor correctamente");
        }else if((car<'0' || car>'9') && (car!='.') && (car!=(char) KeyEvent.VK_BACK_SPACE)){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Solo puede insertar valores decimales");
        }

    }//GEN-LAST:event_jTextField10KeyTyped

    private void jTextField11KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField11KeyTyped
        char car = evt.getKeyChar();
        if((car<'0' || car>'9') && jTextField11.getText().contains(".") && (car!=(char) KeyEvent.VK_BACK_SPACE)){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Ingrese valor correctamente");
        }else if((car<'0' || car>'9') && (car!='.') && (car!=(char) KeyEvent.VK_BACK_SPACE)){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Solo puede insertar valores decimales");
        }
    }//GEN-LAST:event_jTextField11KeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox jComboBox1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
}
