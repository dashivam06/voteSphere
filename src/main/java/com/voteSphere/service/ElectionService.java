package com.voteSphere.service;

import java.sql.Date;
import java.sql.Time;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.voteSphere.dao.ElectionDao;
import com.voteSphere.exception.DataAccessException;
import com.voteSphere.model.Candidate;
import com.voteSphere.model.Election;
import com.voteSphere.model.ElectionResult;
import com.voteSphere.util.ImageUploadHandler;
import com.voteSphere.util.ValidationUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ElectionService {

	private static final Logger logger = LogManager.getLogger(ElectionService.class);

	public static boolean addElection(HttpServletRequest request, HttpServletResponse response) {
		boolean hasErrors = false;

		String name = request.getParameter("name");
		String type = request.getParameter("type");
		String dateStr = request.getParameter("date");
		String startTimeStr = request.getParameter("start_time") +":00";
		String endTimeStr = request.getParameter("end_time")+":00";


		String appRealPath = request.getServletContext().getRealPath("");
		long maxImageSize = 2 * 1024 * 1024;

		// Process cover image
		String coverImageName = ImageUploadHandler.processImageUpload(request, "cover_image", "cover_image_error",
				"election-cover-image", appRealPath, maxImageSize);
		if (coverImageName == null) {
			hasErrors = true;
			logger.warn("Validation failed: Cover image is missing.");
		}

		// Field validations
		if (ValidationUtil.isNullOrEmpty(name)) {
			logger.warn("Validation failed: Election name is empty.");
			request.setAttribute("name_error", "Election name is required.");
			hasErrors = true;
		}

		if (ValidationUtil.isNullOrEmpty(type)) {
			logger.warn("Validation failed: Election type is empty.");
			request.setAttribute("type_error", "Election type is required.");
			hasErrors = true;
		}

		Date date = null;
		if (ValidationUtil.isNullOrEmpty(dateStr)) {
			logger.warn("Validation failed: Election date is empty.");
			request.setAttribute("date_error", "Election date is required.");
			hasErrors = true;
		} else {
			try {
				date = Date.valueOf(dateStr);
			} catch (IllegalArgumentException e) {
				logger.warn("Validation failed: Invalid date format for election date. Provided value: {}", dateStr);
				request.setAttribute("date_error", "Invalid date format. Expected format: yyyy-MM-dd.");
				hasErrors = true;
			}
		}

		Time startTime = null;
		if (ValidationUtil.isNullOrEmpty(startTimeStr)) {
			logger.warn("Validation failed: Start time is empty.");
			request.setAttribute("start_time_error", "Start time is required.");
			hasErrors = true;
		} else {
			try {
				startTime = Time.valueOf(startTimeStr);
			} catch (IllegalArgumentException e) {
				logger.warn("Validation failed: Invalid start time format for election start time. Provided value: {}",
						startTimeStr);
				request.setAttribute("start_time_error", "Invalid start time format. Expected format: HH:mm:ss.");
				hasErrors = true;
			}
		}

		Time endTime = null;
		if (ValidationUtil.isNullOrEmpty(endTimeStr)) {
			logger.warn("Validation failed: End time is empty.");
			request.setAttribute("end_time_error", "End time is required.");
			hasErrors = true;
		} else {
			try {
				endTime = Time.valueOf(endTimeStr);
			} catch (IllegalArgumentException e) {
				logger.warn("Validation failed: Invalid end time format for election end time. Provided value: {}",
						endTimeStr);
				request.setAttribute("end_time_error", "Invalid end time format. Expected format: HH:mm:ss.");
				hasErrors = true;
			}
		}

		if (hasErrors) {
			logger.info("Election creation aborted due to validation errors.");
			return false;
		}
		try {
			Election newElection = new Election(name, type, coverImageName, date, startTime, endTime);
			return ElectionDao.createElection(newElection);
		} catch (DataAccessException dae) {
			logger.error("Failed to create election: " + dae.getMessage(), dae);
			request.setAttribute("election_creation_error", dae.getUserMessage());
			return false;
		} catch (Exception e) {
			logger.error("Unexpected error occurred while creating election", e);
			request.setAttribute("election_creation_error", "An unexpected error occurred. Please try again.");
			return false;
		}
	}

	public static Election getElectionById(HttpServletRequest request, HttpServletResponse response, Integer id) {
		if (id == null || id <= 0) {
			request.setAttribute("id_error", "Valid election ID is required.");
			return null;
		}

		try {
			Election election = ElectionDao.findElectionById(id);
			if (election == null) {
				request.setAttribute("election_not_found", "No election found with the given ID.");
			}
			return election;
		} catch (DataAccessException dae) {
			logger.error("Failed to retrieve election by ID: " + dae.getMessage(), dae);
			request.setAttribute("election_retrieve_error", dae.getUserMessage());
		} catch (Exception e) {
			logger.error("Unexpected error retrieving election by ID", e);
			request.setAttribute("election_retrieve_error", "An unexpected error occurred. Please try again.");
		}
		return null;
	}

	public static List<Election> getAllElections() {
		try {
			return ElectionDao.findAllElections();
		} catch (DataAccessException dae) {
			logger.error("Data access error while fetching all elections: " + dae.getMessage(), dae);
			return Collections.emptyList();
		} catch (Exception e) {
			logger.error("Unexpected error while fetching all elections: " + e.getMessage(), e);
			return Collections.emptyList();
		}
	}

	public static boolean updateElection(HttpServletRequest request, HttpServletResponse response, Integer electionId) {

		if (electionId == null || electionId <= 0) {
			request.setAttribute("election_id_error", "Invalid election ID.");
			return false;
		}

		String name = request.getParameter("name");
		String type = request.getParameter("type");
		String dateStr = request.getParameter("date");
		String startTimeStr = request.getParameter("start_time");
		String endTimeStr = request.getParameter("end_time");

		boolean hasErrors = false;

// Election Name validation
		if (ValidationUtil.isNullOrEmpty(name)) {
			logger.warn("Validation failed: Election name is empty.");
			request.setAttribute("name_error", "Election name is required.");
			hasErrors = true;
		}

// Election Type validation
		if (ValidationUtil.isNullOrEmpty(type)) {
			logger.warn("Validation failed: Election type is empty.");
			request.setAttribute("type_error", "Election type is required.");
			hasErrors = true;
		}

// Election Date validation
		Date date = null;
		if (ValidationUtil.isNullOrEmpty(dateStr)) {
			logger.warn("Validation failed: Election date is empty.");
			request.setAttribute("date_error", "Election date is required.");
			hasErrors = true;
		} else {
			try {
				date = Date.valueOf(dateStr);
			} catch (IllegalArgumentException e) {
				logger.warn("Validation failed: Invalid date format for election date. Provided value: {}", dateStr);
				request.setAttribute("date_error", "Invalid date format. Expected format: yyyy-MM-dd.");
				hasErrors = true;
			}
		}

// Start Time validation
		Time startTime = null;
		if (ValidationUtil.isNullOrEmpty(startTimeStr)) {
			logger.warn("Validation failed: Start time is empty.");
			request.setAttribute("start_time_error", "Start time is required.");
			hasErrors = true;
		} else {
			try {
				startTime = Time.valueOf(startTimeStr);
			} catch (IllegalArgumentException e) {
				logger.warn("Validation failed: Invalid start time format for election start time. Provided value: {}",
						startTimeStr);
				request.setAttribute("start_time_error", "Invalid start time format. Expected format: HH:mm:ss.");
				hasErrors = true;
			}
		}

// End Time validation
		Time endTime = null;
		if (ValidationUtil.isNullOrEmpty(endTimeStr)) {
			logger.warn("Validation failed: End time is empty.");
			request.setAttribute("end_time_error", "End time is required.");
			hasErrors = true;
		} else {
			try {
				endTime = Time.valueOf(endTimeStr);
			} catch (IllegalArgumentException e) {
				logger.warn("Validation failed: Invalid end time format for election end time. Provided value: {}",
						endTimeStr);
				request.setAttribute("end_time_error", "Invalid end time format. Expected format: HH:mm:ss.");
				hasErrors = true;
			}
		}
		
		
		 // Set up paths and max file size for image uploads
	    String appRealPath = request.getServletContext().getRealPath("");
	    long maxImageSize = 2 * 1024 * 1024; // 2MB

	    // Process profile image upload (if present)
	    String coverImage = ImageUploadHandler.processImageUpload(request, "cover_image", "election_cover_image_error",
	            "election_cover_image", appRealPath, maxImageSize);


// If there are validation errors, return false
		if (hasErrors) {
			logger.info("Election creation aborted due to validation errors.");
			return false;
		}
		try {
			Election updatedElection = new Election(electionId, name, type, coverImage, date, startTime, endTime);
			boolean updated = ElectionDao.updateElection(updatedElection);

			if (!updated) {
				request.setAttribute("election_update_error", "No election was updated. Possibly invalid ID.");
			}

			return updated;
		} catch (DataAccessException dae) {
			logger.error("Failed to update election: " + dae.getMessage(), dae);
			request.setAttribute("election_update_error", dae.getUserMessage());
		} catch (Exception e) {
			logger.error("Unexpected error occurred while updating election", e);
			request.setAttribute("election_update_error", "An unexpected error occurred. Please try again.");
		}

		return false;
	}

	public static boolean deleteElection(HttpServletRequest request, HttpServletResponse response, int electionId) {
		if (electionId <= 0) {
			request.setAttribute("election_id_error", "Invalid election ID.");
			return false;
		}

		try {
			boolean deleted = ElectionDao.deleteElection(electionId);
			if (!deleted) {
				request.setAttribute("election_delete_error", "No election found with the given ID to delete.");
			}
			return deleted;
		} catch (DataAccessException dae) {
			logger.error("Failed to delete election: " + dae.getMessage(), dae);
			request.setAttribute("election_delete_error", dae.getUserMessage());
		} catch (Exception e) {
			logger.error("Unexpected error while deleting election", e);
			request.setAttribute("election_delete_error", "An unexpected error occurred. Please try again.");
		}
		return false;
	}
	
	
	/**
	 * Gets election results with calculated percentages
	 */
	public static List<ElectionResult> getElectionResults(HttpServletRequest request, HttpServletResponse response, int electionId) {
	    try {
	        List<ElectionResult> results = ElectionDao.getElectionResults(electionId);
	        int totalVotes = ElectionDao.getTotalVotes(electionId);
	        
	        // Calculate percentages
	        for (ElectionResult result : results) {
	            result.calculatePercentage(totalVotes);
	        }
	        
	        return results;
	    } catch (DataAccessException dae) {
	        logger.error("Failed to get election results: " + dae.getMessage(), dae);
	        request.setAttribute("results_error", dae.getUserMessage());
	    } catch (Exception e) {
	        logger.error("Unexpected error while getting election results", e);
	        request.setAttribute("results_error", "An unexpected error occurred. Please try again.");
	    }
	    return Collections.emptyList();
	}

	
	/**
	 * Gets election results with calculated percentages
	 */
	public static List<ElectionResult> getElectionResults( int electionId) {
	    try {
	        List<ElectionResult> results = ElectionDao.getElectionResults(electionId);
	        int totalVotes = ElectionDao.getTotalVotes(electionId);
	        
	        // Calculate percentages
	        for (ElectionResult result : results) {
	            result.calculatePercentage(totalVotes);
	        }
	        
	        return results;
	    } catch (DataAccessException dae) {
	        logger.error("Failed to get election results: " + dae.getMessage(), dae);
	    } catch (Exception e) {
	        logger.error("Unexpected error while getting election results", e);
	    }
	    return Collections.emptyList();
	}
	
	public static List<Integer> getActiveElectionIds() {
        try {
            // Implement your database query to get active election IDs
        	
            return ElectionDao.getAllOngoingElectionIds();
        } catch (Exception e) {
            logger.error("Failed to fetch active election IDs: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }
 
	
	
	
	/**
	 * Gets total votes for an election
	 */
	public static int getTotalVotes(HttpServletRequest request, HttpServletResponse response, int electionId) {
	    try {
	        return ElectionDao.getTotalVotes(electionId);
	    } catch (DataAccessException dae) {
	        logger.error("Failed to get total votes: " + dae.getMessage(), dae);
	        request.setAttribute("votes_error", dae.getUserMessage());
	    } catch (Exception e) {
	        logger.error("Unexpected error while getting total votes", e);
	        request.setAttribute("votes_error", "An unexpected error occurred. Please try again.");
	    }
	    return 0;
	}
	
	
	/**
	 * Gets all currently running elections (where current date/time is within election period)
	 */
	public static List<Election> getRunningElections() {
	    try {
	        logger.debug("Fetching all currently running elections");
	        List<Election> runningElections = ElectionDao.getRunningElections();
	        logger.info("Successfully retrieved {} running elections", runningElections.size());
	        return runningElections;
	    } catch (DataAccessException dae) {
	        logger.error("Failed to retrieve running elections: {}", dae.getMessage(), dae);
	        return Collections.emptyList();
	    } catch (Exception e) {
	        logger.error("Unexpected error while fetching running elections", e);
	        return Collections.emptyList();
	    }
	}

	/**
	 * Gets candidates for a specific election
	 */
	public static List<Candidate> getCandidatesForElection(int electionId) {
	    try {
	        logger.debug("Fetching candidates for election ID: {}", electionId);
	        List<Candidate> candidates = ElectionDao.getCandidatesByElectionId(electionId);
	        logger.info("Found {} candidates for election ID: {}", candidates.size(), electionId);
	        return candidates;
	    } catch (DataAccessException dae) {
	        logger.error("Failed to retrieve candidates for election {}: {}", electionId, dae.getMessage(), dae);
	        return Collections.emptyList();
	    } catch (Exception e) {
	        logger.error("Unexpected error while fetching candidates for election {}", electionId, e);
	        return Collections.emptyList();
	    }
	}

	/**
	 * Gets election by ID with request/response handling
	 */
	public static Election getElectionById(int electionId) {
	    try {
	        logger.debug("Fetching election with ID: {}", electionId);
	        Election election = ElectionDao.findElectionById(electionId);
	        if (election == null) {
	            logger.warn("No election found with ID: {}", electionId);
	        } else {
	            logger.info("Successfully retrieved election ID: {}", electionId);
	        }
	        return election;
	    } catch (DataAccessException dae) {
	        logger.error("Database error fetching election {}: {}", electionId, dae.getMessage(), dae);
	        return null;
	    } catch (Exception e) {
	        logger.error("Unexpected error fetching election {}", electionId, e);
	        return null;
	    }
	}
}
