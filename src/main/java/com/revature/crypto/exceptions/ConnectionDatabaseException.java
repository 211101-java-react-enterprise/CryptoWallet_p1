package com.revature.crypto.exceptions;

public class ConnectionDatabaseException extends RuntimeException{

    public ConnectionDatabaseException(String s) {
        super(s);
    }

    public ConnectionDatabaseException() {
        super("Could not connect to the database");
    }

}
