package com.revature.crypto.models;

import com.revature.CryptoORM_P1.annotations.Column;
import com.revature.CryptoORM_P1.annotations.Table;
import com.revature.CryptoORM_P1.annotations.Value;

/**
 *  User class is a simple data model used to store information relevant to
 *  a user
 *
 *  it stores username and password as well as a unique UUID
 *
 *  class primarily consists of simple getters and setters
 */
@Table(tableName = "app_user")
public class User {

    //000000000000000000000000000000000000000000000000000000000000000000000000000000000
    @Column(columnName = "user_uuid", columnType = "v")
    private String userId;
    @Column(columnName = "username", columnType = "v")
    private String username;
    @Column(columnName = "password", columnType = "v")
    private String password;
    @Column(columnName = "first_name", columnType = "v")
    private String firstName;
    @Column(columnName = "last_name", columnType = "v")
    private String lastName;
    @Column(columnName = "dollars_invested", columnType = "n")
    private double amount_invested;

    //000000000000000000000000000000000000000000000000000000000000000000000000000000000

    //CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC

    public User(String username, String password) {
        this.username = username;
        this.password = password;

    }

    public User(String userId, String username, String password, String firstName, String lastName, double amount_invested) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.amount_invested = amount_invested;
    }
    //CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC

    @Value(correspondingColumn = "user_uuid")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Value(correspondingColumn = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Value(correspondingColumn = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Value(correspondingColumn = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String first_name) {
        this.firstName = first_name;
    }

    @Value(correspondingColumn = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String last_name) {
        this.lastName = last_name;
    }

    @Value(correspondingColumn = "dollars_invested")
    public double getAmount_invested() {
        return amount_invested;
    }

    public void setAmount_invested(double amount_invested) {
        this.amount_invested = amount_invested;
    }
}
