package com.voteSphere.controller;

import java.io.IOException;
import java.util.List;

import com.voteSphere.model.User;
import com.voteSphere.service.UserService;
import com.voteSphere.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public UserServlet() {
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
            User user = UserService.getUserByEmail(request, response, email);
            if (user != null) {
                request.setAttribute("user", user);
                request.getRequestDispatcher("/userDetails.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "User not found with email: " + email);
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
            return;
        }

        // Get by voter ID
        if (voterId != null && !voterId.trim().isEmpty()) {
            User user = UserService.getUserByVoterId(request, response, voterId);
            if (user != null) {
                request.setAttribute("user", user);
                request.getRequestDispatcher("/userDetails.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "User not found with voter ID: " + voterId);
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
            return;
        }

        // Get by ID or all users
        if (userId == null || userId.trim().isEmpty()) {
            // No ID passed: return all users
            List<User> allUsers = UserService.getAllUsers();
            request.setAttribute("users", allUsers);
            request.getRequestDispatcher("/userList.jsp").forward(request, response);
        } else {
            // Validate ID
            if (!ValidationUtil.isNumeric(userId)) {
                request.setAttribute("error", "Invalid user ID format");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            // ID passed: return specific user
            User user = UserService.getUserById(request, response, Integer.parseInt(userId));
            if (user != null) {
                request.setAttribute("user", user);
                request.getRequestDispatcher("/userDetails.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "User not found with ID: " + userId);
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        boolean added = UserService.registerUser(request, response);

        if (added) {
            response.sendRedirect("user");
        } else {
            request.setAttribute("error", "Failed to create user");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
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

        boolean updated = UserService.updateUser(request, response, Integer.parseInt(userId));

        if (updated) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found to update.");
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

        boolean deleted = UserService.deleteUser(request, response, Integer.parseInt(userId));

        if (deleted) {
            response.sendRedirect("user"); // Successfully deleted, redirect to user list
        } else {
            request.setAttribute("error", "Failed to delete the user. Please try again.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
      
        }}
    }