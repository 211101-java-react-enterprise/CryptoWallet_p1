package com.revature.crypto.services;

import com.revature.crypto.exceptions.AuthenticationException;
import com.revature.crypto.exceptions.InvalidRequestException;
import com.revature.crypto.models.User;

/**
 * User Service class holds logic surrounding the validity of
 * user entered information and ensures database integrity.
 *
 */
public class UserService {

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

    public boolean isUserValid(User user) {
        if (user == null) return false;
        if (user.getFirstName() == null || user.getFirstName().trim().equals("")) return false;
        if (user.getLastName() == null || user.getLastName().trim().equals("")) return false;
        if (user.getUsername() == null || user.getUsername().trim().equals("")) return false;
        return user.getPassword() != null && !user.getPassword().trim().equals("");
    }


}
