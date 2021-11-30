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

    private String userId;
    private String username;
    private String password;
    private String first_name;
    private String last_name;

    //000000000000000000000000000000000000000000000000000000000000000000000000000000000

    //CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC

    public User(String username, String password) {
        this.username = username;
        this.password = password;

    }

    public User(String userId, String username, String password, String firstName, String lastName) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.first_name = firstName;
        this.last_name = lastName;
    }
    //CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }
}
