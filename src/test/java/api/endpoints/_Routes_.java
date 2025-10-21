package api.endpoints;

/* Routes class contains the URLs to be used */

public class _Routes_ {
	
	public static String baseURL = "https://petstore.swagger.io/v2";
	
	// User Model
	public static String userPostURL = baseURL + "/user";
	public static String userGetURL = baseURL + "/user/{username}"; /* {username} --> acts as Path Parameter */
	public static String userUpdateURL = baseURL + "/user/{username}";
	public static String userDeleteURL = baseURL + "/user/{username}";
    public static String userLoginURL = baseURL + "/user/login";
    public static String userLogoutURL = baseURL + "/user/logout";
	
	// Store Model
	public static String storePostURL = baseURL + "/store";
	public static String storeGetURL = baseURL + "/store/{orderID}";
	public static String storeUpdateURL = baseURL + "/store/{orderID}";
	public static String storeDeleteURL = baseURL + "/store/{orderID}";
	
	// Pet Model
	public static String petPostURL = baseURL + "/pet";
	public static String petGetURL = baseURL + "/pet/{petID}";
	public static String petUpdateURL = baseURL + "/pet/{petID}";
	public static String petDeleteURL = baseURL + "/pet/{petID}";
	
}
