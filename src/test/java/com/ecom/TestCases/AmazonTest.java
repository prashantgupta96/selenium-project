package com.ecom.TestCases;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ecom.BaseClass.TestBase;
import com.ecom.Pages.AmazonHomepage;
import com.ecom.Utilities.TestUtility;

public class AmazonTest extends TestBase {

	AmazonHomepage amazon;
	TestUtility testUtil;
	Map<String, List<String>> testData;
	List<String> scenarioData;

	@BeforeClass(alwaysRun = true)
	public void setUp() {
		System.out.println(this.getClass().getSimpleName());
		testUtil = new TestUtility();
		testData = TestUtility.getTestData(this.getClass().getSimpleName());
		System.out.println(testData);
		initialization();
		Log.info("Application Launched Successfully");

		amazon = new AmazonHomepage();
		driver.get(property.getProperty("amznURL"));
	}

	@Test(priority = 1, enabled = true)
	public void validateURLNavigation(Method method) {
		extentTest = extent.startTest(method.getName());
		scenarioData = testData.get(method.getName());

		String actualURL = driver.getCurrentUrl();
		assertThat(actualURL, is(equalTo("https://www.amazon.in/")));
	}

	@Test(priority = 2, enabled = true)
	public void validateTitleNavigation(Method method) {
		extentTest = extent.startTest(method.getName());
		scenarioData = testData.get(method.getName());

		String actualTitle = driver.getTitle();
		assertThat(actualTitle, is(equalTo(scenarioData.get(0))));
	}

	@Test(priority = 3, enabled = true)
	public void validateCreateWishListNavigation(Method method) {
		extentTest = extent.startTest(method.getName());
		scenarioData = testData.get(method.getName());

		amazon.navigateToCreateWishlist();
		String actualTitle = driver.getTitle();
		assertThat(actualTitle, is(equalTo(scenarioData.get(0))));
	}

	@Test(priority = 4, enabled = true)
	public void validateSearchResult(Method method) throws InterruptedException {
		extentTest = extent.startTest(method.getName());
		scenarioData = testData.get(method.getName());

		amazon.searchAProduct(scenarioData.get(0));

		String actualTitle = driver.getTitle();
		assertThat(actualTitle, is(equalTo("Amazon.in : mi mobile")));
	}

	@Test(priority = 5, enabled = true)
	public void validateUserIsAbleToSelectfourStarRating(Method method) {
		extentTest = extent.startTest(method.getName());
		scenarioData = testData.get(method.getName());

		amazon.selectFourRating();

		String actualTitle = driver.getTitle();
		assertThat(actualTitle, is(equalTo("Amazon.in: mi mobile - 4 Stars & Up")));
	}

	@Test(priority = 6, enabled = true)
	public void validateUserIsAbleToClickOnFristProduct(Method method) {
		extentTest = extent.startTest(method.getName());
		scenarioData = testData.get(method.getName());

		amazon.clickOnFirstResult();
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String actualTitle = driver.getTitle();
		assertThat("title was not correct", actualTitle.contains(scenarioData.get(0)));
	}

	@Test(priority = 7, enabled = true)
	public void validateDealPriceDisplayed(Method method) {

		extentTest = extent.startTest(method.getName());
		scenarioData = testData.get(method.getName());

		assertThat("deal price not displayed", amazon.isDealPriceDisplayed());
	}

	@Test(priority = 8, enabled = true)
	public void validateMRPDisplayed(Method method) {
		extentTest = extent.startTest(method.getName());

		assertThat("deal price not displayed", amazon.isDealPriceDisplayed());
	}

	@Test(priority = 9, enabled = true)
	public void validateDeliveryDateDisplayed(Method method) {
		extentTest = extent.startTest(method.getName());
		scenarioData = testData.get(method.getName());

		assertThat("deal price not displayed", amazon.isDeliveryDateDisplayed());
	}

	@Test(priority = 10, enabled = true)
	public void validateDeliveryPinSelected(Method method) {
		extentTest = extent.startTest(method.getName());
		scenarioData = testData.get(method.getName());


		assertThat("deal price not displayed", amazon.isPINTextBox());
	}
}
