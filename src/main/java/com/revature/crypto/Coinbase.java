package com.revature.crypto;
import java.net.*;
import java.io.*;
import java.io.IOException;
    /*
        -methods call getData(String url) which use native stuff in java.net to return a big string formatted like a json.
         cloud.coinbase API uses OKHttpClient in java example code, but I didn't see this until after I already did it the hard way :-/
        -calls to developer.coinbase work with the exception of getBuyPrice() which returns 404, not found. Problem with url?
        -calls to cloud.coinbase are returning 403, forbidden. Haven't yet tested extensively (perhaps switch to OKHttpClient if unable to fix)
        -Note: code 200 means success
     */
public class Coinbase {

    private String getData(String urlText) {
        try {
            URL url = new URL(urlText);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            //headers
            //con.setRequestProperty("Accept", "application/json");
            //String contentType = con.getHeaderField("Accept");

            //timeout
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

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
    Note: For some reason, coinbase has two separate apis, with different methods. I think they both
    come from the same database but its confusing because there are repeats. For instance, both apis have a call that
    returns a json of all crypto-currencies.
    Although they seem to retrieve the same data, the URLS are different. I think it might have something to do with
    coinbase exchange vs coinbase pro (different services that are owned by the same parent company, coinbase global).

    Anyway, for now lets try to loosely keep track of which one we're pulling from just in case we have to debug
    inconsistencies down the road.
    -----------------------------------------------------------------------------------------------------
    https://developers.coinbase.com/api/v2#data-endpoints
     */
    public String getSupportedCurrencies(){
        return getData("https://api.coinbase.com/v2/currencies");
    }

    public String getExchangeRates(){
        return getData("https://api.coinbase.com/v2/exchange-rates");
    }

    public String getBuyPrice(String currency_pair){//ex) BTC-USD
        String url = "https://api.coinbase.com/v2/prices/"+currency_pair+"/buy";
        return getData(url);
    }

    /*
    --------------------------------------------------------------------------------------------------------------
      https://docs.cloud.coinbase.com/exchange/reference/
      403 forbidden
     */
    /*public String getTradingPairs(){
        return getData("https://api.exchange.coinbase.com/products");
    }

    public String getProductStats() { return getData("https://api.exchange.coinbase.com/products/BTC-USD/stats");}

    */

}
