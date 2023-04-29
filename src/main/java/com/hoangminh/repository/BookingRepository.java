package com.hoangminh.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hoangminh.dto.BookingDTO;
import com.hoangminh.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>{

	@Query(value = "SELECT new com.hoangminh.dto.BookingDTO(b.id,b.user_id,u.ho_ten,b.tour_id,t.ten_tour,b.ngay_khoi_hanh,b.so_luong_nguoi ,"
			+ "b.trang_thai,b.tong_tien) "
			+ " FROM Booking b JOIN User u ON b.user_id = u.id JOIN Tour t ON b.tour_id = t.id  "
			+ " WHERE ( :trang_thai IS NULL OR b.trang_thai = :trang_thai ) "
			+ " AND ( :ten_tour IS NULL OR :ten_tour = '' OR t.ten_tour LIKE %:ten_tour% ) "
			+ " ORDER BY t.id ")
	public Page<BookingDTO> findAllBooking(@Param("trang_thai") Integer trang_thai,@Param("trang_thai") String ten_tour,Pageable pageable);
	
	@Query(value = "SELECT new com.hoangminh.dto.BookingDTO(b.id,b.user_id,u.ho_ten,b.tour_id,t.ten_tour,b.ngay_khoi_hanh,b.so_luong_nguoi ,"
			+ "b.trang_thai,b.tong_tien) "
			+ " FROM Booking  b JOIN User u ON b.user_id = u.id JOIN Tour t ON b.tour_id = t.id  WHERE b.user_id = :userId")
	BookingDTO findBookingByUserId(@Param("userId")Long userId);
	
	@Query(value = "SELECT new com.hoangminh.dto.BookingDTO(b.id,b.user_id,u.ho_ten,b.tour_id,t.ten_tour,b.ngay_khoi_hanh,b.so_luong_nguoi ,"
			+ "b.trang_thai,b.tong_tien) "
			+ " FROM Booking  b JOIN User u ON b.user_id = u.id JOIN Tour t ON b.tour_id = t.id  WHERE b.tour_id = :tour_id")
	Page<BookingDTO> findBookingByTourId(@Param("tour_id") Long tour_id,Pageable pageable);
	
}
