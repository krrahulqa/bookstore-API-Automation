package com.bookStoreAPI.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.io.File;

/*
 * ConfigReader loads configuration values from the config.properties file located under test/resources.
 * Provides static methods to access environment and API configuration required by the framework.
 * Ensures that all configuration is loaded once at class load time and enforces presence of required keys.
 * Used by utilities and service layers to fetch API base URI, content type, and any custom property.
 */

public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        try {
            String configPath = System.getProperty("user.dir") + File.separator + "src" +
                    File.separator + "test" + File.separator + "resources" +
                    File.separator + "config.properties";
            FileInputStream input = new FileInputStream(configPath);
            properties.load(input);
            input.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties file.", e);
        }
    }

    public static String getBaseUri() {
        String value = properties.getProperty("base.uri");
        if (value == null || value.isEmpty()) {
            throw new RuntimeException("Missing required config: model.uri");
        }
        return value;
    }

    public static String getContentType() {
        String value = properties.getProperty("content.type");
        if (value == null || value.isEmpty()) {
            throw new RuntimeException("Missing required config: content.type");
        }
        return value;
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.isEmpty()) {
            throw new RuntimeException("Missing required config: " + key);
        }
        return value;
    }
}
