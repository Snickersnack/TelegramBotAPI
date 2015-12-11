package org.telegram.newbot;

import java.util.List;

import org.telegram.BotConfig;
import org.telegram.Commands;
import org.telegram.api.objects.Message;
import org.telegram.newbot.models.NewBotYelpModels;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CommandParser {
	
    private static final String TOKEN = BotConfig.TOKENNEWBOT;
    private static final String BOTNAME = BotConfig.USERNAMENEWBOT;
    private static final String CONSUMERKEY = BotConfig.CONSUMERKEY;
    private static final String CONSUMERSECRET = BotConfig.CONSUMERSECRET;
    private static final String YELPTOKEN = BotConfig.YELPTOKEN;
    private static final String YELPTOKENSECRET = BotConfig.YELPTOKENSECRET;
	private String command;
	private Message message;
	private List<String> mostRecentCommands;
	//Takes in the message and parses for command
	//Runs service based on command match
	
	public CommandParser(Message message){
		this.message = message;

	}
	
	//Push commands to service classes
	public void push(){
	
	command = message.getText().toLowerCase();
	if (mostRecentCommands.size() > 10){
		mostRecentCommands.remove(0);
	}

    if (command.startsWith(Commands.HELPCOMMAND) && command.substring(0, 4).equals("/help")){
		mostRecentCommands.add(command);
    	BotUtilities utilities = new BotUtilities(message);
		utilities.sendHelp();	
    }
    
    else if (command.startsWith(Commands.RECENTCOMMAND)){
		BotUtilities utilities = new BotUtilities(message);
		mostRecentCommands.add(command);
		utilities.sendRecent(mostRecentCommands);
    }
    
    else if (command.startsWith(Commands.MOSTRECENTMATCHCOMMAND)){
    	mostRecentCommands.add(command);
    }
    	
	else if (command.startsWith(Commands.PREVCOMMAND)){
		mostRecentCommands.add(command);
	}
		
	else if (command.startsWith(Commands.YELPCOMMAND) && command.substring(0, 4).equals("/yelp")){
		mostRecentCommands.add(command);
		YelpService yelpService = new YelpService(message);
		yelpService.send();
		
		
	}
	else if(command.startsWith(Commands.YELPPOLLCOMMAND)){
		mostRecentCommands.add(command);
		
	}
	else if(command.startsWith(Commands.YELPADDCOMMAND)){
		mostRecentCommands.add(command);
		
	}
	}
}

