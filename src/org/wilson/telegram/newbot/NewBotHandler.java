package org.wilson.telegram.newbot;

import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
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
		System.out.println("UPDATE FOR DEBUGGING: " + update);

		try {
			PartialBotApiMethod<?> msg = handleUpdate(update);
			if (msg != null) {
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

	public PartialBotApiMethod<?> handleUpdate(Update update)
			throws TelegramApiException {
		PartialBotApiMethod<?> msg = null;		
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
				msg = commandParser.parse();

			} catch (Exception e) {
				e.printStackTrace();
			}
			}
		return msg;

	}
	
	//Each inlinemessageid is unique, we must add this to a list nad update? chance updatecache

	


	private void executeMessage(PartialBotApiMethod<?> msg)
			throws TelegramApiException {

		if(msg == null){
			return;
		}
		if (msg instanceof SendMessage) {
			SendMessage sMessage = (SendMessage) msg;
			if(sMessage.getChatId()!=null){
				Message botMessage = sendMessage(sMessage);

			}


		}
		else if(msg instanceof SendDocument){
			SendDocument sDocument = (SendDocument)msg;
			sendDocument(sDocument);

		}
		else if(msg instanceof SendPhoto){
			SendPhoto sPhoto = (SendPhoto)msg;
			sendPhoto(sPhoto);
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