package com.example.powerbff.exercise;

import com.example.powerbff.entity.Exercise;
import com.example.powerbff.exception.exercise.*;
import com.example.powerbff.service.ExerciseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ExerciseServiceTests {

    @Autowired
    private ExerciseService exerciseService;

    @Test
    public void shouldCreateAndDeleteExercise() {
        Exercise exercise = new Exercise();
        exercise.setName("exercise");
        try {
            exercise = exerciseService.createExercise(exercise);
        } catch (Exception ignored) {}

        assertThat(exercise).isNotNull();
        assertThat(exercise.getName()).isEqualTo("exercise");

        Optional<Exercise> exerciseFound = exerciseService.getExerciseByName(exercise.getName());
        assertThat(exerciseFound).isPresent();
        assertThat(exerciseFound.get().getName()).isEqualTo(exercise.getName());

        exerciseService.deleteExercise(exerciseFound.get().getId());
        Optional<Exercise> exerciseDeleted = exerciseService.getExerciseByName(exercise.getName());
        assertThat(exerciseDeleted).isNotPresent();
    }

    @Test
    public void shouldThrowNameExceptionWithDuplicatedName() {
        Exercise exercise = new Exercise();
        exercise.setName("same-name");
        try {
            exercise = exerciseService.createExercise(exercise);
        } catch (Exception ignored) {}

        Optional<Exercise> exerciseFound = exerciseService.getExerciseByName(exercise.getName());
        assertThat(exerciseFound).isPresent();

        Exercise sameExercise = exercise;
        assertThrows(NameAlreadyExistsException.class, () -> exerciseService.createExercise(sameExercise));

        exerciseService.deleteExercise(exerciseFound.get().getId());
    }

    @Test
    public void shouldFindExerciseById() {
        Exercise exercise = new Exercise();
        exercise.setName("find-by-id");
        try {
            exerciseService.createExercise(exercise);
        } catch (Exception ignored) {}

        Optional<Exercise> exerciseFound = exerciseService.getExerciseById(exercise.getId());
        assertThat(exerciseFound).isPresent();
        assertThat(exerciseFound.get().getName()).isEqualTo(exercise.getName());

        exerciseService.deleteExercise(exerciseFound.get().getId());
    }

    @Test
    public void shouldFindAllExercises() {
        Exercise exercise1 = new Exercise();
        exercise1.setName("exercise1");
        Exercise exercise2 = new Exercise();
        exercise2.setName("exercise2");
        Exercise exercise3 = new Exercise();
        exercise3.setName("exercise3");
        try {
            exerciseService.createExercise(exercise1);
            exerciseService.createExercise(exercise2);
            exerciseService.createExercise(exercise3);
        } catch (Exception ignored) {}

        List<Exercise> exercises = exerciseService.getAllExercises();
        assertThat(exercises).isNotEmpty();
        assertThat(exercises.size()).isEqualTo(3);
        assertThat(exercises.stream().map(Exercise::getName)).containsExactlyInAnyOrder("exercise1", "exercise2", "exercise3");

        exerciseService.deleteExercise(exercise1.getId());
        exerciseService.deleteExercise(exercise2.getId());
        exerciseService.deleteExercise(exercise3.getId());
    }

    @Test
    public void shouldThrowExceptionWithBlankName() {
        Exercise exercise = new Exercise();
        exercise.setName("");
        assertThrows(BlankNameException.class, () -> exerciseService.createExercise(exercise));
    }

    @Test
    public void shouldUpdateExercise() {
        Exercise exercise = new Exercise();
        exercise.setName("exercise-update");
        try {
            exercise = exerciseService.createExercise(exercise);
        } catch (Exception ignored) {}

        Optional<Exercise> exerciseFound = exerciseService.getExerciseById(exercise.getId());
        assertThat(exerciseFound).isPresent();

        exercise = new Exercise();
        exercise.setName("newname");
        exercise.setDescription("newdescription");
        exercise.setImgUrl("newimgurl");
        try {
            exercise = exerciseService.updateExercise(exerciseFound.get().getId(), exercise);
        } catch (Exception ignored) {}

        Optional<Exercise> exerciseUpdated = exerciseService.getExerciseById(exercise.getId());
        assertThat(exerciseUpdated).isPresent();
        assertThat(exerciseUpdated.get().getName()).isEqualTo("newname");
        assertThat(exerciseUpdated.get().getDescription()).isEqualTo("newdescription");
        assertThat(exerciseUpdated.get().getImgUrl()).isEqualTo("newimgurl");

        exerciseService.deleteExercise(exerciseUpdated.get().getId());
    }

    @Test
    public void shouldThrowExceptionUpdatingExerciseWithDuplicatedName() {
        Exercise exercise = new Exercise();
        exercise.setName("name");
        Exercise exercise2 = new Exercise();
        exercise2.setName("exercise2");
        try {
            exerciseService.createExercise(exercise);
            exerciseService.createExercise(exercise2);
        } catch (Exception ignored) {}

        Optional<Exercise> exerciseFound = exerciseService.getExerciseByName(exercise.getName());
        assertThat(exerciseFound).isPresent();
        Optional<Exercise> exercise2Found = exerciseService.getExerciseByName(exercise2.getName());
        assertThat(exercise2Found).isPresent();

        Exercise finalExercise = new Exercise();
        finalExercise.setName("exercise2");
        assertThrows(NameAlreadyExistsException.class, () -> exerciseService.updateExercise(exerciseFound.get().getId(), finalExercise));

        exerciseService.deleteExercise(exerciseFound.get().getId());
        exerciseService.deleteExercise(exercise2Found.get().getId());
    }

    @Test
    public void shouldThrowExceptionWhenUpdatingExerciseNotFound() {
        Exercise exercise = new Exercise();
        exercise.setName("name");
        assertThrows(ExerciseNotFoundException.class, () -> exerciseService.updateExercise(999L, exercise));
    }

}
