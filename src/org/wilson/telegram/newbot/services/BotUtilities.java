package org.wilson.telegram.newbot.services;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.wilson.telegram.BotConfig;
import org.wilson.telegram.newbot.Cache;

public class BotUtilities {
    private static final String TOKEN = BotConfig.TOKENNEWBOT;
    private static final String BOTNAME = BotConfig.USERNAMENEWBOT;
    SendMessage sendMessageRequest;
	private Message message;
	
	public BotUtilities(Message message){
		this.message = message;
		sendMessageRequest = new SendMessage();
	}
	
	public SendMessage sendHelp(){
		 
		try{
    sendMessageRequest.setChatId(message.getChatId());
    sendMessageRequest.enableMarkdown(true);
    sendMessageRequest.setText(
    		"/start - Events can only be shared by host " + System.getProperty("line.separator") +
    		"/yelp - Search term @ location. Search term and location must be filled" + System.getProperty("line.separator") +
    		"/yelpnext - Next page on the most recent yelp search" + System.getProperty("line.separator") + 
    		"/yelpprev - Previous page on the most recent yelp search" + System.getProperty("line.separator") +
    		"/yelpdisplayon - Allows link and images to be displayed for first result" + System.getProperty("line.separator") + 
    		"/yelpdisplayoff - Disables yelp link display" + System.getProperty("line.separator") + 
    		"/steam - Checks if /users are currently in game in Steam " + System.getProperty("line.separator") + 
    		"/user - Replace *user* with a stored /user (/riki, /jdea ,etc) to get the stats of their last dota game " + System.getProperty("line.separator") + 
    		"/users - List of stored users" + System.getProperty("line.separator") +
    		" " + System.getProperty("line.separator") +
    		"*Notes:* Using @BOYZbot will allow you to create events from anywhere" + System.getProperty("line.separator")

    		);

    		System.out.println(sendMessageRequest.getText());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return sendMessageRequest;

	}
	
	public SendMessage sendUsers(){

		try{
		    sendMessageRequest.setChatId(message.getChatId());
		    sendMessageRequest.enableMarkdown(true);
		    StringBuilder sb = new StringBuilder();
		    sb.append("*Current tracked players:*" + System.getProperty("line.separator"));
		    for(String item : Cache.getInstance().getPlayers()){
	    		sb.append(item + System.getProperty("line.separator"));

		    }
		    sendMessageRequest.setText(sb.toString());
				}
				catch(Exception e){
					e.printStackTrace();
				}
	    return sendMessageRequest;

	}
	
	

}
