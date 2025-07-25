package com.hoangminh.service.impl;

import java.util.List;
import java.util.Optional;

import com.hoangminh.dto.BookingDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hoangminh.dto.BookingDTO;
import com.hoangminh.dto.TourDTO;
import com.hoangminh.entity.Booking;
import com.hoangminh.repository.BookingRepository;
import com.hoangminh.repository.TourRepository;
import com.hoangminh.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService{

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private TourRepository tourRepository;

	@Override
	public Page<BookingDTO> findAllBooking(String trang_thai,String ten_tour,Pageable pageable) {
		return this.bookingRepository.findAllBooking(trang_thai,ten_tour, pageable);
	}

	@Override
	public List<BookingDTO> findBookingByUserId(Long userId) {
		return this.bookingRepository.findBookingByUserId(userId);
	}

	@Override
	public Page<BookingDTO> findBookingByTourId(Long tour_Id,Pageable pageable) {
		return this.bookingRepository.findBookingByTourId(tour_Id, pageable);
	}

	@Override
	public boolean addNewBooking(BookingDTO newBooking) {


		TourDTO tourDTO = this.tourRepository.findTourById(newBooking.getTour_id());

		Booking booking = new Booking();
		booking.setSo_luong_nguoi(newBooking.getSo_luong_nguoi());
		booking.setNgay_khoi_hanh(newBooking.getNgay_khoi_hanh());
		booking.setTong_tien(tourDTO.getGia_tour()*newBooking.getSo_luong_nguoi());
		booking.setTour_id(newBooking.getTour_id());
		booking.setUser_id(newBooking.getUser_id());
		booking.setSo_luong_nguoi(newBooking.getSo_luong_nguoi());
		booking.setGhi_chu(newBooking.getGhi_chu());
		booking.setPt_thanh_toan(newBooking.getPt_thanh_toan());
		booking.setTrang_thai("Đang xử lý");

		this.bookingRepository.save(booking);

		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean approveBooking(Long bookingId,String trang_thai) {
		Optional<Booking> booking  = this.bookingRepository.findById(bookingId);

		if(booking.isPresent()) {
			Booking bookingUpdated = booking.get();
			bookingUpdated.setTrang_thai(trang_thai);
			this.bookingRepository.save(bookingUpdated);
			return true;
		}

		return false;
	}

	@Override
	public boolean deleteBooking(Long id) {

		BookingDTO booking = this.getBookingById(id);

		if("Đã hoàn thành".equals(booking.getTrang_thai())) {
			this.bookingRepository.deleteById(id);
			return true;
		}

		return false;
	}

	@Override
	public boolean updateBooking(Long id, BookingDTO bookingDTO) {
		Optional<Booking> bookingOpt = this.bookingRepository.findById(id);
		if (bookingOpt.isPresent()) {
			Booking booking = bookingOpt.get();
			if (bookingDTO.getUser_id() != null) booking.setUser_id(bookingDTO.getUser_id());
			if (bookingDTO.getTour_id() != null) booking.setTour_id(bookingDTO.getTour_id());
			if (bookingDTO.getSo_luong_nguoi() != null) booking.setSo_luong_nguoi(bookingDTO.getSo_luong_nguoi());
			if (bookingDTO.getNgay_khoi_hanh() != null) booking.setNgay_khoi_hanh(bookingDTO.getNgay_khoi_hanh());
			if (bookingDTO.getPt_thanh_toan() != null) booking.setPt_thanh_toan(bookingDTO.getPt_thanh_toan());
			if (bookingDTO.getGhi_chu() != null) booking.setGhi_chu(bookingDTO.getGhi_chu());
			if (bookingDTO.getTrang_thai() != null) booking.setTrang_thai(bookingDTO.getTrang_thai());
			this.bookingRepository.save(booking);
			return true;
		}
		return false;
	}

	@Override
	public BookingDTO getBookingById(Long id) {
		return this.bookingRepository.findBookingById(id);
	}

	@Override
	public BookingDetailDTO getBookingDetailById(Long id) {
		return this.bookingRepository.findBookingDetailById(id);
	}

	@Override
	public double getTotalRevenue() {
		Double total = bookingRepository.sumTotalRevenue();
		return total != null ? total : 0.0;
	}

}
