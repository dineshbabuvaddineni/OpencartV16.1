package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import tesBase.BaseClass;
import utilities.DataProviders;
  
/*Valid   --  login is Success   --passed
                 login is unsuccess --failed

Invalid  -- login is Success      -- Failed
              -- login is unsuccess -- Passed*/


public class TC003_LoginDDT extends BaseClass{
	
	@Test(dataProvider="LoginData1", dataProviderClass=DataProviders.class, groups="DataDriven") //getting data provider from different class
	public void verify_LoginDDT(String email, String pwd, String exp) {
		logger.info("****** TC003_LoginDDT *****");
		
		try {
			//HomePage
			HomePage hp=new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();
			
			//Login
			LoginPage lp=new LoginPage(driver);
			lp.setEmail(email);
			lp.setPassword(pwd);
			lp.clickLogin();
			
			//MyAccount
			MyAccountPage myAc=new MyAccountPage(driver);
			boolean targetPage=myAc.isMyAccountPageExists();
			
			if(exp.equalsIgnoreCase("Valid")){
				if(targetPage==true) {
					myAc.clickLogout();
					Assert.assertTrue(true);
				}
				else {
					Assert.assertTrue(false);
				}
				
			}
			
			if(exp.equalsIgnoreCase("Invalid"))
			{
				if(targetPage==true) {
					myAc.clickLogout();
					Assert.assertTrue(false);
				}
				else {
					Assert.assertTrue(true);
				}
				
			}
		}catch(Exception e) {
			Assert.fail();
		}
			
			
		logger.info("******** Fnished TC003_LoginDDT **********");
			
	}

}

