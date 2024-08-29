package com.example.powerbff.repository;

import com.example.powerbff.entity.Muscle;
import com.example.powerbff.entity.MuscleGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MuscleRepository extends JpaRepository<Muscle, Long> {

    Boolean existsByName(String name);

    Optional<Muscle> findByName(String name);

    List<Muscle> findAllByMuscleGroup(MuscleGroup muscleGroup);

}
