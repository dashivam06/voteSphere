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
import com.voteSphere.model.User;

public class UserDao {

    private static final Logger logger = LogManager.getLogger(UserDao.class);

    public static boolean insertUser(User user) {
        if (user == null) {
            logger.error("Attempt to create null user");
            throw new IllegalArgumentException("User cannot be null");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Attempting to create user with email: " + user.getEmail());
        }

        String sql = "INSERT INTO users (first_name, last_name, voter_id, notification_email, profile_image, " +
                     "phone_number, image_holding_citizenship, voter_card_front, voter_card_back, " +
                     "citizenship_front, citizenship_back, thumb_print, password, dob, gender, " +
                     "permanent_address, temporary_address, role, is_verified, is_email_verified, created_at) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnectionManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (logger.isTraceEnabled()) {
                logger.trace("Setting parameters for user creation: " + user.toString());
            }

            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getVoterId());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getProfileImage());
            stmt.setString(6, user.getPhoneNumber());
            stmt.setString(7, user.getImageHoldingCitizenship());
            stmt.setString(8, user.getVoterCardFront());
            stmt.setString(9, user.getVoterCardBack());
            stmt.setString(10, user.getCitizenshipFront());
            stmt.setString(11, user.getCitizenshipBack());
            stmt.setString(12, user.getThumbPrint());
            stmt.setString(13, user.getPassword());
            stmt.setTimestamp(14, user.getDob());
            stmt.setString(15, user.getGender());
            stmt.setString(16, user.getPermanentAddress());
            stmt.setString(17, user.getTemporaryAddress());
            stmt.setString(18, user.getRole());
            stmt.setBoolean(19, user.getIsVerified());
            stmt.setBoolean(20, user.isEmailVerified());
            stmt.setTimestamp(21, user.getCreatedAt());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                logger.warn("No rows affected when creating user: " + user.getEmail());
                throw new DataAccessException("No rows affected when creating user",
                        "Failed to create user. Please try again.", null);
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setUserId(generatedKeys.getInt(1));
                    if (logger.isInfoEnabled()) {
                        logger.info("Successfully created user with ID " + user.getUserId());
                    }
                    return true;
                } else {
                    logger.error("No ID obtained after creating user: " + user.getEmail());
                    throw new DataAccessException("No generated key returned for new user",
                            "User was created but we couldn't retrieve its ID. Please contact support.", null);
                }
            }
        } catch (DatabaseException e) {
            logger.error("Connection error while creating user", e);
            throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);
        } catch (SQLException e) {
            if ("23000".equals(e.getSQLState())) {
                logger.warn("Duplicate user creation attempted: " + user.getEmail() + ". Error: " + e.getMessage());
                throw new DataAccessException("Duplicate user creation attempted: " + user.getEmail(),
                        "A user with this email already exists. Please use a different email.", e);
            }

            logger.error("Database error while creating user: " + user.getEmail() + 
                       ". Error code: " + e.getErrorCode() + ", SQL state: " + e.getSQLState(), e);
            throw new DataAccessException("Database error while creating user: " + e.getMessage(),
                    "Failed to create user due to system error. Please try again later.", e);
        } catch (Exception e) {
            logger.error("Unexpected error while creating user: " + user.getEmail(), e);
            throw new DataAccessException("Unexpected error while creating user",
                    "An unexpected error occurred. Please contact support.", e);
        }
    }

    public static List<User> getAllUsers() {
        if (logger.isDebugEnabled()) {
            logger.debug("Attempting to retrieve all users");
        }

        String sql = "SELECT * FROM users WHERE user_id != 1";
        List<User> users = new ArrayList<>();

        try (Connection conn = DBConnectionManager.establishConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = mapResultSetToUser(rs);
                users.add(user);
            }

            if (logger.isInfoEnabled()) {
                logger.info("Successfully retrieved " + users.size() + " users from the database");
            }

            return users;
        } catch (DatabaseException e) {
            logger.error("Connection error while retrieving all users", e);
            throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);
        } catch (SQLException e) {
            logger.error("SQL error while retrieving all users. Error code: " + e.getErrorCode() + 
                       ", SQL state: " + e.getSQLState(), e);
            throw new DataAccessException("Database error during retrieving all users",
                    "Failed to load users due to a system error. Please try again later.", e);
        } catch (Exception e) {
            logger.error("Unexpected error while retrieving all users", e);
            throw new DataAccessException("Unexpected error during retrieving all users",
                    "An unexpected error occurred. Please contact support.", e);
        }
    }

    public static User getUserById(int id) {
        if (logger.isDebugEnabled()) {
            logger.debug("Searching for user with ID: " + id);
        }

        String sql = "SELECT * FROM users WHERE user_id = ?";

        try (Connection conn = DBConnectionManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            if (logger.isTraceEnabled()) {
                logger.trace("Executing SQL query to find user by ID: " + id);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = mapResultSetToUser(rs);
                    if (logger.isInfoEnabled()) {
                        logger.info("Found user with ID: " + user.getUserId());
                    }
                    return user;
                } else {
                    logger.warn("No user found with ID: " + id);
                    return null;
                }
            }
        } catch (DatabaseException e) {
            logger.error("Database connection error while fetching user by ID: " + id, e);
            throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);
        } catch (SQLException e) {
            logger.error("SQL error while fetching user by ID: " + id + ". Error code: " + 
                       e.getErrorCode() + ", SQL state: " + e.getSQLState(), e);
            throw new DataAccessException("Database error while retrieving user",
                    "Failed to retrieve user due to system error. Please try again later.", e);
        } catch (Exception e) {
            logger.error("Unexpected error while fetching user by ID: " + id, e);
            throw new DataAccessException("Unexpected error while retrieving user",
                    "An unexpected error occurred. Please contact support.", e);
        }
    }
    
    public static Integer getVoterIdWithUserId(int userId) {
        if (logger.isDebugEnabled()) {
            logger.debug("Searching for voter ID with userId: " + userId);
        }

        String sql = "SELECT voter_id FROM voters WHERE user_id = ?";

        try (Connection conn = DBConnectionManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            if (logger.isTraceEnabled()) {
                logger.trace("Executing SQL query to find voter ID by userId: " + userId);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int voterId = rs.getInt("voter_id");
                    if (logger.isInfoEnabled()) {
                        logger.info("Found voter ID: " + voterId + " for userId: " + userId);
                    }
                    return voterId;
                } else {
                    logger.warn("No voter ID found for userId: " + userId);
                    return null;
                }
            }

        } catch (DatabaseException e) {
            logger.error("Database connection error while fetching voter ID for userId: " + userId, e);
            throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

        } catch (SQLException e) {
            logger.error("SQL error while fetching voter ID for userId: " + userId +
                         ". Error code: " + e.getErrorCode() + ", SQL state: " + e.getSQLState(), e);
            throw new DataAccessException("Database error while retrieving voter ID",
                    "Failed to retrieve voter ID due to system error. Please try again later.", e);

        } catch (Exception e) {
            logger.error("Unexpected error while fetching voter ID for userId: " + userId, e);
            throw new DataAccessException("Unexpected error while retrieving voter ID",
                    "An unexpected error occurred. Please contact support.", e);
        }
    }


    public static boolean updateUser(User user) {
        if (user == null) {
            logger.error("Attempt to update null user");
            throw new IllegalArgumentException("User cannot be null");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Attempting to update user with ID: " + user.getUserId());
        }

        String sql = "UPDATE users SET first_name=?, last_name=?, voter_id=?, notification_email=?, " +
                     "profile_image=?, phone_number=?, image_holding_citizenship=?, " +
                     "voter_card_front=?, voter_card_back=?, citizenship_front=?, " +
                     "citizenship_back=?, thumb_print=?, password=?, dob=?, gender=?, " +
                     "permanent_address=?, temporary_address=?, role=?, is_verified=?, " +
                     "is_email_verified=? WHERE user_id=?";

        try (Connection conn = DBConnectionManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (logger.isTraceEnabled()) {
                logger.trace("Setting parameters for user update: " + user.toString());
            }

            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getVoterId());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getProfileImage());
            stmt.setString(6, user.getPhoneNumber());
            stmt.setString(7, user.getImageHoldingCitizenship());
            stmt.setString(8, user.getVoterCardFront());
            stmt.setString(9, user.getVoterCardBack());
            stmt.setString(10, user.getCitizenshipFront());
            stmt.setString(11, user.getCitizenshipBack());
            stmt.setString(12, user.getThumbPrint());
            stmt.setString(13, user.getPassword());
            stmt.setTimestamp(14, user.getDob());
            stmt.setString(15, user.getGender());
            stmt.setString(16, user.getPermanentAddress());
            stmt.setString(17, user.getTemporaryAddress());
            stmt.setString(18, user.getRole());
            stmt.setBoolean(19, user.getIsVerified());
            stmt.setBoolean(20, user.isEmailVerified());
            stmt.setInt(21, user.getUserId());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                logger.warn("No rows affected when updating user ID: " + user.getUserId());
                throw new DataAccessException("No rows affected while updating user.",
                        "Failed to update user. Please try again.", null);
            }

            if (logger.isInfoEnabled()) {
                logger.info("Successfully updated user ID " + user.getUserId());
            }
            return true;
        } catch (DatabaseException e) {
            logger.error("Connection error while updating user ID: " + user.getUserId(), e);
            throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);
        } catch (SQLException e) {
            if ("23000".equals(e.getSQLState())) {
                logger.warn("Duplicate user update attempted for email: " + user.getEmail());
                throw new DataAccessException("Duplicate email: " + user.getEmail(),
                        "A user with this email already exists. Please use a different email.", e);
            }

            logger.error("SQL error while updating user ID: " + user.getUserId() + 
                       ". Error code: " + e.getErrorCode() + ", SQL state: " + e.getSQLState(), e);
            throw new DataAccessException("SQL error while updating user",
                    "Failed to update user due to a system error. Please try again later.", e);
        } catch (Exception e) {
            logger.error("Unexpected error while updating user ID: " + user.getUserId(), e);
            throw new DataAccessException("Unexpected error while updating user",
                    "An unexpected error occurred. Please contact support.", e);
        }
    }

    public static boolean deleteUser(int id) {
        if (id <= 0) {
            logger.error("Invalid user ID provided for deletion: " + id);
            throw new IllegalArgumentException("User ID must be a positive number");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Attempting to delete user with ID: " + id);
        }

        String sql = "DELETE FROM users WHERE user_id = ?";

        try (Connection conn = DBConnectionManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                if (logger.isInfoEnabled()) {
                    logger.info("Successfully deleted user with ID: " + id);
                }
                return true;
            } else {
                logger.warn("No user found to delete with ID: " + id);
                return false;
            }
        } catch (DatabaseException e) {
            logger.error("Connection error while deleting user with ID: " + id, e);
            throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);
        } catch (SQLException e) {
            logger.error("SQL error while deleting user with ID: " + id + 
                       ". Error code: " + e.getErrorCode() + ", SQL state: " + e.getSQLState(), e);
            throw new DataAccessException("Database error during user deletion",
                    "Failed to delete the user due to system error. Please try again later.", e);
        } catch (Exception e) {
            logger.error("Unexpected error while deleting user with ID: " + id, e);
            throw new DataAccessException("Unexpected error during user deletion",
                    "An unexpected error occurred. Please contact support.", e);
        }
    }

    public static User getUserByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            logger.error("Attempted to search for user with null or empty email");
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Searching for user with email: " + email);
        }

        String sql = "SELECT * FROM users WHERE notification_email = ?";

        try (Connection conn = DBConnectionManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            if (logger.isTraceEnabled()) {
                logger.trace("Executing SQL query to find user by email: " + email);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = mapResultSetToUser(rs);
                    if (logger.isInfoEnabled()) {
                        logger.info("Found user with email: " + email);
                    }
                    return user;
                } else {
                    logger.warn("No user found with email: " + email);
                    return null;
                }
            }
        } catch (DatabaseException e) {
            logger.error("Connection error while fetching user by email: " + email, e);
            throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);
        } catch (SQLException e) {
            logger.error("SQL error while fetching user by email: " + email + 
                       ". Error code: " + e.getErrorCode() + ", SQL state: " + e.getSQLState(), e);
            throw new DataAccessException("Database error while retrieving user by email",
                    "Failed to find user due to system error. Please try again later.", e);
        } catch (Exception e) {
            logger.error("Unexpected error while fetching user by email: " + email, e);
            throw new DataAccessException("Unexpected error while retrieving user by email",
                    "An unexpected error occurred. Please contact support.", e);
        }
    }

    public static User getUserByVoterId(String voterId) {
        if (voterId == null || voterId.trim().isEmpty()) {
            logger.error("Attempted to search for user with null or empty voter ID");
            throw new IllegalArgumentException("Voter ID cannot be null or empty");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Searching for user with voter ID: " + voterId);
        }

        String sql = "SELECT * FROM users WHERE voter_id = ?";

        try (Connection conn = DBConnectionManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, voterId);

            if (logger.isTraceEnabled()) {
                logger.trace("Executing SQL query to find user by voter ID: " + voterId);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = mapResultSetToUser(rs);
                    if (logger.isInfoEnabled()) {
                        logger.info("Found user with voter ID: " + voterId);
                    }
                    return user;
                } else {
                    logger.warn("No user found with voter ID: " + voterId);
                    return null;
                }
            }
        } catch (DatabaseException e) {
            logger.error("Connection error while fetching user by voter ID: " + voterId, e);
            throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);
        } catch (SQLException e) {
            logger.error("SQL error while fetching user by voter ID: " + voterId + 
                       ". Error code: " + e.getErrorCode() + ", SQL state: " + e.getSQLState(), e);
            throw new DataAccessException("Database error while retrieving user by voter ID",
                    "Failed to find user due to system error. Please try again later.", e);
        } catch (Exception e) {
            logger.error("Unexpected error while fetching user by voter ID: " + voterId, e);
            throw new DataAccessException("Unexpected error while retrieving user by voter ID",
                    "An unexpected error occurred. Please contact support.", e);
        }
    }

    public static boolean userExistsByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            logger.error("Attempted to check existence with null or empty email");
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Checking if user exists with email: " + email);
        }

        String sql = "SELECT * FROM users WHERE notification_email = ?";

        try (Connection conn = DBConnectionManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                boolean exists = rs.next();
                if (logger.isInfoEnabled()) {
                    logger.info("User existence check for email " + email + ": " + exists);
                }
                return exists;
            }
        } catch (DatabaseException e) {
            logger.error("Connection error while checking user existence by email: " + email, e);
            throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);
        } catch (SQLException e) {
            logger.error("SQL error while checking user existence by email: " + email + 
                       ". Error code: " + e.getErrorCode() + ", SQL state: " + e.getSQLState(), e);
            throw new DataAccessException("Database error while checking user existence",
                    "Failed to check user existence due to system error. Please try again later.", e);
        } catch (Exception e) {
            logger.error("Unexpected error while checking user existence by email: " + email, e);
            throw new DataAccessException("Unexpected error while checking user existence",
                    "An unexpected error occurred. Please contact support.", e);
        }
    }

    public static boolean setUserVerified(int userId, boolean isVerified) {
        if (userId <= 0) {
            logger.error("Invalid user ID provided for verification update: " + userId);
            throw new IllegalArgumentException("User ID must be a positive number");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Setting verification status for user ID: " + userId + " to " + isVerified);
        }

        String sql = "UPDATE users SET is_verified = ? WHERE user_id = ?";

        try (Connection conn = DBConnectionManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, isVerified);
            stmt.setInt(2, userId);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                logger.warn("No rows affected when updating verification status for user ID: " + userId);
                throw new DataAccessException("No rows affected while updating verification status.",
                        "Failed to update user verification status. Please try again.", null);
            }

            if (logger.isInfoEnabled()) {
                logger.info("Successfully updated verification status for user ID " + userId);
            }
            return true;
        } catch (DatabaseException e) {
            logger.error("Connection error while updating verification status for user ID: " + userId, e);
            throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);
        } catch (SQLException e) {
            logger.error("SQL error while updating verification status for user ID: " + userId + 
                       ". Error code: " + e.getErrorCode() + ", SQL state: " + e.getSQLState(), e);
            throw new DataAccessException("SQL error while updating verification status",
                    "Failed to update verification status due to a system error. Please try again later.", e);
        } catch (Exception e) {
            logger.error("Unexpected error while updating verification status for user ID: " + userId, e);
            throw new DataAccessException("Unexpected error while updating verification status",
                    "An unexpected error occurred. Please contact support.", e);
        }
    }

    // Additional helper methods

    public static boolean setEmailVerified(int userId, boolean isVerified) {
        if (userId <= 0) {
            logger.error("Invalid user ID provided for email verification update: " + userId);
            throw new IllegalArgumentException("User ID must be a positive number");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Setting email verification status for user ID: " + userId + " to " + isVerified);
        }

        String sql = "UPDATE users SET is_email_verified = ? WHERE user_id = ?";

        try (Connection conn = DBConnectionManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, isVerified);
            stmt.setInt(2, userId);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                logger.warn("No rows affected when updating email verification status for user ID: " + userId);
                throw new DataAccessException("No rows affected while updating email verification status.",
                        "Failed to update email verification status. Please try again.", null);
            }

            if (logger.isInfoEnabled()) {
                logger.info("Successfully updated email verification status for user ID " + userId);
            }
            return true;
        } catch (DatabaseException e) {
            logger.error("Connection error while updating email verification status for user ID: " + userId, e);
            throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);
        } catch (SQLException e) {
            logger.error("SQL error while updating email verification status for user ID: " + userId + 
                       ". Error code: " + e.getErrorCode() + ", SQL state: " + e.getSQLState(), e);
            throw new DataAccessException("SQL error while updating email verification status",
                    "Failed to update email verification status due to a system error. Please try again later.", e);
        } catch (Exception e) {
            logger.error("Unexpected error while updating email verification status for user ID: " + userId, e);
            throw new DataAccessException("Unexpected error while updating email verification status",
                    "An unexpected error occurred. Please contact support.", e);
        }
    }

    public static boolean updatePassword(int userId, String newPassword) {
        if (userId <= 0) {
            logger.error("Invalid user ID provided for password update: " + userId);
            throw new IllegalArgumentException("User ID must be a positive number");
        }
        if (newPassword == null || newPassword.trim().isEmpty()) {
            logger.error("Attempted to update with null or empty password");
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Updating password for user ID: " + userId);
        }

        String sql = "UPDATE users SET password = ? WHERE user_id = ?";

        try (Connection conn = DBConnectionManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newPassword);
            stmt.setInt(2, userId);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                logger.warn("No rows affected when updating password for user ID: " + userId);
                throw new DataAccessException("No rows affected while updating password.",
                        "Failed to update password. Please try again.", null);
            }

            if (logger.isInfoEnabled()) {
                logger.info("Successfully updated password for user ID " + userId);
            }
            return true;
        } catch (DatabaseException e) {
            logger.error("Connection error while updating password for user ID: " + userId, e);
            throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);
        } catch (SQLException e) {
            logger.error("SQL error while updating password for user ID: " + userId + 
                       ". Error code: " + e.getErrorCode() + ", SQL state: " + e.getSQLState(), e);
            throw new DataAccessException("SQL error while updating password",
                    "Failed to update password due to a system error. Please try again later.", e);
        } catch (Exception e) {
            logger.error("Unexpected error while updating password for user ID: " + userId, e);
            throw new DataAccessException("Unexpected error while updating password",
                    "An unexpected error occurred. Please contact support.", e);
        }
    }

    private static User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setVoterId(rs.getString("voter_id"));
        user.setEmail(rs.getString("notification_email"));
        user.setProfileImage(rs.getString("profile_image"));
        user.setPhoneNumber(rs.getString("phone_number"));
        user.setImageHoldingCitizenship(rs.getString("image_holding_citizenship"));
        user.setVoterCardFront(rs.getString("voter_card_front"));
        user.setVoterCardBack(rs.getString("voter_card_back"));
        user.setCitizenshipFront(rs.getString("citizenship_front"));
        user.setCitizenshipBack(rs.getString("citizenship_back"));
        user.setThumbPrint(rs.getString("thumb_print"));
        user.setPassword(rs.getString("password"));
        user.setDob(rs.getTimestamp("dob"));
        user.setGender(rs.getString("gender"));
        user.setPermanentAddress(rs.getString("permanent_address"));
        user.setTemporaryAddress(rs.getString("temporary_address"));
        user.setRole(rs.getString("role"));
        user.setIsVerified(rs.getBoolean("is_verified"));
        user.setEmailVerified(rs.getBoolean("is_email_verified"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        return user;
    }
}