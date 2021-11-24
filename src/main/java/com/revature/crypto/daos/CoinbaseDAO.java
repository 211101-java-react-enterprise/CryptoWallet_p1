package com.revature.crypto.daos;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.crypto.models.Coin;

import java.net.*;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/*
    -methods pull information from one of two coinbase apis (links below) and return a string formatted like a json.
    -quarries are made with HttpURLConnection from java.net via getData(String url) method.
    -code 200 means success
 */
public class CoinbaseDAO {
    ObjectMapper mapper;

    public CoinbaseDAO(){
        mapper = new ObjectMapper();
    }

    private String getData(String urlText) {
        try {
            //create url and set up connection
            URL url = new URL(urlText);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            //timeout
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            //name the request
            con.addRequestProperty("User-Agent", "CryptoWalletApp");

            //reading the response
            int status = con.getResponseCode();
            System.out.println(status);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
            return content.toString();
        } catch (Exception e){
            System.out.println("problem retrieving data");
            //TODO log error. Find more specific exception types
            // Is it better to put try catch blocks in the other get() methods within this class instead,
            // to allow for more specific messaging?
            return null;
        }
    }

    public double valueOf(String coin){
        try {
            String json = getBuyPrice_V2(coin);
            System.out.println(json);
            JsonNode jsonNode = mapper.readTree(json);
            double value = Double.parseDouble(jsonNode.get("data").get("amount").textValue());
            return value;
        }catch(Exception e){
            System.out.println("Trouble parsing the data");
            return -1;
            //TODO handle and log exception
        }
    }

    public Coin[] getAllCoins(){
        try{
            List<Coin> pairs = new ArrayList<>();
            String json = getSupportedCurrencies_V2();
            String cutJson = json.substring(8, json.length()-1);
            //JsonNode node = mapper.g
            //List<String> list = mapper.readValue(cutJson, new TypeReference<ArrayList<String>>() { });
            Coin[] coins = mapper.readValue(cutJson, Coin[].class);

            //pairs = Arrays.asList(mapper.readValue(cutJson, Coin[].class));
            return coins;
        } catch(Exception e){
            System.out.println("Trouble parsing the data");
            return null;
            //TODO handle and log exception
        }

    }

    //does same thing as valueOf method, but is uses getExchange rates... making it less space efficient and unnecessarily complicated.
    //acts as a good example of how to use ObjectMapper.get("value") to find specific element in json
    public double getConversion(String coin, double amount){
        try {
            String json = getExchangeRates_V2();
            String cutJson = json.substring(34, json.length()-2);//natively, the json file is nested. This cuts out the fluff
            JsonNode jsonNode = mapper.readTree(cutJson);
            double value = Double.parseDouble(jsonNode.get(coin).textValue());
            System.out.println(coin+": "+value+" per dollar");
            return (1/value)*amount;
        }catch(Exception e){
            System.out.println("Trouble parsing the data");
            return -1;
            //TODO handle and log exception
        }
    }

    private boolean parse(String data){
        String cutData = data.substring(35, data.length()-3);//natively, the json file is nested. This cuts out the fluff
        System.out.println(cutData);


        return false;
    }

    //to be removed. For reference about json node and
    private boolean parseOLD(String data){
        try {
            String newData = data.substring(34, data.length()-2);//natively, the json file is nested. This cuts out the fluff
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(newData);
            JsonNode jsonNode1 = mapper.readTree(data);
            boolean isArray = jsonNode1.get("data").get("rates").isArray();
            System.out.println("isArray: "+isArray);
            String json = jsonNode.get("MANA").textValue();//get("data").get("rates").get("AED").textValue();
            System.out.println(json);
            //mapper.writeValue(new File("mapperValue.txt"), json);
            //"[{\"currency\":\"USD\", \"rates\":38}, {\"name\":\"laplap\", \"age\":5}]";
            List<String> list = mapper.readValue(json, new TypeReference<List<String>>() {});
            return false;
        }catch(Exception e){
            System.out.println("Trouble parsing the data");
            return false;
            //TODO handle and log exception
        }
    }

    /*
    Note: Coinbase has two separate apis that this application uses. They seem to come from the same database and even have
    similar or repeating methods. For instance, both apis have a call that returns a json of all crypto-currencies.
    Although they seem to retrieve the same data, the URLS are different. I think it might have something to do with
    coinbase exchange vs coinbase pro (different services that are owned by the same parent company, coinbase global).

    Anyway, for now lets try to loosely keep track of which one we're pulling from just in case we have to debug
    inconsistencies down the road.
    -----------------------------------------------------------------------------------------------------
    https://developers.coinbase.com/api/v2#data-endpoints
    all methods from this API are tagged with _V2 (version 2, as shown in URL)
     */
    public String getSupportedCurrencies_V2(){
        return getData("https://api.coinbase.com/v2/currencies");
    }

    public String getExchangeRates_V2(){
        String data = getData("https://api.coinbase.com/v2/exchange-rates");
        return data;
    }


    public String getBuyPrice_V2(String currency_pair){//ex) BTC-USD
        String url = "https://api.coinbase.com/v2/prices/"+currency_pair+"/buy";
        return getData(url);
    }

    /*
    --------------------------------------------------------------------------------------------------------------
      https://docs.cloud.coinbase.com/exchange/reference/
      All methods coming from this API are tagged with _E (exchange, as shown in url)
     */
    public String getTradingPairs_E(){
        return getData("https://api.exchange.coinbase.com/products");
    }

    public String getProductStats_E() {
        return getData("https://api.exchange.coinbase.com/products/BTC-USD/stats");
    }

}
