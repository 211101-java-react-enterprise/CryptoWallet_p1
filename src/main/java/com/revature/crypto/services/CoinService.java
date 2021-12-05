package com.revature.crypto.services;

import com.revature.CryptoORM_P1.exception.InvalidClassException;
import com.revature.CryptoORM_P1.exception.MethodInvocationException;
import com.revature.crypto.daos.CoinDAO;
import com.revature.crypto.daos.CoinbaseDAO;
import com.revature.crypto.exceptions.InvalidRequestException;
import com.revature.crypto.exceptions.JsonParsingException;
import com.revature.crypto.models.Coin;
import com.revature.crypto.models.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 *      CoinAmountService Class holds verification logic for user
 *
 *      WalletUUID and CoinAccountUUID are not user entered and
 *      should be safe.
 *
 *      Currency Pair needs to be checked against what pairs are
 *      valid by coinbase API
 */
public class CoinService {

    private CoinDAO coinDAO;
    private CoinbaseDAO coinbaseDAO;

    List<Coin> currencyPairs;

    public CoinService(CoinDAO coinDAO, CoinbaseDAO coinbaseDAO)  {
        this.coinDAO = coinDAO;
        this.coinbaseDAO = coinbaseDAO;

        currencyPairs = coinbaseDAO.getAllCoins();
    }

    public boolean isUserIdValid(Coin coin){
        return coin.getUser_Id() != null && !coin.getUser_Id().trim().equals("");
    }
    public List<Coin> getCoins(String user_uuid) throws InvalidClassException, MethodInvocationException, SQLException {
        Coin coin = new Coin();
        coin.setUser_Id(user_uuid);
        if(isUserIdValid(coin)){
            return coinDAO.getCoinsByUser(coin);
        }
        return null;
    }

    public double getTotalWalletValue(List<Coin> coins) throws IOException {
        double result = 0;

        for (Coin coin : coins) {
            result += coinbaseDAO.valueOf(coin.getCurrencyPair()) * coin.getAmount();
        }
        return result;
    }

    public boolean validateCoinPair(String pair) {
        for (Coin coin : currencyPairs) {
            if (coin.getCurrencyPair().equals(pair)) return true;
        }
        return false;
    }

    public boolean buyCoin(Coin coin, User user) throws InvalidClassException, MethodInvocationException, SQLException, IOException, InvalidRequestException {
        if (!validateCoinPair(coin.getCurrencyPair())) {
            throw new InvalidRequestException("Invalid Currency pair given!");
        }

        double purchaseAmount = coin.getAmount() * coinbaseDAO.valueOf(coin.getCurrencyPair());

        if (user.getUsdBalance() > purchaseAmount) {
            user.setUsdBalance(user.getUsdBalance() - purchaseAmount);
            // check if user already has some of coin

            double coinAmount = coinDAO.getCoinAmount(coin);
            if (coinAmount != -1) {
                // if so update current value
                coin.setAmount(coin.getAmount() + coinAmount);
                return coinDAO.update(coin);
            } else {
                // if not create new entry in coin table
                return coinDAO.save(coin);
            }
        } else {throw new InvalidRequestException("Coin costs more than user can buy!");}
    }

    /**
     * takes in the coin the user wants to sell and the amount they want to sell
     */
    public boolean sellCoin(Coin coin, User user) throws InvalidClassException, MethodInvocationException, SQLException, IOException, InvalidRequestException {
        //check if user has coin they want to sell
        Coin ownedCoin = coinDAO.hasCoin(coin);

        double coinAmount = coin.getAmount() * coinbaseDAO.valueOf(coin.getCurrencyPair());

        if(ownedCoin != null) {//user must have the coin
            if(ownedCoin.getAmount() >= coin.getAmount()){//user must have enough of the coin
                user.setUsdBalance(user.getUsdBalance() + coinAmount);
                        // if so update current value
                        coin.setAmount(ownedCoin.getAmount() - coin.getAmount());
                        if(coin.getAmount() ==0) coinDAO.removeById(ownedCoin);
                        else coinDAO.update(coin);
                        return true;

            } else throw new InvalidRequestException("Not enough of the coin in your wallet");
        } else throw new InvalidRequestException("You don't own that coin");
        //check if amount is valid
    }
}
