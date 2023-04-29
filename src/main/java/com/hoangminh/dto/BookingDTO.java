package com.hoangminh.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {

    private Long user_id;
    
    private Long tour_id;
    
    private String ten_tour;
    
    private Integer so_luong_nguoi;
    
    private Date ngay_khoi_hanh;
    
    private Long tong_tien;
    
    private Integer trang_thai;

}
