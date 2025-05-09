package com.voteSphere.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.voteSphere.config.DBConnectionManager;
import com.voteSphere.exception.DataAccessException;
import com.voteSphere.exception.DatabaseException;
import com.voteSphere.model.Token;

public class TokenDao {

    private static final Logger logger = LogManager.getLogger(TokenDao.class);

    public static boolean createToken(Token token) {
        if (token == null) {
            logger.error("Attempt to create null token");
            throw new IllegalArgumentException("Token cannot be null");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Attempting to create token for user ID: " + token.getUserID());
        }

        String sql = "INSERT INTO tokens (userID, token, createdAt, type, ipAddress, device, isUsed) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnectionManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (logger.isTraceEnabled()) {
                logger.trace("Setting parameters for token creation: userID=" + token.getUserID() + 
                            ", type=" + token.getType() + ", isUsed=" + token.isUsed());
            }

            stmt.setInt(1, token.getUserID());
            stmt.setString(2, token.getToken());
            stmt.setTimestamp(3, token.getCreatedAt());
            stmt.setString(4, token.getType());
            stmt.setString(5, token.getIpAddress());
            stmt.setString(6, token.getDevice());
            stmt.setBoolean(7, token.isUsed());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                logger.warn("No rows affected when creating token for user ID: " + token.getUserID());
                throw new DataAccessException("No rows affected when creating token",
                        "Failed to create token. Please try again.", null);
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    token.setToken_id(generatedKeys.getInt(1));
                    if (logger.isInfoEnabled()) {
                        logger.info("Successfully created token with ID " + token.getToken_id() + 
                                   " for user ID " + token.getUserID());
                    }
                    return true;
                } else {
                    logger.error("No ID obtained after creating token for user ID: " + token.getUserID());
                    throw new DataAccessException("No generated key returned for new token",
                            "Token was created but we couldn't retrieve its ID. Please contact support.", null);
                }
            }
        } catch (DatabaseException e) {
            logger.error("Connection error while creating token", e);
            throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);
        } catch (SQLException e) {
            if ("23000".equals(e.getSQLState())) {
                logger.warn("Duplicate token creation attempted for user ID: " + token.getUserID() + 
                           ". Error: " + e.getMessage());
                throw new DataAccessException("Duplicate token creation attempted",
                        "A token with these details already exists.", e);
            }

            logger.error("Database error while creating token. Error code: " + e.getErrorCode() + 
                        ", SQL state: " + e.getSQLState(), e);
            throw new DataAccessException("Database error while creating token: " + e.getMessage(),
                    "Failed to create token due to system error. Please try again later.", e);
        } catch (Exception e) {
            logger.error("Unexpected error while creating token", e);
            throw new DataAccessException("Unexpected error while creating token",
                    "An unexpected error occurred. Please contact support.", e);
        }
    }

    public static List<Token> getAllTokens() {
        if (logger.isDebugEnabled()) {
            logger.debug("Attempting to retrieve all tokens");
        }

        String sql = "SELECT * FROM tokens";
        List<Token> tokens = new ArrayList<>();

        try (Connection conn = DBConnectionManager.establishConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Token token = mapResultSetToToken(rs);
                tokens.add(token);
            }

            if (logger.isInfoEnabled()) {
                logger.info("Successfully retrieved " + tokens.size() + " tokens from the database");
            }

            return tokens;
        } catch (DatabaseException e) {
            logger.error("Connection error while retrieving all tokens", e);
            throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);
        } catch (SQLException e) {
            logger.error("SQL error while retrieving all tokens. Error code: " + e.getErrorCode() + 
                         ", SQL state: " + e.getSQLState(), e);
            throw new DataAccessException("Database error during retrieving all tokens",
                    "Failed to load tokens due to a system error. Please try again later.", e);
        } catch (Exception e) {
            logger.error("Unexpected error while retrieving all tokens", e);
            throw new DataAccessException("Unexpected error during retrieving all tokens",
                    "An unexpected error occurred. Please contact support.", e);
        }
    }

    public static Token findTokenById(int id) {
        if (logger.isDebugEnabled()) {
            logger.debug("Searching for token with ID: " + id);
        }

        String sql = "SELECT * FROM tokens WHERE token_id = ?";

        try (Connection conn = DBConnectionManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            if (logger.isTraceEnabled()) {
                logger.trace("Executing SQL query to find token by ID: " + id);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Token token = mapResultSetToToken(rs);
                    if (logger.isInfoEnabled()) {
                        logger.info("Found token with ID: " + token.getToken_id());
                    }
                    return token;
                } else {
                    logger.warn("No token found with ID: " + id);
                    return null;
                }
            }
        } catch (DatabaseException e) {
            logger.error("Database connection error while fetching token by ID: " + id, e);
            throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);
        } catch (SQLException e) {
            logger.error("SQL error while fetching token by ID: " + id + ". Error code: " + e.getErrorCode() + 
                         ", SQL state: " + e.getSQLState(), e);
            throw new DataAccessException("Database error while retrieving token",
                    "Failed to retrieve token due to system error. Please try again later.", e);
        } catch (Exception e) {
            logger.error("Unexpected error while fetching token by ID: " + id, e);
            throw new DataAccessException("Unexpected error while retrieving token",
                    "An unexpected error occurred. Please contact support.", e);
        }
    }

    public static boolean updateToken(Token token) {
        if (token == null) {
            logger.error("Attempt to update null token");
            throw new IllegalArgumentException("Token cannot be null");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Attempting to update token with ID: " + token.getToken_id());
        }

        String sql = "UPDATE tokens SET userID=?, token=?, createdAt=?, type=?, ipAddress=?, device=?, isUsed=? WHERE token_id=?";

        try (Connection conn = DBConnectionManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (logger.isTraceEnabled()) {
                logger.trace("Setting parameters for token update: userID=" + token.getUserID() + 
                            ", type=" + token.getType() + ", isUsed=" + token.isUsed() + 
                            ", ID=" + token.getToken_id());
            }

            stmt.setInt(1, token.getUserID());
            stmt.setString(2, token.getToken());
            stmt.setTimestamp(3, token.getCreatedAt());
            stmt.setString(4, token.getType());
            stmt.setString(5, token.getIpAddress());
            stmt.setString(6, token.getDevice());
            stmt.setBoolean(7, token.isUsed());
            stmt.setInt(8, token.getToken_id());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                logger.warn("No rows affected when updating token ID: " + token.getToken_id());
                throw new DataAccessException("No rows affected while updating token.",
                        "Failed to update token. Please try again.", null);
            }

            if (logger.isInfoEnabled()) {
                logger.info("Successfully updated token ID " + token.getToken_id());
            }
            return true;
        } catch (DatabaseException e) {
            logger.error("Connection error while updating token ID: " + token.getToken_id(), e);
            throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);
        } catch (SQLException e) {
            if ("23000".equals(e.getSQLState())) {
                logger.warn("Duplicate token update attempted");
                throw new DataAccessException("Duplicate token update attempted",
                        "A token with these details already exists.", e);
            }

            logger.error("SQL error while updating token ID: " + token.getToken_id() + ". Error code: " + 
                         e.getErrorCode() + ", SQL state: " + e.getSQLState(), e);
            throw new DataAccessException("SQL error while updating token",
                    "Failed to update token due to a system error. Please try again later.", e);
        } catch (Exception e) {
            logger.error("Unexpected error while updating token ID: " + token.getToken_id(), e);
            throw new DataAccessException("Unexpected error while updating token",
                    "An unexpected error occurred. Please contact support.", e);
        }
    }

    public static boolean deleteToken(int id) {
        if (id <= 0) {
            logger.error("Invalid token ID provided for deletion: " + id);
            throw new IllegalArgumentException("Token ID must be a positive number");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Attempting to delete token with ID: " + id);
        }

        String sql = "DELETE FROM tokens WHERE token_id = ?";

        try (Connection conn = DBConnectionManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                if (logger.isInfoEnabled()) {
                    logger.info("Successfully deleted token with ID: " + id);
                }
                return true;
            } else {
                logger.warn("No token found to delete with ID: " + id);
                return false;
            }
        } catch (DatabaseException e) {
            logger.error("Connection error while deleting token with ID: " + id, e);
            throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);
        } catch (SQLException e) {
            logger.error("SQL error while deleting token with ID: " + id + ". Error code: " + e.getErrorCode() + 
                         ", SQL state: " + e.getSQLState(), e);
            throw new DataAccessException("Database error during token deletion",
                    "Failed to delete the token due to system error. Please try again later.", e);
        } catch (Exception e) {
            logger.error("Unexpected error while deleting token with ID: " + id, e);
            throw new DataAccessException("Unexpected error during token deletion",
                    "An unexpected error occurred. Please contact support.", e);
        }
    }

    public static Token findTokenByValue(String tokenValue) {
        if (tokenValue == null || tokenValue.trim().isEmpty()) {
            logger.error("Attempted to search for token with null or empty value");
            throw new IllegalArgumentException("Token value cannot be null or empty");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Searching for token with value: " + tokenValue);
        }

        String sql = "SELECT * FROM tokens WHERE token = ?";

        try (Connection conn = DBConnectionManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tokenValue);

            if (logger.isTraceEnabled()) {
                logger.trace("Executing SQL query to find token by value");
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Token token = mapResultSetToToken(rs);
                    if (logger.isInfoEnabled()) {
                        logger.info("Found token with ID: " + token.getToken_id());
                    }
                    return token;
                } else {
                    logger.warn("No token found with the specified value");
                    return null;
                }
            }
        } catch (DatabaseException e) {
            logger.error("Connection error while fetching token by value", e);
            throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);
        } catch (SQLException e) {
            logger.error("SQL error while fetching token by value. Error code: " + e.getErrorCode() + 
                         ", SQL state: " + e.getSQLState(), e);
            throw new DataAccessException("Database error while retrieving token by value",
                    "Failed to find the token due to system error. Please try again later.", e);
        } catch (Exception e) {
            logger.error("Unexpected error while fetching token by value", e);
            throw new DataAccessException("Unexpected error while retrieving token by value",
                    "An unexpected error occurred. Please contact support.", e);
        }
    }

    public static List<Token> getTokensByUserId(int userId) {
        if (userId <= 0) {
            logger.error("Invalid user ID provided for token search: " + userId);
            throw new IllegalArgumentException("User ID must be a positive number");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Searching for tokens for user ID: " + userId);
        }

        String sql = "SELECT * FROM tokens WHERE userID = ?";
        List<Token> tokens = new ArrayList<>();

        try (Connection conn = DBConnectionManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            if (logger.isTraceEnabled()) {
                logger.trace("Executing SQL query to find tokens by user ID");
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tokens.add(mapResultSetToToken(rs));
                }
            }

            if (logger.isInfoEnabled()) {
                logger.info("Found " + tokens.size() + " tokens for user ID: " + userId);
            }
            return tokens;
        } catch (DatabaseException e) {
            logger.error("Connection error while fetching tokens for user ID: " + userId, e);
            throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);
        } catch (SQLException e) {
            logger.error("SQL error while fetching tokens for user ID: " + userId + ". Error code: " + 
                         e.getErrorCode() + ", SQL state: " + e.getSQLState(), e);
            throw new DataAccessException("Database error while retrieving tokens by user ID",
                    "Failed to find tokens due to system error. Please try again later.", e);
        } catch (Exception e) {
            logger.error("Unexpected error while fetching tokens for user ID: " + userId, e);
            throw new DataAccessException("Unexpected error while retrieving tokens by user ID",
                    "An unexpected error occurred. Please contact support.", e);
        }
    }

    private static Token mapResultSetToToken(ResultSet rs) throws SQLException {
        Token token = new Token();
        token.setToken_id(rs.getInt("token_id"));
        token.setUserID(rs.getInt("userID"));
        token.setToken(rs.getString("token"));
        token.setCreatedAt(rs.getTimestamp("createdAt"));
        token.setType(rs.getString("type"));
        token.setIpAddress(rs.getString("ipAddress"));
        token.setDevice(rs.getString("device"));
        token.setUsed(rs.getBoolean("isUsed"));
        return token;
    }
}