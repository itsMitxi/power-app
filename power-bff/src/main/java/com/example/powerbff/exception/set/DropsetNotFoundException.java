package com.example.powerbff.exception.set;

public class DropsetNotFoundException extends Exception {
    public DropsetNotFoundException(Object object) {
        super("Dropset not found: " + object);
    }
}
