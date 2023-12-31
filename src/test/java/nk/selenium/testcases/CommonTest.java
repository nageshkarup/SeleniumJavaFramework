package nk.selenium.testcases;

import com.aventstack.extentreports.Status;
import io.qameta.allure.*;
import nk.selenium.base.BaseTest;
import nk.selenium.listeners.TestListener;
import nk.selenium.reports.ReportManager;
import nk.selenium.utils.AssertionUtils;
import nk.selenium.utils.WebDriverActions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

public class CommonTest extends BaseTest {

    private Logger log = LogManager.getLogger(CommonTest.class);

    @Test(description = "Verify google page title",retryAnalyzer = TestListener.class)
    @Description("Verify google page title")
    @Feature("Feature 001: google title")
    @Story("verify logo matches")
    @Severity(SeverityLevel.MINOR)
    public void googleTest(){
        WebDriverActions.loadURL("https://www.google.com/");
        ReportManager.addElementScreenShot(By.cssSelector("img[alt='Google']"), Status.PASS,"Google logo");
        WebDriverActions.type(By.name("q"),"Selenium", Keys.ENTER);
        String title = WebDriverActions.getTitle();
        AssertionUtils.assertEquals(title,"Selenium - Google","Verify google page title");
        System.out.println(title);
    }

    @Test(description = "Verify bing page title",retryAnalyzer = TestListener.class)
    @Description("Verify google page title")
    @Feature("Feature 001: google title")
    @Story("verify logo matches")
    @Severity(SeverityLevel.MINOR)
    public void bingTest(){
        WebDriverActions.loadURL("https://www.bing.com/");
        String title = WebDriverActions.getTitle();
        AssertionUtils.assertEquals(title,"Bing","Verify bing page title");
        System.out.println(title);
    }

}
