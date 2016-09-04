package org.telegram.newbot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.telegram.newbot.models.DotaHeroes;

import com.wilson.data.client.UriUtils;
import com.wilson.data.client.user.SteamRequest;

<<<<<<< HEAD

=======
>>>>>>> 4313ba29a2a9b55d5052e7787c2fd41b911725c8
public class HeroesRequest implements SteamRequest{

	private Map<String, String> parameters = new HashMap<String, String>();
	
	private Class responseType = DotaHeroes.class;

	@Override
	public String getSteamRoute() {
		// TODO Auto-generated method stub
		return "/IEconDOTA2_570";
	}

<<<<<<< HEAD
//	@Override
//	public String getSteamMethod() {
//		// TODO Auto-generated method stub
//		return "/GetHeroes";
//	}
=======
	@Override
	public String getSteamMethod() {
		// TODO Auto-generated method stub
		return "/GetHeroes";
	}
>>>>>>> 4313ba29a2a9b55d5052e7787c2fd41b911725c8

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

<<<<<<< HEAD
	@Override
	public String getSteamMethod() {
		// TODO Auto-generated method stub
		return null;
	}

=======
>>>>>>> 4313ba29a2a9b55d5052e7787c2fd41b911725c8

}
