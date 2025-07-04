package com.springboot.car.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.car.model.Review;
import com.springboot.car.service.ReviewService;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
     
	@Autowired
	private ReviewService reviewService;
	
	@PostMapping("/add/{customerId}/{carId}")
	public Review  postReview(@PathVariable int customerId,@PathVariable int carId,@RequestBody Review review) {
		return reviewService.postReview(customerId,carId,review);
	}
	
	@GetMapping("/rating")
	public List<Review> getReviewByRating(@RequestParam String rating) {
		return reviewService.getReviewByRating(rating);
	}
	
}
