package org.telegram.newbot.services;

import java.util.List;

import org.telegram.BotConfig;
import org.telegram.SenderHelper;
import org.telegram.api.methods.SendMessage;
import org.telegram.api.objects.Message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wilson.data.client.SteamApi;
import com.wilson.data.client.dota.DotaGetMatchDetailsRequest;
import com.wilson.data.client.dota.DotaGetMatchHistoryRequest;
import com.wilson.data.client.dota.response.MatchDetailResponse;
import com.wilson.data.client.dota.response.MatchHistoryResponse;
import com.wilson.data.client.user.SteamGetPlayerSummaryRequest;
import com.wilson.data.client.user.response.SteamPlayer;
import com.wilson.data.client.user.response.SteamPlayerSummary;
import com.wilson.data.shared.MatchDetailPlayer;

public class DotaService {
	private Message message;
	private Long mostRecentMatch;
	private String sendOutput = "";
	private String dotaId;
	private String dota32Id; 
	private String matchResult;
    private static final String TOKEN = BotConfig.TOKENNEWBOT;
	SteamApi api = new SteamApi("029021F53D5F974DA73A60F9300C3CF5");


	
	public DotaService(Message message){
		this.message = message;
	}
	
	public void setId(String id){
		this.dota32Id = (Long.parseLong(id) - (Long.parseLong("76561197960265728")) + "");
		this.dotaId = id;
	}
	
	public void send(){
	//get latest matches 
	DotaGetMatchHistoryRequest request = new DotaGetMatchHistoryRequest();
	request.setAccountId(dota32Id);
	MatchHistoryResponse matchHistoryResponse = (MatchHistoryResponse) api
			.execute(request);
	mostRecentMatch = matchHistoryResponse.getResult().getMatches().get(0).getMatchId();
	DotaGetMatchDetailsRequest matchRequest = new DotaGetMatchDetailsRequest();
	matchRequest.setMatchId(mostRecentMatch + "");
	
	MatchDetailResponse matchDetailResponse = (MatchDetailResponse) api
			.execute(matchRequest);
	List<MatchDetailPlayer> players = matchDetailResponse.getResult().getPlayers();

	//get Steam info 
	SteamPlayer playerSummary = new SteamPlayer();
	SteamGetPlayerSummaryRequest steamRequest = new SteamGetPlayerSummaryRequest();
	steamRequest.setSteamId(dotaId);
	SteamPlayerSummary playerSummaryResponse = (SteamPlayerSummary) api
			.execute(steamRequest);
	try{
	playerSummary = playerSummaryResponse.getResponse()
			.getPlayers().get(0);
	}
	catch(IndexOutOfBoundsException e){
		System.out.println("Could not find player with Id of " + dotaId);
		throw e;
	}
	
	
	//loop through all players in response object to find our player
	for (int i = 0; i < players.size(); i++) {
		if (players.get(i).getSteamId().equals(dotaId)) {
			if(matchDetailResponse.getResult().getRadiantWin()){
				if(players.get(i).getPlayerSlot() < 50){
					this.matchResult = "Win";
				}
				else{
					this.matchResult = "Lose";
			}
			}
				else{
					if(players.get(i).getPlayerSlot() < 50){
						this.matchResult = "Lose";
					}
					else{
						this.matchResult = "Win";
					}
				}
			

		sendOutput += 
				playerSummary.getPersonaName() + "'s latest match: " + mostRecentMatch + System.getProperty("line.separator") +
				"Kills: " + players.get(i).getKills() + System.getProperty("line.separator") +
				"Deaths: " + players.get(i).getDeaths() + System.getProperty("line.separator") +
				"Assists: " + players.get(i).getAssists() + System.getProperty("line.separator") +
				"Hero Damage: " + players.get(i).getHeroDamage() + System.getProperty("line.separator") +
				"Win/Lose: " + matchResult + System.getProperty("line.separator") +
				"[Dotabuff Match Stats]"+ "(http://www.dotabuff.com/matches/"+ mostRecentMatch + ")"  ;
		System.out.println(sendOutput);
		SendMessage sendMessageRequest = new SendMessage();
		sendMessageRequest.setChatId(message.getChatId());
	    sendMessageRequest.enableMarkdown(true);
		sendMessageRequest.setText(
				sendOutput);
		SenderHelper.SendApiMethod(sendMessageRequest, TOKEN);
		}
	}
}
}
