package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import tesBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass{ 
	
	@Test(groups={"Regression","Master"})
	public void verify_account_registration() throws InterruptedException {
		logger.info("**** Starting TC001_AccountRegistrationTest *****");
		try {
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		logger.info("Clicked on My Account Link");
		
		hp.clickRegister();
		logger.info("Clicked on My Registration Link");

		AccountRegistrationPage regpage=new AccountRegistrationPage(driver);
		
		logger.info("Providinng customer details");
		regpage.setFirstName(randomString().toUpperCase());
		regpage.setLastName(randomString().toUpperCase());
		regpage.setEmail(randomString()+"@gmail.com"); //randomly generated enail
		regpage.setTelephone(randomNumber());
		
		String password=randomAlphaNumberic();
		
		regpage.setPassword(password);
		regpage.setConfirmPassword(password);
		
		regpage.setPrivacyPolicy();
		regpage.clickContinue();
		
		logger.info("Validating expected message");
		
		String confmsg= regpage.getConfirmationMsg();
		Assert.assertEquals(confmsg, "Your Account Has Been Created!","Confirmation message mismatched");
		logger.info("Test Passed");
		
		}
		catch(Exception e) {
			logger.error("Test Failed:",e.getMessage());
			Assert.fail("Test Failed:" +e.getMessage());
		}
		finally {
			logger.info("***** TC001_AccountRegistrationTest *****");
			
		}
		
		
	}
	


}
