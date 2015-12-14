package org.telegram.newbot.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


public class DotaHeroes {

	private DotaHeroesResultTest result;

	
	public DotaHeroes(){}
	

	public DotaHeroesResultTest getResult() {
		return result;
	}
	@JsonProperty("result")
	public void setResult(DotaHeroesResultTest result) {
		this.result = result;
	}
	
}
