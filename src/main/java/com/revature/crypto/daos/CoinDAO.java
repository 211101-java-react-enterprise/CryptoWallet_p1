package com.revature.crypto.daos;

import com.revature.CryptoORM_P1.exception.InvalidClassException;
import com.revature.CryptoORM_P1.exception.MethodInvocationException;
import com.revature.CryptoORM_P1.mapper.SQLMapper;
import com.revature.crypto.exceptions.ConnectionDatabaseException;
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
    public Coin findById(Coin sessionCoin)  {
        ResultSet rs = null;
        try {
            rs = mapper.select(sessionCoin);
            return createCoin(rs);
        } catch (SQLException e) {
            throw new ConnectionDatabaseException();
        }
    }

    @Override
    public boolean save(Coin newCoin)  {
        int status = 0;
        try {
            status = mapper.insert(newCoin);
            if(status!=-1) return true;
            else return false;
        } catch (SQLException e) {
            throw new ConnectionDatabaseException();
        }
    }

    @Override
    public List<Coin> findAll()  {
        try {
            return createCoinList(mapper.select(new Coin()));
        } catch (SQLException e) {
            throw new ConnectionDatabaseException();
        }
    }

    @Override
    public boolean update(Coin updatedObj)  {
        int status = 0;
        try {
            status = mapper.update(updatedObj, "user_uuid", "currency_pair");
            if(status!=-1) return true;
            else return false;
        } catch (SQLException e) {
            throw new ConnectionDatabaseException();
        }
    }

    public List<Coin> getCoinsByUser(Coin coin) {
        try {
            return createCoinList(mapper.select(coin, "user_uuid"));
        } catch (SQLException e) {
            throw new ConnectionDatabaseException();
        }
    }


    @Override
    public boolean removeById(Coin removedCoin) {
        int status = 0;
        try {
            status = mapper.delete(removedCoin, "user_uuid", "currency_pair");
            if(status!=-1) return true;
            else return false;
        } catch (SQLException e) {
            throw new ConnectionDatabaseException();
        }
    }

    private Coin createCoin (ResultSet rs) {
        try {
            return new Coin(rs.getString("currency_pair"),
                    rs.getDouble("amount"),
                    rs.getString("user_uuid"));
        } catch (SQLException e) {
            throw new ConnectionDatabaseException();
        }
    }

    public double getCoinAmount(Coin coin) {
        ResultSet rs = null;
        try {
            rs = mapper.select(coin, "user_uuid", "currency_pair");
            if(rs.next()) return rs.getDouble("amount");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * does the user have a given coin
     * return null if no
     */
    public Coin hasCoin(Coin coin) {
        ResultSet rs = null;
        try {
            rs = mapper.select(coin, "user_uuid", "currency_pair");
            if(!rs.next()) return null;
            return createCoin(rs);
        } catch (SQLException e) {
            throw new ConnectionDatabaseException();
        }
    }

    private List<Coin> createCoinList(ResultSet rs) {
        List<Coin> coins = new ArrayList<>();
        try{
            while(rs.next()){
                coins.add(createCoin(rs));
            }
            return coins;
        } catch(SQLException e){
            throw new ConnectionDatabaseException();
        }
    }
}
