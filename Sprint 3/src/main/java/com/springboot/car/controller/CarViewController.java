package com.springboot.car.controller;

import com.springboot.car.model.Car;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/cars")
public class CarViewController {

    private final RestTemplate restTemplate = new RestTemplate();

    // Show list of all cars
    @GetMapping
    public String listCars(Model model) {
        ResponseEntity<List<Car>> response = restTemplate.exchange(
                "http://localhost:8081/api/car/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Car>>() {}
        );
        List<Car> cars = response.getBody();
        model.addAttribute("cars", cars);
        return "viewCars";  // viewCars.jsp
    }

    // Show form to add a new car
    @GetMapping("/add")
    public String showAddForm() {
        return "add";  // add.jsp
    }

    // Handle form submission and save new car via REST API
    @PostMapping("/save")
    public String saveCar(@RequestParam String brand,
                          @RequestParam String model,
                          @RequestParam double price,
                          @RequestParam int ownerId) {

        Car car = new Car();
        car.setBrand(brand);
        car.setModel(model);
        car.setPricePerDay(price);

        List<Car> cars = Collections.singletonList(car);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<Car>> request = new HttpEntity<>(cars, headers);

        restTemplate.postForEntity(
                "http://localhost:8080/api/car/add/" + ownerId,
                request,
                String.class
        );

        return "redirect:/cars";  // redirects back to car list
    }
}
