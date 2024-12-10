package testcases;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;

public class HomeTest extends BaseTest{

	@Test
	public void BankManagerTest() {
		click("bankMgrLoginBtn_XPATH");
		wait.until(ExpectedConditions.elementToBeClickable(getWebElement("addCustmr_XPATH")));
		Assert.assertTrue(isElementPresent("addCustmr_XPATH"), "Add customer button is not displayed");
		
		
		
	}
}
