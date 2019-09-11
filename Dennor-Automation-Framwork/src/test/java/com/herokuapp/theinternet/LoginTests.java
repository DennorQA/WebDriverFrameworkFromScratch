package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginTests {
		private WebDriver driver;
	
		
		
		
	@BeforeMethod(alwaysRun = true)
	private void setUp() {
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		driver = new ChromeDriver();
		System.out.println("LoginTest started");

		sleep(3000);
		driver.manage().window().maximize();
		
	}
	
	
	@Test(priority = 2, groups = { "PositiveTests", "SmokeTests"})
	public void PositiveTests() {
	
		String url = "http://the-internet.herokuapp.com/login";
		driver.get(url);
		System.out.println("Page opened");
		

		
		WebElement username = driver.findElement(By.xpath("//input[@id='username']"));
		username.sendKeys("tomsmith");

		WebElement password = driver.findElement(By.xpath("//input[@id='password']"));
		password.sendKeys("SuperSecretPassword!");

		WebElement submit = driver.findElement(By.xpath("//button[@type='submit']"));
		submit.click();
		
		
		
		
		String expectedUrl = "http://the-internet.herokuapp.com/secure";
		String actualUrl = driver.getCurrentUrl();
		Assert.assertEquals(actualUrl, expectedUrl, "Fail to get URL");
		
 
		
		WebElement SuccessMassage = driver.findElement(By.xpath("//a[@class='button secondary radius']"));
		Assert.assertTrue(SuccessMassage.isDisplayed(), "Fail to get button");
		  
		WebElement SuccesssMassage = driver.findElement(By.xpath("//div[@id='flash']"));  
		String expectedMessage = "You logged into a secure area!";
	    String actualMessage = SuccesssMassage.getText();
	//	Assert.assertEquals(actualMessage, expectedMessage, "Message not as expected");
		Assert.assertTrue(actualMessage.contains(expectedMessage), "is not as expected");
		
		System.out.println("Test Passed");
		
		
		
}
	
	
	@Parameters({ "username", "password", "expectedText" })

	@Test(priority = 2, groups = { "NegativeTest", "SmokeTests"})
// invalid login, valid password
	public void IncorectLoginTest(String username, String password, String expectedText)  {
		
		System.out.println("Starting negative test" + username + "and my" + password);
		
		
// open url
		String Url = "http://the-internet.herokuapp.com/login";
		driver.get(Url);
		System.out.println("WebSite opened");
		
		
		WebElement usernameElement = driver.findElement(By.xpath("//input[@id='username']"));
		usernameElement.sendKeys(username);
		
		WebElement passwordElement = driver.findElement(By.xpath("//input[@id='password']"));
		passwordElement.sendKeys(password);
		
		WebElement submit = driver.findElement(By.className("fa")); // "//input[@type='submit']"
		submit.click();
		
		
		WebElement text = driver.findElement(By.xpath("//div[@id='flash']"));
		
		String actualText = text.getText();
		Assert.assertTrue(actualText.contains(expectedText), "The actual text not as expected" + "\n expected text:" + expectedText+ "\n actual text:" + actualText);
		
		
		System.out.println("Passed");
		
	}


	
	
	@AfterMethod(alwaysRun = true)
	private void closWindow() {
		driver.quit();
	}
	

	private void sleep(long m) {
		try {
			Thread.sleep(m);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

}
