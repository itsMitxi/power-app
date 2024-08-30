package com.example.powerbff.controller;

import com.example.powerbff.entity.Set;
import com.example.powerbff.constants.ApiEndpoints;
import com.example.powerbff.service.SetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ApiEndpoints.SETS_BASE)
public class SetController {

    @Autowired
    private SetService setService;

    @PostMapping
    public ResponseEntity<Set> createSet(@RequestBody Set set) {
        try {
            return ResponseEntity.ok(setService.createSet(set));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Set>> getAllSets() {
        return ResponseEntity.ok(setService.getAllSets());
    }

    @GetMapping(ApiEndpoints.SET_BY_ID + "{setId}")
    public ResponseEntity<Set> getSetById(@PathVariable Long setId) {
        Optional<Set> set = setService.getSetById(setId);
        return set.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(ApiEndpoints.SETS_BY_WORKOUT + "{workoutId}")
    public ResponseEntity<List<Set>> getSetsByWorkout(@PathVariable long workoutId) {
        try {
            return ResponseEntity.ok(setService.getSetsByWorkout(workoutId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(ApiEndpoints.SET_BY_ID + "{setId}")
    public ResponseEntity<Set> updateSet(@PathVariable long setId, @RequestBody Set set) {
        try {
            return ResponseEntity.ok(setService.updateSet(setId, set));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(ApiEndpoints.SET_BY_ID + "{setId}")
    public ResponseEntity<Void> deleteSet(@PathVariable long setId) {
        setService.deleteSet(setId);
        return ResponseEntity.noContent().build();
    }

}
