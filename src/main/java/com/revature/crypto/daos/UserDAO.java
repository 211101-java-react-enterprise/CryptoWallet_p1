package com.revature.crypto.daos;



import com.revature.CryptoORM_P1.mapper.SQLMapper;
import com.revature.crypto.models.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class UserDAO implements CrudDAO<User> {

    private SQLMapper mapper;

    public UserDAO(){
        Properties props = new Properties();
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            props.load(loader.getResourceAsStream("db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //mapper = new SQLMapper(props);
    }

    public UserDAO findUserByUsername(String username) {

      return null;
    }

    public User findUserByUsernameAndPassword(String username, String password) {
        return null;

    }

    @Override
    public User save(User newUser) {

        return null;

    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findById(String id) {
        return null;
    }

    @Override
    public boolean update(User updatedObj) {
        return false;
    }

    @Override
    public boolean removeById(String id) {
        return false;
    }

}
