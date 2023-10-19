package utils;

import java.time.Duration;
import java.util.NoSuchElementException;

import javax.naming.NoInitialContextException;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.TestException;

//aici scriem metodele care sunt generice pentru mai multe clase
//le putem reutiliza si pt alte elemente

/**
 * Class used to wrap existing selenium methods and enhance them with additional functionalities
 * like retry mechanism, logging, etc
 * new methods can be added based on existing model
 * @author madalinamodel
 * 
 * 
 */

public class SeleniumWrappers extends BaseTest {

	public SeleniumWrappers(WebDriver driver) {
		this.driver=driver;
	}
	
	/**
	 * Wrapper method over selenium default click() method enhannced with
	 *1. waitForEleement to be clickable
	 *2. Retry ,echanism for StaleElement
	 *3. Logging for noSuchElementException
	 * 
	 * @param WebElement
	 * 
	 * 
	 * 
	 */
	
	public void click(WebElement element) {
		Log.info("called method <click> on element " + element);
		try {
			WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			// WebElement element = driver.findElement(element); //avem deja webElement
			element.click();
		}catch(NoSuchElementException e) {
			Log.error("Element not found in method <click()> after 10 sec wait " + element);
			Log.error(e.getMessage());
			throw new TestException("Element not found in method click");
		}catch (StaleElementReferenceException e)
		{
			//mai incerci o data, retry
			Log.error("StaleException not found on element " + element);
			element.click();
		}
	}
	
	public void scrollByPixels(int x, int y) throws InterruptedException {
		Actions action = new Actions(driver);
		action.scrollByAmount(x, y).perform();	
	}
	
	public WebElement returnElement(By locator) {
		return driver.findElement(locator);
	}
	
	public void sendKeys(WebElement element, String text) {
		Log.info("called method <sendKeys> on element " + element);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(element));
		element.clear();
		element.sendKeys(text);
	
	}
	
	public String getElementText(By locator) {
		return driver.findElement(locator).getText();
	}
	
	public void scrollVertically(int y) {
		Actions action = new Actions(driver);
		action.scrollByAmount(0, y).perform();	
	}
	
	public void scrollHorizontally(int x) {
		Actions action = new Actions(driver);
		action.scrollByAmount(x, 0).perform();	
	}
	
	public boolean verifyIfDisplayed(By locator) {
		return driver.findElement(locator).isDisplayed();
	}
	
	public void dragAndDropSlider(By locator, int x, int y) {
		WebElement element = driver.findElement(locator);
		Actions action = new Actions(driver);
		action.moveToElement(element).clickAndHold(element).moveByOffset(x,y).release().perform();
		
	}
	
	public boolean checkElementIsDisplayed(WebElement element) {
		return element.isDisplayed();
	}
	
	
}
