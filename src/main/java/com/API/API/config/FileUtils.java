package com.API.API.config;

import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.Paths;
import java.io.IOException;

public class FileUtils {

    private static final String UPLOAD_DIR = "src/main/resources/static/images/avatar"; // Thư mục lưu trữ avatar

    public static String saveAvatar(MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            // Tạo tên file duy nhất
            String fileName = file.getOriginalFilename();
            Path targetLocation = Paths.get(UPLOAD_DIR).resolve(fileName);

            // Kiểm tra nếu file đã tồn tại
            if (!Files.exists(targetLocation)) {
                // Lưu file nếu chưa tồn tại
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            }

            // Trả về URL đầy đủ
            return "http://localhost:8080/images/avatar/" + fileName;
        }
        return null;
    }

}
