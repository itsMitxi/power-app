package com.example.powerbff.exercise;

import com.example.powerbff.entity.ExerciseType;
import com.example.powerbff.repository.ExerciseTypeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ExerciseTypeRepositoryTests {

    @Autowired
    private ExerciseTypeRepository exerciseTypeRepository;

    @Test
    public void shouldFindExerciseTypeByName() {
        ExerciseType type = new ExerciseType();
        type.setName("test");
        exerciseTypeRepository.save(type);

        Optional<ExerciseType> exerciseType = exerciseTypeRepository.findByName("test");
        assertThat(exerciseType).isPresent();
        assertThat(exerciseType.get().getName()).isEqualTo("test");

        exerciseTypeRepository.delete(type);
    }

    @Test
    public void shouldNotCreateExerciseTypeWithDuplicatedName() {
        ExerciseType type = new ExerciseType();
        type.setName("test");
        exerciseTypeRepository.save(type);

        Optional<ExerciseType> exerciseType = exerciseTypeRepository.findByName("test");
        assertThat(exerciseType).isPresent();

        ExerciseType finalType = new ExerciseType();
        finalType.setName("test");
        assertThrows(DataIntegrityViolationException.class, () -> exerciseTypeRepository.save(finalType));

        exerciseTypeRepository.delete(type);
    }

}
