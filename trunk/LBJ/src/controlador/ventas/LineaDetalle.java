
package controlador.ventas;

import java.util.Date;

class LineaDetalle {
    
    private int id;
    private float cantidad;
    private Date fecha;
    
    public LineaDetalle(int id, float cantidad, Date fecha) {
        this.id = id;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }
    
    public int getProducto() {
        return this.id;
    }
    public float getCantidad() {
        return cantidad;
    }
    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }
    public long getFecha() {
        return this.fecha.getTime();
    }
    public void setFecha(Date fecha) {
        if (this.fecha.before(fecha)) {
            this.fecha = fecha;
        }
    }
    
}
