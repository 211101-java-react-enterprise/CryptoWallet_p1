package com.revature.crypto.services;

import com.revature.crypto.daos.CoinDAO;
import com.revature.crypto.daos.CoinbaseDAO;
import com.revature.crypto.exceptions.InvalidRequestException;
import com.revature.crypto.models.Coin;
import com.revature.crypto.models.User;

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

    public CoinService(CoinDAO coinDAO, CoinbaseDAO coinbaseDAO) {
        this.coinDAO = coinDAO;
        this.coinbaseDAO = coinbaseDAO;

        currencyPairs = coinbaseDAO.getAllCoins();
    }

    /**
     *      CoinAmountService#isCoinAmountValid is used to check
     *      validity of user entered data when attempting to
     *      save data to database.
     *
     *      Parameters: CoinAmount newCoinAmount - To be checked
     *
     *      returns: true if CoinAmount is larger than 0
     *               false if coin amount is less than or equal to 0
     */
    public boolean isCoinAmountValid(Coin newCoin) {
        return newCoin.getAmount() > 0;
    }

    public boolean isUserIdValid(Coin coin){
        return coin.getUser_Id() != null && !coin.getUser_Id().trim().equals("");
    }
    public List<Coin> getCoins(String user_uuid){
        Coin coin = new Coin();
        coin.setUser_Id(user_uuid);
        if(isUserIdValid(coin)){
            return coinDAO.getCoinsByUser(coin);
        }
        return null;
    }

    public double getTotalWalletValue(List<Coin> coins){
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

    public boolean buyCoin(Coin coin, User user) {
        if (!validateCoinPair(coin.getCurrencyPair())) {
            throw new InvalidRequestException("Invalid Currency pair given!");
        }

        // check if user has enough cash to buy
        //coinbaseDAO.getProductTicker_E("USD-BTC");
        //double valueOf = coinbaseDAO.valueOf(coin.getCurrencyPair());
        //System.out.println("\n\nVALUE:\n"+valueOf+"\n");
        double purchaseAmount = coin.getAmount() * coinbaseDAO.valueOf(coin.getCurrencyPair());

        if (user.getAmount_invested() > purchaseAmount) {
            user.setAmount_invested(user.getAmount_invested() - purchaseAmount);
            // check if user already has some of coin
            try {
                double coinAmount = coinDAO.getCoinAmount(coin);
                if (coinAmount != -1) {
                    // if so update current value
                    coin.setAmount(coin.getAmount() + coinAmount);
                    if(coinDAO.update(coin)) return true;
                } else {
                    // if not create new entry in coin table
                    if (coinDAO.save(coin)) return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    public boolean sellCoin(Coin coin) {

        return false;
    }
}
