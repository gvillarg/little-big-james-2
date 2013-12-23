/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lbj;

/**/
import controlador.almacen.controladoralmacen;
import java.util.ArrayList;
import java.util.List;
import modelos.Producto;
/**/
import paquetevistas.Login;

/**
 *
 * @author mariofcandia
 */
public class LBJ {

    /**
     * @param args the command line arguments
     */
    public static Login Login = new Login();
    /*esto agregue*/
    public static controladoralmacen mycontroladoralmacen = new controladoralmacen();
    /*esto agregue*/
    
    public static void main(String[] args) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LBJ.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        
        // TODO code application logic here
            Login.setVisible(true);
//        List<Producto> pro = new ArrayList<Producto>();
//        pro = mycontroladoralmacen.sacaproductos();
//        for(int i=0;i<pro.size();i++)
//            System.out.println(pro.get(i).getIdproducto() + " " + pro.get(i).getNombre()+" "+pro.get(i).getTipoproducto().getIdtipoproducto());
    }
}
