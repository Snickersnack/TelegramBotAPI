package org.telegram.newbot;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.telegram.BotConfig;
import org.telegram.Commands;
import org.telegram.SenderHelper;
import org.telegram.api.methods.BotApiMethod;
import org.telegram.api.methods.SendMessage;
import org.telegram.api.objects.Message;
import org.telegram.api.objects.Update;
import org.telegram.updateshandlers.UpdatesCallback;
import org.telegram.updatesreceivers.UpdatesThread;
import org.telegram.updatesreceivers.Webhook;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wilson.data.client.SteamApi;
import com.wilson.data.client.dota.DotaGetMatchDetailsRequest;
import com.wilson.data.client.dota.DotaGetMatchHistoryRequest;
import com.wilson.data.client.dota.response.MatchDetailResponse;
import com.wilson.data.client.dota.response.MatchHistoryResponse;
import com.wilson.data.shared.MatchDetailPlayer;

public class NewBotHandler implements UpdatesCallback{
	private Long mostRecentMatch;
    private static final String TOKEN = BotConfig.TOKENNEWBOT;
    private static final String BOTNAME = BotConfig.USERNAMENEWBOT;
    private static final String CONSUMERKEY = BotConfig.CONSUMERKEY;
    private static final String CONSUMERSECRET = BotConfig.CONSUMERSECRET;
    private static final String YELPTOKEN = BotConfig.YELPTOKEN;
    private static final String YELPTOKENSECRET = BotConfig.YELPTOKENSECRET;
    private String location = "";
    private String term = "";
    private String wordList = "";
    private NewBotYelpModels model;
	ObjectMapper mapper = new ObjectMapper();
	private String jsonInString = "";
	private String rikiId = (Long.parseLong("76561198000302345") - (Long.parseLong("76561197960265728")) + "");
	

    private static final boolean USEWEBHOOK = false;

    public NewBotHandler(Webhook webhook) {
        if (USEWEBHOOK) {
            webhook.registerWebhook(this, BOTNAME);
            SenderHelper.SendWebhook(Webhook.getExternalURL(BOTNAME), TOKEN);
        } else {
            SenderHelper.SendWebhook("", TOKEN);
            new UpdatesThread(TOKEN, this);
        }
    }
	
	@Override
	public void onUpdateReceived(Update update) {
		// TODO Auto-generated method stub
        handleUpdate(update);

	}


	@Override
	public BotApiMethod onWebhookUpdateReceived(Update update) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void handleUpdate(Update update) {

	      Message message = update.getMessage();
	      if (message.getText().startsWith(Commands.STOPCOMMAND)){
	      SendMessage sendMessageRequest = new SendMessage();
	        sendMessageRequest.setChatId(message.getChatId());
	        sendMessageRequest.setText("TESTING");
	        
	        SenderHelper.SendApiMethod(sendMessageRequest, TOKEN);
	      }
	      //use markdown after the methods.
	      else if(message.getText().startsWith(Commands.YELPCOMMAND)){
	    	  String commandSplit = message.getText();
	    	  String[] words = commandSplit.split("\\s+");
	    	  for (int i = 0; i < words.length; i++) {
	    	      words[i] = words[i].replaceAll("[^\\w]", "");
	    	  }
	    	  term = words[1];
	    	  location = words[2];
	    	  System.out.println("term: " + term + "location: " + location);
	    	  YelpAuth yelp = new YelpAuth(CONSUMERKEY, CONSUMERSECRET, YELPTOKEN, YELPTOKENSECRET);
	    	  String response = yelp.search(term, location);
	    	  System.out.println(response);
	    	 
	    	  //Map string to object
	    	  try {
				model = mapper.readValue(response, NewBotYelpModels.class);
//				Collection<NewBotYelpModels> readValues = new ObjectMapper().readValue(response, new TypeReference<Collection<NewBotYelpModels>>() { });

			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	  //Create Send message request to Telegram
		      SendMessage sendMessageRequest = new SendMessage();
		        sendMessageRequest.setChatId(message.getChatId());
		       if (model.getBusinesses().size() == 1){
		    	   sendMessageRequest.setText("Name: " + model.getBusinesses().get(0).getName() + System.getProperty("line.separator") +
		        							"Rating: " + model.getBusinesses().get(0).getRating() + System.getProperty("line.separator") +
		        							"URL: " + model.getBusinesses().get(0).getUrl());
		    	   SenderHelper.SendApiMethod(sendMessageRequest, TOKEN);
		       }
		       else if(model.getBusinesses().size()==2)
		       {sendMessageRequest.setText("Name: " + model.getBusinesses().get(0).getName() + System.getProperty("line.separator") +
					"Rating: " + model.getBusinesses().get(0).getRating() + System.getProperty("line.separator") +
					"URL: " + model.getBusinesses().get(0).getUrl() +
					System.getProperty("line.separator") +
					System.getProperty("line.separator") +
					"Name: " + model.getBusinesses().get(1).getName() + System.getProperty("line.separator") +
					"Rating: " + model.getBusinesses().get(1).getRating() + System.getProperty("line.separator") +
					"URL: " + model.getBusinesses().get(1).getUrl());}
		       else{
		        sendMessageRequest.setText("Name: " + model.getBusinesses().get(0).getName() + System.getProperty("line.separator") +
		        							"Rating: " + model.getBusinesses().get(0).getRating() + System.getProperty("line.separator") +
		        							"URL: " + model.getBusinesses().get(0).getUrl() +
		        							System.getProperty("line.separator") +
		        							System.getProperty("line.separator") +
		        							"Name: " + model.getBusinesses().get(1).getName() + System.getProperty("line.separator") +
		        							"Rating: " + model.getBusinesses().get(1).getRating() + System.getProperty("line.separator") +
		        							"URL: " + model.getBusinesses().get(1).getUrl() +
		        							System.getProperty("line.separator") +
		        							System.getProperty("line.separator") +
		        							"Name: " + model.getBusinesses().get(2).getName() + System.getProperty("line.separator") +
		        							"Rating: " + model.getBusinesses().get(2).getRating() + System.getProperty("line.separator") +
		        							"URL: " + model.getBusinesses().get(2).getUrl()
);

		        System.out.println("Rating: " + model.getBusinesses().get(0).getRating() + System.getProperty("line.separator"));
		        SenderHelper.SendApiMethod(sendMessageRequest, TOKEN);
	      }
	      }
	      else if (message.getText().startsWith(Commands.HELPCOMMAND)){
		      SendMessage sendMessageRequest = new SendMessage();
		        sendMessageRequest.setChatId(message.getChatId());
		        sendMessageRequest.setText("Only /stop is available");
		        SenderHelper.SendApiMethod(sendMessageRequest, TOKEN);
	      }
	      else if (message.getText().startsWith(Commands.MOSTRECENTMATCHCOMMAND)){

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

				}
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