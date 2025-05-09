package com.voteSphere.controller;

import java.io.IOException;
import java.sql.SQLException;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.voteSphere.model.AuthUser;
import com.voteSphere.model.User;
import com.voteSphere.service.UserService;
import com.voteSphere.util.CookieUtil;
import com.voteSphere.util.SessionHandler;
import com.voteSphere.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LogManager.getLogger(LoginServlet.class);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String voterId = request.getParameter("voter_id");
		String password = request.getParameter("password");
		logger.info("User logged in: Progress");


		boolean isValid = true;

		if (ValidationUtil.isNullOrEmpty(voterId)) {
			request.setAttribute("voter_id_error", "Voter ID is required");
			isValid = false;
		}

		if (ValidationUtil.isNullOrEmpty(password)) {
			request.setAttribute("password_error", "Password is required");
			isValid = false;
		}

		if (!isValid) {
			request.setAttribute("error", "Please fill all required fields");
		}

		try {
			AuthUser authUser = authenticate(request, response, voterId, password.trim());

			if (authUser == null) {
				logger.info("Login failed: Incorrect password for Voter ID: " + voterId);
				request.setAttribute("error", "Invalid Voter ID or password");
				request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
				return;
			}

			// Generate Session
			SessionHandler.createAndUpdateUserObjectSession(request, authUser);
			
			// Set cookies
			CookieUtil.addUserRoleCookie(response, authUser.getRole());
			CookieUtil.addLoginTimeCookie(response);
			CookieUtil.addRememberMeCookie(request, response, String.valueOf(authUser.getUserId()));


			// Log the successful login
			logger.info("User logged in: " + authUser.getUserId());
			
			response.sendRedirect(request.getContextPath() + "/dashboard");
		


		} catch (Exception e) {
			logger.error("Login error for voter ID: " + voterId, e);
			request.setAttribute("error", "Login failed. Please try again.");
			request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
		}
	}


	

	public static AuthUser authenticate(HttpServletRequest request, HttpServletResponse response, String voterId,
			String password) throws SQLException {

		User user = UserService.getUserByVoterId(request, response, voterId);

		if (user == null) {
			request.setAttribute("login_error", "Account doesn't exist.");
			return null;
		}

		if (BCrypt.verifyer().verify(password.toCharArray(), user.getPassword().toCharArray()).verified) {
			return new AuthUser(user);
		}
		request.setAttribute("password_login_error", "Password doesn't match.");

		return null;
	}
	
	
	
	
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);

	}
	
	

}
