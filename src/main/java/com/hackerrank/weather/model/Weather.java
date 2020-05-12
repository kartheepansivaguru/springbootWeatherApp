package com.hackerrank.weather.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.sql.Date;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.util.CollectionUtils;


@Entity
@Table(name = "WEATHER")
public class Weather {
	public List<TemperatureDetails> getTemperatureDetails() {
		return temperatureDetailsList;
	}

	public void setTemperatureDetails(List<TemperatureDetails> temperatureDetails) {
		this.temperatureDetailsList = temperatureDetails;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Weather other = (Weather) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		setFloatArray();
		return "{\"id\":" + id + ",\"date\":" +"\""+ date +"\""+ ",\"location\":" + location + ",\"temperature\":"
				+ Arrays.toString(temperature) + "}";
	}

	private void setFloatArray() {
		if(CollectionUtils.isEmpty(temperatureDetailsList)) {
			temperature = new Float[temperatureDetailsList.size()];
			int i =0;
			for(TemperatureDetails temp:temperatureDetailsList) {
				temperature[i] = temp.getTemperatureValue().getTemperature();
			}
		}
	}
	
	public void setTemperatureDetails() {
		if(temperature!=null && temperature.length>0) {
			
			for(int i =0;i< temperature.length;i++) {
				TemperatureValue temperatureValue = new TemperatureValue();
				temperatureValue.setTemperature(temperature[i]);
				TemperatureDetails temperatureDetails = new TemperatureDetails();
				
				temperatureDetails.setTemperatureValue(temperatureValue);
				temperatureDetailsList.add(temperatureDetails);
			}
			
		
		}
	}

	@Id
	//@GeneratedValue
	@Column(name = "ID")
    private Long id;
	@Column(name = "DATE")
    private Date date;
    
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "LOCATION_ID", referencedColumnName = "LOCATION_ID", insertable = false, updatable = false)
    private Location location;
  
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "weather", cascade = CascadeType.MERGE)
    private List<TemperatureDetails> temperatureDetailsList = new ArrayList<TemperatureDetails>();
    @Transient
    private Float[] temperature;

    public Weather() {
    	setTemperatureDetails();
    }

    public Weather(Long id, Date date, Location location, Float[] temperature) {
        this.id = id;
        this.date = date;
        this.location = location;
        this.temperature = temperature;
    }

    public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Float[] getTemperature() {
        return temperature;
    }

    public void setTemperature(Float[] temperature) {
        this.temperature = temperature;
    }
    
    public String getLatitudeLongitude() {
    	if(location!=null)
    		 return location.getLat() + "-" +location.getLon();
    	return null;
    }
 
}
