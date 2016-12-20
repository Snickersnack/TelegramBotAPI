package org.wilson.telegram.newbot.services;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.wilson.telegram.BotConfig;
import org.wilson.telegram.newbot.Cache;
import org.wilson.telegram.newbot.auth.YelpAuth;
import org.wilson.telegram.newbot.models.NewBotYelpModels;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Makes a call to the Yelp API and returns search results based on parameters passed in the command
 * 
 * 
 */

public class YelpService {
	private static final String TOKEN = BotConfig.TOKENNEWBOT;
	private static final String BOTNAME = BotConfig.USERNAMENEWBOT;
	private static final String CONSUMERKEY = BotConfig.CONSUMERKEY;
	private static final String CONSUMERSECRET = BotConfig.CONSUMERSECRET;
	private static final String YELPTOKEN = BotConfig.YELPTOKEN;
	private static final String YELPTOKENSECRET = BotConfig.YELPTOKENSECRET;
	private String location = "";
	private String term = "";
	private NewBotYelpModels model;
	private Message message;

	public YelpService(Message message) {
		this.message = message;
	}

	public void request(String query){
	  Cache.getInstance().clearYelpCache();
	  Cache.getInstance().setYelpPageState(0);
	  System.out.println("beginning of request");
	  List<String> items = Arrays.asList(query.split("@"));
	  for (int i = 0; i < items.size(); i++) {
		  
		  System.out.println(items.get(i));
	  }

	  term = items.get(0);
	  location = items.get(1);
	  System.out.println("term: " + term + "location: " + location);
	  
	  YelpAuth yelp = new YelpAuth(CONSUMERKEY, CONSUMERSECRET, YELPTOKEN, YELPTOKENSECRET);
	  
	  String response = yelp.search(term, location);

	  System.out.println(response);
	  ObjectMapper mapper = new ObjectMapper();

	  //Map string to object
	  try {
		this.model = mapper.readValue(response, NewBotYelpModels.class);

	} catch (JsonParseException e) {
		e.printStackTrace();
	} catch (JsonMappingException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	Cache.getInstance().setYelpList(model.getBusinesses());
	Cache.getInstance().setYelpPageState(1);

}

	public SendMessage send() throws TelegramApiException {
		// Create Send message request to Telegram
		SendMessage sendMessageRequest = new SendMessage();
		sendMessageRequest.setChatId(message.getChatId());
		sendMessageRequest.setParseMode("Markdown");
			int count = 0;
			
			if (Cache.getInstance().getYelpPageState() != 0) {
				count = (3 * Cache.getInstance().getYelpPageState()) - 3;
			}
			StringBuilder sb = new StringBuilder();
			sb.append("/yelpprev    |    /yelpnext" + System.getProperty("line.separator") + System.getProperty("line.separator"));

			for (int i = 0; i < Cache.getInstance().getCurrentYelpList()
					.size(); i++) {
				count++;

				sb.append(
//						count+ ") "
						 "[" +Cache.getInstance().getCurrentYelpList().get(i)
								.getName() + "]" + "(" + Cache.getInstance().getCurrentYelpList().get(i)
								.getUrl() + ")"
						+ " - " + Cache.getInstance().getCurrentYelpList().get(i).getLocation().getCity()
						+ " (" + Cache.getInstance().getCurrentYelpList().get(i)
								.getRating()
						+ "/" + Cache.getInstance().getCurrentYelpList().get(i).getReviewCount() + ")"
						+ System.getProperty("line.separator")



				);

				
				
				if(Cache.getInstance().getYelpDisplay()){
					sendMessageRequest.disableWebPagePreview();
				}else{
					sendMessageRequest.enableWebPagePreview();
				}
				
			}
			sendMessageRequest.setText(sb.toString());
			return sendMessageRequest;
		}
//	}
}
