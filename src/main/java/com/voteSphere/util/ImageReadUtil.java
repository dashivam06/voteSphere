package com.voteSphere.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.voteSphere.config.AppConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.http.HttpServletResponse;

public class ImageReadUtil {
    private static final Logger logger = LogManager.getLogger(ImageReadUtil.class);
    private static final String IMAGE_BASE_DIR = AppConfig.get("IMAGE_BASE_UPLOAD_DIR")
            .replace("~", System.getProperty("user.home"));

    public static void sendImage(String imagePath, HttpServletResponse response) {
        try {
            // Build absolute image path
            Path imageFile = Paths.get(IMAGE_BASE_DIR, imagePath).normalize();

            // Security check - ensure path is within base directory
            if (!imageFile.startsWith(Paths.get(IMAGE_BASE_DIR))) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
                return;
            }

            // Check if file exists
            if (!Files.exists(imageFile)) {
                logger.error("Image not found: {}", imageFile);
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            // Set content type
            response.setContentType(getContentType(imagePath));

            // Send file
            Files.copy(imageFile, response.getOutputStream());
            logger.info("Successfully sent image: {}", imageFile);

        } catch (IOException e) {
            logger.error("Error sending image: {}", imagePath, e);
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } catch (IOException ex) {
                logger.error("Failed to send error response", ex);
            }
        }
    }

    private static String getContentType(String filename) {
        if (filename == null) return "application/octet-stream";

        filename = filename.toLowerCase();
        if (filename.endsWith(".png")) return "image/png";
        if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) return "image/jpeg";
        if (filename.endsWith(".gif")) return "image/gif";
        return "application/octet-stream";
    }
}