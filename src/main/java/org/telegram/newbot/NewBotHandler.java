package org.telegram.newbot;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.telegram.BotConfig;
import org.telegram.Commands;
import org.telegram.SenderHelper;
import org.telegram.api.methods.BotApiMethod;
import org.telegram.api.methods.SendMessage;
import org.telegram.api.objects.Message;
import org.telegram.api.objects.Update;
import org.telegram.newbot.auth.YelpAuth;
import org.telegram.newbot.models.NewBotYelpModels;
import org.telegram.updateshandlers.UpdatesCallback;
import org.telegram.updatesreceivers.UpdatesThread;
import org.telegram.updatesreceivers.Webhook;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wilson.data.client.SteamApi;
import com.wilson.data.client.dota.DotaGetMatchDetailsRequest;
import com.wilson.data.client.dota.DotaGetMatchHistoryRequest;
import com.wilson.data.client.dota.response.MatchDetailResponse;
import com.wilson.data.client.dota.response.MatchHistoryResponse;
import com.wilson.data.shared.MatchDetailPlayer;

public class NewBotHandler implements UpdatesCallback{
	private Long mostRecentMatch;
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
	ObjectMapper mapper = new ObjectMapper();
	private String jsonInString = "";
	private String rikiId = (Long.parseLong("76561198000302345") - (Long.parseLong("76561197960265728")) + "");
	private int utilityId;
	

    private static final boolean USEWEBHOOK = false;

    public NewBotHandler(Webhook webhook) {
        if (USEWEBHOOK) {
            webhook.registerWebhook(this, BOTNAME);
            SenderHelper.SendWebhook(Webhook.getExternalURL(BOTNAME), TOKEN);
        } else {
            SenderHelper.SendWebhook("", TOKEN);
            new UpdatesThread(TOKEN, this);
        }
    }
	
	@Override
	public void onUpdateReceived(Update update) {
		// TODO Auto-generated method stub
        handleUpdate(update);

	}


	@Override
	public BotApiMethod onWebhookUpdateReceived(Update update) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void handleUpdate(Update update) {
		
	      Message message = update.getMessage();
	      System.out.println("Message gotten. Chat ID = " + message.getChatId());
	      CommandParser commandParser = new CommandParser(message);
	      commandParser.push();





		}


}