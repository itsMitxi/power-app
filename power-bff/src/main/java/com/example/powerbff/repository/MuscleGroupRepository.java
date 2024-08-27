package com.example.powerbff.repository;

import com.example.powerbff.entity.MuscleGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MuscleGroupRepository extends JpaRepository<MuscleGroup, Long> {

    Boolean existsByColor(String color);

    Boolean existsByName(String name);

    Optional<MuscleGroup> findByName(String name);

}
