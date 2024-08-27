package com.example.powerbff.controller;

import com.example.powerbff.constants.ApiEndpoints;
import com.example.powerbff.entity.ExerciseType;
import com.example.powerbff.service.ExerciseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ApiEndpoints.EXERCISE_TYPES_BASE)
public class ExerciseTypeController {

    @Autowired
    private ExerciseTypeService exerciseTypeService;

    @PostMapping
    public ResponseEntity<ExerciseType> createExerciseType(@RequestBody ExerciseType exerciseType) {
        try {
            return ResponseEntity.ok(exerciseTypeService.createExerciseType(exerciseType));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ExerciseType>> getAllExerciseTypes() {
        return ResponseEntity.ok(exerciseTypeService.getAllExerciseTypes());
    }

    @GetMapping(ApiEndpoints.EXERCISE_TYPE_BY_ID + "{exerciseTypeId}")
    public ResponseEntity<ExerciseType> getExerciseTypeById(@PathVariable Long exerciseTypeId) {
        Optional<ExerciseType> exercise = exerciseTypeService.getExerciseTypeById(exerciseTypeId);
        return exercise.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(ApiEndpoints.EXERCISE_TYPE_BY_NAME + "{exerciseTypeName}")
    public ResponseEntity<ExerciseType> getExerciseByExerciseName(@PathVariable String exerciseTypeName) {
        Optional<ExerciseType> exercise = exerciseTypeService.getExerciseTypeByName(exerciseTypeName);
        return exercise.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(ApiEndpoints.EXERCISE_TYPE_BY_ID + "{exerciseTypeId}")
    public ResponseEntity<ExerciseType> updateExercise(@PathVariable Long exerciseTypeId, @RequestBody ExerciseType exerciseType) {
        try {
            return ResponseEntity.ok(exerciseTypeService.updateExerciseType(exerciseTypeId, exerciseType));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(ApiEndpoints.EXERCISE_TYPE_BY_ID + "{exerciseTypeId}")
    public ResponseEntity<Void> deleteExerciseType(@PathVariable Long exerciseTypeId) {
        exerciseTypeService.deleteExercise(exerciseTypeId);
        return ResponseEntity.noContent().build();
    }

}
