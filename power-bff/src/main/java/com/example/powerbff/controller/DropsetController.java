package com.example.powerbff.controller;

import com.example.powerbff.constants.ApiEndpoints;
import com.example.powerbff.entity.Dropset;
import com.example.powerbff.service.DropsetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ApiEndpoints.DROPSETS_BASE)
public class DropsetController {

    @Autowired
    private DropsetService dropsetService;

    @PostMapping
    public ResponseEntity<Dropset> createDropset(@RequestBody Dropset dropset) {
        try {
            return ResponseEntity.ok(dropsetService.createDropset(dropset));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Dropset>> getAllDropsets() {
        return ResponseEntity.ok(dropsetService.getAllDropsets());
    }

    @GetMapping(ApiEndpoints.DROPSET_BY_ID + "{dropsetId}")
    public ResponseEntity<Dropset> getDropsetById(@PathVariable long dropsetId) {
        Optional<Dropset> dropset = dropsetService.getDropsetById(dropsetId);
        return dropset.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(ApiEndpoints.DROPSET_BY_SET + "{setId}")
    public ResponseEntity<List<Dropset>> getDropsetsBySet(@PathVariable long setId) {
        try {
            return ResponseEntity.ok(dropsetService.getDropsetsBySet(setId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(ApiEndpoints.DROPSET_BY_ID + "{dropsetId}")
    public ResponseEntity<Dropset> updateDropset(@PathVariable long dropsetId, @RequestBody Dropset dropset) {
        try {
            return ResponseEntity.ok(dropsetService.updateDropset(dropsetId, dropset));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(ApiEndpoints.DROPSET_BY_ID + "{dropsetId}")
    public ResponseEntity<Dropset> deleteDropset(@PathVariable long dropsetId) {
        dropsetService.deleteDropset(dropsetId);
        return ResponseEntity.noContent().build();
    }

}
