package com.voteSphere.controller;

import java.io.IOException;
import java.util.List;

import com.voteSphere.model.Candidate;
import com.voteSphere.service.CandidateService;
import com.voteSphere.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/candidate")
public class CandidateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public CandidateServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String candidateId = request.getParameter("id").trim();

        boolean isValidCandidateId = ValidationUtil.isNumeric(candidateId);

        if (!isValidCandidateId) {
            request.setAttribute("error", "Candidate not found. Invalid Candidate Id");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }

        if (candidateId == null || candidateId.isEmpty() || isValidCandidateId) {
            // No ID passed: return all candidates
            List<Candidate> allCandidates = CandidateService.getAllCandidates();
            request.setAttribute("candidates", allCandidates);
            request.getRequestDispatcher("/candidateList.jsp").forward(request, response);
        } else {
            // ID passed: return specific candidate
            Candidate candidate = CandidateService.getCandidateById(request, response, Integer.parseInt(candidateId));
            if (candidate != null) {
                request.setAttribute("candidate", candidate);
                request.getRequestDispatcher("/candidateDetails.jsp").forward(request, response);
            } else {
                // Handle not found
                request.setAttribute("error", "Candidate not found");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        boolean added = CandidateService.addCandidate(request, response);

        if (added) {
            response.sendRedirect("candidate");
        } else {
            request.setAttribute("error", "Failed to add candidate");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String candidateId = request.getParameter("id");
        if (candidateId == null || !ValidationUtil.isNumeric(candidateId)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID for update.");
            return;
        }

        boolean updated = CandidateService.updateCandidate(request, response, Integer.parseInt(candidateId));

        if (updated) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Candidate not found to update.");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String candidateId = request.getParameter("id");

        // Validate the candidate ID
        if (candidateId == null || candidateId.trim().isEmpty() || !ValidationUtil.isNumeric(candidateId)) {
            request.setAttribute("candidate_id_error", "Invalid ID for deletion.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        boolean deleted = false;

        // Attempt to delete the candidate
        deleted = CandidateService.deleteCandidate(request, response, Integer.parseInt(candidateId));

        if (deleted) {
            response.sendRedirect("candidate"); // Successfully deleted, redirect to candidate list
        } else {
            // Deletion failed, set an error and forward
            request.setAttribute("error", "Failed to delete the candidate. Please try again.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
