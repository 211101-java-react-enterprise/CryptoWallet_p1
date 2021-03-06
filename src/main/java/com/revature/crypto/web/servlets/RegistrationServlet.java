package com.revature.crypto.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.CryptoORM_P1.exception.InvalidClassException;
import com.revature.CryptoORM_P1.exception.MethodInvocationException;
import com.revature.crypto.exceptions.AuthenticationException;
import com.revature.crypto.exceptions.ConnectionDatabaseException;
import com.revature.crypto.exceptions.InvalidRequestException;
import com.revature.crypto.exceptions.UniqueCredentialsException;
import com.revature.crypto.models.User;
import com.revature.crypto.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class RegistrationServlet extends HttpServlet {

    private final UserService userService;
    private final ObjectMapper objectMapper;
    private static final Logger logger = LogManager.getLogger(BuyCoinServlet.class);

    public RegistrationServlet(UserService userService, ObjectMapper mapper) {
        this.userService = userService;
        this.objectMapper = mapper;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            PrintWriter respWriter = resp.getWriter();
            resp.setContentType("application/json");

            User newUser = objectMapper.readValue(req.getInputStream(), User.class);
            newUser.setUsdBalance(10000);

            if (userService.registerNewUser(newUser)) {
                resp.setStatus(201);
                logger.trace("User persisted to database");
            } else {
                //failed to persist user to database
                resp.setStatus(500);
                logger.error("failed to persist user to database");
            }

        } catch (MethodInvocationException | InvalidClassException e) {
            resp.setStatus(500);
            logger.error("Internal Server Error");
        } catch (IOException | InvalidRequestException e) {
            resp.setStatus(400);
            logger.error("User made a bad request");
        } catch(ConnectionDatabaseException e) {
            resp.setStatus(403);
            logger.error("User already exists!");
        }
        catch(UniqueCredentialsException e){
            resp.setStatus(403);
            logger.error("Account already exists!");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        try {
            PrintWriter respWriter = resp.getWriter();
            resp.setContentType("application/json");

            HttpSession session = req.getSession(false);

            if (session == null) throw new AuthenticationException("No valid session");

            User deleteUser = (User) session.getAttribute("verifiedUser");

            if (userService.deleteUser(deleteUser)) {
                req.getSession(false).invalidate();
                resp.setStatus(204);
                logger.trace("User deleted from database successfully");
            } else {
                resp.setStatus(500);
                logger.error("Failed to delete user");
            }
        } catch (MethodInvocationException | InvalidClassException | IOException e) {
            resp.setStatus(500);
            logger.error("Internal Server Error");
        } catch (AuthenticationException e) {
            resp.setStatus(401);
            logger.error("No user logged in");
        } catch(ConnectionDatabaseException e) {
            resp.setStatus(408);
            logger.error("Could not connect to database");
        }

    }
}
