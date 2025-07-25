package com.hoangminh.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
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
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date ngay_khoi_hanh;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date ngay_ket_thuc;
	
	private String diem_den;
	
	private String loai_tour;
	
	private String anh_tour;
	
	private String diem_khoi_hanh;
	
	private String trang_thai;
	
	private Long gia_tour;
	
}
