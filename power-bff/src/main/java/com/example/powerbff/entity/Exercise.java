package com.example.powerbff.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "exercises")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    private String name;
    private String description;
    private String imgUrl;

}
