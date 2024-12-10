package testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import utility.DataUtils;

public class OpenAccount extends BaseTest {

	@Test(dataProviderClass = DataUtils.class,dataProvider = "data")
	public void openAccountTest(String firstName,String lastName,String currency) {
		click("openAccountBtn_XPATH");
		selectElement("customer_ID",firstName+" " +lastName);
		selectElement("currency_ID", currency);
		click("processBtn_XPATH");
		wait.until(ExpectedConditions.alertIsPresent());
		Alert al=driver.switchTo().alert();
		String alertText=al.getText();
		System.out.println(alertText);
		Assert.assertTrue(alertText.contains("Account created successfully"),"Account not created");
		al.accept();
	}
}
