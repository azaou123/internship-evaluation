package com.example.internshipevaluation.repository;

import com.example.internshipevaluation.entity.Intern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternRepository extends JpaRepository<Intern, Long> {
    // Add custom query methods if needed
}