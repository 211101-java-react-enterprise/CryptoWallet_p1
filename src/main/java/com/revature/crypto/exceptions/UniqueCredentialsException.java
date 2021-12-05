package com.revature.crypto.exceptions;

public class UniqueCredentialsException extends RuntimeException {
    public UniqueCredentialsException(String s) {
        super(s);
    }

    public UniqueCredentialsException() {
        super("value must be unique");
    }

}
