package org.wilson.telegram.newbot.services;

import org.telegram.BotConfig;
import org.telegram.SenderHelper;
import org.telegram.SteamIds;
import org.telegram.api.methods.SendMessage;
import org.telegram.api.objects.Message;

import com.wilson.data.client.SteamApi;
import com.wilson.data.client.user.SteamGetPlayerSummaryRequest;
import com.wilson.data.client.user.response.SteamPlayer;
import com.wilson.data.client.user.response.SteamPlayerSummary;

public class SteamService {


	private Message message;
	private static final String TOKEN = BotConfig.TOKENNEWBOT;
	private String sendOutput = "";
	
	
	public SteamService(Message message){
		this.message = message;}
	

	public void send(){
	SendMessage sendMessageRequest = new SendMessage();
	sendMessageRequest.setChatId(message.getChatId());
	SteamApi api = new SteamApi("029021F53D5F974DA73A60F9300C3CF5");
	
	
	for (String steamId : SteamIds.LIST){
	SteamPlayer playerSummary = new SteamPlayer();
	SteamGetPlayerSummaryRequest request = new SteamGetPlayerSummaryRequest();
	request.setSteamId(steamId);
	SteamPlayerSummary playerSummaryResponse = (SteamPlayerSummary) api
			.execute(request);
	try{
	playerSummary = playerSummaryResponse.getResponse()
			.getPlayers().get(0);
	}
	catch(IndexOutOfBoundsException e){
		System.out.println("Could not find player with Id of " + steamId);
		throw e;
	}
	if (playerSummary.getGameExtraInfo() != null){

    sendOutput +=
	playerSummary.getPersonaName()
	+ " is now playing: " 
	+ playerSummary.getGameExtraInfo()
	+ System.getProperty("line.separator");
	}
	}
	if (sendOutput.equals("")){
		sendMessageRequest.setText("Nobody is playing games on Steam");
	}
	else{
	sendMessageRequest.setText("In-game players on Steam: " + System.getProperty("line.separator") +  sendOutput);
	}
	SenderHelper.SendApiMethod(sendMessageRequest, TOKEN);

	}
//}
}
