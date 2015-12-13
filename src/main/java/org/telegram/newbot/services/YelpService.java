package org.telegram.newbot.services;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.telegram.BotConfig;
import org.telegram.SenderHelper;
import org.telegram.api.methods.SendMessage;
import org.telegram.api.objects.Message;
import org.telegram.newbot.YelpCache;
import org.telegram.newbot.auth.YelpAuth;
import org.telegram.newbot.models.NewBotYelpModels;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

	public void request(){
	  YelpCache.getInstance().clearYelpCache();
	  YelpCache.getInstance().setYelpPageState(0);
	  System.out.println("beginning of request");
	  String commandSplit = message.getText();
	  List<String> items = Arrays.asList(commandSplit.substring(6).split("@"));
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
	YelpCache.getInstance().setYelpList(model.getBusinesses());

	YelpCache.getInstance().setYelpPageState(1);

}

	public void send() {
		// Create Send message request to Telegram
		SendMessage sendMessageRequest = new SendMessage();
		sendMessageRequest.setChatId(message.getChatId());
		sendMessageRequest.enableMarkdown(true);
			int count = 0;
			
			if (YelpCache.getInstance().getYelpPageState() != 0) {
				count = (3 * YelpCache.getInstance().getYelpPageState()) - 3;
			}
			for (int i = 0; i < YelpCache.getInstance().getCurrentYelpList()
					.size(); i++) {
				count++;


				sendMessageRequest.setText(count
						+ ") "
						+ "[" +YelpCache.getInstance().getCurrentYelpList().get(i)
								.getName() + "]" + "(" + YelpCache.getInstance().getCurrentYelpList().get(i)
								.getUrl() + ")"
						+ System.getProperty("line.separator")
						+ "Rating: "
						+ YelpCache.getInstance().getCurrentYelpList().get(i)
								.getRating()
						+ System.getProperty("line.separator")
						+ "Reviews: " 
						+YelpCache.getInstance().getCurrentYelpList().get(i).getReviewCount()
						+ System.getProperty("line.separator") 

						+ System.getProperty("line.separator") 


				);

				System.out.println("Rating: "
						+ YelpCache.getInstance().getCurrentYelpList().get(i)
								.getRating()
						+ System.getProperty("line.separator"));
				
				SenderHelper.SendApiMethod(sendMessageRequest, TOKEN);
			}
		}
//	}
}
