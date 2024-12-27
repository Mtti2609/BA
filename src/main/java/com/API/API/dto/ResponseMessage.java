package com.API.API.dto;

import com.API.API.model.EventNotification;

public class ResponseMessage {
    private String message;
    private EventNotification data;

    // Constructor, getters, and setters
    public ResponseMessage(String message, EventNotification data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public EventNotification getData() {
        return data;
    }

    public void setData(EventNotification data) {
        this.data = data;
    }
}
