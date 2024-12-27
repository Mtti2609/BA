package com.API.API.dto;

import com.API.API.model.Department;
import com.API.API.model.User.Role;

import java.time.LocalDateTime;

public class UserWithDepartmentResponse {

    private Integer userId;
    private String username;
    private String password; // Có thể mã hóa nếu cần
    private String fullName;
    private String email;
    private Role role;
    private String avatar;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Department department;

    // Constructor
    public UserWithDepartmentResponse(Integer userId, String username, String password, String fullName, String email,
                                      Role role, String avatar, LocalDateTime createdAt, LocalDateTime updatedAt,
                                      Department department) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.avatar = avatar;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.department = department;
    }

    // Getters and Setters
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
