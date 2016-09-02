package org.telegram.newbot.services;

import org.telegram.BotConfig;
import org.telegram.SenderHelper;
import org.telegram.api.methods.SendMessage;
import org.telegram.api.objects.Message;

public class BotUtilities {
    private static final String TOKEN = BotConfig.TOKENNEWBOT;
    private static final String BOTNAME = BotConfig.USERNAMENEWBOT;
	private Message message;
	private String recentCommands = "";
	
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
    		"/steam - List of players in chat currently in game on Steam" + System.getProperty("line.separator") + 
    		"/riki - Ricky's scrub stats in his last match" + System.getProperty("line.separator") +
    		"/ray - Ray's scrub stats in his last match" + System.getProperty("line.separator") + 
    		"/jdea - JDea's scrub stats in his last match" + System.getProperty("line.separator"));
    SenderHelper.SendApiMethod(sendMessageRequest, TOKEN);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	

}
