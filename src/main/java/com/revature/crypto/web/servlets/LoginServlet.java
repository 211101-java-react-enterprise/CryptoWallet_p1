package com.revature.crypto.web.servlets;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.revature.crypto.exceptions.AuthenticationException;
import com.revature.crypto.exceptions.InvalidRequestException;
import com.revature.crypto.models.User;
import com.revature.crypto.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    private final UserService userService;
    private final ObjectMapper objectMapper;

    public LoginServlet(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            User creds = objectMapper.readValue(req.getInputStream(), User.class);
            User verifiedUser = userService.authenticateUser(creds);

            // adds a Cookie to the response containing a SESSION_ID
            // that SESSION_ID is stored within Tomcat
            // and is used to identify the requester in future requests
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("verifiedUser", verifiedUser);


            resp.setStatus(204); // success, but nothing to return (NO_CONTENT)

        } catch (InvalidRequestException | UnrecognizedPropertyException e) {
            resp.setStatus(400); // user made a bad request
            e.printStackTrace();
        } catch (AuthenticationException e) {
            e.printStackTrace();
            resp.setStatus(401); // user provided incorrect credentials
        } catch (Exception e) {
            e.printStackTrace(); // for dev purposes only, to be deleted before push to prod
            resp.setStatus(500);
        }

    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            resp.setStatus(204);
            session.invalidate(); // invalidates the session associated with this request (logging the user out)
        } else {
            resp.setStatus(401);
        }
    }

}
