package org.telegram.newbot;

import java.util.List;

import org.telegram.BotConfig;
import org.telegram.Commands;
import org.telegram.api.methods.SendMessage;
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
//    private static final String YELPTOKENSECRET = BotConfig.YELPTOKENSECRET;
    private YelpService yelpService;

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

//	if (mostRecentCommands.size() > 10){
//	System.out.println("makes it into first if");
//		mostRecentCommands.remove(0);
//	}
System.out.println(command.substring(0, 5));
    if (command.startsWith(Commands.HELPCOMMAND) && command.substring(0, 5).equals("/help")
){

//		mostRecentCommands.add(command);
    	BotUtilities utilities = new BotUtilities(message);
		utilities.sendHelp();	
    }
    
    else if (command.startsWith(Commands.RECENTCOMMAND)){
		BotUtilities utilities = new BotUtilities(message);
//		mostRecentCommands.add(command);
		utilities.sendRecent(mostRecentCommands);
    }
    
    else if (command.startsWith(Commands.MOSTRECENTMATCHCOMMAND)){
//    	mostRecentCommands.add(command);
    }
    	
	else if (command.startsWith(Commands.PREVCOMMAND)){
		this.yelpService = new YelpService(message);

		switch (YelpCache.getInstance().getYelpPageState()){
		
		case 0:
			 SendMessage sendMessageRequest = new SendMessage();
		     sendMessageRequest.setChatId(message.getChatId());
		     sendMessageRequest.setText("Please do a yelp search");
		     break;
		case 1:
			return;
		case 2:
			YelpCache.getInstance().setYelpPageState(1);
			break;
		case 3:
			YelpCache.getInstance().setYelpPageState(2);
			break;
			
		}
		yelpService.send();

	}
//		mostRecentCommands.add(command);
		
		else if (command.startsWith(Commands.NEXTCOMMAND)){
			this.yelpService = new YelpService(message);

			switch (YelpCache.getInstance().getYelpPageState()){
			case 0:
				 SendMessage sendMessageRequest = new SendMessage();
			     sendMessageRequest.setChatId(message.getChatId());
			     sendMessageRequest.setText("Please do a yelp search");
			     break;
			case 1:
				if(YelpCache.getInstance().getYelpList().size() > 3){
				YelpCache.getInstance().setYelpPageState(2);
				break;
				}
				else{
					return;
				}
			case 2:
				if(YelpCache.getInstance().getYelpList().size() > 6){

				YelpCache.getInstance().setYelpPageState(3);
				break;

				}
				else{
					return;
				}
			case 3:
				return;
				
			}
			try{
			yelpService.send();
			}
			catch(Exception e){
				e.printStackTrace();
			}

	}
		
	else if (command.startsWith(Commands.YELPCOMMAND) && command.substring(0, 5).equals("/yelp") && command.length() > 6){
//		mostRecentCommands.add(command);
		this.yelpService = new YelpService(message);
		try{
		yelpService.request();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("request completed");

		yelpService.send();
		
		
	}
	else if(command.startsWith(Commands.YELPPOLLCOMMAND)){
//		mostRecentCommands.add(command);
		
	}
	else if(command.startsWith(Commands.YELPADDCOMMAND)){
//		mostRecentCommands.add(command);
		
	}
	}
}

