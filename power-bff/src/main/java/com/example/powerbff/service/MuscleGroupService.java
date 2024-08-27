package com.example.powerbff.service;

import com.example.powerbff.entity.MuscleGroup;
import com.example.powerbff.exception.muscle.*;
import com.example.powerbff.repository.MuscleGroupRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
public class MuscleGroupService {

    @Autowired
    private MuscleGroupRepository muscleGroupRepository;

    private String generateRandomColor() {
        Random random = new Random();
        String color;

        do {
            int r = random.nextInt(256);
            int g = random.nextInt(256);
            int b = random.nextInt(256);

            color = String.format("#%02x%02x%02x", r, g, b);
        } while (muscleGroupRepository.existsByColor(color));

        return color;
    }

    public MuscleGroup createMuscleGroup(MuscleGroup muscleGroup) throws NameAlreadyExistsException, ColorAlreadyInUseException, BlankNameException {
        if (muscleGroup.getName() == null || muscleGroup.getName().isEmpty()) {
            throw new BlankNameException();
        } else if (muscleGroupRepository.existsByName(muscleGroup.getName())) {
            throw new NameAlreadyExistsException(muscleGroup.getName());
        }
        if (!Objects.equals(muscleGroup.getColor(), null) && !muscleGroup.getColor().isEmpty()) {
            if (muscleGroup.getColor().length() > 7) {
                throw new ColorTooLongException(muscleGroup.getColor());
            } else if (muscleGroupRepository.existsByColor(muscleGroup.getColor())) {
                throw new ColorAlreadyInUseException(muscleGroup.getColor());
            }
        } else {
            muscleGroup.setColor(this.generateRandomColor());
        }
        return muscleGroupRepository.save(muscleGroup);
    }

    public List<MuscleGroup> getAllMuscleGroups() {
        return muscleGroupRepository.findAll();
    }

    public Optional<MuscleGroup> getMuscleGroupById(long id) {
        return muscleGroupRepository.findById(id);
    }

    public Optional<MuscleGroup> getMuscleGroupByName(String name) {
        return muscleGroupRepository.findByName(name);
    }

    @Transactional
    public MuscleGroup updateMuscleGroup(Long id, MuscleGroup muscleGroupDetails) throws MuscleGroupNotFoundException, NameAlreadyExistsException, ColorAlreadyInUseException, ColorTooLongException {
        MuscleGroup muscleGroup = muscleGroupRepository.findById(id).orElseThrow(() -> new MuscleGroupNotFoundException(id));

        if (!Objects.equals(muscleGroupDetails.getName(), "") && !Objects.equals(muscleGroupDetails.getName(), null)) {
            if (this.getMuscleGroupByName(muscleGroupDetails.getName()).isPresent()) {
                throw new NameAlreadyExistsException(muscleGroup.getName());
            }
            muscleGroup.setName(muscleGroupDetails.getName());
        } else if (!Objects.equals(muscleGroupDetails.getColor(), "") && !Objects.equals(muscleGroupDetails.getColor(), null)) {
            if (muscleGroupDetails.getColor().length() > 7) {
                throw new ColorTooLongException(muscleGroupDetails.getColor());
            } else if (this.muscleGroupRepository.existsByColor(muscleGroupDetails.getColor())) {
                throw new ColorAlreadyInUseException(muscleGroupDetails.getColor());
            }
            muscleGroup.setColor(muscleGroupDetails.getColor());
        }

        return muscleGroupRepository.save(muscleGroup);
    }

    public void deleteMuscleGroup(long id) {
        muscleGroupRepository.deleteById(id);
    }

}
