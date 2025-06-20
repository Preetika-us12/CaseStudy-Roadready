package com.springboot.car.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.car.model.Booking;
import com.springboot.car.model.Car;
import com.springboot.car.model.Customer;
import com.springboot.car.service.BookingService;

@RestController
public class BookingController {

	
	@Autowired
	private BookingService bookingService;
	
	@PostMapping("/api/customer/book/car/{customerId}/{carId}")
	public Booking addBooking(
	        @PathVariable int customerId,
	        @PathVariable int carId,
	        @RequestBody Booking booking) {
	    return bookingService.addBooking(customerId, carId, booking);
	}
	
	@GetMapping("/api/customer/book/car/{carId}")
	public List<?> getCustomerByCarId(@PathVariable("carId")int carId){
		return bookingService.getCustomerByCarId(carId);
	}
	
	@GetMapping("/api/car/book/customer/{customerId}")
	public List<Car> getCarByCustomerId(@PathVariable int customerId){
		return bookingService.getCarByCustomerId(customerId);
	}
	
	@PutMapping("/api/customer/booking/update/{bookingId}")
	public Booking updateBooking(@PathVariable int bookingId,
	        @RequestBody Booking bookingDetails) {
	    return bookingService.updateBooking(bookingId, bookingDetails);
	}
	
	@DeleteMapping("/api/customer/booking/cancel/{bookingId}")
	public String cancelBooking(@PathVariable int bookingId) {
	    bookingService.cancelBooking(bookingId);
	    return "Booking with ID " + bookingId + " has been cancelled successfully.";
	}
	
	@GetMapping("/api/customer/view/{bookingId}")
	public Booking viewBooking(@PathVariable int bookingId) {
	    return bookingService.viewBookingById(bookingId);
	}


}
