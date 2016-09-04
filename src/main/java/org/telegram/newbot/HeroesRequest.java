package org.telegram.newbot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.telegram.newbot.models.DotaHeroes;

import com.wilson.data.client.UriUtils;
import com.wilson.data.client.user.SteamRequest;


public class HeroesRequest implements SteamRequest{

	private Map<String, String> parameters = new HashMap<String, String>();
	
	private Class responseType = DotaHeroes.class;

	@Override
	public String getSteamRoute() {
		// TODO Auto-generated method stub
		return "/IEconDOTA2_570";
	}

//	@Override
//	public String getSteamMethod() {
//		// TODO Auto-generated method stub
//		return "/GetHeroes";
//	}

	@Override
	public String getSteamMethodVersion() {
		// TODO Auto-generated method stub
		return "/v0001";
	}

	@Override
	public Class getResponseType() {
		// TODO Auto-generated method stub
		return responseType;
	}

	@Override
	public List<NameValuePair> getSteamParameters() {
		// TODO Auto-generated method stub
		return UriUtils.stringMapToNameValuePairs(parameters);
	}

	@Override
	public String getSteamMethod() {
		// TODO Auto-generated method stub
		return null;
	}


}
