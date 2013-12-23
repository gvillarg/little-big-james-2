package modelos;
// Generated 17-nov-2013 13:30:35 by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * Requerimientocompraxproducto generated by hbm2java
 */
public class Requerimientocompraxproducto  implements java.io.Serializable {


     private RequerimientocompraxproductoId id;
     private Requirimientocompra requirimientocompra;
     private Producto producto;
     private float cantidad;
     private Set detallecompras = new HashSet(0);

    public Requerimientocompraxproducto() {
    }

	
    public Requerimientocompraxproducto(RequerimientocompraxproductoId id, Requirimientocompra requirimientocompra, Producto producto, float cantidad) {
        this.id = id;
        this.requirimientocompra = requirimientocompra;
        this.producto = producto;
        this.cantidad = cantidad;
    }
    public Requerimientocompraxproducto(RequerimientocompraxproductoId id, Requirimientocompra requirimientocompra, Producto producto, float cantidad, Set detallecompras) {
       this.id = id;
       this.requirimientocompra = requirimientocompra;
       this.producto = producto;
       this.cantidad = cantidad;
       this.detallecompras = detallecompras;
    }
   
    public RequerimientocompraxproductoId getId() {
        return this.id;
    }
    
    public void setId(RequerimientocompraxproductoId id) {
        this.id = id;
    }
    public Requirimientocompra getRequirimientocompra() {
        return this.requirimientocompra;
    }
    
    public void setRequirimientocompra(Requirimientocompra requirimientocompra) {
        this.requirimientocompra = requirimientocompra;
    }
    public Producto getProducto() {
        return this.producto;
    }
    
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    public float getCantidad() {
        return this.cantidad;
    }
    
    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }
    public Set getDetallecompras() {
        return this.detallecompras;
    }
    
    public void setDetallecompras(Set detallecompras) {
        this.detallecompras = detallecompras;
    }




}


