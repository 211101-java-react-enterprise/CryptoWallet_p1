package com.revature.crypto.services;

import com.revature.crypto.daos.CoinbaseDAO;

import java.io.FileWriter;
import java.io.IOException;

public class CoinbaseService {
    CoinbaseDAO coinbaseDAO;

    public CoinbaseService (){
        coinbaseDAO = new CoinbaseDAO();
    }

    public void listExchangeRates(){
        String exchangeRates = coinbaseDAO.getExchangeRates_V2();
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
