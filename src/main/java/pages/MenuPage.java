package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.SeleniumWrappers;

public class MenuPage extends SeleniumWrappers {

	public MenuPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);	//fa find element, initializeaza elementele create cu findBy ca sa le putem localiza in aplicatie
	}
	
	@FindBy(linkText = "My account") //inlocuieste locatorul public By
	public WebElement myAccountLink;	//cream web elementul direct in menuPage, nu in pagina de test-> il putem apela direct cu metodele de click

}
