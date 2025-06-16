package com.springboot.car.service;

import org.springframework.stereotype.Service;

import com.springboot.car.model.Manager;
import com.springboot.car.model.User;
import com.springboot.car.repository.ManagerRepository;

@Service
public class ManagerService {

    private ManagerRepository managerRepository;
    private UserService userService;
    public ManagerService(ManagerRepository managerRepository,UserService userService) {
        this.managerRepository = managerRepository;
        this.userService = userService;
    }

    public Manager postManager(Manager manager) {
    	 User user = manager.getUser();
         user.setRole("MANAGER");
         user = userService.signUp(manager.getUser());

         manager.setUser(user);
         return managerRepository.save(manager);
    }
}