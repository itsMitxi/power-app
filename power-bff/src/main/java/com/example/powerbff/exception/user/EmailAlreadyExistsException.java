package com.example.powerbff.exception.user;

public class EmailAlreadyExistsException extends Exception {
    public EmailAlreadyExistsException(String email) {
        super("Email '" + email + "' is already in use");
    }
}
