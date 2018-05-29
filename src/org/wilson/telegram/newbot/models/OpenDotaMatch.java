package org.wilson.telegram.newbot.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)


public class OpenDotaMatch {
	
	private List<OpenDotaPlayer> players;

	public OpenDotaMatch(){};
	
	public List<OpenDotaPlayer> getPlayers() {
		return players;
	}

	@JsonProperty("players")
	public void setPlayers(List<OpenDotaPlayer> players) {
		this.players = players;
	}
	
	
}
