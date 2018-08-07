package org.wilson.telegram;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.wilson.telegram.newbot.Cache;
import org.wilson.telegram.newbot.NewBotHandler;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;



public class BabMonitoring {
	
	Long rDate;
	Long wDate;
	boolean rTimeout;
	boolean wTimeout;
	private long expiration = 5 * 60 * 1000;
	private String mostRecentGameId;
	Long currentTime;
	private boolean wAck;
	private boolean rAck;
	NewBotHandler newBot;
	Long babChatId;

	Long ethChatId;
	Long ethWDate;
	Long ethRDate;
	boolean ethWTimeout;
	boolean ethRTimeout;
	boolean ethWAlerted;
	boolean ethRAlerted;
	
	public BabMonitoring() {
		babChatId = (long) -297769804;
		rDate = System.currentTimeMillis();
		wDate = System.currentTimeMillis();
		mostRecentGameId = "";
		wAck = true;
		rAck = true;
		newBot = Cache.getInstance().getBot();
		wTimeout = false;
		rTimeout = false;
		
		ethChatId = (long)-317649559;
		ethWTimeout = false;
		ethRTimeout = false;
		ethRDate = System.currentTimeMillis();
		ethWDate = System.currentTimeMillis();
		ethWAlerted = false;
		ethRAlerted = false;
		
	}
	
	public boolean isRAck(){
		return rAck;
	}
	
	public boolean isWAck(){
		return wAck;
	}
	
	public void receive()throws IOException{
		String portString = System.getenv("PORT");
		System.out.println("PortString = " + portString);
		int port = Integer.parseInt(portString);
		ServerSocket serverSock = new ServerSocket(port);
		
		//Check for last ping
		Timer timer = new Timer();
		long interval = (expiration) ; // 180 sec

		timer.schedule( new TimerTask() {
			public void run() {
				currentTime = System.currentTimeMillis();
		        if(currentTime - wDate > expiration && !wTimeout){
		        	wTimeout = true;
		        	wAck = false;
		    		sendMessage("No ping received from @snickersnack for " + ((currentTime - wDate)/1000)/60 + " minutes", babChatId);
		        }
		        
		        if(currentTime - rDate > expiration && !rTimeout){
		        	rTimeout = true;
		        	rAck = false;
		    		sendMessage("No ping received from @raymondt for " + ((currentTime - rDate)/1000)/60 + " minutes", babChatId);
		        }    
		        
		        if(currentTime - ethWDate > expiration && !ethWTimeout && !ethWAlerted){
		        	ethWTimeout = true;
		        	ethWAlerted = true;
		    		sendMessage("No ping received from @snickersnack ethCrash for " + ((currentTime - ethWDate)/1000)/60 + " minutes", ethChatId);
		        }   
		        
		        if(currentTime - ethRDate > expiration && !ethRTimeout && !ethRAlerted){
		        	ethRTimeout = true;
		        	ethRAlerted = true;
		    		sendMessage("No ping received from @raymondt ethCrash for " + ((currentTime - ethRDate)/1000)/60 + " minutes", ethChatId);
		        }   
		        
			}
		}, 0, interval); 
	
		//Send if we have a time exceeded
		Timer timer2 = new Timer();
		long interval2 = (30000) ; // 180 sec
		timer2.schedule( new TimerTask() {
			public void run() {
				currentTime = System.currentTimeMillis();

				if(rTimeout && !rAck){
		    		String accountSid = "ACd5626cad8c58605877d175235828ec1b"; // Your Account SID from www.twilio.com/user/account
		    		String authToken = "982b468d55657add09d5f02a6c4aadcf"; // Your Auth Token from www.twilio.com/user/account

		    		Twilio.init(accountSid, authToken);

		    		Message message = Message.creator(
		    			    new PhoneNumber("+16266758082"),  // To number
		    			    new PhoneNumber("+14243583198"),  // From number
		    			    "No ping received from owmygroin for " + ((currentTime - rDate)/1000)/60 + " minutes"                    // SMS body
		    			).create();

					System.out.println(message.getSid());

		        }
		        if(wTimeout && !wAck){
		    		String accountSid = "AC17677b826d0d664deddbb04c23cb97b2"; // Your Account SID from www.twilio.com/user/account
		    		String authToken = "ce66c8f089c3bfd0bc337b5219831c1b"; // Your Auth Token from www.twilio.com/user/account

		    		Twilio.init(accountSid, authToken);
		    		Message message = Message.creator(
		    			    new PhoneNumber("+16266758580"),  // To number
		    			    new PhoneNumber("+13236723408"),  // From number
		    			    "No ping received from Snickersnack for " + ((currentTime - wDate)/1000)/60 + " minutes"                   // SMS body
		    			).create();

					System.out.println(message.getSid());

		        }
			}
		}, 0, interval2);
        

		
	//Server listener
	 while (true) {
	        Socket clientSocket = serverSock.accept();

	        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

	        String s;
	        while ((s = in.readLine()) != null) {
	            System.out.println(s);

	            if (s.isEmpty()) {
	                break;
	            }
	            if(s.equals("GET /Snickersnack HTTP/1.1")){
	        		wDate = System.currentTimeMillis();	
	        		if(wTimeout){
		        		wTimeout = false;
		        		wAck = true;
		        		sendMessage("Monitoring is active for snickersnack. Alert closed.", babChatId);
	        		}
	        		
	        		break;
	            }
	            else if(s.equals("GET /owmygroin HTTP/1.1")){
	            	rDate = System.currentTimeMillis();
	        		if(rTimeout){
		        		rTimeout = false;
		        		rAck = true;
		        		sendMessage("Monitoring is active for owmygroin. Alert closed.", babChatId);
	        		}	            	
	        		break;
	            }
	            else if(s.equals("GET /eth/Snickersnack HTTP/1.1")){
	            	ethWDate = System.currentTimeMillis();
	        		if(ethWTimeout){
		        		ethWTimeout = false;
		        		ethWAlerted = false;
		        		sendMessage("Monitoring is active for Snickersnack (eth).", ethChatId);
	        		}	            	
	        		break;
	            }
	            
	            else if(s.equals("GET /eth/1gbofpr0n HTTP/1.1")){
	            	ethRDate = System.currentTimeMillis();
	        		if(ethRTimeout){
		        		ethRTimeout = false;
		        		ethRAlerted = false;
		        		sendMessage("Monitoring is active for 1gbofpr0n.", ethChatId);
	        		}	            	
	        		break;
	            }
	            
	            else{
	            	String[] sSplit = s.split(" ");
		            String[] gameSplit = sSplit[1].split("/");

		            if(!mostRecentGameId.equals(gameSplit[1])){
		        		mostRecentGameId= gameSplit[1];

		            	Integer gameId = Integer.parseInt(gameSplit[1]) + 1;
		            	
						

		        		sendMessage("High bet Game ID: " + 
		        		"<a href = " + "\"" + "https://www.bustabit.com/game/" + gameId + "\">" + 
		        		gameId + "</a>" + " Total bet: " + gameSplit[2], babChatId);
		            }
		            break;
	            }
	            

	        }
	        
            out.write("HTTP/1.0 200 OK\r\n");
            out.write(new Date() + "\r\n");
            out.write("Content-Type: text/html");
            
	        out.close();
	        in.close();
	        clientSocket.close();
	    }
	}
	
	public String ackAlert(Integer id, String userName){
		StringBuilder sb = new StringBuilder();
		if(id == 163396337 && !wAck){
			sb.append("Alert has been acked for " + userName);
			wAck = true;
		}
		else if(id == 163396337 && wAck){
			sb.append("Alert has already been acked for " + userName);

		}
		else if(id == 143065272 && !rAck){
			sb.append("Alert has been acked for " + userName);
			rAck = true;
		}else if(id == 143065272 && rAck){
			sb.append("Alert has already been acked for " + userName);
		}else if(id == 143065272 && !rTimeout){
			sb.append("No open alerts for " + userName);
		}else if(id == 163396337 && !wTimeout){
			sb.append("No open alerts for " + userName);
		}
		return sb.toString();
	}
	
	public String getStatusString(){
		StringBuilder sb = new StringBuilder();
		currentTime = System.currentTimeMillis();

		if(wTimeout){
			sb.append("Snickersnack script is currently down. Last seen " + ((currentTime - wDate)/1000) + " seconds ago");
		}else{
			sb.append("Snickersnack script is currently up. Last seen " + ((currentTime - wDate)/1000) + " seconds ago");
		}
		
		sb.append(System.getProperty("line.separator"));

		if(rTimeout){
			sb.append("Owmygroin script is currently down. Last seen " + ((currentTime - rDate)/1000) + " seconds ago");
		}else{
			sb.append("Owmygroin script is currently up. Last seen " + ((currentTime - rDate)/1000) + " seconds ago");
		}
		sb.append(System.getProperty("line.separator"));
		sb.append(System.getProperty("line.separator"));

		sb.append("Open alerts: ");
		if(!wAck && wTimeout){
			sb.append("snickersnack ");
		}
		if(wAck && wTimeout){
			sb.append("snickersnack(acked) ");
		}
		if(!rAck && rTimeout){
			sb.append("owmygroin");
		}
		if(rAck && rTimeout){
			sb.append("owmygroin(acked)");
		}
		
		if(!wTimeout && !rTimeout){
			sb.append("None");
		}
		return sb.toString();
	}
	
	
	public String getEthStatusString(){
		StringBuilder sb = new StringBuilder();
		currentTime = System.currentTimeMillis();

		if(ethWTimeout){
			sb.append("Snickersnack(eth) script is currently down. Last seen " + ((currentTime - ethWDate)/1000) + " seconds ago");
		}else{
			sb.append("Snickersnack(eth) script is currently up. Last seen " + ((currentTime - ethWDate)/1000) + " seconds ago");
		}
		
		sb.append(System.getProperty("line.separator"));

		if(ethRTimeout){
			sb.append("1gbofpr0n script is currently down. Last seen " + ((currentTime - ethRDate)/1000) + " seconds ago");
		}else{
			sb.append("1gbofpr0n script is currently up. Last seen " + ((currentTime - ethRDate)/1000) + " seconds ago");
		}

		return sb.toString();
	}
	
	
	
	
	
	private void sendMessage(String text, Long chatId){
		SendMessage sendMessageRequest = new SendMessage();
		sendMessageRequest.setChatId(chatId);
		sendMessageRequest.setText(text);
		sendMessageRequest.disableWebPagePreview();
		sendMessageRequest.setParseMode(BotConfig.SENDMESSAGEMARKDOWN);
		try {
			newBot.sendMessage(sendMessageRequest);
		} catch (TelegramApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
