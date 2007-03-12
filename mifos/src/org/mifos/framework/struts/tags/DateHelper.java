package org.mifos.framework.struts.tags;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.StringTokenizer;

import org.mifos.framework.exceptions.ApplicationException;
import org.mifos.framework.exceptions.FrameworkRuntimeException;

public class DateHelper {

	private final static String dbFormat = "yyyy-MM-dd";

	public static String convertUserToDbFmt(String userDate, String userPattern) {
		try {
			SimpleDateFormat userFormat = new SimpleDateFormat(userPattern);
			java.util.Date date = userFormat.parse(userDate);
			return toDatabaseFormat(date);
		} catch (ParseException e) {
			throw new FrameworkRuntimeException(e);
		}
	}

	public static String convertDbToUserFmt(String dbDate, String userPattern) {
		try {
			SimpleDateFormat databaseFormat = new SimpleDateFormat(dbFormat);
			java.util.Date date = databaseFormat.parse(dbDate);
			SimpleDateFormat userFormat = new SimpleDateFormat(userPattern);
			return userFormat.format(date);
		} catch (ParseException e) {
			throw new FrameworkRuntimeException(e);
		}
	}

	public static String getUserLocaleDate(Locale locale, String databaseDate) {
		if (locale != null && databaseDate != null && !databaseDate.equals("")) {
			try {
				SimpleDateFormat shortFormat = (SimpleDateFormat)
					DateFormat.getDateInstance(DateFormat.SHORT, locale);
				String userfmt = convertToCurrentDateFormat(
					shortFormat.toPattern()
				);
				return convertDbToUserFmt(databaseDate, userfmt);
			} catch (FrameworkRuntimeException e) {
				throw e;
			} catch (Exception e) {
				throw new FrameworkRuntimeException(e);
			}
		} else {
			return "";
		}
	}

	public static java.util.Date getDate(String value) {
		if( value!=null && !value.equals("")){
			try {
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				// Enable this once we've taken a bit more of a look
				// at where this gets called, run the tests, etc.
				// But when the user types "13" for the month, for example,
				// that should be an error not January of the next year.
				//format.setLenient(false);
				return format.parse(value);
			} catch (Exception e) {
				throw new FrameworkRuntimeException(e);
			}
		} else {
			return null;
		}
	}

	//Bug id 26765. Added the method convertToCurrentDateFormat and called it from this method
	public static String getCurrentDate(Locale locale) {
		Calendar currentCalendar = new GregorianCalendar();
		int year=currentCalendar.get(Calendar.YEAR);
		int month=currentCalendar.get(Calendar.MONTH);
		int day=currentCalendar.get(Calendar.DAY_OF_MONTH);
		currentCalendar = new GregorianCalendar(year,month,day);
		java.sql.Date currentDate = new java.sql.Date(
			currentCalendar.getTimeInMillis());
		SimpleDateFormat format = (SimpleDateFormat)
			DateFormat.getDateInstance(DateFormat.SHORT, locale);
		String userfmt = convertToCurrentDateFormat(format.toPattern());
		return convertDbToUserFmt(currentDate.toString(),userfmt);
	}

	//Bug id 26765. Added the method convertToCurrentDateFormat
	public static String convertToCurrentDateFormat(String pattern){
		char chArray[]=pattern.toCharArray();
		StringBuilder fmt = new StringBuilder();
		boolean d=false;
		boolean m=false;
		boolean y=false;
		String separator=DateHelper.getSeparator(pattern);
		for (int i=0; i<chArray.length;i++){
			if ((chArray[i]=='d' ||chArray[i]=='D')&& !d){
				fmt.append("dd"); d = true;
				fmt.append(separator);
			}
			else if ((chArray[i]=='m' ||chArray[i]=='M') && !m){
				fmt.append("MM"); m = true;
				fmt.append(separator);
			}
			else if ((chArray[i]=='y' ||chArray[i]=='Y') && !y){
				fmt.append("yyyy"); y = true;
				fmt.append(separator);
			}
		}
		return fmt.substring(0,fmt.length()-1);
	}

	public static String convertToMFIFormat(String date, String format){
		String MFIString;
		String MFIfmt=getMFIFormat();
		String day="";
		String month="";
		String year="";
		String token;
		MFIfmt=convertToDateTagFormat(MFIfmt);
		StringTokenizer stfmt = new StringTokenizer(format,"/");
		StringTokenizer stdt = new StringTokenizer(date,"/");
		while (stfmt.hasMoreTokens()&& stdt.hasMoreTokens()){
			token= stfmt.nextToken();
			if (token.equalsIgnoreCase("D")){
				day=stdt.nextToken();
			} else if (token.equalsIgnoreCase("M")){
				month=stdt.nextToken();
			} else {
				year=stdt.nextToken();
			}
		}
		MFIString=createDateString(day,month,year,MFIfmt);
		return MFIString;
	}

	public static String[] getDayMonthYear(String date, String format){
		String day="";
		String month="";
		String year="";
		String token;
		StringTokenizer stfmt = new StringTokenizer(format,"/");
		StringTokenizer stdt = new StringTokenizer(date,"/");
		while(stfmt.hasMoreTokens()&& stdt.hasMoreTokens()){
			token= stfmt.nextToken();
			if (token.equalsIgnoreCase("D")){
				day=stdt.nextToken();
			}else if (token.equalsIgnoreCase("M")){
				month=stdt.nextToken();
			}else
				year=stdt.nextToken();
		}
		return new String[]{day,month,year};
	}

	public static String[] getDayMonthYear(String date, String format,String separator){
		String day="";
		String month="";
		String year="";
		String token;
		StringTokenizer stfmt = new StringTokenizer(format,"/");
		StringTokenizer stdt = new StringTokenizer(date,separator);
		while(stfmt.hasMoreTokens()&& stdt.hasMoreTokens()){
			token= stfmt.nextToken();
			if (token.equalsIgnoreCase("D")){
				day=stdt.nextToken();
			}else if (token.equalsIgnoreCase("M")){
				month=stdt.nextToken();
			}else
				year=stdt.nextToken();
		}
		return new String[]{day,month,year};
	}

	public static java.sql.Date getDateAsSentFromBrowser(String value) {
		/* This is just a fixed format we use for historical reasons.
		 * Would make more sense to change the javascript and this to both
		 * use yyyy-mm-dd.
		 */
		return getLocaleDate(Locale.UK, value);
	}
	
	// validate a date string according to UK D/M/Y format, our internal standard
	public static boolean isValidDate(String value) {
		try {
			SimpleDateFormat shortFormat = (SimpleDateFormat)
				DateFormat.getDateInstance(DateFormat.SHORT, Locale.UK);
			shortFormat.setLenient(false);
			shortFormat.parse(value);
			return true;
		}
		
		catch (java.text.ParseException e) {
			return false;
		}
	} 

	public static java.sql.Date getLocaleDate(Locale locale,String value) {
		if (locale!=null && value!=null && !value.equals("")) {
			try{
				SimpleDateFormat shortFormat = (SimpleDateFormat)
					DateFormat.getDateInstance(DateFormat.SHORT, locale);
				shortFormat.setLenient(false);
				String userPattern = shortFormat.toPattern();
				String dbDate = convertUserToDbFmt(value, userPattern);
				return java.sql.Date.valueOf(dbDate);
			} catch (RuntimeException alreadyRuntime) {
				throw alreadyRuntime;
			} catch (Exception e) {
				throw new FrameworkRuntimeException(e);
			}
		} else {
			return null;
		}
	}

	public static String[] getDayMonthYearDbFrmt(String date, String format){
		String day="";
		String month="";
		String year="";
		String token;
		StringTokenizer formatTokenizer = new StringTokenizer(format,"-");
		StringTokenizer dateTokenizer = new StringTokenizer(date,"-");
		while (formatTokenizer.hasMoreTokens()&& dateTokenizer.hasMoreTokens()) {
			token= formatTokenizer.nextToken();
			if (token.equalsIgnoreCase("D")) {
				day = dateTokenizer.nextToken();
			} else if (token.equalsIgnoreCase("M")) {
				month = dateTokenizer.nextToken();
			} else {
				year = dateTokenizer.nextToken();
			}
		}
		return new String[]{day,month,year};
	}

	public static String getMFIFormat(){
		//TODO change this to pick from app config
		return "dd/mm/yy";
	}

	public static String getMFIShortFormat(){
		return convertToDateTagFormat("dd/mm/yy");
	}

	public static String convertToDateTagFormat(String pattern){
		char chArray[]=pattern.toCharArray();
		StringBuilder fmt = new StringBuilder();
		boolean d=false;
		boolean m=false;
		boolean y=false;
		for (int i=0; i<chArray.length;i++){
			if ((chArray[i]=='d' ||chArray[i]=='D')&& !d){
				fmt.append("D/"); d = true;
			}
			else if ((chArray[i]=='m' ||chArray[i]=='M') && !m){
				fmt.append("M/"); m = true;
			}
			else if ((chArray[i]=='y' ||chArray[i]=='Y') && !y){
				fmt.append("Y/"); y = true;
			}
		}

		return fmt.substring(0,fmt.length()-1);
	}

	public static String getSeparator(String pattern){
		char chArray[]=pattern.toCharArray();
		for (int i=0; i<chArray.length;i++){
			if (chArray[i]!='d' && chArray[i]!='D' &&
					chArray[i]!='m' && chArray[i]!='M' && chArray[i]!='y'  &&chArray[i]!='Y') {
				return 	String.valueOf(chArray[i]);
			}
		}
		return "";
	}

	public static String createDateString(String day,String month,String year,String format){
		StringTokenizer stfmt = new StringTokenizer(format,"/");
		String token;
		StringBuilder dt =new StringBuilder();
		while(stfmt.hasMoreTokens()){
			token=stfmt.nextToken();
			if (token.equals("D")){
				dt.append(day+"/");
			}else if (token.equals("M")){
				dt.append(month+"/");
			}else
				dt.append(year+"/");
		}

		return dt.deleteCharAt((dt.length()-1)).toString();
	}

	public static java.sql.Date getSQLDate(String date) throws ApplicationException {
		//TODO change this
		String format="yyyy/MM/dd";
		try {
			if(date != null || !date.equals("")){
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(sdf.parse(date));
				// System.out.println("Date in Helper"+new java.sql.Date(calendar.getTime().getTime()));
				return new java.sql.Date(calendar.getTime().getTime());
			} else {
				return null;
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
	}

	public static int DateDiffInYears(java.sql.Date fromDate) {
		Calendar fromDateCal = new GregorianCalendar();

	    fromDateCal.setTime(fromDate);

	    // Create a calendar object with today's date
	    Calendar today = new GregorianCalendar();
	    //Calendar today = Calendar.getInstance();
	    // Get age based on year
	    int age = today.get(Calendar.YEAR) - fromDateCal.get(Calendar.YEAR);
	    int monthDiff = (today.get(Calendar.MONTH)+1) - (fromDateCal.get(Calendar.MONTH)+1);
	    int dayDiff = today.get(Calendar.DAY_OF_MONTH) - fromDateCal.get(Calendar.DAY_OF_MONTH);
	    // If this year's birthday has not happened yet, subtract one from age
	    if ( monthDiff < 0) {
	        age--;
	    }
	    else if (monthDiff == 0){
	    	if (dayDiff < 0) {
	    		age--;
	    	}
	    }
	    return age;
	}

	public static String getDBtoUserFormatString(
		java.util.Date dbDate, Locale userLocale) {
		SimpleDateFormat format = (SimpleDateFormat)DateFormat.getDateInstance(
			DateFormat.MEDIUM, userLocale);
		return format.format(dbDate);
	}
	
	public static String getDBtoUserFormatShortString(
		java.util.Date dbDate, Locale userLocale) {
		SimpleDateFormat format = (SimpleDateFormat)DateFormat.getDateInstance(
			DateFormat.SHORT, userLocale);
		return format.format(dbDate);
	}

	/**
	 * This method is based on the system's time zone, not, say,
	 * the time zone where the user is.  That might be
	 * dubious.
	 */
	public static String toDatabaseFormat(java.util.Date date) {
		DateFormat format = new SimpleDateFormat(dbFormat);
//		format.setTimeZone(TimeZone.getTimeZone("GMT+0530"));
		return format.format(date);
	}

}
