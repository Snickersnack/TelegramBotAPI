package org.wilson.telegram.newbot.auth;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

public class YelpAuth {
	OAuthService service;
	Token accessToken;

	  /**
	   * Setup the Yelp API OAuth credentials.
	   *
	   * OAuth credentials are available from the developer site, under Manage API access (version 2 API).
	   *
	   * @param consumerKey Consumer key
	   * @param consumerSecret Consumer secret
	   * @param token Token
	   * @param tokenSecret Token secret
	   */
	  public YelpAuth(String consumerKey, String consumerSecret, String yelpToken, String yelpTokenSecret) {
	    this.service = new ServiceBuilder().provider(YelpAuth2.class).apiKey(consumerKey).apiSecret(consumerSecret).build();
	    this.accessToken = new Token(yelpToken, yelpTokenSecret);
	  }
	  
	  public String search(String term, String location) {
		    OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.yelp.com/v2/search");
		    request.addQuerystringParameter("term", term);
		    request.addQuerystringParameter("location", location);
		    request.addQuerystringParameter("limit", "9");
		    this.service.signRequest(this.accessToken, request);
		    Response response = request.send();
		    return response.getBody();
		  }
}
