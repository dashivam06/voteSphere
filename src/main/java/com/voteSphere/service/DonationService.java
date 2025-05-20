package com.voteSphere.service;

import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.voteSphere.dao.DonationDao;
import com.voteSphere.exception.DataAccessException;
import com.voteSphere.model.Donation;
import com.voteSphere.util.ValidationUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DonationService {

    private static final Logger logger = LogManager.getLogger(DonationService.class);

    // Add a new donation
    public static boolean addDonation(HttpServletRequest request, HttpServletResponse response) {
        boolean hasErrors = false;

        String donorName = request.getParameter("donorName");
        String donationAmount = request.getParameter("donationAmount");
        String donationDate = request.getParameter("donationDate");
        String partyId = request.getParameter("partyId");

        // Validate fields
        if (ValidationUtil.isNullOrEmpty(donorName)) {
            request.setAttribute("donorName_error", "Donor name is required.");
            hasErrors = true;
        }

        if (ValidationUtil.isNullOrEmpty(donationAmount) || !ValidationUtil.isNumeric(donationAmount)) {
            request.setAttribute("donationAmount_error", "Valid donation amount is required.");
            hasErrors = true;
        }

        if (ValidationUtil.isNullOrEmpty(donationDate)) {
            request.setAttribute("donationDate_error", "Donation date is required.");
            hasErrors = true;
        }

        if (ValidationUtil.isNullOrEmpty(partyId) || !ValidationUtil.isNumeric(partyId)) {
            request.setAttribute("partyId_error", "Valid party ID is required.");
            hasErrors = true;
        }

        if (hasErrors) {
            return false;
        }

        try {
//            Donation newDonation = new Donation(donorName, Double.parseDouble(donationAmount), donationDate, Integer.parseInt(partyId));
//            return DonationDao.createDonation(newDonation);
        	return true;
        } catch (DataAccessException dae) {
            logger.error("Failed to create donation: " + dae.getMessage(), dae);
            request.setAttribute("donation_creation_error", dae.getUserMessage());
            return false;
        } catch (Exception e) {
            logger.error("Unexpected error occurred while creating donation", e);
            request.setAttribute("donation_creation_error", "An unexpected error occurred. Please try again.");
            return false;
        }
    }

    // Retrieve donation by ID
    public static Donation getDonationById(HttpServletRequest request, HttpServletResponse response, Integer donationId) {
        if (donationId == null || donationId <= 0) {
            request.setAttribute("id_error", "Valid donation ID is required.");
            return null;
        }

        try {
            Donation donation = DonationDao.findDonationById(donationId);
            if (donation == null) {
                request.setAttribute("donation_not_found", "No donation found with the given ID.");
            }
            return donation;
        } catch (DataAccessException dae) {
            logger.error("Failed to retrieve donation by ID: " + dae.getMessage(), dae);
            request.setAttribute("donation_retrieve_error", dae.getUserMessage());
        } catch (Exception e) {
            logger.error("Unexpected error retrieving donation by ID", e);
            request.setAttribute("donation_retrieve_error", "An unexpected error occurred. Please try again.");
        }
        return null;
    }



    // Retrieve donation by ID
    public static Donation getDonationById(Integer donationId) {
        if (donationId == null || donationId <= 0) {
            logger.warn("Valid donation ID is required.");
            return null;
        }

        try {
            Donation donation = DonationDao.findDonationById(donationId);
            if (donation == null) {
                logger.warn("No donation found with the given ID.");
            }
            return donation;
        } catch (DataAccessException dae) {
            logger.error("Failed to retrieve donation by ID: " + dae.getMessage(), dae);
        } catch (Exception e) {
            logger.error("Unexpected error retrieving donation by ID", e);
        }
        return null;
    }



    // Retrieve all donations
    public static List<Donation> getAllDonations() {
        try {
            return DonationDao.getAllDonations();
        } catch (DataAccessException dae) {
            logger.error("Data access error while fetching all donations: " + dae.getMessage(), dae);
            return Collections.emptyList();
        } catch (Exception e) {
            logger.error("Unexpected error while fetching all donations: " + e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    // Update an existing donation
    public static boolean updateDonation(HttpServletRequest request, HttpServletResponse response, Integer donationId) {
        boolean hasErrors = false;

        if (donationId == null || donationId <= 0) {
            request.setAttribute("donation_id_error", "Invalid donation ID.");
            return false;
        }

        String donorName = request.getParameter("donorName");
        String donationAmount = request.getParameter("donationAmount");
        String donationDate = request.getParameter("donationDate");
        String partyId = request.getParameter("partyId");

        // Validate fields
        if (ValidationUtil.isNullOrEmpty(donorName)) {
            request.setAttribute("donorName_error", "Donor name is required.");
            hasErrors = true;
        }

        if (ValidationUtil.isNullOrEmpty(donationAmount) || !ValidationUtil.isNumeric(donationAmount)) {
            request.setAttribute("donationAmount_error", "Valid donation amount is required.");
            hasErrors = true;
        }

        if (ValidationUtil.isNullOrEmpty(donationDate)) {
            request.setAttribute("donationDate_error", "Donation date is required.");
            hasErrors = true;
        }

        if (ValidationUtil.isNullOrEmpty(partyId) || !ValidationUtil.isNumeric(partyId)) {
            request.setAttribute("partyId_error", "Valid party ID is required.");
            hasErrors = true;
        }

        if (hasErrors) {
            return false;
        }

        try {
            Donation updatedDonation = new Donation();
            boolean updated = DonationDao.updateDonation(updatedDonation);

            if (!updated) {
                request.setAttribute("donation_update_error", "No donation was updated. Possibly invalid ID.");
            }

            return updated;
        } catch (DataAccessException dae) {
            logger.error("Failed to update donation: " + dae.getMessage(), dae);
            request.setAttribute("donation_update_error", dae.getUserMessage());
        } catch (Exception e) {
            logger.error("Unexpected error occurred while updating donation", e);
            request.setAttribute("donation_update_error", "An unexpected error occurred. Please try again.");
        }

        return false;
    }

    // Delete a donation
    public static boolean deleteDonation(HttpServletRequest request, HttpServletResponse response, int donationId) {
        if (donationId <= 0) {
            request.setAttribute("donation_id_error", "Invalid donation ID.");
            return false;
        }

        try {
            boolean deleted = DonationDao.deleteDonation(donationId);
            if (!deleted) {
                request.setAttribute("donation_delete_error", "No donation found with the given ID to delete.");
            }
            return deleted;
        } catch (DataAccessException dae) {
            logger.error("Failed to delete donation: " + dae.getMessage(), dae);
            request.setAttribute("donation_delete_error", dae.getUserMessage());
        } catch (Exception e) {
            logger.error("Unexpected error while deleting donation", e);
            request.setAttribute("donation_delete_error", "An unexpected error occurred. Please try again.");
        }
        return false;
    }

    // Get total donations amount with request/response handling
    public static double getTotalDonations(HttpServletRequest request, HttpServletResponse response) {
        try {
            double total = DonationDao.getTotalDonations();
            request.setAttribute("totalDonations", total);
            return total;
        } catch (DataAccessException dae) {
            logger.error("Failed to retrieve total donations: " + dae.getMessage(), dae);
            request.setAttribute("total_donations_error", dae.getUserMessage());
            return 0.0;
        } catch (Exception e) {
            logger.error("Unexpected error retrieving total donations", e);
            request.setAttribute("total_donations_error", "An unexpected error occurred while calculating total donations.");
            return 0.0;
        }
    }

    // Get monthly donations amount with request/response handling
    public static double getMonthlyDonations(HttpServletRequest request, HttpServletResponse response) {
        try {
            double monthlyTotal = DonationDao.getMonthlyDonations();
            request.setAttribute("monthlyDonations", monthlyTotal);
            return monthlyTotal;
        } catch (DataAccessException dae) {
            logger.error("Failed to retrieve monthly donations: " + dae.getMessage(), dae);
            request.setAttribute("monthly_donations_error", dae.getUserMessage());
            return 0.0;
        } catch (Exception e) {
            logger.error("Unexpected error retrieving monthly donations", e);
            request.setAttribute("monthly_donations_error", "An unexpected error occurred while calculating monthly donations.");
            return 0.0;
        }
    }

    // Get average donation amount with request/response handling
    public static double getAverageDonation(HttpServletRequest request, HttpServletResponse response) {
        try {
            double average = DonationDao.getAverageDonation();
            request.setAttribute("averageDonation", average);
            return average;
        } catch (DataAccessException dae) {
            logger.error("Failed to retrieve average donation: " + dae.getMessage(), dae);
            request.setAttribute("average_donation_error", dae.getUserMessage());
            return 0.0;
        } catch (Exception e) {
            logger.error("Unexpected error retrieving average donation", e);
            request.setAttribute("average_donation_error", "An unexpected error occurred while calculating average donation.");
            return 0.0;
        }
    }

    // Get total number of donors with request/response handling
    public static int getTotalDonors(HttpServletRequest request, HttpServletResponse response) {
        try {
            int donorCount = DonationDao.getTotalDonors();
            request.setAttribute("totalDonors", donorCount);
            return donorCount;
        } catch (DataAccessException dae) {
            logger.error("Failed to retrieve total donors: " + dae.getMessage(), dae);
            request.setAttribute("total_donors_error", dae.getUserMessage());
            return 0;
        } catch (Exception e) {
            logger.error("Unexpected error retrieving total donors", e);
            request.setAttribute("total_donors_error", "An unexpected error occurred while counting total donors.");
            return 0;
        }
    }

    // Get all donations with request/response handling
    public static List<Donation> getAllDonations(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Donation> donations = DonationDao.getAllDonations();
            request.setAttribute("donations", donations);
            return donations;
        } catch (DataAccessException dae) {
            logger.error("Failed to retrieve all donations: " + dae.getMessage(), dae);
            request.setAttribute("all_donations_error", dae.getUserMessage());
            return Collections.emptyList();
        } catch (Exception e) {
            logger.error("Unexpected error retrieving all donations", e);
            request.setAttribute("all_donations_error", "An unexpected error occurred while retrieving donations.");
            return Collections.emptyList();
        }
    }



    // Delete a donation
    public static boolean refundDonation( int donationId) {
        if (donationId <= 0) {
            logger.warn("Invalid donation ID.");
            return false;
        }

        try {
            boolean deleted = DonationDao.deleteDonation(donationId);
            if (!deleted) {
                logger.error( "No donation found with the given ID to delete.");
            }
            return deleted;
        } catch (DataAccessException dae) {
            logger.error("Failed to delete donation: " + dae.getMessage(), dae);
        } catch (Exception e) {
            logger.error("Unexpected error while deleting donation", e);
        }
        return false;
    }

//
//    // Search donations with request/response handling
//    public static List<Donation> searchDonations(HttpServletRequest request, HttpServletResponse response, String query) {
//        if (ValidationUtil.isNullOrEmpty(query)) {
//            request.setAttribute("search_error", "Search query cannot be empty");
//            return Collections.emptyList();
//        }
//
//        try {
//            List<Donation> results = Donation.searchDonations(query);
//            request.setAttribute("searchResults", results);
//            request.setAttribute("searchQuery", query);
//            return results;
//        } catch (DataAccessException dae) {
//            logger.error("Failed to search donations: " + dae.getMessage(), dae);
//            request.setAttribute("search_error", dae.getUserMessage());
//            return Collections.emptyList();
//        } catch (Exception e) {
//            logger.error("Unexpected error searching donations", e);
//            request.setAttribute("search_error", "An unexpected error occurred during search.");
//            return Collections.emptyList();
//        }
//    }
//
//    // Process donation refund with request/response handling
//    public static boolean refundDonation(HttpServletRequest request, HttpServletResponse response, int donationId) {
//        if (donationId <= 0) {
//            request.setAttribute("refund_error", "Invalid donation ID");
//            return false;
//        }
//
//        try {
//            boolean success = DonationDao.refundDonation(donationId);
//            if (success) {
//                request.setAttribute("refund_success", "Donation refund processed successfully");
//            } else {
//                request.setAttribute("refund_error", "Failed to process refund");
//            }
//            return success;
//        } catch (DataAccessException dae) {
//            logger.error("Failed to process refund for donation ID " + donationId + ": " + dae.getMessage(), dae);
//            request.setAttribute("refund_error", dae.getUserMessage());
//            return false;
//        } catch (Exception e) {
//            logger.error("Unexpected error processing refund for donation ID " + donationId, e);
//            request.setAttribute("refund_error", "An unexpected error occurred while processing refund.");
//            return false;
//        }
//    }


}
