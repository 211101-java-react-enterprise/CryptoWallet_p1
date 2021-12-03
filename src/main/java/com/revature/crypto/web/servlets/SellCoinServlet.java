package com.revature.crypto.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.crypto.exceptions.InvalidRequestException;
import com.revature.crypto.exceptions.UnauthorizedException;
import com.revature.crypto.models.Coin;
import com.revature.crypto.models.User;
import com.revature.crypto.services.CoinService;
import com.revature.crypto.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SellCoinServlet extends HttpServlet {
    private UserService userService;
    private CoinService coinService;
    private ObjectMapper objectMapper;

    public SellCoinServlet(UserService userService, CoinService coinService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.coinService = coinService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        try {
            Coin transaction = objectMapper.readValue(req.getInputStream(), Coin.class);

            HttpSession session = req.getSession();
            User verifiedUser = (User) session.getAttribute("verifiedUser");
            transaction.setUser_Id(verifiedUser.getUserId());

            if (verifiedUser == null) throw new UnauthorizedException("Could not authenticate User credentials.");

            if (coinService.sellCoin(transaction, verifiedUser)) {
                if (userService.updateUser(verifiedUser)) {
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);

                } else {
                    resp.setStatus(418);
                    throw new InvalidRequestException("Failed to update user, enjoy your free money!");
                }

            } else {
                resp.setStatus(400);
                throw new InvalidRequestException("Could not persist coin purchase to database");
            }
        } catch (UnauthorizedException e) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
