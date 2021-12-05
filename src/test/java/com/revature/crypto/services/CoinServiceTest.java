package com.revature.crypto.services;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.revature.crypto.daos.CoinDAO;
import com.revature.crypto.daos.CoinbaseDAO;
import com.revature.crypto.exceptions.InvalidRequestException;
import com.revature.crypto.models.Coin;

import com.revature.crypto.models.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class CoinServiceTest {

    private CoinbaseDAO mockCoinbaseDAO;
    private CoinDAO mockCoinDAO;
    private CoinService sut;

    public CoinServiceTest() throws IOException {
    }

    @Before
    public void testCaseSetup() throws IOException {
        mockCoinbaseDAO = mock(CoinbaseDAO.class);
        mockCoinDAO = mock(CoinDAO.class);

        //have to mock getAllCoins in CoinService Constructor
        List<Coin> mockCurrencyPairList = new ArrayList<>();
        mockCurrencyPairList.add(new Coin("BTC-USD", 0.0, null));
        mockCurrencyPairList.add(new Coin("ETH-USD", 0.0, null));
        mockCurrencyPairList.add(new Coin("USDT-USD", 0.0, null));

        when(mockCoinbaseDAO.getAllCoins()).thenReturn(mockCurrencyPairList);

        sut = new CoinService(mockCoinDAO, mockCoinbaseDAO);
    }

    @After
    public void testCaseCleanUp() {
        sut = null;
    }

    @Test
    public void test_isUserIdValid_returnsTrue_givenValidCoin() {

        // AAA pattern: Arrange, Act, Assert

        // Arrange
        Coin validCoin = new Coin("valid", 1,"valid");

        // Act
        boolean actualResult = sut.isUserIdValid(validCoin);

        // Assert
        Assert.assertTrue("Expected coin to be considered valid", actualResult);

    }

    @Test
    public void test_isUserIdValid_returnsFalse_givenInvalidCoin() {

        // Arrange
        Coin invalidCoin_1 = new Coin("valid", 1, null);
        Coin invalidCoin_2 = new Coin("valid", 1, "");
        Coin invalidCoin_3 = new Coin("valid", 1, "            ");

        // Act
        boolean actualResult_1 = sut.isUserIdValid(invalidCoin_1);
        boolean actualResult_2 = sut.isUserIdValid(invalidCoin_2);
        boolean actualResult_3 = sut.isUserIdValid(invalidCoin_3);

        // Assert
        Assert.assertFalse("Expected Coin to be considered Invalid.", actualResult_1);
        Assert.assertFalse("Expected Coin to be considered Invalid.", actualResult_2);
        Assert.assertFalse("Expected Coin to be considered Invalid.", actualResult_3);

    }

    @Test
    public void test_getCoins_returnsList_givenValidString() {

        //Arrange
        String validString = "valid";

        when(mockCoinDAO.getCoinsByUser(any())).thenReturn(new ArrayList<Coin>());

        //act
        List<Coin> actualResult = sut.getCoins(validString);

        //Assert
        verify(mockCoinDAO, times(1)).getCoinsByUser(any());
        Assert.assertNotNull("Expect List to be returned" ,actualResult);
    }

    @Test
    public void test_getCoins_returnsNull_givenInvalidString() {
        // Arrange
        String invalidString_1 = "";
        String invalidString_2 = "        ";
        String invalidString_3 = null;

        // Act
        List<Coin> actualResult_1 = sut.getCoins(invalidString_1);
        List<Coin> actualResult_2 = sut.getCoins(invalidString_2);
        List<Coin> actualResult_3 = sut.getCoins(invalidString_3);

        // Assert
        Assert.assertNull("Expected String to be considered Invalid and null returned.", actualResult_1);
        Assert.assertNull("Expected String to be considered Invalid and null returned.", actualResult_2);
        Assert.assertNull("Expected String to be considered Invalid and null returned.", actualResult_3);
    }

    @Test
    public void test_getTotalWalletValue_returnsList_givenValidListofCoins() {

        //Arrange
        Coin validCoin_1 = new Coin("valid", 1, "valid");
        Coin validCoin_2 = new Coin("valid", 2, "valid");
        Coin validCoin_3 = new Coin("valid", 3, "valid");

        ArrayList<Coin> validCoins = new ArrayList<>();
        validCoins.add(validCoin_1);
        validCoins.add(validCoin_2);
        validCoins.add(validCoin_3);

        when(mockCoinbaseDAO.valueOf(any())).thenReturn(1.0);

        //act
        double actualResult = sut.getTotalWalletValue(validCoins);

        //Assert
        verify(mockCoinbaseDAO, times(validCoins.size())).valueOf(validCoin_1.getCurrencyPair());

        Assert.assertEquals("All three cases should evaluate to ~ {1, 2, 3} 1+2+3 = 6, expected output should be 6", 6, actualResult, 0.0001);
    }

    @Test
    public void test_validateCoinPair_returnsTrue_givenValidCurrencyPair() {
        // Note: Currency Pair list is mocked, only accepts "BTC-USD", "ETH-USD", and "USDT-USD"

        //Arrange
        String validCurrencyPair_1 = "BTC-USD";
        String validCurrencyPair_2 = "ETH-USD";
        String validCurrencyPair_3 = "USDT-USD";

        //Act
        boolean actualResult_1 = sut.validateCoinPair(validCurrencyPair_1);
        boolean actualResult_2 = sut.validateCoinPair(validCurrencyPair_2);
        boolean actualResult_3 = sut.validateCoinPair(validCurrencyPair_3);

        //Assert
        Assert.assertTrue("Expected CurrencyPair to be considered Authentic", actualResult_1);
        Assert.assertTrue("Expected CurrencyPair to be considered Authentic", actualResult_2);
        Assert.assertTrue("Expected CurrencyPair to be considered Authentic", actualResult_3);
    }

    @Test
    public void test_validateCoinPair_returnsFalse_givenInvalidCurrencyPair() {
        // Note: Currency Pair list is mocked, only accepts "BTC-USD", "ETH-USD", and "USDT-USD"

        //Arrange
        String invalidCurrencyPair_1 = "ETC-USD";
        String invalidCurrencyPair_2 = "";
        String invalidCurrencyPair_3 = null;

        //Act
        boolean actualResult_1 = sut.validateCoinPair(invalidCurrencyPair_1);
        boolean actualResult_2 = sut.validateCoinPair(invalidCurrencyPair_2);
        boolean actualResult_3 = sut.validateCoinPair(invalidCurrencyPair_3);

        //Assert
        Assert.assertFalse("Expected CurrencyPair to be considered Authentic", actualResult_1);
        Assert.assertFalse("Expected CurrencyPair to be considered Authentic", actualResult_2);
        Assert.assertFalse("Expected CurrencyPair to be considered Authentic", actualResult_3);
    }

    @Test
    public void test_buyCoin_returnsTrue_givenValidOwnedCoinThatUserCanAfford(){
        // Arrange
        Coin validCoin = new Coin("USDT-USD", 1, "Valid");
        User validUser = new User("valid", "valid", "valid", "valid", "valid", 10000);

        when(mockCoinbaseDAO.valueOf(validCoin.getCurrencyPair())).thenReturn(1.0);
        when(mockCoinDAO.getCoinAmount(validCoin)).thenReturn(1.0);
        when(mockCoinDAO.update(validCoin)).thenReturn(true);
        when(mockCoinDAO.save(validCoin)).thenThrow(new InvalidRequestException("Coin should already exist in database, PK violation"));

        //Act
        boolean actualResult = sut.buyCoin(validCoin, validUser);

        //Assert
        Assert.assertTrue("Expected method to properly write coin to DB", actualResult);
        Assert.assertEquals("Expected Coin#amount to be changed", 2.0, validCoin.getAmount(), 0.0001);
        Assert.assertEquals("Expected User#usdBalance to be changed", 9999.0, validUser.getUsdBalance(), 0.0001);

        verify(mockCoinbaseDAO, times(1)).valueOf(validCoin.getCurrencyPair());
        verify(mockCoinDAO, times(1)).getCoinAmount(validCoin);
        verify(mockCoinDAO, times(1)).update(validCoin);
        verify(mockCoinDAO, times(0)).save(validCoin);
    }

    @Test
    public void test_buyCoin_returnsTrue_givenValidUnownedCoinThatUserCanAfford(){
        // Arrange
        Coin validCoin = new Coin("USDT-USD", 1, "Valid");
        User validUser = new User("valid", "valid", "valid", "valid", "valid", 10000);

        when(mockCoinbaseDAO.valueOf(validCoin.getCurrencyPair())).thenReturn(1.0);
        when(mockCoinDAO.getCoinAmount(validCoin)).thenReturn(-1.0);
        when(mockCoinDAO.update(validCoin)).thenThrow(new InvalidRequestException("Coin should not exist in database, update fails"));
        when(mockCoinDAO.save(validCoin)).thenReturn(true);

        //Act
        boolean actualResult = sut.buyCoin(validCoin, validUser);

        //Assert
        Assert.assertTrue("Expected method to properly write coin to DB", actualResult);
        Assert.assertEquals("Expected Coin#amount to remain the same", 1.0, validCoin.getAmount(), 0.0001);
        Assert.assertEquals("Expected User#usdBalance to be changed", 9999.0, validUser.getUsdBalance(), 0.0001);

        verify(mockCoinbaseDAO, times(1)).valueOf(validCoin.getCurrencyPair());
        verify(mockCoinDAO, times(1)).getCoinAmount(validCoin);
        verify(mockCoinDAO, times(0)).update(validCoin);
        verify(mockCoinDAO, times(1)).save(validCoin);
    }

    @Test (expected = InvalidRequestException.class)
    public void test_buyCoin_throwsInvalidRequestException_givenInvalidCoinCurrencyPair(){
        // Arrange
        Coin invalidCoin = new Coin(null, 1, "Valid");
        User validUser = new User("valid", "valid", "valid", "valid", "valid", 10000);

        when(mockCoinbaseDAO.valueOf(invalidCoin.getCurrencyPair())).thenReturn(0.0);
        when(mockCoinDAO.getCoinAmount(invalidCoin)).thenReturn(0.0);
        when(mockCoinDAO.update(invalidCoin)).thenReturn(false);
        when(mockCoinDAO.save(invalidCoin)).thenReturn(false);

        //Act
        try {
            sut.buyCoin(invalidCoin, validUser);
        } finally {

            //Assert
            Assert.assertEquals("Expected Coin#amount to remain the same", 1.0, invalidCoin.getAmount(), 0.0001);
            Assert.assertEquals("Expected User#usdBalance to remain the same", 10000.0, validUser.getUsdBalance(), 0.0001);

            verify(mockCoinbaseDAO, times(0)).valueOf(invalidCoin.getCurrencyPair());
            verify(mockCoinDAO, times(0)).getCoinAmount(invalidCoin);
            verify(mockCoinDAO, times(0)).update(invalidCoin);
            verify(mockCoinDAO, times(0)).save(invalidCoin);
        }
    }

    @Test (expected = InvalidRequestException.class)
    public void test_buyCoin_throwsInvalidRequestException_givenPurchaseCostsMoreThanUserCanAfford(){
        // Arrange
        Coin validCoin = new Coin("USDT-USD", 5, "Valid");
        User validUser = new User("valid", "valid", "valid", "valid", "valid", 1);

        when(mockCoinbaseDAO.valueOf(validCoin.getCurrencyPair())).thenReturn(1.0);
        when(mockCoinDAO.getCoinAmount(validCoin)).thenReturn(0.0);
        when(mockCoinDAO.update(validCoin)).thenReturn(false);
        when(mockCoinDAO.save(validCoin)).thenReturn(false);

        //Act
        try {
            sut.buyCoin(validCoin, validUser);
        } finally {

            //Assert
            verify(mockCoinbaseDAO, times(1)).valueOf(validCoin.getCurrencyPair());
            verify(mockCoinDAO, times(0)).getCoinAmount(validCoin);
            verify(mockCoinDAO, times(0)).update(validCoin);
            verify(mockCoinDAO, times(0)).save(validCoin);
        }
    }

    @Test
    public void test_sellCoin_returnsTrueAndRunsUpdateWhenCoinAmountIsNotSellAmount_givenValidOwnedCoin(){
        // Arrange
        Coin validCoin = new Coin("USDT-USD", 1, "Valid");
        User validUser = new User("valid", "valid", "valid", "valid", "valid", 10000);

        when(mockCoinDAO.hasCoin(validCoin)).thenReturn(new Coin("USDT-USD", 2, "Valid"));
        when(mockCoinbaseDAO.valueOf(validCoin.getCurrencyPair())).thenReturn(1.0);
        when(mockCoinDAO.update(validCoin)).thenReturn(true);
        when(mockCoinDAO.removeById(any())).thenReturn(false);

        //Act
        boolean actualResult = sut.sellCoin(validCoin, validUser);

        //Assert
        Assert.assertTrue("Expected method to properly write coin to DB", actualResult);
        Assert.assertEquals("Expected Coin#amount to be changed", 1.0, validCoin.getAmount(), 0.0001);
        Assert.assertEquals("Expected User#usdBalance to be changed", 10001.0, validUser.getUsdBalance(), 0.0001);

        verify(mockCoinDAO, times(1)).hasCoin(validCoin);
        verify(mockCoinbaseDAO, times(1)).valueOf(validCoin.getCurrencyPair());
        verify(mockCoinDAO, times(1)).update(validCoin);
        verify(mockCoinDAO, times(0)).removeById(any());
    }

    @Test
    public void test_sellCoin_returnsTrueAndRunsDeleteWhenCoinAmountIsSellAmount_givenValidOwnedCoin(){
        // Arrange
        Coin validCoin = new Coin("USDT-USD", 1, "Valid");
        User validUser = new User("valid", "valid", "valid", "valid", "valid", 10000);

        when(mockCoinDAO.hasCoin(validCoin)).thenReturn(new Coin("USDT-USD", 1, "Valid"));
        when(mockCoinbaseDAO.valueOf(validCoin.getCurrencyPair())).thenReturn(1.0);
        when(mockCoinDAO.update(validCoin)).thenReturn(false);
        when(mockCoinDAO.removeById(any())).thenReturn(true);

        //Act
        boolean actualResult = sut.sellCoin(validCoin, validUser);

        //Assert
        Assert.assertTrue("Expected method to properly write coin to DB", actualResult);
        Assert.assertEquals("Expected Coin#amount to be changed", 0.0, validCoin.getAmount(), 0.0001);
        Assert.assertEquals("Expected User#usdBalance to be changed", 10001.0, validUser.getUsdBalance(), 0.0001);

        verify(mockCoinDAO, times(1)).hasCoin(validCoin);
        verify(mockCoinbaseDAO, times(1)).valueOf(validCoin.getCurrencyPair());
        verify(mockCoinDAO, times(0)).update(validCoin);
        verify(mockCoinDAO, times(1)).removeById(any());
    }

    @Test (expected = InvalidRequestException.class)
    public void test_sellCoin_throwsInvalidRequestException_givenInvalidCoin(){
        // Arrange
        Coin invalidCoin = new Coin(null, 1, "Valid");
        User validUser = new User("valid", "valid", "valid", "valid", "valid", 10000);

        when(mockCoinDAO.hasCoin(invalidCoin)).thenReturn(null);
        when(mockCoinbaseDAO.valueOf(invalidCoin.getCurrencyPair())).thenReturn(0.0);
        when(mockCoinDAO.update(invalidCoin)).thenReturn(false);
        when(mockCoinDAO.removeById(any())).thenReturn(false);

        //Act
        try {
            sut.sellCoin(invalidCoin, validUser);
        } finally {

            //Assert
            Assert.assertEquals("Expected Coin#amount to remain the same", 1.0, invalidCoin.getAmount(), 0.0001);
            Assert.assertEquals("Expected User#usdBalance to remain the same", 10000.0, validUser.getUsdBalance(), 0.0001);

            verify(mockCoinDAO, times(0)).hasCoin(invalidCoin);
            verify(mockCoinbaseDAO, times(0)).valueOf(invalidCoin.getCurrencyPair());
            verify(mockCoinDAO, times(0)).update(invalidCoin);
            verify(mockCoinDAO, times(0)).removeById(any());
        }
    }

    @Test (expected = InvalidRequestException.class)
    public void test_sellCoin_throwsInvalidRequestException_givenUnownedCoin(){
        // Arrange
        Coin validCoin = new Coin("BTC-USD", 1, "Valid");
        User validUser = new User("valid", "valid", "valid", "valid", "valid", 10000);

        when(mockCoinDAO.hasCoin(validCoin)).thenReturn(null);
        when(mockCoinbaseDAO.valueOf(validCoin.getCurrencyPair())).thenReturn(60000.0);
        when(mockCoinDAO.update(validCoin)).thenReturn(false);
        when(mockCoinDAO.removeById(any())).thenReturn(false);

        //Act
        try {
            sut.sellCoin(validCoin, validUser);
        } finally {

            //Assert
            Assert.assertEquals("Expected Coin#amount to remain the same", 1.0, validCoin.getAmount(), 0.0001);
            Assert.assertEquals("Expected User#usdBalance to remain the same", 10000.0, validUser.getUsdBalance(), 0.0001);

            verify(mockCoinDAO, times(1)).hasCoin(validCoin);
            verify(mockCoinbaseDAO, times(1)).valueOf(validCoin.getCurrencyPair());
            verify(mockCoinDAO, times(0)).update(validCoin);
            verify(mockCoinDAO, times(0)).removeById(any());
        }
    }

    @Test (expected = InvalidRequestException.class)
    public void test_sellCoin_throwsInvalidRequestException_givenUserHasFewerCoinsThanAttemptingToSell(){
        // Arrange
        Coin validCoin = new Coin("USDT-USD", 5, "Valid");
        User validUser = new User("valid", "valid", "valid", "valid", "valid", 1);

        when(mockCoinDAO.hasCoin(validCoin)).thenReturn(new Coin("USDT-USD", 1, "Valid"));
        when(mockCoinbaseDAO.valueOf(validCoin.getCurrencyPair())).thenReturn(1.0);
        when(mockCoinDAO.update(validCoin)).thenReturn(false);
        when(mockCoinDAO.removeById(any())).thenReturn(false);

        //Act
        try {
            sut.sellCoin(validCoin, validUser);
        } finally {

            //Assert
            verify(mockCoinDAO, times(1)).hasCoin(validCoin);
            verify(mockCoinbaseDAO, times(1)).valueOf(validCoin.getCurrencyPair());
            verify(mockCoinDAO, times(0)).update(validCoin);
            verify(mockCoinDAO, times(0)).removeById(any());
        }
    }

}
