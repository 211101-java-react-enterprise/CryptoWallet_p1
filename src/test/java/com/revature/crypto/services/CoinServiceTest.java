package com.revature.crypto.services;

import com.revature.crypto.daos.CoinDAO;
import com.revature.crypto.daos.CoinbaseDAO;
import com.revature.crypto.daos.UserDAO;
import com.revature.crypto.models.Coin;
import com.revature.crypto.models.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
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
    public void test_getCoins_returnsList_givenValidString() throws SQLException {

        //Arrange
        String validString = "valid";
        when(mockCoinDAO.getCoinsByUser(new Coin())).thenReturn(new ArrayList<Coin>());

        //act
        List<Coin> actualResult = sut.getCoins(validString);

        //Assert
        verify(mockCoinDAO, times(1)).getCoinsByUser(new Coin());
        Assert.assertNotNull("Expect List to be returned" ,actualResult);
    }
}
