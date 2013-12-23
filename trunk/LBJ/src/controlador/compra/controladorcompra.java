/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.compra;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import modelos.Compra;
import modelos.Detallecompra;
import modelos.NewHibernateUtil;
import modelos.Producto;
import modelos.Productoxproveedor;
import modelos.ProductoxproveedorId;
import modelos.Proveedor;
import modelos.Requerimientocompraxproducto;
import modelos.RequerimientocompraxproductoId;
import modelos.Requirimientocompra;
import paquetevistas.compra.LineaProveedoresPorProducto;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import static paquetevistas.compra.AsignarProveedorProducto.mycontroladoralmacen;

/**
 *
 * @author Jacklin
 */
public class controladorcompra {

    public void guardaproveedor(String razonSocial, Long ruc, String direccion, Integer telefono, String correo, Integer dniContacto, String nombreContacto, Integer celularContacto) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Proveedor proveedor = new Proveedor();
            proveedor.setRazonsocial(razonSocial);
            proveedor.setRuc(ruc);
            proveedor.setDireccion(direccion);
            proveedor.setTelefono(telefono);
            proveedor.setCorreo(correo);
            if (dniContacto != -1) {
                proveedor.setDnicontacto(dniContacto);
            }
            if (celularContacto != -1) {
                proveedor.setTelefonocontacto(celularContacto);
            }
            if (!nombreContacto.isEmpty()) {
                proveedor.setNombrecontacto(nombreContacto);
            }
            proveedor.setEstado("Activo");
            session.save(proveedor);
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

    public List<Proveedor> sacaproveedores() {
        List<Proveedor> proveedores = new ArrayList<Proveedor>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Proveedor> proveedores2 = session.createQuery("FROM Proveedor order by idproveedor").list();
            for (Iterator iterator = proveedores2.iterator(); iterator.hasNext();) {
                Proveedor p = (Proveedor) iterator.next();
                if (p.getEstado().equals("Activo")) {
                    proveedores.add(p);
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
        return proveedores;
    }

    public List<Compra> sacacompras() {
        List<Compra> compras = new ArrayList<Compra>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Compra> compras2 = session.createQuery("FROM Compra order by idcompra").list();
            for (Iterator iterator = compras2.iterator(); iterator.hasNext();) {
                Compra p = (Compra) iterator.next();
                if (p.getEstado().equalsIgnoreCase("En Proceso")) {
                    compras.add(p);
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
        return compras;
    }

    public List<Proveedor> buscaproveedores(String razonSocial, Long ruc, String producto, String estado) {
        List<Proveedor> proveedores = new ArrayList<Proveedor>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        Long comparelong = 0L;
        try {
            tx = session.beginTransaction();
            List<Proveedor> proveedores2 = session.createQuery("FROM Proveedor order by idproveedor").list();
            for (Iterator iterator = proveedores2.iterator(); iterator.hasNext();) {
                Proveedor p = (Proveedor) iterator.next();
                if (((p.getRuc().compareTo(ruc) == 0) || (ruc.compareTo(comparelong) == 0)) && ((p.getRazonsocial().toLowerCase().indexOf(razonSocial.toLowerCase()) != -1) || (razonSocial.isEmpty()))
                        && ((p.getEstado().equalsIgnoreCase(estado)))) {
                    String sqlquery = "FROM Productoxproveedor WHERE idproveedor = :idproveedor";
                    query = session.createQuery(sqlquery);
                    query.setParameter("idproveedor", p.getIdproveedor());
                    if (producto.equalsIgnoreCase("Eliga un producto")) {
                        proveedores.add(p);
                    } else {
                        List<Productoxproveedor> prodxprove = query.list();
                        for (Iterator iterator2 = prodxprove.iterator(); iterator2.hasNext();) {
                            Productoxproveedor producxprovee = (Productoxproveedor) iterator2.next();
                            Producto producto2 = producxprovee.getProducto();
                            if (producto2.getNombre().equalsIgnoreCase(producto)) {
                                proveedores.add(p);
                            }
                        }
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
        return proveedores;
    }

    public boolean guardacompra(Float precioAcordado, Date fechaAcordada, String proveedor) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Proveedor proveedor2 = new Proveedor();
        Transaction tx = null;
        Query query2 = null;

        try {
            tx = session.beginTransaction();
            Compra compra2 = new Compra();
            compra2.setPrecioacordado(precioAcordado);
            compra2.setFechaacordada(fechaAcordada);
            String query = "FROM Proveedor WHERE razonsocial = :proveedor";
            query2 = session.createQuery(query);
            query2.setParameter("proveedor", proveedor);
            proveedor2 = (Proveedor) query2.uniqueResult();
            compra2.setProveedor(proveedor2);
            compra2.setEstado("En Proceso");
            Date date = new Date();
            compra2.setFecharegistro(date);
            session.save(compra2);
            tx.commit();
            return true;
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

    public String sacaRazonsocial(int idprov) {
        String razonSocial = null;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Proveedor> proveedores = session.createQuery("FROM Proveedor").list();
            for (Iterator iterator = proveedores.iterator(); iterator.hasNext();) {
                Proveedor prov = (Proveedor) iterator.next();
                if (prov.getIdproveedor() == idprov) {
                    razonSocial = prov.getRazonsocial();
                    break;
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
        return razonSocial;
    }

    public List<Compra> buscacompras(String estado, Long ruc, String producto, Date fechaent1, Date fechaent2) {
        List<Compra> compras = new ArrayList<Compra>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null, query2 = null, query3 = null;
        Long comparelong = 0L;
        try {
            tx = session.beginTransaction();
            String sqlqueryfecha = "FROM Compra WHERE";
            if (!estado.isEmpty()) {
                sqlqueryfecha += (" estado = :estado AND");
            }

            if ((fechaent1 == null) && (fechaent2 != null)) {
                sqlqueryfecha += (" fechaacordada < :fechaent2");
            } else if ((fechaent2 == null) && (fechaent1 != null)) {
                sqlqueryfecha += (" fechaacordada > :fechaent1");
            } else if ((fechaent1 != null) && (fechaent2 != null)) {
                sqlqueryfecha += (" fechaacordada BETWEEN :fechaent1 AND :fechaent2");
            } else {
                sqlqueryfecha = "FROM Compra";
                if (!estado.isEmpty()) {
                    sqlqueryfecha += (" WHERE estado = :estado");
                }
            }

            query = session.createQuery(sqlqueryfecha);
            if (!estado.isEmpty()) {
                query.setParameter("estado", estado);
            }
            if (fechaent1 != null) {
                query.setTimestamp("fechaent1", fechaent1);
            }
            if (fechaent2 != null) {
                query.setTimestamp("fechaent2", fechaent2);
            }
//            System.out.printf(sqlqueryfecha);
            List<Compra> compras2 = query.list();
            for (Iterator iterator = compras2.iterator(); iterator.hasNext();) {

                Compra comp = (Compra) iterator.next();
                Integer idcompra = comp.getIdcompra();
                Proveedor prov = comp.getProveedor();
                Long rucprov = prov.getRuc();

                if ((rucprov.compareTo(ruc) == 0) || (ruc.compareTo(comparelong) == 0)) {
                    if (producto.isEmpty()) {
                        compras.add(comp);
                    } else {
                        String sqlprod = "FROM Detallecompra WHERE idcompra = :idcompra";
                        query2 = session.createQuery(sqlprod);
                        query2.setParameter("idcompra", idcompra);

                        List<Detallecompra> detcomps = query2.list();
                        for (Iterator iterator2 = detcomps.iterator(); iterator2.hasNext();) {
                            Detallecompra detcomp = (Detallecompra) iterator2.next();
                            Requerimientocompraxproducto reqcompxprod = detcomp.getRequerimientocompraxproducto();
                            Producto prod = reqcompxprod.getProducto();
                            String nombreprod = prod.getNombre();
                            if (nombreprod.equalsIgnoreCase(producto)) {
                                compras.add(comp);
                            }
                        }
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
        return compras;
    }

    public String getProductos(int idcomp) {
        String productos = "";
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        tx = session.beginTransaction();
        Integer contador = 0;
        try {
            String sqlquery = "FROM Detallecompra WHERE idcompra=:idcomp";
            query = session.createQuery(sqlquery);
            query.setParameter("idcomp", idcomp);
            List<Detallecompra> detalle = query.list();
            for (Iterator iterator2 = detalle.iterator(); iterator2.hasNext();) {
                Detallecompra detallecompra = (Detallecompra) iterator2.next();
                Requerimientocompraxproducto requerimiento = detallecompra.getRequerimientocompraxproducto();
                Producto producto = requerimiento.getProducto();
                String nombre = "";
                if (contador.compareTo(0) == 0) {
                    nombre = producto.getNombre();
                } else {
                    nombre = "," + producto.getNombre();
                }
                productos = productos + nombre;
                contador = contador + 1;
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
//        List<Proveedor> proveedores = session.createQuery("FROM Proveedor").list();
        return productos;
    }

    public Proveedor sacaproveedorbyid(int id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        Proveedor prov = new Proveedor();
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Proveedor WHERE idproveedor = :codigo";
            query = session.createQuery(sqlquery);
            query.setParameter("codigo", id);
            prov = (Proveedor) query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return prov;
    }

    public boolean modificarproveedor(Proveedor proveedor, String direccion, Integer telefono, String correo, Integer dniContacto, String nombreContacto, Integer celularContacto, String estado) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        try {
            proveedor.setDireccion(direccion);
            proveedor.setTelefono(telefono);
            proveedor.setCorreo(correo);
            if (dniContacto != -1) {
                proveedor.setDnicontacto(dniContacto);
            }
            if (celularContacto != -1) {
                proveedor.setTelefonocontacto(celularContacto);
            }
            if (!nombreContacto.isEmpty()) {
                proveedor.setNombrecontacto(nombreContacto);
            }
            proveedor.setEstado(estado);
            tx = session.beginTransaction();
            session.update(proveedor);
            session.getTransaction().commit();
            return true;
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

    public Compra sacacomprabyid(int id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        Compra comp = new Compra();
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Compra WHERE idcompra = :codigo";
            query = session.createQuery(sqlquery);
            query.setParameter("codigo", id);
            comp = (Compra) query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return comp;
    }

    public boolean modificarcompra(Compra compra, Float precioAcordado, Date fechaAcordadaReal, String estado) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        try {
            compra.setFechareal(fechaAcordadaReal);
            compra.setEstado(estado);
            compra.setPrecioacordado(precioAcordado);
            tx = session.beginTransaction();
            session.update(compra);
            session.getTransaction().commit();
            return true;
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

    public List<Productoxproveedor> sacaprodxprovbyid(Integer id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        List<Productoxproveedor> prodsxprov = new ArrayList<Productoxproveedor>();
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Productoxproveedor WHERE idproveedor = :codigo";
            query = session.createQuery(sqlquery);
            query.setParameter("codigo", id);
            prodsxprov = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return prodsxprov;
    }

    public Productoxproveedor sacaproductoxproveedor(Integer idprov, Integer idprod) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        Productoxproveedor prodxprov = new Productoxproveedor();
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Productoxproveedor WHERE idproveedor = :idprov AND idproducto = :idprod";
            query = session.createQuery(sqlquery);
            query.setParameter("idprov", idprov);
            query.setParameter("idprod", idprod);
            prodxprov = (Productoxproveedor) query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return prodxprov;
    }

    public boolean eliminarprodxprov(Productoxproveedor prodxprov) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        try {
            tx = session.beginTransaction();
            session.delete(prodxprov);
            session.getTransaction().commit();
            return true;
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

    public Proveedor sacaproveedorbyruc(Long rucprov) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        Proveedor prov = new Proveedor();
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Proveedor WHERE ruc = :rucprov";
            query = session.createQuery(sqlquery);
            query.setParameter("rucprov", rucprov);
            prov = (Proveedor) query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return prov;
    }

    public boolean modificarproductoxproveedor(Productoxproveedor prodxprov, Integer cantidad, Float precio, Date fecha) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        try {
            prodxprov.setCantidadmaxima(cantidad);
            prodxprov.setPrecio(precio);
            prodxprov.setFecha(fecha);
            tx = session.beginTransaction();
            session.update(prodxprov);
            session.getTransaction().commit();
            return true;
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

    public void agregarproductosxprov(Proveedor proveedor, String nomproducto, Integer cantidad, Float precio, Date fecha) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            ProductoxproveedorId prodxprovid = new ProductoxproveedorId();
            Producto producto = mycontroladoralmacen.sacaproductobynombre(nomproducto);
            prodxprovid.setIdproveedor(proveedor.getIdproveedor());
            prodxprovid.setIdproducto(producto.getIdproducto());
            Productoxproveedor prodxprov = new Productoxproveedor();
            prodxprov.setId(prodxprovid);
            prodxprov.setCantidadmaxima(cantidad);
            prodxprov.setPrecio(precio);
            prodxprov.setFecha(fecha);
            session.save(prodxprov);
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

    public List<Detallecompra> sacadetallesxcompbycompid(Integer idcomp) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        List<Detallecompra> prodsxcomp = new ArrayList<Detallecompra>();
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Detallecompra WHERE idcompra = :codigo";
            query = session.createQuery(sqlquery);
            query.setParameter("codigo", idcomp);
            prodsxcomp = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return prodsxcomp;
    }

    public Proveedor sacaproveedorbyidcomp(int idcomp) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        Proveedor prov = new Proveedor();
        Compra comp = new Compra();
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Compra WHERE idcompra = :codigo";
            query = session.createQuery(sqlquery);
            query.setParameter("codigo", idcomp);
            comp = (Compra) query.uniqueResult();
            prov = comp.getProveedor();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return prov;
    }

    public String sacarazonbyidcomp(int idcomp) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        Proveedor prov = new Proveedor();
        Compra comp = new Compra();
        String razon = null;
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Compra WHERE idcompra = :codigo";
            query = session.createQuery(sqlquery);
            query.setParameter("codigo", idcomp);
            comp = (Compra) query.uniqueResult();
            prov = comp.getProveedor();
            razon = prov.getRazonsocial();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return razon;
    }

    public List<Requerimientocompraxproducto> sacareqcompxprod() {
        List<Requerimientocompraxproducto> reqcompxprods = new ArrayList<Requerimientocompraxproducto>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Requerimientocompraxproducto> reqcompxprod2 = session.createQuery("FROM Requerimientocompraxproducto").list();
            for (Iterator iterator = reqcompxprod2.iterator(); iterator.hasNext();) {
                Requerimientocompraxproducto req = (Requerimientocompraxproducto) iterator.next();
                if (req.getRequirimientocompra().getEstado() == 0) {
                    reqcompxprods.add(req);
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
        return reqcompxprods;
    }

    public Proveedor sacaproveedorbyrazon(String razonprov) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        Proveedor prov = new Proveedor();
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Proveedor WHERE razonsocial = :razon";
            query = session.createQuery(sqlquery);
            query.setParameter("razon", razonprov);
            prov = (Proveedor) query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return prov;
    }

    public Requerimientocompraxproducto sacareqcompxprodbyid(int idreq, int idprod) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        Requerimientocompraxproducto reqcompxprod = new Requerimientocompraxproducto();
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Requerimientocompraxproducto WHERE idreqerimientocompra = :idreq AND idproducto = :idprod";
            query = session.createQuery(sqlquery);
            query.setParameter("idreq", idreq);
            query.setParameter("idprod", idprod);
            reqcompxprod = (Requerimientocompraxproducto) query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return reqcompxprod;
    }

    public List<Requerimientocompraxproducto> sacareqcompxprodbyreqxprod(int idreq, int idprod) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        List<Requerimientocompraxproducto> reqscompxprod = new ArrayList<Requerimientocompraxproducto>();
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Requerimientocompraxproducto WHERE idreqerimientocompra = :idreq AND idproducto = :idprod";
            query = session.createQuery(sqlquery);
            query.setParameter("idreq", idreq);
            query.setParameter("idprod", idprod);
            reqscompxprod = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return reqscompxprod;
    }

    public void guardadetallecompra(List<Float> cantidades, List<Requerimientocompraxproducto> reqscompxprod) {
//       Session session = NewHibernateUtil.getSessionFactory().openSession();
//        Detallecompra detallecompra = new Detallecompra();
        Transaction tx = null;
        Productoxproveedor prodxprov = new Productoxproveedor();
        List<Compra> compra = new ArrayList<Compra>();
        Compra ultcompra = new Compra();
//        try {
//           tx = session.beginTransaction(); 
        compra = sacacompras();
        int size = compra.size();
        ultcompra = compra.get(size - 1);
        Date date = new Date();
        int idprov = ultcompra.getProveedor().getIdproveedor();
        for (int i = 0; i < reqscompxprod.size(); i++) {
            Detallecompra detallecompra = new Detallecompra();
            detallecompra.setCantidad(cantidades.get(i));
            detallecompra.setCompra(ultcompra);
            detallecompra.setFecha(date);
            int idprod = reqscompxprod.get(i).getId().getIdproducto();
            prodxprov = sacaproductoxproveedor(idprov, idprod);
            detallecompra.setPrecio(prodxprov.getPrecio());
            detallecompra.setRequerimientocompraxproducto(reqscompxprod.get(i));
            guardadetallecompra(detallecompra);
//                   session.save(detallecompra);
//                   tx.commit();
        }

//        } catch (HibernateException e) {
//            if (tx != null) {
//                tx.rollback();
//            }
//            e.printStackTrace();
//        } finally {
//            session.close();
//        }
    }

    private void guardadetallecompra(Detallecompra detallecompra) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(detallecompra);
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

    public List<Requirimientocompra> sacaRequerimientos(int estado) {
        List<Requirimientocompra> requerimientos = new ArrayList<Requirimientocompra>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Requirimientocompra> requerimientos2 = session.createQuery("FROM Requirimientocompra").list();
            for (Iterator iterator = requerimientos2.iterator(); iterator.hasNext();) {
                Requirimientocompra req = (Requirimientocompra) iterator.next();

                if (estado == -1) {
                    requerimientos.add(req);
                } else {
                    if (req.getEstado() == estado) {
                        requerimientos.add(req);
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
        return requerimientos;
    }

    public List<Requerimientocompraxproducto> sacareqcompxprodbyid(int idreq) {
//        System.out.println("sdfgsdgsdfg");
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        List<Requerimientocompraxproducto> reqcompxprod = new ArrayList<Requerimientocompraxproducto>();
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Requerimientocompraxproducto WHERE idreqerimientocompra = :idreq";
            query = session.createQuery(sqlquery);
            query.setParameter("idreq", idreq);
            List<Requerimientocompraxproducto> reqcompxprod2 = query.list();
            for (Iterator iterator = reqcompxprod2.iterator(); iterator.hasNext();) {
                Requerimientocompraxproducto req = (Requerimientocompraxproducto) iterator.next();
                reqcompxprod.add(req);
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
        return reqcompxprod;
    }

    public void sacaprovxprodbyid(int id, List<Proveedor> proveedores, List<Integer> cantidades,
            List<Float> precios) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        List<Productoxproveedor> productosporproveedores = new ArrayList<Productoxproveedor>();
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Productoxproveedor WHERE idproducto = :codigo";
            query = session.createQuery(sqlquery);
            query.setParameter("codigo", id);
            productosporproveedores = query.list();
            int j = productosporproveedores.size();
            for (int i = 0; i < j; i++) {
                Productoxproveedor productoporproveedor = productosporproveedores.get(i);
                proveedores.add(sacaproveedorbyid(productoporproveedor.getProveedor().getIdproveedor()));
                cantidades.add(productoporproveedor.getCantidadmaxima());
                precios.add(productoporproveedor.getPrecio());
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

    public void createCompra(int idRequerimiento, List<LineaProveedoresPorProducto> lineas,
            List<Requerimientocompraxproducto> requerimientos, Date fechaAcordada) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Requirimientocompra requerimiento = sacarequerimientobyid(idRequerimiento);
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            HandlerCompras handlerCompras = new HandlerCompras(idRequerimiento, lineas, requerimientos, fechaAcordada);
            List<Compra> compras = handlerCompras.getCompras();
            int j = compras.size();
            for (int i = 0; i < j; i++) {
                Compra compra = compras.get(i); // Llega con precio acordado y proveedor
                session.save(compra);
                List<Detallecompra> detalles = handlerCompras.getDetalles(i);
                int l = detalles.size();
                for (int k = 0; k < l; k++) {
                    Detallecompra detalle = detalles.get(k);
                    detalle.setCompra(compra);
                    session.save(detalle);
                }
            }
            requerimiento.setEstado(1);
            session.update(requerimiento);
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

    private Requirimientocompra sacarequerimientobyid(int idreq) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        Requirimientocompra requerimiento = new Requirimientocompra();
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Requirimientocompra WHERE idreqerimientocompra = :idreq";
            query = session.createQuery(sqlquery);
            query.setParameter("idreq", idreq);
            requerimiento = (Requirimientocompra) query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return requerimiento;
    }

    public void modificarcompraestado(Compra compra) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            compra.setEstado("Anulada");
            session.update(compra);
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

    public List<Proveedor> sacaproveedoresbyproducto(int idprod) {
        List<Proveedor> proveedores = new ArrayList<Proveedor>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Productoxproveedor prodsxprov = new Productoxproveedor();
        try {
            tx = session.beginTransaction();
            List<Proveedor> proveedores2 = session.createQuery("FROM Proveedor order by idproveedor").list();
            for (Iterator iterator = proveedores2.iterator(); iterator.hasNext();) {
                Proveedor p = (Proveedor) iterator.next();
                if (p.getEstado().equals("Activo")) {
                    int id = p.getIdproveedor();
                    prodsxprov = sacaproductoxproveedor(id, idprod);
                    if (prodsxprov != null) {
                        proveedores.add(p);
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
        return proveedores;
    }

    public int guardaDetalleOrdenCompraLibre(List<Producto> productos, List<Float> cantidades) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        List<Producto> prodaux = new ArrayList<Producto>();
        for (int i = 0; i < productos.size(); i++) {
            prodaux.add(mycontroladoralmacen.sacaproductobyid(productos.get(i).getIdproducto()));
        }
        mysortprod(prodaux);
        noduplicateprod(prodaux);
        int cantprod = prodaux.size();
        List<Float> cantidadesaux = new ArrayList<Float>();
        try {
            for (int i = 0; i < cantprod; i++) {
                cantidadesaux.add(null);
            }

            for (int i = 0; i < prodaux.size(); i++) {
                for (int j = 0; j < productos.size(); j++) {
                    if (prodaux.get(i).getIdproducto() == productos.get(j).getIdproducto()) {
                        if (cantidadesaux.get(i) == null) {
                            cantidadesaux.set(i, cantidades.get(j));
                        } else {
                            cantidadesaux.set(i, cantidades.get(j) + cantidadesaux.get(i));
                        }
                    }
                }
            }
            for (int i = 0; i < prodaux.size(); i++) {
                Producto pr = prodaux.get(i);
                Requirimientocompra req = mycontroladoralmacen.buscaUltimaOrden();
                RequerimientocompraxproductoId id = new RequerimientocompraxproductoId();
                id.setIdproducto(pr.getIdproducto());
                id.setIdreqerimientocompra(req.getIdreqerimientocompra());
                tx = session.beginTransaction();
                modelos.Requerimientocompraxproducto detalleorden = new Requerimientocompraxproducto();
                detalleorden.setId(id);
                detalleorden.setRequirimientocompra(req);
                detalleorden.setProducto(pr);
                detalleorden.setCantidad(cantidadesaux.get(i));
                session.save(detalleorden);
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
        return 1;
    }

    public void guardaCompraLibre(List<Producto> productos, List<Float> cantidades, List<Proveedor> proveedores, Requirimientocompra req, Date fecha) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        List<Proveedor> provaux = new ArrayList<Proveedor>();
        for (int i = 0; i < proveedores.size(); i++) {
            provaux.add(sacaproveedorbyid(proveedores.get(i).getIdproveedor()));
        }
        mysortprov(provaux);
        noduplicateprov(provaux);
        List<Compra> compras = new ArrayList<Compra>();
        Productoxproveedor prodxprov = new Productoxproveedor();
        Requerimientocompraxproducto reqcompxprod = new Requerimientocompraxproducto();
        Compra ultcompra = new Compra();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            for (int i = 0; i < provaux.size(); i++) {
                Compra compra = new Compra();
                Proveedor proveedor = provaux.get(i);
                compra.setEstado("En Proceso");
                compra.setFechaacordada(fecha);
                compra.setFechareal(fecha);
                Date registro = new Date();
                compra.setFecharegistro(registro);
                compra.setProveedor(proveedor);
                guardacompra(compra);
//                session.save(compra);
                compras = sacacompras();
                int size = compras.size();
                float precio = 0;
                ultcompra = compras.get(size - 1);
                for (int j = 0; j < proveedores.size(); j++) {
                    if (proveedores.get(j).getIdproveedor() == proveedor.getIdproveedor()) {
                        Detallecompra detallecompra = new Detallecompra();
                        detallecompra.setCantidad(cantidades.get(j));
                        detallecompra.setCompra(ultcompra);
                        detallecompra.setFecha(fecha);
                        prodxprov = sacaproductoxproveedor(proveedor.getIdproveedor(), productos.get(j).getIdproducto());
                        detallecompra.setPrecio(cantidades.get(j) * prodxprov.getPrecio());
                        precio = precio + detallecompra.getPrecio();
                        reqcompxprod = sacareqcompxprodbyid(req.getIdreqerimientocompra(), productos.get(j).getIdproducto());
                        detallecompra.setRequerimientocompraxproducto(reqcompxprod);
                        guardadetallecompra(detallecompra);
                    }
                }
                ultcompra.setPrecioacordado(precio);
                actualizacompra(ultcompra);
                req.setEstado(1);
                session.update(req);
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

    private void mysortprod(List<Producto> prodaux) {
        Producto prod;
        for (int i = 0; i < prodaux.size() - 1; i++) {
            for (int j = i; j < prodaux.size(); j++) {
                if (prodaux.get(j).getIdproducto() < prodaux.get(i).getIdproducto()) {
                    prod = prodaux.get(i);
                    prodaux.set(i, prodaux.get(j));
                    prodaux.set(j, prod);
                }
            }
        }
    }

    private void noduplicateprod(List<Producto> lista) {
        for (int i = 0; i < lista.size(); i++) {
            while (i < lista.size() - 1 && lista.get(i).getIdproducto() == lista.get(i + 1).getIdproducto()) {
                Producto paux = new Producto();
                paux = lista.get(i);
                lista.set(i, paux);
                lista.remove(i + 1);
            }
        }
    }

    private void mysortprov(List<Proveedor> provaux) {
        Proveedor prov;
        for (int i = 0; i < provaux.size() - 1; i++) {
            for (int j = i; j < provaux.size(); j++) {
                if (provaux.get(j).getIdproveedor() < provaux.get(i).getIdproveedor()) {
                    prov = provaux.get(i);
                    provaux.set(i, provaux.get(j));
                    provaux.set(j, prov);
                }
            }
        }
    }

    private void noduplicateprov(List<Proveedor> lista) {
        for (int i = 0; i < lista.size(); i++) {
            while (i < lista.size() - 1 && lista.get(i).getIdproveedor() == lista.get(i + 1).getIdproveedor()) {
                Proveedor paux = new Proveedor();
                paux = lista.get(i);
                lista.set(i, paux);
                lista.remove(i + 1);
            }
        }
    }

    private void guardacompra(Compra compra) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(compra);
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

    private void actualizacompra(Compra ultcompra) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(ultcompra);
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

    public boolean esprodDelprv(String producto, String proveedor) {
        Producto prod= new Producto();
        Proveedor prov=new Proveedor();
        Productoxproveedor prodxprov = null;
        prod=mycontroladoralmacen.sacaproductobynombre(producto);
        prov=sacaproveedorbyrazon(proveedor);
        prodxprov=sacaproductoxproveedor(prov.getIdproveedor(),prod.getIdproducto());
        if(prodxprov.equals(null)){
           return true; 
        } else{
            return false;
        }    
    }

    public boolean existeproveedor(Long ruc) {
        boolean existe=false;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        Proveedor prov =new Proveedor();
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Proveedor WHERE ruc = :ruc";
            query = session.createQuery(sqlquery);
            query.setParameter("ruc", ruc);
            prov=(Proveedor)query.uniqueResult();
            if(prov!=null){
                existe=true;
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
        return existe;
    }

    public boolean existeproveedor(String razon) {
        boolean existe=false;
        List <Proveedor> proveedores=new ArrayList<Proveedor>();
        proveedores=sacaproveedores();
        for(int i=0; i<proveedores.size();i++){
            if(proveedores.get(i).getRazonsocial().equalsIgnoreCase(razon)){
                existe=true;
                break;
            }
        }
        return existe;
    }

}
