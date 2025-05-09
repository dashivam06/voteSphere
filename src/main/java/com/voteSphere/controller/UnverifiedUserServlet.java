package com.voteSphere.controller;

import java.io.IOException;
import java.util.List;

import com.voteSphere.model.UnverifiedUser;
import com.voteSphere.service.UnverifiedUserService;
import com.voteSphere.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/unverified-user")
public class UnverifiedUserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public UnverifiedUserServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userId = request.getParameter("id");
        String email = request.getParameter("email");
        String voterId = request.getParameter("voterId");

        // Get by email
        if (email != null && !email.trim().isEmpty()) {
            UnverifiedUser user = UnverifiedUserService.getUnverifiedUserByEmail(request, response, email);
            if (user != null) {
                request.setAttribute("unverifiedUser", user);
                request.getRequestDispatcher("/unverifiedUserDetails.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Unverified user not found with email: " + email);
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
            return;
        }

        // Get by voter ID
        if (voterId != null && !voterId.trim().isEmpty()) {
            UnverifiedUser user = UnverifiedUserService.getUnverifiedUserByVoterId(request, response, voterId);
            if (user != null) {
                request.setAttribute("unverifiedUser", user);
                request.getRequestDispatcher("/unverifiedUserDetails.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Unverified user not found with voter ID: " + voterId);
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
            return;
        }

        // Get by ID or all users
        if (userId == null || userId.trim().isEmpty()) {
            // No ID passed: return all unverified users
            List<UnverifiedUser> allUsers = UnverifiedUserService.getAllUnverifiedUsers();
            request.setAttribute("unverifiedUsers", allUsers);
            request.getRequestDispatcher("/unverifiedUserList.jsp").forward(request, response);
        } else {
            // Validate ID
            if (!ValidationUtil.isNumeric(userId)) {
                request.setAttribute("error", "Invalid unverified user ID format");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            // ID passed: return specific user
            UnverifiedUser user = UnverifiedUserService.getUnverifiedUserById(request, response, Integer.parseInt(userId));
            if (user != null) {
                request.setAttribute("unverifiedUser", user);
                request.getRequestDispatcher("/unverifiedUserDetails.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Unverified user not found with ID: " + userId);
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        boolean registered = UnverifiedUserService.registerUnverifiedUser(request, response);

        if (registered) {
            response.sendRedirect("unverified-user");
        } else {
            request.getRequestDispatcher("/registerUnverifiedUser.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userId = request.getParameter("id");
        if (userId == null || !ValidationUtil.isNumeric(userId)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID for update.");
            return;
        }

        boolean updated = UnverifiedUserService.updateUnverifiedUser(request, response, Integer.parseInt(userId));

        if (updated) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unverified user not found to update.");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userId = request.getParameter("id");

        // Validate the user ID
        if (userId == null || userId.trim().isEmpty() || !ValidationUtil.isNumeric(userId)) {
            request.setAttribute("error", "Invalid ID for deletion.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        boolean deleted = UnverifiedUserService.deleteUnverifiedUser(request, response, Integer.parseInt(userId));

        if (deleted) {
            response.sendRedirect("unverified-user"); // Successfully deleted, redirect to user list
        } else {
            request.setAttribute("error", "Failed to delete the unverified user. Please try again.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    // Additional endpoint for promoting to verified user
    protected void doPatch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String userId = request.getParameter("id");

        if (userId == null || !ValidationUtil.isNumeric(userId)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user ID");
            return;
        }

        int id = Integer.parseInt(userId);

        try {
            boolean success = false;
            if ("promote".equals(action)) {
                success = UnverifiedUserService.promoteToVerifiedUser(request, response, id);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                return;
            }

            if (success) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Promotion failed");
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}