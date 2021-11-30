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

    private String user_Id;
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

    public User(String user_Id, String username, String password, String first_name, String last_name) {
        this.user_Id = user_Id;
        this.username = username;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
    }
    //CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC


    public String getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(String user_Id) {
        this.user_Id = user_Id;
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

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}
