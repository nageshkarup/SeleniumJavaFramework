package nk.selenium.utils;

import com.aventstack.extentreports.Status;
import nk.selenium.reports.ReportManager;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class AssertionUtils {


    private static SoftAssert softAssert = new SoftAssert();

    public static void softAssertAll() {
        softAssert.assertAll();
    }


    public static void assertEquals(Object actual, Object expected, String message){
        var equals = "<b>"+message+"</b> Verify object equals: expected [" + expected + "] and actual [" + actual+"]";
        var notEquals = "<b>"+message+"</b> Verify object equals: expected [" + expected + "] but found [" + actual+"]";
        if(actual.equals(expected)) {
            ReportManager.logMessage(Status.PASS, equals);
        }else {
            ReportManager.logMessage(Status.FAIL, notEquals);
            Assert.assertEquals(actual, expected, message);
        }
    }


    public static void assertTextContains(String actual, String expected, String message){
        var equals = "<b>"+message+"</b> Verify text contains: expected [" + expected + "] and actual [" + actual+"]";
        var notEquals = "<b>"+message+"</b> Verify text contains: expected [" + expected + "] but found [" + actual+"]";
        if(actual.contains(expected)) {
            ReportManager.logMessage(Status.PASS, equals);
        }else {
            ReportManager.logMessage(Status.FAIL, notEquals);
            Assert.fail(notEquals);
        }
    }


    public static void assertTrue(boolean condition, String message){
        var log = "<b>"+message+"</b> Verify assert TRUE: "+condition;
        if(condition == true) {
            ReportManager.logMessage(Status.PASS, log);
        }else {
            ReportManager.logMessage(Status.FAIL, log);
            Assert.assertTrue(condition,log);
        }
    }


    public static void softAssertEquals(Object actual, Object expected, String message){
        var equals = "<b>"+message+"</b> Verify object equals: expected [" + expected + "] and actual [" + actual+"]";
        var notEquals = "<b>"+message+"</b> Verify object equals: expected [" + expected + "] but found [" + actual+"]";
        if(actual.equals(expected)) {
            ReportManager.logMessage(Status.PASS, equals);
        }else {
            ReportManager.logMessage(Status.WARNING, notEquals);
            softAssert.assertEquals(actual, expected, message);
        }
    }


    public static void softAssertTextContains(String actual, String expected, String message){
        var equals = "<b>"+message+"</b> Verify text contains: expected [" + expected + "] and actual [" + actual+"]";
        var notEquals = "<b>"+message+"</b> Verify text contains: expected [" + expected + "] but found [" + actual+"]";
        if(actual.contains(expected)) {
            ReportManager.logMessage(Status.PASS, equals);
        }else {
            ReportManager.logMessage(Status.WARNING, notEquals);
            softAssert.assertTrue(actual.contains(expected));
        }
    }





}
