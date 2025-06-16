package com.springboot.car.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.car.model.Manager;
import com.springboot.car.service.ManagerService;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {
	
    @Autowired
    private ManagerService managerService;

 
    @PostMapping("/add")
    public Manager postManager(@RequestBody Manager manager) {
        return managerService.postManager(manager);
    }
}