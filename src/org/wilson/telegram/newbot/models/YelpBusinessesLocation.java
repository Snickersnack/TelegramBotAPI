package org.wilson.telegram.newbot.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Model for the incoming json request made by the YelpService
 * 
 */


@JsonIgnoreProperties(ignoreUnknown = true)
//Currently not mapped properly, won't return values (null)

public class YelpBusinessesLocation {

	private String city;
	private List<String> displayAddress;
	
	public YelpBusinessesLocation(){}

	public String getCity() {
		return city;
	}
	@JsonProperty("city")
	public void setCity(String city) {
		this.city = city;
	}

	public List<String> getDisplayAddress() {
		return displayAddress;
	}
	@JsonProperty("display_address")
	public void setDisplayAddress(List<String> displayAddress) {
		this.displayAddress = displayAddress;
	}
	
	
}
