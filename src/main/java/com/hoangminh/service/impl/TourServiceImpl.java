package com.hoangminh.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hoangminh.dto.TourDTO;
import com.hoangminh.entity.Image;
import com.hoangminh.entity.Tour;
import com.hoangminh.entity.TourStart;
import com.hoangminh.repository.TourRepository;
import com.hoangminh.service.TourService;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class TourServiceImpl implements TourService {

	@Autowired 
	private TourRepository tourRepository;

	
	@Override
	public Page<TourDTO> findAllTour(String ten_tour,Long gia_tour_from,Long gia_tour_to,Date ngay_khoi_hanh,Integer loai_tour,Pageable pageable) {
		


		Page<TourDTO> page = this.tourRepository.findAll(ten_tour, gia_tour_from, gia_tour_to,ngay_khoi_hanh, loai_tour, pageable);
		return page;
	}

	@Override
	public TourDTO findTourById(Long id) {
		TourDTO tourDTO = this.tourRepository.findTourById(id);
		
		if(tourDTO!=null) {
			return tourDTO;
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
