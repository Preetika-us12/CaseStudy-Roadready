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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.car.model.Customer;
import com.springboot.car.service.CustomerService;

@RestController
public class CustomerController {

	
	@Autowired
	private CustomerService customerService;
	  private Logger logger = LoggerFactory.getLogger("CustomerController");
	
	@PostMapping("/api/customer/add")
	public Customer insertCustomer(@RequestBody Customer customer) {
		return customerService.insertCustomer(customer);	
	}
	
	@GetMapping("/api/customer/get-all")
	public ResponseEntity<?> getAllCustomers(
	        @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
	        @RequestParam(name = "size", required = false, defaultValue = "1000000") Integer size) {
	    if (page == 0 && size == 1000000)
	        logger.info("No Pagination call for all customers");
	    List<Customer> customers = customerService.getAllCustomers(page, size);
	    return ResponseEntity
	            .status(HttpStatus.OK)
	            .body(customers);
	}
	
	@DeleteMapping("/api/customer/delete/{id}")
	public ResponseEntity<?> deleteCustomer(@PathVariable int id) {
		 customerService.deleteCustomer(id);
		 return ResponseEntity.status(HttpStatus.OK).body("Customer deleted");
	}
	
	@GetMapping("/api/customer/get-one")
	public Customer getCustomerById(Principal principal) {
		String username = principal.getName(); 
		return customerService.getCustomerByUsername(username);
	}
	@PutMapping("/api/customer/update/{id}")
	public Customer updateCustomer(@PathVariable int id,@RequestBody Customer updatedCustomer) {
		 logger.info("ID given is : " + id);
		return customerService.updateCustomer(id,updatedCustomer);
	}
	
	@GetMapping("/api/customer/search")
	public List<Customer> searchCustomers(
	        @RequestParam String keyword,
	        @RequestParam(name = "page", required = false, defaultValue = "0") int page,
	        @RequestParam(name = "size", required = false, defaultValue = "1000000") int size) {
	    return customerService.searchCustomers(keyword, page, size);
	}
	
	
}
