package org.wilson.telegram.newbot.services;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.wilson.telegram.BotConfig;
import org.wilson.telegram.newbot.models.DotaHeroes;
import org.wilson.telegram.newbot.models.DotaHeroesDetail;
import org.wilson.telegram.newbot.models.OpenDotaMatch;
import org.wilson.telegram.newbot.models.OpenDotaMatchHistory;
import org.wilson.telegram.newbot.models.OpenDotaPlayer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class OpenDotaService {
	
	private Message message;
	private String id;
	private List<DotaHeroesDetail> dotaHeroList;
	private String dotaId;
	private String dota32Id;
	private Integer parseRequest;
	
	public OpenDotaService(Message message) {
		this.message = message;
		dotaHeroList = new ArrayList<DotaHeroesDetail>();
	}
	
	public OpenDotaService(){
		dotaHeroList = new ArrayList<DotaHeroesDetail>();
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
	
	
//	public void getScreenShot(){
//
//		Image image = null;
//		try {
//			URL url = new URL("http://api.screenshotlayer.com/api/capture" +
//				    "? access_key = 46c7d8e6d4ecf2d366fe97ab59c92193" +
//				    "=& url = https://www.opendota.com/matches/3549601979/overview"+
//				    "& viewport = 1440x2800" +
//				    "& width = 1500");
//		    image = ImageIO.read(url);
//		} catch (IOException e) {
//		}
//		
//
//	}
	public void setHeroes() {
		
		HttpClient client = HttpClients.createDefault();
		HttpGet heroRequest = new HttpGet(
				"https://api.steampowered.com/IEconDOTA2_570/GetHeroes/v0001/?key=029021F53D5F974DA73A60F9300C3CF5&language=en_us");
		HttpEntity entity = null;
		try {
			 entity = client.execute(heroRequest).getEntity();
			if (entity == null) {
				System.out.println("Entity null");
			} else {
				String response = EntityUtils.toString(entity);
				ObjectMapper mapper = new ObjectMapper();
				DotaHeroes heroResponse = mapper.readValue(response, DotaHeroes.class);
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

	}
	
	public OpenDotaMatchHistory[] getOpenDotaMatchHistory(String dota32Id){
		HttpClient client = HttpClients.createDefault();
		HttpGet openDotaHistoryRequest = new HttpGet(
				"https://api.opendota.com/api/players/" + dota32Id + "/recentmatches");
		HttpEntity entity = null;
		OpenDotaMatchHistory[] openDotaMatchHistory = null;
		try {
			entity = client.execute(openDotaHistoryRequest).getEntity();
			if (entity == null) {
				System.out.println("Entity null");
			} else {
				String response = EntityUtils.toString(entity);
				ObjectMapper mapper = new ObjectMapper();
				openDotaMatchHistory = mapper.readValue(response, OpenDotaMatchHistory[].class);
				

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
			openDotaHistoryRequest.releaseConnection();
		}
		return openDotaMatchHistory;
	}
	
	public OpenDotaMatch getOpenDotaMatch(Long matchId){
		HttpClient client = HttpClients.createDefault();
		HttpGet openDotaMatchRequest = new HttpGet(
				"https://api.opendota.com/api/matches/" + matchId);
		HttpPost openDotaMatchPost = new HttpPost("https://api.opendota.com/api/request/" + matchId);
		HttpEntity entity = null;
		OpenDotaMatch openDotaMatch = null;
		HttpResponse postResponse = null;
		try {
			postResponse = client.execute(openDotaMatchPost);
			parseRequest = postResponse.getStatusLine().getStatusCode();
			entity = client.execute(openDotaMatchRequest).getEntity();
			if (entity == null) {
				System.out.println("Entity null");
			} else {
				String response = EntityUtils.toString(entity);
				ObjectMapper mapper = new ObjectMapper();
				openDotaMatch = mapper.readValue(response, OpenDotaMatch.class);

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
			openDotaMatchRequest.releaseConnection();
			openDotaMatchPost.releaseConnection();
		}
		return openDotaMatch;
	}
	
	
	public SendMessage send() {
		SendMessage sendMessageRequest = new SendMessage();
		OpenDotaMatchHistory[] openDotaMatchHistory = getOpenDotaMatchHistory(dota32Id);
		OpenDotaMatch openDotaMatch = getOpenDotaMatch(openDotaMatchHistory[0].getMatch_id());
		String personaName = null;
		Integer kills = 0;
		Integer deaths = 0;
		Integer assists = 0;
		Integer party_id = 0;
		boolean is_roaming = false;
		String lane_role = null;
		String hero = null;
		String flavorText = null;
		Integer mostGPM = 0;
		Integer mostDPS = 0;
		Integer mostObsPlaced = 0;
		Integer mostSentriesPlaced = 0;
		Integer mostTowerKills = 0;
		Integer win=0;

		

		Integer max = 0;

		OpenDotaMatchHistory match = openDotaMatchHistory[0];
		Long startTime = match.getStart_time();
		Date dateStart = new Date(startTime * 1000);
		LocalDateTime ldt = LocalDateTime.ofInstant(dateStart.toInstant(), ZoneId.of("America/Los_Angeles"));
		Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		String playDate = df.format(out);
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		for(OpenDotaPlayer player : openDotaMatch.getPlayers()){			
			if(player.getGold_per_min() != null){

				if(player.getGold_per_min() > mostGPM){
					mostGPM = player.getGold_per_min();
				}
			}
			
			if(player.getHero_damage() != null){
				if(player.getHero_damage() > mostDPS){
					mostDPS = player.getHero_damage();
				}
			}

			if(player.getObserver_uses() != null){
				if(player.getObserver_uses() > mostObsPlaced){
					mostObsPlaced = player.getObserver_uses();
				}
			}

			if(player.getSentry_uses() != null){
				if(player.getSentry_uses() > mostSentriesPlaced){
					mostSentriesPlaced = player.getSentry_uses();
				}
			}
			
			if(player.getTower_kills() != null){
				if(player.getTower_kills() > mostTowerKills){
					mostTowerKills = player.getTower_kills();
				}
			}
			
			
			if(player.getAccount_id() != null){
				
				if(player.getAccount_id().toString().equals(dota32Id)){
					map.put("Sentries placed", player.getSentry_uses());
					map.put("Observers placed", player.getObserver_uses());
					map.put("Roshans killed", player.getRoshan_kills());
					map.put("Camps Stacked", player.getCamps_stacked());
					map.put("Couriers killed", player.getCourier_kills());
					party_id = player.getParty_id();
					
					if(player.getLane_role() != null){
						if(player.getLane_role() == 1){
							lane_role = "Safe Lane";
							for(Map.Entry<String, Integer> entry : map.entrySet()){
								if(entry.getValue()>max){
									if(!entry.getKey().equals("Sentries") || !entry.getKey().equals("Observers")){
										flavorText = entry.getKey() + ": " + entry.getValue();
										max = entry.getValue();
									}
								}
							}
						}else if(player.getLane_role() == 2){
							lane_role = "Mid Lane";
							for(Map.Entry<String, Integer> entry : map.entrySet()){
								if(entry.getValue()>max){
									if(!entry.getKey().equals("Sentries") || !entry.getKey().equals("Observers")){
										flavorText = entry.getKey() + ": " + entry.getValue();
										max = entry.getValue();

									}
								}
							}
						}
						else if(player.getLane_role() ==3){
							lane_role = "Offlane";
							for(Map.Entry<String, Integer> entry : map.entrySet()){
								if(entry.getValue()>max){
									flavorText = entry.getKey() + ": " + entry.getValue();
									max = entry.getValue();

								}
							}
						}else{
							lane_role = "Support";
							for(Map.Entry<String, Integer> entry : map.entrySet()){
								if(entry.getValue()>max){
									flavorText = entry.getKey() + ": " + entry.getValue();
									max = entry.getValue();

								}
							}
						}
					}
					
					map.put("Gpm", player.getGold_per_min());
					map.put("Hero Damage", player.getHero_damage());
					map.put("Tower Kills", player.getTower_kills());
					for(DotaHeroesDetail steamHero : dotaHeroList){
						if(steamHero.getId() == player.getHero_id()){
							hero = steamHero.getLocalizedName();
						}
					}
					personaName = player.getPersonaName();
					kills = player.getKills();
					deaths = player.getDeaths();
					assists = player.getAssists();
					if(player.getWin()==1){
						win = 1;
					}
					
				}
			}

		}
		StringBuilder partyMembers = new StringBuilder();
		for(OpenDotaPlayer player : openDotaMatch.getPlayers()){
			try{
				if(player.getPersonaName() != null){
					if(player.getParty_id()==party_id && !player.getPersonaName().equals(personaName)){
						partyMembers.append(player.getPersonaName()+ " ");

					}

				}
			}catch(Exception e){
				partyMembers.append("null" + " ");
			}


			
		}
		
		
		Integer playerSentriesPlaced = map.get("Sentries placed");
		Integer playerObserversPlaced = map.get("Observers placed");
		Integer playerHeroDamage = map.get("Hero Damage");
		Integer playerGPM = map.get("Gpm");
		Integer playerTowerKills = map.get("Tower Kills");
		
		String sendOutput=	
				"<strong>" + personaName + " (" + playDate + ")</strong>"

				+ System.getProperty("line.separator") +

				hero + ": " + kills
				+ "|" + deaths
				+ "|" + assists
				+ System.getProperty("line.separator")
				+"Hero Damage: " + playerHeroDamage
				+ System.getProperty("line.separator"); 		

		
		if(playerHeroDamage >= mostDPS){
					sendOutput+=
							" (highest)"
							+ System.getProperty("line.separator");

				}
		sendOutput+=
				"GPM: " + playerGPM;
		
		if(playerGPM >= mostGPM){
			sendOutput+=
					" (highest)";

		}
		sendOutput+= System.getProperty("line.separator");
		
		sendOutput+= 
				"Lane: " + lane_role;
		
		if(is_roaming){
			sendOutput+=
					"(roaming)";
			}
		sendOutput+= System.getProperty("line.separator");
		
		if(flavorText!=null){
			sendOutput+=flavorText 
					+ System.getProperty("line.separator");

		}
		if(playerSentriesPlaced != null){
			if(playerSentriesPlaced >= mostSentriesPlaced){
				sendOutput+=
						"Had most Sentries placed in game: " + playerSentriesPlaced
						+ System.getProperty("line.separator");

			}	
		}

		if(playerObserversPlaced != null){
			if(playerObserversPlaced >= mostObsPlaced){
				sendOutput+=
						"Had most Observers placed in game: " + playerObserversPlaced
						+ System.getProperty("line.separator");
	
			}
		}

		if(playerTowerKills != null){
			if(playerTowerKills >= mostTowerKills){
				sendOutput+=
						"Had highest tower kills in game: " + playerTowerKills
						+ System.getProperty("line.separator");
	
			}
		}

		if(!partyMembers.toString().equals("")){
			sendOutput+="Party: " + partyMembers
			+ System.getProperty("line.separator");
		}
		
		if(win == 0){
			sendOutput+=

					"Lost"
					+ System.getProperty("line.separator");
		}else{
			sendOutput+=

					"Won"
					+ System.getProperty("line.separator");
		}
		sendOutput+=

				"Duration of match: " + String.format("%02d", match.getDuration() / 60) + ":" + String.format("%02d", match.getDuration() % 60)
				+ System.getProperty("line.separator")+
								
				"Parse Request: " + parseRequest 
				+ System.getProperty("line.separator") +

				"<a href = " + "\"" + "https://yasp.co/matches/" + openDotaMatchHistory[0].getMatch_id() + "\">" + "OpenDota Match Stats</a>";
		
		sendMessageRequest.disableWebPagePreview();
		sendMessageRequest.setChatId(message.getChatId());
		sendMessageRequest.setParseMode(BotConfig.SENDMESSAGEMARKDOWN);
		sendMessageRequest.setText(sendOutput);
		return sendMessageRequest;
	}
	
}
