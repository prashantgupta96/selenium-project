package com.ecom.BaseClass;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.log4testng.Logger;

import com.ecom.Constants.Constants;
import com.ecom.Utilities.TestUtility;
import com.ecom.Utilities.WebEventListener;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestBase {

	public static WebDriver driver;
	public static Properties property;
	public static ChromeOptions chromeOptions;
	public static EventFiringWebDriver e_driver;
//	public static WebEventListener eventListener;
	public static Logger Log;
	public static ExtentReports extent;
	public static ExtentTest extentTest;

	public TestBase() {
		Log = Logger.getLogger(this.getClass());
		try {
			property = new Properties();
			FileInputStream inputStream = new FileInputStream(
					System.getProperty("user.dir") + "/src/main/java/com/ecom/Config/Configuration.properties");
			property.load(inputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@BeforeTest
	public void setLog4j() {
		TestUtility.setDateForLog4j();

		extent = new ExtentReports(
				System.getProperty("user.dir") + "/AutomationReport/" + TestUtility.getSystemDate() + ".html");
		extent.addSystemInfo("Host Name", "Prashant Windows System");
		extent.addSystemInfo("User Name", "Prashant");
		extent.addSystemInfo("Environment", "Automation Test Report");
		extent.addSystemInfo("Host Name", "Gurmeet Windows System");
		extent.addSystemInfo("User Name", "Gurmeet");
		extent.addSystemInfo("Environment", "Automation Test Report");


	}

	public static void initialization() {
		String broswerName = property.getProperty("Browser");
		System.out.println(broswerName);

		if (broswerName.equals("Chrome")) {
			chromeOptions = new ChromeOptions();
			chromeOptions.setExperimentalOption("useAutomationExtension", false);
			chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));

			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			chromeOptions.setExperimentalOption("prefs", prefs);

			System.setProperty("webdriver.chrome.driver", Constants.CHROME_DRIVER_PATH);
			driver = new ChromeDriver(chromeOptions);
		} else if (broswerName.equals("IE")) {
			System.setProperty("webdriver.ie.driver", Constants.INTERNET_EXPLORER_DRIVER_PATH);
			driver = new InternetExplorerDriver();
		} else if (broswerName.equals("Firefox")) {
			System.setProperty("webdriver.gecko.driver", Constants.FIREFOX_DRIVER_PATH);
			driver = new FirefoxDriver();
		} else {
			System.out.println("Path of Driver Executable is not Set for any Browser");
		}

		e_driver = new EventFiringWebDriver(driver);

//		eventListener = new WebEventListener();
//		e_driver.register(eventListener);
		driver = e_driver;

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Constants.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(Constants.IMPLICIT_WAIT, TimeUnit.SECONDS);

//		driver.get(property.getProperty("Url"));

	}

	@AfterTest
	public void endReport() {
		extent.flush();
		extent.close();
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult result) throws IOException {
		System.out.println(result);
		Log.info("Browser Terminated");
		Log.info("-----------------------------------------------");
		if (result.getStatus() == ITestResult.FAILURE) {
			extentTest.log(LogStatus.FAIL, "Test Case Failed is " + result.getName()); // To Add Name in Extent Report.
			extentTest.log(LogStatus.FAIL, "Test Case Failed is " + result.getThrowable()); // To Add Errors and
																							// Exceptions in Extent
																							// Report.

			String screenshotPath = TestUtility.getScreenshot(driver, result.getName());
			extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(screenshotPath)); // To Add Screenshot in Extent
																							// Report.
		} else if (result.getStatus() == ITestResult.SKIP) {
			extentTest.log(LogStatus.SKIP, "Test Case Skipped is " + result.getName());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			extentTest.log(LogStatus.PASS, "Test Case Passed is " + result.getName());
		}
		extent.endTest(extentTest); // Ending Test and Ends the Current Test and Prepare to Create HTML Report.
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		driver.quit();
		Log.info("Browser Terminated");
		Log.info("-----------------------------------------------");
	}
}
