package com.example.internshipevaluation.controller;

import com.example.internshipevaluation.entity.Tutor;
import com.example.internshipevaluation.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tutors")
public class TutorController {

    @Autowired
    private TutorRepository tutorRepository;

    // Get all tutors
    @GetMapping
    public List<Tutor> getAllTutors() {
        return tutorRepository.findAll();
    }

    // Get tutor by ID
    @GetMapping("/{id}")
    public ResponseEntity<Tutor> getTutorById(@PathVariable Long id) {
        Optional<Tutor> tutor = tutorRepository.findById(id);
        return tutor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new tutor
    @PostMapping
    public Tutor createTutor(@RequestBody Tutor tutor) {
        return tutorRepository.save(tutor);
    }

    // Update an existing tutor
    @PutMapping("/{id}")
    public ResponseEntity<Tutor> updateTutor(@PathVariable Long id, @RequestBody Tutor updatedTutor) {
        Optional<Tutor> existingTutor = tutorRepository.findById(id);
        if (existingTutor.isPresent()) {
            Tutor tutor = existingTutor.get();
            tutor.setFirstName(updatedTutor.getFirstName());
            tutor.setLastName(updatedTutor.getLastName());
            tutor.setCompany(updatedTutor.getCompany());
            return ResponseEntity.ok(tutorRepository.save(tutor));
        }
        return ResponseEntity.notFound().build();
    }

    // Delete a tutor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTutor(@PathVariable Long id) {
        if (tutorRepository.existsById(id)) {
            tutorRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}