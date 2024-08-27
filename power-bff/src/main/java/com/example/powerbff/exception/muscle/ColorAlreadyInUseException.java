package com.example.powerbff.exception.muscle;

public class ColorAlreadyInUseException extends Exception {
    public ColorAlreadyInUseException(String color) {
        super("Color '" + color + "' is already in use");
    }
}
