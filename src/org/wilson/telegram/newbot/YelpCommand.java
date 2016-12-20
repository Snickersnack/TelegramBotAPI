package org.wilson.telegram.newbot;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.wilson.telegram.Commands;
import org.wilson.telegram.newbot.services.YelpService;

public class YelpCommand {

	public SendMessage parse(Message message, String command) throws TelegramApiException {

		SendMessage sendMessageRequest = new SendMessage();
		sendMessageRequest.setChatId(message.getChatId());
		System.out.println(command);
		System.out.println(Commands.DISPLAYONCOMMAND);
		System.out.println(command.equals(Commands.DISPLAYONCOMMAND));

		if (command.startsWith(Commands.PREVCOMMAND)) {
			YelpService yelpService = new YelpService(message);
			switch (Cache.getInstance().getYelpPageState()) {
			case 0:
				sendMessageRequest.setText("Please do a yelp search");
				break;
			case 1:
				return null;
			case 2:
				Cache.getInstance().setYelpPageState(1);
				sendMessageRequest = yelpService.send();

				break;
			case 3:
				Cache.getInstance().setYelpPageState(2);
				sendMessageRequest = yelpService.send();

				break;
			}
		}
		
		else if (command.startsWith(Commands.DISPLAYONCOMMAND)) {
			Cache.getInstance().enableYelpDisplay();
			sendMessageRequest.setText("Yelp Display On");

		}
		else if (command.startsWith(Commands.DISPLAYOFFCOMMAND)) {
			Cache.getInstance().disableYelpDisplay();;
			sendMessageRequest.setText("Yelp Display Off");


		}
		else if (command.startsWith(Commands.NEXTCOMMAND)) {
			YelpService yelpService = new YelpService(message);
			switch (Cache.getInstance().getYelpPageState()) {
			case 0:
				sendMessageRequest.setChatId(message.getChatId());
				sendMessageRequest.setText("Please do a yelp search");
				break;
			case 1:
				if (Cache.getInstance().getYelpList().size() > 3) {
					Cache.getInstance().setYelpPageState(2);
					sendMessageRequest = yelpService.send();

					break;
				} else {
					return null;
				}
			case 2:
				if (Cache.getInstance().getYelpList().size() > 6) {
					Cache.getInstance().setYelpPageState(3);
					sendMessageRequest = yelpService.send();

					break;
				} else {
					return null;
				}
			case 3:
				return null;
			}

		}

		else {
			if(command.indexOf('@') < 0){
				sendMessageRequest.setText("Must of format: /yelp search query @ location");
				return sendMessageRequest;
			}
			String[] parseYelpCommand = command.split("@");
			for(String item : parseYelpCommand){
				item = item.replaceAll("\\s", "");
				if(item.isEmpty()){
					sendMessageRequest.setText("Must of format: /yelp search query @ location. Query and location must not be blank.");
					return sendMessageRequest;
				}
			}
			YelpService yelpService = new YelpService(message);
			try {
				yelpService.request(command);
			} catch (Exception e) {
				e.printStackTrace();
			}
			sendMessageRequest = yelpService.send();
		}
		return sendMessageRequest;
	}
}
