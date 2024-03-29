package modelos;
// Generated 17-nov-2013 13:30:35 by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * Almacen generated by hbm2java
 */
public class Almacen  implements java.io.Serializable {


     private int idalmacen;
     private String nombre;
     private String descripcion;
     private Integer capacidad;
     private Float area;
     private int estado;
     private Set productos = new HashSet(0);
     private Set movimientos = new HashSet(0);

    public Almacen() {
    }

	
    public Almacen(int idalmacen, String nombre, String descripcion, int estado) {
        this.idalmacen = idalmacen;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
    }
    public Almacen(int idalmacen, String nombre, String descripcion, Integer capacidad, Float area, int estado, Set productos, Set movimientos) {
       this.idalmacen = idalmacen;
       this.nombre = nombre;
       this.descripcion = descripcion;
       this.capacidad = capacidad;
       this.area = area;
       this.estado = estado;
       this.productos = productos;
       this.movimientos = movimientos;
    }
   
    public int getIdalmacen() {
        return this.idalmacen;
    }
    
    public void setIdalmacen(int idalmacen) {
        this.idalmacen = idalmacen;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Integer getCapacidad() {
        return this.capacidad;
    }
    
    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }
    public Float getArea() {
        return this.area;
    }
    
    public void setArea(Float area) {
        this.area = area;
    }
    public int getEstado() {
        return this.estado;
    }
    
    public void setEstado(int estado) {
        this.estado = estado;
    }
    public Set getProductos() {
        return this.productos;
    }
    
    public void setProductos(Set productos) {
        this.productos = productos;
    }
    public Set getMovimientos() {
        return this.movimientos;
    }
    
    public void setMovimientos(Set movimientos) {
        this.movimientos = movimientos;
    }




}


