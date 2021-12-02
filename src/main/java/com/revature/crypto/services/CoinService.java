package com.revature.crypto.services;

import com.revature.crypto.daos.CoinDAO;
import com.revature.crypto.models.Coin;

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

    public CoinService(CoinDAO coinDAO) {
        this.coinDAO = coinDAO;
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
}
