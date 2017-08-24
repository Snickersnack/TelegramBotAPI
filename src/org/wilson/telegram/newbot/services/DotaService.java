package org.wilson.telegram.newbot.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.wilson.telegram.BotConfig;
import org.wilson.telegram.newbot.models.DotaHeroes;
import org.wilson.telegram.newbot.models.DotaHeroesDetail;

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


/**
 * Makes several API calls to steam and parses the response
 * Leverages some classes from the dota steam app
 * Returns a formatted breakdown of the last match of the user
 */


public class DotaService {
	private Message message;
	private Long mostRecentMatch;
	private String sendOutput = "";
	private String dotaId;
	private String dota32Id;
	private String matchResult;
	private static final String TOKEN = BotConfig.TOKENNEWBOT;
	private String response = "";
	private HttpClient client;
	private HttpEntity entity;
	private String steamKey = "029021F53D5F974DA73A60F9300C3CF5";
	private List<DotaHeroesDetail> dotaHeroList = new ArrayList<DotaHeroesDetail>();
	private String heroName;
	private int matchDetailHero;
	private int highestGpm = 0 ;
	private int lowestGpm = 9999;
	private int highestHeroDamage = 0;
	private int lowestHeroDamage = 999999;
	private String highestDmgHero;
	private String highestGpmHero;
	private String lowestDmgHero;
	private String lowestGpmHero;
	private String highestDmgPlayer;
	private String highestGpmPlayer;
	private String lowestDmgPlayer;
	private String lowestGpmPlayer;
	private String playerPersonaName;
	private String heroHighestGpmMatchId;
	private String heroHighestDmgMatchId;
	private String heroLowestGpmMatchId;
	private String heroLowestDmgMatchId;
	private int  highestGpmHeroId;
	private int  highestDmgHeroId;
	private int  lowestGpmHeroId;
	private int  lowestDmgHeroId;
	private List<SteamPlayer> steamPlayerList;
	private Map<String, String> queryMap;
	MatchHistoryResponse matchHistoryResponse;

	
	
	
	DotaHeroes heroResponse;
	SteamApi api = new SteamApi(steamKey);

	public DotaService(Message message) {
		this.message = message;
	}
	
	public DotaService(){
		
	}
	
	public void setMessage(Message message){
		this.message = message;	
	}

	public void setId(String id) {
		System.out.println("id = " + id);
		this.dota32Id = (Long.parseLong(id)
				- (Long.parseLong("76561197960265728")) + "");
		this.dotaId = id;
	}

	public void setHeroes() {
		client = HttpClients.createDefault();
		HttpGet heroRequest = new HttpGet(
				"https://api.steampowered.com/IEconDOTA2_570/GetHeroes/v0001/?key=029021F53D5F974DA73A60F9300C3CF5&language=en_us");
		try {
			entity = client.execute(heroRequest).getEntity();
			if (entity == null) {
				System.out.println("Entity null");
			} else {
				response = EntityUtils.toString(entity);
				ObjectMapper mapper = new ObjectMapper();
				heroResponse = mapper.readValue(response, DotaHeroes.class);
				this.dotaHeroList = heroResponse.getResult().getDotaHeroes();

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				EntityUtils.consume(entity);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			heroRequest.releaseConnection();
		}
		System.out.println(heroResponse.getResult().getStatus());

	}

	public SendMessage send() {
		SendMessage sendMessageRequest = new SendMessage();
		try{
		// get latest matches
		DotaGetMatchHistoryRequest request = new DotaGetMatchHistoryRequest();
		request.setAccountId(dota32Id);
		System.out.println(dota32Id);
		matchHistoryResponse = (MatchHistoryResponse) api
				.execute(request);
		mostRecentMatch = matchHistoryResponse.getResult().getMatches().get(0)
				.getMatchId();
		DotaGetMatchDetailsRequest matchRequest = new DotaGetMatchDetailsRequest();
		matchRequest.setMatchId(mostRecentMatch + "");

		MatchDetailResponse matchDetailResponse = (MatchDetailResponse) api
				.execute(matchRequest);

		List<MatchDetailPlayer> players = matchDetailResponse.getResult()
				.getPlayers();

		// loop through all players in response object to find our player
		for (int i = 0; i < players.size(); i++) {
	
		//get our Current Player
		if (players.get(i).getSteamId().equals(dotaId)) {
			this.matchDetailHero= i;
			
			if (matchDetailResponse.getResult().getRadiantWin()) {
				if (players.get(i).getPlayerSlot() < 50) {
					this.matchResult = "Win";
				} else {
					this.matchResult = "Lose";
				}
			} else {
				if (players.get(i).getPlayerSlot() < 50) {
					this.matchResult = "Lose";
				} else {
					this.matchResult = "Win";
				}
			}
		}
		//Get highest GPM player
			if(players.get(i).getGoldPerMin() > this.highestGpm){
				this.highestGpm = players.get(i).getGoldPerMin();
				this.heroHighestGpmMatchId = players.get(i).getSteamId();
				this.highestGpmHeroId = players.get(i).getHeroId();
			}

			//Get lowest gpm
			if(players.get(i).getGoldPerMin() < this.lowestGpm){
				this.lowestGpm = players.get(i).getGoldPerMin();
				this.heroLowestGpmMatchId = players.get(i).getSteamId();
				this.lowestGpmHeroId = players.get(i).getHeroId();
				
			}
			//Get highest hero damage
			if(players.get(i).getHeroDamage() > this.highestHeroDamage){
				this.highestHeroDamage = players.get(i).getHeroDamage();
				this.heroHighestDmgMatchId = players.get(i).getSteamId();
				this.highestDmgHeroId = players.get(i).getHeroId();
				System.out.println("HighestHeroDamageMatchId: " + this.heroHighestDmgMatchId
						+System.getProperty("line.separator") +
						"HighestDmgHeroId: " + this.highestDmgHeroId
);
			}

			//Get lowest hero damage
			if(players.get(i).getHeroDamage() < this.lowestHeroDamage){
				this.lowestHeroDamage = players.get(i).getHeroDamage();
				this.heroLowestDmgMatchId = players.get(i).getSteamId();
				this.lowestDmgHeroId = players.get(i).getHeroId();

			}
			//Get Ids
			for (int j = 0; j < dotaHeroList.size(); j++) {
				if (this.highestGpmHeroId == dotaHeroList.get(j)
						.getId()) {				
					this.highestGpmHero = dotaHeroList.get(j).getLocalizedName();				
				}		
				if (lowestGpmHeroId == dotaHeroList.get(j)
						.getId()) {			
					this.lowestGpmHero = dotaHeroList.get(j).getLocalizedName();			
				}
				if (highestDmgHeroId == dotaHeroList.get(j)
						.getId()) {	
					this.highestDmgHero = dotaHeroList.get(j).getLocalizedName();
				}
				if (lowestDmgHeroId == dotaHeroList.get(j)
						.getId()) {
					this.lowestDmgHero = dotaHeroList.get(j).getLocalizedName();
				}
				if (players.get(matchDetailHero).getHeroId() == dotaHeroList.get(j)
						.getId()) {
					heroName = dotaHeroList.get(j).getLocalizedName();
				}
			}
		}
			// get Steam info
			SteamPlayer playerSummary = new SteamPlayer();
			queryMap = new HashMap<String,String>();
			queryMap.put("steamids", dotaId + "," + heroHighestGpmMatchId + "," + heroLowestGpmMatchId + "," + heroLowestDmgMatchId + "," + heroHighestDmgMatchId);
			System.out.println(queryMap);
			SteamGetPlayerSummaryRequest steamRequest = new SteamGetPlayerSummaryRequest();
			steamRequest.setSteamParameters(queryMap);
			SteamPlayerSummary playerSummaryResponse = (SteamPlayerSummary) api
					.execute(steamRequest);
			try{
			steamPlayerList = playerSummaryResponse.getResponse().getPlayers();

			for(int k = 0; k < steamPlayerList.size(); k++){
			System.out.println(steamPlayerList.get(k).getSteamId());
			if(steamPlayerList.get(k).getSteamId().equals(dotaId)){
				playerPersonaName = steamPlayerList.get(k).getPersonaName();

			}
			if(steamPlayerList.get(k).getSteamId().equals(heroHighestGpmMatchId)){
			this.highestGpmPlayer = steamPlayerList.get(k).getPersonaName();
			}
			if(steamPlayerList.get(k).getSteamId().equals(heroLowestGpmMatchId)){
			this.lowestGpmPlayer = steamPlayerList.get(k).getPersonaName();
			}
			if(steamPlayerList.get(k).getSteamId().equals(heroHighestDmgMatchId)){
			this.highestDmgPlayer = steamPlayerList.get(k).getPersonaName();
			}
			if(steamPlayerList.get(k).getSteamId().equals(heroLowestDmgMatchId)){
			this.lowestDmgPlayer = steamPlayerList.get(k).getPersonaName();
			}

			}
			}
			catch (Exception e){
				e.printStackTrace();
			}

			
			

			
		sendOutput += "<strong>" + playerPersonaName + " (" + mostRecentMatch + ")</strong>" + System.getProperty("line.separator") 
				+ "Hero: " + heroName 
				+ System.getProperty("line.separator")
				+ "KDA: " + players.get(matchDetailHero).getKills()
				+ "|" + players.get(matchDetailHero).getDeaths()
				+ "|" + players.get(matchDetailHero).getAssists()
				+ System.getProperty("line.separator") 
				+ playerPersonaName + " Hero Damage: " + players.get(matchDetailHero).getHeroDamage() + " (" + this.highestHeroDamage + " - " + this.lowestHeroDamage + ")" 
				+ System.getProperty("line.separator")
				+ playerPersonaName + " GPM: " + players.get(matchDetailHero).getGoldPerMin() + " (" + this.highestGpm + " - " + this.lowestGpm + ")"
				+ System.getProperty("line.separator")
				+ "Highest Hero Dmg: " + this.highestDmgHero + "|" + this.highestHeroDamage
				+ System.getProperty("line.separator") 
				+ "Lowest Hero Dmg: " + this.lowestDmgHero  + "|" + this.lowestHeroDamage
				+ System.getProperty("line.separator") 
				+ "Highest GPM: "  + this.highestGpmHero + "|" + this.highestGpm
				+ System.getProperty("line.separator")
				+ "Lowest GPM: " + this.lowestGpmHero+ "|" + this.lowestGpm
				+ System.getProperty("line.separator")

		+ "Win/Lose: " + matchResult 
		+ System.getProperty("line.separator")
		+ "Duration of match: " + String.format("%02d", matchDetailResponse.getResult().getDuration() / 60) + ":" + String.format("%02d", matchDetailResponse.getResult().getDuration() % 60)
		+ System.getProperty("line.separator")
		+ "<a href = " + "\"" + "https://yasp.co/matches/" + mostRecentMatch + "\">" + "OpenDota Match Stats</a>";
		if(matchHistoryResponse == null){
			sendOutput = "Unable to reach steamAPI";
		}
		System.out.println(sendOutput);
		sendMessageRequest.disableWebPagePreview();
		sendMessageRequest.setChatId(message.getChatId());
		sendMessageRequest.setParseMode(BotConfig.SENDMESSAGEMARKDOWN);
		sendMessageRequest.setText(sendOutput);
		}
		catch(Exception e){
			sendOutput = "Unable to reach Steam API";
			System.out.println(sendOutput);
			sendMessageRequest.disableWebPagePreview();
			sendMessageRequest.setChatId(message.getChatId());
			sendMessageRequest.setText(sendOutput);
			sendMessageRequest.setParseMode(BotConfig.SENDMESSAGEMARKDOWN);

			e.printStackTrace();
		}
		return sendMessageRequest;
	}
}
