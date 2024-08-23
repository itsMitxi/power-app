package com.example.powerbff.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "exercises")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;
    @Column(nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "type")
    private ExerciseType type;
    private String description;
    private String imgUrl;

    @ManyToMany
    @JoinTable(
            name = "exercise_muscles",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "muscle_id")
    )
    private Set<Muscle> muscles;

}
