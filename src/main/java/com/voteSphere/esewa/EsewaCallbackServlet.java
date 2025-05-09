//package com.voteSphere.esewa;
//
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.*;
//import java.io.*;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.*;
//import java.util.Base64;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import com.voteSphere.dao.DonationDao;
//import com.voteSphere.model.AuthUser;
//import com.voteSphere.model.Donation;
//import com.voteSphere.model.ApiResponse;
//
//@WebServlet("/esewa-callback")
//public class EsewaCallbackServlet extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//    private static final Logger logger = LogManager.getLogger(EsewaCallbackServlet.class);
//    
//    private final ObjectMapper objectMapper = new ObjectMapper()
//        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//    
//    private final DonationDao donationDao = new DonationDao();
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException {
//        try {
//            processPaymentCallback(request, response);
//        } catch (Exception e) {
//            handleUnexpectedError(request, response, e);
//        }
//    }
//
//    private void processPaymentCallback(HttpServletRequest request, HttpServletResponse response) 
//            throws IOException, ServletException {
//        String encodedData = validateAndGetDataParameter(request, response);
//        String jsonData = decodeAndParseData(encodedData, request, response);
//        EsewaCallbackData callbackData = parseCallbackData(jsonData, request, response);
//        validateCallbackData(callbackData, request, response);
////        Integer userId = validateAndGetUserId(request, response);
//        Integer userId =5;
//
//        processPaymentStatus(request, response, callbackData, userId);
//    }
//
//    private String validateAndGetDataParameter(HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException {
//        String encodedData = request.getParameter("data");
//        if (encodedData == null || encodedData.isEmpty()) {
//            logger.warn("Missing data parameter");
//            forwardError(request, response, 
//                new ApiResponse<>(false, "Missing payment data", "Parameter 'data' is required", "/esewa-callback"),
//                400
//            );
//            throw new ServletException("Abort: Missing data");
//        }
//        return encodedData;
//    }
//
//    private String decodeAndParseData(String encodedData, HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException {
//        try {
//            return new String(Base64.getDecoder().decode(encodedData));
//        } catch (IllegalArgumentException e) {
//            logger.warn("Invalid Base64 data");
//            forwardError(request, response,
//                new ApiResponse<>(false, "Invalid request", "Data must be Base64-encoded", "/esewa-callback"),
//                400
//            );
//            throw new ServletException("Abort: Invalid Base64");
//        }
//    }
//
//    private EsewaCallbackData parseCallbackData(String jsonData, HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException {
//        try {
//            return objectMapper.readValue(jsonData, EsewaCallbackData.class);
//        } catch (JsonProcessingException e) {
//            logger.warn("Invalid JSON data");
//            forwardError(request, response,
//                new ApiResponse<>(false, "Invalid request", "Malformed JSON data", "/esewa-callback"),
//                400
//            );
//            throw new ServletException("Abort: Invalid JSON");
//        }
//    }
//
//    private void validateCallbackData(EsewaCallbackData callbackData, HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException {
//    	
//    	
//        System.out.println(callbackData.toString());
//        System.out.println(callbackData.getSignature().equals(response));
//
//
////        if (!callbackData.isSignatureValid()) {
////            logger.error("Invalid signature");
////            forwardError(request, response,
////                new ApiResponse<>(false, "Payment rejected", "Signature verification failed", "/esewa-callback"),
////                403
////            );
////            throw new ServletException("Abort: Invalid signature");
////        }
//        if (callbackData.getTransaction_uuid() == null || callbackData.getTransaction_uuid().isEmpty()) {
//            forwardError(request, response,
//                new ApiResponse<>(false, "Invalid transaction", "Missing transaction ID", "/esewa-callback"),
//                400
//            );
//            throw new ServletException("Abort: Missing transaction ID");
//        }
//        if (callbackData.getTotal_amount() == null || callbackData.getTotal_amount().isEmpty()) {
//            forwardError(request, response,
//                new ApiResponse<>(false, "Invalid amount", "Missing payment amount", "/esewa-callback"),
//                400
//            );
//            throw new ServletException("Abort: Missing amount");
//        }
//    }
//
//    private Integer validateAndGetUserId(HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException {
//        Object userAttr = request.getAttribute("user");
//        if (!(userAttr instanceof AuthUser)) {
//            logger.warn("Unauthenticated access");
//            forwardError(request, response,
//                new ApiResponse<>(false, "Authentication required", "User session expired", "/esewa-callback"),
//                401
//            );
//            throw new ServletException("Abort: Unauthenticated");
//        }
//        return ((AuthUser)userAttr).getId();
//    }
//
//    private void processPaymentStatus(HttpServletRequest request, HttpServletResponse response, 
//            EsewaCallbackData callbackData, Integer userId) throws ServletException, IOException {
//        
//        String status = callbackData.getStatus();
//        String transactionUuid = callbackData.getTransaction_uuid();
//
//        try {
//            switch (status) {
//                case "COMPLETE":
//                    handleCompletePayment(callbackData, userId);
//                    forwardSuccess(request, response, 
//                        new ApiResponse<>(true, null, "Payment completed", "/esewa-callback"));
//                    break;
//                    
//                case "PENDING":
//                    handlePendingPayment(callbackData, userId);
//                    forwardSuccess(request, response, 
//                        new ApiResponse<>(true, null, "Payment pending verification", "/esewa-callback"));
//                    break;
//                    
//                case "FULL_REFUND":
//                    handleFullRefund(callbackData);
//                    forwardSuccess(request, response, 
//                        new ApiResponse<>(true, null, "Full refund processed", "/esewa-callback"));
//                    break;
//                    
//                case "PARTIAL_REFUND":
//                    handlePartialRefund(callbackData);
//                    forwardSuccess(request, response, 
//                        new ApiResponse<>(true, null, "Partial refund processed", "/esewa-callback"));
//                    break;
//                    
//                case "NOT_FOUND":
//                    handleNotFound(transactionUuid);
//                    forwardError(request, response,
//                        new ApiResponse<>(false, "Transaction not found", "Check transaction ID", "/esewa-callback"),
//                        404
//                    );
//                    break;
//                    
//                case "CANCELED":
//                    handleCancellation(transactionUuid);
//                    forwardSuccess(request, response, 
//                        new ApiResponse<>(true, null, "Payment canceled", "/esewa-callback"));
//                    break;
//                    
//                case "AMBIGUOUS":
//                    handleAmbiguousStatus(transactionUuid);
//                    forwardError(request, response,
//                        new ApiResponse<>(false, "Payment requires review", "Contact support", "/esewa-callback"),
//                        409
//                    );
//                    break;
//                    
//                default:
//                    handleUnknownStatus(transactionUuid, status);
//                    forwardError(request, response,
//                        new ApiResponse<>(false, "Unsupported status", "Status: " + status, "/esewa-callback"),
//                        400
//                    );
//            }
//        } catch (Exception e) {
//            logger.error("Processing failed for status: {}", status, e);
//            forwardError(request, response,
//                new ApiResponse<>(false, "Processing error", e.getMessage(), "/esewa-callback"),
//                500
//            );
//        }
//    }
//
//    private void handleCompletePayment(EsewaCallbackData callbackData, Integer userId) throws ServletException {
//        try {
//            double amount = Double.parseDouble(callbackData.getTotal_amount().replace(",", ""));
//            Donation donation = new Donation(
//                userId,
//                amount,
//                callbackData.getProduct_code(),
//                callbackData.getTransaction_uuid(),
//                "COMPLETED"
//            );
//            
//            if (donationDao.getDonationByTransactionId(callbackData.getTransaction_uuid()) == null) {
//                donationDao.makeDonation(donation);
//                logger.info("Created new COMPLETED donation for transaction: {}", callbackData.getTransaction_uuid());
//            } else {
//                donationDao.updateDonationStatus(callbackData.getTransaction_uuid(), "COMPLETED");
//                logger.info("Updated existing donation to COMPLETED for transaction: {}", callbackData.getTransaction_uuid());
//            }
//        } catch (NumberFormatException e) {
//            logger.error("Invalid amount format: {}", callbackData.getTotal_amount());
//            throw new ServletException("Invalid payment amount");
//        } catch (Exception e) {
//            logger.error("Failed to process complete payment", e);
//            throw new ServletException("Payment processing failed");
//        }
//    }
//
//    private void handlePendingPayment(EsewaCallbackData callbackData, Integer userId) throws ServletException {
//        try {
//            double amount = Double.parseDouble(callbackData.getTotal_amount().replace(",", ""));
//            Donation donation = new Donation(
//                userId,
//                amount,
//                callbackData.getProduct_code(),
//                callbackData.getTransaction_uuid(),
//                "PENDING"
//            );
//            
//            if (donationDao.getDonationByTransactionId(callbackData.getTransaction_uuid()) == null) {
//                donationDao.makeDonation(donation);
//                logger.info("Created new PENDING donation for transaction: {}", callbackData.getTransaction_uuid());
//            }
//        } catch (Exception e) {
//            logger.error("Failed to process pending payment", e);
//            throw new ServletException("Payment processing failed");
//        }
//    }
//
//    private void handleFullRefund(EsewaCallbackData callbackData) {
//        donationDao.updateDonationStatus(callbackData.getTransaction_uuid(), "REFUNDED");
//        logger.info("Processed full refund for transaction: {}", callbackData.getTransaction_uuid());
//    }
//
//    private void handlePartialRefund(EsewaCallbackData callbackData) {
//        donationDao.updateDonationStatus(callbackData.getTransaction_uuid(), "PARTIALLY_REFUNDED");
//        logger.info("Processed partial refund for transaction: {}", callbackData.getTransaction_uuid());
//    }
//
//    private void handleNotFound(String transactionUuid) {
//        donationDao.updateDonationStatus(transactionUuid, "NOT_FOUND");
//        logger.warn("Transaction not found: {}", transactionUuid);
//    }
//
//    private void handleCancellation(String transactionUuid) {
//        donationDao.updateDonationStatus(transactionUuid, "CANCELED");
//        logger.info("Payment canceled for transaction: {}", transactionUuid);
//    }
//
//    private void handleAmbiguousStatus(String transactionUuid) {
//        donationDao.updateDonationStatus(transactionUuid, "AMBIGUOUS");
//        logger.warn("Ambiguous payment status for transaction: {}", transactionUuid);
//    }
//
//    private void handleUnknownStatus(String transactionUuid, String status) {
//        donationDao.updateDonationStatus(transactionUuid, "UNKNOWN_" + status);
//        logger.warn("Unknown payment status {} for transaction: {}", status, transactionUuid);
//    }
//
//    private void forwardSuccess(HttpServletRequest request, HttpServletResponse response, 
//            ApiResponse<?> apiResponse) throws ServletException, IOException {
//        request.setAttribute("apiResponse", apiResponse);
//        request.getRequestDispatcher("/WEB-INF/pages/paymentSuccess.jsp").forward(request, response);
//    }
//
//    private void forwardError(HttpServletRequest request, HttpServletResponse response, 
//            ApiResponse<?> apiResponse, int httpStatus) throws ServletException, IOException {
//        response.setStatus(httpStatus);
//        request.setAttribute("apiResponse", apiResponse);
//        request.getRequestDispatcher("/WEB-INF/pages/paymentError.jsp").forward(request, response);
//    }
//
//    private void handleUnexpectedError(HttpServletRequest request, HttpServletResponse response, 
//            Exception e) throws ServletException, IOException {
//        logger.error("Unexpected error", e);
//        forwardError(request, response,
//            new ApiResponse<>(false, "Server error", e.getMessage(), "/esewa-callback"),
//            500
//        );
//    }
//}