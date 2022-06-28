package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Variables;
import java.util.List;




public class SearchTitle {
	private WebDriver driver;
	private WebDriverWait wait;
	private JavascriptExecutor js;
	private static final int INDEXA = 0;

	@FindBy(css = ".rc-pagination-item")
	private List<WebElement> pages;
	@FindBy(id = "product_listing")
	private WebElement productPane;
	@FindBy(css = ".svg-icon")
	private List<WebElement> products;
	@FindBy(xpath = "//*[@id=\"details\"]/a[2]")
	private List<WebElement> products2;
	@FindBy(css = "div:last-child .add_qty.quantity")
	private WebElement txtQuantityFieldLastFoundItem;
	@FindBy(css = "div:last-child .btn.btn-cart.btn-small")
	private WebElement btnAddToCartLastFoundItem;
	@FindBy(id = "ag-sub-grid")
	private WebElement productAccessories;
	@FindBy (xpath = "//button[text()='Add to Cart']")
	private WebElement btnAddToCartOnProductAccessories;
	@FindBy(css = ".notify-body > a")
	private List<WebElement> buttons;
	@FindBy(css = "#paging > nav > ul > li.rc-pagination-item.rc-pagination-item-2 > a")
	private List<WebElement> nexts;
	@FindBy(css = ".rc-pagination-item-9 > .hover\\3A border-green-800")
	private WebElement lastpagination;


	public SearchTitle(WebDriver driver){
		this.driver = driver;
		this.js = (JavascriptExecutor)driver;
		wait = new WebDriverWait(driver,Variables.WAITING_CONTROL);
		PageFactory.initElements(driver,this);
	}

	public boolean checkContainsTitle(String keyword, int reportType){
		boolean result = true;
		Integer lastpageNum = Integer.parseInt(lastpagination.getText());
		System.out.println("Search Result is displayed totally in " + lastpageNum + " pages. ");
		for(int i=1; i<=lastpageNum; i++){
			int tempIndex = i;
			System.out.println("Verifying keyword in titles on page: " + tempIndex );
			boolean isCurrentPage = verifySearchResults(keyword,reportType);
			result = result && isCurrentPage;
			if(tempIndex < lastpageNum.intValue()){
				goToNextPage();
			}
		}
		return result;
	}

	public boolean verifySearchResults(String keyword, int reportType){
		boolean result = true;
		wait.until(ExpectedConditions.visibilityOf(productPane));
		int totalProductPerPage = products2.size();
		for(int i = 0; i<totalProductPerPage; i++){
			String productBoxId = "ProductBoxContainer" ;
			// System.out.println(this.products2.get(i).getText());
			boolean isContainKeyword = products2.get(i).getText().contains(keyword);
			if(!isContainKeyword){
				result = false;
				printFailedTitle(productBoxId,keyword,products2.get(i),reportType);
			}
		}
		return result;
	}

	public void printFailedTitle(String productId, String keyword, WebElement webElement, int reportType){
		if(reportType == Variables.REPORT){
			String productInfo = webElement.getText();
			String str = "FAILED : " + productId + " does not contain \"" + keyword + "\" in its title" + "\n" + productInfo;
			System.out.println(str);
		}
	}


	public void goToNextPage(){
		this.pages.get(INDEXA).click();



	}

}
