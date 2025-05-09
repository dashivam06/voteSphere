package com.voteSphere.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppConfig {

    private static final Logger logger = LogManager.getLogger(AppConfig.class);
    private static final Properties properties = new Properties();

    static {
        try (InputStream input =  AppConfig.class.getClassLoader().getResourceAsStream("application.properties")){
            if (input == null) {
                logger.error("application.properties not found in classpath");
                throw new RuntimeException("application.properties not found in classpath");
            }
            properties.load(input);
            logger.info("Loaded application.properties successfully.");
        } catch (IOException ex) {
            logger.error("Failed to load application.properties", ex);
            throw new RuntimeException("Failed to load application.properties", ex);
        }
    }

    public static String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            logger.warn("Property '{}' not found in application.properties", key);
        }
        return value;
    }
}
