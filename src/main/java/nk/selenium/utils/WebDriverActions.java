package nk.selenium.utils;

import com.aventstack.extentreports.Status;
import static nk.selenium.constants.Constants.*;

import io.qameta.allure.Step;
import nk.selenium.drivers.DriverManager;
import nk.selenium.reports.AllureManager;
import nk.selenium.reports.ExtentReportManager;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class WebDriverActions {

    private WebDriverActions(){
        // Don't want to create an instance
    }

    private static WebDriver driver(){
        return DriverManager.getDriver();
    }

    private static JavascriptExecutor getJsExecutor(){
        return (JavascriptExecutor) driver();
    }

    public static WebElement getElement(By by) {
        return driver().findElement(by);
    }

    public static List<WebElement> getElements(By by){
        return driver().findElements(by);
    }


    @Step("Load URL: {0}")
    public static void loadURL(String url) {
        driver().get(url);

        Log.info("Load URL : "+url);
        ExtentReportManager.logMessage(Status.PASS, "Load URL : "+url);
    }


    public static String getTitle() {
        String title = driver().getTitle();
        Log.info("Get page title : "+title);
        ExtentReportManager.logMessage(Status.PASS, "Get page title : "+title);
        return title;
    }


    public static String getURL() {
        Log.info("Get page URL : "+driver().getCurrentUrl());
        ExtentReportManager.logMessage(Status.PASS, "Get page URL : "+driver().getCurrentUrl());
        return driver().getCurrentUrl();
    }


    public static void reloadPage() {
        driver().navigate().refresh();
        Log.info("Page reloaded URL :"+getURL());
    }

    @Step("Click element: {0}")
    public static void click(By by){
        waitForElementVisible(by);
        getElement(by).click();
        Log.info("Click Element "+by);
        ExtentReportManager.logMessage(Status.PASS,"Click Element "+by);
    }

    @Step("Click element: {0}")
    public static void click(WebElement element){
        waitForElementVisible(element);
        element.click();
        Log.info("Click Element "+element);
        ExtentReportManager.logMessage(Status.PASS,"Click Element "+element);
    }

    @Step("Click element using JS: {0}")
    public static void clickJs(By by){
        waitForElementPresent(by);
        getJsExecutor().executeScript("arguments[0].click();", getElement(by));
        Log.info("Click Element using JavascriptExecutor "+by);
        ExtentReportManager.logMessage(Status.PASS,"Click Element using JavascriptExecutor "+by);
    }

    @Step("Click element using actions: {0}")
    public static void clickActions(By by){
        waitForElementVisible(by);
        Actions action = new Actions(driver());
        action.moveToElement(getElement(by))
                .click().build().perform();
        Log.info("Click Element using Actions "+by);
        ExtentReportManager.logMessage(Status.PASS,"Click Element using Actions "+by);
    }

    @Step("Set text {1} on {0}")
    public static void type(By by, String text) {
        waitForElementVisible(by);
        getElement(by).sendKeys(text);
        Log.info("Set text : '"+text+"' on element : "+by.toString());
        ExtentReportManager.logMessage(Status.PASS, "Set text : '"+text+"' on element : "+by);
    }

    @Step("Set text {1} on {0}")
    public static void type(By by, String text, Keys keys) {
        waitForElementVisible(by);
        getElement(by).sendKeys(text,keys);
        Log.info("Set text : '"+text+"' on element : "+by.toString());
        ExtentReportManager.logMessage(Status.PASS, "Set text : '"+text+"' on element : "+by);
    }

    @Step("Set text {1} on {0}")
    public static void clearAndType(By by, String text) {
        waitForElementVisible(by);
        clearText(by);
        getElement(by).sendKeys(text);
        Log.info("Set text : '"+text+"' on element : "+by.toString());
        ExtentReportManager.logMessage(Status.PASS, "Set text : '"+text+"' on element : "+by);
    }

    @Step("Clear text element {0}")
    public static void clearText(By by) {
        waitForElementVisible(by);
        getElement(by).clear();
        Log.info("Clear text on element : "+by.toString());
        ExtentReportManager.logMessage(Status.PASS, "Clear text on element : "+by);
    }



    public static String getBrowserInformation() {
        try {
            Capabilities capability = ((RemoteWebDriver) driver()).getCapabilities();
            return capability.getBrowserName().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void sleep(double seconds) {
        try {
            Thread.sleep((long) (seconds * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void waitForElementVisible(By by){
        try{
            WebDriverWait wait = new WebDriverWait(driver(), Duration.ofSeconds(WAIT_MEDIUM));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        }catch (Exception e){
            Log.error("Timeout waiting for the element Visible "+by+" exception is "+e.getClass().getSimpleName());
            Assert.fail("Timeout waiting for the element Visible " + by+" exception is "+e.getClass().getSimpleName());
        }
    }

    public static void waitForElementVisible(WebElement element){
        try{
            WebDriverWait wait = new WebDriverWait(driver(), Duration.ofSeconds(WAIT_MEDIUM));
            wait.until(ExpectedConditions.visibilityOf(element));
        }catch (Exception e){
            Log.error("Timeout waiting for the element Visible "+element+" exception is "+e.getClass().getSimpleName());
            Assert.fail("Timeout waiting for the element Visible " + element+" exception is "+e.getClass().getSimpleName());
        }
    }

    public static void waitForElementVisible(WebElement element, long timeout){
        try{
            WebDriverWait wait = new WebDriverWait(driver(), Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.visibilityOf(element));
        }catch (Exception e){
            Log.error("Timeout waiting for the element Visible "+element+" exception is "+e.getClass().getSimpleName());
            Assert.fail("Timeout waiting for the element Visible " + element+" exception is "+e.getClass().getSimpleName());
        }
    }

    public static boolean waitForElementVisible(By by, long timeout){
        try{
            WebDriverWait wait = new WebDriverWait(driver(), Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        }catch (Exception e){
            Log.error("Timeout waiting for the element Visible "+by+" exception is "+e.getClass().getSimpleName());
            return false;
        }
    }

    public static void waitForElementPresent(By by){
        try{
            WebDriverWait wait = new WebDriverWait(driver(), Duration.ofSeconds(WAIT_MEDIUM));
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
        }catch (Exception e){
            Log.error("Timeout waiting for the element present "+by+" exception is "+e.getClass().getSimpleName());
            Assert.fail("Timeout waiting for the element present " + by+" exception is "+e.getClass().getSimpleName());
        }
    }

    @Step("Check element exist {0}")
    public static boolean isElementExist(By by) {
        List<WebElement> element = getElements(by);
        if(element.size() > 0) {
            Log.info("Check element exist "+by);
            return true;
        } else {
            Log.warn("Check element does not exist "+by);
            return false;
        }
    }

    @Step("Check element displayed {0}")
    public static boolean isElementDisplayed(WebElement element) {
        try {
            element.isDisplayed();
            Log.info("Element is displayed "+element);
            return true;
        }catch(Exception e) {
            Log.info("Element is not displayed "+element);
            return false;
        }
    }

    @Step("Check element displayed {0}")
    public static boolean isElementDisplayed(By by) {
        try {
            getElement(by).isDisplayed();
            Log.info("Element is displayed "+by);
            return true;
        }catch(Exception e) {
            Log.info("Element is not displayed "+by);
            return false;
        }
    }


    @Step("Check element is checked {0}")
    public static boolean isElementChecked(By by) {
        waitForElementVisible(by);
        Log.info("Element checkbox status : "+getElement(by).isSelected()+" on "+by);
        return getElement(by).isSelected();
    }

    @Step("Interact element {0}")
    public static void interactDynamicElements(By by, Consumer<WebDriver> action) {
        long wait = WAIT_LONG;
        boolean flag = false;
        while(wait > 0) {
            try {
                action.accept(driver());
                flag = true;
                ExtentReportManager.logMessage(Status.PASS, "Interacted with the Element : "+by);
                break;
            } catch (Throwable e) {
                wait--;
                Log.info("Unable to interact with element : " + by + " because of " + e.getClass().getSimpleName() + ". So Retrying...");
                ExtentReportManager.logMessage("Unable to interact with element : " + by + " because of " + e.getClass().getSimpleName() + ". So Retrying...");
                sleep(1);
            }
        }
        if(!flag){
            ExtentReportManager.logMessage(Status.FAIL,"Timeout waiting for element to interact "+by);
            AllureManager.textLog("Timeout waiting for element to interact "+by);
            Assert.fail("Timeout waiting for element to interact "+by);
        }
    }

    @Step("Get text of input element {0}")
    public static String getInputFieldText(By by) {
        waitForElementVisible(by);
        String text = getElement(by).getAttribute("value");
        Log.info("Get text from input field : '"+text+"' on element : "+by.toString());
        ExtentReportManager.logMessage(Status.PASS, "Get text from input field : '"+text+"' on element : "+by);
        return text;
    }

    @Step("Get text of attribute {1} on element {0}")
    public static String getElementAttribute(By by, String attribute) {
        waitForElementVisible(by);
        String text = getElement(by).getAttribute(attribute);
        Log.info("Get text : '"+text+"' from attribute "+attribute+" on element : "+by.toString());
        ExtentReportManager.logMessage(Status.PASS, "Get text : '"+text+"' from attribute "+attribute+" on element : "+by.toString());
        return text;
    }

    @Step("Get css value of {1} on element {0}")
    public static String getElementCSSValue(By by, String css) {
        waitForElementVisible(by);
        String cssValue = getElement(by).getCssValue(css);
        Log.info("Get cssValue : '"+cssValue+"' on element : "+by.toString());
        ExtentReportManager.logMessage(Status.PASS, "Get cssValue : '"+cssValue+"' on element : "+by.toString());
        return cssValue;
    }



    @Step("Scroll to element {0}")
    public static void scrollToElement(By by) {
        waitForElementPresent(by);
        getJsExecutor().executeScript("arguments[0].scrollIntoView(true);", getElement(by));
        Log.info("Scroll to element : "+by);
    }

    @Step("Scroll to element {0}")
    public static void scrollToElement(WebElement element) {
        getJsExecutor().executeScript("arguments[0].scrollIntoView(true);", element);
        Log.info("Scroll to element : "+element.toString());
    }

    @Step("highlight element {0}")
    public static void highlightElement(By by) {
        scrollToElement(by);
        getJsExecutor().executeScript("arguments[0].style.border='3px solid red'", getElement(by));
        sleep(0.5);
    }

    @Step("highlight element {0}")
    public static void highlightElement(WebElement element) {
        scrollToElement(element);
        getJsExecutor().executeScript("arguments[0].style.border='3px solid red'", element);
        sleep(0.5);
    }

    //Handle Dropdowns

    @Step("Select dropdown element {0} using text {1}")
    public static void selectDropdownByText(By by, String text) {
        waitForElementVisible(by);
        new Select(getElement(by)).selectByVisibleText(text);
        Log.info("Select dropdown by visible text "+text+"  on element : "+by);
        ExtentReportManager.logMessage(Status.PASS, "Select dropdown by visible text "+text+"  on element : "+by);
    }

    @Step("Select dropdown element {0} using value {1}")
    public static void selectDropdownByValue(By by, String value) {
        waitForElementVisible(by);
        new Select(getElement(by)).selectByValue(value);
        Log.info("Select dropdown by value "+value+"  on element : "+by);
        ExtentReportManager.logMessage(Status.PASS, "Select dropdown by value "+value+"  on element : "+by);
    }

    @Step("Select dropdown element {0} using index {1}")
    public static void selectDropdownByIndex(By by, int index) {
        waitForElementPresent(by);
        new Select(getElement(by)).selectByIndex(index);
        Log.info("Select dropdown by index "+index+"  on element : "+by.toString());
        ExtentReportManager.logMessage(Status.PASS, "Select dropdown by index "+index+"  on element : "+by.toString());
    }

    @Step("Deselect all values on dropdown element {0}")
    public static void selectDropdownDeselectAll(By by) {
        waitForElementVisible(by);
        new Select(getElement(by)).deselectAll();
        Log.info("Deselect all in the multidropdown "+by.toString());
        ExtentReportManager.logMessage(Status.PASS, "Deselect all in the multidropdown "+by.toString());
    }

    @Step("Select MultiDropdown element {0} with options {1}")
    public static void selectMultiDropdownOptions(By by,String[] options) {
        waitForElementVisible(by);
        Select select = new Select(getElement(by));
        select.deselectAll();
        for(String s : options)
            select.selectByVisibleText(s);
        Log.info("Select multiple values : "+ Arrays.toString(options)+" in the multidropdown "+by.toString());
        ExtentReportManager.logMessage(Status.PASS, "Select multiple values : "+Arrays.toString(options)+" in the multidropdown "+by.toString());
    }

    @Step("Select MultiDropdown element {0} with values {1}")
    public static void selectMultiDropdownValues(By by,String[] values) {
        waitForElementVisible(by);
        Select select = new Select(getElement(by));
        select.deselectAll();
        for(String s : values)
            select.selectByValue(s);
        Log.info("Select multiple values : "+ Arrays.toString(values)+" in the multidropdown "+by.toString());
        ExtentReportManager.logMessage(Status.PASS, "Select multiple values : "+Arrays.toString(values)+" in the multidropdown "+by);
    }


    public static String getDropdownSelectedOptionText(By by) {
        waitForElementVisible(by);
        String option = new Select(getElement(by)).getFirstSelectedOption().getText();
        Log.info("Get selected option : "+option+" in dropdown "+by.toString());
        ExtentReportManager.logMessage(Status.PASS, "Get selected option : "+option+" in dropdown "+by.toString());
        return option;
    }


    public static String getDropdownSelectedOptionValue(By by) {
        waitForElementVisible(by);
        String option = new Select(getElement(by)).getFirstSelectedOption().getAttribute("value");
        Log.info("Get selected option : "+option+" in dropdown "+by.toString());
        ExtentReportManager.logMessage(Status.PASS, "Get selected option : "+option+" in dropdown "+by);
        return option;
    }


    public static String[] getDropdownAllSelectedOption(By by) {
        waitForElementVisible(by);
        return new Select(getElement(by)).getAllSelectedOptions()
                .stream()
                .map(WebElement::getText)
                .toArray(String[]::new);
    }

    @Step("Press enter on element {0}")
    public static void pressEnter(By by) {
        waitForElementVisible(by);
        new Actions(driver()).moveToElement(getElement(by)).sendKeys(Keys.ENTER).build().perform();
    }

    @Step("Press tab on element {0}")
    public static void pressTab(By by) {
        waitForElementVisible(by);
        new Actions(driver()).moveToElement(getElement(by)).sendKeys(Keys.TAB).build().perform();
    }

    public static void clickSomeWhere() {
        WebElement body = driver().findElement(By.tagName("body"));
        new Actions(driver()).moveToElement(body).click().perform();
    }

    //Handle Windows
    public static void openNewTab() {
        driver().switchTo().newWindow(WindowType.TAB);
        Log.info("Open and Switch to new Tab");
    }

    public static void openNewWindow() {
        driver().switchTo().newWindow(WindowType.WINDOW);
        Log.info("Open and Switch to new Window");
    }

    public static void switchToWindow() {
        String currentWindow = driver().getWindowHandle();
        Set<String> allWindows = driver().getWindowHandles();
        for(String window : allWindows) {
            if(!window.equals(currentWindow)) {
                driver().switchTo().window(window);
                break;
            }
        }
    }

    public static void switchToNewWindowOrTabByIndex(int index) {
        try {
            Set<String> windowsOrTabs = driver().getWindowHandles();
            driver().switchTo().window(windowsOrTabs.toArray()[index].toString());
        }catch(NoSuchWindowException e) {
            Log.info(e.getMessage());
            throw new NoSuchWindowException("No window present for this index "+index);
        }
    }

    public static void switchToNewWindowOrTabByTitle(String title) {
        try {
            Set<String> windowsOrTabs = driver().getWindowHandles();
            for(String tabOrWindow : windowsOrTabs) {
                if(driver().switchTo().window(tabOrWindow).getTitle().toLowerCase().trim().equals(title.toLowerCase().trim())) {
                    driver().switchTo().window(tabOrWindow);
                    break;
                }
            }
        }catch(NoSuchWindowException e) {
            Log.info(e.getMessage());
            throw new NoSuchWindowException("No window contains this title "+title);
        }
    }

    //Handle iFrames
    @Step("Switch to frame on element {0}")
    public static void switchToFrame(By by) {
        waitForFrameAvailableSwitchToIt(by);
        Log.info("Switch to iFrame by element "+by.toString());
        ExtentReportManager.logMessage(Status.PASS, "Switch to iFrame by element : "+by);
    }

    @Step("Switch to frame on element {1}")
    public static void switchToFrame(WebElement element) {
        waitForFrameAvailableSwitchToIt(element);
        Log.info("Switch to iFrame by element "+element);
        ExtentReportManager.logMessage(Status.PASS, "Switch to iFrame by element : "+element);
    }


    public static void switchToFrame(int index, int timeout) {
        waitForFrameAvailableSwitchToIt(index, timeout);
        Log.info("Switch to iFrame by index "+index);
        ExtentReportManager.logMessage(Status.PASS, "Switch to iFrame by index : "+index);
    }

    public static void switchToDefaultContent() {
        driver().switchTo().defaultContent();
        Log.info("Switch to default content ");
        ExtentReportManager.logMessage(Status.PASS, "Switch to default content");
    }

    @Step("verify element {1} displayed on frame {0}")
    public static boolean isElementDisplayedIniFrame(By iFrame, By by) {
        switchToFrame(iFrame);
        boolean status = isElementDisplayed(by);
        Log.info("Find element "+by.toString()+" in frame: ["+status+"]");
        ExtentReportManager.logMessage(Status.INFO, "Find element "+by.toString()+" in frame: ["+status+"]");
        switchToDefaultContent();
        return status;
    }

    public static void waitForFrameAvailableSwitchToIt(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(driver(), Duration.ofSeconds(WAIT_MEDIUM));
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by));
        }catch(Throwable error) {
            Log.error("Timeout waiting for the iFrame availability "+by.toString());
            Assert.fail("Timeout waiting for the iFrame availability. " + by);
        }
    }

    public static void waitForFrameAvailableSwitchToIt(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver(), Duration.ofSeconds(WAIT_MEDIUM));
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));
        }catch(Throwable error) {
            Log.error("Timeout waiting for the iFrame availability "+element);
            Assert.fail("Timeout waiting for the iFrame availability. " + element);
        }
    }

    public static void waitForFrameAvailableSwitchToIt(int index) {
        try {
            WebDriverWait wait = new WebDriverWait(driver(), Duration.ofSeconds(WAIT_MEDIUM));
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(index));
        }catch(Throwable error) {
            Log.error("Timeout waiting for the iFrame availability "+index);
            Assert.fail("Timeout waiting for the iFrame availability. "+ index);
        }
    }

    public static void waitForFrameAvailableSwitchToIt(String name) {
        try {
            WebDriverWait wait = new WebDriverWait(driver(), Duration.ofSeconds(WAIT_MEDIUM));
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(name));
        }catch(Throwable error) {
            Log.error("Timeout waiting for the iFrame availability "+name);
            Assert.fail("Timeout waiting for the iFrame availability. "+ name);
        }
    }

    public static void waitForFrameAvailableSwitchToIt(By by, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver(), Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by));
        }catch(Throwable error) {
            Log.error("Timeout waiting for the iFrame availability "+by);
            Assert.fail("Timeout waiting for the iFrame availability. " + by);
        }
    }

    public static void waitForFrameAvailableSwitchToIt(int index, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver(), Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(index));
        }catch(Throwable error) {
            Log.error("Timeout waiting for the iFrame availability "+index);
            Assert.fail("Timeout waiting for the iFrame availability. " + index);
        }
    }

    //Handle Alerts

    public static void alertAccept() {
        waitForAlertPresent();
        driver().switchTo().alert().accept();
    }

    public static void alertDismiss() {
        waitForAlertPresent();
        driver().switchTo().alert().dismiss();
    }

    public static String alertGetText() {
        waitForAlertPresent();
        return driver().switchTo().alert().getText();
    }

    public static void alertSetText(String text) {
        waitForAlertPresent();
        driver().switchTo().alert().sendKeys(text);
    }

    public static void waitForAlertPresent() {
        try {
            WebDriverWait wait = new WebDriverWait(driver(), Duration.ofSeconds(WAIT_MEDIUM));
            wait.until(ExpectedConditions.alertIsPresent());
        }catch(Throwable error) {
            Log.error("Timeout waiting for the alert present. ");
            Assert.fail("Timeout waiting for the alert present. ");
        }
    }

    public static boolean verifyAlertPresent(long timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver(), Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.alertIsPresent());
            return true;
        }catch(Throwable error) {
            Log.error("Alert is not present");
            return false;
        }
    }



    public static String getStringFromBy(By by) {
        String byString = by.toString();
        if (byString.startsWith("By.cssSelector: ")) {
            return byString.replace("By.cssSelector: ", "");
        } else if (byString.startsWith("By.id: ")) {
            return byString.replace("By.id: ", "");
        } else if (byString.startsWith("By.name: ")) {
            return byString.replace("By.name: ", "");
        } else if (byString.startsWith("By.xpath: ")) {
            return byString.replace("By.xpath: ", "");
        } else {
            return byString;
        }
    }

    public static boolean waitForElementHasAttribute(By by, String attribute, String value) {
        try {
            WebDriverWait wait = new WebDriverWait(driver(),Duration.ofSeconds(WAIT_MEDIUM));
            wait.until(ExpectedConditions.attributeToBe(by, attribute, value));
            return true;
        }catch(Throwable error) {
            Log.error("Element "+by.toString()+" In this attribute "+attribute+" Not has this value "+value+" but found this actual value "+getElementAttribute(by, attribute));
            Assert.fail("Element "+by.toString()+" In this attribute "+attribute+" Not has this value "+value+" but found this actual value "+getElementAttribute(by, attribute));
            return false;
        }
    }

    public static boolean waitForElementHasAttribute(WebElement ele, String attribute, String value) {
        try {
            WebDriverWait wait = new WebDriverWait(driver(),Duration.ofSeconds(WAIT_MEDIUM));
            wait.until(ExpectedConditions.attributeToBe(ele, attribute, value));
            return true;
        }catch(Throwable error) {
            Log.error("Element "+ele.toString()+" In this attribute "+attribute+" Not has this value "+value+" but found this actual value ");
            Assert.fail("Element "+ele.toString()+" In this attribute "+attribute+" Not has this value "+value+" but found this actual value ");
            return false;
        }
    }

    public static void waitForTitleContains(String title) {
        try {
            WebDriverWait wait = new WebDriverWait(driver(), Duration.ofSeconds(WAIT_MEDIUM));
            wait.until(ExpectedConditions.titleContains(title));
        }catch(Throwable error) {
            Log.error("Timeout waiting for the title changes. "+title);
            Assert.fail("Timeout waiting for the title changes . " + title);
        }
    }


    public static void waitForTitleContains(String title, long timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver(), Duration.ofSeconds(timeOut));
            wait.until(ExpectedConditions.titleContains(title));
        }catch(Throwable error) {
            Log.error("Timeout waiting for the title changes. "+title);
            Assert.fail("Timeout waiting for the title changes . " + title);
        }
    }

    public static void waitForURLToBe(String url) {
        try {
            WebDriverWait wait = new WebDriverWait(driver(), Duration.ofSeconds(WAIT_MEDIUM));
            wait.until(ExpectedConditions.urlContains(url));
        }catch(Throwable error) {
            Log.error("Timeout waiting for the title changes. "+url);
            Assert.fail("Timeout waiting for the title changes . " + url);
        }
    }

}
