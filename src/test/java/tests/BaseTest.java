package tests;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;


public class BaseTest {
	
public static WebDriver driver;
	
	@BeforeTest
	public void setup() {
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
		driver.manage().window().maximize();
	}
	
	@Test(priority=1)
	public void test1(){
		System.out.println("hi");
		Assert.assertEquals("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login",driver.getCurrentUrl() );
		
	}
	
	@Test(priority=2)
	public void test2() {
		driver.findElement(By.xpath("//button[text()='Customer Login']")).click();
		Select s=new Select(driver.findElement(By.xpath("//select[@id='userSelect']")));
		s.selectByValue("1");
		driver.findElement(By.xpath("//button[text()='Login']")).click();
		try {
			driver.findElement(By.xpath("//strong[contains(text(),'Welcome')]"));
		}catch(Exception exp){
			Assert.assertTrue(false);
		}
		Assert.assertEquals("1001", driver.findElement(By.xpath("(//div[@class='center'])[1]//strong[1]")).getText());
	}
	
	@Test(priority=3)
	public void depositAmountTest() throws InterruptedException {
		driver.findElement(By.xpath("(//div[@class='center'])[2]//button[2]")).click();
		driver.findElement(By.xpath("//input[@placeholder='amount']")).sendKeys("100");
		driver.findElement(By.xpath("//button[text()='Deposit']")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//span[@class='error ng-binding']"));
	}
	
	@Test(priority=4)
	public void withdrawTest() throws InterruptedException {
		int orgBal=Integer.parseInt(driver.findElement(By.xpath("(//div[@class='center'])[1]//strong[2]")).getText());
		driver.findElement(By.xpath("(//div[@class='center'])[2]//button[3]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@placeholder='amount']")).sendKeys("100");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//button[text()='Withdraw']")).click();
		Thread.sleep(3000);
		int newBal=Integer.parseInt(driver.findElement(By.xpath("(//div[@class='center'])[1]//strong[2]")).getText());
		Assert.assertEquals(orgBal-100, newBal);
	}
	
	@Test(priority=5)
	public void logoutFunc() throws InterruptedException {
		Thread.sleep(3000);
		driver.findElement(By.cssSelector("button[ng-show='logout']")).click();
		Thread.sleep(3000);
		String expUrl="https://www.globalsqa.com/angularJs-protractor/BankingProject/#/customer";
		String actUrl=driver.getCurrentUrl();
		Assert.assertEquals(expUrl, actUrl);
		
	}
	
	
	@Test(priority=6)
	public void testManagerFunctionality() throws InterruptedException {
		driver.findElement(By.cssSelector("button[ng-click='home()']")).click();
		String expUrl="https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login";
		String actUrl=driver.getCurrentUrl();
		Assert.assertEquals(expUrl, actUrl);
		driver.findElement(By.cssSelector("button[ng-click='manager()']")).click();
		Thread.sleep(3000);
		driver.findElement(By.cssSelector("button[ng-click='openAccount()']")).click();
		Thread.sleep(2000);
		Select c=new Select(driver.findElement(By.cssSelector("select[id='userSelect']")));
		c.selectByValue("1");
		Select cur=new Select(driver.findElement(By.cssSelector("select[id='currency']")));
		cur.selectByIndex(1);
		
		driver.findElement(By.xpath("//button[text()='Process']")).click();
		
		try {
			driver.switchTo().alert().accept();
		}catch(Exception exp) {
			Assert.assertTrue(false);
		}
		
		
	}
	@AfterTest
	public void windUp() {
		driver.close();
	}
	

}
