package com.example.internshipevaluation.repository;

import com.example.internshipevaluation.entity.Competency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetencyRepository extends JpaRepository<Competency, Long> {
    // Add custom query methods if needed
}