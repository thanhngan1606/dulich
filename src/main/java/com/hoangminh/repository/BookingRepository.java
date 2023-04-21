package com.hoangminh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoangminh.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>{

}
