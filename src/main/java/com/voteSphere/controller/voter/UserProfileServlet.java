package com.voteSphere.controller.voter;

import com.voteSphere.model.*;
import com.voteSphere.service.ElectionService;
import com.voteSphere.service.UserService;
import com.voteSphere.service.VoteService;
import com.voteSphere.util.SessionUtil;
import com.voteSphere.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

        request.setAttribute("user",user);
        request.setAttribute("votingHistoryList",listOfVote);


        request.getRequestDispatcher("/WEB-INF/pages/voter/profile.jsp").forward(request,response);
    }

}
