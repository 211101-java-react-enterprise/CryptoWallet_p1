package com.revature.crypto;

import com.revature.crypto.daos.CoinbaseDAO;

public class CoinbaseDAOTestDriver {
    public static void main(String[] args) {
        CoinbaseDAO coinbaseDAO = new CoinbaseDAO();
        //testing coinbaseDAO _C API Calls
        /*System.out.println("Product Stats-----------------------");
        System.out.println(coinbaseDAO.getProductStats_E());
        System.out.println("Trading Pairs-----------------------");
        System.out.println(coinbaseDAO.getTradingPairs_E());
        */
        //testing coinbaseDAO _V2 API Calls
        System.out.println("Exchange Rates-----------------------");
        String[] elements = coinbaseDAO.getExchangeRates_V2();
        for(int i = 0; i < elements.length; i++){
            System.out.println(elements[i]);
        }
        /*
        System.out.println("Buy Price-----------------------");
        System.out.println(coinbaseDAO.getBuyPrice_V2("BTC-USD"));
        System.out.println("Supported Currencies-----------------------");
        System.out.println(coinbaseDAO.getSupportedCurrencies_V2());
        */

    }
}
