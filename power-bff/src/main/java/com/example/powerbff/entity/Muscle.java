package com.example.powerbff.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "muscles")
public class Muscle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    private String name;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private MuscleGroup group;

}

