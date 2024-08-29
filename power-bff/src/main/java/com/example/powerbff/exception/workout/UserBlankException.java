package com.example.powerbff.exception.workout;

public class UserBlankException extends RuntimeException {
    public UserBlankException() {
        super("User cannot be blank");
    }
}
