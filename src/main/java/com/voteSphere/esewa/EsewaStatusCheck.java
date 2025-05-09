package com.voteSphere.esewa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class EsewaStatusCheck {

    public String checkStatus(String productCode, String transactionUuid, double amount, boolean isTestEnv) {
        // 1. Validate Inputs
        if (productCode == null || productCode.isEmpty()) {
            return "{\"error\": \"Product code cannot be empty\"}";
        }
        if (transactionUuid == null || transactionUuid.isEmpty()) {
            return "{\"error\": \"Transaction UUID cannot be empty\"}";
        }
        if (amount <= 0) {
            return "{\"error\": \"Amount must be positive\"}";
        }

        // 2. Prepare URL
        String apiUrl = isTestEnv 
            ? "https://rc.esewa.com.np/api/epay/transaction/status/" 
            : "https://epay.esewa.com.np/api/epay/transaction/status/";

        String urlString = apiUrl + "?product_code=" + productCode 
                        + "&transaction_uuid=" + transactionUuid 
                        + "&total_amount=" + amount;

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            // 3. Open Connection (Exam-safe way)
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // 4. Check Response Code
            int responseCode = connection.getResponseCode();

            if (responseCode == 200) { // HTTP_OK
                // 5. Read Successful Response
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                return response.toString();
            } else {
                // Handle HTTP errors (404, 500, etc.)
                return "{\"error\": \"eSewa API failed with code: " + responseCode + "\"}";
            }

        } catch (MalformedURLException e) {
            return "{\"error\": \"Invalid URL: " + e.getMessage() + "\"}";
        } catch (IOException e) {
            return "{\"error\": \"Network error: " + e.getMessage() + "\"}";
        } finally {
            // 6. Cleanup (MUST DO for exams!)
            if (reader != null) {
                try { reader.close(); } catch (IOException e) {}
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}