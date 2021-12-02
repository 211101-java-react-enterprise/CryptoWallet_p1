package com.revature.crypto.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.crypto.services.CoinService;
import com.revature.crypto.services.UserService;

import javax.servlet.http.HttpServlet;

public class ViewWalletServlet extends HttpServlet {

    private UserService userService;
    private CoinService coinService;
    private ObjectMapper objectMapper;

    public ViewWalletServlet(UserService userService, CoinService coinService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.coinService = coinService;
        this.objectMapper = objectMapper;
    }
}
