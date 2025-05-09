package com.voteSphere.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.http.HttpServletResponse;


public class ImageRetrievalHandler {

    private static final String IMAGE_BASE = "WEB-INF/images/";
    private static final Logger logger = LogManager.getLogger(ImageRetrievalHandler.class);

    public static void sendImage(String appRealPath, String imagePath, HttpServletResponse response) {
        try {
            // Build full image path
            Path fullPath = Paths.get(appRealPath, IMAGE_BASE, imagePath);
            File imageFile = fullPath.toFile();

            // Check if file exists
            if (!imageFile.exists()) {
                logger.error("Image not found: "+ fullPath);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Image not found.");
                return;
            }

            // Log successful image retrieval attempt
            logger.info("Attempting to retrieve image: "+ fullPath);

            // Set content type based on file extension
            String contentType = getContentType(imagePath);
            response.setContentType(contentType);

            // Use Files.copy to send the file directly to the output stream
            Files.copy(imageFile.toPath(), response.getOutputStream());

            // Flush the response to ensure all data is sent
            response.getOutputStream().flush();
            logger.info("Image successfully sent: "+ fullPath);

        } catch (IOException e) {
            logger.error("Error while sending image: "+ imagePath+ e);
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
            } catch (IOException ex) {
                logger.error("Error while sending error response: "+ ex.getMessage()+ ex);
            }
        }
    }

    private static String getContentType(String filename) {
        if (filename.endsWith(".png")) return "image/png";
        if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) return "image/jpeg";
        if (filename.endsWith(".gif")) return "image/gif";
        return "application/octet-stream";
    }
}
