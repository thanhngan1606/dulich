package com.hoangminh.utilities;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
public class FileUploadUtil {

    public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        File fileUploadDir = new File(uploadDir);
        if (!fileUploadDir.exists()) {
            fileUploadDir.mkdirs();
        }
        String filePath = fileUploadDir.getAbsolutePath() + "/" + fileName;
        log.info("file path : {}",filePath);
        File file = new File(filePath);
        multipartFile.transferTo(file);
    }

}
