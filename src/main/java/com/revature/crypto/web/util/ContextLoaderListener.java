package com.revature.crypto.web.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.CryptoORM_P1.mapper.SQLMapper;
import com.revature.crypto.daos.CoinDAO;
import com.revature.crypto.daos.UserDAO;
import com.revature.crypto.services.CoinService;
import com.revature.crypto.services.UserService;
import com.revature.crypto.web.servlets.LoginServlet;
import com.revature.crypto.web.servlets.RegistrationServlet;
import com.revature.crypto.web.servlets.TradingServlet;
import com.revature.crypto.web.servlets.ViewWalletServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.swing.text.View;
import java.io.IOException;
import java.util.Properties;

public class ContextLoaderListener implements ServletContextListener {

    UserService userService;
    CoinService coinService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Properties props = new Properties();
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            props.load(loader.getResourceAsStream("db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        SQLMapper.setProperties(props);

        ObjectMapper objectMapper = new ObjectMapper();

        UserDAO userDAO = new UserDAO();
        UserService userService = new UserService(userDAO);

        CoinDAO coinDAO = new CoinDAO();
        CoinService coinService = new CoinService(coinDAO);

        LoginServlet loginServlet = new LoginServlet(userService, objectMapper);
        RegistrationServlet registrationServlet = new RegistrationServlet(userService, objectMapper);
        TradingServlet tradingServlet = new TradingServlet(userService, coinService, objectMapper);
        ViewWalletServlet viewWalletServlet = new ViewWalletServlet(userService, coinService, objectMapper);


        ServletContext context = sce.getServletContext();
        context.addServlet("LoginServlet", loginServlet).addMapping("/login");



    }



}
