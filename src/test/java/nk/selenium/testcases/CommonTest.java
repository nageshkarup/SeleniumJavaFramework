package nk.selenium.testcases;

import nk.selenium.base.BaseTest;
import nk.selenium.drivers.DriverManager;
import nk.selenium.listeners.TestListener;
import nk.selenium.reports.ExtentReportManager;
import nk.selenium.utils.Log;
import nk.selenium.utils.WebDriverActions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CommonTest extends BaseTest {

    private Logger log = LogManager.getLogger(CommonTest.class);

    @Test(description = "Verify google page title",retryAnalyzer = TestListener.class)
    public void googleTest(){
        WebDriverActions.loadURL("https://www.google.com/");
        String title = WebDriverActions.getTitle();
        Assert.assertEquals(title,"Google");
    }

    @Test(description = "Verify yahoo page title",retryAnalyzer = TestListener.class)
    public void yahooTest(){
        WebDriverActions.loadURL("https://www.yahoo.com/");
        String title = WebDriverActions.getTitle();
        Assert.assertEquals(title,"Yahoo");
    }
}
