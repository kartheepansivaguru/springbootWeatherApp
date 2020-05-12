package com.hackerrank.weather.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hackerrank.weather.model.TemperatureDetails;

public interface TemperatureDetailsRepository extends JpaRepository<TemperatureDetails,Long> {
	@Modifying
	@Query("delete from TemperatureDetails u where u.weather.id in ?1")
	void deleteByWeatherId(List<Long> weatherIdList);
}
