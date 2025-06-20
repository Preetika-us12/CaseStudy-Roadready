package com.springboot.car.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.car.model.Review;

public interface ReviewRepository extends JpaRepository<Review,Integer> {
	
	@Query("select r from Review r where r.rating > ?1")
	List<Review> getByRating(String rating);
}
