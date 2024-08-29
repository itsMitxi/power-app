package com.example.powerbff.exception.muscle;

public class NameAlreadyExistsException extends Exception {
    public NameAlreadyExistsException(String name) {
        super("Name '" + name + "' already exists");
    }
}
