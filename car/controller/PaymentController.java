package com.springboot.car.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.car.model.Payment;
import com.springboot.car.service.PaymentService;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
     private PaymentService paymentService;
    private Logger logger = LoggerFactory.getLogger(PaymentController.class);
    
    
    @PostMapping("/add")
    public Payment addPayment(@RequestBody Payment payment, @RequestParam int customerId,
                              @RequestParam int bookingId) {
        return paymentService.addPayment(payment, customerId, bookingId);
    }

    @GetMapping("/get-all")
    public List<Payment> getAllPayments(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "1000000") int size) {
    	 if (page == 0 && size == 1000000) {
    	        logger.info("No Pagination call for all payments");
    	    } else {
    	        logger.info("Pagination call for payments - Page: {}, Size: {}", page, size);
    	    }
        return paymentService.getAllPayments(page, size);
    }


    @GetMapping("/view/{id}")
    public Payment getPayment(@PathVariable int id) {
        return paymentService.getPaymentById(id);
        }

    
    @DeleteMapping("/cancel/{id}")
    public String deletePayment(@PathVariable int id) {
        paymentService.deletePayment(id);
        return "Payment deleted";
    	}

    @PutMapping("/update/{id}")
    public Payment updatePayment(@PathVariable int id, @RequestBody Payment payment) {
        return paymentService.updatePayment(id, payment);
    }

    
    @GetMapping("/customer/{customerId}")
    public List<Payment> getPaymentsByCustomer(@PathVariable int customerId) {
        return paymentService.getPaymentsByCustomerId(customerId);
    
    }

    @GetMapping("/booking/{bookingId}")
    public Payment getPaymentByBooking(@PathVariable int bookingId) {
        return paymentService.getPaymentByBookingId(bookingId);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Payment>> searchPayments(@RequestParam String keyword,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        List<Payment> payments = paymentService.searchPayments(keyword, page, size);
        return ResponseEntity.ok(payments);
    }
}

