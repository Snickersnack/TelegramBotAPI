package org.wilson.telegram.newbot.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model for the incoming json request made by HeroesRequest
 * 
 */


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
