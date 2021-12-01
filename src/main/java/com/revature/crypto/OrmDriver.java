package com.revature.crypto;

import com.revature.CryptoORM_P1.mapper.SQLMapper;
import com.revature.crypto.models.User;
import com.revature.crypto.util.datasource.ConnectionFactory;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrmDriver {
    public static void main(String[] args) {
        SQLMapper mapper = new SQLMapper(ConnectionFactory.getInstance().getConnection());
        String id = "UUID";
        String username= "namebo";
        String password = "password2";
        String firstname = "Name";
        String lastname = "Johnson";
        double amount = 34.3;
        User user = new User(id, username, password, firstname, lastname, amount);

        PreparedStatement pstmt = mapper.insert(user);
        try{
            int rowsInserted = pstmt.executeUpdate();
            System.out.println(rowsInserted);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
