package com.voteSphere.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.concurrent.CompletableFuture;

import com.voteSphere.dto.UserRegistrationDTO;
import com.voteSphere.model.AuthUser;
import com.voteSphere.service.UnverifiedUserService;

import com.voteSphere.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet("/register")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1 MB
		maxFileSize = 1024 * 1024 * 10, // 10 MB
		maxRequestSize = 1024 * 1024 * 50) // 50 MB
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger logger  = LogManager.getLogger(RegisterServlet.class);

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO(request);

		System.out.println(userRegistrationDTO.toString());


		// Start the asynchronous registration process
		CompletableFuture<Boolean> registrationFuture =
				UnverifiedUserService.registerUnverifiedUserAsync(userRegistrationDTO);

		// Handle the completion of the registration
		registrationFuture.whenComplete((success, ex) -> {
			try {
				if (ex != null) {
					// Handle any exceptions that occurred during registration
					logger.error("Registration failed", ex);

					return;
				}



			} catch (Exception e) {
				logger.error("Error during request forwarding", e);

			}
		});
		request.getRequestDispatcher("/WEB-INF/pages/voter/application-received.jsp")
				.forward(request, response);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("WEB-INF/pages/register.jsp").forward(request, response);

	}
}
