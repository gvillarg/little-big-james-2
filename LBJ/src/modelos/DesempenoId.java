package modelos;
// Generated 17-nov-2013 13:30:35 by Hibernate Tools 3.2.1.GA



/**
 * DesempenoId generated by hbm2java
 */
public class DesempenoId  implements java.io.Serializable {


     private int idempleado;
     private int idactividad;
     private int idturno;

    public DesempenoId() {
    }

    public DesempenoId(int idempleado, int idactividad, int idturno) {
       this.idempleado = idempleado;
       this.idactividad = idactividad;
       this.idturno = idturno;
    }
   
    public int getIdempleado() {
        return this.idempleado;
    }
    
    public void setIdempleado(int idempleado) {
        this.idempleado = idempleado;
    }
    public int getIdactividad() {
        return this.idactividad;
    }
    
    public void setIdactividad(int idactividad) {
        this.idactividad = idactividad;
    }
    public int getIdturno() {
        return this.idturno;
    }
    
    public void setIdturno(int idturno) {
        this.idturno = idturno;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof DesempenoId) ) return false;
		 DesempenoId castOther = ( DesempenoId ) other; 
         
		 return (this.getIdempleado()==castOther.getIdempleado())
 && (this.getIdactividad()==castOther.getIdactividad())
 && (this.getIdturno()==castOther.getIdturno());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getIdempleado();
         result = 37 * result + this.getIdactividad();
         result = 37 * result + this.getIdturno();
         return result;
   }   


}


