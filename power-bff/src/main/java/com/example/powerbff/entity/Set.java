package com.example.powerbff.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "sets")
public class Set {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "workout_id")
    private Workout workout;
    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;
    @Column(nullable = false)
    private int setNumber;
    @ManyToOne
    @JoinColumn(name = "set_type_id")
    private SetType setType;
    private int repetitions;
    @Column(precision = 5, scale = 2)
    private BigDecimal weight;

}
