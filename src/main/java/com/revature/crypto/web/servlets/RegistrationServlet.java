package com.revature.crypto.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.crypto.models.User;
import com.revature.crypto.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RegistrationServlet extends HttpServlet {

    private final UserService userService;
    private final ObjectMapper objectMapper;

    public RegistrationServlet(UserService userService, ObjectMapper mapper) {
        this.userService = userService;
        this.objectMapper = mapper;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("application/json");

        try {
            User newUser = objectMapper.readValue(req.getInputStream(), User.class);
            newUser.setAmount_invested(10000);

            if (userService.registerNewUser(newUser)) {
                resp.setStatus(201);
            } else {
                //failed to persist user to database
                resp.setStatus(500);
            }

        } catch (Exception e) {
            // bad request from user
            resp.setStatus(400);
            e.printStackTrace();

        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("application/json");

        try {
            User deleteUser = (User) req.getSession(false).getAttribute("verifiedUser");

            if (userService.deleteUser(deleteUser)) {
                req.getSession(false).invalidate();
                resp.setStatus(204);
            } else {
                resp.setStatus(500);
            }
        } catch (Exception e) {
            resp.setStatus(400);
        }
    }
}
