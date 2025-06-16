package com.springboot.car.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.springboot.car.model.Customer;

@Component
public class CustomerDto {
    private int id;
    private String name;
    private String username;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
   
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public List<CustomerDto> convertCustomerIntoDto(List<Customer> list) {
        List<CustomerDto> listDto = new ArrayList<>();
        list.stream().forEach(learner -> {
        	CustomerDto dto = new CustomerDto();
            dto.setId(learner.getId());
            dto.setName(learner.getName());
            dto.setUsername(learner.getUser().getUsername());
            listDto.add(dto);
        });

        return listDto;
    }

    
}