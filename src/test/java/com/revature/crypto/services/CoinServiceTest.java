package com.revature.crypto.services;

import com.revature.crypto.daos.CoinDAO;
import com.revature.crypto.daos.UserDAO;
import com.revature.crypto.exceptions.AuthenticationException;
import com.revature.crypto.exceptions.InvalidRequestException;
import com.revature.crypto.models.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class CoinServiceTest {

    CoinService sut;
    CoinDAO mockCoinDAO;

    /*
        JUnit Annotations
            - @Before (runs before each test case)
            - @After (runs after each test case)
            - @BeforeClass (runs once before all test cases)
            - @AfterClass (runs once after all test cases)
            - @Test (marks a method in a test suite as a test case)
            - @Ignore (indicates that the annotated test case should be skipped)
     */

    @Before
    public void testCaseSetup() throws IOException {
        mockCoinDAO = mock(CoinDAO.class);
        //sut = new CoinService(mockCoinDAO);
    }


}
