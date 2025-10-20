package api.test;

import org.testng.Assert;
import org.testng.annotations.*;
import com.github.javafaker.Faker;
import api.endpoints.UserModel_EndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserModel_TestCases {
	
	User userPayload;	/* Create an object from the User Class*/
	Faker myFaker; 		/* Create an object from the Faker Class*/
	
	@BeforeClass
	public void setupData()
	{
		myFaker = new Faker();
		userPayload = new User();
		
		/* Set all the payload variables to complete the request */
		
		userPayload.setId(myFaker.idNumber().hashCode());
		userPayload.setUsername(myFaker.name().username());
		userPayload.setFirstName(myFaker.name().firstName());
		userPayload.setLastName(myFaker.name().lastName());
		userPayload.setEmail(myFaker.internet().safeEmailAddress());
		userPayload.setPassword(myFaker.internet().password(5, 15));
		userPayload.setPhone(myFaker.phoneNumber().cellPhone());
	}
	
	@Test (priority = 1)
	public void TC01_verifyCreateUser()
	{
		Response myResponse = UserModel_EndPoints.createUser(userPayload);
		myResponse.then().log().all();
		
		/* Verify the Status Code */
		Assert.assertEquals(myResponse.getStatusCode(), 200);
	}
	
	@Test (priority = 2)
	public void TC02_verifyReadUser()
	{
		Response myResponse = UserModel_EndPoints.readUser(this.userPayload.getUsername());
		myResponse.then().log().all();
		
		/* Verify the Status Code */
		Assert.assertEquals(myResponse.getStatusCode(), 200);
	}
	
	@Test (priority = 3)
	public void TC03_verifyUpdateUser()
	{
		/* Change the values to be changed (By regenerating them) */
		userPayload.setFirstName(myFaker.name().firstName());
		userPayload.setLastName(myFaker.name().lastName());
		userPayload.setEmail(myFaker.internet().safeEmailAddress());
		
		Response myResponse = UserModel_EndPoints.updateUser(userPayload, this.userPayload.getUsername());
		myResponse.then().log().all();
		
		/* Verify the Status Code */
		Assert.assertEquals(myResponse.getStatusCode(), 200);
		
		/* Verify changed values are reflected */
		//Assert.assertEquals(myResponse.getStatusCode(), this.userPayload.getFirstName());
		
	}
	
	@Test (priority = 4)
	public void TC04_verifyDeleteUser()
	{
		Response myResponse = UserModel_EndPoints.deleteUser(this.userPayload.getUsername());
		myResponse.then().log().all();
		
		/* Verify the Status Code */
		Assert.assertEquals(myResponse.getStatusCode(), 200);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
