package com.tamoino.webtest;

import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.Select;

import com.tamoino.webtest.Constants.Clickable;
import com.tamoino.webtest.Constants.Page;

public abstract class TamoinoTestCase extends TestCase {

	protected WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();
	private TestUserAgent testUserAgent = TestUserAgent.FIREFOX;

	private static final long DEFAUT_ELEMENT_FIND_WAIT_TIME = 30;
	private static final long DEFAUT_CLICK_WAIT_TIME = DEFAUT_ELEMENT_FIND_WAIT_TIME;
	private static final String BASE_URL = "http://localhost:8080/";
	private static final String BASE_APP = "Tamoino/";

	private enum TestUserAgent {
		FIREFOX, HTML_UNIT, IE, CHROME, SAFARI
		// TODO
		// , ANDROID, IPHONE
	}

	private void setDriver() {
		switch (testUserAgent) {
		case CHROME:
			driver = new ChromeDriver();
			break;
		case FIREFOX:
			driver = new FirefoxDriver();
			break;
		case IE:
			driver = new InternetExplorerDriver();
			break;
		case SAFARI:
			driver = new SafariDriver();
			break;
		case HTML_UNIT:
			driver = new HtmlUnitDriver();
			break;
		// TODO
		// case ANDROID:
		// driver = new AndroidDriver();
		// break;
		// case IPHONE:
		// driver = new IPhoneDriver();
		// break;
		}
	}

	@Before
	public void setUp() throws Exception {
		setDriver();
		driver.manage().timeouts().implicitlyWait(DEFAUT_ELEMENT_FIND_WAIT_TIME, TimeUnit.SECONDS);
		goToBaseLocation();
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	protected void goToBaseLocation() {
		driver.get(BASE_URL + BASE_APP);
	}

	/**
	 * Goes to the desired page and asserts that it worked by checking the new
	 * page title
	 * 
	 * @param page
	 */
	protected void goToPage(Constants.Page page) {
		String pageLinkId = page.getLinkId();
		driver.findElement(By.id(pageLinkId)).click();
		uiInterval();
		assertPage(page);
	}

	/**
	 * Fill in only text fields (text boxes and text areas)
	 * 
	 * @param elementId
	 * @param text
	 */
	protected void fillIn(String elementId, String text) {
		WebElement webElement = driver.findElement(By.id(elementId));
		webElement.clear();
		webElement.sendKeys(text);
	}

	/**
	 * Select an item in a select option
	 * 
	 * @param elementId
	 * @param option
	 */
	protected void select(String elementId, String option) {
		WebElement webElement = driver.findElement(By.id(elementId));
		Select select = new Select(webElement);
		select.selectByVisibleText(option);
	}

	/**
	 * Click any element (usually button, link, option-button or check-box)
	 * 
	 * @param elementId
	 */
	protected void click(Clickable elementId) {
		WebElement webElement = driver.findElement(By.id(elementId.getName()));
		webElement.click();
	}

	/**
	 * Same as the click method, however in this case a prefix is used to make
	 * the element id for cases like dynamic elements inside rows of HTML tables
	 * generated at JSP compilation time
	 * 
	 * @param prefix
	 * @param elementSuffix
	 */
	protected void click(String prefix, Clickable elementSuffix) {
		String elementId = prefix.concat(elementSuffix.getName());
		WebElement webElement = driver.findElement(By.id(elementId));
		webElement.click();
	}

	protected void clickAndWait(Clickable elementId) {
		click(elementId);
		uiInterval();
	}

	/**
	 * Add an interval between the last and the next UI interaction
	 */
	private void uiInterval() {
		driver.manage().timeouts().implicitlyWait(DEFAUT_CLICK_WAIT_TIME, TimeUnit.SECONDS);
	}

	protected void assertText(String elementId, String expectedText) {
		WebElement webElement = driver.findElement(By.id(elementId));
		String actualText = webElement.getText();
		Assert.assertEquals(expectedText, actualText);
	}

	/**
	 * Asserts that the current page is the correct one by checking its title
	 * 
	 * @param pageTitle
	 */
	protected void assertPage(Page page) {
		assertText("pageTitle", page.getTitle());
	}

	protected void assertStatusMessage(String expectedStatusMessage) {
		assertText("statusMessage", expectedStatusMessage);
	}

	protected void assertLocation(String expectedLocation) {
		try {
			String actualLocation = driver.getCurrentUrl();
			assertEquals(expectedLocation, actualLocation);
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
	}

	protected void assertElementPresent(String elementId) {
		driver.findElement(By.id(elementId));
	}
	
	protected void assertElementPresent(String prefix, Clickable elementSuffix) {
		String elementId = prefix.concat(elementSuffix.getName());
		driver.findElement(By.id(elementId));
	}

	// FIXME Find a way to do not take so much time
	// protected void assertElementNotPresent(String elementId) {
	// try {
	// driver.findElement(By.id(elementId));
	// } catch (NoSuchElementException e) {
	// return;
	// }
	// fail("Element " + elementId +
	// " was not supposed to be presented, but it is");
	// }

}
