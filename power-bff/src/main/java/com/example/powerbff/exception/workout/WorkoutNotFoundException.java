package com.example.powerbff.exception.workout;

public class WorkoutNotFoundException extends Exception {
    public WorkoutNotFoundException(Object object) {
        super("Workout not found: " + object);
    }
}
