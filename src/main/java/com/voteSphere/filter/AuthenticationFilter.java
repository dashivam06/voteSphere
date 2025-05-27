//package com.voteSphere.filter;
//
//import jakarta.servlet.*;
//import java.io.IOException;
//import java.util.concurrent.TimeUnit;
//import jakarta.servlet.http.*;
//import jakarta.servlet.annotation.WebFilter;
//
//@WebFilter("/*")
//public class AuthenticationFilter implements Filter {
//
//    private static final long SESSION_TIMEOUT_MINUTES = 5;
//    private static final String LAST_ACTIVITY_ATTR = "lastActivityTime";
//    private static final String AUTH_USER_ATTR = "authenticated_user";
//    private static final String USER_ROLE_ATTR = "userRole";
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        // Initialization if needed
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
//
//        // Public paths accessible without authentication
//        if (isPublicPath(path)) {
//            chain.doFilter(request, response);
//            return;
//        }
//
//        HttpSession session = httpRequest.getSession(false);
//
//        // Check authentication
//        if (session == null || session.getAttribute(AUTH_USER_ATTR) == null) {
//            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login?redirect=" + path);
//            return;
//        }
//
//        // Check session timeout
//        if (!isSessionActive(session)) {
//            session.invalidate();
//            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login?timeout=true");
//            return;
//        }
//
//        // Update activity timestamp
//        updateLastActivityTime(session);
//
//        // Authorization checks
//        String userRole = (String) session.getAttribute(USER_ROLE_ATTR);
//
//        // Admin-only paths
//        if (path.startsWith("/admin/")) {
//            if (!"admin".equals(userRole)) {
//                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Admin access required");
//                return;
//            }
//        }
//
//        // Payment-related paths (special handling)
//        if (path.equals("/initiate-payment") || path.equals("/payment-update-servlet")) {
//            if (!"voter".equals(userRole)) {
//                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Payment operations require user role");
//                return;
//            }
//        }
//
//        // Voting paths
//        if (path.equals("/vote") || path.startsWith("/cast-vote/")) {
//            if (!"voter".equals(userRole)) {
//                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Only registered voters can cast votes");
//                return;
//            }
//        }
//
//        chain.doFilter(request, response);
//    }
//
//    private boolean isPublicPath(String path) {
//        return path.equals("/login") ||
//                path.equals("/register") ||
//                path.equals("/") ||
//                path.startsWith("/assets/") ||
//                path.startsWith("/css/") ||
//                path.startsWith("/js/") ||
//                path.startsWith("/images/");
//    }
//
//    private boolean isSessionActive(HttpSession session) {
//        Long lastActivityTime = (Long) session.getAttribute(LAST_ACTIVITY_ATTR);
//        if (lastActivityTime == null) return false;
//
//        long currentTime = System.currentTimeMillis();
//        long elapsedMinutes = TimeUnit.MILLISECONDS.toMinutes(currentTime - lastActivityTime);
//
//        return elapsedMinutes < SESSION_TIMEOUT_MINUTES;
//    }
//
//    private void updateLastActivityTime(HttpSession session) {
//        session.setAttribute(LAST_ACTIVITY_ATTR, System.currentTimeMillis());
//        session.setMaxInactiveInterval((int) TimeUnit.MINUTES.toSeconds(SESSION_TIMEOUT_MINUTES));
//    }
//
//    @Override
//    public void destroy() {
//        // Cleanup if needed
//    }
//}