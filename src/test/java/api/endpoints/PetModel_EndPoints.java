package api.endpoints;

import static io.restassured.RestAssured.given;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

/* This is the implementation for CRUD methods for the User Model */

public class PetModel_EndPoints {
	
	
	/* Implementation for Post Request (Create) */
	public static Response createUser(User myPayload)
	{
		Response myResponse = 
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(myPayload)
		.when()
			.post(Routes.userPostURL);
		
		return myResponse;
	}
	
	/* Implementation for Put Request (Update) */
	public static Response updateUser(User myPayload, String myUsername)
	{
		Response myResponse = 
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("username", myUsername)
			.body(myPayload)
		.when()
			.put(Routes.userUpdateURL);
		
		return myResponse;
	}
	
	/* Implementation for Get Request (Read) */
	public static Response readUser(String myUsername)
	{
		Response myResponse = 
		given()
			.pathParam("username", myUsername)
		.when()
			.get(Routes.userGetURL);
		
		return myResponse;
	}
	
	/* Implementation for Delete Request (Delete) */
	public static Response deleteUser(String myUsername)
	{
		Response myResponse = 
		given()
			.pathParam("username", myUsername)
		.when()
			.delete(Routes.userDeleteURL);
		
		return myResponse;
	}
	
}
