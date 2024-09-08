package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import tesBase.BaseClass;

public class TC002_LoginTest extends BaseClass {
	
	@Test(groups={"Sanity","Master"})
	public void verify_Login() {
		logger.info("***** Starting TC_002_LoginTest ******");
		
		try {
			//HomePage
			HomePage hp=new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();
			
			//Login
			LoginPage lp=new LoginPage(driver);
			lp.setEmail(p.getProperty("email"));
			lp.setPassword(p.getProperty("password"));
			lp.clickLogin();
			
			//MyAccount
			MyAccountPage myAc=new MyAccountPage(driver);
			boolean targetPage=myAc.isMyAccountPageExists();
			
			Assert.assertTrue(targetPage); //Assert.assertEquals(targetPage,true,"LoginFailed");
		}catch(Exception e) {
			Assert.fail();
			
			
		}
			
		
		
	}

}
