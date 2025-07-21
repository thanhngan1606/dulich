package com.hoangminh.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface CloudinaryService {
    
    /**
     * Upload một file ảnh lên Cloudinary
     * @param file File ảnh cần upload
     * @return Map chứa thông tin upload (public_id, url, secure_url, etc.)
     * @throws IOException Nếu có lỗi khi upload
     */
    Map<String, Object> uploadImage(MultipartFile file) throws IOException;
    
    /**
     * Upload một file ảnh với folder cụ thể
     * @param file File ảnh cần upload
     * @param folder Folder trong Cloudinary
     * @return Map chứa thông tin upload
     * @throws IOException Nếu có lỗi khi upload
     */
    Map<String, Object> uploadImage(MultipartFile file, String folder) throws IOException;
    
    /**
     * Xóa ảnh từ Cloudinary theo public_id
     * @param publicId Public ID của ảnh cần xóa
     * @return Map chứa thông tin kết quả xóa
     * @throws IOException Nếu có lỗi khi xóa
     */
    Map<String, Object> deleteImage(String publicId) throws IOException;
} 