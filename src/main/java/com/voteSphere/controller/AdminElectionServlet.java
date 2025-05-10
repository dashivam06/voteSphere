package com.voteSphere.controller;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.voteSphere.model.Election;
import com.voteSphere.model.ElectionResult;
import com.voteSphere.service.ElectionService;
import com.voteSphere.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/election/*")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 50)   // 50 MB
public class AdminElectionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(AdminElectionServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/") || pathInfo.equalsIgnoreCase("/list")) {
                handleListElections(request, response);
            }
            else if (pathInfo.startsWith("/view/")) {
                String electionId = pathInfo.substring(6);
                handleViewElection(request, response, electionId);
            }
            else if (pathInfo.startsWith("/results/")) {
                String electionId = pathInfo.substring(9);
                handleViewResults(request, response, electionId);
            }
            else if (pathInfo.startsWith("/add/")) {
                handleAddElectionForm(request, response);
            }
            else {
                logger.warn("Unknown path requested: {}", pathInfo);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
            }
        } catch (Exception e) {
            logger.error("Error processing election request", e);
            request.setAttribute("error", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void handleAddElectionForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("Displaying election add form");
        request.getRequestDispatcher("/WEB-INF/pages/admin/add-election.jsp").forward(request, response);
        logger.info("Successfully displayed election add form");
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        logger.debug("Processing election path: {}", pathInfo);

        try {
            if (pathInfo == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
            }
            else if (pathInfo.equalsIgnoreCase("/add/")) {
                handleAddElection(request, response);
            }
            else if (pathInfo.startsWith("/update/")) {
                String electionId = pathInfo.substring(8);
                handleUpdateElection(request, response, electionId);
            }
            else if (pathInfo.startsWith("/delete/")) {
                String electionId = pathInfo.substring(8);
                handleDeleteElection(request, response, electionId);
            }
            else if (pathInfo.equalsIgnoreCase("/search")) {
                handleSearch(request, response);
            }
            else {
                logger.warn("Invalid path requested: {}", pathInfo);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
            }
        } catch (Exception e) {
            logger.error("Error processing election action", e);
            request.setAttribute("error", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    protected static void handleListElections(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("Listing all elections");
        List<Election> elections = ElectionService.getAllElections();
        request.setAttribute("elections", elections);
        request.getRequestDispatcher("/WEB-INF/pages/admin/elections.jsp").forward(request, response);
        logger.info("Successfully listed {} elections", elections.size());
    }

    private void handleListElectionsByQuery(HttpServletRequest request, HttpServletResponse response, String searchQuery)
            throws ServletException, IOException {
        logger.debug("Listing filtered elections for search query: {}", searchQuery);
        List<Election> elections = ElectionService.getAllElections();
        elections = Election.searchElections(elections, searchQuery);
        request.setAttribute("elections", elections);
        request.setAttribute("searchInput", searchQuery);
        request.getRequestDispatcher("/WEB-INF/pages/admin/elections.jsp").forward(request, response);
        logger.info("Successfully listed {} elections", elections.size());
    }

    private void handleViewElection(HttpServletRequest request, HttpServletResponse response, String electionId)
            throws ServletException, IOException {
        logger.debug("Viewing election with ID: {}", electionId);

        if (ValidationUtil.isNullOrEmpty(electionId) || !ValidationUtil.isNumeric(electionId)) {
            logger.warn("Invalid election ID: {}", electionId);
            request.setAttribute("error", "Invalid election ID");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        Election election = ElectionService.getElectionById(request, response, Integer.parseInt(electionId));
        if (election == null) {
            logger.warn("Election not found with ID: {}", electionId);
            request.setAttribute("error", "Election not found");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        request.setAttribute("election", election);
        request.getRequestDispatcher("/WEB-INF/pages/admin/election-details.jsp").forward(request, response);
        logger.info("Successfully viewed election ID: {}", electionId);
    }

    private void handleSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchQuery = request.getParameter("searchInput");
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            handleListElections(request, response);
            return;
        }

        handleListElectionsByQuery(request, response, searchQuery);
    }

    private void handleAddElection(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("Attempting to add new election");

        boolean success = ElectionService.addElection(request, response);
        if (success) {
            logger.info("Successfully added new election");
            response.sendRedirect(request.getContextPath() + "/admin/election/");
        } else {
            logger.warn("Failed to add new election");
            request.getRequestDispatcher("/WEB-INF/pages/admin/add-election.jsp").forward(request, response);
        }
    }

    private void handleUpdateElection(HttpServletRequest request, HttpServletResponse response, String electionId)
            throws ServletException, IOException {
        logger.debug("Updating election with ID: {}", electionId);

        if (ValidationUtil.isNullOrEmpty(electionId) || !ValidationUtil.isNumeric(electionId)) {
            logger.warn("Invalid election ID for update: {}", electionId);
            request.setAttribute("error", "Invalid election ID");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        boolean success = ElectionService.updateElection(request, response, Integer.parseInt(electionId));
        if (success) {
            logger.info("Successfully updated election ID: {}", electionId);
            response.sendRedirect(request.getContextPath() + "/admin/election/view/" + electionId);
        } else {
            logger.warn("Failed to update election ID: {}", electionId);
            request.getRequestDispatcher("/admin/edit-election.jsp").forward(request, response);
        }
    }

    private void handleDeleteElection(HttpServletRequest request, HttpServletResponse response, String electionId)
            throws ServletException, IOException {
        logger.debug("Deleting election with ID: {}", electionId);

        if (ValidationUtil.isNullOrEmpty(electionId) || !ValidationUtil.isNumeric(electionId)) {
            logger.warn("Invalid election ID for deletion: {}", electionId);
            request.setAttribute("error", "Invalid election ID");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        boolean success = ElectionService.deleteElection(request, response, Integer.parseInt(electionId));
        if (success) {
            logger.info("Successfully deleted election ID: {}", electionId);
            response.sendRedirect(request.getContextPath() + "/admin/election/");
        } else {
            logger.warn("Failed to delete election ID: {}", electionId);
            request.setAttribute("error", "Failed to delete election");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void handleViewResults(HttpServletRequest request, HttpServletResponse response, String electionId)
            throws ServletException, IOException {
        logger.debug("Viewing results for election ID: {}", electionId);

        if (ValidationUtil.isNullOrEmpty(electionId) || !ValidationUtil.isNumeric(electionId)) {
            logger.warn("Invalid election ID for results: {}", electionId);
            request.setAttribute("error", "Invalid election ID");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        int id = Integer.parseInt(electionId);
        List<ElectionResult> results = ElectionService.getElectionResults(request, response, id);
        int totalVotes = ElectionService.getTotalVotes(request, response, id);

        request.setAttribute("results", results);
        request.setAttribute("totalVotes", totalVotes);
        request.setAttribute("electionId", id);

        Election election = ElectionService.getElectionById(request, response, id);
        request.setAttribute("election", election);

        request.getRequestDispatcher("/WEB-INF/pages/admin/election-results.jsp").forward(request, response);
        logger.info("Successfully displayed results for election ID: {}", electionId);
    }
}