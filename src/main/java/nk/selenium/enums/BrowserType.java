package nk.selenium.enums;

import static nk.selenium.constants.Constants.HEADLESS;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

public enum BrowserType {
    CHROME {
        @Override
        public WebDriver createDriverInstance() {
            System.out.println("Launching Chrome Browser...");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-infobars");
            options.addArguments("--disable-extensions");
            options.addArguments("--disable-popup-blocking");
            if(HEADLESS)
                options.addArguments("--headless=new");
            WebDriver driver = new ChromeDriver(options);
            if(HEADLESS)
                driver.manage().window().setSize(new Dimension(1920,1080));
            else
                driver.manage().window().maximize();
            return driver;
        }
    },
    FIREFOX {
        @Override
        public WebDriver createDriverInstance() {
            System.out.println("Launching FireFox Browser...");
            FirefoxOptions options = new FirefoxOptions();
            if(HEADLESS)
                options.addArguments("--headless");
            WebDriver driver = new FirefoxDriver(options);
            if(HEADLESS)
                driver.manage().window().setSize(new Dimension(1920,1080));
            else
                driver.manage().window().maximize();
            return driver;
        }
    },
    EDGE {
        @Override
        public WebDriver createDriverInstance() {
            System.out.println("Launching Edge Browser...");
            WebDriver driver = new EdgeDriver();
            driver.manage().window().maximize();
            return driver;
        }
    },
    SAFARI {
        @Override
        public WebDriver createDriverInstance() {
            WebDriver driver = new SafariDriver();
            driver.manage().window().maximize();
            return driver;
        }
    };

    public abstract WebDriver createDriverInstance();

}
