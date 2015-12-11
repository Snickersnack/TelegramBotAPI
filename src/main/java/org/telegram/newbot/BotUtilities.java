package org.telegram.newbot;

import java.util.List;

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
		
	}
	
	public void sendHelp(){
	SendMessage sendMessageRequest = new SendMessage();
    sendMessageRequest.setChatId(message.getChatId());
    sendMessageRequest.setText("/yelp - Use this format: Search term & location" + System.getProperty("line.separator") +
    		"/riki - returns Ricky's god awful stats on his last match" + System.getProperty("line.separator"));
    SenderHelper.SendApiMethod(sendMessageRequest, TOKEN);
	}
	
	
	public void sendRecent(List<String> mostRecentCommands){
	SendMessage sendMessageRequest = new SendMessage();
    sendMessageRequest.setChatId(message.getChatId());
    for (int i=0; i<mostRecentCommands.size(); i++){
    	recentCommands.concat(mostRecentCommands.get(i) + " ");
    }
    sendMessageRequest.setText(recentCommands);
    SenderHelper.SendApiMethod(sendMessageRequest, TOKEN);
	}
}
