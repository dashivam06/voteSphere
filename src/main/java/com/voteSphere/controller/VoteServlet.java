package com.voteSphere.controller;

import java.io.IOException;
import java.util.Map;

import com.voteSphere.service.VoteService;
import com.voteSphere.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet("/vote")
public class VoteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = LogManager.getLogger(VoteServlet.class);

    public VoteServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String electionIdParam = request.getParameter("electionId");
        logger.info("GET /vote request received with electionId: {}", electionIdParam);

        boolean isValidElectionId = ValidationUtil.isNumeric(electionIdParam);

        if (electionIdParam == null || electionIdParam.trim().isEmpty() || !isValidElectionId) {
            logger.warn("Invalid election ID provided: {}", electionIdParam);
            request.setAttribute("error", "Invalid Election ID provided.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        Integer electionId = Integer.parseInt(electionIdParam);

        Map<Integer, Integer> partyVotes = VoteService.getAllPartyVotesInElection(request, response, electionId);

        if (partyVotes.isEmpty()) {
            logger.warn("No votes found for election ID: {}", electionId);
            request.setAttribute("error", "No votes found for the given election.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } else {
            logger.info("Votes found for election ID: {}", electionId);
            request.setAttribute("partyVotes", partyVotes);
            request.getRequestDispatcher("/voteList.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String electionIdParam = request.getParameter("election_id");
        logger.info("POST /vote request received with election_id: {}", electionIdParam);

        if (!ValidationUtil.isNumeric(electionIdParam)) {
            logger.warn("Invalid election ID for voting: {}", electionIdParam);
            request.setAttribute("error", "Invalid Election Id for voting.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        int electionId = Integer.parseInt(electionIdParam);

        boolean voted = VoteService.addVote(request, response);

        if (voted) {
            logger.info("Vote successfully cast for election ID: {}", electionId);
            request.setAttribute("voteSuccess", true);
            request.getRequestDispatcher("/WEB-INF/pages/voter/cast-vote.jsp").forward(request, response);
        } else {
            logger.error("Failed to cast vote for election ID: {}", electionId);
            request.setAttribute("error", "Failed to cast vote. Please try again.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String voteIdParam = request.getParameter("id");
        logger.info("PUT /vote request received to update vote ID: {}", voteIdParam);

        if (voteIdParam == null || !ValidationUtil.isNumeric(voteIdParam)) {
            logger.warn("Invalid vote ID for update: {}", voteIdParam);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Voter ID for update.");
            return;
        }

        int voteId = Integer.parseInt(voteIdParam);

        boolean updated = VoteService.updateVote(request, response, voteId);

        if (updated) {
            logger.info("Vote updated successfully for ID: {}", voteId);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            logger.warn("Vote not found or failed to update for ID: {}", voteId);
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Vote not found to update.");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String voteIdParam = request.getParameter("id");
        logger.info("DELETE /vote request received to delete vote ID: {}", voteIdParam);

        if (voteIdParam == null || voteIdParam.trim().isEmpty() || !ValidationUtil.isNumeric(voteIdParam)) {
            logger.warn("Invalid vote ID for deletion: {}", voteIdParam);
            request.setAttribute("vote_id_error", "Invalid ID for deletion.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        int voteId = Integer.parseInt(voteIdParam);

        boolean deleted = VoteService.deleteVote(request, response, voteId);

        if (deleted) {
            logger.info("Vote deleted successfully for ID: {}", voteId);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            logger.error("Failed to delete vote for ID: {}", voteId);
            request.setAttribute("error", "Failed to delete the vote. Please try again.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
