package com.example.internshipevaluation.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "evaluations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "internship_id", nullable = false)
    private Internship internship;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ImplicationLevel implication;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OpennessLevel openness;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QualityLevel quality;

    @Column(length = 5000)
    private String observations;

    public enum ImplicationLevel {
        LAZY, JUST_ENOUGH, GOOD, VERY_STRONG, EXCEEDS_OBJECTIVES
    }

    public enum OpennessLevel {
        ISOLATED, CLOSED, GOOD, VERY_GOOD, EXCELLENT
    }

    public enum QualityLevel {
        POOR, ACCEPTABLE, GOOD, VERY_GOOD, HIGHLY_PROFESSIONAL
    }
}