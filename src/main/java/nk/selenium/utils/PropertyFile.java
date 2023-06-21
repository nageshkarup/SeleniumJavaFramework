package nk.selenium.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyFile {

    private static String path = "/src/main/resources/config.properties";
    private static Properties properties;
    private static FileInputStream input;

	public static Properties load() {
		properties = new Properties();
		try {
			input = new FileInputStream(System.getProperty("user.dir") + path);
			properties.load(input);
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return properties;
	}

	public static String getValue(String key) {
		String value = null;
		try {
			if(input == null) {
				properties = new Properties();
				input = new FileInputStream(System.getProperty("user.dir") + path);
				properties.load(input);
				input.close();
			}
			value = properties.getProperty(key);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return value;
	}
}
