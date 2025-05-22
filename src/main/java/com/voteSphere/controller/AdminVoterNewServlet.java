package com.voteSphere.controller;

import java.io.IOException;
import java.util.List;

import com.voteSphere.service.UnverifiedUserService;
import com.voteSphere.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.voteSphere.model.User;
import com.voteSphere.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/voter/*")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,     
        maxRequestSize = 1024 * 1024 * 50)  
public class AdminVoterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(AdminVoterServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/") || pathInfo.equalsIgnoreCase("/list")) {
                handleListVoters(request, response);
            }
            else if (pathInfo.startsWith("/new")) {
                handleAddVoterForm(request, response);
            }
            else if (pathInfo.startsWith("/view/")) {
                String voterId = pathInfo.substring(6);
//                handleViewVoter(request, response, voterId);
            }
            else if (pathInfo.startsWith("/verify/")) {
                String voterId = pathInfo.substring(8);
//                handleVerifyVoter(request, response, voterId);
            }
            else if (pathInfo.equalsIgnoreCase("/unverified")) {


                
            }
            else {
                logger.warn("Unknown path requested: {}", pathInfo);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
            }
        } catch (Exception e) {
            logger.error("Error processing voter request", e);
            request.setAttribute("error", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        logger.debug("Processing voter path: {}", pathInfo);

        try {
            if (pathInfo == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
            }
            else if (pathInfo.equalsIgnoreCase("/new")) {
//                handleAddVoter(request, response);
            }
            else if (pathInfo.startsWith("/update/")) {
                String voterId = pathInfo.substring(8);
//                handleUpdateVoter(request, response, voterId);
            }
            else if (pathInfo.startsWith("/delete/")) {
                String voterId = pathInfo.substring(8);
//                handleDeleteVoter(request, response, voterId);
            }
            else if (pathInfo.startsWith("/search/")) {
                handleSearch(request, response);
            }
            else {
                logger.warn("Invalid path requested: {}", pathInfo);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
            }
        } catch (Exception e) {
            logger.error("Error processing voter action", e);
            request.setAttribute("error", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void handleAddVoterForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("Displaying voter add form");
        request.getRequestDispatcher("/WEB-INF/pages/admin/add-voter.jsp").forward(request, response);
        logger.info("Successfully displayed voter add form");
    }

    private void handleListVoters(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("Listing all voters");
        List<User> voters = UserService.getAllUsers();
        request.setAttribute("voters", voters);
        getStats(request, response);
        System.out.println(voters);
        request.getRequestDispatcher("/WEB-INF/pages/admin/voters.jsp").forward(request, response);
        logger.info("Successfully listed {} voters", voters.size());
    }

    private void getStats(HttpServletRequest request, HttpServletResponse response)
    {
        logger.debug("Listing all voter stats");
        request.setAttribute("totalVoters", UserService.getAllUsers().size());
        request.setAttribute("activeVoters",UserService.getAllUsers().size());
        request.setAttribute("pendingVoters",UnverifiedUserService.getAllUnverifiedUsers().size());

    }

    private void handleListVotersByQuery(HttpServletRequest request, HttpServletResponse response, String searchQuery)
            throws ServletException, IOException {
        logger.debug("Listing filtered voters for search query: {}", searchQuery);
        List<User> voters = UserService.getAllUsers();
        voters = User.searchUsers(voters, searchQuery);
        request.setAttribute("voters", voters);
        request.setAttribute("searchInput", searchQuery);
        request.getRequestDispatcher("/WEB-INF/pages/admin/voters.jsp").forward(request, response);
        logger.info("Successfully listed {} voters", voters.size());
    }
//
//    private void handleUnverifiedVoters(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        logger.debug("Listing unverified voters");
//        List<Voter> voters = UserService.getUnverifiedVoters();
//        request.setAttribute("voters", voters);
//        request.getRequestDispatcher("/WEB-INF/pages/admin/unverified-voters.jsp").forward(request, response);
//        logger.info("Successfully listed {} unverified voters", voters.size());
//    }
//
//    private void handleViewVoter(HttpServletRequest request, HttpServletResponse response, String voterId)
//            throws ServletException, IOException {
//        logger.debug("Viewing voter with ID: {}", voterId);
//
//        if (ValidationUtil.isNullOrEmpty(voterId) || !ValidationUtil.isNumeric(voterId)) {
//            logger.warn("Invalid voter ID: {}", voterId);
//            request.setAttribute("error", "Invalid voter ID");
//            request.getRequestDispatcher("/error.jsp").forward(request, response);
//            return;
//        }
//
//        User voter = UserService.getVoterById(Integer.parseInt(voterId));
//        if (voter == null) {
//            logger.warn("Voter not found with ID: {}", voterId);
//            request.setAttribute("error", "Voter not found");
//            request.getRequestDispatcher("/error.jsp").forward(request, response);
//            return;
//        }
//
//        request.setAttribute("voter", voter);
//        request.getRequestDispatcher("/WEB-INF/pages/admin/voter-details.jsp").forward(request, response);
//        logger.info("Successfully viewed voter ID: {}", voterId);
//    }
//
//    private void handleVerifyVoter(HttpServletRequest request, HttpServletResponse response, String voterId)
//            throws ServletException, IOException {
//        logger.debug("Verifying voter with ID: {}", voterId);
//
//        if (ValidationUtil.isNullOrEmpty(voterId) || !ValidationUtil.isNumeric(voterId)) {
//            logger.warn("Invalid voter ID for verification: {}", voterId);
//            request.setAttribute("error", "Invalid voter ID");
//            request.getRequestDispatcher("/error.jsp").forward(request, response);
//            return;
//        }
//
//        boolean success = UserService.verifyVoter(Integer.parseInt(voterId));
//        if (success) {
//            logger.info("Successfully verified voter ID: {}", voterId);
//            response.sendRedirect(request.getContextPath() + "/admin/voter/view/" + voterId);
//        } else {
//            logger.warn("Failed to verify voter ID: {}", voterId);
//            request.setAttribute("error", "Failed to verify voter");
//            request.getRequestDispatcher("/error.jsp").forward(request, response);
//        }
//    }
//
    private void handleSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchQuery = request.getParameter("searchInput");
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            handleListVoters(request, response);
            return;
        }

        handleListVotersByQuery(request, response, searchQuery);
    }
//
//    private void handleAddVoter(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        logger.debug("Attempting to add new voter");
//
//        boolean success = UserService.addVoter(request, response);
//        if (success) {
//            logger.info("Successfully added new voter");
//            response.sendRedirect(request.getContextPath() + "/admin/voter/list");
//        } else {
//            logger.warn("Failed to add new voter");
//            request.getRequestDispatcher("/WEB-INF/pages/admin/add-voter.jsp").forward(request, response);
//        }
//    }
//
//    private void handleUpdateVoter(HttpServletRequest request, HttpServletResponse response, String voterId)
//            throws ServletException, IOException {
//        logger.debug("Updating voter with ID: {}", voterId);
//
//        if (ValidationUtil.isNullOrEmpty(voterId) || !ValidationUtil.isNumeric(voterId)) {
//            logger.warn("Invalid voter ID for update: {}", voterId);
//            request.setAttribute("error", "Invalid voter ID");
//            request.getRequestDispatcher("/error.jsp").forward(request, response);
//            return;
//        }
//
//        boolean success = UserService.updateVoter(request, response, Integer.parseInt(voterId));
//        if (success) {
//            logger.info("Successfully updated voter ID: {}", voterId);
//            response.sendRedirect(request.getContextPath() + "/admin/voter/view/" + voterId);
//        } else {
//            logger.warn("Failed to update voter ID: {}", voterId);
//            request.getRequestDispatcher("/WEB-INF/pages/admin/edit-voter.jsp").forward(request, response);
//        }
//    }
//
//    private void handleDeleteVoter(HttpServletRequest request, HttpServletResponse response, String voterId)
//            throws ServletException, IOException {
//        logger.debug("Deleting voter with ID: {}", voterId);
//
//        if (ValidationUtil.isNullOrEmpty(voterId) || !ValidationUtil.isNumeric(voterId)) {
//            logger.warn("Invalid voter ID for deletion: {}", voterId);
//            request.setAttribute("error", "Invalid voter ID");
//            request.getRequestDispatcher("/error.jsp").forward(request, response);
//            return;
//        }
//
//        boolean success = UserService.deleteVoter(Integer.parseInt(voterId));
//        if (success) {
//            logger.info("Successfully deleted voter ID: {}", voterId);
//            response.sendRedirect(request.getContextPath() + "/admin/voter/list");
//        } else {
//            logger.warn("Failed to delete voter ID: {}", voterId);
//            request.setAttribute("error", "Failed to delete voter");
//            request.getRequestDispatcher("/error.jsp").forward(request, response);
//        }
//    }
}