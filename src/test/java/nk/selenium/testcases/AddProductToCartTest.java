package nk.selenium.testcases;

import nk.selenium.base.BaseTest;
import nk.selenium.dataprovider.DataProviders;
import nk.selenium.pages.ProductPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

public class AddProductToCartTest extends BaseTest {

    ProductPage productPage;

    @BeforeClass(alwaysRun = true)
    public void setUp(){
        productPage = new ProductPage();
    }

    @Test(dataProvider = "getProductDataMap",dataProviderClass = DataProviders.class)
    public void addMultipleProductToCart(Map<String,String> products){
        productPage.load();
        productPage.addProductTCart(products);
    }

}
