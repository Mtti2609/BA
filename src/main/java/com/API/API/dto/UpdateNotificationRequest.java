package com.API.API.dto;

import java.time.LocalDateTime;
import java.util.List;

public class UpdateNotificationRequest {
    private String message;
    private LocalDateTime sentAt;
    private List<String> attachmentPaths;  // Renamed field to hold list of attachment paths (can be images or other files)

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public List<String> getAttachmentPaths() {
        return attachmentPaths;
    }

    public void setAttachmentPaths(List<String> attachmentPaths) {
        this.attachmentPaths = attachmentPaths;
    }
}
