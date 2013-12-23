/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Guti
 */
public class Utils {
 
    public static int compararFechas(Date fecha1, Date fecha2) {
        Calendar cal1=Calendar.getInstance();
        cal1.setTime(fecha1);
        Calendar cal2=Calendar.getInstance();
        cal2.setTime(fecha2);
        int dif=cal1.get(Calendar.YEAR)-cal2.get(Calendar.YEAR);
        if(dif!=0){
            return dif;
        }
        else{
            return cal1.get(Calendar.DAY_OF_YEAR)-cal2.get(Calendar.DAY_OF_YEAR);            
        }
            
    }
}
