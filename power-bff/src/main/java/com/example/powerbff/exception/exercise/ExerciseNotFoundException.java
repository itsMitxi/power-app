package com.example.powerbff.exception.exercise;

public class ExerciseNotFoundException extends Exception {
    public ExerciseNotFoundException(Object object) {
        super("Exercise not found: " + object);
    }
}
