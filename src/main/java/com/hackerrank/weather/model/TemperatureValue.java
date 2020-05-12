/**
 * 
 */
package com.hackerrank.weather.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author kartheepansivaguru
 *
 */
@Entity
@Table(name = "TEMPERATURE_VALUE")
public class TemperatureValue {
	  @Override
	public String toString() {
		  return temperature != null? ""+temperature : ""+0;}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((temperature == null) ? 0 : temperature.hashCode());
		result = prime * result + ((temperatureValueID == null) ? 0 : temperatureValueID.hashCode());
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
		TemperatureValue other = (TemperatureValue) obj;
		if (temperature == null) {
			if (other.temperature != null)
				return false;
		} else if (!temperature.equals(other.temperature))
			return false;
		if (temperatureValueID == null) {
			if (other.temperatureValueID != null)
				return false;
		} else if (!temperatureValueID.equals(other.temperatureValueID))
			return false;
		return true;
	}
	  @OneToMany(fetch = FetchType.EAGER, mappedBy = "temperatureValue", cascade = CascadeType.ALL)
	    private Set<TemperatureDetails> temperatureDetails;

	  @Id
	  @GeneratedValue
	  @Column(name = "TEMPERATURE_ID")
	  private Long temperatureValueID;
	  
	  @Column(name = "TEMPERATURE")
	  private Float temperature;

	public Float getTemperature() {
		return temperature;
	}

	public void setTemperature(Float temperature) {
		this.temperature = temperature;
	}
}
