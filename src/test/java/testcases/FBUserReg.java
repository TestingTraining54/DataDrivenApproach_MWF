package testcases;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import utility.DataUtils;

public class FBUserReg extends BaseTest{

	@Test(dataProviderClass = DataUtils.class,dataProvider = "data")
	public void doUserReg(String firstName,String lastName,String city)
	{
		System.out.println(firstName + " " + lastName + " " + city);
	}


	
}


