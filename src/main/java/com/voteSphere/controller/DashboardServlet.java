package com.voteSphere.controller;

import java.io.IOException;
import java.util.List;

import com.voteSphere.model.*;
import com.voteSphere.service.ElectionService;
import com.voteSphere.service.TokenService;
import com.voteSphere.service.UserService;
import com.voteSphere.service.VoteService;
import com.voteSphere.util.PdfUtil;
import com.voteSphere.util.SessionUtil;

import com.voteSphere.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private static final String ADMIN_DASHBOARD = "/WEB-INF/pages/admin/dashboard.jsp";
    private static final String VOTER_DASHBOARD = "/WEB-INF/pages/voter/dashboard.jsp";
    private static final String LOGIN_PAGE = "/login";
    private static final String ROLE_ADMIN = "admin";
    private static final String ROLE_USER = "voter";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String userRole = SessionUtil.getUserValueFromSession(request, AuthUser::getRole);

        // If no authenticated user or role is found
        if (userRole == null) {
            response.sendRedirect(request.getContextPath() + LOGIN_PAGE);
            return;
        }

        List<Election> electionList = ElectionService.getAllElections();


        User user = UserService.getUserById(SessionUtil.getUserValueFromSession(request,AuthUser::getUserId));
        request.setAttribute("user",user);


        String destinationPage;

        switch (userRole.toLowerCase()) {
            case ROLE_ADMIN:

                List<Vote> voteList = VoteService.getAllVotes();

                destinationPage = ADMIN_DASHBOARD;
                request.setAttribute("totalElection",electionList.size());
                request.setAttribute("totalVoter", ValidationUtil.formatWithCommas(UserService.getAllUsers().size()));
                request.setAttribute("totalVotes", ValidationUtil.formatWithCommas(voteList.size()));

                break;

            case ROLE_USER:

                List<Election> ongoingElection = ElectionService.getRunningElections();
                List<Election> pastElection = ElectionService.getPastElections();
                List<Election> upcomingElection = ElectionService.getUpcomingElections();

                List<Vote> totalVoteCountOfUser = VoteService.getVotesByUserId(user.getUserId());

                List<Token> totalActiveToken = TokenService.getActiveTokensByUserId(user.getUserId());

                request.setAttribute("electionList",electionList);
                request.setAttribute("ongoingElection",ongoingElection);
                request.setAttribute("pastElection",pastElection);
                request.setAttribute("upcomingElections",upcomingElection);
                request.setAttribute("totalVoteCountOfUser",totalVoteCountOfUser.size());
                request.setAttribute("totalActiveToken",totalActiveToken.size());
//                PdfUtil.generatePdfReportForVerifiedUser(request.getServletContext(),user);


                destinationPage = VOTER_DASHBOARD;
                break;

            default:
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Unauthorized role");
                return;
        }

        request.getRequestDispatcher(destinationPage).forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}