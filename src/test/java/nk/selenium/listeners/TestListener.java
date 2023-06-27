package nk.selenium.listeners;

import static nk.selenium.constants.Constants.*;

import com.aventstack.extentreports.Status;
import nk.selenium.reports.AllureManager;
import nk.selenium.reports.ExtentReportManager;
import nk.selenium.utils.Log;
import nk.selenium.utils.PropertyFile;
import org.testng.IRetryAnalyzer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener, IRetryAnalyzer {

    @Override
    public void onStart(ITestContext context) {
        // load all the values in the property file
        PropertyFile.load();
        Log.info("Property file loaded");
        ExtentReportManager.createReport();
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentReportManager.flushReport();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentReportManager.createTestCase(result.getName(),result.getMethod().getDescription());
        ExtentReportManager.addBrowsers();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        if(TAKE_SCREENSHOT_PASS){
            ExtentReportManager.addScreenShot(Status.PASS,"Test case is passed");
        }
        ExtentReportManager.logMessage(Status.PASS,"Test case is passed");
        AllureManager.textLog("Test case is passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentReportManager.logMessage(Status.FAIL,"Test case is failed");
        AllureManager.textLog("Test case is failed");
        if(TAKE_SCREENSHOT_FAIL){
            ExtentReportManager.addScreenShot(Status.FAIL,"Test case is failed");
            AllureManager.addScreenShot("Screenshot taken for failed test case "+result.getName());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReportManager.logMessage(Status.SKIP,"Test case is skipped");
        AllureManager.textLog("Test case is skipped");
        if(TAKE_SCREENSHOT_FAIL){
            ExtentReportManager.addScreenShot(Status.SKIP,"Test case is skipped");
            AllureManager.addScreenShot("Screenshot taken for skipped test case "+result.getName());
        }
    }


    //RetryListener
    private int count = 1;
    private int maximumTry = 2;

    @Override
    public boolean retry(ITestResult result) {
        if(!result.isSuccess()) {
            if(count < maximumTry) {
                count++;
                return true;
            }
        }
        return false;
    }
}
