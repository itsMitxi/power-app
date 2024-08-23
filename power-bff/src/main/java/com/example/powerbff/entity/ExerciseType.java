package com.example.powerbff.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "exercise_types")
public class ExerciseType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false)
    private String name;

}
