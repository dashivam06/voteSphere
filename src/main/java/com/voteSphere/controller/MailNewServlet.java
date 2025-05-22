package com.voteSphere.controller;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.voteSphere.service.UnverifiedUserService;
import com.voteSphere.util.JwtUtil;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/mail", asyncSupported = true)
public class MailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger(MailServlet.class);

	public MailServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		String token = request.getParameter("email_verification_token");

		logger.debug("Email verification request received. Action: {}, Token Present: {}", action, token != null);

		try {
			if (action == null) {
				action = "verify";

			}

			switch (action.toLowerCase()) {
				case "verify":
					handleVerification(request, response, token);
					break;

				case "resend":
					handleResend(request, response);
					break;

				default:
					logger.warn("Invalid action requested: {}", action);
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
			}
		} catch (Exception e) {
			logger.error("Email verification processing failed", e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Verification failed");
		}
	}

	private void handleVerification(HttpServletRequest request, HttpServletResponse response, String token)
			throws IOException {
		if (token == null || token.isEmpty()) {
			logger.warn("Missing verification token");
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Verification token is missing!!!");
			return;
		}

		try {
			JwtUtil jwtUtil = new JwtUtil();

			if (!jwtUtil.validateToken(token)) {
				logger.warn("Invalid verification token");
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid verification token");
				return;
			}

			Integer userId = jwtUtil.extractUserID(token);
			String email = jwtUtil.extractEmail(token);

			logger.info("Processing email verification for user ID: {}, Email: {}", userId, email);

			if (UnverifiedUserService.setIsEmailVerifiedTrue(request, response, userId)) {
				logger.info("Email verified successfully for user ID: {}", userId);
				UnverifiedUserService.promoteToVerifiedUser(request, response, userId);
				response.sendRedirect("dashboard?verified=true");
			} else {
				logger.error("Failed to verify email for user ID: {}", userId);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Verification failed");
			}

		} catch (JwtException e) {
			logger.warn("JWT validation failed: {}", e.getMessage());
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid verification token");
		}
	}

	private void handleResend(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String email = request.getParameter("email");

		if (email == null || email.isEmpty()) {
			logger.warn("Resend request missing email parameter");
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email address required");
			return;
		}

		try {
			logger.info("Processing resend request for email: {}", email);
			// Implement your resend logic here
			response.sendRedirect("login?resent=true");
		} catch (Exception e) {
			logger.error("Failed to resend verification email to: {}", email, e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to resend email");
		}
	}

}
