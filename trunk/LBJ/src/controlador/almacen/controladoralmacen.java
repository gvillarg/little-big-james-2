/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.almacen;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import javax.swing.JTextField;
import modelos.Actividad;
//import modelos.Actividadxproducto;
//import modelos.ActividadxproductoId;
import modelos.Almacen;
import modelos.Compra;
import modelos.Detallecompra;
import modelos.Detalleordenproduccion;
import modelos.Detalleventa;
import modelos.Motivo;
import modelos.Movimiento;
import modelos.Movsalidadetalle;
import modelos.MovsalidadetalleId;
import modelos.NewHibernateUtil;
import modelos.Ordenproduccion;
import modelos.Producto;
import modelos.Productoxmovimiento;
import modelos.ProductoxmovimientoId;
import modelos.Productoxreceta;
import modelos.ProductoxrecetaId;
import modelos.Receta;
import modelos.Requerimientocompraxproducto;
import modelos.RequerimientocompraxproductoId;
import modelos.Requirimientocompra;
import modelos.Tipoproducto;
import modelos.Unidadmedida;
import modelos.Venta;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author mariofcandia
 */
public class controladoralmacen {

    public List<Producto> sacaproductos() {
        List<Producto> productos = new ArrayList<Producto>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Producto> productos2 = session.createQuery("FROM Producto").list();
            for (Iterator iterator = productos2.iterator(); iterator.hasNext();) {
                Producto p = (Producto) iterator.next();
                if (p.getEstado() == 1) {
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

    public List<Actividad> sacaactividades() {
        List<Actividad> actividades = new ArrayList<Actividad>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Actividad> actividades2 = session.createQuery("FROM Actividad").list();
            for (Iterator iterator = actividades2.iterator(); iterator.hasNext();) {
                Actividad a = (Actividad) iterator.next();
                actividades.add(a);
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
        return actividades;
    }

    public List<Movimiento> sacamovimientos() {
        List<Movimiento> movimientos = new ArrayList<Movimiento>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Movimiento> movimientos2 = session.createQuery("FROM Movimiento where estado = 1").list();
            for (Iterator iterator = movimientos2.iterator(); iterator.hasNext();) {
                Movimiento m = (Movimiento) iterator.next();
                movimientos.add(m);
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
        return movimientos;
    }

    public List<Producto> sacaninguno() {
        List<Producto> productos = new ArrayList<Producto>();
        Producto p = new Producto();
        Unidadmedida um = new Unidadmedida();
        Tipoproducto tp = new Tipoproducto();
        tp.setIdtipoproducto(1);
        tp.setNombre("-");
        tp.setDescripcion("-");
        um.setNombre("-");
        um.setDescripcion("-");
        um.setIdunidadmedida(1);
        p.setNombre("Seleccione");
        p.setTipoproducto(tp);
        p.setStock(0);
        p.setUnidadmedida(um);
        productos.add(p);
        return productos;
    }

    public int verificanumeros(ArrayList<String> numeros) {
        int correccion = 4;
        float valor;
        ArrayList<String> novalidos = new ArrayList<String>();
        novalidos.add("q");
        novalidos.add("w");
        novalidos.add("e");
        novalidos.add("r");
        novalidos.add("s");
        novalidos.add("t");
        novalidos.add("y");
        novalidos.add("u");
        novalidos.add("i");
        novalidos.add("o");
        novalidos.add("p");
        novalidos.add("a");
        novalidos.add("s");
        novalidos.add("d");
        novalidos.add("f");
        novalidos.add("g");
        novalidos.add("h");
        novalidos.add("j");
        novalidos.add("k");
        novalidos.add("l");
        novalidos.add("z");
        novalidos.add("x");
        novalidos.add("c");
        novalidos.add("v");
        novalidos.add("b");
        novalidos.add("n");
        novalidos.add("m");
        novalidos.add(" ");
        novalidos.add("`");
        novalidos.add("~");
        novalidos.add("!");
        novalidos.add("@");
        novalidos.add("#");
        novalidos.add("$");
        novalidos.add("%");
        novalidos.add("^");
        novalidos.add("&");
        novalidos.add("*");
        novalidos.add("(");
        novalidos.add(")");
        novalidos.add("_");
        novalidos.add("+");
        novalidos.add("=");
        novalidos.add("[");
        novalidos.add("{");
        novalidos.add("]");
        novalidos.add("}");
        novalidos.add(";");
        novalidos.add(":");
        novalidos.add("'");
        novalidos.add(",");
        novalidos.add("<");
        novalidos.add(">");
        novalidos.add("/");
        novalidos.add("?");

        for (int i = 0; i < numeros.size(); i++) {
            if (numeros.get(i).indexOf("-") != -1) {
                System.out.println(numeros.get(i).indexOf("-"));
                correccion = 1;
                return correccion;
            }
            for (int m = 0; m < novalidos.size(); m++) {
                if ((numeros.get(i).toLowerCase()).indexOf(novalidos.get(m)) != -1) {
                    correccion = 2;
                    return correccion;
                }
            }
        }
        for (int j = 0; j < numeros.size(); j++) {
            valor = Float.parseFloat(numeros.get(j));
            if (valor == 0) {
                correccion = 3;
                return correccion;
            }
        }
        return correccion;
    }

//    public int verificafechas(ArrayList<Date> fechas) {
//        int correcto = 1;
//        for (int i = 0; i < fechas.size(); i++) {
//            if (fechas.get(i) == null) {
//                correcto = 3;
//                return correcto;
//            }
//        }
//        Date fechahoy = new Date();
//        for (int i = 0; i < fechas.size(); i++) {
//            if ((fechas.get(i)).before(fechahoy)) {
//                correcto = 2;
//                return correcto;
//            }
//        }
//        return correcto;
//    }
    public int verificafechas(ArrayList<String> fechas) {
        for (int i = 0; i < fechas.size(); i++) {
            String dateToValidate = fechas.get(i);
            if (dateToValidate == null) {
                return 2;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            try {
                //if not valid, it will throw ParseException
                Date date = sdf.parse(dateToValidate);
                Date fechahoy = new Date();
                if (date.before(fechahoy) || date.equals(fechahoy)) {
                    return 3;
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return 2;
            }
        }
        return 1;
    }

    public int validafechacabecera(Date fecha) {
        int correcto = 1;
        if (fecha == null) {
            correcto = 2;
            return correcto;
        }
        return correcto;
    }

    public int validanombre(String nombre) {
        int correcto = 3;
        ArrayList<String> novalidos = new ArrayList<String>();
        //novalidos.add(" ");
        novalidos.add("`");
        novalidos.add("~");
        novalidos.add("!");
        novalidos.add("@");
        novalidos.add("#");
        novalidos.add("$");
        novalidos.add("%");
        novalidos.add("^");
        novalidos.add("&");
        novalidos.add("*");
        novalidos.add("(");
        novalidos.add(")");
        novalidos.add("_");
        novalidos.add("+");
        novalidos.add("=");
        novalidos.add("[");
        novalidos.add("{");
        novalidos.add("]");
        novalidos.add("}");
        novalidos.add(";");
        novalidos.add(":");
        novalidos.add("'");
        novalidos.add(",");
        novalidos.add("<");
        novalidos.add(">");
        novalidos.add("/");
        novalidos.add("?");
        if (nombre.isEmpty()) {
            correcto = 1;
            return correcto;
        }
        for (int i = 0; i < novalidos.size(); i++) {
            if ((nombre.toLowerCase()).indexOf(novalidos.get(i)) != -1) {
                correcto = 2;
                return correcto;
            }
        }
        return correcto;
    }

    public int validastockminimo(String sm) {
        int correcto = 4;
        if (sm.isEmpty()) {
            correcto = 1;
            return correcto;
        }
        if (!sm.matches("[0-9]+[.]*[0-9]*")) {
            correcto = 2;
            return correcto;
        }
        return correcto;
    }

    public int verificanumeros2(ArrayList<String> numeros) {
        int correcto = 3;
        String numero = null;
        for (int i = 0; i < numeros.size(); i++) {
            if (numeros.get(i).isEmpty()) {
                correcto = 1;
                return correcto;
            }
            numero = numeros.get(i);
            if (!numero.matches("[0-9]+[.]*[0-9]*")) {
                correcto = 2;
                return correcto;
            }
        }
        return correcto;
    }

    public int verificanumeros1(ArrayList<String> numeros) {
        int correcto = 4;
        for (int i = 0; i < numeros.size(); i++) {
            if (numeros.get(i).isEmpty()) {
                correcto = 1;
                return correcto;
            }
            if (!numeros.get(i).matches("[0-9]+[.]*[0-9]*")) {
                correcto = 2;
                return correcto;
            }
            if (Float.parseFloat(numeros.get(i)) == Float.parseFloat("0")) {
                correcto = 3;
                return correcto;
            }
        }
        return correcto;
    }

    public int validastockmaximo(String sm) {
        int correcto = 4;
        if (sm.isEmpty()) {
            correcto = 1;
            return correcto;
        }
        if (!sm.matches("[0-9]+[.]*[0-9]*")) {
            correcto = 2;
            return correcto;
        }
        if (Float.parseFloat(sm) == Float.parseFloat("0")) {
            correcto = 3;
            return correcto;
        }
        return correcto;
    }
    
    public int validastockmaximo2(String sm, float smin) {
        int correcto = 5;
        if (sm.isEmpty()) {
            correcto = 1;
            return correcto;
        }
        if (!sm.matches("[0-9]+[.]*[0-9]*")) {
            correcto = 2;
            return correcto;
        }
        if (Float.parseFloat(sm) == Float.parseFloat("0")) {
            correcto = 3;
            return correcto;
        }
        float smax = Float.parseFloat(sm);
        if(smax < smin){
            correcto = 4;
            return correcto;
        }
        return correcto;
    }

    public int comparacantidades(List<Producto> productos, ArrayList<String> cantidades) {
        int posicion = 0;
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getStock() < Float.parseFloat(cantidades.get(i))) {
                posicion = i;
                return posicion + 1;
            }
        }
        return posicion;
    }

    public List<Producto> buscaproductos(String nombreb, int estadob, String tipo, String almacen) {
        List<Producto> productos = new ArrayList<Producto>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;

        try {
            tx = session.beginTransaction();
            if (tipo.equalsIgnoreCase("Todos") && almacen.equalsIgnoreCase("Todos")) {//sacatodos
                String sqlquery = "FROM Producto WHERE lower(nombre) like lower(:nombreb) AND estado = :estadob";
                query = session.createQuery(sqlquery);
                query.setParameter("nombreb", "%" + nombreb + "%");
                query.setParameter("estadob", estadob);
            } else if (tipo.equalsIgnoreCase("Todos") && !(almacen.equalsIgnoreCase("Todos"))) {
                Almacen a = getalmacenbyname(almacen);
                String sqlquery = "FROM Producto WHERE lower(nombre) like lower(:nombreb) AND "
                        + "idalmacen = :idalmacenb AND estado = :estadob";
                query = session.createQuery(sqlquery);
                query.setParameter("nombreb", "%" + nombreb + "%");
                query.setParameter("idalmacenb", a.getIdalmacen());
                query.setParameter("estadob", estadob);
            } else if (!(tipo.equalsIgnoreCase("Todos")) && almacen.equalsIgnoreCase("Todos")) {
                Tipoproducto tp = sacatipoproductobynombre(tipo);
                String sqlquery = "FROM Producto WHERE lower(nombre) like lower(:nombreb) AND "
                        + "idtipoproducto = :idtipoproductob AND estado = :estadob";
                query = session.createQuery(sqlquery);
                query.setParameter("nombreb", "%" + nombreb + "%");
                query.setParameter("idtipoproductob", tp.getIdtipoproducto());
                query.setParameter("estadob", estadob);
            } else {
                Tipoproducto tp = sacatipoproductobynombre(tipo);
                Almacen a = getalmacenbyname(almacen);
                String sqlquery = "FROM Producto WHERE lower(nombre) like lower(:nombreb) AND "
                        + "idtipoproducto = :idtipoproductob AND idalmacen = :idalmacenb AND estado = :estadob";
                query = session.createQuery(sqlquery);
                query.setParameter("nombreb", "%" + nombreb + "%");
                query.setParameter("idtipoproductob", tp.getIdtipoproducto());
                query.setParameter("idalmacenb", a.getIdalmacen());
                query.setParameter("estadob", estadob);
            }
            List<Producto> productos2 = query.list();
            for (Iterator iterator = productos2.iterator(); iterator.hasNext();) {
                Producto p = (Producto) iterator.next();
                productos.add(p);
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
//        List<Producto> productos = new ArrayList<Producto>();
//        Session session = NewHibernateUtil.getSessionFactory().openSession();
//        Transaction tx = null;
//        try {
//            tx = session.beginTransaction();
//            List<Producto> productos2 = session.createQuery("FROM Producto").list();
//            for (Iterator iterator = productos2.iterator(); iterator.hasNext();) {
//                Producto p = (Producto) iterator.next();
//                if (p.getEstado() == estado && p.getTipoproducto().getNombre().equalsIgnoreCase(tipo)
//                        && p.getAlmacen().getNombre().equalsIgnoreCase(almacen)) {
//                    String nombreactual = p.getNombre();
//                    if (nombreactual.toLowerCase().indexOf(nombre.toLowerCase()) != -1) {
//                        productos.add(p);
//                    }
//                }
//            }
//            tx.commit();
//        } catch (HibernateException e) {
//            if (tx != null) {
//                tx.rollback();
//            }
//            e.printStackTrace();
//        } finally {
//            session.close();
//        }
//        return productos;
    }
    
    //********************************
    
    public List<Producto> buscaproductos1(String nombreb, String tipo) {
        List<Producto> productos = new ArrayList<Producto>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;

        try {
            tx = session.beginTransaction();
            if (tipo.equalsIgnoreCase("Seleccione")) {//sacatodos
                String sqlquery = "FROM Producto WHERE lower(nombre) like lower(:nombreb)";
                query = session.createQuery(sqlquery);
                query.setParameter("nombreb", "%" + nombreb + "%");
            } else if (tipo.equalsIgnoreCase("Seleccione") ) {
                String sqlquery = "FROM Producto WHERE lower(nombre) like lower(:nombreb) AND ";
                query = session.createQuery(sqlquery);
                query.setParameter("nombreb", "%" + nombreb + "%");
            } else if (!(tipo.equalsIgnoreCase("Seleccione"))) {
                Tipoproducto tp = sacatipoproductobynombre(tipo);
                String sqlquery = "FROM Producto WHERE lower(nombre) like lower(:nombreb) AND "
                        + "idtipoproducto = :idtipoproductob";
                query = session.createQuery(sqlquery);
                query.setParameter("nombreb", "%" + nombreb + "%");
                query.setParameter("idtipoproductob", tp.getIdtipoproducto());
            } else {
                Tipoproducto tp = sacatipoproductobynombre(tipo);
 
                String sqlquery = "FROM Producto WHERE lower(nombre) like lower(:nombreb) AND "
                        + "idtipoproducto = :idtipoproductob";
                query = session.createQuery(sqlquery);
                query.setParameter("nombreb", "%" + nombreb + "%");
                query.setParameter("idtipoproductob", tp.getIdtipoproducto());
            }
            List<Producto> productos2 = query.list();
            for (Iterator iterator = productos2.iterator(); iterator.hasNext();) {
                Producto p = (Producto) iterator.next();
                productos.add(p);
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
//        List<Producto> productos = new ArrayList<Producto>();
//        Session session = NewHibernateUtil.getSessionFactory().openSession();
//        Transaction tx = null;
//        try {
//            tx = session.beginTransaction();
//            List<Producto> productos2 = session.createQuery("FROM Producto").list();
//            for (Iterator iterator = productos2.iterator(); iterator.hasNext();) {
//                Producto p = (Producto) iterator.next();
//                if (p.getEstado() == estado && p.getTipoproducto().getNombre().equalsIgnoreCase(tipo)
//                        && p.getAlmacen().getNombre().equalsIgnoreCase(almacen)) {
//                    String nombreactual = p.getNombre();
//                    if (nombreactual.toLowerCase().indexOf(nombre.toLowerCase()) != -1) {
//                        productos.add(p);
//                    }
//                }
//            }
//            tx.commit();
//        } catch (HibernateException e) {
//            if (tx != null) {
//                tx.rollback();
//            }
//            e.printStackTrace();
//        } finally {
//            session.close();
//        }
//        return productos;
    }

    public List<Movimiento> sacamovimientosentradas() {
        List<Movimiento> movimientos = new ArrayList<Movimiento>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;

        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Movimiento WHERE tipo = :tipoint "
                    + "AND estado = :estado";
            query = session.createQuery(sqlquery);
            query.setParameter("tipoint", 1);
            query.setParameter("estado", 1);
            List<Movimiento> movimientos2 = query.list();
            for (Iterator iterator = movimientos2.iterator(); iterator.hasNext();) {
                Movimiento m = (Movimiento) iterator.next();
                movimientos.add(m);
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
        return movimientos;
    }

    public List<Movimiento> buscamovimientos(String tipoint, String motivo, Date fecha, Date fecha2, int estado) {
        List<Movimiento> movimientos = new ArrayList<Movimiento>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;

        try {
            tx = session.beginTransaction();
            if (tipoint.equalsIgnoreCase("Todos") && motivo.equalsIgnoreCase("Todos")) {
                if (fecha == null && fecha2 == null) {
                    String sqlquery = "FROM Movimiento WHERE "
                            + "estado = :estado";
                    query = session.createQuery(sqlquery);
                    query.setParameter("estado", estado);
                } else if (fecha != null && fecha2 == null) {
                    String sqlquery = "FROM Movimiento WHERE"
                            + " fecha >= :fecha AND estado = :estado";
                    query = session.createQuery(sqlquery);
                    query.setParameter("fecha", fecha);
                    query.setParameter("estado", estado);
                } else if (fecha == null && fecha2 != null) {
                    String sqlquery = "FROM Movimiento WHERE"
                            + " fecha <= :fecha AND estado = :estado";
                    query = session.createQuery(sqlquery);
                    query.setParameter("fecha", fecha2);
                    query.setParameter("estado", estado);
                } else {
                    String sqlquery = "FROM Movimiento WHERE"
                            + " fecha >= :fecha1 AND fecha <= :fecha2 AND estado = :estado";
                    query = session.createQuery(sqlquery);
                    query.setParameter("fecha1", fecha);
                    query.setParameter("fecha2", fecha2);
                    query.setParameter("estado", estado);
                }
            } else if (tipoint.equalsIgnoreCase("Todos") && !(motivo.equalsIgnoreCase("Todos"))) {
                if (fecha == null && fecha2 == null) {
                    String sqlquery = "FROM Movimiento WHERE idmotivo = :motivo "
                            + "AND estado = :estado";
                    query = session.createQuery(sqlquery);
                    query.setParameter("motivo", Integer.parseInt(motivo));
                    query.setParameter("estado", estado);
                } else if (fecha != null && fecha2 == null) {
                    String sqlquery = "FROM Movimiento WHERE idmotivo = :motivo AND"
                            + " fecha >= :fecha AND estado = :estado";
                    query = session.createQuery(sqlquery);
                    query.setParameter("motivo", Integer.parseInt(motivo));
                    query.setParameter("fecha", fecha);
                    query.setParameter("estado", estado);
                } else if (fecha == null && fecha2 != null) {
                    String sqlquery = "FROM Movimiento WHERE idmotivo = :motivo AND"
                            + " fecha <= :fecha AND estado = :estado";
                    query = session.createQuery(sqlquery);
                    query.setParameter("motivo", Integer.parseInt(motivo));
                    query.setParameter("fecha", fecha2);
                    query.setParameter("estado", estado);
                } else {
                    String sqlquery = "FROM Movimiento WHERE idmotivo = :motivo AND"
                            + " fecha >= :fecha1 AND fecha <= :fecha2 AND estado = :estado";
                    query = session.createQuery(sqlquery);
                    query.setParameter("motivo", Integer.parseInt(motivo));
                    query.setParameter("fecha1", fecha);
                    query.setParameter("fecha2", fecha2);
                    query.setParameter("estado", estado);
                }
            } else if (!(tipoint.equalsIgnoreCase("Todos")) && motivo.equalsIgnoreCase("Todos")) {
                if (fecha == null && fecha2 == null) {
                    String sqlquery = "FROM Movimiento WHERE tipo = :tipoint "
                            + "AND estado = :estado";
                    query = session.createQuery(sqlquery);
                    query.setParameter("tipoint", Integer.parseInt(tipoint));
                    query.setParameter("estado", estado);
                } else if (fecha != null && fecha2 == null) {
                    String sqlquery = "FROM Movimiento WHERE tipo = :tipoint AND"
                            + " fecha >= :fecha AND estado = :estado";
                    query = session.createQuery(sqlquery);
                    query.setParameter("tipoint", Integer.parseInt(tipoint));
                    query.setParameter("fecha", fecha);
                    query.setParameter("estado", estado);
                } else if (fecha == null && fecha2 != null) {
                    String sqlquery = "FROM Movimiento WHERE tipo = :tipoint AND"
                            + " fecha <= :fecha AND estado = :estado";
                    query = session.createQuery(sqlquery);
                    query.setParameter("tipoint", Integer.parseInt(tipoint));
                    query.setParameter("fecha", fecha2);
                    query.setParameter("estado", estado);
                } else {
                    String sqlquery = "FROM Movimiento WHERE tipo = :tipoint AND"
                            + " fecha >= :fecha1 AND fecha <= :fecha2 AND estado = :estado";
                    query = session.createQuery(sqlquery);
                    query.setParameter("tipoint", Integer.parseInt(tipoint));
                    query.setParameter("fecha1", fecha);
                    query.setParameter("fecha2", fecha2);
                    query.setParameter("estado", estado);
                }
            } else {
                if (fecha == null && fecha2 == null) {
                    String sqlquery = "FROM Movimiento WHERE tipo = :tipoint AND idmotivo = :motivo "
                            + "AND estado = :estado";
                    query = session.createQuery(sqlquery);
                    query.setParameter("tipoint", Integer.parseInt(tipoint));
                    query.setParameter("motivo", Integer.parseInt(motivo));
                    query.setParameter("estado", estado);
                } else if (fecha != null && fecha2 == null) {
                    String sqlquery = "FROM Movimiento WHERE tipo = :tipoint AND idmotivo = :motivo AND"
                            + " fecha >= :fecha AND estado = :estado";
                    query = session.createQuery(sqlquery);
                    query.setParameter("tipoint", Integer.parseInt(tipoint));
                    query.setParameter("motivo", Integer.parseInt(motivo));
                    query.setParameter("fecha", fecha);
                    query.setParameter("estado", estado);
                } else if (fecha == null && fecha2 != null) {
                    String sqlquery = "FROM Movimiento WHERE tipo = :tipoint AND idmotivo = :motivo AND"
                            + " fecha <= :fecha AND estado = :estado";
                    query = session.createQuery(sqlquery);
                    query.setParameter("tipoint", Integer.parseInt(tipoint));
                    query.setParameter("motivo", Integer.parseInt(motivo));
                    query.setParameter("fecha", fecha2);
                    query.setParameter("estado", estado);
                } else {
                    String sqlquery = "FROM Movimiento WHERE tipo = :tipoint AND idmotivo = :motivo AND"
                            + " fecha >= :fecha1 AND fecha <= :fecha2 AND estado = :estado";
                    query = session.createQuery(sqlquery);
                    query.setParameter("tipoint", Integer.parseInt(tipoint));
                    query.setParameter("motivo", Integer.parseInt(motivo));
                    query.setParameter("fecha1", fecha);
                    query.setParameter("fecha2", fecha2);
                    query.setParameter("estado", estado);
                }
            }
            List<Movimiento> movimientos2 = query.list();
            for (Iterator iterator = movimientos2.iterator(); iterator.hasNext();) {
                Movimiento m = (Movimiento) iterator.next();
                movimientos.add(m);
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
        return movimientos;
    }
    
    public List<Movsalidadetalle> sacamovimientosalidadetalle(int idmovimiento, int idproducto){
        List<Movsalidadetalle> movdtllsalida = new ArrayList<Movsalidadetalle>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;

        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Movsalidadetalle WHERE "
                    + "idproducto = :idproductob AND "
                    + "idmovimientos = :idmovimientob";
            query = session.createQuery(sqlquery);
            query.setParameter("idproductob", idproducto);
            query.setParameter("idmovimientob", idmovimiento);
            List<Movsalidadetalle> movdtllsalida2 = query.list();
            for (Iterator iterator = movdtllsalida2.iterator(); iterator.hasNext();) {
                Movsalidadetalle a = (Movsalidadetalle) iterator.next();
                movdtllsalida.add(a);
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
        return movdtllsalida;
    }

    public List<Actividad> buscaactividad(String nombre, String descripcion) {
        List<Actividad> actividades = new ArrayList<Actividad>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;

        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Actividad WHERE "
                    + "lower(nombre) LIKE lower(:nombreb) AND "
                    + "lower(descripcion) LIKE lower(:descripcionb)";
            query = session.createQuery(sqlquery);
            query.setParameter("nombreb", "%" + nombre + "%");
            query.setParameter("descripcionb", "%" + descripcion + "%");
            List<Actividad> actividad2 = query.list();
            for (Iterator iterator = actividad2.iterator(); iterator.hasNext();) {
                Actividad a = (Actividad) iterator.next();
                actividades.add(a);
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
        return actividades;
    }

    public List<Producto> buscaproductosactividad(String nombreb, Tipoproducto tp) {
        List<Producto> productos = new ArrayList<Producto>();
        //List<Actividad> actividades = new ArrayList<Actividad>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;

        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Producto WHERE "
                    + "lower(nombre) LIKE lower(:nombreb) AND "
                    + "idtipoproducto = :idtipo";
            query = session.createQuery(sqlquery);
            query.setParameter("nombreb", "%" + nombreb + "%");
            query.setParameter("idtipo", tp.getIdtipoproducto());
            List<Producto> productos2 = query.list();
            for (Iterator iterator = productos2.iterator(); iterator.hasNext();) {
                Producto p = (Producto) iterator.next();
                if (p.getEstado() == 1) {
                    //solo pone a los productos activos
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

    public int verificastockactual(Movimiento mov) {
        int todaviatienproductos = 1;

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Productoxmovimiento WHERE idmovimiento = :tipop";
            query = session.createQuery(sqlquery);
            query.setParameter("tipop", mov.getIdmovimiento());
            List<Productoxmovimiento> productosenmovimiento2 = query.list();
            for (Iterator iterator = productosenmovimiento2.iterator(); iterator.hasNext();) {
                Productoxmovimiento pxm = (Productoxmovimiento) iterator.next();
                if (pxm.getStock() != pxm.getStockactual()) {
                    todaviatienproductos = 2;
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

        return todaviatienproductos;
    }

    public int verificaexistenciareceta(String nombre) {
        int verdad = 0;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Receta> recetas = session.createQuery("From Receta").list();
            for (Iterator iterator = recetas.iterator(); iterator.hasNext();) {
                Receta p = (Receta) iterator.next();
                if (p.getNombre().toLowerCase().equalsIgnoreCase(nombre.toLowerCase())) {
                    verdad = 1;
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
        return verdad;
    }

    public int verificaexistenciaproducto(String nombre) {
        int verdad = 0;
        String nombre2;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Producto> productos = session.createQuery("From Producto").list();
            for (Iterator iterator = productos.iterator(); iterator.hasNext();) {
                Producto p = (Producto) iterator.next();
                nombre2 = p.getNombre();
                if (nombre2.toLowerCase().equalsIgnoreCase(nombre.toLowerCase())) {
                    verdad = 1;
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
        return verdad;
    }

    public List<Producto> buscaproductosmovimientos(String nombre, int tipo) {
        List<Producto> productos = new ArrayList<Producto>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Producto WHERE estado = :estadob AND lower(nombre) like lower(:nombreb) "
                    + "AND idtipoproducto = :tipob";
            query = session.createQuery(sqlquery);
            query.setParameter("estadob", 1);
            query.setParameter("nombreb", "%" + nombre + "%");
            query.setParameter("tipob", tipo);
            productos = query.list();
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
    
        public Receta buscaproductoenrecetaporactividad(int idactividad, int idproducto) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        Receta rec = new Receta();

        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Receta WHERE idactividad = :idactividadb AND "
                    + "idproducto = :idproductob";
            query = session.createQuery(sqlquery);
            query.setParameter("idactividadb", idactividad);
            query.setParameter("idproductob", idproducto);
            rec = (Receta) query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return rec;
    }

    public int verificaconsistencia(Actividad act, List<Producto> productos){
        int consistencia = 1;
        int esta = 1;
        Receta r = new Receta();
        if(act.getIdactividad() == 2){//DECORADO
            for(int i=0; i<productos.size();i++){
                r = buscaproductoenrecetaporactividad(3,productos.get(i).getIdproducto());
                if(r != null){
                    consistencia = 2;
                    break;
                }
            }
            return consistencia;
        }else if(act.getIdactividad() == 3){//RELLENO
            for(int i=0; i<productos.size(); i++){
                r = buscaproductoenrecetaporactividad(2,productos.get(i).getIdproducto());
                if(r!= null){
                    consistencia = 3;
                    break;
                }
            }
            return consistencia;
        }
        return consistencia;
    }

    public List<Producto> buscaproductosreceta(String nombre, int tipo, Producto p) {
        List<Producto> productos = new ArrayList<Producto>();
        List<Producto> prodsinelproducto = new ArrayList<Producto>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Producto WHERE estado = :estadob AND lower(nombre) like lower(:nombreb) "
                    + "AND idtipoproducto = :tipob";
            query = session.createQuery(sqlquery);
            query.setParameter("estadob", 1);
            query.setParameter("nombreb", "%" + nombre + "%");
            query.setParameter("tipob", tipo);
            productos = query.list();
            for (int i = 0; i < productos.size(); i++) {
                if (productos.get(i).getIdproducto() != p.getIdproducto()) {
                    prodsinelproducto.add(productos.get(i));
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
        return prodsinelproducto;
    }

    public List<Tipoproducto> sacatipoproducto() {
        List<Tipoproducto> tipoproductos = new ArrayList<Tipoproducto>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Tipoproducto> tipoproductos2 = session.createQuery("FROM Tipoproducto order by 1").list();
            for (Iterator iterator = tipoproductos2.iterator(); iterator.hasNext();) {
                Tipoproducto p = (Tipoproducto) iterator.next();
                tipoproductos.add(p);
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
        return tipoproductos;
    }

    public List<Almacen> sacaalmacen() {
        List<Almacen> almacenes = new ArrayList<Almacen>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Almacen> almacenes2 = session.createQuery("FROM Almacen").list();
            for (Iterator iterator = almacenes2.iterator(); iterator.hasNext();) {
                Almacen p = (Almacen) iterator.next();
                almacenes.add(p);
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
        return almacenes;
    }

    public List<Motivo> sacamotivos() {
        List<Motivo> motivos = new ArrayList<Motivo>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Motivo> motivos2 = session.createQuery("FROM Motivo order by 1").list();
            for (Iterator iterator = motivos2.iterator(); iterator.hasNext();) {
                Motivo p = (Motivo) iterator.next();
                motivos.add(p);
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
        return motivos;
    }

    public List<Unidadmedida> sacaunidadmedida() {
        List<Unidadmedida> unidades = new ArrayList<Unidadmedida>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Unidadmedida> unidades2 = session.createQuery("FROM Unidadmedida order by 1").list();
            for (Iterator iterator = unidades2.iterator(); iterator.hasNext();) {
                Unidadmedida p = (Unidadmedida) iterator.next();
                unidades.add(p);
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
        return unidades;
    }

    public List<Compra> sacacompras() {
        List<Compra> compras = new ArrayList<Compra>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Compra WHERE estado = :estadob order by 1";
            query = session.createQuery(sqlquery);
            query.setParameter("estadob", "En Proceso");
            List<Compra> compra2 = query.list();
            for (Iterator iterator = compra2.iterator(); iterator.hasNext();) {
                Compra c = (Compra) iterator.next();
                compras.add(c);
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

    public List<Venta> sacaventas() {
        List<Venta> ventas = new ArrayList<Venta>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Venta WHERE estado = :estadob order by 1";
            query = session.createQuery(sqlquery);
            query.setParameter("estadob", "0");
            List<Venta> venta2 = query.list();
            for (Iterator iterator = venta2.iterator(); iterator.hasNext();) {
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

    /*TENGO QUE MEJORAR ESTO PARA QUE SAQUE A UNO NOMAS Y NO SAQUE A TODOS*/
    public String sacanombretipo(int tipo) {
        String nombre = null;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Tipoproducto> productos2 = session.createQuery("FROM Tipoproducto").list();
            for (Iterator iterator = productos2.iterator(); iterator.hasNext();) {
                Tipoproducto p = (Tipoproducto) iterator.next();
                if (p.getIdtipoproducto() == tipo) {
                    nombre = p.getNombre();
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
        return nombre;
    }

    public Tipoproducto sadaidtipo(String nombre) {
        Tipoproducto tipopro = null;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Tipoproducto> productos2 = session.createQuery("FROM Tipoproducto").list();
            for (Iterator iterator = productos2.iterator(); iterator.hasNext();) {
                Tipoproducto p = (Tipoproducto) iterator.next();
                if (p.getNombre().equalsIgnoreCase(nombre)) {
                    tipopro = p;
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
        return tipopro;
    }

    public Almacen sacaidalmacen(String almacen) {
        Almacen almac = null;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Almacen> almacen2 = session.createQuery("FROM Almacen").list();
            for (Iterator iterator = almacen2.iterator(); iterator.hasNext();) {
                Almacen p = (Almacen) iterator.next();
                if (p.getNombre().equalsIgnoreCase(almacen)) {
                    almac = p;
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
        return almac;
    }

    public Unidadmedida sacaidunidad(String unidad) {
        Unidadmedida unid = null;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Unidadmedida> unidad2 = session.createQuery("FROM Unidadmedida").list();
            for (Iterator iterator = unidad2.iterator(); iterator.hasNext();) {
                Unidadmedida p = (Unidadmedida) iterator.next();
                if (p.getNombre().equalsIgnoreCase(unidad)) {
                    unid = p;
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
        return unid;
    }
    /*TENGO QUE MEJORAR ESTO PARA QUE SAQUE A UNO NOMAS Y NO SAQUE A TODOS*/

    public void guardaproducto(String nombre, String descripcion, float stock, float stockminimo,
            float stockmaximo, int estado, Tipoproducto tipoproducto, Almacen idalmacen, Unidadmedida idunidad,
            String peso, String costo) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Producto producto = new Producto();
            producto.setNombre(nombre);
            producto.setDescripcion(descripcion);
            producto.setStock(stock);
            producto.setStockcomprometido(Float.parseFloat("0"));
            producto.setStocksolicitado(Float.parseFloat("0"));
            producto.setStockminimo(stockminimo);
            producto.setStockmaximo(stockmaximo);
            producto.setEstado(estado);
            producto.setTipoproducto(tipoproducto);
            producto.setAlmacen(idalmacen);
            producto.setUnidadmedida(idunidad);
            if (!(peso.isEmpty())) {
                producto.setPeso(Float.parseFloat(peso));
            }
            if (!(costo.isEmpty())) {
                producto.setCosto(Float.parseFloat(costo));
            }
            session.save(producto);
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

    public void eliminaproductoreceta(Productoxreceta pxr) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(pxr);
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

    public void actualizarstock(Producto p, float stock) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        try {
            p.setStock(stock);
            tx = session.beginTransaction();
            session.update(p);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
   
    public void actualizaproductoxmovimiento(int idmovimiento, float cantidad, int idproducto){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        try {
            Productoxmovimiento pxm = sacaproductoxmovimiento(idmovimiento, idproducto);
            pxm.setStockactual(cantidad + pxm.getStockactual());
            tx = session.beginTransaction();
            session.update(pxm);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        
    }
    
    public void actualizaproductoxmovimiento1(int idmovimiento, float cantidad, int idproducto){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        try {
            Productoxmovimiento pxm = sacaproductoxmovimiento(idmovimiento, idproducto);
            pxm.setStockactual(cantidad - pxm.getStock());
            pxm.setStock((cantidad- pxm.getStock()) + pxm.getStock());
            tx = session.beginTransaction();
            session.update(pxm);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        
    }
    
    public void actualizamovimiento(int idmovimiento, float cantidad, int idproducto){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        try {
            Movimiento mov = sacamovimientobyid(idmovimiento);
            mov.setEstado(1);
            actualizaproductoxmovimiento(idmovimiento,cantidad,idproducto);
            tx = session.beginTransaction();
            session.update(mov);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void actualizamovimiento1(int idmovimiento){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        try {
            Movimiento mov = sacamovimientobyid(idmovimiento);
            mov.setEstado(1);
            tx = session.beginTransaction();
            session.update(mov);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void actualizareceta(Receta receta, String nombre) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        try {
            receta.setNombre(nombre);
            tx = session.beginTransaction();
            session.update(receta);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void guardaproductoxreceta(ProductoxrecetaId pxr, Receta r, Producto p, float cantidad) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Productoxreceta productoxreceta = new Productoxreceta();
            productoxreceta.setId(pxr);
            productoxreceta.setProducto(p);
            productoxreceta.setReceta(r);
            productoxreceta.setCantidad(cantidad);
            session.saveOrUpdate(productoxreceta);
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

    public void actualizaestadomovimiento(Movimiento mov) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            mov.setEstado(2);
            session.update(mov);
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

//    public void guardaactividadxproducto(ActividadxproductoId axpid, float cantidad, int tipo) {
//        Session session = NewHibernateUtil.getSessionFactory().openSession();
//        Transaction tx = null;
//        try {
//            tx = session.beginTransaction();
//            Actividadxproducto actividadxproducto = new Actividadxproducto();
//            actividadxproducto.setId(axpid);
//            actividadxproducto.setCantidad(cantidad);
//            actividadxproducto.setTipo(tipo);
//            session.save(actividadxproducto);
//            tx.commit();
//        } catch (HibernateException e) {
//            if (tx != null) {
//                tx.rollback();
//            }
//            e.printStackTrace();
//        } finally {
//            session.close();
//        }
//    }
    public void actualizastockactual(Productoxmovimiento pxm, float valor) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            pxm.setStockactual(valor);
            session.update(pxm);
            System.out.println("actualiza");
            session.getTransaction().commit();
            if (valor == 0) {
                int correcto = 1;
                List<Productoxmovimiento> productosxmo = sacaproductosxmovimiento(pxm.getId().getIdmovimiento());
                for (int i = 0; i < productosxmo.size(); i++) {
                    if (productosxmo.get(i).getStockactual() != 0) {
                        correcto = 2;
                        break;
                    }
                }
                if (correcto == 1) {
                    Movimiento mov = sacamovimientobyid(pxm.getId().getIdmovimiento());
                    actualizaestadomovimiento(mov);
                }
            }
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void guardaproductoxmovimiento(ProductoxmovimientoId pxm,
            Movimiento movimiento, Producto produdcto, float cantidad, Date fechav) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Productoxmovimiento productoxmovimiento = new Productoxmovimiento();
            productoxmovimiento.setId(pxm);
            productoxmovimiento.setFvencimiento(fechav);
            productoxmovimiento.setMovimiento(movimiento);
            productoxmovimiento.setProducto(produdcto);
            productoxmovimiento.setStock(cantidad);
            productoxmovimiento.setStockactual(cantidad);
            session.saveOrUpdate(productoxmovimiento);
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
    public void guardamovsalidadetalle(MovsalidadetalleId mvsdid,Movimiento m, Productoxmovimiento pxm, float valorcancelacion){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Movsalidadetalle movsdtll = new Movsalidadetalle();
            movsdtll.setId(mvsdid);
            movsdtll.setMovimiento(m);
            movsdtll.setProductoxmovimiento(pxm);
            movsdtll.setCantidad(valorcancelacion);
            session.saveOrUpdate(movsdtll);
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
    
    public Productoxmovimiento guardaproductoxmovimiento2(ProductoxmovimientoId pxm,
            Movimiento movimiento, Producto produdcto, float cantidad, Date fechav) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Productoxmovimiento productoxmovimiento = new Productoxmovimiento();
        try {
            tx = session.beginTransaction();
            productoxmovimiento.setId(pxm);
            productoxmovimiento.setFvencimiento(fechav);
            productoxmovimiento.setMovimiento(movimiento);
            productoxmovimiento.setProducto(produdcto);
            productoxmovimiento.setStock(cantidad);
            productoxmovimiento.setStockactual(cantidad);
            session.saveOrUpdate(productoxmovimiento);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return productoxmovimiento;
    }

    public void guardareceta(String nombre, Actividad actv) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Receta receta = new Receta();
            receta.setNombre(nombre);
            receta.setDescripcion("ninguna");
            receta.setActividad(actv);
            Producto p = new Producto();
            p = sacaproductobynombre(nombre);
            receta.setProducto(p);
            session.save(receta);
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
    public int guardarecetasacasuid(String nombre, Actividad actv) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Receta receta = new Receta();
        try {
            tx = session.beginTransaction();
            receta.setNombre(nombre);
            receta.setDescripcion("ninguna");
            receta.setActividad(actv);
            Producto p = new Producto();
            p = sacaproductobynombre(nombre);
            receta.setProducto(p);
            session.save(receta);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return receta.getIdreceta();
    }

    public void cambiaestadocompra(Compra compra) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        try {
            compra.setEstado("Procesada");
            tx = session.beginTransaction();
            session.update(compra);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void cambiaestadoventa(Venta venta) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        try {
            venta.setEstado("1");
            tx = session.beginTransaction();
            session.update(venta);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void guardamovimiento(int tipomovimiento, Almacen alma, Motivo motivo, Date fecha, int estado,
            int compra, int venta) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Movimiento movimiento = new Movimiento();
            movimiento.setFecha(fecha);
            movimiento.setTipo(tipomovimiento);
            movimiento.setEstado(estado);
            movimiento.setAlmacen(alma);
            movimiento.setMotivo(motivo);
            Compra comprac = new Compra();
            Venta ventac = new Venta();
            if (compra == 0) {
                movimiento.setCompra(null);
            } else {
                comprac = sacacomprabyid(compra);
                movimiento.setCompra(comprac);
                cambiaestadocompra(comprac);
            }
            if (venta == 0) {
                movimiento.setVenta(null);
            } else {
                ventac = sacaventabyid(venta);
                movimiento.setVenta(ventac);
                cambiaestadoventa(ventac);
            }
            session.saveOrUpdate(movimiento);
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

    public Productoxmovimiento sacaproductoxmovimiento(int idmovimiento, int idproducto) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        Productoxmovimiento pxm = new Productoxmovimiento();
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Productoxmovimiento WHERE idmovimiento = :codigo1 AND "
                    + "idproducto = :codigo2";
            query = session.createQuery(sqlquery);
            query.setParameter("codigo1", idmovimiento);
            query.setParameter("codigo2", idproducto);
            pxm = (Productoxmovimiento) query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return pxm;
    }

    public Almacen sacaalmacenbyid(int id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        Almacen a = new Almacen();

        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Almacen WHERE idalmacen = :codigo";
            query = session.createQuery(sqlquery);
            query.setParameter("codigo", id);
            a = (Almacen) query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return a;
    }

    public Venta sacaventabyid(int id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        Venta vent = new Venta();

        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Venta WHERE idventa = :codigo";
            query = session.createQuery(sqlquery);
            query.setParameter("codigo", id);
            vent = (Venta) query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return vent;
    }

    public Producto sacaproductobyid(int id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        Producto pro = new Producto();

        try {
            tx = session.beginTransaction();
            //query = session.createQuery("FROM Rol WHERE lower(nombre) LIKE lower(:rolbuscar)");
            String sqlquery = "FROM Producto WHERE idproducto = :codigo";
            query = session.createQuery(sqlquery);
            query.setParameter("codigo", id);
            pro = (Producto) query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return pro;
    }

    public Receta sacarecetabyid(int idproductob) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        Receta rect = new Receta();

        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Receta WHERE idproducto = :idproductob";
            query = session.createQuery(sqlquery);
            query.setParameter("idproductob", idproductob);
            rect = (Receta) query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return rect;
    }

    public Receta sacarecetabyidreceta(int idreceta) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        Receta rect = new Receta();
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Receta WHERE idreceta = :idrecetab";
            query = session.createQuery(sqlquery);
            query.setParameter("idrecetab", idreceta);
            rect = (Receta) query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return rect;
    }

    public Movimiento sacamovimientobyid(int id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        Movimiento mov = new Movimiento();
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Movimiento WHERE idmovimiento = :id";
            query = session.createQuery(sqlquery);
            query.setParameter("id", id);
            mov = (Movimiento) query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return mov;
    }

    public Productoxreceta sacapxr(int idreceta, int idproducto) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        Productoxreceta pxr = new Productoxreceta();

        try {
            tx = session.beginTransaction();
            //query = session.createQuery("FROM Rol WHERE lower(nombre) LIKE lower(:rolbuscar)");
            String sqlquery = "FROM Productoxreceta WHERE idproducto = :idproductob AND idreceta = :idrecetab";
            query = session.createQuery(sqlquery);
            query.setParameter("idproductob", idproducto);
            query.setParameter("idrecetab", idreceta);
            pxr = (Productoxreceta) query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return pxr;
    }

    public Actividad sacaactividadbyid(int id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        Actividad act = new Actividad();

        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Actividad WHERE idactividad = :id";
            query = session.createQuery(sqlquery);
            query.setParameter("id", id);
            act = (Actividad) query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return act;
    }

    public Actividad sacaactividadbyname(String nombre) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        Actividad act = new Actividad();

        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Actividad WHERE nombre = :nombre";
            query = session.createQuery(sqlquery);
            query.setParameter("nombre", nombre);
            act = (Actividad) query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return act;
    }

    public Movimiento sacaultimomovimiento() {
        List<Movimiento> movimientos = new ArrayList<Movimiento>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Movimiento> movimientos2 = session.createQuery("FROM Movimiento").list();
            for (Iterator iterator = movimientos2.iterator(); iterator.hasNext();) {
                Movimiento m = (Movimiento) iterator.next();
                movimientos.add(m);
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
        int posicion = movimientos.size();
        Movimiento mov = movimientos.get(posicion - 1);
        return mov;
    }

    public Motivo sacamotivobyid(int id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        Motivo mot = new Motivo();
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Motivo WHERE idmotivo = :id";
            query = session.createQuery(sqlquery);
            query.setParameter("id", id);
            mot = (Motivo) query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return mot;
    }

    public Motivo sacamotivobyname(String nombre) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        Motivo mot = new Motivo();
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Motivo WHERE nombre = :nombre";
            query = session.createQuery(sqlquery);
            query.setParameter("nombre", nombre);
            mot = (Motivo) query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return mot;
    }

    public Tipoproducto sacatipoproductobynombre(String nombre) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        Tipoproducto tp = new Tipoproducto();
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Tipoproducto WHERE nombre = :nombre";
            query = session.createQuery(sqlquery);
            query.setParameter("nombre", nombre);
            tp = (Tipoproducto) query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return tp;
    }

    public Almacen getalmacenbyname(String nombre) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        Almacen al = new Almacen();

        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Almacen WHERE nombre = :nombre";
            query = session.createQuery(sqlquery);
            query.setParameter("nombre", nombre);
            al = (Almacen) query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return al;
    }

    public Receta sacaidreceta(String nombre) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        Receta rec = new Receta();

        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Receta WHERE lower(nombre) = lower(:nombrer)";
            query = session.createQuery(sqlquery);
            query.setParameter("nombrer", nombre);
            rec = (Receta) query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return rec;
    }

    public Receta sacarecetaxproducto(int idproducto) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        Receta rec = new Receta();

        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Receta WHERE idproducto = :idproducto";
            query = session.createQuery(sqlquery);
            query.setParameter("idproducto", idproducto);
            rec = (Receta) query.uniqueResult();

            tx.commit();
        } catch (HibernateException e) {
            String R = e.toString();
            System.out.print(R);
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } catch (Exception e) {
            String R = e.toString();
            System.out.print(R);
        } finally {
            session.close();
        }
        return rec;
    }

    public List<Producto> sacaproductosmp() {
        List<Producto> productos = new ArrayList<Producto>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        try {
            tx = session.beginTransaction();
            //List<Producto> productos2 = session.createQuery("FROM Producto").list();
            String sqlquery = "FROM Producto WHERE idtipoproducto = :tipop";
            query = session.createQuery(sqlquery);
            query.setParameter("tipop", 1);
            List<Producto> productos2 = query.list();
            for (Iterator iterator = productos2.iterator(); iterator.hasNext();) {
                Producto p = (Producto) iterator.next();
                if (p.getEstado() == 1) {
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

    public List<Producto> sacaproductosdependeactividad(int tipopb) {
        List<Producto> productos = new ArrayList<Producto>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        try {
            tx = session.beginTransaction();
            //List<Producto> productos2 = session.createQuery("FROM Producto").list();
            String sqlquery = "FROM Producto WHERE idtipoproducto = :tipop";
            query = session.createQuery(sqlquery);
            if (tipopb == 4) {
                query.setParameter("tipop", 4);
            } else {
                query.setParameter("tipop", 3);
            }
            List<Producto> productos2 = query.list();
            for (Iterator iterator = productos2.iterator(); iterator.hasNext();) {
                Producto p = (Producto) iterator.next();
                if (p.getEstado() == 1) {
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

//    public List<Producto> sacaproductosactividad(int idactividad) {
//        List<Producto> productos = new ArrayList<Producto>();
//        Session session = NewHibernateUtil.getSessionFactory().openSession();
//        Transaction tx = null;
//        Query query = null;
//        try {
//            tx = session.beginTransaction();
//            String sqlquery = "FROM Actividadxproducto WHERE idactividad = :id";
//            query = session.createQuery(sqlquery);
//            query.setParameter("id", idactividad);
//            List<Actividadxproducto> productos2 = query.list();
//            for (Iterator iterator = productos2.iterator(); iterator.hasNext();) {
//                Actividadxproducto axp = (Actividadxproducto) iterator.next();
//                Producto p = sacaproductobyid(axp.getId().getIdproducto());
//                productos.add(p);
//            }
//            tx.commit();
//        } catch (HibernateException e) {
//            if (tx != null) {
//                tx.rollback();
//            }
//            e.printStackTrace();
//        } finally {
//            session.close();
//        }
//        return productos;
//    }
    public List<Producto> sacaproductosbyalmacen(Almacen almacen) {
        List<Producto> productos = new ArrayList<Producto>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Producto WHERE idalmacen = :id";
            query = session.createQuery(sqlquery);
            query.setParameter("id", almacen.getIdalmacen());
            List<Producto> productos2 = query.list();
            for (Iterator iterator = productos2.iterator(); iterator.hasNext();) {
                Producto p = (Producto) iterator.next();
                if (p.getEstado() == 1) {
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

    public List<Producto> sacaproductosalidaactividad(int id) {
        List<Producto> productos = new ArrayList<Producto>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Receta WHERE idactividad = :id";
            query = session.createQuery(sqlquery);
            query.setParameter("id", id);
            List<Receta> recetas = query.list();
            for (Iterator iterator = recetas.iterator(); iterator.hasNext();) {
                Receta r = (Receta) iterator.next();
                String nombre = r.getNombre();
                Producto p = sacaproductobynombre(nombre);
                if (p.getEstado() == 1) {
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

    public List<Receta> sacarecetabyactividad(int id) {
        List<Receta> recetas = new ArrayList<Receta>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Receta WHERE idactividad = :id";
            query = session.createQuery(sqlquery);
            query.setParameter("id", id);
            List<Receta> recetas2 = query.list();
            for (Iterator iterator = recetas2.iterator(); iterator.hasNext();) {
                Receta r = (Receta) iterator.next();
                Producto p = sacaproductobyid(r.getProducto().getIdproducto());
                if (p.getEstado() == 1) {
                    recetas.add(r);
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
        return recetas;
    }

    public Receta sacarecetabyproducto(int idproductob) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        Receta receta = new Receta();

        try {
            tx = session.beginTransaction();
            //query = session.createQuery("FROM Rol WHERE lower(nombre) LIKE lower(:rolbuscar)");
            String sqlquery = "FROM Receta WHERE idproducto = :idproductob";
            query = session.createQuery(sqlquery);
            query.setParameter("idproductob", idproductob);
            receta = (Receta) query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return receta;
    }

    public List<Producto> sacaproductosdereceta(int idreceta) {
        List<Producto> productos = new ArrayList<Producto>();
        //List<Productoxreceta> pxr = new ArrayList<Productoxreceta>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        try {
            tx = session.beginTransaction();
            //List<Producto> productos2 = session.createQuery("FROM Producto").list();
            String sqlquery = "FROM Productoxreceta WHERE idreceta = :idreceta";
            query = session.createQuery(sqlquery);
            query.setParameter("idreceta", idreceta);
            List<Productoxreceta> pxr2 = query.list();
            for (Iterator iterator = pxr2.iterator(); iterator.hasNext();) {
                Productoxreceta pxr = (Productoxreceta) iterator.next();
                //aca saco al producto por su id y lo pongo en la lista
                Producto p = sacaproductobyid(pxr.getId().getIdproducto());
                productos.add(p);
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
    
    
    /******************/
    
    public List<Producto> sacaproductosxtipo(int idtipo) {
        List<Producto> productos = new ArrayList<Producto>();
        //List<Productoxreceta> pxr = new ArrayList<Productoxreceta>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        try {
            tx = session.beginTransaction();
            //List<Producto> productos2 = session.createQuery("FROM Producto").list();
            String sqlquery = "FROM Producto WHERE idtipoproducto = :idtipoproducto";
            query = session.createQuery(sqlquery);
            query.setParameter("idtipoproducto", idtipo);
            List<Producto> pxr2 = query.list();
            for (Iterator iterator = pxr2.iterator(); iterator.hasNext();) {
                Producto pxr = (Producto) iterator.next();
                //aca saco al producto por su id y lo pongo en la lista
                Producto p = sacaproductobyid(pxr.getIdproducto());
                productos.add(p);
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
    

    public List<Productoxmovimiento> sacaproductosxmovimiento(int idmovimiento) {
        List<Productoxmovimiento> pxms = new ArrayList<Productoxmovimiento>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Productoxmovimiento WHERE idmovimiento = :idmovimiento";
            query = session.createQuery(sqlquery);
            query.setParameter("idmovimiento", idmovimiento);
            List<Productoxreceta> pxm2 = query.list();
            for (Iterator iterator = pxm2.iterator(); iterator.hasNext();) {
                Productoxmovimiento pxm = (Productoxmovimiento) iterator.next();
                pxms.add(pxm);
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
        return pxms;
    }

    public List<Productoxmovimiento> sacaproductosxmovimientoordenadosporfecha(Producto p, List<Movimiento> movimientos) {
        List<Productoxmovimiento> pxms = new ArrayList<Productoxmovimiento>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Productoxmovimiento WHERE idproducto = :idproductob order by 4 ASC";
            query = session.createQuery(sqlquery);
            query.setParameter("idproductob", p.getIdproducto());
            List<Productoxreceta> pxm2 = query.list();
            int esta;
            for (Iterator iterator = pxm2.iterator(); iterator.hasNext();) {
                esta = 0;
                Productoxmovimiento pxm = (Productoxmovimiento) iterator.next();
                for (int i = 0; i < movimientos.size(); i++) {
                    if (movimientos.get(i).getIdmovimiento() == pxm.getId().getIdmovimiento() && pxm.getStockactual() != 0) {
                        esta = 1;
                        break;
                    }
                }
                if (esta == 1) {
                    pxms.add(pxm);
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
        return pxms;
    }

    public List<Productoxreceta> verificatienereceta(int producto) {
        List<Productoxreceta> productosxreceta = new ArrayList<Productoxreceta>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Productoxreceta WHERE idproducto = :productob";
            query = session.createQuery(sqlquery);
            query.setParameter("productob", producto);
            List<Productoxreceta> pxr = query.list();
            for (Iterator iterator = pxr.iterator(); iterator.hasNext();) {
                Productoxreceta pr = (Productoxreceta) iterator.next();
                productosxreceta.add(pr);
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
        return productosxreceta;
    }

    public List<Detallecompra> sacadetallecompra(int idcomprab) {
        List<Detallecompra> detalles = new ArrayList<Detallecompra>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Detallecompra WHERE idcompra = :idcomprab";
            query = session.createQuery(sqlquery);
            query.setParameter("idcomprab", idcomprab);
            List<Detallecompra> dt2 = query.list();
            for (Iterator iterator = dt2.iterator(); iterator.hasNext();) {
                Detallecompra dc = (Detallecompra) iterator.next();
                detalles.add(dc);
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

    public List<Detalleventa> sacadetalleventa(int idventab) {
        List<Detalleventa> detalleventas = new ArrayList<Detalleventa>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Detalleventa WHERE idventa = :idventab";
            query = session.createQuery(sqlquery);
            query.setParameter("idventab", idventab);
            List<Detalleventa> dt2 = query.list();
            for (Iterator iterator = dt2.iterator(); iterator.hasNext();) {
                Detalleventa dc = (Detalleventa) iterator.next();
                detalleventas.add(dc);
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
        return detalleventas;
    }

    public ArrayList<String> sacacantidadreceta(int idreceta) {
        ArrayList<String> cantidades = new ArrayList<String>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Productoxreceta WHERE idreceta = :idreceta";
            query = session.createQuery(sqlquery);
            query.setParameter("idreceta", idreceta);
            List<Productoxreceta> pxr2 = query.list();
            for (Iterator iterator = pxr2.iterator(); iterator.hasNext();) {
                Productoxreceta pxr = (Productoxreceta) iterator.next();
                String cantidad = "" + pxr.getCantidad();
                cantidades.add(cantidad);
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
        return cantidades;
    }

    public Producto sacaproductobynombre(String nombrep) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        Producto pro = new Producto();

        try {
            tx = session.beginTransaction();
            //query = session.createQuery("FROM Rol WHERE lower(nombre) LIKE lower(:rolbuscar)");
            String sqlquery = "FROM Producto WHERE lower(nombre) = lower(:nombrep)";
            query = session.createQuery(sqlquery);
            query.setParameter("nombrep", nombrep);
            pro = (Producto) query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return pro;
    }

//    public Actividadxproducto sacadetalleactividadxproducto(int idactividad, int idproducto) {
//        Session session = NewHibernateUtil.getSessionFactory().openSession();
//        Transaction tx = null;
//        Query query = null;
//        Actividadxproducto axp = new Actividadxproducto();
//        try {
//            tx = session.beginTransaction();
//            String sqlquery = "FROM Actividadxproducto WHERE idactividad = :id1 AND "
//                    + "idproducto =:id2";
//            query = session.createQuery(sqlquery);
//            query.setParameter("id1", idactividad);
//            query.setParameter("id2", idproducto);
//            axp = (Actividadxproducto) query.uniqueResult();
//            tx.commit();
//        } catch (HibernateException e) {
//            if (tx != null) {
//                tx.rollback();
//            }
//            e.printStackTrace();
//        } finally {
//            session.close();
//        }
//        return axp;
//    }
    public String sacanombreunidad(int id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        String nombre = null;
        Unidadmedida unidaddemedida = new Unidadmedida();

        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Unidadmedida WHERE idunidadmedida = :codigo";
            query = session.createQuery(sqlquery);
            query.setParameter("codigo", id);
            unidaddemedida = (Unidadmedida) query.uniqueResult();
            nombre = unidaddemedida.getNombre();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return nombre;
    }

    public boolean editarproducto(Producto product, String nombre, String descripcion,
            float stock, float stockminimo, float stockmaximo, int estado,
            Tipoproducto tipoproducto, Almacen idalmacen, Unidadmedida idunidad,
            String peso, String costo) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;

        try {
            product.setNombre(nombre);
            product.setDescripcion(descripcion);
            product.setStock(stock);
            product.setStockminimo(stockminimo);
            product.setStockmaximo(stockmaximo);
            product.setEstado(estado);
            product.setTipoproducto(tipoproducto);
            product.setAlmacen(idalmacen);
            product.setUnidadmedida(idunidad);
            if (peso.isEmpty() || peso.equalsIgnoreCase(" ")) {
            } else {
                product.setPeso(Float.parseFloat(peso));
            }

            if (costo.isEmpty() || costo.equalsIgnoreCase(" ")) {
            } else {
                product.setCosto(Float.parseFloat(costo));
            }
            tx = session.beginTransaction();
            session.update(product);
            Receta rec = sacarecetabyid(product.getIdproducto());
            if (rec != null) {
                actualizareceta(rec, product.getNombre());
            }
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

    public void editareceta(Receta receta, String nombre, Actividad actividad) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;

        try {
            receta.setActividad(actividad);
            tx = session.beginTransaction();
            session.update(receta);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public boolean eliminarproducto(Producto p) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;

        try {
            p.setEstado(2);
            tx = session.beginTransaction();
            session.update(p);
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

    public void cambiaestadocompraregreso(Compra compra) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        try {
            compra.setEstado("En Proceso");
            tx = session.beginTransaction();
            session.update(compra);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    public void cambiaestadoventaregreso(Venta venta) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        try {
            venta.setEstado("0");
            tx = session.beginTransaction();
            session.update(venta);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    public boolean eliminamovimiento(Movimiento mov) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;

        try {
            mov.setEstado(3);
            //aca evalua si tenia asociado una compra o una venta
            if(mov.getCompra()!=null){
                //regreso al estado que tenia la compra
                Compra c = sacacomprabyid(mov.getCompra().getIdcompra());
                cambiaestadocompraregreso(c);
            }else if(mov.getVenta() != null){
                //regresa al estado que tenia la venta
                Venta v = sacaventabyid(mov.getVenta().getIdventa());
                cambiaestadoventaregreso(v);
            }
            tx = session.beginTransaction();
            session.update(mov);
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

    //*********************************CALCULAR STOCK****************************
    //Saco las ordenes de produccion que aun no se han mandado a producir ni a comprar
    public List<Ordenproduccion> sacaOrdenes() {
        List<Ordenproduccion> ordenes = new ArrayList<Ordenproduccion>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Ordenproduccion WHERE estado= :idestado";
            query = session.createQuery(sqlquery);
            query.setParameter("idestado", 0);
            List<Ordenproduccion> ordenes2 = query.list();
            for (Iterator iterator = ordenes2.iterator(); iterator.hasNext();) {
                Ordenproduccion o = (Ordenproduccion) iterator.next();
                ordenes.add(o);
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

    //*******LE DOY EL NOMBRE DE LA GALLETA Y ME DEVUELE SU ID
    public int buscaGalletaxNombre(String nombre) {
        int idgalleta = 0;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Producto WHERE nombre LIKE: nom";
            query = session.createQuery(sqlquery);
            nombre = "%" + nombre + "%";
            query.setParameter("nom", nombre);
            idgalleta = (int) query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return idgalleta;
    }

    //*******SACO DETALLE POR ORDEN DE COMPRA
    public List<Detalleordenproduccion> sacaDetallexOrden(int id) {
        List<Detalleordenproduccion> detallePlan = new ArrayList<Detalleordenproduccion>();
        Transaction tx = null;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Query query = null;
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Detalleordenproduccion WHERE idordenproduccion= :id";
            query = session.createQuery(sqlquery);
            query.setParameter("id", id);
            List<Detalleordenproduccion> detalle2 = query.list();
            for (Iterator iterator = detalle2.iterator(); iterator.hasNext();) {
                Detalleordenproduccion d = (Detalleordenproduccion) iterator.next();
                detallePlan.add(d);
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
        return detallePlan;
    }

    //************************* PARA CALCULAR LA NECESIDAD ESTIMADA ***************************//
    public List<modelos.Productoxreceta> sacaRecetaporGalleta(int idgalleta) {
        List<modelos.Productoxreceta> prod = new ArrayList<modelos.Productoxreceta>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;

        Receta r = sacarecetaxproducto(idgalleta);
        if(r != null){
            try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Productoxreceta p join p.producto k join k.unidadmedida WHERE idreceta= :idrec";
            query = session.createQuery(sqlquery);
            query.setParameter("idrec", r.getIdreceta());

            List<Object[]> temp = query.list();
            for (Object[] obj : temp) {
                prod.add((modelos.Productoxreceta) obj[0]);
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
        return prod;
        }
        else return null;
        
    }

    //Metodo para ordenar listas
    public void mySort(List<Productoxreceta> lista) {
        Productoxreceta r;
        for (int i = 0; i < lista.size() - 1; i++) {
            for (int j = i; j < lista.size(); j++) {
                if (lista.get(j).getProducto().getIdproducto() < lista.get(i).getProducto().getIdproducto()) {
                    r = lista.get(i);
                    lista.set(i, lista.get(j));
                    lista.set(j, r);
                }
            }
        }
    }

    public void acumulate(List<Productoxreceta> lista) {
        for (int i = 0; i < lista.size(); i++) {
            while (i < lista.size() - 1 && lista.get(i).getProducto().getIdproducto() == lista.get(i + 1).getProducto().getIdproducto()) {
                float aux = lista.get(i).getCantidad() + lista.get(i + 1).getCantidad();
                Productoxreceta paux = new Productoxreceta(lista.get(i).getId(), lista.get(i).getReceta(), lista.get(i).getProducto(), aux);
                lista.set(i, paux);
                lista.remove(i + 1);
            }
        }
    }

    //Inserta Productos Terminados del Pedido, sin agregar a la base
    public Productoxreceta sacaningunoPT(int idproducto, float cantidad) {
        Productoxreceta prodxrec = new Productoxreceta();
        Producto p = sacaproductobyid(idproducto);
        Receta r = new Receta();
        r.setIdreceta(1000);
        prodxrec.setProducto(p);
        prodxrec.setReceta(r);
        prodxrec.setCantidad(cantidad);
        return prodxrec;
    }

    //************************* PARA GUARDAR ORDEN DE COMPRA ***************************//
    public void guardaOrdenCompra() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            //Para setear fecha de hoy
            Calendar c = new GregorianCalendar();
            c.set(Calendar.HOUR_OF_DAY, 0); //anything 0 - 23
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            Date d1 = c.getTime();

            Requirimientocompra req = new Requirimientocompra();
            req.setFechaemision(d1);
            req.setEstado(0);
            session.save(req);
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

    public modelos.Requirimientocompra buscaUltimaOrden() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        List<modelos.Requirimientocompra> listareq = new ArrayList<modelos.Requirimientocompra>();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Movimiento> listareq2 = session.createQuery("FROM Requirimientocompra").list();
            for (Iterator iterator = listareq2.iterator(); iterator.hasNext();) {
                Requirimientocompra m = (Requirimientocompra) iterator.next();
                listareq.add(m);
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

        int posicion = listareq.size();
        Requirimientocompra req = listareq.get(posicion - 1);
        return req;
    }

    public int guardaDetalleOrdenCompra(List<Productoxreceta> lista) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            for (int i = 0; i < lista.size(); i++) {

                Producto pr = lista.get(i).getProducto();
                Requirimientocompra req = buscaUltimaOrden();
                modelos.RequerimientocompraxproductoId id = new RequerimientocompraxproductoId();
                id.setIdproducto(pr.getIdproducto());
                id.setIdreqerimientocompra(req.getIdreqerimientocompra());
                tx = session.beginTransaction();
                modelos.Requerimientocompraxproducto detalleorden = new Requerimientocompraxproducto();
                detalleorden.setId(id);
                detalleorden.setRequirimientocompra(req);
                detalleorden.setProducto(pr);
                detalleorden.setCantidad(lista.get(i).getCantidad());
                session.saveOrUpdate(detalleorden);
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
        return 0;
    }

    public modelos.Ordenproduccion sacoOrdenxid(int id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        Ordenproduccion o = new Ordenproduccion();

        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Ordenproduccion WHERE idordenproduccion = :id";
            query = session.createQuery(sqlquery);
            query.setParameter("id", id);
            o = (Ordenproduccion) query.uniqueResult();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return o;
    }

    public void editaEstadoOrden(Ordenproduccion o, int estado) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;

        try {
            o.setEstado(estado);
            tx = session.beginTransaction();
            session.update(o);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    //Metodo para validar que ningun campo sea null; --> EMPLEADO
    public int validaempleado1(JTextField a, JTextField b, JTextField c, JTextField d, JTextField e) {
        if ((a.getText().trim().isEmpty()) || (a.getText().trim().isEmpty()) || (b.getText().trim().isEmpty())
                || (c.getText().trim().isEmpty()) || (d.getText().trim().isEmpty()) || (e.getText().trim().isEmpty())) {
            return 0;
        } else {
            return 1;
        }
    }

    public int validaempleado(JTextField a, JTextField b, JTextField c, JTextField d, JTextField e, JTextField f, JTextField g,
            JTextField h, JTextField i, JTextField j, JTextField k, JTextField l, JTextField m, JTextField n, JTextField o,
            JTextField p, JTextField q, JTextField r, JTextField s, JTextField t, JTextField u) {

        if ((a.getText().trim().isEmpty()) || (a.getText().trim().isEmpty()) || (b.getText().trim().isEmpty())
                || (c.getText().trim().isEmpty()) || (d.getText().trim().isEmpty()) || (e.getText().trim().isEmpty())
                || (f.getText().trim().isEmpty()) || (g.getText().trim().isEmpty()) || (h.getText().trim().isEmpty())
                || (i.getText().trim().isEmpty()) || (j.getText().trim().isEmpty()) || (k.getText().trim().isEmpty())
                || (l.getText().trim().isEmpty()) || (m.getText().trim().isEmpty()) || (n.getText().trim().isEmpty())
                || (o.getText().trim().isEmpty()) || (p.getText().trim().isEmpty()) || (q.getText().trim().isEmpty())
                || (r.getText().trim().isEmpty()) || (s.getText().trim().isEmpty()) || (t.getText().trim().isEmpty())
                || (u.getText().trim().isEmpty())) {
            return 0;
        } else {
            return 1;
        }
    }

    //SAORI: CARGA MASIVA
    //Es lo mismo que el método guardamovimiento, con la diferencia que hago que me devuelva el movimiento agregado
    //para asignarlo a productoxmovimiento de la carga masiva
    public Movimiento guardamovimiento2(int tipomovimiento, Almacen alma, Motivo motivo, Date fecha, int estado,
            int compra, int venta) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Movimiento movimiento = null;

        try {
            tx = session.beginTransaction();
            movimiento = new Movimiento();
            movimiento.setFecha(fecha);
            movimiento.setTipo(tipomovimiento);
            movimiento.setEstado(estado);
            movimiento.setAlmacen(alma);
            movimiento.setMotivo(motivo);
            movimiento.setCompra(null);
            movimiento.setVenta(null);
            
            session.save(movimiento);

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return movimiento;
    }
    
    //SAORI: CARGA MASIVA
    //Guardar productos provenientes de una lista
    //LLevando aparte el listado de las fechas de vencimiento para agregarlo en la tabla de productoxmovimiento
    public void guardaListaProductos(List<Producto> productos, List<Date> fechas) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Motivo motivo = sacamotivobyid(7);//Nuevo motivo agregado en la base de datos: "7: Ingreso por Carga Masiva"
            
            String nombreAlmacen = null, nombreAlmacenAnterior = null;
            Movimiento movimientoPrincipal = null, movimientoProduccion = null;
            int cont = 0;//lo utilizo para ir leyendo los índices de las fechas de vencimiento en orden
            
            for (Producto producto : productos) {
                session.save(producto);
                
                nombreAlmacen = producto.getAlmacen().getNombre();
                
                if(!(nombreAlmacen.equalsIgnoreCase(nombreAlmacenAnterior))){
//                        guardamovimiento(int tipomovimiento, Almacen alma, Motivo motivo, Date fecha, int estado, int compra, int venta)
                    if(nombreAlmacen.equalsIgnoreCase("Principal"))
                        movimientoPrincipal = guardamovimiento2(1, producto.getAlmacen(), motivo, new Date(), 1, 0, 0);
                    else
                        movimientoProduccion = guardamovimiento2(1, producto.getAlmacen(), motivo, new Date(), 1, 0, 0);
                }
                nombreAlmacenAnterior = nombreAlmacen; 
            }
            
            tx.commit();
//            guardaproductoxmovimiento(ProductoxmovimientoId pxm,
//            Movimiento movimiento, Producto produdcto, float cantidad, Date fechav)
            
            ProductoxmovimientoId id = new ProductoxmovimientoId();
            
            for(Producto producto : productos){
                
                Producto pro = sacaproductobynombre(producto.getNombre());
                
                id.setIdproducto(pro.getIdproducto());
                
                if(producto.getAlmacen().getNombre().equalsIgnoreCase("Principal")){
                    id.setIdmovimiento(movimientoPrincipal.getIdmovimiento());
                    guardaproductoxmovimiento(id, movimientoPrincipal, pro, producto.getStock(), fechas.get(cont++));
                }
                else{
                    id.setIdmovimiento(movimientoProduccion.getIdmovimiento());
                    guardaproductoxmovimiento(id, movimientoProduccion, pro, producto.getStock(), fechas.get(cont++));
                }

//                guardaproductoxmovimiento(ProductoxmovimientoId pxm,
//            Movimiento movimiento, Producto produdcto, float cantidad, Date fechav)
 
            }

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
