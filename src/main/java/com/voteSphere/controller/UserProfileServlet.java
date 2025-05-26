package com.voteSphere.controller;

import com.voteSphere.model.AuthUser;
import com.voteSphere.model.User;
import com.voteSphere.model.Vote;
import com.voteSphere.service.UserService;
import com.voteSphere.service.VoteService;
import com.voteSphere.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/profile")
public class UserProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer userId = SessionUtil.getUserValueFromSession(request, AuthUser::getUserId);
        User user = UserService.getUserById(userId);
        List<Vote> listOfVote = VoteService.getVotesByUserId(userId);
        System.out.println(listOfVote);

        request.setAttribute("user",user);
        request.setAttribute("votingHistoryList",listOfVote);


        request.getRequestDispatcher("/WEB-INF/pages/voter/profile.jsp").forward(request,response);
    }


}
