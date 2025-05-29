package com.voteSphere.esewa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.voteSphere.dao.DonationDao;
import com.voteSphere.model.AuthUser;
import com.voteSphere.model.Donation;
import com.voteSphere.service.DonationService;
import com.voteSphere.util.CookieUtil;
import com.voteSphere.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

@WebServlet("/initiate-payment")
public class EsewaPaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String amount = request.getParameter("amount");

            // Validate amount
            if (amount == null || amount.isEmpty() || Double.parseDouble(amount) <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid amount");
                return;
            }

            // Create payment request
            EsewaPaymentRequest paymentRequest = new EsewaPaymentRequest(amount);

            // Get user ID from session
            Integer userId = SessionUtil.getUserValueFromSession(request, AuthUser::getUserId);

            // Record donation in database
            DonationDao.createDonation(new Donation(
                    userId,
                    Double.parseDouble(amount),
                    paymentRequest.getProductCode(),
                    paymentRequest.getTransactionUuid(),
                    "INITIATED"
            ));

            // Set cookies (optional)
            Cookie esewaTotalAmountCookie = new Cookie("esewaTotalAmount", paymentRequest.getAmount());
            Cookie transactionUuidCookie = new Cookie("esewaTransactionUuid", paymentRequest.getTransactionUuid());
            Cookie productCodeCookie = new Cookie("esewaProductCode", paymentRequest.getProductCode());

            CookieUtil.addCookie(response, esewaTotalAmountCookie);
            CookieUtil.addCookie(response, transactionUuidCookie);
            CookieUtil.addCookie(response, productCodeCookie);

            // Return JSON response
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            ObjectMapper mapper = new ObjectMapper();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            mapper.writeValue(response.getWriter(), paymentRequest);

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing payment");
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/voter/donation.jsp").forward(request, response);
    }
}