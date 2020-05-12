/**
 * 
 */
package com.hackerrank.weather.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * @author kartheepansivaguru
 *
 */
@Entity
@Table(name = "TEMPERATURE_DETAILS")
public class TemperatureDetails {
	 @Override
	public String toString() {
		return temperatureValue.toString();
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((temperatureDetailsID == null) ? 0 : temperatureDetailsID.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TemperatureDetails other = (TemperatureDetails) obj;
		if (temperatureDetailsID == null) {
			if (other.temperatureDetailsID != null)
				return false;
		} else if (!temperatureDetailsID.equals(other.temperatureDetailsID))
			return false;
		
		
		return true;
	}

/*
 * angular js date api,crud application in spring,,,hateos global exception ,,request param,,,,,,,,,,,,,array field one to many relationship locations
 */
	 @ManyToOne(cascade = CascadeType.ALL)
	 @JoinColumn(name = "ID")
	 private Weather weather;
	 
	  public Weather getWeather() {
		return weather;
	}


	public void setWeather(Weather weather) {
		this.weather = weather;
	}


	public Long getTemperatureDetailsID() {
		return temperatureDetailsID;
	}


	public void setTemperatureDetailsID(Long temperatureDetailsID) {
		this.temperatureDetailsID = temperatureDetailsID;
	}

	@Id
	  @GeneratedValue
	  @Column(name = "TEMPERATURE_DETAILS_ID")
	  private Long temperatureDetailsID;
	 
	  
	  @ManyToOne(cascade = CascadeType.ALL)
	  @JoinColumn(name = "TEMPERATURE_ID")  
	  private TemperatureValue temperatureValue;


	public TemperatureValue getTemperatureValue() {
		return temperatureValue;
	}


	public void setTemperatureValue(TemperatureValue temperatureValue) {
		this.temperatureValue = temperatureValue;
	}
}
