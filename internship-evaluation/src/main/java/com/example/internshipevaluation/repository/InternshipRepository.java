package com.example.internshipevaluation.repository;

import com.example.internshipevaluation.entity.Internship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternshipRepository extends JpaRepository<Internship, Long> {
    // Add custom query methods if needed
}