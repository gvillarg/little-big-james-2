package modelos;
// Generated 17-nov-2013 13:30:35 by Hibernate Tools 3.2.1.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Turno generated by hbm2java
 */
public class Turno  implements java.io.Serializable {


     private int idturno;
     private String nombre;
     private Date horainicio;
     private Date horafin;
     private Set asignacionxempleados = new HashSet(0);
     private Set desempenos = new HashSet(0);

    public Turno() {
    }

	
    public Turno(int idturno) {
        this.idturno = idturno;
    }
    public Turno(int idturno, String nombre, Date horainicio, Date horafin, Set asignacionxempleados, Set desempenos) {
       this.idturno = idturno;
       this.nombre = nombre;
       this.horainicio = horainicio;
       this.horafin = horafin;
       this.asignacionxempleados = asignacionxempleados;
       this.desempenos = desempenos;
    }
   
    public int getIdturno() {
        return this.idturno;
    }
    
    public void setIdturno(int idturno) {
        this.idturno = idturno;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Date getHorainicio() {
        return this.horainicio;
    }
    
    public void setHorainicio(Date horainicio) {
        this.horainicio = horainicio;
    }
    public Date getHorafin() {
        return this.horafin;
    }
    
    public void setHorafin(Date horafin) {
        this.horafin = horafin;
    }
    public Set getAsignacionxempleados() {
        return this.asignacionxempleados;
    }
    
    public void setAsignacionxempleados(Set asignacionxempleados) {
        this.asignacionxempleados = asignacionxempleados;
    }
    public Set getDesempenos() {
        return this.desempenos;
    }
    
    public void setDesempenos(Set desempenos) {
        this.desempenos = desempenos;
    }




}

