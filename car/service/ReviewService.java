package com.springboot.car.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.car.exception.ResourceNotFoundException;
import com.springboot.car.model.Booking;
import com.springboot.car.model.Review;
import com.springboot.car.repository.BookingRepository;
import com.springboot.car.repository.ReviewRepository;

@Service
public class ReviewService {
      

	private BookingRepository bookingRepository;
	private ReviewRepository reviewRepository;
	public ReviewService(BookingRepository bookingRepository,ReviewRepository reviewRepository) {
		super();
				this.bookingRepository = bookingRepository;
		this.reviewRepository = reviewRepository;
	}



	public Review postReview(int customerId,int carId,Review review) {
	     Booking booking=bookingRepository.getUsingJPQL(customerId,carId)
	    		 .orElseThrow(()->new ResourceNotFoundException("Customer not booked the car"));
	     review.setBooking(booking);
	     return reviewRepository.save(review);
	}
	
	
	public List<Review> getReviewByRating(String rating) {
		return reviewRepository.getByRating(rating);
	}
}
