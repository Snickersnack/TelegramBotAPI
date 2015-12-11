package org.telegram.newbot;

import java.util.List;

import org.telegram.BotConfig;
import org.telegram.SenderHelper;
import org.telegram.api.methods.SendMessage;
import org.telegram.api.objects.Message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wilson.data.client.SteamApi;
import com.wilson.data.client.dota.DotaGetMatchDetailsRequest;
import com.wilson.data.client.dota.DotaGetMatchHistoryRequest;
import com.wilson.data.client.dota.response.MatchDetailResponse;
import com.wilson.data.client.dota.response.MatchHistoryResponse;
import com.wilson.data.shared.MatchDetailPlayer;
import com.wilson.data.shared.MatchHistory;

public class DotaService {
	private Message message;
	private Long mostRecentMatch;
	private String jsonInString = "";
	private String rikiId = (Long.parseLong("76561198000302345") - (Long.parseLong("76561197960265728")) + "");
    private static final String TOKEN = BotConfig.TOKENNEWBOT;


	
	public DotaService(Message message){
		this.message = message;
	}
	
	public void send(){
		
	ObjectMapper mapper = new ObjectMapper();
	SteamApi api = new SteamApi("029021F53D5F974DA73A60F9300C3CF5");
	DotaGetMatchHistoryRequest request = new DotaGetMatchHistoryRequest();
	request.setAccountId(rikiId);
	MatchHistoryResponse matchHistoryResponse = (MatchHistoryResponse) api
			.execute(request);
	mostRecentMatch = matchHistoryResponse.getResult().getMatches().get(0).getMatchId();
	DotaGetMatchDetailsRequest matchRequest = new DotaGetMatchDetailsRequest();
	matchRequest.setMatchId(mostRecentMatch + "");
	MatchDetailResponse matchDetailResponse = (MatchDetailResponse) api
			.execute(matchRequest);
	List<MatchDetailPlayer> players = matchDetailResponse.getResult().getPlayers();
	for (int i = 0; i < players.size(); i++) {
		String temp = "";
		MatchDetailPlayer player;
		if (players.get(i).getSteamId().equals("76561198000302345")) {
			System.out.println("True");
		try {
			jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(players.get(i)) + " ";
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(jsonInString);
		SendMessage sendMessageRequest = new SendMessage();
		sendMessageRequest.setChatId(message.getChatId());
		sendMessageRequest.setText("Ricky's most recent Match ID: "
				+ mostRecentMatch + System.getProperty("line.separator")
				+ jsonInString);
		SenderHelper.SendApiMethod(sendMessageRequest, TOKEN);
		}
	}
}
}
