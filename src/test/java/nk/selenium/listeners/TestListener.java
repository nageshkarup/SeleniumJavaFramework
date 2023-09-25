package nk.selenium.listeners;

import static nk.selenium.constants.Constants.*;

import com.aventstack.extentreports.Status;
import nk.selenium.reports.ReportManager;
import nk.selenium.utils.AssertionUtils;
import nk.selenium.utils.Log;
import nk.selenium.utils.PropertyFileUtils;
import org.testng.IRetryAnalyzer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener, IRetryAnalyzer{

    @Override
    public void onStart(ITestContext context) {
        // load all the values in the property file
        PropertyFileUtils.load();
        Log.info("Property file loaded");
        ReportManager.createReport();
    }

    @Override
    public void onFinish(ITestContext context) {
        ReportManager.flushReport();
        AssertionUtils.softAssertAll();;
    }

    @Override
    public void onTestStart(ITestResult result) {
        ReportManager.createTestCase(result.getName(),result.getMethod().getDescription());
        //ReportManager.addBrowsers();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        if(TAKE_SCREENSHOT_PASS){
            ReportManager.addScreenShot(Status.PASS,"Test case is passed");
        }
        ReportManager.logMessage(Status.PASS,"Test case is passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ReportManager.logMessage(Status.FAIL,"FAIL: "+result.getThrowable().getMessage());
        if(TAKE_SCREENSHOT_FAIL){
            ReportManager.addScreenShot(Status.FAIL,"SCREENSHOT TAKEN, TEST CASE FAILED");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ReportManager.logMessage(Status.SKIP,"SKIP: "+result.getThrowable().getMessage());
        if(TAKE_SCREENSHOT_FAIL){
            ReportManager.addScreenShot(Status.SKIP,"SCREENSHOT TAKEN, TEST CASE SKIPPED");
        }
    }


    //RetryListener
    private int count = 1;

    @Override
    public boolean retry(ITestResult result) {
        if(!result.isSuccess()) {
            int maximumTry = 2;
            if(count < maximumTry) {
                Log.info("Test case is failed retrying.../n"+result.getThrowable().getMessage());
                count++;
                return true;
            }
        }
        return false;
    }
}
