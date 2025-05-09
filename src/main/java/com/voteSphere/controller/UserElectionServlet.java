package com.voteSphere.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.voteSphere.model.Candidate;
import com.voteSphere.model.Election;
import com.voteSphere.service.ElectionService;
import com.voteSphere.service.VoteService;
import com.voteSphere.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/user-election")
public class UserElectionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(UserElectionServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");       
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = request.getParameter(paramName);
            response.getWriter().println(paramName + " = " + paramValue);
        }
        
        try {
            if ("list".equalsIgnoreCase(action) || action == null) {
                handleListRunningElections(request, response);
            } 
            else if ("viewCandidates".equalsIgnoreCase(action)) {
                handleViewCandidates(request, response);
            }
            else {
                logger.warn("Unknown action requested: {}", action);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            }
        } catch (Exception e) {
            logger.error("Error processing election request", e);
            request.setAttribute("error", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("vote".equalsIgnoreCase(action)) {
                handleVote(request, response);
            } else {
                logger.warn("Unknown POST action requested: {}", action);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            }
        } catch (Exception e) {
            logger.error("Error processing vote action", e);
            request.setAttribute("error", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    // 1. List all running elections
    private void handleListRunningElections(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("Listing running elections");
        List<Election> runningElections = ElectionService.getRunningElections();
        for(Election election : runningElections )
        {
        	System.out.println(election.toString());
        }
        request.setAttribute("runningElections", runningElections);
        request.getRequestDispatcher("/user/election-list.jsp").forward(request, response);
        logger.info("Successfully listed {} running elections", runningElections.size());
    }

    // 2. View candidates for a particular election
    private void handleViewCandidates(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String electionId = request.getParameter("election_id");
        logger.debug("Viewing candidates for election ID: {}", electionId);

        if (ValidationUtil.isNullOrEmpty(electionId) || !ValidationUtil.isNumeric(electionId)) {
            logger.warn("Invalid election ID: {}", electionId);
            request.setAttribute("error", "Invalid election ID");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        Election election = ElectionService.getElectionById(Integer.parseInt(electionId));
        if (election == null) {
            logger.warn("Election not found with ID: {}", electionId);
            request.setAttribute("error", "Election not found");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        List<Candidate> candidates = ElectionService.getCandidatesForElection(Integer.parseInt(electionId));
        request.setAttribute("election", election);
        request.setAttribute("candidates", candidates);
        request.getRequestDispatcher("/user/candidate-list.jsp").forward(request, response);
        logger.info("Successfully listed candidates for election ID: {}", electionId);
    }

    // 3. Allow the user to vote for a candidate
    private void handleVote(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String electionId = request.getParameter("election_id");
        String partyId = request.getParameter("party_id");

      
        boolean success = VoteService.addVote(request,response);
        if (success) {
            logger.info("Vote successfully casted for election ID: {} and candidate ID: {}", electionId, partyId);
            response.sendRedirect(request.getContextPath() + "/user-election?action=list");
        } else {
            logger.warn("Failed to cast vote for election ID: {} and Party ID: {}", electionId, partyId);
            request.setAttribute("error", "Failed to cast vote. Please try again.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
