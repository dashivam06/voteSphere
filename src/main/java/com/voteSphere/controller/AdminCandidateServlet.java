package com.voteSphere.controller;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.voteSphere.model.Candidate;
import com.voteSphere.service.CandidateService;
import com.voteSphere.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/candidate/*")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 50)   // 50 MB
public class AdminCandidateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(AdminCandidateServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/") || pathInfo.equalsIgnoreCase("/list")) {
                handleListCandidates(request, response);
            }
            else if (pathInfo.startsWith("/view/")) {
                String candidateId = pathInfo.substring(6);
                handleViewCandidate(request, response, candidateId);
            }
            else if (pathInfo.startsWith("/party/")) {
                String partyId = pathInfo.substring(7);
                handleCandidatesByParty(request, response, partyId);
            }
            else if (pathInfo.startsWith("/election/")) {
                String electionId = pathInfo.substring(10);
                handleCandidatesByElection(request, response, electionId);
            }
            else if (pathInfo.equalsIgnoreCase("/search")) {
                handleSearch(request, response);
            }
            else {
                logger.warn("Unknown path requested: {}", pathInfo);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
            }
        } catch (Exception e) {
            logger.error("Error processing candidate request", e);
            request.setAttribute("error", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        logger.debug("Processing candidate path: {}", pathInfo);

        try {
            if (pathInfo == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
            }
            else if (pathInfo.equalsIgnoreCase("/add")) {
                handleAddCandidate(request, response);
            }
            else if (pathInfo.startsWith("/update/")) {
                String candidateId = pathInfo.substring(8);
                handleUpdateCandidate(request, response, candidateId);
            }
            else if (pathInfo.startsWith("/delete/")) {
                String candidateId = pathInfo.substring(8);
                handleDeleteCandidate(request, response, candidateId);
            }
            else if (pathInfo.equalsIgnoreCase("/search")) {
                handleSearch(request, response);
            }
            else {
                logger.warn("Invalid path requested: {}", pathInfo);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
            }
        } catch (Exception e) {
            logger.error("Error processing candidate action", e);
            request.setAttribute("error", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void handleListCandidates(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("Listing all candidates");
        List<Candidate> candidates = CandidateService.getAllCandidates();
        request.setAttribute("candidates", candidates);
        request.getRequestDispatcher("/WEB-INF/pages/admin/candidates.jsp").forward(request, response);
        logger.info("Successfully listed {} candidates", candidates.size());
    }

    private void handleListCandidatesByQuery(HttpServletRequest request, HttpServletResponse response, String searchQuery)
            throws ServletException, IOException {
        logger.debug("Listing filtered candidates for search query: {}", searchQuery);
        List<Candidate> candidates = CandidateService.getAllCandidates();
//        candidates = Candidate.searchCandidates(candidates, searchQuery);
        request.setAttribute("candidates", candidates);
        request.setAttribute("searchInput", searchQuery);
        request.getRequestDispatcher("/WEB-INF/pages/admin/candidates.jsp").forward(request, response);
        logger.info("Successfully listed {} candidates", candidates.size());
    }

    private void handleViewCandidate(HttpServletRequest request, HttpServletResponse response, String candidateId)
            throws ServletException, IOException {
        logger.debug("Viewing candidate with ID: {}", candidateId);

        if (ValidationUtil.isNullOrEmpty(candidateId) || !ValidationUtil.isNumeric(candidateId)) {
            logger.warn("Invalid candidate ID: {}", candidateId);
            request.setAttribute("error", "Invalid candidate ID");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        Candidate candidate = CandidateService.getCandidateById(request, response, Integer.parseInt(candidateId));
        if (candidate == null) {
            logger.warn("Candidate not found with ID: {}", candidateId);
            request.setAttribute("error", "Candidate not found");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        request.setAttribute("candidate", candidate);
        request.getRequestDispatcher("/WEB-INF/pages/admin/candidate-details.jsp").forward(request, response);
        logger.info("Successfully viewed candidate ID: {}", candidateId);
    }

    private void handleCandidatesByParty(HttpServletRequest request, HttpServletResponse response, String partyId)
            throws ServletException, IOException {
        logger.debug("Listing candidates for party ID: {}", partyId);

        if (ValidationUtil.isNullOrEmpty(partyId) || !ValidationUtil.isNumeric(partyId)) {
            logger.warn("Invalid party ID: {}", partyId);
            request.setAttribute("error", "Invalid party ID");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        List<Candidate> candidates = CandidateService.getCandidatesByParty(request, response, Integer.parseInt(partyId));
        request.setAttribute("candidates", candidates);
        request.getRequestDispatcher("/WEB-INF/pages/admin/candidates.jsp").forward(request, response);
        logger.info("Successfully listed {} candidates for party ID: {}", candidates.size(), partyId);
    }

    private void handleCandidatesByElection(HttpServletRequest request, HttpServletResponse response, String electionId)
            throws ServletException, IOException {
        logger.debug("Listing candidates for election ID: {}", electionId);

        if (ValidationUtil.isNullOrEmpty(electionId) || !ValidationUtil.isNumeric(electionId)) {
            logger.warn("Invalid election ID: {}", electionId);
            request.setAttribute("error", "Invalid election ID");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        List<Candidate> candidates = CandidateService.getCandidatesByElection(request, response, Integer.parseInt(electionId));
        request.setAttribute("candidates", candidates);
        request.getRequestDispatcher("/WEB-INF/pages/admin/candidates.jsp").forward(request, response);
        logger.info("Successfully listed {} candidates for election ID: {}", candidates.size(), electionId);
    }

    private void handleSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchQuery = request.getParameter("searchInput");
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            handleListCandidates(request, response);
            return;
        }

        handleListCandidatesByQuery(request, response, searchQuery);
    }

    private void handleAddCandidate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("Attempting to add new candidate");

        boolean success = CandidateService.addCandidate(request, response);
        if (success) {
            logger.info("Successfully added new candidate");
            response.sendRedirect(request.getContextPath() + "/admin/candidate/list");
        } else {
            logger.warn("Failed to add new candidate");
            request.getRequestDispatcher("/WEB-INF/pages/admin/add-candidate.jsp").forward(request, response);
        }
    }

    private void handleUpdateCandidate(HttpServletRequest request, HttpServletResponse response, String candidateId)
            throws ServletException, IOException {
        logger.debug("Updating candidate with ID: {}", candidateId);

        if (ValidationUtil.isNullOrEmpty(candidateId) || !ValidationUtil.isNumeric(candidateId)) {
            logger.warn("Invalid candidate ID for update: {}", candidateId);
            request.setAttribute("error", "Invalid candidate ID");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        boolean success = CandidateService.updateCandidate(request, response, Integer.parseInt(candidateId));
        if (success) {
            logger.info("Successfully updated candidate ID: {}", candidateId);
            response.sendRedirect(request.getContextPath() + "/admin/candidate/view/" + candidateId);
        } else {
            logger.warn("Failed to update candidate ID: {}", candidateId);
            request.getRequestDispatcher("/WEB-INF/pages/admin/edit-candidate.jsp").forward(request, response);
        }
    }

    private void handleDeleteCandidate(HttpServletRequest request, HttpServletResponse response, String candidateId)
            throws ServletException, IOException {
        logger.debug("Deleting candidate with ID: {}", candidateId);

        if (ValidationUtil.isNullOrEmpty(candidateId) || !ValidationUtil.isNumeric(candidateId)) {
            logger.warn("Invalid candidate ID for deletion: {}", candidateId);
            request.setAttribute("error", "Invalid candidate ID");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        boolean success = CandidateService.deleteCandidate(request, response, Integer.parseInt(candidateId));
        if (success) {
            logger.info("Successfully deleted candidate ID: {}", candidateId);
            response.sendRedirect(request.getContextPath() + "/admin/candidate/list");
        } else {
            logger.warn("Failed to delete candidate ID: {}", candidateId);
            request.setAttribute("error", "Failed to delete candidate");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}