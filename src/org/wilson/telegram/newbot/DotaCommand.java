package org.wilson.telegram.newbot;

import java.util.Random;

import org.telegram.telegrambots.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.wilson.telegram.newbot.services.OpenDotaService;

public class DotaCommand {

	private static OpenDotaService dota;

	
	public DotaCommand(Message message){
		dota = new OpenDotaService(message);
		dota.setHeroes();
	}
	
	public static PartialBotApiMethod<?> parse(Message message){
		String command = message.getText().substring(1, message.getText().length()).toLowerCase();
		
      	SendMessage sendMessageRequest = new SendMessage();
		
      	


		if (command.startsWith("calvin")) {

			String[] arr = {"AgADAQADy6cxG_JViEVEtzvjow1MqCtd9y8ABFD7nFgNoA4l1tQBAAEC", "AgADAQADzKcxG_JViEXgoVlKGLqVHGkd9y8ABNSENcAuNEFZAkAAAgI", "AgADAQADzacxG_JViEWP_ZDdNpoFheaN3i8ABHzcCJBpClcUsYEBAAEC",
					"AgADAQADzqcxG_JViEXihtjJ2mIZ3dgS9y8ABIApyAgYGinSL6AAAgI", "AgADAQADz6cxG_JViEWoybyoFSDbvqcS9y8ABBRb2AShWsNIgZ4AAgI", "AgADAQAD0KcxG_JViEXcaLXSMK1oluEb9y8ABGcrJCdlHi3ZokMAAgI", 
					"AgADAQAD0acxG_JViEUmx77cPzz_RWwc9y8ABBSsYlJWYBhiLUYAAgI", "AgADAQAD1KcxG_JViEVqCVSdBusMbPEY9y8ABAFdYK2zf725oEcAAgI", "AgADAQAD06cxG_JViEWHMahVGrondrcg9y8ABBKIJnTjA0wICkIAAgI"};
			Random rand = new Random();
			int  n = rand.nextInt(arr.length);
			SendPhoto sendP = new SendPhoto();
			sendP.setPhoto(arr[n]);
			sendP.setChatId(message.getChatId());		
			return sendP;	
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
