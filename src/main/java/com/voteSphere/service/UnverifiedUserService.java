package com.voteSphere.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.voteSphere.dao.UnverifiedUserDao;
import com.voteSphere.exception.DataAccessException;
import com.voteSphere.model.UnverifiedUser;
import com.voteSphere.util.ImageUploadHandler;
import com.voteSphere.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UnverifiedUserService {

	private static final Logger logger = LogManager.getLogger(UnverifiedUserService.class);

	public static boolean registerUnverifiedUser(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		boolean hasErrors = false;

		// Extract parameters from request
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String voterId = request.getParameter("voter_id");
		String email = request.getParameter("notification_email");
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
				"unverified-user-profile", appRealPath, maxImageSize);

		// Process document images
		String imageHoldingCitizenship = ImageUploadHandler.processImageUpload(request, "image_holding_citizenship",
				"image_holding_citizenship_error", "unverified-user-docs", appRealPath, maxImageSize);
		String voterCardFront = ImageUploadHandler.processImageUpload(request, "voter_card_front",
				"voter_card_front_error", "unverified-user-docs", appRealPath, maxImageSize);
		String voterCardBack = ImageUploadHandler.processImageUpload(request, "voter_card_back",
				"voter_card_back_error", "unverified-user-docs", appRealPath, maxImageSize);
		String citizenshipFront = ImageUploadHandler.processImageUpload(request, "citizenship_front",
				"citizenship_front_error", "unverified-user-docs", appRealPath, maxImageSize);
		String citizenshipBack = ImageUploadHandler.processImageUpload(request, "citizenship_back",
				"citizenship_back_error", "unverified-user-docs", appRealPath, maxImageSize);
		String thumbPrint = ImageUploadHandler.processImageUpload(request, "thumb_print", "thumb_print_error",
				"unverified-user-docs", appRealPath, maxImageSize);

		if (profileImage == null || imageHoldingCitizenship == null || voterCardFront == null || voterCardBack == null
				|| citizenshipFront == null || citizenshipBack == null || thumbPrint == null) {
			hasErrors = true;
			logger.warn("One or more required document images are missing or failed to upload.");

		}

		// Field validations
		if (ValidationUtil.isNullOrEmpty(firstName)) {
			logger.warn("Validation failed: First name is empty.");
			request.setAttribute("firstName_error", "First name is required.");
			hasErrors = true;
		} else if (!ValidationUtil.isAlphabetic(firstName)) {
			logger.warn("Validation failed: First name contains non-alphabetic characters.");
			request.setAttribute("firstName_error", "First name must contain only letters.");
			hasErrors = true;
		}

		if (ValidationUtil.isNullOrEmpty(lastName)) {
			logger.warn("Validation failed: Last name is empty.");
			request.setAttribute("lastName_error", "Last name is required.");
			hasErrors = true;
		} else if (!ValidationUtil.isAlphabetic(lastName)) {
			logger.warn("Validation failed: Last name contains non-alphabetic characters.");
			request.setAttribute("lastName_error", "Last name must contain only letters.");
			hasErrors = true;
		}

		if (ValidationUtil.isNullOrEmpty(voterId)) {
			logger.warn("Validation failed: Voter ID is empty.");
			request.setAttribute("voterId_error", "Voter ID is required.");
			hasErrors = true;
		}

		if (ValidationUtil.isNullOrEmpty(email)) {
			logger.warn("Validation failed: Email is empty.");
			request.setAttribute("email_error", "Email is required.");
			hasErrors = true;
		} else if (!ValidationUtil.isValidEmail(email)) {
			logger.warn("Validation failed: Invalid email format - {}", email);
			request.setAttribute("email_error", "Please enter a valid email address.");
			hasErrors = true;
		}

		if (ValidationUtil.isNullOrEmpty(phoneNumber)) {
			logger.warn("Validation failed: Phone number is empty.");
			request.setAttribute("phoneNumber_error", "Phone number is required.");
			hasErrors = true;
		} else if (!ValidationUtil.isValidPhoneNumber(phoneNumber)) {
			logger.warn("Validation failed: Invalid phone number - {}", phoneNumber);
			request.setAttribute("phoneNumber_error", "Please enter a valid phone number.");
			hasErrors = true;
		}

		if (ValidationUtil.isNullOrEmpty(dob)) {
			logger.warn("Validation failed: Date of birth is empty.");
			request.setAttribute("dob_error", "Date of birth is required.");
			hasErrors = true;
		}

		if (ValidationUtil.isNullOrEmpty(gender)) {
			logger.warn("Validation failed: Gender is empty.");
			request.setAttribute("gender_error", "Gender is required.");
			hasErrors = true;
		}

		if (ValidationUtil.isNullOrEmpty(permanentAddress)) {
			logger.warn("Validation failed: Permanent address is empty.");
			request.setAttribute("permanentAddress_error", "Permanent address is required.");
			hasErrors = true;
		}

		if (ValidationUtil.isNullOrEmpty(password)) {
			logger.warn("Validation failed: Password is empty.");
			request.setAttribute("password_error", "Password is required.");
			hasErrors = true;
		} else if (!ValidationUtil.isValidPassword(password)) {
			logger.warn("Validation failed: Password does not meet strength requirements.");
			request.setAttribute("password_error",
					"Password must be at least 8 characters with uppercase, lowercase, and numbers.");
			hasErrors = true;
		}

		if (hasErrors) {
			logger.info("User registration aborted due to validation errors.");
			return false;
		}

		try {

			String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());

			// Create unverified user object
			UnverifiedUser newUser = new UnverifiedUser(firstName, lastName, voterId, email, profileImage, phoneNumber,
					imageHoldingCitizenship, voterCardFront, voterCardBack, citizenshipFront, citizenshipBack,
					thumbPrint, hashedPassword, Timestamp.valueOf(dob + " 00:00:00"), gender, permanentAddress,
					temporaryAddress);


			return UnverifiedUserDao.createUnverifiedUser(newUser);
		} catch (DataAccessException dae) {
			logger.error("Failed to register unverified user: " + dae.getMessage(), dae);
			request.setAttribute("registration_error", dae.getUserMessage());
			return false;
		} catch (Exception e) {
			logger.error("Unexpected error occurred while registering unverified user", e);
			request.setAttribute("registration_error", "An unexpected error occurred. Please try again.");
			return false;
		}
	}

	public static UnverifiedUser getUnverifiedUserById(HttpServletRequest request, HttpServletResponse response,
			Integer id) {
		if (id == null || id <= 0) {
			request.setAttribute("id_error", "Valid unverified user ID is required.");
			return null;
		}

		try {
			UnverifiedUser user = UnverifiedUserDao.findUnverifiedUserById(id);
			if (user == null) {
				request.setAttribute("user_not_found", "No unverified user found with the given ID.");
			}
			return user;
		} catch (DataAccessException dae) {
			logger.error("Failed to retrieve unverified user by ID: " + dae.getMessage(), dae);
			request.setAttribute("user_retrieve_error", dae.getUserMessage());
		} catch (Exception e) {
			logger.error("Unexpected error retrieving unverified user by ID", e);
			request.setAttribute("user_retrieve_error", "An unexpected error occurred. Please try again.");
		}
		return null;
	}

	public static UnverifiedUser getUnverifiedUserByEmail(HttpServletRequest request, HttpServletResponse response,
			String email) {
		if (ValidationUtil.isNullOrEmpty(email)) {
			request.setAttribute("email_error", "Email is required.");
			return null;
		}

		try {
			UnverifiedUser user = UnverifiedUserDao.getUnverifiedUserByEmail(email);
			if (user == null) {
				request.setAttribute("user_not_found", "No unverified user found with the given email.");
			}
			return user;
		} catch (DataAccessException dae) {
			logger.error("Failed to retrieve unverified user by email: " + dae.getMessage(), dae);
			request.setAttribute("user_retrieve_error", dae.getUserMessage());
		} catch (Exception e) {
			logger.error("Unexpected error retrieving unverified user by email", e);
			request.setAttribute("user_retrieve_error", "An unexpected error occurred. Please try again.");
		}
		return null;
	}

	public static UnverifiedUser getUnverifiedUserByVoterId(HttpServletRequest request, HttpServletResponse response,
			String voterId) {
		if (ValidationUtil.isNullOrEmpty(voterId)) {
			request.setAttribute("voterId_error", "Voter ID is required.");
			return null;
		}

		try {
			UnverifiedUser user = UnverifiedUserDao.getUnverifiedUserByVoterId(voterId);
			if (user == null) {
				request.setAttribute("user_not_found", "No unverified user found with the given voter ID.");
			}
			return user;
		} catch (DataAccessException dae) {
			logger.error("Failed to retrieve unverified user by voter ID: " + dae.getMessage(), dae);
			request.setAttribute("user_retrieve_error", dae.getUserMessage());
		} catch (Exception e) {
			logger.error("Unexpected error retrieving unverified user by voter ID", e);
			request.setAttribute("user_retrieve_error", "An unexpected error occurred. Please try again.");
		}
		return null;
	}

	public static List<UnverifiedUser> getAllUnverifiedUsers() {
		try {
			return UnverifiedUserDao.getAllUnverifiedUsers();
		} catch (DataAccessException dae) {
			logger.error("Data access error while fetching all unverified users: " + dae.getMessage(), dae);
			return Collections.emptyList();
		} catch (Exception e) {
			logger.error("Unexpected error while fetching all unverified users: " + e.getMessage(), e);
			return Collections.emptyList();
		}
	}

	public static boolean updateUnverifiedUser(HttpServletRequest request, HttpServletResponse response,
			Integer userId) {
		boolean hasErrors = false;

		if (userId == null || userId <= 0) {
			request.setAttribute("user_id_error", "Invalid unverified user ID.");
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

	    // Set up paths and max file size for image uploads
	    String appRealPath = request.getServletContext().getRealPath("");
	    long maxImageSize = 2 * 1024 * 1024; // 2MB

	    // Process profile image upload (if present)
	    String profileImage = ImageUploadHandler.processImageUpload(request, "profile_image", "profile_image_error",
	            "unverified-user-profile", appRealPath, maxImageSize);

	    // Process document images (if present)
	    String imageHoldingCitizenship = ImageUploadHandler.processImageUpload(request, "image_holding_citizenship",
	            "image_holding_citizenship_error", "unverified-user-docs", appRealPath, maxImageSize);
	    String voterCardFront = ImageUploadHandler.processImageUpload(request, "voter_card_front", "voter_card_front_error",
	            "unverified-user-docs", appRealPath, maxImageSize);
	    String voterCardBack = ImageUploadHandler.processImageUpload(request, "voter_card_back", "voter_card_back_error",
	            "unverified-user-docs", appRealPath, maxImageSize);
	    String citizenshipFront = ImageUploadHandler.processImageUpload(request, "citizenship_front", "citizenship_front_error",
	            "unverified-user-docs", appRealPath, maxImageSize);
	    String citizenshipBack = ImageUploadHandler.processImageUpload(request, "citizenship_back", "citizenship_back_error",
	            "unverified-user-docs", appRealPath, maxImageSize);
	    String thumbPrint = ImageUploadHandler.processImageUpload(request, "thumb_print", "thumb_print_error",
	            "unverified-user-docs", appRealPath, maxImageSize);

	    // Check if any image upload failed or is missing
	    if (profileImage == null || imageHoldingCitizenship == null || voterCardFront == null || voterCardBack == null
	            || citizenshipFront == null || citizenshipBack == null || thumbPrint == null) {
	        hasErrors = true;
	        logger.warn("One or more required document images are missing or failed to upload.");
	    }
	    
		if (hasErrors) {
			return false;
		}

		try {
			// Get existing user to preserve some fields
			UnverifiedUser existingUser = UnverifiedUserDao.findUnverifiedUserById(userId);
			if (existingUser == null) {
				request.setAttribute("user_not_found", "No unverified user found with the given ID.");
				return false;
			}

			// Create updated user object
			UnverifiedUser updatedUser = new UnverifiedUser(userId, firstName, lastName, existingUser.getVoterId(),
					email, existingUser.getProfileImage(), phoneNumber, imageHoldingCitizenship,
					voterCardFront, voterCardBack,
					citizenshipFront, citizenshipBack, thumbPrint,
					existingUser.getPassword(), existingUser.getDob(), gender, permanentAddress, temporaryAddress,
					existingUser.getRole(), existingUser.getIsVerified(), existingUser.isEmailVerified(),
					existingUser.getCreatedAt());

			boolean updated = UnverifiedUserDao.updateUnverifiedUser(updatedUser);

			if (!updated) {
				request.setAttribute("user_update_error", "No unverified user was updated. Possibly invalid ID.");
			}

			return updated;
		} catch (DataAccessException dae) {
			logger.error("Failed to update unverified user: " + dae.getMessage(), dae);
			request.setAttribute("user_update_error", dae.getUserMessage());
		} catch (Exception e) {
			logger.error("Unexpected error occurred while updating unverified user", e);
			request.setAttribute("user_update_error", "An unexpected error occurred. Please try again.");
		}

		return false;
	}

	public static boolean deleteUnverifiedUser(HttpServletRequest request, HttpServletResponse response, int userId) {
		if (userId <= 0) {
			request.setAttribute("user_id_error", "Invalid unverified user ID.");
			return false;
		}

		try {
			boolean deleted = UnverifiedUserDao.deleteUnverifiedUser(userId);
			if (!deleted) {
				request.setAttribute("user_delete_error", "No unverified user found with the given ID to delete.");
			}
			return deleted;
		} catch (DataAccessException dae) {
			logger.error("Failed to delete unverified user: " + dae.getMessage(), dae);
			request.setAttribute("user_delete_error", dae.getUserMessage());
		} catch (Exception e) {
			logger.error("Unexpected error while deleting unverified user", e);
			request.setAttribute("user_delete_error", "An unexpected error occurred. Please try again.");
		}
		return false;
	}

	public static boolean promoteToVerifiedUser(HttpServletRequest request, HttpServletResponse response, int userId) {
		if (userId <= 0) {
			request.setAttribute("user_id_error", "Invalid unverified user ID.");
			return false;
		}

		try {
			boolean promoted = UnverifiedUserDao.promoteUnverifiedUserToVerifiedUser(userId);
			if (!promoted) {
				request.setAttribute("promotion_error", "Failed to promote unverified user to verified user.");
			}
			return promoted;
		} catch (DataAccessException dae) {
			logger.error("Failed to promote unverified user: " + dae.getMessage(), dae);
			request.setAttribute("promotion_error", dae.getUserMessage());
		} catch (Exception e) {
			logger.error("Unexpected error while promoting unverified user", e);
			request.setAttribute("promotion_error", "An unexpected error occurred. Please try again.");
		}
		return false;
	}
	
	
	/**
	 * Handles email verification for an unverified user.
	 * 
	 * @param request The HttpServletRequest object
	 * @param response The HttpServletResponse object
	 * @param userId The ID of the user to verify
	 * @return true if verification was successful, false otherwise
	 */
	public static boolean verifyUserEmail(HttpServletRequest request, HttpServletResponse response, int userId) {
	    // Input validation
	    if (userId <= 0) {
	        logger.warn("Invalid user ID provided for email verification: {}", userId);
	        request.setAttribute("verification_error", "Invalid user ID");
	        return false;
	    }

	    try {
	        // Step 1: Verify email in unverified_users table
	        boolean emailVerified = UnverifiedUserDao.setIsEmailVerifiedTrue(userId);
	        
	        if (!emailVerified) {
	            logger.warn("Email verification failed - user not found or already verified: {}", userId);
	            request.setAttribute("verification_error", "User not found or already verified");
	            return false;
	        }

	        // Step 2: Promote user to verified_users table if all verifications are complete
	        UnverifiedUser user = UnverifiedUserDao.findUnverifiedUserById(userId);
	        if (user != null && user.getIsVerified() && user.isEmailVerified()) {
	            boolean promoted = UnverifiedUserDao.promoteUnverifiedUserToVerifiedUser(userId);
	            
	            if (promoted) {
	                logger.info("User successfully promoted to verified users: {}", userId);
	                request.setAttribute("verification_success", "Account fully verified!");
	                return true;
	            } else {
	                logger.error("Email verified but failed to promote user: {}", userId);
	                request.setAttribute("verification_error", "Account partially verified - please contact support");
	                return false;
	            }
	        }

	        logger.info("Email verified but other verifications pending for user: {}", userId);
	        request.setAttribute("verification_success", "Email verified successfully!");
	        return true;

	    } catch (DataAccessException dae) {
	        logger.error("Database error during email verification for user ID {}: {}", userId, dae.getMessage(), dae);
	        request.setAttribute("verification_error", dae.getUserMessage());
	    } catch (Exception e) {
	        logger.error("Unexpected error during email verification for user ID {}: {}", userId, e.getMessage(), e);
	        request.setAttribute("verification_error", "An unexpected error occurred");
	    }
	    
	    return false;
	}
	
	/**
	 * Sets the is_email_verified flag to true for an unverified user.
	 *
	 * @param request  The HttpServletRequest object
	 * @param response The HttpServletResponse object
	 * @param userId   The ID of the unverified user to email-verify
	 * @return true if successful, false otherwise
	 */
	public static boolean setIsEmailVerifiedTrue(HttpServletRequest request, HttpServletResponse response, int userId) {
	    if (userId <= 0) {
	        logger.warn("Email verification aborted: Invalid user ID provided: {}", userId);
	        request.setAttribute("user_id_error", "Invalid user ID for email verification.");
	        return false;
	    }

	    try {
	        boolean verified = UnverifiedUserDao.setIsEmailVerifiedTrue(userId);
	        if (!verified) {
	            logger.info("No user found or already email-verified for user ID: {}", userId);
	            request.setAttribute("email_verification_error", "User not found or already verified.");
	        } else {
	            logger.info("Successfully set is_email_verified to true for user ID: {}", userId);
	        }
	        return verified;
	    } catch (DataAccessException dae) {
	        logger.error("Database error during email verification for user ID {}: {}", userId, dae.getMessage(), dae);
	        request.setAttribute("email_verification_error", dae.getUserMessage());
	    } catch (Exception e) {
	        logger.error("Unexpected error during email verification for user ID {}.", userId, e);
	        request.setAttribute("email_verification_error", "An unexpected error occurred. Please try again.");
	    }
	    return false;
	}
	
	
	/**
	 * Sets the is_verified flag to true for an unverified user (admin approval step).
	 * Note: This is separate from email verification which sets is_email_verified.
	 * 
	 * @param request The HttpServletRequest object
	 * @param response The HttpServletResponse object 
	 * @param userId The ID of the unverified user to verify
	 * @return true if successful, false otherwise
	 */
	public static boolean setIsVerifiedTrue(HttpServletRequest request, HttpServletResponse response, int userId) {
	    if (userId <= 0) {
	        logger.warn("Invalid user ID provided for verification: {}", userId);
	        request.setAttribute("error", "Invalid user ID");
	        return false;
	    }

	    try {
	        logger.debug("Initiating admin verification for user ID: {}", userId);
	        
	        boolean verificationSuccess = UnverifiedUserDao.setIsVerifiedTrue(userId);
	        
	        if (verificationSuccess) {
	            logger.info("Admin successfully verified user ID: {}", userId);
	            request.setAttribute("success", "User account verified");
	            return true;
	        } else {
	            logger.warn("Verification failed - no user found with ID: {}", userId);
	            request.setAttribute("error", "User not found");
	            return false;
	        }
	        
	    } catch (DataAccessException dae) {
	        logger.error("Database error during verification of user ID {}: {}", userId, dae.getMessage(), dae);
	        request.setAttribute("error", "System error during verification");
	        return false;
	    } catch (Exception e) {
	        logger.error("Unexpected error verifying user ID {}: {}", userId, e.getMessage(), e);
	        request.setAttribute("error", "Unexpected error during verification");
	        return false;
	    }
	}
	
	
	
	
	public static String getEmailOfUnverifiedUser(HttpServletRequest request, HttpServletResponse response,
			int userId) {
		if (userId <= 0) {
			request.setAttribute("user_id_error", "Invalid unverified user ID.");
			return null;
		}

		try {
			String email = UnverifiedUserDao.getEmailOfUnverifiedUser(userId);
			if (email == null) {
				request.setAttribute("email_not_found", "No email found for the given unverified user ID.");
			}
			return email;
		} catch (DataAccessException dae) {
			logger.error("Failed to get email of unverified user: " + dae.getMessage(), dae);
			request.setAttribute("email_retrieve_error", dae.getUserMessage());
		} catch (Exception e) {
			logger.error("Unexpected error retrieving email of unverified user", e);
			request.setAttribute("email_retrieve_error", "An unexpected error occurred. Please try again.");
		}
		return null;
	}
}