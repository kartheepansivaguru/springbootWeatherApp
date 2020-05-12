package com.hackerrank.weather.service;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackerrank.weather.model.Location;
import com.hackerrank.weather.model.Weather;
import com.hackerrank.weather.repository.TemperatureDetailsRepository;
import com.hackerrank.weather.repository.WeatherRepository;
@Service
public class WeatherServiceImpl implements WeatherService{
	  @Autowired
	  private WeatherRepository weatherRepository;
	  
	  @Autowired
	  private TemperatureDetailsRepository temperatureDetailsRepository;
	  Function<Weather, Long> weatherToWeatherId = 
			    new Function<Weather, Long>() { 
			        public Long apply(Weather i) { return i.getId(); }
			    };
private static final String filename = "http00.json";
private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
List<Weather> weatherList = new LinkedList<Weather>();
/*
 * {
	"id": 10,
	"date": "1985-01-02",
	"location": {
		"lat": 32.5,
		"lon": -93.6667,
		"city": "Shreveport",
		"state": "Louisiana"
	},
	"temperature": [53.9, 53.9, 53.9, 53.9, 53.9, 53.9, 39.5, 39.5, 40.2, 43.1, 45.9, 48.6, 50.5, 52.1, 53.4, 53.9, 53.7, 52.0, 49.1, 47.0, 45.8, 44.8, 44.2, 43.4]
}
 */
private Date getDate(String dateInString) {
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

 

  
	@Override
	public void loadJsonDb() {
	 final List<String> jsonStrings = new ArrayList<>();

         ClassPathResource jsonResource = new ClassPathResource("" + filename);
         try (InputStream inputStream = jsonResource.getInputStream()) {
             new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                     .lines()
                     .collect(toList())
                     .forEach(jsonString -> jsonStrings.add(jsonString));
         } catch (IOException ex) {
             System.out.println(String.join("\n", Stream.of(ex.getStackTrace())
             .map(trace -> trace.toString())
             .collect(toList())));

             throw new Error(ex.toString());
         }
         if (!jsonStrings.isEmpty()) {
             jsonStrings.forEach(jsonString -> {
                 
                 try {
                     JsonNode jsonObject = OBJECT_MAPPER.readTree(jsonString);

                     Long id = jsonObject.get("id").asLong();
                     String dateString = jsonObject.get("date").asText();
                     JsonNode locationNode = jsonObject.get("location");
                     float latitude = BigDecimal.valueOf(locationNode.get("lat").asDouble()).floatValue();
                     float longitude = BigDecimal.valueOf(locationNode.get("lon").asDouble()).floatValue();
                     String cityName = locationNode.get("city").toString();
                     String stateName = locationNode.get("state").asText();
                     Location location = new Location( cityName,  stateName,  latitude,  longitude);
                     JsonNode temperatureNode = jsonObject.get("temperature");
                     List<Float> tempArray = new ArrayList<Float>();
                    temperatureNode.forEach(item->{
                    	   float temp = BigDecimal.valueOf(item.asDouble()).floatValue();
                           tempArray.add(temp);
                    });;
                    Float[] temperature = (Float[]) tempArray.toArray();
                    Weather weather = new Weather(id, new java.sql.Date(this.getDate(dateString).getTime()),  location, temperature);
                    weatherList.add(weather);
                 } catch (IOException ex) {
                     System.out.println(String.join("\n", Stream.of(ex.getStackTrace())
                     .map(trace -> trace.toString())
                     .collect(toList())));

                     throw new Error(ex.toString());
                 }
                 catch (Exception ex) {
                     System.out.println(String.join("\n", Stream.of(ex.getStackTrace())
                     .map(trace -> trace.toString())
                     .collect(toList())));

                     throw new Error(ex.toString());
                 }
             });
         }
	}
private Map<Date,List<Weather>> getWeatherMapBasedOnDate(List<Weather> dtoList){
	Map<Date, List<Weather>> map = dtoList.stream().collect(Collectors.groupingBy(Weather::getDate));
	return map;
}


private Map<String,List<Weather>> getWeatherMapBasedOnLatitudelongitude(List<Weather> dtoList){
	Map<String, List<Weather>> map = dtoList.stream().collect(Collectors.groupingBy(Weather::getLatitudeLongitude));
	return map;
}
	@Override
	public void erase(String startDate, String endDate,Long latitude,Long longitude) {
		Map<Date,List<Weather>> weatherMapBasedOnDate = getWeatherMapBasedOnDate(weatherList);
		if(!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
			this.eraseAll();
		}
		else if(StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
			this.erase(endDate);
		}
		else if(!StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)) {
			 if(latitude!=null && longitude!=null){
				 this.erase(startDate,latitude,longitude);
			 }
			this.erase(startDate);
		}
		else if(!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
			this.erase(startDate,endDate);
		}
		else if(!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate) && latitude!=null && longitude!=null) {
			this.erase(startDate,endDate,latitude,longitude);
		}
		else if(latitude!=null && longitude!=null ) {
			this.erase(latitude,longitude);
		}
		
	}

	private void erase(Long latitude, Long longitude) {
		// TODO Auto-generated method stub
List<Weather> weatherlist = this.weatherRepository.selectByLatitudeLongitude(latitude,longitude);
		
		List<Long> weatherIdList = 
				weatherlist.parallelStream().map(weatherToWeatherId).filter(w -> w != null)
				.collect(Collectors.<Long>toList());
				
				
		this.temperatureDetailsRepository.deleteByWeatherId(weatherIdList);
	}




	private void erase(String dateInString, Long latitude, Long longitude) {
		Date utilDate = Utils.getDate(dateInString);
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
List<Weather> weatherlist = this.weatherRepository.selectByDateAndLatitudeLongitude(sqlDate,latitude,longitude);
		
		List<Long> weatherIdList = 
				weatherlist.parallelStream().map(weatherToWeatherId).filter(w -> w != null)
				.collect(Collectors.<Long>toList());
				
				
		this.temperatureDetailsRepository.deleteByWeatherId(weatherIdList);
		
	}




	@Override
	public List<Weather> weather(String dateInString, Long latitude,Long longitude) throws CustomException{
		// TODO Auto-generated method stub
		List<Weather> weatherData = null;
		if(!StringUtils.isEmpty(dateInString)) {
			Date utilDate = Utils.getDate(dateInString);
			java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
			if (!StringUtils.isEmpty(latitude) && !StringUtils.isEmpty(longitude)) {
				weatherData = weatherRepository.selectByDateAndLatitudeLongitude(sqlDate, latitude, longitude);
			}else {
				weatherData = this.weatherRepository.findAllByDate(sqlDate);
			}
		}else if(!StringUtils.isEmpty(latitude) && !StringUtils.isEmpty(longitude)) {
			weatherData = this.weatherRepository.selectByLatitudeLongitude(latitude, longitude);
		}else {
			weatherData = (List<Weather>) this.weatherRepository.findAll();
		}
		if(!CollectionUtils.isEmpty(weatherData)) {
			 List<Weather> sortedList = weatherData.stream()
						.sorted(Comparator.comparingLong(Weather::getId))
						.collect(Collectors.toList());
			 weatherData = sortedList;
			 }else {
				 throw new CustomException("Record Does not exist","404");
			 }
		return weatherData;
	}

	




	@Override
	public void add(Weather weather) throws CustomException{
		 weather.setTemperatureDetails();
		 Long id = weather.getId();
		 Optional<Weather> weatherWithId = this.weatherRepository.findById(id);
		 if(weatherWithId.isPresent())
			 throw new CustomException("ID already exists","400");
		 else 	 
			 this.weatherRepository.save(weather);
	}

	@Override
	public void erase(String dateInString) {
		Date utilDate = Utils.getDate(dateInString);
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		List<Weather> list = this.weatherRepository.findAllByDate(sqlDate);
		this.weatherRepository.deleteAll(list);
	}

	@Override
	public void eraseAll() {
		this.weatherRepository.deleteAll();
	}




	@Override
	public void erase(String startdate, String endDate) {
		Date utilStartDate = Utils.getDate(startdate);
		java.sql.Date sqlStartDate = new java.sql.Date(utilStartDate.getTime());
		Date utilEndDate = Utils.getDate(endDate);
		java.sql.Date sqlEndDate = new java.sql.Date(utilEndDate.getTime());
		this.weatherRepository.deleteByStartDateEndDate(sqlStartDate,sqlEndDate);	
	}




	@Override
	public void erase(String startDate, String endDate, Long latitude) {
		Date utilStartDate = Utils.getDate(startDate);
		java.sql.Date sqlStartDate = new java.sql.Date(utilStartDate.getTime());
		Date utilEndDate = Utils.getDate(endDate);
		java.sql.Date sqlEndDate = new java.sql.Date(utilEndDate.getTime());
		List<Weather> weatherlist = this.weatherRepository.selectByStartDateEndDateAndLatitude(sqlStartDate,sqlEndDate,latitude);
		
		List<Long> weatherIdList = 
				weatherlist.parallelStream().map(weatherToWeatherId).filter(w -> w != null)
				.collect(Collectors.<Long>toList());
				
				
		this.temperatureDetailsRepository.deleteByWeatherId(weatherIdList);
		
	}

}
