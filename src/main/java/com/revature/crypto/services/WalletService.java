package com.revature.crypto.services;

import com.revature.crypto.daos.CoinbaseDAO;
import com.revature.crypto.models.Wallet;

public class WalletService {
    Wallet sessionWallet;
    CoinbaseDAO coinbaseDAO;

    WalletService(Wallet sessionWallet, CoinbaseDAO coinbaseDAO){
        this.sessionWallet = sessionWallet;
        this.coinbaseDAO = coinbaseDAO;
    }

    //TODO: Implement me!
    public boolean buyCurrency(String tradingPair){
        return false;
    }

    public void printAllCryptos(){
        //System.out.println(coinbase.getTradingPairs());
    }

}
