package com.VRJD.Place.Utilities;

import java.util.ArrayList;
import java.util.List;

import com.VRJD.Place.POJO.AddPlace;
import com.VRJD.Place.POJO.Location;

public class TestDataBuild {

	public AddPlace addPlacePayLoad(String name, String language, String address) {

		AddPlace addPlace = new AddPlace();

		addPlace.setAccuracy(50);
		addPlace.setAddress(address);
		addPlace.setLanguage(language);
		addPlace.setPhone_number("9611388667");
		addPlace.setWebsite("https://rahulshettyacademy.com");
		addPlace.setName(name);

		List<String> list = new ArrayList<String>();
		list.add("Black");
		list.add("White");
		addPlace.setTypes(list);

		Location location = new Location();
		location.setLat(-38.383494);
		location.setLng(33.427362);
		addPlace.setLocation(location);

		return addPlace;
	}

	public String deletePlacePayload(String placeId) {
		return "{\r\n    \"place_id\":\"" + placeId + "\"\r\n}";
	}
}
