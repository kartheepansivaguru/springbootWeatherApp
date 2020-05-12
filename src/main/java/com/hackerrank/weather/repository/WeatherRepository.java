package com.hackerrank.weather.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hackerrank.weather.model.Weather;

public interface WeatherRepository extends CrudRepository<Weather,Long> {
	@Query("SELECT a FROM Weather a WHERE  a.date = :date")
	List<Weather> findAllByDate(Date date);

	@Query("DELETE FROM Weather a WHERE  a.date BETWEEN :sqlStartDate AND :sqlEndDate")
	void deleteByStartDateEndDate(Date sqlStartDate, Date sqlEndDate);
	
	@Query("select a FROM Weather a,Location b WHERE  a.location.locationId = b.locationId and a.location.lat=:latitude and a.date  BETWEEN :sqlStartDate AND :sqlEndDate")
	List<Weather> selectByStartDateEndDateAndLatitude(Date sqlStartDate, Date sqlEndDate, Long latitude);
	
	@Query("select a FROM Weather a,Location b WHERE  a.location.locationId = b.locationId and b.lat=:latitude and b.lon=:longitude")
	List<Weather> selectByLatitudeLongitude(Long latitude, Long longitude);
	@Query("select a FROM Weather a,Location b WHERE  a.location.locationId = b.locationId and b.lat=:latitude and b.lon=:longitude and a.date=:sqlDate")
	List<Weather> selectByDateAndLatitudeLongitude(Date sqlDate, Long latitude, Long longitude);
	
}
