package com.example.powerbff.exception.set;

public class NameAlreadyExistsException extends Exception {
    public NameAlreadyExistsException(String name) {
        super("Name '" + name + "' already exists");
    }
}
