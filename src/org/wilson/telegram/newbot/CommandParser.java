package org.wilson.telegram.newbot;



import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Random;

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

      	try{
    		command = message.getText().toLowerCase();

      	}catch(Exception e){
      		return null;
      	}
//		String user = message.getFrom().getUserName();
		Integer userId = message.getFrom().getId();


		if (command.startsWith(Commands.HELPCOMMAND)
				&& command.substring(0, 5).equals("/help")) {
			BotUtilities utilities = new BotUtilities(message);
			sendMessageRequest = utilities.sendHelp();
		}
		
		else if (command.startsWith(Commands.TESTCOMMAND)) {
			BotUtilities utilities = new BotUtilities(message);
			SendPhoto sendPhoto = new SendPhoto();
			InputStream in = null;
			URL url;
			try {
				url = new URL("http://api.screenshotlayer.com/api/capture?access_key=46c7d8e6d4ecf2d366fe97ab59c92193&url=https://www.opendota.com/matches/3549601979/overview&viewport=1440x2800&width=2500&fullpage=1");
				in = new BufferedInputStream(url.openStream());
				sendPhoto.setNewPhoto("any", in);
				sendPhoto.setChatId(message.getChatId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return sendPhoto;

		}
		else if (command.startsWith(Commands.STARTCOMMAND)) {
			if(message.getChatId() == (long) -297769804){
				Cache.getInstance().setMonitoringOn();
				sendMessageRequest.setChatId(message.getChatId());
				sendMessageRequest = sendMessageRequest.setText("Monitoring is on");
			}

		}else if (command.startsWith(Commands.STOPCOMMAND)) {
			sendMessageRequest.setChatId(message.getChatId());
			if(message.getChatId() == (long) -297769804){
				Cache.getInstance().setMonitoringOff();
				sendMessageRequest.setChatId(message.getChatId());
				sendMessageRequest = sendMessageRequest.setText("Monitoring is off");
			}

		}else if (command.startsWith(Commands.STATUSCOMMAND)) {
			
			boolean monitoring = Cache.getInstance().getMonitoringStatus();
			sendMessageRequest.setChatId(message.getChatId());
			if(monitoring){
				sendMessageRequest = sendMessageRequest.setText("Monitoring is on");
			}else{
				sendMessageRequest = sendMessageRequest.setText("Monitoring is off");
			}

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
	
		else if(command.startsWith(Commands.STACKCOMMAND)){
			SendPhoto sendP = new SendPhoto();
			sendP.setPhoto("AgADAQADq6cxG9PgUUyMYDBnLA4WYKYe9y8ABGekt0hIlJ3ZdgIBAAEC");
			sendP.setChatId(message.getChatId());		
			return sendP;	
		}		
		
		else if(command.startsWith(Commands.PERDERDERCOMMAND)){
			String[] arr = {"AgADAQADwKcxGyBykUVEvmbAsPwQ2euM3i8ABLKtlN4JtIWN-YEBAAEC","AgADAQADyacxGwuxSUXw4YJjEd5_Mhog9y8ABOqrvRDxKZhDoCMAAgI", "AgADAwADq6cxGz2ukE1cBnTAQThqEjb4hjEABHbeHOWFnRYNyl0BAAEC"}; 
			Random rand = new Random();
			int  n = rand.nextInt(arr.length);
			SendPhoto sendP = new SendPhoto();
			sendP.setPhoto(arr[n]);
			sendP.setChatId(message.getChatId());		
			return sendP;	
		}
		
		else if(command.startsWith(Commands.KISSCOMMAND)){
			SendDocument send = new SendDocument();
			send.setChatId(message.getChatId());
			send.setDocument("CgADAQADDAADq3WBRegpKkbhzGD3Ag");
			return send;
		}
		
		else if(command.startsWith(Commands.CUTECOMMAND)){
			SendPhoto sendP = new SendPhoto();
			sendP.setPhoto("AgADAQAD4KcxG0vriUWjUMWEpiZs7Ase9y8ABKVbGhiAj4B0OT4AAgI");
			sendP.setChatId(message.getChatId());		
			return sendP;	
		}
		else if(command.startsWith(Commands.CUTIECOMMAND)){
			SendPhoto sendP = new SendPhoto();
			sendP.setPhoto("AgADAQADvKcxGwhIgUWOpcj-xBuEEYYW9y8ABE9XE8x0mvx5dT8AAgI");
			sendP.setChatId(message.getChatId());		
			return sendP;	
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
