package com.example.powerbff.exception.set;

public class BlankNameException extends Exception {
    public BlankNameException() {
        super("Name cannot be blank");
    }
}
