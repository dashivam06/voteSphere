package com.voteSphere.controller;

import java.io.IOException;
import java.util.Enumeration;

import com.voteSphere.service.UnverifiedUserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1 MB
		maxFileSize = 1024 * 1024 * 10, // 10 MB
		maxRequestSize = 1024 * 1024 * 50) // 50 MB
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			boolean user = UnverifiedUserService.registerUnverifiedUser(request, response);
			Enumeration<String> parameterNames = request.getParameterNames();

			while (parameterNames.hasMoreElements()) {
			    String paramName = parameterNames.nextElement();
			    String paramValue = request.getParameter(paramName);
			    System.out.println("Parameter Name: " + paramName + ", Value: " + paramValue);
			}

			if (user) {
				request.getRequestDispatcher("/WEB-INF/pages/voter/application-received.jsp")
				.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Registration failed.");
			request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("WEB-INF/pages/register.jsp").forward(request, response);

	}
}
