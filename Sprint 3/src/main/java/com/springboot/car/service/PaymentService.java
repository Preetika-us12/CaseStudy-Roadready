package com.springboot.car.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.car.exception.InvalidInputException;
import com.springboot.car.model.Booking;
import com.springboot.car.model.Customer;
import com.springboot.car.model.Payment;
import com.springboot.car.repository.BookingRepository;
import com.springboot.car.repository.CustomerRepository;
import com.springboot.car.repository.PaymentRepository;

@Service
public class PaymentService {

	 private final CustomerRepository customerRepository;
	    private final BookingRepository bookingRepository;
	    private final PaymentRepository paymentRepository;

	    @Autowired
	    public PaymentService(CustomerRepository customerRepository, BookingRepository bookingRepository, PaymentRepository paymentRepository) {
	        this.customerRepository = customerRepository;
	        this.bookingRepository = bookingRepository;
	        this.paymentRepository = paymentRepository;
	    }
    public Payment addPayment(Payment payment, int customerId, int bookingId) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new InvalidInputException("Invalid customer ID"));

        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new InvalidInputException("Invalid booking ID"));
        payment.setCustomer(customer);
        payment.setBooking(booking);
        payment.setPaymentDate(LocalDate.now());
        return paymentRepository.save(payment);
    }

    public List<Payment> getAllPayments(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return paymentRepository.findAll(pageable).getContent();
    	}

    public Payment getPaymentById(int id) {
        return paymentRepository.findById(id)
            .orElseThrow(() -> new InvalidInputException("Invalid payment ID"));
    }

    public void deletePayment(int id) {
        paymentRepository.findById(id)
       .orElseThrow(() -> new InvalidInputException("Invalid payment ID"));
        paymentRepository.deleteById(id);
    	}

    public List<Payment> getPaymentsByCustomerId(int customerId) {
        List<Payment> payments = paymentRepository.findByCustomerId(customerId);
        if (payments == null || payments.isEmpty()) {
            throw new InvalidInputException("No payments found for customer ID: " + customerId);
        }
        return payments;
    }

    public Payment getPaymentByBookingId(int bookingId) {
        Payment payment = paymentRepository.findByBookingId(bookingId);
        if (payment == null) {
            throw new InvalidInputException("No payment found for booking ID: " + bookingId);
        }
        return payment;
    }



    public Payment updatePayment(int id, Payment updated) {
        Payment payment = getPaymentById(id);
        if (updated.getMethod() != null) payment.setMethod(updated.getMethod());
        if (updated.getStatus() != null) payment.setStatus(updated.getStatus());
        return paymentRepository.save(payment);
    		}
    
    public List<Payment> searchPayments(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Payment> paymentPage = paymentRepository.searchByStatusOrMethod(keyword, pageable);
        return paymentPage.getContent();
    }
}
