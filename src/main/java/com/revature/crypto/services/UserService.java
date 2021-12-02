package com.revature.crypto.services;

import com.revature.CryptoORM_P1.mapper.SQLMapper;
import com.revature.crypto.daos.UserDAO;
import com.revature.crypto.exceptions.AuthenticationException;
import com.revature.crypto.exceptions.InvalidRequestException;
import com.revature.crypto.models.User;

/**
 * User Service class holds logic surrounding the validity of
 * user entered information and ensures database integrity.
 *
 */
public class UserService {

    UserDAO userDAO;
    User sessionUser;

    public UserService(User sessionUser){
        this.sessionUser = sessionUser;
    }

    public boolean registerNewUser(User newUser) {
        return false;
    }
    /**
     *      UserService#isUserValid is used to check if a user has
     *      provided valid information before persisting to database.
     *
     *      Paramaters: newUser of type User (a new user that is trying to
     *                  be added to the database
     *      Returns:    boolean - true is a valid user ready to be stored
     *                            to the database and false is a user that
     *                            should not be persisted to database
     */
    public boolean isUserValid(User sessionUser) {
        if (sessionUser == null) return false;
        if (sessionUser.getFirstName() == null || sessionUser.getFirstName().trim().equals("")) return false;
        if (sessionUser.getLastName() == null || sessionUser.getLastName().trim().equals("")) return false;
        if (sessionUser.getUsername() == null || sessionUser.getUsername().trim().equals("")) return false;
        return sessionUser.getPassword() != null && !sessionUser.getPassword().trim().equals("");
    }

    /**
     * calls methods important to authenticating the integrity of the data and then persists
     */
    public User authenticateUser(User sessionUser) {

        if (!isUserValid(sessionUser)) {
            throw new InvalidRequestException("Invalid credential values provided!");
        }

        User authenticatedUser = userDAO.findUserByUsernameAndPassword(sessionUser);

        if (authenticatedUser == null) {
            throw new AuthenticationException();
        }

        return authenticatedUser;

    }

    /**
     *validates username and password
     */
    public boolean isUserAuthentic(){
        if (sessionUser.getUsername() == null || sessionUser.getUsername().trim().equals("")) return false;
        return sessionUser.getPassword() != null && !sessionUser.getPassword().trim().equals("");
    }


}
