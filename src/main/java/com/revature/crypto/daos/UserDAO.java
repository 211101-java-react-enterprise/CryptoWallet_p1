package com.revature.crypto.daos;



import com.revature.CryptoORM_P1.mapper.SQLMapper;
import com.revature.crypto.exceptions.InvalidRequestException;
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
            SQLMapper.setProperties(props);
            mapper = SQLMapper.getInstance();
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
    public boolean save(User newUser) {
        int status = mapper.insert(newUser);
        if(status!=-1) return true;
        else return false;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findById(String id) {
        ResultSet rs = mapper.select(new User(id, "", "", "", "", -1), "user_uuid");
        User newUser;
        try{
            if(rs.next()){
                return new User(rs.getString("user_uuid"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("first_name"),
                        rs.getString("password"),
                        rs.getDouble("dollars_invested"));
            }
        }catch(Exception e) {
            throw new InvalidRequestException("No matching Id found in database");
        }
        return null;

    }

    @Override
    public boolean update(User updatedObj) {
        int status = mapper.update(updatedObj, "user_uuid");
        if(status!=-1) return true;
        else return false;
    }

    @Override
    public boolean removeById(String id) {
        return false;
    }

}
