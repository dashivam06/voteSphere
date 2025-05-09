//package com.voteSphere.controller;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//
//import com.voteSphere.service.TokenService;
//import com.voteSphere.util.ValidationUtil;
//
//@WebServlet("/token")
//public class TokenServlet extends HttpServlet {
//
//    private static final long serialVersionUID = 1L;
//
//    public TokenServlet() {
//        super();
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        String token = request.getParameter("token");
//
//        if (token == null || token.trim().isEmpty()) {
//            request.setAttribute("error", "Token is missing");
//            request.getRequestDispatcher("/error.jsp").forward(request, response);
//            return;
//        }
//
////        boolean isValid = TokenService.validateToken(token);
//
//        if (isValid) {
//            request.setAttribute("message", "Token is valid.");
//            request.getRequestDispatcher("/tokenSuccess.jsp").forward(request, response);
//        } else {
//            request.setAttribute("error", "Invalid or expired token.");
//            request.getRequestDispatcher("/error.jsp").forward(request, response);
//        }
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        String userId = request.getParameter("userId");
//
//        if (userId == null || !ValidationUtil.isNumeric(userId)) {
//            request.setAttribute("error", "Invalid user ID for token generation.");
//            request.getRequestDispatcher("/error.jsp").forward(request, response);
//            return;
//        }
//
////        String token = TokenService.generateToken(Integer.parseInt(userId));
//
//        if (token != null) {
//            request.setAttribute("token", token);
//            request.getRequestDispatcher("/tokenGenerated.jsp").forward(request, response);
//        } else {
//            request.setAttribute("error", "Failed to generate token.");
//            request.getRequestDispatcher("/error.jsp").forward(request, response);
//        }
//    }
//}
