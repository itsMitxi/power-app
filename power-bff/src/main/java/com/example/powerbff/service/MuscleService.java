package com.example.powerbff.service;

import com.example.powerbff.entity.Muscle;
import com.example.powerbff.entity.MuscleGroup;
import com.example.powerbff.exception.muscle.*;
import com.example.powerbff.repository.MuscleGroupRepository;
import com.example.powerbff.repository.MuscleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MuscleService {

    @Autowired
    private MuscleRepository muscleRepository;
    @Autowired
    private MuscleGroupRepository muscleGroupRepository;

    public Muscle createMuscle(Muscle muscle) throws NameAlreadyExistsException, BlankNameException, MuscleGroupNotFoundException {
        if (muscle.getName() == null || muscle.getName().isEmpty()) {
            throw new BlankNameException();
        } else if (muscleRepository.existsByName(muscle.getName())) {
            throw new NameAlreadyExistsException(muscle.getName());
        } else if (!Objects.equals(muscle.getMuscleGroup(), null)) {
            MuscleGroup muscleGroup = muscleGroupRepository.findByName(muscle.getMuscleGroup().getName()).orElseThrow(() -> new MuscleGroupNotFoundException(muscle.getMuscleGroup().getName()));
            muscle.setMuscleGroup(muscleGroup);
        }
        return muscleRepository.save(muscle);
    }

    public List<Muscle> getAllMuscles() {
        return muscleRepository.findAll();
    }

    public Optional<Muscle> getMuscleById(long id) {
        return muscleRepository.findById(id);
    }

    public Optional<Muscle> getMuscleByName(String name) {
        return muscleRepository.findByName(name);
    }

    public List<Muscle> getMusclesByMuscleGroup(String muscleGroupName) throws MuscleGroupNotFoundException {
        MuscleGroup muscleGroup = muscleGroupRepository.findByName(muscleGroupName).orElseThrow(() -> new MuscleGroupNotFoundException(muscleGroupName));
        return muscleRepository.findByMuscleGroup(muscleGroup);
    }

    @Transactional
    public Muscle updateMuscle(long id, Muscle muscleDetails) throws MuscleNotFoundException, NameAlreadyExistsException, MuscleGroupNotFoundException {
        Muscle muscle = muscleRepository.findById(id).orElseThrow(() -> new MuscleNotFoundException(id));

        if (!Objects.equals(muscleDetails.getName(), "") && !Objects.equals(muscleDetails.getName(), null)) {
            if (this.getMuscleByName(muscleDetails.getName()).isPresent()) {
                throw new NameAlreadyExistsException(muscle.getName());
            }
            muscle.setName(muscleDetails.getName());
        }
        if (!Objects.equals(muscleDetails.getMuscleGroup(), null)) {
            MuscleGroup muscleGroup = muscleGroupRepository.findByName(muscleDetails.getMuscleGroup().getName()).orElseThrow(() -> new MuscleGroupNotFoundException(muscleDetails.getMuscleGroup().getName()));
            muscle.setMuscleGroup(muscleGroup);
        }

        return muscleRepository.save(muscle);
    }

    public void deleteMuscle(long id) {
        muscleRepository.deleteById(id);
    }

}
