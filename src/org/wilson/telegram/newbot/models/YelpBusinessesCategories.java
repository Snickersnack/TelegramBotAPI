package org.wilson.telegram.newbot.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Model for the incoming json request made by the YelpService
 * 
 */


@JsonIgnoreProperties(ignoreUnknown = true)

public class YelpBusinessesCategories {

	private List<List<String>> categories;
	
	public YelpBusinessesCategories(){}
	public List<List<String>> getCategories(){
		return categories;
	}
	@JsonProperty("categories")
	public void setCategories(List<List<String>> categories){
		this.categories = categories;
	}
}
