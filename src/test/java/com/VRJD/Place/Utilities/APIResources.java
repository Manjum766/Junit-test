package com.VRJD.Place.Utilities;

// ENUM is Special Class in Java which has Collection of Constants or Methods
public enum APIResources {
	
	GetCourseDetailsAPI("/oauthapi/getCourseDetails"),

	AddPlaceAPI("/maps/api/place/add/json"), 
	GetPlaceAPI("/maps/api/place/get/json"),
	DeletePlaceAPI("/maps/api/place/delete/json");

	private String resource;

	APIResources(String resource) {
		this.resource = resource;
	}

	public String getResource() {
		return resource;
	}

}
