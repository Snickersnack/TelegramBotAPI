package org.wilson.telegram.newbot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.wilson.telegram.BotConfig;
import org.wilson.telegram.newbot.models.EventModel;

public class EventStartCommand {

	
	public EventStartCommand(){
		
	}
	
	public static SendMessage setEventInfo(Message message){
		HashMap<Integer, EventModel> inProgressItem = Cache.getInstance().getInProgressEventCreations();
		Integer userId = message.getFrom().getId();
		int stage = inProgressItem.get(userId).getEventInputStage();

		EventModel inProgressEventItem = inProgressItem.get(userId);
		SendMessage sendMessageRequest = new SendMessage();
		sendMessageRequest.setChatId(message.getChatId());
		

			if(stage == 1){
				inProgressEventItem.setEventName(message.getText());

				//If event already exists, request another input
				if(Cache.getInstance().getEventMap().get(userId).contains(inProgressEventItem)){
					sendMessageRequest.setText("There is already an event with this name. Please try another name");
				}else{
					sendMessageRequest.setText("Where is the location of this event?");
					inProgressEventItem.setEventInputStage(2);
					inProgressItem.put(userId,inProgressEventItem);
					Cache.getInstance().setInProgressEventCreations(inProgressItem);
				}
			}else if(stage == 2){
				sendMessageRequest.setText("What is the date of this event? (e.g. 11/21/2017 10:00PM)");
				inProgressEventItem.setEventLocation(message.getText());
				inProgressEventItem.setEventInputStage(3);
				inProgressItem.put(userId,inProgressEventItem);
				Cache.getInstance().setInProgressEventCreations(inProgressItem);
		
			}else if(stage == 3){
				inProgressEventItem.setEventDate(message.getText());
				HashMap<Integer, HashSet<EventModel>> completedEvent = generateNewEvent(userId);
		
				
				
				//Generate a return keyboard button
				InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
				InlineKeyboardButton button = new InlineKeyboardButton();
				button.setText("Search Events");
				button.setSwitchInlineQuery(inProgressEventItem.getEventName());
				List<InlineKeyboardButton> buttons = new ArrayList();
				buttons.add(button);
				List<List<InlineKeyboardButton>> grid = new ArrayList();
				grid.add(buttons);
				markup.setKeyboard(grid);
				sendMessageRequest.setReplyMarkup(markup);
				sendMessageRequest.setText("Event Created!");
				sendMessageRequest.setParseMode(BotConfig.SENDMESSAGEMARKDOWN);	
			
				
				inProgressItem.put(userId, null);
				
				
				//clear item for this user from cache. Add Event to Eventmap

				Cache.getInstance().setEventMap(completedEvent);
				Cache.getInstance().setInProgressEventCreations(inProgressItem);
				
			}
		

		return sendMessageRequest;
		
	}

    public static HashMap<Integer, HashSet<EventModel>> generateNewEvent(Integer userId){
    	HashMap<Integer, HashSet<EventModel>> map = Cache.getInstance().getEventMap();
    	EventModel newEvent = Cache.getInstance().getInProgressEventCreations().get(userId);
    	HashSet<EventModel> mapSet = map.get(userId);
    	
    	System.out.println("Event start command: " + userId);
		String eventName = newEvent.getEventName();
		String eventLocation = newEvent.getEventLocation();
		String eventDate = newEvent.getEventDate();
		String eventHostFirst = newEvent.getEventHostFirst();
		Integer attendeesNumber = newEvent.getAttendeeNumber();
		
		InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
		InlineKeyboardButton button = new InlineKeyboardButton();
		InlineKeyboardButton button2 = new InlineKeyboardButton();
		button.setText("Yes");
		button.setCallbackData("Yes");
		button2.setText("No");
		button2.setCallbackData("No");
		List<InlineKeyboardButton> buttons = new ArrayList();
		buttons.add(button);
		buttons.add(button2);
		List<List<InlineKeyboardButton>> grid = new ArrayList();
		grid.add(buttons);
		newEvent.setEventGrid(grid);
			
		//Fix this duplicate here, we don't need to iterate over this when initiating the event

		String attendeeString = "Attendees (" + attendeesNumber + "): ";
		String event = "<strong>" + eventName + "</strong>" + System.getProperty("line.separator") 
				+ eventDate + System.getProperty("line.separator") 
				+ "📍" + eventLocation + System.getProperty("line.separator") 

				+ attendeeString;
		
		//Add event to the set, then add to map and return map
		newEvent.setEventText(event);
		mapSet.add(newEvent);
		map.put(userId, mapSet);
		
		return map;
    }
}
