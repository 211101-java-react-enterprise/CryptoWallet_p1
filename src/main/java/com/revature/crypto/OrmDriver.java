package com.revature.crypto;

import com.revature.CryptoORM_P1.mapper.SQLMapper;
import com.revature.crypto.daos.UserDAO;
import com.revature.crypto.models.Coin;
import com.revature.crypto.models.User;

import java.io.IOException;
import java.sql.ResultSet;
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
        SQLMapper.setProperties(props);
        SQLMapper mapper = SQLMapper.getInstance();
        UserDAO userDAO = new UserDAO();
        String id = "5256d1e5-3393-4640-8b9b-44f607a2ec84";
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

//        ResultSet rs = mapper.joinSelect(user, Coin.class, "user_uuid", "user_uuid", "user_uuid", "user_uuid");
//        try{
//            while(rs.next()){
//                System.out.println("amount: "+rs.getString("amount"));
//                System.out.println("name: "+rs.getString("username"));
//                System.out.println("pair: "+rs.getString("currency_pair"));
//                System.out.println("\n-------------------------------------------");
//            }
//
//        } catch(Exception e){
//            System.out.println();
//        }

        User user1 = userDAO.findById(user.getUserId());
        System.out.println("user1 username: "+user1.getUsername());

        //mapper.update(user, "user_uuid");
        //mapper.delete(user, "user_uuid");

        //mapper.joinSelect(user, Coin.class);
        //mapper.join
//        mapper.insert(coin);
//        System.out.println(mapper.joinSelect(user, Coin.class, "user_uuid", "user_uuid", "user_uuid", "user_uuid"));

    }
}
