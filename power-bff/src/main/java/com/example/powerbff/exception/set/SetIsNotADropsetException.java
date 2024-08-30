package com.example.powerbff.exception.set;

public class SetIsNotADropsetException extends RuntimeException {
    public SetIsNotADropsetException(Object object) {
        super("Set '" + object + "' is not a dropset");
    }
}
