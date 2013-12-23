/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.ventas;

import controlador.almacen.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.swing.JTextField;
import modelos.Almacen;
import modelos.Cliente;
import modelos.Motivo;
import modelos.NewHibernateUtil;
import modelos.Producto;
import modelos.Tipoproducto;
import modelos.Unidadmedida;
import modelos.Venta;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import paquetevistas.ventas.Ventas;

/**
 *
 * @author mariofcandia
 */
public class ControllerClientes {

    public List<Cliente> getClientes() {
        List<Cliente> clientes = new ArrayList<Cliente>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Cliente> clientes2 = session.createQuery("FROM Cliente").list();
            for (Iterator iterator = clientes2.iterator(); iterator.hasNext();) {
                Cliente c = (Cliente) iterator.next();
//                if (c.getEstado() == 1) {
                    clientes.add(c);
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
        return clientes;
    }
    public List<Cliente> getClientes(String razonSocial, String ruc, int estado) {
        List<Cliente> clientes = new ArrayList<Cliente>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Cliente> clientes2 = session.createQuery("FROM Cliente").list();
            for (Iterator iterator = clientes2.iterator(); iterator.hasNext();) {
                Cliente c = (Cliente) iterator.next();
                if ((razonSocial.isEmpty() || c.getRazonsocial().toLowerCase().indexOf(razonSocial.toLowerCase()) != -1) &&
                        (ruc.isEmpty() || c.getRuc().toString().equalsIgnoreCase(ruc)) &&
                        ((estado == 0) || estado == c.getEstado())) {
                    clientes.add(c);
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
        return clientes;
    }
    public String getLastVenta(Cliente c) {
        Set v = c.getVentas();
        List<Venta> ventas;
        ventas = Arrays.asList((Venta[])v.toArray());
        return ventas.get(0).getFecharegistro().toString();
    }
    public Cliente getCliente(int id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Cliente> clientes2 = session.createQuery("FROM Cliente").list();
            for (Iterator iterator = clientes2.iterator(); iterator.hasNext();) {
                Cliente c1 = (Cliente) iterator.next();
                if (c1.getIdcliente() == id) {
                    return c1;
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
                cliente.setRuc(Long.parseLong(ruc));
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
    public void delete(int id) {
        Cliente cliente = getCliente(id);
        if (cliente != null) {
            Session session = NewHibernateUtil.getSessionFactory().openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                cliente.setEstado(2);
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
    }

    public Object getRazonSocial(int id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Cliente> clientes2 = session.createQuery("FROM Cliente").list();
            for (Iterator iterator = clientes2.iterator(); iterator.hasNext();) {
                Cliente c1 = (Cliente) iterator.next();
                if (c1.getIdcliente() == id) {
                    return c1.getRazonsocial();
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
    
    //////*********METODO PARA LA CARGA MASIVA DE VENTAS
    
    public Cliente sacaClientexNombre(String nombre) {
        Cliente cli = null;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Cliente> productos2 = session.createQuery("FROM Cliente").list();
            for (Iterator iterator = productos2.iterator(); iterator.hasNext();) {
                Cliente c = (Cliente) iterator.next();
                if (c.getRazonsocial().equalsIgnoreCase(nombre)) {
                    cli = c;
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
        return cli;
    }
}
