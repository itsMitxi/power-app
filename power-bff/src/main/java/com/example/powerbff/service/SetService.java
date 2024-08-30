package com.example.powerbff.service;

import com.example.powerbff.entity.*;
import com.example.powerbff.exception.exercise.ExerciseNotFoundException;
import com.example.powerbff.exception.exercise.ExerciseTypeNotFoundException;
import com.example.powerbff.exception.set.SetNotFoundException;
import com.example.powerbff.exception.set.SetTypeNotFoundException;
import com.example.powerbff.exception.workout.WorkoutNotFoundException;
import com.example.powerbff.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SetService {

    @Autowired
    private SetRepository setRepository;
    @Autowired
    private WorkoutRepository workoutRepository;
    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private SetTypeRepository setTypeRepository;
    @Autowired
    private ExerciseTypeRepository exerciseTypeRepository;

    public Set createSet(Set set) throws WorkoutNotFoundException, ExerciseTypeNotFoundException, ExerciseNotFoundException, SetTypeNotFoundException {
        Workout workout = workoutRepository.findById(set.getWorkout().getId()).orElseThrow(() -> new WorkoutNotFoundException(set.getWorkout().getId()));
        set.setWorkout(workout);

        ExerciseType exerciseType = exerciseTypeRepository.findByName(set.getExercise().getType().getName()).orElseThrow(() -> new ExerciseTypeNotFoundException(set.getExercise().getType().getName()));
        set.getExercise().setType(exerciseType);
        Exercise exercise = exerciseRepository.findByNameAndType(set.getExercise().getName(), set.getExercise().getType()).orElseThrow(() -> new ExerciseNotFoundException(set.getExercise().getName()));
        set.setExercise(exercise);

        SetType setType = setTypeRepository.findByName(set.getSetType().getName()).orElseThrow(() -> new SetTypeNotFoundException(set.getSetType().getName()));
        set.setSetType(setType);

        autoGenerateSetNumber(set);

        return setRepository.save(set);
    }

    public List<Set> getAllSets() {
        return setRepository.findAll();
    }

    public Optional<Set> getSetById(long id) {
        return setRepository.findById(id);
    }

    public List<Set> getSetsByWorkout(long workoutId) throws WorkoutNotFoundException {
        Workout workout = workoutRepository.findById(workoutId).orElseThrow(() -> new WorkoutNotFoundException(workoutId));
        return setRepository.findByWorkout(workout);
    }

    @Transactional
    public Set updateSet(long id, Set setDetails) throws SetNotFoundException, SetTypeNotFoundException {
        Set set = setRepository.findById(id).orElseThrow(() -> new SetNotFoundException(id));

        if (!Objects.equals(setDetails.getSetType(), null)) {
            SetType setType = setTypeRepository.findByName(setDetails.getSetType().getName()).orElseThrow(() -> new SetTypeNotFoundException(setDetails.getSetType().getName()));
            set.setSetType(setType);
        }
        set.setRepetitions(setDetails.getRepetitions());
        set.setWeight(setDetails.getWeight());

        return setRepository.save(set);
    }

    public void deleteSet(long id) {
        Optional<Set> set = setRepository.findById(id);

        setRepository.deleteById(id);
        set.ifPresent(this::autoUpdateSetNumbers);
    }

    private void autoGenerateSetNumber(Set set){
        List<Set> antSets = setRepository.findByWorkoutAndExercise(set.getWorkout(), set.getExercise());

        if (antSets.isEmpty()) set.setSetNumber(1);
        else set.setSetNumber(antSets.size() + 1);
    }

    private void autoUpdateSetNumbers(Set setToDelete) {
        List<Set> otherSets = setRepository.findByWorkoutAndExercise(setToDelete.getWorkout(), setToDelete.getExercise());

        int index = 1;
        for (Set set: otherSets) {
            set.setSetNumber(index++);
            setRepository.save(set);
        }
    }

}
