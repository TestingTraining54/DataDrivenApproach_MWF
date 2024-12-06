package testcases;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import utility.DataUtils;


public class FBLogin extends BaseTest{

	@Test(dataProviderClass = DataUtils.class,dataProvider = "data")
	public void doLogin(String username,String password) {
		type("email_ID",username);
		type("pass_ID",password);
		click("loginBtn_XPATH");
		
	}
}
	
	
