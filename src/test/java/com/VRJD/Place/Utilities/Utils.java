package com.VRJD.Place.Utilities;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ResourceBundle;

import org.json.JSONObject;
import org.json.JSONTokener;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;

public class Utils {

	private static final String accessUri = "https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token";
	private static final String clientId = "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com";
	private static final String clientSecret = "erZOWM9g3UtwNRj340YYaK_W";
	private static final String clientGrantType = "client_credentials";
	private static final String clientScope = "trust";

	public static RequestSpecification requestSpecification;
	public static Response response;
	public static ResourceBundle resourceBundle;

	public static String generateAccessTokenTest() throws InterruptedException {
		String accessToken = "";
		RequestSpecification requestSpecification = null;
		requestSpecification = RestAssured.given().auth().preemptive().basic(clientId, clientSecret).baseUri(accessUri).formParam("grant-type", clientGrantType)
				.formParam("scope", clientScope);
//				.header("accept", "application/json").header("Cache-Control", "no-cache")
//				.header("content-type", "application/x-www-form-urlencoded")
		System.out.println(requestSpecification.toString());
		Thread.sleep(3000);
		Response response = requestSpecification.post();
		Thread.sleep(3000);
		System.out.println("Response Recieved: " + response.asString());
		JsonPath jsonPath = response.jsonPath();
		accessToken = jsonPath.getString("access_token");
		System.out.println("ACCESS TOKEN RECIEVED: " + accessToken);
		return accessToken;
	}

	public static String generateAccessToken() {
		String response = given().formParams("client_id", clientId).formParams("client_secret", clientSecret)
				.formParams("grant_type", clientGrantType).formParams("scope", clientScope).when().post(accessUri)
				.asString();

		JsonPath jsonPath = new JsonPath(response);
		String accessToken = jsonPath.getString("access_token");
		System.out.println("ACCESS TOKEN RECIEVED: " + accessToken);
		return accessToken;
	}

	public RequestSpecification requestSpecification() throws IOException {
		if (requestSpecification == null) {
			PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
			requestSpecification = new RequestSpecBuilder().setBaseUri(getBaseURI("BaseURI"))
					.addQueryParam("key", "qaclick123").addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log)).setContentType(ContentType.JSON).build();
			return requestSpecification;
		}
		return requestSpecification;
	}

	public RequestSpecification requestSpecification(String baseURI) throws IOException {
		if (requestSpecification == null) {
			PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
			requestSpecification = new RequestSpecBuilder().setBaseUri(getBaseURI(baseURI))
					.addQueryParam("key", "qaclick123").addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log)).setContentType(ContentType.JSON).build();
			return requestSpecification;
		}
		return requestSpecification;
	}

	public static String getBaseURI(String key) {
		resourceBundle = ResourceBundle.getBundle("config");
		return resourceBundle.getString(key);
	}

	public String getJsonPath(Response response, String key) {
		JsonPath jsonPath = new JsonPath(response.asString());
		return jsonPath.get(key).toString();
	}

	public static String getRequestBody(String filePath) throws FileNotFoundException {
		FileReader fileReader = new FileReader(filePath);
		JSONTokener jsonTokener = new JSONTokener(fileReader);
		JSONObject data = new JSONObject(jsonTokener);
		return data.toString();
	}

}
