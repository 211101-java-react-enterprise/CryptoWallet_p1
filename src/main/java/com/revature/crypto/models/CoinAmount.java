package com.revature.crypto.models;

/**
 *      CoinAmount is a data model class that is used to store information,
 *      it stores how much of a particular coin a certain wallet has.
 *
 *      holds:
 *          coinAmountUUID - ID of this coinAmount object in database
 *          walletUUID - ID of associated wallet, primarily used by database
 *          currencyPair - 3 digit string used for determining which currency this is for
 *          amount - As a double stores the current amount of this coin a wallet holds
 *
 *      Methods are simple getters and setters
 */
public class CoinAmount {

    //000000000000000000000000000000000000000000000000000000000000000000000000000000000

    private String coinAmountUUID;
    private String walletUUID;
    private String currencyPair;

    private double amount;

    //000000000000000000000000000000000000000000000000000000000000000000000000000000000

    //CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC

    public CoinAmount(String walletUUID, String currencyPair, double amount) {
        this.walletUUID = walletUUID;
        this.currencyPair = currencyPair;
        this.amount = amount;
    }

    //CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC

    //----------------------------------------------------------------------------------

    public String getCoinAmountUUID() {
        return coinAmountUUID;
    }
    public void setCoinAmountUUID(String coinAmountUUID) {
        this.coinAmountUUID = coinAmountUUID;
    }

    //----------------------------------------------------------------------------------

    public String getWalletUUID() {
        return walletUUID;
    }
    public void setWalletUUID(String walletUUID) {
        this.walletUUID = walletUUID;
    }

    //----------------------------------------------------------------------------------

    public String getCurrencyPair() {
        return currencyPair;
    }
    public void setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
    }

    //----------------------------------------------------------------------------------

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    //----------------------------------------------------------------------------------

}
