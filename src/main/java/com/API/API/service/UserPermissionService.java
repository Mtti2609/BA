package com.API.API.service;

import com.API.API.model.User;
import com.API.API.model.Permission;
import com.API.API.model.UserPermission;
import com.API.API.repository.UserPermissionRepository;
import com.API.API.repository.UserRepository;
import com.API.API.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPermissionService {

    @Autowired
    private UserPermissionRepository userPermissionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    public void assignPermissionsToUser(Integer userId, List<Integer> permissionIds) {
        if (userId == null || permissionIds == null || permissionIds.isEmpty()) {
            throw new IllegalArgumentException("User ID hoặc Permission IDs không được để trống.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với ID: " + userId));

        for (Integer permissionId : permissionIds) {
            Permission permission = permissionRepository.findById(permissionId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy quyền với ID: " + permissionId));

            UserPermission userPermission = new UserPermission();
            userPermission.setUser(user);
            userPermission.setPermission(permission);

            userPermissionRepository.save(userPermission);
        }
    }
    // Lấy danh sách quyền của người dùng theo userId
    public List<Permission> getPermissionsByUserId(Integer userId) {
        return userPermissionRepository.findPermissionsByUserId(userId);
    }
    // Xóa quyền cho người dùng
    public void removePermissionFromUser(Integer userId, Integer permissionId) {
        if (userId == null || permissionId == null) {
            throw new IllegalArgumentException("User ID hoặc Permission ID không được để trống.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với ID: " + userId));

        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy quyền với ID: " + permissionId));

        UserPermission userPermission = userPermissionRepository.findByUserAndPermission(user, permission)
                .orElseThrow(() -> new RuntimeException("Quyền không tồn tại cho người dùng này"));

        userPermissionRepository.delete(userPermission);
    }
}

