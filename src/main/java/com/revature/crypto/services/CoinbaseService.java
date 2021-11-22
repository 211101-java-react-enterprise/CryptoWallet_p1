package com.revature.crypto.services;

import com.revature.crypto.Coinbase;

import java.io.FileWriter;
import java.io.IOException;

public class CoinbaseService {
    Coinbase coinbase;

    public CoinbaseService (){
        coinbase = new Coinbase();
    }

    public void listExchangeRates(){
        String exchangeRates = coinbase.getExchangeRates();
        System.out.println(exchangeRates);
        String[] rateList = exchangeRates.split(",");
        //String[]
        for(int i = 0; i < rateList.length; i++){
            System.out.println(rateList[i]);
        }
        //writeToFile(exchangeRates);

    }

    public void writeToFile(String file){
        try {
            FileWriter myWriter = new FileWriter("exchange_rates.json");
            myWriter.write(file);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
