package org.telegram.newbot;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
	
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
