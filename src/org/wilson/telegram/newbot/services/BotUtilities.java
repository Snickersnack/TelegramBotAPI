package org.wilson.telegram.newbot.services;

import org.telegram.BotConfig;
import org.telegram.SenderHelper;
import org.telegram.api.methods.SendMessage;
import org.telegram.api.objects.Message;

public class BotUtilities {
    private static final String TOKEN = BotConfig.TOKENNEWBOT;
    private static final String BOTNAME = BotConfig.USERNAMENEWBOT;
	private Message message;
	
	public BotUtilities(Message message){
		this.message = message;
	}
	
	public void sendMenu(){
		try{
	SendMessage sendMessageRequest = new SendMessage();
    sendMessageRequest.setChatId(message.getChatId());
    sendMessageRequest.enableMarkdown(true);
    sendMessageRequest.setText("/yelp - Use this format: *search term* @ *location*" + System.getProperty("line.separator") +
    		"/next - Next page on the most recent yelp search" + System.getProperty("line.separator") + 
    		"/prev - Previous page on the most recent yelp search" + System.getProperty("line.separator") +
    		"/yelpdisplayon - Enables yelp link display" + System.getProperty("line.separator") + 
    		"/yelpdisplayoff - Disables yelp link display" + System.getProperty("line.separator") + 
    		"/steam - Checks for any stored users currently in game on Steam" + System.getProperty("line.separator") + 
    		"/user - Replace 'user' with a stored user (/riki, /jdea, etc) to get the stats of their last dota game " + System.getProperty("line.separator") + 
    		"/users - List of stored users" + System.getProperty("line.separator") +
    		"*Note that rapid requests against the Steam API will cause it to fail" + System.getProperty("line.separator"));
    SenderHelper.SendApiMethod(sendMessageRequest, TOKEN);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void sendUsers(){
		try{
			SendMessage sendMessageRequest = new SendMessage();
		    sendMessageRequest.setChatId(message.getChatId());
		    sendMessageRequest.enableMarkdown(true);
		    sendMessageRequest.setText(
		    		"Current Users:" + System.getProperty("line.separator") +
		    		"calvin" + System.getProperty("line.separator") + 
		    		"chris" + System.getProperty("line.separator") +
		    		"david" + System.getProperty("line.separator") + 
		    		"elton" + System.getProperty("line.separator") + 
		    		"jdea" + System.getProperty("line.separator") + 
		    		"ray" + System.getProperty("line.separator") + 
		    		"riki" + System.getProperty("line.separator") +
		    		"wilson" + System.getProperty("line.separator"));
		    SenderHelper.SendApiMethod(sendMessageRequest, TOKEN);
				}
				catch(Exception e){
					e.printStackTrace();
				}
	}
	
	

}
