package tests;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


import utils.BaseTest;
import utils.Screenshots;

public class LoginTest extends BaseTest {

	@Parameters({"user","pass"})
	@Test(priority=1)
	public void loginTest(String username, String password) {
		
		app.click(app.menu.myAccountLink);
		app.myAccount.loginInApp(username, password);
		//ambele asserturi fac acelasi lucru
		assertTrue(app.checkElementIsDisplayed(app.myAccount.usernameGreetings));
		Screenshots.screenshot(driver);
		assertTrue(app.myAccount.usernameGreetings.isDisplayed());
		app.click(app.myAccount.logoutButton);
		
	}
	
	@Parameters({"user2","pass"})
	@Test(priority=2)
		public void loginTestInvalid(String username, String password) {	
		app.click(app.menu.myAccountLink);
		app.myAccount.loginInApp(username, password);
		//ambele asserturi fac acelasi lucru
		assertTrue(app.checkElementIsDisplayed(app.myAccount.usernameGreetings));
		Screenshots.screenshot(driver);
		assertTrue(app.myAccount.usernameGreetings.isDisplayed());
		
	}
}
