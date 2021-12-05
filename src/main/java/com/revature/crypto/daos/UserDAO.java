package com.revature.crypto.daos;



import com.revature.CryptoORM_P1.exception.InvalidClassException;
import com.revature.CryptoORM_P1.exception.MethodInvocationException;
import com.revature.CryptoORM_P1.mapper.SQLMapper;
import com.revature.crypto.exceptions.ConnectionDatabaseException;
import com.revature.crypto.exceptions.InvalidRequestException;
import com.revature.crypto.models.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class UserDAO implements CrudDAO<User> {

    private SQLMapper mapper;

    public UserDAO(){
        mapper = SQLMapper.getInstance();
    }

    public User findUserByUsername(User sessionUser)  {

        try {
            return createUser(mapper.select(sessionUser, "username"));
        } catch (SQLException e) {
            throw new ConnectionDatabaseException();
        }
    }

    public User findUserByUsernameAndPassword(User sessionUser)  {
        try {
            return createUser(
                    mapper.select(sessionUser, "username", "password"));
        } catch (SQLException e) {
            throw new ConnectionDatabaseException();
        }
    }

    @Override
    public boolean save(User newUser) {
        int status = 0;
        try {
            status = mapper.insert(newUser);
        } catch (SQLException e) {
            throw new ConnectionDatabaseException();
        }
        if(status!=-1) return true;
        else return false;
    }

    @Override
    public List<User> findAll()  {
        ResultSet rs = null;
        try {
            rs = mapper.select(new User());
            List<User> users = new ArrayList<>();
            while(rs.next()){
                users.add(createUser(rs));
            }
            return users;
        } catch (SQLException e) {
            throw new ConnectionDatabaseException();
        }
    }

    @Override
    public User findById(User sessionUser) {
        ResultSet rs = null;
        try {
            rs = mapper.select(sessionUser);
            return createUser(rs);
        } catch (SQLException e) {
            throw new ConnectionDatabaseException();
        }
    }

    @Override
    public boolean update(User updatedObj)  {
        int status = 0;
        try {
            status = mapper.update(updatedObj, "user_uuid");
        } catch (SQLException e) {
           throw new ConnectionDatabaseException();
        }
        if(status!=-1) return true;
        else return false;
    }

    @Override
    public boolean removeById(User removedUser) {
        int status = 0;
        try {
            status = mapper.delete(removedUser, "user_uuid");
        } catch (SQLException e) {
            throw new ConnectionDatabaseException();
        }
        if(status!=-1) return true;
        else return false;
    }

    private User createUser(ResultSet rs) {
        try {
            if(rs.next()){
                return new User(rs.getString("user_uuid"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("first_name"),
                        rs.getString("password"),
                        rs.getDouble("dollars_invested"));
            }
            return null;
        } catch (SQLException e) {
            throw new ConnectionDatabaseException();
        }
    }
}
