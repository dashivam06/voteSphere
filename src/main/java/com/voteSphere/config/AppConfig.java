package com.voteSphere.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
    private static final Logger logger = LogManager.getLogger(AppConfig.class);
    private static final Properties properties = new Properties();

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (InputStream input = AppConfig.class.getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (input == null) {
                throw new RuntimeException("application.properties not found in classpath");
            }

            // 1. Load raw properties
            properties.load(input);

            // 2. Resolve environment variables
            resolveEnvironmentVariables();

            logger.info("Loaded and resolved application.properties successfully");

        } catch (Exception ex) {
            logger.error("Failed to load configuration", ex);
            throw new RuntimeException("Configuration initialization failed", ex);
        }
    }

    private static void resolveEnvironmentVariables() {
        Properties resolved = new Properties();
        properties.forEach((keyObj, valueObj) -> {
            String key = keyObj.toString();
            String value = valueObj.toString();

            // Resolve ${ENV_VAR} patterns
            if (value.startsWith("${") && value.endsWith("}")) {
                String envVar = value.substring(2, value.length() - 1);
                String envValue = System.getenv(envVar);
                if (envValue != null) {
                    value = envValue;
                    logger.debug("Resolved {} -> {}", key, value);
                } else {
                    logger.warn("Environment variable {} not found for property {}", envVar, key);
                }
            }

            resolved.setProperty(key, value);
            System.setProperty(key, value); // Make available system-wide
        });
        properties.putAll(resolved);
    }

    public static String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            logger.warn("Property '{}' not found", key);
        }
        return value;
    }

    public static String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}