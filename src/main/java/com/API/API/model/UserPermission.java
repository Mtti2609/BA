package com.API.API.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_permissions")
public class UserPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userPermissionId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "permissionId", nullable = false)
    private Permission permission;

    @Column(name = "assignedAt", updatable = false)
    private java.time.LocalDateTime assignedAt = java.time.LocalDateTime.now();

    // Constructor không tham số (bắt buộc để JPA hoạt động)
    public UserPermission() {}

    // Constructor đầy đủ tham số
    public UserPermission(User user, Permission permission) {
        this.user = user;
        this.permission = permission;
    }

    // Getters and Setters
    public Integer getUserPermissionId() {
        return userPermissionId;
    }

    public void setUserPermissionId(Integer userPermissionId) {
        this.userPermissionId = userPermissionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public java.time.LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(java.time.LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }
}

