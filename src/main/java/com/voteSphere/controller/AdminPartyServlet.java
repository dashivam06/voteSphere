package com.voteSphere.controller;

import java.io.IOException;
import java.util.List;

import com.voteSphere.model.Candidate;
import com.voteSphere.service.CandidateService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.voteSphere.model.Party;
import com.voteSphere.service.PartyService;
import com.voteSphere.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/admin/party/*")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 50) // 50 MB
public class AdminPartyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(AdminPartyServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/") || pathInfo.equalsIgnoreCase("/list")) {
                handleListParties(request, response);
            } else if (pathInfo.startsWith("/view/")) {
                String partyId = pathInfo.substring(6); // Extract ID after "/view/"
                handleViewParty(request, response, partyId);
            } else if (pathInfo.equalsIgnoreCase("/add")) {
                handleAddForm(request, response);
            } else if (pathInfo.startsWith("/update/")) {
                String partyId = pathInfo.substring(8); // Extract ID after "/edit/"
                handleEditForm(request, response, partyId);
            } else {
                logger.warn("Unknown path requested: {}", pathInfo);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
            }
        } catch (Exception e) {
            logger.error("Error processing party request", e);
            request.setAttribute("error", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        logger.debug("Processing party path: {}", pathInfo);

        try {
            if (pathInfo == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
            } else if (pathInfo.equalsIgnoreCase("/add")) {
                handleAddParty(request, response);
            } else if (pathInfo.startsWith("/update/")) {
                String partyId = pathInfo.substring(8); // Extract ID after "/update/"
                handleUpdateParty(request, response, partyId);
            } else if (pathInfo.startsWith("/delete/")) {
                String partyId = pathInfo.substring(8); // Extract ID after "/delete/"
                handleDeleteParty(request, response, partyId);
            } else if (pathInfo.startsWith("/search/")) {
                handleSearch(request, response);
            } else {
                logger.warn("Invalid path requested: {}", pathInfo);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
            }
        } catch (Exception e) {
            logger.error("Error processing party action", e);
            request.setAttribute("error", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    protected static void handleListParties(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("Listing all parties");
        List<Party> parties = PartyService.getAllPartys();
        request.setAttribute("parties", parties);
        request.getRequestDispatcher("/WEB-INF/pages/admin/parties.jsp").forward(request, response);
        logger.info("Successfully listed {} parties", parties.size());
    }


    private void handleListPartiesByQuery(HttpServletRequest request, HttpServletResponse response, String searchQuery)
            throws ServletException, IOException {
        logger.debug("Listing filtered parties for search query: {}",searchQuery);
        List<Party> parties = PartyService.getAllPartys();
        parties = Party.searchParties(parties,searchQuery);
        request.setAttribute("parties", parties);
        request.setAttribute("searchInput",searchQuery);
        request.getRequestDispatcher("/WEB-INF/pages/admin/parties.jsp").forward(request, response);
        logger.info("Successfully listed {} parties", parties.size());
    }


    private void handleViewParty(HttpServletRequest request, HttpServletResponse response, String partyId)
            throws ServletException, IOException {
        logger.debug("Viewing party with ID: {}", partyId);

        if (ValidationUtil.isNullOrEmpty(partyId) || !ValidationUtil.isNumeric(partyId)) {
            logger.warn("Invalid party ID: {}", partyId);
            request.setAttribute("error", "Invalid party ID");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        Party party = PartyService.getPartyById(request, response, Integer.parseInt(partyId));
        List<Candidate> partyCandidates = CandidateService.getCandidatesByParty(request,response,party.getPartyId());
        System.out.println("ajhsjhagsjhgahgjsghjaghjshgjjghasjhga"+request.getAttribute("partyCandidates"));
        request.setAttribute("partyCandidates", partyCandidates);
        if (party == null) {
            logger.warn("Party not found with ID: {}", partyId);
            request.setAttribute("error", "Party not found");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        request.setAttribute("party", party);
        request.getRequestDispatcher("/WEB-INF/pages/admin/party-details.jsp").forward(request, response);
        logger.info("Successfully viewed party ID: {}", partyId);
    }


    private void handleSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchQuery = request.getParameter("searchInput");
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            handleListParties(request,response);
            return;
        }

        handleListPartiesByQuery(request, response, searchQuery);
    }


    private void handleAddParty(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("Attempting to add new party");

        boolean success = PartyService.addPartyElection(request, response);
        if (success) {
            logger.info("Successfully added new party");
            response.sendRedirect(request.getContextPath() + "/admin/party/");
        } else {
            logger.warn("Failed to add new party");
            request.setAttribute("error", "Failed to add party");
            request.getRequestDispatcher("/WEB-INF/pages/admin/add-party.jsp").forward(request, response);
        }
    }

    private void handleUpdateParty(HttpServletRequest request, HttpServletResponse response, String partyId)
            throws ServletException, IOException {
        logger.debug("Updating party with ID: {}", partyId);

        if (ValidationUtil.isNullOrEmpty(partyId) || !ValidationUtil.isNumeric(partyId)) {
            logger.warn("Invalid party ID for update: {}", partyId);
            request.setAttribute("error", "Invalid party ID");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        boolean success = PartyService.updateParty(request, response, Integer.parseInt(partyId));
        if (success) {
            logger.info("Successfully updated party ID: {}", partyId);
            response.sendRedirect(request.getContextPath() + "/admin/party/" );
        } else {
            logger.warn("Failed to update party ID: {}", partyId);
            request.setAttribute("error", "Failed to update party");
            request.getRequestDispatcher("/WEB-INF/pages/admin/update-party.jsp").forward(request, response);
        }
    }

    private void handleDeleteParty(HttpServletRequest request, HttpServletResponse response, String partyId)
            throws ServletException, IOException {
        logger.debug("Deleting party with ID: {}", partyId);

        if (ValidationUtil.isNullOrEmpty(partyId) || !ValidationUtil.isNumeric(partyId)) {
            logger.warn("Invalid party ID for deletion: {}", partyId);
            request.setAttribute("error", "Invalid party ID");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        boolean success = PartyService.deleteParty(request, response, Integer.parseInt(partyId));
        if (success) {
            logger.info("Successfully deleted party ID: {}", partyId);
            response.sendRedirect(request.getContextPath() + "/admin/party/");
        } else {
            logger.warn("Failed to delete party ID: {}", partyId);
            request.setAttribute("error", "Failed to delete party");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void handleAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("Displaying party add form");
        request.getRequestDispatcher("/WEB-INF/pages/admin/add-party.jsp").forward(request, response);
        logger.info("Successfully displayed party add form");
    }

    private void handleEditForm(HttpServletRequest request, HttpServletResponse response, String partyId)
            throws ServletException, IOException {
        logger.debug("Displaying edit form for party ID: {}", partyId);

        if (ValidationUtil.isNullOrEmpty(partyId) || !ValidationUtil.isNumeric(partyId)) {
            logger.warn("Invalid party ID for edit form: {}", partyId);
            request.setAttribute("error", "Invalid party ID");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        Party party = PartyService.getPartyById(request, response, Integer.parseInt(partyId));
        if (party == null) {
            logger.warn("Party not found with ID: {}", partyId);
            request.setAttribute("error", "Party not found");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        request.setAttribute("party", party);
        request.getRequestDispatcher("/WEB-INF/pages/admin/update-party.jsp").forward(request, response);
        logger.info("Successfully displayed edit form for party ID: {}", partyId);
    }
}