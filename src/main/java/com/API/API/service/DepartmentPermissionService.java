package com.API.API.service;

import com.API.API.model.Department;
import com.API.API.model.Permission;
import com.API.API.model.DepartmentPermission;
import com.API.API.repository.DepartmentPermissionRepository;
import com.API.API.repository.DepartmentRepository;
import com.API.API.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentPermissionService {

    @Autowired
    private DepartmentPermissionRepository departmentPermissionRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    // Gán quyền cho phòng ban
    public void assignPermissionsToDepartment(Integer departmentId, List<Integer> permissionIds) {
        if (departmentId == null || permissionIds == null || permissionIds.isEmpty()) {
            throw new IllegalArgumentException("Department ID hoặc Permission IDs không được để trống.");
        }

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng ban với ID: " + departmentId));

        for (Integer permissionId : permissionIds) {
            Permission permission = permissionRepository.findById(permissionId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy quyền với ID: " + permissionId));

            if (!departmentPermissionRepository.existsByDepartmentAndPermission(department, permission)) {
                DepartmentPermission departmentPermission = new DepartmentPermission();
                departmentPermission.setDepartment(department);
                departmentPermission.setPermission(permission);
                departmentPermissionRepository.save(departmentPermission);
            }
        }
    }

    // Xóa quyền khỏi phòng ban
    public void removePermissionFromDepartment(Integer departmentId, Integer permissionId) {
        if (departmentId == null || permissionId == null) {
            throw new IllegalArgumentException("Department ID hoặc Permission ID không được để trống.");
        }

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng ban với ID: " + departmentId));

        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy quyền với ID: " + permissionId));

        DepartmentPermission departmentPermission = departmentPermissionRepository.findByDepartmentAndPermission(department, permission)
                .orElseThrow(() -> new RuntimeException("Quyền không tồn tại cho phòng ban này"));

        departmentPermissionRepository.delete(departmentPermission);
    }

    // Lấy danh sách quyền của phòng ban
    public List<DepartmentPermission> getPermissionsByDepartment(Integer departmentId) {
        if (departmentId == null) {
            throw new IllegalArgumentException("Department ID không được để trống.");
        }

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng ban với ID: " + departmentId));

        return departmentPermissionRepository.findAllByDepartment(department);
    }
}