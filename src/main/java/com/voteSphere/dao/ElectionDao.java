package com.voteSphere.dao;

import java.sql.Connection;
import java.sql.Date;
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
import com.voteSphere.model.Candidate;
import com.voteSphere.model.Election;
import com.voteSphere.model.ElectionResult;
public class ElectionDao {

	private static final Logger logger = LogManager.getLogger(ElectionDao.class);

	public static boolean createElection(Election election) {

		if (election == null) {
			logger.error("Attempt to create null election");
			throw new IllegalArgumentException("Election cannot be null");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Attempting to create election: " + election.getName());
		}

		String sql = "INSERT INTO elections (name, type, cover_image, date, start_time, end_time) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection conn = DBConnectionManager.establishConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			if (logger.isTraceEnabled()) {
				logger.trace("Setting parameters for election creation: name=" + election.getName() + ", type="
						+ election.getType() + ", date=" + election.getDate() + ", startTime=" + election.getStartTime()
						+ ", endTime=" + election.getEndTime());
			}

			stmt.setString(1, election.getName());
			stmt.setString(2, election.getType());
			stmt.setString(3, election.getCoverImage());
			stmt.setDate(4, election.getDate());
			stmt.setTime(5, election.getStartTime());
			stmt.setTime(6, election.getEndTime());

			int affectedRows = stmt.executeUpdate();

			if (affectedRows == 0) {
				logger.warn("No rows affected when creating election: " + election.getName());
				throw new DataAccessException("No rows affected when creating election",
						"Failed to create election. Please try again.", null);
			}

			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					election.setElectionId(generatedKeys.getInt(1));
					if (logger.isInfoEnabled()) {
						logger.info("Successfully created election " + election.getName() + " with ID "
								+ election.getElectionId());
					}
					return true;
				} else {
					logger.error("No ID obtained after creating election: " + election.getName());
					throw new DataAccessException("No generated key returned for new election",
							"Election was created but we couldn't retrieve its ID. Please contact support.", null);
				}
			}
		} catch (DatabaseException e) {
			logger.error("Connection error while creating election", e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

		} catch (SQLException e) {

			if ("23000".equals(e.getSQLState())) {
				logger.warn(
						"Duplicate election creation attempted: " + election.getName() + ". Error: " + e.getMessage());
				throw new DataAccessException("Duplicate election creation attempted: " + election.getName(),
						"An election with this name already exists. Please choose a different name.", e);
			}

			logger.error("Database error while creating election: " + election.getName() + ". Error code: "
					+ e.getErrorCode() + ", SQL state: " + e.getSQLState(), e);

			throw new DataAccessException("Database error while creating election: " + e.getMessage(),
					"Failed to create election due to system error. Please try again later.", e);

		} catch (Exception e) {
			logger.error("Unexpected error while creating election: " + election.getName(), e);
			throw new DataAccessException("Unexpected error while creating election",
					"An unexpected error occurred. Please contact support.", e);
		}
	}
	
	
	public static String getElectionNameById(int electionId) {
	    if (logger.isDebugEnabled()) {
	        logger.debug("Attempting to retrieve election name for ID: " + electionId);
	    }

	    String sql = "SELECT name FROM elections WHERE election_id = ?";

	    try (Connection conn = DBConnectionManager.establishConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, electionId);

	        if (logger.isTraceEnabled()) {
	            logger.trace("Executing SQL query to retrieve name for election ID: " + electionId);
	        }

	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                String name = rs.getString("name");
	                if (logger.isInfoEnabled()) {
	                    logger.info("Retrieved election name: " + name + " for ID: " + electionId);
	                }
	                return name;
	            } else {
	                logger.warn("No election found with ID: " + electionId);
	                return null;
	            }
	        }

	    } catch (DatabaseException e) {
	        logger.error("Database connection error while retrieving election name for ID: " + electionId, e);
	        throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

	    } catch (SQLException e) {
	        logger.error("SQL error while retrieving election name for ID: " + electionId +
	                     ". Error code: " + e.getErrorCode() + ", SQL state: " + e.getSQLState(), e);
	        throw new DataAccessException("Database error while retrieving election name",
	                "Failed to retrieve election name due to system error. Please try again later.", e);

	    } catch (Exception e) {
	        logger.error("Unexpected error while retrieving election name for ID: " + electionId, e);
	        throw new DataAccessException("Unexpected error while retrieving election name",
	                "An unexpected error occurred. Please contact support.", e);
	    }
	}


	public static boolean updateElection(Election election) {
		if (election == null) {
			logger.error("Attempt to update null election");
			throw new IllegalArgumentException("Election cannot be null");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Attempting to update election: " + election.getName());
		}

		String query = "UPDATE elections SET name=?, type=?, cover_image=?, date=?, start_time=?, end_time=? WHERE election_id=?";
		try (Connection conn = DBConnectionManager.establishConnection();
				PreparedStatement stmt = conn.prepareStatement(query)) {

			if (logger.isTraceEnabled()) {
				logger.trace("Setting parameters for updating election: " + election);
			}

			stmt.setString(1, election.getName());
			stmt.setString(2, election.getType());
			stmt.setString(3, election.getCoverImage());
			stmt.setDate(4, election.getDate());
			stmt.setTime(5, election.getStartTime());
			stmt.setTime(6, election.getEndTime());
			stmt.setInt(7, election.getElectionId());

			int affectedRows = stmt.executeUpdate();

			if (affectedRows == 0) {
				logger.warn("No election found to update with ID: " + election.getElectionId());
				return false;
			}

			if (logger.isInfoEnabled()) {
				logger.info("Successfully updated election: " + election.getName() + " (ID: " + election.getElectionId()
						+ ")");
			}
			return true;

		} catch (DatabaseException e) {
			logger.error("Database connection error during election update", e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

		} catch (SQLException e) {
			logger.error("SQL error during election update. Error code: " + e.getErrorCode() + ", SQL state: "
					+ e.getSQLState(), e);
			throw new DataAccessException("Database error while updating election: " + e.getMessage(),
					"Failed to update election. Please try again later.", e);

		} catch (Exception e) {
			logger.error("Unexpected error while updating election: " + election.getName(), e);
			throw new DataAccessException("Unexpected error while updating election",
					"An unexpected error occurred. Please contact support.", e);
		}
	}

	public static boolean deleteElection(int id) {
		if (logger.isDebugEnabled()) {
			logger.debug("Attempting to delete election with ID: " + id);
		}

		String query = "DELETE FROM elections WHERE election_id = ?";
		try (Connection conn = DBConnectionManager.establishConnection();
				PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setInt(1, id);
			int affectedRows = stmt.executeUpdate();

			if (affectedRows == 0) {
				logger.warn("No election found to delete with ID: " + id);
				return false;
			}

			if (logger.isInfoEnabled()) {
				logger.info("Successfully deleted election with ID: " + id);
			}
			return true;

		} catch (DatabaseException e) {
			logger.error("Database connection error during election deletion", e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

		} catch (SQLException e) {
			logger.error("SQL error during election deletion. Error code: " + e.getErrorCode() + ", SQL state: "
					+ e.getSQLState(), e);
			throw new DataAccessException("Database error while deleting election: " + e.getMessage(),
					"Failed to delete election. Please try again later.", e);

		} catch (Exception e) {
			logger.error("Unexpected error while deleting election with ID: " + id, e);
			throw new DataAccessException("Unexpected error while deleting election",
					"An unexpected error occurred. Please contact support.", e);
		}
	}

	public static List<Election> getAllOngoingElections() {
		List<Election> elections = new ArrayList<>();
		String sql = "SELECT * FROM elections WHERE date = CURDATE() AND start_time <= CURTIME() AND end_time >= CURTIME()";

		if (logger.isDebugEnabled()) {
			logger.debug("Fetching all ongoing elections");
		}

		try (Connection conn = DBConnectionManager.establishConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Election election = new Election();
				election.setElectionId(rs.getInt("election_id"));
				election.setName(rs.getString("name"));
				election.setType(rs.getString("type"));
				election.setCoverImage(rs.getString("cover_image"));
				election.setDate(rs.getDate("date"));
				election.setStartTime(rs.getTime("start_time"));
				election.setEndTime(rs.getTime("end_time"));
				elections.add(election);
			}

			if (logger.isInfoEnabled()) {
				logger.info("Found " + elections.size() + " ongoing elections.");
			}

		} catch (DatabaseException e) {
			logger.error("Database connection error while fetching ongoing elections", e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

		} catch (SQLException e) {
			logger.error("SQL error while fetching ongoing elections. Error code: " + e.getErrorCode() + ", SQL state: "
					+ e.getSQLState(), e);
			throw new DataAccessException("Database error while fetching elections: " + e.getMessage(),
					"Failed to fetch ongoing elections. Please try again later.", e);

		} catch (Exception e) {
			logger.error("Unexpected error while fetching ongoing elections", e);
			throw new DataAccessException("Unexpected error while fetching elections",
					"An unexpected error occurred. Please contact support.", e);
		}

		return elections;
	}
	
	public static List<Integer> getAllOngoingElectionIds() {
	    List<Integer> electionIds = new ArrayList<>();
	    String sql = "SELECT election_id FROM elections WHERE date = CURDATE() AND start_time <= CURTIME() AND end_time >= CURTIME()";

	    if (logger.isDebugEnabled()) {
	        logger.debug("Fetching all ongoing election IDs");
	    }

	    try (Connection conn = DBConnectionManager.establishConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            electionIds.add(rs.getInt("election_id"));
	        }

	        if (logger.isInfoEnabled()) {
	            logger.info("Found " + electionIds.size() + " ongoing election IDs.");
	        }

	    } catch (DatabaseException e) {
	        logger.error("Database connection error while fetching election IDs", e);
	        throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

	    } catch (SQLException e) {
	        logger.error("SQL error while fetching election IDs. Error code: " + e.getErrorCode() +
	                ", SQL state: " + e.getSQLState(), e);
	        throw new DataAccessException("Database error while fetching election IDs: " + e.getMessage(),
	                "Failed to fetch ongoing election IDs. Please try again later.", e);

	    } catch (Exception e) {
	        logger.error("Unexpected error while fetching election IDs", e);
	        throw new DataAccessException("Unexpected error while fetching election IDs",
	                "An unexpected error occurred. Please contact support.", e);
	    }

	    return electionIds;
	}

	

	public static List<Election> findUpcomingElections(Date today) {
		List<Election> elections = new ArrayList<>();
		String query = "SELECT * FROM elections WHERE date > ?";

		if (logger.isDebugEnabled()) {
			logger.debug("Fetching upcoming elections after: " + today);
		}

		try (Connection conn = DBConnectionManager.establishConnection();
				PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setDate(1, today);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				elections.add(new Election(rs.getInt("election_id"), rs.getString("name"), rs.getString("type"),
						rs.getString("cover_image"), rs.getDate("date"), rs.getTime("start_time"),
						rs.getTime("end_time")));
			}

			if (logger.isInfoEnabled()) {
				logger.info("Found " + elections.size() + " upcoming elections.");
			}

		} catch (DatabaseException e) {
			logger.error("Database connection error while fetching upcoming elections", e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

		} catch (SQLException e) {
			logger.error("SQL error while fetching upcoming elections. Error code: " + e.getErrorCode()
					+ ", SQL state: " + e.getSQLState(), e);
			throw new DataAccessException("Database error while fetching elections: " + e.getMessage(),
					"Failed to fetch upcoming elections. Please try again later.", e);

		} catch (Exception e) {
			logger.error("Unexpected error while fetching upcoming elections", e);
			throw new DataAccessException("Unexpected error while fetching elections",
					"An unexpected error occurred. Please contact support.", e);
		}

		return elections;
	}

	public static  Election findElectionById(int id) {
		if (logger.isDebugEnabled()) {
			logger.debug("Fetching election with ID: " + id);
		}

		String query = "SELECT * FROM elections WHERE election_id = ?";
		try (Connection conn = DBConnectionManager.establishConnection();
				PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				if (logger.isInfoEnabled()) {
					logger.info("Election found with ID: " + id);
				}
				return new Election(rs.getInt("election_id"), rs.getString("name"), rs.getString("type"),
						rs.getString("cover_image"), rs.getDate("date"), rs.getTime("start_time"),
						rs.getTime("end_time"));
			} else {
				logger.warn("No election found with ID: " + id);
			}

		} catch (DatabaseException e) {
			logger.error("Database connection error while fetching election by ID", e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

		} catch (SQLException e) {
			logger.error("SQL error while fetching election by ID. Error code: " + e.getErrorCode() + ", SQL state: "
					+ e.getSQLState(), e);
			throw new DataAccessException("Database error while fetching election: " + e.getMessage(),
					"Failed to fetch election. Please try again later.", e);

		} catch (Exception e) {
			logger.error("Unexpected error while fetching election by ID", e);
			throw new DataAccessException("Unexpected error while fetching election",
					"An unexpected error occurred. Please contact support.", e);
		}

		return null;
	}

	public static  List<Election> findAllElections() {
		List<Election> elections = new ArrayList<>();
		String query = "SELECT * FROM elections";

		if (logger.isDebugEnabled()) {
			logger.debug("Fetching all elections");
		}

		try (Connection conn = DBConnectionManager.establishConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				elections.add(new Election(rs.getInt("election_id"), rs.getString("name"), rs.getString("type"),
						rs.getString("cover_image"), rs.getDate("date"), rs.getTime("start_time"),
						rs.getTime("end_time")));
			}

			if (logger.isInfoEnabled()) {
				logger.info("Found " + elections.size() + " elections.");
			}

		} catch (DatabaseException e) {
			logger.error("Database connection error while fetching all elections", e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

		} catch (SQLException e) {
			logger.error("SQL error while fetching all elections. Error code: " + e.getErrorCode() + ", SQL state: "
					+ e.getSQLState(), e);
			throw new DataAccessException("Database error while fetching elections: " + e.getMessage(),
					"Failed to fetch elections. Please try again later.", e);

		} catch (Exception e) {
			logger.error("Unexpected error while fetching all elections", e);
			throw new DataAccessException("Unexpected error while fetching elections",
					"An unexpected error occurred. Please contact support.", e);
		}

		return elections;
	}
	
	/**
	 * Gets election results with party vote counts and associated candidates
	 */
	public static List<ElectionResult> getElectionResults(int electionId) {
		
	    logger.debug("Fetching election results for election ID: {}", electionId);

	    String sql = "SELECT c.candidate_id, c.first_name, c.last_name, " +
	                 "p.party_id, p.name as party_name, " +
	                 "COUNT(v.vote_id) as vote_count " +
	                 "FROM candidates c " +
	                 "JOIN parties p ON c.party_id = p.party_id " +
	                 "LEFT JOIN votes v ON p.party_id = v.party_id AND v.election_id = c.election_id " +
	                 "WHERE c.election_id = ? " +
	                 "GROUP BY c.candidate_id, c.first_name, c.last_name, p.party_id, p.name " +
	                 "ORDER BY vote_count DESC";

	    List<ElectionResult> results = new ArrayList<>();
	    
	     // Calculate total votes
        int totalVotes = getTotalVotes(electionId);
        
	    
	    try (Connection conn = DBConnectionManager.establishConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        
	        stmt.setInt(1, electionId);
	        ResultSet rs = stmt.executeQuery();

	        // First get all results to calculate total votes
	        while (rs.next()) {
	            ElectionResult result = new ElectionResult();
	            result.setCandidateId(rs.getInt("candidate_id"));
	            result.setCandidateName(rs.getString("first_name") + " " + rs.getString("last_name"));
	            result.setPartyId(rs.getInt("party_id"));
	            result.setPartyName(rs.getString("party_name"));
	            result.setVoteCount(rs.getInt("vote_count"));
	            results.add(result);
	        }

	   
	        // Calculate percentages for each result
	        for (ElectionResult result : results) {
	            result.calculatePercentage(totalVotes);
	        }

	        logger.info("Found {} candidate results for election ID: {}", results.size(), electionId);
	        return results;

	    } catch (DatabaseException e) {
	        logger.error("Connection error while fetching election results for election {}", electionId, e);
	        throw new DataAccessException("Database connection failed", 
	                                    "Could not connect to election database", e);
	    } catch (SQLException e) {
	        logger.error("SQL error fetching election {} results. Error: {} - SQL State: {}", 
	                   electionId, e.getErrorCode(), e.getSQLState(), e);
	        throw new DataAccessException("Database query failed", 
	                                    "Error retrieving election results", e);
	    } catch (Exception e) {
	        logger.error("Unexpected error fetching results for election {}", electionId, e);
	        throw new DataAccessException("Unexpected error",
	                                    "An unexpected error occurred while fetching results", e);
	    }
	}

	/**
	 * Gets total votes cast in an election
	 */
	public static int getTotalVotes(int electionId) {
	    logger.debug("Calculating total votes for election ID: {}", electionId);

	    String sql = "SELECT COUNT(*) as total FROM votes WHERE election_id = ?";

	    try (Connection conn = DBConnectionManager.establishConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        
	        stmt.setInt(1, electionId);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            int total = rs.getInt("total");
	            logger.info("Election {} has {} total votes", electionId, total);
	            return total;
	        }
	        logger.warn("No votes found for election ID: {}", electionId);
	        return 0;

	    } catch (DatabaseException e) {
	        logger.error("Connection error calculating total votes for election {}", electionId, e);
	        throw new DataAccessException("Database connection failed", 
	                                    "Could not connect to vote database", e);
	    } catch (SQLException e) {
	        logger.error("SQL error counting votes for election {}. Error: {} - SQL State: {}", 
	                   electionId, e.getErrorCode(), e.getSQLState(), e);
	        throw new DataAccessException("Database query failed", 
	                                    "Error counting votes", e);
	    } catch (Exception e) {
	        logger.error("Unexpected error counting votes for election {}", electionId, e);
	        throw new DataAccessException("Unexpected error",
	                                    "An unexpected error occurred while counting votes", e);
	    }
	}
	
	
	/**
	 * Gets all currently running elections (where current date/time is within election period)
	 */
	public static List<Election> getRunningElections() {
	    List<Election> elections = new ArrayList<>();
	    String sql = "SELECT * FROM elections WHERE date = CURDATE() AND start_time <= CURTIME() AND end_time >= CURTIME()";

	    if (logger.isDebugEnabled()) {
	        logger.debug("Fetching currently running elections");
	    }

	    try (Connection conn = DBConnectionManager.establishConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            elections.add(mapResultSetToElection(rs));
	        }
	        logger.info("Found {} currently running elections", elections.size());
	        return elections;

	    } catch (DatabaseException e) {
	        logger.error("Database connection error while fetching running elections", e);
	        throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);
	    } catch (SQLException e) {
	        logger.error("SQL error while fetching running elections. Error code: {}, SQL state: {}", 
	                   e.getErrorCode(), e.getSQLState(), e);
	        throw new DataAccessException("Database error while fetching elections", 
	                                    "Failed to fetch running elections. Please try again later.", e);
	    } catch (Exception e) {
	        logger.error("Unexpected error while fetching running elections", e);
	        throw new DataAccessException("Unexpected error while fetching elections",
	                                    "An unexpected error occurred. Please contact support.", e);
	    }
	}

	/**
	 * Gets candidates for a specific election
	 */
	public static List<Candidate> getCandidatesByElectionId(int electionId) {
	    List<Candidate> candidates = new ArrayList<>();
	    String sql = "SELECT * FROM candidates WHERE election_id = ?";

	    if (logger.isDebugEnabled()) {
	        logger.debug("Fetching candidates for election ID: {}", electionId);
	    }

	    try (Connection conn = DBConnectionManager.establishConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, electionId);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            candidates.add(mapResultSetToCandidate(rs));
	        }
	        logger.info("Found {} candidates for election ID: {}", candidates.size(), electionId);
	        return candidates;

	    } catch (DatabaseException e) {
	        logger.error("Database connection error while fetching candidates", e);
	        throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);
	    } catch (SQLException e) {
	        logger.error("SQL error while fetching candidates. Error code: {}, SQL state: {}", 
	                   e.getErrorCode(), e.getSQLState(), e);
	        throw new DataAccessException("Database error while fetching candidates", 
	                                    "Failed to fetch candidates. Please try again later.", e);
	    } catch (Exception e) {
	        logger.error("Unexpected error while fetching candidates", e);
	        throw new DataAccessException("Unexpected error while fetching candidates",
	                                    "An unexpected error occurred. Please contact support.", e);
	    }
	}

	/**
	 * Helper method to map ResultSet to Election object
	 */
	private static Election mapResultSetToElection(ResultSet rs) throws SQLException {
	    Election election = new Election();
	    election.setElectionId(rs.getInt("election_id"));
	    election.setName(rs.getString("name"));
	    election.setType(rs.getString("type"));
	    election.setCoverImage(rs.getString("cover_image"));
	    election.setDate(rs.getDate("date"));
	    election.setStartTime(rs.getTime("start_time"));
	    election.setEndTime(rs.getTime("end_time"));
	    return election;
	}

	/**
	 * Helper method to map ResultSet to Candidate object
	 */
	private static Candidate mapResultSetToCandidate(ResultSet rs) throws SQLException {
	    Candidate candidate = new Candidate();
	    candidate.setCandidateId(rs.getInt("candidate_id"));
	    candidate.setElectionId(rs.getInt("election_id"));
	    candidate.setFname(rs.getString("fname"));
	    candidate.setLname(rs.getString("lname"));
	    candidate.setPartyId(rs.getInt("party_id"));
	    candidate.setBio(rs.getString("bio"));
	    candidate.setProfileImage(rs.getString("profile_image"));
	    return candidate;
	}

	/**
	 * Gets all upcoming elections (elections that haven't started yet)
	 * @return List of upcoming elections
	 */
	public static List<Election> getUpcomingElections() {
		if (logger.isDebugEnabled()) {
			logger.debug("Fetching all upcoming elections");
		}

		String sql = "SELECT * FROM elections WHERE date > CURDATE() OR " +
				"(date = CURDATE() AND start_time > CURTIME()) " +
				"ORDER BY date ASC, start_time ASC";

		List<Election> upcomingElections = new ArrayList<>();

		try (Connection conn = DBConnectionManager.establishConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql);
			 ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Election election = mapResultSetToElection(rs);
				upcomingElections.add(election);

				if (logger.isTraceEnabled()) {
					logger.trace("Added upcoming election: {} (ID: {})",
							election.getName(), election.getElectionId());
				}
			}

			if (logger.isInfoEnabled()) {
				logger.info("Successfully retrieved {} upcoming elections", upcomingElections.size());
			}

			return upcomingElections;

		} catch (DatabaseException e) {
			logger.error("Database connection error while fetching upcoming elections", e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

		} catch (SQLException e) {
			logger.error("SQL error while fetching upcoming elections. Error code: {}, SQL state: {}",
					e.getErrorCode(), e.getSQLState(), e);
			throw new DataAccessException("Database error while fetching upcoming elections",
					"Failed to fetch upcoming elections. Please try again later.", e);

		} catch (Exception e) {
			logger.error("Unexpected error while fetching upcoming elections", e);
			throw new DataAccessException("Unexpected error while fetching upcoming elections",
					"An unexpected error occurred. Please contact support.", e);
		}
	}

	/**
	 * Gets all past elections (elections that have already ended)
	 * @return List of past elections
	 */
	public static List<Election> getPastElections() {
		if (logger.isDebugEnabled()) {
			logger.debug("Fetching all past elections");
		}

		String sql = "SELECT * FROM elections WHERE date < CURDATE() OR " +
				"(date = CURDATE() AND end_time < CURTIME()) " +
				"ORDER BY date DESC, end_time DESC";

		List<Election> pastElections = new ArrayList<>();

		try (Connection conn = DBConnectionManager.establishConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql);
			 ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Election election = mapResultSetToElection(rs);
				pastElections.add(election);

				if (logger.isTraceEnabled()) {
					logger.trace("Added past election: {} (ID: {})",
							election.getName(), election.getElectionId());
				}
			}

			if (logger.isInfoEnabled()) {
				logger.info("Successfully retrieved {} past elections", pastElections.size());
			}

			return pastElections;

		} catch (DatabaseException e) {
			logger.error("Database connection error while fetching past elections", e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

		} catch (SQLException e) {
			logger.error("SQL error while fetching past elections. Error code: {}, SQL state: {}",
					e.getErrorCode(), e.getSQLState(), e);
			throw new DataAccessException("Database error while fetching past elections",
					"Failed to fetch past elections. Please try again later.", e);

		} catch (Exception e) {
			logger.error("Unexpected error while fetching past elections", e);
			throw new DataAccessException("Unexpected error while fetching past elections",
					"An unexpected error occurred. Please contact support.", e);
		}
	}

	/**
	 * Gets elections scheduled for today that haven't started yet
	 * @return List of elections starting later today
	 */
	public static List<Election> getTodayUpcomingElections() {
		if (logger.isDebugEnabled()) {
			logger.debug("Fetching elections starting later today");
		}

		String sql = "SELECT * FROM elections WHERE date = CURDATE() AND start_time > CURTIME() " +
				"ORDER BY start_time ASC";

		List<Election> todayUpcoming = new ArrayList<>();

		try (Connection conn = DBConnectionManager.establishConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql);
			 ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Election election = mapResultSetToElection(rs);
				todayUpcoming.add(election);

				if (logger.isTraceEnabled()) {
					logger.trace("Added today's upcoming election: {} (Starts at {})",
							election.getName(), election.getStartTime());
				}
			}

			if (logger.isInfoEnabled()) {
				logger.info("Found {} elections starting later today", todayUpcoming.size());
			}

			return todayUpcoming;

		} catch (DatabaseException e) {
			logger.error("Database connection error while fetching today's upcoming elections", e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

		} catch (SQLException e) {
			logger.error("SQL error while fetching today's upcoming elections. Error code: {}, SQL state: {}",
					e.getErrorCode(), e.getSQLState(), e);
			throw new DataAccessException("Database error while fetching today's upcoming elections",
					"Failed to fetch today's upcoming elections. Please try again later.", e);

		} catch (Exception e) {
			logger.error("Unexpected error while fetching today's upcoming elections", e);
			throw new DataAccessException("Unexpected error while fetching today's upcoming elections",
					"An unexpected error occurred. Please contact support.", e);
		}
	}

	/**
	 * Gets elections that ended earlier today
	 * @return List of elections that ended earlier today
	 */
	public static List<Election> getTodayPastElections() {
		if (logger.isDebugEnabled()) {
			logger.debug("Fetching elections that ended earlier today");
		}

		String sql = "SELECT * FROM elections WHERE date = CURDATE() AND end_time < CURTIME() " +
				"ORDER BY end_time DESC";

		List<Election> todayPast = new ArrayList<>();

		try (Connection conn = DBConnectionManager.establishConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql);
			 ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Election election = mapResultSetToElection(rs);
				todayPast.add(election);

				if (logger.isTraceEnabled()) {
					logger.trace("Added today's past election: {} (Ended at {})",
							election.getName(), election.getEndTime());
				}
			}

			if (logger.isInfoEnabled()) {
				logger.info("Found {} elections that ended earlier today", todayPast.size());
			}

			return todayPast;

		} catch (DatabaseException e) {
			logger.error("Database connection error while fetching today's past elections", e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

		} catch (SQLException e) {
			logger.error("SQL error while fetching today's past elections. Error code: {}, SQL state: {}",
					e.getErrorCode(), e.getSQLState(), e);
			throw new DataAccessException("Database error while fetching today's past elections",
					"Failed to fetch today's past elections. Please try again later.", e);

		} catch (Exception e) {
			logger.error("Unexpected error while fetching today's past elections", e);
			throw new DataAccessException("Unexpected error while fetching today's past elections",
					"An unexpected error occurred. Please contact support.", e);
		}
	}


}