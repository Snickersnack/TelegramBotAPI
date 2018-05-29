package org.wilson.telegram.newbot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

public class OpenDotaPlayer {
	private Integer account_id;
	private Integer assists;
	private Integer camps_stacked;
	private Integer courier_kills;
	private Integer deaths;
	private Integer gold_per_min;
	private Integer hero_damage;
	private Integer hero_id;
	private boolean is_roaming;
	private Integer kills;
	private Integer lane; //the lane number
	private Integer lane_role; //role relative to the dire/radiant side
	private Integer observer_uses;
	private Integer party_id;
	private String personaName;
	private Integer roshan_kills;
	private Integer sentry_uses;
	private Integer tower_kills;
	private Integer win;
	
	public OpenDotaPlayer(){}

	public Integer getAccount_id() {
		return account_id;
	}

	public Integer getAssists() {
		return assists;
	}

	public Integer getCamps_stacked() {
		return camps_stacked;
	}

	public Integer getCourier_kills() {
		return courier_kills;
	}

	public Integer getDeaths() {
		return deaths;
	}

	public Integer getHero_id() {
		return hero_id;
	}

	public boolean isIs_roaming() {
		return is_roaming;
	}

	public Integer getKills() {
		return kills;
	}

	public Integer getLane() {
		return lane;
	}

	public Integer getLane_role() {
		return lane_role;
	}

	public Integer getObserver_uses() {
		return observer_uses;
	}

	public Integer getParty_id() {
		return party_id;
	}
	public String getPersonaName() {
		return personaName;
	}

	public Integer getRoshan_kills() {
		return roshan_kills;
	}

	public Integer getSentry_uses() {
		return sentry_uses;
	}

	public Integer getWin() {
		return win;
	}

	public Integer getGold_per_min() {
		return gold_per_min;
	}



	public Integer getHero_damage() {
		return hero_damage;
	}



	public Integer getTower_kills() {
		return tower_kills;
	}



	@JsonProperty("account_id")
	public void setAccount_id(Integer account_id) {
		this.account_id = account_id;
	}
	@JsonProperty("assists")
	public void setAssists(Integer assists) {
		this.assists = assists;
	}
	@JsonProperty("camps_stacked")
	public void setCamps_stacked(Integer camps_stacked) {
		this.camps_stacked = camps_stacked;
	}
	@JsonProperty("courier_kills")
	public void setCourier_kills(Integer courier_kills) {
		this.courier_kills = courier_kills;
	}
	@JsonProperty("deaths")
	public void setDeaths(Integer deaths) {
		this.deaths = deaths;
	}
	@JsonProperty("hero_id")
	public void setHero_id(Integer hero_id) {
		this.hero_id = hero_id;
	}
	@JsonProperty("hero_damage")
	public void setHero_damage(Integer hero_damage) {
		this.hero_damage = hero_damage;
	}
	@JsonProperty("is_roaming")
	public void setIs_roaming(boolean is_roaming) {
		this.is_roaming = is_roaming;
	}
	@JsonProperty("kills")
	public void setKills(Integer kills) {
		this.kills = kills;
	}
	@JsonProperty("lane")
	public void setLane(Integer lane) {
		this.lane = lane;
	}
	@JsonProperty("lane_role")
	public void setLane_role(Integer lane_role) {
		this.lane_role = lane_role;
	}
	@JsonProperty("observer_uses")
	public void setObserver_uses(Integer observer_uses) {
		this.observer_uses = observer_uses;
	}
	@JsonProperty("party_id")
	public void setParty_id(Integer party_id) {
		this.party_id = party_id;
	}
	@JsonProperty("personaname")
	public void setPersonaName(String persona_name) {
		this.personaName = persona_name;
	}

	@JsonProperty("roshan_kills")
	public void setRoshan_kills(Integer roshan_kills) {
		this.roshan_kills = roshan_kills;
	}
	@JsonProperty("sentry_uses")
	public void setSentry_uses(Integer sentry_uses) {
		this.sentry_uses = sentry_uses;
	}
	@JsonProperty("gold_per_min")
	public void setGold_per_min(Integer gold_per_min) {
		this.gold_per_min = gold_per_min;
	}
	@JsonProperty("tower_kills")
	public void setTower_kills(Integer tower_kills) {
		this.tower_kills = tower_kills;
	}
	@JsonProperty("win")
	public void setWin(Integer win) {
		this.win = win;
	}
	
	

}
