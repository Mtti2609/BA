package com.API.API.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "department_permissions")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DepartmentPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer departmentPermissionID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departmentID", nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permissionID", nullable = false)
    private Permission permission;

    @Column(nullable = false, updatable = false)
    private LocalDateTime assignedAt = LocalDateTime.now(); // Default to the current timestamp

    // Getters and Setters
    public Integer getDepartmentPermissionID() {
        return departmentPermissionID;
    }

    public void setDepartmentPermissionID(Integer departmentPermissionID) {
        this.departmentPermissionID = departmentPermissionID;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }
}
