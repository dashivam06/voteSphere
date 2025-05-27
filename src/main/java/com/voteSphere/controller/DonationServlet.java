package com.voteSphere.controller;

import com.voteSphere.model.Candidate;
import com.voteSphere.service.CandidateService;
import com.voteSphere.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/donate")
public class DonationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

                request.getRequestDispatcher("/WEB-INF/pages/voter/donation.jsp").forward(request, response);
            }




}
