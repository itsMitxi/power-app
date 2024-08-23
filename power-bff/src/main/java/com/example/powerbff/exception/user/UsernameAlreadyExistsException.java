package com.example.powerbff.exception.user;

public class UsernameAlreadyExistsException extends Exception {
    public UsernameAlreadyExistsException(String username) {
        super("Username '" + username + "' already exists");
    }
}
