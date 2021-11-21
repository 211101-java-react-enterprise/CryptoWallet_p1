package com.revature.crypto;

public class Driver {
    public static void main(String[] args) {
        Coinbase coinbase = new Coinbase();
        String currencies = coinbase.getSupportedCurrencies();
        String exchangeRates = coinbase.getExchangeRates();
        String buyPrice = coinbase.getBuyPrice("ASM-USDT");//doesn't work. returns 404 not found
        String tradingPairs = coinbase.getTradingPairs();//doesn't work. returns 403 forbidden
        System.out.println(exchangeRates);


    }
}
