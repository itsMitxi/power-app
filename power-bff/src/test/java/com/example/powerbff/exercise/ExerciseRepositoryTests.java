package com.example.powerbff.exercise;

import com.example.powerbff.entity.Exercise;
import com.example.powerbff.entity.ExerciseType;
import com.example.powerbff.entity.User;
import com.example.powerbff.repository.ExerciseRepository;
import com.example.powerbff.repository.ExerciseTypeRepository;
import com.example.powerbff.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ExerciseRepositoryTests {

    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ExerciseTypeRepository exerciseTypeRepository;

    @Test
    public void shouldSaveAndDeleteExercise() {
        Exercise exercise = new Exercise();
        exercise.setName("testexercise");
        exercise.setDescription("testdescription");
        exercise.setImgUrl("testimgurl");

        exerciseRepository.save(exercise);
        Optional<Exercise> foundExercise = exerciseRepository.findByName("testexercise");
        assertThat(foundExercise).isPresent();

        exerciseRepository.deleteById(foundExercise.get().getId());
        foundExercise = exerciseRepository.findByName("testexercise");
        assertThat(foundExercise).isNotPresent();
    }

    @Test
    public void shouldCreateTwoExercisesWithDifferentIds() {
        Exercise exercise = new Exercise();
        exercise.setName("oneExercise");
        exerciseRepository.save(exercise);

        exercise = new Exercise();
        exercise.setName("anotherExercise");
        exerciseRepository.save(exercise);

        Optional<Exercise> oneExercise = exerciseRepository.findByName("oneExercise");
        assertThat(oneExercise).isPresent();

        Optional<Exercise> anotherExercise = exerciseRepository.findByName("anotherExercise");
        assertThat(anotherExercise).isPresent();

        assertThat(oneExercise.get().getId()).isNotEqualTo(anotherExercise.get().getId());

        exerciseRepository.deleteById(oneExercise.get().getId());
        exerciseRepository.deleteById(anotherExercise.get().getId());
    }

    @Test
    public void shouldNotCreateExerciseWithNonAutoGeneratedId() {
        Exercise exercise = new Exercise();
        exercise.setId(999L);
        exercise.setName("exercise");
        exerciseRepository.save(exercise);

        assertThat(exerciseRepository.findById(999L)).isNotPresent();
        Optional<Exercise> foundExercise = exerciseRepository.findByName("exercise");
        assertThat(foundExercise).isPresent();

        exerciseRepository.deleteById(foundExercise.get().getId());
    }

    @Test
    public void shouldCreateNewAndNotUpdateExerciseWhenUpdatingId() {
        Exercise exercise = new Exercise();
        exercise.setName("name1");
        exerciseRepository.save(exercise);

        Optional<Exercise> foundExercise = exerciseRepository.findByName("name1");
        assertThat(foundExercise).isPresent();

        exercise = foundExercise.get();
        exercise.setId(999);
        exercise.setName("name2");
        try {
            exerciseRepository.save(exercise);
        } catch (Exception ignored) {}

        foundExercise = exerciseRepository.findById(999L);
        assertThat(foundExercise).isNotPresent();

        Optional<Exercise> exercise1 = exerciseRepository.findByName("name1");
        assertThat(exercise1).isPresent();

        Optional<Exercise> exercise2 = exerciseRepository.findByName("name2");
        assertThat(exercise2).isPresent();

        exerciseRepository.deleteById(exercise1.get().getId());
        exerciseRepository.deleteById(exercise2.get().getId());
    }

    @Test
    public void shouldUpdateOtherFieldsThanId() {
        Exercise exercise = new Exercise();
        exercise.setName("name1");
        exercise.setDescription("description1");
        exercise.setImgUrl("imgurl1");
        exerciseRepository.save(exercise);

        Optional<Exercise> foundExercise = exerciseRepository.findByName("name1");
        assertThat(foundExercise).isPresent();
        assertThat(foundExercise.get().getName()).isEqualTo("name1");
        assertThat(foundExercise.get().getDescription()).isEqualTo("description1");
        assertThat(foundExercise.get().getImgUrl()).isEqualTo("imgurl1");

        exercise = foundExercise.get();
        exercise.setName("name2");
        exercise.setDescription("description2");
        exercise.setImgUrl("imgurl2");
        exerciseRepository.save(exercise);

        foundExercise = exerciseRepository.findByName("name2");
        assertThat(foundExercise).isPresent();
        assertThat(foundExercise.get().getName()).isEqualTo("name2");
        assertThat(foundExercise.get().getDescription()).isEqualTo("description2");
        assertThat(foundExercise.get().getImgUrl()).isEqualTo("imgurl2");

        exerciseRepository.deleteById(foundExercise.get().getId());
    }

    @Test
    public void shouldFindAllExercisesOfUser() {
        User user1 = new User();
        user1.setUsername("user1");
        User user2 = new User();
        user2.setUsername("user2");
        userRepository.save(user1);
        userRepository.save(user2);

        Exercise exercise1 = new Exercise();
        exercise1.setName("exercise1");
        exerciseRepository.save(exercise1);

        Exercise exercise2 = new Exercise();
        exercise2.setName("exercise2");
        exercise2.setCreatedBy(user1);
        exerciseRepository.save(exercise2);

        Exercise exercise3 = new Exercise();
        exercise3.setName("exercise3");
        exercise3.setCreatedBy(user2);
        exerciseRepository.save(exercise3);

        List<Exercise> exercisesUser1 = exerciseRepository.findByCreatedByIsNullOrCreatedBy(user1);
        assertThat(exercisesUser1).isNotEmpty();
        assertThat(exercisesUser1.stream().map(Exercise::getName)).containsExactlyInAnyOrder(exercise1.getName(), exercise2.getName());

        userRepository.delete(user1);
        userRepository.delete(user2);
        exerciseRepository.delete(exercise1);
        exerciseRepository.delete(exercise2);
        exerciseRepository.delete(exercise3);
    }

    @Test
    public void shouldFindAllExercisesOfUserByName() {
        User user1 = new User();
        user1.setUsername("user1");
        User user2 = new User();
        user2.setUsername("user2");
        userRepository.save(user1);
        userRepository.save(user2);

        Exercise exercise1 = new Exercise();
        exercise1.setName("exercise1");
        exerciseRepository.save(exercise1);

        Exercise exercise2 = new Exercise();
        exercise2.setName("exercise1");
        exercise2.setCreatedBy(user1);
        exerciseRepository.save(exercise2);

        Exercise exercise3 = new Exercise();
        exercise3.setName("exercise2");
        exercise3.setCreatedBy(user1);
        exerciseRepository.save(exercise3);

        Exercise exercise4 = new Exercise();
        exercise4.setName("exercise2");
        exercise4.setCreatedBy(user2);
        exerciseRepository.save(exercise4);

        List<Exercise> exercisesUser1 = exerciseRepository.findByNameContainsAndCreatedByIsNullOrCreatedBy("exercise1", user1);
        assertThat(exercisesUser1).isNotEmpty();
        assertThat(exercisesUser1.stream().map(Exercise::getName)).containsExactlyInAnyOrder("exercise1", "exercise1");

        exercisesUser1 = exerciseRepository.findByNameContainsAndCreatedByIsNullOrCreatedBy("exercise2", user1);
        assertThat(exercisesUser1).isNotEmpty();
        assertThat(exercisesUser1.stream().map(Exercise::getName)).containsExactlyInAnyOrder("exercise2");

        userRepository.delete(user1);
        userRepository.delete(user2);
        exerciseRepository.delete(exercise1);
        exerciseRepository.delete(exercise2);
        exerciseRepository.delete(exercise3);
        exerciseRepository.delete(exercise4);
    }

    @Test
    public void shouldFindAllExercisesOfUserByType() {
        User user1 = new User();
        user1.setUsername("user1");
        User user2 = new User();
        user2.setUsername("user2");
        userRepository.save(user1);
        userRepository.save(user2);

        ExerciseType exerciseType1 = new ExerciseType();
        exerciseType1.setName("exerciseType1");
        ExerciseType exerciseType2 = new ExerciseType();
        exerciseType2.setName("exerciseType2");
        exerciseTypeRepository.save(exerciseType1);
        exerciseTypeRepository.save(exerciseType2);

        Exercise exercise1 = new Exercise();
        exercise1.setName("exerciseType1");
        exercise1.setType(exerciseType1);
        exerciseRepository.save(exercise1);

        Exercise exercise2 = new Exercise();
        exercise2.setName("exerciseType1");
        exercise2.setCreatedBy(user1);
        exercise2.setType(exerciseType1);
        exerciseRepository.save(exercise2);

        Exercise exercise3 = new Exercise();
        exercise3.setName("exerciseType2");
        exercise3.setCreatedBy(user1);
        exercise3.setType(exerciseType2);
        exerciseRepository.save(exercise3);

        Exercise exercise4 = new Exercise();
        exercise4.setName("exerciseType1");
        exercise4.setCreatedBy(user2);
        exercise4.setType(exerciseType1);
        exerciseRepository.save(exercise4);

        List<Exercise> exercisesUser1 = exerciseRepository.findByTypeAndCreatedByIsNullOrCreatedBy(exerciseType1, user1);
        assertThat(exercisesUser1).isNotEmpty();
        assertThat(exercisesUser1.stream().map(Exercise::getName)).containsExactlyInAnyOrder("exerciseType1", "exerciseType1");

        exercisesUser1 = exerciseRepository.findByTypeAndCreatedByIsNullOrCreatedBy(exerciseType2, user1);
        assertThat(exercisesUser1).isNotEmpty();
        assertThat(exercisesUser1.stream().map(Exercise::getName)).containsExactlyInAnyOrder("exerciseType2");

        userRepository.delete(user1);
        userRepository.delete(user2);
        exerciseTypeRepository.delete(exerciseType1);
        exerciseTypeRepository.delete(exerciseType2);
        exerciseRepository.delete(exercise1);
        exerciseRepository.delete(exercise2);
        exerciseRepository.delete(exercise3);
        exerciseRepository.delete(exercise4);
    }

}
