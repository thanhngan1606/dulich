package com.hoangminh.config;

import com.hoangminh.entity.User;
import com.hoangminh.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class AdminInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Kiểm tra xem đã có admin chưa
        if (userRepository.findByUsername("admin") == null) {
            // Tạo admin mặc định
            User admin = new User();
            admin.setId(1L); // Set ID bằng tay theo constraint
            admin.setUsername("admin");
            admin.setHo_ten("Administrator");
            admin.setPass(BCrypt.hashpw("123456", BCrypt.gensalt(10)));
            admin.setGioi_tinh("Nam");
            admin.setSdt("0123456789");
            admin.setEmail("admin@gmail.com");
            admin.setDia_chi("Hà Nội");
            admin.setRole_id(1); // 1 = ADMIN
            admin.setCreated_at(new Date());

            userRepository.save(admin);
            log.info("Đã tạo tài khoản admin mặc định: admin/123456");
        }

        // Tạo admin thứ 2 nếu chưa có
        if (userRepository.findByUsername("admin2") == null) {
            User admin2 = new User();
            admin2.setId(2L); // Set ID bằng tay theo constraint
            admin2.setUsername("admin2");
            admin2.setHo_ten("Admin Manager");
            admin2.setPass(BCrypt.hashpw("admin123", BCrypt.gensalt(10)));
            admin2.setGioi_tinh("Nam");
            admin2.setSdt("0987654321");
            admin2.setEmail("admin2@gmail.com");
            admin2.setDia_chi("TP.HCM");
            admin2.setRole_id(1); // 1 = ADMIN
            admin2.setCreated_at(new Date());

            userRepository.save(admin2);
            log.info("Đã tạo tài khoản admin thứ 2: admin2/admin123");
        }

        log.info("Khởi tạo admin hoàn tất!");
    }
} 