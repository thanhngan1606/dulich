package com.hoangminh.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hoangminh.entity.Tour;
import com.hoangminh.repository.TourRepository;
import com.hoangminh.service.TourService;

@Service
public class TourServiceImpl implements TourService {

	@Autowired 
	private TourRepository tourRepository;
	
	@Override
	public Page<Tour> findAllTour(Pageable pageable) {
		
		Page<Tour> page = tourRepository.findAll(pageable);
		
		return page;
	}

	@Override
	public Tour findTourById(Long id) {
		Optional<Tour> tour = tourRepository.findById(id);
		
		if(tour.isPresent()) {
			return tour.get();
		}
		
		return null;
	}

	@Override
	public boolean saveTour(Tour tour) {
		if(this.tourRepository.save(tour) != null) {
			return true;
		}
		return false;
		
	}

	@Override
	public boolean updateTour(Tour newTour, Long id) {
		Optional<Tour> tour = this.tourRepository.findById(id);
		if(tour.isPresent()) {
			Tour updatedTour = newTour;
			updatedTour.setId(id);
			this.tourRepository.save(updatedTour);
			return true;
		}
		return false;
		
	}

	@Override
	public boolean deleteTour(Long id) {
		Optional<Tour> tour = this.tourRepository.findById(id);
		if(tour.isPresent()) {
			this.tourRepository.deleteById(id);
			return true;
		}
		return false;
	}

}
