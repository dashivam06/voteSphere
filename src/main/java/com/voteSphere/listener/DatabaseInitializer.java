package com.voteSphere.listener;

import com.voteSphere.config.AppConfig;
import com.voteSphere.config.DBConnectionManager;
import com.voteSphere.exception.DatabaseException;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@WebListener
public class DatabaseInitializer implements ServletContextListener {

    private static final Logger logger = LogManager.getLogger(DatabaseInitializer.class);
    private static final String SCHEMA_FILE_PATH = AppConfig.get("DB_SCHEMA_FILE");

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Starting database schema initialization...");

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(SCHEMA_FILE_PATH)) {
            if (inputStream == null) {
                logger.error("Schema file not found in resources: {}", SCHEMA_FILE_PATH);
                return;
            }

            String sql = new String(inputStream.readAllBytes());
            executeSqlStatements(sql);

        } catch (Exception e) {
            logger.error("Error during schema initialization", e);
        }
    }

    private void executeSqlStatements(String sql) {
        // Split by semicolon followed by newline to avoid splitting within statements
        String[] statements = sql.split(";(\\r)?\\n");

        try (Connection connection = DBConnectionManager.establishConnection();
             Statement statement = connection.createStatement()) {

            connection.setAutoCommit(false); // Start transaction

            for (String stmt : statements) {
                stmt = stmt.trim();
                if (!stmt.isEmpty()) {
                    try {
                        logger.debug("Executing SQL: {}", stmt);
                        statement.execute(stmt);
                    } catch (SQLException e) {
                        logger.error("Failed to execute statement: {}", stmt, e);
                        connection.rollback();
                        return;
                    }
                }
            }
            connection.commit();
            logger.info("Database schema executed successfully");

        } catch (SQLException e) {
            logger.error("Database error during schema execution", e);
        } catch (DatabaseException e) {
            logger.error("Database connection failed", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Context destroyed");
    }
}