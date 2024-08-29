package com.example.powerbff.service;

import com.example.powerbff.entity.User;
import com.example.powerbff.entity.Workout;
import com.example.powerbff.exception.user.*;
import com.example.powerbff.exception.workout.*;
import com.example.powerbff.repository.UserRepository;
import com.example.powerbff.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository;
    @Autowired
    private UserRepository userRepository;

    public Workout createWorkout(Workout workout) throws UserBlankException, UserNotFoundException, BlankUsernameException {
        if (Objects.equals(workout.getUser(), null)) {
            throw new UserBlankException();
        } else if (workout.getUser().getUsername() == null) {
            throw new BlankUsernameException();
        } else if (workout.getDate() != null) {
            if (isDateAfterToday(workout.getDate())) {
                throw new DateNotReachedException(workout.getDate());
            }
        } else {
            workout.setDate(LocalDate.now());
        }
        User user = userRepository.findByUsername(workout.getUser().getUsername()).orElseThrow(() -> new UserNotFoundException(workout.getUser().getUsername()));
        workout.setUser(user);

        return workoutRepository.save(workout);
    }

    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }

    public Optional<Workout> getWorkoutById(long id) {
        return workoutRepository.findById(id);
    }

    public List<Workout> getWorkoutsByUser(String username) throws UserNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        return workoutRepository.findByUser(user);
    }

    public List<Workout> getWorkoutsByDate(LocalDate date) {
        return workoutRepository.findByDate(date);
    }

    public List<Workout> getWorkoutsBetweenDates(LocalDate startDate, LocalDate endDate) {
        return workoutRepository.findByDateBetween(startDate, endDate);
    }

    @Transactional
    public Workout updateWorkout(long id, Workout workoutDetails) throws WorkoutNotFoundException, UserNotFoundException {
        Workout workout = workoutRepository.findById(id).orElseThrow(() -> new WorkoutNotFoundException(id));

        if (!Objects.equals(workoutDetails.getUser(), null)) {
            User user = userRepository.findByUsername(workoutDetails.getUser().getUsername()).orElseThrow(() -> new UserNotFoundException(workoutDetails.getUser().getUsername()));
            workout.setUser(user);
        }
        if (!Objects.equals(workoutDetails.getDate(), null)) {
            workout.setDate(workoutDetails.getDate());
        }
        if (workoutDetails.getNotes() != null) {
            if (isDateAfterToday(workoutDetails.getDate())) {
                throw new DateNotReachedException(workout.getDate());
            }
            workout.setNotes(workoutDetails.getNotes());
        }

        return workoutRepository.save(workout);
    }

    public void deleteWorkout(long id) {
        workoutRepository.deleteById(id);
    }

    private static boolean isDateAfterToday(LocalDate date) {
        return date.isAfter(LocalDate.now());
    }

}
