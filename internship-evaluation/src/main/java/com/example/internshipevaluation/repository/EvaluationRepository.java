package com.example.internshipevaluation.repository;

import com.example.internshipevaluation.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    // Add custom query methods if needed
}