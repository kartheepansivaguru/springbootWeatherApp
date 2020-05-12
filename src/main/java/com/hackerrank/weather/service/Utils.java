/**
 * 
 */
package com.hackerrank.weather.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author kartheepansivaguru
 *
 */
public class Utils {
	public static Date getDate(String dateInString) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    Date date =null;
	    try {

	         date = formatter.parse(dateInString);
	        System.out.println(date);
	        System.out.println(formatter.format(date));

	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    return date;
		
	}

}
