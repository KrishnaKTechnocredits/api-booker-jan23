package testscripts;

import java.util.Map;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseService;
import constants.Status_Code;
import io.restassured.response.Response;
import services.GenerateTokenService;
import utilities.DataGenerator;

public class FMCTest3 {

	String token;
	Response res;
	String otp;
	BaseService baseService = new BaseService();
	String emailId = DataGenerator.getEmailId();
	String fullName = DataGenerator.getFullName();
	String phoneNumber = DataGenerator.getPhoneNumber();
	String password = "pass@123";
	GenerateTokenService generateTokenService = new GenerateTokenService();

	@Test
	public void createToken() {
		Response res = generateTokenService.getTokenResponse();
		System.out.println(res.asPrettyString());
		token = res.jsonPath().get("accessToken");
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		Assert.assertTrue(token.length() >0);
		Assert.assertEquals(res.jsonPath().get("tokenType"), "bearer");
		System.out.println(token);
	}
	
	@Test(priority = 1)
	public void emailSignup() {	
		JSONObject emailSignUpPayLoad = new JSONObject();
		emailSignUpPayLoad.put("email_id", emailId);
		
		res = generateTokenService.getEmailSignupResponse(emailSignUpPayLoad);
		otp = res.jsonPath().getString("content.otp");
		Assert.assertEquals(res.getStatusCode(), Status_Code.CREATED);
	}
	
	@SuppressWarnings("unchecked")
	@Test(priority = 2)
	public void verifyOtp() {
		if(otp == null) {
			JSONObject emailSignUpPayLoad = new JSONObject();
			emailSignUpPayLoad.put("email_id", emailId);
			otp = generateTokenService.getOptFromEmailSignUpResponse(emailSignUpPayLoad);
		}
		JSONObject verifyOtpPayload = new JSONObject();
		verifyOtpPayload.put("email_id",emailId);
		verifyOtpPayload.put("full_name", fullName);
		verifyOtpPayload.put("phone_number", phoneNumber);
		verifyOtpPayload.put("password", password);
		verifyOtpPayload.put("otp", otp);
		
		res = generateTokenService.getVerifyOptResponse(verifyOtpPayload);
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		int userId = res.jsonPath().getInt("content.userId");
		System.out.println(userId);
		
		
		userId = generateTokenService.getUserId(emailId, "pass123");
		System.out.println(userId);
	}
	
	@Test
	public void verifyOpt1() {
		int userId = generateTokenService.getUserId(emailId, "pass123");
		System.out.println(userId);
	}
	
	
}

