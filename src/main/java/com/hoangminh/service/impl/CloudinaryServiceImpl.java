package com.hoangminh.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.hoangminh.service.CloudinaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public Map<String, Object> uploadImage(MultipartFile file) throws IOException {
        try {
            Map<String, Object> options = ObjectUtils.asMap(
                "resource_type", "auto",
                "folder", "ltnc_tour"
            );
            
            Map<String, Object> result = cloudinary.uploader().upload(file.getBytes(), options);
            log.info("Upload thành công: {}", result.get("public_id"));
            return result;
        } catch (IOException e) {
            log.error("Lỗi khi upload ảnh: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Map<String, Object> uploadImage(MultipartFile file, String folder) throws IOException {
        try {
            Map<String, Object> options = ObjectUtils.asMap(
                "resource_type", "auto",
                "folder", folder
            );
            
            Map<String, Object> result = cloudinary.uploader().upload(file.getBytes(), options);
            log.info("Upload thành công vào folder {}: {}", folder, result.get("public_id"));
            return result;
        } catch (IOException e) {
            log.error("Lỗi khi upload ảnh vào folder {}: {}", folder, e.getMessage());
            throw e;
        }
    }

    @Override
    public Map<String, Object> deleteImage(String publicId) throws IOException {
        try {
            Map<String, Object> result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            log.info("Xóa ảnh thành công: {}", publicId);
            return result;
        } catch (IOException e) {
            log.error("Lỗi khi xóa ảnh {}: {}", publicId, e.getMessage());
            throw e;
        }
    }
} 