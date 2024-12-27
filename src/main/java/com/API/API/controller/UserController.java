package com.API.API.controller;

import com.API.API.dto.LoginRequest;
import com.API.API.dto.LoginResponse;
import com.API.API.dto.UserWithDepartmentResponse;
import com.API.API.model.Department;
import com.API.API.model.User;
import com.API.API.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.API.API.repository.DepartmentRepository;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private DepartmentRepository departmentRepository;
    // POST: /api/users/login
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (user.isPresent()) {
            User loggedInUser = user.get();
            return ResponseEntity.ok(new LoginResponse(
                    "Login successful!",
                    true,
                    loggedInUser.getUserId(),
                    loggedInUser.getUsername(),
                    loggedInUser.getRole().toString() // Trả về vai trò của người dùng
            ));
        } else {
            return ResponseEntity.status(401).body(new LoginResponse(
                    "Invalid username or password",
                    false,
                    null,
                    null,
                    null
            ));
        }
    }



    // GET: /api/users

    @GetMapping
    public List<UserWithDepartmentResponse> getAllUsers() {
        // Lấy danh sách tất cả người dùng từ UserService
        return userService.getAllUsers().stream()
                .map(user -> new UserWithDepartmentResponse(
                        user.getUserId(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getFullName(),
                        user.getEmail(),
                        user.getRole(),
                        user.getAvatar(),
                        user.getCreatedAt(),
                        user.getUpdatedAt(),
                        user.getDepartment() // Thông tin phòng ban
                ))
                .toList(); // Chuyển đổi thành danh sách
    }

    // GET: /api/users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        return userService.getUserById(id)
                .map(user -> {
                    // Trả về JSON với cả thông tin user và department
                    return ResponseEntity.ok(new UserWithDepartmentResponse(
                            user.getUserId(),
                            user.getUsername(),
                            user.getPassword(),
                            user.getFullName(),
                            user.getEmail(),
                            user.getRole(),
                            user.getAvatar(),
                            user.getCreatedAt(),
                            user.getUpdatedAt(),
                            user.getDepartment() // Trả về thông tin phòng ban
                    ));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // POST: /api/users
    @PostMapping
    public User createUser(@RequestBody User user, @RequestParam(required = false) Integer departmentId) {
        if (departmentId != null) {
            Department department = departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Department ID"));
            user.setDepartment(department);
        }
        return userService.createUser(user);
    }


    // PUT: /api/users/{id}
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Integer id,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam("username") String username,
            @RequestParam("fullName") String fullName,
            @RequestParam("email") String email,
            @RequestParam("role") String role,
            @RequestParam(value = "departmentId", required = false) Integer departmentId) { // Thêm departmentId
        try {
            User updatedUser = new User();
            updatedUser.setUsername(username);
            updatedUser.setFullName(fullName);
            updatedUser.setEmail(email);
            updatedUser.setRole(User.Role.valueOf(role));

            // Gán phòng ban nếu departmentId không null
            if (departmentId != null) {
                Department department = departmentRepository.findById(departmentId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid Department ID"));
                updatedUser.setDepartment(department);
            } else {
                updatedUser.setDepartment(null); // Xóa phòng ban nếu departmentId không được truyền
            }

            User savedUser = userService.updateUser(id, updatedUser, file);
            return ResponseEntity.ok(savedUser);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Gán người dùng vào phòng ban và kế thừa quyền
    @PutMapping("/{userId}/department/{departmentId}")
    public ResponseEntity<String> addUserToDepartment(
            @PathVariable Integer userId,
            @PathVariable Integer departmentId
    ) {
        try {
            User user = userService.addUserToDepartment(userId, departmentId);
            return ResponseEntity.ok("User with ID: " + userId +
                    " has been added to department with ID: " + departmentId +
                    " and inherited permissions.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    // DELETE: /api/users/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}