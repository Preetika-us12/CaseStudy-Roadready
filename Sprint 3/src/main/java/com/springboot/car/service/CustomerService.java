package com.springboot.car.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.car.dto.CustomerDto;
import com.springboot.car.exception.InvalidInputException;
import com.springboot.car.model.Customer;
import com.springboot.car.model.User;
import com.springboot.car.repository.CarRepository;
import com.springboot.car.repository.CustomerRepository;

@Service
public class CustomerService {
	 private CustomerRepository customerRepository;
	 private UserService userService; 
	 private final CarRepository carRepository;
		@Autowired
		private CustomerDto customerDto;
	   public CustomerService(CustomerRepository customerRepository,UserService userService,CarRepository carRepository) {
		   this.customerRepository=customerRepository;
		   this.userService = userService;
		   this.carRepository=carRepository;
	   }
	   
	   public Customer insertCustomer(Customer customer) {  
			User user = customer.getUser();
			user.setRole("CUSTOMER");
			user = userService.signUp(user);
			customer.setUser(user);
			return customerRepository.save(customer);
		}
	   
	   public List<Customer> getAllCustomers(int page, int size) {
		    Pageable pageable = PageRequest.of(page, size);
		    return customerRepository.findAll(pageable).getContent();
		}
	   
	   public void deleteCustomer(int id) {
		   Customer deletedcustomer = customerRepository.findById(id)
			        .orElseThrow(() -> new RuntimeException("Invalid ID Given"));
			    customerRepository.deleteById(id);
	   }
	   public Customer getCustomerById(int id) {
		   return customerRepository.findById(id)
				   .orElseThrow(()-> new RuntimeException("ID is Invalid"));
	   }
	   
	   public Customer updateCustomer(int id, Customer customer) {
		    Customer updatedcustomer = customerRepository.findById(id)
		        .orElseThrow(() -> new RuntimeException("Invalid ID Given"));

		    if (customer.getName() != null) {
		        updatedcustomer.setName(customer.getName());
		    }
		    if (customer.getEmail() != null) {
		        updatedcustomer.setEmail(customer.getEmail());
		    }
		    if (customer.getPhone() != null) {
		        updatedcustomer.setPhone(customer.getPhone());
		    }
		    if (customer.getUser() != null) {
		        updatedcustomer.setUser(customer.getUser()); 
		    }

		    return customerRepository.save(updatedcustomer);
		}

	   
	   public Customer getCustomerByUsername(String username) {
			return customerRepository.getCustomerByUsername(username);
		}
	   
	   public List<Customer> searchCustomers(String keyword, int page, int size) {
	        Pageable pageable = PageRequest.of(page, size);
	        Page<Customer> resultPage = customerRepository.searchCustomers(keyword, pageable);
	        return resultPage.getContent();
	   }
	   
	   
	   
	   
}
