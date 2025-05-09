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
import com.voteSphere.model.UnverifiedUser;

public class UnverifiedUserDao {

    private static final Logger logger = LogManager.getLogger(UnverifiedUserDao.class);

    public static boolean createUnverifiedUser(UnverifiedUser user) {
        if (user == null) {
            logger.error("Attempt to create null unverified user");
            throw new IllegalArgumentException("UnverifiedUser cannot be null");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Attempting to create unverified user with voter ID: " + user.getVoterId());
        }

        String sql = "INSERT INTO unverified_users (first_name, last_name, voter_id, notification_email, " +
                    "profile_image, phone_number, image_holding_citizenship, voter_card_front, " +
                    "voter_card_back, citizenship_front, citizenship_back, thumb_print, password, " +
                    "dob, gender, permanent_address, temporary_address, role, is_verified, " +
                    "is_email_verified, created_at) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnectionManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (logger.isTraceEnabled()) {
                logger.trace("Setting parameters for unverified user creation: " + user.toString());
            }

            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getVoterId());
            stmt.setString(4, user.getNotificationEmail());
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
                logger.warn("No rows affected when creating unverified user: " + user.getVoterId());
                throw new DataAccessException("No rows affected when creating unverified user",
                        "Failed to create unverified user. Please try again.", null);
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setUnverifiedUserId(generatedKeys.getInt(1));
                    if (logger.isInfoEnabled()) {
                        logger.info("Successfully created unverified user with ID " + user.getUnverifiedUserId());
                    }
                    return true;
                } else {
                    logger.error("No ID obtained after creating unverified user: " + user.getVoterId());
                    throw new DataAccessException("No generated key returned for new unverified user",
                            "User was created but we couldn't retrieve its ID. Please contact support.", null);
                }
            }
        } catch (DatabaseException e) {
            logger.error("Connection error while creating unverified user", e);
            throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);
        } catch (SQLException e) {
            if ("23000".equals(e.getSQLState())) {
                logger.warn("Duplicate unverified user creation attempted: " + user.getVoterId() + 
                          ". Error: " + e.getMessage());
                throw new DataAccessException("Duplicate unverified user creation attempted: " + user.getVoterId(),
                        "A user with this voter ID already exists. Please use a different voter ID.", e);
            }

            logger.error("Database error while creating unverified user: " + user.getVoterId() + 
                       ". Error code: " + e.getErrorCode() + ", SQL state: " + e.getSQLState(), e);

            throw new DataAccessException("Database error while creating unverified user: " + e.getMessage(),
                    "Failed to create unverified user due to system error. Please try again later.", e);
        } catch (Exception e) {
            logger.error("Unexpected error while creating unverified user: " + user.getVoterId(), e);
            throw new DataAccessException("Unexpected error while creating unverified user",
                    "An unexpected error occurred. Please contact support.", e);
        }
    }
    public static UnverifiedUser findUnverifiedUserById(int id) {
        if (logger.isDebugEnabled()) {
            logger.debug("Searching for unverified user with ID: " + id);
        }

        String sql = "SELECT * FROM unverified_users WHERE unverified_user_id = ?";

        try (Connection conn = DBConnectionManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            if (logger.isTraceEnabled()) {
                logger.trace("Executing SQL query to find unverified user by ID: " + id);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    UnverifiedUser user = mapResultSetToUnverifiedUser(rs);
                    if (logger.isInfoEnabled()) {
                        logger.info("Found unverified user with ID: " + user.getUnverifiedUserId());
                    }
                    return user;
                } else {
                    logger.warn("No unverified user found with ID: " + id);
                    return null;
                }
            }
        } catch (DatabaseException e) {
            logger.error("Database connection error while fetching unverified user by ID: " + id, e);
            throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);
        } catch (SQLException e) {
            logger.error("SQL error while fetching unverified user by ID: " + id + ". Error code: " + 
                       e.getErrorCode() + ", SQL state: " + e.getSQLState(), e);
            throw new DataAccessException("Database error while retrieving unverified user",
                    "Failed to retrieve unverified user due to system error. Please try again later.", e);
        } catch (Exception e) {
            logger.error("Unexpected error while fetching unverified user by ID: " + id, e);
            throw new DataAccessException("Unexpected error while retrieving unverified user",
                    "An unexpected error occurred. Please contact support.", e);
        }
    }

    public static boolean updateUnverifiedUser(UnverifiedUser user) {
        if (user == null) {
            logger.error("Attempt to update null unverified user");
            throw new IllegalArgumentException("UnverifiedUser cannot be null");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Attempting to update unverified user with ID: " + user.getUnverifiedUserId());
        }

        String sql = "UPDATE unverified_users SET first_name=?, last_name=?, voter_id=?, " +
                     "notification_email=?, profile_image=?, phone_number=?, " +
                     "image_holding_citizenship=?, voter_card_front=?, voter_card_back=?, " +
                     "citizenship_front=?, citizenship_back=?, thumb_print=?, password=?, " +
                     "dob=?, gender=?, permanent_address=?, temporary_address=?, role=?, " +
                     "is_verified=?, is_email_verified=? WHERE unverified_user_id=?";

        try (Connection conn = DBConnectionManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (logger.isTraceEnabled()) {
                logger.trace("Setting parameters for unverified user update: " + user.toString());
            }

            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getVoterId());
            stmt.setString(4, user.getNotificationEmail());
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
            stmt.setInt(21, user.getUnverifiedUserId());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                logger.warn("No rows affected when updating unverified user ID: " + user.getUnverifiedUserId());
                throw new DataAccessException("No rows affected while updating unverified user.",
                        "Failed to update unverified user. Please try again.", null);
            }

            if (logger.isInfoEnabled()) {
                logger.info("Successfully updated unverified user ID " + user.getUnverifiedUserId());
            }
            return true;
        } catch (DatabaseException e) {
            logger.error("Connection error while updating unverified user ID: " + user.getUnverifiedUserId(), e);
            throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);
        } catch (SQLException e) {
            if ("23000".equals(e.getSQLState())) {
                logger.warn("Duplicate unverified user update attempted for voter ID: " + user.getVoterId());
                throw new DataAccessException("Duplicate voter ID: " + user.getVoterId(),
                        "A user with this voter ID already exists. Please use a different voter ID.", e);
            }

            logger.error("SQL error while updating unverified user ID: " + user.getUnverifiedUserId() + 
                       ". Error code: " + e.getErrorCode() + ", SQL state: " + e.getSQLState(), e);
            throw new DataAccessException("SQL error while updating unverified user",
                    "Failed to update unverified user due to a system error. Please try again later.", e);
        } catch (Exception e) {
            logger.error("Unexpected error while updating unverified user ID: " + user.getUnverifiedUserId(), e);
            throw new DataAccessException("Unexpected error while updating unverified user",
                    "An unexpected error occurred. Please contact support.", e);
        }
    }

    public static boolean deleteUnverifiedUser(int id) {
        if (id <= 0) {
            logger.error("Invalid unverified user ID provided for deletion: " + id);
            throw new IllegalArgumentException("Unverified user ID must be a positive number");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Attempting to delete unverified user with ID: " + id);
        }

        String sql = "DELETE FROM unverified_users WHERE unverified_user_id = ?";

        try (Connection conn = DBConnectionManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                if (logger.isInfoEnabled()) {
                    logger.info("Successfully deleted unverified user with ID: " + id);
                }
                return true;
            } else {
                logger.warn("No unverified user found to delete with ID: " + id);
                return false;
            }
        } catch (DatabaseException e) {
            logger.error("Connection error while deleting unverified user with ID: " + id, e);
            throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);
        } catch (SQLException e) {
            logger.error("SQL error while deleting unverified user with ID: " + id + 
                       ". Error code: " + e.getErrorCode() + ", SQL state: " + e.getSQLState(), e);
            throw new DataAccessException("Database error during unverified user deletion",
                    "Failed to delete the unverified user due to system error. Please try again later.", e);
        } catch (Exception e) {
            logger.error("Unexpected error while deleting unverified user with ID: " + id, e);
            throw new DataAccessException("Unexpected error during unverified user deletion",
                    "An unexpected error occurred. Please contact support.", e);
        }
    }

    public static UnverifiedUser findUnverifiedUserByVoterId(String voterId) {
        if (voterId == null || voterId.trim().isEmpty()) {
            logger.error("Attempted to search for unverified user with null or empty voter ID");
            throw new IllegalArgumentException("Voter ID cannot be null or empty");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Searching for unverified user with voter ID: " + voterId);
        }

        String sql = "SELECT * FROM unverified_users WHERE voter_id = ?";

        try (Connection conn = DBConnectionManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, voterId);

            if (logger.isTraceEnabled()) {
                logger.trace("Executing SQL query to find unverified user by voter ID: " + voterId);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    UnverifiedUser user = mapResultSetToUnverifiedUser(rs);
                    if (logger.isInfoEnabled()) {
                        logger.info("Found unverified user with voter ID: " + user.getVoterId());
                    }
                    return user;
                } else {
                    logger.warn("No unverified user found with voter ID: " + voterId);
                    return null;
                }
            }
        } catch (DatabaseException e) {
            logger.error("Connection error while fetching unverified user by voter ID: " + voterId, e);
            throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);
        } catch (SQLException e) {
            logger.error("SQL error while fetching unverified user by voter ID: " + voterId + 
                       ". Error code: " + e.getErrorCode() + ", SQL state: " + e.getSQLState(), e);
            throw new DataAccessException("Database error while retrieving unverified user by voter ID",
                    "Failed to find the unverified user due to system error. Please try again later.", e);
        } catch (Exception e) {
            logger.error("Unexpected error while fetching unverified user by voter ID: " + voterId, e);
            throw new DataAccessException("Unexpected error while retrieving unverified user by voter ID",
                    "An unexpected error occurred. Please contact support.", e);
        }
    }
    
    
    /**
     * Sets the is_verified flag to true for an unverified user
     * @param unverifiedUserId The ID of the unverified user to verify
     * @return true if the operation was successful, false otherwise
     */
    public static boolean setIsVerifiedTrue(int unverifiedUserId) {
        if (unverifiedUserId <= 0) {
            logger.error("Invalid unverified user ID provided for verification: " + unverifiedUserId);
            throw new IllegalArgumentException("Unverified user ID must be a positive number");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Attempting to verify user with ID: " + unverifiedUserId);
        }

        String sql = "UPDATE unverified_users SET is_verified = true WHERE unverified_user_id = ?";

        try (Connection conn = DBConnectionManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, unverifiedUserId);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                logger.info("Successfully verified user with ID: " + unverifiedUserId);
                return true;
            } else {
                logger.warn("No unverified user found with ID: " + unverifiedUserId);
                return false;
            }
        } catch (DatabaseException e) {
            logger.error("Connection error while verifying user ID: " + unverifiedUserId, e);
            throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);
        } catch (SQLException e) {
            logger.error("SQL error while verifying user ID: " + unverifiedUserId + 
                       ". Error code: " + e.getErrorCode() + ", SQL state: " + e.getSQLState(), e);
            throw new DataAccessException("Database error during user verification",
                    "Failed to verify user due to system error. Please try again later.", e);
        } catch (Exception e) {
            logger.error("Unexpected error while verifying user ID: " + unverifiedUserId, e);
            throw new DataAccessException("Unexpected error during user verification",
                    "An unexpected error occurred. Please contact support.", e);
        }
    }
    
    
    
        /**
         * Promotes an unverified user to a verified user by moving the record to the verified_users table
         * and deleting it from the unverified_users table
         */
        public static boolean promoteUnverifiedUserToVerifiedUser(int unverifiedUserId) {
            if (unverifiedUserId <= 0) {
                logger.error("Invalid unverified user ID provided for promotion: " + unverifiedUserId);
                throw new IllegalArgumentException("Unverified user ID must be a positive number");
            }

            if (logger.isDebugEnabled()) {
                logger.debug("Attempting to promote unverified user with ID: " + unverifiedUserId + " to verified user");
            }

            Connection conn = null;
            try {
                conn = DBConnectionManager.establishConnection();
                conn.setAutoCommit(false); // Start transaction

                // 1. Get the unverified user
                UnverifiedUser user = findUnverifiedUserById(unverifiedUserId);
                if (user == null) {
                    logger.warn("No unverified user found with ID: " + unverifiedUserId + " to promote");
                    return false;
                }

                // 2. Insert into verified_users table
                String insertSql = "INSERT INTO users (first_name, last_name, voter_id, notification_email, " +
                        "profile_image, phone_number, image_holding_citizenship, voter_card_front, " +
                        "voter_card_back, citizenship_front, citizenship_back, thumb_print, password, " +
                        "dob, gender, permanent_address, temporary_address, role, is_verified, " +
                        "is_email_verified, created_at) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.setString(1, user.getFirstName());
                    insertStmt.setString(2, user.getLastName());
                    insertStmt.setString(3, user.getVoterId());
                    insertStmt.setString(4, user.getNotificationEmail());
                    insertStmt.setString(5, user.getProfileImage());
                    insertStmt.setString(6, user.getPhoneNumber());
                    insertStmt.setString(7, user.getImageHoldingCitizenship());
                    insertStmt.setString(8, user.getVoterCardFront());
                    insertStmt.setString(9, user.getVoterCardBack());
                    insertStmt.setString(10, user.getCitizenshipFront());
                    insertStmt.setString(11, user.getCitizenshipBack());
                    insertStmt.setString(12, user.getThumbPrint());
                    insertStmt.setString(13, user.getPassword());
                    insertStmt.setTimestamp(14, user.getDob());
                    insertStmt.setString(15, user.getGender());
                    insertStmt.setString(16, user.getPermanentAddress());
                    insertStmt.setString(17, user.getTemporaryAddress());
                    insertStmt.setString(18, user.getRole());
                    insertStmt.setBoolean(19, true); 
                    insertStmt.setBoolean(20, user.isEmailVerified());
                    insertStmt.setTimestamp(21, user.getCreatedAt());

                    int inserted = insertStmt.executeUpdate();
                    if (inserted == 0) {
                        logger.error("Failed to insert user into verified table: " + unverifiedUserId);
                        conn.rollback();
                        return false;
                    }
                }

                // 3. Delete from unverified_users table
                String deleteSql = "DELETE FROM unverified_users WHERE unverified_user_id = ?";
                try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                    deleteStmt.setInt(1, unverifiedUserId);
                    int deleted = deleteStmt.executeUpdate();
                    if (deleted == 0) {
                        logger.error("Failed to delete user from unverified table: " + unverifiedUserId);
                        conn.rollback();
                        return false;
                    }
                }

                conn.commit();
                logger.info("Successfully promoted unverified user with ID: " + unverifiedUserId + " to verified user");
                return true;
            } catch (DatabaseException e) {
                logger.error("Connection error while promoting unverified user: " + unverifiedUserId, e);
                throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);
            } catch (SQLException e) {
                try {
                    if (conn != null) conn.rollback();
                } catch (SQLException ex) {
                    logger.error("Error during rollback", ex);
                }
                logger.error("SQL error while promoting unverified user: " + unverifiedUserId + 
                           ". Error code: " + e.getErrorCode() + ", SQL state: " + e.getSQLState(), e);
                throw new DataAccessException("Database error during user promotion",
                        "Failed to promote user due to system error. Please try again later.", e);
            } catch (Exception e) {
                try {
                    if (conn != null) conn.rollback();
                } catch (SQLException ex) {
                    logger.error("Error during rollback", ex);
                }
                logger.error("Unexpected error while promoting unverified user: " + unverifiedUserId, e);
                throw new DataAccessException("Unexpected error during user promotion",
                        "An unexpected error occurred. Please contact support.", e);
            } finally {
                try {
                    if (conn != null) {
                        conn.setAutoCommit(true);
                        conn.close();
                    }
                } catch (SQLException e) {
                    logger.error("Error closing connection", e);
                }
            }
        }
        
        
        /**
         * Updates the email verification status for an unverified user.
         * 
         * @param userId The ID of the user to verify
         * @return true if the update was successful, false if no user was found
         * @throws DataAccessException if there's a database error
         */
        public static boolean setIsEmailVerifiedTrue(int userId) throws DataAccessException {
            if (userId <= 0) {
                logger.error("Invalid user ID provided for email verification: {}", userId);
                throw new IllegalArgumentException("User ID must be a positive number");
            }

            final String sql = "UPDATE unverified_users SET is_email_verified = true WHERE unverified_user_id = ?";
            
            try (Connection conn = DBConnectionManager.establishConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                
                stmt.setInt(1, userId);
                
                if (logger.isDebugEnabled()) {
                    logger.debug("Attempting to verify email for user ID: {}", userId);
                }
                
                int rowsAffected = stmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    logger.info("Successfully verified email for user ID: {}", userId);
                    return true;
                } else {
                    logger.warn("No user found or already verified for user ID: {}", userId);
                    return false;
                }
                
            } catch (SQLException e) {
                String errorMsg = String.format("SQL error verifying email for user ID %d. Error code: %d, SQL state: %s", 
                                              userId, e.getErrorCode(), e.getSQLState());
                logger.error(errorMsg, e);
                throw new DataAccessException("Database error during email verification", 
                                             "Failed to verify email due to system error", e);
            } catch (Exception e) {
                logger.error("Unexpected error verifying email for user ID: {}", userId, e);
                throw new DataAccessException("Unexpected error during email verification", 
                                            "An unexpected error occurred", e);
            }
        }

        

        /**
         * Gets an unverified user by voter ID
         */
        public static UnverifiedUser getUnverifiedUserByVoterId(String voterId) {
            if (voterId == null || voterId.trim().isEmpty()) {
                logger.error("Attempted to search for unverified user with null or empty voter ID");
                throw new IllegalArgumentException("Voter ID cannot be null or empty");
            }

            if (logger.isDebugEnabled()) {
                logger.debug("Searching for unverified user with voter ID: " + voterId);
            }

            String sql = "SELECT * FROM unverified_users WHERE voter_id = ?";

            try (Connection conn = DBConnectionManager.establishConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, voterId);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        UnverifiedUser user = mapResultSetToUnverifiedUser(rs);
                        if (logger.isInfoEnabled()) {
                            logger.info("Found unverified user with voter ID: " + voterId);
                        }
                        return user;
                    } else {
                        logger.warn("No unverified user found with voter ID: " + voterId);
                        return null;
                    }
                }
            } catch (DatabaseException e) {
                logger.error("Connection error while fetching unverified user by voter ID: " + voterId, e);
                throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);
            } catch (SQLException e) {
                logger.error("SQL error while fetching unverified user by voter ID: " + voterId + 
                           ". Error code: " + e.getErrorCode() + ", SQL state: " + e.getSQLState(), e);
                throw new DataAccessException("Database error while retrieving unverified user by voter ID",
                        "Failed to find user due to system error. Please try again later.", e);
            } catch (Exception e) {
                logger.error("Unexpected error while fetching unverified user by voter ID: " + voterId, e);
                throw new DataAccessException("Unexpected error while retrieving user by voter ID",
                        "An unexpected error occurred. Please contact support.", e);
            }
        }

        /**
         * Gets the email of an unverified user by their ID
         */
        public static String getEmailOfUnverifiedUser(int userId) {
            if (userId <= 0) {
                logger.error("Invalid user ID provided for email lookup: " + userId);
                throw new IllegalArgumentException("User ID must be a positive number");
            }

            if (logger.isDebugEnabled()) {
                logger.debug("Getting email for unverified user with ID: " + userId);
            }

            String sql = "SELECT notification_email FROM unverified_users WHERE unverified_user_id = ?";

            try (Connection conn = DBConnectionManager.establishConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, userId);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String email = rs.getString("notification_email");
                        if (logger.isInfoEnabled()) {
                            logger.info("Found email for unverified user ID: " + userId);
                        }
                        return email;
                    } else {
                        logger.warn("No unverified user found with ID: " + userId);
                        return null;
                    }
                }
            } catch (DatabaseException e) {
                logger.error("Connection error while fetching email for unverified user: " + userId, e);
                throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);
            } catch (SQLException e) {
                logger.error("SQL error while fetching email for unverified user: " + userId + 
                           ". Error code: " + e.getErrorCode() + ", SQL state: " + e.getSQLState(), e);
                throw new DataAccessException("Database error while retrieving user email",
                        "Failed to retrieve email due to system error. Please try again later.", e);
            } catch (Exception e) {
                logger.error("Unexpected error while fetching email for unverified user: " + userId, e);
                throw new DataAccessException("Unexpected error while retrieving email",
                        "An unexpected error occurred. Please contact support.", e);
            }
        }

        /**
         * Gets an unverified user by their email
         */
        public static UnverifiedUser getUnverifiedUserByEmail(String email) {
            if (email == null || email.trim().isEmpty()) {
                logger.error("Attempted to search for unverified user with null or empty email");
                throw new IllegalArgumentException("Email cannot be null or empty");
            }

            if (logger.isDebugEnabled()) {
                logger.debug("Searching for unverified user with email: " + email);
            }

            String sql = "SELECT * FROM unverified_users WHERE notification_email = ?";

            try (Connection conn = DBConnectionManager.establishConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, email);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        UnverifiedUser user = mapResultSetToUnverifiedUser(rs);
                        if (logger.isInfoEnabled()) {
                            logger.info("Found unverified user with email: " + email);
                        }
                        return user;
                    } else {
                        logger.warn("No unverified user found with email: " + email);
                        return null;
                    }
                }
            } catch (DatabaseException e) {
                logger.error("Connection error while fetching unverified user by email: " + email, e);
                throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);
            } catch (SQLException e) {
                logger.error("SQL error while fetching unverified user by email: " + email + 
                           ". Error code: " + e.getErrorCode() + ", SQL state: " + e.getSQLState(), e);
                throw new DataAccessException("Database error while retrieving unverified user by email",
                        "Failed to find user due to system error. Please try again later.", e);
            } catch (Exception e) {
                logger.error("Unexpected error while fetching unverified user by email: " + email, e);
                throw new DataAccessException("Unexpected error while retrieving user by email",
                        "An unexpected error occurred. Please contact support.", e);
            }
        }

        /**
         * Gets all unverified users
         */
        public static List<UnverifiedUser> getAllUnverifiedUsers() {
            if (logger.isDebugEnabled()) {
                logger.debug("Attempting to retrieve all unverified users");
            }

            String sql = "SELECT * FROM unverified_users";
            List<UnverifiedUser> users = new ArrayList<>();

            try (Connection conn = DBConnectionManager.establishConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    UnverifiedUser user = mapResultSetToUnverifiedUser(rs);
                    users.add(user);
                }

                if (logger.isInfoEnabled()) {
                    logger.info("Successfully retrieved " + users.size() + " unverified users from the database");
                }

                return users;
            } catch (DatabaseException e) {
                logger.error("Connection error while retrieving all unverified users", e);
                throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);
            } catch (SQLException e) {
                logger.error("SQL error while retrieving all unverified users. Error code: " + e.getErrorCode() + 
                           ", SQL state: " + e.getSQLState(), e);
                throw new DataAccessException("Database error during retrieving all unverified users",
                        "Failed to load users due to a system error. Please try again later.", e);
            } catch (Exception e) {
                logger.error("Unexpected error while retrieving all unverified users", e);
                throw new DataAccessException("Unexpected error during retrieving all users",
                        "An unexpected error occurred. Please contact support.", e);
            }
        }    
    
    
    
    private static UnverifiedUser mapResultSetToUnverifiedUser(ResultSet rs) throws SQLException {
        UnverifiedUser user = new UnverifiedUser();
        user.setUnverifiedUserId(rs.getInt("unverified_user_id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setVoterId(rs.getString("voter_id"));
        user.setNotificationEmail(rs.getString("notification_email"));
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