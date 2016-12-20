package org.wilson.telegram.newbot;



import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.wilson.telegram.BotConfig;
import org.wilson.telegram.Commands;
import org.wilson.telegram.newbot.models.EventModel;
import org.wilson.telegram.newbot.services.BotUtilities;
import org.wilson.telegram.newbot.services.SteamService;


/**
 * Service to parse commands
 * 
 */


// Takes in the message and parses for command
// Runs service based on command match

public class CommandParser {

	private String command;
	private Message message;
	SendMessage sendMessageRequest;
	private static final String TOKEN = BotConfig.TOKENNEWBOT;
	public CommandParser(Message message) {
		this.message = message;
		sendMessageRequest = new SendMessage();

	}

	// Push commands to service classes

	public SendMessage parse() throws TelegramApiException {
		command = message.getText().toLowerCase();
//		String user = message.getFrom().getUserName();
		Integer userId = message.getFrom().getId();
		HashMap<Integer, EventModel> inProgressCache = Cache.getInstance().getInProgressEventCreations();			
		
		//Check if we're in the middle of creating an event for user
		if(inProgressCache.get(userId) != null){
			if(message.isUserMessage()){
				sendMessageRequest = EventStartCommand.setEventInfo(message);
				
			}else{
				sendMessageRequest.setText("Events must be created privately");
			}
		}

		else if (command.startsWith(Commands.DELETEEVENTSCOMMAND)){
			sendMessageRequest.setChatId(message.getChatId());
			String eventName = command.substring(14);
			if(message.isUserMessage()){
				
				HashMap<Integer, HashSet<EventModel>> newMap = Cache.getInstance().getEventMap();
				HashSet<EventModel> set = newMap.get(userId);
				Iterator<EventModel> it = set.iterator();
				while(it.hasNext()){
					EventModel event = it.next();
					if(event.getEventName().toLowerCase().equals(eventName.toLowerCase())){
						set.remove(event);
						break;
					}
				}
				newMap.put(userId, set);
				Cache.getInstance().setEventMap(newMap);
				sendMessageRequest.setText(eventName + " has been deleted from your events");
			}else{
				sendMessageRequest.setText("Events must be deleted privately");
			}
		}
		else if(command.startsWith(Commands.CLEAREVENTSCOMMAND)){
			sendMessageRequest.setChatId(message.getChatId());
			if(message.isUserMessage()){
				HashMap<Integer, HashSet<EventModel>> newMap = Cache.getInstance().getEventMap();
				newMap.put(userId, new HashSet<EventModel>());
				Cache.getInstance().setEventMap(newMap);
				sendMessageRequest.setText("Your events have been cleared");
			}else{
				sendMessageRequest.setText("Events must be cleared privately");
			}

			
		}
		else if (command.startsWith(Commands.HELPCOMMAND)
				&& command.substring(0, 5).equals("/help")) {
			BotUtilities utilities = new BotUtilities(message);
			sendMessageRequest = utilities.sendHelp();
		}
		

		else if (command.startsWith(Commands.USERSCOMMAND)) {
			BotUtilities utilities = new BotUtilities(message);
			sendMessageRequest = utilities.sendUsers();

		}
		

		else if(command.startsWith(Commands.YELPCOMMAND)){
			YelpCommand yelpCommand = new YelpCommand();
			sendMessageRequest = yelpCommand.parse(message, command.substring(6));
		}
		else if (command.startsWith(Commands.STEAMCOMMAND)) {
			SteamService steamService = new SteamService(message);
			sendMessageRequest = steamService.send();

		}
//		else if(command.equals(Commands.TESTCOMMAND)){
//			Message message = new Message();
//		}
		else if(command.startsWith(Commands.STARTCOMMAND)){
			//See if user is already creating an event
			if(inProgressCache.get(userId) != null){
				sendMessageRequest.setChatId(message.getChatId());
				sendMessageRequest.setText("Event creation already in progress");
			}
			//Check if we are in a group chat
			else if(message.getChat().isUserChat()){
				EventModel newEvent = new EventModel();
//				newEvent.setEventHost(user);
				newEvent.setEventHostFirst(message.getFrom().getFirstName());
				newEvent.setEventInputStage(1);

				inProgressCache.put(userId, newEvent);
				
				//Check if the cache has an existing user, if not add it to eventMap
				HashMap<Integer, HashSet<EventModel>> map = Cache.getInstance().getEventMap();
				if(map.get(userId) == null){
					map.put(userId, new HashSet<EventModel>());
				}
				Cache.getInstance().setInProgressEventCreations(inProgressCache);

				sendMessageRequest.setChatId(message.getChatId());
				sendMessageRequest.setText("What is the name of your event?");
			}else{
				sendMessageRequest.setChatId(message.getChatId());
				sendMessageRequest.setText("Events must be created privately");
			}

			
		}

		else if(command.startsWith(Commands.commandInitChar)){
			HashSet<String> dotaPlayers = Cache.getInstance().getDotaPlayers();
			command.replaceAll(" ", "");
			if(command.length() > 10){
				if(dotaPlayers.contains(command.substring(1,command.length()-8))){
					DotaCommand dota = new DotaCommand(message);
					sendMessageRequest = dota.parse(message);
				}
			}else{
				if(dotaPlayers.contains(command.substring(1))){
					DotaCommand dota = new DotaCommand(message);
					sendMessageRequest = dota.parse(message);
				}
			}


		}
		
		return sendMessageRequest;


	}
}
