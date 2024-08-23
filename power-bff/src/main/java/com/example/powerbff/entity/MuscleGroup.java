package com.example.powerbff.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "muscle_groups")
public class MuscleGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(length = 7, nullable = false)
    private String color;

}
