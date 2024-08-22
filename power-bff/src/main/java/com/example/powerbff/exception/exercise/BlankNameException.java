package com.example.powerbff.exception.exercise;

public class BlankNameException extends Exception {
    public BlankNameException() {
        super("Name cannot be blank");
    }
}
