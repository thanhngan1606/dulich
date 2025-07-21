package com.hoangminh.config;

import com.hoangminh.entity.Tour;
import com.hoangminh.repository.TourRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class TourDataInitializer implements CommandLineRunner {

    @Autowired
    private TourRepository tourRepository;

    @Override
    public void run(String... args) throws Exception {
        // Kiểm tra xem đã có tour chưa
        if (tourRepository.count() == 0) {
            log.info("Khởi tạo dữ liệu Tour...");
            
            // Tour trong nước
            createTour(1L, "Tour Đà Nẵng - Hội An", "Khám phá vẻ đẹp của Đà Nẵng và phố cổ Hội An", 
                      3, "Tham quan Bãi biển Mỹ Khê, Phố cổ Hội An, Bán đảo Sơn Trà, Ngũ Hành Sơn", 
                      new Date(124, 2, 15), new Date(124, 2, 17), "Đà Nẵng, Hội An", 1, "tour1.jpg", "TP.HCM", 1, 2500000L);
            
            createTour(2L, "Tour Nha Trang", "Tận hưởng không khí biển Nha Trang", 
                      2, "Tham quan Đảo Hòn Tre, Vinpearl, Tháp Bà Ponagar, Bãi biển Nha Trang", 
                      new Date(124, 2, 20), new Date(124, 2, 21), "Nha Trang", 1, "tour2.jpg", "TP.HCM", 1, 3000000L);
            
            createTour(3L, "Tour Phú Quốc", "Khám phá đảo ngọc Phú Quốc", 
                      4, "Tham quan Bãi Sao, Bãi Khem, Vinpearl Land Phú Quốc, Cáp treo Hòn Thơm", 
                      new Date(124, 3, 1), new Date(124, 3, 4), "Phú Quốc", 1, "tour3.jpg", "TP.HCM", 1, 4000000L);
            
            createTour(4L, "Tour Sapa", "Khám phá vùng núi Tây Bắc", 
                      3, "Tham quan Fansipan, Bản Cát Cát, Thác Bạc, Chợ Sapa", 
                      new Date(124, 3, 10), new Date(124, 3, 12), "Sapa", 1, "tour4.jpg", "Hà Nội", 1, 3500000L);
            
            createTour(5L, "Tour Huế", "Khám phá cố đô Huế", 
                      2, "Tham quan Đại Nội, Lăng Tự Đức, Chùa Thiên Mụ, Sông Hương", 
                      new Date(124, 3, 15), new Date(124, 3, 16), "Huế", 1, "tour5.jpg", "TP.HCM", 1, 2000000L);
            
            // Tour ngoài nước
            createTour(6L, "Tour Thái Lan - Bangkok", "Khám phá thủ đô Thái Lan", 
                      4, "Tham quan Cung điện Hoàng gia, Wat Phra Kaew, Chatuchak Market, Chao Phraya", 
                      new Date(124, 4, 1), new Date(124, 4, 4), "Bangkok, Thái Lan", 2, "tour6.jpg", "TP.HCM", 1, 8000000L);
            
            createTour(7L, "Tour Singapore", "Khám phá đảo quốc sư tử", 
                      3, "Tham quan Marina Bay Sands, Gardens by the Bay, Sentosa Island, Universal Studios", 
                      new Date(124, 4, 10), new Date(124, 4, 12), "Singapore", 2, "tour7.jpg", "TP.HCM", 1, 12000000L);
            
            createTour(8L, "Tour Malaysia - Kuala Lumpur", "Khám phá thủ đô Malaysia", 
                      3, "Tham quan Petronas Towers, Batu Caves, KL Tower, Genting Highlands", 
                      new Date(124, 4, 20), new Date(124, 4, 22), "Kuala Lumpur, Malaysia", 2, "tour8.jpg", "TP.HCM", 1, 9000000L);
            
            createTour(9L, "Tour Nhật Bản - Tokyo", "Khám phá thủ đô Nhật Bản", 
                      5, "Tham quan Tokyo Tower, Senso-ji, Shibuya, Mount Fuji, Disneyland", 
                      new Date(124, 5, 1), new Date(124, 5, 5), "Tokyo, Nhật Bản", 2, "tour9.jpg", "TP.HCM", 1, 25000000L);
            
            createTour(10L, "Tour Hàn Quốc - Seoul", "Khám phá thủ đô Hàn Quốc", 
                      4, "Tham quan Gyeongbokgung, N Seoul Tower, Myeongdong, Gangnam, Lotte World", 
                      new Date(124, 5, 10), new Date(124, 5, 13), "Seoul, Hàn Quốc", 2, "tour10.jpg", "TP.HCM", 1, 20000000L);
            
            log.info("Đã tạo thành công 10 tour test!");
        } else {
            log.info("Đã có dữ liệu Tour, bỏ qua khởi tạo.");
        }
    }
    
    private void createTour(Long id, String tenTour, String gioiThieu, Integer soNgay, String noiDung, 
                           Date ngayKhoiHanh, Date ngayKetThuc, String diemDen, Integer loaiTour, 
                           String anhTour, String diemKhoiHanh, Integer trangThai, Long giaTour) {
        Tour tour = new Tour();
        tour.setId(id);
        tour.setTen_tour(tenTour);
        tour.setGioi_thieu_tour(gioiThieu);
        tour.setSo_ngay(soNgay);
        tour.setNoi_dung_tour(noiDung);
        tour.setNgay_khoi_hanh(ngayKhoiHanh);
        tour.setNgay_ket_thuc(ngayKetThuc);
        tour.setDiem_den(diemDen);
        tour.setLoai_tour(loaiTour);
        tour.setAnh_tour(anhTour);
        tour.setDiem_khoi_hanh(diemKhoiHanh);
        tour.setTrang_thai(trangThai);
        tour.setGia_tour(giaTour);
        
        tourRepository.save(tour);
        log.info("Đã tạo tour: {}", tenTour);
    }
} 