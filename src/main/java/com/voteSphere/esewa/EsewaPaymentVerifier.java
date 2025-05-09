//package com.voteSphere.esewa;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//public class EsewaPaymentVerifier {
////    private static final Logger logger = Logger.getLogger(EsewaPaymentVerifier.class.getName());
//    private static final String STATUS_CHECK_URL = "https://rc.esewa.com.np/api/epay/transaction/status/";
//    private static final long CALLBACK_TIMEOUT_MS = 300000; // 5 minutes
//
//    public PaymentStatus verifyPayment(String transactionUuid, String productCode, double amount) {
//        // 1. First check if we already received a callback
//        PaymentStatus callbackStatus = checkDatabaseForCallback(transactionUuid);
//        if (callbackStatus != null) {
//            return callbackStatus;
//        }
//
//        // 2. If no callback, wait for a reasonable time
//        long startTime = System.currentTimeMillis();
//        while (System.currentTimeMillis() - startTime < CALLBACK_TIMEOUT_MS) {
//            try {
//                Thread.sleep(5000); // Check every 5 seconds
//                
//                // Check database again
////                callbackStatus = checkDatabaseForCallback(transactionUuid);
//                if (callbackStatus != null) {
//                    return callbackStatus;
//                }
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//                break;
//            }
//        }
//
//        // 3. If still no callback, query status API
//        return queryEsewaStatusApi(transactionUuid, productCode, amount);
//    }
//
////    private PaymentStatus checkDatabaseForCallback(String transactionUuid) {
////        // Implement your database lookup here
////        // Return the status if found, null if not found
////        return paymentRepository.findByTransactionUuid(transactionUuid);
////    }
//
//    private PaymentStatus queryEsewaStatusApi(String transactionUuid, String productCode, double amount) {
//        try {
//            String url = String.format("%s?product_code=%s&total_amount=%s&transaction_uuid=%s",
//                STATUS_CHECK_URL,
//                URLEncoder.encode(productCode, "UTF-8"),
//                amount,
//                URLEncoder.encode(transactionUuid, "UTF-8"));
//
//            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
//            conn.setRequestMethod("GET");
//
//            if (conn.getResponseCode() == 200) {
//                try (BufferedReader reader = new BufferedReader(
//                    new InputStreamReader(conn.getInputStream()))) {
//                    
//                    JsonNode response = new ObjectMapper().readTree(reader);
//                    return parseStatusResponse(response);
//                }
//            }
//        } catch (Exception e) {
////            logger.error("Status check failed: " + e.getMessage());
//        }
//        return PaymentStatus.UNKNOWN;
//    }
//
//    private PaymentStatus parseStatusResponse(JsonNode response) {
//        String status = response.path("status").asText();
//        switch (status) {
//            case "COMPLETE": return PaymentStatus.COMPLETED;
//            case "PENDING": return PaymentStatus.PENDING;
//            case "FAILED": return PaymentStatus.FAILED;
//            default: return PaymentStatus.UNKNOWN;
//        }
//    }
//
//    public enum PaymentStatus {
//        COMPLETED, PENDING, FAILED, UNKNOWN
//    }
//    
//    
//    
//    // Helper method to check eSewa status API
//    private String checkEsewaStatus(String transactionUuid, String productCode, double amount) {
//        try {
//            String url = String.format("%s?product_code=%s&total_amount=%s&transaction_uuid=%s",
//                STATUS_CHECK_URL,
//                URLEncoder.encode(productCode, "UTF-8"),
//                amount,
//                URLEncoder.encode(transactionUuid, "UTF-8"));
//
//            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
//            conn.setRequestMethod("GET");
//
//            if (conn.getResponseCode() == 200) {
//                try (BufferedReader reader = new BufferedReader(
//                    new InputStreamReader(conn.getInputStream()))) {
//                    
//                    JsonNode response = new ObjectMapper().readTree(reader);
//                    String status = response.path("status").asText();
//                    
//                    // Update database if we get a definitive status
//                    if ("COMPLETE".equals(status) || "FAILED".equals(status)) {
//                        updateDonationStatus(transactionUuid, status);
//                    }
//                    
//                    return status;
//                }
//            }
//        } catch (Exception e) {
//            logger.severe("Error checking eSewa status: " + e.getMessage());
//        }
//        return "UNKNOWN";
//    }
//
//}