package org.telegram.newbot.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"categories", "location"} )


public class YelpBusinesses {
	
    private double rating; 
    private String mobileUrl;
    private String ratingImgUrl; 
    private int reviewCount; 
    private String name;
    private String ratingImgUrlSmall;
    private String url; 
    private List<YelpBusinessesCategories> categories;
    private String phone; 
    private String isClosed;
    private YelpBusinessesLocation location;

	public YelpBusinesses(){}

	public double getRating() {
		return rating;
	}
	@JsonProperty("rating")
	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getMobileUrl() {
		return mobileUrl;
	}
	@JsonProperty("mobile_url")
	public void setMobileUrl(String mobileUrl) {
		this.mobileUrl = mobileUrl;
	}

	public String getRatingImgUrl() {
		return ratingImgUrl;
	}
	@JsonProperty("rating_img_url")
	public void setRatingImgUrl(String ratingImgUrl) {
		this.ratingImgUrl = ratingImgUrl;
	}

	public int getReviewCount() {
		return reviewCount;
	}
	@JsonProperty("review_count")
	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}

	public String getName() {
		return name;
	}
	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}
	@JsonProperty("rating_img_url_small")
	public String getRatingImgUrlSmall() {
		return ratingImgUrlSmall;
	}

	public void setRatingImgUrlSmall(String ratingImgUrlSmall) {
		this.ratingImgUrlSmall = ratingImgUrlSmall;
	}

	public String getUrl() {
		return url;
	}
	@JsonProperty("url")
	public void setUrl(String url) {
		this.url = url;
	}

	public List<YelpBusinessesCategories> getCategories() {
		return categories;
	}
	@JsonProperty("categories")
	public void setCategories(List<YelpBusinessesCategories> categories) {
		this.categories = categories;
	}

	public String getPhone() {
		return phone;
	}
	@JsonProperty("phone")
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIsClosed() {
		return isClosed;
	}
	@JsonProperty("is_closed")
	public void setIsClosed(String isClosed) {
		this.isClosed = isClosed;
	}

	public YelpBusinessesLocation getLocation() {
		return location;
	}
	@JsonProperty("location")
	public void setLocation(YelpBusinessesLocation location) {
		this.location = location;
	}
	
}
