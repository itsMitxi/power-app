package com.example.powerbff.controller;

import com.example.powerbff.constants.ApiEndpoints;
import com.example.powerbff.entity.Workout;
import com.example.powerbff.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ApiEndpoints.WORKOUTS_BASE)
public class WorkoutController {

    @Autowired
    private WorkoutService workoutService;

    @PostMapping
    public ResponseEntity<Workout> createWorkout(@RequestBody Workout workout) {
        try {
            return ResponseEntity.ok(workoutService.createWorkout(workout));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Workout>> getAllWorkouts() {
        return ResponseEntity.ok(workoutService.getAllWorkouts());
    }

    @GetMapping(ApiEndpoints.WORKOUT_BY_ID + "{workoutId}")
    public ResponseEntity<Workout> getWorkoutById(@PathVariable long workoutId) {
        Optional<Workout> workout = workoutService.getWorkoutById(workoutId);
        return workout.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(ApiEndpoints.WORKOUTS_BY_USER + "{userUsername}")
    public ResponseEntity<List<Workout>> getWorkoutsByUser(@PathVariable String userUsername) {
        try {
            return ResponseEntity.ok(workoutService.getWorkoutsByUser(userUsername));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(ApiEndpoints.WORKOUTS_BY_DATE + "{date}")
    public ResponseEntity<List<Workout>> getWorkoutsByDate(@PathVariable LocalDate date) {
        return ResponseEntity.ok(workoutService.getWorkoutsByDate(date));
    }

    @GetMapping(ApiEndpoints.WORKOUTS_BETWEEN_DATES)
    public ResponseEntity<List<Workout>> getWorkoutsBetweenDates(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(workoutService.getWorkoutsBetweenDates(startDate, endDate));
    }

    @PutMapping(ApiEndpoints.WORKOUT_BY_ID + "{workoutId}")
    public ResponseEntity<Workout> updateWorkout(@PathVariable long workoutId, @RequestBody Workout workout) {
        try {
            return ResponseEntity.ok(workoutService.updateWorkout(workoutId, workout));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(ApiEndpoints.WORKOUT_BY_ID + "{workoutId}")
    public ResponseEntity<Workout> deleteWorkout(@PathVariable long workoutId) {
        workoutService.deleteWorkout(workoutId);
        return ResponseEntity.noContent().build();
    }

}
