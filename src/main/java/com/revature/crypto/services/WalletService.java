package com.revature.crypto.services;

import com.revature.crypto.Coinbase;
import com.revature.crypto.models.Wallet;

public class WalletService {
    Wallet sessionWallet;
    Coinbase coinbase;

    WalletService(Wallet sessionWallet, Coinbase coinbase){
        this.sessionWallet = sessionWallet;
        this.coinbase = coinbase;
    }

    //TODO: Implement me!
    public boolean buyCurrency(String tradingPair){
        return false;
    }

    public void printAllCryptos(){
        System.out.println(coinbase.getTradingPairs());
    }

}
