package org.wilson.telegram.newbot;



import java.util.HashSet;

import org.telegram.telegrambots.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
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

	public PartialBotApiMethod<?> parse() throws TelegramApiException {

      	
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
		else if(command.startsWith(Commands.CRYCOMMAND)){
			SendDocument send = new SendDocument();
			send.setChatId(message.getChatId());
			send.setDocument("CgADAQADBQADq3WBRXyA4tZmeuwBAg");
			return send;			
		}
		else if(command.startsWith(Commands.MADCOMMAND)){
			SendDocument send = new SendDocument();
			send.setChatId(message.getChatId());
			send.setDocument("CgADAQADCAADq3WBRRLHsY2Gd0iuAg");
			return send;
		}		
		
		else if(command.startsWith(Commands.KISSCOMMAND)){
			SendDocument send = new SendDocument();
			send.setChatId(message.getChatId());
			send.setDocument("CgADAQADDAADq3WBRegpKkbhzGD3Ag");
			return send;
		}
		
		else if(command.startsWith(Commands.APPROVECOMMAND)){
			SendDocument send = new SendDocument();
			send.setChatId(message.getChatId());
			send.setDocument("CgADAQADBwADq3WBRXbPAAGBJ59w7QI");
			return send;			
		}
		else if(command.startsWith(Commands.LAUGHCOMMAND)){
			SendDocument send = new SendDocument();
			send.setChatId(message.getChatId());
			send.setDocument("CgADAQADCAADqfN4RQXnIgThC1aWAg");
			return send;
		
	}
		

		else if(command.startsWith(Commands.YELPCOMMAND)){
			YelpCommand yelpCommand = new YelpCommand();
			sendMessageRequest = yelpCommand.parse(message, command.substring(6));
		}
		else if (command.startsWith(Commands.STEAMCOMMAND)) {
			SteamService steamService = new SteamService(message);
			sendMessageRequest = steamService.send();

		}

			

		else if(command.startsWith(Commands.commandInitChar)){
			HashSet<String> dotaPlayers = Cache.getInstance().getPlayers();
			command.replaceAll(" ", "");
			if(command.length() > 10){
				if(dotaPlayers.contains(command.substring(1,command.length()-8))){
					DotaCommand dota = new DotaCommand(message);
					return dota.parse(message);
				}
			}else{
				if(dotaPlayers.contains(command.substring(1))){
					DotaCommand dota = new DotaCommand(message);
					return dota.parse(message);
				}
			}


		}
		
		return sendMessageRequest;


	}
}
