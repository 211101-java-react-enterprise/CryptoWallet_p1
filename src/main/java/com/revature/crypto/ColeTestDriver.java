package com.revature.crypto;

import com.revature.crypto.services.CoinbaseService;

public class ColeTestDriver {
    public static void main(String[] args) {
        Coinbase coinbase = new Coinbase();
        //String currencies = coinbase.getSupportedCurrencies();
        //String exchangeRates = coinbase.getExchangeRates();
        //System.out.println(coinbase.getSupportedCurrencies());
        //System.out.println(coinbase.getBuyPrice("BTC-USD"));//works!
        //System.out.println(exchangeRates);
        //String tradingPairs = coinbase.getTradingPairs();//doesn't work. returns 403 forbidden
        //System.out.println(exchangeRates);
        //System.out.println(coinbase.getProductStats());

        CoinbaseService cbService = new CoinbaseService();
        cbService.listExchangeRates();

    }
}
