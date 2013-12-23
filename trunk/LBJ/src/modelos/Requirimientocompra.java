package modelos;
// Generated 17-nov-2013 13:30:35 by Hibernate Tools 3.2.1.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Requirimientocompra generated by hbm2java
 */
public class Requirimientocompra  implements java.io.Serializable {


     private int idreqerimientocompra;
     private Date fechaemision;
     private int estado;
     private Set requerimientocompraxproductos = new HashSet(0);

    public Requirimientocompra() {
    }

	
    public Requirimientocompra(int idreqerimientocompra, int estado) {
        this.idreqerimientocompra = idreqerimientocompra;
        this.estado = estado;
    }
    public Requirimientocompra(int idreqerimientocompra, Date fechaemision, int estado, Set requerimientocompraxproductos) {
       this.idreqerimientocompra = idreqerimientocompra;
       this.fechaemision = fechaemision;
       this.estado = estado;
       this.requerimientocompraxproductos = requerimientocompraxproductos;
    }
   
    public int getIdreqerimientocompra() {
        return this.idreqerimientocompra;
    }
    
    public void setIdreqerimientocompra(int idreqerimientocompra) {
        this.idreqerimientocompra = idreqerimientocompra;
    }
    public Date getFechaemision() {
        return this.fechaemision;
    }
    
    public void setFechaemision(Date fechaemision) {
        this.fechaemision = fechaemision;
    }
    public int getEstado() {
        return this.estado;
    }
    
    public void setEstado(int estado) {
        this.estado = estado;
    }
    public Set getRequerimientocompraxproductos() {
        return this.requerimientocompraxproductos;
    }
    
    public void setRequerimientocompraxproductos(Set requerimientocompraxproductos) {
        this.requerimientocompraxproductos = requerimientocompraxproductos;
    }




}


