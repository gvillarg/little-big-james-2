
package controlador.ventas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modelos.Detalleordenproduccion;
import modelos.Detalleventa;
import modelos.Ordenproduccion;
import modelos.Producto;

class Detalles {
    
    private List<LineaDetalle> detalles;
    private Ordenproduccion orden;
    
    public Detalles() {
        this.detalles = new ArrayList<LineaDetalle>();
        this.orden = new Ordenproduccion();
        this.orden.setEstado(0);
        Date time = new Date();
        time.setTime(System.currentTimeMillis());
        this.orden.setFecharegistro(time);
        ControllerOrdenes controllerOrdenes = new ControllerOrdenes();
        Date time2 = new Date();
        Date time3 = new Date();
        time2 = controllerOrdenes.getLastOrdenFecha();
        this.orden.setFechainicio(time2);
        time3.setTime(time2.getTime());
        for (int i=0; i<29; i++) {
            time3.setTime(time3.getTime() + 1000 * 60 * 60 * 24);
        }
        this.orden.setFechafin(time3);
    }
    
    public void add(Detalleventa detalle) {
        Producto p = detalle.getProducto();
        int id = p.getIdproducto();
        float cantidad = detalle.getCantidad();
        Date fecha = detalle.getFecha();
        int j = detalles.size();
        boolean agregado = false;
        for (int i=0; i<j; i++) {
            LineaDetalle linea = detalles.get(i);
            if (linea.getProducto() == id) {
                linea.setCantidad(linea.getCantidad() + cantidad);
                linea.setFecha(fecha);
                agregado = true;
            }
        }
        if (!agregado) {
            LineaDetalle linea2 = new LineaDetalle(id,cantidad,fecha);
            detalles.add(linea2);
        }
    }
    public Ordenproduccion getOrden() {
        return this.orden;
    }
    public int size() {
        return detalles.size();
    }
    public Detalleordenproduccion getDetalle(int i) {
        LineaDetalle linea = detalles.get(i);
        ControllerVentas controllerVentas = new ControllerVentas();
        Detalleordenproduccion detalle = new Detalleordenproduccion();
        float cantidadLinea =linea.getCantidad();
        float cantidadAlmacen = controllerVentas.cantidadAlmacen(linea.getProducto());
        if (cantidadAlmacen > cantidadLinea) {
            cantidadLinea = 0;
            cantidadAlmacen = 0;
        }
        detalle.setCantidad(cantidadLinea - cantidadAlmacen);
        detalle.setProducto(controllerVentas.getProducto(linea.getProducto()));
        return detalle;
    }
}
