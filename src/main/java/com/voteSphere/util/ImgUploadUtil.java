package com.voteSphere.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.voteSphere.config.AppConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ImgUploadUtil {
	private static final Logger logger = LogManager.getLogger(ImgUploadUtil.class);

	private static String getAbsoluteUploadPath(String configPath) {
		if (configPath.startsWith("~/")) {
			String homeDir = System.getProperty("user.home");
			return configPath.replace("~", homeDir);
		}
		return configPath;
	}

	public static String saveUploadedImageToCloudinary(Part filePart, String subfolder) throws IOException {
		if (filePart == null || filePart.getSize() == 0) {
			logger.warn("No file uploaded or empty file for subfolder: {}", subfolder);
			throw new IOException("No file uploaded or empty file");
		}

		// Create a temporary file to ensure the stream is properly handled
		Path tempFile = null;
		try {
			Cloudinary cloudinary = CloudinaryUtil.getInstance();

			// Generate a unique filename
			String originalFileName = filePart.getSubmittedFileName();
			String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
			String uniqueName = UUID.randomUUID().toString() + extension.toLowerCase();

			// Create a temporary file
			tempFile = Files.createTempFile("cloudinary-upload-", extension);
			try (InputStream input = filePart.getInputStream()) {
				Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
			}

			// Prepare upload options
			Map<String, Object> uploadOptions = new HashMap<>();
			uploadOptions.put("public_id", uniqueName);
			if (!subfolder.isEmpty()) {
				uploadOptions.put("folder", subfolder);
			}

			// Upload the temporary file
			Map<?, ?> uploadResult = cloudinary.uploader().upload(tempFile.toFile(), uploadOptions);
			String secureUrl = (String) uploadResult.get("secure_url");
			logger.info("Image uploaded successfully to Cloudinary: {}", secureUrl);
			return secureUrl;

		} catch (Exception e) {
			logger.error( "Error while uploading image to Cloudinary", e);
			throw new IOException("Failed to upload image to Cloudinary: " + e.getMessage(), e);
		} finally {
			// Clean up the temporary file
			if (tempFile != null) {
				try {
					Files.deleteIfExists(tempFile);
				} catch (IOException e) {
					logger.warn("Failed to delete temporary file: {}", tempFile, e);
				}
			}
		}
	}

	public static String processImageUpload(HttpServletRequest request, String formFieldName, String errorAttributeName,
											String uploadDirectory, String appRealPath, long maxFileSizeInBytes) {
		try {
			Part imagePart = request.getPart(formFieldName);

			if (imagePart == null || imagePart.getSize() == 0) {
				logger.warn("No image uploaded for field: {}", formFieldName);
				request.setAttribute(errorAttributeName, "Please upload an image.");
				return null;
			}

			if (!ValidationUtil.isValidImageExtension(imagePart)) {
				logger.warn("Invalid image extension for field: {}", formFieldName);
				request.setAttribute(errorAttributeName, "Image must be jpg, jpeg, png, or gif.");
				return null;
			}

			if (imagePart.getSize() > maxFileSizeInBytes) {
				logger.warn("Image too large for field: {}. Size: {} bytes", formFieldName, imagePart.getSize());
				request.setAttribute(errorAttributeName,
						"Image size must be less than " + (maxFileSizeInBytes / (1024 * 1024)) + "MB.");
				return null;
			}

			String savedPath = saveUploadedImageToCloudinary(imagePart, uploadDirectory);
			logger.info("Image uploaded successfully for field: {}. Saved at: {}", formFieldName, savedPath);
			return savedPath;

		} catch (Exception e) {
			logger.error("Error during image upload for field: {}", formFieldName, e);
			request.setAttribute(errorAttributeName, "Error while uploading image: " + e.getMessage());
			request.removeAttribute(formFieldName);
			return null;
		}
	}
}