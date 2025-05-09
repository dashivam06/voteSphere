package com.voteSphere.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.voteSphere.config.DBConnectionManager;
import com.voteSphere.exception.DataAccessException;
import com.voteSphere.exception.DatabaseException;
import com.voteSphere.model.Candidate;

public class CandidateDao {

	private static final Logger logger = LogManager.getLogger(CandidateDao.class);

	public static boolean createCandidate(Candidate candidate) {
		if (candidate == null) {
			logger.error("Attempt to create null candidate");
			throw new IllegalArgumentException("Candidate cannot be null");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Attempting to create candidate: " + candidate.getFname() + " " + candidate.getLname());
		}

		String sql = "INSERT INTO candidates (first_name, last_name, address, gender, dob, is_independent, highest_education, bio, profile_image, party_id, election_id, manifesto) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = DBConnectionManager.establishConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			if (logger.isTraceEnabled()) {
				logger.trace("Setting parameters for candidate creation.");
			}

			stmt.setString(1, candidate.getFname());
			stmt.setString(2, candidate.getLname());
			stmt.setString(3, candidate.getAddress());
			stmt.setString(4, candidate.getGender());
			stmt.setDate(5, candidate.getDob());
			stmt.setBoolean(6, candidate.getIsIndependent());
			stmt.setString(7, candidate.getHighestEducation());
			stmt.setString(8, candidate.getBio());
			stmt.setString(9, candidate.getProfileImage());

			if (candidate.getPartyId() != null) {
				stmt.setInt(10, candidate.getPartyId());
			} else {
				stmt.setNull(10, Types.INTEGER);
			}

			stmt.setInt(11, candidate.getElectionId());
			stmt.setString(12, candidate.getManifesto());

			int affectedRows = stmt.executeUpdate();

			if (affectedRows == 0) {
				logger.warn("No rows affected when creating candidate: " + candidate.getFname() + " "
						+ candidate.getLname());
				throw new DataAccessException("No rows affected when creating candidate",
						"Failed to create candidate. Please try again.", null);
			}

			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					candidate.setCandidateId(generatedKeys.getInt(1));
					if (logger.isInfoEnabled()) {
						logger.info("Successfully created candidate " + candidate.getFname() + " "
								+ candidate.getLname() + " with ID " + candidate.getCandidateId());
					}
					return true;
				} else {
					logger.error("No ID obtained after creating candidate: " + candidate.getFname() + " "
							+ candidate.getLname());
					throw new DataAccessException("No generated key returned for new candidate",
							"Candidate was created but ID could not be retrieved.", null);
				}
			}

		} catch (DatabaseException e) {
			logger.error("Connection error while creating candidate", e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

		} catch (SQLException e) {
			logger.error("Database error while creating candidate: " + e.getMessage(), e);
			throw new DataAccessException("Database error while creating candidate",
					"Failed to create candidate due to a system error. Please try again later.", e);

		} catch (Exception e) {
			logger.error("Unexpected error while creating candidate", e);
			throw new DataAccessException("Unexpected error while creating candidate",
					"An unexpected error occurred. Please contact support.", e);
		}
	}
	
	
	public static boolean updateCandidate(Candidate candidate) {
	    if (candidate == null) {
	        logger.error("Attempt to update null candidate");
	        throw new IllegalArgumentException("Candidate cannot be null");
	    }

	    if (logger.isDebugEnabled()) {
	        logger.debug("Attempting to update candidate: " + candidate.getFname() + " " + candidate.getLname());
	    }

	    String sql = "UPDATE candidates SET first_name = ?, last_name = ?, address = ?, gender = ?, dob = ?, "
	               + "is_independent = ?, highest_education = ?, bio = ?, profile_image = ?, party_id = ?, "
	               + "election_id = ?, manifesto = ? WHERE candidate_id = ?";

	    try (Connection conn = DBConnectionManager.establishConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        if (logger.isTraceEnabled()) {
	            logger.trace("Setting parameters for candidate update.");
	        }

	        stmt.setString(1, candidate.getFname());
	        stmt.setString(2, candidate.getLname());
	        stmt.setString(3, candidate.getAddress());
	        stmt.setString(4, candidate.getGender());
	        stmt.setDate(5, candidate.getDob());
	        stmt.setBoolean(6, candidate.getIsIndependent());
	        stmt.setString(7, candidate.getHighestEducation());
	        stmt.setString(8, candidate.getBio());
	        stmt.setString(9, candidate.getProfileImage());

	        if (candidate.getPartyId() != null) {
	            stmt.setInt(10, candidate.getPartyId());
	        } else {
	            stmt.setNull(10, Types.INTEGER);
	        }

	        stmt.setInt(11, candidate.getElectionId());
	        stmt.setString(12, candidate.getManifesto());
	        stmt.setInt(13, candidate.getCandidateId());

	        int affectedRows = stmt.executeUpdate();

	        if (affectedRows == 0) {
	            logger.warn("No rows affected when updating candidate: " + candidate.getFname() + " "
	                    + candidate.getLname());
	            throw new DataAccessException("No rows affected when updating candidate",
	                    "Failed to update candidate. Please try again.", null);
	        }

	        if (logger.isInfoEnabled()) {
	            logger.info("Successfully updated candidate " + candidate.getFname() + " "
	                    + candidate.getLname() + " with ID " + candidate.getCandidateId());
	        }

	        return true;

	    } catch (DatabaseException e) {
	        logger.error("Connection error while updating candidate", e);
	        throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

	    } catch (SQLException e) {
	        logger.error("Database error while updating candidate: " + e.getMessage(), e);
	        throw new DataAccessException("Database error while updating candidate",
	                "Failed to update candidate due to a system error. Please try again later.", e);

	    } catch (Exception e) {
	        logger.error("Unexpected error while updating candidate", e);
	        throw new DataAccessException("Unexpected error while updating candidate",
	                "An unexpected error occurred. Please contact support.", e);
	    }
	}


	public static List<Candidate> getAllCandidates() {
		if (logger.isDebugEnabled()) {
			logger.debug("Attempting to retrieve all candidates");
		}

		String sql = "SELECT * FROM candidates";
		List<Candidate> candidates = new ArrayList<>();

		try (Connection conn = DBConnectionManager.establishConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				Candidate candidate = new Candidate(rs.getInt("candidate_id"), rs.getString("first_name"),
						rs.getString("last_name"), rs.getString("address"), rs.getString("gender"), rs.getDate("dob"),
						rs.getBoolean("is_independent"), rs.getString("highest_education"), rs.getString("bio"),
						rs.getString("profile_image"), rs.getInt("party_id"), rs.getInt("election_id"),
						rs.getString("manifesto"));
				candidates.add(candidate);
			}

			if (logger.isInfoEnabled()) {
				logger.info("Successfully retrieved " + candidates.size() + " candidates from the database");
			}

			return candidates;

		} catch (DatabaseException e) {
			logger.error("Connection error while retrieving all candidates", e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

		} catch (SQLException e) {
			logger.error("SQL error while retrieving all candidates: " + e.getMessage(), e);
			throw new DataAccessException("Database error during retrieving all candidates",
					"Failed to load candidates due to a system error. Please try again later.", e);

		} catch (Exception e) {
			logger.error("Unexpected error while retrieving all candidates", e);
			throw new DataAccessException("Unexpected error during retrieving all candidates",
					"An unexpected error occurred. Please contact support.", e);
		}
	}

	public static Candidate findCandidateById(int id) {
		if (logger.isDebugEnabled()) {
			logger.debug("Searching for candidate with ID: " + id);
		}

		String sql = "SELECT * FROM candidates WHERE candidate_id = ?";

		try (Connection conn = DBConnectionManager.establishConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);

			if (logger.isTraceEnabled()) {
				logger.trace("Executing SQL query to find candidate by ID: " + id);
			}

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					Candidate candidate = new Candidate(rs.getInt("candidate_id"), rs.getString("first_name"),
							rs.getString("last_name"), rs.getString("address"), rs.getString("gender"), rs.getDate("dob"),
							rs.getBoolean("is_independent"), rs.getString("highest_education"), rs.getString("bio"),
							rs.getString("profile_image"), rs.getInt("party_id"), rs.getInt("election_id"),
							rs.getString("manifesto"));

					if (logger.isInfoEnabled()) {
						logger.info("Found candidate: " + candidate.getFname() + " " + candidate.getLname() + " (ID: "
								+ candidate.getCandidateId() + ")");
					}
					return candidate;
				} else {
					logger.warn("No candidate found with ID: " + id);
					return null;
				}
			}

		} catch (DatabaseException e) {
			logger.error("Database connection error while fetching candidate by ID: " + id, e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

		} catch (SQLException e) {
			logger.error("SQL error while fetching candidate by ID: " + id + ". Error: " + e.getMessage(), e);
			throw new DataAccessException("Database error while retrieving candidate",
					"Failed to retrieve candidate. Please try again later.", e);

		} catch (Exception e) {
			logger.error("Unexpected error while retrieving candidate by ID", e);
			throw new DataAccessException("Unexpected error during retrieving candidate",
					"An unexpected error occurred. Please contact support.", e);
		}
	}

	public static List<Candidate> getCandidateByParty(int partyId) {
		if (logger.isDebugEnabled()) {
			logger.debug("Retrieving candidates for party ID: " + partyId);
		}

		String sql = "SELECT * FROM candidates WHERE party_id = ?";
		List<Candidate> candidates = new ArrayList<>();

		try (Connection conn = DBConnectionManager.establishConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, partyId);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Candidate candidate = mapResultSetToCandidate(rs);
					candidates.add(candidate);
				}
			}

			if (logger.isInfoEnabled()) {
				logger.info("Found " + candidates.size() + " candidates for party ID " + partyId);
			}

			return candidates;

		} catch (DatabaseException e) {
			logger.error("Database connection error while retrieving candidates for party", e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

		} catch (SQLException e) {
			logger.error("SQL error while retrieving candidates by party: Error code: " + e.getErrorCode()
					+ ", SQL state: " + e.getSQLState(), e);
			throw new DataAccessException("Database error while retrieving candidates by party",
					"Failed to load candidates for party due to a system error.", e);

		} catch (Exception e) {
			logger.error("Unexpected error while retrieving candidates by party", e);
			throw new DataAccessException("Unexpected error while retrieving candidates by party",
					"An unexpected error occurred. Please contact support.", e);
		}
	}

	public static List<Candidate> getCandidateByElection(int electionId) {
		if (logger.isDebugEnabled()) {
			logger.debug("Retrieving candidates for election ID: " + electionId);
		}

		String sql = "SELECT * FROM candidates WHERE election_id = ?";
		List<Candidate> candidates = new ArrayList<>();

		try (Connection conn = DBConnectionManager.establishConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, electionId);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Candidate candidate = mapResultSetToCandidate(rs);
					candidates.add(candidate);
				}
			}

			if (logger.isInfoEnabled()) {
				logger.info("Found " + candidates.size() + " candidates for election ID " + electionId);
			}

			return candidates;

		} catch (DatabaseException e) {
			logger.error("Database connection error while retrieving candidates for election", e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

		} catch (SQLException e) {
			logger.error("SQL error while retrieving candidates by election: Error code: " + e.getErrorCode()
					+ ", SQL state: " + e.getSQLState(), e);
			throw new DataAccessException("Database error while retrieving candidates by election",
					"Failed to load candidates for election due to a system error.", e);

		} catch (Exception e) {
			logger.error("Unexpected error while retrieving candidates by election", e);
			throw new DataAccessException("Unexpected error while retrieving candidates by election",
					"An unexpected error occurred. Please contact support.", e);
		}
	}
	
	
	
	public static boolean deleteCandidate(int candidateId) {
	    if (candidateId <= 0) {
	        logger.error("Invalid candidate ID: " + candidateId);
	        throw new IllegalArgumentException("Invalid candidate ID");
	    }

	    if (logger.isDebugEnabled()) {
	        logger.debug("Attempting to delete candidate with ID: " + candidateId);
	    }

	    String sql = "DELETE FROM candidates WHERE candidate_id = ?";

	    try (Connection conn = DBConnectionManager.establishConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        if (logger.isTraceEnabled()) {
	            logger.trace("Setting parameters for candidate deletion.");
	        }

	        stmt.setInt(1, candidateId);

	        int affectedRows = stmt.executeUpdate();

	        if (affectedRows == 0) {
	            logger.warn("No rows affected when deleting candidate with ID: " + candidateId);
	            throw new DataAccessException("No rows affected when deleting candidate",
	                    "Failed to delete candidate. Please try again.", null);
	        }

	        if (logger.isInfoEnabled()) {
	            logger.info("Successfully deleted candidate with ID: " + candidateId);
	        }

	        return true;

	    } catch (DatabaseException e) {
	        logger.error("Connection error while deleting candidate with ID: " + candidateId, e);
	        throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

	    } catch (SQLException e) {
	        logger.error("Database error while deleting candidate with ID: " + candidateId + ": " + e.getMessage(), e);
	        throw new DataAccessException("Database error while deleting candidate",
	                "Failed to delete candidate due to a system error. Please try again later.", e);

	    } catch (Exception e) {
	        logger.error("Unexpected error while deleting candidate with ID: " + candidateId, e);
	        throw new DataAccessException("Unexpected error while deleting candidate",
	                "An unexpected error occurred. Please contact support.", e);
	    }
	}


	private static Candidate mapResultSetToCandidate(ResultSet rs) throws SQLException {
		return new Candidate(rs.getInt("candidate_id"), rs.getString("first_name"), rs.getString("last_name"),
				rs.getString("address"), rs.getString("gender"), rs.getDate("dob"), rs.getBoolean("is_independent"),
				rs.getString("highest_education"), rs.getString("bio"), rs.getString("profile_image"),
				rs.getInt("party_id"), rs.getInt("election_id"), rs.getString("manifesto"));

	}
}
