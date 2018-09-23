package mobile.a3tech.com.a3tech.utils;

import java.util.Calendar;
import java.util.Date;

public class DateManager {
	
	
	  public static String elapsed2(Date before,Date after){ 
	    	//Date after = new Date();
	        Calendar start =  DateToCalendar(before);
	        Calendar end = DateToCalendar(after);
	        Integer[] elapsed = new Integer[6];
	        Calendar clone = (Calendar) start.clone(); // Otherwise changes are been reflected.
	        elapsed[0] = elapsed(clone, end, Calendar.YEAR);
	        clone.add(Calendar.YEAR, elapsed[0]);
	        elapsed[1] = elapsed(clone, end, Calendar.MONTH);
	        clone.add(Calendar.MONTH, elapsed[1]);
	        elapsed[2] = elapsed(clone, end, Calendar.DATE);
	        clone.add(Calendar.DATE, elapsed[2]);
	        elapsed[3] = (int) (end.getTimeInMillis() - clone.getTimeInMillis()) / 3600000;
	        clone.add(Calendar.HOUR, elapsed[3]);
	        elapsed[4] = (int) (end.getTimeInMillis() - clone.getTimeInMillis()) / 60000;
	        clone.add(Calendar.MINUTE, elapsed[4]);
	        elapsed[5] = (int) (end.getTimeInMillis() - clone.getTimeInMillis()) / 1000;
	        String datestr = "";
	        if(elapsed[0]!=0)
	        	datestr = datestr + elapsed[0]+"y ";
	        if(elapsed[1]!=0)
	        	datestr = datestr + elapsed[1]+"m ";
	        if(elapsed[2]!=0)
	        	datestr = datestr + elapsed[2]+"d ";
	        if(elapsed[3]!=0)
	        	datestr = datestr + elapsed[3]+"h ";
	        if(elapsed[4]!=0)
	        	datestr = datestr+  elapsed[4]+"m";
	        return datestr;
	      
	    }
	  
	  public static String elapsed(Date before,Date after){ 
	    	//Date after = new Date();
	        Calendar start =  DateToCalendar(before);
	        Calendar end = DateToCalendar(after);
	        Integer[] elapsed = new Integer[4];
	        Calendar clone = (Calendar) start.clone(); // Otherwise changes are been reflected.
//	        elapsed[0] = elapsed(clone, end, Calendar.YEAR);
//	        clone.add(Calendar.YEAR, elapsed[0]);
//	        elapsed[1] = elapsed(clone, end, Calendar.MONTH);
//	        clone.add(Calendar.MONTH, elapsed[1]);
	        elapsed[0] = elapsed(clone, end, Calendar.DATE);
	        clone.add(Calendar.DATE, elapsed[0]);
	        elapsed[1] = (int) (end.getTimeInMillis() - clone.getTimeInMillis()) / 3600000;
	        clone.add(Calendar.HOUR, elapsed[1]);
	        elapsed[2] = (int) (end.getTimeInMillis() - clone.getTimeInMillis()) / 60000;
	        clone.add(Calendar.MINUTE, elapsed[2]);
	        elapsed[3] = (int) (end.getTimeInMillis() - clone.getTimeInMillis()) / 1000;
	        String datestr = "";
//	        if(elapsed[0]!=0)
//	        	datestr = datestr + elapsed[0]+"y ";
//	        if(elapsed[1]!=0)
//	        	datestr = datestr + elapsed[1]+"m ";
	        if(elapsed[0]!=0)
	        	datestr = datestr + elapsed[0]+"d ";
	        if(elapsed[1]!=0)
	        	datestr = datestr + elapsed[1]+"h ";
	        if(elapsed[2]!=0)
	        	datestr = datestr+  elapsed[2]+"mn";
	        return datestr;
	      
	    }
	  
	  public static Integer[] elapsedTime(Date before,Date after){ 
	        Calendar start =  DateToCalendar(before);
	        Calendar end = DateToCalendar(after);
	        Integer[] elapsed = new Integer[4];
	        Calendar clone = (Calendar) start.clone();
	        elapsed[0] = elapsed(clone, end, Calendar.DATE);
	        clone.add(Calendar.DATE, elapsed[0]);
	        elapsed[1] = (int) (end.getTimeInMillis() - clone.getTimeInMillis()) / 3600000;
	        clone.add(Calendar.HOUR, elapsed[1]);
	        elapsed[2] = (int) (end.getTimeInMillis() - clone.getTimeInMillis()) / 60000;
	        clone.add(Calendar.MINUTE, elapsed[2]);
	        elapsed[3] = (int) (end.getTimeInMillis() - clone.getTimeInMillis()) / 1000;
	        return elapsed;
	      
	    }



	    private static int elapsed(Calendar before, Calendar after, int field) {
	        Calendar clone = (Calendar) before.clone(); 
	        int elapsed = -1;
	        while (!clone.after(after)) {
	            clone.add(field, 1);
	            elapsed++;
	        }
	        return elapsed;
	    }

	    public static Calendar DateToCalendar(Date date){ 
	    	  Calendar cal = Calendar.getInstance();
	    	  cal.setTime(date);
	    	  return cal;
	    }
	    

}
