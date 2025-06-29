package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties property;

    static {
        try {
            FileInputStream file = new FileInputStream("./src/test/resources/config.properties");
            property = new Properties();
            property.load(file);
        } catch (IOException e) {
            System.err.println("Failed to load config.properties: " + e.getMessage());
        }
    }

    public static String getProperty(String key) {
        return property.getProperty(key);
    }
}
