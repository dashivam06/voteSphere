package com.voteSphere.esewa;

import com.voteSphere.dao.DonationDao;
import com.voteSphere.model.AuthUser;
import com.voteSphere.model.Donation;

import com.voteSphere.service.DonationService;
import com.voteSphere.util.CookieUtil;
import com.voteSphere.util.SessionUtil;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.sql.Timestamp;

@WebServlet("/initiate-payment")
public class EsewaPaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, java.io.IOException {


        String amount = request.getParameter("amount");
        System.out.print(amount.repeat(10));
        EsewaPaymentRequest paymentRequest = new EsewaPaymentRequest(amount);
//        AuthUser authUser = (AuthUser) request.getSession(false).getAttribute("authenticated_user");

        Integer userId = SessionUtil.getUserValueFromSession(request,AuthUser::getUserId);
        DonationDao.createDonation(new Donation(
        				userId,
        				Double.parseDouble(amount),
        				paymentRequest.getProductCode(),
        				paymentRequest.getTransactionUuid(),
        				"INITIATED"));

        System.out.print(paymentRequest);

        request.setAttribute("paymentRequest", paymentRequest);
        Cookie esewaTotalAmountCookie = new Cookie("esewaTotalAmount", paymentRequest.getAmount());
        Cookie transactionUuidCookie = new Cookie("esewaTransactionUuid", paymentRequest.getTransactionUuid());
        Cookie productCodeCookie = new Cookie("esewaProductCode", paymentRequest.getProductCode());
//        Cookie donationId = new Cookie("donationId", DonationService.getpaymentRequest.getTransactionUuid());

        CookieUtil.addCookie(response,esewaTotalAmountCookie);
        CookieUtil.addCookie(response,transactionUuidCookie);
        CookieUtil.addCookie(response,productCodeCookie);

		request.getRequestDispatcher("/WEB-INF/pages/esewa-payment-form.jsp").forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, java.io.IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/WEB-INF/pages/esewa-payment-form.jsp").forward(request, response);


	}

}