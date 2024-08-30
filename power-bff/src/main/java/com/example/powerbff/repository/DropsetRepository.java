package com.example.powerbff.repository;

import com.example.powerbff.entity.Dropset;
import com.example.powerbff.entity.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DropsetRepository extends JpaRepository<Dropset, Long> {

    List<Dropset> findBySet(Set set);

}
