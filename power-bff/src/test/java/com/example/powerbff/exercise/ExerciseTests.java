package com.example.powerbff.exercise;

import com.example.powerbff.entity.Exercise;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ExerciseTests {

    @Test
    public void shouldCreateExercise() {
        Exercise exercise = new Exercise();
        exercise.setName("exercise");
        exercise.setDescription("description");
        exercise.setImgUrl("imgurl");

        assertThat(exercise.getName()).isEqualTo("exercise");
        assertThat(exercise.getDescription()).isEqualTo("description");
        assertThat(exercise.getImgUrl()).isEqualTo("imgurl");
    }

    @Test
    public void shouldAutoAssignId() {
        Exercise exercise = new Exercise();

        assertThat(exercise.getId()).isNotEqualTo(null);
    }

}
