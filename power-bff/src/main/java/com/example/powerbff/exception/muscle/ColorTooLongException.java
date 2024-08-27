package com.example.powerbff.exception.muscle;

public class ColorTooLongException extends RuntimeException {
    public ColorTooLongException(String color) {
        super("Color too long: " + color);
    }
}
