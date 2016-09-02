package org.telegram.newbot;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.telegram.BotConfig;
import org.telegram.Commands;
import org.telegram.SenderHelper;
import org.telegram.SteamIds;
import org.telegram.api.methods.SendMessage;
import org.telegram.api.objects.Message;
import org.telegram.newbot.services.BotUtilities;
import org.telegram.newbot.services.DotaService;
import org.telegram.newbot.services.SteamService;
import org.telegram.newbot.services.YelpService;


// Takes in the message and parses for command
// Runs service based on command match

public class CommandParser {

	private YelpService yelpService;
	private String command;
	private Message message;
	private static final String TOKEN = BotConfig.TOKENNEWBOT;
	private static String steamIdRegex = "/(RIKI|JDEA|WILSON|ELTON|CHRIS|RAYMOND)\\s(RIKI|JDEA|WILSON|ELTON|CHRIS|RAYMOND)/i";
	public CommandParser(Message message) {
		this.message = message;
	}

	// Push commands to service classes

	public void push() {
		command = message.getText().toLowerCase();
	      	    
		if (command.startsWith(Commands.MENUCOMMAND)
				&& command.substring(0, 5).equals("/menu")) {
			BotUtilities utilities = new BotUtilities(message);
			utilities.sendMenu();
		}
		

		else if (command.startsWith(Commands.RIKICOMMAND)) {
			try{
				System.out.println("riki command");
			DotaService dota = new DotaService(message);
			dota.setHeroes();
			dota.setId(SteamIds.RIKI);
			dota.send();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		else if (command.startsWith(Commands.JDEACOMMAND)) {
			DotaService dota = new DotaService(message);
			dota.setHeroes();
			dota.setId(SteamIds.JDEA);
			dota.send();

		}
		else if (command.startsWith(Commands.RAYCOMMAND)) {
			DotaService dota = new DotaService(message);
			dota.setHeroes();
			dota.setId(SteamIds.BRO);
			dota.send();

		}
		else if (command.startsWith(Commands.ELTONCOMMAND)) {
			DotaService dota = new DotaService(message);
			dota.setHeroes();
			dota.setId(SteamIds.ELTON);
			dota.send();

		}
		else if (command.startsWith(Commands.DAVIDCOMMAND)) {
			DotaService dota = new DotaService(message);
			dota.setHeroes();
			dota.setId(SteamIds.DAVID);
			dota.send();
		}
		else if (command.startsWith(Commands.COMBOCOMMAND)) {
		
		}
		
		else if (command.startsWith(Commands.CALVINCOMMAND)) {
			SendMessage sendMessageRequest = new SendMessage();
			sendMessageRequest.setChatId(message.getChatId());
			sendMessageRequest.setText("https://upload.wikimedia.org/wikipedia/commons/a/a2/Bubble_Tea.png");
			SenderHelper.SendApiMethod(sendMessageRequest, TOKEN);

		}
		else if (command.startsWith(Commands.WILSONCOMMAND)) {
			DotaService dota = new DotaService(message);
			dota.setHeroes();
			dota.setId(SteamIds.WILSON);
			dota.send();

		}
		
		else if (command.startsWith(Commands.CHRISCOMMAND)) {
			DotaService dota = new DotaService(message);
			dota.setHeroes();
			dota.setId(SteamIds.CHRIS);
			dota.send();

		}
		else if (command.startsWith(Commands.DISPLAYONCOMMAND)) {
			YelpCache.getInstance().enableYelpDisplay();
			SendMessage sendMessageRequest = new SendMessage();
			sendMessageRequest.setChatId(message.getChatId());
			sendMessageRequest.setText("Yelp Display On");
			SenderHelper.SendApiMethod(sendMessageRequest, TOKEN);

		}
		else if (command.startsWith(Commands.DISPLAYOFFCOMMAND)) {
			YelpCache.getInstance().disableYelpDisplay();;
			SendMessage sendMessageRequest = new SendMessage();
			sendMessageRequest.setChatId(message.getChatId());
			sendMessageRequest.setText("Yelp Display Off");
			SenderHelper.SendApiMethod(sendMessageRequest, TOKEN);


		}

		else if (command.startsWith(Commands.PREVCOMMAND)) {
			this.yelpService = new YelpService(message);
			switch (YelpCache.getInstance().getYelpPageState()) {
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

		} else if (command.startsWith(Commands.NEXTCOMMAND)) {
			this.yelpService = new YelpService(message);
			switch (YelpCache.getInstance().getYelpPageState()) {
			case 0:
				SendMessage sendMessageRequest = new SendMessage();
				sendMessageRequest.setChatId(message.getChatId());
				sendMessageRequest.setText("Please do a yelp search");
				break;
			case 1:
				if (YelpCache.getInstance().getYelpList().size() > 3) {
					YelpCache.getInstance().setYelpPageState(2);
					break;
				} else {
					return;
				}
			case 2:
				if (YelpCache.getInstance().getYelpList().size() > 6) {
					YelpCache.getInstance().setYelpPageState(3);
					break;
				} else {
					return;
				}
			case 3:
				return;
			}
			try {
				yelpService.send();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		else if (command.startsWith(Commands.YELPCOMMAND)
				&& command.substring(0, 5).equals("/yelp")
				&& command.length() > 6) {
			this.yelpService = new YelpService(message);
			try {
				yelpService.request();
			} catch (Exception e) {
				e.printStackTrace();
			}
			yelpService.send();
		}

		else if (command.startsWith(Commands.STEAMCOMMAND)) {
			SteamService steamService = new SteamService(message);
			steamService.send();

		}
	}
}
