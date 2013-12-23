package modelos;
// Generated 17-nov-2013 13:30:35 by Hibernate Tools 3.2.1.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Compra generated by hbm2java
 */
public class Compra  implements java.io.Serializable {


     private int idcompra;
     private Proveedor proveedor;
     private Date fecharegistro;
     private Date fechaacordada;
     private Date fechareal;
     private String estado;
     private Float precioacordado;
     private Set movimientos = new HashSet(0);
     private Set detallecompras = new HashSet(0);

    public Compra() {
    }

	
    public Compra(int idcompra, Proveedor proveedor) {
        this.idcompra = idcompra;
        this.proveedor = proveedor;
    }
    public Compra(int idcompra, Proveedor proveedor, Date fecharegistro, Date fechaacordada, Date fechareal, String estado, Float precioacordado, Set movimientos, Set detallecompras) {
       this.idcompra = idcompra;
       this.proveedor = proveedor;
       this.fecharegistro = fecharegistro;
       this.fechaacordada = fechaacordada;
       this.fechareal = fechareal;
       this.estado = estado;
       this.precioacordado = precioacordado;
       this.movimientos = movimientos;
       this.detallecompras = detallecompras;
    }
   
    public int getIdcompra() {
        return this.idcompra;
    }
    
    public void setIdcompra(int idcompra) {
        this.idcompra = idcompra;
    }
    public Proveedor getProveedor() {
        return this.proveedor;
    }
    
    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
    public Date getFecharegistro() {
        return this.fecharegistro;
    }
    
    public void setFecharegistro(Date fecharegistro) {
        this.fecharegistro = fecharegistro;
    }
    public Date getFechaacordada() {
        return this.fechaacordada;
    }
    
    public void setFechaacordada(Date fechaacordada) {
        this.fechaacordada = fechaacordada;
    }
    public Date getFechareal() {
        return this.fechareal;
    }
    
    public void setFechareal(Date fechareal) {
        this.fechareal = fechareal;
    }
    public String getEstado() {
        return this.estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public Float getPrecioacordado() {
        return this.precioacordado;
    }
    
    public void setPrecioacordado(Float precioacordado) {
        this.precioacordado = precioacordado;
    }
    public Set getMovimientos() {
        return this.movimientos;
    }
    
    public void setMovimientos(Set movimientos) {
        this.movimientos = movimientos;
    }
    public Set getDetallecompras() {
        return this.detallecompras;
    }
    
    public void setDetallecompras(Set detallecompras) {
        this.detallecompras = detallecompras;
    }




}


