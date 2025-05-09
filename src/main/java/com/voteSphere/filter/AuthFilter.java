//package com.voteSphere.filter;
//
//import jakarta.servlet.Filter;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.FilterConfig;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
//import jakarta.servlet.annotation.WebFilter;
//import java.io.IOException;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.util.Set;
//
//@WebFilter("/*") // Filter all requests
//public class AuthFilter implements Filter {
//
//    // Set of paths that do not require authentication
//    private static final Set<String> PUBLIC_PATH_PREFIXES = Set.of(
//        "/login",
//        "/register",
//        "/css/",
//        "/esewa/",
//        "/js/",
//        "/images/",
//        "/WEB-INF/pages/public/"
//    );
//    
//    
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//        String contextPath = httpRequest.getContextPath();
//        String requestURI = httpRequest.getRequestURI();
//
//        String relativePath = requestURI.substring(contextPath.length()).replaceAll("[/]+$", "");
//
//        if (isPublicPath(relativePath)) {
//            chain.doFilter(request, response); // Allow request to continue
//            return;
//        }
//        
////        if (requestURI.endsWith(".png") || requestURI.endsWith(".jpg") || requestURI.endsWith(".css")) {
////			chain.doFilter(request, response);
////			return;
////		}
//
//        HttpSession session = httpRequest.getSession(false);
//        boolean isLoggedIn = session != null && session.getAttribute("user") != null;
//
//        if (!isLoggedIn) {
//            String redirectURL = contextPath + "/login?from=" + 
//                URLEncoder.encode(requestURI, StandardCharsets.UTF_8.name());
//            httpResponse.sendRedirect(redirectURL);
//            return;
//        }
//
//        chain.doFilter(request, response); // User is authenticated, continue
//    }
//
//    // Check if the requested path is publicly accessible
//    private boolean isPublicPath(String path) {
//        return PUBLIC_PATH_PREFIXES.stream().anyMatch(path::startsWith);
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        // Optional: Add logging or config setup if needed
//    }
//
//    @Override
//    public void destroy() {
//        // Optional: Clean-up logic if needed
//    }
//}
