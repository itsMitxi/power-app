package com.example.powerbff.controller;

import com.example.powerbff.constants.ApiEndpoints;
import com.example.powerbff.entity.SetType;
import com.example.powerbff.service.SetTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ApiEndpoints.SET_TYPES_BASE)
public class SetTypeController {

    @Autowired
    private SetTypeService setTypeService;

    @PostMapping
    public ResponseEntity<SetType> createSetType(@RequestBody SetType setType) {
        try {
            return ResponseEntity.ok(setTypeService.createSetType(setType));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<SetType>> getAllSetTypes() {
        return ResponseEntity.ok(setTypeService.getAllSetTypes());
    }

    @GetMapping(ApiEndpoints.SET_TYPE_BY_ID + "{setTypeId}")
    public ResponseEntity<SetType> getSetTypeById(@PathVariable Long setTypeId) {
        Optional<SetType> setType = setTypeService.getSetTypeById(setTypeId);
        return setType.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(ApiEndpoints.SET_TYPE_BY_NAME + "{setTypeName}")
    public ResponseEntity<SetType> getSetTypeByName(@PathVariable String setTypeName) {
        Optional<SetType> setType = setTypeService.getSetTypeByName(setTypeName);
        return setType.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(ApiEndpoints.SET_TYPE_BY_ID + "{setTypeId}")
    public ResponseEntity<SetType> updateSetTypeById(@PathVariable long setTypeId, @RequestBody SetType setType) {
        try {
            return ResponseEntity.ok(setTypeService.updateSetType(setTypeId, setType));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(ApiEndpoints.SET_TYPE_BY_ID + "{setTypeId}")
    public ResponseEntity<Void> deleteSetType(@PathVariable long setTypeId) {
        setTypeService.deleteSetType(setTypeId);
        return ResponseEntity.noContent().build();
    }

}
