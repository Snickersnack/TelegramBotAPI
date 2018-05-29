package org.wilson.telegram.newbot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class OpenDotaMatchHistory {
	private Long match_id;
	private Integer duration;
	private Long start_time;
	
	public OpenDotaMatchHistory(){}

	public Long getMatch_id() {
		return match_id;
	}

	public Integer getDuration() {
		return duration;
	}

	public Long getStart_time() {
		return start_time;
	}

	@JsonProperty("match_id")
	public void setMatch_id(Long match_id) {
		this.match_id = match_id;
	}
	@JsonProperty("duration")
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
	@JsonProperty("start_time")
	public void setStart_time(Long start_time) {
		this.start_time = start_time;
	};
	
	

}
