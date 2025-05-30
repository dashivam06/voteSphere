package com.voteSphere.controller.voter;

import com.voteSphere.model.AuthUser;
import com.voteSphere.model.Candidate;
import com.voteSphere.model.Election;
import com.voteSphere.service.ElectionService;
import com.voteSphere.service.VoteService;
import com.voteSphere.util.SessionUtil;
import com.voteSphere.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

@WebServlet("/cast-vote/*")
public class CastVoteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(CastVoteServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo(); // e.g. "/1"

        if (pathInfo == null || pathInfo.length() <= 1) {
            logger.warn("Election ID not provided in URL path");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Election ID not provided");
            return;
        }

        String electionIdStr = pathInfo.substring(1);
        if (!ValidationUtil.isNumeric(electionIdStr)) {
            logger.warn("Invalid election ID format: {}", electionIdStr);
            response.sendRedirect(request.getContextPath() + "/election");
            return;
        }

        int electionId = Integer.parseInt(electionIdStr);
        logger.debug("Processing GET vote request for election ID: {}", electionId);

        if (!prepareElectionData(request, electionId)) {
            response.sendRedirect(request.getContextPath() + "/election");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/pages/voter/cast-vote.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String electionIdParam = request.getParameter("election_id");
        logger.debug("POST /cast-vote received with election_id: {}", electionIdParam);

        if (!ValidationUtil.isNumeric(electionIdParam)) {
            logger.warn("Invalid election ID for voting: {}", electionIdParam);
            request.setAttribute("error", "Invalid Election Id for voting.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        int electionId = Integer.parseInt(electionIdParam);

        if (VoteService.addVote(request, response)) {
            logger.info("Vote successfully cast for election ID: {}", electionId);

            if (!prepareElectionData(request, electionId)) {
                response.sendRedirect(request.getContextPath() + "/election");
                return;
            }

            request.setAttribute("voteSuccess", true);
            request.getRequestDispatcher("/WEB-INF/pages/voter/cast-vote.jsp").forward(request, response);

        } else {
            logger.warn("Failed to cast vote for election ID: {}", electionId);
            request.setAttribute("error", "Failed to cast vote. Please try again.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private boolean prepareElectionData(HttpServletRequest request, int electionId) {
        Election election = ElectionService.getElectionById(electionId);
        if (election == null) {
            logger.warn("Election not found with ID: {}", electionId);
            return false;
        }

        List<Candidate> candidates = ElectionService.getCandidatesForElection(electionId);
        long now = System.currentTimeMillis();
        long timeRemaining = election.getEndTime().getTime() - now;

        request.setAttribute("electionId", electionId);
        request.setAttribute("election", election);
        request.setAttribute("candidateList", candidates);
        request.setAttribute("hoursRemaining", (int) (timeRemaining / (1000 * 60 * 60)));
        request.setAttribute("progressPercent", calculateProgressPercent(election));
        request.setAttribute("voterToken", generateVoterToken(request));

        Integer userId = SessionUtil.getUserValueFromSession(request, AuthUser::getUserId);
        request.setAttribute("user_id", userId);

        logger.info("Prepared election details for election ID: {}", electionId);
        return true;
    }

    private int calculateProgressPercent(Election election) {
        long totalDuration = election.getEndTime().getTime() - election.getStartTime().getTime();
        long elapsed = System.currentTimeMillis() - election.getStartTime().getTime();
        return (int) ((elapsed * 100) / totalDuration);
    }

    private String generateVoterToken(HttpServletRequest request) {
        return "VOTE-" + request.getSession(false).getId().substring(0, 6).toUpperCase();
    }
}