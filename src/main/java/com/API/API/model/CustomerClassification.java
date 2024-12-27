package com.API.API.model;

import jakarta.persistence.*;

@Entity
@Table(name = "customer_classification")
public class CustomerClassification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer classificationId; // Matches the referencedColumnName in Customer entity

    @Column(nullable = false)
    private String classificationName;

    // Getters and Setters
    public Integer getClassificationId() {
        return classificationId;
    }

    public void setClassificationId(Integer classificationId) {
        this.classificationId = classificationId;
    }

    public String getClassificationName() {
        return classificationName;
    }

    public void setClassificationName(String classificationName) {
        this.classificationName = classificationName;
    }
}
