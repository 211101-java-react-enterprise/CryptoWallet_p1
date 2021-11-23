package com.revature.crypto.daos;
import java.net.*;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;

/*
    -methods pull information from one of two coinbase apis (links below) and return a string formatted like a json.
    -quarries are made with HttpURLConnection from java.net via getData(String url) method.
    -code 200 means success
 */
public class CoinbaseDAO {

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

    public String[] getExchangeRates_V2(){
        String data = getData("https://api.coinbase.com/v2/exchange-rates");
        System.out.println(data);
        String[] elements = data.split(",");
        return elements;
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

    //for debugging and visualizing data. Will eventually be removed
    private void writeToFile(String file){
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
