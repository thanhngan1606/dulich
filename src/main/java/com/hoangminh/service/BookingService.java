package com.hoangminh.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hoangminh.entity.Booking;

public interface BookingService {

	Page<Booking> findAllBooking(Pageable pageable);
	
	Booking findBookingById(Long id);
}
