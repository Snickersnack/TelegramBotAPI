package org.wilson.telegram.newbot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class RegionModel {
	private RegionSpanModel span;
	private RegionCenterModel center;
	
	public RegionModel(){}
	
	public RegionSpanModel getSpan() {
		return span;
	}
	public void setSpan(RegionSpanModel span) {
		this.span = span;
	}
	public RegionCenterModel getCenter() {
		return center;
	}
	public void setCenter(RegionCenterModel center) {
		this.center = center;
	}
	
}
