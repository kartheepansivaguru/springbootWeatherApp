package com.hackerrank.weather.model;

import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "LOCATION",uniqueConstraints = { @UniqueConstraint( columnNames = { "LATITUDE", "LONGITUDE" } ) } )
public class Location {
    @Override
	public int hashCode() {
		return Objects.hash(locationId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (locationId == null) {
			if (other.locationId != null)
				return false;
		} else if (!locationId.equals(other.locationId))
			return false;
				
		return true;
	}

	@Override
	public String toString() {
		return "{\"lat\":" + lat
				
				+ ", \"lon\":" + lon
				
				+ ", \"city\":" +"\""+ city
				
				+ "\", \"state\":" + "\""+ state +"\""+ "}";
	}
	
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "location", cascade = CascadeType.ALL)
    private Set<Weather> weatherList;
	
	
	@Column(name = "CITY")
	private String city;
	@Column(name = "STATE")
    private String state;
    @Column(name = "LATITUDE")
    private Float lat;
    @Column(name = "LONGITUDE")
    private Float lon;
    @Id
    @GeneratedValue
	@Column(name = "LOCATION_ID")
    private Integer locationId;
    public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public Location() {
    }

    public Location(String city, String state, Float lat, Float lon) {
        this.city = city;
        this.state = state;
        this.lat = lat;
        this.lon = lon;
        
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float latitude) {
        this.lat = latitude;
    }

    public Float getLon() {
        return lon;
    }

    public void setLon(Float longitude) {
        this.lon = longitude;
    }
}
