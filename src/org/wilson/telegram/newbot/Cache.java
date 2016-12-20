package org.wilson.telegram.newbot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.wilson.telegram.newbot.models.EventModel;
import org.wilson.telegram.newbot.models.YelpBusinesses;

/**
 * Singleton cache for the Yelp service
 * Includes settings to toggle yelp link displays
 */

public class Cache {

	private static Cache yelpCache = new Cache();
	private List<YelpBusinesses> yelpList;
	private List<YelpBusinesses> currentYelpList;
	private int yelpPageState = 0;
	private boolean disableYelpDisplay = false;
	private HashMap<Integer, HashSet<EventModel>> eventMap;
	private HashMap<Integer, EventModel> inProgressEventCreations;
	private HashSet<String> dotaPlayers;
	private HashMap<String, HashSet<EventModel>> channelEventMap;

	


	private Cache() {
		setEventMap(new HashMap<Integer, HashSet<EventModel>>());
		setInProgressEventCreations(new HashMap<Integer, EventModel>());
		dotaPlayers = new HashSet<String>();
		dotaPlayers.add("david");
		dotaPlayers.add("jdea");
		dotaPlayers.add("riki");
		dotaPlayers.add("calvin");
		dotaPlayers.add("ray");
		dotaPlayers.add("wilson");

	}

	public static Cache getInstance() {
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


	public HashMap<Integer, HashSet<EventModel>> getEventMap() {
		return eventMap;
	}

	public void setEventMap(HashMap<Integer, HashSet<EventModel>> eventMap) {
		this.eventMap = eventMap;
	}

	public HashMap<Integer, EventModel> getInProgressEventCreations() {
		return inProgressEventCreations;
	}

	public void setInProgressEventCreations(HashMap<Integer, EventModel> inProgressEventCreations) {
		this.inProgressEventCreations = inProgressEventCreations;
	}
	public HashSet<String> getDotaPlayers() {
		return dotaPlayers;
	}

	public void setDotaPlayers(HashSet<String> dotaPlayers) {
		this.dotaPlayers = dotaPlayers;
	}

	public HashMap<String, HashSet<EventModel>> getChannelEventMap() {
		return channelEventMap;
	}

	public void setChannelEventMap(HashMap<String, HashSet<EventModel>> channelEventMap) {
		this.channelEventMap = channelEventMap;
	}
}
