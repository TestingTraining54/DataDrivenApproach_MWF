package utility;

import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

import base.BaseTest;

public class DataUtils extends BaseTest{
	@DataProvider(name="data")
	public Object[][] getData(Method m) {
		
		String sheetName=m.getName();
		
		int noOfrows=excel.getRowCount(sheetName);
		int noOfCol = excel.getColumnCount(sheetName);
		
		Object[][] data = new Object[noOfrows-1][noOfCol];
		
		for(int row=2;row<=noOfrows;row++) {
			for(int col=0;col<noOfCol;col++) {
				data[row-2][col] = excel.getCellData(sheetName,col,row);
			}
		}
				
		return data;
	}
}
