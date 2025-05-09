package com.voteSphere.controller;

import java.io.IOException;

import com.voteSphere.model.AuthUser;
import com.voteSphere.util.SessionHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private static final String ADMIN_DASHBOARD = "/WEB-INF/pages/admin/dashboard.jsp";
    private static final String VOTER_DASHBOARD = "/WEB-INF/pages/voter/dashboard.jsp";
    private static final String LOGIN_PAGE = "/login";
    private static final String ROLE_ADMIN = "admin";
    private static final String ROLE_USER = "voter";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String userRole = SessionHandler.getUserValueFromSession(request, AuthUser::getRole);

        // If no authenticated user or role is found
        if (userRole == null) {
            response.sendRedirect(request.getContextPath() + LOGIN_PAGE);
            return;
        }

        String destinationPage;

        switch (userRole.toLowerCase()) {
            case ROLE_ADMIN:
                destinationPage = ADMIN_DASHBOARD;
                break;
            case ROLE_USER:
                destinationPage = VOTER_DASHBOARD;
                break;
            default:
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Unauthorized role");
                return;
        }

        request.getRequestDispatcher(destinationPage).forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}