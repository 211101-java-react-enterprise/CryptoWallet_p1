package com.revature.crypto.services;

import com.revature.crypto.daos.UserDAO;
import com.revature.crypto.exceptions.AuthenticationException;
import com.revature.crypto.exceptions.InvalidRequestException;
import com.revature.crypto.models.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class UserServiceTest {

    // System Under Test
//    UserService sut = new UserService();
    UserService sut;
    UserDAO mockUserDAO;

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
    public void testCaseSetup() {
        mockUserDAO = mock(UserDAO.class);
        sut = new UserService(mockUserDAO);
    }

    @After
    public void testCaseCleanUp() {
        sut = null;
    }

    @Test
    public void test_isUserValid_returnsTrue_givenValidUser() {

        // Arrange
        User validUser = new User("valid", "valid", "valid", "valid", "valid", 1);

        // Act
        boolean actualResult = sut.isUserValid(validUser);

        // Assert
        Assert.assertTrue("Expected user to be considered valid", actualResult);

    }

    @Test
    public void test_isUserValid_returnsFalse_givenUserWithInvalidFirstName() {

        // Arrange
        User invalidUser_1 = new User("valid", "valid", "", "valid");
        User invalidUser_2 = new User("valid", "valid", null, "valid");
        User invalidUser_3 = new User("valid", "valid", "        ", "valid");

        // Act
        boolean actualResult_1 = sut.isUserValid(invalidUser_1);
        boolean actualResult_2 = sut.isUserValid(invalidUser_2);
        boolean actualResult_3 = sut.isUserValid(invalidUser_3);

        // Assert
        Assert.assertFalse("Expected user to be considered false.", actualResult_1);
        Assert.assertFalse("Expected user to be considered false.", actualResult_2);
        Assert.assertFalse("Expected user to be considered false.", actualResult_3);

    }
    @Test
    public void test_isUserValid_returnsFalse_givenUserWithInvalidLastName() {

        // Arrange
        User invalidUser_1 = new User("valid", "valid", "valid", "");
        User invalidUser_2 = new User("valid", "valid", "valid", null);
        User invalidUser_3 = new User("valid", "valid", "valid", "         ");

        // Act
        boolean actualResult_1 = sut.isUserValid(invalidUser_1);
        boolean actualResult_2 = sut.isUserValid(invalidUser_2);
        boolean actualResult_3 = sut.isUserValid(invalidUser_3);

        // Assert
        Assert.assertFalse("Expected user to be considered false.", actualResult_1);
        Assert.assertFalse("Expected user to be considered false.", actualResult_2);
        Assert.assertFalse("Expected user to be considered false.", actualResult_3);

    }
    @Test
    public void test_isUserValid_returnsFalse_givenUserWithInvalidUsername() {

        // Arrange
        User invalidUser_1 = new User("", "valid", "valid", "valid");
        User invalidUser_2 = new User(null, "valid", "valid", "valid");
        User invalidUser_3 = new User("      ", "valid", "valid", "valid");

        // Act
        boolean actualResult_1 = sut.isUserValid(invalidUser_1);
        boolean actualResult_2 = sut.isUserValid(invalidUser_2);
        boolean actualResult_3 = sut.isUserValid(invalidUser_3);

        // Assert
        Assert.assertFalse("Expected user to be considered false.", actualResult_1);
        Assert.assertFalse("Expected user to be considered false.", actualResult_2);
        Assert.assertFalse("Expected user to be considered false.", actualResult_3);

    }
    @Test
    public void test_isUserValid_returnsFalse_givenUserWithInvalidPassword() {

        // Arrange
        User invalidUser_1 = new User("valid", "", "valid", "valid");
        User invalidUser_2 = new User("valid", null, "valid", "valid");
        User invalidUser_3 = new User("valid", "      ", "valid", "valid");

        // Act
        boolean actualResult_1 = sut.isUserValid(invalidUser_1);
        boolean actualResult_2 = sut.isUserValid(invalidUser_2);
        boolean actualResult_3 = sut.isUserValid(invalidUser_3);

        // Assert
        Assert.assertFalse("Expected user to be considered false.", actualResult_1);
        Assert.assertFalse("Expected user to be considered false.", actualResult_2);
        Assert.assertFalse("Expected user to be considered false.", actualResult_3);

    }

    @Test
    public void test_registerNewUser_returnsTrue_givenValidUser() {
        //Arrange
        User validUser = new User("valid", "valid", "valid", "valid");
        when(mockUserDAO.findUserByUsername(validUser)).thenReturn(null);
        when(mockUserDAO.save(validUser)).thenReturn(true);

        //Act
        boolean actualResult = sut.registerNewUser(validUser);

        //Assert
        Assert.assertTrue("Expected true return with valid user: ", actualResult);
        verify(mockUserDAO, times(1)).findUserByUsername(validUser);
        verify(mockUserDAO, times(1)).save(validUser);
    }

    @Test (expected = InvalidRequestException.class)
    public void test_registerNewUser_throwsInvalidRequestException_givenInvalidUser() {
        //Arrange
        User invalidUser = new User(null, "valid", "valid", "valid");
        when(mockUserDAO.findUserByUsername(invalidUser)).thenReturn(invalidUser);
        when(mockUserDAO.save(invalidUser)).thenReturn(true);

        //Act
        boolean actualResult = sut.registerNewUser(invalidUser);
        verify(mockUserDAO, times(0)).findUserByUsername(invalidUser);
        verify(mockUserDAO, times(0)).save(invalidUser);

    }

    @Test
    public void test_isUserAuthentic_returnsTrue_givenUserWithValidUsername() {

        // Arrange
        User validUser = new User("valid", "valid", "valid", "valid");

        // Act
        boolean actualResult_1 = sut.isUserAuthentic(validUser);

        // Assert
        Assert.assertTrue("Expected user to be considered false.", actualResult_1);
    }

    @Test
    public void test_isUserAuthentic_returnsFalse_givenUserWithInvalidUsername() {

        // Arrange
        User invalidUser_1 = new User(null, "valid", "valid", "valid");
        User invalidUser_2 = new User("", "valid", "valid", "valid");

        // Act
        boolean actualResult_1 = sut.isUserAuthentic(invalidUser_1);
        boolean actualResult_2 = sut.isUserAuthentic(invalidUser_2);

        // Assert
        Assert.assertFalse("Expected user to be considered false.", actualResult_1);
        Assert.assertFalse("Expected user to be considered false.", actualResult_2);
    }


   @Test
    public void test_authenticateUser_returnsValidUser_givenValidSessionUser() throws SQLException {
        //Arrange
       User validUser = new User("valid", "valid", "valid", "valid");
       when(mockUserDAO.findUserByUsernameAndPassword(validUser)).thenReturn(validUser);

       //Act
       User user = sut.authenticateUser(validUser);

       //assert
       verify(mockUserDAO, times(1)).findUserByUsernameAndPassword(user);
       Assert.assertNotNull(user);
   }

   @Test(expected = InvalidRequestException.class)
    public void test_authenticateUser_throwsInvalidRequestException_givenInvalidUser() throws SQLException {
        //Arrange
       User invalidUser = new User(null, "valid", "valid", "valid");
       when(mockUserDAO.findUserByUsernameAndPassword(invalidUser)).thenReturn(invalidUser);

       //Act
       User user = sut.authenticateUser(invalidUser);
       verify(mockUserDAO, times(0)).findUserByUsername(invalidUser);

   }

    @Test(expected = AuthenticationException.class)
    public void test_authenticateUser_throwsAuthenticationException_givenInauthenticUser() throws SQLException {
        //Arrange
        User inauthenticUser = new User("valid", "valid", "valid", "valid");
        when(mockUserDAO.findUserByUsernameAndPassword(inauthenticUser)).thenReturn(null);

        //Act
        User user = sut.authenticateUser(inauthenticUser);
        verify(mockUserDAO, times(0)).findUserByUsername(inauthenticUser);

    }

    @Test
    public void test_deleteUser_returnsTrue_givenValidUser(){
        //arrange
        User validUser = new User("valid","valid","valid","valid");
        when(mockUserDAO.removeById(validUser)).thenReturn(true);

        //act
        boolean actualResult = sut.deleteUser(validUser);

        //assert
        Assert.assertTrue(actualResult);

    }

    @Test
    public void test_deleteUser_returnsFalse_givenInvalidUser(){
        //arrange
        User invalidUser = new User(null, "valid", "valid", "valid");
        when(mockUserDAO.removeById(invalidUser)).thenReturn(false);

        //act
        boolean actualResult = sut.deleteUser(invalidUser);

        //assert
        Assert.assertFalse(actualResult);
    }

    @Test
    public void test_updateUser_returnsTrue_givenValidUser(){
        //arrange
        User validUser = new User("valid","valid","valid","valid");
        when(mockUserDAO.update(validUser)).thenReturn(true);

        //act
        boolean actualResult = sut.updateUser(validUser);

        //assert
        Assert.assertTrue(actualResult);

    }

    @Test
    public void test_updateUser_returnsFalse_givenInvalidUser(){
        //arrange
        User invalidUser = new User(null, "valid", "valid", "valid");
        when(mockUserDAO.removeById(invalidUser)).thenReturn(false);

        //act
        boolean actualResult = sut.updateUser(invalidUser);

        //assert
        Assert.assertFalse(actualResult);
    }

}
