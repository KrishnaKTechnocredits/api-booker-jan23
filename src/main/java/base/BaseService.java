package base;

import java.util.HashMap;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class BaseService {
	
	
	public BaseService(){
		RestAssured.baseURI = "http://Fmc-env.eba-5akrwvvr.us-east-1.elasticbeanstalk.com";
	}
	
	public BaseService(String baseURI){
		RestAssured.baseURI = baseURI;
	}
	
	public Map<String,String> getHeaderHavingAuth(String token){
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("Content-Type", "application/json");
		headerMap.put("Accept", "application/json");
		headerMap.put("Authorization", "Bearer " + token);
		return headerMap;
	}
	
	public Map<String,String> getHeaderWithoutAuth(){
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("Content-Type", "application/json");
		headerMap.put("Accept", "application/json");
		return headerMap;
	}
	
	public Response executeGetAPI(String endPoint, Map<String, String> header) {
		return RestAssured.given()
			.headers(header)
			.log().all()
			.when()
			.get(endPoint)
			.then()
			.log().all().extract().response();
	}
	
	public Response executePostAPI(String endPoint, Map<String, String> header, Object payload) {
		return RestAssured.given()
				.headers(header)
				.log().all()
				.body(payload)
				.when()
				.post(endPoint)
				.then()
				.log().all().extract().response();
	}
	
	public Response executePutAPI(String endPoint, Map<String, String> header, Object payload) {
		return RestAssured.given()
				.headers(header)
				.log().all()
				.body(payload)
				.when()
				.put(endPoint)
				.then()
				.log().all().extract().response();
	}
	
	public Response executeDeleteAPI(String endPoint, Map<String, String> header) {
		return RestAssured.given()
				.headers(header)
				.log().all()
				.when()
				.delete(endPoint)
				.then()
				.log().all().extract().response();
	}
	
	public Response executeDeleteAPI(String endPoint, Map<String, String> header, Object payload) {
		return RestAssured.given()
				.headers(header)
				.log().all()
				.body(payload)
				.when()
				.delete(endPoint)
				.then()
				.log().all().extract().response();
	}
}
