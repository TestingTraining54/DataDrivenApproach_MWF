package base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import extentlisteners.ExtentListeners;
import utility.DbManager;
import utility.ExcelReader;

public class BaseTest {

	/*
	 * WebDriver Implicit Wait Explicit Wait Excel Reader Log4j TestNG DBConnection
	 * Properties screenshot keyword - type/click/select Extent Reports
	 * 
	 * Sequential Browser Opened Testcase1 Testcase2 Testcase3 Close browser
	 * 
	 * End-To-End Browser Opened Testcase1 Close browser Browser Opened Testcase2
	 * Close browser Browser Opened Testcase3 Close browser
	 * 
	 */

	public static WebDriver driver;
	public static ExcelReader excel = new ExcelReader("./src/test/resources/data/testData.xlsx");
	public static FileInputStream fis;
	public static Properties config = new Properties();
	public static Properties or = new Properties();
	public static Logger log = Logger.getLogger(BaseTest.class);
	public static WebDriverWait wait;
	public static DbManager dbManager = new DbManager();

	@BeforeSuite
	public void setUp() {
		PropertyConfigurator.configure("./src/test/resources/properties/log4j.properties");
		log.info("Test Execution started");

		try {
			fis = new FileInputStream("./src/test/resources/properties/config.properties");
			config.load(fis);
			log.info("Config file loaded");
		}

		catch (IOException e) {
			System.out.println("File is not Found: " + e.getMessage());
			log.info(e.getMessage());
		}

		try {
			fis = new FileInputStream("./src/test/resources/properties/or.properties");
			or.load(fis);
			log.info("OR properties file loaded");
		}

		catch (IOException e) {
			System.out.println("File is not Found: " + e.getMessage());
			log.info(e.getMessage());
		}

		if (config.getProperty("browser").equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
			log.info("Chrome browser is launched");
		}

		else if (config.getProperty("browser").equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
			log.info("Firefox browser is launched");
		}

		else if (config.getProperty("browser").equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
			log.info("Edge browser is launched");
		}

		wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(config.getProperty("explicit.wait"))));
		driver.manage().timeouts()
				.implicitlyWait(Duration.ofSeconds(Integer.parseInt(config.getProperty("implicit.wait"))));
		driver.manage().window().maximize();
		log.info("Browser window maximized");

		driver.get(config.getProperty("testsiteurl"));
		log.info("Navigated to an url: " + config.getProperty("testsiteurl"));

	}

	public static WebElement getWebElement(String keyword) {
		WebElement ele = null;
		if (keyword.endsWith("_ID")) {
			ele = driver.findElement(By.id(or.getProperty(keyword)));
		} else if (keyword.endsWith("_NAME")) {
			ele = driver.findElement(By.name(or.getProperty(keyword)));
		} else if (keyword.endsWith("_CSS")) {
			ele = driver.findElement(By.cssSelector(or.getProperty(keyword)));
		} else if (keyword.endsWith("_XPATH")) {
			ele = driver.findElement(By.xpath(or.getProperty(keyword)));
		} else if (keyword.endsWith("_CLASS")) {
			ele = driver.findElement(By.className(or.getProperty(keyword)));
		}
		return ele;
	}

	public boolean isElementPresent(String keyword) {

		try {
			getWebElement(keyword);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			log.error("Unable to locate the element " + keyword);
			ExtentListeners.test.fail(e.getMessage());
			// Assert.fail(e.getMessage());
			return false;
		}
	}

	// type/click/select
	public static void type(String keyword, String value) {
		try {
			getWebElement(keyword).clear();
			getWebElement(keyword).sendKeys(value);
		} catch (Exception e) {
			log.error("Element is not found - unable to type on element with keyword: " + keyword);
			log.error(e.getMessage());
		}
		ExtentListeners.test.info("Typed on field with a keyword as : " + keyword + " with  a value as : " + value);
		log.info("Typed on field with a keyword as : " + keyword + " with  a value as : " + value);
	}

	public static void click(String keyword) {
		try {
			getWebElement(keyword).click();
		} catch (Exception e) {
			log.error("Unable to click on element with keyword: " + keyword);
			log.error(e.getMessage());
		}
		log.info("Clicked on field with a keyword as : " + keyword);
		ExtentListeners.test.info("Clicked on field with a keyword as : " + keyword);
	}

	public static void selectElement(String keyword, String value) {
		WebElement ele;
		try {
		ele = getWebElement(keyword);
		Select s1 = new Select(ele);
		s1.selectByVisibleText(value);
		}
		catch (Exception e) {
			log.error("Unable to Select on element with keyword: " + keyword);
			log.error(e.getMessage());
			
		}
		
		ExtentListeners.test.info("Selected the dropdown with keyword as: " +keyword +" with value as:" + value);
		log.info("Selected the dropdown with keyword as: " +keyword +" with value as:" + value);
		
		
	}

	@AfterSuite
	public void tearDown() {
		driver.close();
		log.info("Execution Stopped");
	}

}
