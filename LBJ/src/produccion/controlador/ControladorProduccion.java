package produccion.controlador;

import controlador.configuracion.controladorconfiguracion;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import modelos.Actividad;
import modelos.Almacen;
import modelos.Asignacion;
import modelos.Asignacionxproducto;
import modelos.Desempeno;
import modelos.DesempenoId;
import modelos.Asignacionxempleado;
import modelos.Detalleordenproduccion;
import modelos.Empleado;
import modelos.Maquina;
import modelos.Motivo;
import modelos.Movimiento;
import modelos.Movsalidadetalle;
import modelos.MovsalidadetalleId;
import modelos.NewHibernateUtil;
import modelos.Ordenproduccion;
import modelos.Parametrosalgoritmo;
import modelos.Produccion;
import modelos.Produccionxempleado;
import modelos.Produccionxproducto;
import modelos.Producto;
import modelos.Productoxmovimiento;
import modelos.ProductoxmovimientoId;
import modelos.Productoxreceta;
import modelos.Receta;
import modelos.Tipoempleado;
import modelos.Turno;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Guti, James
 */
public class ControladorProduccion {

    public static controladorconfiguracion ControladorConfiguracion = new controladorconfiguracion();

    // Actividades
    public static ArrayList<Actividad> seleccionarActividades() {
        ArrayList<Actividad> actividades = new ArrayList<>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List<Actividad> temp = session.createQuery("FROM Actividad a").list();
            for (Actividad act : temp) {
                actividades.add((Actividad) act);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return actividades;
    }

    // Asignacion
    public static ArrayList<Asignacion> seleccionarAsignaciones() {
        ArrayList<Asignacion> listaAsignaciones = new ArrayList<>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query;
        try {
            tx = session.beginTransaction();
            query = session.createQuery("FROM Asignacion "
                    + "WHERE estado!=:estado "
                    + "ORDER BY fechainicial");
            query.setParameter("estado", produccion.vistas.Asignacion.ELIMINADA);
            List<Asignacion> temp = query.list();

            Date hoy = new Date();
            for (Asignacion asig : temp) {
                if (asig.getEstado() == produccion.vistas.Asignacion.PENDIENTE
                        && utils.Utils.compararFechas(asig.getFechainicial(), hoy) <= 0) {
                    asig.setEstado(produccion.vistas.Asignacion.ACTIVA);
                    session.save(asig);
                }
                listaAsignaciones.add((Asignacion) asig);
            }
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return listaAsignaciones;
    }

    public static ArrayList<Asignacion> buscarAsignacionesPorFechas(Date fechaInicial, Date fechaFinal) {
        ArrayList<Asignacion> listaAsignaciones = new ArrayList<>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Query query;
        try {
            session.beginTransaction();
            query = session.createQuery("FROM Asignacion "
                    + "WHERE estado!=:estado "
                    + "AND fechainicial<=:fechaFinal "
                    + "AND fechafinal>=:fechaInicial ");
            query.setParameter("estado", produccion.vistas.Asignacion.ELIMINADA);
            query.setParameter("fechaFinal", fechaFinal);
            query.setParameter("fechaInicial", fechaInicial);
            List<Asignacion> temp = query.list();

            for (Asignacion asig : temp) {
                listaAsignaciones.add((Asignacion) asig);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return listaAsignaciones;
    }

    public static Asignacion buscarAsignacionPorFecha(Date fecha) {
        Asignacion asignacion = null;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Query query;
        try {
            session.beginTransaction();
            query = session.createQuery("FROM Asignacion "
                    + "WHERE estado!=:estado "
                    + "AND fechainicial<=:fecha "
                    + "AND fechafinal>=:fecha ");
            query.setParameter("estado", produccion.vistas.Asignacion.ELIMINADA);
            query.setParameter("fecha", fecha);
            asignacion = (Asignacion) query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return asignacion;
    }
    
    public static Produccion buscarProduccionPorFecha(Date fecha) {
        Produccion prod = null;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Query query;
        try {
            session.beginTransaction();
            query = session.createQuery("FROM Produccion "
                    + "WHERE estado!=:estado "
                    + "AND fecha=:fecha "
                    );
            query.setParameter("estado", produccion.vistas.Asignacion.ELIMINADA);
            query.setParameter("fecha", fecha);
            prod= (Produccion) query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return prod;
    }

    public static ArrayList<Asignacionxempleado> buscarAsignacionxempleado(int idasignacion) {
        ArrayList<Asignacionxempleado> listaDetalle = new ArrayList<>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Query query;
        try {
            session.beginTransaction();
            String sqlquery = "FROM Asignacionxempleado d "
                    + "join d.turno join d.empleado join d.producto join d.maquina m join m.actividad "
                    + "WHERE idasignacion = :idAsignacion";
            query = session.createQuery(sqlquery);
            query.setParameter("idAsignacion", idasignacion);
            List<Object[]> temp = query.list();
            for (Object[] obj : temp) {
                listaDetalle.add((Asignacionxempleado) obj[0]);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return listaDetalle;
    }

    public static ArrayList<Asignacionxproducto> buscarAsignacionxproducto(int idasignacion) {
        ArrayList<Asignacionxproducto> lista = new ArrayList<>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Query query;
        try {
            session.beginTransaction();
            String sqlquery = "FROM Asignacionxproducto a "
                    + "join a.producto "
                    + "WHERE idasignacion = :idAsignacion";
            query = session.createQuery(sqlquery);
            query.setParameter("idAsignacion", idasignacion);
            List<Object[]> temp = query.list();
            for (Object[] obj : temp) {
                lista.add((Asignacionxproducto) obj[0]);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return lista;
    }

    public static void guardarAsignacion(Asignacion asignacion,
            ArrayList<Asignacionxempleado> listaAsignacionxempleado,
            ArrayList<Asignacionxproducto> listaAsignacionxproducto) {

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        String sqlquery;
        Query query;
        try {
            tx = session.beginTransaction();
            Ordenproduccion orden = asignacion.getOrdenproduccion();
            session.refresh(orden);
            if (orden.getEstado() == 1) {
                orden.setEstado(2);
                session.save(orden);
            }
            session.save(asignacion);

            for (int i = 0; i < listaAsignacionxempleado.size(); i++) {
                Asignacionxempleado detalle = listaAsignacionxempleado.get(i);
                detalle.getId().setIdasignacion(asignacion.getIdasignacion());
                detalle.setAsignacion(asignacion);
                Maquina maquina = detalle.getMaquina();
                session.refresh(maquina);
                session.save(detalle);
            }

            int numDias = 0;
            Calendar cal = Calendar.getInstance();
            cal.setTime(asignacion.getFechainicial());
            while (utils.Utils.compararFechas(cal.getTime(), asignacion.getFechafinal()) <= 0) {
                numDias++;
                cal.add(Calendar.DAY_OF_MONTH, 1);
            }


            Detalleordenproduccion detalleOrden;
            for (int i = 0; i < listaAsignacionxproducto.size(); i++) {
                Asignacionxproducto asignacionxproducto = listaAsignacionxproducto.get(i);
                asignacionxproducto.getId().setIdasignacion(asignacion.getIdasignacion());
                asignacionxproducto.setAsignacion(asignacion);
                session.save(asignacionxproducto);

                sqlquery = "FROM Detalleordenproduccion "
                        + "WHERE idproducto=:idproducto "
                        + "AND idordenproduccion=:idordenproduccion";
                query = session.createQuery(sqlquery);
                query.setParameter("idproducto", asignacionxproducto.getId().getIdproducto());
                query.setParameter("idordenproduccion", asignacion.getOrdenproduccion().getIdordenproduccion());

                detalleOrden = (Detalleordenproduccion) query.uniqueResult();
                if (detalleOrden != null) {
                    float cantidadEstimada = detalleOrden.getCantidadestimada();
                    cantidadEstimada += asignacionxproducto.getProduccionestimada() * numDias;
                    detalleOrden.setCantidadestimada(cantidadEstimada);
                    session.update(detalleOrden);
                }
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static String eliminarAsignacion(Asignacion asignacion) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query;
        String resultado = null;
        try {
            tx = session.beginTransaction();

            String sqlquery = "UPDATE Asignacion "
                    + "SET estado=:estadoEliminada "
                    + "WHERE idasignacion = :idasignacion "
                    + "AND estado!=:estadoCompletada "
                    + "AND estado!=:estadoEnUso";
            query = session.createQuery(sqlquery);
            query.setParameter("idasignacion", asignacion.getIdasignacion());
            query.setParameter("estadoEliminada", produccion.vistas.Asignacion.ELIMINADA);
            query.setParameter("estadoCompletada", produccion.vistas.Asignacion.COMPLETADA);
            query.setParameter("estadoEnUso", produccion.vistas.Asignacion.EN_USO);

            int res = query.executeUpdate();
            if (res == 0) {
                resultado = "No se puede eliminar una asignacion en estado COMPLETA o EN USO";
            } else {
                Ordenproduccion orden = asignacion.getOrdenproduccion();
                session.refresh(orden);
                if (orden.getEstado() == 2) {
                    sqlquery = "FROM Asignacion "
                            + "WHERE idordenproduccion = :idordenproduccion "
                            + "AND estado != :estadoEliminada";
                    query = session.createQuery(sqlquery);
                    query.setParameter("idordenproduccion", orden.getIdordenproduccion());
                    query.setParameter("estadoEliminada", produccion.vistas.Asignacion.ELIMINADA);

                    if (query.list().isEmpty()) {
                        orden.setEstado(1);
                    }
                    session.update(orden);
                }

                int numDias = 0;
                Calendar cal = Calendar.getInstance();
                cal.setTime(asignacion.getFechainicial());
                while (utils.Utils.compararFechas(cal.getTime(), asignacion.getFechafinal()) <= 0) {
                    numDias++;
                    cal.add(Calendar.DAY_OF_MONTH, 1);
                }

                ArrayList<Detalleordenproduccion> listaDetalleorden = new ArrayList<>();;
                sqlquery = "FROM Detalleordenproduccion d join d.producto WHERE "
                        + "idordenproduccion = :idOrdenProduccion";
                query = session.createQuery(sqlquery);
                query.setParameter("idOrdenProduccion", orden.getIdordenproduccion());
                List<Object[]> tempDetalles = query.list();
                for (Object[] obj : tempDetalles) {
                    listaDetalleorden.add((Detalleordenproduccion) obj[0]);
                }



                for (int i = 0; i < listaDetalleorden.size(); i++) {

                    Detalleordenproduccion detalle = listaDetalleorden.get(i);
                    sqlquery = "FROM Asignacionxproducto "
                            + "WHERE idproducto=:idproducto "
                            + "AND idasignacion=:idasignacion";
                    query = session.createQuery(sqlquery);
                    query.setParameter("idproducto", detalle.getId().getIdproducto());
                    query.setParameter("idasignacion", asignacion.getIdasignacion());

                    Asignacionxproducto asignacionxproducto = (Asignacionxproducto) query.uniqueResult();
                    if (asignacionxproducto != null) {
                        float cantidadEstimada = detalle.getCantidadestimada();
                        cantidadEstimada -= asignacionxproducto.getProduccionestimada() * numDias;
                        detalle.setCantidadestimada(cantidadEstimada);
                        session.update(detalle);
                    }
                }

                tx.commit();
            }
        } catch (Exception e) {
            resultado = "No se pudo conectar con la Base de Datos";
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return resultado;
    }

    // Desempeno
    public static Desempeno buscarDesempeno(int idempleado, int idactividad, int idturno) {
        Desempeno desempeno = null;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Query query;
        try {
            session.beginTransaction();
            String sqlquery = "FROM Desempeno "
                    + "WHERE idempleado = :idempleado "
                    + "AND idactividad = :idactividad "
                    + "AND idturno = :idturno";
            query = session.createQuery(sqlquery);
            query.setParameter("idempleado", idempleado);
            query.setParameter("idactividad", idactividad);
            query.setParameter("idturno", idturno);
            desempeno = (Desempeno) query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return desempeno;
    }

    // Empleado
    public List<Empleado> seleccionarEmpleados() {
        List<Empleado> empleados = new ArrayList<>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Query query;
        try {
            session.beginTransaction();
            query = session.createQuery("FROM Empleado "
                    + "WHERE idtipoempleado=5 "
                    + "AND estado = 1");
            List<Empleado> empleados2 = query.list();
            for (Empleado e : empleados2) {
                if (e.getEstado() == 1) {
                    empleados.add(e);
                }
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return empleados;
    }

    public static int seleccionarMaximoIdempleado() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Query query;
        int id = 0;
        try {
            session.beginTransaction();
            String sqlquery = "SELECT max(idempleado) FROM Empleado";
            query = session.createQuery(sqlquery);
            id = (int) query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return id;
    }

    public static void generarEmpleados(int numEmpleados) {

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            Random ran = new Random();
            int num = ControladorProduccion.seleccionarMaximoIdempleado();
            ArrayList<Actividad> listaActividades = seleccionarActividades();
            ArrayList<Turno> listaTurnos = seleccionarTurnos();
            Tipoempleado tipoEmpleado = seleccionarTipoEmpleado();

            for (int i = 0; i < numEmpleados; i++) {
                Empleado emp = new Empleado();

                num++;
                emp.setNombre("Empleado " + num);
                emp.setAppaterno("Empleado " + num);
                emp.setApmaterno("Empleado " + num);
                emp.setFechanacimiento(new Date());
                emp.setFechacontrato(new Date());
                emp.setGenero(0);
                emp.setDni(12345678 + num);
                emp.setSueldo(new Float(1000.0));
                emp.setTipoempleado(tipoEmpleado);
                emp.getTipoempleado().setIdtipoempleado(5);
                emp.setEstado(1);

                session.save(emp);

                for (int j = 0; j < listaActividades.size(); j++) {
                    for (int k = 0; k < listaTurnos.size(); k++) {
                        Desempeno des = new Desempeno();

                        DesempenoId id = new DesempenoId();
                        id.setIdempleado(emp.getIdempleado());
                        id.setIdactividad(listaActividades.get(j).getIdactividad());
                        id.setIdturno(listaTurnos.get(k).getIdturno());

                        des.setId(id);
                        des.setActividad(listaActividades.get(j));
                        des.setTurno(listaTurnos.get(k));
                        des.setEmpleado(emp);
                        des.setFecha(new Date());
                        des.setProduccion(new Float(ran.nextInt(500) + 500));
                        des.setMerma(new Float(ran.nextInt(15) + 5));
                        des.setNumdatos(1);

                        session.save(des);
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
    }

    private static Tipoempleado seleccionarTipoEmpleado() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Query query;
        Tipoempleado tipoEmpleado = null;
        try {
            session.beginTransaction();
            String sqlquery = "FROM Tipoempleado";
            query = session.createQuery(sqlquery);
            tipoEmpleado = (Tipoempleado) query.setMaxResults(1).uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return tipoEmpleado;
    }

    // Maquina
    public static ArrayList<Maquina> buscarMaquinasPorIdactividad(int idActividad) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Query query;
        ArrayList<Maquina> maquinas = new ArrayList<>();

        try {
            session.beginTransaction();
            String sqlquery = "FROM Maquina m join m.actividad a "
                    + "WHERE a.idactividad = :idActividad "
                    + "AND m.estado=1";
            query = session.createQuery(sqlquery);
            query.setParameter("idActividad", idActividad);

            List<Object[]> tempMaquinas = query.list();
            for (Object[] obj : tempMaquinas) {
                maquinas.add((Maquina) obj[0]);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return maquinas;
    }

    // Orden de Produccion
    public static Ordenproduccion buscarOrdenProduccionPorFecha(Date fecha) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Query query;
        Ordenproduccion orden = null;

        try {
            session.beginTransaction();
            String sqlquery = "FROM Ordenproduccion WHERE fechainicio <= :fecha "
                    + "AND fechafin >= :fecha "
                    + "AND (estado = 1 "
                    + "OR estado = 2)";
            query = session.createQuery(sqlquery);
            query.setParameter("fecha", fecha);
            orden = (Ordenproduccion) query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return orden;
    }

    public static ArrayList<Detalleordenproduccion> buscarDetalleOrdenProduccion(int idOrdenProduccion) {
        ArrayList<Detalleordenproduccion> detalles = null;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Query query;
        try {
            session.beginTransaction();
            String sqlquery = "FROM Detalleordenproduccion d join d.producto WHERE "
                    + "idordenproduccion = :idOrdenProduccion";
            query = session.createQuery(sqlquery);
            query.setParameter("idOrdenProduccion", idOrdenProduccion);
            List<Object[]> tempDetalles = query.list();
            if (tempDetalles.isEmpty() == false) {
                detalles = new ArrayList<>();
                for (Object[] obj : tempDetalles) {
                    detalles.add((Detalleordenproduccion) obj[0]);
                }
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return detalles;
    }

    // Parametros Algoritmo
    public static Parametrosalgoritmo seleccionarParametroAlgoritmo() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Query query;
        Parametrosalgoritmo param = null;
        try {
            session.beginTransaction();
            String sqlquery = "FROM Parametrosalgoritmo "
                    + "WHERE idparametrosalgoritmo=1";
            query = session.createQuery(sqlquery);
            param = (Parametrosalgoritmo) query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return param;
    }

    public static String actualizarParametrosAlgoritmo(Parametrosalgoritmo parametro) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        String resultado = null;
        try {
            tx = session.beginTransaction();
            session.update(parametro);
            tx.commit();
        } catch (Exception e) {
            resultado = "No se pudo conectar con la Base de Datos";
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return resultado;
    }

    // Produccion
    public static ArrayList<Produccion> seleccionarProducciones() {
        ArrayList<Produccion> listaProducciones = new ArrayList<>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Query query;
        try {
            session.beginTransaction();
            query = session.createQuery("FROM Produccion "
                    + "WHERE estado!=:estadoEliminada "
                    + "ORDER BY fecha");
            query.setParameter("estadoEliminada", produccion.vistas.BuscarProduccion.ELIMINADA);
            List<Produccion> temp = query.list();

            for (Produccion prod : temp) {
                listaProducciones.add((Produccion) prod);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return listaProducciones;
    }

    public static String actualizarProduccion(Produccion prod, ArrayList<Produccionxempleado> listaProduccionxempleado, ArrayList<Produccionxproducto> listaProduccionxproducto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static String guardarProduccion(Produccion prod, ArrayList<Produccionxempleado> listaProduccionxempleado, ArrayList<Produccionxproducto> listaProduccionxproducto) {

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query;
        String sqlquery;
        String mensajeError = null;

        try {
            tx = session.beginTransaction();
            Asignacion asignacion = prod.getAsignacion();
            session.refresh(asignacion);
            if (utils.Utils.compararFechas(asignacion.getFechafinal(), prod.getFecha()) == 0) {
                asignacion.setEstado(produccion.vistas.Asignacion.COMPLETADA);
            } else {
                asignacion.setEstado(produccion.vistas.Asignacion.EN_USO);
            }
            session.update(asignacion);

            Ordenproduccion orden = asignacion.getOrdenproduccion();
            session.refresh(orden);
            if (orden.getEstado() == 2
                    && prod.getFecha().equals(orden.getFechafin())) {
                orden.setEstado(3);
                session.update(orden);
                controlador.ventas.ControllerOrdenes.actualizarVentas(asignacion.getOrdenproduccion());//?????
            }

            session.save(prod);

            Produccionxempleado produccionxempleado;
            Desempeno desempeno;
            for (int i = 0; i < listaProduccionxempleado.size(); i++) {
                produccionxempleado = listaProduccionxempleado.get(i);

                sqlquery = "FROM Desempeno "
                        + "WHERE idempleado=:idempleado "
                        + "AND idactividad=:idactividad "
                        + "AND idturno=:idturno";
                query = session.createQuery(sqlquery);
                query.setParameter("idempleado", produccionxempleado.getId().getIdempleado());
                query.setParameter("idactividad", produccionxempleado.getAsignacionxempleado().getMaquina().getActividad().getIdactividad());
                query.setParameter("idturno", produccionxempleado.getAsignacionxempleado().getTurno().getIdturno());
                desempeno = (Desempeno) query.uniqueResult();

                float produccion, merma;
                int numDatos;
                numDatos = desempeno.getNumdatos();
                produccion = desempeno.getProduccion() * numDatos + produccionxempleado.getProduccion_1();
                merma = desempeno.getMerma() * numDatos + produccionxempleado.getMerma();
                numDatos++;
                produccion /= numDatos;
                merma /= numDatos;

                produccionxempleado.setProduccion(prod);
                produccionxempleado.getId().setIdproduccion(prod.getIdproduccion());
                session.save(produccionxempleado);

                desempeno.setProduccion(produccion);
                desempeno.setMerma(merma);
                desempeno.setNumdatos(numDatos);
                session.update(desempeno);

            }

            Produccionxproducto produccionxproducto;
            Detalleordenproduccion detalleOrden;
            float cantidadProducida;
            for (int i = 0; i < listaProduccionxproducto.size(); i++) {

                produccionxproducto = listaProduccionxproducto.get(i);

                sqlquery = "FROM Detalleordenproduccion "
                        + "WHERE idproducto=:idproducto "
                        + "AND idordenproduccion=:idordenproduccion";
                query = session.createQuery(sqlquery);
                query.setParameter("idproducto", produccionxproducto.getId().getIdproducto());
                query.setParameter("idordenproduccion", asignacion.getOrdenproduccion().getIdordenproduccion());

                detalleOrden = (Detalleordenproduccion) query.uniqueResult();
                if (detalleOrden != null) {
                    cantidadProducida = detalleOrden.getCantidadproducida();
                    cantidadProducida += produccionxproducto.getProduccion_1();
                    detalleOrden.setCantidadproducida(cantidadProducida);
                    session.update(detalleOrden);
                }
                produccionxproducto.getId().setIdproduccion(prod.getIdproduccion());
                produccionxproducto.setProduccion(prod);
                session.save(produccionxproducto);
            }

            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.MONTH, 1);

            ArrayList<Productoxmovimiento> listaProductoxmovimiento = new ArrayList<>();
            Producto producto;
            Productoxmovimiento prodxmov;

            for (int i = 0; i < listaProduccionxproducto.size(); i++) {
                produccionxproducto = listaProduccionxproducto.get(i);
                prodxmov = new Productoxmovimiento();
                ProductoxmovimientoId id = new ProductoxmovimientoId();
                id.setIdproducto(produccionxproducto.getId().getIdproducto());
                prodxmov.setId(id);
                prodxmov.setFvencimiento(cal.getTime());
                producto = produccionxproducto.getAsignacionxproducto().getProducto();
                session.refresh(producto);
                prodxmov.setProducto(producto);
                prodxmov.setStock(produccionxproducto.getProduccion_1());
                prodxmov.setStockactual(prodxmov.getStock());
                listaProductoxmovimiento.add(prodxmov);
            }
            Productoxmovimiento prodxmovtemp;
            for (int i = 0; i < listaProduccionxproducto.size(); i++) {
                prodxmov = listaProductoxmovimiento.get(i);
                if (prodxmov.getProducto().getTipoproducto().getIdtipoproducto() == 3
                        || prodxmov.getProducto().getTipoproducto().getIdtipoproducto() == 4) {
                    sqlquery = "FROM Receta r, Productoxreceta d, Producto p "
                            + "WHERE r.producto.idproducto=:idproducto "
                            + "AND r.idreceta=d.id.idreceta "
                            + "AND d.id.idproducto=p.idproducto";
                    query = session.createQuery(sqlquery);
                    query.setParameter("idproducto", prodxmov.getId().getIdproducto());
                    List<Object[]> temp = query.list();
                    Productoxreceta prodxreceta;
                    for (int j = 0; j < temp.size(); j++) {
                        prodxreceta = (Productoxreceta) temp.get(j)[1];
                        int indice = 0;
                        while (indice < listaProductoxmovimiento.size()
                                && listaProductoxmovimiento.get(indice).getProducto().getIdproducto()
                                != prodxreceta.getId().getIdproducto()) {
                            indice++;
                        }
                        if (indice < listaProductoxmovimiento.size()) {
                            prodxmovtemp = listaProductoxmovimiento.get(indice);
                            prodxmovtemp.setStock(prodxmovtemp.getStock() - prodxreceta.getCantidad()
                                    * listaProduccionxproducto.get(i).getProduccion_1());
                        } else {
                            producto = prodxreceta.getProducto();
                            session.refresh(producto);
                            prodxmovtemp = new Productoxmovimiento();
                            ProductoxmovimientoId id = new ProductoxmovimientoId();
                            id.setIdproducto(producto.getIdproducto());
                            prodxmovtemp.setId(id);
                            prodxmovtemp.setFvencimiento(cal.getTime());
                            prodxmovtemp.setProducto(producto);
                            prodxmovtemp.setStock(-prodxreceta.getCantidad()
                                    * listaProduccionxproducto.get(i).getProduccion_1());
                            prodxmovtemp.setStockactual(prodxmovtemp.getStock());
                            listaProductoxmovimiento.add(prodxmovtemp);
                        }
                    }
                }
            }

            ArrayList<Productoxmovimiento> movimientosEntrada = new ArrayList<>();
            ArrayList<Productoxmovimiento> movimientosSalidaProduccion = new ArrayList<>();
            ArrayList<Productoxmovimiento> movimientosSalidaPrincipal = new ArrayList<>();
            for (int i = 0; i < listaProductoxmovimiento.size(); i++) {
                prodxmov = listaProductoxmovimiento.get(i);
                float stock = prodxmov.getProducto().getStock();
                stock += prodxmov.getStock();
                if (stock < 0) {
                    if (mensajeError == null) {
                        mensajeError = "No hay suficiente stock de los siguientes productos:\n";
                    }
                    stock *= -1;
                    mensajeError += "\t\t-" + prodxmov.getProducto().getNombre() + " (" + stock + ")\n";
                } else {
                    prodxmov.getProducto().setStock(stock);
                    if (prodxmov.getStock() > 0) {
                        prodxmov.getProducto().setStocksolicitado(prodxmov.getProducto().getStocksolicitado()-prodxmov.getStock());
                        if(prodxmov.getProducto().getStocksolicitado()<0){
                            prodxmov.getProducto().setStocksolicitado(0); 
                       }
                        prodxmov.setStockactual(prodxmov.getStock());
                        movimientosEntrada.add(prodxmov);
                    } else if (prodxmov.getStock() < 0) {
                        prodxmov.setStock(-prodxmov.getStock());
                        prodxmov.setStockactual(prodxmov.getStock());
                        if (prodxmov.getProducto().getTipoproducto().getIdtipoproducto() == 3
                                || prodxmov.getProducto().getTipoproducto().getIdtipoproducto() == 4) {
                            movimientosSalidaProduccion.add(prodxmov);
                        } else {
                            movimientosSalidaPrincipal.add(prodxmov);
                        }
                    }
                }
            }


            if (mensajeError == null) {

                //SI hay entrada:
                if (movimientosEntrada.size() > 0) {
                    // Guardar Movimiento de Entrada
                    Movimiento movimiento = new Movimiento();
                    movimiento.setProduccion(prod);
                    movimiento.setFecha(new Date());
                    movimiento.setEstado(1);
                    movimiento.setTipo(1);  // Movimiento de entrada                
                    Almacen almacen = new Almacen();
                    almacen.setIdalmacen(2); // Almacen de produccion
                    session.refresh(almacen);
                    movimiento.setAlmacen(almacen);
                    Motivo motivo = new Motivo();
                    motivo.setIdmotivo(5);
                    session.refresh(motivo);
                    movimiento.setMotivo(motivo);

                    session.save(movimiento);

                    // Guardar Detalle
                    for (int i = 0; i < movimientosEntrada.size(); i++) {
                        movimientosEntrada.get(i).setMovimiento(movimiento);
                        movimientosEntrada.get(i).setStockactual(movimientosEntrada.get(i).getStock());
                        movimientosEntrada.get(i).getId().setIdmovimiento(movimiento.getIdmovimiento());
                        session.save(movimientosEntrada.get(i));
                    }
                }
                //Si hay salidas de intermedios
                if (movimientosSalidaProduccion.size() > 0) {
                    // Guardar Movimiento de Entrada
                    Movimiento movimiento = new Movimiento();
                    movimiento.setProduccion(prod);
                    movimiento.setFecha(new Date());
                    movimiento.setEstado(1);
                    movimiento.setTipo(2);      // Movimiento de salida                
                    Almacen almacen = new Almacen();
                    almacen.setIdalmacen(2);    // Almacen de produccion
                    session.refresh(almacen);
                    movimiento.setAlmacen(almacen);
                    Motivo motivo = new Motivo();
                    motivo.setIdmotivo(5);      //Motivo produccion
                    session.refresh(motivo);
                    movimiento.setMotivo(motivo);

                    session.save(movimiento);

                    // Guardar Detalle
                    for (int i = 0; i < movimientosSalidaProduccion.size(); i++) {
                        movimientosSalidaProduccion.get(i).setMovimiento(movimiento);
                        movimientosSalidaProduccion.get(i).getId().setIdmovimiento(movimiento.getIdmovimiento());
                        session.save(movimientosSalidaProduccion.get(i));
                    }
                }
                // Si hay salidas de insumos y materia prima
                if (movimientosSalidaPrincipal.size() > 0) {
                    // Guardar Movimiento de Entrada
                    Movimiento movimiento = new Movimiento();
                    movimiento.setProduccion(prod);
                    movimiento.setFecha(new Date());
                    movimiento.setEstado(1);
                    movimiento.setTipo(2);  // Movimiento de salida                
                    Almacen almacen = new Almacen();
                    almacen.setIdalmacen(1);    //almacen principal
                    session.refresh(almacen);
                    movimiento.setAlmacen(almacen);
                    Motivo motivo = new Motivo();
                    motivo.setIdmotivo(5);  //Motivo produccion
                    session.refresh(motivo);
                    movimiento.setMotivo(motivo);

                    session.save(movimiento);

                    // Guardar Detalle
                    for (int i = 0; i < movimientosSalidaPrincipal.size(); i++) {
                        movimientosSalidaPrincipal.get(i).setMovimiento(movimiento);
                        movimientosSalidaPrincipal.get(i).getId().setIdmovimiento(movimiento.getIdmovimiento());
                        session.save(movimientosSalidaPrincipal.get(i));
                    }
                }

                ArrayList<Productoxmovimiento> movimientosSalida = new ArrayList<>();
                movimientosSalida.addAll(movimientosSalidaPrincipal);
                movimientosSalida.addAll(movimientosSalidaProduccion);

                for (int i = 0; i < movimientosSalida.size(); i++) {
                    Productoxmovimiento movSalida = movimientosSalida.get(i);

                    sqlquery = "FROM Productoxmovimiento p join p.movimiento m "
                            + "WHERE m.idmovimiento = p.id.idmovimiento "
                            + "AND m.tipo = 1 "
                            + "AND p.id.idproducto = :idproducto "
                            + "AND p.stockactual > 0 "
                            + "ORDER BY p.fvencimiento ASC";
                    query = session.createQuery(sqlquery);
                    query.setParameter("idproducto", movSalida.getId().getIdproducto());
                    List<Object[]> temp = query.list();

                    ArrayList<Productoxmovimiento> listaEntradas = new ArrayList<>();
                    for (Object[] obj : temp) {
                        listaEntradas.add((Productoxmovimiento) obj[0]);
                    }

                    float cantidad = movSalida.getStock();
                    int indice = 0;
                    float cantidadMovimiento;
                    float valor = 0;
                    float cantidadRestada = 0;

                    while (cantidad > 0) {
                        Productoxmovimiento movEntrada = listaEntradas.get(indice);
                        cantidadMovimiento = movEntrada.getStockactual();
                        if (cantidad > cantidadMovimiento) {
                            cantidad = cantidad - cantidadMovimiento;
                            valor = 0;
                            cantidadRestada = cantidadMovimiento;
                        } else if (cantidad < cantidadMovimiento) {
                            valor = cantidadMovimiento - cantidad;
                            cantidadRestada = cantidad;
                            cantidad = 0;
                        } else if (cantidad == cantidadMovimiento) {
                            cantidadRestada = cantidad;
                            cantidad = 0;
                            valor = 0;
                        }

                        movEntrada.setStockactual(valor);
                        session.save(movEntrada);

                        // verificar que todos los detalles del movimiento tengan stockactual = 0
                        sqlquery = "FROM Productoxmovimiento p join p.movimiento m "
                                + "WHERE m.idmovimiento = p.id.idmovimiento "
                                + "AND m.idmovimiento = :idmovimiento "
                                + "AND m.tipo = 1 "
                                + "AND p.stockactual > 0 "
                                + "ORDER BY p.fvencimiento ASC";
                        query = session.createQuery(sqlquery);
                        query.setParameter("idmovimiento", movEntrada.getId().getIdmovimiento());
                        List<Object[]> temp2 = query.list();
                        if (temp2.isEmpty()) {
                            Movimiento movimiento = (Movimiento) temp2.get(0)[1];
                            movimiento.setEstado(2);
                            session.update(movimiento);
                        }

                        Movsalidadetalle movSalidaDetalle = new Movsalidadetalle();
                        MovsalidadetalleId id = new MovsalidadetalleId();
                        id.setIdmovimientoe(movEntrada.getId().getIdmovimiento());
                        id.setIdmovimientos(movSalida.getId().getIdmovimiento());
                        id.setIdproducto(movSalida.getId().getIdproducto());
                        movSalidaDetalle.setId(id);
                        movSalidaDetalle.setCantidad(cantidadRestada);
                        movSalidaDetalle.setProductoxmovimiento(movSalida);
                        movSalidaDetalle.setMovimiento(movEntrada.getMovimiento());

                        session.save(movSalidaDetalle);

                        indice++;
                    }
                }

                tx.commit();

            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return mensajeError;
    }

    public static ArrayList<Produccionxempleado> buscarProduccionxempleado(int idproduccion) {
        ArrayList<Produccionxempleado> listaDetalle = new ArrayList<>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Query query;
        try {
            session.beginTransaction();
            String sqlquery = "FROM Produccionxempleado p "
                    + "JOIN p.asignacionxempleado a "
                    + "JOIN a.turno "
                    + "JOIN a.empleado "
                    + "JOIN a.producto "
                    + "JOIN a.maquina m "
                    + "JOIN m.actividad "
                    + "WHERE p.id.idproduccion = :idproduccion";
            query = session.createQuery(sqlquery);
            query.setParameter("idproduccion", idproduccion);
            List<Object[]> temp = query.list();
            for (Object[] obj : temp) {
                listaDetalle.add((Produccionxempleado) obj[0]);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return listaDetalle;
    }

    public static ArrayList<Produccionxproducto> buscarProduccionxproducto(int idproduccion) {
        ArrayList<Produccionxproducto> listaDetalle = new ArrayList<>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Query query;
        try {
            session.beginTransaction();
            String sqlquery = "FROM Produccionxproducto p "
                    + "JOIN p.asignacionxproducto a "
                    + "JOIN a.producto "
                    + "WHERE p.id.idproduccion = :idproduccion";
            query = session.createQuery(sqlquery);
            query.setParameter("idproduccion", idproduccion);
            List<Object[]> temp = query.list();
            for (Object[] obj : temp) {
                listaDetalle.add((Produccionxproducto) obj[0]);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return listaDetalle;
    }

    public static String eliminarProduccion(Produccion prod) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query;
        String sqlquery;
        String mensajeError = null;

        try {
            tx = session.beginTransaction();
            Asignacion asignacion = prod.getAsignacion();
            session.refresh(asignacion);
            if (utils.Utils.compararFechas(asignacion.getFechafinal(), prod.getFecha()) == 0) {
                asignacion.setEstado(produccion.vistas.Asignacion.EN_USO);
                session.update(asignacion);
            }
            //buscar lista de produccion


            Ordenproduccion orden = asignacion.getOrdenproduccion();
            session.refresh(orden);
            if (orden.getEstado() == 2
                    && prod.getFecha().equals(orden.getFechafin())) {
                orden.setEstado(3);
                session.update(orden);
                //controlador.ventas.ControllerOrdenes.actualizarVentas(asignacion.getOrdenproduccion());//?????
            }

            prod.setEstado(produccion.vistas.BuscarProduccion.ELIMINADA);
            session.update(prod);

            ArrayList<Produccionxempleado> listaProduccionxempleado = new ArrayList<>();
            sqlquery = "FROM Produccionxempleado p "
                    + "JOIN p.asignacionxempleado a "
                    + "JOIN a.turno "
                    + "JOIN a.empleado "
                    + "JOIN a.producto "
                    + "JOIN a.maquina m "
                    + "JOIN m.actividad "
                    + "WHERE p.id.idproduccion = :idproduccion";
            query = session.createQuery(sqlquery);
            query.setParameter("idproduccion", prod.getIdproduccion());
            List<Object[]> temp = query.list();
            for (Object[] obj : temp) {
                listaProduccionxempleado.add((Produccionxempleado) obj[0]);
            }

            Produccionxempleado produccionxempleado;
            Desempeno desempeno;
            for (int i = 0; i < listaProduccionxempleado.size(); i++) {
                produccionxempleado = listaProduccionxempleado.get(i);

                sqlquery = "FROM Desempeno "
                        + "WHERE idempleado=:idempleado "
                        + "AND idactividad=:idactividad "
                        + "AND idturno=:idturno";
                query = session.createQuery(sqlquery);
                query.setParameter("idempleado", produccionxempleado.getId().getIdempleado());
                query.setParameter("idactividad", produccionxempleado.getAsignacionxempleado().getMaquina().getActividad().getIdactividad());
                query.setParameter("idturno", produccionxempleado.getAsignacionxempleado().getTurno().getIdturno());
                desempeno = (Desempeno) query.uniqueResult();

                float produccion, merma;
                int numDatos;
                numDatos = desempeno.getNumdatos();
                produccion = desempeno.getProduccion() * numDatos - produccionxempleado.getProduccion_1();
                merma = desempeno.getMerma() * numDatos - produccionxempleado.getMerma();
                numDatos--;
                produccion /= numDatos;
                merma /= numDatos;

                desempeno.setProduccion(produccion);
                desempeno.setMerma(merma);
                desempeno.setNumdatos(numDatos);
                session.update(desempeno);

            }

            ArrayList<Produccionxproducto> listaProduccionxproducto = new ArrayList<>();
            sqlquery = "FROM Produccionxproducto p "
                    + "JOIN p.asignacionxproducto a "
                    + "JOIN a.producto "
                    + "WHERE p.id.idproduccion = :idproduccion";
            query = session.createQuery(sqlquery);
            query.setParameter("idproduccion", prod.getIdproduccion());
            temp = query.list();
            for (Object[] obj : temp) {
                listaProduccionxproducto.add((Produccionxproducto) obj[0]);
            }

            Produccionxproducto produccionxproducto;
            Detalleordenproduccion detalleOrden;
            float cantidadProducida;
            for (int i = 0; i < listaProduccionxproducto.size(); i++) {

                produccionxproducto = listaProduccionxproducto.get(i);

                sqlquery = "FROM Detalleordenproduccion "
                        + "WHERE idproducto=:idproducto "
                        + "AND idordenproduccion=:idordenproduccion";
                query = session.createQuery(sqlquery);
                query.setParameter("idproducto", produccionxproducto.getId().getIdproducto());
                query.setParameter("idordenproduccion", asignacion.getOrdenproduccion().getIdordenproduccion());

                detalleOrden = (Detalleordenproduccion) query.uniqueResult();
                if (detalleOrden != null) {
                    cantidadProducida = detalleOrden.getCantidadproducida();
                    cantidadProducida -= produccionxproducto.getProduccion_1();
                    detalleOrden.setCantidadproducida(cantidadProducida);
                    session.update(detalleOrden);
                }
            }

            ArrayList<Movimiento> listaMovimientos = new ArrayList<>();
            sqlquery = "FROM Movimiento m "
                    + "WHERE m.produccion.idproduccion = :idproduccion";
            query = session.createQuery(sqlquery);
            query.setParameter("idproduccion", prod.getIdproduccion());
            temp = query.list();
            for (Object obj : temp) {
                listaMovimientos.add((Movimiento) obj);
            }

            for (int i = 0; i < listaMovimientos.size(); i++) {
                Movimiento movimiento = listaMovimientos.get(i);
                movimiento.setEstado(3);
                session.update(movimiento);

                ArrayList<Productoxmovimiento> listaProductoxmovimiento = new ArrayList<>();
                sqlquery = "FROM Productoxmovimiento p join p.producto "
                        + "WHERE p.id.idmovimiento = :idmovimiento";
                query = session.createQuery(sqlquery);
                query.setParameter("idmovimiento", movimiento.getIdmovimiento());
                temp = query.list();
                for (Object[] obj : temp) {
                    listaProductoxmovimiento.add((Productoxmovimiento) obj[0]);
                }

                for (int j = 0; j < listaProductoxmovimiento.size(); j++) {
                    Productoxmovimiento productoxmovimiento = listaProductoxmovimiento.get(j);
                    Producto producto = productoxmovimiento.getProducto();

                    float stock = producto.getStock();
                    // Si es entrada
                    if (movimiento.getTipo() == 1) {
                        stock -= productoxmovimiento.getStock();
                    } else // Si es salida
                    if (movimiento.getTipo() == 2) {
                        stock += productoxmovimiento.getStock();

                        ArrayList<Productoxmovimiento> listaMovEntrada = new ArrayList<>();
                        ArrayList<Movsalidadetalle> listaMovsalidadetalle = new ArrayList<>();
                        sqlquery = "FROM Productoxmovimiento p, Movsalidadetalle m "
                                + "WHERE p.id.idproducto = m.id.idproducto "
                                + "AND p.id.idmovimiento = m.id.idmovimientoe "
                                + "AND m.id.idproducto = :idproducto "
                                + "AND m.id.idmovimientos = :idmovimientos";
                        query = session.createQuery(sqlquery);
                        query.setParameter("idproducto", producto.getIdproducto());
                        query.setParameter("idmovimientos", movimiento.getIdmovimiento());
                        temp = query.list();
                        for (Object[] obj : temp) {
                            listaMovEntrada.add((Productoxmovimiento) obj[0]);
                            listaMovsalidadetalle.add((Movsalidadetalle) obj[1]);
                        }

                        for (int k = 0; k < listaMovEntrada.size(); k++) {
                            Productoxmovimiento movEntrada = listaMovEntrada.get(k);
                            float stockactual = movEntrada.getStockactual();
                            stockactual += listaMovsalidadetalle.get(k).getCantidad();
                            movEntrada.setStockactual(stockactual);
                            session.update(movEntrada);
                        }

                    }
                    producto.setStock(stock);
                    session.update(producto);
                }

            }
            
            tx.commit();

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return mensajeError;
    }

    // Productos    
    public List<Producto> seleccionarProductosTerminados() {
        List<Producto> productos = new ArrayList<>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List<Producto> productos2 = session.createQuery("FROM Producto").list();
            for (Producto p : productos2) {
                if (p.getEstado() == 1 && p.getTipoproducto().getIdtipoproducto() == 4) {
                    productos.add(p);
                }
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return productos;
    }

    public static ArrayList<Producto> seleccionarProductosConAlmacen() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Query query;
        ArrayList<Producto> listaProductos = new ArrayList<>();

        try {
            session.beginTransaction();
            String sqlquery = "FROM Producto p join p.almacen";
            query = session.createQuery(sqlquery);
            //query.setParameter("codigo", id);
            List<Object[]> temp = query.list();
            for (Object[] obj : temp) {
                listaProductos.add((Producto) obj[0]);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return listaProductos;
    }

    // Receta
    public static List<Productoxreceta> buscarProductoxReceta(int idreceta) {
        List<Productoxreceta> lista = new ArrayList<>();
        //List<Productoxreceta> pxr = new ArrayList<Productoxreceta>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Query query;
        try {
            session.beginTransaction();
            //List<Producto> productos2 = session.createQuery("FROM Producto").list();
            String sqlquery = "FROM Productoxreceta p join p.producto WHERE idreceta = :idreceta";
            query = session.createQuery(sqlquery);
            query.setParameter("idreceta", idreceta);
            List<Object[]> temp = query.list();
            for (Object[] obj : temp) {
                lista.add((Productoxreceta) obj[0]);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return lista;
    }

    public static Receta buscarRecetaPorIdproducto(int idproducto) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Query query;
        Receta receta = null;
        try {
            session.beginTransaction();
            String sqlquery = "FROM Receta r join r.producto p "
                    + "WHERE r.producto.idproducto = :idproducto";
            query = session.createQuery(sqlquery);
            query.setParameter("idproducto", idproducto);
            Object[] obj = (Object[]) query.uniqueResult();
            if (obj != null) {
                receta = (Receta) obj[0];
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return receta;
    }

    public static ArrayList<Productoxreceta> buscarProductoxRecetaPorIdproducto(int idproducto) {
        ArrayList<Productoxreceta> lista = new ArrayList<>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Query query;
        try {
            session.beginTransaction();
            String sqlquery = "FROM Productoxreceta pxr join pxr.producto prod join pxr.receta rec "
                    + "WHERE rec.producto.idproducto = :idproducto";
            query = session.createQuery(sqlquery);
            query.setParameter("idproducto", idproducto);
            List<Object[]> temp = query.list();
            for (Object[] obj : temp) {
                lista.add((Productoxreceta) obj[0]);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return lista;
    }

    // Turnos
    public static ArrayList<Turno> seleccionarTurnos() {
        ArrayList<Turno> turnos = new ArrayList<>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List<Turno> tempTurnos = session.createQuery("FROM Turno").list();
            for (Turno t : tempTurnos) {
                turnos.add(t);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return turnos;
    }

    // Otros
    public static void refrescarObjeto(Object objeto) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.refresh(objeto);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
