package com.example.powerbff.controller;

import com.example.powerbff.constants.ApiEndpoints;
import com.example.powerbff.entity.Muscle;
import com.example.powerbff.service.MuscleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(ApiEndpoints.MUSCLES_BASE)
public class MuscleController {

    @Autowired
    private MuscleService muscleService;

    @PostMapping
    public ResponseEntity<Muscle> createMuscle(@RequestBody Muscle muscle) {
        try {
            return ResponseEntity.ok(muscleService.createMuscle(muscle));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Muscle>> getAllMuscles() {
        return ResponseEntity.ok(muscleService.getAllMuscles());
    }

    @GetMapping(ApiEndpoints.MUSCLE_BY_ID + "{muscleId}")
    public ResponseEntity<Muscle> getMuscleById(@PathVariable Long muscleId) {
        Optional<Muscle> muscle = muscleService.getMuscleById(muscleId);
        return muscle.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(ApiEndpoints.MUSCLE_BY_NAME + "{muscleName}")
    public ResponseEntity<Muscle> getMuscleByName(@PathVariable String muscleName) {
        Optional<Muscle> muscle = muscleService.getMuscleByName(muscleName);
        return muscle.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(ApiEndpoints.MUSCLES_BY_MUSCLE_GROUP + "{muscleGroupName}")
    public ResponseEntity<List<Muscle>> getMusclesByMuscleGroup(@PathVariable String muscleGroupName) {
        try {
            return ResponseEntity.ok(muscleService.getMusclesByMuscleGroup(muscleGroupName));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(ApiEndpoints.MUSCLE_BY_ID + "{muscleId}")
    public ResponseEntity<Muscle> updateMuscle(@PathVariable Long muscleId, @RequestBody Muscle muscle) {
        try {
            return ResponseEntity.ok(muscleService.updateMuscle(muscleId, muscle));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(ApiEndpoints.MUSCLE_BY_ID + "{muscleId}")
    public ResponseEntity<Void> deleteMuscle(@PathVariable Long muscleId) {
        muscleService.deleteMuscle(muscleId);
        return ResponseEntity.noContent().build();
    }

}
