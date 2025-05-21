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

@WebServlet("/vote")
public class VoteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public VoteServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String electionIdParam = request.getParameter("electionId");
        boolean isValidElectionId = ValidationUtil.isNumeric(electionIdParam);

        if (electionIdParam == null || electionIdParam.trim().isEmpty() || !isValidElectionId) {
            request.setAttribute("error", "Invalid Election ID provided.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        Integer electionId = Integer.parseInt(electionIdParam);

        Map<Integer, Integer> partyVotes = VoteService.getAllPartyVotesInElection(request, response, electionId);

        if (partyVotes.isEmpty()) {
            request.setAttribute("error", "No votes found for the given election.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } else {
            request.setAttribute("partyVotes", partyVotes);
            request.getRequestDispatcher("/voteList.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String electionIdParam = request.getParameter("election_id");

        if ( !ValidationUtil.isNumeric(electionIdParam)) {
            request.setAttribute("error", "Invalid Election Id for voting.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        int electionId = Integer.parseInt(electionIdParam);
        


        boolean voted = VoteService.addVote(request, response);

        if (voted) {
            response.sendRedirect("vote?electionId=" + electionId);
            } else {
            request.setAttribute("error", "Failed to cast vote. Please try again.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String voteIdParam = request.getParameter("id");

        if (voteIdParam == null
            || !ValidationUtil.isNumeric(voteIdParam)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Voter ID for update.");
            return;
        }

        int voteId = Integer.parseInt(voteIdParam);

        boolean updated = VoteService.updateVote(request, response, voteId);

        if (updated) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Vote not found to update.");
        }
    }


    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String voteIdParam = request.getParameter("id");

        if (voteIdParam == null || voteIdParam.trim().isEmpty() || !ValidationUtil.isNumeric(voteIdParam)) {
            request.setAttribute("vote_id_error", "Invalid ID for deletion.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        int voteId = Integer.parseInt(voteIdParam);

        boolean deleted = VoteService.deleteVote(request, response, voteId);

        if (deleted) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            request.setAttribute("error", "Failed to delete the vote. Please try again.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

}
