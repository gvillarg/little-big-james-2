package modelos;
// Generated 17-nov-2013 13:30:35 by Hibernate Tools 3.2.1.GA



/**
 * ProductoxproveedorId generated by hbm2java
 */
public class ProductoxproveedorId  implements java.io.Serializable {


     private int idproducto;
     private int idproveedor;

    public ProductoxproveedorId() {
    }

    public ProductoxproveedorId(int idproducto, int idproveedor) {
       this.idproducto = idproducto;
       this.idproveedor = idproveedor;
    }
   
    public int getIdproducto() {
        return this.idproducto;
    }
    
    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }
    public int getIdproveedor() {
        return this.idproveedor;
    }
    
    public void setIdproveedor(int idproveedor) {
        this.idproveedor = idproveedor;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ProductoxproveedorId) ) return false;
		 ProductoxproveedorId castOther = ( ProductoxproveedorId ) other; 
         
		 return (this.getIdproducto()==castOther.getIdproducto())
 && (this.getIdproveedor()==castOther.getIdproveedor());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getIdproducto();
         result = 37 * result + this.getIdproveedor();
         return result;
   }   


}

