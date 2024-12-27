package com.API.API.controller;

import com.API.API.model.CustomerClassification;
import com.API.API.service.CustomerClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/classifications")
public class CustomerClassificationController {

    @Autowired
    private CustomerClassificationService classificationService;

    // GET: /api/classifications
    @GetMapping
    public List<CustomerClassification> getAllClassifications() {
        return classificationService.getAllClassifications();
    }

    // GET: /api/classifications/{id}
    @GetMapping("/{id}")
    public ResponseEntity<CustomerClassification> getClassificationById(@PathVariable Integer id) {
        Optional<CustomerClassification> classification = classificationService.getClassificationById(id);
        return classification.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // POST: /api/classifications
    @PostMapping
    public CustomerClassification createClassification(@RequestBody CustomerClassification classification) {
        return classificationService.createClassification(classification);
    }

    // PUT: /api/classifications/{id}
    @PutMapping("/{id}")
    public ResponseEntity<CustomerClassification> updateClassification(
            @PathVariable Integer id,
            @RequestBody CustomerClassification classification) {
        try {
            CustomerClassification updatedClassification = classificationService.updateClassification(id, classification);
            return ResponseEntity.ok(updatedClassification);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: /api/classifications/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassification(@PathVariable Integer id) {
        classificationService.deleteClassification(id);
        return ResponseEntity.noContent().build();
    }
}
