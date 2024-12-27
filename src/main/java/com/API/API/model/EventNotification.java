package com.API.API.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "event_notifications")
public class EventNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer notificationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eventUserID", nullable = false, foreignKey = @ForeignKey(name = "fk_notification_event_user"))
    private EventUser eventUser;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Method method;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.Pending;

    @Column(nullable = true) // Allow null
    private LocalDateTime sentAt = LocalDateTime.now();

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(nullable = true) // Allow null (attachment path is optional)
    private String attachmentPath;  // Renamed field

    public enum Method {
        Email, SMS, PhoneCall
    }

    public enum Status {
        Success, Failed, Pending
    }

    // Getters and Setters
    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public EventUser getEventUser() {
        return eventUser;
    }

    public void setEventUser(EventUser eventUser) {
        this.eventUser = eventUser;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }
}
