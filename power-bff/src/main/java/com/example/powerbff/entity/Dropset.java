package com.example.powerbff.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "dropsets")
public class Dropset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "set_id")
    private Set set;
    private int repetitions;
    @Column(precision = 5, scale = 2)
    private BigDecimal weight;

}
