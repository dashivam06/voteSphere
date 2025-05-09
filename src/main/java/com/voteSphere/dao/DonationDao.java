package com.voteSphere.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.voteSphere.config.DBConnectionManager;
import com.voteSphere.exception.DataAccessException;
import com.voteSphere.model.Donation;

public class DonationDao {

    private static final Logger logger = LogManager.getLogger(DonationDao.class);

    // Method to create a donation
    public static boolean createDonation(Donation donation) {

        if (donation == null) {
            logger.error("Attempt to create null donation");
            throw new IllegalArgumentException("Donation cannot be null");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Attempting to create donation for user: " + donation.getUserId() + ", amount: " + donation.getAmount());
        }

        String sql = "INSERT INTO donations (user_id, amount, product_code, transaction_uuid, status, donation_time) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnectionManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, donation.getUserId());
            stmt.setDouble(2, donation.getAmount());
            stmt.setString(3, donation.getProductCode());
            stmt.setString(4, donation.getTransactionUuid());
            stmt.setString(5, donation.getStatus());
            stmt.setTimestamp(6, donation.getDonationTime());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                logger.warn("No rows affected when creating donation for user ID: " + donation.getUserId());
                throw new DataAccessException("No rows affected when creating donation",
                        "Failed to create donation. Please try again.", null);
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    donation.setDonationId(generatedKeys.getInt(1));
                    if (logger.isInfoEnabled()) {
                        logger.info("Successfully created donation with ID: " + donation.getDonationId());
                    }
                    return true;
                } else {
                    logger.error("No ID obtained after creating donation");
                    throw new DataAccessException("No generated key returned for new donation",
                            "Donation was created but we couldn't retrieve its ID. Please contact support.", null);
                }
            }
        } catch (SQLException e) {
            logger.error("SQL error while creating donation: " + donation.getUserId(), e);
            throw new DataAccessException("Database error while creating donation", "Please try again later.", e);
        } catch (Exception e) {
            logger.error("Unexpected error while creating donation", e);
            throw new DataAccessException("Unexpected error while creating donation", "Please contact support.", e);
        }
    }

    // Method to get all donations
    public static List<Donation> getAllDonations() {

        if (logger.isDebugEnabled()) {
            logger.debug("Attempting to retrieve all donations");
        }

        String sql = "SELECT * FROM donations";
        List<Donation> donations = new ArrayList<>();

        try (Connection conn = DBConnectionManager.establishConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Donation donation = new Donation(
                        rs.getInt("donation_id"),
                        rs.getInt("user_id"),
                        rs.getDouble("amount"),
                        rs.getString("product_code"),
                        rs.getString("transaction_uuid"),
                        rs.getString("status"),
                        rs.getTimestamp("donation_time")
                );
                donations.add(donation);
            }

            if (logger.isInfoEnabled()) {
                logger.info("Successfully retrieved " + donations.size() + " donations from the database");
            }

            return donations;

        } catch (SQLException e) {
            logger.error("SQL error while retrieving all donations", e);
            throw new DataAccessException("Database error while retrieving donations", "Please try again later.", e);
        } catch (Exception e) {
            logger.error("Unexpected error while retrieving all donations", e);
            throw new DataAccessException("Unexpected error while retrieving donations", "Please contact support.", e);
        }
    }

    // Method to find a donation by ID
    public static Donation findDonationById(int id) {

        if (logger.isDebugEnabled()) {
            logger.debug("Searching for donation with ID: " + id);
        }

        String sql = "SELECT * FROM donations WHERE donation_id = ?";

        try (Connection conn = DBConnectionManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Donation(
                            rs.getInt("donation_id"),
                            rs.getInt("user_id"),
                            rs.getDouble("amount"),
                            rs.getString("product_code"),
                            rs.getString("transaction_uuid"),
                            rs.getString("status"),
                            rs.getTimestamp("donation_time")
                    );
                } else {
                    logger.warn("No donation found with ID: " + id);
                    return null;
                }
            }
        } catch (SQLException e) {
            logger.error("SQL error while retrieving donation by ID", e);
            throw new DataAccessException("Database error while retrieving donation", "Please try again later.", e);
        } catch (Exception e) {
            logger.error("Unexpected error while retrieving donation", e);
            throw new DataAccessException("Unexpected error while retrieving donation", "Please contact support.", e);
        }
    }

    // Method to update a donation
    public static boolean updateDonation(Donation donation) {

        if (donation == null) {
            logger.error("Attempt to update null donation");
            throw new IllegalArgumentException("Donation cannot be null");
        }

        String sql = "UPDATE donations SET user_id = ?, amount = ?, product_code = ?, transaction_uuid = ?, status = ?, donation_time = ? WHERE donation_id = ?";

        try (Connection conn = DBConnectionManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, donation.getUserId());
            stmt.setDouble(2, donation.getAmount());
            stmt.setString(3, donation.getProductCode());
            stmt.setString(4, donation.getTransactionUuid());
            stmt.setString(5, donation.getStatus());
            stmt.setTimestamp(6, donation.getDonationTime());
            stmt.setInt(7, donation.getDonationId());

            int affectedRows = stmt.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            logger.error("SQL error while updating donation", e);
            throw new DataAccessException("Database error while updating donation", "Please try again later.", e);
        } catch (Exception e) {
            logger.error("Unexpected error while updating donation", e);
            throw new DataAccessException("Unexpected error while updating donation", "Please contact support.", e);
        }
    }

    // Method to delete a donation
    public static boolean deleteDonation(int id) {

        if (id <= 0) {
            logger.error("Invalid donation ID provided for deletion: " + id);
            throw new IllegalArgumentException("Donation ID must be a positive number");
        }

        String sql = "DELETE FROM donations WHERE donation_id = ?";

        try (Connection conn = DBConnectionManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            logger.error("SQL error while deleting donation", e);
            throw new DataAccessException("Database error while deleting donation", "Please try again later.", e);
        } catch (Exception e) {
            logger.error("Unexpected error while deleting donation", e);
            throw new DataAccessException("Unexpected error while deleting donation", "Please contact support.", e);
        }
    }
    
    // Method to get donation status by transaction UUID
    public static String getDonationStatus(String transactionUuid) {
        if (transactionUuid == null || transactionUuid.isEmpty()) {
            logger.error("Transaction UUID cannot be null or empty");
            throw new IllegalArgumentException("Transaction UUID cannot be null or empty");
        }

        String sql = "SELECT status FROM donations WHERE transaction_uuid = ?";
        try (Connection conn = DBConnectionManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, transactionUuid);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("status");
                } else {
                    logger.warn("No donation found with transaction UUID: " + transactionUuid);
                    return null;
                }
            }
        } catch (SQLException e) {
            logger.error("SQL error while retrieving donation status for transaction UUID: " + transactionUuid, e);
            throw new DataAccessException("Database error while retrieving donation status", "Please try again later.", e);
        } catch (Exception e) {
            logger.error("Unexpected error while retrieving donation status", e);
            throw new DataAccessException("Unexpected error while retrieving donation status", "Please contact support.", e);
        }
    }

    // Method to find donations between specific timestamps
    public static List<Donation> findDonationsBetween(Timestamp start, Timestamp end) {
        if (start == null || end == null) {
            logger.error("Start and end timestamps cannot be null");
            throw new IllegalArgumentException("Start and end timestamps cannot be null");
        }

        String sql = "SELECT * FROM donations WHERE donation_time BETWEEN ? AND ?";
        List<Donation> donations = new ArrayList<>();

        try (Connection conn = DBConnectionManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, start);
            stmt.setTimestamp(2, end);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Donation donation = new Donation(
                            rs.getInt("donation_id"),
                            rs.getInt("user_id"),
                            rs.getDouble("amount"),
                            rs.getString("product_code"),
                            rs.getString("transaction_uuid"),
                            rs.getString("status"),
                            rs.getTimestamp("donation_time")
                    );
                    donations.add(donation);
                }
            }

            return donations;
        } catch (SQLException e) {
            logger.error("SQL error while retrieving donations between dates", e);
            throw new DataAccessException("Database error while retrieving donations", "Please try again later.", e);
        } catch (Exception e) {
            logger.error("Unexpected error while retrieving donations", e);
            throw new DataAccessException("Unexpected error while retrieving donations", "Please contact support.", e);
        }
    }

    // Method to get donations by user ID
    public static List<Donation> getDonationByUserId(int userId) {
        if (userId <= 0) {
            logger.error("Invalid user ID provided for donation search: " + userId);
            throw new IllegalArgumentException("User ID must be a positive number");
        }

        String sql = "SELECT * FROM donations WHERE user_id = ?";
        List<Donation> donations = new ArrayList<>();

        try (Connection conn = DBConnectionManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Donation donation = new Donation(
                            rs.getInt("donation_id"),
                            rs.getInt("user_id"),
                            rs.getDouble("amount"),
                            rs.getString("product_code"),
                            rs.getString("transaction_uuid"),
                            rs.getString("status"),
                            rs.getTimestamp("donation_time")
                    );
                    donations.add(donation);
                }
            }

            return donations;
        } catch (SQLException e) {
            logger.error("SQL error while retrieving donations for user ID: " + userId, e);
            throw new DataAccessException("Database error while retrieving donations", "Please try again later.", e);
        } catch (Exception e) {
            logger.error("Unexpected error while retrieving donations", e);
            throw new DataAccessException("Unexpected error while retrieving donations", "Please contact support.", e);
        }
    }

    // Method to get a donation by transaction ID
    public static Donation getDonationByTransactionId(String transactionUuid) {
        if (transactionUuid == null || transactionUuid.isEmpty()) {
            logger.error("Transaction UUID cannot be null or empty");
            throw new IllegalArgumentException("Transaction UUID cannot be null or empty");
        }

        String sql = "SELECT * FROM donations WHERE transaction_uuid = ?";
        try (Connection conn = DBConnectionManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, transactionUuid);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Donation(
                            rs.getInt("donation_id"),
                            rs.getInt("user_id"),
                            rs.getDouble("amount"),
                            rs.getString("product_code"),
                            rs.getString("transaction_uuid"),
                            rs.getString("status"),
                            rs.getTimestamp("donation_time")
                    );
                } else {
                    logger.warn("No donation found with transaction UUID: " + transactionUuid);
                    return null;
                }
            }
        } catch (SQLException e) {
            logger.error("SQL error while retrieving donation by transaction UUID: " + transactionUuid, e);
            throw new DataAccessException("Database error while retrieving donation", "Please try again later.", e);
        } catch (Exception e) {
            logger.error("Unexpected error while retrieving donation", e);
            throw new DataAccessException("Unexpected error while retrieving donation", "Please contact support.", e);
        }
    }

    // Method to verify a transaction by UUID, product code, and amount
    public static boolean verifyTransaction(String transactionUuid, String productCode, double amount) {
        if (transactionUuid == null || transactionUuid.isEmpty()) {
            logger.error("Transaction UUID cannot be null or empty");
            throw new IllegalArgumentException("Transaction UUID cannot be null or empty");
        }
        if (productCode == null || productCode.isEmpty()) {
            logger.error("Product code cannot be null or empty");
            throw new IllegalArgumentException("Product code cannot be null or empty");
        }
        if (amount <= 0) {
            logger.error("Amount must be greater than zero");
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        String sql = "SELECT * FROM donations WHERE transaction_uuid = ? AND product_code = ? AND amount = ?";
        try (Connection conn = DBConnectionManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, transactionUuid);
            stmt.setString(2, productCode);
            stmt.setDouble(3, amount);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();  // Returns true if a matching transaction is found
            }
        } catch (SQLException e) {
            logger.error("SQL error while verifying transaction", e);
            throw new DataAccessException("Database error while verifying transaction", "Please try again later.", e);
        } catch (Exception e) {
            logger.error("Unexpected error while verifying transaction", e);
            throw new DataAccessException("Unexpected error while verifying transaction", "Please contact support.", e);
        }
    }

    // Method to update donation status by transaction UUID
    public static void updateDonationStatus(String transactionUuid, String status) {
        if (transactionUuid == null || transactionUuid.isEmpty()) {
            logger.error("Transaction UUID cannot be null or empty");
            throw new IllegalArgumentException("Transaction UUID cannot be null or empty");
        }
        if (status == null || status.isEmpty()) {
            logger.error("Status cannot be null or empty");
            throw new IllegalArgumentException("Status cannot be null or empty");
        }

        String sql = "UPDATE donations SET status = ? WHERE transaction_uuid = ?";
        try (Connection conn = DBConnectionManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setString(2, transactionUuid);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                logger.warn("No donation found with transaction UUID: " + transactionUuid);
                throw new DataAccessException("No donation found with the given transaction UUID", "Donation not found.", null);
            }

        } catch (SQLException e) {
            logger.error("SQL error while updating donation status for transaction UUID: " + transactionUuid, e);
            throw new DataAccessException("Database error while updating donation status", "Please try again later.", e);
        } catch (Exception e) {
            logger.error("Unexpected error while updating donation status", e);
            throw new DataAccessException("Unexpected error while updating donation status", "Please contact support.", e);
        }
    }
}
