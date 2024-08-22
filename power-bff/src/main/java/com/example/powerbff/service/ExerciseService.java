package com.example.powerbff.service;

import com.example.powerbff.entity.Exercise;
import com.example.powerbff.exception.exercise.*;
import com.example.powerbff.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;

    public Exercise createExercise(Exercise exercise) throws NameAlreadyExistsException, BlankNameException {
        if (exercise.getName() == null || exercise.getName().isEmpty()) {
            throw new BlankNameException();
        } else if (exerciseRepository.existsByName(exercise.getName())) {
            throw new NameAlreadyExistsException(exercise.getName());
        }
        return exerciseRepository.save(exercise);
    }

    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }

    public Optional<Exercise> getExerciseById(Long id) {
        return exerciseRepository.findById(id);
    }

    public Optional<Exercise> getExerciseByName(String name) {
        return exerciseRepository.findByName(name);
    }

    @Transactional
    public Exercise updateExercise(Long id, Exercise exerciseDetails) throws ExerciseNotFoundException, NameAlreadyExistsException {
        Exercise exercise = exerciseRepository.findById(id).orElseThrow(() -> new ExerciseNotFoundException(id));

        if (!Objects.equals(exerciseDetails.getName(), "") && !Objects.equals(exerciseDetails.getName(), null)) {
            if (this.getExerciseByName(exerciseDetails.getName()).isPresent()) {
                throw new NameAlreadyExistsException(exercise.getName());
            }
            exercise.setName(exerciseDetails.getName());
        }
        if (!Objects.equals(exerciseDetails.getDescription(), "") && !Objects.equals(exerciseDetails.getDescription(), null))
            exercise.setDescription(exerciseDetails.getDescription());
        if (!Objects.equals(exerciseDetails.getImgUrl(), "") && !Objects.equals(exerciseDetails.getImgUrl(), null))
            exercise.setImgUrl(exerciseDetails.getImgUrl());

        return exerciseRepository.save(exercise);
    }

    public void deleteExercise(Long id) {
        exerciseRepository.deleteById(id);
    }

}
