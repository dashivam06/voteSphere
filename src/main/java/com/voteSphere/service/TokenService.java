package com.voteSphere.service;

import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.voteSphere.dao.TokenDao;
import com.voteSphere.exception.DataAccessException;
import com.voteSphere.model.Token;
import com.voteSphere.util.ValidationUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TokenService {

    private static final Logger logger = LogManager.getLogger(TokenService.class);

    public static boolean createToken(HttpServletRequest request, HttpServletResponse response) {
        boolean hasErrors = false;

        String userIdStr = request.getParameter("userID");
        String tokenValue = request.getParameter("token");
        String type = request.getParameter("type");

        // Field validations
        if (ValidationUtil.isNullOrEmpty(userIdStr)) {
            request.setAttribute("userID_error", "User ID is required.");
            hasErrors = true;
        } else if (!ValidationUtil.isNumeric(userIdStr)) {
            request.setAttribute("userID_error", "User ID must be a number.");
            hasErrors = true;
        }

        if (ValidationUtil.isNullOrEmpty(tokenValue)) {
            request.setAttribute("token_error", "Token value is required.");
            hasErrors = true;
        }

        if (ValidationUtil.isNullOrEmpty(type)) {
            request.setAttribute("type_error", "Token type is required.");
            hasErrors = true;
        }

        if (hasErrors) {
            return false;
        }

        try {
            int userId = Integer.parseInt(userIdStr);
            Token newToken = new Token(request, userId, tokenValue, type);
            return TokenDao.createToken(newToken);
        } catch (DataAccessException dae) {
            logger.error("Failed to create token: " + dae.getMessage(), dae);
            request.setAttribute("token_creation_error", dae.getUserMessage());
            return false;
        } catch (NumberFormatException nfe) {
            logger.error("Invalid user ID format: " + userIdStr, nfe);
            request.setAttribute("userID_error", "Invalid user ID format.");
            return false;
        } catch (Exception e) {
            logger.error("Unexpected error occurred while creating token", e);
            request.setAttribute("token_creation_error", "An unexpected error occurred. Please try again.");
            return false;
        }
    }

    public static Token getTokenById(HttpServletRequest request, HttpServletResponse response, Integer tokenId) {
        if (tokenId == null || tokenId <= 0) {
            request.setAttribute("token_id_error", "Valid token ID is required.");
            return null;
        }

        try {
            Token token = TokenDao.findTokenById(tokenId);
            if (token == null) {
                request.setAttribute("token_not_found", "No token found with the given ID.");
            }
            return token;
        } catch (DataAccessException dae) {
            logger.error("Failed to retrieve token by ID: " + dae.getMessage(), dae);
            request.setAttribute("token_retrieve_error", dae.getUserMessage());
        } catch (Exception e) {
            logger.error("Unexpected error retrieving token by ID", e);
            request.setAttribute("token_retrieve_error", "An unexpected error occurred. Please try again.");
        }
        return null;
    }

    public static Token getTokenByValue(HttpServletRequest request, HttpServletResponse response, String tokenValue) {
        if (ValidationUtil.isNullOrEmpty(tokenValue)) {
            request.setAttribute("token_value_error", "Token value is required.");
            return null;
        }

        try {
            Token token = TokenDao.findTokenByValue(tokenValue);
            if (token == null) {
                request.setAttribute("token_not_found", "No token found with the given value.");
            }
            return token;
        } catch (DataAccessException dae) {
            logger.error("Failed to retrieve token by value: " + dae.getMessage(), dae);
            request.setAttribute("token_retrieve_error", dae.getUserMessage());
        } catch (Exception e) {
            logger.error("Unexpected error retrieving token by value", e);
            request.setAttribute("token_retrieve_error", "An unexpected error occurred. Please try again.");
        }
        return null;
    }

    public static List<Token> getTokensByUserId(HttpServletRequest request, HttpServletResponse response, Integer userId) {
        if (userId == null || userId <= 0) {
            request.setAttribute("user_id_error", "Valid user ID is required.");
            return Collections.emptyList();
        }

        try {
            return TokenDao.getTokensByUserId(userId);
        } catch (DataAccessException dae) {
            logger.error("Failed to retrieve tokens by user ID: " + dae.getMessage(), dae);
            request.setAttribute("token_retrieve_error", dae.getUserMessage());
        } catch (Exception e) {
            logger.error("Unexpected error retrieving tokens by user ID", e);
            request.setAttribute("token_retrieve_error", "An unexpected error occurred. Please try again.");
        }
        return Collections.emptyList();
    }

    public static List<Token> getAllTokens() {
        try {
            return TokenDao.getAllTokens();
        } catch (DataAccessException dae) {
            logger.error("Data access error while fetching all tokens: " + dae.getMessage(), dae);
            return Collections.emptyList();
        } catch (Exception e) {
            logger.error("Unexpected error while fetching all tokens: " + e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public static boolean updateToken(HttpServletRequest request, HttpServletResponse response, Integer tokenId) {
        boolean hasErrors = false;

        if (tokenId == null || tokenId <= 0) {
            request.setAttribute("token_id_error", "Invalid token ID.");
            return false;
        }

        String userIdStr = request.getParameter("userID");
        String tokenValue = request.getParameter("token");
        String type = request.getParameter("type");
        String isUsedStr = request.getParameter("isUsed");

        // Field validations
        if (ValidationUtil.isNullOrEmpty(userIdStr)) {
            request.setAttribute("userID_error", "User ID is required.");
            hasErrors = true;
        } else if (!ValidationUtil.isNumeric(userIdStr)) {
            request.setAttribute("userID_error", "User ID must be a number.");
            hasErrors = true;
        }

        if (ValidationUtil.isNullOrEmpty(tokenValue)) {
            request.setAttribute("token_error", "Token value is required.");
            hasErrors = true;
        }

        if (ValidationUtil.isNullOrEmpty(type)) {
            request.setAttribute("type_error", "Token type is required.");
            hasErrors = true;
        }

        if (ValidationUtil.isNullOrEmpty(isUsedStr)) {
            request.setAttribute("isUsed_error", "Usage status is required.");
            hasErrors = true;
        } else if (!ValidationUtil.isBoolean(isUsedStr)) {
            request.setAttribute("isUsed_error", "Usage status must be true or false.");
            hasErrors = true;
        }

        if (hasErrors) {
            return false;
        }

        try {
            int userId = Integer.parseInt(userIdStr);
            boolean isUsed = Boolean.parseBoolean(isUsedStr);
            
            // Create token using the constructor that handles request attributes
            Token updatedToken = new Token(request, userId, tokenValue, type);
            updatedToken.setToken_id(tokenId);
            updatedToken.setUsed(isUsed);

            boolean updated = TokenDao.updateToken(updatedToken);

            if (!updated) {
                request.setAttribute("token_update_error", "No token was updated. Possibly invalid ID.");
            }

            return updated;
        } catch (DataAccessException dae) {
            logger.error("Failed to update token: " + dae.getMessage(), dae);
            request.setAttribute("token_update_error", dae.getUserMessage());
        } catch (Exception e) {
            logger.error("Unexpected error occurred while updating token", e);
            request.setAttribute("token_update_error", "An unexpected error occurred. Please try again.");
        }

        return false;
    }

    public static boolean markTokenAsUsed(HttpServletRequest request, HttpServletResponse response, String tokenValue) {
        if (ValidationUtil.isNullOrEmpty(tokenValue)) {
            request.setAttribute("token_value_error", "Token value is required.");
            return false;
        }

        try {
            Token token = TokenDao.findTokenByValue(tokenValue);
            if (token == null) {
                request.setAttribute("token_not_found", "No token found with the given value.");
                return false;
            }

            token.setUsed(true);
            return TokenDao.updateToken(token);
        } catch (DataAccessException dae) {
            logger.error("Failed to mark token as used: " + dae.getMessage(), dae);
            request.setAttribute("token_update_error", dae.getUserMessage());
        } catch (Exception e) {
            logger.error("Unexpected error occurred while marking token as used", e);
            request.setAttribute("token_update_error", "An unexpected error occurred. Please try again.");
        }

        return false;
    }

    public static boolean deleteToken(HttpServletRequest request, HttpServletResponse response, Integer tokenId) {
        if (tokenId == null || tokenId <= 0) {
            request.setAttribute("token_id_error", "Invalid token ID.");
            return false;
        }

        try {
            boolean deleted = TokenDao.deleteToken(tokenId);
            if (!deleted) {
                request.setAttribute("token_delete_error", "No token found with the given ID to delete.");
            }
            return deleted;
        } catch (DataAccessException dae) {
            logger.error("Failed to delete token: " + dae.getMessage(), dae);
            request.setAttribute("token_delete_error", dae.getUserMessage());
        } catch (Exception e) {
            logger.error("Unexpected error while deleting token", e);
            request.setAttribute("token_delete_error", "An unexpected error occurred. Please try again.");
        }
        return false;
    }

    public static boolean validateToken(HttpServletRequest request, String tokenValue, String expectedType) {
        if (ValidationUtil.isNullOrEmpty(tokenValue) || ValidationUtil.isNullOrEmpty(expectedType)) {
            return false;
        }

        try {
            Token token = TokenDao.findTokenByValue(tokenValue);
            if (token == null || token.isUsed() || !expectedType.equals(token.getType())) {
                return false;
            }

            // Check if token is expired (example: 24-hour expiry)
            long tokenAge = System.currentTimeMillis() - token.getCreatedAt().getTime();
            if (tokenAge > 24 * 60 * 60 * 1000) { // 24 hours
                return false;
            }

            return true;
        } catch (Exception e) {
            logger.error("Error validating token", e);
            return false;
        }
    }
}