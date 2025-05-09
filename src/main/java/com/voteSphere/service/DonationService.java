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
}
