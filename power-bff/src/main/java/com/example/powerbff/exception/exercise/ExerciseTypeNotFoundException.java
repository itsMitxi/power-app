package com.example.powerbff.exception.exercise;

public class ExerciseTypeNotFoundException extends Exception {
    public ExerciseTypeNotFoundException(Object object) {
        super("Exercise type not found: " + object);
    }
}
