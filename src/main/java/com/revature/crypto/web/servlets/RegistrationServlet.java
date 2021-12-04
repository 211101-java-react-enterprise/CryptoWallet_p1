package com.revature.crypto.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.CryptoORM_P1.exception.InvalidClassException;
import com.revature.CryptoORM_P1.exception.MethodInvocationException;
import com.revature.crypto.exceptions.AuthenticationException;
import com.revature.crypto.exceptions.InvalidRequestException;
import com.revature.crypto.models.User;
import com.revature.crypto.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class RegistrationServlet extends HttpServlet {

    private final UserService userService;
    private final ObjectMapper objectMapper;

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
            } else {
                //failed to persist user to database
                resp.setStatus(500);
            }

        } catch (MethodInvocationException | InvalidClassException e) {
            resp.setStatus(500);
            e.printStackTrace();
        } catch (IOException | SQLException | InvalidRequestException e) {
            resp.setStatus(400);
            e.printStackTrace();
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
            } else {
                resp.setStatus(500);
            }
        } catch (MethodInvocationException | InvalidClassException e) {
            e.printStackTrace();
            resp.setStatus(500);
        } catch (IOException | SQLException e) {
            resp.setStatus(400);
            e.printStackTrace();
        } catch (AuthenticationException e) {
            e.printStackTrace();
            resp.setStatus(401);
        }
    }
}
