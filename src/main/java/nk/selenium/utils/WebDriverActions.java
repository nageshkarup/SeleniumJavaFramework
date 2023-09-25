package nk.selenium.utils;

import com.aventstack.extentreports.Status;
import static nk.selenium.constants.Constants.*;


import nk.selenium.drivers.DriverManager;
import nk.selenium.reports.ReportManager;
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
import java.util.function.Function;

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



    public static void loadURL(String url) {
        driver().get(url);
        ReportManager.logMessage(Status.PASS, "Load URL : "+url);
    }


    public static String getTitle() {
        String title = driver().getTitle();
        ReportManager.logMessage(Status.PASS, "Get page title : "+title);
        return title;
    }


    public static String getURL() {
        ReportManager.logMessage(Status.PASS, "Get page URL : "+driver().getCurrentUrl());
        return driver().getCurrentUrl();
    }


    public static void reloadPage() {
        driver().navigate().refresh();
        ReportManager.logMessage("Page reloaded URL :"+driver().getCurrentUrl());
    }

    public static void click(By by){
        waitForElementVisible(by);
        getElement(by).click();
        ReportManager.logMessage(Status.PASS,"Click Element "+by);
    }

    public static void click(WebElement element){
        waitForElementVisible(element);
        element.click();
        ReportManager.logMessage(Status.PASS,"Click Element "+element);
    }

    public static void clickJs(By by){
        waitForElementPresent(by);
        getJsExecutor().executeScript("arguments[0].click();", getElement(by));
        ReportManager.logMessage(Status.PASS,"Click Element using JavascriptExecutor "+by);
    }

    public static void clickActions(By by){
        waitForElementVisible(by);
        Actions action = new Actions(driver());
        action.moveToElement(getElement(by))
                .click().build().perform();
        ReportManager.logMessage(Status.PASS,"Click Element using Actions "+by);
    }

    public static void type(By by, String text) {
        waitForElementVisible(by);
        getElement(by).sendKeys(text);
        ReportManager.logMessage(Status.PASS, "Set text : '"+text+"' on element : "+by);
    }

    public static void type(By by, String text, Keys keys) {
        waitForElementVisible(by);
        getElement(by).sendKeys(text,keys);
        ReportManager.logMessage(Status.PASS, "Set text : '"+text+"' on element : "+by);
    }

    public static void clearAndType(By by, String text) {
        waitForElementVisible(by);
        clearText(by);
        getElement(by).sendKeys(text);
        ReportManager.logMessage(Status.PASS, "Set text : '"+text+"' on element : "+by);
    }

    public static void clearText(By by) {
        waitForElementVisible(by);
        getElement(by).clear();
        ReportManager.logMessage(Status.PASS, "Clear text on element : "+by);
    }

    public static void clearTextAction(By by) {
        waitForElementVisible(by);
        Actions action = new Actions(driver());
        action.moveToElement(getElement(by))
                .click()
                .keyDown(Keys.CONTROL).sendKeys("a")
                .keyUp(Keys.CONTROL).sendKeys(Keys.BACK_SPACE)
                .build().perform();
        ReportManager.logMessage(Status.PASS, "Clear text on element : "+by.toString());
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

    public static boolean isElementDisplayed(WebElement element) {
        try {
            boolean status = element.isDisplayed();
            if(status)
                Log.info("Element is displayed "+element);
            else
                Log.info("Element is not displayed "+element);
            return status;
        }catch(Exception e) {
            Log.info("Element is not displayed "+element);
            return false;
        }
    }

    public static boolean isElementDisplayed(By by) {
        try {
            boolean status = getElement(by).isDisplayed();
            if(status)
                Log.info("Element is displayed "+by);
            else
                Log.info("Element is not displayed "+by);
            return status;
        }catch(Exception e) {
            Log.info("Element is not displayed "+by);
            return false;
        }
    }

    public static boolean isElementChecked(By by) {
        waitForElementVisible(by);
        ReportManager.logMessage("Element checkbox status : "+getElement(by).isSelected()+" on "+by);
        return getElement(by).isSelected();
    }

    public static void interactWithElement(By by, Consumer<WebDriver> action) {
        long wait = WAIT_LONG;
        boolean flag = false;
        while(wait > 0) {
            try {
                action.accept(driver());
                flag = true;
                ReportManager.logMessage(Status.PASS, "Interacted with the Element : "+by);
                break;
            } catch (Exception e) {
                wait--;
                Log.info("Unable to interact with element : " + by + " because of " + e.getClass().getSimpleName() + ". So Retrying...");
                sleep(1);
            }
        }
        if(!flag){
            ReportManager.logMessage(Status.FAIL,"Timeout waiting for element to interact "+by);
            Assert.fail("Timeout waiting for element to interact "+by);
        }
    }

    public static <T> T interactWithElement(By by, Function<WebDriver,Object> condition) {
        long wait = WAIT_LONG;
        boolean flag = false;
        T t = null;
        while(wait > 0) {
            try {
                Object obj = condition.apply(driver());
                t = (T) obj;
                flag = true;
                ReportManager.logMessage(Status.PASS, "Interacted with the Element : "+by);
                break;
            } catch (Exception e) {
                wait--;
                Log.info("Unable to interact with element : " + by + " because of " + e.getClass().getSimpleName() + ". So Retrying...");
                sleep(1);
            }
        }
        if(!flag){
            ReportManager.logMessage(Status.FAIL,"Timeout waiting for element to interact "+by);
            Assert.fail("Timeout waiting for element to interact "+by);
        }
        return t;
    }

    public static String getInputFieldText(By by) {
        waitForElementVisible(by);
        String text = getElement(by).getAttribute("value");
        ReportManager.logMessage(Status.PASS, "Get text from input field : '"+text+"' on element : "+by);
        return text;
    }


    public static String getElementAttribute(By by, String attribute) {
        waitForElementVisible(by);
        String text = getElement(by).getAttribute(attribute);
        ReportManager.logMessage(Status.PASS, "Get text : '"+text+"' from attribute "+attribute+" on element : "+by.toString());
        return text;
    }

    public static String getElementCSSValue(By by, String css) {
        waitForElementVisible(by);
        String cssValue = getElement(by).getCssValue(css);
        ReportManager.logMessage(Status.PASS, "Get cssValue : '"+cssValue+"' on element : "+by.toString());
        return cssValue;
    }


    public static void scrollToTop(By by) {
        waitForElementPresent(by);
        getJsExecutor().executeScript("arguments[0].scrollIntoView(true);", getElement(by));
        ReportManager.logMessage("Scroll to element : "+by);
    }

    public static void scrollToBottom(By by) {
        waitForElementPresent(by);
        getJsExecutor().executeScript("arguments[0].scrollIntoView(false);", getElement(by));
        ReportManager.logMessage("Scroll till element : "+by);
    }

    public static void scrollToTop(WebElement element) {
        getJsExecutor().executeScript("arguments[0].scrollIntoView(true);", element);
        ReportManager.logMessage("Scroll to element : "+element.toString());
    }

    public static void highlightElement(By by) {
        scrollToTop(by);
        getJsExecutor().executeScript("arguments[0].style.border='3px solid red'", getElement(by));
        sleep(0.5);
    }

    public static void highlightElement(WebElement element) {
        scrollToTop(element);
        getJsExecutor().executeScript("arguments[0].style.border='3px solid red'", element);
        sleep(0.5);
    }

    public static boolean hoverOnElement(By by){
        try{
            new Actions(driver())
                    .moveToElement(getElement(by))
                    .perform();
            ReportManager.logMessage("hover on element"+by);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static boolean doubleClick(By by){
        try{
            new Actions(driver())
                    .doubleClick(getElement(by))
                    .perform();
            ReportManager.logMessage("Double click on element"+by);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static boolean rightClick(By by){
        try{
            new Actions(driver())
                    .contextClick(getElement(by))
                    .perform();
            ReportManager.logMessage("Right click on element"+by);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static boolean dragAndDropElement(By from, By to){
        try{
            new Actions(driver())
                    .dragAndDrop(getElement(from),getElement(to))
                    .build()
                    .perform();
            ReportManager.logMessage(Status.PASS,"Drag element "+from+"from to "+to);
            return true;
        }catch (Exception e){
            return false;
        }
    }



    /**
     *Handle Dropdowns
     * **/
    public static void selectDropdownByText(By by, String text) {
        waitForElementVisible(by);
        new Select(getElement(by)).selectByVisibleText(text);
        ReportManager.logMessage(Status.PASS, "Select dropdown by visible text "+text+"  on element : "+by);
    }

    public static void selectDropdownByValue(By by, String value) {
        waitForElementVisible(by);
        new Select(getElement(by)).selectByValue(value);
        ReportManager.logMessage(Status.PASS, "Select dropdown by value "+value+"  on element : "+by);
    }

    public static void selectDropdownByIndex(By by, int index) {
        waitForElementPresent(by);
        new Select(getElement(by)).selectByIndex(index);
        ReportManager.logMessage(Status.PASS, "Select dropdown by index "+index+"  on element : "+by.toString());
    }

    public static void selectDropdownDeselectAll(By by) {
        waitForElementVisible(by);
        new Select(getElement(by)).deselectAll();
        ReportManager.logMessage(Status.PASS, "Deselect all in the multidropdown "+by.toString());
    }

    public static void selectMultiDropdownOptions(By by,String[] options) {
        waitForElementVisible(by);
        Select select = new Select(getElement(by));
        select.deselectAll();
        for(String s : options)
            select.selectByVisibleText(s);
        ReportManager.logMessage(Status.PASS, "Select multiple values : "+Arrays.toString(options)+" in the multidropdown "+by.toString());
    }

    public static void selectMultiDropdownValues(By by,String[] values) {
        waitForElementVisible(by);
        Select select = new Select(getElement(by));
        select.deselectAll();
        for(String s : values)
            select.selectByValue(s);
        ReportManager.logMessage(Status.PASS, "Select multiple values : "+Arrays.toString(values)+" in the multidropdown "+by);
    }


    public static String getDropdownSelectedOptionText(By by) {
        waitForElementVisible(by);
        String option = new Select(getElement(by)).getFirstSelectedOption().getText();
        ReportManager.logMessage(Status.PASS, "Get selected option : "+option+" in dropdown "+by.toString());
        return option;
    }


    public static String getDropdownSelectedOptionValue(By by) {
        waitForElementVisible(by);
        String option = new Select(getElement(by)).getFirstSelectedOption().getAttribute("value");
        ReportManager.logMessage(Status.PASS, "Get selected option : "+option+" in dropdown "+by);
        return option;
    }


    public static String[] getDropdownAllSelectedOption(By by) {
        waitForElementVisible(by);
        return new Select(getElement(by)).getAllSelectedOptions()
                .stream()
                .map(WebElement::getText)
                .toArray(String[]::new);
    }

    public static void pressEnter(By by) {
        waitForElementVisible(by);
        new Actions(driver()).moveToElement(getElement(by)).sendKeys(Keys.ENTER).build().perform();
    }

    public static void pressTab(By by) {
        waitForElementVisible(by);
        new Actions(driver()).moveToElement(getElement(by)).sendKeys(Keys.TAB).build().perform();
    }

    public static void clickSomeWhere() {
        WebElement body = driver().findElement(By.tagName("body"));
        new Actions(driver()).moveToElement(body).click().perform();
    }

    /**
     * Handle Windows
     * */
    public static void openNewTab() {
        driver().switchTo().newWindow(WindowType.TAB);
        ReportManager.logMessage("Open and Switch to new Tab");
    }

    public static void openNewWindow() {
        driver().switchTo().newWindow(WindowType.WINDOW);
        ReportManager.logMessage("Open and Switch to new Window");
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
            ReportManager.logMessage("Switch to the "+index+" the window");
        }catch(NoSuchWindowException e) {
            throw new NoSuchWindowException("No window present for this index "+index);
        }
    }

    public static void switchToNewWindowOrTabByTitle(String title) {
        try {
            Set<String> windowsOrTabs = driver().getWindowHandles();
            for(String tabOrWindow : windowsOrTabs) {
                if(driver().switchTo().window(tabOrWindow).getTitle().toLowerCase().trim().equals(title.toLowerCase().trim())) {
                    driver().switchTo().window(tabOrWindow);
                    ReportManager.logMessage("Switch to the window contains title "+title);
                    break;
                }
            }
        }catch(NoSuchWindowException e) {
            throw new NoSuchWindowException("No window contains this title "+title);
        }
    }

    /***
     * Handle iFrames
     * */
    public static void switchToFrame(By by) {
        waitForFrameAvailableSwitchToIt(by);
        ReportManager.logMessage(Status.PASS, "Switch to iFrame by element : "+by);
    }

    public static void switchToParentFrame() {
        driver().switchTo().parentFrame();
        ReportManager.logMessage(Status.PASS, "Switch to parent frame");
    }

    public static void switchToFrame(WebElement element) {
        waitForFrameAvailableSwitchToIt(element);
        ReportManager.logMessage(Status.PASS, "Switch to iFrame by element : "+element);
    }


    public static void switchToFrame(int index, int timeout) {
        waitForFrameAvailableSwitchToIt(index, timeout);
        ReportManager.logMessage(Status.PASS, "Switch to iFrame by index : "+index);
    }

    public static void switchToDefaultContent() {
        driver().switchTo().defaultContent();
        ReportManager.logMessage(Status.PASS, "Switch to default content");
    }

    public static boolean isElementDisplayedIniFrame(By iFrame, By by) {
        switchToFrame(iFrame);
        boolean status = isElementDisplayed(by);
        ReportManager.logMessage(Status.INFO, "Find element "+by.toString()+" in frame: ["+status+"]");
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

    /***
     *Handle Alerts
     **/

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
            Assert.fail("Element "+by+" In this attribute "+attribute+" Not has this value "+value+" but found this actual value "+getElementAttribute(by, attribute));
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
            Assert.fail("Element "+ele+" In this attribute "+attribute+" Not has this value "+value+" but found this actual value ");
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

    public static void waitForElementClickable(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver(), Duration.ofSeconds(WAIT_MEDIUM));
            wait.until(ExpectedConditions.elementToBeClickable(element));
        }catch(Throwable error) {
            Log.error("Timeout waiting for the element to be clickable. "+element.toString());
            Assert.fail("Timeout waiting for the element to be clickable. " + element);
        }
    }

    public static void waitForElementClickable(By by, int seconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver(), Duration.ofSeconds(seconds));
            wait.until(ExpectedConditions.elementToBeClickable(by));
        }catch(Throwable error) {
            Log.error("Timeout waiting for the element to be clickable. "+by);
            Assert.fail("Timeout waiting for the element to be clickable. " + by);
        }
    }

}
