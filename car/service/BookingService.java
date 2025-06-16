package com.springboot.car.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.car.dto.CustomerDto;
import com.springboot.car.exception.InvalidInputException;
import com.springboot.car.exception.NotBookedAnyCarException;
import com.springboot.car.exception.ResourceNotFoundException;
import com.springboot.car.model.Booking;
import com.springboot.car.model.Car;
import com.springboot.car.model.Customer;
import com.springboot.car.repository.BookingRepository;
import com.springboot.car.repository.CarRepository;
import com.springboot.car.repository.CustomerRepository;


@Service
public class BookingService {

		private CustomerRepository customerRepository;
		private CarRepository carRepository;
		private BookingRepository bookingRepository;
		@Autowired
		private CustomerDto customerDto;

	public BookingService(CustomerRepository customerRepository, CarRepository carRepository,
				BookingRepository bookingRepository) {
			super();
			this.customerRepository = customerRepository;
			this.carRepository = carRepository;
			this.bookingRepository = bookingRepository;
		}


	public Booking addBooking(int customerId,int carId,Booking booking) {
		Customer customer=customerRepository.findById(customerId)
				.orElseThrow(()->new ResourceNotFoundException("Customer ID Invalid"));
		 Car car=carRepository.findById(carId)
				 .orElseThrow(()->
				 new ResourceNotFoundException("Car ID Invalid"));
		 booking.setCustomer(customer);
		 booking.setCar(car);
		 return  bookingRepository.save(booking);
	}
	
	public List<CustomerDto> getCustomerByCarId(int carId) {
		carRepository.findById(carId)
				.orElseThrow(() -> new InvalidInputException("Car Id Invalid"));

		List<Customer> list = bookingRepository.getCustomerByCarId(carId);

		return customerDto.convertCustomerIntoDto(list);
	}


	public List<Car> getCarByCustomerId(int customerId){
		customerRepository.findById(customerId)
		.orElseThrow(()->new ResourceNotFoundException("Customer ID Invalid"));
		
		 List<Car> list = bookingRepository.getCarByCustomerId(customerId);
		 if(list != null && list.isEmpty())
			 throw new NotBookedAnyCarException("Customer not booked any car!!");
		return list;

	}
	
	public void cancelBooking(int bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setBookingStatus("Cancelled");
        bookingRepository.save(booking);

        Car car = booking.getCar();
        car.setAvailabilityStatus("Available");
        carRepository.save(car);
    }
	
	 public Booking viewBookingById(int bookingId) {
	        return bookingRepository.findById(bookingId)
	                .orElseThrow(() -> new RuntimeException("Booking not found"));
	    }
	 
	 
	 public Booking updateBooking(int bookingId, Booking updated) {
	        Booking exist = bookingRepository.findById(bookingId)
	                .orElseThrow(() -> new RuntimeException("Booking not found"));
	        if (updated.getPickupDate() != null) {
	            exist.setPickupDate(updated.getPickupDate());
	        }
	        if (updated.getDropoffDate() != null) {
	            exist.setDropoffDate(updated.getDropoffDate());
	        }
	        if (updated.getBookingStatus() != null) {
	            exist.setBookingStatus(updated.getBookingStatus());
	        }
	        if (updated.getTotalAmount() != 0.0) {
	            exist.setTotalAmount(updated.getTotalAmount());
	        }

	        return bookingRepository.save(exist);
	    }
	 
	 
}
