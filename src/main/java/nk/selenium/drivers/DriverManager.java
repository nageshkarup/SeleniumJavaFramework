package nk.selenium.drivers;

import org.openqa.selenium.WebDriver;

public class DriverManager {

    private DriverManager() {

    }

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void setDriver(WebDriver d){
        driver.set(d);
    }
    public static WebDriver getDriver(){
        return driver.get();
    }

    public static void quitDriver() {
        if(DriverManager.getDriver() != null){
            driver.get().quit();
            driver.remove();
        }
    }

}

