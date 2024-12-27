package com.API.API.repository;

import com.API.API.model.EventNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventNotificationRepository extends JpaRepository<EventNotification, Integer> {

    @Query("SELECT n.eventUser.eventUserId FROM EventNotification n")
    List<Integer> findAllEventUserIdsWithNotifications();
}
