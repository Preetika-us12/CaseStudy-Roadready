package com.springboot.car.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.car.model.CarOwner;
import com.springboot.car.model.User;
import com.springboot.car.repository.CarOwnerRepository;

@Service
public class CarOwnerService {
    private final CarOwnerRepository carOwnerRepository;
    private final UserService userService;

    public CarOwnerService(CarOwnerRepository carOwnerRepository, UserService userService) {
        this.carOwnerRepository = carOwnerRepository;
        this.userService = userService;
    }

    public CarOwner addOwner(CarOwner carOwner) {
        User user = carOwner.getUser();
        user.setRole("CAROWNER");
        user = userService.signUp(user);
        carOwner.setUser(user);
        return carOwnerRepository.save(carOwner);
    }

    public List<CarOwner> getAllCarOwners(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return carOwnerRepository.findAll(pageable).getContent();
    }

    public void deleteCarOwner(int id) {
        CarOwner carOwner = carOwnerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Invalid ID Given"));
        carOwnerRepository.deleteById(id);
    }

    public CarOwner updateCarOwner(int id, CarOwner carOwner) {
        CarOwner updatedOwner = carOwnerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Invalid ID Given"));

        if (carOwner.getName() != null) {
            updatedOwner.setName(carOwner.getName());
        }
        if (carOwner.getEmail() != null) {
            updatedOwner.setEmail(carOwner.getEmail());
        }
        if (carOwner.getPhone() != null) {
            updatedOwner.setPhone(carOwner.getPhone());
        }
        if (carOwner.getUser() != null) {
            updatedOwner.setUser(carOwner.getUser());
        }
        updatedOwner.setActive(true);
        updatedOwner.setverified(true);

        return carOwnerRepository.save(updatedOwner);
    }

    public CarOwner getCarOwnerByUsername(String username) {
        return carOwnerRepository.getCarOwnerByUsername(username);
    }
    
    public List<CarOwner> searchCarOwners(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return carOwnerRepository.searchByKeyword(keyword.toLowerCase(), pageable).getContent();
    }
}
