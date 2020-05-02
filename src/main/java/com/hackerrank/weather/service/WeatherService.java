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
	public void erase(String startDate,String endDate);
	
	public List<Weather> weather(String date, String latitude, String longitude);
}
