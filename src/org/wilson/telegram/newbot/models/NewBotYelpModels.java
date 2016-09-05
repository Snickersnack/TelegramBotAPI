package org.wilson.telegram.newbot.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model for the incoming json request made by the YelpService
 * 
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewBotYelpModels {
	
	private List<YelpBusinesses> businesses;
	private int total;
//	private List<RegionModel> region;
	
	public NewBotYelpModels(){}

//If regions are needed:
//	public List<RegionModel> getRegion() {
//		return region;
//	}
//	@JsonProperty("region")
//	public void setRegion(List<RegionModel> region) {
//		this.region = region;
//	}
	public int getTotal() {
		return total;
	}
	@JsonProperty("total")
	public void setTotal(int total) {
		this.total = total;
	}
	public List<YelpBusinesses> getBusinesses() {
		return businesses;
	}
	
	@JsonProperty("businesses")
	public void setBusinesses(List<YelpBusinesses> businesses) {
		this.businesses = businesses;
	}		
}
	