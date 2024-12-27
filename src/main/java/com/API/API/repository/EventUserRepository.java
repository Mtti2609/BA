package com.API.API.repository;

import com.API.API.model.EventUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventUserRepository extends JpaRepository<EventUser, Integer> {
    // Lấy danh sách người phụ trách và khách hàng theo EventID
    @Query("SELECT e FROM EventUser e WHERE e.event.eventId = :eventId")
    List<EventUser> findByEventId(@Param("eventId") Integer eventId);

    @Query("SELECT e.eventUserId FROM EventUser e")
    List<Integer> findAllEventUserIds();


    @Query("SELECT eu.customer.customerId FROM EventUser eu WHERE eu.event.eventId = :eventId")
    List<Integer> findCustomerIdsByEventId(@Param("eventId") Integer eventId);
}
