package com.hoangminh.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hoangminh.enumtype.USER_ROLE;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
@Entity
@Table(name = "user")
public class User {
	@Id
	// Không dùng AUTO_INCREMENT, sẽ tự set bằng tay
	private Long id;
	
	private String username;
	
	private String ho_ten;
	
	@JsonIgnore
	private String pass; // Đổi từ password thành pass để phù hợp với database
	
	private String gioi_tinh;
	
	private String sdt;
	
	private String email;
	
	private String dia_chi;

	private Integer role_id; // 1 = admin, 2 = user, 3 = manager
	
	private Date created_at;
}
