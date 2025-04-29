package com.example.internshipevaluation.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tutors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tutor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
}