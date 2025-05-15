package com.voteSphere.controller;

import com.voteSphere.util.ImageReadUtil;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet(name = "ImageServlet", urlPatterns = {"/uploads/*","/images/*"})  // Handles all URLs starting with /images/
public class ImageServlet extends HttpServlet {
    
    private static final long serialVersionUID = -1032030194806528368L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response)  {
        String imagePath = request.getPathInfo().substring(1); // Remove leading slash
        ImageReadUtil.sendImage(imagePath, response);
    }
}