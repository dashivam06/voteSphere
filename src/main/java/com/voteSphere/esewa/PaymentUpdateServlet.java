package com.voteSphere.esewa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.voteSphere.model.AuthUser;
import com.voteSphere.util.MailUtil;
import com.voteSphere.util.SessionUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;


@WebServlet("/payment-update-servlet")
public class PaymentUpdateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String amountStr = request.getParameter("amount");
        if (amountStr == null || amountStr.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Invalid amount\"}");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Invalid amount format\"}");
            return;
        }

        String baseUrl = SessionUtil.getBaseUrl(request);

        EsewaPaymentRequest pr = new EsewaPaymentRequest(amountStr,baseUrl);


        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(pr);


        response.getWriter().write(json);
    }
}
