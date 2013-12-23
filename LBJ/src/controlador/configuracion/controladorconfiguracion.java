/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.configuracion;

import controlador.almacen.controladoralmacen;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import modelos.Actividad;
import modelos.Cliente;
import modelos.Desempeno;
import modelos.DesempenoId;
import modelos.NewHibernateUtil;
import modelos.Empleado;
import modelos.Maquina;
import modelos.Productoxproveedor;
import modelos.Proveedor;
import modelos.Tipoempleado;
import modelos.Turno;
import modelos.Usuario;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


/**
 *
 * @author Karina
 */
public class controladorconfiguracion {
    
   //***************CONTROLADOR DE EMPLEADOS********************************
    
    public List<Empleado> sacaempleados() {
        List<Empleado> empleados = new ArrayList<Empleado>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Empleado> empleados2 = session.createQuery("FROM Empleado ORDER BY idempleado").list();
            for (Iterator iterator = empleados2.iterator(); iterator.hasNext();) {
                 Empleado e = (Empleado) iterator.next();
                if (e.getEstado()== 1) {
                    empleados.add(e);
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
    
    public List<Empleado> sacaninguno() {
        List<Empleado> empleados = new ArrayList<Empleado>();
        Empleado e = new Empleado();
        Tipoempleado tp = new Tipoempleado();
        tp.setIdtipoempleado(1);
        tp.setNombre("-");
        e.setNombre("-");
        e.setAppaterno("-");
        e.setApmaterno("-");
        //e.setArea("200");
        e.setEstado(0);
        return empleados;
    }
    
        public List<Tipoempleado> sacatipoempleado() {
        List<Tipoempleado> tipoempleados = new ArrayList<Tipoempleado>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Tipoempleado> tipoempleados2 = session.createQuery("FROM Tipoempleado ORDER BY idtipoempleado").list();
            for (Iterator iterator = tipoempleados2.iterator(); iterator.hasNext();) {
                Tipoempleado e = (Tipoempleado) iterator.next();
                tipoempleados.add(e);
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
        return tipoempleados;
    }
    
    //Saca desempenio por empleado y por turno
    
    public List<Desempeno> sacadesempeñoxemp(int idemp, int turno){
    List<Desempeno> desempeño = new ArrayList<Desempeno>();
    Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Desempeno WHERE idempleado= :idemp AND idTurno= :turno ORDER BY idempleado";
            query= session.createQuery(sqlquery);
            query.setParameter("idemp", idemp);
            query.setParameter("turno", turno);
            List<Desempeno>desempeño2= query.list();
            for (Iterator iterator = desempeño2.iterator(); iterator.hasNext();) {
                Desempeno d = (Desempeno) iterator.next();
                desempeño.add(d);
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
        return desempeño;
    }
    
    public List<Empleado> Buscaempleado(String nombre, String tipo, String apellidop, int estado){
        List<Empleado> empleados = new ArrayList<Empleado>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query =null;
        try {
            tx = session.beginTransaction();
            if (tipo.equalsIgnoreCase("Todos")){
                String sqlquery = "FROM Empleado WHERE lower(nombre) like lower(:nombre) AND lower(appaterno) like lower(:apellidop) AND estado = :estado";
                query = session.createQuery(sqlquery);
                query.setParameter("nombre", "%" + nombre + "%");
                query.setParameter("apellidop", "%" + apellidop + "%");
                query.setParameter("estado", estado);
                List<Empleado> empleados2 = query.list();
                for (Iterator iterator = empleados2.iterator(); iterator.hasNext();) {
                Empleado p = (Empleado) iterator.next();
                empleados.add(p);
            }
            //tx.commit();
            }
            else{
                List<Empleado> empleados2 = session.createQuery("FROM Empleado").list();
                for (Iterator iterator = empleados2.iterator(); iterator.hasNext();) {
                    Empleado e = (Empleado) iterator.next();
                    if (e.getTipoempleado().getNombre().equalsIgnoreCase(tipo)) {
                        String nombreactual = e.getNombre();
                        String apellidoactual = e.getAppaterno();
                        if (e.getEstado()== estado && nombreactual.toLowerCase().indexOf(nombre.toLowerCase()) != -1 && (apellidoactual.toLowerCase().indexOf(apellidop.toLowerCase())) != -1 ) {
                            empleados.add(e);
                    }
                }
            }
            }
            //tx.commit();
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
    
    //GUARDAR
    
    public void guardaempleado(String nombre, String appaterno, String apmaterno, Date fechanac, Date fechacon,int genero,int dni, float sueldo, Tipoempleado tipoe,int estado){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Empleado emp = new Empleado();
            emp.setNombre(nombre);
            emp.setAppaterno(appaterno);
            emp.setApmaterno(apmaterno);
            emp.setFechanacimiento(fechanac);
            emp.setFechacontrato(fechacon);
            emp.setGenero(genero);
            emp.setDni(dni);
            emp.setSueldo(sueldo);
            emp.setTipoempleado(tipoe);
            emp.setEstado(estado);
            session.save(emp);
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
    
    //***********
    
    //*********************DESEMPEÑO*******************************
    
    public void guardadesempeñoxempleado(DesempenoId desid, Empleado emp, Actividad act, Turno tur,  float galletasrotas, float galletasproducidas){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Desempeno actxemp = new Desempeno();
            actxemp.setId(desid);
            actxemp.setEmpleado(emp);
            actxemp.setActividad(act);
            actxemp.setTurno(tur);
            actxemp.setProduccion(galletasproducidas);
            actxemp.setMerma(galletasrotas);
            actxemp.setNumdatos(1);
            
            //Para setear fecha de hoy
            Calendar c = new GregorianCalendar();
            c.set(Calendar.HOUR_OF_DAY, 0); //anything 0 - 23
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            Date d1 = c.getTime();
            
            
            actxemp.setFecha(d1);
            session.save(actxemp);
            tx.commit();
        }catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    public Desempeno sacadesxempxact(int idemp, int idact, int idtur){	
	Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
		Desempeno desempeño = new Desempeno();
        try {
            tx = session.beginTransaction();
            String sqlquery = "FROM Desempeno WHERE idempleado= :idemp AND idactividad= :idact AND idturno= :idtur";
            query= session.createQuery(sqlquery);
            query.setParameter("idemp", idemp);
            query.setParameter("idact", idact);
            query.setParameter("idtur", idtur);
            desempeño= (Desempeno) query.uniqueResult();	
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return desempeño;
    }
    
    //************Busco Turno por ID
    
    public Turno sacaturnoxid(int id){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        Turno tur = new Turno();
        
        try {
            tx = session.beginTransaction();
            //query = session.createQuery("FROM Rol WHERE lower(nombre) LIKE lower(:rolbuscar)");
            String sqlquery = "FROM Turno WHERE idTurno = :id";
            query = session.createQuery(sqlquery);
            query.setParameter("id", id);
            tur = (Turno) query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        
    return tur;
   }
    
    public boolean editadesempeño(Empleado emp, Actividad act, Turno tur, float galletasrotas, float galletasproducidas){
        int idemp = emp.getIdempleado();
        int idact = act.getIdactividad();
        int idtur = tur.getIdturno();
        Desempeno desempeño = sacadesxempxact(idemp, idact, idtur);
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        try {
            desempeño.setProduccion(galletasproducidas);
            desempeño.setMerma(galletasrotas);
            tx = session.beginTransaction();
            session.update(desempeño);
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
    
    public Empleado sacaempleadoxid(int id){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        Empleado emp = new Empleado();
        
        try {
            tx = session.beginTransaction();
            //query = session.createQuery("FROM Rol WHERE lower(nombre) LIKE lower(:rolbuscar)");
            String sqlquery = "FROM Empleado WHERE idEmpleado = :id";
            query = session.createQuery(sqlquery);
            query.setParameter("id", id);
            emp = (Empleado) query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        
    return emp;
   }
    
    //********
    //Busca empledo por DNI
    
        public Empleado sacaempleadoxDNI(int dni){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        Empleado emp = new Empleado();
        
        try {
            tx = session.beginTransaction();
            //query = session.createQuery("FROM Rol WHERE lower(nombre) LIKE lower(:rolbuscar)");
            String sqlquery = "FROM Empleado WHERE dni = :dni";
            query = session.createQuery(sqlquery);
            query.setParameter("dni", dni);
            emp = (Empleado) query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        
    return emp;
   }
    
        //validacion por DNI
        
        public int validaempleadoxDNI(int dni, int id){
        List<Empleado> emps = sacaempleados();
        int encontro=0;
        for(int i=0; i<emps.size();i++){
                if(emps.get(i).getDni()==dni&&emps.get(i).getIdempleado()!=id){
                    encontro=1;
                    break;
                }    
                else encontro=0;
            }
        return encontro;
        }
    
    //Validacion de mayor de edad
        
        public long diasEntre(Date f1,Date f2){
        long ms = 60*60*24*1000;
        long v = (f2.getTime()-f1.getTime())/ms;
        return v;
        }

        public int CalculaEdad(Date f1, Date f2){
            int edad=0;
            int dia=f1.getDate();
            int mes=f1.getMonth();
            Date aux=new Date();
            aux.setDate(f1.getDate());
            long n = diasEntre(f1,f2);

            for(long i=1;i<=n;i++){
                aux.setDate(aux.getDate()+1);
                if(aux.getDate()==dia && aux.getMonth()==mes)
                    edad++;
            }

            return edad;
        }
        
        
        public int validaedad(Date fechanac, Date fechacontrato){
        
        int edad = CalculaEdad(fechanac, fechacontrato);
            
        if (edad >= 18) 
            return 1;
            else return 0;
        
         
        }
        
        //Validacion DNI con 8 caracteres
        
        public int validaDni8(int DNI){
           String dni1 = Integer.toString(DNI);
           int size = dni1.length();
           if (size==8) return 1;
                   else return 0;
        }

       
        
    //********
    
    public boolean editarempleado(Empleado emp, String nombre, String appaterno, String apmaterno, Date  fechanac, Date fechacon,int genero,int dni, Tipoempleado tipoe, float sueldo,int estado){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        
        try {
            emp.setNombre(nombre);
            emp.setAppaterno(appaterno);
            emp.setApmaterno(apmaterno);
            emp.setFechanacimiento(fechanac);
            emp.setFechacontrato(fechacon);
            emp.setGenero(genero);
            emp.setDni(dni);
            emp.setSueldo(sueldo);
            emp.setTipoempleado(tipoe);
            emp.setEstado(estado);
            tx = session.beginTransaction();
            session.update(emp);
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
    
    //*****
    
    public boolean eliminarempleado(Empleado emp){
    Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        boolean respuesta=false;
        try {
            emp.setEstado(0);
            tx = session.beginTransaction();
            session.update(emp);
            session.getTransaction().commit();
            respuesta= true;
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return respuesta;
    }
    
    //******************
    public Tipoempleado sacaidtipo(String nombre){
        Tipoempleado tipoemp = null;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Tipoempleado> empleados2 = session.createQuery("FROM Tipoempleado ORDER BY idtipoempleado").list();
            for (Iterator iterator = empleados2.iterator(); iterator.hasNext();) {
                Tipoempleado e = (Tipoempleado) iterator.next();
                if (e.getNombre().equalsIgnoreCase(nombre)) {
                    tipoemp = e;
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
        return tipoemp;
    }
    
    //***************CONTROLADOR DE MÁQUINAS********************************
    
    public List<Maquina> sacamaquinas() {
        List<Maquina> maquinas = new ArrayList<Maquina>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Maquina> maquinas2 = session.createQuery("FROM Maquina").list();
            for (Iterator iterator = maquinas2.iterator(); iterator.hasNext();) {
                Maquina m = (Maquina) iterator.next();
                if (m.getEstado()== 1) {
                    maquinas.add(m);
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
        return maquinas;
    }
    
    public static List<Actividad> sacaactividad() {
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
    
    public List<Maquina> Buscamaquinas(String nmaquina, String actividad){
        List<Maquina> maquinas = new ArrayList<Maquina>();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Maquina> empleados2 = session.createQuery("FROM Maquina").list();
            for (Iterator iterator = empleados2.iterator(); iterator.hasNext();) {
                Maquina m = (Maquina) iterator.next();
                if (m.getActividad().getNombre().equalsIgnoreCase(actividad)) {
                    String nmaquinaactual = m.getNombre();
                    if (nmaquinaactual.toLowerCase().indexOf(nmaquina.toLowerCase()) != -1) {
                        maquinas.add(m);
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
        
        return maquinas;
    }
    
    //*********************************
    
    public void guardamaquina(String nombre, String descripcion, int estado, Actividad act){
    Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Maquina maq = new Maquina();
            maq.setNombre(nombre);
            maq.setDescripcion(descripcion);
            maq.setEstado(estado);
            maq.setActividad(act);
            session.save(maq);
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
    
    //*******************
    
        public Maquina sacamaquinaxnombre(String nom){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        Maquina maq = new Maquina();
        
        try {
            tx = session.beginTransaction();
            //query = session.createQuery("FROM Rol WHERE lower(nombre) LIKE lower(:rolbuscar)");
            String sqlquery = "FROM Maquina WHERE nombre LIKE :nom";
            query = session.createQuery(sqlquery);
            nom = "%"+nom+"%";
            query.setParameter("nom", nom);
            maq = (Maquina) query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        
    return maq;
   }
        
   //***************************
        
       public boolean editarmaquina(Maquina maq, String nombre, String descripcion, int estado, Actividad act){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query = null;
        
        try {
            maq.setNombre(nombre);
            maq.setDescripcion(descripcion);
            maq.setEstado(estado);
            maq.setActividad(act);
            tx = session.beginTransaction();
            session.update(maq);
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
       
       //Eliminar Maquina: Le cambia el estado
       public boolean eliminarmaquina(Maquina maq){
                Session session = NewHibernateUtil.getSessionFactory().openSession();
                Transaction tx = null;
                Query query = null;
                boolean respuesta=false;
                try {
                    maq.setEstado(0);
                    tx = session.beginTransaction();
                    session.update(maq);
                    session.getTransaction().commit();
                    respuesta= true;
                } catch (HibernateException e) {
                    if (tx != null) {
                        tx.rollback();
                    }
                    e.printStackTrace();
                } finally {
                    session.close();
                }
                return respuesta;
    }
       
       
       public Actividad sacaidactividad(String nombre){
        Actividad act = null;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Actividad> actividades2 = session.createQuery("FROM Actividad").list();
            for (Iterator iterator = actividades2.iterator(); iterator.hasNext();) {
                Actividad a = (Actividad) iterator.next();
                if (a.getNombre().equalsIgnoreCase(nombre)) {
                    act = a;
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
        return act;
    }
       
       //Para eliminar el estado del usuario relacionado a empleado
       public Usuario sacaUsuarioxEmp(int idempleado){
           Session session = NewHibernateUtil.getSessionFactory().openSession();
            Usuario usuario = null;
            Transaction tx = null;
            Query query;
            try {
                tx = session.beginTransaction();
                List<Usuario> listausuarios = session.createQuery("FROM Usuario").list();
                for (Iterator iterator = listausuarios.iterator(); iterator.hasNext();) {
                Usuario u = (Usuario) iterator.next();
                if (u.getIdempleado()==idempleado) {
                     usuario= u;
                    break;
                }
            }
                
            } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }

        return usuario;
       }
       
       
       //********************************METODO DE TAVO
       
       public ArrayList<Maquina> sacamaquinaxactividad(int idActividad) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Query query;
        ArrayList<Maquina> maquinas=new ArrayList<>();
        
        try {
            tx = session.beginTransaction();
            //query = session.createQuery("FROM Rol WHERE lower(nombre) LIKE lower(:rolbuscar)");
            String sqlquery = "FROM Maquina WHERE idActividad = :idActividad";
            query = session.createQuery(sqlquery);
            query.setParameter("idActividad", idActividad);

            List<Maquina> tempMaquinas = query.list();
            for (Iterator iterator = tempMaquinas.iterator(); iterator.hasNext();) {
                Maquina maq = (Maquina) iterator.next();
                if (maq.getEstado() == 1) {
                    maquinas.add(maq);
                }
            }
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }

        return maquinas;

    }
       
       public int verificaexistenciamaquina(String nombre) {
        int verdad = 0;
        String nombre2;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Maquina> productos = session.createQuery("From Maquina").list();
            for (Iterator iterator = productos.iterator(); iterator.hasNext();) {
                Maquina m = (Maquina) iterator.next();
                nombre2 = m.getNombre();
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
       
       // Verificar Proveedor
       
       public int verificaexistenciaproveedor(long ruc) {
        int verdad = 0;
        long ruc2;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Proveedor> proveedores = session.createQuery("From Proveedor").list();
            for (Iterator iterator = proveedores.iterator(); iterator.hasNext();) {
                Proveedor m = (Proveedor) iterator.next();
                ruc2 = m.getRuc();
                if (ruc2==ruc) {
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
       
       //VERIFICA EXISTENCIA CLIENTE
    
       
       public int verificaexistenciacliente(long ruc) {
        int verdad = 0;
        long ruc2;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Cliente> clientes = session.createQuery("From Cliente").list();
            for (Iterator iterator = clientes.iterator(); iterator.hasNext();) {
                Cliente m = (Cliente) iterator.next();
                ruc2 = m.getRuc();
                if (ruc2==ruc) {
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
       
    //CARGA MASIVA MAQUINAS
    //Guarda Maquinas desde una lista
       
     public void guardaListamaquina(List<modelos.Maquina> maquinas){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            for(Maquina maq : maquinas){
                session.save(maq);
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
   
     // CARGA MASIVA CLIENTES
     
          public void guardaListacliente(List<modelos.Cliente> clientes){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            for(Cliente cli : clientes){
                session.save(cli);
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
     
     // CARGA MASIVA PROVEEDORES
     
     public void guardaListaproveedores(List<modelos.Proveedor> proveedores){
    Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            for(Proveedor pr : proveedores){
                session.save(pr);
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
     
     //CARGA MASIVA VENTAS
     
      public void guardaListaventas(List<modelos.Venta> ventas){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            for(modelos.Venta v : ventas){
                session.save(v);
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
     
     //Carga Masiva Productos por Proveedor
     
     public void guardaListaProductosProveedor(List<modelos.Productoxproveedor> lista){
    Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            for(Productoxproveedor pr : lista){
                session.save(pr);
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
       
    //SAORI: CARGA MASIVA
    //Guardar empleados provenientes de una lista
    public void guardaListaEmpleados(List<Empleado> empleados, List<Float> datos) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        modelos.Actividad actividad1 = null;
        modelos.Actividad actividad2 = null;
        modelos.Actividad actividad3 = null;
        modelos.Actividad actividad4 = null;
        modelos.Turno turno1 = null;
        modelos.Turno turno2 = null;
        modelos.Empleado emp = new Empleado();
        int cont=0;
        
        try {
            tx = session.beginTransaction();
            controladoralmacen mycontroladoralmacen = new controladoralmacen();
            turno1 = sacaturnoxid(1);
            turno2 = sacaturnoxid(2);
            actividad1 = mycontroladoralmacen.sacaactividadbyid(1);
            actividad2 = mycontroladoralmacen.sacaactividadbyid(2);
            actividad3 = mycontroladoralmacen.sacaactividadbyid(3);
            actividad4 = mycontroladoralmacen.sacaactividadbyid(4);
            
            for(Empleado empleado : empleados){
                session.save(empleado);
            }
            tx.commit();
            for(Empleado empleado : empleados){
//                tx.commit();
                int dni = empleado.getDni();
                emp = sacaempleadoxDNI(dni);
                
                modelos.DesempenoId id11 = new modelos.DesempenoId();
                
                id11.setIdactividad(1);
                id11.setIdempleado(emp.getIdempleado());
                id11.setIdturno(1);
                
                guardadesempeñoxempleado(id11, emp, actividad1, turno1, datos.get(cont++), datos.get(cont++));
                
                modelos.DesempenoId id12 = new modelos.DesempenoId();
                
                id12.setIdactividad(1);
                id12.setIdempleado(emp.getIdempleado());
                id12.setIdturno(2);
                
                guardadesempeñoxempleado(id12, emp, actividad1, turno2, datos.get(cont++), datos.get(cont++));
               
                modelos.DesempenoId id21 = new modelos.DesempenoId();
                
                id21.setIdactividad(2);
                id21.setIdempleado(emp.getIdempleado());
                id21.setIdturno(1);
                
                guardadesempeñoxempleado(id21, emp, actividad2, turno1, datos.get(cont++), datos.get(cont++));
                modelos.DesempenoId id22 = new modelos.DesempenoId();
                id22.setIdactividad(2);
                id22.setIdempleado(emp.getIdempleado());
                id22.setIdturno(2);
                
                guardadesempeñoxempleado(id22, emp, actividad2, turno2, datos.get(cont++), datos.get(cont++));
                modelos.DesempenoId id31 = new modelos.DesempenoId();
                id31.setIdactividad(3);
                id31.setIdempleado(emp.getIdempleado());
                id31.setIdturno(1);
                
                guardadesempeñoxempleado(id31, emp, actividad3, turno1, datos.get(cont++), datos.get(cont++));
                modelos.DesempenoId id32 = new modelos.DesempenoId();
                id32.setIdactividad(3);
                id32.setIdempleado(emp.getIdempleado());
                id32.setIdturno(2);
                
                guardadesempeñoxempleado(id32, emp, actividad3, turno2, datos.get(cont++), datos.get(cont++));
                modelos.DesempenoId id41 = new modelos.DesempenoId();
                
                id41.setIdactividad(4);
                id41.setIdempleado(emp.getIdempleado());
                id41.setIdturno(1);
                
                guardadesempeñoxempleado(id41, emp, actividad4, turno1, datos.get(cont++), datos.get(cont++));
                modelos.DesempenoId id42 = new modelos.DesempenoId();
                id42.setIdactividad(4);
                id42.setIdempleado(emp.getIdempleado());
                id42.setIdturno(2);
                
                guardadesempeñoxempleado(id42, emp, actividad4, turno2, datos.get(cont++), datos.get(cont++));
      
            }   
            
//            tx.commit();
            
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            System.out.println(e);
        } finally {
            session.close();
        }
    }
    
    public int verificaexistenciaempleado(int dni) {
        int verdad = 0;
        int dni2;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Empleado> empleados = session.createQuery("From Empleado").list();
            for (Iterator iterator = empleados.iterator(); iterator.hasNext();) {
                Empleado e = (Empleado) iterator.next();
                dni2 = e.getDni();
                if (dni2 == dni) {
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
}

