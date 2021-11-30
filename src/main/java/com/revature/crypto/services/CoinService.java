package com.revature.crypto.services;

import com.revature.crypto.models.Coin;

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
}
