package org.wilson.telegram.newbot.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class EventModel {

	private String eventText;
	private Integer attendeeNumber;
	private List<List<InlineKeyboardButton>> eventGrid;
	private String eventHost;
	private String eventHostFirst;
	private HashSet<String> attendees;
	private int eventInputStage;
	private String eventName;
	private String eventLocation;
	private String eventDate;
	private HashSet<String> inLineMessageIds;
	
	public EventModel(){
		eventText = null;
		attendeeNumber = 0;
		eventGrid = new ArrayList<List<InlineKeyboardButton>>();
		eventHost = null;
		eventHostFirst = null;
		attendees = new HashSet<String>();
		eventInputStage = 0;
		eventName = null;
		eventLocation = null;
		eventDate = null;
		inLineMessageIds = new HashSet<String>();
	}
	
	public String getEventText() {
		return eventText;
	}

	public void setEventText(String eventText) {
		this.eventText = eventText;
	}

	public Integer getAttendeeNumber() {
		return attendeeNumber;
	}

	public void setAttendeeNumber(Integer attendeeNumber) {
		this.attendeeNumber = attendeeNumber;
	}

	public List<List<InlineKeyboardButton>> getEventGrid() {
		return eventGrid;
	}

	public void setEventGrid(List<List<InlineKeyboardButton>> eventGrid) {
		this.eventGrid = eventGrid;
	}

	public String getEventHost() {
		return eventHost;
	}

	public void setEventHost(String eventHost) {
		this.eventHost = eventHost;
	}

	public String getEventHostFirst() {
		return eventHostFirst;
	}

	public void setEventHostFirst(String eventHostFirst) {
		this.eventHostFirst = eventHostFirst;
	}

	public HashSet<String> getAttendees() {
		return attendees;
	}

	public void setAttendees(HashSet<String> attendees) {
		this.attendees = attendees;
	}

	public int getEventInputStage() {
		return eventInputStage;
	}

	public void setEventInputStage(int eventInputStage) {
		this.eventInputStage = eventInputStage;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventLocation() {
		return eventLocation;
	}

	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}


	
	@Override
	public boolean equals(Object o){
		if(o == null){
			return false;
		}
		if(!EventModel.class.isAssignableFrom(o.getClass())){
			return false;
		}
		EventModel temp = (EventModel)o;
		if(!this.eventName.equals(temp.getEventName())){
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode(){
		int hash = 17;
		hash = 31 * hash + eventName.hashCode();
		return hash;
	}

	public HashSet<String> getInLineMessageId() {
		return inLineMessageIds;
	}

	public void setInLineMessageId(HashSet<String> inLineMessageId) {
		this.inLineMessageIds = inLineMessageId;
	}
	
	
}
