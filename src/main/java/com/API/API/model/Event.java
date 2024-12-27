package com.API.API.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "event")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "eventTypeId", referencedColumnName = "eventTypeId", foreignKey = @ForeignKey(name = "fk_event_eventType"))
    private EventType eventType; // Event type

    @Column(nullable = false)
    private LocalDate eventDate; // Event date

    @Column(columnDefinition = "TEXT")
    private String description; // Event description

    private LocalDate reminderDate; // Reminder date

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean reminderSent = false; // Reminder sent status

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('PLANNED', 'COMPLETED', 'CANCELED') DEFAULT 'PLANNED'")
    private EventStatus status = EventStatus.PLANNED; // Event status

    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // Created at

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now(); // Updated at

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "event_users",
            joinColumns = @JoinColumn(name = "EventID"),
            inverseJoinColumns = @JoinColumn(name = "UserID")
    )
    private List<User> assignedUsers; // List of assigned users

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(LocalDate reminderDate) {
        this.reminderDate = reminderDate;
    }

    public Boolean getReminderSent() {
        return reminderSent;
    }

    public void setReminderSent(Boolean reminderSent) {
        this.reminderSent = reminderSent;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<User> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(List<User> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    // Enum for Event Status
    public enum EventStatus {
        PLANNED,
        COMPLETED,
        CANCELED
    }
}
