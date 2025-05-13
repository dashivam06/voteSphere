package com.voteSphere.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.voteSphere.exception.DatabaseException;

public class DBConnectionManager {

	private static final Logger logger = LogManager.getLogger(DBConnectionManager.class);

	public static Connection establishConnection() {

		logger.trace("Attempting to establish database connection");

		final String DRIVER = AppConfig.get("JDBC_DRIVER");
		final String URL = AppConfig.get("JDBC_URL");
		final String USER = AppConfig.get("JDBC_USER");
		final String PASSWORD = AppConfig.get("JDBC_PASSWORD");

		try {
			Class.forName(DRIVER);

			Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

			if (connection == null || connection.isClosed()) {
				logger.error("Connection returned but is closed");
				throw new DatabaseException("Connection returned but is closed", null);
			}

			logger.debug("Database connection established successfully");
			return connection;
		} catch (ClassNotFoundException e) {
			logger.fatal("JDBC Driver not found", e);
			throw new DatabaseException("JDBC Driver not found", e);
		} catch (SQLException e) {
			String errorMsg = "Connection failed to " + URL;
			logger.error(errorMsg, e);

			// Handle specific connection errors
			if (e.getErrorCode() == 1045) { // Access denied
				throw new DatabaseException(errorMsg, "Invalid database credentials", e);
			} else if (e.getErrorCode() == 1049) { // Unknown database
				throw new DatabaseException(errorMsg, "Database does not exist", e);
			} else {
				throw new DatabaseException(errorMsg, "Cannot connect to database", e);
			}
		}
	}
}