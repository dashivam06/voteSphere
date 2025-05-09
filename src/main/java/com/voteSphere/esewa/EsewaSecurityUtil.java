package com.voteSphere.esewa;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class EsewaSecurityUtil {
    public static String generateSignature(String message) {
        try {
        	
        	System.out.println(" Message :"+message );
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            String secret = "8gBm/:&EnhH.1/q";
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            
            byte[] hashBytes = sha256_HMAC.doFinal(message.getBytes());
            String signature = Base64.getEncoder().encodeToString(hashBytes);
        	System.out.println(" Signature :"+signature );

            return signature;
        } catch (Exception e) {
            throw new RuntimeException("Error generating signature", e);
        }
    }
    
    public static boolean isValidSignature(String receivedMessage, String receivedSignature, String secretKey) {
        try {
            // Re-generate the signature from received message
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] hashBytes = sha256_HMAC.doFinal(receivedMessage.getBytes());
            String expectedSignature = Base64.getEncoder().encodeToString(hashBytes);

            // Compare it with the received signature
            return expectedSignature.equals(receivedSignature);
        } catch (Exception e) {
            return false; // treat as invalid if any error
        }
    }
    
    
    public static String truncateToTwoDecimals(String value) {
        double d = Double.parseDouble(value.replace(",", ""));
        d = Math.floor(d * 100) / 100.0; // Truncate to 2 decimals
        return String.valueOf(d);
    }


}