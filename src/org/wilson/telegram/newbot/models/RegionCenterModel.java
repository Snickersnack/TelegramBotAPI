package org.wilson.telegram.newbot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Model for the incoming json request made by the YelpService
 * 
 */

@JsonIgnoreProperties(ignoreUnknown = true)



public class RegionCenterModel {

	private double latitude;
	private double longitude;
	public RegionCenterModel(){}
	public double getLatitude() {
		return latitude;
	}
	
	@JsonProperty("latitude")
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	@JsonProperty("longitude")
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
