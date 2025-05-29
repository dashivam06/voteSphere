package com.voteSphere.controller.voter;

import com.voteSphere.exception.DatabaseConnectionException;
import com.voteSphere.model.AuthUser;
import com.voteSphere.service.UserService;
import com.voteSphere.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet("/update-user-detail")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 50) // 50 MB
public class UserDetailUpdateServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(UserChangePassword.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Redirecting user to profile page after editing profile.");
        response.sendRedirect(request.getContextPath() + "/profile");
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer loggedInUserId = SessionUtil.getUserValueFromSession(request, AuthUser::getUserId);

        if (loggedInUserId == null) {
            logger.warn("User not logged in. Redirecting to login page.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        try {
            boolean profileUpdated = UserService.editUser(request, response,loggedInUserId);

            if (profileUpdated) {
                logger.info("Profile updated successfully for user ID: {}", loggedInUserId);
                HttpSession session = request.getSession(false);
                if(session != null)
                {
                    AuthUser authUser = new AuthUser(UserService.getUserById(loggedInUserId));
                    session.setAttribute("authenticated_user", authUser);
                }
                request.setAttribute("success", "Profile updated successfully.");
            } else {
                logger.warn("Profile update failed for user ID: {}", loggedInUserId);
                request.setAttribute("error", "Profile update failed.");
            }

        } catch (DatabaseConnectionException e) {
            logger.error("Database error while updating profile for user ID: " + loggedInUserId, e);
            request.setAttribute("error", "Database connection error occurred.");
        } catch (Exception e) {
            logger.error("Unexpected error during profile update for user ID: " + loggedInUserId, e);
            request.setAttribute("error", "Unexpected error occurred. Please try again.");
        }

        // Forward back to profile page (or call doGet if needed)
        doGet(request,response);
    }

}
