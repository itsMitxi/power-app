package com.example.powerbff.repository;

import com.example.powerbff.entity.User;
import com.example.powerbff.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    List<Workout> findByUser(User user);

    List<Workout> findByDate(LocalDate date);

    List<Workout> findByDateBetween(LocalDate startDate, LocalDate endDate);

}
