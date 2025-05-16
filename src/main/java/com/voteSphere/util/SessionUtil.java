package com.voteSphere.util;

import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import com.voteSphere.model.AuthUser;
import com.voteSphere.model.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionUtil {

	// Configuration constants
	private static final long INITIAL_SESSION_TIMEOUT_MINUTES = 5;
	private static final long MAX_SESSION_EXTENSION_MINUTES = 30;
	private static final long SESSION_EXTENSION_WINDOW_MINUTES = 4;
	private static final long SESSION_EXTENSION_INCREMENT_MINUTES = 5;

	public static void createAndUpdateSession(HttpServletRequest request, User user) {
		HttpSession session = request.getSession();

		// Invalidate any existing session first
		session.invalidate();
		session = request.getSession(true);

		AuthUser authenticatedUser = new AuthUser(user);
		session.setAttribute("authenticated_user", authenticatedUser);
		session.setAttribute("created_at", Instant.now());
		session.setAttribute("last_accessed_at", Instant.now());
		session.setAttribute("timeout_minutes", INITIAL_SESSION_TIMEOUT_MINUTES);

		// Set initial session timeout
		session.setMaxInactiveInterval((int) TimeUnit.MINUTES.toSeconds(INITIAL_SESSION_TIMEOUT_MINUTES));
	}

	public static void createAndUpdateUserObjectSession(HttpServletRequest request, AuthUser authenticatedUser) {
		HttpSession session = request.getSession();

		// Invalidate any existing session first
		session.invalidate();
		session = request.getSession(true);

		session.setAttribute("authenticated_user", authenticatedUser);
		session.setAttribute("created_at", Instant.now());
		session.setAttribute("last_accessed_at", Instant.now());
		session.setAttribute("timeout_minutes", INITIAL_SESSION_TIMEOUT_MINUTES);

		// Set initial session timeout
		session.setMaxInactiveInterval((int) TimeUnit.MINUTES.toSeconds(INITIAL_SESSION_TIMEOUT_MINUTES));
	}

	public static boolean isSessionValid(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		// Check if session exists
		if (session == null) {
			return false;
		}

		// Check if authenticated user exists
		AuthUser authUser = (AuthUser) session.getAttribute("authenticated_user");
		if (authUser == null) {
			return false;
		}

		// Check if session creation time exists
		Instant createdAt = (Instant) session.getAttribute("created_at");
		if (createdAt == null) {
			return false;
		}

		// Check session expiration
		return !checkSessionExpiry(session);
	}

	public static boolean checkSessionExpiry(HttpSession session) {
		Instant lastAccessedAt = (Instant) session.getAttribute("last_accessed_at");
		Long timeoutMinutes = (Long) session.getAttribute("timeout_minutes");

		if (lastAccessedAt == null || timeoutMinutes == null) {
			return true;
		}

		// Check if session is expired
		boolean isExpired = lastAccessedAt.plusSeconds(TimeUnit.MINUTES.toSeconds(timeoutMinutes))
				.isBefore(Instant.now());

		// Implement sliding expiration if not expired and within extension window
		if (!isExpired) {
			Instant now = Instant.now();
			long minutesSinceLastAccess = TimeUnit.SECONDS
					.toMinutes(now.getEpochSecond() - lastAccessedAt.getEpochSecond());

			// If user is active within the extension window and we haven't reached max
			// extension
			if (minutesSinceLastAccess >= (timeoutMinutes - SESSION_EXTENSION_WINDOW_MINUTES)
					&& timeoutMinutes < MAX_SESSION_EXTENSION_MINUTES) {

				// Extend the session
				long newTimeout = Math.min(timeoutMinutes + SESSION_EXTENSION_INCREMENT_MINUTES,
						MAX_SESSION_EXTENSION_MINUTES);

				session.setAttribute("timeout_minutes", newTimeout);
				session.setAttribute("last_accessed_at", now);
				session.setMaxInactiveInterval((int) TimeUnit.MINUTES.toSeconds(newTimeout));
			}
		}

		return isExpired;
	}

	public static void updateLastAccessTime(HttpSession session) {
		if (session != null) {
			session.setAttribute("last_accessed_at", Instant.now());
		}
	}

	public static void invalidateSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
	}

	public static <T> T getUserValueFromSession(HttpServletRequest request, Function<AuthUser, T> extractor) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			Object userObj = session.getAttribute("authenticated_user");
			if (userObj instanceof AuthUser authUser) {
				return extractor.apply(authUser);
			}
		}
		return null;
	}

}