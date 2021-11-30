package com.revature.crypto.exceptions;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String msg) {
        super(msg);
    }
}