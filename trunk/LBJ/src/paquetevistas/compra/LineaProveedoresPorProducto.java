
package paquetevistas.compra;

import java.util.ArrayList;
import java.util.List;
import modelos.Proveedor;

public class LineaProveedoresPorProducto {
    List<Proveedor> proveedores;
    List<Float> cantidades;
    List<Float> precios;
    boolean newLinea;
    
    public LineaProveedoresPorProducto() {
        this.newLinea = true;
        this.proveedores = new ArrayList<Proveedor>();
        this.cantidades = new ArrayList<Float>();
        this.precios = new ArrayList<Float>();
    }
    public void fill(List<Proveedor> proveedores, List<Float> cantidades, List<Float> precios) {
        this.proveedores = proveedores;
        this.cantidades = cantidades;
        this.precios = precios;
    }
    public float getMonto() {
        int j = proveedores.size();
        float monto = 0;
        for (int i=0; i<j; i++) {
            monto = monto + cantidades.get(i) * precios.get(i);
        }
        return monto;
    }
    public float getCantidadCubierta() {
        int j = proveedores.size();
        float cantidadCubierta = 0;
        for (int i=0; i<j; i++) {
            cantidadCubierta = cantidadCubierta + cantidades.get(i);
        }
        return cantidadCubierta;
    }
    public int size() {
        return this.proveedores.size();
    }
    public Proveedor getProveedor(int k) {
        return proveedores.get(k);
    }
    public float getCantidad(int k) {
        return cantidades.get(k);
    }
    public float getPrecio(int k) {
        return precios.get(k);
    }
}
