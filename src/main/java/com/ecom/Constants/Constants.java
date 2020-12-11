package com.ecom.Constants;

public class Constants {

	public static final String CHROME_DRIVER_PATH = System.getProperty("user.dir") + "/Drivers/chromedriver.exe";
	public static final String INTERNET_EXPLORER_DRIVER_PATH = System.getProperty("user.dir") + "/Drivers/IEDriverServer.exe";
	public static final String FIREFOX_DRIVER_PATH = System.getProperty("user.dir") + "/Drivers/geckodriver.exe";
	
	public static final long PAGE_LOAD_TIMEOUT = 30;
	public static final long IMPLICIT_WAIT = 15;
	public static final long EXPLICIT_WAIT = 15;

	public static final int SHORT_WAIT = 3000;
	public static final int MEDIUM_WAIT = 6000;
	public static final int LONG_WAIT = 10000;
	public static final String TEST_DATA_SHEET_PATH = "src\\main\\java\\TestData\\TestData.xlsx";
	public static final String HOME_PAGE_TITLE = null;
}
