package com.voteSphere.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

public class ImageUploadHandler {

	private static final Logger logger = LogManager.getLogger(ImageUploadHandler.class);
	private static final String BASE_UPLOAD_DIR = "WEB-INF/images/";

	public static String saveUploadedImage(Part filePart, String applicationRealPath, String subfolder)
			throws IOException {

		if (filePart == null || filePart.getSize() == 0) {
			logger.warn("No file uploaded or empty file for subfolder: {}", subfolder);
			throw new IOException("No file uploaded or empty file");
		}

		Path uploadPath = Paths.get(applicationRealPath, BASE_UPLOAD_DIR, subfolder).normalize().toAbsolutePath();
		Files.createDirectories(uploadPath);

		String fileName = generateUniqueFilename(filePart.getSubmittedFileName());
		Path filePath = uploadPath.resolve(fileName);

		try (InputStream fileContent = filePart.getInputStream()) {
			Files.copy(fileContent, filePath, StandardCopyOption.REPLACE_EXISTING);
			logger.info("Image saved successfully: {}", filePath);
		} catch (IOException e) {
			logger.error("Error while saving image to path: {}", filePath, e);
			throw e;
		}

		return subfolder.isEmpty() ? fileName : subfolder + "/" + fileName;
	}

	public static String saveUploadedImage(Part filePart, String applicationRealPath) throws IOException {
		return saveUploadedImage(filePart, applicationRealPath, "");
	}

	private static String generateUniqueFilename(String originalFileName) {
		String extension = "";
		int dotIndex = originalFileName.lastIndexOf('.');
		if (dotIndex > 0) {
			extension = originalFileName.substring(dotIndex);
		}
		String uniqueName = UUID.randomUUID().toString() + extension.toLowerCase();
		logger.debug("Generated unique filename: {}", uniqueName);
		return uniqueName;
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

			String savedPath = ImageUploadHandler.saveUploadedImage(imagePart, appRealPath, uploadDirectory);
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
