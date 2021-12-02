package com.revature.crypto.daos;

import com.revature.crypto.models.User;

import java.util.List;

public class CoinDAO implements CrudDAO<User>{
    @Override
    public User findById(String id) {
        return null;
    }

    @Override
    public boolean save(User newObj) {
        return false;
    }

    @Override
    public List<User> findAll() {
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
