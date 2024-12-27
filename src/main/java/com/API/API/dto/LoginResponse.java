package com.API.API.dto;

public class LoginResponse {
    private String message;
    private boolean success;
    private Integer userId;
    private String username;
    private String role;

    // Constructor
    public LoginResponse(String message, boolean success, Integer userId, String username, String role) {
        this.message = message;
        this.success = success;
        this.userId = userId;
        this.username = username;
        this.role = role;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
