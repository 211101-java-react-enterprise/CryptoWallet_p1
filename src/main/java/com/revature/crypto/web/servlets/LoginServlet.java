package com.revature.crypto.web.servlets;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.revature.CryptoORM_P1.exception.InvalidClassException;
import com.revature.CryptoORM_P1.exception.MethodInvocationException;
import com.revature.crypto.exceptions.AuthenticationException;
import com.revature.crypto.exceptions.ConnectionDatabaseException;
import com.revature.crypto.exceptions.InvalidRequestException;
import com.revature.crypto.models.User;
import com.revature.crypto.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {

    private final UserService userService;
    private final ObjectMapper objectMapper;
    private static final Logger logger = LogManager.getLogger(BuyCoinServlet.class);

    public LoginServlet(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            HttpSession session = req.getSession(false);
            if(session!=null) throw new InvalidRequestException("already logged in");//somebody is logged in
            User creds = objectMapper.readValue(req.getInputStream(), User.class);
            User verifiedUser = userService.authenticateUser(creds);

            // adds a Cookie to the response containing a SESSION_ID
            // that SESSION_ID is stored within Tomcat
            // and is used to identify the requester in future requests
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("verifiedUser", verifiedUser);


            resp.setStatus(204); // success, but nothing to return (NO_CONTENT)
            logger.trace("Login Successful");

        } catch (InvalidRequestException | UnrecognizedPropertyException e) {
            resp.setStatus(400); // user made a bad request
            logger.error("Already logged in");
        } catch (AuthenticationException e) {
            logger.error("Invalid username or password!");
            resp.setStatus(401); // user provided incorrect credentials
        } catch (InvalidClassException | MethodInvocationException e) {
            logger.error("Internal server error"); // for dev purposes only, to be deleted before push to prod
            resp.setStatus(500);
        } catch(ConnectionDatabaseException e) {
            logger.error("Could not connect to database");
            resp.setStatus(408);
        }

    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            logger.info("Logout Successful");
            resp.setStatus(204);
            session.invalidate(); // invalidates the session associated with this request (logging the user out)
        } else {
            logger.error("No user logged in!");
            resp.setStatus(401);
        }
    }

}
