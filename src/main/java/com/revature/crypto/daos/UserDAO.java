package com.revature.crypto.daos;



import com.revature.CryptoORM_P1.exception.InvalidClassException;
import com.revature.CryptoORM_P1.exception.MethodInvocationException;
import com.revature.CryptoORM_P1.mapper.SQLMapper;
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

    public User findUserByUsername(User sessionUser) throws InvalidClassException, MethodInvocationException, SQLException {
      return createUser(mapper.select(sessionUser, "username"));
    }

    public User findUserByUsernameAndPassword(User sessionUser) throws InvalidClassException, MethodInvocationException, SQLException {
        return createUser(
                mapper.select(sessionUser, "username", "password"));
    }

    @Override
    public boolean save(User newUser) throws InvalidClassException, MethodInvocationException, SQLException {
        int status = mapper.insert(newUser);
        if(status!=-1) return true;
        else return false;
    }

    @Override
    public List<User> findAll() throws InvalidClassException, MethodInvocationException, SQLException {
        ResultSet rs = mapper.select(new User());
        List<User> users = new ArrayList<>();
        while(rs.next()){
            users.add(createUser(rs));
        }
        return null;
    }

    @Override
    public User findById(User sessionUser) throws InvalidClassException, MethodInvocationException, SQLException {
        ResultSet rs = mapper.select(sessionUser);
        return createUser(rs);
    }

    @Override
    public boolean update(User updatedObj) throws InvalidClassException, MethodInvocationException, SQLException {
        int status = mapper.update(updatedObj, "user_uuid");
        if(status!=-1) return true;
        else return false;
    }

    @Override
    public boolean removeById(User removedUser) throws InvalidClassException, MethodInvocationException, SQLException {
        int status = mapper.delete(removedUser, "user_uuid");
        if(status!=-1) return true;
        else return false;
    }

    private User createUser(ResultSet rs) throws SQLException {
        if(rs.next()){
            return new User(rs.getString("user_uuid"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("first_name"),
                    rs.getString("password"),
                    rs.getDouble("dollars_invested"));
        }
        return null;
    }
}
