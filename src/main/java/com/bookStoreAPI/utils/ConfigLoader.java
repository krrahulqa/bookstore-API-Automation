package com.bookStoreAPI.utils;

import java.io.InputStream;
import java.util.Properties;

/*
 * ConfigLoader is a thread-safe singleton utility for loading configuration properties from
 * the 'config.properties' file located in the resources directory. It ensures global and consistent
 * access to configuration values (such as base URLs or credentials) throughout the framework.
 * Provides methods to fetch property values by key and validates presence of required properties.
 */

public class ConfigLoader {

    private static volatile ConfigLoader configLoader;
    private final Properties properties;

    private ConfigLoader() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties not found in resources directory");
            }
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties file", e);
        }
    }

    public static ConfigLoader getInstance() {
        if (configLoader == null) {
            synchronized (ConfigLoader.class) {
                if (configLoader == null) {
                    configLoader = new ConfigLoader();
                }
            }
        }
        return configLoader;
    }

    public String getBaseUrl() {
        String value = properties.getProperty("baseUrl");
        if (value == null || value.isEmpty()) {
            throw new RuntimeException("Missing required property: baseUrl");
        }
        return value;
    }

    public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.isEmpty()) {
            throw new RuntimeException("Missing required property: " + key);
        }
        return value;
    }
}
