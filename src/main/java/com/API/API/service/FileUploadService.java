package com.API.API.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileUploadService {

    // Thư mục tải lên được xác định từ hệ thống hoặc có thể cấu hình trong file cấu hình (application.properties)
    private final String uploadDir = System.getProperty("user.dir") + "/uploads/";

    // Phương thức upload cho tất cả các loại tệp (bao gồm ảnh, PDF, tài liệu, v.v.)
    public String uploadFile(MultipartFile file) throws IOException {
        // Tạo tên tệp duy nhất để tránh trùng lặp
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // Tạo đường dẫn tệp hoàn chỉnh
        Path path = Paths.get(uploadDir, fileName);

        // Kiểm tra và tạo thư mục nếu chưa tồn tại
        if (!Files.exists(Paths.get(uploadDir))) {
            Files.createDirectories(Paths.get(uploadDir));
        }

        // Lưu tệp vào thư mục
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        // Trả về đường dẫn của tệp đã lưu
        return path.toString();  // Trả về đường dẫn tệp đã tải lên
    }
}
