package nk.selenium.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import nk.selenium.constants.Constants;
import nk.selenium.drivers.DriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class ExtentReportManager {

    private static ExtentReports extentReports;
    public static void createReport(){
        if(extentReports == null){
            extentReports = new ExtentReports();
            ExtentSparkReporter spark = new ExtentSparkReporter("./ExtentReport/extent_report.html");
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

    public static void logMessage(String message){
        if(getExtentTest() != null)
            getExtentTest().info(message);
    }

    public static void logMessage(Status status,String message){
        if(getExtentTest() != null)
            getExtentTest().log(status,message);
    }

    public static void addScreenShot(Status status, String message) {
        String base64Image = "data:image/png;base64," + ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BASE64);
        getExtentTest().log(status,message, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build());
    }

}
