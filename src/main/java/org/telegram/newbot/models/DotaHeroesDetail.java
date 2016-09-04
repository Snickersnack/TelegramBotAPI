package org.telegram.newbot.models;

<<<<<<< HEAD
=======
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
>>>>>>> 4313ba29a2a9b55d5052e7787c2fd41b911725c8
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
