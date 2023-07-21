package nk.selenium.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import nk.selenium.constants.Constants;
import nk.selenium.drivers.DriverManager;
import nk.selenium.utils.Log;
import nk.selenium.utils.WebDriverActions;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;
import java.util.Base64;

public class ReportManager {

    private static ExtentReports extentReports;

    public static void createReport(){
        if(extentReports == null){
            extentReports = new ExtentReports();
            ExtentSparkReporter spark = new ExtentSparkReporter(Constants.EXTENT_REPORT_PATH);
            extentReports.attachReporter(spark);
            spark.config().setTheme(Theme.STANDARD);
            spark.config().setDocumentTitle(Constants.EXTENT_REPORT_TITLE);
            spark.config().setReportName(Constants.EXTENT_REPORT_TITLE);
            extentReports.setSystemInfo("Framework Name", Constants.EXTENT_REPORT_TITLE);
            extentReports.setSystemInfo("Author", Constants.FRAMEWORK_AUTHOR);
        }
    }

    public static void flushReport(){
        if(extentReports != null)
            extentReports.flush();
        removeExtentTest();
    }

    //Maintaining ExtentTest in the ThreadLocal class to avoid concurrency issues.
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    private static void setExtentTest(ExtentTest test){
        extentTest.set(test);
    }
    private static ExtentTest getExtentTest(){
        return extentTest.get();
    }

    private static void removeExtentTest(){
        extentTest.remove();
    }

    public static void createTestCase(String testCaseName){
        setExtentTest(extentReports.createTest(testCaseName));
    }

    public static void createTestCase(String testCaseName, String description){
        setExtentTest(extentReports.createTest(testCaseName,description));
    }

    @Step(value = "{0}")
    public static void logMessage(String message){
        if(getExtentTest() != null)
            getExtentTest().info(message);
        Log.info(message);
    }

    @Step(value = "{1}")
    public static void logMessage(Status status,String message){
        if(getExtentTest() != null)
            getExtentTest().log(status,message);
        Log.info(message);
    }

    @Step(value = "Add Screenshot: {0}: {1}")
    public static void addScreenShot(Status status, String message) {
        //For allure report
        byte[] screenshotBytes = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
        Allure.addAttachment(message, new ByteArrayInputStream(screenshotBytes));
        //For extent report
        String base64Image = "data:image/png;base64," + ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BASE64);
        getExtentTest().log(status,message, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build());
        Log.info(message);
    }

    @Step(value = "Add Screenshot of element: {0}")
    public static void addElementScreenShot(By by, Status status, String message) {
        WebDriverActions.waitForElementVisible(by);
        //For allure report
        byte[] bytes = WebDriverActions.getElement(by).getScreenshotAs(OutputType.BYTES);
        Allure.addAttachment("Screenshot of element "+by, new ByteArrayInputStream(bytes));
        //For extent report
        String screenshotBase64 = Base64.getEncoder().encodeToString(bytes);
        getExtentTest().log(status,message, MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotBase64).build());
        Log.info(message);
    }

    synchronized public static void addBrowsers(){
        if(getExtentTest() != null)
            getExtentTest().assignDevice(WebDriverActions.getBrowserInformation());
    }

}
