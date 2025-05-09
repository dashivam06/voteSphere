//package com.voteSphere.esewa;
//
//import com.voteSphere.dao.DonationDao;
//import com.voteSphere.model.AuthUser;
//import com.voteSphere.model.Donation;
//
//import io.jsonwebtoken.io.IOException;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.*;
//
//@WebServlet("/initiate-payment")
//public class EsewaPaymentServlet extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException, java.io.IOException {
//        
//    	
//        String amount = request.getParameter("amount");
//        System.out.print(amount.repeat(10));
//        EsewaPaymentRequest paymentRequest = new EsewaPaymentRequest(amount);
//        DonationDao donationDao = new DonationDao();
////        AuthUser authUser = (AuthUser) request.getSession(false).getAttribute("user");
//        
//        donationDao.makeDonation(new Donation(
////        				authUser.getUserId(),
//        				5,
//        				Double.valueOf(paymentRequest.getAmount().replace(",", "")),
//        				paymentRequest.getProductCode(), 
//        				paymentRequest.getTransactionUuid(),
//        				"INITIATED")); 
//        System.out.print(paymentRequest);
//        request.setAttribute("paymentRequest", paymentRequest);
//		request.getRequestDispatcher("/WEB-INF/pages/esewa-payment-form.jsp").forward(request, response);
//
//    }
//    
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, java.io.IOException {
//		// TODO Auto-generated method stub
//		System.out.println("in here ------Get");
//		request.getRequestDispatcher("/WEB-INF/pages/esewa-amt.jsp").forward(request, response);
//
//		
//	}
//
//}