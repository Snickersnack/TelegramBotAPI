package org.wilson.telegram;

import org.telegram.updateshandlers.UpdatesCallback;
import org.telegram.updatesreceivers.Webhook;
import org.wilson.telegram.newbot.NewBotHandler;
import org.wilson.telegram.newbot.YelpCache;

/**
 * Main initialization class
 * 
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
    	YelpCache.getInstance().init();	
    	UpdatesCallback newBot = new NewBotHandler(webhook);
    }
}
