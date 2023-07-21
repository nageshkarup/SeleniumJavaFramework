package nk.selenium.pages;

import io.qameta.allure.Step;
import nk.selenium.utils.WebDriverActions;
import org.openqa.selenium.By;
import java.util.Map;


public class ProductPage {


    private By productViewPopup = By.cssSelector("#product-quick-view");
    private By productQty = By.cssSelector("input[name='quantity']");
    private By addProductToCart = By.cssSelector("#product-quick-view button[title='Add to Cart']");
    private By notificationTop = By.cssSelector("#notification-box-top");

    @Step("Load Product page")
    public ProductPage load(){
        WebDriverActions.loadURL("https://ecommerce-playground.lambdatest.io/index.php?route=product/category&path=18");
        return this;
    }

    @Step("Add to product id {0} and quantity {1}")
    public void addProductTCart(String id, String qty){
        String imagePath = "(//a[contains(@href,'product_id="+id+"')])[1]";
        WebDriverActions.scrollToTop(By.xpath(imagePath));
        WebDriverActions.hoverOnElement(By.xpath(imagePath));
        String viewProduct = "(//a[contains(@href,'product_id="+id+"')])[1]/../following-sibling::div/button[@title='Quick view']";
        WebDriverActions.clickJs(By.xpath(viewProduct));
        WebDriverActions.waitForElementVisible(productViewPopup);
        WebDriverActions.clearAndType(productQty,qty);
        WebDriverActions.click(addProductToCart);
        WebDriverActions.waitForElementVisible(notificationTop);
        WebDriverActions.sleep(2);
    }

    @Step("Add to product")
    public void addProductTCart(Map<String,String> data){
        String imagePath = "(//a[contains(@href,'product_id="+data.get("ID")+"')])[1]";
        WebDriverActions.scrollToTop(By.xpath(imagePath));
        WebDriverActions.hoverOnElement(By.xpath(imagePath));
        String viewProduct = "(//a[contains(@href,'product_id="+data.get("ID")+"')])[1]/../following-sibling::div/button[@title='Quick view']";
        WebDriverActions.clickJs(By.xpath(viewProduct));
        WebDriverActions.waitForElementVisible(productViewPopup);
        WebDriverActions.clearAndType(productQty,data.get("QTY"));
        WebDriverActions.click(addProductToCart);
        WebDriverActions.waitForElementVisible(notificationTop);
        WebDriverActions.sleep(1);
    }

}
