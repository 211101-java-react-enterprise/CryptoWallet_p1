package com.revature.crypto.models;

import java.util.ArrayList;
import java.util.List;

public class Wallet {
    private ArrayList<String> currencies;
    private double USDValue;


    public Wallet(ArrayList<String> currencies, double USDValue){
        this.currencies = currencies;
        this.USDValue = USDValue;
    }

    public ArrayList<String> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(ArrayList<String> currencies) {
        this.currencies = currencies;
    }

    public double getUSDValue() {
        return USDValue;
    }

    public void setUSDValue(double USDValue) {
        this.USDValue = USDValue;
    }

    public void printCurrencies(){
        for(String c : currencies){
            System.out.println(c);
        }
    }
}
