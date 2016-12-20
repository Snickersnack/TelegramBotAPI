package org.wilson.telegram;

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
            telegramBotsApi.registerBot(new NewBotHandler());
        } catch (TelegramApiException e) {
            BotLogger.error("Error: ", e);
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
