package com.example.powerbff.repository;

import com.example.powerbff.entity.Exercise;
import com.example.powerbff.entity.ExerciseType;
import com.example.powerbff.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    Boolean existsByName(String name);

    Optional<Exercise> findByName(String name);

    List<Exercise> findByCreatedByIsNullOrCreatedBy(User user);
    List<Exercise> findByNameContainsAndCreatedByIsNullOrCreatedBy(String name, User user);
    List<Exercise> findByTypeAndCreatedByIsNullOrCreatedBy(ExerciseType type, User user);

}
