package com.example.powerbff.repository;

import com.example.powerbff.entity.SetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SetTypeRepository extends JpaRepository<SetType, Long> {

    Boolean existsByName(String name);

    Optional<SetType> findByName(String name);

}
