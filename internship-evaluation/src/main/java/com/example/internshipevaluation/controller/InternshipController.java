package com.example.internshipevaluation.controller;

import com.example.internshipevaluation.entity.Internship;
import com.example.internshipevaluation.repository.InternshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/internships")
public class InternshipController {

    @Autowired
    private InternshipRepository internshipRepository;

    // Get all internships
    @GetMapping
    public List<Internship> getAllInternships() {
        return internshipRepository.findAll();
    }

    // Get internship by ID
    @GetMapping("/{id}")
    public ResponseEntity<Internship> getInternshipById(@PathVariable Long id) {
        Optional<Internship> internship = internshipRepository.findById(id);
        return internship.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new internship
    @PostMapping
    public Internship createInternship(@RequestBody Internship internship) {
        return internshipRepository.save(internship);
    }

    // Update an existing internship
    @PutMapping("/{id}")
    public ResponseEntity<Internship> updateInternship(@PathVariable Long id, @RequestBody Internship updatedInternship) {
        Optional<Internship> existingInternship = internshipRepository.findById(id);
        if (existingInternship.isPresent()) {
            Internship internship = existingInternship.get();
            internship.setIntern(updatedInternship.getIntern());
            internship.setCompany(updatedInternship.getCompany());
            internship.setTutor(updatedInternship.getTutor());
            internship.setStartDate(updatedInternship.getStartDate());
            internship.setEndDate(updatedInternship.getEndDate());
            internship.setProjectTheme(updatedInternship.getProjectTheme());
            internship.setObjectives(updatedInternship.getObjectives());
            return ResponseEntity.ok(internshipRepository.save(internship));
        }
        return ResponseEntity.notFound().build();
    }

    // Delete an internship
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInternship(@PathVariable Long id) {
        if (internshipRepository.existsById(id)) {
            internshipRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}