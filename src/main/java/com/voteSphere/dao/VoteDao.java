package com.voteSphere.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.voteSphere.config.DBConnectionManager;
import com.voteSphere.exception.DataAccessException;
import com.voteSphere.exception.DatabaseException;
import com.voteSphere.model.Vote;

public class VoteDao {

	private static final Logger logger = LogManager.getLogger(VoteDao.class);

	public static boolean createVote(Vote vote) {

		if (vote == null) {
			logger.error("Attempt to create null vote");
			throw new IllegalArgumentException("Vote cannot be null");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Attempting to create vote for userId: " + vote.getUserId() + ", electionId: "
					+ vote.getElectionId() + ", partyId: " + vote.getPartyId());
		}

		String sql = "INSERT INTO votes (user_id, election_id, party_id, voted_at, ip) VALUES (?, ?, ?, ?, ?)";

		try (Connection conn = DBConnectionManager.establishConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			stmt.setInt(1, vote.getUserId());
			stmt.setInt(2, vote.getElectionId());
			stmt.setInt(3, vote.getPartyId());
			stmt.setTimestamp(4, vote.getVotedAt());
			stmt.setString(5, vote.getIp());

			int affectedRows = stmt.executeUpdate();

			if (affectedRows == 0) {
				logger.warn("No rows affected when creating vote");
				throw new DataAccessException("No rows affected when creating vote",
						"Failed to record your vote. Please try again.", null);
			}

			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					vote.setVoteId(generatedKeys.getInt(1));
					if (logger.isInfoEnabled()) {
						logger.info("Successfully created vote with ID " + vote.getVoteId());
					}
					return true;
				} else {
					logger.error("No ID obtained after creating vote");
					throw new DataAccessException("No generated key returned for new vote",
							"Vote was recorded but we couldn't retrieve its ID. Please contact support.", null);
				}
			}

		} catch (DatabaseException e) {
			logger.error("Connection error while creating vote", e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

		} catch (SQLException e) {
			logger.error("SQL error while creating vote. Error code: " + e.getErrorCode() + ", SQL state: "
					+ e.getSQLState(), e);

			throw new DataAccessException("Database error while creating vote",
					"Failed to record vote due to system error. Please try again later.", e);

		} catch (Exception e) {
			logger.error("Unexpected error while creating vote", e);
			throw new DataAccessException("Unexpected error while creating vote",
					"An unexpected error occurred. Please contact support.", e);
		}
	}
	
	
	
	
	public static boolean hasUserAlreadyVoted(Integer userId, Integer electionId) {
		if (logger.isDebugEnabled()) {
			logger.debug("Checking if user with ID " + userId + " has already voted in election ID " + electionId);
		}

		String sql = "SELECT vote_id FROM votes WHERE user_id = ? AND election_id = ?";

		try (Connection conn = DBConnectionManager.establishConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, userId);
			stmt.setInt(2, electionId);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					if (logger.isInfoEnabled()) {
						logger.info("User with ID " + userId + " has already voted in election ID " + electionId);
					}
					return true;
				} else {
					if (logger.isDebugEnabled()) {
						logger.debug("User with ID " + userId + " has not voted yet in election ID " + electionId);
					}
					return false;
				}
			}

		} catch (DatabaseException e) {
			logger.error("Connection error while checking vote existence", e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

		} catch (SQLException e) {
			logger.error("SQL error while checking vote existence. Error code: " + e.getErrorCode()
					+ ", SQL state: " + e.getSQLState(), e);
			throw new DataAccessException("Database error while checking vote existence",
					"Unable to verify vote status. Please try again later.", e);

		} catch (Exception e) {
			logger.error("Unexpected error while checking vote existence", e);
			throw new DataAccessException("Unexpected error while checking vote existence",
					"An unexpected error occurred. Please contact support.", e);
		}
	}

	
	

	public static List<Vote> getAllVotes() {

		if (logger.isDebugEnabled()) {
			logger.debug("Attempting to retrieve all votes");
		}

		String sql = "SELECT * FROM votes";
		List<Vote> votes = new ArrayList<>();

		try (Connection conn = DBConnectionManager.establishConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				Vote vote = new Vote(rs.getInt("vote_id"), rs.getInt("user_id"), rs.getInt("election_id"),
						rs.getInt("party_id"), rs.getTimestamp("voted_at"), rs.getString("ip"));
				votes.add(vote);
			}

			if (logger.isInfoEnabled()) {
				logger.info("Successfully retrieved " + votes.size() + " votes from the database");
			}

			return votes;

		} catch (DatabaseException e) {
			logger.error("Connection error while retrieving votes", e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

		} catch (SQLException e) {
			logger.error("SQL error while retrieving votes. Error code: " + e.getErrorCode() + ", SQL state: "
					+ e.getSQLState(), e);

			throw new DataAccessException("Database error during retrieving votes",
					"Failed to load votes due to a system error. Please try again later.", e);

		} catch (Exception e) {
			logger.error("Unexpected error while retrieving votes", e);
			throw new DataAccessException("Unexpected error during retrieving votes",
					"An unexpected error occurred. Please contact support.", e);
		}
	}

	public static Vote findVoteById(int id) {
		if (logger.isDebugEnabled()) {
			logger.debug("Searching for vote ID: " + id);
		}
		String sql = "SELECT * FROM votes WHERE vote_id = ?";

		try (Connection conn = DBConnectionManager.establishConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					Vote vote = extractVoteFromResultSet(rs);
					if (logger.isInfoEnabled()) {
						logger.info("Found vote ID: " + id);
					}
					return vote;
				} else {
					logger.warn("No vote found with ID: " + id);
					return null;
				}
			}

		} catch (DatabaseException e) {
			logger.error("Connection error while fetching vote by ID: " + id, e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

		} catch (SQLException e) {
			logger.error("SQL error while fetching vote by ID: " + id + ". Error code: " + e.getErrorCode()
					+ ", SQL state: " + e.getSQLState(), e);
			throw new DataAccessException("Database error while retrieving vote",
					"Failed to retrieve vote due to system error. Please try again later.", e);

		} catch (Exception e) {
			logger.error("Unexpected error while fetching vote by ID: " + id, e);
			throw new DataAccessException("Unexpected error while retrieving vote",
					"An unexpected error occurred. Please contact support.", e);
		}
	}

	public static boolean updateVote(Vote vote) {
		if (vote == null) {
			logger.error("Attempt to update null vote");
			throw new IllegalArgumentException("Vote cannot be null");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Updating vote ID: " + vote.getVoteId());
		}

		String sql = "UPDATE votes SET user_id=?, election_id=?, party_id=?, voted_at=?, ip=? WHERE vote_id=?";
		try (Connection conn = DBConnectionManager.establishConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, vote.getUserId());
			stmt.setInt(2, vote.getElectionId());
			stmt.setInt(3, vote.getPartyId());
			stmt.setTimestamp(4, vote.getVotedAt());
			stmt.setString(5, vote.getIp());
			stmt.setInt(6, vote.getVoteId());

			int rows = stmt.executeUpdate();
			if (rows == 0) {
				logger.warn("No rows affected when updating vote ID: " + vote.getVoteId());
				throw new DataAccessException("No rows affected when updating vote",
						"Failed to update your vote. Please try again.", null);
			}
			if (logger.isInfoEnabled()) {
				logger.info("Successfully updated vote ID: " + vote.getVoteId());
			}
			return true;

		} catch (DatabaseException e) {
			logger.error("Connection error while updating vote", e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

		} catch (SQLException e) {
			logger.error("SQL error while updating vote. Error code: " + e.getErrorCode() + ", SQL state: "
					+ e.getSQLState(), e);
			throw new DataAccessException("Database error while updating vote",
					"Failed to update vote due to system error. Please try again later.", e);

		} catch (Exception e) {
			logger.error("Unexpected error while updating vote", e);
			throw new DataAccessException("Unexpected error while updating vote",
					"An unexpected error occurred. Please contact support.", e);
		}
	}

	public static boolean deleteVote(int voteId) {
		if (logger.isDebugEnabled()) {
			logger.debug("Deleting vote ID: " + voteId);
		}
		String sql = "DELETE FROM votes WHERE vote_id = ?";

		try (Connection conn = DBConnectionManager.establishConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, voteId);
			int rows = stmt.executeUpdate();
			if (rows == 0) {
				logger.warn("No vote deleted for ID: " + voteId);
				return false;
			}
			if (logger.isInfoEnabled()) {
				logger.info("Successfully deleted vote ID: " + voteId);
			}
			return true;

		} catch (DatabaseException e) {
			logger.error("Connection error while deleting vote", e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

		} catch (SQLException e) {
			logger.error("SQL error while deleting vote. Error code: " + e.getErrorCode() + ", SQL state: "
					+ e.getSQLState(), e);
			throw new DataAccessException("Database error while deleting vote",
					"Failed to delete vote due to system error. Please try again later.", e);

		} catch (Exception e) {
			logger.error("Unexpected error while deleting vote", e);
			throw new DataAccessException("Unexpected error while deleting vote",
					"An unexpected error occurred. Please contact support.", e);
		}
	}

	public static boolean deleteVotesByUserId(int userId) {
		if (logger.isDebugEnabled()) {
			logger.debug("Deleting votes for userId: " + userId);
		}
		String sql = "DELETE FROM votes WHERE user_id = ?";

		try (Connection conn = DBConnectionManager.establishConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, userId);
			int rows = stmt.executeUpdate();
			return rows > 0;

		} catch (DatabaseException e) {
			logger.error("Connection error while deleting user votes", e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

		} catch (SQLException e) {
			logger.error("SQL error while deleting user votes. Error code: " + e.getErrorCode() + ", SQL state: "
					+ e.getSQLState(), e);
			throw new DataAccessException("Database error while deleting user votes",
					"Failed to delete votes due to system error. Please try again later.", e);

		} catch (Exception e) {
			logger.error("Unexpected error while deleting user votes", e);
			throw new DataAccessException("Unexpected error while deleting user votes",
					"An unexpected error occurred. Please contact support.", e);
		}
	}

	public static Map<Integer, Integer> getAllPartyVotesInElection(int electionId) {
		if (logger.isDebugEnabled()) {
			logger.debug("Counting votes by party for electionId: " + electionId);
		}
		String sql = "SELECT party_id, COUNT(*) AS vote_count FROM votes WHERE election_id = ? GROUP BY party_id";
		Map<Integer, Integer> map = new HashMap<>();

		try (Connection conn = DBConnectionManager.establishConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, electionId);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					map.put(rs.getInt("party_id"), rs.getInt("vote_count"));
				}
			}
			return map;

		} catch (DatabaseException e) {
			logger.error("Connection error while grouping votes", e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

		} catch (SQLException e) {
			logger.error("SQL error while grouping votes. Error code: " + e.getErrorCode() + ", SQL state: "
					+ e.getSQLState(), e);
			throw new DataAccessException("Database error while counting votes by party",
					"Failed to count votes due to system error. Please try again later.", e);

		} catch (Exception e) {
			logger.error("Unexpected error while counting votes", e);
			throw new DataAccessException("Unexpected error while counting votes",
					"An unexpected error occurred. Please contact support.", e);
		}
	}

	public static int getVoteCountForPartyInElection(int electionId, int partyId) {
		if (logger.isDebugEnabled()) {
			logger.debug("Counting votes for partyId " + partyId + " in electionId " + electionId);
		}
		String sql = "SELECT COUNT(*) FROM votes WHERE election_id = ? AND party_id = ?";

		try (Connection conn = DBConnectionManager.establishConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, electionId);
			stmt.setInt(2, partyId);
			try (ResultSet rs = stmt.executeQuery()) {
				return rs.next() ? rs.getInt(1) : 0;
			}

		} catch (DatabaseException e) {
			logger.error("Connection error while counting party votes", e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

		} catch (SQLException e) {
			logger.error("SQL error while counting party votes. Error code: " + e.getErrorCode() + ", SQL state: "
					+ e.getSQLState(), e);
			throw new DataAccessException("Database error while counting party votes",
					"Failed to count votes due to system error. Please try again later.", e);

		} catch (Exception e) {
			logger.error("Unexpected error while counting party votes", e);
			throw new DataAccessException("Unexpected error while counting party votes",
					"An unexpected error occurred. Please contact support.", e);
		}
	}

	public static int getTotalVotesInElection(int electionId) {
		if (logger.isDebugEnabled()) {
			logger.debug("Counting total votes for electionId: " + electionId);
		}
		String sql = "SELECT COUNT(*) FROM votes WHERE election_id = ?";

		try (Connection conn = DBConnectionManager.establishConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, electionId);
			try (ResultSet rs = stmt.executeQuery()) {
				return rs.next() ? rs.getInt(1) : 0;
			}

		} catch (DatabaseException e) {
			logger.error("Connection error while counting total votes", e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

		} catch (SQLException e) {
			logger.error("SQL error while counting total votes. Error code: " + e.getErrorCode() + ", SQL state: "
					+ e.getSQLState(), e);
			throw new DataAccessException("Database error while counting total votes",
					"Failed to count votes due to system error. Please try again later.", e);

		} catch (Exception e) {
			logger.error("Unexpected error while counting total votes", e);
			throw new DataAccessException("Unexpected error while counting total votes",
					"An unexpected error occurred. Please contact support.", e);
		}
	}

	public static List<Vote> getVotesByPartyId(int partyId) {
		if (logger.isDebugEnabled()) {
			logger.debug("Retrieving votes for partyId: " + partyId);
		}
		String sql = "SELECT * FROM votes WHERE party_id = ?";
		List<Vote> list = new ArrayList<>();

		try (Connection conn = DBConnectionManager.establishConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, partyId);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					list.add(extractVoteFromResultSet(rs));
				}
			}
			return list;

		} catch (DatabaseException e) {
			logger.error("Connection error while retrieving party votes", e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

		} catch (SQLException e) {
			logger.error("SQL error while retrieving party votes. Error code: " + e.getErrorCode() + ", SQL state: "
					+ e.getSQLState(), e);
			throw new DataAccessException("Database error while retrieving party votes",
					"Failed to load votes due to system error. Please try again later.", e);

		} catch (Exception e) {
			logger.error("Unexpected error while retrieving party votes", e);
			throw new DataAccessException("Unexpected error while retrieving party votes",
					"An unexpected error occurred. Please contact support.", e);
		}
	}

	public static List<Vote> findVotesByUserId(int userId) {
		if (logger.isDebugEnabled()) {
			logger.debug("Retrieving votes for userId: " + userId);
		}
		String sql = "SELECT * FROM votes WHERE user_id = ?";
		List<Vote> list = new ArrayList<>();

		try (Connection conn = DBConnectionManager.establishConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, userId);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					list.add(extractVoteFromResultSet(rs));
				}
			}
			return list;

		} catch (DatabaseException e) {
			logger.error("Connection error while retrieving user votes", e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

		} catch (SQLException e) {
			logger.error("SQL error while retrieving user votes. Error code: " + e.getErrorCode() + ", SQL state: "
					+ e.getSQLState(), e);
			throw new DataAccessException("Database error while retrieving user votes",
					"Failed to load votes due to system error. Please try again later.", e);

		} catch (Exception e) {
			logger.error("Unexpected error while retrieving user votes", e);
			throw new DataAccessException("Unexpected error while retrieving user votes",
					"An unexpected error occurred. Please contact support.", e);
		}
	}

	public static List<Vote> findVoteHistoryForElection(int electionId) {
		if (logger.isDebugEnabled()) {
			logger.debug("Retrieving vote history for electionId: " + electionId);
		}
		String sql = "SELECT * FROM votes WHERE election_id = ? ORDER BY voted_at DESC";
		List<Vote> list = new ArrayList<>();

		try (Connection conn = DBConnectionManager.establishConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, electionId);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					list.add(extractVoteFromResultSet(rs));
				}
			}
			return list;

		} catch (DatabaseException e) {
			logger.error("Connection error while retrieving vote history", e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

		} catch (SQLException e) {
			logger.error("SQL error while retrieving vote history. Error code: " + e.getErrorCode() + ", SQL state: "
					+ e.getSQLState(), e);
			throw new DataAccessException("Database error while retrieving vote history",
					"Failed to load votes due to system error. Please try again later.", e);

		} catch (Exception e) {
			logger.error("Unexpected error while retrieving vote history", e);
			throw new DataAccessException("Unexpected error while retrieving vote history",
					"An unexpected error occurred. Please contact support.", e);
		}
	}

	public static boolean hasUserVotedInElection(int userId, int electionId) {
		if (logger.isDebugEnabled()) {
			logger.debug("Checking if userId " + userId + " has voted in electionId " + electionId);
		}
		String sql = "SELECT COUNT(*) FROM votes WHERE user_id = ? AND election_id = ?";

		try (Connection conn = DBConnectionManager.establishConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, userId);
			stmt.setInt(2, electionId);
			try (ResultSet rs = stmt.executeQuery()) {
				return rs.next() && rs.getInt(1) > 0;
			}

		} catch (DatabaseException e) {
			logger.error("Connection error while checking user vote", e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

		} catch (SQLException e) {
			logger.error("SQL error while checking user vote. Error code: " + e.getErrorCode() + ", SQL state: "
					+ e.getSQLState(), e);
			throw new DataAccessException("Database error while checking user vote",
					"Failed to verify vote due to system error. Please try again later.", e);

		} catch (Exception e) {
			logger.error("Unexpected error while checking user vote", e);
			throw new DataAccessException("Unexpected error while checking user vote",
					"An unexpected error occurred. Please contact support.", e);
		}
	}

	public static int getMostVotedPartyInElection(int electionId) {
		if (logger.isDebugEnabled()) {
			logger.debug("Finding most voted party in electionId: " + electionId);
		}
		String sql = "SELECT party_id, COUNT(*) AS vote_count FROM votes WHERE election_id = ? "
				+ "GROUP BY party_id ORDER BY vote_count DESC LIMIT 1";

		try (Connection conn = DBConnectionManager.establishConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, electionId);
			try (ResultSet rs = stmt.executeQuery()) {
				return rs.next() ? rs.getInt("party_id") : -1;
			}

		} catch (DatabaseException e) {
			logger.error("Connection error while finding top party", e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

		} catch (SQLException e) {
			logger.error("SQL error while finding top party. Error code: " + e.getErrorCode() + ", SQL state: "
					+ e.getSQLState(), e);
			throw new DataAccessException("Database error while finding top party",
					"Failed to determine top party due to system error. Please try again later.", e);

		} catch (Exception e) {
			logger.error("Unexpected error while finding top party", e);
			throw new DataAccessException("Unexpected error while finding top party",
					"An unexpected error occurred. Please contact support.", e);
		}
	}

	public static int getTotalVoteCountForElection(int electionId) {
		if (electionId <= 0) {
			logger.error("Invalid electionId provided: " + electionId);
			throw new IllegalArgumentException("Election ID must be a positive integer");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Fetching total vote count for electionId: " + electionId);
		}

		String sql = "SELECT COUNT(*) FROM votes WHERE election_id = ?";

		try (Connection conn = DBConnectionManager.establishConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, electionId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					int totalVotes = rs.getInt(1);
					if (logger.isInfoEnabled()) {
						logger.info("Total votes for electionId " + electionId + ": " + totalVotes);
					}
					return totalVotes;
				} else {
					logger.warn("No votes found for electionId: " + electionId);
					return 0;
				}
			}

		} catch (DatabaseException e) {
			logger.error("Connection error while fetching total vote count for electionId: " + electionId, e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

		} catch (SQLException e) {
			logger.error("SQL error while fetching total vote count for electionId: " + electionId + ". Error code: "
					+ e.getErrorCode() + ", SQL state: " + e.getSQLState(), e);
			throw new DataAccessException("Database error while fetching total vote count",
					"Unable to retrieve total votes due to system error. Please try again later.", e);

		} catch (Exception e) {
			logger.error("Unexpected error while fetching total vote count for electionId: " + electionId, e);
			throw new DataAccessException("Unexpected error while fetching total vote count",
					"An unexpected error occurred. Please contact support.", e);
		}
	}

	private static Vote extractVoteFromResultSet(ResultSet rs) throws SQLException {
		return new Vote(rs.getInt("vote_id"), rs.getInt("user_id"), rs.getInt("election_id"), rs.getInt("party_id"),
				rs.getTimestamp("voted_at"), rs.getString("ip"));
	}






}
