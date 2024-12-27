package com.API.API.service;

import com.API.API.model.Permission;
import com.API.API.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    // Lấy tất cả các quyền
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    // Lấy quyền theo Department ID
    public List<Permission> getPermissionsByDepartmentId(Integer departmentId) {
        return permissionRepository.findPermissionsByDepartmentId(departmentId);
    }
}
