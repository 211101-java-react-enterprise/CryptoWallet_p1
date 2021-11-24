package com.revature.crypto;

import com.revature.crypto.daos.CoinbaseDAO;
import com.revature.crypto.models.Coin;

import java.util.ArrayList;
import java.util.List;

public class CoinbaseDAOTestDriver {
    public static void main(String[] args) {



        CoinbaseDAO coinbaseDAO = new CoinbaseDAO();

         //Coin[] coins = coinbaseDAO.getAllCoins2();
         List<Coin> coins = coinbaseDAO.getAllCoins();

         double failCount = 0;
         double successCount = 0;
         double coti = (coinbaseDAO.valueOf("COTI-USD"));
         double dollars = 100;
         double converted_amount = dollars/coti;
        System.out.println(converted_amount);

//         for(Coin coin : coins){
//             //if(coin.getId()== "BTC-EUR") System.out.println("BTC-USD");
//             //System.out.println(coin.getId());
//             if(coinbaseDAO.valueOf(coin.getId())!=-1){
//                 System.out.println("success: "+coin.getId());
//                 successCount+=1;
//             }
//             else {
//                 System.out.println("failed: "+coin.getId());
//                 failCount+=1;
//             }
//         }

        //System.out.println("Success rate: %"+(successCount/(successCount+failCount))*100);//64.2% success rate
        //double value = coinbaseDAO.valueOf(coins[0].getId());
         //double value = coinbaseDAO.valueOf(coins[0].getId());
//         for(int i = 0; i < coins.length; i++){
//             System.out.println(coins[i].getName());
//         }

//        String coin = "BTC";
//        int amount = 2;
//        double conversion = coinbaseDAO.getConversion(coin, amount);
//        System.out.println("Account Balance: $"+conversion);
//
//        double btc = coinbaseDAO.valueOf("BTC-USD");
//        System.out.println(btc);
//
//        //testing coinbaseDAO _C API Calls
//        /*System.out.println("Product Stats-----------------------");
//        System.out.println(coinbaseDAO.getProductStats_E());*/
//        System.out.println("Trading Pairs-----------------------");
//        //System.out.println(coinbaseDAO.getTradingPairs_E());
//
//        //testing coinbaseDAO _V2 API Calls
//        //System.out.println("Exchange Rates-----------------------");
//        //coinbaseDAO.getExchangeRates_V2();
//
//        System.out.println("Buy Price-----------------------");
//        System.out.println(coinbaseDAO.getBuyPrice_V2("BTC-USD"));
//        System.out.println("Supported Currencies-----------------------");
//        System.out.println(coinbaseDAO.getSupportedCurrencies_V2());



    }
}
