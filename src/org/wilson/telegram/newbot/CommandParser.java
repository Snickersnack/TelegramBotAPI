package org.wilson.telegram.newbot;



import java.util.HashSet;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.wilson.telegram.BotConfig;
import org.wilson.telegram.Commands;
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
		


		if (command.startsWith(Commands.HELPCOMMAND)
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
