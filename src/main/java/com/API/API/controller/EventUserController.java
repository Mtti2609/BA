package com.API.API.controller;

import com.API.API.model.Customer;
import com.API.API.model.EventUser;
import com.API.API.service.EventUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventUserController {

    @Autowired
    private EventUserService eventUserService;

    /**
     * Gán người phụ trách và khách hàng vào sự kiện.
     */
    @PostMapping("/{eventId}/users/{userId}/customers/{customerId}")
    public ResponseEntity<?> assignUserAndCustomerToEvent(
            @PathVariable Integer eventId,
            @PathVariable Integer userId,
            @PathVariable Integer customerId) {
        try {
            EventUser eventUser = eventUserService.assignUserAndCustomerToEvent(eventId, userId, customerId);
            return ResponseEntity.ok(eventUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Đã xảy ra lỗi khi gán người phụ trách và khách hàng.");
        }
    }

    /**
     * Lấy danh sách người phụ trách và khách hàng đã được gán cho sự kiện.
     */
    @GetMapping("/{eventId}/users")
    public ResponseEntity<List<EventUser>> getEventUsersByEventId(@PathVariable Integer eventId) {
        List<EventUser> eventUsers = eventUserService.getEventUsersByEventId(eventId);
        return ResponseEntity.ok(eventUsers);
    }

    @GetMapping("/{eventId}/available-customers")
    public ResponseEntity<List<Customer>> getAvailableCustomersForEvent(@PathVariable Integer eventId) {
        try {
            List<Customer> availableCustomers = eventUserService.getAvailableCustomersForEvent(eventId);
            return ResponseEntity.ok(availableCustomers);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

}
