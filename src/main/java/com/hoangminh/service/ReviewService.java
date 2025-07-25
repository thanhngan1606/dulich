package com.hoangminh.service;


import com.hoangminh.entity.Review;
import com.hoangminh.dto.ReviewDTO;
import java.util.List;
import java.util.Optional;


public interface ReviewService {
    List<Review> findAll();


    Optional<Review> findById(Long id);


    Review save(Review review);


    void deleteById(Long id);

    Review addReview(ReviewDTO reviewDTO);
    List<ReviewDTO> getReviewsByUserId(Long userId);
}

