package com.example.internshipevaluation.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "competencies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Competency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "evaluation_id", nullable = false)
    private Evaluation evaluation;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CompetencyLevel level;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CompetencyType type;

    private Integer score; // For company or scientific/technical competencies (out of 20)

    public enum CompetencyLevel {
        NA, BEGINNER, AUTONOMOUS, AUTONOMOUS_PLUS
    }

    public enum CompetencyType {
        INDIVIDUAL, COMPANY, SCIENTIFIC_TECHNICAL, JOB_SPECIFIC
    }
}