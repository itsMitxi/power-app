package com.example.powerbff.exception.user;

public class BlankUsernameException extends Exception {
    public BlankUsernameException() {
        super("Username cannot be blank");
    }
}
