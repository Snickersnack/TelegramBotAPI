package org.telegram.newbot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class YelpCache {

	private static YelpCache yelpCache = new YelpCache();
	private List<String> yelpList;
	private List<String> currentYelpList;
	private int yelpPageState = 0;
	private YelpCache(){}
	
	public static YelpCache getInstance(){
		return yelpCache;
	}
	
	public void setYelpList(List<String> yelpList){
		this.yelpList = yelpList;
	}
	
	public void clearYelpCache(){
		this.yelpList.clear();
		yelpPageState = 0;
	}
	
	public void setYelpPageState(int state){
		if (state > 3){
			throw new IllegalArgumentException("State must be less than 4");
		}
		else{
			this.yelpPageState = state;
		}
			
	}
	public List<String> getYelpList(){
		return yelpList;
	}
	
	public List<String> getCurrentYelpList(){
		switch(this.yelpPageState){
		case 0: 
			currentYelpList = null;
			break;
		case 1: 
			currentYelpList = yelpList.subList(0, 2);
			break;
		case 2: 
			currentYelpList = yelpList.subList(3, 5);
			break;
		case 3: 
			currentYelpList = yelpList.subList(6, 8);
			break;
		
		
		}
	return currentYelpList;
	
	}
}	

