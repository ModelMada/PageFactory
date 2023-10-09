package tests;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import utils.BaseTest;

public class FindBrokenLinks extends BaseTest {

	static List<String> brokenLinks = new ArrayList<String>();
	
	@Test
	public void testBrokenLinks() throws IOException {
	
		List<WebElement> links = driver.findElements(By.cssSelector("a[href*='http']"));
		System.out.println(links.size());
		
		//pt un singur Link
		System.out.println("----------------");
		checkLinks("https://keyfood.ro/product/gortons-beer-battered-fish-fillets/");
		System.out.println("----------------");
		
		System.out.println("--------Bad URL--------");
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].setAttribute('href', 'https://keyfood.ro/product-category/breads-bake/')", 
				links.get(2));
		System.out.println("----------------- ");
		
		for(int i = 0; i < links.size()-1; i++) {
			WebElement element = links.get(i);	//ne ia linkurile separat ca sa iteram prin ele
			String url = element.getAttribute("href"); //din elementele respective scoatem doar linkul pe baza argumentului href
			if(url == null) {
				continue;
			}
			checkLinks(url);
		}
		
		assertTrue(brokenLinks.size() == 0);
	}
	
	public static void checkLinks(String linkUrl) throws IOException {
		
		try {
			
			URL url = new URL(linkUrl);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); //construim conexiunea, ca in postman cand costruim body la request
			httpURLConnection.setConnectTimeout(2000);
			httpURLConnection.connect();	//send Request din postman
			
			if(httpURLConnection.getResponseCode() == 200 ) {
				System.out.println(linkUrl +" --- " + httpURLConnection.getResponseCode());
			}
			
			if(httpURLConnection.getResponseCode() == 404) {
				System.out.println(linkUrl +" --- " + httpURLConnection.getResponseCode());	
				brokenLinks.add(linkUrl);
			}
			
		}catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}
}
