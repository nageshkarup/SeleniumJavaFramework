package nk.selenium.base;

import io.qameta.allure.Step;
import nk.selenium.drivers.DriverManager;
import nk.selenium.enums.BrowserType;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import static nk.selenium.enums.BrowserType.*;

public class BaseTest {



    @BeforeMethod
    @Parameters({"browser"})
    public void createDriverInstance(@Optional("CHROME") String browser){
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        WebDriver driver = setUp(browser);
        DriverManager.setDriver(driver);
    }

    @AfterMethod
    public void quitDriverInstance(){
        DriverManager.quitDriver();
    }

    @Step("Create browser {0} instance")
    public WebDriver setUp(String browserName) {
        WebDriver driver;
        switch (BrowserType.valueOf(browserName)){
            case CHROME:
                driver = CHROME.createDriverInstance();
                break;
            case FIREFOX:
                driver = FIREFOX.createDriverInstance();
                break;
            case EDGE:
                driver = EDGE.createDriverInstance();
                break;
            case SAFARI:
                driver = SAFARI.createDriverInstance();
                break;
            default:
                throw new InvalidArgumentException(browserName);
        }
        return driver;
    }

}
