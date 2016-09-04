package org.wilson.telegram.newbot.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model for the incoming json request made by HeroesRequest
 * 
 */

public class DotaHeroesResultTest {

	private List<DotaHeroesDetail> dotaHeroes;
	private int status;
	private int count;
	
	public DotaHeroesResultTest(){
		
	}
	public List<DotaHeroesDetail> getDotaHeroes() {
		return dotaHeroes;
	}
	
	@JsonProperty("heroes")
	public void setDotaHeroes(List<DotaHeroesDetail> dotaHeroes) {
		this.dotaHeroes = dotaHeroes;
	}

	public int getStatus() {
		return status;
	}

	@JsonProperty("status")
	public void setStatus(int status) {
		this.status = status;
	}

	public int getCount() {
		return count;
	}
	@JsonProperty("count")
	public void setCount(int count) {
		this.count = count;
	}
}
