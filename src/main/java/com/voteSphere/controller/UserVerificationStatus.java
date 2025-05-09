//package com.voteSphere.controller;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Map;
//
//import com.voteSphere.model.UnverifiedUser;
//import com.voteSphere.model.User;
//import com.voteSphere.service.UnverifiedUserService;
//import com.voteSphere.service.UserService;
//
///**
// * Servlet implementation class UserVerificationStatus
// */
//@WebServlet("/user-verification-status")
//public class UserVerificationStatus extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//       
//    /**
//     * @see HttpServlet#HttpServlet()
//     */
//    public UserVerificationStatus() {
//        super();
//        // TODO Auto-generated constructor stub
//    }
//
//	/**
//	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
//	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//	}
//
//	/**
//	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
//	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		doGet(request, response);
//	}
//	
//	
//	
//	private Map<Boolean,String> getVerificationStatus(HttpServletRequest request, HttpServletResponse response, Integer voterId)
//	{
//		try
//		{
//			User user = UserService.getUserById(request, response, voterId);
//			
//			UnverifiedUser unverifiedUser = UnverifiedUserService.getUnverifiedUserByVoterId(request, response, String.valueOf(voterId));
//			
//					
//		}catch(Exception e)
//		{
//			
//		}
//	}
//
//}
