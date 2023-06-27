package nk.selenium.utils;

import com.aventstack.extentreports.Status;
import static nk.selenium.constants.Constants.*;

import io.qameta.allure.Step;
import nk.selenium.drivers.DriverManager;
import nk.selenium.reports.ExtentReportManager;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class WebDriverActions {

    private static WebDriver driver(){
        return DriverManager.getDriver();
    }

    private static JavascriptExecutor getJsExecutor(){
        return (JavascriptExecutor) driver();
    }

    public static WebElement getElement(By by) {
        return driver().findElement(by);
    }

    public static List<WebElement> getElements(By by){
        return driver().findElements(by);
    }


    @Step("Load URL: {0}")
    public static void loadURL(String url) {
        driver().get(url);

        Log.info("Load URL : "+url);
        ExtentReportManager.logMessage(Status.PASS, "Load URL : "+url);
    }

    @Step("Get Title")
    public static String getTitle() {
        String title = driver().getTitle();
        Log.info("Get page title : "+title);
        ExtentReportManager.logMessage(Status.PASS, "Get page title : "+title);
        return title;
    }

    @Step("Click element: {0}")
    public static void click(By by){
        getElement(by).click();
        Log.info("Click Element "+by);
        ExtentReportManager.logMessage(Status.PASS,"Click Element "+by);
    }

    @Step("Click element using JS: {0}")
    public static void clickJs(By by){
        getJsExecutor().executeScript("arguments[0].click();", getElement(by));
        Log.info("Click Element using JavascriptExecutor "+by);
        ExtentReportManager.logMessage(Status.PASS,"Click Element using JavascriptExecutor "+by);
    }

    @Step("Click element using actions: {0}")
    public static void clickActions(By by){
        Actions action = new Actions(driver());
        action.moveToElement(getElement(by))
                .click().build().perform();
        Log.info("Click Element using Actions "+by);
        ExtentReportManager.logMessage(Status.PASS,"Click Element using Actions "+by);
    }

    @Step("Set text {1} on {0}")
    public static void setText(By by, String text) {
        waitForElementVisible(by);
        getElement(by).sendKeys(text);
        Log.info("Set text : '"+text+"' on element : "+by.toString());
        ExtentReportManager.logMessage(Status.PASS, "Set text : '"+text+"' on element : "+by);
    }

    @Step("Set text {1} on {0}")
    public static void setText(By by, String text, Keys keys) {
        waitForElementVisible(by);
        getElement(by).sendKeys(text,keys);
        Log.info("Set text : '"+text+"' on element : "+by.toString());
        ExtentReportManager.logMessage(Status.PASS, "Set text : '"+text+"' on element : "+by);
    }

    @Step("Set text {1} on {0}")
    public static void clearAndSetText(By by, String text) {
        waitForElementVisible(by);
        clearText(by);
        getElement(by).sendKeys(text);
        Log.info("Set text : '"+text+"' on element : "+by.toString());
        ExtentReportManager.logMessage(Status.PASS, "Set text : '"+text+"' on element : "+by);
    }

    @Step("Clear text element {0}")
    public static void clearText(By by) {
        waitForElementVisible(by);
        getElement(by).clear();
        Log.info("Clear text on element : "+by.toString());
        ExtentReportManager.logMessage(Status.PASS, "Clear text on element : "+by);
    }

    @Step("Press enter on element {0}")
    public static void pressEnter(By by) {
        new Actions(driver()).moveToElement(getElement(by)).sendKeys(Keys.ENTER).build().perform();
    }


    public static String getBrowserInformation() {
        try {
            Capabilities capability = ((RemoteWebDriver) driver()).getCapabilities();
            return capability.getBrowserName().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void sleep(double seconds) {
        try {
            Thread.sleep((long) (seconds * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void waitForElementVisible(By by){
        try{
            WebDriverWait wait = new WebDriverWait(driver(), Duration.ofSeconds(WAIT_MEDIUM));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        }catch (Exception e){
            Log.error("Timeout waiting for the element Visible "+by+" exception is "+e.getClass().getSimpleName());
            Assert.fail("Timeout waiting for the element Visible " + by+" exception is "+e.getClass().getSimpleName());
        }
    }

    public static boolean waitForElementVisible(By by, long timeout){
        try{
            WebDriverWait wait = new WebDriverWait(driver(), Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        }catch (Exception e){
            Log.error("Timeout waiting for the element Visible "+by+" exception is "+e.getClass().getSimpleName());
            return false;
        }
    }

    public static void waitForElementPresent(By by){
        try{
            WebDriverWait wait = new WebDriverWait(driver(), Duration.ofSeconds(WAIT_MEDIUM));
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
        }catch (Exception e){
            Log.error("Timeout waiting for the element present "+by+" exception is "+e.getClass().getSimpleName());
            Assert.fail("Timeout waiting for the element present " + by+" exception is "+e.getClass().getSimpleName());
        }
    }




}
