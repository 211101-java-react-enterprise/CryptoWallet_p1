package com.revature.crypto.models;
/*
jackson allows for easy parsing if there is a model with the same exact variables that are in the json.
This model is meant to represent the getSupportedCurrencies method
 */
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Coin {//extends Object {
    private String id;

    public Coin(){
        super();
    }
    public Coin(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
