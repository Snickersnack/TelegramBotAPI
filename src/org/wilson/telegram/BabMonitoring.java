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
	int counter;
	
	Long rDate;
	Long wDate;
	boolean rTimeout;
	boolean wTimeout;
	private double expiration = .5 * 60 * 1000;
	private String mostRecentGameId;
	Long currentTime;
	
	public BabMonitoring() {
		counter =0;
		rDate = System.currentTimeMillis();
		wDate = System.currentTimeMillis();
		mostRecentGameId = "";
	}
	
	public void receive()throws IOException{
		String portString = System.getenv("PORT");
		System.out.println("PortString = " + portString);
		int port = Integer.parseInt(portString);
		ServerSocket serverSock = new ServerSocket(port);
		
		Timer timer = new Timer();
		long interval = (180000) ; // 180 sec

		timer.schedule( new TimerTask() {
			public void run() {
				currentTime = System.currentTimeMillis();
		        if(currentTime - wDate > expiration){
		        	wTimeout = true;
		        }else{
		        	wTimeout = false;
		        }
		        
		        if(currentTime - rDate > expiration){
		        	rTimeout = true;
		        }else{
		        	rTimeout = false;
		        }
		        

			}
		}, 0, interval); 
	
		Timer timer2 = new Timer();
		long interval2 = (30000) ; // 180 sec
		timer2.schedule( new TimerTask() {
			public void run() {
				currentTime = System.currentTimeMillis();

				if(rTimeout && Cache.getInstance().getMonitoringStatus()){
		    		String accountSid = "ACd5626cad8c58605877d175235828ec1b"; // Your Account SID from www.twilio.com/user/account
		    		String authToken = "982b468d55657add09d5f02a6c4aadcf"; // Your Auth Token from www.twilio.com/user/account

		    		Twilio.init(accountSid, authToken);
		        	SendMessage sendMessageRequest = new SendMessage();
		    		sendMessageRequest.setChatId((long) -297769804);
		    		NewBotHandler newBot = Cache.getInstance().getBot();
		    		sendMessageRequest.setText("No ping received from owmygroin for " + ((currentTime - rDate)/1000)/60 + " minutes");
		    		Message message = Message.creator(
		    			    new PhoneNumber("+16266758082"),  // To number
		    			    new PhoneNumber("+14243583198"),  // From number
		    			    "No ping received from owmygroin for " + ((currentTime - rDate)/1000)/60 + " minutes"                    // SMS body
		    			).create();

					System.out.println(message.getSid());
		    		try {
						newBot.sendMessage(sendMessageRequest);
					} catch (TelegramApiException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
		        if(wTimeout && Cache.getInstance().getMonitoringStatus()){
		    		String accountSid = "AC17677b826d0d664deddbb04c23cb97b2"; // Your Account SID from www.twilio.com/user/account
		    		String authToken = "ce66c8f089c3bfd0bc337b5219831c1b"; // Your Auth Token from www.twilio.com/user/account

		    		Twilio.init(accountSid, authToken);
		        	SendMessage sendMessageRequest = new SendMessage();
		    		sendMessageRequest.setChatId((long) -297769804);
		    		NewBotHandler newBot = Cache.getInstance().getBot();
		    		sendMessageRequest.setText("No ping received from Snickersnack for " + ((currentTime - wDate)/1000)/60 + " minutes");
		    		Message message = Message.creator(
		    			    new PhoneNumber("+16266758580"),  // To number
		    			    new PhoneNumber("+13236723408"),  // From number
		    			    "No ping received from Snickersnack for " + ((currentTime - wDate)/1000)/60 + " minutes"                   // SMS body
		    			).create();

					System.out.println(message.getSid());
		    		try {
						newBot.sendMessage(sendMessageRequest);
					} catch (TelegramApiException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
			}
		}, 0, interval2);
        

		
		
	 while (true) {
	        Socket clientSocket = serverSock.accept();

	        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

	        String s;
	        while ((s = in.readLine()) != null) {
	            System.out.println(s);
	            if(s.equals("GET /Snickersnack HTTP/1.1")){
	        		wDate = System.currentTimeMillis();	
	        		wTimeout = false;
	        		break;
	            }
	            if(s.equals("GET /owmygroin HTTP/1.1")){
	            	rDate = System.currentTimeMillis();
	            	rTimeout = false;
	            	break;
	            }
	            
	            if(!s.equals("GET /owmygroin HTTP/1.1") && !s.equals("GET /Snickersnack HTTP/1.1")){
	            	String[] sSplit = s.split(" ");
		            String[] gameSplit = sSplit[1].split("/");
		            
		            if(!mostRecentGameId.equals(gameSplit[1])){
		        		mostRecentGameId=gameSplit[1];
			            SendMessage sendMessageRequest = new SendMessage();
		        		sendMessageRequest.setChatId((long) -297769804);
		        		NewBotHandler newBot = Cache.getInstance().getBot();
		        		sendMessageRequest.setText("High bet game detected from a script. Game id: " + gameSplit[1] + " Total bet: " + gameSplit[2]);
		        		try {
							newBot.sendMessage(sendMessageRequest);
						} catch (TelegramApiException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            }
		            break;
	            }
	            

	            if (s.isEmpty()) {
	                break;
	            }
	        }
	        
            out.write("HTTP/1.0 200 OK\r\n");
            out.write(new Date() + "\r\n");
            out.write("Content-Type: text/html");
            
            counter++;
	        out.close();
	        in.close();
	        clientSocket.close();
	    }
	}

}
