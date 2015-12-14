package org.telegram.newbot.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DotaHeroesResponseModel {

	private DotaHeroes result;
	
	public DotaHeroesResponseModel(){}
	public DotaHeroes getResult() {
		return result;
	}
	@JsonProperty("result")

	public void setResult(DotaHeroes result) {
		this.result = result;
	}
	
	
}
