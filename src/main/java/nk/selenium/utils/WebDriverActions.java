package nk.selenium.utils;

import com.aventstack.extentreports.Status;
import static nk.selenium.constants.Constants.*;
import nk.selenium.drivers.DriverManager;
import nk.selenium.reports.ExtentReportManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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


    public static void loadURL(String url) {
        driver().get(url);

        Log.info("Load URL : "+url);
        ExtentReportManager.logMessage(Status.PASS, "Load URL : "+url);
    }

    public static String getTitle() {
        String title = driver().getTitle();
        Log.info("Get page title : "+title);
        ExtentReportManager.logMessage(Status.PASS, "Get page title : "+title);
        return title;
    }

    public static void click(By by){
        getElement(by).click();
        Log.info("Click Element "+by);
        ExtentReportManager.logMessage(Status.PASS,"Click Element "+by);
    }

    public static void clickJs(By by){
        getJsExecutor().executeScript("arguments[0].click();", getElement(by));
        Log.info("Click Element using JavascriptExecutor "+by);
        ExtentReportManager.logMessage(Status.PASS,"Click Element using JavascriptExecutor "+by);
    }

    public static void clickActions(By by){
        Actions action = new Actions(driver());
        action.moveToElement(getElement(by))
                .click().build().perform();
        Log.info("Click Element using Actions "+by);
        ExtentReportManager.logMessage(Status.PASS,"Click Element using Actions "+by);
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
