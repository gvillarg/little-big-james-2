/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Seguridad.Controlador;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Date;
import java.util.Calendar;
import modelos.NewHibernateUtil;
import modelos.Rol;
import modelos.Usuario;
import modelos.Empleado;
import modelos.Log;
import modelos.Vista;
import modelos.Vistaxrol;
import modelos.VistaxrolId;
import modelos.Tipoproducto;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;

/**
 *
 * @author Saori
 */
public class controladorseguridad {

    //buscar roles
    public List<Rol> buscarroles(String rolbuscar,String estado) {
        List<Rol> roles = new ArrayList<Rol>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query=null;
        int estadobuscar=2;
        
        if(estado=="Activo") estadobuscar=1;
        else if(estado=="Inactivo") estadobuscar=0;
        
        try {          
            tx = session.beginTransaction();
            //query = session.createQuery("FROM Rol WHERE lower(nombre) LIKE lower(:rolbuscar)");
            String sqlquery="FROM Rol WHERE lower(nombre) LIKE lower(:rolbuscar)";
            if(estadobuscar!=2) sqlquery += " AND estado = :estadobuscar";
            sqlquery += " ORDER BY idrol";
            query = session.createQuery(sqlquery);
            query.setParameter("rolbuscar", "%"+rolbuscar+"%");
            if(estadobuscar!=2) query.setParameter("estadobuscar",estadobuscar);
            
            List<Rol> roles2 = query.list();
            for (Iterator iterator = roles2.iterator(); iterator.hasNext();) {
                Rol r = (Rol) iterator.next();
                roles.add(r);
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
        return roles;
    }
    
    //buscar usuarios
    public List<Usuario> buscarusuarios(String usuariobuscar, String rolbuscar, String estado) {
        List<Usuario> usuarios = new ArrayList<Usuario>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query=null;
        int estadobuscar=2;
        Rol role=null;
        
//        if(!rolbuscar.equals("") || !rolbuscar.equals("Seleccionar")){role=getRolByNombre(rolbuscar);}
        
        if(estado=="Activo") estadobuscar=1;
        else if(estado=="Inactivo") estadobuscar=0;
        
        try {
            if(!rolbuscar.equals("") || !rolbuscar.equals("Seleccionar")){role=getRolByNombre(rolbuscar);}
            
            tx = session.beginTransaction();
            String sqlquery="FROM Usuario WHERE lower(nombreusuario) LIKE lower(:usuariobuscar) ";
            if(role!=null) sqlquery += "AND idrol = :role ";
            if(estadobuscar!=2) sqlquery += "AND estado = :estadobuscar";
            sqlquery += " ORDER BY idempleado";
            
            query = session.createQuery(sqlquery);
            query.setParameter("usuariobuscar", "%"+usuariobuscar+"%");
            if(role!=null) query.setParameter("role",role);
            if(estadobuscar!=2) query.setParameter("estadobuscar",estadobuscar);
            
            List<Usuario> usuarios2 = query.list();
            for (Iterator iterator = usuarios2.iterator(); iterator.hasNext();) {
                Usuario u = (Usuario) iterator.next();
                usuarios.add(u);
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
        return usuarios;
    }
    
    public String sacanombreusuario(int usuario) {
        String nombre = null;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Usuario> usuario2 = session.createQuery("FROM Usuario").list();
            for (Iterator iterator = usuario2.iterator(); iterator.hasNext();) {
                Usuario u = (Usuario) iterator.next();
                if (u.getIdempleado() == usuario) {
                    nombre = u.getNombreusuario();
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
    
    public String sacanombrerol(int rol) {
        String nombre = null;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Rol> rol2 = session.createQuery("FROM Rol").list();
            for (Iterator iterator = rol2.iterator(); iterator.hasNext();) {
                Rol u = (Rol) iterator.next();
                if (u.getIdrol() == rol) {
                    nombre = u.getNombre();
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
    
    //buscar vista
    public Vista getVista(int vistaid) {
        Vista vista=null;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query=null;
        
        try {          
            tx = session.beginTransaction();
            query = session.createQuery("FROM Vista WHERE idvista = :vistaid");
            query.setParameter("vistaid", vistaid);
            
            vista=(Vista)query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return vista;
    }
    
    
    //buscar empleados2 (PARA LA CREACIÓN DE LOS USUARIOS)
    public List<Empleado> buscarempleados2(String nomb,String apePat,String apeMat) {
        List<Empleado> empleados = new ArrayList<Empleado>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query=null;
        
        try {
           tx = session.beginTransaction();
            //query = session.createQuery("FROM Rol WHERE lower(nombre) LIKE lower(:rolbuscar)");
            String sqlquery="FROM Empleado WHERE lower(nombre) LIKE lower(:nomb) AND lower(appaterno) LIKE lower(:apePat) AND lower(apmaterno) LIKE lower(:apeMat)";
            query = session.createQuery(sqlquery);
            query.setParameter("nomb", "%"+nomb+"%");
            query.setParameter("apePat", "%"+apePat+"%");
            query.setParameter("apeMat", "%"+apeMat+"%");
            
            List<Empleado> empleados2 = query.list();
            for (Iterator iterator = empleados2.iterator(); iterator.hasNext();) {
                Empleado p = (Empleado) iterator.next();
                empleados.add(p);
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
        return empleados;
    }
    
    //buscar empleados (PARA LA CREACIÓN DE LOS USUARIOS) --- solo empleados sin usuarios y estado activo
    public List<Empleado> buscarempleados(String nomb,String apePat,String apeMat) {
        List<Empleado> empleados = new ArrayList<Empleado>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query=null;
        Query query2=null;
        
        try {
           tx = session.beginTransaction();
            String sqlquery="FROM Empleado WHERE lower(nombre) LIKE lower(:nomb) "
                    + "AND lower(appaterno) LIKE lower(:apePat) "
                    + "AND lower(apmaterno) LIKE lower(:apeMat) "
                    + "AND estado=1";
            sqlquery += " ORDER BY idempleado";
            query = session.createQuery(sqlquery);
            query.setParameter("nomb", "%"+nomb+"%");
            query.setParameter("apePat", "%"+apePat+"%");
            query.setParameter("apeMat", "%"+apeMat+"%");
            
            String sqlquery2="FROM Usuario";
            query2 = session.createQuery(sqlquery2);
            
            List<Empleado> empleados2 = query.list();
            List<Usuario> usuarios2=query2.list();
            
            for (Iterator iterator = empleados2.iterator(); iterator.hasNext();) {
                Empleado p = (Empleado) iterator.next();
                for (Iterator iterator2 = usuarios2.iterator(); iterator2.hasNext();) {
                    Usuario u = (Usuario) iterator2.next();
                    if(p.getIdempleado()==u.getIdempleado())
                        break;
                    if(p.getIdempleado()!=u.getIdempleado() && iterator2.hasNext()==false)
                        empleados.add(p);
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
        return empleados;
    }
    
    //buscar logs
    public List<Log> buscarlogs(String nomb,Date fechainicio,Date fechafin) {
        List<Log> logs = new ArrayList<Log>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query=null;
        List<Usuario> usuarios = null;
        
        try {
            tx = session.beginTransaction();
            
            usuarios = buscarusuarios(nomb, "", "");
            
            String sqlquery="FROM Log";
            
            if(fechainicio!=null) sqlquery += " WHERE fecha >= :fechainicio";
            if(fechainicio==null && fechafin!=null) sqlquery += " WHERE fecha <= :fechafin";
            if(fechainicio!=null && fechafin!=null) sqlquery += " AND fecha <= :fechafin";
            sqlquery += " ORDER BY idlog";
            
            query = session.createQuery(sqlquery);

            if(fechainicio!=null) query.setParameter("fechainicio", fechainicio);
            if(fechafin!=null) query.setParameter("fechafin", fechafin);
            
            List<Log> logs2 = query.list();
            for (Iterator iterator = logs2.iterator(); iterator.hasNext();) {
                Log l = (Log) iterator.next();
                for (Iterator iterator2 = usuarios.iterator(); iterator2.hasNext();) {
                    Usuario u = (Usuario) iterator2.next();
                    if(l.getUsuario().getIdempleado() == u.getIdempleado()){
                        logs.add(l);
                        break;
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
        return logs;
    }
    
    //insertar roles
    public Rol insertarrol(String rolinsertar,String desc) {
//    public boolean insertarrol(String rolinsertar,String desc) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        
        Rol rol=null;
        
        try {          
            rol=new Rol();
            rol.setNombre(rolinsertar);
            rol.setDescripcion(desc);
            rol.setEstado(1);//Inicialmente, salen en estado Activo
            //rol.setVistas(null);//De prueba, x mientras!
            rol.setUsuarios(null);//ÉSTE PARÁMETRO NO VA!... NO ES ATRIBUTO DE LA TABLA!
       
            tx = session.beginTransaction();
            session.save(rol);
            session.getTransaction().commit();
//            return true;
            return rol;
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
//        return false;
        return rol;
    }
    
    //insertar vistas x roles 2
    public boolean insertarvistaxrol2(Vistaxrol vxr) {
//    public boolean insertarrol(String rolinsertar,String desc) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        
        try {  
            
            tx = session.beginTransaction();
            session.save(vxr);
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
     
    //insertar vistas x roles
   public boolean insertarvistaxrol(Rol rol, int vistaid, String descripcion) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Vistaxrol vxr=null;
        Vistaxrol vxr2=null;
        
        try {
            Vista vista=getVista(vistaid);
            
            vxr=new Vistaxrol();

            VistaxrolId id=new VistaxrolId();
            id.setIdrol(rol.getIdrol());
            id.setIdvista(vistaid);

            vxr.setRol(rol);
            vxr.setVista(vista);
            vxr.setId(id);
            vxr.setDescripcion(descripcion);

            tx = session.beginTransaction();  
            session.save(vxr);
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
    
    //eliminar todas las vistas, segun el rol
    public boolean eliminarvistaxrol(Rol rol) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        List<Vistaxrol> vxr  =new ArrayList<Vistaxrol>();
        Query query=null;
        
        try {
            query = session.createQuery("FROM Vistaxrol WHERE idrol = :rol");
            query.setParameter("rol", rol);
            tx = session.beginTransaction();
            List<Vistaxrol> vxr2 = query.list();
            for (Iterator iterator = vxr2.iterator(); iterator.hasNext();) {
                Vistaxrol v = (Vistaxrol) iterator.next();
                session.delete(v);
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
    
    //insertar usuario
    public boolean insertarusuario(String user,String pass,String correo,Rol rol,Empleado empleado) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        
        Usuario usuario=null;
        
        try {          
            usuario=new Usuario();
            usuario.setNombreusuario(user);
            usuario.setPassword(pass);
            usuario.setCorreo(correo);
            
            usuario.setEstado(1);//Inicialmente, salen en estado Activo
            usuario.setLogs(null);//De prueba, x mientras!
            usuario.setIdempleado(empleado.getIdempleado());
            usuario.setEmpleado(empleado);//
            usuario.setRol(rol);//
            
            tx = session.beginTransaction();
            session.save(usuario);
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
    
    //editar usuario
    public boolean editarUsuario(Usuario usuario, Rol rol, String correo){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query=null;
        
        try {          
            usuario.setCorreo(correo);
            usuario.setRol(rol);
            tx = session.beginTransaction();
            session.update(usuario);
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
    
    //eliminar usuario
    public void eliminarUsuario(int row, int column){
//        List<Usuario> usuarios = buscarusuarios("","", "");
//        int codigo=usuarios.get(row).getIdempleado();
        List<Usuario> usuarios = null;
        int codigo;
        
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query=null;

        try {          
            usuarios = buscarusuarios("","", "");
            codigo=usuarios.get(row).getIdempleado();
        
            tx = session.beginTransaction();
            String sqlquery="FROM Usuario WHERE idempleado = :codigo";
            query = session.createQuery(sqlquery);
            query.setParameter("codigo",codigo);
            
            Usuario usuario=(Usuario)query.uniqueResult();
            usuario.setEstado(0);//cambio de estado a inactivo
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
    
    //**************Elimina Usuario, le doy un usuario
    
        public void eliminarUsuario2(Usuario u){
//        List<Usuario> usuarios = buscarusuarios("","", "");
//        int codigo=usuarios.get(row).getIdempleado();
        List<Usuario> usuarios = null;
        int codigo;
        
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query=null;

        try {          
        
            tx = session.beginTransaction();
            String sqlquery="FROM Usuario WHERE idempleado = :codigo";
            query = session.createQuery(sqlquery);
            query.setParameter("codigo",u.getIdempleado());
            
            Usuario usuario=(Usuario)query.uniqueResult();
            usuario.setEstado(0);//cambio de estado a inactivo
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
    
    
    //editar rol
    public boolean editarRol(Rol rol, String nomb, String desc){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query=null;
        
        try {          
            rol.setNombre(nomb);
            rol.setDescripcion(desc);
       
            tx = session.beginTransaction();
            session.update(rol);
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
    
    //eliminar rol
    //retorna 1: si se pudo eliminar
    //retorna 2: si no se pudo eliminar por estar relacionado a un usuario
    //retorna 3:
    public int eliminarRol(int row, int column){
//        List<Rol> roles = buscarroles("", "");
//        int codigo=roles.get(row).getIdrol();
        List<Rol> roles = null;
        int codigo;
        
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query=null;

        try {          
            roles = buscarroles("", "");
            codigo=roles.get(row).getIdrol();
            
            Rol rolVerificar=getRolById(codigo);
            if(buscarusuarios("",rolVerificar.getNombre(), "").isEmpty()){
            
                tx = session.beginTransaction();
                //query = session.createQuery("FROM Rol WHERE lower(nombre) LIKE lower(:rolbuscar)");
                String sqlquery="FROM Rol WHERE idrol = :codigo";
                query = session.createQuery(sqlquery);
                query.setParameter("codigo",codigo);

                Rol rol=(Rol)query.uniqueResult();
                rol.setEstado(0);//cambio de estado a inactivo
                tx.commit();
                return 1;
            }
            else{
                return 0;
            }
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return 3;
//        return roles;
    }
    
    //obtener el rol con el ID respectivo
    public Rol getRolById(int codigo){
//        List<Rol> roles = buscarroles("", "");
//        int codigo=roles.get(row).getIdrol();
        Rol rol=null;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query=null;

        try {          
            tx = session.beginTransaction();
            //query = session.createQuery("FROM Rol WHERE lower(nombre) LIKE lower(:rolbuscar)");
            String sqlquery="FROM Rol WHERE idrol = :codigo";
            query = session.createQuery(sqlquery);
            query.setParameter("codigo",codigo);
            rol=(Rol)query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return rol;
    }
    
    //obtener el usuario, segun el nombre
    public Usuario getUsuarioByNombre(String nombre){
        Usuario usuario=null;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query=null;

        try {          
            tx = session.beginTransaction();
            String sqlquery="FROM Usuario WHERE lower(nombreusuario) LIKE lower(:nombre)";
            query = session.createQuery(sqlquery);
            query.setParameter("nombre",nombre);
            usuario=(Usuario)query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return usuario;
    } 
    
    //obtener el usuario con el ID respectivo
    public Usuario getUsuarioById(int codigo){
//        List<Rol> roles = buscarroles("", "");
//        int codigo=roles.get(row).getIdrol();
        Usuario usuario=null;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query=null;

        try {          
            tx = session.beginTransaction();
            //query = session.createQuery("FROM Rol WHERE lower(nombre) LIKE lower(:rolbuscar)");
            String sqlquery="FROM Usuario WHERE idempleado = :codigo";
            query = session.createQuery(sqlquery);
            query.setParameter("codigo",codigo);
            usuario=(Usuario)query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return usuario;
    } 
    
    //obtener el rol con el nombre respectivo
    public Rol getRolByNombre(String nomb){
        Rol rol=null;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query=null;

        try {          
            tx = session.beginTransaction();
            //query = session.createQuery("FROM Rol WHERE lower(nombre) LIKE lower(:rolbuscar)");
            String sqlquery="FROM Rol WHERE lower(nombre) LIKE lower(:nomb)";
            query = session.createQuery(sqlquery);
            query.setParameter("nomb",nomb);
            rol=(Rol)query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return rol;
    }
    
    //obtener el empleado con el ID respectivo
    public Empleado getEmpleadoById(int codigo){
//        List<Empleado> empleados = buscarempleados("", "", "");
//        int codigo=empleados.get(row).getIdempleado();
        Empleado empleado=null;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query=null;

        try {          
            tx = session.beginTransaction();
            //query = session.createQuery("FROM Rol WHERE lower(nombre) LIKE lower(:rolbuscar)");
            String sqlquery="FROM Empleado WHERE idempleado = :codigo";
            query = session.createQuery(sqlquery);
            query.setParameter("codigo",codigo);
            empleado=(Empleado)query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return empleado;
    }
    
    //validar usuario y contraseña
    public boolean login(String nombre, String pass){
//        List<Empleado> empleados = buscarempleados("", "", "");
//        int codigo=empleados.get(row).getIdempleado();
        Usuario usuario=null;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query=null;

        try {          
            tx = session.beginTransaction();
            //query = session.createQuery("FROM Rol WHERE lower(nombre) LIKE lower(:rolbuscar)");
            String sqlquery="FROM Usuario WHERE nombreusuario = :nombre and password = :pass";
            query = session.createQuery(sqlquery);
            query.setParameter("nombre", nombre);
            query.setParameter("pass",pass);
            usuario=(Usuario)query.uniqueResult();
            tx.commit();
            if(usuario!=null)
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
    
    //validar usuario y contraseña
    public Usuario login2(String nombre, String pass){
//        List<Empleado> empleados = buscarempleados("", "", "");
//        int codigo=empleados.get(row).getIdempleado();
        Usuario usuario=null;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query=null;

        try {          
            tx = session.beginTransaction();
            //query = session.createQuery("FROM Rol WHERE lower(nombre) LIKE lower(:rolbuscar)");
            String sqlquery="FROM Usuario WHERE nombreusuario = :nombre and password = :pass";
            query = session.createQuery(sqlquery);
            query.setParameter("nombre", nombre);
            query.setParameter("pass",pass);
            usuario=(Usuario)query.uniqueResult();
//            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
//                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return usuario;
    }
    
    public boolean editarContrasena(Usuario usuario,String passactual,String passnuevo){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query=null;
        
        try {          
            usuario.setPassword(passnuevo);
       
            tx = session.beginTransaction();
            session.update(usuario);
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
    
    public boolean validarAcceso(int rolid, int vistaid){
        Vistaxrol vxr=null;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query=null;
        
        
        try {          
            tx = session.beginTransaction();
            //query = session.createQuery("FROM Rol WHERE lower(nombre) LIKE lower(:rolbuscar)");
            String sqlquery="FROM Vistaxrol WHERE idrol = :rolid AND idvista = :vistaid";
            query = session.createQuery(sqlquery);
            query.setParameter("rolid", rolid);
            query.setParameter("vistaid",vistaid);
            vxr=(Vistaxrol)query.uniqueResult();
            tx.commit();
            if(vxr!=null)
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
    
    
    //buscar vista x rol
    public List<Vistaxrol> buscarvistaxrol(Rol rol) {
        List<Vistaxrol> vxr = new ArrayList<Vistaxrol>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query=null;
        
        try {          
            tx = session.beginTransaction();
            //query = session.createQuery("FROM Rol WHERE lower(nombre) LIKE lower(:rolbuscar)");
            String sqlquery="FROM Vistaxrol WHERE idrol = :rol";
            query = session.createQuery(sqlquery);
            query.setParameter("rol", rol);
            
            List<Vistaxrol> vxr2 = query.list();
            for (Iterator iterator = vxr2.iterator(); iterator.hasNext();) {
                Vistaxrol v = (Vistaxrol) iterator.next();
                vxr.add(v);
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
        return vxr;
    }
    
    //insertar log
    public boolean insertarlog(Usuario usuario, Date fecha, String accion, String tabla, String descripcion) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        
        Log log=null;
        
        try {         
            log=new Log();
            log.setUsuario(usuario);
            log.setFecha(fecha);
            log.setAccion(accion);
            log.setTabla(tabla);
            log.setDescripcion(descripcion);
     
            tx = session.beginTransaction();
            session.save(log);
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
    
    //generar cadena aleotoria
    public String generarNuevaContrasena(int len) {
        String ALPHA_NUM = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer sb = new StringBuffer(len);
        for (int i=0;  i<len;  i++) {
           int ndx = (int)(Math.random()*ALPHA_NUM.length());
           sb.append(ALPHA_NUM.charAt(ndx));
        }
        return sb.toString();
    }    
    //fin de cadena aleotoria
}
