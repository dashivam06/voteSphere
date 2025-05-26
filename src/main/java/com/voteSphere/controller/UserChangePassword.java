package com.voteSphere.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.voteSphere.dao.UserDao;
import com.voteSphere.exception.DatabaseConnectionException;
import com.voteSphere.model.AuthUser;
import com.voteSphere.model.User;
import com.voteSphere.service.UserService;
import com.voteSphere.util.CookieUtil;
import com.voteSphere.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;

@WebServlet("/voter/password-change")
public class UserChangePassword extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(UserChangePassword.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Redirecting user to profile page after password change.");
        response.sendRedirect(request.getContextPath() + "/profile");
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = request.getParameter(paramName);
            logger.info("Request Parameter - {}: {}", paramName, paramValue);
        }

        Integer loggedInUserId = SessionUtil.getUserValueFromSession(request, AuthUser::getUserId);

        if (loggedInUserId == null) {
            logger.warn("User not logged in. Redirecting to login page.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            boolean passwordChanged = UserService.updatePassword(request, response, loggedInUserId);

            if (passwordChanged) {
                logger.info("Password changed successfully for user ID: {}", loggedInUserId);
                request.setAttribute("success", "Password changed successfully.");
            } else {
                logger.warn("Password change failed for user ID: {}", loggedInUserId);
            }

        } catch (DatabaseConnectionException e) {
            logger.error("Database error while changing password for user ID: " + loggedInUserId, e);
            request.setAttribute("error", "Database connection error occurred.");
        } catch (Exception e) {
            logger.error("Unexpected error during password change for user ID: " + loggedInUserId, e);
            request.setAttribute("error", "Unexpected error occurred. Please try again.");
        }

        doGet(request, response);
    }
}

