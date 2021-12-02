package com.revature.crypto.daos;

import com.revature.CryptoORM_P1.mapper.SQLMapper;
import com.revature.crypto.exceptions.InvalidRequestException;
import com.revature.crypto.models.Coin;
import com.revature.crypto.models.User;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CoinDAO implements CrudDAO<Coin>{

    SQLMapper mapper;

    public CoinDAO(){
        mapper = SQLMapper.getInstance();
    }

    @Override
    public Coin findById(Coin sessionCoin) {
        ResultSet rs = mapper.select(sessionCoin);
        return createCoin(rs);
    }

    @Override
    public boolean save(Coin newCoin) {
        int status = mapper.insert(newCoin);
        if(status!=-1) return true;
        else return false;
    }

    @Override
    public List<Coin> findAll() {
        ResultSet rs = mapper.select(new User());
        List<Coin> coins = new ArrayList<>();
        try{
            while(rs.next()){
                coins.add(createCoin(rs));
            }
        } catch(Exception e){
            throw new InvalidRequestException("Failed to retrieve list of users");
        }
        return null;
    }

    @Override
    public boolean update(Coin updatedObj) {
        int status = mapper.update(updatedObj, "user_uuid");
        if(status!=-1) return true;
        else return false;
    }


    @Override
    public boolean removeById(Coin removedCoin) {
        int status = mapper.delete(removedCoin);
        if(status!=-1) return true;
        else return false;
    }

    private Coin createCoin (ResultSet rs){
        try{
            if(rs.next()){
                return new Coin(rs.getString("currency_pair"),
                        rs.getDouble("amount"),
                        rs.getString("user_uuid"));
            }
        }catch(Exception e) {
            throw new InvalidRequestException("No matching Id found in database");
        }
        return null;
    }
}
