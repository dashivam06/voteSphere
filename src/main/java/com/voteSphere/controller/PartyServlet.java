package com.voteSphere.controller;

import java.io.IOException;
import java.util.List;

import com.voteSphere.model.Party;
import com.voteSphere.service.PartyService;
import com.voteSphere.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;





@WebServlet("/party")
public class PartyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	

	public PartyServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String partyId = request.getParameter("id");

		boolean isValidPartyId = ValidationUtil.isNumeric(partyId);

		if (isValidPartyId) {
			request.setAttribute("error", "Party not found. Invalid Party Id");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}

		if (partyId == null || partyId.isEmpty() || isValidPartyId) {
			// No ID passed: return all parties
			List<Party> allParties = PartyService.getAllPartys();
			request.setAttribute("parties", allParties);
			request.getRequestDispatcher("/partyList.jsp").forward(request, response);
		} else {

			// ID passed: return specific party
			Party party = PartyService.getPartyById(request, response, Integer.parseInt(partyId)); // assume this method
																									// exists
			if (party != null) {
				request.setAttribute("party", party);
				request.getRequestDispatcher("/partyDetails.jsp").forward(request, response);
			} else {
				// Handle not found
				request.setAttribute("error", "Party not found");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
			}
		}
	}

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		boolean added = PartyService.addPartyElection(request, response);

		if (added) {
			response.sendRedirect("party");
		} else {
			request.setAttribute("error", "Failed to add party");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String partyId = request.getParameter("id");
		if (partyId == null || !ValidationUtil.isNumeric(partyId)) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID for update.");
			return;
		}

		boolean updated = PartyService.updateParty(request, response, Integer.parseInt(partyId));

		if (updated) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Party not found to update.");
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String partyId = request.getParameter("id");

		// Validate the party ID
		if (partyId == null || partyId.trim().isEmpty() || !ValidationUtil.isNumeric(partyId)) {
			request.setAttribute("party_id_error", "Invalid ID for deletion.");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
			return;
		}

		boolean deleted = false;

		// Attempt to delete the party
		deleted = PartyService.deleteParty(request, response, Integer.parseInt(partyId));

		if (deleted) {
			response.sendRedirect("party"); // Successfully deleted, redirect to party list
		} else {
			// Deletion failed, set an error and forward
			request.setAttribute("error", "Failed to delete the party. Please try again.");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}

	}

}
