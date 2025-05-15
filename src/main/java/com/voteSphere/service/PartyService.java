package com.voteSphere.service;

import java.util.Collections;
import java.util.List;

import com.voteSphere.util.ImgUploadUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.voteSphere.dao.PartyDao;
import com.voteSphere.exception.DataAccessException;
import com.voteSphere.model.Party;
import com.voteSphere.util.ValidationUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PartyService {

	private static final Logger logger = LogManager.getLogger(PartyService.class); 
																					

	public static boolean addPartyElection(HttpServletRequest request, HttpServletResponse response) {

		boolean hasErrors = false;

		String name = request.getParameter("name");
		String leaderName = request.getParameter("leader_name");
		String founderName = request.getParameter("founder_name");
		String description = request.getParameter("description");

		String appRealPath = request.getServletContext().getRealPath("");
		long maxImageSize = 2 * 1024 * 1024;

		// Process symbol image
		String symbolImageName = ImgUploadUtil.processImageUpload(request, "symbol_image", "symbol_image_error",
				"party-symbol-image", appRealPath, maxImageSize);
		if (symbolImageName == null) {
			hasErrors = true;
		}

		// Process cover image
		String coverImageName = ImgUploadUtil.processImageUpload(request, "cover_image", "cover_image_error",
				"party-cover-image", appRealPath, maxImageSize);

		if (coverImageName == null) {
			hasErrors = true;
		}

		// Field validations
		if (ValidationUtil.isNullOrEmpty(name)) {
			request.setAttribute("name_error", "Party name is required.");
			hasErrors = true;
		}

		if (ValidationUtil.isNullOrEmpty(leaderName)) {
			request.setAttribute("leaderName_error", "Leader name is required.");
			hasErrors = true;
		} else if (!ValidationUtil.isAlphabetic(leaderName)) {
			request.setAttribute("leaderName_error", "Leader name must contain only letters.");
			hasErrors = true;
		}

		if (ValidationUtil.isNullOrEmpty(founderName)) {
			request.setAttribute("founderName_error", "Founder name is required.");
			hasErrors = true;
		} else if (!ValidationUtil.isAlphabetic(founderName)) {
			request.setAttribute("founderName_error", "Founder name must contain only letters.");
			hasErrors = true;
		}

		if (ValidationUtil.isNullOrEmpty(description)) {
			request.setAttribute("description_error", "Party description is required.");
			hasErrors = true;
		}

		if (hasErrors) {
			return false;
		}

		try {
			Party newParty = new Party(name, leaderName, founderName, symbolImageName, coverImageName, description);
			return PartyDao.createParty(newParty);
		} catch (DataAccessException dae) {
			logger.error("Failed to create party: " + dae.getMessage(), dae);
			request.setAttribute("party_creation_error", dae.getUserMessage());
			return false;
		} catch (Exception e) {
			logger.error("Unexpected error occurred while creating party", e);
			request.setAttribute("party_creation_error", "An unexpected error occurred. Please try again.");
			return false;
		}
	}

	public static Party getPartyByName(HttpServletRequest request, HttpServletResponse response, String name) {
		if (ValidationUtil.isNullOrEmpty(name)) {
			request.setAttribute("name_error", "Party name is required.");
			return null;
		}

		try {
			Party party = PartyDao.findPartyByName(name);
			if (party == null) {
				request.setAttribute("party_not_found", "No party found with the given name.");
			}
			return party;
		} catch (DataAccessException dae) {
			logger.error("Failed to retrieve party by name: " + dae.getMessage(), dae);
			request.setAttribute("party_retrieve_error", dae.getUserMessage());
		} catch (Exception e) {
			logger.error("Unexpected error retrieving party by name", e);
			request.setAttribute("party_retrieve_error", "An unexpected error occurred. Please try again.");
		}
		return null;
	}
	
	
	public static Party getPartyById(HttpServletRequest request, HttpServletResponse response, Integer id) {
	    if (id == null || id <= 0) {
	        request.setAttribute("id_error", "Valid party ID is required.");
	        return null;
	    }

	    try {
	        Party party = PartyDao.findPartyById(id);
	        if (party == null) {
	            request.setAttribute("party_not_found", "No party found with the given ID.");
	        }
	        return party;
	    } catch (DataAccessException dae) {
	        logger.error("Failed to retrieve party by ID: " + dae.getMessage(), dae);
	        request.setAttribute("party_retrieve_error", dae.getUserMessage());
	    } catch (Exception e) {
	        logger.error("Unexpected error retrieving party by ID", e);
	        request.setAttribute("party_retrieve_error", "An unexpected error occurred. Please try again.");
	    }
	    return null;
	}



	public static Party getPartyById(Integer id) {

		try {
			logger.debug("Fetching party with ID: {}", id);

			Party party = PartyDao.findPartyById(id);

			if (party == null) {
				logger.warn("No party found with ID: {}", id);
			} else {
				logger.info("Successfully retrieved party ID: {}", id);
			}

			return party;
		} catch (DataAccessException dae) {
			logger.error("Failed to retrieve party by ID: " + dae.getMessage(), dae);
		} catch (Exception e) {
			logger.error("Unexpected error retrieving party by ID", e);
		}
		return null;
	}

	public static List<Party> getAllPartys() {
		try {
			return PartyDao.getAllParties();
		} catch (DataAccessException dae) {
			logger.error("Data access error while fetching all parties: " + dae.getMessage(), dae);
			return Collections.emptyList();
		} catch (Exception e) {
			logger.error("Unexpected error while fetching all parties: " + e.getMessage(), e);
			return Collections.emptyList();
		}
	}

	public static boolean updateParty(HttpServletRequest request, HttpServletResponse response, Integer partyId) {
		boolean hasErrors = false;

		if (partyId == null || partyId <= 0) {
			request.setAttribute("party_id_error", "Invalid party ID.");
			return false;
		}

		String name = request.getParameter("name");
		String leaderName = request.getParameter("leader_name");
		String founderName = request.getParameter("founder_name");
		String description = request.getParameter("description");

		if (ValidationUtil.isNullOrEmpty(name)) {
		    logger.warn("Validation failed: Party name is empty.");
		    request.setAttribute("name_error", "Party name is required.");
		    hasErrors = true;
		}

		if (ValidationUtil.isNullOrEmpty(leaderName)) {
		    logger.warn("Validation failed: Leader name is empty.");
		    request.setAttribute("leaderName_error", "Leader name is required.");
		    hasErrors = true;
		} else if (!ValidationUtil.isAlphabetic(leaderName)) {
		    logger.warn("Validation failed: Leader name is not alphabetic - {}", leaderName);
		    request.setAttribute("leader_name_error", "Leader name must contain only letters.");
		    hasErrors = true;
		}

		if (ValidationUtil.isNullOrEmpty(founderName)) {
		    logger.warn("Validation failed: Founder name is empty.");
		    request.setAttribute("founder_name_error", "Founder name is required.");
		    hasErrors = true;
		} else if (!ValidationUtil.isAlphabetic(founderName)) {
		    logger.warn("Validation failed: Founder name is not alphabetic - {}", founderName);
		    request.setAttribute("founder_name_error", "Founder name must contain only letters.");
		    hasErrors = true;
		}

		if (ValidationUtil.isNullOrEmpty(description)) {
		    logger.warn("Validation failed: Description is empty.");
		    request.setAttribute("description_error", "Party description is required.");
		    hasErrors = true;
		}
		
		String appRealPath = request.getServletContext().getRealPath("");
		long maxImageSize = 2 * 1024 * 1024; // 2MB
		
		// Process party-related images
		String symbolImage = ImgUploadUtil.processImageUpload(
		    request, "symbol_image", "symbol_image_error", "party-symbols", appRealPath, maxImageSize);

		String coverImage = ImgUploadUtil.processImageUpload(
		    request, "cover_image", "cover_image_error", "party-covers", appRealPath, maxImageSize);

		if (symbolImage == null) {
		    hasErrors = true;
		    logger.warn("Validation failed: Party symbol image is missing or failed to upload.");
		}

		if (coverImage == null) {
		    hasErrors = true;
		    logger.warn("Validation failed: Party cover image is missing or failed to upload.");
		}


		if (hasErrors) {
			return false;
		}

		try {
			Party updatedParty = new Party(partyId, name, leaderName, founderName, symbolImage, coverImage,
					description);
			boolean updated = PartyDao.updateParty(updatedParty);

			if (!updated) {
				request.setAttribute("party_update_error", "No party was updated. Possibly invalid ID.");
			}

			return updated;
		} catch (DataAccessException dae) {
			logger.error("Failed to update party: " + dae.getMessage(), dae);
			request.setAttribute("party_update_error", dae.getUserMessage());
		} catch (Exception e) {
			logger.error("Unexpected error occurred while updating party", e);
			request.setAttribute("party_update_error", "An unexpected error occurred. Please try again.");
		}

		return false;
	}

	public static boolean deleteParty(HttpServletRequest request, HttpServletResponse response, int partyId) {
		if (partyId <= 0) {
			request.setAttribute("party_id_error", "Invalid party ID.");
			return false;
		}

		try {
			boolean deleted = PartyDao.deleteParty(partyId);
			if (!deleted) {
				request.setAttribute("party_delete_error", "No party found with the given ID to delete.");
			}
			return deleted;
		} catch (DataAccessException dae) {
			logger.error("Failed to delete party: " + dae.getMessage(), dae);
			request.setAttribute("party_delete_error", dae.getUserMessage());
		} catch (Exception e) {
			logger.error("Unexpected error while deleting party", e);
			request.setAttribute("party_delete_error", "An unexpected error occurred. Please try again.");
		}
		return false;
	}

}
