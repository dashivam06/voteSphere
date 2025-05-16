package com.voteSphere.controller;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.voteSphere.model.UnverifiedUser;
import com.voteSphere.service.UnverifiedUserService;
import com.voteSphere.util.MailUtil;
import com.voteSphere.util.ValidationUtil;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/user-approval/*")
public class AdminUserApprovalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(AdminUserApprovalServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pathInfo = request.getPathInfo();

		try {
			if (pathInfo == null || pathInfo.equals("/") || pathInfo.equalsIgnoreCase("/list")) {
				handleListUnverifiedUsers(request, response);
			}
			else if (pathInfo.startsWith("/view/")) {
				String userId = pathInfo.substring(6);
				System.out.println("In View : "+pathInfo+" ID: "+userId);
				handleViewUser(request, response, userId);
			}
			else {
				logger.warn("Unknown path requested: {}", pathInfo);
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
			}
		} catch (Exception e) {
			logger.error("Error processing user approval request", e);
			request.setAttribute("error", "An error occurred: " + e.getMessage());
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		logger.debug("Processing user approval path: {}", pathInfo);

		try {
			if (pathInfo == null) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
			}
			else if (pathInfo.startsWith("/approve/")) {
				String userId = pathInfo.substring(9);
				handleApproveUser(request, response, userId);
			}
			else if (pathInfo.startsWith("/reject/")) {
				String userId = pathInfo.substring(8);
				handleRejectUser(request, response, userId);
			}
			else if (pathInfo.startsWith("/update/")) {
				String userId = pathInfo.substring(8);
				handleUpdateUser(request, response, userId);
			}
			else if (pathInfo.startsWith("/delete/")) {
				String userId = pathInfo.substring(8);
				handleDeleteUser(request, response, userId);
			}
			else if (pathInfo.equalsIgnoreCase("/search")) {
				handleSearch(request, response);
			}
			else {
				logger.warn("Invalid path requested: {}", pathInfo);
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
			}
		} catch (Exception e) {
			logger.error("Error processing user approval action", e);
			request.setAttribute("error", "An error occurred: " + e.getMessage());
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	private void handleListUnverifiedUsers(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.debug("Listing all unverified users");
		List<UnverifiedUser> unverifiedUsers = UnverifiedUserService.getAllUnverifiedUsers();
		request.setAttribute("unverifiedUsers", unverifiedUsers);
		request.getRequestDispatcher("/WEB-INF/pages/admin/account-requests.jsp").forward(request, response);
		logger.info("Successfully listed {} unverified users", unverifiedUsers.size());
	}

	private void handleViewUser(HttpServletRequest request, HttpServletResponse response, String userId)
			throws ServletException, IOException {
		logger.debug("Viewing user with ID: {}", userId);

		if (ValidationUtil.isNullOrEmpty(userId) || !ValidationUtil.isNumeric(userId)) {
			logger.warn("Invalid user ID: {}", userId);
			request.setAttribute("error", "Invalid user ID");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
			return;
		}

		UnverifiedUser user = UnverifiedUserService.getUnverifiedUserById(Integer.parseInt(userId));
		if (user == null) {
			logger.warn("User not found with ID: {}", userId);
			request.setAttribute("error", "User not found");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
			return;
		}

		request.setAttribute("user", user);
		request.getRequestDispatcher("/WEB-INF/pages/admin/account-request-details.jsp").forward(request, response);
		logger.info("Successfully viewed user ID: {}", userId);
	}

	private void handleApproveUser(HttpServletRequest request, HttpServletResponse response, String userId)
			throws ServletException, IOException {
		logger.debug("Approving user with ID: {}", userId);

		if (ValidationUtil.isNullOrEmpty(userId) || !ValidationUtil.isNumeric(userId)) {
			logger.warn("Invalid user ID for approval: {}", userId);
			request.setAttribute("error", "Invalid user ID");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
			return;
		}

		int id = Integer.parseInt(userId);
		UnverifiedUser user = UnverifiedUserService.getUnverifiedUserById(id);
		if (user == null) {
			logger.warn("User not found with ID: {}", userId);
			request.setAttribute("error", "User not found");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
			return;
		}

		boolean verificationUpdated = UnverifiedUserService.setIsVerifiedTrue(id);
		if (!verificationUpdated) {
			logger.error("Failed to verify user ID: {}", userId);
			request.setAttribute("error", "Failed to verify user account");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
			return;
		}

		// Send approval email asynchronously
		try {
			ServletContext context = request.getServletContext();
			String baseUrl = MailUtil.getBaseUrl(request);
			MailUtil.sendEmailVerificationAsync(
					context,
					baseUrl,
					user.getFirstName(),
					user.getNotificationEmail(),
					id
			);
		} catch (Exception e) {
			logger.error("Email sending failed for user ID: {}", userId, e);
		}

		logger.info("Successfully approved user ID: {}", userId);
		response.sendRedirect(request.getContextPath() + "/admin/user-approval/");
	}

	private void handleRejectUser(HttpServletRequest request, HttpServletResponse response, String userId)
			throws ServletException, IOException {
		logger.debug("Rejecting user with ID: {}", userId);
		String reason = request.getParameter("reason");

		if (ValidationUtil.isNullOrEmpty(userId) || !ValidationUtil.isNumeric(userId)) {
			logger.warn("Invalid user ID for rejection: {}", userId);
			request.setAttribute("error", "Invalid user ID");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
			return;
		}

		int id = Integer.parseInt(userId);
		UnverifiedUser user = UnverifiedUserService.getUnverifiedUserById(id);
		if (user == null) {
			logger.warn("User not found with ID: {}", userId);
			request.setAttribute("error", "User not found");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
			return;
		}

		boolean deleted = UnverifiedUserService.deleteUnverifiedUser(id);
		if (!deleted) {
			logger.error("Failed to reject user ID: {}", userId);
			request.setAttribute("error", "Failed to reject user account");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
			return;
		}

		// Send rejection email asynchronously
		try {
			ServletContext context = request.getServletContext();
			String baseUrl = MailUtil.getBaseUrl(request);
			MailUtil.sendKycFailedEmailAsync(
					context,
					baseUrl,
					user.getFirstName(),
					user.getNotificationEmail(),
					reason != null ? reason : "Your application was rejected"
			);
		} catch (Exception e) {
			logger.error("Email sending failed for user ID: {}", userId, e);
		}

		logger.info("Successfully rejected user ID: {}", userId);
		response.sendRedirect(request.getContextPath() + "/admin/account-requests/");
	}

	private void handleUpdateUser(HttpServletRequest request, HttpServletResponse response, String userId)
			throws ServletException, IOException {
		logger.debug("Updating user with ID: {}", userId);

		if (ValidationUtil.isNullOrEmpty(userId) || !ValidationUtil.isNumeric(userId)) {
			logger.warn("Invalid user ID for update: {}", userId);
			request.setAttribute("error", "Invalid user ID");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
			return;
		}

		boolean updated = UnverifiedUserService.updateUnverifiedUser(request,null, Integer.parseInt(userId));
		if (updated) {
			logger.info("Successfully updated user ID: {}", userId);
			response.sendRedirect(request.getContextPath() + "/admin/user-approval/view/" + userId);
		} else {
			logger.warn("Failed to update user ID: {}", userId);
			request.setAttribute("error", "Failed to update user");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	private void handleDeleteUser(HttpServletRequest request, HttpServletResponse response, String userId)
			throws ServletException, IOException {
		logger.debug("Deleting user with ID: {}", userId);

		if (ValidationUtil.isNullOrEmpty(userId) || !ValidationUtil.isNumeric(userId)) {
			logger.warn("Invalid user ID for deletion: {}", userId);
			request.setAttribute("error", "Invalid user ID");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
			return;
		}

		boolean deleted = UnverifiedUserService.deleteUnverifiedUser(Integer.parseInt(userId));
		if (deleted) {
			logger.info("Successfully deleted user ID: {}", userId);
			response.sendRedirect(request.getContextPath() + "/admin/user-approval/list");
		} else {
			logger.warn("Failed to delete user ID: {}", userId);
			request.setAttribute("error", "Failed to delete user");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	private void handleSearch(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String searchQuery = request.getParameter("searchInput");
		if (searchQuery == null || searchQuery.trim().isEmpty()) {
			handleListUnverifiedUsers(request, response);
			return;
		}

		List<UnverifiedUser> users = UnverifiedUserService.getAllUnverifiedUsers();
		users = UnverifiedUser.searchUnverifiedUsers(users, searchQuery);
		request.setAttribute("unverifiedUsers", users);
		request.setAttribute("searchInput", searchQuery);
		request.getRequestDispatcher("/WEB-INF/pages/admin/account-requests.jsp").forward(request, response);
		logger.info("Successfully searched users with query: {}", searchQuery);
	}
}