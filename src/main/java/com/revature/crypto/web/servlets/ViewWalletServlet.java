package com.revature.crypto.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

        HttpSession session = req.getSession(false);


        try{
            if(session==null){
                throw new AuthenticationException("nobody is logged in!");
            }

            User authUser = (User) session.getAttribute("verifiedUser");

            List<Coin> coins = coinService.getCoins(authUser.getUserId());
            double walletValue = coinService.getTotalWalletValue(coins);

            ObjectNode json = objectMapper.createObjectNode();
            json.put("Wallet Value:", String.format("$%.2f", walletValue));

            ObjectNode coinNode = objectMapper.createObjectNode();
            for (Coin coin : coins) {
                coinNode.put(coin.getCurrencyPair(), coin.getAmount());
            }
            json.set("Coins:", coinNode);

            String payload = objectMapper.writeValueAsString(json);

            resp.getWriter().write(payload);
            resp.setStatus(200);
        }catch (AuthenticationException e) {
            resp.setStatus(401);//unauthenticated client
        }catch (Exception e){
            resp.setStatus(500);

            System.out.println("\n\n");
            e.printStackTrace();
            System.out.println("\n\n");

            throw new InvalidRequestException("bad request");
        }
    }


}
