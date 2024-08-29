package com.example.powerbff.service;

import com.example.powerbff.entity.SetType;
import com.example.powerbff.exception.set.*;
import com.example.powerbff.repository.SetTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SetTypeService {

    @Autowired
    private SetTypeRepository setTypeRepository;

    public SetType createSetType(SetType setType) throws BlankNameException, NameAlreadyExistsException {
        if (setType.getName() == null || setType.getName().isEmpty()) {
            throw new BlankNameException();
        } else if (setTypeRepository.existsByName(setType.getName())) {
            throw new NameAlreadyExistsException(setType.getName());
        }

        return setTypeRepository.save(setType);
    }

    public List<SetType> getAllSetTypes() {
        return setTypeRepository.findAll();
    }

    public Optional<SetType> getSetTypeById(long id) {
        return setTypeRepository.findById(id);
    }

    public Optional<SetType> getSetTypeByName(String name) {
        return setTypeRepository.findByName(name);
    }

    @Transactional
    public SetType updateSetType(long id, SetType setTypeDetails) throws SetTypeNotFoundException, NameAlreadyExistsException {
        SetType setType = setTypeRepository.findById(id).orElseThrow(() -> new SetTypeNotFoundException(id));

        if (!Objects.equals(setTypeDetails.getName(), "") && !Objects.equals(setTypeDetails.getName(), null)) {
            if (this.getSetTypeByName(setTypeDetails.getName()).isPresent()) {
                throw new NameAlreadyExistsException(setType.getName());
            }
            setType.setName(setTypeDetails.getName());
        }

        return setTypeRepository.save(setType);
    }

    public void deleteSetType(long id) {
        setTypeRepository.deleteById(id);
    }

}
