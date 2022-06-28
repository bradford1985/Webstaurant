package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.SearchTitle;
import utils.Variables;
import utils.PageNavigationHandler;

import java.sql.Driver;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;



public class VerifyTableInPages {
	private WebDriver driver;
	private SearchTitle searchTitle;
	private JavascriptExecutor js;
	private int reportType;

	public VerifyTableInPages(){
		System.setProperty("webdriver.chrome.driver",Variables.DRIVE_PATH);
		driver = new ChromeDriver();
		searchTitle = new SearchTitle(driver);
		js = (JavascriptExecutor)driver;
		reportType = Variables.REPORT; // DETAIL_TESTCASE_REPORT
	}

	@Before
	public void setup(){
		// Go to https://www.webstaurantstore.com/
		driver.get(Variables.URL);
		driver.manage().window().maximize();

	}

	@After
	public void teardown(){
		System.out.println("Test Done! ");
		driver.quit();
	}

	@Test
	public void testA() {

		//Search for 'stainless work table'
		driver.findElement(By.id("searchval")).click();
		driver.findElement(By.id("searchval")).sendKeys("stainless work table");
		driver.findElement(By.cssSelector(".rounded-r-normal")).click();
		{
			WebDriverWait wait = new WebDriverWait(driver, 40);

		}

		//Check the search result ensuring every product has the word 'Table' in its title.
		boolean doesItContain = searchTitle.checkContainsTitle("Table",reportType);
		js.executeScript("window.scrollTo(0,75)");
		js.executeScript("window.scrollTo(0,8666)");
		driver.findElement(By.cssSelector(".rc-pagination-item-9 > .hover\\3A border-green-800")).click();
		js.executeScript("window.scrollTo(0,20)");
		js.executeScript("window.scrollTo(0,8666)");

		//Add the last of found items to Cart
		driver.findElement(By.xpath("(//input[@name=\'addToCartButton\'])[60]")).click();
		{
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//button[@type=\'submit\'])[5]")));
		}
		driver.findElement(By.xpath("(//button[@type=\'submit\'])[5]")).click();
		{
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("View Cart")));
		}
		driver.findElement(By.linkText("View Cart")).click();
		{
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".emptyCartButton")));
		}

		//Empty Cart
		driver.findElement(By.cssSelector(".emptyCartButton")).click();
		{
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".bg-gray-100 > .mr-2")));
		}
		driver.findElement(By.cssSelector(".bg-gray-100 > .mr-2")).click();
		{
			WebDriverWait wait = new WebDriverWait(driver, 40);

		}
		driver.close();
	}
}
