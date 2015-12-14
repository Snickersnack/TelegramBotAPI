package org.telegram.newbot.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.telegram.BotConfig;
import org.telegram.SenderHelper;
import org.telegram.api.methods.SendMessage;
import org.telegram.api.objects.Message;
import org.telegram.newbot.models.DotaHeroes;
import org.telegram.newbot.models.DotaHeroesDetail;
import org.telegram.newbot.models.DotaHeroesResponseModel;

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
	private String response = "";
	private HttpClient client;
	private HttpEntity entity;
	private String steamKey = "029021F53D5F974DA73A60F9300C3CF5";
	private List<DotaHeroesDetail> dotaHeroList = new ArrayList<DotaHeroesDetail>();
	private String heroName;
	DotaHeroes heroResponse;
	SteamApi api = new SteamApi(steamKey);

	public DotaService(Message message) {
		this.message = message;
	}

	public void setId(String id) {
		this.dota32Id = (Long.parseLong(id)
				- (Long.parseLong("76561197960265728")) + "");
		this.dotaId = id;
	}

	public void setHeroes() {
		// HeroesRequest heroRequest = new HeroesRequest();
		// DotaHeroes heroResponse = (DotaHeroes) api
		// .execute(heroRequest);

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

	// dotaHeroList = heroResponse.getResult().getDotaHeroes();
	// System.out.println(heroResponse.getResult().getStatus());
	// System.out.println(heroResponse.getResult().getDotaHeroes().size());
	// System.out.println(heroResponse.getResult().getDotaHeroes().get(1).getId());
	// System.out.println(heroResponse.getResult().getDotaHeroes().get(1).getLocalizedName());
	//
	// }

	public void send() {
		// get latest matches
		DotaGetMatchHistoryRequest request = new DotaGetMatchHistoryRequest();
		request.setAccountId(dota32Id);
		MatchHistoryResponse matchHistoryResponse = (MatchHistoryResponse) api
				.execute(request);
		mostRecentMatch = matchHistoryResponse.getResult().getMatches().get(0)
				.getMatchId();
		DotaGetMatchDetailsRequest matchRequest = new DotaGetMatchDetailsRequest();
		matchRequest.setMatchId(mostRecentMatch + "");

		MatchDetailResponse matchDetailResponse = (MatchDetailResponse) api
				.execute(matchRequest);
		List<MatchDetailPlayer> players = matchDetailResponse.getResult()
				.getPlayers();

		// get Steam info
		SteamPlayer playerSummary = new SteamPlayer();
		SteamGetPlayerSummaryRequest steamRequest = new SteamGetPlayerSummaryRequest();
		steamRequest.setSteamId(dotaId);
		SteamPlayerSummary playerSummaryResponse = (SteamPlayerSummary) api
				.execute(steamRequest);
		try {
			playerSummary = playerSummaryResponse.getResponse().getPlayers()
					.get(0);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Could not find player with Id of " + dotaId);
			throw e;
		}

		// loop through all players in response object to find our player
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getSteamId().equals(dotaId)) {
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

				for (int j = 0; j < dotaHeroList.size(); j++) {
					if (players.get(i).getHeroId() == dotaHeroList.get(j)
							.getId()) {
						
						heroName = dotaHeroList.get(j).getLocalizedName();
						System.out.println(dotaHeroList.get(j).getId());
						
					}
				}
				sendOutput += playerSummary.getPersonaName()
						+ "'s latest match: " + mostRecentMatch
						+ System.getProperty("line.separator") + "Hero: "
						+ heroName + System.getProperty("line.separator")
						+ "Kills: " + players.get(i).getKills()
						+ System.getProperty("line.separator") + "Deaths: "
						+ players.get(i).getDeaths()
						+ System.getProperty("line.separator") + "Assists: "
						+ players.get(i).getAssists()
						+ System.getProperty("line.separator")
						+ "Hero Damage: " + players.get(i).getHeroDamage()
						+ System.getProperty("line.separator") + "Win/Lose: "
						+ matchResult + System.getProperty("line.separator")
						+ "[Dotabuff Match Stats]"
						+ "(http://www.dotabuff.com/matches/" + mostRecentMatch
						+ ")";
				System.out.println(sendOutput);
				SendMessage sendMessageRequest = new SendMessage();
				sendMessageRequest.setChatId(message.getChatId());
				sendMessageRequest.enableMarkdown(true);
				sendMessageRequest.setText(sendOutput);
				SenderHelper.SendApiMethod(sendMessageRequest, TOKEN);
			}
		}
	}
}
