package com.hoangminh.entity;

import java.util.ArrayList;
import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
@Entity
@Table(name = "tour")
public class Tour {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String ten_tour;
	
	private String gioi_thieu_tour;
	
	private Integer so_ngay;
	
	private String noi_dung_tour;
	
	private ArrayList<Date> ngay_khoi_hanh = new ArrayList<>();
	
	@OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, orphanRemoval = true)
	private ArrayList<Image> list_anh_tour = new ArrayList<>();
	
	private Date ngay_ket_thuc;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="diem_den_id",nullable = true)
	private Destination diem_den;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="loai_tour_id",nullable = true)
	private LoaiTour loai_tour;
	
	private String anh_tour;
	
	private String diem_khoi_hanh;
	
	private Long gia_tour;
	
	
}
