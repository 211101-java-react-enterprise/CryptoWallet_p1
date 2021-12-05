package com.revature.crypto.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.CryptoORM_P1.exception.InvalidClassException;
import com.revature.CryptoORM_P1.exception.MethodInvocationException;
import com.revature.crypto.exceptions.InvalidRequestException;
import com.revature.crypto.exceptions.UnauthorizedException;
import com.revature.crypto.models.Coin;
import com.revature.crypto.models.User;
import com.revature.crypto.services.CoinService;
import com.revature.crypto.services.UserService;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class BuyCoinServlet extends HttpServlet {

    private UserService userService;
    private CoinService coinService;
    private ObjectMapper objectMapper;
    private static final Logger logger = LogManager.getLogger(BuyCoinServlet.class);

    public BuyCoinServlet(UserService userService, CoinService coinService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.coinService = coinService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Coin transaction = objectMapper.readValue(req.getInputStream(), Coin.class);

            HttpSession session = req.getSession(false);

            if (session == null) throw new UnauthorizedException("Could not authenticate User credentials.");

            User verifiedUser = (User) session.getAttribute("verifiedUser");
            transaction.setUser_Id(verifiedUser.getUserId());

            if (coinService.buyCoin(transaction, verifiedUser)) {

                if (userService.updateUser(verifiedUser)) {
                    logger.trace("successfully updated user");
                    resp.setStatus(204);
                } else {throw new InvalidRequestException("Failed to update user, enjoy your free money!");}

            } else {throw new InvalidRequestException("Could not persist coin purchase to database");}

        } catch (InvalidClassException | MethodInvocationException e) {
            logger.error("could not invoke method");
            resp.setStatus(500);
        } catch (InvalidRequestException | IOException e) {
            logger.error("the user made an invalid request");
            resp.setStatus(400);
        } catch (UnauthorizedException e) {
            logger.error("the user is not authorized");
            resp.setStatus(401);
        }
    }
}
