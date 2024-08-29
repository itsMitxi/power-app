package com.example.powerbff.exception.workout;

import java.time.LocalDate;

public class DateNotReachedException extends RuntimeException {
    public DateNotReachedException(LocalDate date) {
        super("Date " + date + " must not be after today (" + LocalDate.now() + ")");
    }
}
