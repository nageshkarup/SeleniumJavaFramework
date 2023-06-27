package nk.selenium.testcases;

import io.qameta.allure.*;
import nk.selenium.base.BaseTest;
import nk.selenium.drivers.DriverManager;
import nk.selenium.listeners.TestListener;
import nk.selenium.reports.ExtentReportManager;
import nk.selenium.utils.Log;
import nk.selenium.utils.WebDriverActions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
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
        WebDriverActions.setText(By.name("q"),"Selenium", Keys.ENTER);
        String title = WebDriverActions.getTitle();
        Assert.assertEquals(title,"Selenium - Google Search");
    }

    @Test(description = "Verify yahoo page title",retryAnalyzer = TestListener.class)
    @Description("Verify google page title")
    @Feature("Feature 001: google title")
    @Story("verify logo matches")
    @Severity(SeverityLevel.MINOR)
    public void yahooTest(){
        WebDriverActions.loadURL("https://www.yahoo.com/");
        String title = WebDriverActions.getTitle();
        Assert.assertEquals(title,"Yahoo");
    }

}
