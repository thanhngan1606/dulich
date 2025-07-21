package com.hoangminh.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageUploadResponse {
    private String publicId;
    private String url;
    private String secureUrl;
    private String format;
    private Long bytes;
    private Integer width;
    private Integer height;
    private String resourceType;
    private String createdAt;
    
    public ImageUploadResponse(String publicId, String url, String secureUrl) {
        this.publicId = publicId;
        this.url = url;
        this.secureUrl = secureUrl;
    }
} 