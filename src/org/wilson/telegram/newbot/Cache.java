package org.wilson.telegram.newbot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.wilson.telegram.SteamIds;
import org.wilson.telegram.newbot.models.YelpBusinesses;

/**
 * Singleton cache for the Yelp service
 * Includes settings to toggle yelp link displays
 */

public class Cache {

	private static Cache cache = new Cache();
	private List<YelpBusinesses> yelpList;
	private List<YelpBusinesses> currentYelpList;
	private int yelpPageState = 0;
	private boolean disableYelpDisplay = false;
	private HashSet<String> players;
	private HashMap<String, String> steamIds;

	


	private Cache() {
		this.yelpList = new ArrayList<YelpBusinesses>();
		this.currentYelpList = new ArrayList<YelpBusinesses>();
		this.players = new HashSet<String>();
		this.steamIds = new HashMap<String, String>();


	}

	public static Cache getInstance() {
		return cache;
	}

	
	public void setYelpList(List<YelpBusinesses> yelpList) {
		this.yelpList = yelpList;
	}

	public void init() {
		steamIds.put("riki", "76561198000302345");
		steamIds.put("jdea", "76561197999636832");
		steamIds.put("wilson", "76561198021016937");
		steamIds.put("ray", "76561198019034863");
		steamIds.put("dfoo", "76561197961230323");
		steamIds.put("calvin", "76561197998512839");
		steamIds.put("elton", "76561197993274771");
		steamIds.put("justin", "76561197991697481");
		steamIds.put("chris", "76561198025272852");
		steamIds.put("jeff", "76561198051626437");
		steamIds.put("alan", "76561197968492026");
		steamIds.put("lorock", "76561198031531434");
		steamIds.put("hanson", "76561197998833583");
		steamIds.put("alvin", "76561198009924427");
		steamIds.put("nicole", "76561198250542926");
		steamIds.put("shirley", "76561197998883829");

		for(Map.Entry<String, String> item : steamIds.entrySet()){
			players.add(item.getKey().toLowerCase());
		}
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



	public HashSet<String> getPlayers() {
		return players;
	}

	public void setPlayers(HashSet<String> players) {
		this.players = players;
	}

	public HashMap<String, String> getSteamIds() {
		return steamIds;
	}

	public void setSteamIds(HashMap<String, String> steamIds) {
		this.steamIds = steamIds;
	}

}
