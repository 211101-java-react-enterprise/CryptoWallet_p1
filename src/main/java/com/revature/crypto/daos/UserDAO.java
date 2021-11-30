package com.revature.crypto.daos;

import com.revature.crypto.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements CrudDAO<User>{

    @Override
    public User findById(String id) {
        return null;
    }


}
