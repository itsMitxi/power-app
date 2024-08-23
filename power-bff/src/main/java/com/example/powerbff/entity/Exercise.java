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
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;
    @Column(nullable = false)
    private String name;
    private String description;
    private String imgUrl;

}
