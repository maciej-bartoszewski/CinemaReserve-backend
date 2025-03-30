package com.cinemareserve.cinemareserve.exception;

public class PasswordsAreNotTheSameException extends RuntimeException {
    public PasswordsAreNotTheSameException(String message) {
        super(message);
    }
}
