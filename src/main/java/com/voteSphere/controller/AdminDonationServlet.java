package com.voteSphere.controller;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.voteSphere.model.Donation;
import com.voteSphere.service.DonationService;
import com.voteSphere.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/donation/*")
public class AdminDonationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(AdminDonationServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/") || pathInfo.equalsIgnoreCase("/list")) {
                handleListDonations(request, response);
            }
            else if (pathInfo.startsWith("/view/")) {
                String donationId = pathInfo.substring(6);
                handleViewDonation(request, response, donationId);
            }
            else if (pathInfo.equalsIgnoreCase("/stats")) {
                handleDonationStats(request, response);
            }
            else {
                logger.warn("Unknown path requested: {}", pathInfo);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
            }
        } catch (Exception e) {
            logger.error("Error processing donation request", e);
            request.setAttribute("error", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        logger.debug("Processing donation path: {}", pathInfo);

        try {
            if (pathInfo == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
            }
//            else if (pathInfo.startsWith("/refund/")) {
//                String donationId = pathInfo.substring(8);
//                handleRefundDonation(request, response, donationId);
//            }
            else if (pathInfo.startsWith("/search")) {
                handleSearch(request, response);
            }
            else {
                logger.warn("Invalid path requested: {}", pathInfo);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
            }
        } catch (Exception e) {
            logger.error("Error processing donation action", e);
            request.setAttribute("error", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void handleListDonations(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("Listing all donations");
        List<Donation> donations = DonationService.getAllDonations();
        request.setAttribute("donations", donations);
        handleDonationStats(request, response);
        request.getRequestDispatcher("/WEB-INF/pages/admin/donations.jsp").forward(request, response);
        logger.info("Successfully listed {} donations", donations.size());
    }

    private void handleViewDonation(HttpServletRequest request, HttpServletResponse response, String donationId)
            throws ServletException, IOException {
        logger.debug("Viewing donation with ID: {}", donationId);

        if (ValidationUtil.isNullOrEmpty(donationId) || !ValidationUtil.isNumeric(donationId)) {
            logger.warn("Invalid donation ID: {}", donationId);
            request.setAttribute("error", "Invalid donation ID");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        Donation donation = DonationService.getDonationById(Integer.parseInt(donationId));
        if (donation == null) {
            logger.warn("Donation not found with ID: {}", donationId);
            request.setAttribute("error", "Donation not found");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        request.setAttribute("donation", donation);
        request.getRequestDispatcher("/WEB-INF/pages/admin/donation-details.jsp").forward(request, response);
        logger.info("Successfully viewed donation ID: {}", donationId);
    }

    private void handleDonationStats(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("Loading donation statistics");

        double totalDonations = DonationService.getTotalDonations(request, response);
        double monthlyDonations = DonationService.getMonthlyDonations(request, response);
        double averageDonation = DonationService.getAverageDonation(request, response);
        int totalDonors = DonationService.getTotalDonors(request, response);

        request.setAttribute("totalDonations", totalDonations);
        request.setAttribute("monthlyDonations", monthlyDonations);
        request.setAttribute("averageDonation", averageDonation);
        request.setAttribute("totalDonors", totalDonors);

        logger.info("Successfully loaded donation statistics");
    }

//    private void handleRefundDonation(HttpServletRequest request, HttpServletResponse response, String donationId)
//            throws ServletException, IOException {
//        logger.debug("Processing refund for donation ID: {}", donationId);
//
//        if (ValidationUtil.isNullOrEmpty(donationId) || !ValidationUtil.isNumeric(donationId)) {
//            logger.warn("Invalid donation ID for refund: {}", donationId);
//            request.setAttribute("error", "Invalid donation ID");
//            request.getRequestDispatcher("/error.jsp").forward(request, response);
//            return;
//        }
//
//        boolean success = DonationService.refundDonation(Integer.parseInt(donationId));
//        if (success) {
//            logger.info("Successfully refunded donation ID: {}", donationId);
//            response.sendRedirect(request.getContextPath() + "/admin/donation/list");
//        } else {
//            logger.warn("Failed to refund donation ID: {}", donationId);
//            request.setAttribute("error", "Failed to process refund");
//            request.getRequestDispatcher("/error.jsp").forward(request, response);
//        }
//    }

    private void handleSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchQuery = request.getParameter("searchInput");
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            handleListDonations(request, response);
            return;
        }

        List<Donation> donations = DonationService.getAllDonations();
        donations = Donation.searchDonations(donations,searchQuery);
        request.setAttribute("donations", donations);
        request.setAttribute("searchInput", searchQuery);
        handleDonationStats(request, response);
        request.getRequestDispatcher("/WEB-INF/pages/admin/donations.jsp").forward(request, response);
        logger.info("Successfully searched donations with query: {}", searchQuery);
    }
}