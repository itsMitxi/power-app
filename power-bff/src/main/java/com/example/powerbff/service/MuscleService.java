package com.example.powerbff.service;

import com.example.powerbff.entity.Muscle;
import com.example.powerbff.exception.muscle.*;
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

    public Muscle createMuscle(Muscle muscle) throws NameAlreadyExistsException, BlankNameException {
        if (muscle.getName() == null || muscle.getName().isEmpty()) {
            throw new BlankNameException();
        } else if (muscleRepository.existsByName(muscle.getName())) {
            throw new NameAlreadyExistsException(muscle.getName());
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

    @Transactional
    public Muscle updateMuscle(long id, Muscle muscleDetails) throws MuscleNotFoundException, NameAlreadyExistsException {
        Muscle muscle = muscleRepository.findById(id).orElseThrow(() -> new MuscleNotFoundException(id));

        if (!Objects.equals(muscleDetails.getName(), "") && !Objects.equals(muscleDetails.getName(), null)) {
            if (this.getMuscleByName(muscleDetails.getName()).isPresent()) {
                throw new NameAlreadyExistsException(muscle.getName());
            }
            muscle.setName(muscleDetails.getName());
        } else if (!Objects.equals(muscleDetails.getMuscleGroup(), null)) {
            muscle.setMuscleGroup(muscleDetails.getMuscleGroup());
        }

        return muscleRepository.save(muscle);
    }

    public void deleteMuscle(long id) {
        muscleRepository.deleteById(id);
    }

}
