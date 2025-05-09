package com.voteSphere.util;

import jakarta.servlet.http.HttpServletRequest;

public class ClientInfoUtil {

    /**
     * Gets the client's IP address even when behind proxies
     * @param request The HTTP request object
     * @return Client IP address as String
     */
    public static String getClientIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        
        // In case of multiple IPs (behind multiple proxies), take the first one
        if (ipAddress != null && ipAddress.contains(",")) {
            ipAddress = ipAddress.split(",")[0].trim();
        }
        
        return ipAddress;
    }

    /**
     * Detects the client's operating system from User-Agent header
     * @param request The HTTP request object
     * @return Detected OS as String (Windows, Mac, Linux, Android, iOS, etc.)
     */
    public static String getClientOS(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (userAgent == null || userAgent.isEmpty()) {
            return "Unknown";
        }
        
        userAgent = userAgent.toLowerCase();
        
        if (userAgent.contains("windows")) {
            return "Windows";
        } else if (userAgent.contains("mac os x") || userAgent.contains("macintosh")) {
            return "Mac";
        } else if (userAgent.contains("linux")) {
            return "Linux";
        } else if (userAgent.contains("android")) {
            return "Android";
        } else if (userAgent.contains("iphone") || userAgent.contains("ipad") || userAgent.contains("ipod")) {
            return "iOS";
        } else if (userAgent.contains("x11") || userAgent.contains("unix")) {
            return "Unix";
        } else {
            return "Unknown";
        }
    }

    /**
     * Gets simplified device type (Desktop, Mobile, Tablet, Bot)
     * @param request The HTTP request object
     * @return Device type as String
     */
    public static String getDeviceType(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (userAgent == null || userAgent.isEmpty()) {
            return "Unknown";
        }
        
        userAgent = userAgent.toLowerCase();
        
        if (userAgent.contains("mobile") || userAgent.contains("android") || 
            userAgent.contains("iphone") || userAgent.contains("ipod")) {
            return "Mobile";
        } else if (userAgent.contains("ipad") || userAgent.contains("tablet")) {
            return "Tablet";
        } else if (userAgent.contains("bot") || userAgent.contains("crawler") || 
                 userAgent.contains("spider") || userAgent.contains("facebookexternalhit")) {
            return "Bot";
        } else {
            return "Desktop";
        }
    }
}
