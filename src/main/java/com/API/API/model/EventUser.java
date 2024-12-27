package com.API.API.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(
        name = "event_users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"eventId", "customerId"}) // Đảm bảo EventID và CustomerID là duy nhất
        }
)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EventUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eventId", nullable = false, foreignKey = @ForeignKey(name = "fk_event_user_event"))
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false, foreignKey = @ForeignKey(name = "fk_event_user_user"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId", foreignKey = @ForeignKey(name = "fk_event_user_customer"))
    private Customer customer;

    // Getters and Setters
    public Integer getEventUserId() {
        return eventUserId;
    }

    public void setEventUserId(Integer eventUserId) {
        this.eventUserId = eventUserId;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
