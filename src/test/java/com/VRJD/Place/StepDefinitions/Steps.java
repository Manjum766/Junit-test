package com.VRJD.Place.StepDefinitions;

import static io.restassured.RestAssured.*;

import java.io.IOException;

import org.junit.Assert;

import com.VRJD.Place.Utilities.APIResources;
import com.VRJD.Place.Utilities.TestDataBuild;
import com.VRJD.Place.Utilities.Utils;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Steps extends Utils {
	RequestSpecification requestSpecification;
	ResponseSpecification responseSpecification;
	Response response;
	TestDataBuild data = new TestDataBuild();
	static String place_ID;
	static String accessToken;

	@Given("I am an Authorized User")
	public void i_am_an_authorized_user() throws InterruptedException {
		accessToken = Utils.generateAccessToken();
	}
	
	@Given("Fetch Course Details API")
	public void fetch_course_details_api() throws IOException {
//		requestSpecification = given().spec(requestSpecification().headers("Authorization","Bearer " + access_Token));
		requestSpecification = given().spec(requestSpecification().queryParams("access_token", accessToken));
	}

	@Given("Add Place Payload with {string}  {string} {string}")
	public void add_Place_Payload_with(String name, String language, String address) throws IOException {
		requestSpecification = given().spec(requestSpecification()).body(data.addPlacePayLoad(name, language, address));
	}

	@When("User Calls {string} with {string} Http Request")
	public void user_calls_with_http_request(String resource, String method) {
		APIResources resourceAPI = APIResources.valueOf(resource);
		responseSpecification = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON)
				.build();
		if (method.equalsIgnoreCase("POST"))
			response = requestSpecification.when().post(resourceAPI.getResource());
		else if (method.equalsIgnoreCase("GET"))
			response = requestSpecification.when().get(resourceAPI.getResource());
	}

	@Then("Verify Status Code {string}")
	public void verify_status_code(String statusCode) {
 		Assert.assertEquals("Response Status Code Result: ", Integer.parseInt(statusCode), response.getStatusCode());
	}

	@And("Verify {string} in Response Body is {string}")
	public void verify_in_response_body_is(String jsonKey, String jsonValue) {
		String key = getJsonPath(response,jsonKey);
		Assert.assertEquals("Response Body Result: ", jsonValue, key);
	}

	@And("Verify place_Id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
		place_ID = getJsonPath(response, "place_id");
		requestSpecification = given().spec(requestSpecification()).queryParam("place_id", place_ID);
		user_calls_with_http_request(resource, "GET");
		String actualName = getJsonPath(response, "name");
		Assert.assertEquals(actualName, expectedName);
	}

	@Given("Delete Place Payload")
	public void delete_place_payload() throws IOException {
		requestSpecification = given().spec(requestSpecification()).body(data.deletePlacePayload(place_ID));
	}

}
