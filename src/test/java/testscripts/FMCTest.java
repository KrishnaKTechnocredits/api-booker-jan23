package testscripts;

import static io.restassured.RestAssured.given;

import java.util.Map;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseService;
import constants.Status_Code;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import utilities.DataGenerator;

public class FMCTest {

	String emailId;
	String token;
	Response res;
	BaseService baseService = new BaseService();
	
	/*
	 * private void createToken1() { RestAssured.baseURI =
	 * "http://Fmc-env.eba-5akrwvvr.us-east-1.elasticbeanstalk.com"; res =
	 * given().headers("Accept", "application/json").when().get("/fmc/token"); token
	 * = res.jsonPath().get("accessToken"); System.out.println(token); }
	 */
	
	private void createToken() {
		Map<String, String> headerMap = baseService.getHeaderWithoutAuth();
		res = baseService.executeGetAPI("/fmc/token", headerMap);
		token = res.jsonPath().get("accessToken");
		System.out.println(token);
	}
	
	private String emailSignup(String emailId) {	
		JSONObject emailSignUpPayLoad = new JSONObject();
		emailSignUpPayLoad.put("email_id", emailId);
		
		Map<String, String> headerMap = baseService.getHeaderHavingAuth(token);
		res = baseService.executePostAPI("/fmc/email-signup-automation", headerMap, emailSignUpPayLoad);
		
		Assert.assertEquals(res.getStatusCode(), Status_Code.CREATED);
		return res.jsonPath().getString("content.otp");
	}
	
	private void verifyOtp(String emailId, String fullName, String phoneNumber, String password, String otp) {
		JSONObject verifyOtpPayload = new JSONObject();
		verifyOtpPayload.put("email_id",emailId);
		verifyOtpPayload.put("full_name", fullName);
		verifyOtpPayload.put("phone_number", phoneNumber);
		verifyOtpPayload.put("password", password);
		verifyOtpPayload.put("otp", otp);
		
		Map<String, String> headerMap = baseService.getHeaderHavingAuth(token);
		res = baseService.executePutAPI("/fmc/verify-otp/", headerMap, verifyOtpPayload);
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		System.out.println(res.asPrettyString());
	}

	@Test
	public void signupTest() {
		String emailId = DataGenerator.getEmailId();
		String fullName = DataGenerator.getFullName();
		String phoneNumber = DataGenerator.getPhoneNumber();
		String password = "pass@123";
		
		createToken();
		String otp = emailSignup(emailId);
		System.out.println(otp);
		verifyOtp(emailId, fullName, phoneNumber, password, otp);
		//verifyOtp();
	}
}
