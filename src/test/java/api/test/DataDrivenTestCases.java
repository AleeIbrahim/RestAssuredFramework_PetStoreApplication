package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import api.endpoints.UserModel_EndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DataDrivenTestCases {
	
	@Test (priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class)
	public void TC01_verifyCreateUser(String userID, String username, String firstName, String lastName, String userEmail, String password, String phoneNumber)
	{
		User userPayload = new User();
		
		userPayload.setId(Integer.parseInt(userID));
		userPayload.setUsername(username);
		userPayload.setFirstName(firstName);
		userPayload.setLastName(lastName);
		userPayload.setEmail(userEmail);
		userPayload.setPassword(password);
		userPayload.setPhone(phoneNumber);
		
		Response myResponse = UserModel_EndPoints.createUser(userPayload);
		
		/* Verify the Status Code */
		Assert.assertEquals(myResponse.getStatusCode(), 200);
	}
	
	@Test (priority = 2, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
	public void TC02_verifyDeleteUser(String username)
	{
		Response myResponse = UserModel_EndPoints.deleteUser(username);
		
		/* Verify the Status Code */
		Assert.assertEquals(myResponse.getStatusCode(), 200);
	}

}
