package com.voteSphere.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.voteSphere.controller.ElectionResultServlet;
import com.voteSphere.dao.ElectionDao;
import com.voteSphere.dao.UserDao;
import com.voteSphere.dao.VoteDao;
import com.voteSphere.exception.DataAccessException;
import com.voteSphere.model.User;
import com.voteSphere.model.Vote;
import com.voteSphere.util.ClientInfoUtil;
import com.voteSphere.util.EmailService;
import com.voteSphere.util.ValidationUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class VoteService {

	private static final Logger logger = LogManager.getLogger(VoteService.class);

	public static boolean addVote(HttpServletRequest request, HttpServletResponse response) {

		String userIdString = request.getParameter("user_id");
		String partyIdStr = request.getParameter("party_id");
		String electionIdStr = request.getParameter("election_id");

		logger.debug("Received parameters - user_id: {}, party_id: {}, election_id: {}", userIdString, partyIdStr,
				electionIdStr);

		Integer userId = null;
		Integer partyId = null;
		Integer electionId = null;
		boolean hasErrors = false;

		// User ID validation
		if (ValidationUtil.isNullOrEmpty(userIdString)) {
			logger.warn("Validation failed: User ID is empty");
			request.setAttribute("user_id_error", "User ID is required.");
			hasErrors = true;
		} else {
			try {
				userId = Integer.parseInt(userIdString);
				if (userId <= 0) {
					logger.warn("Validation failed: User ID must be positive. Provided value: {}", userIdString);
					request.setAttribute("user_id_error", "User ID must be a positive number.");
					hasErrors = true;
				} else {
					logger.debug("Valid User ID: {}", userId);
				}
			} catch (NumberFormatException e) {
				logger.warn("Validation failed: Invalid User ID format. Provided value: {}", userIdString);
				request.setAttribute("user_id_error", "Invalid User ID format.");
				hasErrors = true;
			}
		}

		// Party ID validation
		if (ValidationUtil.isNullOrEmpty(partyIdStr)) {
			logger.warn("Validation failed: Party ID is empty");
			request.setAttribute("party_id_error", "Party ID is required.");
			hasErrors = true;
		} else {
			try {
				partyId = Integer.parseInt(partyIdStr);
				if (partyId <= 0) {
					logger.warn("Validation failed: Party ID must be positive. Provided value: {}", partyIdStr);
					request.setAttribute("party_id_error", "Party ID must be a positive number.");
					hasErrors = true;
				} else {
					logger.debug("Valid Party ID: {}", partyId);
				}
			} catch (NumberFormatException e) {
				logger.warn("Validation failed: Invalid Party ID format. Provided value: {}", partyIdStr);
				request.setAttribute("party_id_error", "Invalid Party ID format.");
				hasErrors = true;
			}
		}

		// Election ID validation
		if (ValidationUtil.isNullOrEmpty(electionIdStr)) {
			logger.warn("Validation failed: Election ID is empty");
			request.setAttribute("election_id_error", "Election ID is required.");
			hasErrors = true;
		} else {
			try {
				electionId = Integer.parseInt(electionIdStr);
				if (electionId <= 0) {
					logger.warn("Validation failed: Election ID must be positive. Provided value: {}", electionIdStr);
					request.setAttribute("election_id_error", "Election ID must be a positive number.");
					hasErrors = true;
				} else {
					logger.debug("Valid Election ID: {}", electionId);
				}
			} catch (NumberFormatException e) {
				logger.warn("Validation failed: Invalid Election ID format. Provided value: {}", electionIdStr);
				request.setAttribute("election_id_error", "Invalid Election ID format.");
				hasErrors = true;
			}
		}

		if (hasErrors) {
			logger.info("Request validation failed with {} errors",
					(ValidationUtil.isNullOrEmpty(userIdString) ? 1 : 0)
							+ (ValidationUtil.isNullOrEmpty(partyIdStr) ? 1 : 0)
							+ (ValidationUtil.isNullOrEmpty(electionIdStr) ? 1 : 0));
			return false;
		}

		logger.debug("All parameters validated successfully");
		try {

			boolean hasUserAlreadyVoted = VoteDao.hasUserAlreadyVoted(userId, electionId);

			if (!hasUserAlreadyVoted) {

				Vote newVote = new Vote(userId, electionId, partyId, ClientInfoUtil.getClientIpAddress(request));
				boolean votePushed = VoteDao.createVote(newVote);
				User user = UserDao.getUserById(userId);
//				Election election = ElectionDao.findElectionById(electionId);
				String electionName = ElectionDao.getElectionNameById(electionId);

				String time = ValidationUtil.convertTimeStampToHrAndMinsOnly(newVote.getVotedAt());
		        
				if (votePushed) {
					 String baseUrl = EmailService.getBaseUrl(request);
			         EmailService.sendVoteSubmissionResponseAsync(request.getServletContext(),
			        		 		baseUrl, 
			        		 		user.getVoterId(), 
			        		 		user.getFirstName(),
			        		 		user.getEmail(), 
			        		 		electionName,time,
							 		String.valueOf(newVote.getVoteId()));
			       
					ElectionResultServlet.notifyVoteAdded(electionId);
					return votePushed;
				}

			}
			return false;

		} catch (DataAccessException dae) {
			logger.error("Failed to create vote: " + dae.getMessage(), dae);
			request.setAttribute("vote_creation_error", dae.getUserMessage());
			return false;
		} catch (Exception e) {
			logger.error("Unexpected error occurred while creating vote", e);
			request.setAttribute("vote_creation_error", "An unexpected error occurred. Please try again.");
			return false;
		}
	}

	public static List<Vote> getVotesByElectionId(Integer electionId) {
		if (electionId == null || electionId <= 0) {
			return Collections.emptyList();
		}

		try {
			return VoteDao.findVoteHistoryForElection(electionId);
		} catch (DataAccessException dae) {
			logger.error("Data access error while fetching votes by election ID: " + dae.getMessage(), dae);
			return Collections.emptyList();
		} catch (Exception e) {
			logger.error("Unexpected error while fetching votes by election ID: " + e.getMessage(), e);
			return Collections.emptyList();
		}
	}

	public static Map<Integer, Integer> getAllPartyVotesInElection(HttpServletRequest request,
			HttpServletResponse response, Integer electionId) {
		if (electionId == null || electionId <= 0) {
			request.setAttribute("electionId_error", "Invalid Election ID.");
			return Collections.emptyMap();
		}

		try {
			return VoteDao.getAllPartyVotesInElection(electionId);
		} catch (DataAccessException dae) {
			logger.error("Failed to retrieve party vote counts: " + dae.getMessage(), dae);
			request.setAttribute("party_vote_count_error", dae.getUserMessage());
			return Collections.emptyMap();
		} catch (Exception e) {
			logger.error("Unexpected error while retrieving party vote counts", e);
			request.setAttribute("party_vote_count_error", "An unexpected error occurred. Please try again later.");
			return Collections.emptyMap();
		}
	}


	/**
	 * Gets all party names and their respective vote counts for a specific election
	 * @param electionId The ID of the election
	 * @return Map of party names to vote counts
	 */
	public static Map<String, Integer> getAllPartyNamesAndRespectiveVoteCountInElection(int electionId) {
		if (logger.isDebugEnabled()) {
			logger.debug("Getting party names and vote counts for electionId: {}", electionId);
		}

		try {
			Map<String, Integer> result = VoteDao.getAllPartyNamesAndRespectiveVoteCountInElection(electionId);

			if (logger.isInfoEnabled()) {
				logger.info("Successfully retrieved vote counts for {} parties in election {}",
						result.size(), electionId);
			}

			return result;

		} catch (DataAccessException dae) {
			logger.error("Data access error while getting party vote counts for election {}: {}",
					electionId, dae.getMessage(), dae);
			throw new DataAccessException(dae.getMessage(),
					"Failed to retrieve vote distribution. Please try again later.", dae);

		} catch (Exception e) {
			logger.error("Unexpected error while getting party vote counts for election {}: {}",
					electionId, e.getMessage(), e);
			throw new DataAccessException("Unexpected error retrieving vote distribution",
					"An unexpected error occurred. Please contact support.", e);
		}
	}

	public static Vote getVoteById(HttpServletRequest request, HttpServletResponse response, Integer voteId) {
		if (voteId == null || voteId <= 0) {
			request.setAttribute("voteId_error", "Valid vote ID is required.");
			return null;
		}

		try {
			Vote vote = VoteDao.findVoteById(voteId);
			if (vote == null) {
				request.setAttribute("vote_not_found", "No vote found with the given ID.");
			}
			return vote;
		} catch (DataAccessException dae) {
			logger.error("Failed to retrieve vote by ID: " + dae.getMessage(), dae);
			request.setAttribute("vote_retrieve_error", dae.getUserMessage());
		} catch (Exception e) {
			logger.error("Unexpected error retrieving vote by ID", e);
			request.setAttribute("vote_retrieve_error", "An unexpected error occurred. Please try again.");
		}
		return null;
	}

	public static List<Vote> getAllVotes() {
		try {
			return VoteDao.getAllVotes();
		} catch (DataAccessException dae) {
			logger.error("Data access error while fetching all votes: " + dae.getMessage(), dae);
			return Collections.emptyList();
		} catch (Exception e) {
			logger.error("Unexpected error while fetching all votes: " + e.getMessage(), e);
			return Collections.emptyList();
		}
	}

	public static boolean updateVote(HttpServletRequest request, HttpServletResponse response, Integer voteId) {
		boolean hasErrors = false;

		if (voteId == null || voteId <= 0) {
			request.setAttribute("voteId_error", "Invalid vote ID.");
			return false;
		}

		String voterIdString = request.getParameter("voter_id");
		String partyIdStr = request.getParameter("partyId");
		String electionIdStr = request.getParameter("electionId");

		Integer partyId = null;
		Integer userId = null;
		Integer electionId = null;

		// Field validations
		if (ValidationUtil.isNullOrEmpty(voterIdString)) {
			request.setAttribute("voter_id_error", "Voter Id is required.");
			hasErrors = true;
		} else {
			try {
				userId = Integer.parseInt(voterIdString);
				if (userId <= 0) {
					request.setAttribute("user_id_error", "User ID must be a positive number.");
					hasErrors = true;
				}
			} catch (NumberFormatException e) {
				request.setAttribute("user_id_error", "Invalid User ID format.");
				hasErrors = true;
			}
		}

		if (ValidationUtil.isNullOrEmpty(partyIdStr)) {
			request.setAttribute("partyId_error", "Party ID is required.");
			hasErrors = true;
		} else {
			try {
				partyId = Integer.parseInt(partyIdStr);
				if (partyId <= 0) {
					request.setAttribute("partyId_error", "Party ID must be positive.");
					hasErrors = true;
				}
			} catch (NumberFormatException e) {
				request.setAttribute("partyId_error", "Invalid Party ID format.");
				hasErrors = true;
			}
		}

		if (ValidationUtil.isNullOrEmpty(electionIdStr)) {
			request.setAttribute("electionId_error", "Election ID is required.");
			hasErrors = true;
		} else {
			try {
				electionId = Integer.parseInt(electionIdStr);
				if (electionId <= 0) {
					request.setAttribute("electionId_error", "Election ID must be positive.");
					hasErrors = true;
				}
			} catch (NumberFormatException e) {
				request.setAttribute("electionId_error", "Invalid Election ID format.");
				hasErrors = true;
			}
		}

		if (hasErrors) {
			return false;
		}

		try {
			Vote updatedVote = new Vote(voteId, userId, electionId, partyId,
					ClientInfoUtil.getClientIpAddress(request));
			boolean updated = VoteDao.updateVote(updatedVote);

			if (!updated) {
				request.setAttribute("vote_update_error", "No vote was updated. Possibly invalid ID.");
			}

			return updated;
		} catch (DataAccessException dae) {
			logger.error("Failed to update vote: " + dae.getMessage(), dae);
			request.setAttribute("vote_update_error", dae.getUserMessage());
		} catch (Exception e) {
			logger.error("Unexpected error occurred while updating vote", e);
			request.setAttribute("vote_update_error", "An unexpected error occurred. Please try again.");
		}

		return false;
	}

	public static boolean deleteVote(HttpServletRequest request, HttpServletResponse response, int voteId) {
		if (voteId <= 0) {
			request.setAttribute("voteId_error", "Invalid vote ID.");
			return false;
		}

		try {
			boolean deleted = VoteDao.deleteVote(voteId);
			if (!deleted) {
				request.setAttribute("vote_delete_error", "No vote found with the given ID to delete.");
			}
			return deleted;
		} catch (DataAccessException dae) {
			logger.error("Failed to delete vote: " + dae.getMessage(), dae);
			request.setAttribute("vote_delete_error", dae.getUserMessage());
		} catch (Exception e) {
			logger.error("Unexpected error while deleting vote", e);
			request.setAttribute("vote_delete_error", "An unexpected error occurred. Please try again.");
		}
		return false;
	}


	public static Integer getTotalVotesInElection(int electionId) {
		if (electionId <= 0) {
			return 0;
		}

		try {
			return VoteDao.getTotalVotesInElection(electionId);
		} catch (DataAccessException dae) {
			logger.error("Failed to delete vote: " + dae.getMessage(), dae);
		} catch (Exception e) {
			logger.error("Unexpected error while deleting vote", e);
		}
		return 0;
	}

}
