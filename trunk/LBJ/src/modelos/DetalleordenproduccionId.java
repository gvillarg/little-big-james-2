package modelos;
// Generated 17-nov-2013 13:30:35 by Hibernate Tools 3.2.1.GA



/**
 * DetalleordenproduccionId generated by hbm2java
 */
public class DetalleordenproduccionId  implements java.io.Serializable {


     private int idproducto;
     private int idordenproduccion;

    public DetalleordenproduccionId() {
    }

    public DetalleordenproduccionId(int idproducto, int idordenproduccion) {
       this.idproducto = idproducto;
       this.idordenproduccion = idordenproduccion;
    }
   
    public int getIdproducto() {
        return this.idproducto;
    }
    
    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }
    public int getIdordenproduccion() {
        return this.idordenproduccion;
    }
    
    public void setIdordenproduccion(int idordenproduccion) {
        this.idordenproduccion = idordenproduccion;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof DetalleordenproduccionId) ) return false;
		 DetalleordenproduccionId castOther = ( DetalleordenproduccionId ) other; 
         
		 return (this.getIdproducto()==castOther.getIdproducto())
 && (this.getIdordenproduccion()==castOther.getIdordenproduccion());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getIdproducto();
         result = 37 * result + this.getIdordenproduccion();
         return result;
   }   


}

