package com.revature.crypto.web.servlets;

import com.fasterxml.jackson.core.JsonParseException;
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

            boolean wasRegistered = userService.registerNewUser(newUser);

        } catch (JsonParseException e) {

        }
    }
}
