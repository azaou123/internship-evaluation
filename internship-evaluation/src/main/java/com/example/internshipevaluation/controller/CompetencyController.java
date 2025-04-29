package com.example.internshipevaluation.controller;

import com.example.internshipevaluation.entity.Competency;
import com.example.internshipevaluation.repository.CompetencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/competencies")
public class CompetencyController {

    @Autowired
    private CompetencyRepository competencyRepository;

    // Get all competencies
    @GetMapping
    public List<Competency> getAllCompetencies() {
        return competencyRepository.findAll();
    }

    // Get competency by ID
    @GetMapping("/{id}")
    public ResponseEntity<Competency> getCompetencyById(@PathVariable Long id) {
        Optional<Competency> competency = competencyRepository.findById(id);
        return competency.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new competency
    @PostMapping
    public Competency createCompetency(@RequestBody Competency competency) {
        return competencyRepository.save(competency);
    }

    // Update an existing competency
    @PutMapping("/{id}")
    public ResponseEntity<Competency> updateCompetency(@PathVariable Long id, @RequestBody Competency updatedCompetency) {
        Optional<Competency> existingCompetency = competencyRepository.findById(id);
        if (existingCompetency.isPresent()) {
            Competency competency = existingCompetency.get();
            competency.setEvaluation(updatedCompetency.getEvaluation());
            competency.setName(updatedCompetency.getName());
            competency.setLevel(updatedCompetency.getLevel());
            competency.setType(updatedCompetency.getType());
            competency.setScore(updatedCompetency.getScore());
            return ResponseEntity.ok(competencyRepository.save(competency));
        }
        return ResponseEntity.notFound().build();
    }

    // Delete a competency
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompetency(@PathVariable Long id) {
        if (competencyRepository.existsById(id)) {
            competencyRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}