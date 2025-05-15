package com.voteSphere.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

public class ValidationUtil {

	// 1. Validate if a field is null or empty
	public static boolean isNullOrEmpty(String value) {
		return value == null || value.trim().isEmpty();
	}

	
	public static boolean isAlphabetic(String value) {
	    if (value == null) {
	        return false;
	    }
	    // Remove all whitespace (spaces, tabs, etc.)
	    String lettersOnly = value.replaceAll("\\s+", "");
	    // If it’s empty once spaces are stripped, or was only spaces, it’s not valid
	    if (lettersOnly.isEmpty()) {
	        return false;
	    }
	    // Check that every remaining character is a letter
	    for (char c : lettersOnly.toCharArray()) {
	        if (!Character.isLetter(c)) {
	            return false;
	        }
	    }
	    return true;
	}

	// 3. Validate if a string starts with a letter and is composed of letters and
	// numbers
	public static boolean isAlphanumericStartingWithLetter(String value) {
		return value != null && value.matches("^[a-zA-Z][a-zA-Z0-9]*$");
	}

	// 4. Validate if a string is "male" or "female" (case insensitive)
	public static boolean isValidGender(String value) {
		return value != null && (value.equalsIgnoreCase("male") || value.equalsIgnoreCase("female"));
	}

	// 5. Validate if a string is a valid email address
	public static boolean isValidEmail(String email) {
		String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
		return email != null && Pattern.matches(emailRegex, email);
	}

	// 6. Validate if a number is of 10 digits and starts with 98
	public static boolean isValidPhoneNumber(String number) {
		return number != null && number.matches("^98\\d{8}$");
	}

	// 7. Validate if a password is composed of at least 1 capital letter, 1 number,
	// and 1 symbol
	public static boolean isValidPassword(String password) {
	    String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&.])[A-Za-z\\d@$!%*?&.]{8,}$";
	    return password != null && password.matches(passwordRegex);
	}

	public static String validatePassword(String password) {

		if (password == null || password.isEmpty()) {
			return "Password cannot be empty.";
		}

		if (password.length() < 8 || !password.matches(".*[A-Z].*") || !password.matches(".*\\d.*")
				|| !password.matches(".*[@$!%*?&].*")) {

			return "Password must be at least 8 characters long, contain at least one uppercase letter, one digit, and one special character.";
		}

		return null;
	}

	public static String verifyPassword(HttpServletRequest request) {
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirm_password");

		boolean doPassMatch = ValidationUtil.doPasswordsMatch(confirmPassword, password);

		if (ValidationUtil.validatePassword(password) != null) {
			request.setAttribute("password_error", ValidationUtil.validatePassword(password));
			request.removeAttribute("password");
			request.removeAttribute("confirm_password");
			return null;
		} else if (!doPassMatch) {
			request.setAttribute("confirm_password_error", "Password and confirm password didn't match");
			return null;

		} else {
			return BCrypt.withDefaults().hashToString(12, password.toCharArray());

		}
	}

	public static boolean isNumeric(String str) {
		if (str == null || str.trim().isEmpty()) {
			return false;
		}
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isNumeric(String... values) {
		for (String str : values) {
			if (str == null || str.trim().isEmpty()) {
				return false;
			}
			try {
				Double.parseDouble(str);
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNullOrEmpty(String... values) {
		for (String value : values) {
			if (value == null || value.trim().isEmpty()) {
				return true;
			}
		}
		return false;
	}


	public static LocalDate convertTimestampToDateOnly(Timestamp timestamp) {
		if (timestamp == null) {
			throw new IllegalArgumentException("Timestamp cannot be null");
		}
		return timestamp.toLocalDateTime().toLocalDate();
	}



	// 8. Validate if a Part's file extension matches with image extensions (jpg,
	// jpeg, png, gif)
	public static boolean isValidImageExtension(Part imagePart) {
		if (imagePart == null || isNullOrEmpty(imagePart.getSubmittedFileName())) {
			return false;
		}
		String fileName = imagePart.getSubmittedFileName().toLowerCase();
		return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png")
				|| fileName.endsWith(".gif");
	}

	// 9. Validate if password and retype password match
	public static boolean doPasswordsMatch(String password, String retypePassword) {
		return password != null && password.equals(retypePassword);
	}

	// 10. Validate if the date of birth is at least 16 years before today
	public static boolean isAgeAtLeast16(LocalDate dob) {
		if (dob == null) {
			return false;
		}
		LocalDate today = LocalDate.now();
		return Period.between(dob, today).getYears() >= 16;
	}
	


	public static boolean isBoolean(String isUsedStr) {
		if (isUsedStr == null) {
			return false;
		}
		return isUsedStr.equalsIgnoreCase("true") || isUsedStr.equalsIgnoreCase("false");
	}
	
	
	public static String convertTimeStampToHrAndMinsOnly(Timestamp timestamp )
	{


        // Convert to LocalDateTime
        LocalDateTime localDateTime = timestamp.toLocalDateTime();

        // Format to date and minute only (yyyy-MM-dd HH:mm)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy 'at' hh:mm a");
        String formattedTime = localDateTime.format(formatter);

        return formattedTime;
        
	}

	public static String capitalize(String input) {
		if (input == null || input.isEmpty()) return input;
		input = input.trim().toLowerCase(); // make all lowercase
		return input.substring(0, 1).toUpperCase() + input.substring(1);
	}

	public static String sanitizeName(String name) {
		return name.trim().replaceAll("[^a-zA-Z0-9]", "_");
	}

	public static String getTimestamp() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm"));
	}




}