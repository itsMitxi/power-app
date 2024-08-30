package com.example.powerbff.repository;

import com.example.powerbff.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SetRepository extends JpaRepository<Set, Long> {

    List<Set> findByWorkout(Workout workout);

    List<Set> findByWorkoutAndExercise(Workout workout, Exercise exercise);

}
