package com.example.internshipevaluation.controller;

import com.example.internshipevaluation.entity.Evaluation;
import com.example.internshipevaluation.repository.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/evaluations")
public class EvaluationController {

    @Autowired
    private EvaluationRepository evaluationRepository;

    // Get all evaluations
    @GetMapping
    public List<Evaluation> getAllEvaluations() {
        return evaluationRepository.findAll();
    }

    // Get evaluation by ID
    @GetMapping("/{id}")
    public ResponseEntity<Evaluation> getEvaluationById(@PathVariable Long id) {
        Optional<Evaluation> evaluation = evaluationRepository.findById(id);
        return evaluation.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new evaluation
    @PostMapping
    public Evaluation createEvaluation(@RequestBody Evaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }

    // Update an existing evaluation
    @PutMapping("/{id}")
    public ResponseEntity<Evaluation> updateEvaluation(@PathVariable Long id, @RequestBody Evaluation updatedEvaluation) {
        Optional<Evaluation> existingEvaluation = evaluationRepository.findById(id);
        if (existingEvaluation.isPresent()) {
            Evaluation evaluation = existingEvaluation.get();
            evaluation.setInternship(updatedEvaluation.getInternship());
            evaluation.setImplication(updatedEvaluation.getImplication());
            evaluation.setOpenness(updatedEvaluation.getOpenness());
            evaluation.setQuality(updatedEvaluation.getQuality());
            evaluation.setObservations(updatedEvaluation.getObservations());
            return ResponseEntity.ok(evaluationRepository.save(evaluation));
        }
        return ResponseEntity.notFound().build();
    }

    // Delete an evaluation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvaluation(@PathVariable Long id) {
        if (evaluationRepository.existsById(id)) {
            evaluationRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}