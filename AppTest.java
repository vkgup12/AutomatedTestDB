package myCompany.DeutscheBank;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class AppTest {

	WebDriver driver;
	Boolean Continue = false;
	WebElement elem;

	@AfterMethod
	public void getResult(ITestResult result) throws Exception {
		if (result.getStatus() == ITestResult.FAILURE) {
			System.out.println("Test Case Failed is " + result.getName());
			System.out.println("Test Case Failed is " + result.getThrowable());
			AppTest.getScreenhot(driver, result.getName());
			Continue = true;
		} else if (result.getStatus() == ITestResult.SKIP) {
			System.out.println("Test Case Skipped is " + result.getName());
		}
	}

	@AfterTest
	public void closeBrowser() {
		driver.quit();
	}

	@BeforeTest
	public void openBrowser() {
		System.setProperty("webdriver.gecko.driver", "F:\\Job Search\\Testing\\geckodriver.exe");
		driver = new FirefoxDriver();
	}

	public static void getScreenhot(WebDriver driver, String screenshotName) throws Exception {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = "F:\\Job Search\\Testing\\" + "FailedTestsScreenshots\\" + screenshotName + dateName
				+ ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
	}

	@Test(priority = 1)
	public void TestMethod() throws InterruptedException {

		driver.get("https://www.deutschebank.be");
		Thread.sleep(5000);
		elem = driver.findElement(By.linkText("Nederlands"));
		Assert.assertEquals(elem.isDisplayed(), true);
	}

	@Test(priority = 2)
	public void TestDutchHomePage() throws InterruptedException {

		if (Continue)
			throw new SkipException("Skipping this exception");

		driver.findElement(By.linkText("Nederlands")).click();
		Thread.sleep(5000);
		driver.findElement(By.className("callToAction")).click();
		Thread.sleep(5000);

		Assert.assertEquals(driver.findElement(By.linkText("Oplossingen")).isDisplayed(), true);

	}

	@Test(priority = 3)
	public void TestOplossingPage() throws InterruptedException {

		if (Continue)
			throw new SkipException("Skipping this exception");
		driver.findElement(By.linkText("Oplossingen")).click();
		Thread.sleep(5000);
		Assert.assertEquals(driver.findElement(By.xpath("//*[@class='solution-comp']/li/a")).isDisplayed(), true);

	}

	@Test(priority = 4)
	public void TestInvestPage() throws InterruptedException {

		if (Continue)
			throw new SkipException("Skipping this exception");
		driver.findElement(By.xpath("//*[@class='solution-comp']/li/a")).click();
		Thread.sleep(5000);
		Assert.assertEquals(driver.getPageSource().contains("funds"), true);

	}

	@Test(priority = 5)
	public void TestFundsPage() throws InterruptedException {

		if (Continue)
			throw new SkipException("Skipping this exception");
		driver.findElement(By.xpath("//*[@class='funds']/li/a")).click();
		Thread.sleep(5000);
		Assert.assertEquals(driver.getPageSource().contains("ISIN=LU0050427557"), true);

	}

	@Test(priority = 6)
	public void TestFundsSerach() throws InterruptedException {

		if (Continue)
			throw new SkipException("Skipping this exception");
		driver.findElement(By.name("funds_search")).sendKeys(Keys.ENTER);
		Thread.sleep(10000);
		Assert.assertEquals(driver.getPageSource().contains("fundsListResume"), true);
		System.out.println("You result Goes Here" + driver.findElement(By.className("amount")).getText());

		TestBack();
		Thread.sleep(5000);
		TestBack();
		Thread.sleep(5000);

	}

	public void TestBack() throws InterruptedException {

		driver.navigate().back();
		Thread.sleep(5000);
	}

	@Test(priority = 7)
	public void TestObligationsPage() throws InterruptedException {
		if (Continue)
			throw new SkipException("Skipping this exception");
		driver.findElement(By.partialLinkText("Obligaties")).click();
		Thread.sleep(5000);
		driver.findElement(By.linkText("Obligaties: de selectie")).click();
		Thread.sleep(15000);
		Assert.assertEquals(driver.getTitle().contains("Obligaties"), true);
	}

	@Test(priority = 8)
	public void TestOplossinglink() throws InterruptedException {
		if (Continue)
			throw new SkipException("Skipping this exception");
		driver.findElement(By.partialLinkText("Oplossingen")).click();
		Thread.sleep(5000);
		Assert.assertEquals(driver.getPageSource().contains("solution-comp"), true);
	}
}
