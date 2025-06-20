package com.springboot.car.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.car.exception.InvalidInputException;
import com.springboot.car.model.Car;
import com.springboot.car.model.CarOwner;
import com.springboot.car.repository.CarOwnerRepository;
import com.springboot.car.repository.CarRepository;
import com.springboot.car.repository.ManagerRepository;
@Service
public class CarService {
private CarRepository carRepository;
private ManagerRepository managerRepository;
private final CarOwnerRepository carOwnerRepository;

	  Logger logger = LoggerFactory.getLogger("CarService");
	  
	public CarService(CarRepository carRepository,ManagerRepository managerRepository,CarOwnerRepository carOwnerRepository) {
		super();
		this.carRepository = carRepository;
		this.managerRepository = managerRepository;
		this.carOwnerRepository = carOwnerRepository;
	}

	
	@Transactional
    public void batchInsert(List<Car> carList, int ownerId) {
        CarOwner owner = carOwnerRepository.findById(ownerId)
                .orElseThrow(() -> new InvalidInputException("Car Owner Id is Invalid"));
        logger.info("CarOwner record fetched by ownerId");
        if (carList.isEmpty())
            throw new InvalidInputException("No Cars Found for Insertion");
        logger.info("Adding.. CarOwner to Database");
        carList.parallelStream().forEach(car -> car.setCarOwner(owner));
        
        carRepository.saveAll(carList);
    }
	
	

    public List<Car> getAllCars(int page, int size) {
       
        Pageable pageable = PageRequest.of(page, size);
       
        return carRepository.findAll(pageable).getContent();
    }
    
    
    
    public List<Car> getCarsByPriceRange(double min, double max, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return carRepository.getCarsByPriceRange(min, max, pageable);
    }

    public List<Car> filterCars(String brand, String model, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return carRepository.filterCars(brand, model, pageable);
    }
    public List<Car> getAvailableCars(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return carRepository.findByAvailabilityStatus("Available", pageable).getContent();
    }


   
  


	
}
