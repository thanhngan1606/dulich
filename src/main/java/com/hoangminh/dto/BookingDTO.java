package com.hoangminh.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class BookingDTO {

    private Long id;

    private Long user_id;
    
    private Long tour_id;
    
    private String ten_tour;
    
    private Integer so_luong_nguoi;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date ngay_khoi_hanh;
    
    private Long tong_tien;
    
    private String trang_thai;

    private String pt_thanh_toan;

    private String ghi_chu;

    private Date booking_at;

    public BookingDTO(Long id, Long user_id, Long tour_id, String ten_tour, Integer so_luong_nguoi, Date ngay_khoi_hanh, Long tong_tien, String trang_thai, String pt_thanh_toan, String ghi_chu, Date booking_at) {
        this.id = id;
        this.user_id = user_id;
        this.tour_id = tour_id;
        this.ten_tour = ten_tour;
        this.so_luong_nguoi = so_luong_nguoi;
        this.ngay_khoi_hanh = ngay_khoi_hanh;
        this.tong_tien = tong_tien;
        this.trang_thai = trang_thai;
        this.pt_thanh_toan = pt_thanh_toan;
        this.ghi_chu = ghi_chu;
        this.booking_at = booking_at;
    }
}
