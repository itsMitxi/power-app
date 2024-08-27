package com.example.powerbff.service;

import com.example.powerbff.entity.ExerciseType;
import com.example.powerbff.exception.exercise.*;
import com.example.powerbff.repository.ExerciseTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ExerciseTypeService {

    @Autowired
    private ExerciseTypeRepository exerciseTypeRepository;

    public ExerciseType createExerciseType(ExerciseType exerciseType) throws NameAlreadyExistsException, BlankNameException {
        if (exerciseType.getName() == null || exerciseType.getName().isEmpty()) {
            throw new BlankNameException();
        } else if (exerciseTypeRepository.existsByName(exerciseType.getName())) {
            throw new NameAlreadyExistsException(exerciseType.getName());
        }
        return exerciseTypeRepository.save(exerciseType);
    }

    public List<ExerciseType> getAllExerciseTypes() {
        return exerciseTypeRepository.findAll();
    }

    public Optional<ExerciseType> getExerciseTypeById(Long id) {
        return exerciseTypeRepository.findById(id);
    }

    public Optional<ExerciseType> getExerciseTypeByName(String name) {
        return exerciseTypeRepository.findByName(name);
    }

    @Transactional
    public ExerciseType updateExerciseType(Long id, ExerciseType exerciseTypeDetails) throws ExerciseTypeNotFoundException, NameAlreadyExistsException {
        ExerciseType exerciseType = exerciseTypeRepository.findById(id).orElseThrow(() -> new ExerciseTypeNotFoundException(id));

        if (!Objects.equals(exerciseTypeDetails.getName(), "") && !Objects.equals(exerciseTypeDetails.getName(), null)) {
            if (this.getExerciseTypeByName(exerciseTypeDetails.getName()).isPresent()) {
                throw new NameAlreadyExistsException(exerciseType.getName());
            }
            exerciseType.setName(exerciseTypeDetails.getName());
        }

        return exerciseTypeRepository.save(exerciseType);
    }

    public void deleteExercise(Long id) {
        exerciseTypeRepository.deleteById(id);
    }

}
