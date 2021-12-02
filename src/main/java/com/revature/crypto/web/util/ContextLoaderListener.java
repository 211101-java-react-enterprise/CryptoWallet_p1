package com.revature.crypto.web.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.CryptoORM_P1.mapper.SQLMapper;
import com.revature.crypto.daos.CoinDAO;
import com.revature.crypto.daos.UserDAO;
import com.revature.crypto.services.CoinService;
import com.revature.crypto.services.UserService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
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
        UserService userService = new UserService();

        CoinDAO coinDAO = new CoinDAO();
        CoinService coinService = new CoinService();


    }



}
