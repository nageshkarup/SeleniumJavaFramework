package nk.selenium.reports;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.listener.TestLifecycleListener;
import nk.selenium.drivers.DriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;

public class AllureManager implements TestLifecycleListener {

    @Attachment(value = "{0}", type = "text/plain")
    public  static String textLog(String message){
        return message;
    }

    @Attachment(value = "{0}", type = "text/html")
    public  static String htmlLog(String message){
        return message;
    }

    public static void addScreenShot(String name){
        byte[] screenshotBytes = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
        Allure.addAttachment(name, new ByteArrayInputStream(screenshotBytes));
    }


}
