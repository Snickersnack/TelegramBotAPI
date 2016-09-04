package org.telegram.newbot.models;

<<<<<<< HEAD
=======
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
>>>>>>> 4313ba29a2a9b55d5052e7787c2fd41b911725c8
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
