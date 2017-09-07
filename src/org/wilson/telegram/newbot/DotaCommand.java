package org.wilson.telegram.newbot;

import org.telegram.telegrambots.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.wilson.telegram.Commands;
import org.wilson.telegram.SteamIds;
import org.wilson.telegram.newbot.services.DotaService;

public class DotaCommand {

	private static DotaService dota;

	
	public DotaCommand(Message message){
		dota = new DotaService(message);
		dota.setHeroes();
	}
	
	public static PartialBotApiMethod<?> parse(Message message){
		String command = message.getText().substring(1, message.getText().length()).toLowerCase();
		
      	SendMessage sendMessageRequest = new SendMessage();
		
      	


		if (command.startsWith("calvin")) {
			sendMessageRequest.setChatId(message.getChatId());
			sendMessageRequest.setText("https://upload.wikimedia.org/wikipedia/commons/a/a2/Bubble_Tea.png");
			return sendMessageRequest;
		}
		else if(command.startsWith("nicole")){
			SendPhoto sendP = new SendPhoto();
			sendP.setPhoto("AgADAwADqacxGz2uiE2koARw6cdKELH3hjEABHvI-EcZcbrTR1oBAAEC");
			sendP.setChatId(message.getChatId());		
			return sendP;	
			
		}
		System.out.println(command);
      	dota.setId(Cache.getInstance().getSteamIds().get(command));

		sendMessageRequest = dota.send();
		return sendMessageRequest;
	}
}
