package com.API.API.repository;

import com.API.API.model.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventTypeRepository extends JpaRepository<EventType, Integer> {
}
