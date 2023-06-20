package nk.selenium.base;

import nk.selenium.drivers.DriverManager;
import nk.selenium.enums.BrowserType;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;


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

    public WebDriver setUp(BrowserType browserName) {
        WebDriver driver;
        switch (browserName){
            case CHROME:
                driver = BrowserType.CHROME.createDriverInstance();
                break;
            case FIREFOX:
                driver = BrowserType.FIREFOX.createDriverInstance();
                break;
            case EDGE:
                driver = BrowserType.EDGE.createDriverInstance();
                break;
            case SAFARI:
                driver = BrowserType.SAFARI.createDriverInstance();
                break;
            default:
                throw new InvalidArgumentException(browserName.toString());
        }
        return driver;
    }

}
