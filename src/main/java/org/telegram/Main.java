package org.telegram;

import org.telegram.newbot.NewBotHandler;
import org.telegram.newbot.YelpCache;
import org.telegram.updateshandlers.UpdatesCallback;
import org.telegram.updatesreceivers.Webhook;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Main class to create all bots
 * @date 20 of June of 2015
 */
public class Main {
    private static Webhook webhook;

    public static void main(String[] args) {

        if (BuildVars.useWebHook) {
            webhook = new Webhook();
        }

        initBots();

        if (BuildVars.useWebHook) {
            webhook.startDebugServer();
            //webhook.startServer();
        }
    }

    private static void initBots() {
//        UpdatesCallback weatherBot = new WeatherHandlers(webhook);
//        UpdatesCallback transifexBot = new TransifexHandlers(webhook);
//        UpdatesCallback filesBot = new FilesHandlers(webhook);
//        UpdatesCallback directionsBot = new DirectionsHandlers(webhook);
    	YelpCache.getInstance().init();	
    	UpdatesCallback newBot = new NewBotHandler(webhook);
    }
}
