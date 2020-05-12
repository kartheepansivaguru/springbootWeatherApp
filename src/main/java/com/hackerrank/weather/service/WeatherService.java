/**
 * 
 */
package com.hackerrank.weather.service;

import java.util.List;

import com.hackerrank.weather.model.Weather;

/**
 * @author kartheepansivaguru
 *
 */
public interface WeatherService {
	public void loadJsonDb();
	public void erase(String date);
	public void erase(String startDate,String endDate, Long latitude, Long longitude);
	public void add(Weather weather) throws CustomException;
	public void eraseAll();
	public void erase(String startdate, String endDate);
	public void erase(String startDate, String endDate, Long latitude);
	List<Weather> weather(String dateInString, Long latitude, Long longitude) throws CustomException;
	
}
