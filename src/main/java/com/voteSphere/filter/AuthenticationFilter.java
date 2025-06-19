package com.voteSphere.filter;

import com.voteSphere.model.AuthUser;
import com.voteSphere.util.SessionUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(AuthenticationFilter.class);

    // Public URLs that don't require authentication
    private static final Set<String> PUBLIC_URLS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            "/",
            "/login",
            "/register",
            "/styles/",
            "/logout",
            "/images/",
            "/uploads/",
            "/mail/"
    )));

    // Admin URLs
    private static final Set<String> ADMIN_URLS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            "/admin/candidate",
            "/admin/donation",
            "/admin/election",
            "/admin/party",
            "/admin/user-approval",
            "/admin/voter",
            "/election-results",
            "/dashboard",
            "/voteChart"
    )));

    // Voter URLs
    private static final Set<String> VOTER_URLS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            "/dashboard",
            "/profile",
            "/cast-vote",
            "/donate",
            "/voter/password-change",
            "/update-user-detail",
            "/election",
            "/DownloadUserPDF",
            "/initiate-payment",
            "/esewa-callback",
            "/payment-update-servlet"
    )));

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("VoteSphere Authentication Filter initialized");
        logger.debug("Public URLs: {}", PUBLIC_URLS);
        logger.debug("Admin URLs: {}", ADMIN_URLS);
        logger.debug("Voter URLs: {}", VOTER_URLS);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        logger.debug("Processing request for path: {}", path);

        // Check if URL is valid
        if (!isValidUrl(path)) {
            logger.warn("Invalid URL detected: {}", path);
            handleInvalidUrl(httpRequest, httpResponse);
            return;
        }
        logger.debug("URL validation passed for: {}", path);

        // Skip authentication for public URLs
        if (isPublicUrl(path)) {
            logger.debug("Public URL accessed: {}", path);
            chain.doFilter(request, response);
            return;
        }

        // Check if user is authenticated
        AuthUser authUser = SessionUtil.getCurrentUser(httpRequest);
        if (authUser == null) {
            logger.warn("Unauthenticated access attempt to protected resource: {}", path);
            String redirectUrl = httpRequest.getContextPath() + "/login?redirect=" + path;
            logger.debug("Redirecting to login page with redirect URL: {}", redirectUrl);
            httpResponse.sendRedirect(redirectUrl);
            return;
        }
        logger.debug("User authenticated: ID={}, Role={}", authUser.getUserId(), authUser.getRole());

        // Check role-based authorization
        if (!isAuthorized(authUser, path)) {
            logger.warn("Unauthorized access attempt - User ID: {}, Role: {}, Attempted Path: {}",
                    authUser.getUserId(), authUser.getRole(), path);
            logger.debug("Sending 403 Forbidden response");
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        logger.debug("Authorization granted for user {} to access {}", authUser.getUserId(), path);

        // Log request headers for debugging (optional)
        if (logger.isTraceEnabled()) {
            logger.trace("Request headers:");
            Collections.list(httpRequest.getHeaderNames())
                    .forEach(header -> logger.trace("{}: {}", header, httpRequest.getHeader(header)));
        }

        chain.doFilter(request, response);
        logger.debug("Request processing completed for path: {}", path);
    }

    private boolean isValidUrl(String path) {
        boolean isValid = PUBLIC_URLS.stream().anyMatch(path::startsWith) ||
                ADMIN_URLS.stream().anyMatch(path::startsWith) ||
                VOTER_URLS.stream().anyMatch(path::startsWith);
        logger.trace("URL validation result for {}: {}", path, isValid);
        return isValid;
    }

    private void handleInvalidUrl(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.warn("Handling invalid URL: {}", request.getRequestURI());
        logger.debug("Forwarding to URL not found page");
        request.getRequestDispatcher("/WEB-INF/pages/error/url-not-found.jsp").forward(request, response);
    }

    private boolean isPublicUrl(String path) {

        path = path.toLowerCase();
        // Exact matches for root and some paths
        if ("/".equals(path) || "/login".equals(path) || "/register".equals(path) || "/logout".equals(path) || "/style".equals(path) || "/style/".equals(path) || "/style/global.css".equals(path) || "/favicon.ico".equals(path)){
            return true;
        }
        // Prefix matches for directories
        return path.startsWith("/images/") || path.startsWith("/uploads/") || path.startsWith("/mail/");
    }


    private boolean isAuthorized(AuthUser user, String path) {
        String role = user.getRole().toUpperCase();
        logger.debug("Checking authorization for role {} on path {}", role, path);

        // Admin has access to admin URLs
        if ("ADMIN".equals(role)) {
            boolean isAdminPath = ADMIN_URLS.stream().anyMatch(path::startsWith);
            logger.trace("Admin authorization check result: {}", isAdminPath);
            return isAdminPath;
        }

        // Voter has access to voter URLs
        if ("VOTER".equals(role)) {
            boolean isVoterPath = VOTER_URLS.stream().anyMatch(path::startsWith);
            logger.trace("Voter authorization check result: {}", isVoterPath);
            return isVoterPath;
        }

        logger.warn("Unknown role detected: {}", role);
        return false;
    }

    @Override
    public void destroy() {
        logger.info("VoteSphere Authentication Filter destroyed");
        logger.debug("Filter cleanup completed");
    }
}