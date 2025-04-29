package com.example.internshipevaluation.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "interns")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Intern {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true)
    private String email;
}