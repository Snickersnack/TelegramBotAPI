package org.wilson.telegram;

import java.io.IOException;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.logging.BotLogger;
import org.wilson.telegram.newbot.NewBotHandler;
import org.wilson.telegram.newbot.Cache;

/**
 * Main initialization class
 * 
 */

public class Main {
//    private static Webhook webhook;

    public static void main(String[] args) throws TelegramApiRequestException {
    	Cache.getInstance().init();	
    	ApiContextInitializer.init();

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
        	NewBotHandler newBot = new NewBotHandler();
            telegramBotsApi.registerBot(newBot);
            Cache.getInstance().setBot(newBot);
        } catch (TelegramApiException e) {
            BotLogger.error("Error:  ", e);
        }
        
        
        
        BabMonitoring bab = new BabMonitoring();
        System.out.println("bab: " + bab);
        try {
			bab.receive();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        
        
//        if (BuildVars.useWebHook) {
//            webhook = new DefaultWebhook();
//        }

//        initBots();

//        if (BuildVars.useWebHook) {
//            webhook.startDebugServer();
            //webhook.startServer();
//        }
//    }

//    private static void initBots() {
//    	YelpCache.getInstance().init();	
//    	CallBack newBot = new NewBotHandler(webhook);
//    }
    }
}
