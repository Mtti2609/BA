package com.API.API.controller;

import com.API.API.service.DepartmentPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/department-permissions")
public class DepartmentPermissionController {

    @Autowired
    private DepartmentPermissionService departmentPermissionService;

    // Assign permissions to a department
    @PostMapping("/assign")
    public ResponseEntity<String> assignPermissionsToDepartment(@RequestBody AssignPermissionsRequest request) {
        if (request.getDepartmentId() == null || request.getPermissionIds() == null || request.getPermissionIds().isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid request data.");
        }
        departmentPermissionService.assignPermissionsToDepartment(request.getDepartmentId(), request.getPermissionIds());
        return ResponseEntity.ok("Permissions assigned successfully.");
    }

    // Remove permission from a department
    @DeleteMapping("/remove")
    public ResponseEntity<String> removePermissionFromDepartment(@RequestBody RemovePermissionsRequest request) {
        if (request.getDepartmentId() == null || request.getPermissionId() == null) {
            return ResponseEntity.badRequest().body("Invalid request data.");
        }
        departmentPermissionService.removePermissionFromDepartment(request.getDepartmentId(), request.getPermissionId());
        return ResponseEntity.ok("Permission removed successfully.");
    }

    public static class AssignPermissionsRequest {
        private Integer departmentId;
        private List<Integer> permissionIds;

        // Getters and Setters
        public Integer getDepartmentId() { return departmentId; }
        public void setDepartmentId(Integer departmentId) { this.departmentId = departmentId; }
        public List<Integer> getPermissionIds() { return permissionIds; }
        public void setPermissionIds(List<Integer> permissionIds) { this.permissionIds = permissionIds; }
    }

    public static class RemovePermissionsRequest {
        private Integer departmentId;
        private Integer permissionId;

        // Getters and Setters
        public Integer getDepartmentId() { return departmentId; }
        public void setDepartmentId(Integer departmentId) { this.departmentId = departmentId; }
        public Integer getPermissionId() { return permissionId; }
        public void setPermissionId(Integer permissionId) { this.permissionId = permissionId; }
    }
}
