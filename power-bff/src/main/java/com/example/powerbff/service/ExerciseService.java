package com.example.powerbff.service;

import com.example.powerbff.entity.*;
import com.example.powerbff.exception.exercise.*;
import com.example.powerbff.exception.muscle.MuscleNotFoundException;
import com.example.powerbff.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.Set;

@Service
public class ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private MuscleRepository muscleRepository;
    @Autowired
    private ExerciseTypeRepository exerciseTypeRepository;

    public Exercise createExercise(Exercise exercise) throws NameAlreadyExistsException, BlankNameException, MuscleNotFoundException, ExerciseTypeNotFoundException {
        if (exercise.getName() == null || exercise.getName().isEmpty()) {
            throw new BlankNameException();
        } else if (exerciseRepository.existsByName(exercise.getName())) {
            throw new NameAlreadyExistsException(exercise.getName());
        }
        if (!exercise.getMuscles().isEmpty()) {
            Set<Muscle> muscles = new HashSet<>();
            for (Muscle muscle : exercise.getMuscles()) {
                Muscle muscleFound = muscleRepository.findByName(muscle.getName()).orElseThrow(() -> new MuscleNotFoundException(muscle.getName()));
                muscles.add(muscleFound);
            }
            exercise.setMuscles(muscles);
        }
        if (!Objects.equals(exercise.getType(), null)) {
            ExerciseType exerciseType = exerciseTypeRepository.findByName(exercise.getType().getName()).orElseThrow(() -> new ExerciseTypeNotFoundException(exercise.getType().getName()));
            exercise.setType(exerciseType);
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
    public Exercise updateExercise(Long id, Exercise exerciseDetails) throws ExerciseNotFoundException, NameAlreadyExistsException, MuscleNotFoundException, ExerciseTypeNotFoundException {
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
        if (!exerciseDetails.getMuscles().isEmpty()) {
            Set<Muscle> muscles = new HashSet<>();
            for (Muscle muscle : exerciseDetails.getMuscles()) {
                Muscle muscleFound = muscleRepository.findByName(muscle.getName()).orElseThrow(() -> new MuscleNotFoundException(muscle.getName()));
                muscles.add(muscleFound);
            }
            exercise.setMuscles(muscles);
        }
        if (!Objects.equals(exercise.getType(), null)) {
            ExerciseType exerciseType = exerciseTypeRepository.findByName(exercise.getType().getName()).orElseThrow(() -> new ExerciseTypeNotFoundException(exercise.getType().getName()));
            exercise.setType(exerciseType);
        }

        return exerciseRepository.save(exercise);
    }

    public void deleteExercise(Long id) {
        exerciseRepository.deleteById(id);
    }

}
