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
	public void TC01_verifyCreateUserRequest()
	{
		Response myResponse = UserModel_EndPoints.createUser(userPayload);
//		myResponse.then().log().all();

		/* Verify the Status Code */
		Assert.assertEquals(myResponse.getStatusCode(), 200);

        /* Verify the Status Code */
        Assert.assertEquals(myResponse.contentType(), "application/json");
	}
	
	@Test (priority = 2)
	public void TC02_verifyReadUserRequest()
	{
		Response myResponse = UserModel_EndPoints.readUser(this.userPayload.getUsername());
//        Response myResponse = UserModel_EndPoints.readUser("jermaine.doyle");
        System.out.println("==============================");
        System.out.println(this.userPayload.getUsername());
        System.out.println("==============================");
//		myResponse.then().log().all();
		
		/* Verify the Status Code */
		Assert.assertEquals(myResponse.getStatusCode(), 200);

        /* Verify the correct Username is fetched */
        String userName  = myResponse.body().jsonPath().getString("username");
        Assert.assertEquals(userName, this.userPayload.getUsername());

        /* Verify the correct Firstname is fetched */
        String firstName  = myResponse.body().jsonPath().getString("firstname");
        Assert.assertEquals(firstName, this.userPayload.getFirstName());

        /* Verify the correct Lastname is fetched */
        String lastName  = myResponse.body().jsonPath().getString("lastname");
        Assert.assertEquals(lastName, this.userPayload.getLastName());
	}
	
	@Test (priority = 3)
	public void TC03_verifyUpdateUserRequest()
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
        String firstName  = myResponse.body().jsonPath().getString("firstname");
        String lastName  = myResponse.body().jsonPath().getString("lastname");
        String eMail  = myResponse.body().jsonPath().getString("email");

		Assert.assertEquals(firstName, this.userPayload.getFirstName());
        Assert.assertEquals(lastName, this.userPayload.getLastName());
        Assert.assertEquals(eMail, this.userPayload.getEmail());
	}
	
	@Test (priority = 4)
	public void TC04_verifyDeleteUserRequest()
	{
		Response myDeleteResponse = UserModel_EndPoints.deleteUser(this.userPayload.getUsername());
        myDeleteResponse.then().log().all();
		
		/* Verify the Status Code */
		Assert.assertEquals(myDeleteResponse.getStatusCode(), 200);

        /* Verify User Not Found */
        Response myReadResponse = UserModel_EndPoints.readUser(this.userPayload.getUsername());
        Assert.assertEquals(myReadResponse.getStatusCode(), 404);
	}

    @Test ()
    public void TC05_verifyUserLogin()
    {
        Response myResponse = UserModel_EndPoints.loginUser(this.userPayload.getUsername(),
                                                            this.userPayload.getPassword());
        myResponse.then().log().all();

        /* Verify the Status Code */
        Assert.assertEquals(myResponse.getStatusCode(), 200);
    }

    @Test ()
    public void TC06_verifyLogoutCurrentLoggedInUser()
    {
        Response myResponse = UserModel_EndPoints.logoutCurrentLoggedUser();
        myResponse.then().log().all();

        /* Verify the Status Code */
        Assert.assertEquals(myResponse.getStatusCode(), 200);
    }

	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
