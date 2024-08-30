package com.example.powerbff.exception.set;

public class SetNotFoundException extends Exception {
    public SetNotFoundException(Object object) {
        super("Set not found: " + object);
    }
}
