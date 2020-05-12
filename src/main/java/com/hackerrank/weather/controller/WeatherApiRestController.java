package com.hackerrank.weather.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hackerrank.weather.model.Weather;
import com.hackerrank.weather.service.CustomException;
import com.hackerrank.weather.service.WeatherService;




@RestController
public class WeatherApiRestController {
	
	  private static final Logger LOGGER = LoggerFactory.getLogger(WeatherApiRestController.class);
	  @Autowired
	  @Qualifier("weatherServiceImpl")
	  WeatherService weatherService;
	  
	  
	/*
	 * @CrossOrigin//(origins="http://localhost:8080")
	 * 
	 * @GetMapping(value = "/erase", produces = {MediaType.APPLICATION_JSON_VALUE})
	 * public ResponseEntity erase(@RequestParam(name= "start", required = false)
	 * String date) { try { weatherService.erase(date); } catch (Exception ex) {
	 * LOGGER.error("", ex); throw ex; } return new
	 * ResponseEntity<>("Successful deletion of records", HttpStatus.BAD_REQUEST); }
	 * 
	 * 
	 * @CrossOrigin//(origins="http://localhost:8080")
	 * 
	 * @GetMapping(value = "/erase", produces = {MediaType.APPLICATION_JSON_VALUE})
	 * public ResponseEntity erase(@RequestParam(name= "start", required = false)
	 * String startdate,@RequestParam(name= "end", required = false) String endDate)
	 * { try { weatherService.erase(startdate,endDate); } catch (Exception ex) {
	 * LOGGER.error("", ex); throw ex; } return new
	 * ResponseEntity<>("Successful deletion of records", HttpStatus.BAD_REQUEST); }
	 * 
	 * @CrossOrigin//(origins="http://localhost:8080")
	 * 
	 * @GetMapping(value = "/erase", produces = {MediaType.APPLICATION_JSON_VALUE})
	 * public ResponseEntity erase() { try { weatherService.eraseAll(); } catch
	 * (Exception ex) { LOGGER.error("", ex); throw ex; } return new
	 * ResponseEntity<>("Successful deletion of records", HttpStatus.BAD_REQUEST); }
	 * 
	 * @CrossOrigin//(origins="http://localhost:8080")
	 * 
	 * @GetMapping(value = "/erase", produces = {MediaType.APPLICATION_JSON_VALUE})
	 * public ResponseEntity erase(@RequestParam(name= "start", required = false)
	 * String startDate,
	 * 
	 * @RequestParam(name= "end", required = false) String endDate,
	 * 
	 * @RequestParam(name= "lat", required = false) Long latitude ) { try {
	 * weatherService.erase(startDate, endDate,latitude); } catch (Exception ex) {
	 * LOGGER.error("", ex); throw ex; } return new
	 * ResponseEntity<>("Successful deletion of records", HttpStatus.BAD_REQUEST); }
	 */
	  @CrossOrigin//(origins="http://localhost:8080")
	  @GetMapping(value = "/erase", produces = {MediaType.APPLICATION_JSON_VALUE})
		    public ResponseEntity erase(@RequestParam(name= "start", required = false) String startDate,
		    		@RequestParam(name= "end", required = false) String endDate,
		    		@RequestParam(name= "lat", required = false) Long latitude,
		    		@RequestParam(name= "lon", required = false) Long longitude
		    		) {
		    try { 
		    	weatherService.erase(startDate, endDate,latitude,longitude);
	        } catch (Exception ex) {
	            LOGGER.error("", ex);
	            throw ex;
	        }
			 return new ResponseEntity<>("Successful deletion of records", HttpStatus.OK);
	    }
	  
	 /* @CrossOrigin//(origins="http://localhost:8080")
	  @GetMapping(value = "/weather", produces = {MediaType.APPLICATION_JSON_VALUE})
		    public ResponseEntity weather(@RequestParam(name= "date", required = false) String date) {
		  List<Weather> weatherList = null; 
		  try { 
		    	 weatherList = weatherService.weather(date,null,null);
		    
		    } catch (Exception ex) {
	            LOGGER.error("", ex);
	            throw ex;
	        }
			if(CollectionUtils.isEmpty(weatherList))  return new ResponseEntity<>("Successful deletion of records", HttpStatus.OK);
			return null;
	    }*/
	  @CrossOrigin//(origins="http://localhost:8080")
	  @GetMapping(value = "/weather", produces = {MediaType.APPLICATION_JSON_VALUE})
		    public ResponseEntity weather() {
		  List<Weather> weatherList = null; 
		  try { 
		    	 weatherList = weatherService.weather(null,null,null);
		    
		    } catch (CustomException ex) {
		    	LOGGER.error(ex.getMessage(), ex.getCode());
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
			if(!CollectionUtils.isEmpty(weatherList))  return new ResponseEntity<List>(weatherList, HttpStatus.OK);
			return null;
	    }
		
	  
	@CrossOrigin // (origins="http://localhost:8080")
	@PostMapping("/weather")
	public ResponseEntity addWeather(@RequestBody Weather weather) {
		try {
			weatherService.add(weather);
		} catch (CustomException ex) {
			LOGGER.error(ex.getMessage(), ex.getCode());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Successful creation of the record", HttpStatus.CREATED);
	}
	  
	  
}
