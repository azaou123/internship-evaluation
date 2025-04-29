package com.example.internshipevaluation.repository;

import com.example.internshipevaluation.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    // Add custom query methods if needed
}
// No additional code needed here for CompanyRepository