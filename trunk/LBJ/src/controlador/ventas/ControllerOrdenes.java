/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.ventas;

import controlador.almacen.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import modelos.Almacen;
import modelos.Asignacion;
import modelos.Cliente;
import modelos.Detalleordenproduccion;
import modelos.DetalleordenproduccionId;
import modelos.Detalleventa;
import modelos.Motivo;
import modelos.NewHibernateUtil;
import modelos.Ordenproduccion;
import modelos.Producto;
import modelos.Tipoproducto;
import modelos.Unidadmedida;
import modelos.Venta;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import paquetevistas.ventas.OrdenesDeProduccion;
import paquetevistas.ventas.ProductoC;
import paquetevistas.ventas.VentaNueva;
import paquetevistas.ventas.Ventas;

/**
 *
 * @author mariofcandia
 */
public class ControllerOrdenes {

    public List<Ordenproduccion> getOrdenes() {
        List<Ordenproduccion> ordenes = new ArrayList<Ordenproduccion>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Ordenproduccion> ventas2 = session.createQuery("FROM Ordenproduccion").list();
            for (Iterator iterator = ventas2.iterator(); iterator.hasNext();) {
                Ordenproduccion v = (Ordenproduccion) iterator.next();
//                if (c.getEstado() == 1) {
                    ordenes.add(v);
//                }
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return ordenes;
    }
    public List<Ordenproduccion> getOrdenes(Date fecha, int estado) {
        List<Ordenproduccion> ordenes = new ArrayList<Ordenproduccion>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Ordenproduccion> ordenes2 = session.createQuery("FROM Ordenproduccion").list();
            for (Iterator iterator = ordenes2.iterator(); iterator.hasNext();) {
                Ordenproduccion o = (Ordenproduccion) iterator.next();
                Date inicio = o.getFechainicio();
                Date fin = o.getFechafin();
                boolean start = true;
                boolean end = true;
                if (fecha != null) {
                    start = inicio.getTime() <= fecha.getTime();
                    end = fin.getTime() >= fin.getTime();
                }
                if ( (start && end) &&
                        (estado == 0 || (estado - 1) == o.getEstado())) {
                    ordenes.add(o);
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return ordenes;
    }
//    public String getLastVenta(Cliente c) {
//        Set v = c.getVentas();
//        List<Venta> ventas;
//        ventas = Arrays.asList((Venta[])v.toArray());
//        return ventas.get(0).getFecharegistro().toString();
//    }
    public Ordenproduccion getOrden(int id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Ordenproduccion> ordenes2 = session.createQuery("FROM Ordenproduccion").list();
            for (Iterator iterator = ordenes2.iterator(); iterator.hasNext();) {
                Ordenproduccion o1 = (Ordenproduccion) iterator.next();
                if (o1.getIdordenproduccion() == id) {
                    return o1;
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
    public void updateCliente(Cliente cliente, String nombre, String ruc, String direccion, String telefono, String correo, String dniContacto, String nombreContacto, String telefonoContacto, String direccionEntrega) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
            try {
                tx = session.beginTransaction();
                cliente.setRazonsocial(nombre);
                cliente.setRuc(Long.parseLong(ruc));
                cliente.setDireccion(direccion);
                cliente.setTelefono(Integer.parseInt(telefono));
                cliente.setCorreo(correo);
                cliente.setDnicontacto(dniContacto);
                cliente.setNombrecontacto(nombreContacto);
                cliente.setTelefonocontacto(Integer.parseInt(telefonoContacto));
                cliente.setDireccion1(direccionEntrega);
                session.update(cliente);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) {
                    tx.rollback();
                }
                e.printStackTrace();
            } finally {
                session.close();
            }
    }
    public void createCliente(String nombre, String ruc, String direccion, String telefono, String correo, String dniContacto, String nombreContacto, String telefonoContacto, String direccionEntrega) {
        Cliente cliente = new Cliente();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
            try {
                tx = session.beginTransaction();
                cliente.setRazonsocial(nombre);
                cliente.setRuc(Long.getLong(ruc));
                cliente.setDireccion(direccion);
                cliente.setTelefono(Integer.parseInt(telefono));
                cliente.setCorreo(correo);
                cliente.setDnicontacto(dniContacto);
                cliente.setNombrecontacto(nombreContacto);
                cliente.setTelefonocontacto(Integer.parseInt(telefonoContacto));
                cliente.setDireccion1(direccionEntrega);
                cliente.setEstado(1);
                session.save(cliente);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) {
                    tx.rollback();
                }
                e.printStackTrace();
            } finally {
                session.close();
            }
    }
    public void delete(int id, OrdenesDeProduccion ordenes) {
        Ordenproduccion orden = getOrden(id);
        if (orden != null) {
            Session session = NewHibernateUtil.getSessionFactory().openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                boolean asignaciones = checkAsignaciones(orden);
                if (orden.getEstado() == 0 && !asignaciones) {
                    orden.setEstado(4);
                    session.update(orden);
                } else {
                    JOptionPane.showMessageDialog(ordenes.getRootPane(),
                            "La orden de producción está en proceso, no se puede cancelar", "Mensaje",
                            JOptionPane.OK_OPTION);
                }
                
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) {
                    tx.rollback();
                }
                e.printStackTrace();
            } finally {
                session.close();
            }
        }
    }
    public List<Producto> getProductos(String nombre) {
        List<Producto> productos = new ArrayList<Producto>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Producto> productos2 = session.createQuery("FROM Producto").list();
            for (Iterator iterator = productos2.iterator(); iterator.hasNext();) {
                Producto p = (Producto) iterator.next();
                if (p.getEstado() == 1 && p.getTipoproducto().getIdtipoproducto() == 4 && 
                        p.getNombre().toLowerCase().indexOf(nombre.toLowerCase()) != -1) {
                    productos.add(p);
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return productos;
    }
    public List<Producto> getProductos() {
        List<Producto> productos = new ArrayList<Producto>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Producto> productos2 = session.createQuery("FROM Producto").list();
            for (Iterator iterator = productos2.iterator(); iterator.hasNext();) {
                Producto p = (Producto) iterator.next();
                if (p.getTipoproducto().getIdtipoproducto() == 4) {
                    productos.add(p);
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return productos;
    }
    public Producto getProducto(int id) {
        List<Producto> productos = new ArrayList<Producto>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Producto> productos2 = session.createQuery("FROM Producto").list();
            for (Iterator iterator = productos2.iterator(); iterator.hasNext();) {
                Producto p = (Producto) iterator.next();
                if (p.getIdproducto() == id) {
                    return p;
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public void createVenta(Cliente cliente, List<ProductoC> tablaProductosC, float monto) {
        Venta venta = new Venta();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
            try {
                tx = session.beginTransaction();
                venta.setCliente(cliente);
                venta.setEstado(String.valueOf(0));
                venta.setPrecioacordado(monto);
                venta.setIgv(Float.parseFloat(String.valueOf(monto*0.18)));
                Date date = new Date();
                date.setTime(System.currentTimeMillis());
                venta.setFecharegistro(date);
                venta.setFechaentregaacordada(this.getFechaAcordada(tablaProductosC));
                session.save(venta);
                int j = tablaProductosC.size();
                for(int i = 0; i<j; i++) {
                    ProductoC productoC = tablaProductosC.get(i);
                    Detalleventa detalle = new Detalleventa();
                    detalle.setVenta(venta);
                    detalle.setFecha(productoC.getFechaCotizada());
                    detalle.setProducto(this.getProducto(productoC.getIdProducto()));
                    detalle.setCantidad(productoC.getCantidad());
                    detalle.setPreciounitario(productoC.getPrecioUnd());
                    session.save(detalle);
                }
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) {
                    tx.rollback();
                }
                e.printStackTrace();
            } finally {
                session.close();
            }
    }

    private Date getFechaAcordada(List<ProductoC> tablaProductosC) {
        int j = tablaProductosC.size();
        Date date = new Date();
        date.setTime(0);
        Date date2 = new Date();
        date.setTime(0);
        for(int i=0; i<j; i++) {
            date2 = tablaProductosC.get(i).getFechaCotizada();
            if (date.before(date2)) {
                date.setTime(date2.getTime());
            }
        }
        return date;
    }

    private boolean checkAsignaciones(Ordenproduccion orden) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Asignacion> asignaciones2 = session.createQuery("FROM Asignacion").list();
            if (asignaciones2.size() != 0) {
                return true;
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    public void createOrden(List<Venta> ventas) {
        int j = ventas.size();
        Detalles detalles = new Detalles();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            for(int i=0; i<j; i++) {
                Venta v = ventas.get(i);
                Query query = session.createQuery("FROM Detalleventa WHERE idventa = :idventa");
                query.setParameter("idventa", v.getIdventa());
                v.setEstado(String.valueOf(1));
                session.update(v);
                List<Detalleventa> detalles2 = query.list();
                int l = detalles2.size();
                for (int k=0; k<l; k++) {
                    detalles.add(detalles2.get(k));
                }
            }
            Ordenproduccion o = detalles.getOrden();
            session.save(o);
            
            tx.commit();
            tx.begin();
            int n = detalles.size();
            for (int m=0; m<n; m++) {
                Detalleordenproduccion detalle = detalles.getDetalle(m);
                detalle.setOrdenproduccion(o);
                
                DetalleordenproduccionId id=new DetalleordenproduccionId();
                id.setIdordenproduccion(o.getIdordenproduccion());
                id.setIdproducto(detalle.getProducto().getIdproducto());
                detalle.setId(id);
                
                session.save(detalle);
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        
    }
    public void createOrden(List<Venta> ventas, List<ProductoC> productos) {
        int j = ventas.size();
        Detalles detalles = new Detalles();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            for(int i=0; i<j; i++) {
                Venta v = ventas.get(i);
                Query query = session.createQuery("FROM Detalleventa WHERE idventa = :idventa");
                query.setParameter("idventa", v.getIdventa());
                v.setEstado(String.valueOf(1));
                session.update(v);
                List<Detalleventa> detalles2 = query.list();
                int l = detalles2.size();
                for (int k=0; k<l; k++) {
                    detalles.add(detalles2.get(k));
                }
            }
            Ordenproduccion o = detalles.getOrden();
            session.save(o);
            
            tx.commit();
            tx.begin();
            int n = detalles.size();
            for (int m=0; m<n; m++) {
                Detalleordenproduccion detalle = detalles.getDetalle(m);
                detalle.setOrdenproduccion(o);
                
                DetalleordenproduccionId id=new DetalleordenproduccionId();
                id.setIdordenproduccion(o.getIdordenproduccion());
                id.setIdproducto(detalle.getProducto().getIdproducto());
                detalle.setId(id);
                ProductoC producto = in(productos,detalle.getProducto().getIdproducto());
                if (producto != null) {
                    float cant = producto.getCantidad();
                    detalle.setCantidad(cant);
                }
                session.save(detalle);
            }
            int y = productos.size();
            for (int x=0; x<y; x++) {
                Detalleordenproduccion detalle = new Detalleordenproduccion();
                detalle.setCantidad(productos.get(x).getCantidad());
                detalle.setOrdenproduccion(o);
                detalle.setProducto(getProducto(productos.get(x).getIdProducto()));
                session.save(detalle);
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        
    }

    public Date getLastOrdenFecha() {
        Date date = new Date();
        long time = System.currentTimeMillis();
        for (int i=0; i<29; i++) {
            time = time + 1000*60*60*24;
        }
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Ordenproduccion> ordenes2 = session.createQuery("FROM Ordenproduccion").list();
            if (!ordenes2.isEmpty()) {
                time = ordenes2.get(ordenes2.size()-1).getFechafin().getTime() + 1000*60*60*24;
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        date.setTime(time);
        return date;
    }

    public List<Detalleordenproduccion> getDetalles(Ordenproduccion orden) {
        List<Detalleordenproduccion> detalles = null;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Detalleordenproduccion WHERE idordenproduccion = :idordenproduccion");
            query.setParameter("idordenproduccion", orden.getIdordenproduccion());
            detalles = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return detalles;
    }
    public static void actualizarVentas(Ordenproduccion orden) throws Exception{
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Venta WHERE idordenproduccion = :idproduccion");
            query.setParameter("idproduccion", orden.getIdordenproduccion());
            List<Venta> ventas = query.list();
            int l = ventas.size();
            for (int k=0; k<l; k++) {
                Venta v = ventas.get(k);
                v.setEstado(String.valueOf(2));
                session.save(v);
            }
            tx.commit();
    }
    public void createOrdenProductos(List<ProductoC> productos) {
        int j = productos.size();
        Detalles detalles = new Detalles();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Ordenproduccion orden = new Ordenproduccion();
            orden = new Ordenproduccion();
            orden.setEstado(0);
            Date time = new Date();
            time.setTime(System.currentTimeMillis());
            orden.setFecharegistro(time);
            ControllerOrdenes controllerOrdenes = new ControllerOrdenes();
            Date time2 = new Date();
            Date time3 = new Date();
            time2 = controllerOrdenes.getLastOrdenFecha();
            orden.setFechainicio(time2);
            time3.setTime(time2.getTime());
            for (int i=0; i<29; i++) {
                time3.setTime(time3.getTime() + 1000 * 60 * 60 * 24);
            }
            orden.setFechafin(time3);
            session.save(orden);
            for (int i=0; i<j; i++) {
                Detalleordenproduccion detalle = new Detalleordenproduccion();
                detalle.setOrdenproduccion(orden);
                
                ProductoC producto = productos.get(i);
                Producto p = getProducto(producto.getIdProducto());
                p.setStocksolicitado(p.getStocksolicitado() + producto.getCantidad());
                DetalleordenproduccionId id=new DetalleordenproduccionId();
                id.setIdordenproduccion(orden.getIdordenproduccion());
                id.setIdproducto(producto.getIdProducto());
                detalle.setId(id);
                detalle.setCantidad(producto.getCantidad());
                session.save(detalle);
                session.update(p);
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public ProductoC in(List<ProductoC> productos, int producto) {
        int j = productos.size();
        for (int i=0; i<j; i++) {
            if (productos.get(i).getIdProducto() == producto) {
                ProductoC productoC = productos.get(i);
                productos.remove(i);
                return productoC;
            }
        }
        return null;
    }

}
