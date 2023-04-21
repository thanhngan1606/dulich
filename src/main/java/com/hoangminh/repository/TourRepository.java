package com.hoangminh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoangminh.entity.Tour;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
	
	
	
}
