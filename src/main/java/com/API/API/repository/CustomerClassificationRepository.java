package com.API.API.repository;

import com.API.API.model.CustomerClassification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerClassificationRepository extends JpaRepository<CustomerClassification, Integer> {
}
