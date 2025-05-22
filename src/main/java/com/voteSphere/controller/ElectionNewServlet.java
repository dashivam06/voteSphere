package com.voteSphere.controller;

import java.io.IOException;
import java.util.List;

import com.voteSphere.model.Election;
import com.voteSphere.service.ElectionService;
import com.voteSphere.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/election")
public class ElectionServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public ElectionServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String electionId = request.getParameter("id");

        if (electionId == null || electionId.trim().isEmpty()) {
            // No ID passed: return all elections
            List<Election> allElections = ElectionService.getAllElections();
            request.setAttribute("elections", allElections);
            request.getRequestDispatcher("/electionList.jsp").forward(request, response);
            return;
        }

        electionId = electionId.trim();

        boolean isValidElectionId = ValidationUtil.isNumeric(electionId);

        if (!isValidElectionId) {
            request.setAttribute("error", "Election not found. Invalid Election ID.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        // ID passed and valid: return specific election
        Election election = ElectionService.getElectionById(request, response, Integer.parseInt(electionId));
        if (election != null) {
            request.setAttribute("election", election);
            request.getRequestDispatcher("/electionDetails.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Election not found.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        boolean success = ElectionService.addElection(request, response);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/election");
        } else {
            request.getRequestDispatcher("/electionForm.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String electionId = request.getParameter("id");

        if (electionId == null || electionId.trim().isEmpty() || !ValidationUtil.isNumeric(electionId)) {
            request.setAttribute("error", "Invalid Election ID for update.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        boolean success = ElectionService.updateElection(request, response, Integer.parseInt(electionId));

        if (success) {
            response.sendRedirect(request.getContextPath() + "/election?id=" + electionId);
        } else {
            request.getRequestDispatcher("/electionForm.jsp").forward(request, response);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String electionId = request.getParameter("id");

        if (electionId == null || electionId.trim().isEmpty() || !ValidationUtil.isNumeric(electionId)) {
            request.setAttribute("error", "Invalid Election ID for deletion.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        boolean success = ElectionService.deleteElection(request, response, Integer.parseInt(electionId));

        if (success) {
            response.sendRedirect(request.getContextPath() + "/election");
        } else {
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
