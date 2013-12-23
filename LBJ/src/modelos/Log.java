package modelos;
// Generated 17-nov-2013 13:30:35 by Hibernate Tools 3.2.1.GA


import java.util.Date;

/**
 * Log generated by hbm2java
 */
public class Log  implements java.io.Serializable {


     private int idlog;
     private Usuario usuario;
     private Date fecha;
     private String accion;
     private String tabla;
     private String descripcion;

    public Log() {
    }

	
    public Log(int idlog, Usuario usuario, Date fecha) {
        this.idlog = idlog;
        this.usuario = usuario;
        this.fecha = fecha;
    }
    public Log(int idlog, Usuario usuario, Date fecha, String accion, String tabla, String descripcion) {
       this.idlog = idlog;
       this.usuario = usuario;
       this.fecha = fecha;
       this.accion = accion;
       this.tabla = tabla;
       this.descripcion = descripcion;
    }
   
    public int getIdlog() {
        return this.idlog;
    }
    
    public void setIdlog(int idlog) {
        this.idlog = idlog;
    }
    public Usuario getUsuario() {
        return this.usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public Date getFecha() {
        return this.fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public String getAccion() {
        return this.accion;
    }
    
    public void setAccion(String accion) {
        this.accion = accion;
    }
    public String getTabla() {
        return this.tabla;
    }
    
    public void setTabla(String tabla) {
        this.tabla = tabla;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }




}


