package mobile.a3tech.com.a3tech.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by SPIA on 05/04/2017.
 */

public class DateStuffs {
    public static String TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public static String HOURS_MINUTES_FORMAT = "HH:mm";
    public static String SIMPLE_DATE_FORMAT = "dd/MM/yyyy";
    public static String MOUNTH_ONY = "MMMM";
    public static String YEAR_ONY = "yyyy";
    public static String DATE_DAY_APLHA  ="EEE, d MMM yyyy HH:mm";
    public static String DATE_TIME  ="dd MMM  HH:mm";
    public static String DATE_APLPHA_HEADER = "EEEE d MMMM";
    public static String DAY_APLPHA_HEADER = "EEEE";
    public static String MONTH_APLPHA_HEADER = "MMMM";
    public static String DAY_NBR_APLPHA_HEADER = "dd";
    public static String dateToString(String format, Date date){
        if(date == null){
            throw new RuntimeException("Empty Date");
        }
        try{
            DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
            return df.format(date);
        }catch (Exception e){
            throw new RuntimeException("Format Date non valide");
        }

    }
    public static Date stringToDate(String date, String form){
        try{  DateFormat format = new SimpleDateFormat(form, new Locale("Fr"));
       return format.parse(date);
   }catch (Exception e){
        throw new RuntimeException("Format Date non valide");
    }
    }

    public static Date getCurrentDate(String form){
        try{  DateFormat format = new SimpleDateFormat(form, new Locale("Fr"));
            return format.parse(dateToString(form,new Date()));
        }catch (Exception e){
            throw new RuntimeException("Format Date non valide");
        }
    }

    public static Date dateInFourmat(Date date, String form) {
        String dateS = dateToString(SIMPLE_DATE_FORMAT, date);
        return stringToDate(dateS, SIMPLE_DATE_FORMAT);
    }
    public static  Date getFirstDateOfCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        return cal.getTime();
    }

    public static  Date getFirstDateOfCurrentMonthMinus (int nbrMounth) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -3);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        return cal.getTime();
    }


    public static Date getLastDayOfMounth(){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.HOUR_OF_DAY, 23);
        return c.getTime();
    }

    public static Date getLastDayOfMounthPlus3(){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.HOUR_OF_DAY, 23);
        return c.getTime();
    }
    public static Date initDatesFrom(Date dateFrom){
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateFrom);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        return cal.getTime();
    }


    public static Date initDatesTo(Date dateFrom){
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateFrom);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        return cal.getTime();
    }




}
