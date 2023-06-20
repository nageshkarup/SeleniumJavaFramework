package nk.selenium.testcases;

import com.aventstack.extentreports.Status;
import nk.selenium.base.BaseTest;
import nk.selenium.drivers.DriverManager;
import nk.selenium.reports.ExtentReportManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class CommonTest extends BaseTest {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com/");
        System.out.println(driver.getTitle());
        driver.quit();
    }

    @Test
    public void googleTest(){
        ExtentReportManager.createTestCase("Google Teste");
        DriverManager.getDriver().get("https://www.google.com/");
        ExtentReportManager.logMessage(DriverManager.getDriver().getTitle());
        ExtentReportManager.addScreenShot(Status.PASS,"Google Test");
    }
}
