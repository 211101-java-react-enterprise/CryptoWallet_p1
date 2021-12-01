package com.revature.crypto;

import com.revature.CryptoORM_P1.mapper.SQLMapper;
import com.revature.crypto.models.User;
import com.revature.crypto.util.datasource.ConnectionFactory;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class OrmDriver {
    public static void main(String[] args) {
        Properties props = new Properties();
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            props.load(loader.getResourceAsStream("db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        SQLMapper mapper = new SQLMapper(props);
        String id = "UUID";
        String username= "namebo";
        String password = "password2";
        String firstname = "Name";
        String lastname = "Johnson";
        double amount = 34.3;
        User user = new User(id, username, password, firstname, lastname, amount);

        int rowsInserted = mapper.insert(user);

        System.out.println(rowsInserted);
    }
}
