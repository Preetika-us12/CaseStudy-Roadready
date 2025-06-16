package com.springboot.car.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.car.model.CarOwner;
import com.springboot.car.service.CarOwnerService;

@RestController
public class CarOwnerController {

    @Autowired
    private CarOwnerService carOwnerService;

    private Logger logger = LoggerFactory.getLogger("CarOwnerController");

    @PostMapping("/api/carowner/add")
    public CarOwner addOwner(@RequestBody CarOwner carOwner) {
        return carOwnerService.addOwner(carOwner);
    }

    @GetMapping("/api/carowner/get-all")
    public ResponseEntity<?> getAllCarOwners(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "1000000") Integer size) {

        if (page == 0 && size == 1000000)
            logger.info("No Pagination call for all car owners");

        List<CarOwner> carOwners = carOwnerService.getAllCarOwners(page, size);

        return ResponseEntity.status(HttpStatus.OK).body(carOwners);
    }

    @DeleteMapping("/api/carowner/delete/{id}")
    public ResponseEntity<?> deleteCarOwner(@PathVariable int id) {
        carOwnerService.deleteCarOwner(id);
        return ResponseEntity.status(HttpStatus.OK).body("Car owner deleted");
    }

    @GetMapping("/api/carowner/get-one")
    public CarOwner getCarOwnerById(Principal principal) {
        String username = principal.getName();
        return carOwnerService.getCarOwnerByUsername(username);
    }

    @PostMapping("/api/carowner/update/{id}")
    public CarOwner updateCarOwner(@PathVariable int id, @RequestBody CarOwner updatedCarOwner) {
        logger.info("ID given is : " + id);
        return carOwnerService.updateCarOwner(id, updatedCarOwner);
    }
    
    @GetMapping("/api/carowner/search")
    public List<CarOwner> searchCarOwners(@RequestParam String keyword,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "1000000") int size) {
        return carOwnerService.searchCarOwners(keyword, page, size);
    }
}
