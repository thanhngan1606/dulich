package com.hoangminh.controller.api;

import com.hoangminh.dto.ImageUploadResponse;
import com.hoangminh.dto.ResponseDTO;
import com.hoangminh.service.CloudinaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/upload")
public class ImageUploadController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/image")
    public ResponseEntity<ResponseDTO<ImageUploadResponse>> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "folder", required = false) String folder) {
        
        try {
            // Kiểm tra file
            if (file.isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(new ResponseDTO<>(false, "File không được để trống", null));
            }

            // Kiểm tra định dạng file
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.badRequest()
                    .body(new ResponseDTO<>(false, "Chỉ chấp nhận file ảnh", null));
            }

            // Upload ảnh
            Map<String, Object> result;
            if (folder != null && !folder.trim().isEmpty()) {
                result = cloudinaryService.uploadImage(file, folder);
            } else {
                result = cloudinaryService.uploadImage(file);
            }

            // Tạo response
            ImageUploadResponse response = new ImageUploadResponse(
                (String) result.get("public_id"),
                (String) result.get("url"),
                (String) result.get("secure_url")
            );

            return ResponseEntity.ok(new ResponseDTO<>(true, "Upload thành công", response));

        } catch (IOException e) {
            log.error("Lỗi khi upload ảnh: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                .body(new ResponseDTO<>(false, "Lỗi khi upload ảnh: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/image/{publicId}")
    public ResponseEntity<ResponseDTO<String>> deleteImage(@PathVariable String publicId) {
        try {
            cloudinaryService.deleteImage(publicId);
            return ResponseEntity.ok(new ResponseDTO<>(true, "Xóa ảnh thành công", publicId));
        } catch (IOException e) {
            log.error("Lỗi khi xóa ảnh: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                .body(new ResponseDTO<>(false, "Lỗi khi xóa ảnh: " + e.getMessage(), null));
        }
    }
} 