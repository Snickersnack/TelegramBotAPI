package org.wilson.telegram.newbot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import org.telegram.telegrambots.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultArticle;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.wilson.telegram.BotConfig;
import org.wilson.telegram.newbot.models.EventModel;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Re-implemented handler class. Deals with message updates
 * 
 */

public class NewBotHandler extends TelegramLongPollingBot {
	private static final String TOKEN = BotConfig.TOKENNEWBOT;
	private static final String BOTNAME = BotConfig.USERNAMENEWBOT;

	ObjectMapper mapper = new ObjectMapper();

	private static final boolean USEWEBHOOK = false;

	// public NewBotHandler(Webhook webhook) {
	// if (USEWEBHOOK) {
	// webhook.registerWebhook(this, BOTNAME);
	// SenderHelper.SendWebhook(Webhook.getExternalURL(BOTNAME), TOKEN);
	// } else {
	// SenderHelper.SendWebhook("", TOKEN);
	// new UpdatesThread(TOKEN, this);
	// }
	// }

	public void onUpdateReceived(Update update) {
		// TODO Auto-generated method stub
		System.out.println("heres' the update: " + update);
		try {
			BotApiMethod<?> msg = handleUpdate(update);
			if (msg != null) {
				System.out.println("UPDATE FOR DEBUGGING: " + update);
				System.out.println("MSG FOR DEBUGGING" + msg);
				executeMessage(msg);
			}

		} catch (TelegramApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public BotApiMethod onWebhookUpdateReceived(Update update) {
		// TODO Auto-generated method stub
		return null;
	}

	public BotApiMethod<?> handleUpdate(Update update)
			throws TelegramApiException {
		
		SendMessage sendMessageRequest = new SendMessage();
		
		if(update.hasChosenInlineQuery()){		
			updateCacheId(update);
			return null;
		}
		else if (update.hasInlineQuery()) {
			AnswerInlineQuery aQuery = handleInlineQuery(update);
			return aQuery;
		}
		else if (update.hasCallbackQuery()) {
			EditMessageText editMessageRequest = handleCallbackQuery(update);
			return editMessageRequest;
		}
		else if (update.hasMessage()){
			Message message = update.getMessage();
//			System.out.println(update);

			CommandParser commandParser = new CommandParser(message);
			try {
				sendMessageRequest = commandParser.parse();

			} catch (Exception e) {
				e.printStackTrace();
			}

			return sendMessageRequest;		
			}
		return null;

	}
	
	//Each inlinemessageid is unique, we must add this to a list nad update? chance updatecache
	private EditMessageText handleCallbackQuery(Update update){
		String response = update.getCallbackQuery().getData();
		User user = update.getCallbackQuery().getFrom();
		String userFirst = user.getFirstName();
		String inLineMessageId = update.getCallbackQuery().getInlineMessageId();
		EventModel eventModel = null;
		HashMap<Integer, HashSet<EventModel>> map = Cache.getInstance().getEventMap();
		Integer cachedUser = null;
		Boolean found = false;
		
		//search for the event based on the inLineMessageId
		for(Entry<Integer, HashSet<EventModel>> entry : map.entrySet()){
			if(!entry.getValue().isEmpty()){
				cachedUser = entry.getKey();
				//if event model's hashset of channels has this id, this is our event
				for(EventModel event : entry.getValue()){
					if(event.getInLineMessageId().contains(inLineMessageId)){
						eventModel = event;
						found = true;
//						System.out.println("eventname: " + event.getEventName() + " eventmessageId = " + event.getInLineMessageId());
						break;
					}
				}
				if(found){
					break;
				}
			}
		}
//		System.out.println("Cached user: " + cachedUser);
//		StringBuilder sb2 = new StringBuilder();
//		String setString1 = "";
//		sb2.append("Edit event: ");
//		for(Entry<String, HashSet<EventModel>> entry : map.entrySet()){
//			for(EventModel event : entry.getValue()){
//				setString1 += event.getEventName() + " ";
//			}
//			sb2.append(entry.getKey() + ": " + setString1);
//			setString1= "";
//		}
//		System.out.println("initial map from edit: " + sb2);
		//get our eventmodel based off of the user we found the message in
		HashSet<EventModel> cachedSet = map.get(cachedUser);

					
		
		if(eventModel == null){
			return null;
		}
		Integer newAttendee = eventModel.getAttendeeNumber();
		HashSet<String> set = eventModel.getAttendees();
		if (response.equals("Yes")) {
			// if "Yes" and is not currently on attendees list, add to
			// attendees
			if (!eventModel.getAttendees().contains(userFirst)) {
				set.add(userFirst);
				eventModel.setAttendeeNumber(++newAttendee);
			} else {
				return null;
			}
			// if "No" and is currently on attendees list, remove from
			// attendees
		} else {
//			System.out.println("UserFirst variable: " + userFirst);
			if (eventModel.getAttendees().contains(userFirst)) {
				set.remove(userFirst);
				eventModel.setAttendeeNumber(--newAttendee);

			} else {
				return null;
			}
			eventModel.setAttendees(set);

		}
		String eventName = eventModel.getEventName();
		String eventLocation = eventModel.getEventLocation();
		String eventDate = eventModel.getEventDate();
		String eventHostFirst = eventModel.getEventHostFirst();
		String attendeeList = "Attendees (" + newAttendee + "): ";
		int counter = 1;
		if(!set.isEmpty()){
			for (String item : set) {
				if(counter == set.size()){
					attendeeList += " " + item;
				}else{
					attendeeList += " " + item + ", ";

				}
				counter++;
			}
		}
		String eventText = "<strong>" + eventName + "</strong>" + System.getProperty("line.separator") 
				+ eventDate + System.getProperty("line.separator") 
				+ "📍" + eventLocation + System.getProperty("line.separator") 
				+ attendeeList;
		
		eventModel.setEventText(eventText);
		EditMessageText editRequest = new EditMessageText();
		InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
		markup.setKeyboard(eventModel.getEventGrid());

		editRequest.setInlineMessageId(inLineMessageId);
		editRequest.setParseMode("HTML");
		editRequest.setText(eventText);
		editRequest.setReplyMarkup(markup);
//
//		String test = "";
//		for(EventModel e : cachedSet){
//			test += e.getEventName() + " ";
//		}
//		System.out.println("Before set remove:" + test);
//		test = "";
		cachedSet.remove(eventModel);
		cachedSet.add(eventModel);
//		for(EventModel e : cachedSet){
//			test += e.getEventName() + " ";
//		}
//		System.out.println("After set remove:" + test);

		map.put(cachedUser, cachedSet);
		
//		StringBuilder sb = new StringBuilder();
//		String setString = "";
//		sb.append("Edit event: ");
//		for(Entry<String, HashSet<EventModel>> entry : map.entrySet()){
//			for(EventModel event : entry.getValue()){
//				setString += event.getEventName() + " ";
//			}
//			sb.append(entry.getKey() + ": " + setString);
//			setString= "";
//		}
//		System.out.println(sb);
		Cache.getInstance().setEventMap(map);
		
//		System.out.println("Outgoing editRequest: " + editRequest.toString());
		return editRequest;
	}
	
	
	private AnswerInlineQuery handleInlineQuery(Update update){
		InlineQuery iQuery = update.getInlineQuery();
		Integer userId = iQuery.getFrom().getId();
		AnswerInlineQuery aQuery = new AnswerInlineQuery();
		List<InlineQueryResult> list = new ArrayList<>();
		HashMap<Integer, HashSet<EventModel>> cachedEvents = Cache.getInstance().getEventMap();
		
		//We can have users, but no events. So we must find how many events we have
		//For each event in our eventmap,  create a Result article and add to list
		//If no events, return empty message
		HashSet<EventModel> userSet = cachedEvents.get(userId);
		if(userSet!= null && !userSet.isEmpty()){
			for(EventModel event : cachedEvents.get(userId)){
				InlineQueryResultArticle qResultArticle = new InlineQueryResultArticle();
				InputTextMessageContent content = new InputTextMessageContent();
				content.setMessageText(event.getEventText());
				content.setParseMode(BotConfig.SENDMESSAGEMARKDOWN);
				qResultArticle.setInputMessageContent(content);
				InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
				markup.setKeyboard(event.getEventGrid());
				qResultArticle.setReplyMarkup(markup);
				qResultArticle.setId(event.getEventName());
				qResultArticle.setTitle(event.getEventName());
				list.add(qResultArticle);
			
			}	

		}
		StringBuilder sb = new StringBuilder();
		String setString = "";
		for(Entry<Integer, HashSet<EventModel>> entry : cachedEvents.entrySet()){
			for(EventModel event : entry.getValue()){
				setString += event.getEventName() + " ";
			}
			sb.append(entry.getKey() + ": " + setString);
			setString= "";
		}
		System.out.println(sb.toString());
		aQuery.setInlineQueryId(iQuery.getId());
		aQuery.setSwitchPmText("Click here to create event");
		aQuery.setSwitchPmParameter("/event");
		aQuery.setResults(list);
		aQuery.setCacheTime(1);
		
		for(InlineQueryResult result : aQuery.getResults()){
			InlineQueryResultArticle art = (InlineQueryResultArticle) result;
			System.out.println("Article ids: " + art.getId());
			
		}
		return aQuery;
		
	}

	private void executeMessage(BotApiMethod<?> msg)
			throws TelegramApiException {

		if(msg == null){
			return;
		}
		System.out.println("sending: "  + msg.toString());
		if (msg instanceof SendMessage) {
			SendMessage sMessage = (SendMessage) msg;
			if(sMessage.getChatId()!=null){
				Message botMessage = sendMessage(sMessage);

			}


		} else {
			sendApiMethod(msg);

		}

	}

	private void updateCacheId(Update update){
		String resultId = update.getChosenInlineQuery().getResultId();
		String inLineMessageId = update.getChosenInlineQuery().getInlineMessageId();
//		System.out.println("Chosen query resultId: " + resultId);
//		System.out.println("Chosen query From: " + update.getChosenInlineQuery().getFrom());
//		System.out.println("Inline Message Id: " + inLineMessageId);
		
		HashMap<Integer, HashSet<EventModel>> map = Cache.getInstance().getEventMap();
		EventModel tempEvent = new EventModel();
		for(Entry<Integer, HashSet<EventModel>> entry : map.entrySet()){
			if(!entry.getValue().isEmpty()){
				for(EventModel event : entry.getValue()){
					if(event.getEventName().equals(resultId)){
						System.out.println("inline event: " + event.getInLineMessageId());
						HashSet<String> set = event.getInLineMessageId();
						set.add(inLineMessageId);
						event.setInLineMessageId(set);
						return;
					}
					
					
				}
			}
		}
		
//		channelMap.put(update.getChosenInlineQuery().get)
	}
	@Override
	public String getBotUsername() {
		// TODO Auto-generated method stub
		return BOTNAME;
	}

	@Override
	public String getBotToken() {
		// TODO Auto-generated method stub
		return TOKEN;
	}

}