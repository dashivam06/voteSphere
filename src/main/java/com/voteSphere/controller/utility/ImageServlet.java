package com.voteSphere.controller.utility;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.voteSphere.util.CloudinaryUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@WebServlet(name = "ImageServlet", urlPatterns = {"/uploads/*", "/images/*"})
public class ImageServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(ImageServlet.class);
    private static final long serialVersionUID = -1032030194806528368L;
    private static final long CACHE_DURATION = TimeUnit.DAYS.toSeconds(30); // 30 days caching
    private static final String CLOUDINARY_PREFIX = "cloudinary_";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String imagePath = getSanitizedImagePath(request);

                handleCloudinaryImage(imagePath, response);

        } catch (Exception e) {
            logger.error("Error processing image request", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Image processing error");
        }
    }

    private String getSanitizedImagePath(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.length() <= 1) {
            throw new IllegalArgumentException("Invalid image path");
        }
        return pathInfo.substring(1); // Remove leading slash
    }


    private void handleCloudinaryImage(String imagePath, HttpServletResponse response) throws IOException {
        try {
            Cloudinary cloudinary = CloudinaryUtil.getInstance();
            String publicId = imagePath.substring(CLOUDINARY_PREFIX.length());

            // Generate Cloudinary URL with optimizations
            String cloudinaryUrl = cloudinary.url()
                    .secure(true)
                    .format("auto") // Automatic format selection
                    .transformation(new Transformation().quality("auto"))

                    // Automatic quality adjustment
                    .generate(publicId);

            // Permanent redirect with caching headers
            response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            response.setHeader("Location", cloudinaryUrl);
            setCacheHeaders(response);
            logger.debug("Redirecting to Cloudinary URL: {}", cloudinaryUrl);
        } catch (Exception e) {
            logger.error("Failed to generate Cloudinary URL for: {}", imagePath, e);
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Image not available");
        }
    }


    private void setCacheHeaders(HttpServletResponse response) {
        response.setHeader("Cache-Control", "public, max-age=" + CACHE_DURATION);
        response.setHeader("Pragma", "cache");
        response.setDateHeader("Expires", System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(CACHE_DURATION));
    }

}