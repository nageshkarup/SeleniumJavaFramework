package nk.selenium.base;

import io.qameta.allure.Step;
import nk.selenium.drivers.DriverManager;
import nk.selenium.enums.BrowserType;
import nk.selenium.listeners.TestListener;
import nk.selenium.reports.ExtentReportManager;
import nk.selenium.utils.Log;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

//@Listeners(TestListener.class)
public class BaseTest {



    @BeforeMethod
    @Parameters({"browser"})
    public void createDriverInstance(@Optional("CHROME") String browser){
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        WebDriver driver = setUp(BrowserType.valueOf(browser));
        DriverManager.setDriver(driver);
    }

    @AfterMethod
    public void quitDriverInstance(){
        DriverManager.quitDriver();
    }

    @Step("Create browser {0} instance")
    public WebDriver setUp(BrowserType browserName) {
        WebDriver driver;
        switch (browserName){
            case CHROME:
                driver = browserName.CHROME.createDriverInstance();
                break;
            case FIREFOX:
                driver = browserName.FIREFOX.createDriverInstance();
                break;
            case EDGE:
                driver = browserName.EDGE.createDriverInstance();
                break;
            case SAFARI:
                driver = browserName.SAFARI.createDriverInstance();
                break;
            default:
                throw new InvalidArgumentException(browserName.toString());
        }
        return driver;
    }

}
