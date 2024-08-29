package com.example.powerbff.exception.muscle;

public class MuscleNotFoundException extends Exception {
    public MuscleNotFoundException(Object object) {
        super("Muscle not found: " + object);
    }
}
