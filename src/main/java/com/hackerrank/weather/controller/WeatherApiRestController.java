package com.hackerrank.weather.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hackerrank.weather.model.Weather;
import com.hackerrank.weather.service.WeatherService;




@RestController
public class WeatherApiRestController {
	
	  private static final Logger LOGGER = LoggerFactory.getLogger(WeatherApiRestController.class);
	  @Autowired
	  WeatherService weatherService;
	  @CrossOrigin//(origins="http://localhost:8080")
	  @RequestMapping(value = "/erase", produces = {MediaType.APPLICATION_JSON_VALUE})
		    public ResponseEntity erase(@RequestHeader(name= "start", required = false) String startDate,
		    		@RequestHeader(name= "end", required = false) String endDate) {
		    try { 
		    	weatherService.erase(startDate, endDate);
	        } catch (Exception ex) {
	            LOGGER.error("", ex);
	            throw ex;
	        }
			 return new ResponseEntity<>("Successful deletion of records", HttpStatus.BAD_REQUEST);
	    }
	  
	  
	  @CrossOrigin//(origins="http://localhost:8080")
	  @RequestMapping(value = "/weather", produces = {MediaType.APPLICATION_JSON_VALUE})
		    public ResponseEntity weather(@RequestHeader(name= "date", required = false) String date,
		    		@RequestHeader(name= "lat", required = false) String latitude,	@RequestHeader(name= "lat", required = false) String longitude
		    		) {
		  List<Weather> weatherList = null; 
		  try { 
		    	 weatherList = weatherService.weather(date,latitude,longitude);
		    
		    } catch (Exception ex) {
	            LOGGER.error("", ex);
	            throw ex;
	        }
			if(CollectionUtils.isEmpty(weatherList))  return new ResponseEntity<>("Successful deletion of records", HttpStatus.OK);
			return null;
	    }
}
