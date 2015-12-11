package org.telegram.newbot;

import java.io.IOException;

import org.telegram.BotConfig;
import org.telegram.SenderHelper;
import org.telegram.api.methods.SendMessage;
import org.telegram.api.objects.Message;
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
    private String wordList = "";
    private NewBotYelpModels model;
	private Message message;
    
    public YelpService(Message message){
    	this.message = message;
    }
    
    public void send(){
	  String commandSplit = message.getText();
	  String[] words = commandSplit.substring(6).split("&");	    	  
	  for (int i = 0; i < words.length; i++) {
		  System.out.println(words[i]);
	  }
	  
	  term = words[0];
	  location = words[1];
	  System.out.println("term: " + term + "location: " + location);
	  YelpAuth yelp = new YelpAuth(CONSUMERKEY, CONSUMERSECRET, YELPTOKEN, YELPTOKENSECRET);
	  String response = yelp.search(term, location);
	  System.out.println(response);
	  ObjectMapper mapper = new ObjectMapper();

	  //Map string to object
	  try {
		model = mapper.readValue(response, NewBotYelpModels.class);

	} catch (JsonParseException e) {
		e.printStackTrace();
	} catch (JsonMappingException e) {
		e.printStackTrace();
	} catch (IOException e) {
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
     
    else{
  	int count = 0;
  	
  	for (int i =0; i < model.getBusinesses().size(); i++){
  		count++;

      sendMessageRequest.setText(count + ".) " + model.getBusinesses().get(i).getName() + System.getProperty("line.separator") +
      							"  " + "Rating: " + model.getBusinesses().get(i).getRating() + System.getProperty("line.separator") +
      							"  " + "URL: " + model.getBusinesses().get(i).getUrl() +
      							System.getProperty("line.separator") +  System.getProperty("line.separator") 

);

      System.out.println("Rating: " + model.getBusinesses().get(i).getRating() + System.getProperty("line.separator"));
      SenderHelper.SendApiMethod(sendMessageRequest, TOKEN);
}
}
}
}

