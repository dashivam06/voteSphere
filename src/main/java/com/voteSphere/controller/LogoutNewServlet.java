package com.voteSphere.controller;

import java.io.IOException;

import com.voteSphere.util.CookieUtil;
import com.voteSphere.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LogoutServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			
			SessionUtil.invalidateSession(request);
			
		} catch (IllegalStateException ignored) {
			
			
			
		}

		// Delete all the cookies
		CookieUtil.deleteAllCookies(request, response);

		// 3. Redirect to login or home page
		response.sendRedirect(request.getContextPath() + "/login");
	}

}