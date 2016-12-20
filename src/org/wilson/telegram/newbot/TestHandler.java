package org.wilson.telegram.newbot;

import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.wilson.telegram.BotConfig;

public class TestHandler extends TelegramLongPollingBot {




        @Override
        public void onUpdateReceived(Update arg0) {
                // TODO Auto-generated method stub
        		System.out.println("ayy");
        }
        
    	@Override
    	public String getBotUsername() {
    		// TODO Auto-generated method stub
    		return BotConfig.USERNAMENEWBOT;
    	}

    	@Override
    	public String getBotToken() {
    		// TODO Auto-generated method stub
    		return BotConfig.TOKENNEWBOT;
    	}


	
}
