package com.example.internshipevaluation.repository;

import com.example.internshipevaluation.entity.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {
    // Add custom query methods if needed
}