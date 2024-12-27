package com.API.API.model;

import jakarta.persistence.*;

@Entity
@Table(name = "event_type")
public class EventType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventTypeId;

    @Column(nullable = false)
    private String eventTypeName;

    // Getters and Setters
    public Integer getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(Integer eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public String getEventTypeName() {
        return eventTypeName;
    }

    public void setEventTypeName(String eventTypeName) {
        this.eventTypeName = eventTypeName;
    }
}
