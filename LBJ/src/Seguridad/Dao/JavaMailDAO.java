/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Seguridad.Dao;

import modelos.Usuario;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Saori
 */
public class JavaMailDAO {
    //private final String USUARIO = "tuCorreoYahoo@yahoo.es";
    private final String USUARIO = "littlebigjamessystem@gmail.com";
    private final String PASSWORD = "littlebigjames";

    /*
     * Se obtienen las propiedades del sistema 
     * y se establece el servidor SMTP
     */
    public Properties SMTPservidor() {
        Properties prop = new Properties();

        /*
         * Configurar Java cliente de correo saliente gmail
         */
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "465");

        return prop;
    }

    /*
     * Se obtiene una sesiòn con las propiedades anteriormente definidas
     */
    public Session autenticacion() {
        Session sess = Session.getDefaultInstance(SMTPservidor(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USUARIO, PASSWORD);
            }
        });
        return sess;
    }

    /*
     * Se rellenan los atributos y el contenido
     */
//    public void mensaje(UsuarioBEAN u) {
    public void mensaje(Usuario u) {
        //Crear un mensaje vacìo
        Message mensaje = new MimeMessage(autenticacion());
        try {
            // ¿Quien envia este mensaje? 
            mensaje.setFrom(new InternetAddress(USUARIO));
            // ¿Para quien se envia mensaje?
            mensaje.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(u.getCorreo()));
            //Asunto de mensaje
            mensaje.setSubject("Recuperacion de Contraseña");
            //El texto del mensaje
            mensaje.setText("Estimado(a) "+ u.getNombreusuario() + ":\n\n"
                    + "Su nueva contraseña es: " + u.getPassword()
                    + "\n\nModifique su contraseña al ingresar al sistema."
                    + "\n\nAtentamente,"
                    + "\nLittleBigJames System.");
            /**
             * El mensaje ya esta listo para ser enviado. Para enviarlo se usa
             * la clase Transport mediante su metodo estatico send().
             */
            Transport.send(mensaje);
            //Si el mensaje se envio correctamente nos muestra el texto
            //System.out.println("Mensaje enviado");
            //En caso contrario nos muestra excepción
        } catch (MessagingException ex) {
            Logger.getLogger(JavaMailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
     * Constructor
     */
    public JavaMailDAO(Usuario u) {
        mensaje(u);
    }
    private JavaMailDAO(String correoDestino,String asunto,String contenido){
        //Crear un mensaje vacìo
        Message mensaje = new MimeMessage(autenticacion());
        try {
            // ¿Quien envia este mensaje? 
            mensaje.setFrom(new InternetAddress(USUARIO));
            // ¿Para quien se envia mensaje?
            mensaje.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(correoDestino));
            //Asunto de mensaje
            mensaje.setSubject(asunto);
            //El texto del mensaje
            mensaje.setText(contenido + " \n");
            /**
             * El mensaje ya esta listo para ser enviado. Para enviarlo se usa
             * la clase Transport mediante su metodo estatico send().
             */
            Transport.send(mensaje);
            //Si el mensaje se envio correctamente nos muestra el texto
            //System.out.println("Mensaje enviado");
            //En caso contrario nos muestra excepción
        } catch (MessagingException ex) {
            Logger.getLogger(JavaMailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public static void enviarCorreo(String correoDestino,String asunto,String contenido){
        JavaMailDAO mail= new JavaMailDAO(correoDestino, asunto, contenido);   
    }
}
