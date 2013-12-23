
package controlador.compra;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modelos.Compra;
import modelos.Detallecompra;
import modelos.Producto;
import modelos.Proveedor;
import modelos.Requerimientocompraxproducto;
import modelos.Requirimientocompra;
import paquetevistas.compra.LineaProveedoresPorProducto;

public class HandlerCompras {
    private int idRequerimiento;
    private List<Compra> compras;
    private List<List<Detallecompra>> listaDetalles;
    private List<Requerimientocompraxproducto> requerimientos;
    private List<LineaProveedoresPorProducto> lineas;
    private List<Proveedor> proveedores;
    private List<Producto> productos;
    private List<Float> cantidades;
    private List<Float> precios;
    private Date fechaAcordada;
    private List<Requerimientocompraxproducto> requerimientos2;
    private List<Integer> rangosProveedores;
    
    public HandlerCompras() {}

    HandlerCompras(int idRequerimiento, List<LineaProveedoresPorProducto> lineas, 
            List<Requerimientocompraxproducto> requerimientos, Date fechaAcordada) {
        this.idRequerimiento = idRequerimiento;
        this.lineas = lineas;
        this.requerimientos = requerimientos;
        this.fechaAcordada = fechaAcordada;
        this.compras = new ArrayList<Compra>();
        this.listaDetalles = new ArrayList<List<Detallecompra>>();
        this.proveedores = new ArrayList<Proveedor>();
        this.productos = new ArrayList<Producto>();
        this.cantidades = new ArrayList<Float>();
        this.precios = new ArrayList<Float>();
        this.requerimientos2 = new ArrayList<Requerimientocompraxproducto>();
        this.rangosProveedores = new ArrayList<Integer>();
        createAll();
    }
    public void createAll() {
        createDetalles();
        createCompras();
    }
    public void createDetalles() {
        int j = lineas.size();
        for (int i=0; i<j; i++) {
            LineaProveedoresPorProducto linea = lineas.get(i);
            Requerimientocompraxproducto req = requerimientos.get(i);
            int l = linea.size();
            for (int k=0; k<l; k++) {
                int m = getIndiceProveedor(linea.getProveedor(k));
                if (m < proveedores.size()) {
                    proveedores.add(m,linea.getProveedor(k));
                    productos.add(m,req.getProducto());
                    cantidades.add(m,linea.getCantidad(k));
                    precios.add(m,linea.getPrecio(k));
                    requerimientos2.add(m,req);
                } else {
                    proveedores.add(linea.getProveedor(k));
                    productos.add(req.getProducto());
                    cantidades.add(linea.getCantidad(k));
                    precios.add(linea.getPrecio(k));
                    requerimientos2.add(req);
                }
            }
        }
        setRangosProveedores();
    }
    public void createCompras() {
        int j = rangosProveedores.size() / 2;
        for (int i=0; i<j; i++) {
            Compra compra = new Compra();
            compra.setEstado("En Proceso");
            Date date = new Date();
            long time = System.currentTimeMillis();
            date.setTime(time);
            compra.setFecharegistro(date);
            compra.setProveedor(proveedores.get(rangosProveedores.get(i*2)));
            int l = rangosProveedores.get(i*2+1) + 1;
            float monto = 0;
            List<Detallecompra> detallesCompras = new ArrayList<Detallecompra>();
            for (int k=rangosProveedores.get(i*2); k<l; k++) {
//                System.out.println(cantidades.get(k));
//                System.out.println(precios.get(k));
                monto = monto + cantidades.get(k) * precios.get(k);
                Detallecompra detalleCompra = new Detallecompra();
                detalleCompra.setCantidad(cantidades.get(k));
                detalleCompra.setPrecio(precios.get(k));
                detalleCompra.setRequerimientocompraxproducto(requerimientos2.get(k));
                detalleCompra.setFecha(fechaAcordada);
                detallesCompras.add(detalleCompra);
            }
            listaDetalles.add(detallesCompras);
            compra.setPrecioacordado(monto);
            compra.setFechaacordada(fechaAcordada);
            compra.setFechareal(fechaAcordada);
            compras.add(compra);
        }
    }
    public void setRangosProveedores() {
        int j = proveedores.size();
        if (j>0) {
            Proveedor proveedor = proveedores.get(0);
            rangosProveedores.add(new Integer(0));
            for (int i=0; i<j; i++) {
                if (proveedores.get(i).getIdproveedor() != proveedor.getIdproveedor()) {
                    rangosProveedores.add(new Integer(i-1));
                    rangosProveedores.add(new Integer(i));
                    proveedor = proveedores.get(i);
                }
            }
            rangosProveedores.add(j-1);
        }
    }
    public int getIndiceProveedor(Proveedor proveedor) {
        int j = proveedores.size();
        for (int i=0; i<j; i++) {
            if (proveedores.get(i).getIdproveedor() == proveedor.getIdproveedor()) {
                return i;
            }
        }
        return j;
    }
    public List<Compra> getCompras() {
        return compras;
    }
    public List<Detallecompra> getDetalles(int i) {
        return listaDetalles.get(i);
    }
}
