package nk.selenium.utils;

import com.aventstack.extentreports.Status;
import io.qameta.allure.Step;
import nk.selenium.reports.AllureManager;
import nk.selenium.reports.ExtentReportManager;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class AssertionUtils {


    private static SoftAssert softAssert = new SoftAssert();

    public static void softAssertAll() {
        softAssert.assertAll();
    }

    @Step("Verify Assert Equals: {0} and {1}")
    public static void assertEquals(Object actual, Object expected, String message){
        var equals = message+" Verify assert equals: expected [" + expected + "] and actual [" + actual+"]";
        var notEquals = message+" Verify assert equals: expected [" + expected + "] but found [" + actual+"]";
        if(actual.equals(expected)) {
            Log.info(equals);
            ExtentReportManager.logMessage(Status.PASS, equals);
            AllureManager.textLog(equals);
        }else {
            Log.warn(notEquals);
            ExtentReportManager.logMessage(Status.FAIL, notEquals);
            AllureManager.textLog(notEquals);
            Assert.assertEquals(actual, expected, message);
        }
    }


    @Step("Verify Assert Text Contains : {0} and {1}")
    public static void assertTextContains(String actual, String expected, String message){
        var equals = message+" Verify assert text contains: expected [" + expected + "] and actual [" + actual+"]";
        var notEquals = message+" Verify assert text contains: expected [" + expected + "] but found [" + actual+"]";
        if(actual.contains(expected)) {
            Log.info(equals);
            ExtentReportManager.logMessage(Status.PASS, equals);
            AllureManager.textLog(equals);
        }else {
            Log.warn(notEquals);
            ExtentReportManager.logMessage(Status.FAIL, notEquals);
            AllureManager.textLog(notEquals);
            Assert.fail(notEquals);
        }
    }

    @Step("Verify Assert TRUE: {0}")
    public static void assertTrue(boolean condition, String message){
        var log = message+" Verify assert TRUE: "+condition;
        if(condition == true) {
            Log.info(log);
            ExtentReportManager.logMessage(Status.PASS, log);
            AllureManager.textLog(log);
        }else {
            Log.warn(log);
            ExtentReportManager.logMessage(Status.FAIL, log);
            AllureManager.textLog(log);
            Assert.assertTrue(condition,log);
        }
    }


    @Step("Verify Assert Equals: {0} and {1}")
    public static void softAssertEquals(Object actual, Object expected, String message){
        var equals = message+" Verify assert equals: expected [" + expected + "] and actual [" + actual+"]";
        var notEquals = message+" Verify assert equals: expected [" + expected + "] but found [" + actual+"]";
        if(actual.equals(expected)) {
            Log.info(equals);
            ExtentReportManager.logMessage(Status.PASS, equals);
            AllureManager.textLog(equals);
        }else {
            Log.warn(notEquals);
            ExtentReportManager.logMessage(Status.FAIL, notEquals);
            AllureManager.textLog(notEquals);
            softAssert.assertEquals(actual, expected, message);
        }
    }

    @Step("Verify Assert Text Contains : {0} and {1}")
    public static void softAssertTextContains(String actual, String expected, String message){
        var equals = message+" Verify assert text contains: expected [" + expected + "] and actual [" + actual+"]";
        var notEquals = message+" Verify assert text contains: expected [" + expected + "] but found [" + actual+"]";
        if(actual.contains(expected)) {
            Log.info(equals);
            ExtentReportManager.logMessage(Status.PASS, equals);
            AllureManager.textLog(equals);
        }else {
            Log.warn(notEquals);
            ExtentReportManager.logMessage(Status.FAIL, notEquals);
            AllureManager.textLog(notEquals);
            softAssert.assertTrue(actual.contains(expected));
        }
    }



}
