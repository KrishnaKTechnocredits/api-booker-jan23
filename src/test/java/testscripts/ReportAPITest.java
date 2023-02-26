package testscripts;

import org.testng.annotations.BeforeMethod;

import io.restassured.response.Response;
import services.LoginService;

public class ReportAPITest {

	LoginService loginService = new LoginService();
	String emailId = "kanani.maulik@gmail.com";
	String password = "pass@123";
	int userId;
	
	@BeforeMethod
	public void login() {
		Response res = loginService.login(emailId, password);
		userId = res.jsonPath().getInt("content.userId");
	}
	
	public void verfiyAddReportTest() {
		
	}
}
