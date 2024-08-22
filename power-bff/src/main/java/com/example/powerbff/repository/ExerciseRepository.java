package com.example.powerbff.repository;

import com.example.powerbff.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    Boolean existsByName(String name);

    Optional<Exercise> findByName(String name);

}
