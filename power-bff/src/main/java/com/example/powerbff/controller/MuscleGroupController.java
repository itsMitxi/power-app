package com.example.powerbff.controller;

import com.example.powerbff.constants.ApiEndpoints;
import com.example.powerbff.entity.MuscleGroup;
import com.example.powerbff.service.MuscleGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(ApiEndpoints.MUSCLE_GROUPS_BASE)
public class MuscleGroupController {

    @Autowired
    private MuscleGroupService muscleGroupService;

    @PostMapping
    public ResponseEntity<MuscleGroup> createMuscleGroup(@RequestBody MuscleGroup muscleGroup) {
        try {
            return ResponseEntity.ok(muscleGroupService.createMuscleGroup(muscleGroup));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<MuscleGroup>> getAllMuscleGroups() {
        return ResponseEntity.ok(muscleGroupService.getAllMuscleGroups());
    }

    @GetMapping(ApiEndpoints.MUSCLE_GROUP_BY_ID + "{muscleGroupId}")
    public ResponseEntity<MuscleGroup> getMuscleGroupById(@PathVariable Long muscleGroupId) {
        Optional<MuscleGroup> muscleGroup = muscleGroupService.getMuscleGroupById(muscleGroupId);
        return muscleGroup.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(ApiEndpoints.MUSCLE_GROUP_BY_NAME + "{muscleGroupName}")
    public ResponseEntity<MuscleGroup> getMuscleGroupByName(@PathVariable String muscleGroupName) {
        Optional<MuscleGroup> muscleGroup = muscleGroupService.getMuscleGroupByName(muscleGroupName);
        return muscleGroup.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(ApiEndpoints.MUSCLE_GROUP_BY_ID + "{muscleGroupId}")
    public ResponseEntity<MuscleGroup> updateMuscleGroup(@PathVariable Long muscleGroupId, @RequestBody MuscleGroup muscleGroup) {
        try {
            return ResponseEntity.ok(muscleGroupService.updateMuscleGroup(muscleGroupId, muscleGroup));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(ApiEndpoints.MUSCLE_GROUP_BY_ID + "{muscleGroupId}")
    public ResponseEntity<Void> deleteMuscleGroup(@PathVariable Long muscleGroupId) {
        muscleGroupService.deleteMuscleGroup(muscleGroupId);
        return ResponseEntity.noContent().build();
    }

}
