package com.API.API.service;

import com.API.API.config.FileUtils;
import com.API.API.model.Department;
import com.API.API.model.Permission;
import com.API.API.model.User;
import com.API.API.model.UserPermission;
import com.API.API.repository.DepartmentRepository;
import com.API.API.repository.PermissionRepository;
import com.API.API.repository.UserPermissionRepository;
import com.API.API.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UserPermissionRepository userPermissionRepository;

    // Hàm mã hóa mật khẩu bằng SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            // Chuyển đổi byte[] thành chuỗi Hexadecimal
            try (Formatter formatter = new Formatter()) {
                for (byte b : hashedBytes) {
                    formatter.format("%02x", b);
                }
                return formatter.toString();
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error while hashing password", e);
        }
    }

    // Xác thực đăng nhập
    public Optional<User> login(String username, String password) {
        // Mã hóa mật khẩu người dùng nhập vào
        String hashedPassword = hashPassword(password);

        // So sánh với mật khẩu đã mã hóa trong cơ sở dữ liệu
        return userRepository.findByUsernameAndPassword(username, hashedPassword);
    }

    // Tạo user mới và gán quyền từ phòng ban
    public User createUser(User user) {
        // Mã hóa mật khẩu trước khi lưu
        user.setPassword(hashPassword(user.getPassword()));

        // Lưu user mới
        User savedUser = userRepository.save(user);

        // Nếu user thuộc phòng ban, gán quyền từ phòng ban cho user
        if (savedUser.getDepartment() != null) {
            assignPermissionsFromDepartment(savedUser, savedUser.getDepartment().getDepartmentId());
        }

        return savedUser;
    }

    // Gán user vào phòng ban và kế thừa quyền
    public User addUserToDepartment(Integer userId, Integer departmentId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + departmentId));

        // Cập nhật phòng ban cho người dùng
        user.setDepartment(department);
        userRepository.save(user);

        // Gán quyền từ phòng ban cho người dùng
        assignPermissionsFromDepartment(user, departmentId);

        return user;
    }

    // Gán quyền từ phòng ban cho user
    // Gán tất cả các quyền từ phòng ban cho người dùng
    private void assignPermissionsFromDepartment(User user, Integer departmentId) {
        List<Permission> departmentPermissions = permissionRepository.findPermissionsByDepartmentId(departmentId);

        for (Permission permission : departmentPermissions) {
            if (!userPermissionRepository.existsByUserAndPermission(user, permission)) {
                userPermissionRepository.save(new UserPermission(user, permission));
            }
        }
    }
    public List<Permission> getUserPermissions(Integer userId) {
        return userPermissionRepository.findPermissionsByUserId(userId);
    }


    // Cập nhật thông tin user

    public User updateUser(Integer id, User updatedUser, MultipartFile file) throws IOException {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Cập nhật thông tin cơ bản
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setFullName(updatedUser.getFullName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setRole(updatedUser.getRole());

        // Kiểm tra nếu phòng ban thay đổi
        if (updatedUser.getDepartment() != null &&
                (existingUser.getDepartment() == null ||
                        !existingUser.getDepartment().getDepartmentId().equals(updatedUser.getDepartment().getDepartmentId()))) {

            // Xóa toàn bộ quyền cũ
            userPermissionRepository.deleteByUserId(existingUser.getUserId());

            // Cập nhật phòng ban
            existingUser.setDepartment(updatedUser.getDepartment());

            // Gán quyền mới từ phòng ban
            assignPermissionsFromDepartment(existingUser, updatedUser.getDepartment().getDepartmentId());
        }


        return userRepository.save(existingUser);
    }



    public void clearUserPermissions(Integer userId) {
        userPermissionRepository.deleteByUserId(userId);
    }


    // Xóa user
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    // Lấy danh sách tất cả user
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Lấy thông tin user theo ID
    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }
}
