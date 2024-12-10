package testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import utility.DataUtils;

public class addCustomerTest extends BaseTest {

	@Test(dataProviderClass = DataUtils.class,dataProvider = "data")
	public void addCustomer(String fname,String lName,String postCode) {
		click("addCustmr_XPATH");
		type("firstNameTxtbox_XPATH",fname);
		type("lastNameTxtbox_XPATH",lName);
		type("postalCode_XPATH",postCode);
		click("addCutomer_XPATH");
		
		wait.until(ExpectedConditions.alertIsPresent());
		Alert al=driver.switchTo().alert();
		String alertText=al.getText();
		System.out.println(alertText);
		Assert.assertTrue(alertText.contains("Customer added successfully"),"Customer not created");
		al.accept();
	}
}
