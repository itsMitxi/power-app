package com.example.powerbff.exception.user;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(Object object) {
        super("User not found: " + object);
    }
}
