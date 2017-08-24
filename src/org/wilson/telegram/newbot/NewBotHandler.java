package org.wilson.telegram.newbot;

import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.wilson.telegram.BotConfig;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Re-implemented handler class. Deals with message updates
 * 
 */

public class NewBotHandler extends TelegramLongPollingBot {
	private static final String TOKEN = BotConfig.TOKENNEWBOT;
	private static final String BOTNAME = BotConfig.USERNAMENEWBOT;

	ObjectMapper mapper = new ObjectMapper();

	private static final boolean USEWEBHOOK = false;

	// public NewBotHandler(Webhook webhook) {
	// if (USEWEBHOOK) {
	// webhook.registerWebhook(this, BOTNAME);
	// SenderHelper.SendWebhook(Webhook.getExternalURL(BOTNAME), TOKEN);
	// } else {
	// SenderHelper.SendWebhook("", TOKEN);
	// new UpdatesThread(TOKEN, this);
	// }
	// }

	public void onUpdateReceived(Update update) {
		// TODO Auto-generated method stub
		System.out.println("heres' the update: " + update);
		try {
			BotApiMethod<?> msg = handleUpdate(update);
			if (msg != null) {
				System.out.println("UPDATE FOR DEBUGGING: " + update);
				System.out.println("MSG FOR DEBUGGING" + msg);
				executeMessage(msg);
			}

		} catch (TelegramApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public BotApiMethod onWebhookUpdateReceived(Update update) {
		// TODO Auto-generated method stub
		return null;
	}

	public BotApiMethod<?> handleUpdate(Update update)
			throws TelegramApiException {
		
		SendMessage sendMessageRequest = new SendMessage();
		
		if(update.hasChosenInlineQuery()){		
		}
		else if (update.hasInlineQuery()) {
		}
		else if (update.hasCallbackQuery()) {
		}
		else if (update.hasMessage()){
			Message message = update.getMessage();
//			System.out.println(update);

			CommandParser commandParser = new CommandParser(message);
			try {
				sendMessageRequest = commandParser.parse();

			} catch (Exception e) {
				e.printStackTrace();
			}

			return sendMessageRequest;		
			}
		return null;

	}
	
	//Each inlinemessageid is unique, we must add this to a list nad update? chance updatecache

	


	private void executeMessage(BotApiMethod<?> msg)
			throws TelegramApiException {

		if(msg == null){
			return;
		}
		System.out.println("sending: "  + msg.toString());
		if (msg instanceof SendMessage) {
			SendMessage sMessage = (SendMessage) msg;
			if(sMessage.getChatId()!=null){
				Message botMessage = sendMessage(sMessage);

			}


		} else {
			sendApiMethod(msg);

		}

	}

	@Override
	public String getBotUsername() {
		// TODO Auto-generated method stub
		return BOTNAME;
	}

	@Override
	public String getBotToken() {
		// TODO Auto-generated method stub
		return TOKEN;
	}

}