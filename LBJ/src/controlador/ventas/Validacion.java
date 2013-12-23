/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.ventas;

import Seguridad.Controlador.*;
import java.awt.Dialog;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

/**
 *
 * @author Saori
 */
public class Validacion {
    public int MAXLENGTHDOUBLE = 10;
    public int MAXLENGTHTEL = 9;
    public int MINLENGTHTEL = 7;
    public int MAXLENGTHDNI = 8;
    public int MAXLENGTHNOM = 50;
    public int MAXLENGTHSTR = 256;
    public int MAXLENGTHCLAVE = 20;
    public int MINLENGTHCLAVE = 8;   
    public int MAXLENGTHFECHA = 10;
    public int ANHOACTUAL = Calendar.getInstance().get(Calendar.YEAR);
    public int ANHOBASE = 1900;
    public String msj;
    
    /**     * 
     * @param args the command line arguments
     */
    
    public boolean es_vacio(String cadena){
        if (cadena.isEmpty()) {
            msj="Debe rellenar el campo de: ";
            return true;
        }
        else{
            return false;   
        }
    }
    
    public boolean es_clave(String cadena){
        int i = 0;
        boolean es_valido = true;
        
        //solo longitud
        if(cadena.isEmpty()){
            msj = "Debe rellenar el campo de: RUC.";
            return false;
        }
        if ((cadena.length() >= MINLENGTHCLAVE) && (cadena.length()<=MAXLENGTHCLAVE)){
            while((i<cadena.length()) && es_valido){	                
		if ( es_letra(cadena.charAt(i)) || es_digito(cadena.charAt(i))) es_valido=true;                            
            	else 
                {
                    msj = "La  debe tener solo letras y/o números.";
                    es_valido=false;                
                    return false;
                }
		i++;
            }
            return true;
        }
        else {
            msj="La Contraseña debe tener:\nLong min. : "+ MINLENGTHCLAVE+"\nLong. max : "+MAXLENGTHCLAVE;
            return false;
        }		
    }
    
    public boolean es_telefono(String cadena){
        //numeros y espacio
        boolean es_valido=true;
        int i=0;
        if ((cadena.length()<=MAXLENGTHTEL) && (cadena.length()>=MINLENGTHTEL))
            while ((i<cadena.length())&&es_valido){
                if (es_digito(cadena.charAt(i)) || cadena.charAt(i)==' ') es_valido=true;
                else {
                    msj = "Solo dígitos.";
                    es_valido=false;}
                i++;
            }
        else {
            msj = "Long. min. : " + MINLENGTHTEL + "\n Long. max. = " + MAXLENGTHTEL ;
            es_valido=false;        
        }
        return es_valido;
    }
    
     public boolean es_correo(String cadena){
         //cad contiene solo letras, digitos y "_"
         //cad@cad.cad.cad
        boolean es_valido=true;
	int cantArroba=0,i=0,cantPuntos=0;		
	char ant='a';
        
        if (!cadena.isEmpty()){
	   while ((i<cadena.length()) && es_valido){
			
                if(i==0) {
                     if (es_letra(cadena.charAt(i))) es_valido=true;
                     else {
                         msj = "El correo debe empezar con una letra.";
                         es_valido=false;
                     }
                }
                else            
                if ( cantArroba==0 && (es_letra(cadena.charAt(i))||es_digito(cadena.charAt(i))||cadena.charAt(i)=='_'||cadena.charAt(i)=='.')) es_valido=true;
                else
                if (cantArroba==1 && es_letra(cadena.charAt(i))) es_valido=true;
                else 
                if (es_letra(ant) && cadena.charAt(i)=='.' && ((cadena.length()-1)!= i) && (cantArroba==1)){ 
                    cantPuntos++;
                    if (cantPuntos<=2)es_valido=true;
                    else es_valido=true;			
                }				
                else
                if (cadena.charAt(i)=='@'){
                    cantArroba++;
                    if (cantArroba==1) es_valido=true;
                    else {
                        msj = "El correo debe contener solo un @.";
                        es_valido=false;
                    }                
                }            
                else {
                    es_valido=false;
                }
                ant=cadena.charAt(i);
                i++;
           }
           if ( es_valido && (cantPuntos==1||cantPuntos==2) && cantArroba==1) es_valido=true;
           else {
               msj = "Formato aceptado de Correo: \"cad@dom.dom.dom\" o \"cad@dom.dom\"\nFormato cad : \"letras,digitos, \"_\", \".\"\n"                
                + "Formato dom : solo letras.";
               es_valido=false;
           }
        }
        else {
            msj= "Debe rellenar el campo de: Correo";
            es_valido=false;}
        
        return es_valido;
    }
    
    public boolean es_dni(String cadena){
        //que no deje ingresar mas del tamnho permitido
        boolean es_valido;
        int i=0;
	if (cadena.length()==MAXLENGTHDNI){
           if (es_numero_entero(cadena)){
               if ((Integer.parseInt(cadena))>0) es_valido=true;
               else es_valido=false;
           }
           else {
               msj = "Solo dígitos.";
               es_valido=false;
           }
        }
	else {
            msj= "Longitud máxima es "+MAXLENGTHDNI;
            es_valido=false;}
		
	return es_valido;
    }
 
    public boolean es_fecha(String cadena){
        //formato dd/mm/aaaa
    	boolean es_valido = true;
	int i=0,dd,mm,aa;
		
	if ( cadena.length() == MAXLENGTHFECHA){			
            while ((i<cadena.length()) && es_valido){
                if ((i==0||i==1||i==3||i==4||i==6||i==7||i==8||i==9) && es_digito(cadena.charAt(i))) es_valido=true;
                else if ((i==2|| i==5) && (cadena.charAt(i)=='/')) es_valido=true;                
                i++;
            }
            
            if (es_valido == true) {
                                 
                dd = Integer.parseInt(cadena.substring(0, 2));
                mm = Integer.parseInt(cadena.substring(3,5));
                aa = Integer.parseInt(cadena.substring(6,10));                    
                if (verifica_fecha(dd,mm,aa)) es_valido = true;
                else {
                    msj = "La fecha no existe.";
                    es_valido=false;
                }				
            }
        }
	else {
            msj = "Fomato válido: \"dd/mm/aaaa\"";
            es_valido=false;
        }
		
        return es_valido;
	}

    public boolean es_numero_entero(String cadena){
        //evitar que se ingresen letras, solo digitos y punto si es real
        boolean es_valido=true;
	int i=0;        
        while ((i<cadena.length()) && es_valido) {
             if (es_digito(cadena.charAt(i))) es_valido=true;			
             else {
                 msj = "Solo dígitos.";
                 es_valido=false;
             }
             i++;
        }
                
	return es_valido;    
    }
    

    public boolean es_numero_real(String cadena){        
        cadena = cadena.replace(",","");
         try {
            Double.parseDouble(cadena);            
            if (cadena.length()<=MAXLENGTHDOUBLE){
                if (Double.parseDouble(cadena)>0){
                    return true;
                }
                else{
                    msj ="Debe ser mayor a 0.";
                    return false;
                }                    
            }
            else{
                msj= "Longitud max. de "+MAXLENGTHDOUBLE;
                return false;
            }        
         }
         catch(Exception nfe) {            
            System.out.println(nfe);
            msj = "Solo dígitos y \".\"";
            return false;
         }    
    }
     
    public boolean es_nombre(String cadena){
	boolean es_valido=true;
	int i=0;	
	if (cadena.length() <= MAXLENGTHNOM) {				
            while((i<cadena.length()) && es_valido){	                
		if ( es_letra(cadena.charAt(i))||cadena.charAt(i)==' ' || es_digito(cadena.charAt(i)) || 
                        cadena.charAt(i) == 'ñ' || cadena.charAt(i) == 'Ñ') 
                    es_valido=true;                            
            	else {
                    msj = "Solo letras.";
                    es_valido=false;
                    return false;
                }
		i++;
            }	
        }
	else {
            msj = "Longitud maxima de "+ MAXLENGTHNOM+ " caracteres.";
            es_valido =false;
        }
		
	return es_valido;
    }
    
//     public String msj_nombre(){
//        return "Solo letras.\nLongitud maxima de "+ MAXLENGTHSTR + " caracteres.";
//    }
     public boolean es_direccion(String cadena){
	boolean es_valido=true;
	int i=0;	
	if (cadena.length() <= MAXLENGTHSTR) {				
            while((i<cadena.length()) && es_valido){	                
		if ( es_letra(cadena.charAt(i))||cadena.charAt(i)==' '|| cadena.charAt(i)=='/' ||
                      cadena.charAt(i)=='-'||cadena.charAt(i)=='_' || es_digito(cadena.charAt(i))
                       || cadena.charAt(i)=='#'|| cadena.charAt(i)=='°'|| cadena.charAt(i)=='.') es_valido=true;                            
            	else 
                {
                    msj = "Solo letras, digitos o \ncaracteres: \"-, \".\" ,_,°,/,#\"";
                    es_valido=false;                
                }
		i++;
            }	
        }
	else {
            msj = "Longitud maxima de "+ MAXLENGTHSTR+" caracteres.";
            es_valido =false;
        }
		
	return es_valido;
    }
    
    public boolean es_descripcion(String cadena){
        
	if (cadena.length() <= MAXLENGTHSTR) return true;
        else {
            msj = "Longitud maxima de "+MAXLENGTHSTR+" caracteres.";
            return false;
        }
    }   
    
    public boolean verifica_fecha(int dd, int mm, int aa){
	boolean es_valido=false;		
	//dia
	if (aa>=ANHOBASE && aa<=ANHOACTUAL) {			
            if (mm>0 && mm<=12){	
		if ((mm==1 || mm==3 || mm== 5||mm==7||mm==8||mm==10||mm==12) && dd>0 && dd<=31) es_valido=true;
		else
		if ((mm==4 ||mm==6||mm==9||mm==11) && dd>0 && dd<=30) es_valido=true;
		else
		if (mm==2){
                    if (es_bisiesto(aa) && dd>0 && dd<=29) es_valido=true;
                    else
                    if (!es_bisiesto(aa) && dd>0 && dd<=28) es_valido = true;
		}
            }
	}        
	return es_valido;	
    }
	
    public boolean es_bisiesto(int aa){
	if ((aa%4)==0) return true;
	return false;
    }
    
    public boolean es_letra_mayusc(char car){
    	if (((int)car >=65) && ((int)car<=90)) return true;
	return false;
    }

//    public boolean es_letra(Character car){
//        
//    	if ((((int)car >=65) && ((int)car <=90)) || (((int)car>=90) && ((int)car<=122)) || es_letra_especial(car)) return true;
//        return false;			
//    }
	
    public boolean es_letra(Character car){
        
    	if ((((int)car >=65) && ((int)car <=90)) || (((int)car>=90) && ((int)car<=122)) || (((int)car>=164) && ((int)car<=165))) return true;
        return false;			
    }
    
    public boolean es_digito(char dig){
	if (((int) dig>=48) && ((int)dig <=57)) return true;
	return false;
    }   
   
    public boolean es_alfanumerico(char car){
        if (es_letra(car) || es_digito(car)) return true;
        return false;
    }
    
   public String formato_real(String cad){        
        String s;
        NumberFormat formatter;
        formatter = new DecimalFormat("###,###,###.00");        
        s = formatter.format(Double.parseDouble(cad));            
        s= s.replace(",","-");
        s= s.replace(".",",");
        s= s.replace("-",".");                       
        return s;
    }
    public boolean es_letra_especial(char car){
        switch (car){
            case 'ñ': return true;
            case 'Ñ':return true;
            case 'á': return true;
            case 'é':return true;
            case 'í':return true;
            case 'ó':return true;
            case 'ú':return true;
            case 'Á':return true;
            case 'É':return true;           
            case 'Í':return true;    
            case 'Ó':return true; 
            case 'Ú':return true;
            case 'ü':return true;
            case 'Ü':return true;    
        }
        return false;
    }    
}
