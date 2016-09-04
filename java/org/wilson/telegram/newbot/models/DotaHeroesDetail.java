package org.wilson.telegram.newbot.models;

import com.fasterxml.jackson.annotation.JsonProperty;


public class DotaHeroesDetail {

	private String name;
	private int id;
	private String localizedName;
	
	public DotaHeroesDetail(){}

	public String getName() {
		return name;
	}
	
	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(int id) {
		this.id = id;
	}

	public String getLocalizedName() {
		return localizedName;
	}

	@JsonProperty("localized_name")
	public void setLocalizedName(String localizedName) {
		this.localizedName = localizedName;
	}
}
