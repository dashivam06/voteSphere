package com.voteSphere.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.voteSphere.config.AppConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class CloudinaryUtil {
    private static final Logger logger = LogManager.getLogger(CloudinaryUtil.class);
    private static Cloudinary cloudinary;

    static {
        try {
            cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", AppConfig.get("CLOUDINARY_CLOUD_NAME"),
                    "api_key", AppConfig.get("CLOUDINARY_API_KEY"),
                    "api_secret", AppConfig.get("CLOUDINARY_API_SECRET"),
                    "secure", true
            ));
            logger.info("Cloudinary initialized successfully");
        } catch (Exception e) {
            logger.error("Failed to initialize Cloudinary", e);
            throw new RuntimeException("Failed to initialize Cloudinary", e);
        }
    }

    public static Cloudinary getInstance() {
        return cloudinary;
    }
}