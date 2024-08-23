package com.example.powerbff.repository;

import com.example.powerbff.entity.ExerciseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExerciseTypeRepository extends JpaRepository<ExerciseType, Long> {

    Boolean existsByName(String name);

    Optional<ExerciseType> findByName(String name);

}
