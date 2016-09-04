package org.wilson.telegram.newbot;



import org.wilson.telegram.BotConfig;
import org.wilson.telegram.Commands;
import org.wilson.telegram.SteamIds;
import org.wilson.telegram.newbot.services.BotUtilities;
import org.wilson.telegram.newbot.services.DotaService;
import org.wilson.telegram.newbot.services.SteamService;
import org.wilson.telegram.newbot.services.YelpService;
import org.telegram.SenderHelper;
import org.telegram.api.methods.SendMessage;
import org.telegram.api.objects.Message;

/**
 * Service to parse commands
 * 
 */


// Takes in the message and parses for command
// Runs service based on command match

public class CommandParser {

	private YelpService yelpService;
	private DotaService dota;
	private String command;
	private Message message;
	private static final String TOKEN = BotConfig.TOKENNEWBOT;
	public CommandParser(Message message) {
		this.message = message;
		dota = new DotaService(message);
		dota.setHeroes();
	}

	// Push commands to service classes

	public void push() {
		command = message.getText().toLowerCase();
	      	    
		if (command.startsWith(Commands.MENUCOMMAND)
				&& command.substring(0, 5).equals("/menu")) {
			BotUtilities utilities = new BotUtilities(message);
			utilities.sendMenu();
		}
		
		
		else if (command.startsWith(Commands.USERSCOMMAND)) {
			BotUtilities utilities = new BotUtilities(message);
			utilities.sendUsers();

		}
		
		else if (command.startsWith(Commands.RIKICOMMAND)) {
			try{
			dota.setId(SteamIds.RIKI);
			dota.send();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		else if (command.startsWith(Commands.JDEACOMMAND)) {
			dota.setId(SteamIds.JDEA);
			dota.send();

		}
		else if (command.startsWith(Commands.RAYCOMMAND)) {
			dota.setId(SteamIds.BRO);
			dota.send();

		}
		else if (command.startsWith(Commands.ELTONCOMMAND)) {
			dota.setId(SteamIds.ELTON);
			dota.send();

		}
		else if (command.startsWith(Commands.DAVIDCOMMAND)) {
			dota.setId(SteamIds.DAVID);
			dota.send();
		}

		else if (command.startsWith(Commands.CALVINCOMMAND)) {
			SendMessage sendMessageRequest = new SendMessage();
			sendMessageRequest.setChatId(message.getChatId());
			sendMessageRequest.setText("https://upload.wikimedia.org/wikipedia/commons/a/a2/Bubble_Tea.png");
			SenderHelper.SendApiMethod(sendMessageRequest, TOKEN);

		}
		else if (command.startsWith(Commands.WILSONCOMMAND)) {
			dota.setId(SteamIds.WILSON);
			dota.send();

		}
		
		else if (command.startsWith(Commands.CHRISCOMMAND)) {
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
