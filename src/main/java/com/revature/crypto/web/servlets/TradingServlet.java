package com.revature.crypto.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.crypto.models.Coin;
import com.revature.crypto.models.User;
import com.revature.crypto.services.CoinService;
import com.revature.crypto.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TradingServlet extends HttpServlet {

    private UserService userService;
    private CoinService coinService;
    private ObjectMapper objectMapper;

    public TradingServlet(UserService userService, CoinService coinService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.coinService = coinService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Coin transaction = objectMapper.readValue(req.getInputStream(), Coin.class);
        //User verifiedUser = userService.authenticateUser(transaction);
    }
}
