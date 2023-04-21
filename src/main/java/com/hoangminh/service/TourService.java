package com.hoangminh.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hoangminh.entity.Tour;

public interface TourService {

	Page<Tour> findAllTour(Pageable pageable);
	
	Tour findTourById(Long id);
	
	boolean saveTour(Tour tour);
	
	boolean updateTour(Tour tour,Long id);
	
	boolean deleteTour(Long id);
}
