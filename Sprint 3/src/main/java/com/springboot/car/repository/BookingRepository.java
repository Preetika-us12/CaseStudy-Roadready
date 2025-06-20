package com.springboot.car.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.car.model.Booking;
import com.springboot.car.model.Car;
import com.springboot.car.model.Customer;

public interface BookingRepository extends JpaRepository<Booking,Integer> {
	@Query("select b from Booking b where b.customer.id=?1 and b.car.id=?2")
	Optional<Booking> getUsingJPQL(int customerID,int carId);
	
	
	@Query("select b.customer from Booking b where b.car.id=?1")
	List<Customer> getCustomerByCarId(int carId);
	
	@Query("select b.car from Booking b where b.customer.id=?1")
	List<Car> getCarByCustomerId(int customerId);
	

}
