package com.revature.crypto.daos;

import com.revature.CryptoORM_P1.exception.InvalidClassException;
import com.revature.CryptoORM_P1.exception.MethodInvocationException;
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
    public Coin findById(Coin sessionCoin) throws InvalidClassException, MethodInvocationException, SQLException {
        ResultSet rs = mapper.select(sessionCoin);
        return createCoin(rs);
    }

    @Override
    public boolean save(Coin newCoin) throws InvalidClassException, MethodInvocationException, SQLException {
        int status = mapper.insert(newCoin);
        if(status!=-1) return true;
        else return false;
    }

    @Override
    public List<Coin> findAll() throws InvalidClassException, MethodInvocationException, SQLException {
        return createCoinList(mapper.select(new Coin()));
    }

    @Override
    public boolean update(Coin updatedObj) throws InvalidClassException, MethodInvocationException, SQLException {
        int status = mapper.update(updatedObj, "user_uuid", "currency_pair");
        if(status!=-1) return true;
        else return false;
    }

    public List<Coin> getCoinsByUser(Coin coin) throws InvalidClassException, MethodInvocationException, SQLException{
        return createCoinList(mapper.select(coin, "user_uuid"));
    }


    @Override
    public boolean removeById(Coin removedCoin) throws InvalidClassException, MethodInvocationException, SQLException {
        int status = mapper.delete(removedCoin, "user_uuid", "currency_pair");
        if(status!=-1) return true;
        else return false;
    }

    private Coin createCoin (ResultSet rs) throws SQLException {
        return new Coin(rs.getString("currency_pair"),
                rs.getDouble("amount"),
                rs.getString("user_uuid"));

    }

    public double getCoinAmount(Coin coin) throws SQLException {
        ResultSet rs = mapper.select(coin, "user_uuid", "currency_pair");
        if(rs.next()) return rs.getDouble("amount");
        return -1;
    }

    /**
     * does the user have a given coin
     * return null if no
     */
    public Coin hasCoin(Coin coin) throws InvalidClassException, MethodInvocationException, SQLException{
        ResultSet rs = mapper.select(coin, "user_uuid", "currency_pair");
        if(!rs.next()) return null;
        return createCoin(rs);
    }

    private List<Coin> createCoinList(ResultSet rs) throws InvalidClassException, MethodInvocationException, SQLException {
        List<Coin> coins = new ArrayList<>();
        while(rs.next()){
            coins.add(createCoin(rs));
        }
        return coins;
    }
}
