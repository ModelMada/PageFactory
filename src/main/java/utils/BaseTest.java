package utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.google.common.io.Files;

import pages.BasePage;

public class BaseTest {

	public BasePage app; //app poate sa cheme toate metodele din Base Page
	public static WebDriver driver;		//obiectul va fi instantiat inainte de instantierea claselor, va avea aceeasi valoarea in toate clasele//vreau sa fie accesibil obiectul , este null, reprezinta browseru nostru
	
	@Parameters({"appURL"})			//parametrul se va folosi peste tot in metoda unde avem variabila de URL, la metoda adaugam un parametru de tip String url
	@BeforeClass(alwaysRun = true)
	public void setup(String appURL) {
		
		driver = new ChromeDriver();		
		driver.manage().window().maximize();		//face maximize la browser
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));	//setupul se propaga pe toata rularea, orice findElem va activa implicit wait. se pune in base test ca best practice, nu are sens sa il folosim in test
		driver.get(appURL);
		//driver.get("https://the-internet.herokuapp.com/javascript_alerts");
		//driver.get("https://the-internet.herokuapp.com/dynamic_loading/2");		
		app = new BasePage(driver); //creez obiectele inainte,
	}
	
	@AfterClass (alwaysRun = true)
	public void tearDown() throws InterruptedException {
		Thread.sleep(4000);			//ca sa se inchida browseru dupa 4 secunde -- este bad practice
		//driver.close();	//inchide tabul curent
		driver.quit();//inchide browserul indiferent de cate tab uri are deschise
	}
	
	@AfterMethod
	public void recordFailure(ITestResult result) {
		
		if(ITestResult.FAILURE == result.getStatus()) {
			TakesScreenshot poza = (TakesScreenshot) driver;
			File picture = poza.getScreenshotAs(OutputType.FILE);
			String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
			try {
				Files.copy(picture, new File("poze/" + result.getName() +" - " +timestamp+".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
