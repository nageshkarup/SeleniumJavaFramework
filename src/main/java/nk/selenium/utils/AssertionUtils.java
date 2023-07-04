package nk.selenium.utils;

import com.aventstack.extentreports.Status;
import io.qameta.allure.Step;
import nk.selenium.drivers.DriverManager;
import nk.selenium.reports.AllureManager;
import nk.selenium.reports.ExtentReportManager;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class AssertionUtils {


    private static SoftAssert softAssert = new SoftAssert();

    public static void softAssertAll() {
        softAssert.assertAll();
    }

    @Step("Assert Equals: {0} and {1}")
    public static void assertEquals(Object actual, Object expected, String message){
        var equals = "<b>"+message+"</b> Verify object equals: expected [" + expected + "] and actual [" + actual+"]";
        var notEquals = "<b>"+message+"</b> Verify object equals: expected [" + expected + "] but found [" + actual+"]";
        if(actual.equals(expected)) {
            Log.info(equals);
            ExtentReportManager.logMessage(Status.PASS, equals);
            AllureManager.htmlLog(equals);
        }else {
            Log.warn(notEquals);
            ExtentReportManager.logMessage(Status.FAIL, notEquals);
            AllureManager.htmlLog(notEquals);
            Assert.assertEquals(actual, expected, message);
        }
    }


    @Step("Assert Text Contains : {0} and {1}")
    public static void assertTextContains(String actual, String expected, String message){
        var equals = "<b>"+message+"</b> Verify text contains: expected [" + expected + "] and actual [" + actual+"]";
        var notEquals = "<b>"+message+"</b> Verify text contains: expected [" + expected + "] but found [" + actual+"]";
        if(actual.contains(expected)) {
            Log.info(equals);
            ExtentReportManager.logMessage(Status.PASS, equals);
            AllureManager.htmlLog(equals);
        }else {
            Log.warn(notEquals);
            ExtentReportManager.logMessage(Status.FAIL, notEquals);
            AllureManager.htmlLog(notEquals);
            Assert.fail(notEquals);
        }
    }

    @Step("Assert TRUE: {0}")
    public static void assertTrue(boolean condition, String message){
        var log = "<b>"+message+"</b> Verify assert TRUE: "+condition;
        if(condition == true) {
            Log.info(log);
            ExtentReportManager.logMessage(Status.PASS, log);
            AllureManager.htmlLog(log);
        }else {
            Log.warn(log);
            ExtentReportManager.logMessage(Status.FAIL, log);
            AllureManager.htmlLog(log);
            Assert.assertTrue(condition,log);
        }
    }


    @Step("Soft Assert Equals: {0} and {1}")
    public static void softAssertEquals(Object actual, Object expected, String message){
        var equals = "<b>"+message+"</b> Verify object equals: expected [" + expected + "] and actual [" + actual+"]";
        var notEquals = "<b>"+message+"</b> Verify object equals: expected [" + expected + "] but found [" + actual+"]";
        if(actual.equals(expected)) {
            Log.info(equals);
            ExtentReportManager.logMessage(Status.PASS, equals);
            AllureManager.htmlLog(equals);
        }else {
            Log.warn(notEquals);
            ExtentReportManager.logMessage(Status.FAIL, notEquals);
            AllureManager.htmlLog(notEquals);
            softAssert.assertEquals(actual, expected, message);
        }
    }

    @Step("Soft Assert Text Contains : {0} and {1}")
    public static void softAssertTextContains(String actual, String expected, String message){
        var equals = "<b>"+message+"</b> Verify text contains: expected [" + expected + "] and actual [" + actual+"]";
        var notEquals = "<b>"+message+"</b> Verify text contains: expected [" + expected + "] but found [" + actual+"]";
        if(actual.contains(expected)) {
            Log.info(equals);
            ExtentReportManager.logMessage(Status.PASS, equals);
            AllureManager.htmlLog(equals);
        }else {
            Log.warn(notEquals);
            ExtentReportManager.logMessage(Status.FAIL, notEquals);
            AllureManager.htmlLog(notEquals);
            softAssert.assertTrue(actual.contains(expected));
        }
    }





}
