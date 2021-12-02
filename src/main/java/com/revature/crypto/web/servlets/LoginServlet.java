package com.revature.crypto.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.crypto.services.UserService;

import javax.servlet.http.HttpServlet;

public class LoginServlet extends HttpServlet {

    private UserService userService;
    private ObjectMapper objectMapper;

    public LoginServlet(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;

    }
}
