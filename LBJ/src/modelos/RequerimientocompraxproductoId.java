package modelos;
// Generated 17-nov-2013 13:30:35 by Hibernate Tools 3.2.1.GA



/**
 * RequerimientocompraxproductoId generated by hbm2java
 */
public class RequerimientocompraxproductoId  implements java.io.Serializable {


     private int idreqerimientocompra;
     private int idproducto;

    public RequerimientocompraxproductoId() {
    }

    public RequerimientocompraxproductoId(int idreqerimientocompra, int idproducto) {
       this.idreqerimientocompra = idreqerimientocompra;
       this.idproducto = idproducto;
    }
   
    public int getIdreqerimientocompra() {
        return this.idreqerimientocompra;
    }
    
    public void setIdreqerimientocompra(int idreqerimientocompra) {
        this.idreqerimientocompra = idreqerimientocompra;
    }
    public int getIdproducto() {
        return this.idproducto;
    }
    
    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof RequerimientocompraxproductoId) ) return false;
		 RequerimientocompraxproductoId castOther = ( RequerimientocompraxproductoId ) other; 
         
		 return (this.getIdreqerimientocompra()==castOther.getIdreqerimientocompra())
 && (this.getIdproducto()==castOther.getIdproducto());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getIdreqerimientocompra();
         result = 37 * result + this.getIdproducto();
         return result;
   }   


}


