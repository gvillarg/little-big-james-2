package modelos;
// Generated 17-nov-2013 13:30:35 by Hibernate Tools 3.2.1.GA



/**
 * Detallefactura generated by hbm2java
 */
public class Detallefactura  implements java.io.Serializable {


     private int iddetallefactura;
     private Detalleventa detalleventa;
     private Factura factura;

    public Detallefactura() {
    }

    public Detallefactura(int iddetallefactura, Detalleventa detalleventa, Factura factura) {
       this.iddetallefactura = iddetallefactura;
       this.detalleventa = detalleventa;
       this.factura = factura;
    }
   
    public int getIddetallefactura() {
        return this.iddetallefactura;
    }
    
    public void setIddetallefactura(int iddetallefactura) {
        this.iddetallefactura = iddetallefactura;
    }
    public Detalleventa getDetalleventa() {
        return this.detalleventa;
    }
    
    public void setDetalleventa(Detalleventa detalleventa) {
        this.detalleventa = detalleventa;
    }
    public Factura getFactura() {
        return this.factura;
    }
    
    public void setFactura(Factura factura) {
        this.factura = factura;
    }




}


