/*
 * Project Name: Stock Accounting
 * TesterName: Ranga
 * Creation date: 19-12-22
 * Module Name: ObjectType
 */
package commonFunctions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utilities.PropertyFileUtil;

public class FunctionLibrary {
	public static WebDriver driver;
	public static String expetednumber;
	//method for start browser
	public static WebDriver startBrowser()throws Throwable
	{
		if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("chrome"))
		{
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}
		else if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("firefox"))
		{
			driver = new FirefoxDriver();
		}
		else
		{
			System.out.println("Browser value is not Matching");
		}
		return driver;
	}
	//method for url launching
	public static void openUrl(WebDriver driver)throws Throwable
	{
		driver.get(PropertyFileUtil.getValueForKey("Url"));
	}
	//method for wait for elmenet
	public static void waitForElement(WebDriver driver,String Locator_Type,String Locator_Value,
			String TestData)
	{
		WebDriverWait myWait = new WebDriverWait(driver, Integer.parseInt(TestData));
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			myWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locator_Value)));
		}
		else if(Locator_Type.equalsIgnoreCase("name"))
		{
			myWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(Locator_Value)));
		}
		else if(Locator_Type.equalsIgnoreCase("id"))
		{
			myWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Locator_Value)));
		}
	}
	//method for textbox
	public static void typeAction(WebDriver driver,String Locator_Type,String Locator_Value,String Testdata)
	{
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(Locator_Value)).clear();
			driver.findElement(By.xpath(Locator_Value)).sendKeys(Testdata);
		}
		else if(Locator_Type.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(Locator_Value)).clear();
			driver.findElement(By.name(Locator_Value)).sendKeys(Testdata);
		}
		else if(Locator_Type.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(Locator_Value)).clear();
			driver.findElement(By.id(Locator_Value)).sendKeys(Testdata);
		}
	}
	//method for click action
	public static void clickAction(WebDriver driver,String Locator_Type,String Locaor_Value)
	{
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(Locaor_Value)).click();
		}
		else if(Locator_Type.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(Locaor_Value)).click();
		}
		else if(Locator_Type.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(Locaor_Value)).sendKeys(Keys.ENTER);
		}
	}
	//method for validateTitle
	public static void validateTitle(WebDriver driver,String ExpectedTitle)
	{
		String ActualTitle =driver.getTitle();
		try {
			Assert.assertEquals(ActualTitle, ExpectedTitle,"Title is Not Matching");
		}catch(Throwable t)
		{
			System.out.println(t.getMessage());
		}
	}
	//method for close browser
	public static void closeBrowser(WebDriver driver)
	{
		driver.close();
	}
	//method for date Genarete
	public static String  generateDate()
	{
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("YYYY_MM_dd");
		return df.format(date);
	}
	//method for  mousse click
	public static  void mouseClick(WebDriver driver)throws Throwable
	{
		Actions ac = new Actions(driver);
		ac.moveToElement(driver.findElement(By.linkText("Stock Items"))).perform();
		Thread.sleep(3000);
		ac.moveToElement(driver.findElement(By.xpath("(//a[contains(.,'Stock Categories')])[2]"))).click().perform();
	}
	//method for stock table
	public static void stockTable(WebDriver driver,String ExpectedData)throws Throwable
	{
		if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).isDisplayed())
			//click search panel button 
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-panel"))).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).clear();
		Thread.sleep(3000);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).sendKeys(ExpectedData);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-button"))).click();
		Thread.sleep(4000);
		String Actualdata = driver.findElement(By.xpath("//table[@id='tbl_a_stock_categorieslist']/tbody/tr[1]/td[4]/div/span/span")).getText();
		System.out.println(ExpectedData+"      "+Actualdata);
		try {
			Assert.assertEquals(ExpectedData, Actualdata, "Category Name Not Found In Table");
		}catch(Throwable t)
		{
			System.out.println(t.getMessage());
		}

	}
	public static void captureData(WebDriver driver,String Locator_type,String Locator_Value)
	{
	 expetednumber =driver.findElement(By.name(Locator_Value)).getAttribute("value");
	}
	//method for supplier table
	public static void supplierTable(WebDriver driver)throws Throwable
	{
		if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).isDisplayed())
			//click search panel button 
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-panel"))).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).clear();
		Thread.sleep(3000);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).sendKeys(expetednumber);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-button"))).click();
		Thread.sleep(4000);
		String ActulNumber= driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr[1]/td[6]/div/span/span")).getText();
		System.out.println(expetednumber+"    "+ActulNumber);
		try {
		Assert.assertEquals(expetednumber, ActulNumber, "Supplier Number Not Found in Table");
		}catch(Throwable t)
		{
			System.out.println(t.getMessage());
		}
	}
}













