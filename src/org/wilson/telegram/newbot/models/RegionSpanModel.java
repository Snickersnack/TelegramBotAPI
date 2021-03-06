package org.wilson.telegram.newbot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Model for the incoming json request made by the YelpService
 * 
 */


@JsonIgnoreProperties(ignoreUnknown = true)

public class RegionSpanModel {
	private double latitudeDelta;
	private double longitudeDelta;
	public RegionSpanModel(){}
	public double getLatitudeDelta() {
		return latitudeDelta;
	}
	@JsonProperty("latitude_delta")
	public void setLatitudeDelta(double latitudeDelta) {
		this.latitudeDelta = latitudeDelta;
	}
	public double getLongitudeDelta() {
		return longitudeDelta;
	}
	@JsonProperty("longitude_delta")
	public void setLongitudeDelta(double longitudeDelta) {
		this.longitudeDelta = longitudeDelta;
	}
	
}
