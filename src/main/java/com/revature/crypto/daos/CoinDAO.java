package com.revature.crypto.daos;

import com.revature.CryptoORM_P1.mapper.SQLMapper;
import com.revature.crypto.exceptions.InvalidRequestException;
import com.revature.crypto.models.Coin;
import com.revature.crypto.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
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
        return createCoinList(mapper.select(new Coin()));
    }

    @Override
    public boolean update(Coin updatedObj) {
        int status = mapper.update(updatedObj, "user_uuid", "currency_pair");
        if(status!=-1) return true;
        else return false;
    }

    public List<Coin> getCoinsByUser(Coin coin){
        return createCoinList(mapper.select(coin, "user_uuid"));
    }


    @Override
    public boolean removeById(Coin removedCoin) {
        int status = mapper.delete(removedCoin);
        if(status!=-1) return true;
        else return false;
    }

    private Coin createCoin (ResultSet rs){
        try{
            return new Coin(rs.getString("currency_pair"),
                    rs.getDouble("amount"),
                    rs.getString("user_uuid"));
        }catch(Exception e) {
            throw new InvalidRequestException("No matching Id found in database");
        }
    }

    public double getCoinAmount(Coin coin) throws SQLException {
        ResultSet rs = mapper.select(coin, "user_uuid", "currency_pair");
        try {
            if(rs.next()) return rs.getDouble("amount");
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to create resultSet");
        }
    }

    /**
     * does the user have a given coin
     * return null if no
     */
    public Coin hasCoin(Coin coin){
        ResultSet rs = mapper.select(coin, "user_uuid", "currency_pair");
        try {
            if(!rs.next()) return null;
        } catch(Exception e){
            e.printStackTrace();
        }
        return createCoin(rs);
    }

    private List<Coin> createCoinList(ResultSet rs) {
        List<Coin> coins = new ArrayList<>();
        try{
            while(rs.next()){
                coins.add(createCoin(rs));
            }
            return coins;
        } catch(Exception e){
            throw new InvalidRequestException("Failed to retrieve list of users");
        }
    }
}
