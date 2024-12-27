package com.API.API.repository;

import com.API.API.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findDistinctByAssignedUsersUserId(Integer userId);
}
