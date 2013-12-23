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
import modelos.Cliente;
import modelos.Detallefactura;
import modelos.Detalleventa;
import modelos.Factura;
import modelos.Igvt;
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
import paquetevistas.ventas.ProductoC;
import paquetevistas.ventas.VentaNueva;
import paquetevistas.ventas.Ventas;

/**
 *
 * @author mariofcandia
 */
public class ControllerVentas {

    public List<Venta> getVentas() {
        List<Venta> ventas = new ArrayList<Venta>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Venta> ventas2 = session.createQuery("FROM Venta").list();
            for (Iterator iterator = ventas2.iterator(); iterator.hasNext();) {
                Venta v = (Venta) iterator.next();
                ventas.add(v);
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
        return ventas;
    }
    public List<Detalleventa> getDetalles(Venta v) {
        List<Detalleventa> detalles = new ArrayList<Detalleventa>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Detalleventa> detalles2 = session.createQuery("FROM Detalleventa").list();
            for (Iterator iterator = detalles2.iterator(); iterator.hasNext();) {
                Detalleventa detalle = (Detalleventa) iterator.next();
                if ( detalle.getVenta().getIdventa() == v.getIdventa() ) {
                    detalles.add(detalle);
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
        return detalles;
    }
    public List<Venta> getVentas(Cliente cliente, int estado) {
        List<Venta> ventas = new ArrayList<Venta>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Venta> ventas2 = session.createQuery("FROM Venta").list();
            for (Iterator iterator = ventas2.iterator(); iterator.hasNext();) {
                Venta v = (Venta) iterator.next();
                if ( (cliente == null || cliente.getIdcliente() == v.getCliente().getIdcliente()) &&
                        (estado == 0 || ((estado - 1) == Integer.parseInt(v.getEstado())))) {
                    ventas.add(v);
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
        return ventas;
    }
    public List<Venta> getVentas(Cliente cliente, int estado, int orden) {
        List<Venta> ventas = new ArrayList<Venta>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Venta> ventas2 = session.createQuery("FROM Venta").list();
            for (Iterator iterator = ventas2.iterator(); iterator.hasNext();) {
                Venta v = (Venta) iterator.next();
                if ( (cliente == null || cliente.getIdcliente() == v.getCliente().getIdcliente()) &&
                        (estado == 0 || ((estado - 1) == Integer.parseInt(v.getEstado()))) ){ //&&
                        //(v.getOrdenproduccion() != null && v.getOrdenproduccion().getIdordenproduccion() == orden)) {
                    ventas.add(v);
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
        return ventas;
    }
    public List<Venta> getVentas(int orden) {
        List<Venta> ventas = new ArrayList<Venta>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Venta> ventas2 = session.createQuery("FROM Venta").list();
            for (Iterator iterator = ventas2.iterator(); iterator.hasNext();) {
                Venta v = (Venta) iterator.next();
                //if ( (v.getOrdenproduccion() != null && v.getOrdenproduccion().getIdordenproduccion() == orden)) {
                    ventas.add(v);
                //}
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
        return ventas;
    }
    public List<Venta> getVentasRegistradas() {
        List<Venta> ventas = new ArrayList<Venta>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Venta> ventas2 = session.createQuery("FROM Venta").list();
            for (Iterator iterator = ventas2.iterator(); iterator.hasNext();) {
                Venta v = (Venta) iterator.next();
                if (Integer.parseInt(v.getEstado()) == 0) {
                    ventas.add(v);
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
        return ventas;
    }
//    public String getLastVenta(Cliente c) {
//        Set v = c.getVentas();
//        List<Venta> ventas;
//        ventas = Arrays.asList((Venta[])v.toArray());
//        return ventas.get(0).getFecharegistro().toString();
//    }
    public Venta getVenta(int id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Venta> ventas2 = session.createQuery("FROM Venta").list();
            for (Iterator iterator = ventas2.iterator(); iterator.hasNext();) {
                Venta v1 = (Venta) iterator.next();
                if (v1.getIdventa()== id) {
                    return v1;
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
    public List<Venta> getVentasRegistradas(Date date) {
        List<Venta> ventas = new ArrayList<Venta>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Venta> ventas2 = session.createQuery("FROM Venta").list();
            for (Iterator iterator = ventas2.iterator(); iterator.hasNext();) {
                Venta v1 = (Venta) iterator.next();
                Date date2 = v1.getFechaentregaacordada();
                Long time;
                Long time2 = date2.getTime();
                boolean time3 = true;
                if (date != null) {
                    time = date.getTime();
                    time3 = time - 1000*60*60*24 <= time2;
                }
                if (time3 && Integer.parseInt(v1.getEstado()) == 0) {
                    ventas.add(v1);
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
        return ventas;
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
    public void delete(int id, Ventas ventas) {
        Venta venta = getVenta(id);
        if (venta != null) {
            Session session = NewHibernateUtil.getSessionFactory().openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Venta v = getVenta(id);
                if (Integer.parseInt(v.getEstado()) == 2) {
                    JOptionPane.showMessageDialog(ventas.getRootPane(),
                            "La venta ya ha sido cancelada antes, no se puede cancelar", "Mensaje",
                            JOptionPane.OK_OPTION);
                    return;
                } else if (Integer.parseInt(v.getEstado()) != 0) {
                    JOptionPane.showMessageDialog(ventas.getRootPane(),
                            "La venta ya fue despachada, no se puede cancelar", "Mensaje",
                            JOptionPane.OK_OPTION);
                    return;
                    //devolver(v);
                }
                v.setEstado(String.valueOf(2));
                session.update(v);
                cancelFactura(v);
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
    public void cancelFactura(Venta venta) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Factura> facturas = session.createQuery("FROM Factura").list();
            for (Iterator iterator = facturas.iterator(); iterator.hasNext();) {
                Factura f = (Factura) iterator.next();
                f.setEstado(2);
                session.update(f);
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
                if (p.getEstado() == 1 && p.getTipoproducto().getIdtipoproducto() == 4) {
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
    public List<Producto> getProductos2(String nombre) { // Muestra productos con stock para agregarlos
        // a la orden de produccion
        List<Producto> productos = new ArrayList<Producto>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Producto WHERE idtipoproducto = :idtipoproducto");
            query.setParameter("idtipoproducto", 4);
            List<Producto> productos2 = query.list();
//            List<Producto> productos2 = session.createQuery("FROM Producto").list();
            for (Iterator iterator = productos2.iterator(); iterator.hasNext();) {
                Producto p = (Producto) iterator.next();
                float stock = p.getStock();
                float stockComprometido = p.getStockcomprometido();
                float stockSolicitado = p.getStocksolicitado();
                float cantidad =  stock + stockSolicitado - stockComprometido;
                float stockEstimado = calcularStockEstimado(p);
                if (cantidad >= stockEstimado) {
                    if (p.getEstado() == 1 && p.getTipoproducto().getIdtipoproducto() == 4 && 
                            p.getNombre().toLowerCase().indexOf(nombre.toLowerCase()) != -1) {
                        productos.add(p);
                    }
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
    public List<Producto> getProductos2() { // Busca productos sin stock (para orden de produccion)
        List<Producto> productos = new ArrayList<Producto>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Producto WHERE idtipoproducto = :idtipoproducto");
            query.setParameter("idtipoproducto", 4);
            List<Producto> productos2 = query.list();
//            List<Producto> productos2 = session.createQuery("FROM Producto").list();
            for (Iterator iterator = productos2.iterator(); iterator.hasNext();) {
                Producto p = (Producto) iterator.next();
                if (p.getEstado() == 1 && p.getTipoproducto().getIdtipoproducto() == 4) {
                    float stock = p.getStock();
                    float stockComprometido = p.getStockcomprometido();
                    float stockSolicitado = p.getStocksolicitado();
                    float cantidad = stockComprometido - stock - stockSolicitado;

                    float stockEstimado = calcularStockEstimado(p);
                    if (-cantidad >= stockEstimado) {
                        productos.add(p);
                    }
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
                ControllerClientes controllerClientes = new ControllerClientes();
                Cliente c = controllerClientes.getCliente(cliente.getIdcliente());
                venta.setCliente(c);
                System.out.println(cliente);
                venta.setEstado(String.valueOf(0));
                venta.setPrecioacordado(monto);
                venta.setIgv(Float.parseFloat(String.valueOf(monto*0.18)));
                Date date = new Date();
                date.setTime(System.currentTimeMillis());
                venta.setFecharegistro(date);
                venta.setFechaentregaacordada(this.getFechaAcordada(tablaProductosC));
                session.update(c);
                session.save(venta);
                int j = tablaProductosC.size();
                Detalleventa detalle;
                for(int i = 0; i<j; i++) {
                    ProductoC productoC = tablaProductosC.get(i);
                    detalle = new Detalleventa();
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
    public Venta getCreateVenta(Cliente cliente, List<ProductoC> tablaProductosC, float monto) {
        Venta venta = new Venta();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            ControllerClientes controllerClientes = new ControllerClientes();
            Cliente c = controllerClientes.getCliente(cliente.getIdcliente());
            venta.setCliente(c);
            System.out.println(cliente);
            venta.setEstado(String.valueOf(0));
            venta.setPrecioacordado(monto);
            venta.setIgv(Float.parseFloat(String.valueOf(monto*0.18)));
            Date date = new Date();
            date.setTime(System.currentTimeMillis());
            venta.setFecharegistro(date);
            venta.setFechaentregaacordada(this.getFechaAcordada(tablaProductosC));
            session.update(c);
            session.save(venta);
            Factura factura = new Factura();
            factura.setCliente(cliente);
            factura.setDireccionentrega(cliente.getDireccion1());
            factura.setEstado(0);
            factura.setFechaemision(date);
            factura.setIgv(venta.getIgv());
            factura.setSubtotal(venta.getPrecioacordado());
            factura.setTotal(venta.getPrecioacordado() + venta.getIgv());
            session.save(factura);
            int j = tablaProductosC.size();
            Detalleventa detalle;
            float pesoTotal = 0;
            float pesoProducto = 0;
            for(int i = 0; i<j; i++) {
                ProductoC productoC = tablaProductosC.get(i);
                Producto p = this.getProducto(productoC.getIdProducto());
                p.setStockcomprometido(p.getStockcomprometido() + productoC.getCantidad());
                pesoProducto = p.getPeso() * productoC.getCantidad();
                pesoTotal = pesoTotal + pesoProducto;
                session.update(p);
                detalle = new Detalleventa();
                detalle.setVenta(venta);
                detalle.setFecha(productoC.getFechaCotizada());
                detalle.setProducto(this.getProducto(productoC.getIdProducto()));
                detalle.setCantidad(productoC.getCantidad());
                detalle.setPreciounitario(productoC.getPrecioUnd());
                session.save(detalle);
                Detallefactura detallefactura = new Detallefactura();
                detallefactura.setDetalleventa(detalle);
                detallefactura.setFactura(factura);
                session.save(detallefactura);
            }
            factura.setPesototal(pesoTotal);
            session.update(factura);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return venta;
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

    public float cantidadAlmacen(int producto) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            Query query = session.createQuery("FROM Producto WHERE idproducto = :idproducto");
            query.setParameter("idproducto", producto);
            List<Producto> p = query.list();
            if (p.size() > 0) {
                return p.get(0).getStock();
            }
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return 0;
    }
    public Factura getFactura(Venta venta) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            Query query = session.createQuery("FROM Detalleventa WHERE idventa = :idventa");
            query.setParameter("idventa", venta.getIdventa());
            List<Detalleventa> detallesVentas = query.list();
            query = session.createQuery("FROM Detallefactura WHERE iddetalleventa = :iddetalle");
            query.setParameter("iddetalle", detallesVentas.get(0).getIddetalleventa());
            List<Detallefactura> detallesFactura = query.list();
            query = session.createQuery("FROM Factura WHERE idfactura = :iddetalle");
            query.setParameter("iddetalle", detallesFactura.get(0).getFactura().getIdfactura());
            List<Factura> facturas = query.list();
            if (facturas.size() > 0) {
                return facturas.get(0);
            }
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
    public float getIgv() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            Query query = session.createQuery("FROM Igvt");
            List<Igvt> igvs = query.list();
            if (igvs.size() > 0) {
                return igvs.get(igvs.size()-1).getPorcentaje();
            } else return 0;
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return 0;
    }
    public void setIgv(Float igv) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Igvt igv1 = new Igvt();
            igv1.setPorcentaje(igv);
            Date date = new Date();
            igv1.setFechainiciovigencia(date);
            session.save(igv1);
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
    public void getProductosAProducir(List<ProductoC> productos, List<ProductoC> productos2,
            List<ProductoC> productos3) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Producto> productos4 = session.createQuery("FROM Producto").list();
            for (Iterator iterator = productos4.iterator(); iterator.hasNext();) {
                Producto p = (Producto) iterator.next();
                float stock = p.getStock();
                float stockComprometido = p.getStockcomprometido();
                float stockSolicitado = p.getStocksolicitado();
                float cantidad =  stock - stockComprometido + stockSolicitado;
                float stockEstimado = calcularStockEstimado(p);
                if (p.getTipoproducto().getIdtipoproducto() == 4 && cantidad < stockEstimado) {
                    ProductoC productoC = new ProductoC(p,stockEstimado - cantidad,0);
                    productos.add(productoC);
                    productoC = new ProductoC(p,stockEstimado,0);
                    productos2.add(productoC);
                    productoC = new ProductoC(p,stockEstimado - cantidad,0);
                    productos3.add(productoC);
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
    }
    public float calcularStockEstimado(Producto p) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        float diasTotales = 0;
        float cantidadTotal = 0;
        try {
            tx = session.beginTransaction();
            List<Venta> ventas = session.createQuery("FROM Venta").list();
            float dias = 0;
            for (Iterator iterator = ventas.iterator(); iterator.hasNext();) {
                Venta v = (Venta) iterator.next();
                if (Integer.parseInt(v.getEstado()) == 0 || v.getFechaentregareal() == null) {
                    continue;
                }
                float cantidad = 0;
                Detalleventa detalle = checkProductoInVenta(p,v);
                if (detalle != null) {
                    cantidad = detalle.getCantidad();
                    cantidadTotal = cantidadTotal + cantidad;
                    dias = (v.getFechaentregareal().getTime() - v.getFecharegistro().getTime()) / (1000 * 60 * 60 * 24);
                    diasTotales = diasTotales + dias;
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
        float cantidadMes = 0;
        if (diasTotales != 0) {
            cantidadMes = 30 * cantidadTotal / diasTotales;
        }
        if (cantidadMes > p.getStockminimo() * 30) {
            return cantidadMes;
        }
        return p.getStockminimo() * 30;
    }

    private Detalleventa checkProductoInVenta(Producto p, Venta v) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Detalleventa> detalles2 = session.createQuery("FROM Detalleventa").list();
            for (Iterator iterator = detalles2.iterator(); iterator.hasNext();) {
                Detalleventa detalle = (Detalleventa) iterator.next();
                if (detalle.getProducto().getIdproducto() == p.getIdproducto() && 
                        detalle.getVenta().getIdventa() == v.getIdventa()) {
                    return detalle;
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

    
    //CARGA MASIVA VENTA
    
        public Venta guardaventa(Venta v){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(v);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        } return v;
    }
        
        public void agregardetalleventa(Venta v, Producto p, Float cantidad, Float precio, Date fecha) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            modelos.Detalleventa detalle =  new modelos.Detalleventa();
            detalle.setCantidad(cantidad);
            detalle.setProducto(p);
            detalle.setVenta(v);
            detalle.setPreciounitario(precio);
            detalle.setFecha(fecha);
            session.save(detalle);
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
