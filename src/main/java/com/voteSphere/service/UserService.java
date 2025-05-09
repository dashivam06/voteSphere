package com.voteSphere.service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.voteSphere.dao.UserDao;
import com.voteSphere.exception.DataAccessException;
import com.voteSphere.model.User;
import com.voteSphere.util.ImageUploadHandler;
import com.voteSphere.util.ValidationUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserService {

	private static final Logger logger = LogManager.getLogger(UserService.class);

	public static boolean registerUser(HttpServletRequest request, HttpServletResponse response) {
		boolean hasErrors = false;

		// Extract parameters from request
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String voterId = request.getParameter("voter_id");
		String email = request.getParameter("email");
		String phoneNumber = request.getParameter("phone_number");
		String password = request.getParameter("password");
		String dob = request.getParameter("dob");
		String gender = request.getParameter("gender");
		String permanentAddress = request.getParameter("permanent_address");
		String temporaryAddress = request.getParameter("temporary_address");

		String appRealPath = request.getServletContext().getRealPath("");
		long maxImageSize = 2 * 1024 * 1024; // 2MB

		// Process profile image upload
		String profileImage = ImageUploadHandler.processImageUpload(request, "profile_image", "profile_image_error",
				"user-profile", appRealPath, maxImageSize);

		// Process document images
		String imageHoldingCitizenship = ImageUploadHandler.processImageUpload(request, "image_holding_citizenship",
				"image_holding_citizenship_error", "user-docs", appRealPath, maxImageSize);
		String voterCardFront = ImageUploadHandler.processImageUpload(request, "voter_card_front",
				"voter_card_front_error", "user-docs", appRealPath, maxImageSize);
		String voterCardBack = ImageUploadHandler.processImageUpload(request, "voter_card_back",
				"voter_card_back_error", "user-docs", appRealPath, maxImageSize);
		String citizenshipFront = ImageUploadHandler.processImageUpload(request, "citizenship_front",
				"citizenship_front_error", "user-docs", appRealPath, maxImageSize);
		String citizenshipBack = ImageUploadHandler.processImageUpload(request, "citizenship_back",
				"citizenship_back_error", "user-docs", appRealPath, maxImageSize);
		String thumbPrint = ImageUploadHandler.processImageUpload(request, "thumb_print", "thumb_print_error",
				"user-docs", appRealPath, maxImageSize);

		if (profileImage == null || imageHoldingCitizenship == null || voterCardFront == null || voterCardBack == null
				|| citizenshipFront == null || citizenshipBack == null || thumbPrint == null) {
			hasErrors = true;

		}

		// Field validations
		if (ValidationUtil.isNullOrEmpty(firstName)) {
			request.setAttribute("firstName_error", "First name is required.");
			hasErrors = true;
		} else if (!ValidationUtil.isAlphabetic(firstName)) {
			request.setAttribute("firstName_error", "First name must contain only letters.");
			hasErrors = true;
		}

		if (ValidationUtil.isNullOrEmpty(lastName)) {
			request.setAttribute("lastName_error", "Last name is required.");
			hasErrors = true;
		} else if (!ValidationUtil.isAlphabetic(lastName)) {
			request.setAttribute("lastName_error", "Last name must contain only letters.");
			hasErrors = true;
		}

		if (ValidationUtil.isNullOrEmpty(voterId)) {
			request.setAttribute("voterId_error", "Voter ID is required.");
			hasErrors = true;
		}

		if (ValidationUtil.isNullOrEmpty(email)) {
			request.setAttribute("email_error", "Email is required.");
			hasErrors = true;
		} else if (!ValidationUtil.isValidEmail(email)) {
			request.setAttribute("email_error", "Please enter a valid email address.");
			hasErrors = true;
		}

		if (ValidationUtil.isNullOrEmpty(phoneNumber)) {
			request.setAttribute("phoneNumber_error", "Phone number is required.");
			hasErrors = true;
		} else if (!ValidationUtil.isValidPhoneNumber(phoneNumber)) {
			request.setAttribute("phoneNumber_error", "Please enter a valid phone number.");
			hasErrors = true;
		}

		if (ValidationUtil.isNullOrEmpty(password)) {
			request.setAttribute("password_error", "Password is required.");
			hasErrors = true;
		} else if (!ValidationUtil.isValidPassword(password)) {
			request.setAttribute("password_error",
					"Password must be at least 8 characters with uppercase, lowercase, and numbers.");
			hasErrors = true;
		}

		if (ValidationUtil.isNullOrEmpty(dob)) {
			request.setAttribute("dob_error", "Date of birth is required.");
			hasErrors = true;
		}

		if (ValidationUtil.isNullOrEmpty(gender)) {
			request.setAttribute("gender_error", "Gender is required.");
			hasErrors = true;
		}

		if (ValidationUtil.isNullOrEmpty(permanentAddress)) {
			request.setAttribute("permanentAddress_error", "Permanent address is required.");
			hasErrors = true;
		}

		if (hasErrors) {
			return false;
		}

		try {
			// Create user object
			User newUser = new User(firstName, lastName, voterId, email, phoneNumber, profileImage,
					imageHoldingCitizenship, voterCardFront, voterCardBack, citizenshipFront, citizenshipBack,
					thumbPrint, password, Timestamp.valueOf(dob + " 00:00:00"), // Convert string to Timestamp
					permanentAddress, temporaryAddress, "voter", // Default role
					false, // Initially not verified
					false // Email not verified
			);

			return UserDao.insertUser(newUser);
		} catch (DataAccessException dae) {
			logger.error("Failed to register user: " + dae.getMessage(), dae);
			request.setAttribute("registration_error", dae.getUserMessage());
			return false;
		} catch (Exception e) {
			logger.error("Unexpected error occurred while registering user", e);
			request.setAttribute("registration_error", "An unexpected error occurred. Please try again.");
			return false;
		}
	}

	public static User getUserById(HttpServletRequest request, HttpServletResponse response, Integer id) {
		if (id == null || id <= 0) {
			request.setAttribute("id_error", "Valid user ID is required.");
			return null;
		}

		try {
			User user = UserDao.getUserById(id);
			if (user == null) {
				request.setAttribute("user_not_found", "No user found with the given ID.");
			}
			return user;
		} catch (DataAccessException dae) {
			logger.error("Failed to retrieve user by ID: " + dae.getMessage(), dae);
			request.setAttribute("user_retrieve_error", dae.getUserMessage());
		} catch (Exception e) {
			logger.error("Unexpected error retrieving user by ID", e);
			request.setAttribute("user_retrieve_error", "An unexpected error occurred. Please try again.");
		}
		return null;
	}

	public static User getUserByEmail(HttpServletRequest request, HttpServletResponse response, String email) {
		if (ValidationUtil.isNullOrEmpty(email)) {
			request.setAttribute("email_error", "Email is required.");
			return null;
		}

		try {
			User user = UserDao.getUserByEmail(email);
			if (user == null) {
				request.setAttribute("user_not_found", "No user found with the given email.");
			}
			return user;
		} catch (DataAccessException dae) {
			logger.error("Failed to retrieve user by email: " + dae.getMessage(), dae);
			request.setAttribute("user_retrieve_error", dae.getUserMessage());
		} catch (Exception e) {
			logger.error("Unexpected error retrieving user by email", e);
			request.setAttribute("user_retrieve_error", "An unexpected error occurred. Please try again.");
		}
		return null;
	}

	public static User getUserByVoterId(HttpServletRequest request, HttpServletResponse response, String voterId) {
		if (ValidationUtil.isNullOrEmpty(voterId)) {
			request.setAttribute("voter_id_error", "Voter ID is required.");
			return null;
		}

		if (!ValidationUtil.isNumeric(voterId)) {
			request.setAttribute("voter_id_error", "Voter ID must contain only digit.");
			return null;
		}

		try {
			User user = UserDao.getUserByVoterId(voterId);
			if (user == null) {
				request.setAttribute("user_not_found", "No user found with the given voter ID.");
			}
			return user;
		} catch (DataAccessException dae) {
			logger.error("Failed to retrieve user by voter ID: " + dae.getMessage(), dae);
			request.setAttribute("user_retrieve_error", dae.getUserMessage());
		} catch (Exception e) {
			logger.error("Unexpected error retrieving user by voter ID", e);
			request.setAttribute("user_retrieve_error", "An unexpected error occurred. Please try again.");
		}
		return null;
	}

	public static List<User> getAllUsers() {
		try {
			return UserDao.getAllUsers();
		} catch (DataAccessException dae) {
			logger.error("Data access error while fetching all users: " + dae.getMessage(), dae);
			return Collections.emptyList();
		} catch (Exception e) {
			logger.error("Unexpected error while fetching all users: " + e.getMessage(), e);
			return Collections.emptyList();
		}
	}

	public static boolean updateUser(HttpServletRequest request, HttpServletResponse response, Integer userId) {
		boolean hasErrors = false;

		if (userId == null || userId <= 0) {
			request.setAttribute("user_id_error", "Invalid user ID.");
			return false;
		}

		// Extract parameters from request
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String phoneNumber = request.getParameter("phoneNumber");
		String gender = request.getParameter("gender");
		String permanentAddress = request.getParameter("permanentAddress");
		String temporaryAddress = request.getParameter("temporaryAddress");

		// Field validations
		if (ValidationUtil.isNullOrEmpty(firstName)) {
			request.setAttribute("firstName_error", "First name is required.");
			hasErrors = true;
		} else if (!ValidationUtil.isAlphabetic(firstName)) {
			request.setAttribute("firstName_error", "First name must contain only letters.");
			hasErrors = true;
		}

		if (ValidationUtil.isNullOrEmpty(lastName)) {
			request.setAttribute("lastName_error", "Last name is required.");
			hasErrors = true;
		} else if (!ValidationUtil.isAlphabetic(lastName)) {
			request.setAttribute("lastName_error", "Last name must contain only letters.");
			hasErrors = true;
		}

		if (ValidationUtil.isNullOrEmpty(email)) {
			request.setAttribute("email_error", "Email is required.");
			hasErrors = true;
		} else if (!ValidationUtil.isValidEmail(email)) {
			request.setAttribute("email_error", "Please enter a valid email address.");
			hasErrors = true;
		}

		if (ValidationUtil.isNullOrEmpty(phoneNumber)) {
			request.setAttribute("phoneNumber_error", "Phone number is required.");
			hasErrors = true;
		} else if (!ValidationUtil.isValidPhoneNumber(phoneNumber)) {
			request.setAttribute("phoneNumber_error", "Please enter a valid phone number.");
			hasErrors = true;
		}

		if (ValidationUtil.isNullOrEmpty(gender)) {
			request.setAttribute("gender_error", "Gender is required.");
			hasErrors = true;
		}

		if (ValidationUtil.isNullOrEmpty(permanentAddress)) {
			request.setAttribute("permanentAddress_error", "Permanent address is required.");
			hasErrors = true;
		}

		if (hasErrors) {
			return false;
		}

		try {
			// Get existing user to preserve some fields
			User existingUser = UserDao.getUserById(userId);
			if (existingUser == null) {
				request.setAttribute("user_not_found", "No user found with the given ID.");
				return false;
			}

			// Create updated user object
			User updatedUser = new User(userId, firstName, lastName, existingUser.getVoterId(), email, phoneNumber,
					existingUser.getProfileImage(), existingUser.getImageHoldingCitizenship(),
					existingUser.getVoterCardFront(), existingUser.getVoterCardBack(),
					existingUser.getCitizenshipFront(), existingUser.getCitizenshipBack(), existingUser.getThumbPrint(),
					existingUser.getPassword(), existingUser.getDob(), permanentAddress, temporaryAddress,
					existingUser.getRole(), existingUser.getIsVerified(), existingUser.getCreatedAt());

			updatedUser.setTemporaryAddress(temporaryAddress);
			updatedUser.setGender(gender);

			boolean updated = UserDao.updateUser(updatedUser);

			if (!updated) {
				request.setAttribute("user_update_error", "No user was updated. Possibly invalid ID.");
			}

			return updated;
		} catch (DataAccessException dae) {
			logger.error("Failed to update user: " + dae.getMessage(), dae);
			request.setAttribute("user_update_error", dae.getUserMessage());
		} catch (Exception e) {
			logger.error("Unexpected error occurred while updating user", e);
			request.setAttribute("user_update_error", "An unexpected error occurred. Please try again.");
		}

		return false;
	}

	public static boolean deleteUser(HttpServletRequest request, HttpServletResponse response, int userId) {
		if (userId <= 0) {
			request.setAttribute("user_id_error", "Invalid user ID.");
			return false;
		}

		try {
			boolean deleted = UserDao.deleteUser(userId);
			if (!deleted) {
				request.setAttribute("user_delete_error", "No user found with the given ID to delete.");
			}
			return deleted;
		} catch (DataAccessException dae) {
			logger.error("Failed to delete user: " + dae.getMessage(), dae);
			request.setAttribute("user_delete_error", dae.getUserMessage());
		} catch (Exception e) {
			logger.error("Unexpected error while deleting user", e);
			request.setAttribute("user_delete_error", "An unexpected error occurred. Please try again.");
		}
		return false;
	}

	public static boolean verifyUser(HttpServletRequest request, HttpServletResponse response, int userId) {
		if (userId <= 0) {
			request.setAttribute("user_id_error", "Invalid user ID.");
			return false;
		}

		try {
			boolean verified = UserDao.setUserVerified(userId, true);
			if (!verified) {
				request.setAttribute("verification_error", "No user found with the given ID to verify.");
			}
			return verified;
		} catch (DataAccessException dae) {
			logger.error("Failed to verify user: " + dae.getMessage(), dae);
			request.setAttribute("verification_error", dae.getUserMessage());
		} catch (Exception e) {
			logger.error("Unexpected error while verifying user", e);
			request.setAttribute("verification_error", "An unexpected error occurred. Please try again.");
		}
		return false;
	}

	public static boolean verifyEmail(HttpServletRequest request, HttpServletResponse response, int userId) {
		if (userId <= 0) {
			request.setAttribute("user_id_error", "Invalid user ID.");
			return false;
		}

		try {
			boolean verified = UserDao.setEmailVerified(userId, true);
			if (!verified) {
				request.setAttribute("email_verification_error", "No user found with the given ID to verify email.");
			}
			return verified;
		} catch (DataAccessException dae) {
			logger.error("Failed to verify email: " + dae.getMessage(), dae);
			request.setAttribute("email_verification_error", dae.getUserMessage());
		} catch (Exception e) {
			logger.error("Unexpected error while verifying email", e);
			request.setAttribute("email_verification_error", "An unexpected error occurred. Please try again.");
		}
		return false;
	}

	public static boolean updatePassword(HttpServletRequest request, HttpServletResponse response, int userId) {
		boolean hasErrors = false;

		if (userId <= 0) {
			request.setAttribute("user_id_error", "Invalid user ID.");
			return false;
		}

		String currentPassword = request.getParameter("currentPassword");
		String newPassword = request.getParameter("newPassword");
		String confirmPassword = request.getParameter("confirmPassword");

		// Field validations
		if (ValidationUtil.isNullOrEmpty(currentPassword)) {
			request.setAttribute("currentPassword_error", "Current password is required.");
			hasErrors = true;
		}

		if (ValidationUtil.isNullOrEmpty(newPassword)) {
			request.setAttribute("newPassword_error", "New password is required.");
			hasErrors = true;
		} else if (!ValidationUtil.isValidPassword(newPassword)) {
			request.setAttribute("newPassword_error",
					"Password must be at least 8 characters with uppercase, lowercase, and numbers.");
			hasErrors = true;
		}

		if (ValidationUtil.isNullOrEmpty(confirmPassword)) {
			request.setAttribute("confirmPassword_error", "Please confirm your new password.");
			hasErrors = true;
		} else if (!newPassword.equals(confirmPassword)) {
			request.setAttribute("confirmPassword_error", "Passwords do not match.");
			hasErrors = true;
		}

		if (hasErrors) {
			return false;
		}

		try {
			// Verify current password first
			User user = UserDao.getUserById(userId);
			if (user == null) {
				request.setAttribute("password_update_error", "User not found.");
				return false;
			}

			if (!user.getPassword().equals(currentPassword)) {
				request.setAttribute("currentPassword_error", "Current password is incorrect.");
				return false;
			}

			// Update password
			boolean updated = UserDao.updatePassword(userId, newPassword);

			if (!updated) {
				request.setAttribute("password_update_error", "Failed to update password.");
			}

			return updated;
		} catch (DataAccessException dae) {
			logger.error("Failed to update password: " + dae.getMessage(), dae);
			request.setAttribute("password_update_error", dae.getUserMessage());
		} catch (Exception e) {
			logger.error("Unexpected error occurred while updating password", e);
			request.setAttribute("password_update_error", "An unexpected error occurred. Please try again.");
		}

		return false;
	}
}