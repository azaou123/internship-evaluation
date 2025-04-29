package com.example.internshipevaluation.controller;

import com.example.internshipevaluation.entity.Intern;
import com.example.internshipevaluation.repository.InternRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/interns")
public class InternController {

    @Autowired
    private InternRepository internRepository;

    // Get all interns
    @GetMapping
    public List<Intern> getAllInterns() {
        return internRepository.findAll();
    }

    // Get intern by ID
    @GetMapping("/{id}")
    public ResponseEntity<Intern> getInternById(@PathVariable Long id) {
        Optional<Intern> intern = internRepository.findById(id);
        return intern.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new intern
    @PostMapping
    public Intern createIntern(@RequestBody Intern intern) {
        return internRepository.save(intern);
    }

    // Update an existing intern
    @PutMapping("/{id}")
    public ResponseEntity<Intern> updateIntern(@PathVariable Long id, @RequestBody Intern updatedIntern) {
        Optional<Intern> existingIntern = internRepository.findById(id);
        if (existingIntern.isPresent()) {
            Intern intern = existingIntern.get();
            intern.setFirstName(updatedIntern.getFirstName());
            intern.setLastName(updatedIntern.getLastName());
            intern.setEmail(updatedIntern.getEmail());
            return ResponseEntity.ok(internRepository.save(intern));
        }
        return ResponseEntity.notFound().build();
    }

    // Delete an intern
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIntern(@PathVariable Long id) {
        if (internRepository.existsById(id)) {
            internRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}