package com.hackerrank.weather.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hackerrank.weather.model.Location;

public interface LocationRepository extends JpaRepository<Location,Long> {
}
