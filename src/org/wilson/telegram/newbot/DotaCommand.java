package org.wilson.telegram.newbot;

import org.telegram.telegrambots.api.methods.send.SendMessage;
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
	
	public static SendMessage parse(Message message){
		String command = message.getText().toLowerCase();
		
      	SendMessage sendMessageRequest = new SendMessage();
		if (command.startsWith(Commands.RIKICOMMAND)) {
			try{
			dota.setId(SteamIds.RIKI);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		else if (command.startsWith(Commands.JDEACOMMAND)) {
			dota.setId(SteamIds.JDEA);

		}
		else if (command.startsWith(Commands.RAYCOMMAND)) {
			dota.setId(SteamIds.BRO);

		}
		else if (command.startsWith(Commands.ELTONCOMMAND)) {
			dota.setId(SteamIds.ELTON);

		}
		else if (command.startsWith(Commands.DAVIDCOMMAND)) {
			dota.setId(SteamIds.DAVID);
		}

		else if (command.startsWith(Commands.CALVINCOMMAND)) {
			sendMessageRequest.setChatId(message.getChatId());
			sendMessageRequest.setText("https://upload.wikimedia.org/wikipedia/commons/a/a2/Bubble_Tea.png");
			return sendMessageRequest;

		}
		else if (command.startsWith(Commands.WILSONCOMMAND)) {
			dota.setId(SteamIds.WILSON);

		}
		sendMessageRequest = dota.send();
		return sendMessageRequest;
	}
}
