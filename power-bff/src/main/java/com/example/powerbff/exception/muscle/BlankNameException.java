package com.example.powerbff.exception.muscle;

public class BlankNameException extends Exception {
    public BlankNameException() {
        super("Name cannot be blank");
    }
}
