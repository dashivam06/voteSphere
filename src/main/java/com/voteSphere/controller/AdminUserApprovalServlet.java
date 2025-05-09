package com.voteSphere.controller;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.voteSphere.model.UnverifiedUser;
import com.voteSphere.service.UnverifiedUserService;
import com.voteSphere.util.EmailService;
import com.voteSphere.util.ValidationUtil;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "AdminUserApprovalServlet", urlPatterns = { "/admin/account-requests" }, asyncSupported = true)
public class AdminUserApprovalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(AdminUserApprovalServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		try {
			if ("list".equalsIgnoreCase(action) || action == null) {
				logger.debug("Listing all unverified users");
				List<UnverifiedUser> unverifiedUsers = UnverifiedUserService.getAllUnverifiedUsers();
				request.setAttribute("unverifiedUsers", unverifiedUsers);
				request.getRequestDispatcher("/WEB-INF/pages/admin/account-requests.jsp").forward(request, response);
				logger.info("Successfully listed all unverified users");
			} else if ("view".equalsIgnoreCase(action)) {
				String userId = request.getParameter("id");
				logger.debug("Attempting to view user with ID: {}", userId);

				if (ValidationUtil.isNullOrEmpty(userId) || !ValidationUtil.isNumeric(userId)) {
					String errorMsg = "Invalid user ID: " + userId;
					logger.error(errorMsg);

					request.setAttribute("error", errorMsg);
					request.getRequestDispatcher("/error.jsp").forward(request, response);
					return;
				}

				UnverifiedUser user = UnverifiedUserService.getUnverifiedUserById(request, response,
						Integer.parseInt(userId));
				if (user == null) {
					String errorMsg = "User not found with ID: " + userId;
					logger.error(errorMsg);
					request.setAttribute("error", errorMsg);
					request.getRequestDispatcher("/error.jsp").forward(request, response);
					return;
				}

				request.setAttribute("user", user);
				request.getRequestDispatcher("/WEB-INF/pages/admin/account-requests.jsp").forward(request, response);
				logger.info("Successfully retrieved details for user ID: {}", userId);
			} else {
				String errorMsg = "Unknown action: " + action;
				logger.warn(errorMsg);
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, errorMsg);
			}
		} catch (Exception e) {
			logger.error("Error in doGet: ", e);
			request.setAttribute("error", "An error occurred: " + e.getMessage());
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		logger.debug("Processing POST request with action: {}", action);

		try {
			if ("approve".equalsIgnoreCase(action)) {
				handleApprove(request, response);
			} else if ("reject".equalsIgnoreCase(action)) {
				handleReject(request, response);
			} else if ("update".equalsIgnoreCase(action)) {
				handleUpdate(request, response);
			} else if ("delete".equalsIgnoreCase(action)) {
				handleDelete(request, response);
			} else {
				String errorMsg = "Unknown action: " + action;
				logger.warn(errorMsg);
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, errorMsg);
			}
		} catch (Exception e) {
			logger.error("Error in doPost: ", e);
			request.setAttribute("error", "An error occurred: " + e.getMessage());
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	private void handleApprove(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Get and validate user ID
		String userIdStr = request.getParameter("id");
		if (ValidationUtil.isNullOrEmpty(userIdStr) || !ValidationUtil.isNumeric(userIdStr)) {
			logger.warn("Invalid user ID format for approval: {}", userIdStr);
			request.setAttribute("error", "Invalid user ID provided");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
			return;
		}

		int userId = Integer.parseInt(userIdStr);
		logger.info("Admin approval process started for user ID: {}", userId);

		try {
			// Get user details
			UnverifiedUser unverifiedUser = UnverifiedUserService.getUnverifiedUserById(request, response, userId);
			if (unverifiedUser == null) {
				logger.error("No unverified user found with ID: {}", userId);
				request.setAttribute("error", "User not found");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				return;
			}

			// Set is_verified=true
			boolean verificationUpdated = UnverifiedUserService.setIsVerifiedTrue(request, response, userId);
			if (!verificationUpdated) {
				logger.error("Failed to set is_verified=true for user ID: {}", userId);
				request.setAttribute("error", "Failed to verify user account");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				return;
			}
			logger.info("Successfully set is_verified=true for user ID: {}", userId);
			
			try {

			// Get base URL before starting async operation
	        String baseUrl = EmailService.getBaseUrl(request);
	        ServletContext context  = request.getServletContext();
	        
	        // Start async email sending with required parameters only
	        EmailService.sendEmailVerificationAsync(
	        		context,
	            baseUrl,
	            unverifiedUser.getFirstName(),
	            unverifiedUser.getNotificationEmail(), 
	            userId
	        );
		} catch (Exception e) {
			logger.error("Email sending failed for user ID: {}. User was still rejected.", userId, e);
			// Continue despite email failure since rejection is already processed
		}
			
			request.setAttribute("success", "User verification initiated successfully");
			response.sendRedirect(request.getContextPath() + "/admin-users?action=list");

		} catch (NumberFormatException e) {
			logger.error("Invalid user ID conversion: {}", userIdStr, e);
			request.setAttribute("error", "Invalid user ID format");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		} catch (Exception e) {
			logger.error("Unexpected error during approval for user ID: {}", userId, e);
			request.setAttribute("error", "An unexpected error occurred");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	private void handleReject(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Get and validate user ID
		String userIdStr = request.getParameter("id");
		String reason = request.getParameter("reason");

		if (ValidationUtil.isNullOrEmpty(userIdStr) || !ValidationUtil.isNumeric(userIdStr)) {
			logger.warn("Invalid user ID format for rejection: {}", userIdStr);
			request.setAttribute("error", "Invalid user ID provided");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
			return;
		}

		int userId = Integer.parseInt(userIdStr);
		logger.info("Admin rejection process started for user ID: {} with reason: {}", userId, reason);

		try {
			// Get user details before deletion
			UnverifiedUser unverifiedUser = UnverifiedUserService.getUnverifiedUserById(request, response, userId);
			if (unverifiedUser == null) {
				logger.error("No unverified user found with ID: {}", userId);
				request.setAttribute("error", "User not found");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				return;
			}

			// Delete the unverified user
			boolean deleted = UnverifiedUserService.deleteUnverifiedUser(request, response, userId);

			if (!deleted) {
				logger.error("Failed to delete user ID: {}", userId);
				request.setAttribute("error", "Failed to reject user account");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				return;
			}
			logger.info("Successfully rejected and deleted user ID: {}", userId);

			// Send rejection notification email
			try {
				// Get base URL before starting async operation
		        String baseUrl = EmailService.getBaseUrl(request);
		        ServletContext context  = request.getServletContext();
		        
				EmailService.sendKycFailedEmailAsync(context, baseUrl, unverifiedUser.getFirstName(),
						unverifiedUser.getNotificationEmail(), reason != null ? reason
								: "We regret to inform you that your KYC verification was unsuccessful. "
										+ "This may be due to missing or invalid information in the documents provided. "
										+ "Please review your submission and try again. Thank you for your understanding.");
				
				logger.info("Rejection email sent to {} for user ID: {}", unverifiedUser.getNotificationEmail(),
						userId);
			} catch (Exception e) {
				logger.error("Email sending failed for user ID: {}. User was still rejected.", userId, e);
				// Continue despite email failure since rejection is already processed
			}

			request.setAttribute("success", "User rejected successfully");
			response.sendRedirect(request.getContextPath() + "/admin-users?action=list");

		} catch (NumberFormatException e) {
			logger.error("Invalid user ID conversion: {}", userIdStr, e);
			request.setAttribute("error", "Invalid user ID format");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		} catch (Exception e) {
			logger.error("Unexpected error during rejection for user ID: {}", userId, e);
			request.setAttribute("error", "An unexpected error occurred");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	private void handleUpdate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userId = request.getParameter("id");
		logger.info("Attempting to update user with ID: {}", userId);

		if (ValidationUtil.isNullOrEmpty(userId) || !ValidationUtil.isNumeric(userId)) {
			String errorMsg = "Invalid user ID for update: " + userId;
			logger.error(errorMsg);
			request.setAttribute("error", errorMsg);
			request.getRequestDispatcher("/error.jsp").forward(request, response);
			return;
		}

		boolean updated = UnverifiedUserService.updateUnverifiedUser(request, response, Integer.parseInt(userId));

		if (updated) {
			logger.info("Successfully updated user ID: {}", userId);
			request.setAttribute("success", "User information updated successfully");
			response.sendRedirect(request.getContextPath() + "/admin-users?action=view&id=" + userId);
		} else {
			String errorMsg = "Failed to update user ID: " + userId;
			logger.error(errorMsg);
			request.setAttribute("error", errorMsg);
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	private void handleDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userId = request.getParameter("id");
		logger.info("Attempting to delete user with ID: {}", userId);

		if (ValidationUtil.isNullOrEmpty(userId) || !ValidationUtil.isNumeric(userId)) {
			String errorMsg = "Invalid user ID for deletion: " + userId;
			logger.error(errorMsg);
			request.setAttribute("error", errorMsg);
			request.getRequestDispatcher("/error.jsp").forward(request, response);
			return;
		}

		boolean deleted = UnverifiedUserService.deleteUnverifiedUser(request, response, Integer.parseInt(userId));

		if (deleted) {
			logger.info("Successfully deleted user ID: {}", userId);
			request.setAttribute("success", "User deleted successfully");
			response.sendRedirect(request.getContextPath() + "/admin-users?action=list");
		} else {
			String errorMsg = "Failed to delete user ID: " + userId;
			logger.error(errorMsg);
			request.setAttribute("error", errorMsg);
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
}