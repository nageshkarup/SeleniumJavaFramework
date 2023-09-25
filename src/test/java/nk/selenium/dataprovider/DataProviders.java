package nk.selenium.dataprovider;

import nk.selenium.constants.Constants;
import nk.selenium.utils.ExcelUtils;
import org.testng.annotations.DataProvider;

public class DataProviders {

    @DataProvider(name = "getProductDataMap", parallel = true)
    public Object[][] getProductData(){
        return ExcelUtils.getDataHashMap(Constants.EXCEL_FILE_PATH,"Products");
    }

}
