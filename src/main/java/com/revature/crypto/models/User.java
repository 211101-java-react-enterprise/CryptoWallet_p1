package com.revature.crypto.models;

/**
 *  User class is a simple data model used to store information relevant to
 *  a user
 *
 *  it stores username and password as well as a unique UUID
 *
 *  class primarily consists of simple getters and setters
 */
public class User {

    //000000000000000000000000000000000000000000000000000000000000000000000000000000000

    private String userUUID;
    private String username;
    private String password;

    //000000000000000000000000000000000000000000000000000000000000000000000000000000000

    //CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC

    public User(String username, String password) {
        this.username = username;
        this.password = password;

    }

    //CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC

    //---------------------------------------------------------------------------------

    public String getUserUUID() {
        return userUUID;
    }
    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    //----------------------------------------------------------------------------------

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    //-----------------------------------------------------------------------------------

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
