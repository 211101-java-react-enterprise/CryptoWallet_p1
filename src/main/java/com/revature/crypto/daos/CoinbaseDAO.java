package com.revature.crypto.daos;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.revature.crypto.exceptions.InvalidRequestException;
import com.revature.crypto.exceptions.JsonParsingException;
import com.revature.crypto.models.Coin;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
    -methods pull information from one of two coinbase apis (links below) and return a string formatted like a json.
    -quarries are made with HttpURLConnection from java.net via getData(String url) method.
    -code 200 means success
 */
public class CoinbaseDAO {
    ObjectMapper mapper;

    public CoinbaseDAO() {
        mapper = new ObjectMapper();
    }

    //returns a USD value given a supported coin
    public double valueOf(String coin) throws IOException {
        try {
            String json = getProductTicker_E(coin);
            //System.out.println(json);
            JsonNode jsonNode = mapper.readTree(json);
            double value = Double.parseDouble(jsonNode.get("price").textValue());
            return value;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("CoinbaseDAO#valueOf failed to Parse JSON");
            //TODO log exception
        }
    }

    //returns a list of all supported coins
    public List<Coin> getAllCoins() throws IOException, JsonParsingException {
        try {
            List<Coin> pairs = new ArrayList<>();
            String json = getTradingPairs_E();
            //Coin[] coins = mapper.readValue(json, Coin[].class);
            TypeFactory typeFactory = mapper.getTypeFactory();
            List<Coin> coins = mapper.
                    readValue(json, typeFactory.
                            constructCollectionType(List.class, Coin.class));
            coins = coins.stream().filter(c -> c.getCurrencyPair().endsWith("USD")).collect(Collectors.toList());
//            for(Coin coin: coins){
//                String cut_id = coin.getId().substring(coin.getId().length()-3);//checks if currency paired with USD
//                if(!cut_id.equals("USD")){
//                    System.out.println("removing coin: "+coin.getId());
//                    coins.remove(coin);
//                }
//            }

            return coins;
        } catch (JsonParseException  | JsonMappingException e) {
            e.printStackTrace();
            throw new JsonParsingException("CoinbaseDAO#getAllCoins failed to properly parse JSON");
            //TODO log exception
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("CoinbaseDAO#getAllCoins failed to load JSON");
            //TODO log exception
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
    public String getBuyPrice_V2(String currency_pair) throws IOException, InvalidRequestException {//ex) BTC-USD
        String url = "https://api.coinbase.com/v2/prices/" + currency_pair + "/buy";
        return getData(url);
    }

    //not implemented
    public String getSupportedCurrencies_V2() throws IOException, InvalidRequestException {
        return getData("https://api.coinbase.com/v2/currencies");
    }

    //not implemented
    public String getExchangeRates_V2() throws IOException, InvalidRequestException {
        String data = getData("https://api.coinbase.com/v2/exchange-rates");
        return data;
    }

    /*
    --------------------------------------------------------------------------------------------------------------
      https://docs.cloud.coinbase.com/exchange/reference/
      All methods coming from this API are tagged with _E (exchange, as shown in url)
     */
    public String getTradingPairs_E() throws IOException, InvalidRequestException {
        return getData("https://api.exchange.coinbase.com/products");
    }

    //not implemented
    public String getProductStats_E() throws IOException, InvalidRequestException {
        return getData("https://api.exchange.coinbase.com/products/BTC-USD/stats");
    }

    public String getProductTicker_E(String currencyPair) throws IOException{
        return getData("https://api.exchange.coinbase.com/products/"+currencyPair+"/ticker");
    }


    /*---------------------------------------------------------------------------------------------------------------
        This method uses HttpURLConnecton from java.net to fetch data from the apis.
        -takes in a url from the API and returns a raw json string
     */
    private String getData(String urlText) throws IOException, InvalidRequestException {
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
            //System.out.println(status);

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
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new InvalidRequestException("CoinBaseDAO#getData was likely given a malformedURL");
            //TODO log error.
        } catch (ProtocolException e) {
            e.printStackTrace();
            throw new InvalidRequestException("CoinBaseDAO#getData was likely given a malformedURL");
        }catch (IOException e) {
            e.printStackTrace();
            throw new IOException("CoinBaseDAO#getData failed to read JSON");
        }
    }
}