package com.voteSphere.controller;

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

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Get the path info from the URL, e.g., "/1"
            String pathInfo = request.getPathInfo(); // returns "/1"

            if (pathInfo != null && pathInfo.length() > 1) {
                String electionId = pathInfo.substring(1); // removes the leading "/"
                // Now you can use electionId as needed
                request.setAttribute("electionId", electionId);
                // forward or redirect to the appropriate JSP or logic

                if (electionId == null || electionId.isEmpty()) {
                    response.sendRedirect(request.getContextPath() + "/election");
                    return;
                }

                // Fetch election details
                Election election = ElectionService.getElectionById(Integer.parseInt(electionId));
                if (election == null) {
                    response.sendRedirect(request.getContextPath() + "/election");
                    return;
                }

                // Fetch candidates for this election
                List<Candidate> candidates = ElectionService.getCandidatesForElection(Integer.parseInt(electionId));

                // Calculate time remaining
                long now = System.currentTimeMillis();
                long timeRemaining = election.getEndTime().getTime() - now;
                int hoursRemaining = (int) (timeRemaining / (1000 * 60 * 60));
                int progressPercent = calculateProgressPercent(election);

                // Set attributes for JSP
                request.setAttribute("election", election);
                request.setAttribute("candidateList", candidates);
                request.setAttribute("hoursRemaining", hoursRemaining);
                request.setAttribute("progressPercent", progressPercent);
                request.setAttribute("voterToken", generateVoterToken(request));


                Integer userId = SessionUtil.getUserValueFromSession(request, AuthUser::getUserId);
                request.setAttribute("user_id", userId);


                // Forward to JSP
                request.getRequestDispatcher("/WEB-INF/pages/voter/cast-vote.jsp").forward(request, response);

            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Election ID not provided");
            }


        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/election");
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

            // Fetch election details
            Election election = ElectionService.getElectionById(Integer.parseInt(electionIdParam));
            if (election == null) {
                response.sendRedirect(request.getContextPath() + "/election");
                return;
            }

            // Fetch candidates for this election
            List<Candidate> candidates = ElectionService.getCandidatesForElection(Integer.parseInt(electionIdParam));

            // Calculate time remaining
            long now = System.currentTimeMillis();
            long timeRemaining = election.getEndTime().getTime() - now;
            int hoursRemaining = (int) (timeRemaining / (1000 * 60 * 60));
            int progressPercent = calculateProgressPercent(election);


            request.setAttribute("electionId", electionId);

            request.setAttribute("election", election);
            request.setAttribute("candidateList", candidates);
            request.setAttribute("hoursRemaining", hoursRemaining);
            request.setAttribute("progressPercent", progressPercent);
            request.setAttribute("voterToken", generateVoterToken(request));

            Integer userId = SessionUtil.getUserValueFromSession(request, AuthUser::getUserId);
            request.setAttribute("user_id", userId);


            request.setAttribute("voteSuccess", true);
            request.getRequestDispatcher("/WEB-INF/pages/voter/cast-vote.jsp").forward(request, response);
        } else {
            logger.error("Failed to cast vote for election ID: {}", electionId);
            request.setAttribute("error", "Failed to cast vote. Please try again.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }


    private int calculateProgressPercent(Election election) {
        long totalDuration = election.getEndTime().getTime() - election.getStartTime().getTime();
        long elapsed = System.currentTimeMillis() - election.getStartTime().getTime();
        return (int) ((elapsed * 100) / totalDuration);
    }

    private String generateVoterToken(HttpServletRequest request) {
        // In a real app, generate a unique token for the voter
        return "VOTE-" + request.getSession().getId().substring(0, 6).toUpperCase();
    }
}