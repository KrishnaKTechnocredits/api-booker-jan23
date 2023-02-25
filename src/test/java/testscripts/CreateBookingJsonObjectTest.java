package testscripts;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import constants.Status_Code;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.datafaker.Faker;

public class CreateBookingJsonObjectTest {

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
	
	@SuppressWarnings("unchecked")
	@Test
	public void createBookingTestWithPOJO() {
		Faker faker = new Faker();
		
		JSONObject jsonBookingDate = new JSONObject();
		jsonBookingDate.put("checkin", "2023-05-02");
		jsonBookingDate.put("checkout", "2019-01-02");
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("firstname", faker.name().firstName());
		jsonObject.put("lastname", faker.name().lastName());
		jsonObject.put("totalprice", Integer.parseInt(faker.number().digits(3)));
		jsonObject.put("depositpaid", faker.bool().bool());
		jsonObject.put("bookingdates", jsonBookingDate);
		jsonObject.put("additionalneeds", "Breakfast");
		
		RestAssured.baseURI = "https://restful-booker.herokuapp.com/";
		
		Response res = RestAssured.given()
			.headers("Content-Type","application/json")
			.headers("Accept", "application/json")
			.body(jsonObject)
			.log().all()
			.when()
			.post("/booking");
		
		int bookingId = res.jsonPath().getInt("bookingid"); //
		
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		//Assert.assertTrue(Integer.valueOf(res.jsonPath().getInt("bookingid")) instanceof Integer);
		System.out.println(bookingId);
		Assert.assertTrue(bookingId>0);
	}
}
