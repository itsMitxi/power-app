package com.example.powerbff.exception.set;

public class SetTypeNotFoundException extends Exception {
    public SetTypeNotFoundException(Object object) {
        super("Set type not found: " + object);
    }
}
