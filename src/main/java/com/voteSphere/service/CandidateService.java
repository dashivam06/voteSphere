package com.voteSphere.service;

import java.sql.Date;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.voteSphere.dao.CandidateDao;
import com.voteSphere.exception.DataAccessException;
import com.voteSphere.model.Candidate;
import com.voteSphere.util.ImageUploadHandler;
import com.voteSphere.util.ValidationUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CandidateService {

    private static final Logger logger = LogManager.getLogger(CandidateService.class);

    public static boolean addCandidate(HttpServletRequest request, HttpServletResponse response) {
        boolean hasErrors = false;

        String fname = request.getParameter("first_name");
        String lname = request.getParameter("last_name");
        String address = request.getParameter("address");
        String gender = request.getParameter("gender");
        String dobStr = request.getParameter("dob");
        String highestEducation = request.getParameter("highest_education");
        String bio = request.getParameter("bio");
        String manifesto = request.getParameter("manifesto");
        String isIndependentStr = request.getParameter("is_independent");
        String partyIdStr = request.getParameter("party_id");
        String electionIdStr = request.getParameter("election_id");

        String appRealPath = request.getServletContext().getRealPath("");
        long maxImageSize = 2 * 1024 * 1024;

        String profileImageName = ImageUploadHandler.processImageUpload(request, "candidate_profile_image", "candidate_profile_image_error",
                "candidate-profile-image", appRealPath, maxImageSize);
        if (profileImageName == null) {
            hasErrors = true;
        }

        // Field validations
        if (ValidationUtil.isNullOrEmpty(fname)) {
            request.setAttribute("fname_error", "First name is required.");
            hasErrors = true;
        } else if (!ValidationUtil.isAlphabetic(fname)) {
            request.setAttribute("fname_error", "First name must contain only letters.");
            hasErrors = true;
        }

        if (ValidationUtil.isNullOrEmpty(lname)) {
            request.setAttribute("lname_error", "Last name is required.");
            hasErrors = true;
        } else if (!ValidationUtil.isAlphabetic(lname)) {
            request.setAttribute("lname_error", "Last name must contain only letters.");
            hasErrors = true;
        }

        if (ValidationUtil.isNullOrEmpty(address)) {
            request.setAttribute("address_error", "Address is required.");
            hasErrors = true;
        }

        if (ValidationUtil.isNullOrEmpty(gender)) {
            request.setAttribute("gender_error", "Gender is required.");
            hasErrors = true;
        }

        Date dob = null;
        if (ValidationUtil.isNullOrEmpty(dobStr)) {
            request.setAttribute("dob_error", "Date of birth is required.");
            hasErrors = true;
        } else {
            try {
                dob = Date.valueOf(dobStr);
            } catch (IllegalArgumentException e) {
                request.setAttribute("dob_error", "Invalid date format. Use yyyy-MM-dd.");
                hasErrors = true;
            }
        }

        if (ValidationUtil.isNullOrEmpty(highestEducation)) {
            request.setAttribute("highestEducation_error", "Highest education is required.");
            hasErrors = true;
        }

        if (ValidationUtil.isNullOrEmpty(bio)) {
            request.setAttribute("bio_error", "Bio is required.");
            hasErrors = true;
        }

        if (ValidationUtil.isNullOrEmpty(manifesto)) {
            request.setAttribute("manifesto_error", "Manifesto is required.");
            hasErrors = true;
        }

        Boolean isIndependent = null;
        if (ValidationUtil.isNullOrEmpty(isIndependentStr)) {
            request.setAttribute("isIndependent_error", "Independence status is required.");
            hasErrors = true;
        } else {
            isIndependent = Boolean.valueOf(isIndependentStr);
        }

        Integer partyId = null;
        if (!ValidationUtil.isNullOrEmpty(partyIdStr)) {
            try {
                partyId = Integer.parseInt(partyIdStr);
            } catch (NumberFormatException e) {
                request.setAttribute("partyId_error", "Invalid Party ID.");
                hasErrors = true;
            }
        }

        Integer electionId = null;
        if (ValidationUtil.isNullOrEmpty(electionIdStr)) {
            request.setAttribute("electionId_error", "Election ID is required.");
            hasErrors = true;
        } else {
            try {
                electionId = Integer.parseInt(electionIdStr);
            } catch (NumberFormatException e) {
                request.setAttribute("electionId_error", "Invalid Election ID.");
                hasErrors = true;
            }
        }

        if (hasErrors) {
            return false;
        }

        try {
            Candidate candidate = new Candidate(fname, lname, address, gender, dob, isIndependent,
                    highestEducation, bio, profileImageName, partyId, electionId, manifesto);
            return CandidateDao.createCandidate(candidate);
        } catch (DataAccessException dae) {
            logger.error("Failed to create candidate: " + dae.getMessage(), dae);
            request.setAttribute("candidate_creation_error", dae.getUserMessage());
            return false;
        } catch (Exception e) {
            logger.error("Unexpected error occurred while creating candidate", e);
            request.setAttribute("candidate_creation_error", "An unexpected error occurred. Please try again.");
            return false;
        }
    }

    public static Candidate getCandidateById(HttpServletRequest request, HttpServletResponse response, Integer id) {
        if (id == null || id <= 0) {
            request.setAttribute("id_error", "Valid candidate ID is required.");
            return null;
        }

        try {
            Candidate candidate = CandidateDao.findCandidateById(id);
            if (candidate == null) {
                request.setAttribute("candidate_not_found", "No candidate found with the given ID.");
            }
            return candidate;
        } catch (DataAccessException dae) {
            logger.error("Failed to retrieve candidate by ID: " + dae.getMessage(), dae);
            request.setAttribute("candidate_retrieve_error", dae.getUserMessage());
        } catch (Exception e) {
            logger.error("Unexpected error retrieving candidate by ID", e);
            request.setAttribute("candidate_retrieve_error", "An unexpected error occurred. Please try again.");
        }
        return null;
    }
    
    
    public static boolean deleteCandidate(HttpServletRequest request, HttpServletResponse response, Integer id) {
        if (id == null || id <= 0) {
            request.setAttribute("id_error", "Valid candidate ID is required.");
            return false;
        }

        try {
            boolean deleted = CandidateDao.deleteCandidate(id);

            if (deleted) {
                return true;
            } else {
                request.setAttribute("candidate_not_found", "No candidate found with the given ID.");
                return false;
            }

        } catch (DataAccessException dae) {
            logger.error("Failed to delete candidate by ID: " + dae.getMessage(), dae);
            request.setAttribute("candidate_delete_error", dae.getUserMessage());
        } catch (Exception e) {
            logger.error("Unexpected error deleting candidate by ID", e);
            request.setAttribute("candidate_delete_error", "An unexpected error occurred. Please try again.");
        }

        return false;
    }


    public static List<Candidate> getAllCandidates() {
        try {
            return CandidateDao.getAllCandidates();
        } catch (DataAccessException dae) {
            logger.error("Data access error while fetching all candidates: " + dae.getMessage(), dae);
            return Collections.emptyList();
        } catch (Exception e) {
            logger.error("Unexpected error while fetching all candidates: " + e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public static boolean updateCandidate(HttpServletRequest request, HttpServletResponse response, Integer candidateId) {
        boolean hasErrors = false;


        if (candidateId == null || candidateId <= 0) {
            logger.warn("Validation failed: Invalid candidate ID. Provided value: {}", candidateId);
            request.setAttribute("candidate_id_error", "Invalid candidate ID.");
            return false;
        }

        String fname = request.getParameter("first_name");
        String lname = request.getParameter("last_name");
        String address = request.getParameter("address");
        String gender = request.getParameter("gender");
        String dobStr = request.getParameter("dob");
        String highestEducation = request.getParameter("highest_education");
        String bio = request.getParameter("bio");
        String manifesto = request.getParameter("manifesto");
        String isIndependentStr = request.getParameter("is_independent");
        String partyIdStr = request.getParameter("party_id");
        String electionIdStr = request.getParameter("election_id");

        // Set up paths and max file size for image uploads
        String appRealPath = request.getServletContext().getRealPath("");
        long maxImageSize = 2 * 1024 * 1024; // 2MB

        String profileImage = ImageUploadHandler.processImageUpload(
            request,
            "candidate_profile_image",
            "candidate_profile_image_error",
            "candidate-profile-image",
            appRealPath,
            maxImageSize
        );

        // Validations
        if (ValidationUtil.isNullOrEmpty(fname)) {
            logger.warn("Validation failed: First name is empty.");
            request.setAttribute("fname_error", "First name is required.");
            hasErrors = true;
        }

        if (ValidationUtil.isNullOrEmpty(lname)) {
            logger.warn("Validation failed: Last name is empty.");
            request.setAttribute("lname_error", "Last name is required.");
            hasErrors = true;
        }

        if (ValidationUtil.isNullOrEmpty(address)) {
            logger.warn("Validation failed: Address is empty.");
            request.setAttribute("address_error", "Address is required.");
            hasErrors = true;
        }

        if (ValidationUtil.isNullOrEmpty(gender)) {
            logger.warn("Validation failed: Gender is empty.");
            request.setAttribute("gender_error", "Gender is required.");
            hasErrors = true;
        }

        Date dob = null;
        if (ValidationUtil.isNullOrEmpty(dobStr)) {
            logger.warn("Validation failed: Date of birth is empty.");
            request.setAttribute("dob_error", "Date of birth is required.");
            hasErrors = true;
        } else {
            try {
                dob = Date.valueOf(dobStr);
            } catch (IllegalArgumentException e) {
                logger.warn("Validation failed: Invalid date format. Provided value: {}", dobStr);
                request.setAttribute("dob_error", "Invalid date format. Use yyyy-MM-dd.");
                hasErrors = true;
            }
        }

        if (ValidationUtil.isNullOrEmpty(highestEducation)) {
            logger.warn("Validation failed: Highest education is empty.");
            request.setAttribute("highestEducation_error", "Highest education is required.");
            hasErrors = true;
        }

        if (ValidationUtil.isNullOrEmpty(bio)) {
            logger.warn("Validation failed: Bio is empty.");
            request.setAttribute("bio_error", "Bio is required.");
            hasErrors = true;
        }

        if (ValidationUtil.isNullOrEmpty(manifesto)) {
            logger.warn("Validation failed: Manifesto is empty.");
            request.setAttribute("manifesto_error", "Manifesto is required.");
            hasErrors = true;
        }

        Boolean isIndependent = null;
        if (ValidationUtil.isNullOrEmpty(isIndependentStr)) {
            logger.warn("Validation failed: Independence status is empty.");
            request.setAttribute("isIndependent_error", "Independence status is required.");
            hasErrors = true;
        } else {
            isIndependent = Boolean.valueOf(isIndependentStr);
        }

        Integer partyId = null;
        if (!ValidationUtil.isNullOrEmpty(partyIdStr)) {
            try {
                partyId = Integer.parseInt(partyIdStr);
            } catch (NumberFormatException e) {
                logger.warn("Validation failed: Invalid Party ID format. Provided value: {}", partyIdStr);
                request.setAttribute("partyId_error", "Invalid Party ID.");
                hasErrors = true;
            }
        }

        Integer electionId = null;
        if (ValidationUtil.isNullOrEmpty(electionIdStr)) {
            logger.warn("Validation failed: Election ID is empty.");
            request.setAttribute("electionId_error", "Election ID is required.");
            hasErrors = true;
        } else {
            try {
                electionId = Integer.parseInt(electionIdStr);
            } catch (NumberFormatException e) {
                logger.warn("Validation failed: Invalid Election ID format. Provided value: {}", electionIdStr);
                request.setAttribute("electionId_error", "Invalid Election ID.");
                hasErrors = true;
            }
        }

        if (hasErrors) {
            logger.info("Candidate creation/update aborted due to validation errors.");
            return false;
        }

        try {
            Candidate updatedCandidate = new Candidate(candidateId, fname, lname, address, gender, dob,
                    isIndependent, highestEducation, bio, profileImage, partyId, electionId, manifesto);
            return CandidateDao.updateCandidate(updatedCandidate);
        } catch (DataAccessException dae) {
            logger.error("Failed to update candidate: " + dae.getMessage(), dae);
            request.setAttribute("candidate_update_error", dae.getUserMessage());
            return false;
        } catch (Exception e) {
            logger.error("Unexpected error occurred while updating candidate", e);
            request.setAttribute("candidate_update_error", "An unexpected error occurred. Please try again.");
            return false;
        }
    }
    
    
    public static List<Candidate> getCandidatesByParty(HttpServletRequest request, HttpServletResponse response, Integer partyId) {
        if (partyId == null || partyId <= 0) {
            request.setAttribute("party_id_error", "Valid party ID is required.");
            return Collections.emptyList();
        }

        try {
            List<Candidate> candidates = CandidateDao.getCandidateByParty(partyId);
            if (candidates == null || candidates.isEmpty()) {
                request.setAttribute("no_candidates_found", "No candidates found for the given party.");
            }
            return candidates;
        } catch (DataAccessException dae) {
            logger.error("Failed to retrieve candidates by party ID: " + dae.getMessage(), dae);
            request.setAttribute("candidate_retrieve_error", dae.getUserMessage());
        } catch (Exception e) {
            logger.error("Unexpected error retrieving candidates by party ID", e);
            request.setAttribute("candidate_retrieve_error", "An unexpected error occurred. Please try again.");
        }
        return Collections.emptyList();
    }

    public static List<Candidate> getCandidatesByElection(HttpServletRequest request, HttpServletResponse response, Integer electionId) {
        if (electionId == null || electionId <= 0) {
            request.setAttribute("election_id_error", "Valid election ID is required.");
            return Collections.emptyList();
        }

        try {
            List<Candidate> candidates = CandidateDao.getCandidateByElection(electionId);
            if (candidates == null || candidates.isEmpty()) {
                request.setAttribute("no_candidates_found", "No candidates found for the given election.");
            }
            return candidates;
        } catch (DataAccessException dae) {
            logger.error("Failed to retrieve candidates by election ID: " + dae.getMessage(), dae);
            request.setAttribute("candidate_retrieve_error", dae.getUserMessage());
        } catch (Exception e) {
            logger.error("Unexpected error retrieving candidates by election ID", e);
            request.setAttribute("candidate_retrieve_error", "An unexpected error occurred. Please try again.");
        }
        return Collections.emptyList();
    }

}
