package com.revature.crypto.daos;

import com.revature.crypto.models.Coin;

import java.util.List;

public class CoinDAO implements CrudDAO<Coin>{

    @Override
    public Coin findById(Coin newObj) {
        return null;
    }

    @Override
    public boolean save(Coin newObj) {
        return false;
    }

    @Override
    public List<Coin> findAll() {
        return null;
    }

    @Override
    public boolean update(Coin updatedObj) {
        return false;
    }


    @Override
    public boolean removeById(Coin coin) {
        return false;
    }
}
