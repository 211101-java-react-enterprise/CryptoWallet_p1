package com.revature.crypto.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.crypto.exceptions.AuthenticationException;
import com.revature.crypto.exceptions.InvalidRequestException;
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
import java.io.PrintWriter;
import java.util.List;

public class ViewWalletServlet extends HttpServlet {

    private UserService userService;
    private CoinService coinService;
    private ObjectMapper objectMapper;

    public ViewWalletServlet(UserService userService, CoinService coinService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.coinService = coinService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");
//        Coin sessionCoin = objectMapper.readValue(req.getInputStream(), Coin.class);
        HttpSession session = req.getSession();
        Coin coin = new Coin();


        if(session==null){
            resp.setStatus(401);//unauthenticated client
            throw new AuthenticationException("nobody is logged in!");
        }
        try{
            User authUser = (User) session.getAttribute("verifiedUser");
            //coin.setUser_Id(authUser.getUserId());
            List<Coin> coins = coinService.getCoins(authUser.getUserId());
            String payload = objectMapper.writeValueAsString(coins);
            resp.getWriter().write(payload);
            resp.setStatus(200);
        }catch (Exception e){
            resp.setStatus(500);
            throw new InvalidRequestException("bad request");
        }
    }


}
