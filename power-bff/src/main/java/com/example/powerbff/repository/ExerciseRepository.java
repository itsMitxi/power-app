package com.example.powerbff.repository;

import com.example.powerbff.entity.Exercise;
import com.example.powerbff.entity.ExerciseType;
import com.example.powerbff.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    Boolean existsByName(String name);

    Optional<Exercise> findByName(String name);

    Optional<Exercise> findByNameAndType(String name, ExerciseType type);

    List<Exercise> findByCreatedByIsNullOrCreatedBy(User user);

    @Query("SELECT e FROM Exercise e WHERE e.name LIKE %:name% AND (e.createdBy IS NULL OR e.createdBy = :user)")
    List<Exercise> findByNameContainsAndCreatedByIsNullOrCreatedBy(@Param("name") String name, @Param("user") User user);

    @Query("SELECT e FROM Exercise e WHERE e.type = :type AND (e.createdBy IS NULL OR e.createdBy = :user)")
    List<Exercise> findByTypeAndCreatedByIsNullOrCreatedBy(ExerciseType type, User user);

}
