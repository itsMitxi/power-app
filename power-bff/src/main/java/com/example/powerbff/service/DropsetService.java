package com.example.powerbff.service;

import com.example.powerbff.entity.Dropset;
import com.example.powerbff.entity.Set;
import com.example.powerbff.exception.set.DropsetNotFoundException;
import com.example.powerbff.exception.set.SetIsNotADropsetException;
import com.example.powerbff.exception.set.SetNotFoundException;
import com.example.powerbff.repository.DropsetRepository;
import com.example.powerbff.repository.SetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DropsetService {

    @Autowired
    private DropsetRepository dropsetRepository;
    @Autowired
    private SetRepository setRepository;

    public Dropset createDropset(Dropset dropset) throws SetNotFoundException, SetIsNotADropsetException {
        Set set = setRepository.findById(dropset.getSet().getId()).orElseThrow(() -> new SetNotFoundException(dropset.getSet().getId()));
        if (!Objects.equals(set.getSetType().getName(), "Dropset")) throw new SetIsNotADropsetException(set.getId());
        dropset.setSet(set);

        return dropsetRepository.save(dropset);
    }

    public List<Dropset> getAllDropsets() {
        return dropsetRepository.findAll();
    }

    public Optional<Dropset> getDropsetById(long id) {
        return dropsetRepository.findById(id);
    }

    public List<Dropset> getDropsetsBySet(long id) throws SetNotFoundException {
        Set set = setRepository.findById(id).orElseThrow(() -> new SetNotFoundException(id));
        return dropsetRepository.findBySet(set);
    }

    public Dropset updateDropset(long id, Dropset dropsetDetails) throws DropsetNotFoundException {
        Dropset dropset = dropsetRepository.findById(id).orElseThrow(() -> new DropsetNotFoundException(id));

        dropset.setRepetitions(dropsetDetails.getRepetitions());
        dropset.setWeight(dropsetDetails.getWeight());

        return dropsetRepository.save(dropset);
    }

    public void deleteDropset(long id) {
        dropsetRepository.deleteById(id);
    }

}
