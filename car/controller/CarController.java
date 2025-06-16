package com.springboot.car.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.car.model.Car;
import com.springboot.car.service.CarService;

@RestController
@RequestMapping("/api/car")
public class CarController {

	
	
	@Autowired
	private CarService carService;
	Logger logger = LoggerFactory.getLogger("CarController");
	
	 @PostMapping("/add/{ownerId}")
	    public ResponseEntity<?> batchInsertCars(@RequestBody List<Car> carList,
	                                             @PathVariable("ownerId") int ownerId) {
	        carService.batchInsert(carList, ownerId);
	        return ResponseEntity.ok("Cars added successfully!");
	    }
	 

	@GetMapping("/all")
	public List<Car> getAllCars(
			@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "1000000") Integer size) {
		if (page == 0 && size == 1000000)
		logger.info("No Pagination call for all cars");
		return carService.getAllCars(page, size);
	}
	
	
	@GetMapping("/search/price")
	public List<Car> getCarsByPriceRange(@RequestParam double min, @RequestParam double max,
			 @RequestParam(name = "page", required = false, defaultValue = "0") int page,
			 @RequestParam(name = "size", required = false, defaultValue = "1000000") int size) {
	    return carService.getCarsByPriceRange(min, max,page,size);
	}
	
	@GetMapping("/filter")
	public List<Car> filterCars(
	    @RequestParam String brand, @RequestParam String model,
	    @RequestParam(name = "page", required = false, defaultValue = "0") int page,
	    @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
	    return carService.filterCars(brand, model, page, size);
	}

	@GetMapping("/available")
	public List<Car> getAvailableCars(
	    @RequestParam(name = "page", required = false, defaultValue = "0") int page,
	    @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
	    return carService.getAvailableCars(page, size);
	}
	
	




}

