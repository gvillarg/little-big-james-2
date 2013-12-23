
package paquetevistas.ventas;

import com.oracle.jrockit.jfr.ContentType;
import controlador.ventas.ControllerOrdenes;
import java.sql.Time;
import java.util.Date;
import modelos.Producto;

public class ProductoC {
    
    private int idProducto;
    private String nombre;
    private float cantidad;
    private float precioUnd;
    private Date fechaCotizada;
    
    public ProductoC() {
        
    }
    public ProductoC(Producto p, float cantidad, float precio) {
        this.idProducto = p.getIdproducto();
        this.nombre = p.getNombre();
        this.cantidad = cantidad;
        this.precioUnd = precio;
        this.fechaCotizada = null;
    }
    public void cotizar(int type) {
        this.fechaCotizada = new Date();
        this.fechaCotizada.setTime(System.currentTimeMillis());
        if (type == 1) {
            this.fechaCotizada.setTime(System.currentTimeMillis());
            this.fechaCotizada.setTime(this.fechaCotizada.getTime() + 1000*60*60*24);
        } else {
            for (int i=0; i<29; i++) {
                ControllerOrdenes controllerOrdenes = new ControllerOrdenes();
                this.fechaCotizada.setTime(controllerOrdenes.getLastOrdenFecha().getTime() + 1000*60*60*24);
            }
        }
    }
    /**
     * @return the idProducto
     */
    public int getIdProducto() {
        return idProducto;
    }

    /**
     * @param idProducto the idProducto to set
     */
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the cantidad
     */
    public float getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the precioUnd
     */
    public float getPrecioUnd() {
        return precioUnd;
    }

    /**
     * @param precioUnd the precioUnd to set
     */
    public void setPrecioUnd(float precioUnd) {
        this.precioUnd = precioUnd;
    }

    /**
     * @return the fechaCotizada
     */
    public Date getFechaCotizada() {
        return fechaCotizada;
    }

    /**
     * @param fechaCotizada the fechaCotizada to set
     */
    public void setFechaCotizada(Date fechaCotizada) {
        this.fechaCotizada = fechaCotizada;
    }
    
}
