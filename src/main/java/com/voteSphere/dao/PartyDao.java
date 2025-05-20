package com.voteSphere.dao;

import java.sql.Connection;
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
import com.voteSphere.model.Party;

public class PartyDao {

	private static final Logger logger = LogManager.getLogger(PartyDao.class);

	public static boolean createParty(Party party) {

		if (party == null) {
			logger.error("Attempt to create null party");
			throw new IllegalArgumentException("Party cannot be null");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Attempting to create party: " + party.getName());
		}
		String sql = "INSERT INTO parties (name, leader_name, founder_name, symbol_image, cover_image, description) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection conn = DBConnectionManager.establishConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			if (logger.isTraceEnabled()) {
				logger.trace("Setting parameters for party creation: name=" + party.getName() + ", leader="
						+ party.getLeaderName() + ", founder=" + party.getFounderName());
			}

			stmt.setString(1, party.getName());
			stmt.setString(2, party.getLeaderName());
			stmt.setString(3, party.getFounderName());
			stmt.setString(4, party.getSymbolImage());
			stmt.setString(5, party.getCoverImage());
			stmt.setString(6, party.getDescription());

			int affectedRows = stmt.executeUpdate();

			if (affectedRows == 0) {
				logger.warn("No rows affected when creating party: " + party.getName());
				throw new DataAccessException("No rows affected when creating party",
						"Failed to create political party. Please try again.", null);
			}

			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					party.setPartyId(generatedKeys.getInt(1));
					if (logger.isInfoEnabled()) {
						logger.info("Successfully created party " + party.getName() + " with ID " + party.getPartyId());
					}
					return true;
				} else {
					logger.error("No ID obtained after creating party: " + party.getName());
					throw new DataAccessException("No generated key returned for new party",
							"Party was created but we couldn't retrieve its ID. Please contact support.", null);
				}
			}
		} catch (DatabaseException e) {
			logger.error("Connection error while creating party", e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

		} catch (SQLException e) {

			if ("23000".equals(e.getSQLState())) {
				logger.warn("Duplicate party creation attempted: " + party.getName() + ". Error: " + e.getMessage());
				throw new DataAccessException("Duplicate party creation attempted: " + party.getName(),
						"A party with this name already exists. Please choose a different name.", e);
			}

			logger.error("Database error while creating party: " + party.getName() + ". Error code: " + e.getErrorCode()
					+ ", SQL state: " + e.getSQLState(), e);

			throw new DataAccessException("Database error while creating party: " + e.getMessage(),
					"Failed to create political party due to system error. Please try again later.", e);

		} catch (Exception e) {

			logger.error("Unexpected error while creating party: " + party.getName(), e);
			throw new DataAccessException("Unexpected error while creating party",
					"An unexpected error occurred. Please contact support.", e);

		}
	}

	public static List<Party> getAllParties() {

		if (logger.isDebugEnabled()) {
			logger.debug("Attempting to retrieve all political parties");
		}

		String sql = "SELECT * FROM parties";
		List<Party> parties = new ArrayList<>();

		try (Connection conn = DBConnectionManager.establishConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				Party party = new Party(rs.getInt("party_id"), rs.getString("name"), rs.getString("leader_name"),
						rs.getString("founder_name"), rs.getString("symbol_image"), rs.getString("cover_image"),
						rs.getString("description"));
				parties.add(party);
			}

			if (logger.isInfoEnabled()) {
				logger.info("Successfully retrieved " + parties.size() + " parties from the database");
			}

			return parties;

		} catch (DatabaseException e) {
			logger.error("Connection error while retrieving all parties", e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

		} catch (SQLException e) {
			logger.error("SQL error while retrieving all parties. Error code: " + e.getErrorCode() + ", SQL state: "
					+ e.getSQLState(), e);

			throw new DataAccessException("Database error during retrieving all parties",
					"Failed to load political parties due to a system error. Please try again later.", e);

		} catch (Exception e) {
			logger.error("Unexpected error while retrieving all parties", e);
			throw new DataAccessException("Unexpected error during retrieving all parties",
					"An unexpected error occurred. Please contact support.", e);
		}
	}

	public static Party findPartyById(int id) {

		if (logger.isDebugEnabled()) {
			logger.debug("Searching for party with ID: " + id);
		}

		String sql = "SELECT * FROM parties WHERE party_id = ?";

		try (Connection conn = DBConnectionManager.establishConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);

			if (logger.isTraceEnabled()) {
				logger.trace("Executing SQL query to find party by ID: " + id);
			}

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					Party party = new Party(rs.getInt("party_id"), rs.getString("name"), rs.getString("leader_name"),
							rs.getString("founder_name"), rs.getString("symbol_image"), rs.getString("cover_image"),
							rs.getString("description"));

					if (logger.isInfoEnabled()) {
						logger.info("Found party: " + party.getName() + " (ID: " + party.getPartyId() + ")");
					}
					return party;
				} else {
					logger.warn("No party found with ID: " + id);
					return null;
				}
			}

		} catch (DatabaseException e) {
			logger.error("Database connection error while fetching party by ID: " + id, e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

		} catch (SQLException e) {
			logger.error("SQL error while fetching party by ID: " + id + ". Error code: " + e.getErrorCode()
					+ ", SQL state: " + e.getSQLState(), e);

			throw new DataAccessException("Database error while retrieving party",
					"Failed to retrieve party due to system error. Please try again later.", e);

		} catch (Exception e) {
			logger.error("Unexpected error while fetching party by ID: " + id, e);
			throw new DataAccessException("Unexpected error while retrieving party",
					"An unexpected error occurred. Please contact support.", e);
		}
	}

	public static boolean updateParty(Party party) {

		if (party == null) {
			logger.error("Attempt to update null party");
			throw new IllegalArgumentException("Party cannot be null");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Attempting to update party: " + party.getName() + " (ID: " + party.getPartyId() + ")");
		}

		String sql = "UPDATE parties SET name=?, leader_name=?, founder_name=?, symbol_image=?, cover_image=?, description=? WHERE party_id=?";

		try (Connection conn = DBConnectionManager.establishConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			if (logger.isTraceEnabled()) {
				logger.trace("Setting parameters for party update: name=" + party.getName() + ", leader="
						+ party.getLeaderName() + ", founder=" + party.getFounderName() + ", symbol="
						+ party.getSymbolImage() + ", cover=" + party.getCoverImage() + ", desc="
						+ party.getDescription() + ", ID=" + party.getPartyId());
			}

			stmt.setString(1, party.getName());
			stmt.setString(2, party.getLeaderName());
			stmt.setString(3, party.getFounderName());
			stmt.setString(4, party.getSymbolImage());
			stmt.setString(5, party.getCoverImage());
			stmt.setString(6, party.getDescription());
			stmt.setInt(7, party.getPartyId());

			int affectedRows = stmt.executeUpdate();

			if (affectedRows == 0) {
				logger.warn("No rows affected when updating party ID: " + party.getPartyId());
				throw new DataAccessException("No rows affected while updating party.",
						"Failed to update political party. Please try again.", null);
			}

			if (logger.isInfoEnabled()) {
				logger.info("Successfully updated party ID " + party.getPartyId() + ": " + party.getName());
			}
			return true;

		} catch (DatabaseException e) {
			logger.error("Connection error while updating party ID: " + party.getPartyId(), e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

		} catch (SQLException e) {
			if ("23000".equals(e.getSQLState())) {
				logger.warn("Duplicate party update attempted for name: " + party.getName());
				throw new DataAccessException("Duplicate party name: " + party.getName(),
						"A party with this name already exists. Please choose a different name.", e);
			}

			logger.error("SQL error while updating party ID: " + party.getPartyId() + ". Error code: "
					+ e.getErrorCode() + ", SQL state: " + e.getSQLState(), e);

			throw new DataAccessException("SQL error while updating party",
					"Failed to update political party due to a system error. Please try again later.", e);

		} catch (Exception e) {
			logger.error("Unexpected error while updating party ID: " + party.getPartyId(), e);
			throw new DataAccessException("Unexpected error while updating party",
					"An unexpected error occurred. Please contact support.", e);
		}
	}

	public static boolean deleteParty(int id) {

		if (id <= 0) {
			logger.error("Invalid party ID provided for deletion: " + id);
			throw new IllegalArgumentException("Party ID must be a positive number");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Attempting to delete party and related data with ID: " + id);
		}

		String deleteVotesSQL = "DELETE FROM votes WHERE party_id = ?";
		String deleteCandidatesSql = "DELETE FROM candidates WHERE party_id = ?";
		String deletePartySql = "DELETE FROM parties WHERE party_id = ?";


		try (Connection conn = DBConnectionManager.establishConnection()) {

			conn.setAutoCommit(false); // Start transaction

			try (PreparedStatement deleteCandidatesStmt = conn.prepareStatement(deleteCandidatesSql);
				 PreparedStatement deletePartyStmt = conn.prepareStatement(deletePartySql);
				PreparedStatement deleteVoteStmt = conn.prepareStatement(deleteVotesSQL)) {

				// Delete the votes
				deleteVoteStmt.setInt(1, id);
				deleteVoteStmt.executeUpdate();

				// Delete candidates associated with the party
				deleteCandidatesStmt.setInt(1, id);
				deleteCandidatesStmt.executeUpdate();

				// Delete the party itself
				deletePartyStmt.setInt(1, id);
				int affectedRows = deletePartyStmt.executeUpdate();

				if (affectedRows > 0) {
					conn.commit(); // Commit transaction
					logger.info("Successfully deleted party and its candidates along with votes with ID: " + id);
					return true;
				} else {
					conn.rollback(); // No party found, rollback
					logger.warn("No party found to delete with ID: " + id);
					return false;
				}

			} catch (SQLException e) {
				conn.rollback(); // Rollback on error
				throw e;
			}

		} catch (SQLException e) {
			logger.error("SQL error while deleting party with ID: " + id, e);
			throw new DataAccessException("Database error during party deletion",
					"Failed to delete the party due to a system error.", e);
		} catch (DatabaseException e) {
			logger.error("Connection error while deleting party with ID: " + id, e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);
		} catch (Exception e) {
			logger.error("Unexpected error while deleting party with ID: " + id, e);
			throw new DataAccessException("Unexpected error during party deletion",
					"An unexpected error occurred. Please contact support.", e);
		}
	}

	public static Party findPartyByName(String name) {

		if (name == null || name.trim().isEmpty()) {
			logger.error("Attempted to search for party with null or empty name");
			throw new IllegalArgumentException("Party name cannot be null or empty");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Searching for party with name: " + name);
		}

		String sql = "SELECT * FROM parties WHERE name = ?";

		try (Connection conn = DBConnectionManager.establishConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, name);

			if (logger.isTraceEnabled()) {
				logger.trace("Executing SQL query to find party by name: " + name);
			}

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					Party party = new Party(rs.getInt("party_id"), rs.getString("name"), rs.getString("leader_name"),
							rs.getString("founder_name"), rs.getString("symbol_image"), rs.getString("cover_image"),
							rs.getString("description"));

					if (logger.isInfoEnabled()) {
						logger.info("Found party: " + party.getName() + " (ID: " + party.getPartyId() + ")");
					}
					return party;
				} else {
					logger.warn("No party found with name: " + name);
					return null;
				}
			}

		} catch (DatabaseException e) {
			logger.error("Connection error while fetching party by name: " + name, e);
			throw new DataAccessException(e.getMessage(), e.getUserMessage(), e);

		} catch (SQLException e) {
			logger.error("SQL error while fetching party by name: " + name + ". Error code: " + e.getErrorCode()
					+ ", SQL state: " + e.getSQLState(), e);

			throw new DataAccessException("Database error while retrieving party by name",
					"Failed to find the political party due to system error. Please try again later.", e);

		} catch (Exception e) {
			logger.error("Unexpected error while fetching party by name: " + name, e);
			throw new DataAccessException("Unexpected error while retrieving party by name",
					"An unexpected error occurred. Please contact support.", e);
		}
	}

}
