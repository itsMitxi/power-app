package com.example.powerbff.exception.muscle;

public class MuscleGroupNotFoundException extends Exception {
    public MuscleGroupNotFoundException(Object object) {
        super("Muscle group not found: " + object);
    }
}
