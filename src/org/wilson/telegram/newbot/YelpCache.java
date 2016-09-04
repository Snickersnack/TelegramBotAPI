package org.wilson.telegram.newbot;

import java.util.ArrayList;
import java.util.List;

import org.wilson.telegram.newbot.models.YelpBusinesses;

/**
 * Singleton cache for the Yelp service
 * Includes settings to toggle yelp link displays
 */

public class YelpCache {

	private static YelpCache yelpCache = new YelpCache();
	private List<YelpBusinesses> yelpList;
	private List<YelpBusinesses> currentYelpList;
	private int yelpPageState = 0;
	private boolean disableYelpDisplay = false;

	private YelpCache() {
	}

	public static YelpCache getInstance() {
		return yelpCache;
	}

	public void setYelpList(List<YelpBusinesses> yelpList) {
		this.yelpList = yelpList;
	}

	public void init() {
		this.yelpList = new ArrayList<YelpBusinesses>();
		this.currentYelpList = new ArrayList<YelpBusinesses>();

	}

	public boolean getYelpDisplay(){
		return disableYelpDisplay;
	}
	public void disableYelpDisplay(){
		this.disableYelpDisplay = true;
	}
	
	public void enableYelpDisplay(){
		this.disableYelpDisplay = false;
	}
	public void clearYelpCache() {
		if (yelpList.size() > 0) {
			this.yelpList.clear();
			yelpPageState = 0;
		}
	}

	public void setYelpPageState(int state) {
		if (state > 3) {
			throw new IllegalArgumentException("State must be less than 4");
		} else {
			this.yelpPageState = state;
		}

	}

	public int getYelpPageState() {
		return yelpPageState;
	}

	public List<YelpBusinesses> getYelpList() {
		return yelpList;
	}

	public List<YelpBusinesses> getCurrentYelpList() {
		switch (this.yelpPageState) {
		case 0:
			currentYelpList = new ArrayList<YelpBusinesses>();
			break;
		case 1:
			if (yelpList.size() == 1 || yelpList.size() == 2) {
				currentYelpList = yelpList.subList(0, yelpList.size());
				break;
			} else {
				currentYelpList = yelpList.subList(0, 3);
				break;
			}
		case 2:
			if (yelpList.size() == 4 || yelpList.size() == 5) {
				currentYelpList = yelpList.subList(3, yelpList.size());
				break;
			} else {
				currentYelpList = yelpList.subList(3, 6);
				break;
			}
		case 3:
			if (yelpList.size() == 7 || yelpList.size() == 8) {
				currentYelpList = yelpList.subList(6, yelpList.size());
				break;
			} else {
				currentYelpList = yelpList.subList(6, 9);
				break;
			}

		}
		return currentYelpList;

	}
}