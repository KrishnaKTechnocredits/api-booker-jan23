package testscripts;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import constants.Status_Code;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.request.createbooking.Bookingdates;
import pojo.request.createbooking.CreateBookingRequest;

// given - all input details [URI, headers, path/query parameters, payload]
// when - submit api [headerType, endpoint]
// then - validate the response

public class CreateBookingTest {
	String token;
	int bookingId;
	CreateBookingRequest payload;
	
	@BeforeMethod
	public void generateToken() {
		RestAssured.baseURI = "https://restful-booker.herokuapp.com/";
		
		Response res = RestAssured.given()
			.log().all()
			.headers("Content-Type","application/json")
			.body("{\r\n"
					+ "    \"username\" : \"admin\",\r\n"
					+ "    \"password\" : \"password123\"\r\n"
					+ "}")
			.when()
			.post("/auth");
		
		Assert.assertEquals(res.statusCode(), 200);
		token = res.jsonPath().getString("token");
		System.out.println(token);
	}
	
	@Test (enabled = false)
	public void createBookingTest() {
		Response res = RestAssured.given()
			.headers("Content-Type","application/json")
			.headers("Accept", "application/json")
			.body("{\r\n"
					+ "    \"firstname\" : \"Maulik\",\r\n"
					+ "    \"lastname\" : \"Kanani\",\r\n"
					+ "    \"totalprice\" : 123,\r\n"
					+ "    \"depositpaid\" : true,\r\n"
					+ "    \"bookingdates\" : {\r\n"
					+ "        \"checkin\" : \"2023-05-02\",\r\n"
					+ "        \"checkout\" : \"2019-01-02\"\r\n"
					+ "    },\r\n"
					+ "    \"additionalneeds\" : \"Breakfast\"\r\n"
					+ "}")
			.when()
			.post("/booking");
		
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
	}
	
	@Test
	public void createBookingTestWithPOJO() {
		Bookingdates bookingDates = new Bookingdates();
		bookingDates.setCheckin("2023-05-02");
		bookingDates.setCheckout("2023-05-05");
		
		payload = new CreateBookingRequest();
		payload.setFirstname("Maulik");
		payload.setLastname("Kanani");
		payload.setTotalprice(123);
		payload.setDepositpaid(true);
		payload.setAdditionalneeds("breakfast");
		payload.setBookingdates(bookingDates);
		
		Response res = RestAssured.given()
			.headers("Content-Type","application/json")
			.headers("Accept", "application/json")
			.body(payload)
			.log().all()
			.when()
			.post("/booking");
		
		bookingId = res.jsonPath().getInt("bookingid"); //
		
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		//Assert.assertTrue(Integer.valueOf(res.jsonPath().getInt("bookingid")) instanceof Integer);
		System.out.println(bookingId);
		Assert.assertTrue(bookingId>0);
		validateResponse(res, payload, "booking.");
	}
	
	@Test(priority = 1, enabled = false)
	public void getAllBookingTest() {
		Response res = RestAssured.given()
				.headers("Accept", "application/json")
				.log().all()
				.when()
				.get("/booking");
		
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		List<Integer> listOfBookingIds = res.jsonPath().getList("bookingid");
		System.out.println(listOfBookingIds.size());
		Assert.assertTrue(listOfBookingIds.contains(bookingId));
	}
	
	@Test(priority = 2, enabled = false)
	public void getBookingIdTest() {
		Response res = RestAssured.given()
				.headers("Accept", "application/json")
				.when()
				.get("/booking/"+bookingId);
		
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		System.out.println(res.asPrettyString());
		validateResponse(res, payload, "");
	}
	
	@Test(priority = 2, enabled = false)
	public void getBookingIdDeserializedTest() {
		//bookingId = 2522;
		Response res = RestAssured.given()
				.headers("Accept", "application/json")
				.log().all()
				.when()
				.get("/booking/"+bookingId);
		
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		System.out.println(res.asPrettyString());
		
		CreateBookingRequest responseBody = res.as(CreateBookingRequest.class);
		// payload : all details of request
		// responseBody : all details from getBooking
		
		Assert.assertTrue(responseBody.equals(payload));
	}
	
	@Test(priority = 3)
	public void updateBookingIdTest() {
		//bookingId = 2522;
		payload.setFirstname("Krishna");
		Response res = RestAssured.given()
				.headers("Content-Type","application/json")
				.headers("Accept", "application/json")
				.headers("Cookie", "token="+token)
				.log().all()
				.body(payload)
				.when()
				.put("/booking/"+bookingId);
		
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		System.out.println(res.asPrettyString());
		
		CreateBookingRequest responseBody = res.as(CreateBookingRequest.class);
		Assert.assertTrue(responseBody.equals(payload));
	}
	
	
	@Test(enabled = false)
	public void createBookingTestInPlanMode() {
		
		String payload = "{\r\n"
				+ "    \"username\" : \"admin\",\r\n"
				+ "    \"password\" : \"password123\"\r\n"
				+ "}";
		
		RequestSpecification reqSpec = RestAssured.given();
		reqSpec.baseUri("https://restful-booker.herokuapp.com/");
		reqSpec.headers("Content-Type","application/json");
		reqSpec.body(payload);
		Response res = reqSpec.post("/auth");
		
		Assert.assertEquals(res.statusCode(), 200);
		System.out.println(res.asPrettyString());
	}
	
	
	
	private void validateResponse(Response res, CreateBookingRequest payload, String object) {
		Assert.assertEquals(res.jsonPath().getString(object+"firstname"), payload.getFirstname());
		Assert.assertEquals(res.jsonPath().getString(object+"lastname"), payload.getLastname());
		Assert.assertEquals(res.jsonPath().getInt(object+"totalprice"), payload.getTotalprice());
		Assert.assertEquals(res.jsonPath().getBoolean(object+"depositpaid"), payload.isDepositpaid());
		Assert.assertEquals(res.jsonPath().getString(object+"bookingdates.checkin"), 
				payload.getBookingdates().getCheckin());
		Assert.assertEquals(res.jsonPath().getString(object+"bookingdates.checkout"), 
				payload.getBookingdates().getCheckout());
		Assert.assertEquals(res.jsonPath().getString(object+"additionalneeds"), payload.getAdditionalneeds());
	}
	
}
