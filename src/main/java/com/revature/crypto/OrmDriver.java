package com.revature.crypto;

import com.revature.CryptoORM_P1.mapper.SQLMapper;
import com.revature.crypto.models.Coin;
import com.revature.crypto.models.User;
import com.revature.crypto.util.datasource.ConnectionFactory;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        String id = "UUID4";
        String username= "namebozies";
        String password = "password3";
        String firstname = "Name";
        String lastname = "Johnson";
        double amount = 34.3;
        User user = new User(id, username, password, firstname, lastname, amount);
        Coin coin = new Coin("BTC-USD", 1, "UUID3");

//        int rowsInserted = mapper.insert(user);
//
//        System.out.println(rowsInserted);

//        ResultSet rs = mapper.select(user, "username", "password");
//        try{
//            rs.next();
//            System.out.println("U: "+rs.getString("username"));
//            System.out.println("P: "+rs.getString("password"));
//        } catch(Exception e){
//            System.out.println();
//        }

        //mapper.update(user, "user_uuid");
        //mapper.delete(user, "user_uuid");

        //mapper.joinSelect(user, Coin.class);
        //mapper.join
        mapper.insert(coin);


    }
}
