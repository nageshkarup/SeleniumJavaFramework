package nk.selenium.constants;

import nk.selenium.utils.PropertyFileUtils;

public class Constants {

    public static final String CURRENT_DIR = System.getProperty("user.dir")+System.getProperty("file.separator");
    public static final String CONFIG_FILE_PATH = CURRENT_DIR+"src/main/resources/config.properties";

    public static final String URL =  PropertyFileUtils.getValue("URL");
    public static final Boolean HEADLESS = Boolean.parseBoolean(PropertyFileUtils.getValue("HEADLESS"));
    public static final String EXTENT_REPORT_TITLE = PropertyFileUtils.getValue("EXTENT_REPORT_TITLE");
    public static final String EXTENT_REPORT_PATH = PropertyFileUtils.getValue("EXTENT_REPORT_PATH");
    public static final String FRAMEWORK_AUTHOR = PropertyFileUtils.getValue("FRAMEWORK_AUTHOR");
    public static final long WAIT_SHORT = Long.parseLong(PropertyFileUtils.getValue("WAIT_SHORT"));
    public static final long WAIT_MEDIUM = Long.parseLong(PropertyFileUtils.getValue("WAIT_MEDIUM"));
    public static final long WAIT_LONG = Long.parseLong(PropertyFileUtils.getValue("WAIT_LONG"));

    public static final Boolean TAKE_SCREENSHOT_FAIL = Boolean.parseBoolean(PropertyFileUtils.getValue("TAKE_SCREENSHOT_FAIL"));
    public static final Boolean TAKE_SCREENSHOT_PASS = Boolean.parseBoolean(PropertyFileUtils.getValue("TAKE_SCREENSHOT_PASS"));

    public static final String EXCEL_FILE_PATH  = CURRENT_DIR+ PropertyFileUtils.getValue("EXCEL_FILE_PATH");


}
