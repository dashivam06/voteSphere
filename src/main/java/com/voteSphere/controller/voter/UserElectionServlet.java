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

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/election/*")
public class UserElectionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                handleListElections(request, response);
            }
            else if (pathInfo.startsWith("/view/")) {
                handleViewElectionDetail(request, response);
            }
            else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request");
            }
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo != null && pathInfo.equals("/vote")) {
                handleVote(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            }
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void handleListElections(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Election> runningElections = ElectionService.getRunningElections();
        List<Election> upcomingElections = ElectionService.getUpcomingElections();
        List<Election> pastElections = ElectionService.getPastElections();

        Integer userId = SessionUtil.getUserValueFromSession(request, AuthUser::getUserId);
        Map<Integer, Boolean> hasUserVotedMap = new HashMap<>();

        for (Election election : runningElections) {
            boolean hasVoted = VoteService.hasUserVotedInElection(userId, election.getId());
            hasUserVotedMap.put(election.getId(), hasVoted);
        }

        request.setAttribute("activeElections", runningElections);
        request.setAttribute("upcomingElections", upcomingElections);
        request.setAttribute("pastElections", pastElections);
        request.setAttribute("hasUserVotedMap", hasUserVotedMap);

        request.getRequestDispatcher("/WEB-INF/pages/voter/elections.jsp").forward(request, response);
    }

    private void handleViewElectionDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {

        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");

        if (pathParts.length < 3) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid election ID format");
            return;
        }

        String electionIdStr = pathParts[2]; // The part after "/view/"

        if (!ValidationUtil.isNumeric(electionIdStr)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid election ID");
            return;
        }

        int electionId = Integer.parseInt(electionIdStr);
        Election election = ElectionService.getElectionById(electionId);

        if (election == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Election not found");
            return;
        }

        List<Candidate> candidates = ElectionService.getCandidatesForElection(electionId);

        String dateStr = election.getDate().toString(); // assuming it's a String
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = sdf.parse(dateStr);
        request.setAttribute("parsedDate", parsedDate);

        request.setAttribute("election", election);
        request.setAttribute("candidates", candidates);

        request.getRequestDispatcher("/WEB-INF/pages/voter/election-details.jsp").forward(request, response);
    }
    private void handleVote(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String electionId = request.getParameter("election_id");
        String candidateId = request.getParameter("candidate_id");
        Integer userId = SessionUtil.getUserValueFromSession(request, AuthUser::getUserId);

        if (!ValidationUtil.isNumeric(electionId) || !ValidationUtil.isNumeric(candidateId)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid parameters");
            return;
        }

        boolean success = VoteService.addVote(
               request, response);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/election");
        } else {
            request.setAttribute("error", "Failed to cast vote. Please try again.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}