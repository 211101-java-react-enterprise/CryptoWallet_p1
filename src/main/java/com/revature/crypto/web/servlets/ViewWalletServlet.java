package com.revature.crypto.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.revature.CryptoORM_P1.exception.InvalidClassException;
import com.revature.CryptoORM_P1.exception.MethodInvocationException;
import com.revature.crypto.exceptions.AuthenticationException;
import com.revature.crypto.exceptions.ConnectionDatabaseException;
import com.revature.crypto.models.Coin;
import com.revature.crypto.models.User;
import com.revature.crypto.services.CoinService;
import com.revature.crypto.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private static final Logger logger = LogManager.getLogger(BuyCoinServlet.class);

    public ViewWalletServlet(UserService userService, CoinService coinService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.coinService = coinService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        try{
            PrintWriter writer = resp.getWriter();
            resp.setContentType("application/json");

            HttpSession session = req.getSession(false);

            if(session==null){
                logger.error("inactive session error");
                throw new AuthenticationException("nobody is logged in!");
            }

            User authUser = (User) session.getAttribute("verifiedUser");

            List<Coin> coins = coinService.getCoins(authUser.getUserId());
            double walletValue = coinService.getTotalWalletValue(coins);

            ObjectNode json = objectMapper.createObjectNode();
            json.put("USD Available: ", String.format("$%.2f",authUser.getUsdBalance()));
            json.put("Wallet Value:", String.format("$%.2f", walletValue));

            ObjectNode coinNode = objectMapper.createObjectNode();
            for (Coin coin : coins) {
                coinNode.put(coin.getCurrencyPair(), coin.getAmount());
            }
            json.set("Coins:", coinNode);

            String payload = objectMapper.writeValueAsString(json);

            resp.getWriter().write(payload);
            resp.setStatus(200);
            logger.trace("request successful");

        }catch (AuthenticationException e) {
            resp.setStatus(401);//unauthenticated client
            logger.error("the user made an unauthenticated request. Please log in");
        } catch (InvalidClassException | MethodInvocationException | IOException e) {
            resp.setStatus(500);
            logger.error("internal server error");
        } catch(ConnectionDatabaseException e) {
            logger.error("Could not connect to database");
            resp.setStatus(408);
        }
    }


}
