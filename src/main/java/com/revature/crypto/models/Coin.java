package com.revature.crypto.models;
/*
jackson allows for easy parsing if there is a model with the same exact variables that are in the json.
This model is meant to represent the getSupportedCurrencies method
 */
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Coin {//extends Object {
    private String user_Id;//pk/fk
    private String currencyPair;//pk (composite)
    private double amount;

    //constructors
    Coin(String currencyPair, double amount){//called when user updates existing coin
        this.currencyPair = currencyPair;
        this.amount = amount;
    }
    Coin(String currencyPair, double amount, String user_Id){//called when user gets a new coin
        this.currencyPair = currencyPair;
        this.amount = amount;
        this.user_Id = user_Id;
    }

    //getters and setters:
    public String getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(String user_Id) {
        this.user_Id = user_Id;
    }

    public String getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    //override methods
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
